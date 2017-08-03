package com.wo56.business.ord.vo.out;

import com.framework.core.inter.vo.OutParamVO;

public class ZxOrdViewOutParam extends OutParamVO {

	private String cityName;
	private String companyName;
	private String consigneeBill;
	private String consigneeName;
	private long orderId;
	private String orderNo;
	private int orderState;
	private String orderStateName;
	private String trackingNum;
	private String pickAddress;
	private String consignorBill;
	private String consignorName;
	private String address;
	private String collectMoney;
	private String count;
	private String deliveryCharge;
	private String freight;
	private String landFee;
	private String otherFee;
	private String packageName;
	private String paymentTypeName;
	private String premium;
	private String product;
	private String reputation;
	private String serviceCharge;
	private String serviceTypeName;
	private String traCityName;
	private String transitFee;
	private String volume;
	private String weight;
	private String workerBill;
	private String workerName;
	private String totalFee;
	private String tipsFee;
	private String orderTimeString;
	private String tenantPhone;
	private String provinceName;
	private String districtName;
	private String inputTimeString;
	private String supportStaffPhone;
	private String trackingId;
	
	
		
		
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getSupportStaffPhone() {
		return supportStaffPhone;
	}
	public void setSupportStaffPhone(String supportStaffPhone) {
		this.supportStaffPhone = supportStaffPhone;
	} 
	
	//新增开单时间
	public String getInputTimeString() {
		return inputTimeString;
	}
	public void setInputTimeString(String inputTimeString) {
		this.inputTimeString = inputTimeString;
	}
	//新加包装费
	private String pickingFee;
	
	private String remark;
	private String pikeCityName;
	
	
	
	public String getPikeCityName() {
		return pikeCityName;
	}
	public void setPikeCityName(String pikeCityName) {
		this.pikeCityName = pikeCityName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPickingFee() {
		return pickingFee;
	}
	public void setPickingFee(String pickingFee) {
		this.pickingFee = pickingFee;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getTenantPhone() {
		return tenantPhone;
	}
	public void setTenantPhone(String tenantPhone) {
		this.tenantPhone = tenantPhone;
	}
	public String getOrderTimeString() {
		return orderTimeString;
	}
	public void setOrderTimeString(String orderTimeString) {
		this.orderTimeString = orderTimeString;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getTipsFee() {
		return tipsFee;
	}
	public void setTipsFee(String tipsFee) {
		this.tipsFee = tipsFee;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCollectMoney() {
		return collectMoney;
	}
	public void setCollectMoney(String collectMoney) {
		this.collectMoney = collectMoney;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getDeliveryCharge() {
		return deliveryCharge;
	}
	public void setDeliveryCharge(String deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getLandFee() {
		return landFee;
	}
	public void setLandFee(String landFee) {
		this.landFee = landFee;
	}
	public String getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPaymentTypeName() {
		return paymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}
	public String getPremium() {
		return premium;
	}
	public void setPremium(String premium) {
		this.premium = premium;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getReputation() {
		return reputation;
	}
	public void setReputation(String reputation) {
		this.reputation = reputation;
	}
	public String getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public String getTraCityName() {
		return traCityName;
	}
	public void setTraCityName(String traCityName) {
		this.traCityName = traCityName;
	}
	public String getTransitFee() {
		return transitFee;
	}
	public void setTransitFee(String transitFee) {
		this.transitFee = transitFee;
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
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
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

    
    
    
}