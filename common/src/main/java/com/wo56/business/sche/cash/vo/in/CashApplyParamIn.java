package com.wo56.business.sche.cash.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class CashApplyParamIn extends PageInParamVO implements IParamIn {
	
	private long userId;
	private String strTaskIds;
	private String gscode;
	private String userName;
	private String tenantId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CASH.CASH_APPLICATION_APPLY;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getStrTaskIds() {
		return strTaskIds;
	}

	public void setStrTaskIds(String strTaskIds) {
		this.strTaskIds = strTaskIds;
	}

	public String getGscode() {
		return gscode;
	}

	public void setGscode(String gscode) {
		this.gscode = gscode;
	}

}
