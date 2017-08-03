package com.wo56.business.cm.vo.out;

public class CmUserInfoPullParamOut {
	private long userId;
	private String tenantName;
	private String userName;
	private String billId;
	private String address;
	private String paymentTypeString;
	private String bankName;
	private String accountName;
	private String bankICard;
	private String pullStateString;
	private String cooperationMode;
	private String jobNumber;
	private String defaultSingularNum;
	
	
	
	public String getDefaultSingularNum() {
		return defaultSingularNum;
	}
	public void setDefaultSingularNum(String defaultSingularNum) {
		this.defaultSingularNum = defaultSingularNum;
	}
	public String getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPaymentTypeString() {
		return paymentTypeString;
	}
	public void setPaymentTypeString(String paymentTypeString) {
		this.paymentTypeString = paymentTypeString;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankICard() {
		return bankICard;
	}
	public void setBankICard(String bankICard) {
		this.bankICard = bankICard;
	}
	public String getPullStateString() {
		return pullStateString;
	}
	public void setPullStateString(String pullStateString) {
		this.pullStateString = pullStateString;
	}
	public String getCooperationMode() {
		return cooperationMode;
	}
	public void setCooperationMode(String cooperationMode) {
		this.cooperationMode = cooperationMode;
	}
	
	
}
