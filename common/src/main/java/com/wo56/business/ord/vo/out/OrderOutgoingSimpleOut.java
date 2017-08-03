package com.wo56.business.ord.vo.out;

import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class OrderOutgoingSimpleOut extends BaseOutParamVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1200002030408414345L;
	/**订单号*/
	private long orderId;
	/**运单号*/
	private String trackingNum;
	/**开单网点*/
	private Long orgId;
	/**开单网点名称*/
	private String orgIdName;
	/**订单状态*/
	private String orderStateName;
	/**订单状态*/
	private Integer orderState;
	/**总重量*/
	private double weight;
	/**总体积*/
	private double volume;
	/**目的城市*/
	private Long destCity;
	/**目的城市名*/
	private String destCityName;
	/**配送网点*/
	private Long distributionOrgId;
	/**配送城市名*/
	private String distributionOrgName;
	/**回单号*/
	private String receiptNum;
	/**回单份数*/
	private Integer receiptCount;
	/**付款方式*/
	private String paymentTypeName;
	/**现付*/
	private Long cashPayment;
	/**到付*/
	private Long freightCollect;
	/**月结*/
	private Long monthlyPayment;
	/**代收货款*/
	private Long collectingMoney;
	/**收货人网点*/
	private String consigneeName;
	/**收货人电话*/
	private String consigneeBill;
	/**配送方式、交接方式*/
	private String deliveryTypeName;
	/**收货地址(这个数据看一下是否要组装)*/
	private String address;
	/**货物总数量*/
	private Integer count;
	/**备注*/
	private String remarks;
	private String  shipmentNoticeName;
	/**回单状态id*/
	private String receiptState;
	/**回单状态name*/
	private String receiptStateName;
	/**回单付*/
	private Long receiptPayment;
	/***收货人*/
	private String consigneeLinkmanName;
	/**发货人*/
	private String consignorLinkmanName;
	private String consignorName;
	private String goodsNames;
	private String packingTypeNames;
	private Integer orderType;// 订单类型
	private String carrierCompanyName ;// 承运商
	private String carrierCompanyId ;// 承运商Id
	/**回扣*/
	private Long discount;
	/**发货人手机*/
	private String consignorBill;

	private Long outgoingTrackingNum;
	
	private String outgoingTrackingNumString;
	
	private Long outgoingFee;
	private String linkerName;
	private String linkerPhone;
	private String deliveryPhone;
	private String deliveryAddress;
	private Long shouldReceivables;
	private Long shouldPay;
	private Date createDate;
	
	private Date billingTime;
	private Date transitTime;
	private Long carrOrgId;
	private String carrOrgIdName;
	private String billingTimeString;
	private String transitTimeString;
	
	
	public String getOutgoingTrackingNumString() {
		return outgoingTrackingNumString;
	}
	public void setOutgoingTrackingNumString(String outgoingTrackingNumString) {
		this.outgoingTrackingNumString = outgoingTrackingNumString;
	}
	public String getBillingTimeString() {
		return billingTimeString;
	}
	public String getTransitTimeString() {
		return transitTimeString;
	}
	public void setBillingTimeString(String billingTimeString) {
		this.billingTimeString = billingTimeString;
	}
	public void setTransitTimeString(String transitTimeString) {
		this.transitTimeString = transitTimeString;
	}
	public Long getCarrOrgId() {
		return carrOrgId;
	}
	public String getCarrOrgIdName() {
		return carrOrgIdName;
	}
	public void setCarrOrgId(Long carrOrgId) {
		this.carrOrgId = carrOrgId;
	}
	public void setCarrOrgIdName(String carrOrgIdName) {
		this.carrOrgIdName = carrOrgIdName;
	}
	public Date getBillingTime() {
		return billingTime;
	}
	public Date getTransitTime() {
		return transitTime;
	}
	public void setBillingTime(Date billingTime) {
		this.billingTime = billingTime;
	}
	public void setTransitTime(Date transitTime) {
		this.transitTime = transitTime;
	}
	public String getConsigneeLinkmanName() {
		return consigneeLinkmanName;
	}
	public void setConsigneeLinkmanName(String consigneeLinkmanName) {
		this.consigneeLinkmanName = consigneeLinkmanName;
	}
	public Long getReceiptPayment() {
		return receiptPayment;
	}
	public void setReceiptPayment(Long receiptPayment) {
		this.receiptPayment = receiptPayment;
	}
	public String getReceiptState() {
		return receiptState;
	}
	public void setReceiptState(String receiptState) {
		this.receiptState = receiptState;
	}
	public String getReceiptStateName() {
		return receiptStateName;
	}
	public void setReceiptStateName(String receiptStateName) {
		this.receiptStateName = receiptStateName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOrgIdName() {
		return orgIdName;
	}
	public void setOrgIdName(String orgIdName) {
		this.orgIdName = orgIdName;
	}
	public String getOrderStateName() {
		return orderStateName;
	}
	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public Long getDestCity() {
		return destCity;
	}
	public void setDestCity(Long destCity) {
		this.destCity = destCity;
	}
	public String getDestCityName() {
		return destCityName;
	}
	public void setDestCityName(String destCityName) {
		this.destCityName = destCityName;
	}
	public Long getDistributionOrgId() {
		return distributionOrgId;
	}
	public void setDistributionOrgId(Long distributionOrgId) {
		this.distributionOrgId = distributionOrgId;
	}
	public String getDistributionOrgName() {
		return distributionOrgName;
	}
	public void setDistributionOrgName(String distributionOrgName) {
		this.distributionOrgName = distributionOrgName;
	}
	public String getReceiptNum() {
		return receiptNum;
	}
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}
	public Integer getReceiptCount() {
		return receiptCount;
	}
	public void setReceiptCount(Integer receiptCount) {
		this.receiptCount = receiptCount;
	}
	public String getPaymentTypeName() {
		return paymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}
	public Long getCashPayment() {
		return cashPayment;
	}
	public void setCashPayment(Long cashPayment) {
		this.cashPayment = cashPayment;
	}
	public Long getFreightCollect() {
		return freightCollect;
	}
	public void setFreightCollect(Long freightCollect) {
		this.freightCollect = freightCollect;
	}
	public Long getCollectingMoney() {
		return collectingMoney;
	}
	public void setCollectingMoney(Long collectingMoney) {
		this.collectingMoney = collectingMoney;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneeBill() {
		return consigneeBill;
	}
	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}
	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getShipmentNoticeName() {
		return shipmentNoticeName;
	}
	public void setShipmentNoticeName(String shipmentNoticeName) {
		this.shipmentNoticeName = shipmentNoticeName;
	}
	public String getGoodsNames() {
		return goodsNames;
	}
	public void setGoodsNames(String goodsNames) {
		this.goodsNames = goodsNames;
	}
	public Long getMonthlyPayment() {
		return monthlyPayment;
	}
	public void setMonthlyPayment(Long monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}
	public String getConsignorLinkmanName() {
		return consignorLinkmanName;
	}
	public void setConsignorLinkmanName(String consignorLinkmanName) {
		this.consignorLinkmanName = consignorLinkmanName;
	}
	public Long getDiscount() {
		return discount;
	}
	public void setDiscount(Long discount) {
		this.discount = discount;
	}
	public String getPackingTypeNames() {
		return packingTypeNames;
	}
	public void setPackingTypeNames(String packingTypeNames) {
		this.packingTypeNames = packingTypeNames;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getConsignorName() {
		return consignorName;
	}
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}
	public String getCarrierCompanyName() {
		return carrierCompanyName;
	}
	public void setCarrierCompanyName(String carrierCompanyName) {
		this.carrierCompanyName = carrierCompanyName;
	}
	public String getCarrierCompanyId() {
		return carrierCompanyId;
	}
	public void setCarrierCompanyId(String carrierCompanyId) {
		this.carrierCompanyId = carrierCompanyId;
	}
	public Long getOutgoingTrackingNum() {
		return outgoingTrackingNum;
	}
	public void setOutgoingTrackingNum(Long outgoingTrackingNum) {
		this.outgoingTrackingNum = outgoingTrackingNum;
	}
	public Long getOutgoingFee() {
		return outgoingFee;
	}
	public void setOutgoingFee(Long outgoingFee) {
		this.outgoingFee = outgoingFee;
	}
	public String getLinkerName() {
		return linkerName;
	}
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}
	public String getLinkerPhone() {
		return linkerPhone;
	}
	public void setLinkerPhone(String linkerPhone) {
		this.linkerPhone = linkerPhone;
	}
	public String getDeliveryPhone() {
		return deliveryPhone;
	}
	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getConsignorBill() {
		return consignorBill;
	}
	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}
	public Long getShouldReceivables() {
		return shouldReceivables;
	}
	public void setShouldReceivables(Long shouldReceivables) {
		this.shouldReceivables = shouldReceivables;
	}
	public Long getShouldPay() {
		return shouldPay;
	}
	public void setShouldPay(Long shouldPay) {
		this.shouldPay = shouldPay;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
