package com.wo56.business.sys.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class UserRoleOut extends BaseOutParamVO{
	
	private static final long serialVersionUID = 3547589906134075157L;
	/**角色id**/
	private Integer roleId;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
}
