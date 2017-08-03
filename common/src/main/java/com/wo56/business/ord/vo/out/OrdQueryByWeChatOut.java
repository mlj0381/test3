package com.wo56.business.ord.vo.out;

import java.util.Date;

public class OrdQueryByWeChatOut {
	private String cityName;
	private String consigneeBill;
	private String consigneeName;
	private long orderId;
	private String orderStateName;
	private Date createDate;
	private String deliveryAddress;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getConsigneeBill() {
		return consigneeBill;
	}
	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getOrderStateName() {
		return orderStateName;
	}
	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	 
 
 
	

}
