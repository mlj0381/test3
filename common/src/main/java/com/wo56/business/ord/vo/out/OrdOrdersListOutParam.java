package com.wo56.business.ord.vo.out;

import com.framework.core.inter.vo.OutParamVO;

public class OrdOrdersListOutParam extends OutParamVO {

	private String cityName;
	private String consigneeBill;
	private String consigneeName;
	private long orderId;
	private String orderNo;
	private int orderState;
	private String orderStateName;
	private String paymentTypeName;
	private String serviceTypeName;
	private String trackingNum;
	private String workerBill;
	private String workerName;
	private String pickAddress;
	private String consignorBill;
	private String consignorName;
	private String disOpName;
	private String disTime;
	private String planPickupTime;
	private String pickupTime;
	private String inputUserName;
	private String inputTime;
	private String createTime;
	private String gxEndTime;
	private String deliveryTime;
	private long idNo;
	private String signTime;
	private String remark;
	private String receiverAddress;
	private String pickStation;
	private String companyName;
	private String reminderTime;
	private String reminderCount;
	private long tenantId;
	private String timeOutString;
	private String tenantName;//专线名称
	private String doOrderTime;
	
	
	
	public String getDoOrderTime() {
		return doOrderTime;
	}
	public void setDoOrderTime(String doOrderTime) {
		this.doOrderTime = doOrderTime;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getTimeOutString() {
		return timeOutString;
	}
	public void setTimeOutString(String timeOutString) {
		this.timeOutString = timeOutString;
	}
	private String pickProCityCounty;
	
	
	
	public String getPickProCityCounty() {
		return pickProCityCounty;
	}
	public void setPickProCityCounty(String pickProCityCounty) {
		this.pickProCityCounty = pickProCityCounty;
	}
	public long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getReminderTime() {
		return reminderTime;
	}
	public void setReminderTime(String reminderTime) {
		this.reminderTime = reminderTime;
	}
	public String getReminderCount() {
		return reminderCount;
	}
	public void setReminderCount(String reminderCount) {
		this.reminderCount = reminderCount;
	}
	public String getPickStation() {
		return pickStation;
	}
	public void setPickStation(String pickStation) {
		this.pickStation = pickStation;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getOrderState() {
		return orderState;
	}
	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	public String getOrderStateName() {
		return orderStateName;
	}
	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}
	public String getPaymentTypeName() {
		return paymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getWorkerBill() {
		return workerBill;
	}
	public void setWorkerBill(String workerBill) {
		this.workerBill = workerBill;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	
	public String getPickAddress() {
		return pickAddress;
	}
	public void setPickAddress(String pickAddress) {
		this.pickAddress = pickAddress;
	}
	public String getConsignorBill() {
		return consignorBill;
	}
	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}
	public String getConsignorName() {
		return consignorName;
	}
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}
	public String getDisOpName() {
		return disOpName;
	}
	public void setDisOpName(String disOpName) {
		this.disOpName = disOpName;
	}
	public String getDisTime() {
		return disTime;
	}
	public void setDisTime(String disTime) {
		this.disTime = disTime;
	}
	public String getPlanPickupTime() {
		return planPickupTime;
	}
	public void setPlanPickupTime(String planPickupTime) {
		this.planPickupTime = planPickupTime;
	}
	public String getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}
	public String getInputUserName() {
		return inputUserName;
	}
	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}
	public String getInputTime() {
		return inputTime;
	}
	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}
	public String getGxEndTime() {
		return gxEndTime;
	}
	public void setGxEndTime(String gxEndTime) {
		this.gxEndTime = gxEndTime;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public long getIdNo() {
		return idNo;
	}
	public void setIdNo(long idNo) {
		this.idNo = idNo;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

    
    
}