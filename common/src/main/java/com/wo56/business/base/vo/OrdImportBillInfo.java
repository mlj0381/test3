package com.wo56.business.base.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OrdImportBillInfo implements Serializable{
	private String fhdh;//订单号
	private String artNo;//货号
	private String consignor;
	private String consignorPhone;
	private String fridges;//货品
	private String number;//件数
	private String volume;//体积（方）
	private String volumeNum;//体积单价
	private String weight;//重量
	private String weightNum;//重量单价
	private String payment;//付款方式
	private String deliveryType;//交接方式
	private String serviceType;//家装服务
	private String installCosts;//配安费
	private String freight;//运费
	private String consignee;//收货人
	private String consigneePhone;//收货人手机
	private String alternativePhone;//备用电话
	private String recProvinces;//收货人省
	private String recCity;//收货人市
	private String recDistrict;//收货人区
	private String address;//详细地址
	private String remarks;//备注
	
	public OrdImportBillInfo(){}
	
	public OrdImportBillInfo(String fhdh, String artNo, String consignor,
			String consignorPhone, String fridges, String number,
			String volume, String volumeNum, String weight, String weightNum,
			String payment, String deliveryType, String serviceType,
			String installCosts, String freight, String consignee,
			String consigneePhone, String alternativePhone,
			String recProvinces, String recCity, String recDistrict,
			String address, String remarks) {
		super();
		this.fhdh = fhdh;
		this.artNo = artNo;
		this.consignor = consignor;
		this.consignorPhone = consignorPhone;
		this.fridges = fridges;
		this.number = number;
		this.volume = volume;
		this.volumeNum = volumeNum;
		this.weight = weight;
		this.weightNum = weightNum;
		this.payment = payment;
		this.deliveryType = deliveryType;
		this.serviceType = serviceType;
		this.installCosts = installCosts;
		this.freight = freight;
		this.consignee = consignee;
		this.consigneePhone = consigneePhone;
		this.alternativePhone = alternativePhone;
		this.recProvinces = recProvinces;
		this.recCity = recCity;
		this.recDistrict = recDistrict;
		this.address = address;
		this.remarks = remarks;
	}

	public String getFhdh() {
		return fhdh;
	}

	public String getArtNo() {
		return artNo;
	}

	public String getConsignor() {
		return consignor;
	}

	public String getConsignorPhone() {
		return consignorPhone;
	}
	public String getFridges() {
		return fridges;
	}
	public String getNumber() {
		return number;
	}
	public String getVolume() {
		return volume;
	}

	public String getVolumeNum() {
		return volumeNum;
	}

	public String getWeight() {
		return weight;
	}

	public String getWeightNum() {
		return weightNum;
	}

	public String getPayment() {
		return payment;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public String getServiceType() {
		return serviceType;
	}
	public String getInstallCosts() {
		return installCosts;
	}
	public String getFreight() {
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
	public String getRecProvinces() {
		return recProvinces;
	}
	public String getRecCity() {
		return recCity;
	}
	public String getRecDistrict() {
		return recDistrict;
	}
	public String getAddress() {
		return address;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setFhdh(String fhdh) {
		this.fhdh = fhdh;
	}
	public void setArtNo(String artNo) {
		this.artNo = artNo;
	}
	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}
	public void setConsignorPhone(String consignorPhone) {
		this.consignorPhone = consignorPhone;
	}
	public void setFridges(String fridges) {
		this.fridges = fridges;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}

	public void setVolumeNum(String volumeNum) {
		this.volumeNum = volumeNum;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public void setWeightNum(String weightNum) {
		this.weightNum = weightNum;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public void setInstallCosts(String installCosts) {
		this.installCosts = installCosts;
	}
	public void setFreight(String freight) {
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
	public void setRecProvinces(String recProvinces) {
		this.recProvinces = recProvinces;
	}
	public void setRecCity(String recCity) {
		this.recCity = recCity;
	}
	public void setRecDistrict(String recDistrict) {
		this.recDistrict = recDistrict;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
