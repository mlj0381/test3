package com.wo56.business.ord.vo.out;

import java.util.Date;

public class OrdOrderCostOut {
	private String FeeName;
	private Long FeeId;
	//支出网点
	private Long orgId;
	private String orgName;
	//收入网点
	private Long inComeOrgId;
	private String inComeOrgName;
	private Long costAmount;
	private Date currTime;
	public String getFeeName() {
		return FeeName;
	}
	public void setFeeName(String feeName) {
		FeeName = feeName;
	}

	public Long getFeeId() {
		return FeeId;
	}
	public void setFeeId(Long feeId) {
		FeeId = feeId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getInComeOrgId() {
		return inComeOrgId;
	}
	public void setInComeOrgId(Long inComeOrgId) {
		this.inComeOrgId = inComeOrgId;
	}
	public String getInComeOrgName() {
		return inComeOrgName;
	}
	public void setInComeOrgName(String inComeOrgName) {
		this.inComeOrgName = inComeOrgName;
	}
	public Long getCostAmount() {
		return costAmount;
	}
	public void setCostAmount(Long costAmount) {
		this.costAmount = costAmount;
	}
	public Date getCurrTime() {
		return currTime;
	}
	public void setCurrTime(Date currTime) {
		this.currTime = currTime;
	}

	
	
	
}
