package com.wo56.business.route.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class RouteCostOut extends BaseOutParamVO{
	/**费用id*/
	private long feeId;
	/***费用 (单位：分)*/
	private long costAmount;
	/***费用 (单位：分)*/
	private long feeName;
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
	public long getFeeName() {
		return feeName;
	}
	public void setFeeName(long feeName) {
		this.feeName = feeName;
	}
	
	

}
