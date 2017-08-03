package com.wo56.business.out.intf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.framework.components.citys.City;
import com.framework.components.citys.Province;
import com.framework.components.fdfs.impl.SysAttachFileBO;
import com.framework.components.fdfs.vo.SysAttach;
import com.framework.components.redis.RemoteCacheUtil;
import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysCfg;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.SysCfgUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.cm.intf.CmUserInfoTF;
import com.wo56.business.cm.intf.CmUserOrgRelTF;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.org.vo.Organization;
//import com.wo56.business.sche.cash.intf.CashManageTF;
import com.wo56.business.sche.index.intf.IndexTF;
import com.wo56.business.sche.ord.vo.out.ExceptionQueryParamOut;
import com.wo56.business.sche.ord.vo.out.RepairTaskDetailOutParam;
import com.wo56.business.sys.intf.PortalBusiTF;
//import com.wo56.business.sys.intf.ProductDefTF;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.LoginConst;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.EncryPwdUtil;
import com.wo56.common.utils.GenValidateCode;
import com.wo56.common.utils.SmsUtil;
/**
 * 提供接口给app
 * @author liyiye
 *
 *接口编号从： 300000
 *
 */
public class ToAppTF {
	private static final Log log = LogFactory.getLog(ToAppTF.class);
	
	/**
	 * 退出app
	 * 300071
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> loginOut(Map<String, String> inParam) throws Exception{
		BaseUser baseUser = SysContexts.getCurrentOperator();
		if(baseUser != null && baseUser.getUserId()!=null){
			SysContexts.setCurrentOperator(null);
		}
		return null;
	}
	
	/**
	 * lyh
	 * 上传图片
	 * 300002
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doUploadPrices(Map<String, String> inParam) throws Exception {
		Map reqMap = new HashMap();
		String flowId = "";
		Session session = SysContexts.getEntityManager();
//		FastDFSHelper client = FastDFSHelper.getInstance();
		String url = "";
		String pictureUrl = "";
		if (SysContexts.getFileItem("file") != null) {
			SysAttachFileBO sysAttachFile = (SysAttachFileBO) SysContexts
					.getBean("attach");
			flowId = sysAttachFile.doUpload();
			if (StringUtils.isNotEmpty(flowId)) {
				SysAttach sysAttach = (SysAttach) session.get(SysAttach.class,
						Long.valueOf(flowId));
				pictureUrl = sysAttach.getFullPathUrl();
//				url = client.getHttpURL(sysAttach.getStorePath()).split("\\?")[0];
			}
		} else if (StringUtils.isEmpty(flowId)) {
			throw new BusinessException("图片上传失败");
		}
		reqMap.put("flowId", flowId);
//		reqMap.put("picUrl", url);
		reqMap.put("picFullUrl", pictureUrl);
		return reqMap;
	}
	/**
	 * lyh
	 * 获取图片地址
	 * @param flowId
	 * @return
	 * @throws Exception
	 */
	private String getAttachFile(String flowId) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		/*SysAttachFileBO sysAttachFile = (SysAttachFileBO) SysContexts
				.getBean("attach");*/
		if (StringUtils.isNotEmpty(flowId)) {
			SysAttach sysAttach = (SysAttach) session.get(SysAttach.class,
					Long.valueOf(flowId));
//			pictureUrl = sysAttach.getStorePath();
			//FastDFSHelper client = FastDFSHelper.getInstance();

			return sysAttach.getFullPathUrl();
		}
		return "";
	}
	
	/**
	 * lyh
	 * 设置app登录或注册后，放到session的基本信息
	 * TODO 设置组织信息
	 * @param userInfo
	 * @throws Exception
	 */
	private String setUserInfoToSession(CmUserInfo userInfo) throws Exception{
		BaseUser user=new BaseUser(); 
		user.setOperId(userInfo.getUserId());
//		user.setOrgId(userInfo.getOrgId());
		user.setUserId(userInfo.getUserId());
		user.setUserName(userInfo.getUserName());
		user.setTelphone(userInfo.getLoginAcct());
		user.setUserType(userInfo.getUserType());
//		user.setTenantId(userInfo.getTenantId());
		Map<String, Object> attrs=new HashMap<String, Object>();
		attrs.put(LoginConst.BaseUserAttr.LOGIN_ACCOUNT, userInfo.getLoginAcct());
//		attrs.put(LoginConst.BaseUserAttr.TENANT_CODE, userInfo.getTenantCode());
//		attrs.put(LoginConst.BaseUserAttr.ORG_CODE, userInfo.getOrgCode());
		user.setAttrs(attrs);
		//从session里面拿组织id和顶级组织id
		return SysContexts.setCurrentOperator(user);
	}
	
	/**
	 * 申请提现
	 * 300029
	 * @param inParam
	 *         companyId 公司id
	 *         id 申请的订单主键，已逗号隔开
	 * @return
	 * @throws Exception
	 */
//	public Map<String,Object> applyCashApplication(Map<String, String> inParam) throws Exception{
//		
//		BaseUser user= SysContexts.getCurrentOperator();
//		Long userId=user.getUserId();
//		Long  tenantId=DataFormat.getLongKey(inParam,"companyId");
//		String ids=DataFormat.getStringKey(inParam, "id");
//		CashManageTF cashManageTF = (CashManageTF)SysContexts.getBean("cashManageTF");
//		return cashManageTF.applyCashApplication(userId, tenantId, ids);
//	}
//	
	
	
	
	
	

	

	
	
	
	
	
	

		
	
	
	
	/**
	 * 获取首页轮播的图片地址
	 * 300034
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String getImageUrl(Map<String, Object> inParam) throws Exception{
		IndexTF indexTF = (IndexTF)SysContexts.getBean("indexTF");
		String retStr=indexTF.getImageUrl();
		return JsonHelper.json(retStr);
	}
	

	
	
	
	
	
	/**
	 * 打款回传核销信息接口
	 * 400011
	 * @param inParam
	 *        
	 * @return
	 * @throws Exception
	 */
//	public void payCashForTms(Map<String, String> inParam) throws Exception{
//		Long appId=DataFormat.getLongKey(inParam,"appId");
//		Long auditId=DataFormat.getLongKey(inParam,"auditId");
//		String auditMan=DataFormat.getStringKey(inParam,"auditMan");
//		String verifyId=DataFormat.getStringKey(inParam,"verifyId");
//		
//		CashManageTF cashManageTF = (CashManageTF)SysContexts.getBean("cashManageTF");
//		cashManageTF.payCashForTms(appId, auditId, auditMan,verifyId);
//	}
	/**
	 * lyh
	 * 注册接口
	 * 300007
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map regAppUserInfo(Map<String, Object> inParam) throws Exception{
		
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF)SysContexts.getBean("cmUserInfoTF");
		String  captcha=DataFormat.getStringKey(inParam, "captcha");//验证码
		String  billId=DataFormat.getStringKey(inParam, "billId");//验证码
		String  password=DataFormat.getStringKey(inParam, "password");//验证码
		
		if(StringUtils.isBlank(captcha)){
			throw new BusinessException("输入的验证码为空，请重新输入！");
		}
		
		if(StringUtils.isBlank(billId)){
			throw new BusinessException("输入的手机号码为空，请重新输入！");
		}
		
		if(StringUtils.isBlank(password)){
			throw new BusinessException("输入的密码为空，请重新输入！");
		}
		
		if(!CommonUtil.isCheckPhone(billId)){
			throw new BusinessException("输入的手机号码格式不对，请重新输入！");
		}
		
//		if(!GenValidateCode.checkCode(billId,GenValidateCode.REGINSTER, captcha)){
//			throw new BusinessException("输入的验证码不对，请重新输入！");
//		}
		
		CmUserInfo cmUserInfo= cmUserInfoTF.regAppUserInfo(inParam);
		Map<String,Object> retMap=new HashMap<String, Object>();
		String rd = setUserInfoToSession(cmUserInfo);
		
		retMap.put("billId", cmUserInfo.getLoginAcct());
		retMap.put("auditState", SysStaticDataEnum.ADUIT_TYPE.SF_USER_UN_AUDIT);
		retMap.put("checkFlagName", SysStaticDataUtil.getSysStaticDataCodeName(EnumConsts.SysStaticData.SF_AUDIT_TYPE , String.valueOf(SysStaticDataEnum.ADUIT_TYPE.SF_USER_UN_AUDIT)));
		retMap.put("linkMan", "");
		retMap.put("loginAcct", cmUserInfo.getLoginAcct());
		retMap.put("userPic", "");
		retMap.put("userPicture", "");
		//Y已选择城市类型N没有选择城市类型
		retMap.put("cityType",  "N");
		retMap.put("tokenId", rd);
		return retMap;
	}
	
	/**
	 * 校验号码是否已注册
	 * 
	 * 师傅端，校验师傅的手机号码是否已经注册
	 * 300032
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String checkUserPhoneIfReg(Map<String, String> inParam) throws Exception{
		
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF)SysContexts.getBean("cmUserInfoTF");
		inParam.put("loginAcct", inParam.get("billId"));
		CmUserInfo rs=cmUserInfoTF.checkUserPhoneIfRegByType(inParam.get("billId"),SysStaticDataEnum.USER_TYPE.MASTER);
		Map<String, String> rtnMap=new HashMap<String, String>();
		if(rs!=null){
			rtnMap.put("result", "1");
		}else{
			rtnMap.put("result", "0");
		}
		
		return JsonHelper.json(rtnMap);
	}
	
	/**
	 * 忘记密码
	 * 300001
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public void setPassword(Map<String, String> inParam) throws Exception{
		String telPhone=inParam.get("phone").toString();
		
		String captcha = DataFormat.getStringKey(inParam, "captcha");
		
		if(!GenValidateCode.checkCode(telPhone,GenValidateCode.REGINSTER, captcha)){
			throw new BusinessException("验证码不正确！");
		}
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF)SysContexts.getBean("cmUserInfoTF");
		
		Map<String,String> params=new HashMap<String, String>();
			
		params.put("loginAcct", inParam.get("phone").toString());
		params.put("password", inParam.get("password").toString());
			
		cmUserInfoTF.setPassword(params);
		
	}
	
	/**
	 * 手机验证码验证
	 * 300033
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String validCode(Map<String, String> inParam) throws Exception{
		String billId=DataFormat.getStringKey(inParam, "billId");
		String code=DataFormat.getStringKey(inParam, "code");
		String validCode= RemoteCacheUtil.get(EnumConsts.Common.VALID_CODE+billId);
		Map<String, String> retMap=new HashMap<String, String>();
		if(StringUtils.isNotBlank(validCode) && validCode.equals(code)){
			inParam.put("loginAcct", billId);
			retMap.put("result", "1");
		}else{
			retMap.put("result", "0");
		}
		return JsonHelper.json(retMap);
	}
	
	/**
	 * lyh
	 * 
	 * 发送手机验证码
	 * 300006
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public void sendValidCode(Map<String, String> inParam) throws Exception{
		String billId=DataFormat.getStringKey(inParam, "billId");
		String busiType=DataFormat.getStringKey(inParam, "busiType");
		if(!CommonUtil.isCheckPhone(billId)){
			throw new BusinessException("输入的手机号码不正确！");
		}
		
		String code= GenValidateCode.setPhoneCode(billId,busiType);
		//TODO 发送验证
		SmsUtil.sendVaildCode(billId, code);
	}
	
	/**
	 * 保存师傅的增值服务
	 * 300046
	 * @param inParam
	 * @throws Exception
	 */
//	public void saveSfSvFromPt(Map<String, String> inParam) throws Exception{
//		String maintainType=DataFormat.getStringKey(inParam, "maintainType");
//		String opType=DataFormat.getStringKey(inParam, "opType");
//		saveSfServiceInst(maintainType,"",""+EnumConsts.SERVICE_INST_TYPE.VALUE_SERVICE_TYPE, opType);
//	}
	/**
	 * 保存师傅的普通服务
	 * 300047
	 * @param inParam
	 * @throws Exception
	 */
//	public void saveSfSvFromZz(Map<String, String> inParam) throws Exception{
//		String serverList=DataFormat.getStringKey(inParam, "serverList");
//		String opType=DataFormat.getStringKey(inParam, "opType");
//		saveSfServiceInst("",serverList,""+EnumConsts.SERVICE_INST_TYPE.COMMON_SERVICE_TYPE , opType);
//	}
	
	/**
	 * 保存服务实例
	 * 300009
	 * 
	 * @param inParam
	 *            maintainType  以逗号隔开，1(皮艺) ，2(板式)，3(返货),4(养护),5(其它)	
	 *            serverList 
	 *            	    serverCode  产品编码
	 *                  serverType  以逗号隔开，1(配送+安装),2(配送到家),3(上门安装)
	 * @return
	 * @throws Exception
	 */
//	private void saveSfServiceInst(String maintainType,String serverList,String serviceType,String opType) throws Exception{
//		Map<String,Object> tfParam=new HashMap<String,Object>();
//		String serviceAttrIds="";
//		List<Map<String, String>> list = new ArrayList();
////		List<Map<String,String>> services=new ArrayList<Map<String,String>>();
//		if(StringUtils.isNotBlank(maintainType)){
//			String[] maintainTypes=maintainType.split(",");
//			for (String maintainVl : maintainTypes) {
//				if(maintainVl.indexOf("1")!=-1){
//					serviceAttrIds=serviceAttrIds+SysStaticDataEnum.SCHE_VALUE_SERVICE_ATTR.SERVICE_ATTR_SKIN+",";
//				}
//				if(maintainVl.indexOf("2")!=-1){
//					serviceAttrIds=serviceAttrIds+SysStaticDataEnum.SCHE_VALUE_SERVICE_ATTR.SERVICE_ATTR_WOOD+",";
//				}
//				
//				if(maintainVl.indexOf("5")!=-1){
//					serviceAttrIds=serviceAttrIds+SysStaticDataEnum.SCHE_VALUE_SERVICE_ATTR.SERVICE_ATTR_OTHER+",";
//				}
//				
//				if(maintainVl.indexOf("3")!=-1){
//					Map<String, String> serviceMap=new HashMap<String, String>();
//					serviceMap.put("serviceIds", String.valueOf(SysStaticDataEnum.SCHE_VALUE_SERVICE_TYPE.VALUE_SERVICE_TYPE_RETURN_HUO));
//					serviceMap.put("serviceType", serviceType);
////					services.add(serviceMap);
//					list.add(serviceMap);
//				}
//				
//				if(maintainVl.indexOf("4")!=-1){
//					Map<String, String> serviceMap=new HashMap<String, String>();
//					serviceMap.put("serviceIds", String.valueOf(SysStaticDataEnum.SCHE_VALUE_SERVICE_TYPE.VALUE_SERVICE_TYPE_YANGHU));
//					serviceMap.put("serviceType", serviceType);
//					list.add(serviceMap);
//				}
//			}
//			
//			if(StringUtils.isNotBlank(serviceAttrIds)){
//				serviceAttrIds=serviceAttrIds.substring(0, serviceAttrIds.length()-1);
//				
//				Map<String, String> serviceMap=new HashMap<String, String>();
//				serviceMap.put("serviceIds", String.valueOf(SysStaticDataEnum.SCHE_VALUE_SERVICE_TYPE.VALUE_SERVICE_TYPE_MAINTAINT));
//				serviceMap.put("serviceType", serviceType);
//				list.add(serviceMap);
//			}
//			
//			tfParam.put("serviceAttrIds", serviceAttrIds);
//		}
//		if(StringUtils.isNotBlank(serverList)){
//		JSONArray jsonArray= JSONArray.fromObject(serverList);
//		Iterator iterator=  jsonArray.iterator();
//		while (iterator.hasNext()) {
//			JSONObject jsonObject=(JSONObject)iterator.next();
//			
//			String serverType=jsonObject.getString("serverType");
//			if(StringUtils.isNotBlank(serverType)){
//				Map<String,String> map=new HashMap<String, String>();
//				map.put("productCode", jsonObject.getString("serverCode"));
//				map.put("serverName", jsonObject.getString("serverName"));
//				map.put("serviceType", serviceType);
//				map.put("serviceIds", serverType);
//				list.add(map);
//			}
//		}
//		}
//		CmServiceInstTF cmServiceInstTF = (CmServiceInstTF)SysContexts.getBean("cmServiceInstTF"); 
//		
//		tfParam.put("services", list);
//		tfParam.put("opType", opType);
//		cmServiceInstTF.saveSfServiceInst(tfParam);
//		
//	}
//	
	/**
	 * 查询服务类型
	 * 
	 * 300008
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
//	public Map queryAPPProductCatalog(Map<String, Object> inParam) throws Exception{
//		
//		List<Map<String, Object>> list = new ArrayList();
//		
//		ProductDefTF productDefTF = (ProductDefTF)SysContexts.getBean("sysProductTF"); 
//		
//		List serverList= productDefTF.queryAPPProductCatalog(inParam);
//		Map<String, Object> retMap=new HashMap<String, Object>();
//		retMap.put("serverList", serverList);
//		
////		List<SysStaticData>  typelist=SysStaticDataUtil.getCodeValueListByCodeType(SysStaticDataEnum.SCHE_VALUE_SERVICE_TYPE.SCHE_VALUE_SERVICE_TYPE);
////		
//		List<Map<String,Object>> retList=new ArrayList<Map<String,Object>>();
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("serverCode", "");
//		map.put("serverName", "维修");
//		
//		List<Map<String,Object>> serList=new ArrayList<Map<String,Object>>();
//		Map<String, Object> serMap=new HashMap<String, Object>();
//		serMap.put("serverCode", "1");
//		serMap.put("serverName", "皮艺");
//		serList.add(serMap);
//		
//		serMap=new HashMap<String, Object>();
//		serMap.put("serverCode", "2");
//		serMap.put("serverName", "板式");
//		serList.add(serMap);
//		
//		serMap=new HashMap<String, Object>();
//		serMap.put("serverCode", "5");
//		serMap.put("serverName", "其他");
//		serList.add(serMap);
//		
//		map.put("childList", serList);
//		
//		retList.add(map);
//		
//		map=new HashMap<String, Object>();
//		map.put("serverCode", "3");
//		map.put("serverName", "返货");
//		
//		serList=new ArrayList<Map<String,Object>>();
//		serMap=new HashMap<String, Object>();
//		serMap.put("serverCode", "3");
//		serMap.put("serverName", "返货");
//		serList.add(serMap);
//		map.put("childList", serList);
//		
//		retList.add(map);
//		
//		map=new HashMap<String, Object>();
//		map.put("serverCode", "4");
//		map.put("serverName", "养护");
//		
//		serList=new ArrayList<Map<String,Object>>();
//		serMap=new HashMap<String, Object>();
//		serMap.put("serverCode", "4");
//		serMap.put("serverName", "养护");
//		serList.add(serMap);
//		map.put("childList", serList);
//		
//		retList.add(map);
//		
//
//		
//		retMap.put("valueAddServerList", retList);
//		
//		return retMap;
//	}
	
	

	/**
	 * lyh
	 * 修改密码
	 * 300026
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public void resetPassword(Map<String, Object> inParam) throws Exception{
		String telPhone="";
		BaseUser user= SysContexts.getCurrentOperator();
		if(user==null){
				throw new BusinessException("登录失效，请重新登录！");
		}
		telPhone=user.getTelphone();
			//修改密码
		String captcha = DataFormat.getStringKey(inParam, "captcha");
		
		if(!GenValidateCode.checkCode(telPhone,GenValidateCode.REGINSTER, captcha)){
			throw new BusinessException("验证码不正确！");
		}
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF)SysContexts.getBean("cmUserInfoTF");
		Long userId=user.getUserId();
		inParam.put("userId", userId);
		cmUserInfoTF.resetPassword(inParam);
	}
	

	

	/**
	 * 用户帐号信息
	 * 300025
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String statisticsBalance(Map<String, Object> inParam) throws Exception{
		IndexTF cmSfUseTfrTF = (IndexTF)SysContexts.getBean("indexTF");
		Map map=cmSfUseTfrTF.statisticsBalance();
		
		return JsonHelper.json(map);
	}
	
	

	/**
	 * 师傅流水查询(300044)
	 * @return
	 * @throws Exception
	 */
//	public String querySfFlowInfo(Map<String, Object> inParam) throws Exception{
//		CashManageTF cashManageTF = (CashManageTF)SysContexts.getBean("cashManageTF");
//		BaseUser user= SysContexts.getCurrentOperator();
//		IPage retPage= cashManageTF.querySfFlowInfo(user.getUserId());
//		Set<String> includeSet=new HashSet<String>();
//		includeSet.add("createDate");
//		includeSet.add("gsName");
//		includeSet.add("operType");
//		includeSet.add("paySts");
//		
//		includeSet.add("receiverName");
//		includeSet.add("settlementMoney");
//		includeSet.add("wayBillId");
//		
//		String ret=JsonHelper.jsonPageInclude(retPage, includeSet);
//		return ret;
//	}
	


		/**
	 * 查询师傅加入的公司列表
	 * 300041
	 * @return
	 * @throws Exception 
	 */
	public Map queryCompanyOfSf(Map<String, Object> inParam) throws Exception{
		CmUserOrgRelTF cmUserOrgRelTF = (CmUserOrgRelTF)SysContexts.getBean("cmUserOrgRelTF");
		BaseUser user= SysContexts.getCurrentOperator();
		List<Organization> organizations= cmUserOrgRelTF.queryOrgByUserId(user.getUserId());
		List<Map<String,Object>> retList=new ArrayList<Map<String,Object>>();
		if(organizations!=null && organizations.size()>0){
			for(Organization org:organizations){
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("companyId", org.getTenantId());
				map.put("companyName", org.getOrgName());
				retList.add(map);
			}
		}
		Map<String,List<Map<String,Object>>> map=new HashMap<String,List<Map<String,Object>>>(); 
		map.put("list", retList);
		return map;
	}	
	/***
	 * lyh 
	 *  首页数据统计
	 * 300080
	 * */
	public Map<String,Object> indexCount(Map<String,Object> inParam)throws Exception {
		IndexTF indexTF = (IndexTF)SysContexts.getBean("indexTF");
		Map rtnMap = indexTF.indexStatistics();
		return rtnMap;
	}
	/**
	 * lyh
	 * app 查询系统版本信息接口
	 * 300090
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> getVerInfo(Map<String, String> inParam)
			throws Exception {
		PortalBusiTF cmUserInfoTF = (PortalBusiTF)SysContexts.getBean("portalBusiTF");
		Map retMap = cmUserInfoTF.getVerInfo(inParam);
		return retMap;
	}
	
	public static void main(String[] args) {
		//md5 
//		String password="123456";
//		String md5=MD5.eccryptMD5(password);
//		System.out.println("e10adc3949ba59abbe56e057f20f883e".equals(md5));
		
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		Map<String,String> map=new HashMap<String, String>();
		map.put("code", "1");
		list.add(map);
		map=new HashMap<String, String>();
		map.put("code", "2");
		list.add(map);
		map=new HashMap<String, String>();
		map.put("code", "3");
		list.add(map);
		String jsonList="[{\"code\":\"1\"},{\"code\":\"2\"},{\"code\":\"3\"}]";
		System.out.println(jsonList);
		JSONArray jsonArray= JSONArray.fromObject(jsonList);
		Iterator iterator=  jsonArray.iterator();
		while (iterator.hasNext()) {
			JSONObject jsonObject=(JSONObject)iterator.next();
			System.out.println(jsonObject.get("code"));
		}	
	}
}
