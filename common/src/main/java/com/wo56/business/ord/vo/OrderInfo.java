package com.wo56.business.ord.vo;

import java.util.Date;

/**
 * OrderInfo entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class OrderInfo implements java.io.Serializable {

	// Fields

	private Long orderId;
	private String orderNumber;
	private Long desProvince;
	private String desProvinceName;
	private Long desCity;
	private String desCityName;
	private Long desDistrict;
	private String desDistrictName;
	private Long traProvince;
	private String traProvinceName;
	private Long traCity;
	private String traCityName;
	private Long traDistrict;
	private String traDistrictName;
	private String consignee;
	private String consigneePhone;
	private String consigneeAddress;
	private String consignor;
	private String consignorPhone;
	private String consignorAddress;
	private String productName;
	private String packName;
	private Long carrierId;
	private String carrierName;
	private Long pullId;
	private String pullName;
	private String pullPhone;
	private Integer orderState;
	private Date createTime;
	private Long createId;
	private String createName;
	private Date opTime;
	private Long opId;
	private String opName;
	private Integer number;
	private String weight;
	private String volume;
	private Integer payment;
	private String paymentName;
	private Integer interchange;
	private String interchangeName;
	private String remarks;
	private Long ordsId;
	private Long arrivedOrgId;
	private Long billingOrgId;
	private Long tenantId;
	private Long staProvince;
	private Long staCity;
	private Long staDistrict;
	private Integer departCount;
	private String selfNumber;
	private Integer orderStateOut;
	
	

	public Integer getOrderStateOut() {
		return orderStateOut;
	}

	public void setOrderStateOut(Integer orderStateOut) {
		this.orderStateOut = orderStateOut;
	}

	public Integer getDepartCount() {
		return departCount;
	}

	public String getSelfNumber() {
		return selfNumber;
	}

	public void setSelfNumber(String selfNumber) {
		this.selfNumber = selfNumber;
	}

	public void setDepartCount(Integer departCount) {
		this.departCount = departCount;
	}

	public Long getBillingOrgId() {
		return billingOrgId;
	}

	public Long getStaProvince() {
		return staProvince;
	}

	public void setStaProvince(Long staProvince) {
		this.staProvince = staProvince;
	}

	public Long getStaCity() {
		return staCity;
	}

	public void setStaCity(Long staCity) {
		this.staCity = staCity;
	}

	public Long getStaDistrict() {
		return staDistrict;
	}

	public void setStaDistrict(Long staDistrict) {
		this.staDistrict = staDistrict;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public void setBillingOrgId(Long billingOrgId) {
		this.billingOrgId = billingOrgId;
	}

	public Long getOrdsId() {
		return ordsId;
	}

	public Long getArrivedOrgId() {
		return arrivedOrgId;
	}

	public void setArrivedOrgId(Long arrivedOrgId) {
		this.arrivedOrgId = arrivedOrgId;
	}

	public String getPullPhone() {
		return pullPhone;
	}

	public void setPullPhone(String pullPhone) {
		this.pullPhone = pullPhone;
	}

	public void setOrdsId(Long ordsId) {
		this.ordsId = ordsId;
	}

	/** default constructor */
	public OrderInfo() {
	}

	/** minimal constructor */
	public OrderInfo(String orderNumber, Integer orderState,
			Date createTime) {
		this.orderNumber = orderNumber;
		this.orderState = orderState;
		this.createTime = createTime;
	}

	/** full constructor */
	public OrderInfo(String orderNumber, Long desProvince,
			String desProvinceName, Long desCity, String desCityName,
			Long desDistrict, String desDistrictName, Long traProvince,
			String traProvinceName, Long traCity, String traCityName,
			Long traDistrict, String traDistrictName, String consignee,
			String consigneePhone, String consigneeAddress, String consignor,
			String consignorPhone, String consignorAddress, String productName,
			String packName, Long carrierId, String carrierName, Long pullId,
			String pullName, Integer orderState, Date createTime,
			Long createId, String createName, Date opTime, Long opId,
			String opName, Integer number, String weight, String volume,
			Integer payment, String paymentName, Integer interchange,
			String interchangeName, String remarks) {
		this.orderNumber = orderNumber;
		this.desProvince = desProvince;
		this.desProvinceName = desProvinceName;
		this.desCity = desCity;
		this.desCityName = desCityName;
		this.desDistrict = desDistrict;
		this.desDistrictName = desDistrictName;
		this.traProvince = traProvince;
		this.traProvinceName = traProvinceName;
		this.traCity = traCity;
		this.traCityName = traCityName;
		this.traDistrict = traDistrict;
		this.traDistrictName = traDistrictName;
		this.consignee = consignee;
		this.consigneePhone = consigneePhone;
		this.consigneeAddress = consigneeAddress;
		this.consignor = consignor;
		this.consignorPhone = consignorPhone;
		this.consignorAddress = consignorAddress;
		this.productName = productName;
		this.packName = packName;
		this.carrierId = carrierId;
		this.carrierName = carrierName;
		this.pullId = pullId;
		this.pullName = pullName;
		this.orderState = orderState;
		this.createTime = createTime;
		this.createId = createId;
		this.createName = createName;
		this.opTime = opTime;
		this.opId = opId;
		this.opName = opName;
		this.number = number;
		this.weight = weight;
		this.volume = volume;
		this.payment = payment;
		this.paymentName = paymentName;
		this.interchange = interchange;
		this.interchangeName = interchangeName;
		this.remarks = remarks;
	}

	// Property accessors

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Long getDesProvince() {
		return this.desProvince;
	}

	public void setDesProvince(Long desProvince) {
		this.desProvince = desProvince;
	}

	public String getDesProvinceName() {
		return this.desProvinceName;
	}

	public void setDesProvinceName(String desProvinceName) {
		this.desProvinceName = desProvinceName;
	}

	public Long getDesCity() {
		return this.desCity;
	}

	public void setDesCity(Long desCity) {
		this.desCity = desCity;
	}

	public String getDesCityName() {
		return this.desCityName;
	}

	public void setDesCityName(String desCityName) {
		this.desCityName = desCityName;
	}

	public Long getDesDistrict() {
		return this.desDistrict;
	}

	public void setDesDistrict(Long desDistrict) {
		this.desDistrict = desDistrict;
	}

	public String getDesDistrictName() {
		return this.desDistrictName;
	}

	public void setDesDistrictName(String desDistrictName) {
		this.desDistrictName = desDistrictName;
	}

	public Long getTraProvince() {
		return this.traProvince;
	}

	public void setTraProvince(Long traProvince) {
		this.traProvince = traProvince;
	}

	public String getTraProvinceName() {
		return this.traProvinceName;
	}

	public void setTraProvinceName(String traProvinceName) {
		this.traProvinceName = traProvinceName;
	}

	public Long getTraCity() {
		return this.traCity;
	}

	public void setTraCity(Long traCity) {
		this.traCity = traCity;
	}

	public String getTraCityName() {
		return this.traCityName;
	}

	public void setTraCityName(String traCityName) {
		this.traCityName = traCityName;
	}

	public Long getTraDistrict() {
		return this.traDistrict;
	}

	public void setTraDistrict(Long traDistrict) {
		this.traDistrict = traDistrict;
	}

	public String getTraDistrictName() {
		return this.traDistrictName;
	}

	public void setTraDistrictName(String traDistrictName) {
		this.traDistrictName = traDistrictName;
	}

	public String getConsignee() {
		return this.consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneePhone() {
		return this.consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getConsigneeAddress() {
		return this.consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsignor() {
		return this.consignor;
	}

	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}

	public String getConsignorPhone() {
		return this.consignorPhone;
	}

	public void setConsignorPhone(String consignorPhone) {
		this.consignorPhone = consignorPhone;
	}

	public String getConsignorAddress() {
		return this.consignorAddress;
	}

	public void setConsignorAddress(String consignorAddress) {
		this.consignorAddress = consignorAddress;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPackName() {
		return this.packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public Long getCarrierId() {
		return this.carrierId;
	}

	public void setCarrierId(Long carrierId) {
		this.carrierId = carrierId;
	}

	public String getCarrierName() {
		return this.carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public Long getPullId() {
		return this.pullId;
	}

	public void setPullId(Long pullId) {
		this.pullId = pullId;
	}

	public String getPullName() {
		return this.pullName;
	}

	public void setPullName(String pullName) {
		this.pullName = pullName;
	}

	public Integer getOrderState() {
		return this.orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateId() {
		return this.createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getOpName() {
		return this.opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getVolume() {
		return this.volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public Integer getPayment() {
		return this.payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public String getPaymentName() {
		return this.paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public Integer getInterchange() {
		return this.interchange;
	}

	public void setInterchange(Integer interchange) {
		this.interchange = interchange;
	}

	public String getInterchangeName() {
		return this.interchangeName;
	}

	public void setInterchangeName(String interchangeName) {
		this.interchangeName = interchangeName;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}