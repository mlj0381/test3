package com.wo56.business.cm.vo.out;

import java.util.Date;

public class PullBlackCarrierOut {
	private long pullId;
	private String carrierName;
	private String carrierAccount;
	private String companyName;
	private Date createDate;
	private String creatorName;
	private String remark;
	private String pullLevel;
	
	

	public String getPullLevel() {
		return pullLevel;
	}

	public void setPullLevel(String pullLevel) {
		this.pullLevel = pullLevel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getPullId() {
		return pullId;
	}

	public void setPullId(long pullId) {
		this.pullId = pullId;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getCarrierAccount() {
		return carrierAccount;
	}

	public void setCarrierAccount(String carrierAccount) {
		this.carrierAccount = carrierAccount;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

}
