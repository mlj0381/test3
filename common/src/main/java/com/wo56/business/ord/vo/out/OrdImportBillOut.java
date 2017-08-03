package com.wo56.business.ord.vo.out;

import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;

@SuppressWarnings("serial")
public class OrdImportBillOut extends BaseOutParamVO{
	private Long id;
	private String fhdh;
	private String artNo;
	private String fridges;
	private Integer number;
	private String volume;
	private String payment;
	private Long freight;
	private String freightString;
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
	private String installCostsString;
	private String remarks;
	private String paymentName;
	private Integer state;
	private String stateString;
	private String trackingNum;
	private String consignor;
	private String consignorPhone;
	private Integer volumeNum;
	private Integer weightNum;
	
	public Integer getVolumeNum() {
		return volumeNum;
	}
	public Integer getWeightNum() {
		return weightNum;
	}
	public void setVolumeNum(Integer volumeNum) {
		this.volumeNum = volumeNum;
	}
	public void setWeightNum(Integer weightNum) {
		this.weightNum = weightNum;
	}
	public String getConsignor() {
		return consignor;
	}
	public String getConsignorPhone() {
		return consignorPhone;
	}
	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}
	public void setConsignorPhone(String consignorPhone) {
		this.consignorPhone = consignorPhone;
	}
	public String getStateString() {
		if(state != null && state >= 0){
			return (state == 0 ? "未提取" : "已提取");
		}
		return stateString;
	}
	public void setStateString(String stateString) {
		this.stateString = stateString;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getFreightString() {
		if(freight != null && freight >= 0){
			return CommonUtil.parseDouble(freight);
		}
		return "";
	}
	public void setFreightString(String freightString) {
		this.freightString = freightString;
	}
	public String getInstallCostsString() {
		if(installCosts != null && installCosts >= 0){
			return CommonUtil.parseDouble(installCosts);
		}
		return "";
	}
	public void setInstallCostsString(String installCostsString) {
		this.installCostsString = installCostsString;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFhdh() {
		return fhdh;
	}
	public void setFhdh(String fhdh) {
		this.fhdh = fhdh;
	}
	public String getArtNo() {
		return artNo;
	}
	public void setArtNo(String artNo) {
		this.artNo = artNo;
	}
	public String getFridges() {
		return fridges;
	}
	public void setFridges(String fridges) {
		this.fridges = fridges;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public Long getFreight() {
		return freight;
	}
	public void setFreight(Long freight) {
		this.freight = freight;
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
	public String getAlternativePhone() {
		return alternativePhone;
	}
	public void setAlternativePhone(String alternativePhone) {
		this.alternativePhone = alternativePhone;
	}
	public Long getRecProvinces() {
		return recProvinces;
	}
	public void setRecProvinces(Long recProvinces) {
		this.recProvinces = recProvinces;
	}
	public String getRecProvincesName() {
		return recProvincesName;
	}
	public void setRecProvincesName(String recProvincesName) {
		this.recProvincesName = recProvincesName;
	}
	public Long getRecCity() {
		return recCity;
	}
	public void setRecCity(Long recCity) {
		this.recCity = recCity;
	}
	public String getRecCityName() {
		return recCityName;
	}
	public void setRecCityName(String recCityName) {
		this.recCityName = recCityName;
	}
	public Long getRecDistrict() {
		return recDistrict;
	}
	public void setRecDistrict(Long recDistrict) {
		this.recDistrict = recDistrict;
	}
	public String getRecDistrictName() {
		return recDistrictName;
	}
	public void setRecDistrictName(String recDistrictName) {
		this.recDistrictName = recDistrictName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public Integer getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getDeliveryTypeName() {
		if(deliveryType != null){
			return SysStaticDataUtil.getSysStaticData("DELIVERY_TYPE", String.valueOf(deliveryType)).getCodeName();
		}
		return "";
	}
	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}
	public Integer getServiceType() {
		return serviceType;
	}
	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceTypeName() {
		if(serviceType != null){
			return SysStaticDataUtil.getSysStaticData("SCHE_SERVICE_TYPE", String.valueOf(serviceType)).getCodeName();
		}
		return "";
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public Long getInstallCosts() {
		return installCosts;
	}
	public void setInstallCosts(Long installCosts) {
		this.installCosts = installCosts;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPaymentName() {
		if(payment != null){
			return SysStaticDataUtil.getSysStaticData("PAYMENT_TYPE", payment).getCodeName();
		}
		return "";
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
