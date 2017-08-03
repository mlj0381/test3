package com.wo56.business.sys;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 所有用户模型，权限的请求都放在这个bo
 * @author liyiye
 *
 */
public class SysSecBO {
	private static final Log log = LogFactory.getLog(SysSecBO.class);
	
	
	
	/**
	 * 查询角色列表
	 *      根据当前登录的用户的角色查询相应的角色
	 *      如果当前登录的角色类型为-1(平台角色类型) ,查询角色类型为公司管理类型
	 *      如果当前登录的角色公司管理角色,查找改租户下面的所有角色
	 *      如果当前登录的角色类型为网点管理员角色，查询该网点下面的角色
	 * 
	 * @return
	 */
	public String queryRole() throws Exception{
		
		return "";
	}
	
	/**
	 * 
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryUserOfMenu() throws Exception{
		
		return "";
	}
	
	/**
	 * 保存角色，角色相关的菜单
	 * 
	 * 如果角色对应的菜单，操作减少，需要查询该角色对应的用户
	 *             如果该用户是公司管理员，需要失效该公司下面的所有角色对应的菜单（减少的菜单）关系
	 *             如果该用户是网点管理员，需要失效该网点下面的所有角色对应的菜单（减少的菜单）关系
	 * @return
	 * @throws Exception
	 */
	public String saveRoleOfMenu() throws Exception{
		
		return "";
	}
	
}
