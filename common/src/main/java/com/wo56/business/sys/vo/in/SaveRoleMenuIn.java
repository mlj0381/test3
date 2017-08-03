package com.wo56.business.sys.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class SaveRoleMenuIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.COMMON.SAVE_ROLE_MENU;
	}
	
	private Integer[] entityIds;
	private int roleId;
	public Integer[] getEntityIds() {
		return entityIds;
	}
	public void setEntityIds(Integer[] entityIds) {
		this.entityIds = entityIds;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


}
