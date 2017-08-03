package com.wo56.business.sys.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class UserRoleIn implements IParamIn{
	
	private Long userId;
	
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.COMMON.USER_ENTITY;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
