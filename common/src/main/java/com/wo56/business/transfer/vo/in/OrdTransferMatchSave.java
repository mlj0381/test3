package com.wo56.business.transfer.vo.in;


public class OrdTransferMatchSave {



	//承运商
	private Long orgId;
	private long orderId;
	private String linkPhone;
	private String csPhones;
	private String outgoingTrackingNum;
	private String transferValue;
	private String trackingNum;
	private String outgoingTime;
	private String transportContract;
	private String deliveryAddress;
	
	private String linkerName;
	
	
	
	public String getLinkerName() {
		return linkerName;
	}
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getTransportContract() {
		return transportContract;
	}
	public void setTransportContract(String transportContract) {
		this.transportContract = transportContract;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getCsPhones() {
		return csPhones;
	}
	public void setCsPhones(String csPhones) {
		this.csPhones = csPhones;
	}
	public String getOutgoingTrackingNum() {
		return outgoingTrackingNum;
	}
	public void setOutgoingTrackingNum(String outgoingTrackingNum) {
		this.outgoingTrackingNum = outgoingTrackingNum;
	}
	public String getTransferValue() {
		return transferValue;
	}
	public void setTransferValue(String transferValue) {
		this.transferValue = transferValue;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getOutgoingTime() {
		return outgoingTime;
	}
	public void setOutgoingTime(String outgoingTime) {
		this.outgoingTime = outgoingTime;
	}
	
	
	
	
}
