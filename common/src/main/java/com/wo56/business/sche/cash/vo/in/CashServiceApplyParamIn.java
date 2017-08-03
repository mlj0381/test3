package com.wo56.business.sche.cash.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class CashServiceApplyParamIn extends PageInParamVO implements IParamIn {
	
	private long serviceId;
	private String strTaskIds;
	private String accountName;
	private Integer receiType;
	private Integer bankType;
	private String bankAccount;
	private String wxAccount;
	private String paypalAccount;
	

	public long getServiceId() {
		return serviceId;
	}


	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}


	public String getStrTaskIds() {
		return strTaskIds;
	}


	public void setStrTaskIds(String strTaskIds) {
		this.strTaskIds = strTaskIds;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public Integer getReceiType() {
		return receiType;
	}


	public void setReceiType(Integer receiType) {
		this.receiType = receiType;
	}


	public Integer getBankType() {
		return bankType;
	}


	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}


	public String getBankAccount() {
		return bankAccount;
	}


	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}


	public String getWxAccount() {
		return wxAccount;
	}


	public void setWxAccount(String wxAccount) {
		this.wxAccount = wxAccount;
	}


	public String getPaypalAccount() {
		return paypalAccount;
	}


	public void setPaypalAccount(String paypalAccount) {
		this.paypalAccount = paypalAccount;
	}


	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CASH.SERVICE_APPLY_CASH;
	}

	
}
