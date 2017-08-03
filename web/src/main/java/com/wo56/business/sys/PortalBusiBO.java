package com.wo56.business.sys;

import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.components.citys.City;
import com.framework.core.SysContexts;
import com.framework.core.cache.CacheFactory;
import com.framework.core.cache.impl.SysUrlDataCache;
import com.framework.core.exception.AuthException;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.interceptor.vo.SysUrl;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.CookieUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.SysStaticDataUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.vo.in.CmCompanyIn;
import com.wo56.business.cm.vo.in.QueryUserAreaIn;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.sys.vo.SysMenu;
import com.wo56.business.sys.vo.SysRoleGrant;
import com.wo56.business.sys.vo.in.LoginRelUserIn;
import com.wo56.business.sys.vo.in.LoginUserIn;
import com.wo56.business.sys.vo.in.queryCmUserRelarIn;
import com.wo56.business.sys.vo.out.LoginUserOut;
import com.wo56.common.cache.SysMenuCache;
import com.wo56.common.cache.SysRoleGrantCache;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.InterFacesCodeConsts;
import com.wo56.common.consts.LoginConst;
import com.wo56.common.consts.PermissionConsts;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.sms.utils.SysTenantExtendCacheUtil;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;
/**
 * 所有用户模型，权限的请求都放在这个bo
 * @author liyiye
 *
 */
public class PortalBusiBO {
	private static final Log log = LogFactory.getLog(PortalBusiBO.class);
	
	/**
	 * 查询登录用户关联的用户
	 * @param inParam
	 * @return
	 */
	public String getCmUserRelat() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		queryCmUserRelarIn query = new queryCmUserRelarIn();
		BeanUtils.populate(query, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 师傅合作商登录方法
	 * @return
	 * @throws Exception
	 */
	public String doLoginMaster() throws Exception {
		
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String loginAcct = DataFormat.getStringKey(map, "username").trim();
		String passWord = DataFormat.getStringKey(map, "password");
		Long tenantId = DataFormat.getLongKey(map, "tenantId");
		
		LoginUserIn loginUserIn=new LoginUserIn();
		loginUserIn.setLoginAcct(loginAcct);
		loginUserIn.setPassWord(passWord);
		loginUserIn.setTenantId(tenantId);
		loginUserIn.setUserType(String.valueOf(SysStaticDataEnum.USER_TYPE.MASTER_PARTNERS));
		loginUserIn.setLoginType(String.valueOf(SysStaticDataEnum.LOGIN_TYPE.WEB)+","+String.valueOf(SysStaticDataEnum.LOGIN_TYPE.WEB_AND_APP));
		return doLogin(loginUserIn);
	}
	/**
	 * 拉包公司登录方法
	 * @return
	 * @throws Exception
	 */
	public String doLoginPullPagCompany()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String loginAcct = DataFormat.getStringKey(map, "username").trim();
		String passWord = DataFormat.getStringKey(map, "password");
		Long tenantId = DataFormat.getLongKey(map, "tenantId");
		
		LoginUserIn loginUserIn=new LoginUserIn();
		loginUserIn.setLoginAcct(loginAcct);
		loginUserIn.setPassWord(passWord);
		loginUserIn.setTenantId(tenantId);
		loginUserIn.setUserType(String.valueOf(SysStaticDataEnum.USER_TYPE.PULL_PAG_COMPANY));
		loginUserIn.setLoginType(String.valueOf(SysStaticDataEnum.LOGIN_TYPE.WEB)+","+String.valueOf(SysStaticDataEnum.LOGIN_TYPE.WEB_AND_APP));
		return doLogin(loginUserIn);
	}
	
	/**
	 * 商家登录方法
	 * @return
	 * @throws Exception
	 */
	public String doLoginBusi() throws Exception {
		
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String loginAcct = DataFormat.getStringKey(map, "username").trim();
		String passWord = DataFormat.getStringKey(map, "password");
		Long tenantId = DataFormat.getLongKey(map, "tenantId");
		
		LoginUserIn loginUserIn=new LoginUserIn();
		loginUserIn.setLoginAcct(loginAcct);
		loginUserIn.setPassWord(passWord);
		loginUserIn.setTenantId(tenantId);
		loginUserIn.setUserType(String.valueOf(SysStaticDataEnum.USER_TYPE.BUSINESS));
		loginUserIn.setLoginType(String.valueOf(SysStaticDataEnum.LOGIN_TYPE.WEB)+","+String.valueOf(SysStaticDataEnum.LOGIN_TYPE.WEB_AND_APP));
		return doLogin(loginUserIn);
	}
	
	/**
	 * 专线登录方法
	 * @return
	 * @throws Exception
	 */
	public String doLoginChain() throws Exception {
		
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String loginAcct = DataFormat.getStringKey(map, "username").trim();
		String passWord = DataFormat.getStringKey(map, "password");
		Long tenantId = DataFormat.getLongKey(map, "tenantId");
		
		LoginUserIn loginUserIn=new LoginUserIn();
		loginUserIn.setLoginAcct(loginAcct);
		loginUserIn.setPassWord(passWord);
		loginUserIn.setTenantId(tenantId);
		loginUserIn.setUserType(String.valueOf(SysStaticDataEnum.USER_TYPE.CHAIN));
		loginUserIn.setLoginType(String.valueOf(SysStaticDataEnum.LOGIN_TYPE.WEB)+","+String.valueOf(SysStaticDataEnum.LOGIN_TYPE.WEB_AND_APP));
		return doLogin(loginUserIn);
	}
	
	/**
	 * 平台登录方法
	 * @return
	 * @throws Exception
	 */
	public String doLoginPlat() throws Exception {
		
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String loginAcct = DataFormat.getStringKey(map, "username").trim();
		String passWord = DataFormat.getStringKey(map, "password");
		Long tenantId = DataFormat.getLongKey(map, "tenantId");
		
		LoginUserIn loginUserIn=new LoginUserIn();
		loginUserIn.setLoginAcct(loginAcct);
		loginUserIn.setPassWord(passWord);
		loginUserIn.setTenantId(tenantId);
		loginUserIn.setUserType(String.valueOf(SysStaticDataEnum.USER_TYPE.PLATFORM));
		loginUserIn.setLoginType(String.valueOf(SysStaticDataEnum.LOGIN_TYPE.WEB)+","+String.valueOf(SysStaticDataEnum.LOGIN_TYPE.WEB_AND_APP));
		return doLogin(loginUserIn);
	}
	
	/**
	 * 用户登陆。
	 * 
	 * @return 
	 * 		  0:表示校验成功
	 *        1:表示校验失败
	 * @throws Exception
	 */
	private String doLogin(LoginUserIn loginUserIn ) throws Exception {
		
		SimpleOutParamVO<LoginUserOut> simpleOutParamVO = CallerProxy.call(loginUserIn,new TypeToken<SimpleOutParamVO<LoginUserOut>>(){}.getType());
		LoginUserOut loginUserOut=simpleOutParamVO.getContent();
		String result=loginUserOut.getResult();
		//登录成功
		if(StringUtils.isNotBlank(result) && EnumConsts.Result.SUCCESS.equals(result)){
			//设置用户信息
			BaseUser user=new BaseUser(); 
			
			user.setOperId(loginUserOut.getUserId());
			user.setOrgId(loginUserOut.getOrgId());
			user.setUserId(loginUserOut.getUserId());
			user.setUserName(loginUserOut.getUserName());
			user.setUserType(loginUserOut.getUserType());
			if(loginUserOut.getTenantId()==null){
				user.setTenantId(-1L);
			}else{
				user.setTenantId(loginUserOut.getTenantId());
			}
			
			Map<String, Object> attrs=new HashMap<String, Object>();
			attrs.put(LoginConst.BaseUserAttr.LOGIN_ACCOUNT, loginUserOut.getLoginAcct());
			attrs.put(LoginConst.BaseUserAttr.TENANT_CODE, loginUserOut.getTenantCode());
			attrs.put(LoginConst.BaseUserAttr.ORG_CODE, loginUserOut.getOrgCode());
			attrs.put(LoginConst.BaseUserAttr.TENANT_NAME, loginUserOut.getTenantName());
			 

			List<Integer> roles=new ArrayList<Integer>();
			for(Integer roleId:loginUserOut.getRoles()){
				roles.add(roleId);
			}
			HttpSession sess = SysContexts.getHttpSession(true);
			if (sess != null) {
				attrs.put(EnumConsts.Common.SESSION_ROLES, roles);
			}
			user.setAttrs(attrs);
			/**  联运汇有操作区域的逻辑，云企目前没有
			///写入操作区域
			QueryUserAreaIn areaIn = new QueryUserAreaIn();
			areaIn.setUserId(loginUserOut.getUserId());
			ListOutParamVO<HashMap> out = CallerProxy.call(areaIn, new TypeToken<ListOutParamVO<HashMap>>(){}.getType());
			attrs.put(LoginConst.BaseUserAttr.OPERATOR_OP_AREA, out.getContent());
			**/
			
			String logo=SysTenantExtendCacheUtil.getValue(loginUserOut.getTenantId(), SysTenantExtendCacheUtil.LOGO_URL);
			
			CookieUtils.addCookie(SysContexts.getResponse(), "logo",logo==null ? "" : logo);
			
			
			
			//从session里面拿组织id和顶级组织id
			SysContexts.setCurrentOperator(user);
			String orgName=OraganizationCacheDataUtil.getOrgName(loginUserOut.getOrgId());
			String tenantName=CommonUtil.getTennatNameById(loginUserOut.getTenantId());
			
//			loginUserOut.setOrgName(orgName);
			CookieUtils.addCookie(SysContexts.getResponse(), "userName",URLEncoder.encode(loginUserOut.getUserName(),"utf-8"));
			CookieUtils.addCookie(SysContexts.getResponse(), "tenantName", URLEncoder.encode(tenantName,"utf-8"));
			CookieUtils.addCookie(SysContexts.getResponse(), "orgName", URLEncoder.encode(orgName,"utf-8"));
			CookieUtils.addCookie(SysContexts.getResponse(), "orgId",String.valueOf(loginUserOut.getOrgId()));
			CookieUtils.addCookie(SysContexts.getResponse(), "userType",String.valueOf(loginUserOut.getUserType()));
			Organization organization=  OraganizationCacheDataUtil.getOrganization(loginUserOut.getOrgId());
			if(organization!=null&&StringUtils.isNotEmpty(organization.getRegionId())){
				City city= SysStaticDataUtil.getCityDataList("SYS_CITY",organization.getRegionId());
				if(city!=null && city.getName()!=null){
					CookieUtils.addCookie(SysContexts.getResponse(), "cityName",URLEncoder.encode(city.getName(),"utf-8"));
				}
			}
			CookieUtils.addCookie(SysContexts.getResponse(), "userId",String.valueOf(loginUserOut.getUserId()));
//			
		}
		return JsonHelper.json(loginUserOut);
	}
	/**
	 * 退出登录
	 * @return
	 *       0 表示成功
	 * @throws Exception
	 */
	public String doLogout() throws Exception {
		BaseUser baseUser = SysContexts.getCurrentOperator();
		if(baseUser != null && baseUser.getUserId()!=null){
			SysContexts.setCurrentOperator(null);
		}
		return EnumConsts.Result.SUCCESS;
	}
	
	
	/**
	 * 根据登录用户获取用户可以操作的菜单树
	 * 
	 * 
	 * menuType 根据传入的菜单类型 
	 * userId   如果该值不为空时，查询该用户对应的角色，获取该角色对应的实体，并且在叶子节点获取对应的url操作
	 * @return
	 * @throws Exception
	 */
	public String getTopMenu() throws Exception{
		Map inParam = (Map)SysContexts.getRequestParameterMap();
		BaseUser baseUser= SysContexts.getCurrentOperator();
		
		if(baseUser==null){
			throw new AuthException("登录超时！");
		}
		List<Integer> roles =(List<Integer>)baseUser.getAttrs().get(EnumConsts.Common.SESSION_ROLES);
		
		Set<Integer> entityIdSet=new HashSet<Integer>();
		if(roles==null || roles.size()<1){
			return null;
		}
		for(Integer role:roles){
			List<SysRoleGrant> roleGrants=(List<SysRoleGrant>)CacheFactory.get(SysRoleGrantCache.class, PermissionConsts.GrantConstant.SYS_ROLE_GRANT_CACHE_KEY+role);
			if(roleGrants!=null && roleGrants.size()>0){
				for(SysRoleGrant grant:roleGrants){
						entityIdSet.add(grant.getEntityId());
				}
			}
		}
		
		Map<Integer,String> entityMap=new HashMap<Integer, String>();
		Iterator<Integer> iterator=entityIdSet.iterator();
		while(iterator.hasNext()){
			entityMap.put(iterator.next(), "1");
		}
		
		//TODO 如果以后需要根据菜单类型进行
		List<SysMenu> menuCache=(List<SysMenu>)CacheFactory.get(SysMenuCache.class.getName(), PermissionConsts.GrantConstant.SYS_MENU+baseUser.getUserType().toString());
		
		SysMenu menu=getSysMenuTree(menuCache, 1);
		menu.setEntityId(1);
		
		filtSysMenuTreeByEntity(menu, entityMap);
		Gson gson = new Gson();
		
		return gson.toJson(menu);
	}
	
	/**
	 * 查询传入的用户id选中的菜单
	 * 
	 * 
	 * menuType 根据传入的菜单类型 
	 * userId   如果该值不为空时，查询该用户对应的角色，获取该角色对应的实体，并且在叶子节点获取对应的url操作
	 * @return
	 * @throws Exception
	 */
	public String getSelectMenu() throws Exception{
		Map inParam = (Map)SysContexts.getRequestParameterMap();
		String menuType = DataFormat.getStringKey(inParam, "menuType");
		BaseUser baseUser= SysContexts.getCurrentOperator();
		if(StringUtils.isEmpty(menuType)){
			//如果传入的菜单类型为空，取当前用户登录的用户类型
			menuType=baseUser.getUserType().toString();
		}
		
		int roleId=DataFormat.getIntKey(inParam, "roleId");
		
		if(baseUser==null){
			throw new AuthException("登录超时！");
		}
		List<Integer> roles =(List<Integer>)baseUser.getAttrs().get(EnumConsts.Common.SESSION_ROLES);
		Map<Integer,String> entityMap=getEntityByRoles(roles);
		List<Integer> userRole=new ArrayList<Integer>();
		if(roleId>0){
			userRole.add(roleId);
		}
		
		Map<Integer,String> entityUserMap=getEntityByRoles(userRole);
		
		List<SysMenu> menuCache=(List<SysMenu>)CacheFactory.get(SysMenuCache.class.getName(), PermissionConsts.GrantConstant.SYS_MENU+menuType);
		SysMenu menu=getSysMenuAndUrlTree(menuCache, 1);
		menu.setEntityId(1);
		
		filtSysMenuTreeByEntity(menu, entityMap,entityUserMap);
		Gson gson = new Gson();
		
		return gson.toJson(menu);
	}
	
	/**
	 * 根据角色获取该角色对于的实体
	 * 
	 * @param roles
	 * @return
	 */
	private Map<Integer,String> getEntityByRoles(List<Integer> roles){
		Set<Integer> entityIdSet=new HashSet<Integer>();
		if(roles==null || roles.size()<1){
			return null;
		}
		for(Integer role:roles){
			List<SysRoleGrant> roleGrants=(List<SysRoleGrant>)CacheFactory.get(SysRoleGrantCache.class, PermissionConsts.GrantConstant.SYS_ROLE_GRANT_CACHE_KEY+role.intValue());
			if(roleGrants!=null && roleGrants.size()>0){
				for(SysRoleGrant grant:roleGrants){
					entityIdSet.add(grant.getEntityId());
				}
			}
		}
		
		Map<Integer,String> entityMap=new HashMap<Integer, String>();
		Iterator<Integer> iterator=entityIdSet.iterator();
		while(iterator.hasNext()){
			entityMap.put(iterator.next(), "1");
		}
		return entityMap;
	}
	
//	/**
//	 * 查询传入的用户的角色
//	 * @param userId
//	 * @return
//	 * @throws Exception
//	 */
//	private List<Integer> getRoleByUserId(long userId) throws Exception{
//		SysRoleIn roleIn=new SysRoleIn(InterFacesCodeConsts.COMMON.QUERY_SYS_ROLE);
//		roleIn.setUserId(userId);
//		ListOutParamVO<SysRoleOperRel> listOutParamVO = CallerProxy.call(roleIn, new TypeToken<ListOutParamVO<SysRoleOperRel>>(){}.getType());
//		List<SysRoleOperRel> list=listOutParamVO.getContent();
//		List<Integer> rolesList=new ArrayList<Integer>();
//		if(list!=null){
//			for(SysRoleOperRel operRel:list){
//				rolesList.add(operRel.getRoleId());
//			}
//		}
//		return rolesList;
//	}
	
	/**
	 * 通过菜单id获取该菜单的子菜单数据
	 * 
	 * @param sysMenus
	 * @param parentId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private SysMenu getSysMenuAndUrlTree(List<SysMenu> sysMenus,int parentId) throws IllegalAccessException, InvocationTargetException{
		SysMenu menu=new SysMenu();
		List<SysMenu> menus=new ArrayList<SysMenu>();
		if(sysMenus!=null && sysMenus.size()>0){
			for(SysMenu sysMenu:sysMenus){
				if(sysMenu.getParentId()==parentId){
					SysMenu childMenu=getSysMenuAndUrlTree(sysMenus, sysMenu.getMenuId());
					List<SysMenu> menuList=childMenu.getChildMenus();
					BeanUtils.copyProperties(childMenu, sysMenu);
					childMenu.setChildMenus(menuList);
					menus.add(childMenu);
					
				}
			}
		}
		if(menus.size()==0){
			menu.setLeaf(true);
			//加载菜单下面的url
			menus=changeUrlToMenu(parentId);
		}
		menu.setChildMenus(menus);
		return menu;
	}
	/**
	 * 获取用户的菜单
	 * @param sysMenus
	 * @param parentId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private SysMenu getSysMenuTree(List<SysMenu> sysMenus,int parentId) throws IllegalAccessException, InvocationTargetException{
		SysMenu menu=new SysMenu();
		List<SysMenu> menus=new ArrayList<SysMenu>();
		if(sysMenus!=null && sysMenus.size()>0){
			for(SysMenu sysMenu:sysMenus){
				if(sysMenu.getParentId()==parentId ){
					SysMenu childMenu=getSysMenuTree(sysMenus, sysMenu.getMenuId());
					List<SysMenu> menuList=childMenu.getChildMenus();
					BeanUtils.copyProperties(childMenu, sysMenu);
					childMenu.setChildMenus(menuList);
					menus.add(childMenu);
					
				}
			}
		}
		
		
		menu.setChildMenus(menus);
		return menu;
	}
	
	/**
	 * 根据菜单查询是否该菜单下面有挂着对应的url请求
	 * 
	 * @param menuId
	 * @return
	 */
	private List<SysMenu> changeUrlToMenu(long menuId){
		List<SysMenu> list=new ArrayList<SysMenu>();
		List<SysUrl> urls= getUrlByGroupId(menuId);
		if(urls==null || urls.size()==0){
			return list;
		}
		for(SysUrl sysUrl:urls){
			SysMenu menu=new SysMenu();
			menu.setParentId(Integer.valueOf(String.valueOf(menuId)));
			menu.setEntityId(sysUrl.getEntityId());
			menu.setMenuName(sysUrl.getUrlName());
			menu.setMenuType("4");//用于判断是url转换的菜单，不会写到表里面的
			list.add(menu);
		}
		return list;
	}
	
	
	/**
	 * 根据菜单id 获取菜单对应的url
	 * @param menuId
	 * @return
	 */
	private List<SysUrl> getUrlByGroupId(long menuId){
		List<SysUrl> sysUrls=(List<SysUrl>)CacheFactory.get(SysUrlDataCache.class.getName(), PermissionConsts.GrantConstant.SYS_URL);
		if(sysUrls!=null && sysUrls.size()>0){
			List<SysUrl> urls=new ArrayList<SysUrl>();
			for(SysUrl sysUrl:sysUrls){
				if(sysUrl.getGroupId().longValue()==menuId){
					urls.add(sysUrl);
				}
			}
			return urls;
		}
		return null;
	}
	
	/**
	 *  过滤菜单树的权限
	 * @param sysMenu  全部的菜单
	 * @param entityMap 登录用户拥有的菜单实体权限
	 * @return
	 */
	private boolean filtSysMenuTreeByEntity(SysMenu sysMenu,Map<Integer, String> entityMap){
		
		if(entityMap.get(Integer.valueOf(""+sysMenu.getEntityId()))==null && sysMenu.getEntityId()!=1L){
			return false;
		}else{
			List<SysMenu> menus= sysMenu.getChildMenus();
			List<SysMenu> sysMenus=new ArrayList<SysMenu>();
			
			for(SysMenu menu :menus){
				if(entityMap.get(Integer.valueOf(""+sysMenu.getEntityId()))!=null  || sysMenu.getEntityId()==1L){
					if(filtSysMenuTreeByEntity(menu, entityMap)){
						sysMenus.add(menu);
					}
				}
			}
			sysMenu.setChildMenus(sysMenus);
		}
		return true;
	}
	
	/**
	 * lyh
	 * 
	 *  过滤菜单树的权限
	 * @param sysMenu  全部的菜单
	 * @param entityMap 登录用户拥有的菜单实体权限
	 * @param userEntityMap 对于的用户的菜单权限实体
	 * @return
	 */
	private boolean filtSysMenuTreeByEntity(SysMenu sysMenu,Map<Integer, String> entityMap,Map<Integer, String> userEntityMap){
		
		//1 是首页的实体,每个人都有权限
		if(entityMap.get(Integer.valueOf(""+sysMenu.getEntityId()))==null && sysMenu.getEntityId()!=1L){
			return false;
		}else{
			List<SysMenu> menus= sysMenu.getChildMenus();
			List<SysMenu> sysMenus=new ArrayList<SysMenu>();
			if(menus!=null && menus.size()>0){
				for(SysMenu menu :menus){
					if(entityMap.get(Integer.valueOf(""+sysMenu.getEntityId()))!=null || sysMenu.getEntityId()==1L){
						
						if(filtSysMenuTreeByEntity(menu, entityMap,userEntityMap)){
							sysMenus.add(menu);
						}
					}
				}
			}
				
				if(userEntityMap!=null && userEntityMap.get(Integer.valueOf(""+sysMenu.getEntityId()))!=null){
					sysMenu.set$$isChecked(true);
				}else{
					sysMenu.set$$isChecked(false);
				}
				sysMenu.setChildMenus(sysMenus);
			
		}
		return true;
	}
	/**
	 * 查询师傅合作商租户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryCompanyInfoMster() throws Exception{
		Map inParam = (Map)SysContexts.getRequestParameterMap();
		String tenantCode = DataFormat.getStringKey(inParam, "tenantCode");
		if(StringUtils.isEmpty(tenantCode)){
			throw new BusinessException("未传入公司编码");
		}
		CmCompanyIn cmCompanyIn=new CmCompanyIn(InterFacesCodeConsts.COMMON.LOGIN_QUERY_TENANT);
		
		cmCompanyIn.setTenantCode(tenantCode);
		cmCompanyIn.setTenantType(String.valueOf(SysStaticDataEnum.USER_TYPE.MASTER_PARTNERS ));

		SimpleOutParamVO<Map> companyInfo = CallerProxy.call(cmCompanyIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(companyInfo.getContent());
	}
	
	/**
	 * 查询拉包公司信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryCompanyInfoPullPagCompany() throws Exception{
		Map inParam = (Map)SysContexts.getRequestParameterMap();
		String tenantCode = DataFormat.getStringKey(inParam, "tenantCode");
		if(StringUtils.isEmpty(tenantCode)){
			throw new BusinessException("未传入公司编码");
		}
		CmCompanyIn cmCompanyIn=new CmCompanyIn(InterFacesCodeConsts.COMMON.LOGIN_QUERY_TENANT);
		
		cmCompanyIn.setTenantCode(tenantCode);
		cmCompanyIn.setTenantType(String.valueOf(SysStaticDataEnum.USER_TYPE.PULL_PAG_COMPANY ));

		SimpleOutParamVO<Map> companyInfo = CallerProxy.call(cmCompanyIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(companyInfo.getContent());
	}
	
	/**
	 * 查询商家租户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryCompanyInfoBusi() throws Exception{
		Map inParam = (Map)SysContexts.getRequestParameterMap();
		String tenantCode = DataFormat.getStringKey(inParam, "tenantCode");
		if(StringUtils.isEmpty(tenantCode)){
			throw new BusinessException("未传入公司编码");
		}
		CmCompanyIn cmCompanyIn=new CmCompanyIn(InterFacesCodeConsts.COMMON.LOGIN_QUERY_TENANT);
		
		cmCompanyIn.setTenantCode(tenantCode);
		cmCompanyIn.setTenantType(String.valueOf(SysStaticDataEnum.USER_TYPE.BUSINESS ));

		SimpleOutParamVO<Map> companyInfo = CallerProxy.call(cmCompanyIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(companyInfo.getContent());
	}
	
	/**
	 * 查询平台租户的信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryCompanyInfoPlat() throws Exception{
		Map inParam = (Map)SysContexts.getRequestParameterMap();
		String tenantCode = DataFormat.getStringKey(inParam, "tenantCode");
		if(StringUtils.isEmpty(tenantCode)){
			throw new BusinessException("未传入公司编码");
		}
		CmCompanyIn cmCompanyIn=new CmCompanyIn(InterFacesCodeConsts.COMMON.LOGIN_QUERY_TENANT);
		
		cmCompanyIn.setTenantCode(tenantCode);
		cmCompanyIn.setTenantType(String.valueOf(SysStaticDataEnum.USER_TYPE.PLATFORM ));
		SimpleOutParamVO<Map> companyInfo = CallerProxy.call(cmCompanyIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(companyInfo.getContent());
	}
	
	/**
	 * 查询专线租户的信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryCompanyInfoChain() throws Exception{
		Map inParam = (Map)SysContexts.getRequestParameterMap();
		String tenantCode = DataFormat.getStringKey(inParam, "tenantCode");
		if(StringUtils.isEmpty(tenantCode)){
			throw new BusinessException("未传入公司编码");
		}
		CmCompanyIn cmCompanyIn=new CmCompanyIn(InterFacesCodeConsts.COMMON.LOGIN_QUERY_TENANT);
		
		cmCompanyIn.setTenantCode(tenantCode);
		cmCompanyIn.setTenantType(String.valueOf(SysStaticDataEnum.USER_TYPE.CHAIN ));

		SimpleOutParamVO<Map> companyInfo = CallerProxy.call(cmCompanyIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(companyInfo.getContent());
	}

	/**
	 * 获取自定义table数据
	 * @return
	 * @throws Exception
	 */
	public String getTableDiy()throws Exception{
		CmCompanyIn cmCompanyIn=new CmCompanyIn(InterFacesCodeConsts.COMMON.TABLE_DIY);
		SimpleOutParamVO<Map> companyInfo = CallerProxy.call(cmCompanyIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(companyInfo.getContent());
	}
	/**
	 * 判断传入的实体，该用户是否有权限
	 * 
	 * @return
	 * @throws Exception
	 */
	public String hasAuth() throws Exception{
		Map inParam = (Map)SysContexts.getRequestParameterMap();
		Long entityId = DataFormat.getLongKey(inParam, "entityId");
		
		BaseUser baseUser= SysContexts.getCurrentOperator();
		
		if(baseUser==null){
			throw new AuthException("登录超时！");
		}
		List<Integer> roles =(List<Integer>)baseUser.getAttrs().get(EnumConsts.Common.SESSION_ROLES);
		
		Set<Integer> entityIdSet=new HashSet<Integer>();
		if(roles==null || roles.size()<1){
			return null;
		}
		String isHasAuth="0";
		
		if(entityId==null || entityId<0){
			return isHasAuth;
		}
		
		for(Integer role:roles){
			List<SysRoleGrant> roleGrants=(List<SysRoleGrant>)CacheFactory.get(SysRoleGrantCache.class, PermissionConsts.GrantConstant.SYS_ROLE_GRANT_CACHE_KEY+role);
			if(roleGrants!=null && roleGrants.size()>0){
				for(SysRoleGrant grant:roleGrants){
						if(grant.getEntityId()==entityId.intValue()){
							isHasAuth="1";
							break;
						}
				}
			}
		}
		
		return isHasAuth;
		
	}
	/**
	 * 
	 * 切换账号，重新登录（不用密码登录）
	 * @param inParam
	 * @return
	 */
	public String LoginUserOut() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		LoginRelUserIn query = new LoginRelUserIn();
		BeanUtils.populate(query, map);
		SimpleOutParamVO<LoginUserOut> simpleOutParamVO = CallerProxy.call(query,new TypeToken<SimpleOutParamVO<LoginUserOut>>(){}.getType());
		LoginUserOut loginUserOut=simpleOutParamVO.getContent();
		String result=loginUserOut.getResult();
		//登录成功
		if(StringUtils.isNotBlank(result) && EnumConsts.Result.SUCCESS.equals(result)){
			//设置用户信息
			BaseUser user=new BaseUser(); 
			user.setOperId(loginUserOut.getUserId());
			user.setOrgId(loginUserOut.getOrgId());
			user.setUserId(loginUserOut.getUserId());
			user.setUserName(loginUserOut.getUserName());
			user.setUserType(loginUserOut.getUserType());
			user.setTenantId(loginUserOut.getTenantId());
			Map<String, Object> attrs=new HashMap<String, Object>();
			attrs.put(EnumConsts.Common.LOGIN_ACCT, loginUserOut.getLoginAcct());
			
			List<Integer> roles=new ArrayList<Integer>();
			for(Integer roleId:loginUserOut.getRoles()){
				roles.add(roleId);
			}
			HttpSession sess = SysContexts.getHttpSession(true);
			if (sess != null) {
				attrs.put(EnumConsts.Common.SESSION_ROLES, roles);
				
			}
			user.setAttrs(attrs);
			//从session里面拿组织id和顶级组织id
			SysContexts.setCurrentOperator(user);
			String orgName=OraganizationCacheDataUtil.getOrgName(loginUserOut.getOrgId());
			loginUserOut.setOrgName(orgName);
			CookieUtils.addCookie(SysContexts.getResponse(), "userName",URLEncoder.encode(loginUserOut.getUserName(),"utf-8"));
			CookieUtils.addCookie(SysContexts.getResponse(), "orgName", URLEncoder.encode(orgName,"utf-8"));
			CookieUtils.addCookie(SysContexts.getResponse(), "orgId",String.valueOf(loginUserOut.getOrgId()));
			CookieUtils.addCookie(SysContexts.getResponse(), "cityName",URLEncoder.encode(SysStaticDataUtil.getCityDataList("SYS_CITY",OraganizationCacheDataUtil.getOrganization(loginUserOut.getOrgId()).getRegionId()).getName(),"utf-8"));
			CookieUtils.addCookie(SysContexts.getResponse(), "userId",String.valueOf(loginUserOut.getUserId()));
			
		}
		return JsonHelper.json(loginUserOut);
	}
	
}
