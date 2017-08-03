package com.wo56.business.sys.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class IndexElementIn implements IParamIn{
	private Long userId;
	
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.COMMON.INDEX_ELEMENT;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}
