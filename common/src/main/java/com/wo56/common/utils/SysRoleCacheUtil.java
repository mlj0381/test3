package com.wo56.common.utils;

import java.util.List;

import com.framework.core.cache.CacheFactory;
import com.wo56.business.sys.vo.SysRole;
import com.wo56.common.cache.SysRoleCache;

public class SysRoleCacheUtil {
	public static String  getSysRoleName(int roleId){
		@SuppressWarnings("unchecked")
		List<SysRole> sysRoleList = (List<SysRole>)CacheFactory.get(SysRoleCache.class, "allRole");
		if(sysRoleList.size()>0){
			for(SysRole sysRole:sysRoleList){
				if(roleId==sysRole.getRoleId()){
					return sysRole.getRoleName();
				}
			}
		}
		return "";
}
	
	
	public static List  getSysList(){
		@SuppressWarnings("unchecked")
		List<SysRole> sysRoleList = (List<SysRole>)CacheFactory.get(SysRoleCache.class, "allRole");
		return sysRoleList;
}
	/**
	 * 根据角色Id获取角色信息
	 * @param roleId
	 * @return
	 */
	public static SysRole  getSysRoleById(int roleId){
		List<SysRole> sysRoleList = (List<SysRole>)CacheFactory.get(SysRoleCache.class, "allRole");
		if(sysRoleList.size()>0){
			for(SysRole sysRole:sysRoleList){
				if(roleId==sysRole.getRoleId()){
					return sysRole;
				}
			}
		}
		return null;
	}
	/**
	 * 判断角色列表里面，是否有角色类型
	 * 
	 * @param roleIds
	 * @param roleType
	 * @return
	 */
	public static Boolean isRoleType(List<Integer> roleIds,int roleType){
		List<SysRole> sysRoleList = (List<SysRole>)CacheFactory.get(SysRoleCache.class, "allRole");
		if(sysRoleList.size()>0){
			for(SysRole sysRole:sysRoleList){
				for(Integer roleId:roleIds){
					if(roleId.intValue()==sysRole.getRoleId().intValue() && sysRole.getRoleType()==roleType){
						return true;
					}
				}
				
			}
		}
		
		return false;
	}
	
}
