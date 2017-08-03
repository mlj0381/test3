package com.wo56.business.cm.vo.out;


public class SfUserInfoOut {

	private long userId;
	private String phone;
	private String name;
	private Integer jdAmount;
	private Integer currAmount;
	private String creditScore;
	private Integer userType;
	private String remark;
	private String branchAndInstallFee;
	
	
	
	public String getBranchAndInstallFee() {
		return branchAndInstallFee;
	}
	public void setBranchAndInstallFee(String branchAndInstallFee) {
		this.branchAndInstallFee = branchAndInstallFee;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getJdAmount() {
		return jdAmount;
	}
	public void setJdAmount(Integer jdAmount) {
		this.jdAmount = jdAmount;
	}
	public Integer getCurrAmount() {
		return currAmount;
	}
	public void setCurrAmount(Integer currAmount) {
		this.currAmount = currAmount;
	}
	public String getCreditScore() {
		return creditScore;
	}
	public void setCreditScore(String creditScore) {
		this.creditScore = creditScore;
	}
	
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	

}
