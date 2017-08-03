package com.wo56.business.transfer.vo.in.save;

import java.io.Serializable;

public class OrderSomeInfo implements Serializable{

	private Long orderId;
	//发货人
	private String consignorName;
	//发货人手机
	private String consignorBill;
	//收货方
	private String consigneeLinkmanName;
	//收货联系电话
	private String consigneeTelephone;
	//收货地址
	private String address;
	//服务方式
	private String deliveryType;
	//中转费
	private double transferValue;
	
	private String outgoingTrackingNum;
	private String outgoingTime;
	
	
	
	public String getOutgoingTime() {
		return outgoingTime;
	}
	public void setOutgoingTime(String outgoingTime) {
		this.outgoingTime = outgoingTime;
	}
	public double getTransferValue() {
		return transferValue;
	}
	public void setTransferValue(double transferValue) {
		this.transferValue = transferValue;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getConsignorName() {
		return consignorName;
	}
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}
	public String getConsignorBill() {
		return consignorBill;
	}
	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}
	public String getConsigneeLinkmanName() {
		return consigneeLinkmanName;
	}
	public void setConsigneeLinkmanName(String consigneeLinkmanName) {
		this.consigneeLinkmanName = consigneeLinkmanName;
	}
	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}
	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getOutgoingTrackingNum() {
		return outgoingTrackingNum;
	}
	public void setOutgoingTrackingNum(String outgoingTrackingNum) {
		this.outgoingTrackingNum = outgoingTrackingNum;
	}
	
}
