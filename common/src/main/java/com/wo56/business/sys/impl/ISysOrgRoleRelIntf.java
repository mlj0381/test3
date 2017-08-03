package com.wo56.business.sys.impl;

import java.util.List;
import java.util.Map;

import com.wo56.business.sys.vo.SysOrgRoleRel;



public interface ISysOrgRoleRelIntf {
	/**
	 * 根据租户查询网点角色
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgRoleRel> querySysOrgRoleRelList(Map<String,Object> params) throws Exception;
	
	/**
	 * 删除网点角色
	 * 
	 * 如果这个网点的角色已经赋权给到下面的员工了，提示不能删除
	 * @param id
	 * @throws Exception
	 */
	public void delSysOrgRoleRel(Map<String,Object> params) throws Exception;
	/**
	 * 
	 * @param params
	 * @throws Exception
	 */
	public void saveSysOrgRoleRelList(Map<String,Object> params) throws Exception;
	/**
	 * 更新用户的的网点，角色信息
	 * @param params
	 * @throws Exception
	 */
	public void updateUserOrgRole(Map<String,Object> params) throws Exception;
}
