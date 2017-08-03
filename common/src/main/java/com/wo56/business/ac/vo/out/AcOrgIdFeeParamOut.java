package com.wo56.business.ac.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class AcOrgIdFeeParamOut  extends BaseOutParamVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2755911617509962081L;
	/**费用id*/
	private long feeId;
	/***费用 (单位：分)*/
	private long costAmount;
	/***费用名称*/
	private String feeName;
	/**网点id (1)**/
	private long orgId;
	private String isFreight;
	public long getFeeId() {
		return feeId;
	}
	public void setFeeId(long feeId) {
		this.feeId = feeId;
	}
	public long getCostAmount() {
		return costAmount;
	}
	public void setCostAmount(long costAmount) {
		this.costAmount = costAmount;
	}

	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	public String getIsFreight() {
		return isFreight;
	}
	public void setIsFreight(String isFreight) {
		this.isFreight = isFreight;
	}
	
	
}
