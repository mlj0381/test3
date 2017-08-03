package com.wo56.business.ord.vo.out;

import com.framework.core.inter.vo.OutParamVO;

public class OrdPickDetailOutParam extends OutParamVO {

	private String address;
	private String consignorBill;
	private Long consignorId;
	private String consignorName;
	private int isPickup;
	private Long idNo;
	private String planPickupTime;
	private String provinceName;
	private String cityName;
	private String countyName;
	private String streetName;
	private Long provinceId;
	private Long cityId;
	private Long countyId;
	private Long streetId;
	private String color;
	private String code;
	private String standard;
	private Integer count;
	private String product;
	private String latitude;
	private String longitude;
	
	
	
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
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public Long getCountyId() {
		return countyId;
	}
	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
	public Long getStreetId() {
		return streetId;
	}
	public void setStreetId(Long streetId) {
		this.streetId = streetId;
	}
	public Long getConsignorId() {
		return consignorId;
	}
	public void setConsignorId(Long consignorId) {
		this.consignorId = consignorId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public int getIsPickup() {
		return isPickup;
	}
	public void setIsPickup(int isPickup) {
		this.isPickup = isPickup;
	}
	public Long getIdNo() {
		return idNo;
	}
	public void setIdNo(Long idNo) {
		this.idNo = idNo;
	}
	public String getPlanPickupTime() {
		return planPickupTime;
	}
	public void setPlanPickupTime(String planPickupTime) {
		this.planPickupTime = planPickupTime;
	}
	
	
	
}