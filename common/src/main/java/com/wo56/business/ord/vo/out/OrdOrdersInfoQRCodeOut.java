package com.wo56.business.ord.vo.out;

import java.util.List;

import com.wo56.business.ord.vo.OrdGoodsInfo;

public class OrdOrdersInfoQRCodeOut {
	private Long orderId;
	private String pullName;
	private String pullPhone;
	private String cityName;
	private String tenantName;
	private String consigneeName;
	private String consigneePhone;
	private String consigneeAddress;
	private String ordersQrCodeUrl;
	private List<OrdGoodsInfo> goodList;
	
	
	public List<OrdGoodsInfo> getGoodList() {
		return goodList;
	}
	public void setGoodList(List<OrdGoodsInfo> goodList) {
		this.goodList = goodList;
	}
	public String getOrdersQrCodeUrl() {
		return ordersQrCodeUrl;
	}
	public void setOrdersQrCodeUrl(String ordersQrCodeUrl) {
		this.ordersQrCodeUrl = ordersQrCodeUrl;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	
	
}
