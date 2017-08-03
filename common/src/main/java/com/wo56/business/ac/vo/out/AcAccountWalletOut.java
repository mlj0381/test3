package com.wo56.business.ac.vo.out;

import java.util.List;


public class AcAccountWalletOut {
	private String amount;
	private String applyTime;
	private List<AcMyWalletOut> items;
	private String showTime;
	private String tenantName;
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public List<AcMyWalletOut> getItems() {
		return items;
	}
	public void setItems(List<AcMyWalletOut> items) {
		this.items = items;
	}
	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	
}
