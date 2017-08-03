package com.wo56.business.ac.vo.out;


public class AcAccountWalletTipOut {
	private Long userId;
	private String loginAcct;
	private String userName;
	private String paymentTypeString;
	private String bankCard;
	private String bankHolder;
	private String accountNum;
	private String notPresentFee;//未体现
	private String cashWithdrawalFee;//提现中
	private String alreadyPresentFee;//已提现
	private String remark;
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getLoginAcct() {
		return loginAcct;
	}
	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPaymentTypeString() {
		return paymentTypeString;
	}
	public void setPaymentTypeString(String paymentTypeString) {
		this.paymentTypeString = paymentTypeString;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getBankHolder() {
		return bankHolder;
	}
	public void setBankHolder(String bankHolder) {
		this.bankHolder = bankHolder;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getNotPresentFee() {
		return notPresentFee;
	}
	public void setNotPresentFee(String notPresentFee) {
		this.notPresentFee = notPresentFee;
	}
	public String getCashWithdrawalFee() {
		return cashWithdrawalFee;
	}
	public void setCashWithdrawalFee(String cashWithdrawalFee) {
		this.cashWithdrawalFee = cashWithdrawalFee;
	}
	public String getAlreadyPresentFee() {
		return alreadyPresentFee;
	}
	public void setAlreadyPresentFee(String alreadyPresentFee) {
		this.alreadyPresentFee = alreadyPresentFee;
	}
	
}
