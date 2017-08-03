package com.wo56.business.ord.vo;

import java.util.Date;

/**
 * OrderInfoTmsErrorHis entity. @author MyEclipse Persistence Tools
 */

public class OrderInfoTmsErrorHis implements java.io.Serializable {

	// Fields

	private Long id;
	private Long orderId;
	private Long ordsId;
	private String orderNumber;
	private Date createTime;
	private String createName;
	private String userName;
	private String passPort;
	private String passPortNo;
	private Date signTime;
	private String signName;
	private Long tenantId;
	private Integer sendLevel;
	private Integer orderState;
	private Date sendTime;
	private Integer sendFlag;
	
	private String desProvinceName;
	private String desCityName;
	private String desDistrictName;
	private String consignee;
	private String consigneePhone;
	private String consigneeAddress;
	private String consignor;
	private String consignorPhone;
	private String consignorAddress;
	private String productName;
	private String packName;
	private String carrierName;
	private String pullName;
	private String pullPhone;
	private String number;
	private String weight;
	private String volume;
	private String paymentName;
	private String interchangeName;
	private String remarks;
	private String arrivedOrgName;
	private String  billingOrgName;
	private String freightDouble;
	private String reputationDouble;
	private String premiumDouble;
	private String deliveryChargeDouble;
	private String transitFeeDouble;
	private String otherFeeDouble;
	private String collectMoneyDouble;
	private String landFeeDouble;
	private String serviceChargeDouble;
	private String totalFeeDouble;
	private String tipDouble;
	private String selfNumber;
	
	private String pickingCostsDouble;
	
	
	
	
	// Constructors

	public String getPickingCostsDouble() {
		return pickingCostsDouble;
	}

	public void setPickingCostsDouble(String pickingCostsDouble) {
		this.pickingCostsDouble = pickingCostsDouble;
	}

	public String getSelfNumber() {
		return selfNumber;
	}

	public void setSelfNumber(String selfNumber) {
		this.selfNumber = selfNumber;
	}

	public String getFreightDouble() {
		return freightDouble;
	}

	public void setFreightDouble(String freightDouble) {
		this.freightDouble = freightDouble;
	}

	public String getReputationDouble() {
		return reputationDouble;
	}

	public void setReputationDouble(String reputationDouble) {
		this.reputationDouble = reputationDouble;
	}

	public String getPremiumDouble() {
		return premiumDouble;
	}

	public void setPremiumDouble(String premiumDouble) {
		this.premiumDouble = premiumDouble;
	}

	public String getDeliveryChargeDouble() {
		return deliveryChargeDouble;
	}

	public void setDeliveryChargeDouble(String deliveryChargeDouble) {
		this.deliveryChargeDouble = deliveryChargeDouble;
	}

	public String getTransitFeeDouble() {
		return transitFeeDouble;
	}

	public void setTransitFeeDouble(String transitFeeDouble) {
		this.transitFeeDouble = transitFeeDouble;
	}

	public String getOtherFeeDouble() {
		return otherFeeDouble;
	}

	public void setOtherFeeDouble(String otherFeeDouble) {
		this.otherFeeDouble = otherFeeDouble;
	}

	public String getCollectMoneyDouble() {
		return collectMoneyDouble;
	}

	public void setCollectMoneyDouble(String collectMoneyDouble) {
		this.collectMoneyDouble = collectMoneyDouble;
	}

	public String getLandFeeDouble() {
		return landFeeDouble;
	}

	public void setLandFeeDouble(String landFeeDouble) {
		this.landFeeDouble = landFeeDouble;
	}

	public String getServiceChargeDouble() {
		return serviceChargeDouble;
	}

	public void setServiceChargeDouble(String serviceChargeDouble) {
		this.serviceChargeDouble = serviceChargeDouble;
	}

	public String getTotalFeeDouble() {
		return totalFeeDouble;
	}

	public void setTotalFeeDouble(String totalFeeDouble) {
		this.totalFeeDouble = totalFeeDouble;
	}

	public String getTipDouble() {
		return tipDouble;
	}

	public void setTipDouble(String tipDouble) {
		this.tipDouble = tipDouble;
	}

	public String getDesProvinceName() {
		return desProvinceName;
	}

	public void setDesProvinceName(String desProvinceName) {
		this.desProvinceName = desProvinceName;
	}

	public String getDesCityName() {
		return desCityName;
	}

	public void setDesCityName(String desCityName) {
		this.desCityName = desCityName;
	}

	public String getDesDistrictName() {
		return desDistrictName;
	}

	public void setDesDistrictName(String desDistrictName) {
		this.desDistrictName = desDistrictName;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
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

	public String getConsignor() {
		return consignor;
	}

	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}

	public String getConsignorPhone() {
		return consignorPhone;
	}

	public void setConsignorPhone(String consignorPhone) {
		this.consignorPhone = consignorPhone;
	}

	public String getConsignorAddress() {
		return consignorAddress;
	}

	public void setConsignorAddress(String consignorAddress) {
		this.consignorAddress = consignorAddress;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getInterchangeName() {
		return interchangeName;
	}

	public void setInterchangeName(String interchangeName) {
		this.interchangeName = interchangeName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getArrivedOrgName() {
		return arrivedOrgName;
	}

	public void setArrivedOrgName(String arrivedOrgName) {
		this.arrivedOrgName = arrivedOrgName;
	}

	public String getBillingOrgName() {
		return billingOrgName;
	}

	public void setBillingOrgName(String billingOrgName) {
		this.billingOrgName = billingOrgName;
	}

	

	public Integer getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}
	/** default constructor */
	public OrderInfoTmsErrorHis() {
	}

	/** minimal constructor */
	public OrderInfoTmsErrorHis(Long orderId, Long ordsId, String orderNumber,
			Date createTime) {
		this.orderId = orderId;
		this.ordsId = ordsId;
		this.orderNumber = orderNumber;
		this.createTime = createTime;
	}

	/** full constructor */
	public OrderInfoTmsErrorHis(Long orderId, Long ordsId, String orderNumber,
			Date createTime, String createName, String userName,
			String passPort, String passPortNo, Date signTime,
			String signName, Long tenantId, Integer sendLevel,
			Integer orderState, Date sendTime) {
		this.orderId = orderId;
		this.ordsId = ordsId;
		this.orderNumber = orderNumber;
		this.createTime = createTime;
		this.createName = createName;
		this.userName = userName;
		this.passPort = passPort;
		this.passPortNo = passPortNo;
		this.signTime = signTime;
		this.signName = signName;
		this.tenantId = tenantId;
		this.sendLevel = sendLevel;
		this.orderState = orderState;
		this.sendTime = sendTime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrdsId() {
		return this.ordsId;
	}

	public void setOrdsId(Long ordsId) {
		this.ordsId = ordsId;
	}

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassPort() {
		return this.passPort;
	}

	public void setPassPort(String passPort) {
		this.passPort = passPort;
	}

	public String getPassPortNo() {
		return this.passPortNo;
	}

	public void setPassPortNo(String passPortNo) {
		this.passPortNo = passPortNo;
	}

	public Date getSignTime() {
		return this.signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getSignName() {
		return this.signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public Long getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getSendLevel() {
		return this.sendLevel;
	}

	public void setSendLevel(Integer sendLevel) {
		this.sendLevel = sendLevel;
	}

	public Integer getOrderState() {
		return this.orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

}