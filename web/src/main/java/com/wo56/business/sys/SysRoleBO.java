package com.wo56.business.sys;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.framework.core.SysContexts;
import com.framework.core.cache.CacheFactory;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.sys.vo.SysMenu;
import com.wo56.business.sys.vo.SysRole;
import com.wo56.business.sys.vo.in.DelSysMenuIn;
import com.wo56.business.sys.vo.in.DelSysRoleIn;
import com.wo56.business.sys.vo.in.DelSysUrlIn;
import com.wo56.business.sys.vo.in.QueryMenuIn;
import com.wo56.business.sys.vo.in.QueryRoleDtlIn;
import com.wo56.business.sys.vo.in.QuerySysMenuDtlIn;
import com.wo56.business.sys.vo.in.QuerySysMenuIn;
import com.wo56.business.sys.vo.in.QuerySysUrlDtlIn;
import com.wo56.business.sys.vo.in.QueryUrlIn;
import com.wo56.business.sys.vo.in.QueryUserRoleIn;
import com.wo56.business.sys.vo.in.SaveRoleMenuIn;
import com.wo56.business.sys.vo.in.SaveSysMenuIn;
import com.wo56.business.sys.vo.in.SaveSysRoleIn;
import com.wo56.business.sys.vo.in.SaveSysUrlIn;
import com.wo56.business.sys.vo.in.SysMenuIn;
import com.wo56.business.sys.vo.in.SysOperUrlIn;
import com.wo56.business.sys.vo.in.SysRoleIn;
import com.wo56.business.sys.vo.in.SysRoleUrlIn;
import com.wo56.business.sys.vo.in.SysUrlIn;
import com.wo56.common.cache.SysMenuCache;
import com.wo56.common.consts.InterFacesCodeConsts;
import com.wo56.common.consts.PermissionConsts;
import com.wo56.common.consts.SysStaticDataEnum;

public class SysRoleBO {

	/**
	 * 查询用户信息
	 * @return
	 * @throws Exception
	 */
	public String doQuerySysRole() throws Exception{
//		Map<String, String[]> map = SysContexts.getRequestParameterMap();
//		SysRoleIn sysRoleIn = new SysRoleIn();
//		BeanUtils.populate(sysRoleIn, map);
//	/*	ListOutParamVO<CmUserInfo> out = CallerProxy.call(cmUser, new TypeToken<ListOutParamVO<HashMap>>(){}.getType());
//		List<CmUserInfo> cmList = out.getContent();*/
//		PageOutParamVO<Map> out = CallerProxy.call(sysRoleIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
//		return JsonHelper.json(out.getContent()); 
		return "";
	}
	/**
	 * 保存角色信息
	 * @return
	 * @throws Exception
	 */
	public String doSaveSysRole() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SaveSysRoleIn saveSysRoleIn = new SaveSysRoleIn();
		BeanUtils.populate(saveSysRoleIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(saveSysRoleIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	/**
	 * lyh
	 * 平台管理员 添加角色并赋权
	 * @return
	 * @throws Exception
	 */
	public String saveUserRole() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SysRoleIn userRoleIn = new SysRoleIn(InterFacesCodeConsts.COMMON.DOSAVE_SYS_ROLE);
		BeanUtils.populate(userRoleIn, map);
		BaseUser baseUser= SysContexts.getCurrentOperator();
		
		userRoleIn.setOrgId(baseUser.getTenantId().intValue());
		userRoleIn.setTenantId(-1L);
		userRoleIn.setRoleType(SysStaticDataEnum.ROLE_TYPE.COMPANY);
		SimpleOutParamVO<Map> out = CallerProxy.call(userRoleIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return "";
	}
	
	
	/**
	 * lyh
	 * 公司管理员 添加角色并赋权
	 * @return
	 * @throws Exception
	 */
	public String saveUserRoleOfCompany() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SysRoleIn userRoleIn = new SysRoleIn(InterFacesCodeConsts.COMMON.DOSAVE_SYS_ROLE);
		BeanUtils.populate(userRoleIn, map);
		BaseUser baseUser= SysContexts.getCurrentOperator();
		
		userRoleIn.setOrgId(baseUser.getOrgId().intValue());
		userRoleIn.setTenantId(baseUser.getTenantId());
		userRoleIn.setRoleType(SysStaticDataEnum.ROLE_TYPE.COMMON);
		SimpleOutParamVO<Map> out = CallerProxy.call(userRoleIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return "";
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryUserRole() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryUserRoleIn queryUser =  new QueryUserRoleIn();
		BeanUtils.populate(queryUser, map);
		ListOutParamVO out = CallerProxy.call(queryUser, new TypeToken<ListOutParamVO>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 查询系统菜单信息
	 * @return
	 * @throws Exception
	 */
	public String doQuerySysMenu() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SysMenuIn sysMenuIn = new SysMenuIn();
		BeanUtils.populate(sysMenuIn, map);
		PageOutParamVO<Map> out = CallerProxy.call(sysMenuIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent()); 
	}
	/**
	 * 查询系统URl信息
	 * @return
	 * @throws Exception
	 */
	public String doQuerySysUrl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SysUrlIn sysUrlIn = new SysUrlIn();
		BeanUtils.populate(sysUrlIn, map);
		//ListOutParamVO out = CallerProxy.call(sysUrlIn, new TypeToken<ListOutParamVO>(){}.getType());
		PageOutParamVO<Map> out = CallerProxy.call(sysUrlIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	/**
	 * 保存系统URl信息
	 * @return
	 * @throws Exception
	 */
	public String doSaveSysUrl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SaveSysUrlIn sysUrlIn = new SaveSysUrlIn();
		BeanUtils.populate(sysUrlIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(sysUrlIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	/**
	 *保存菜单信息
	 * @return
	 * @throws Exception
	 */
	public String doSaveSysMenu() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SaveSysMenuIn sysMenuIn = new SaveSysMenuIn();
		BeanUtils.populate(sysMenuIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(sysMenuIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	
	/**
	 * 检索菜单信息
	 * @return
	 * @throws Exception
	 */
	public String querySysMenuByMenuName() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QuerySysMenuIn query = new QuerySysMenuIn();
		String menuName  = DataFormat.getStringKey(map,"menuName");
		query.setMenuName(menuName);
		ListOutParamVO<SysMenu> out = CallerProxy.call(query, new TypeToken<ListOutParamVO<SysMenu>>(){}.getType());
		List<SysMenu> iparmList = out.getContent();
		return JsonHelper.json(out);
	}
	
	
	/**
	 * 查询角色对应的url
	 * @return
	 * @throws Exception
	 */
	public String queryUrlByRoleId() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryUrlIn queryUrl =  new QueryUrlIn();
		BeanUtils.populate(queryUrl, map);
		ListOutParamVO out = CallerProxy.call(queryUrl, new TypeToken<ListOutParamVO>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	/**
	 * 给角色添加Url
	 * @return
	 * @throws Exception
	 */
	public String saveRoleUrl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SysRoleUrlIn sysRoleUrlIn = new SysRoleUrlIn();
		BeanUtils.populate(sysRoleUrlIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(sysRoleUrlIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	/**
	 * 查询URL详情
	 * @return
	 * @throws Exception
	 */
	public String querySysUrlDtl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QuerySysUrlDtlIn sysUrlDtlIn = new QuerySysUrlDtlIn();
		BeanUtils.populate(sysUrlDtlIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(sysUrlDtlIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	
	/**
	 * 查询菜单详情
	 * @return
	 * @throws Exception
	 */
	public String querySysMenuDtl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QuerySysMenuDtlIn menuDtl = new QuerySysMenuDtlIn();
		BeanUtils.populate(menuDtl, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(menuDtl, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	/**
	 *查询全部的菜单
	 * @return
	 */
	public String queryAllSysMenu(){
		 List<SysMenu> menuCache=(List<SysMenu>)CacheFactory.get(SysMenuCache.class.getName(), PermissionConsts.GrantConstant.SYS_MENU);
		if(menuCache.size()<0){
			menuCache=new ArrayList<SysMenu>();	
		}
		 return JsonHelper.json(menuCache);
	}
	/**
	 *删除URL
	 * @return
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public String doDelUrl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		DelSysUrlIn sysUrlIn = new DelSysUrlIn();
		BeanUtils.populate(sysUrlIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(sysUrlIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	
	/**
	 *删除菜单
	 * @return
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public String doDelMenu() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		DelSysMenuIn sysMenuIn = new DelSysMenuIn();
		BeanUtils.populate(sysMenuIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(sysMenuIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	public String doSaveRoleMenu() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SaveRoleMenuIn roleMenuIn = new SaveRoleMenuIn();
		BeanUtils.populate(roleMenuIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(roleMenuIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 查询角色对应的菜单
	 * @return
	 * @throws Exception
	 */
	public String queryMenuByRoleId() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryMenuIn queryMenu =  new QueryMenuIn();
		BeanUtils.populate(queryMenu, map);
		ListOutParamVO out = CallerProxy.call(queryMenu, new TypeToken<ListOutParamVO>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	/**
	 * 删除角色
	 * @return
	 * @throws Exception
	 */
	public String delSysRole() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		DelSysRoleIn roleIn = new DelSysRoleIn();
		BeanUtils.populate(roleIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(roleIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	/**
	 * 查询角色详情
	 * @return
	 * @throws Exception
	 */
	public String querySysRoleDtl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryRoleDtlIn roleDtl = new QueryRoleDtlIn();
		BeanUtils.populate(roleDtl, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(roleDtl, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	
	/**
	 * 查询系统URl信息
	 * @return
	 * @throws Exception
	 */
	public String querySysOperUrl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SysOperUrlIn operUrlIn = new SysOperUrlIn();
		BeanUtils.populate(operUrlIn, map);
		ListOutParamVO out = CallerProxy.call(operUrlIn, new TypeToken<ListOutParamVO>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * lyh
	 * 查询角色组织为－1的角色
	 * @return
	 */
	public String queryRoleByPlat() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		int menuType=DataFormat.getIntKey(map,"menuType");
		SysRoleIn sysRoleIn = new SysRoleIn(InterFacesCodeConsts.COMMON.QUERY_SYS_ROLE);
		sysRoleIn.setTenantId(-1L);
		sysRoleIn.setRoleType(SysStaticDataEnum.ROLE_TYPE.COMPANY);
		sysRoleIn.setMenuType(menuType);
		ListOutParamVO<SysRole> out = CallerProxy.call(sysRoleIn, new TypeToken<ListOutParamVO<SysRole>>(){}.getType());
		
		Set<String> includeSet=new HashSet<String>();
		includeSet.add("roleId");
		includeSet.add("roleName");
		includeSet.add("menuType");
		return JsonHelper.jsonInclude(out.getContent(), includeSet);
	}
	
	/**
	 * lyh
	 * 
	 * 查询当前的租户下的角色
	 * 
	 * @return
	 */
	public String queryRoleByTenant() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SysRoleIn sysRoleIn = new SysRoleIn(InterFacesCodeConsts.COMMON.QUERY_SYS_ROLE);
		BaseUser baseUser=SysContexts.getCurrentOperator();
		sysRoleIn.setTenantId(baseUser.getTenantId());
		ListOutParamVO<SysRole> out = CallerProxy.call(sysRoleIn, new TypeToken<ListOutParamVO<SysRole>>(){}.getType());
		
		Set<String> includeSet=new HashSet<String>();
		includeSet.add("roleId");
		includeSet.add("roleName");
		includeSet.add("menuType");
		return JsonHelper.jsonInclude(out.getContent(), includeSet);
	}
	/***
	 * 查询平台角色的租户为－1
	 * @author 邱林锋
	 * @param queryRoleCm
	 * */
	public String queryRoleCm() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		SysRoleIn queryOrgIn = new  SysRoleIn(InterFacesCodeConsts.COMMON.QUERY_SYS_PTROLE);
		BeanUtils.populate(queryOrgIn, dataMap);
		PageOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/***
	 * 查询平台角色的租户为－1
	 * @author 邱林锋
	 * @param queryRoleCm
	 * */
	public String queryRoleST() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		SysRoleIn queryOrgIn = new  SysRoleIn(InterFacesCodeConsts.COMMON.QUERY_SYS_STROLE);
		BeanUtils.populate(queryOrgIn, dataMap);
		PageOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/**
	 * 删除角色
	 * @return
	 * @throws Exception
	 */
	public String delSysRoleAllByRoleId() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SysRoleIn roleIn = new  SysRoleIn(InterFacesCodeConsts.COMMON.DEL_SYS_STROLE);
		BeanUtils.populate(roleIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(roleIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
}
