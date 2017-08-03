package com.wo56.business.ord.vo.out;

import com.framework.core.inter.vo.OutParamVO;

public class TrackListOutParam extends OutParamVO {
	private String consigneeBill;
	private String consigneeName;
	private long orderId;
	private String orderNo;//
	private String orderState;//
	private String orderStateName;
	private String paymentTypeName;//付款方式
	private String serviceTypeName;
	private String trackingNum;//运单号
	private String  trackingStateName; //运单状态
	private String workerBill;
	private String workerName;
	private String pickAddress;//发货人地址
	private String consignorBill;//发湖人手机
	private String consignorName;//发货人名称
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
	private String signTime;//签收时间
	private String signName;//签收人
	private String remark;
	private String receiverAddress;//收货人地址
	private String pickStation;
	private String companyName;
	private String orgName;//开单网点
	private String productName;//品名
	private String count;//件数
	private String packingName;
	private String weight;
	private String volume;
	private String freight;//运费
	private String reputation;//声价
	private String premium;//保价
	private String deliveryCharge;//送货费
	private String transitFee;//中转费
	private String otherFee;//其他费
	private String collectMoney;//货费
	private String landFee;//落地费
	private String serviceCharge;//服务费
	private String totalFee;//总费用
	private String tipsMonery;//小费
	private String createDate;
	private String createName;
	//新加包装费
	private String pickFee;//包装费
	private String tenantId;
	private String  deliverTypeName;//配送方式
	private String dispatchingName;//配送人
	private String dispatchingTime;//配送时间

	private String cityName;//到站
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getOpTime() {
		return opTime;
	}
	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}
	private String opName;//取消运单操作人
	private String opTime;//取消时间
	
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
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String string) {
		this.orderState = string;
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
	public String getTrackingStateName() {
		return trackingStateName;
	}
	public void setTrackingStateName(String trackingStateName) {
		this.trackingStateName = trackingStateName;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getPickStation() {
		return pickStation;
	}
	public void setPickStation(String pickStation) {
		this.pickStation = pickStation;
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
	public String getTipsMonery() {
		return tipsMonery;
	}
	public void setTipsMonery(String tipsMonery) {
		this.tipsMonery = tipsMonery;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getPickFee() {
		return pickFee;
	}
	public void setPickFee(String pickFee) {
		this.pickFee = pickFee;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getDeliverTypeName() {
		return deliverTypeName;
	}
	public void setDeliverTypeName(String deliverTypeName) {
		this.deliverTypeName = deliverTypeName;
	}
	public String getDispatchingName() {
		return dispatchingName;
	}
	public void setDispatchingName(String dispatchingName) {
		this.dispatchingName = dispatchingName;
	}
	public String getDispatchingTime() {
		return dispatchingTime;
	}
	public void setDispatchingTime(String dispatchingTime) {
		this.dispatchingTime = dispatchingTime;
	}
	
}
