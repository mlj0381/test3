package com.wo56.business.sys.intf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.components.citys.City;
import com.framework.components.citys.District;
import com.framework.components.citys.Province;
import com.framework.core.SysContexts;
import com.framework.core.cache.CacheFactory;
import com.framework.core.cache.impl.SysCfgCache;
import com.framework.core.cache.vo.SysCfg;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.encrypt.K;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.SysCfgUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.cm.intf.CmUserInfoTF;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserOrgRel;
import com.wo56.business.cm.vo.CmUserRelationshipCorrespond;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.sys.impl.PortalBusiSV;
import com.wo56.business.sys.vo.SysTenantDef;
import com.wo56.business.sys.vo.SysVerInfo;
import com.wo56.business.sys.vo.TableDiy;
import com.wo56.business.sys.vo.out.LoginUserOut;
import com.wo56.business.sys.vo.out.UserRoleOut;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.EncryPwdUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class PortalBusiTF {
	/**
	 * 
	 * 登录接口，判断输入的账号，密码是否正确
	 * 
	 * code=100002
	 * 
	 * @param inParam
	 * 			登录账号
	 *          登录密码：密码为前台加密的密码
	 * @return
	 *         0 表示登录成功
	 *         1 表示登录失败
	 * @throws Exception
	 */
	public LoginUserOut login(Map<String, String> inParam) throws Exception{
		
		String loginAcct=DataFormat.getStringKey(inParam, "loginAcct");
		String inputPwd=DataFormat.getStringKey(inParam, "passWord");
		Long tenantId=DataFormat.getLongKey(inParam, "tenantId");
		if(StringUtils.isEmpty(loginAcct)){
			throw new BusinessException("登录账号不能为空");
		}
		
		if(StringUtils.isEmpty(inputPwd)){
			throw new BusinessException("登录密码不能为空");
		}
		
		if(tenantId==null){
			throw new BusinessException("登录的公司编码为空");
		}
		
		inputPwd=EncryPwdUtil.pwdDecryption(inputPwd);
		
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF)SysContexts.getBean("cmUserInfoTF");

		CmUserInfo userInfo=cmUserInfoTF.queryUserPwd(inParam);
		LoginUserOut loginUserOut=new LoginUserOut(); 
		if(userInfo==null){
			loginUserOut.setResult(EnumConsts.Result.FAIL);
			return loginUserOut;
		}
		
		String userPwd=userInfo.getLoginPwd();
		//密码不正确  
		if (!K.j_s(inputPwd).equals(userPwd)) {
			loginUserOut.setResult(EnumConsts.Result.FAIL);
			return loginUserOut;
		}
		
		//判断用户是否属于该租户下面的
		
		PortalBusiSV portalBusiSV = (PortalBusiSV)SysContexts.getBean("portalBusiSV");
		CmUserOrgRel sysUserOrgRel= portalBusiSV.getUserOrgRel(userInfo.getUserId(), tenantId);
		if(sysUserOrgRel==null){
			throw new BusinessException("登录的账号不属于该公司！");
		}
		
		BeanUtils.copyProperties(loginUserOut,userInfo);
		loginUserOut.setResult(EnumConsts.Result.SUCCESS);
		loginUserOut.setTenantCode(CommonUtil.getTennatCodeById(sysUserOrgRel.getTenantId()));
		loginUserOut.setTenantId(sysUserOrgRel.getTenantId());
		if(OraganizationCacheDataUtil.getOrganization(sysUserOrgRel.getOrgId())!=null){
			loginUserOut.setOrgCode(OraganizationCacheDataUtil.getOrganization(sysUserOrgRel.getOrgId()).getOrgCode());
		}else{
			loginUserOut.setOrgCode(null);
		}
		loginUserOut.setOrgId(sysUserOrgRel.getOrgId());
		if(OraganizationCacheDataUtil.getOrganization(sysUserOrgRel.getOrgId())!=null){
			loginUserOut.setOrgName(OraganizationCacheDataUtil.getOrganization(sysUserOrgRel.getOrgId()).getOrgName());
		}else{
			loginUserOut.setOrgName(null);
		}
		loginUserOut.setTenantName(CommonUtil.getTennatNameById(sysUserOrgRel.getTenantId()));
		
		Map<String, String> roleIn=new HashMap<String, String>();
		roleIn.put("userId", String.valueOf(userInfo.getUserId()));
		List<Integer> roles=new ArrayList<Integer>();
		List<UserRoleOut> listOutParamVO=getOperatorOwnerRole(roleIn);
		if(listOutParamVO!=null && listOutParamVO.size()>0){
			for(UserRoleOut roleOut:listOutParamVO){
				roles.add(roleOut.getRoleId());
			}
		}
		loginUserOut.setRoles(roles);
		 return loginUserOut;
	}
	/**
	 * 查询用户的角色
	 * 
	 * code=100003
	 * 
	 * @param inParam
	 *          userId 用户的id
	 * @return
	 *         返回角色id
	 * @throws Exception
	 */
	public List<UserRoleOut> getOperatorOwnerRole(Map<String, String> inParam) throws Exception{
		Long userId=DataFormat.getLongKey(inParam, "userId");
		PortalBusiSV portalBusiSV = (PortalBusiSV)SysContexts.getBean("portalBusiSV");
		return portalBusiSV.getOperatorOwnerRole(userId);
	}
	
	/**
	 * lyh
	 * 登录接口，判断输入的账号，密码是否正确
	 * 
	 * @param inParam
	 * 			登录账号
	 *          登录密码：密码为前台加密的密码
	 *          用户类型：
	 *  @return CmUserInfo
	 * @throws Exception
	 */
	public CmUserInfo appLogin(Map<String, String> inParam) throws Exception{
		
		String loginAcct=DataFormat.getStringKey(inParam, "loginAcct");
		String inputPwd=DataFormat.getStringKey(inParam, "passWord");
		if(StringUtils.isEmpty(loginAcct)){
			throw new BusinessException("", "登录账号不能为空");
		}
		if(StringUtils.isEmpty(inputPwd)){
			throw new BusinessException("", "登录密码不能为空");
		}
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF)SysContexts.getBean("cmUserInfoTF");
		CmUserInfo userInfo=cmUserInfoTF.queryAppUserPwd(inParam);
		if(userInfo==null){
			throw new BusinessException("该账号未注册！");
		}
		String userPwd=userInfo.getLoginPwd();
		//密码不正确
		if (!K.j_s(inputPwd).equals(userPwd)) {
			throw new BusinessException("输入密码不正确");
		}
		return userInfo;
	}
	/**
	 * 根据租户的编码获取租户名称，租户id
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map getCompanyInfo(Map<String, String> inParam) throws Exception{
		Map<String,Object> retMap=new HashMap<String, Object>();
		String tenantCode=DataFormat.getStringKey(inParam, "tenantCode");
		String tenantType=DataFormat.getStringKey(inParam, "tenantType");
		if(StringUtils.isEmpty(tenantCode)){
			throw new BusinessException("公司编码没有填，请重新输入！");
		}
		PortalBusiSV portalBusiSV = (PortalBusiSV)SysContexts.getBean("portalBusiSV");
		SysTenantDef sysTenantDef= portalBusiSV.getSysTenantDefByCode(tenantCode,tenantType);
		if(sysTenantDef!=null){
			retMap.put("name", sysTenantDef.getName());
			retMap.put("tenantId", sysTenantDef.getTenantId());
			return retMap;
		}
		
		return retMap;
	}
	
	/**
	 * 省市县区数据
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map queryAddressInfo(Map<String, String> inParam) throws Exception{
		Map rtn = new HashMap();
		List<Map<String,Object>> provinceList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> cityList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> countyList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> streetList=new ArrayList<Map<String,Object>>();
		List<Province> list = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE");
		for (Province province : list) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", province.getId());
			map.put("name", province.getName());
			provinceList.add(map);
		}
		List<City> cityListData = SysStaticDataUtil.getCityDataList("SYS_CITY");
		for (City city : cityListData) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", city.getId());
			map.put("name", city.getName());
			map.put("provId", city.getProvId());
			cityList.add(map);
		}
		List<District> districtDataList = SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT");
		for (District district : districtDataList) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", district.getId());
			map.put("name", district.getName());
			map.put("cityId", district.getCityId());
			countyList.add(map);
		}
//		 List<Street> streetDataList = SysStaticDataUtil.getStreetDataList("SYS_STREET");
//		 for (Street street : streetDataList) {
//			 Map<String,Object> map = new HashMap<String,Object>();
//			map.put("id", street.getId());
//			map.put("name", street.getName());
//			map.put("districtId", street.getDistrictId());
//			streetList.add(map);
//		}
		rtn.put("SYS_PROVINCE", provinceList);
		rtn.put("SYS_CITY",cityList );
		rtn.put("SYS_DISTRICT",countyList);
//		rtn.put("SYS_STREET",streetList);
		return rtn;
	}
	/**
	 * 系统参数
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map querySysCfg(Map<String, String> inParam) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		  String cfgName = DataFormat.getStringKey(inParam, "cfgName");
		   if(!CommonUtil.isCollection(cfgName)){
			   throw new BusinessException("输入的查询字段值得数据类型必须是数组！");
		   }
		   JSONArray jsonArray = JSONArray.fromObject(inParam.get("cfgName"));
		   List<String> cfgNames = jsonArray;
		   List<SysCfg> list = new ArrayList<SysCfg>();
		   SysCfg cfg = null;
		   if(cfgNames.size() != 0){
			   for(String cn : cfgNames){
				   //循环获取数据
				  try {
					  cfg =  SysCfgUtil.getSysCfg(cn);
					  if(cfg != null){
						  list.add(cfg);
					  }
				} catch (Exception e) {
				    //批量获取不抛异常，打印错误日志。单个获取抛出异常
					if(cfgNames.size() == 1){
						throw new BusinessException("没有找到字段为["+cn+"]系统参数配置！");
					}else{
						//log.error("获取字段未["+cn+"]系统参数数据失败，或该系统参数配置不存在。");
					}
					
				}  
			   }
 
		   }else{
			  //获取所有APP控制系统参数
			   net.sf.ehcache.Cache cache = CacheFactory.getCache(SysCfgCache.class.getName());
			   List<String> keyList = cache.getKeys();
				for (String key : keyList) {
					cfg = SysCfgUtil.getSysCfg(key);
					if(cfg.getCfgSystem()!=null){
	                   int cfgValue =  cfg.getCfgSystem();
						if (cfgValue == 0 
								|| cfgValue == 3
								  || "APP_VERSION_IOS".equals(cfg.getCfgName()) 
								    || "APP_VERSION_ANDROID".equals(cfg.getCfgName())) {
							list.add(cfg);
						
						}
					}
				}
		   }
		   List listOut = new ArrayList();
		   for(SysCfg sc : list){
			        Map map = new HashMap();
					map.put("cfgName", sc.getCfgName());
					map.put("cfgValue", sc.getCfgValue());
					if(cfg.getCfgRemark() != null){
						map.put("cfgRemark", sc.getCfgRemark());
					}else{
						map.put("cfgRemark", "");
					}
					map.put("cfgSystem", sc.getCfgSystem()+"");
					listOut.add(map);
		   }
		   return JsonHelper.parseJSON2Map(JsonHelper.json(listOut)); 
	}
	
	/**
	 * 系统静态参数参数
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map querySysStatic(Map<String, String> inParam) throws Exception{
		   String codeType = DataFormat.getStringKey(inParam, "codeType");
		   if(!CommonUtil.isCollection(codeType)){
			   throw new BusinessException("输入的查询字段值得数据类型必须是数组！");
		   }
		   JSONArray jsonArray = JSONArray.fromObject(inParam.get("codeType"));
		   List<String> codeTypes = jsonArray;
		   if(codeTypes.size() == 0){
			   throw new BusinessException("请输入查询数据值不能为空！"); 
		   }
		   List listOut = new ArrayList();
		   for(String ct : codeTypes){
			   List<SysStaticData> listDatas =  SysStaticDataUtil.getSysStaticDataList(ct);
			   Map map = new HashMap();
			   List list = new ArrayList();
			   if(listDatas != null){
				   for(SysStaticData ssd : listDatas){
					    Map m = new HashMap();
						m.put("codeValue", ssd.getCodeValue());
						m.put("codeName", ssd.getCodeName());
						m.put("sortId", ssd.getSortId()+"");
						if(ssd.getCodeDesc() != null){
							m.put("codeDesc", ssd.getCodeDesc());
						}else{
							m.put("codeDesc", "");
						}
						list.add(m);
					   }
			   }else{
				   if(codeTypes.size() == 1){
					   throw new BusinessException("没有找到字段为["+ct+"]静态参数数据配置！");
				   }else{
					//   log.error("获取字段为["+ct+"]静态参数数据失败，或该静态参数配置不存在。");
				   }
			   }
			   map.put("codeType", ct);
			   map.put("listData", list);
			   listOut.add(map);
		   }
		   return JsonHelper.parseJSON2Map(JsonHelper.json(listOut));
	}
	
	
	/**
	 * 入参  
	 *     osType  系统类型   1:IOS 2ANDROID
	 *     verType 版本类型   1、车主版 2、货主版
	 *     userPhone 用户手机
	 * 
	 * 出参 
	 *    updateType 更新方式  1 强制、0 可选
	 *    verNum  版本号
	 *    verPath  版本路径
	 *    verDesc 版本描述
	 *    remark  备注
	 * 
	 * 
	 * 查询系统版本信息接口
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getVerInfo(Map<String, String> inParam) throws Exception {
		Session session = SysContexts.getEntityManager(true);
		int osType = DataFormat.getIntKey(inParam, "osType");
		int verType = DataFormat.getIntKey(inParam, "verType");
		String userPhone = DataFormat.getStringKey(inParam, "userPhone");
		if(osType <= 0){
			throw new BusinessException("请输入正确的系统类型(1:IOS 2:ANDROID)！");
		}
		if (!CommonUtil.isContains(new int[]{1,2}, osType)) {
			throw new BusinessException("输入的系统类型无效！");
		}
		if(verType <= 0){
			throw new BusinessException("请输入正确的APP版本类型(1、管家版 2、师傅版)！");
		}
		if (!CommonUtil.isContains(new int[]{1,2,3}, verType)) {
			throw new BusinessException("输入的APP版本类型无效！");
		}
		if(!StringUtils.isEmpty(userPhone)){
			if(!CommonUtil.isCheckPhone(userPhone)){
				throw new BusinessException("请输入正确的手机号！");
			}
		}
		Criteria ca = session.createCriteria(SysVerInfo.class);
		ca.add(Restrictions.eq("osType", (long)osType));
		ca.add(Restrictions.eq("verType", (long)verType));
		ca.add(Restrictions.eq("state", 1L)); //1有效 0 无效
		List list = ca.list();
		SysVerInfo sysVerInfo = null;
		if(list.size() > 0){
			if(list.size() == 1){
				sysVerInfo = (SysVerInfo) list.get(0);
			}else{
				throw new BusinessException("查找到的版本更新信息有多条！");
			}
		}else{
			throw new BusinessException("查找版本配置更新信息失败！");
		}
		Map reqMap = new HashMap();
		reqMap.put("updateType", sysVerInfo.getUpdateType()+"");
		reqMap.put("verNum", sysVerInfo.getVerNum());
		reqMap.put("verPath", sysVerInfo.getVerPath());
		reqMap.put("verDesc", sysVerInfo.getVerDesc());
		reqMap.put("remark", sysVerInfo.getRemark());
		return reqMap;
	}
	public Map getTableDiy(Map<String, String> inParam)throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager();
		Criteria ca = session.createCriteria(TableDiy.class);
		ca.add(Restrictions.eq("tenantId", user.getTenantId()));
		List<TableDiy> list = ca.list();
		Map map = new HashMap();
		List<TableDiy> newList = new ArrayList<TableDiy>();
		if(null != list && list.size()>0){
			for(TableDiy tab : list){
				if(tab.getState() == 1){
					newList.add(tab);
				}
			}
			map.put("tableDiy", newList);
		}
		return map;
	}
	
	/**
	 * 查询登录用户关联的用户
	 * @param inParam
	 * @return
	 */
	public Map getCmUserRelat(Map<String, String> inParam) throws Exception{
		Map map = new HashMap();
		map.put("info", "Y");
		
		BaseUser baseUser = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		CmUserInfo cmUserInfo = (CmUserInfo)session.get(CmUserInfo.class, baseUser.getUserId());
		Criteria caUser = session.createCriteria(CmUserRelationshipCorrespond.class);
		caUser.add(Restrictions.eq("loginAcct",cmUserInfo.getLoginAcct()));
		List<CmUserInfo> userInfoList = new ArrayList<CmUserInfo>();
		List<CmUserRelationshipCorrespond> poneList=caUser.list();
		if(caUser.list().size()>0){
			CmUserRelationshipCorrespond cmUserRelat = (CmUserRelationshipCorrespond)caUser.list().get(0);	
			if(cmUserRelat!=null){
				String relationshipLoginAcct = cmUserRelat.getRelationshipLoginAcct();
				String[] relationshipLoginAccts = relationshipLoginAcct.split(",");
				if(relationshipLoginAccts.length>0){
					List<String> userIdList = new ArrayList<String>();
					for(int i=0;i<relationshipLoginAccts.length;i++){
						userIdList.add(relationshipLoginAccts[i]);
					}
					if(userIdList.size()>0){
						Integer[] login = new Integer[]{1,3};
						Criteria userCa = session.createCriteria(CmUserInfo.class);
						userCa.add(Restrictions.in("loginAcct", userIdList));
						userCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
						userCa.add(Restrictions.in("loginType", login));
						//userCa.add(Restrictions.eq("tenantId", baseUser.getTenantId()));
						userInfoList=userCa.list();
					}
				}
			}
		}
		List<LoginUserOut> log = new ArrayList<LoginUserOut>();
		for(CmUserInfo temp : userInfoList){
			LoginUserOut out = new LoginUserOut();
			Query query = session.createSQLQuery("select org_id from cm_user_org_rel where user_id = :userId");
			List<Object> obj = (List<Object>)query.setParameter("userId", temp.getUserId()).list();
			BeanUtils.copyProperties(out,temp);
			if(obj!=null && obj.size() > 0){
				for(Object oo : obj){
					out.setOrgId(Long.valueOf(String.valueOf(oo)));
					Organization org = OraganizationCacheDataUtil.getOrganization(out.getOrgId());
					out.setOrgName(org.getOrgName());
					out.setTenantId(org.getTenantId());
				}
			}
			log.add(out);
		}
		map.put("userInfoList", log);
		return map;
	}
	/**
	 * 登录接口（切换账号）
	 */
	public LoginUserOut loginOut(Map<String, String> inParam) throws Exception{
		//BaseUser  = SysContexts.getCurrentOperator();
		String loginAcct = DataFormat.getStringKey(inParam, "loginAcct");
		long tenantId = DataFormat.getLongKey(inParam, "tenantId");
		long orgId = DataFormat.getLongKey(inParam,"orgId");
		if (tenantId <= 0)
			throw new Exception("请输入公司编号!");
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF)SysContexts.getBean("cmUserInfoTF");
		// 握零担版本上线之后，需要增加租户ID的查询条件
		CmUserInfo userInfo = cmUserInfoTF.queryUserPwdNew(inParam);
		LoginUserOut loginUserOut = new LoginUserOut(); 
		if (userInfo == null) {
			loginUserOut.setResult(EnumConsts.Result.FAIL);
			return loginUserOut;
		}
		
		String userPwd=userInfo.getLoginPwd();
		
		BeanUtils.copyProperties(loginUserOut,userInfo);
		loginUserOut.setResult(EnumConsts.Result.SUCCESS);
		loginUserOut.setOrgId(orgId);
		Map<String, String> roleIn=new HashMap<String, String>();
		roleIn.put("userId", String.valueOf(userInfo.getUserId()));
		List<Integer> roles=new ArrayList<Integer>();
		List<UserRoleOut> listOutParamVO=getOperatorOwnerRole(roleIn);
		if(listOutParamVO!=null && listOutParamVO.size()>0){
			for(UserRoleOut roleOut:listOutParamVO){
				roles.add(roleOut.getRoleId());
			}
		}
		loginUserOut.setRoles(roles);
		 return loginUserOut;
	}
	
	
}
