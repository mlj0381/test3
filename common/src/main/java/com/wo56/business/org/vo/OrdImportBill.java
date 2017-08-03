package com.wo56.business.org.vo;

import java.util.Date;


/**
 * Daorushuju entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class OrdImportBill implements java.io.Serializable {

	// Fields

	private Long id;
	private String fhdh;
	private String artNo;
	private String fridges;
	private Integer number;
	private String volume;
	private String payment;
	private Long freight;
	private String consignee;
	private String consigneePhone;
	private String alternativePhone;
	private Long recProvinces;
	private String recProvincesName;
	private Long recCity;
	private String recCityName;
	private Long recDistrict;
	private String recDistrictName;
	private String address;
	private Long opId;
	private String opName;
	private Date createDate;
	
	private String weight;
	private Integer deliveryType;
	private String deliveryTypeName;
	private Integer serviceType;
	private String serviceTypeName;
	private Long installCosts;
	private String remarks;
	private String paymentName;
	private Integer state;
	private String trackingNum;
	private String consignor;
	private String 	consignorPhone;
	private Long orgId;
	private Long tenantId;
	private Integer volumeNum;
	private Integer weightNum;
	
	public OrdImportBill(){}
	
	public OrdImportBill(Long id, String fhdh, String artNo, String fridges,
			Integer number, String volume, String payment, Long freight,
			String consignee, String consigneePhone, String alternativePhone,
			Long recProvinces, String recProvincesName, Long recCity,
			String recCityName, Long recDistrict, String recDistrictName,
			String address, Long opId, String opName, Date createDate,
			String weight, Integer deliveryType, String deliveryTypeName,
			Integer serviceType, String serviceTypeName, Long installCosts,
			String remarks, String paymentName, Integer state,
			String trackingNum, String consignor, String consignorPhone,
			Long orgId, Long tenantId, Integer volumeNum, Integer weightNum) {
		super();
		this.id = id;
		this.fhdh = fhdh;
		this.artNo = artNo;
		this.fridges = fridges;
		this.number = number;
		this.volume = volume;
		this.payment = payment;
		this.freight = freight;
		this.consignee = consignee;
		this.consigneePhone = consigneePhone;
		this.alternativePhone = alternativePhone;
		this.recProvinces = recProvinces;
		this.recProvincesName = recProvincesName;
		this.recCity = recCity;
		this.recCityName = recCityName;
		this.recDistrict = recDistrict;
		this.recDistrictName = recDistrictName;
		this.address = address;
		this.opId = opId;
		this.opName = opName;
		this.createDate = createDate;
		this.weight = weight;
		this.deliveryType = deliveryType;
		this.deliveryTypeName = deliveryTypeName;
		this.serviceType = serviceType;
		this.serviceTypeName = serviceTypeName;
		this.installCosts = installCosts;
		this.remarks = remarks;
		this.paymentName = paymentName;
		this.state = state;
		this.trackingNum = trackingNum;
		this.consignor = consignor;
		this.consignorPhone = consignorPhone;
		this.orgId = orgId;
		this.tenantId = tenantId;
		this.volumeNum = volumeNum;
		this.weightNum = weightNum;
	}
	public Long getId() {
		return id;
	}
	public String getFhdh() {
		return fhdh;
	}
	public String getArtNo() {
		return artNo;
	}
	public String getFridges() {
		return fridges;
	}
	public Integer getNumber() {
		return number;
	}
	public String getVolume() {
		return volume;
	}
	public String getPayment() {
		return payment;
	}
	public Long getFreight() {
		return freight;
	}
	public String getConsignee() {
		return consignee;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public String getAlternativePhone() {
		return alternativePhone;
	}
	public Long getRecProvinces() {
		return recProvinces;
	}
	public String getRecProvincesName() {
		return recProvincesName;
	}
	public Long getRecCity() {
		return recCity;
	}
	public String getRecCityName() {
		return recCityName;
	}
	public Long getRecDistrict() {
		return recDistrict;
	}
	public String getRecDistrictName() {
		return recDistrictName;
	}
	public String getAddress() {
		return address;
	}
	public Long getOpId() {
		return opId;
	}
	public String getOpName() {
		return opName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public String getWeight() {
		return weight;
	}
	public Integer getDeliveryType() {
		return deliveryType;
	}
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}
	public Integer getServiceType() {
		return serviceType;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public Long getInstallCosts() {
		return installCosts;
	}
	public String getRemarks() {
		return remarks;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public Integer getState() {
		return state;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public String getConsignor() {
		return consignor;
	}
	public String getConsignorPhone() {
		return consignorPhone;
	}
	public Long getOrgId() {
		return orgId;
	}
	public Long getTenantId() {
		return tenantId;
	}

	public Integer getVolumeNum() {
		return volumeNum;
	}

	public Integer getWeightNum() {
		return weightNum;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setFhdh(String fhdh) {
		this.fhdh = fhdh;
	}
	public void setArtNo(String artNo) {
		this.artNo = artNo;
	}
	public void setFridges(String fridges) {
		this.fridges = fridges;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public void setFreight(Long freight) {
		this.freight = freight;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	public void setAlternativePhone(String alternativePhone) {
		this.alternativePhone = alternativePhone;
	}
	public void setRecProvinces(Long recProvinces) {
		this.recProvinces = recProvinces;
	}
	public void setRecProvincesName(String recProvincesName) {
		this.recProvincesName = recProvincesName;
	}
	public void setRecCity(Long recCity) {
		this.recCity = recCity;
	}
	public void setRecCityName(String recCityName) {
		this.recCityName = recCityName;
	}
	public void setRecDistrict(Long recDistrict) {
		this.recDistrict = recDistrict;
	}
	public void setRecDistrictName(String recDistrictName) {
		this.recDistrictName = recDistrictName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}
	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}
	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public void setInstallCosts(Long installCosts) {
		this.installCosts = installCosts;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}
	public void setConsignorPhone(String consignorPhone) {
		this.consignorPhone = consignorPhone;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public void setVolumeNum(Integer volumeNum) {
		this.volumeNum = volumeNum;
	}

	public void setWeightNum(Integer weightNum) {
		this.weightNum = weightNum;
	}
	
}