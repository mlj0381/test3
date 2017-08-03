package com.wo56.business.ac.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class AcAccountWallet implements Serializable{
	private Long id;
	private String accountNum;
	private Long amount;
	private Integer amountType;
	private Long walletId;
	private Long userId;
	private Date createTime;
	private Integer amountState;
	private Date applyTime;
	private Date showTime;
	private Long tenantId;
	private Integer auditStatus;
	private Date auditTime;
	private Long auditId;
	private Integer writeState;
	private Date writeTime;
	private Long writeId;
	private String auditRemark;
	private Long applyId;
	private Integer state;
	private Integer paymentType;
	private String paymentCard;
	
	
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentCard() {
		return paymentCard;
	}
	public void setPaymentCard(String paymentCard) {
		this.paymentCard = paymentCard;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public Long getAuditId() {
		return auditId;
	}
	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}
	public Integer getWriteState() {
		return writeState;
	}
	public void setWriteState(Integer writeState) {
		this.writeState = writeState;
	}
	public Date getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}
	public Long getWriteId() {
		return writeId;
	}
	public void setWriteId(Long writeId) {
		this.writeId = writeId;
	}
	
	public String getAuditRemark() {
		return auditRemark;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	public Long getApplyId() {
		return applyId;
	}
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public AcAccountWallet(){}
	public AcAccountWallet(Long id, String accountNum, Long amount,
			Integer amountType, Long walletId, Long userId, Date createTime,
			Integer amountState, Date applyTime, Date showTime, Long tenantId) {
		super();
		this.id = id;
		this.accountNum = accountNum;
		this.amount = amount;
		this.amountType = amountType;
		this.walletId = walletId;
		this.userId = userId;
		this.createTime = createTime;
		this.amountState = amountState;
		this.applyTime = applyTime;
		this.showTime = showTime;
		this.tenantId = tenantId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Integer getAmountType() {
		return amountType;
	}
	public void setAmountType(Integer amountType) {
		this.amountType = amountType;
	}
	public Long getWalletId() {
		return walletId;
	}
	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getAmountState() {
		return amountState;
	}
	public void setAmountState(Integer amountState) {
		this.amountState = amountState;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getShowTime() {
		return showTime;
	}
	public void setShowTime(Date showTime) {
		this.showTime = showTime;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	
	
}
