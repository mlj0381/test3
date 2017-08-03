package com.wo56.business.ord.vo;

import java.util.Date;

import com.framework.core.base.POJO;
import com.framework.core.util.SysStaticDataUtil;

/**
 * OrdOrdersInfo entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class OrdOrdersInfo extends POJO implements java.io.Serializable {

	// Fields

	private Long orderId;
	private Long consigneeId;
	private String consigneeName;
	private String consigneeBill;
	private Long provinceId;
	private Long cityId;
	private Long countyId;
	private Long streetId;
	private String address;
	private Integer serviceType;
	private Integer paymentType;
	//private Long tenantId;
	private Long orgId;
	private Integer orderType;
	//private Date createDate;
	private Long createId;
	private Integer createType;
	private Date opDate;
	private Long opId;
	private Integer orderState;
	private String orderNo;
	private String trackingNum;
	private Integer orderNum;
	private String weight;
	private String volume;
	private Long disId;
	private Date disTime;
	private String products;
	private Long tipMoney;
	private Integer isPayTip;
	private String remark;
	private String provinceName;
	private String cityName;
	private String countyName;
	private String streetName;
	private String orderStateName;

	private String serviceTypeName;
	private String paymentTypeName;
	private String companyName;
	private Long freight;
	private String cancerReason;
	private String disOpName;
	private Long qrCodeId;
	private String qrCodeUrl;
	private Long conTenantId;
	private String latitude;
	private String longitude;
	private Long idNo;
	
	

	public Long getIdNo() {
		return idNo;
	}

	public void setIdNo(Long idNo) {
		this.idNo = idNo;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Long getConTenantId() {
		return conTenantId;
	}

	public void setConTenantId(Long conTenantId) {
		this.conTenantId = conTenantId;
	}

	public Long getQrCodeId() {
		return qrCodeId;
	}

	public void setQrCodeId(Long qrCodeId) {
		this.qrCodeId = qrCodeId;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getDisOpName() {
		return disOpName;
	}

	public void setDisOpName(String disOpName) {
		this.disOpName = disOpName;
	}

	public String getCancerReason() {
		return cancerReason;
	}

	public void setCancerReason(String cancerReason) {
		this.cancerReason = cancerReason;
	}

	public Long getFreight() {
		return freight;
	}

	public void setFreight(Long freight) {
		this.freight = freight;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProvinceName() {
		if(provinceId!=null){
			setProvinceName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", provinceId+"").getName());
		}
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		if(cityId!=null)
		setCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", cityId+"").getName());
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		if(countyId!=null)
			setCountyName(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", countyId+"").getName());
		return countyName;
	}

	public void setCountyName(String countyName) {
		
		this.countyName = countyName;
	}

	public String getStreetName() {
		if(streetId!=null)
			setCountyName(SysStaticDataUtil.getStreetDataList("SYS_STREET", streetId+"").getName());
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getOrderStateName() {
		if(orderState!=null){
			setOrderStateName(SysStaticDataUtil.getSysStaticData("ORDERS_STATE", orderState+"").getCodeName());
		}
		return orderStateName;
	}

	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}

	public String getServiceTypeName() {
		if(serviceType!=null){
			setServiceTypeName(SysStaticDataUtil.getSysStaticData("SERVICE_TYPE", serviceType+"").getCodeName());
		}
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public String getPaymentTypeName() {
		if(paymentType!=null){
			setPaymentTypeName(SysStaticDataUtil.getSysStaticData("PAYMENT_TYPE_YQ", paymentType+"").getCodeName());
		}
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	/** default constructor */
	public OrdOrdersInfo() {
	}

	/** minimal constructor */
	public OrdOrdersInfo(Long consigneeId, String consigneeName,
			String consigneeBill, Long provinceId, Long cityId,
			Integer serviceType, Integer paymentType, Date createDate,
			Long createId, Integer createType, Date opDate) {
		this.consigneeId = consigneeId;
		this.consigneeName = consigneeName;
		this.consigneeBill = consigneeBill;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.serviceType = serviceType;
		this.paymentType = paymentType;
		this.createDate = createDate;
		this.createId = createId;
		this.createType = createType;
		this.opDate = opDate;
	}

	/** full constructor */
	public OrdOrdersInfo(Long consigneeId, String consigneeName,
			String consigneeBill, Long provinceId, Long cityId, Long countyId,
			Long streetId, String address, Integer serviceType,
			Integer paymentType, Long tenantId, Long orgId, Integer orderType,
			Date createDate, Long createId, Integer createType,
			Date opDate, Long opId, Integer orderState, String orderNo,
			String trackingNum, Integer orderNum, String weight, String volume,
			Long disId, Date disTime, String products, Long tipMoney,
			Integer isPayTip, String remark) {
		this.consigneeId = consigneeId;
		this.consigneeName = consigneeName;
		this.consigneeBill = consigneeBill;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.countyId = countyId;
		this.streetId = streetId;
		this.address = address;
		this.serviceType = serviceType;
		this.paymentType = paymentType;
		this.tenantId = tenantId;
		this.orgId = orgId;
		this.orderType = orderType;
		this.createDate = createDate;
		this.createId = createId;
		this.createType = createType;
		this.opDate = opDate;
		this.opId = opId;
		this.orderState = orderState;
		this.orderNo = orderNo;
		this.trackingNum = trackingNum;
		this.orderNum = orderNum;
		this.weight = weight;
		this.volume = volume;
		this.disId = disId;
		this.disTime = disTime;
		this.products = products;
		this.tipMoney = tipMoney;
		this.isPayTip = isPayTip;
		this.remark = remark;
	}

	// Property accessors

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getConsigneeId() {
		return this.consigneeId;
	}

	public void setConsigneeId(Long consigneeId) {
		this.consigneeId = consigneeId;
	}

	public String getConsigneeName() {
		return this.consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeBill() {
		return this.consigneeBill;
	}

	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}



	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return this.cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getCountyId() {
		return this.countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public Long getStreetId() {
		return this.streetId;
	}

	public void setStreetId(Long streetId) {
		this.streetId = streetId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Long getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getOrderType() {
		return this.orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreateId() {
		return this.createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Integer getCreateType() {
		return this.createType;
	}

	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

	public Date getOpDate() {
		return this.opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Integer getOrderState() {
		return this.orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTrackingNum() {
		return this.trackingNum;
	}

	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}

	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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

	public Long getDisId() {
		return this.disId;
	}

	public void setDisId(Long disId) {
		this.disId = disId;
	}

	public Date getDisTime() {
		return this.disTime;
	}

	public void setDisTime(Date disTime) {
		this.disTime = disTime;
	}

	public String getProducts() {
		return this.products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public Long getTipMoney() {
		return this.tipMoney;
	}

	public void setTipMoney(Long tipMoney) {
		this.tipMoney = tipMoney;
	}

	public Integer getIsPayTip() {
		return this.isPayTip;
	}

	public void setIsPayTip(Integer isPayTip) {
		this.isPayTip = isPayTip;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}