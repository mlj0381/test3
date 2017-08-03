package com.wo56.business.cm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.vo.in.CmPlateFormUserInfoIn;
import com.wo56.business.cm.vo.in.CmUserInfoIn;
import com.wo56.business.cm.vo.in.CmUserInfoInParam;
import com.wo56.business.cm.vo.in.DelCmUserIn;
import com.wo56.business.cm.vo.in.DelUserAreaIn;
import com.wo56.business.cm.vo.in.QueryCmUserIn;
import com.wo56.business.cm.vo.in.QueryUserAreaIn;
import com.wo56.business.cm.vo.in.SaveCmUserInfoIn;
import com.wo56.business.cm.vo.in.SaveUserAreaIn;
import com.wo56.business.cm.vo.in.UserAreaParam;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.InterFacesCodeConsts;
import com.wo56.common.consts.IntfCodeConstsLyh;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class CmUserInfoBO extends BaseBO {

	/**
	 * 查询网点的开单员和业务员
	 * 调用接口编码：110000
	 * @param inParam
	 * 入参：
	 * 		orgId 网点id 
	 * 		tenantId 租户id
	 * 
	 * */
	public String doQueryOrgUser() throws Exception{
		CmUserInfoInParam cmInParam = new CmUserInfoInParam();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		cmInParam.setOrgId(baseUser.getOrgId());
		cmInParam.setTenantId(baseUser.getTenantId());
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		int state = DataFormat.getIntKey(map, "state");
		cmInParam.setState(state);
		
		//直接将后台的JSON串输出到前台
		ListOutParamVO<HashMap> out = CallerProxy.call(cmInParam, new TypeToken<ListOutParamVO<HashMap>>(){}.getType());
		List<HashMap> list = out.getContent();
		return JsonHelper.json(list);
	}
	
	
	/**
	 * 查询用户信息
	 * @return
	 * @throws Exception
	 */
	public String doQueryCmUser() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		CmUserInfoIn cmUser = new CmUserInfoIn();
		BeanUtils.populate(cmUser, map);
		PageOutParamVO<Map> out = CallerProxy.call(cmUser, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent()); 
	}
	/**
	 * lyh
	 * 保存用户信息
	 * @return
	 * @throws Exception
	 */
	public String doSaveCmUserInfo() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SaveCmUserInfoIn saveCmUser = new SaveCmUserInfoIn(InterFacesCodeConsts.CM.SAVE_CM_USER);
		BeanUtils.populate(saveCmUser, map);
		if(saveCmUser.getTenantId()==null || saveCmUser.getTenantId()==-1){
			BaseUser baseUser=SysContexts.getCurrentOperator();
			saveCmUser.setTenantId(baseUser.getTenantId());
		}
		int loginType=DataFormat.getIntKey(map,"loginType");
		//用户类型
		int userType=DataFormat.getIntKey(map,"userType");
		if(userType>0){
			saveCmUser.setUserType(userType);
		}
		if(loginType == -1){
			saveCmUser.setLoginType(SysStaticDataEnum.LOGIN_TYPE.WEB);
		}
		SimpleOutParamVO<Map> out = CallerProxy.call(saveCmUser, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return "";
	}
//	/**查询用户详情**/
//	public String doQueryCmUserByUserId() throws Exception{
//		Map<String, String[]> map = SysContexts.getRequestParameterMap();
//		QueryCmUserIn cmUser = new QueryCmUserIn();
//		BeanUtils.populate(cmUser, map);
//		//直接将后台的JSON串输出到前台
//		SimpleOutParamVO<Map> out = CallerProxy.call(cmUser, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
//		return JsonHelper.json(out.getContent());
//	}
	
	
	/**
	 * lyh
	 * 查询用户信息
	 * @return
	 * @throws Exception
	 */
	public String doQueryPlatformCmUser() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		CmPlateFormUserInfoIn cmUser = new CmPlateFormUserInfoIn();
		BeanUtils.populate(cmUser, map);
		PageOutParamVO<Map> out = CallerProxy.call(cmUser, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent()); 
	}
	
	/**
	 * 删除用户
	 * @return
	 * @throws Exception
	 */
	public String doDelCmUser() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		DelCmUserIn  delCmUser= new DelCmUserIn();
		BeanUtils.populate(delCmUser, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(delCmUser, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	public String getUserRole(){
		BaseUser user=SysContexts.getCurrentOperator();
		Map map = new HashMap();
		Map<String,Object> attrs= user.getAttrs();
		List<Integer> rolds=(List<Integer>) attrs.get(EnumConsts.Common.SESSION_ROLES);
		if(rolds.size()>0){
			for(int i=0;i<rolds.size();i++){
				int roleId=rolds.get(i);
				if(roleId ==SysStaticDataEnum.ADMIN_ROLE.SUPP_ADMINISTRATOR){
					map.put("roleType",SysStaticDataEnum.ADMIN_ROLE.SUPP_ADMINISTRATOR);
					break;
				}else if(roleId ==SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER){
					map.put("roleType", SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER);
					break;
				}else{
					map.put("roleType", SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER);
				}
			}
		}
		return JsonHelper.json(map);
	}
	
	/**
	 * 获取中转的开单网点
	 * @return
	 */
	public String getUserOrgType(){
		BaseUser user=SysContexts.getCurrentOperator();
		Organization organ = OraganizationCacheDataUtil.getOrganization(user.getOrgId());
		//OraganizationCacheDataUtil.
		List<Organization> orgList = new ArrayList<Organization>();
		if(organ.getOrgType()!=null&&organ.getOrgType()==SysStaticDataEnum.ORG_TYPE.FOXTOWN_CENTER){
			orgList = OraganizationCacheDataUtil.getTenantOrg(Long.valueOf(user.getTenantId()), 8);
		}else{
			orgList.add(organ);
		}
		Map map = new HashMap();
		map.put("orgList", orgList);
		map.put("orgType", organ.getOrgType());
		return JsonHelper.json(map);
	}

	
	
	/**
	 * lyh
	 * 查询公司管理员信息
	 * 
	 * 入参：
	 *     公司id
	 *     角色id 
	 *     名称
	 *     登录账号 
	 * 出参：
	 *    
	 * @return
	 * @throws Exception
	 */
	public String queryCompanyUser() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryCmUserIn cmUserIn=new QueryCmUserIn(InterFacesCodeConsts.CM.QUERY_CM_USER_USERID);
		BeanUtils.populate(cmUserIn, map);
		cmUserIn.setRoleType(SysStaticDataEnum.ROLE_TYPE.COMPANY);
		SimpleOutParamVO<Map> out = CallerProxy.call(cmUserIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * lyh
	 * 查询租户下面的员工信息
	 * 
	 * 入参：
	 *     公司id
	 *     角色id 
	 *     名称
	 *     登录账号 
	 * 出参：
	 *    
	 * @return
	 * @throws Exception
	 */
	public String queryOrgUser() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryCmUserIn cmUserIn=new QueryCmUserIn(InterFacesCodeConsts.CM.QUERY_CM_USER_USERID);
		BaseUser baseUser=SysContexts.getCurrentOperator();
		BeanUtils.populate(cmUserIn, map);
		cmUserIn.setTenantId(baseUser.getTenantId());
		cmUserIn.setRoleType(SysStaticDataEnum.ROLE_TYPE.COMMON);
		SimpleOutParamVO<Map> out = CallerProxy.call(cmUserIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 失效用户
	 * @return
	 * @throws Exception
	 */
	public String delUser() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryCmUserIn cmUserIn=new QueryCmUserIn(IntfCodeConstsLyh.CM.DEL_USER );
		BeanUtils.populate(cmUserIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(cmUserIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		
		return "";
	}
	
	/**查询用户详情**/
	public String doQueryCmUserByUserId() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryCmUserIn cmUser = new QueryCmUserIn(InterFacesCodeConsts.CM.QUERY_CUSTOMER_IN);
		BeanUtils.populate(cmUser, map);
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(cmUser, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 *查询区域列表
	 * @return
	 * @throws Exception
	 */
	public String queryAreaList() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryUserAreaIn areaIn = new QueryUserAreaIn();
		BeanUtils.populate(areaIn, map);
		ListOutParamVO<HashMap> out = CallerProxy.call(areaIn, new TypeToken<ListOutParamVO<HashMap>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	public String delArea() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		DelUserAreaIn areaIn = new DelUserAreaIn();
		BeanUtils.populate(areaIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(areaIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 给操作员添加区域
	 * @return
	 * @throws Exception
	 */
	public String doSaveUserArea() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		int size = DataFormat.getIntKey(map, "size");
		SaveUserAreaIn saveUserAreaIn = new SaveUserAreaIn();
		List<UserAreaParam> paramList = new ArrayList<UserAreaParam>();
		if(size>0){
			for(int i=0;i<size;i++){
				UserAreaParam users = new UserAreaParam();
				BeanUtils.populate(users, map, "list\\["+i+"\\].");
				if(users!=null){
					paramList.add(users);
				}
			}
			saveUserAreaIn.setUserAreaList(paramList);
		}
		SimpleOutParamVO<Map> out = CallerProxy.call(saveUserAreaIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent()); 
	}
	
	
	/**
	 * 修改用户的密码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String modifyCmUserInfoPwd() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		
		ICmUserInfoIntf cmUserInfoIntf = CallerProxy.getSVBean(ICmUserInfoIntf.class, "cmUserInfoTF", new TypeToken<Map>(){}.getType());
		cmUserInfoIntf.modifyUserInfoPwd(inParam);
	    return "";
	}
	/**
	 * 商户查询
	 * @return
	 */
	public String queryMerchan(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmUserInfoIntf intf = CallerProxy.getSVBean(ICmUserInfoIntf.class, "cmUserInfoTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.queryMerchan(inParam));
	}
}
