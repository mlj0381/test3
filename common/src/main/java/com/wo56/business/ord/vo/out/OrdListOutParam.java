package com.wo56.business.ord.vo.out;

import com.framework.core.inter.vo.OutParamVO;

public class OrdListOutParam extends OutParamVO {

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
	private String orgName;
	private String productName;
	private String count;
	private String packingName;
	private String weight;
	private String volume;
	private String freight;
	private String reputation;
	private String premium;
	private String deliveryCharge;
	private String transitFee;
	private String otherFee;
	private String collectMoney;
	private String landFee;
	private String serviceCharge;
	private String totalFee;
	private String tipsMonery;
	private String createDate;
	private String createName;
	//新加包装费
	private String pickFee;
	//取消时间
	private String opTime;
	
	public String getOpTime() {
		return opTime;
	}
	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}
	public String getPickFee() {
			return pickFee;
	}
    public void setPickFee(String pickFee) {
	    this.pickFee = pickFee;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getTipsMonery() {
		return tipsMonery;
	}
	public void setTipsMonery(String tipsMonery) {
		this.tipsMonery = tipsMonery;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getPackingName() {
		return packingName;
	}
	public void setPackingName(String packingName) {
		this.packingName = packingName;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getReputation() {
		return reputation;
	}
	public void setReputation(String reputation) {
		this.reputation = reputation;
	}
	public String getPremium() {
		return premium;
	}
	public void setPremium(String premium) {
		this.premium = premium;
	}
	public String getDeliveryCharge() {
		return deliveryCharge;
	}
	public void setDeliveryCharge(String deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	public String getTransitFee() {
		return transitFee;
	}
	public void setTransitFee(String transitFee) {
		this.transitFee = transitFee;
	}
	public String getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}
	public String getCollectMoney() {
		return collectMoney;
	}
	public void setCollectMoney(String collectMoney) {
		this.collectMoney = collectMoney;
	}
	public String getLandFee() {
		return landFee;
	}
	public void setLandFee(String landFee) {
		this.landFee = landFee;
	}
	public String getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
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