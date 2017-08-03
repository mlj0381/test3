package com.wo56.business.sys.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class IndexAmountIn implements IParamIn{
	private Long orgId;
	
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.COMMON.INDEX_AMOUNT;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
}
