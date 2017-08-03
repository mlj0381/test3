package com.wo56.business.sys.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class queryCmUserRelarIn implements IParamIn {
	/** 登录用户的账号 **/
	private String loginAcct;
	private long tenantId;

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.COMMON.QUERY_REL_LOGIN;
	}

	public String getLoginAcct() {
		return loginAcct;
	}

	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}


	public long getTenantId() {
		return tenantId;
	}

	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
}
