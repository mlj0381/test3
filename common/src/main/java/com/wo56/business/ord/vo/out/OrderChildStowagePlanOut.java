package com.wo56.business.ord.vo.out;

public class OrderChildStowagePlanOut {
	/**子运单id*/
	private String childOrderId;
	/**子运单号别名*/
	private String childTrackingNumAli;
	/**收货人*/
	private String consigneeName;
	/**到站*/
	private String destCityName;
	/**子运单状态*/
	private String childOrderState;
	
	private String orderId;
	
	private String volume;
	
	private String weight;
	
	private String childNumber;
	
	private String isSplit;
	
	public String getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}

	public String getChildNumber() {
		return childNumber;
	}

	public void setChildNumber(String childNumber) {
		this.childNumber = childNumber;
	}

	public String getChildOrderId() {
		return childOrderId;
	}

	public void setChildOrderId(String childOrderId) {
		this.childOrderId = childOrderId;
	}

	public String getChildTrackingNumAli() {
		return childTrackingNumAli;
	}

	public void setChildTrackingNumAli(String childTrackingNumAli) {
		this.childTrackingNumAli = childTrackingNumAli;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getDestCityName() {
		return destCityName;
	}

	public void setDestCityName(String destCityName) {
		this.destCityName = destCityName;
	}

	public String getChildOrderState() {
		return childOrderState;
	}

	public void setChildOrderState(String childOrderState) {
		this.childOrderState = childOrderState;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	
	
}
