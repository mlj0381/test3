package com.wo56.business.ord.vo.out;

import com.framework.core.inter.vo.OutParamVO;

public class ChildOrderInfoOut extends OutParamVO{
	private String childOrderName;
	private String childOrderState;
	private String stockOrgName;
	private String trackInfo;
	public String getChildOrderName() {
		return childOrderName;
	}
	public void setChildOrderName(String childOrderName) {
		this.childOrderName = childOrderName;
	}
	public String getChildOrderState() {
		return childOrderState;
	}
	public void setChildOrderState(String childOrderState) {
		this.childOrderState = childOrderState;
	}
	public String getStockOrgName() {
		return stockOrgName;
	}
	public void setStockOrgName(String stockOrgName) {
		this.stockOrgName = stockOrgName;
	}
	public String getTrackInfo() {
		return trackInfo;
	}
	public void setTrackInfo(String trackInfo) {
		this.trackInfo = trackInfo;
	}
}
