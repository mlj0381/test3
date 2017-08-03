package com.wo56.business.ord.vo.out;

public class OrderInfoPageOut {
	private String orderNo;
	private String orderNumber;
	private String ordsTenantName;
	private String ordTenantName;
	private String billingOrgName;
	private String cityName;
	private String pullName;
	private String pullPhone;
	private String consignor;
	private String consignorPhone;
	private String consignorAddress;
	private String tipString;
	private String createDataString;
	private Long orderId;
	//新加包装费
	private String pickingFee;
	public String getPickingFee() {
			return pickingFee;
    }
	public void setPickingFee(String pickingFee) {
			this.pickingFee = pickingFee;
			}
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getCreateDataString() {
		return createDataString;
	}
	public void setCreateDataString(String createDataString) {
		this.createDataString = createDataString;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOrdsTenantName() {
		return ordsTenantName;
	}
	public void setOrdsTenantName(String ordsTenantName) {
		this.ordsTenantName = ordsTenantName;
	}
	public String getOrdTenantName() {
		return ordTenantName;
	}
	public void setOrdTenantName(String ordTenantName) {
		this.ordTenantName = ordTenantName;
	}
	public String getBillingOrgName() {
		return billingOrgName;
	}
	public void setBillingOrgName(String billingOrgName) {
		this.billingOrgName = billingOrgName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getPullName() {
		return pullName;
	}
	public void setPullName(String pullName) {
		this.pullName = pullName;
	}
	public String getPullPhone() {
		return pullPhone;
	}
	public void setPullPhone(String pullPhone) {
		this.pullPhone = pullPhone;
	}
	public String getConsignor() {
		return consignor;
	}
	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}
	public String getConsignorPhone() {
		return consignorPhone;
	}
	public void setConsignorPhone(String consignorPhone) {
		this.consignorPhone = consignorPhone;
	}
	public String getConsignorAddress() {
		return consignorAddress;
	}
	public void setConsignorAddress(String consignorAddress) {
		this.consignorAddress = consignorAddress;
	}
	public String getTipString() {
		return tipString;
	}
	public void setTipString(String tipString) {
		this.tipString = tipString;
	}
	
}
