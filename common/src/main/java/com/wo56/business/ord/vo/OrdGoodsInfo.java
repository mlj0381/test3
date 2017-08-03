package com.wo56.business.ord.vo;

import java.util.Date;

import com.framework.core.base.POJO;
import com.framework.core.util.SysStaticDataUtil;

/**
 * OrdGoodsInfo entity. @author MyEclipse Persistence Tools
 */

public class OrdGoodsInfo extends POJO implements java.io.Serializable {

	// Fields

	private Long idNo;
	private Long orderId;
	private String trackingNum;
	private Long provinceId;
	private Long cityId;
	private Long countyId;
	private Long streetId;
	private Long consignorId;
	private String consignorName;
	private String consignorBill;
	private String address;
	private String product;
	private Integer count;
	private String weight;
	private String volume;
	private String color;
	private String standard;
	private Date planPickupTime;
	private String pickupSlot;
	private Date pickupTime;
	private String lon;
	private String lat;
	//private Date createDate;
	private Long createId;
	private Date opDate;
	//private Long opId;
	private String remark;
	private String orderNo;
	private String code;
	private Integer isPickup;
	private String provineName;
	private String cityName;
	private String countyName;
	private String streetName;
	private Integer reminderCount;
	private Date reminderTime;
	
	
	
	
	
	// Constructors

	public Date getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(Date reminderTime) {
		this.reminderTime = reminderTime;
	}

	public Integer getReminderCount() {
		return reminderCount;
	}

	public void setReminderCount(Integer reminderCount) {
		this.reminderCount = reminderCount;
	}

	public String getProvineName() {
		if(provinceId!=null){
			setProvineName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(provinceId)).getName());
		}
		return provineName;
	}

	public void setProvineName(String provineName) {
		this.provineName = provineName;
	}

	public String getCityName() {
		if(cityId!=null)
		setCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(cityId)).getName());
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		if(countyId!=null)
			setCountyName(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(countyId)).getName());
		return countyName;
	}

	public void setCountyName(String countyName) {
		
		this.countyName = countyName;
	}

	public String getStreetName() {
		if(streetId!=null)
			setCountyName(SysStaticDataUtil.getStreetDataList("SYS_STREET", String.valueOf(streetId)).getName());
		return streetName;
	}
	// Constructors

	/** default constructor */
	public OrdGoodsInfo() {
	}

	public Long getIdNo() {
		return idNo;
	}

	public void setIdNo(Long idNo) {
		this.idNo = idNo;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getTrackingNum() {
		return trackingNum;
	}

	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
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

	public String getConsignorName() {
		return consignorName;
	}

	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}

	public String getConsignorBill() {
		return consignorBill;
	}

	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public Date getPlanPickupTime() {
		return planPickupTime;
	}

	public void setPlanPickupTime(Date planPickupTime) {
		this.planPickupTime = planPickupTime;
	}

	public String getPickupSlot() {
		return pickupSlot;
	}

	public void setPickupSlot(String pickupSlot) {
		this.pickupSlot = pickupSlot;
	}

	public Date getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getIsPickup() {
		return isPickup;
	}

	public void setIsPickup(Integer isPickup) {
		this.isPickup = isPickup;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	
}