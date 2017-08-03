package com.wo56.business.sys.intf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.interceptor.vo.SysUrl;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.wo56.business.sys.impl.SysRoleSV;
import com.wo56.business.sys.vo.SysMenu;
import com.wo56.business.sys.vo.SysRole;
import com.wo56.business.sys.vo.SysRoleGrant;
import com.wo56.business.sys.vo.SysRoleOperRel;
import com.wo56.common.consts.SysStaticDataEnum;

public class SysRoleTF {
	
	/**
	 * 查询角色信息
	 * @param inParam
	 * @return
	 */
	public Map doQuerySysRole(Map<String, String> inParam){
			Session session = SysContexts.getEntityManager(true);
			String roleName = DataFormat.getStringKey(inParam, "roleName");
			int orgId = DataFormat.getIntKey(inParam, "orgId");
			SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
			Pagination page = sysRoleSV.doQuerySysRole(roleName, orgId);
			return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	}
	/**
	 * lyh
	 * 保存角色信息
	 * @param inParam
	 * @return
	 */
	public Map doSaveSysRole(Map<String, String> inParam) throws Exception{
		SysRole sysRole = new SysRole();
        Session session = SysContexts.getEntityManager();
		int roleId = DataFormat.getIntKey(inParam, "roleId");
		String roleName = DataFormat.getStringKey(inParam, "roleName");
		long tenantId = DataFormat.getLongKey(inParam, "tenantId");
		int roleType = DataFormat.getIntKey(inParam, "roleType");
		int orgId = DataFormat.getIntKey(inParam, "orgId");
		int menuType = DataFormat.getIntKey(inParam, "menuType");
		
		String entityId = DataFormat.getStringKey(inParam, "entityId");
		
		if(StringUtils.isEmpty(roleName)){
			throw new BusinessException("角色名称不能为空");
		}
		BaseUser baseUser = SysContexts.getCurrentOperator();
		if(roleId>0){
			sysRole = (SysRole)session.get(SysRole.class, roleId);
		}else{
			
			sysRole.setTenantId(tenantId);
			
			sysRole.setTenantId(tenantId);
			sysRole.setRoleType(roleType);
			sysRole.setCreateDate(new Date(System.currentTimeMillis()));
			sysRole.setState(SysStaticDataEnum.STS.VALID);
			sysRole.setOrgId(baseUser.getOrgId().intValue());
		}
		sysRole.setLastModifyDate(new Date(System.currentTimeMillis()));
		sysRole.setLastModifyOperatorId(baseUser.getOperId());
		sysRole.setOpId(baseUser.getUserId());
		sysRole.setRoleName(roleName);
		sysRole.setMenuType(menuType);
		
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		sysRoleSV.doSaveSysRole(sysRole);
		
		if(roleId>0){
			//修改
			
			List<SysRoleGrant> sysRoleGrants= sysRoleSV.queryUrlByRoleId(roleId);
			Map<String, Map<String, String>> map=filtRoleGrant(sysRoleGrants, entityId);
			
			Map<String,String> addMap=map.get("add");
			if(addMap.keySet().size()>0){
				for(String entity:addMap.keySet()){
					saveSysRoleGrant(roleId, Integer.valueOf(entity));
				}
			}
			Map<String,String> delMap=map.get("del");
			
			if(delMap!=null && delMap.keySet().size()>0){
				List<String> entityIds=new ArrayList<String>();
				for(String entity:delMap.keySet()){
					entityIds.add(entity);
				}
				if(entityIds.size()>0){
					//删除租户下面失效的菜单，功能	
					sysRoleSV.delRoleGrantByTenantId(entityIds, orgId);
					//删除角色对应失效的菜单，功能
					sysRoleSV.delRoleGrantByRoleId(entityIds, roleId);
				}
				
			}
		}else{
			//新增
			roleId=sysRole.getRoleId();
			if(StringUtils.isNotEmpty(entityId)){
				String[] entityIds=entityId.split(",");
				for(String entity :entityIds){
					saveSysRoleGrant(roleId, Integer.valueOf(entity));
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 如果修改角色的菜单，需要判断哪些是需要新增，哪些需要删除
	 * @param sysRoleGrants
	 * @param entityId
	 * @return
	 */
	private Map<String,Map<String,String>> filtRoleGrant(List<SysRoleGrant> sysRoleGrants,String entityId){
		Map<String,String> delMap=new HashMap<String, String>();
		Map<String,String> addMap=new HashMap<String, String>();
		if(sysRoleGrants!=null && sysRoleGrants.size()>0){
			for(SysRoleGrant grant:sysRoleGrants ){
				delMap.put(String.valueOf(grant.getEntityId()), String.valueOf(grant.getEntityId()));
			}
		}
		
		if(StringUtils.isNotEmpty(entityId)){
			String[] entityIds=entityId.split(",");
			for(String entity:entityIds){
				if(delMap.get(entity)!=null){
					delMap.remove(entity);
				}else{
					addMap.put(entity, entity);
				}
				
			}
		}
		
		Map<String,Map<String,String>> retMap=new HashMap<String, Map<String,String>>();
		
		retMap.put("del", delMap);
		retMap.put("add", addMap);
		return retMap;
	}
	
	
	/**
	 * lyh
	 * 保存用户的授权
	 * 
	 * @param roleId
	 * @param entityId
	 */
	private void saveSysRoleGrant(int roleId,int entityId){
		BaseUser baseUser = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager();
		SysRoleGrant grant = new SysRoleGrant();
		grant.setEntityId(entityId);
		grant.setRoleId(roleId);
		grant.setState(SysStaticDataEnum.STS.VALID);
		grant.setLastModifyDate(new Date(System.currentTimeMillis()));
		grant.setLastModifyOperatorId(baseUser.getUserId());
		grant.setCreateDate(new Date(System.currentTimeMillis()));
		session.save(grant);
	}
	
	/**
	 * 用户添加角色
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map saveUserRole(Map<String, Object> inParam) throws Exception{
		Map paramMap = new HashMap();
		List roleIds = (List)inParam.get("roleIds");
		Long userId = Long.valueOf(inParam.get("userId").toString());
		if(roleIds==null || roleIds.size()<=0){
			throw new BusinessException("请选择角色");
		}
		if(userId==null || userId<=0){
			throw new BusinessException("请选择用户");	
		}
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		sysRoleSV.saveUserRole(roleIds,userId);
		paramMap.put("info", "Y");
		return paramMap;
	}
	/**
	 * lhy
	 * 根据用户id查询该用户关联的角色 
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List<SysRoleOperRel> queryUserRole(Map<String, Object> inParam) throws Exception{
		Long userId = Long.valueOf(inParam.get("userId").toString());
		if(userId==null || userId<=0){
			throw new BusinessException("请选择用户");	
		}
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		List<SysRoleOperRel> roleList = sysRoleSV.queryUserRole(userId);
		return roleList;
	}
	/**
	 * 查询菜单
	 * @param inParam
	 * @return
	 */
	public Pagination querySysMenu(Map<String, String> inParam){
		//DataFormat.getStringKey(inParam, "menuName");
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		Pagination page  = sysRoleSV.querySysMenu(inParam);
		return page;
	}
	
	/**
	 * 查询URl
	 * @param inParam
	 * @return
	 */
	public Pagination querySysUrl(Map<String, String> inParam){
		//DataFormat.getStringKey(inParam, "urlName");
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		String urlName = DataFormat.getStringKey(inParam, "urlName");
		Map ipMap = new HashMap();
		ipMap.put("urlName", urlName);
		Pagination page  =  sysRoleSV.querySysUrl(ipMap);
		return page;
	}
	
	
	
	/**
	 * 保存Url信息
	 * @param inParam
	 * @return
	 */
	public Map doSaveSysUrl(Map<String, String> inParam) throws Exception{
		SysUrl sysUrl = new SysUrl();
		Long id = DataFormat.getLongKey(inParam,"id");
		
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		if(id!=null && id>0){
			Map map = querySysUrlDtl(inParam);
			sysUrl=(SysUrl)map.get("sysUrl");
		}
			BeanUtils.populate(sysUrl, inParam);
		if(StringUtils.isEmpty(sysUrl.getUrlName())){
			throw new BusinessException("系统Url名称不能为空");
		}
		if(StringUtils.isEmpty(sysUrl.getUrlPath())){
			throw new BusinessException("系统Url路径不能为空");	
		}
		sysRoleSV.doSaveSysUrl(sysUrl);
		//acFeeConfigSV.doSaveFeeConfig(acFeeConfig);
		Map map = new HashMap();
		map.put("info", "Y");
		return map;
	}
	
	/**
	 * 保存系统菜单信息
	 * @param inParam
	 * @return
	 */
	public Map doSaveSysMenu(Map<String, String> inParam) throws Exception{
		SysMenu sysMenu = new SysMenu();
		
		Long menuId = DataFormat.getLongKey(inParam,"menuId");
		if(menuId!=null && menuId>0){
			Map map = querySysMenuDtl(inParam);
			sysMenu=(SysMenu)map.get("sysMenu");
		}
		BeanUtils.populate(sysMenu, inParam);
		if(StringUtils.isEmpty(sysMenu.getMenuName())){
			throw new BusinessException("菜单名称不能为空");
		}
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		sysRoleSV.doSaveSysMenu(sysMenu);
		//acFeeConfigSV.doSaveFeeConfig(acFeeConfig);
		Map map = new HashMap();
		map.put("info", "Y");
		return map;
	}
	/**
	 * 根据菜单名称查询菜单信息
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List querySysMenuByMenuName(Map<String, String> inParam) throws Exception{
		String menuName=DataFormat.getStringKey(inParam, "menuName");
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		List<SysMenu> iparmList  = sysRoleSV.searchSysMenu(menuName);
		return iparmList;
	}
	/**
	 * 查询角色已添加的URL
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List queryUrlByRoleId(Map<String, Object> inParam) throws Exception{
		Map paramMap = new HashMap();
		Integer roleId = Integer.valueOf(inParam.get("roleId").toString());
		if(roleId==null || roleId<=0){
			throw new BusinessException("请选择角色");	
		}
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		List roleList = sysRoleSV.queryUrlByRoleId(roleId);
		
		return roleList;
	}
	
	
	
	/**
	 * 角色添加Url
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map saveRoleUrl(Map<String, Object> inParam) throws Exception{
		Map paramMap = new HashMap();
		List urlIds = (List)inParam.get("urlIds");
		Integer roleId = Integer.valueOf(inParam.get("roleId").toString());
		if(urlIds==null || urlIds.size()<=0){
			throw new BusinessException("请选择角色");
		}
		if(roleId==null || roleId<=0){
			throw new BusinessException("请选择用户");	
		}
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		sysRoleSV.saveRoleUrl(urlIds,roleId);
		paramMap.put("info", "Y");
		return paramMap;
	}
	
	
	/**
	 * 查询URL详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map querySysUrlDtl(Map<String, String> inParam) throws Exception{
		Long id=DataFormat.getLongKey(inParam, "id");
		Session session = SysContexts.getEntityManager(true);
		SysUrl sysUrl = (SysUrl)session.get(SysUrl.class, id);
		if(sysUrl==null){
			throw new BusinessException("查询不到该URL的信息");	
		}
		Map iparmMap = new HashMap();
		//EncryPwd.pwdEncryption(K.k_s(objAry[11]+""))
		//cmUser.setLoginPwd(EncryPwdUtil.pwdEncryption(K.k_s(cmUser.getLoginPwd())));
		iparmMap.put("sysUrl", sysUrl);
		return iparmMap;
		
	}
	
	
	
	/**
	 * 查询菜单详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map querySysMenuDtl(Map<String, String> inParam) throws Exception{
		int menuId=DataFormat.getIntKey(inParam, "menuId");
		Session session = SysContexts.getEntityManager(true);
		SysMenu sysMenu = (SysMenu)session.get(SysMenu.class, menuId);
		if(sysMenu==null){
			throw new BusinessException("查询不到该菜单的信息");	
		}
		Map iparmMap = new HashMap();
		iparmMap.put("sysMenu", sysMenu);
		return iparmMap;
		
	}
	/**
	 * 删除Url
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doDelUrl(Map<String, Object> inParam) throws Exception{
		List urlIds = (List)inParam.get("urlIds");
		Map paramMap = new HashMap();
	     Session session  = SysContexts.getEntityManager();
		if(urlIds.size()<=0){
			throw new BusinessException("请传入ID");	
		}
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		String data  = sysRoleSV.delUrlAndEntity(urlIds);
		paramMap.put("info", "Y");
		return paramMap;
	}
	/**
	 * 删除菜单
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doDelMenu(Map<String, Object> inParam) throws Exception{
		Map paramMap = new HashMap();
		List menuIds = (List)inParam.get("menuIds");
		 Session session  = SysContexts.getEntityManager();
		if(menuIds.size()<=0){
			throw new BusinessException("请传入menuId");	
		}
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		sysRoleSV.delEntityAndMenu(menuIds);
		paramMap.put("info", "Y");
		return paramMap;
	}
	/**
	 * 保存角色对应的菜单
	 * @param inParam
	 * @return
	 */
	public Map doSaveRoleMenu(Map<String, Object> inParam){
		Map paramMap = new HashMap();
		List entityIds = (List)inParam.get("entityIds");
		int roleId = Integer.valueOf(inParam.get("roleId")+"");
		if(entityIds.size()<=0){
			throw new BusinessException("请传入entityIds");	
		}
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		sysRoleSV.doSaveRoleMenu(entityIds,roleId);
		paramMap.put("info", "Y");
		return paramMap;
		
	}
	
	
	/**
	 * 查询角色已添加的URL
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List queryMenuByRoleId(Map<String, Object> inParam) throws Exception{
		Map paramMap = new HashMap();
		Integer roleId = Integer.valueOf(inParam.get("roleId").toString());
		if(roleId==null || roleId<=0){
			throw new BusinessException("请选择角色");	
		}
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		List roleList = sysRoleSV.queryRoleByMenu(roleId);
		
		return roleList;
	}
	
	
	/**
	 * 删除菜单
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map delSysRole(Map<String, Object> inParam) throws Exception{
		Map paramMap = new HashMap();
		List<Integer> roleIds = (List<Integer>)inParam.get("roleIds");
		 Session session  = SysContexts.getEntityManager();
		if(roleIds.size()<=0){
			throw new BusinessException("请传入menuId");	
		}
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		sysRoleSV.delSysRole(roleIds);
		paramMap.put("info", "Y");
		return paramMap;
	}
	
	
	/**
	 * 查询角色详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map querySysRoleDtl(Map<String, String> inParam) throws Exception{
		int roleId=DataFormat.getIntKey(inParam, "roleId");
		Session session = SysContexts.getEntityManager(true);
		SysRole sysRole = (SysRole)session.get(SysRole.class, roleId);
		if(sysRole==null){
			throw new BusinessException("查询不到该角色的信息");	
		}
		Map iparmMap = new HashMap();
		iparmMap.put("sysRole", sysRole);
		return iparmMap;
	}
	
	
	
	
	
	/**
	 * 查询URl
	 * @param inParam
	 * @return
	 */
	public List queryOperSysUrl(Map<String, String> inParam){
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		List<SysUrl> urlList  =  sysRoleSV.queryOperSysUrl();
		return urlList;
	}
	
	
	/**
	 * lyh
	 * 根据传入的角色的租户，组织查询角色
	 * 
	 * @param tenantId  －1 表示查询的是公司管理员的角色
	 * @param orgId
	 * @return
	 */
	public List<SysRole> queryRoleByOrg(Map<String, String> inParam){
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		Long tenantId=DataFormat.getLongKey(inParam, "tenantId");
		Long orgId=DataFormat.getLongKey(inParam, "orgId");
		Integer roleType = DataFormat.getIntKey(inParam, "roleType");
		Integer menuType=DataFormat.getIntKey(inParam, "menuType");
		List<SysRole> roles=sysRoleSV.queryRoleByOrg(tenantId, orgId,roleType,menuType);
		return roles;
	}
	/***
	 * 查询平台角色的租户为－1
	 * @author 邱林锋
	 * @param queryRoleCm
	 * 接口：100009
	 * */
	public Map<String,Object> queryRoleCm(Map<String, String> inParam){
		Long tenantId = -1L;
		int roleType = SysStaticDataEnum.ROLE_TYPE.COMPANY;
		String roleName = DataFormat.getStringKey(inParam, "roleName");
		Integer menuType = DataFormat.getIntKey(inParam,"menuType");
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		Pagination page= sysRoleSV.getSysRoleAll(tenantId, roleType,roleName,menuType);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	}
	/***
	 * 查询员工角色的租户为当前住户
	 * @author 邱林锋
	 * @param queryRoleST
	 * 接口：100010
	 * */
	public Map<String,Object> queryRoleST(Map<String, String> inParam){
		Long tenantId = SysContexts.getCurrentOperator().getTenantId();
		String roleName = DataFormat.getStringKey(inParam, "roleName");
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		Pagination page= sysRoleSV.getSysRoleAll(tenantId, null,roleName,null);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	}
	/***
	 * 删除角色
	 * @author 邱林锋
	 * @param delSysRoleAllByRoleId
	 * 接口：100011
	 * */
	public Map<String,Object> delSysRoleAllByRoleId(Map<String, String> inParam){
		Integer roleId = DataFormat.getIntKey(inParam,"roleId");
		SysRoleSV sysRoleSV = (SysRoleSV)SysContexts.getBean("sysRoleSV");
		if(sysRoleSV.checkSysROR(roleId)){
			throw new BusinessException("已经存在使用的用户，不能删除！");
		}
		Session session = SysContexts.getEntityManager();
		session.createSQLQuery("delete from sys_role where role_id = :roleId ").setParameter("roleId", roleId).executeUpdate();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
	}
}
