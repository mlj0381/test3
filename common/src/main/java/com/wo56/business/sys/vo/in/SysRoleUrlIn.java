package com.wo56.business.sys.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class SysRoleUrlIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.COMMON.SAVE_ROLE_URl;
	}
	private Integer roleId;
	private String[] urlIds;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String[] getUrlIds() {
		return urlIds;
	}
	public void setUrlIds(String[] urlIds) {
		this.urlIds = urlIds;
	}
	
}
