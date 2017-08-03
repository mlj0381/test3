package com.wo56.business.ac.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class AcBatchAccountParamIn extends PageInParamVO implements IParamIn {

	/**核销类型 (1)**/
	private String checkType;
	/**收支类型(1)**/
	private int inoutSts;
	/**核销状态 (1)**/
	private int checkSts;
	private String orderId;
	private long orgId;
	/**现金收取状态**/
	private int cashSts;
	private String driverName;
	private String driverPhone;
	private int queryType;
	
	public int getQueryType() {
		return queryType;
	}


	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}


	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.AC.QUERY_PROVE_BATCH;
	}


	public String getCheckType() {
		return checkType;
	}


	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}


	public int getInoutSts() {
		return inoutSts;
	}


	public void setInoutSts(int inoutSts) {
		this.inoutSts = inoutSts;
	}


	public int getCheckSts() {
		return checkSts;
	}


	public void setCheckSts(int checkSts) {
		this.checkSts = checkSts;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public long getOrgId() {
		return orgId;
	}


	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}


	public int getCashSts() {
		return cashSts;
	}


	public void setCashSts(int cashSts) {
		this.cashSts = cashSts;
	}


	public String getDriverName() {
		return driverName;
	}


	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}


	public String getDriverPhone() {
		return driverPhone;
	}


	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	
}

