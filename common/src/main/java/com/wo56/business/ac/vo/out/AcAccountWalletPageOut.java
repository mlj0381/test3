package com.wo56.business.ac.vo.out;

import java.util.Date;

import org.apache.http.client.utils.DateUtils;

import com.framework.core.util.DateUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;

public class AcAccountWalletPageOut {
	private Long id;
	private String accountNum;
	private String userName;
	private String loginAcct;
	private Long amount;
	private String amountString;
	private Date applyTime;
	private String applyTimeString;
	private String applyString;
	private Integer auditStatus;
	private String auditStatusString;
	private String auditString;
	private Date auditTime;
	private String auditTimeString;
	private Integer writeState;
	private String writeStateString;
	private Date writeTime;
	private String writeTimeString;
	private String writeString;
	private String writeRemark;
	private Long userId;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginAcct() {
		return loginAcct;
	}
	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getAmountString() {
		if(amount != null && amount > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(amount, 2));
		}
		return amountString;
	}
	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public String getApplyTimeString() {
		if(applyTime != null){
			return DateUtil.formatDate(applyTime, "yyyy-MM-dd HH:mm:ss");
		}
		return applyTimeString;
	}
	public void setApplyTimeString(String applyTimeString) {
		this.applyTimeString = applyTimeString;
	}
	public String getApplyString() {
		return applyString;
	}
	public void setApplyString(String applyString) {
		this.applyString = applyString;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAuditStatusString() {
		if(auditStatus != null && auditStatus > 0){
			return SysStaticDataUtil.getSysStaticDataCodeName("AUDIT_STATUS_YQ", String.valueOf(auditStatus));
		}
		return auditStatusString;
	}
	public void setAuditStatusString(String auditStatusString) {
		this.auditStatusString = auditStatusString;
	}
	public String getAuditString() {
		return auditString;
	}
	public void setAuditString(String auditString) {
		this.auditString = auditString;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditTimeString() {
		if(auditTime != null){
			return DateUtil.formatDate(auditTime, "yyyy-MM-dd HH:mm:ss");
		}
		return auditTimeString;
	}
	public void setAuditTimeString(String auditTimeString) {
		this.auditTimeString = auditTimeString;
	}
	public Integer getWriteState() {
		return writeState;
	}
	public void setWriteState(Integer writeState) {
		this.writeState = writeState;
	}
	public String getWriteStateString() {
		if(writeState != null && writeState > 0){
			return SysStaticDataUtil.getSysStaticDataCodeName("WRITE_STATE_YQ", String.valueOf(writeState));
		}
		return writeStateString;
	}
	public void setWriteStateString(String writeStateString) {
		this.writeStateString = writeStateString;
	}
	public Date getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}
	public String getWriteTimeString() {
		if(writeTime != null){
			return DateUtil.formatDate(writeTime, "yyyy-MM-dd HH:mm:ss");
		}
		return writeTimeString;
	}
	public void setWriteTimeString(String writeTimeString) {
		this.writeTimeString = writeTimeString;
	}
	public String getWriteString() {
		return writeString;
	}
	public void setWriteString(String writeString) {
		this.writeString = writeString;
	}
	public String getWriteRemark() {
		return writeRemark;
	}
	public void setWriteRemark(String writeRemark) {
		this.writeRemark = writeRemark;
	}
	
}
