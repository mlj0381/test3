package com.wo56.business.sche.cash.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class CashSfParamIn extends PageInParamVO implements IParamIn {

	private String phone;
	private long userId;
	private String userName;
	private String tenantId;
	private String doObjName;
	private int state ;
	private int feeId ;
	private int userType;
	private String signTimeBegin;
	private String signTimeEnd;
	private int isQuery;
	
	
	
	
	public int getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(int isQuery) {
		this.isQuery = isQuery;
	}

	public String getDoObjName() {
		return doObjName;
	}

	public void setDoObjName(String doObjName) {
		this.doObjName = doObjName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getFeeId() {
		return feeId;
	}

	public void setFeeId(int feeId) {
		this.feeId = feeId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getSignTimeBegin() {
		return signTimeBegin;
	}

	public void setSignTimeBegin(String signTimeBegin) {
		this.signTimeBegin = signTimeBegin;
	}

	public String getSignTimeEnd() {
		return signTimeEnd;
	}

	public void setSignTimeEnd(String signTimeEnd) {
		this.signTimeEnd = signTimeEnd;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

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
		return InterFacesCodeConsts.CASH.CASH_SF_QUERY;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
