package com.wo56.business.cm.vo.out;

public class PullBlackCarrierAddOut {
	private long userId;
	private String userName;
	private String companyName;
	private String cooperationMode;//合作方式
	private String serviceArea;//服务商圈
	private String carrierAccount;
	
	public String getCarrierAccount() {
		return carrierAccount;
	}
	public void setCarrierAccount(String carrierAccount) {
		this.carrierAccount = carrierAccount;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCooperationMode() {
		return cooperationMode;
	}
	public void setCooperationMode(String cooperationMode) {
		this.cooperationMode = cooperationMode;
	}
	public String getServiceArea() {
		return serviceArea;
	}
	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}
	
	
	

}
