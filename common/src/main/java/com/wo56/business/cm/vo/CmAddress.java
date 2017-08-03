package com.wo56.business.cm.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class CmAddress implements Serializable {
	private Long id;
	private String linkman;
	private String bill;
	private Long province;
	private String provinceName;
	private Long city;
	private String cityName;
	private Long district;
	private String districtName;
	private Long street;
	private String streetName;
	private String address;
	private Integer merchantAddressType;
	private Long orgId;
	private Long tenantId;
	private Long opId;

	private Date createTime;
	private String nand;
	private String eand;
	private Integer adderssDefault;
	private int state;
	private Long userId;
	private Integer commonAddress;
	

	public Integer getCommonAddress() {
		return commonAddress;
	}

	public void setCommonAddress(Integer commonAddress) {
		this.commonAddress = commonAddress;
	}

	public CmAddress() {
	}

	public CmAddress(Long id, String linkman, String bill, Long province, String provinceName, Long city,
			String cityName, Long district, String districtName, Long street, String streetName, String address,
			Integer merchantAddressType, Long orgId, Long tenantId, Long opId, Date createTime, String nand,
			String eand, Integer adderssDefault, int state, Long userId) {
		super();
		this.id = id;
		this.linkman = linkman;
		this.bill = bill;
		this.province = province;
		this.provinceName = provinceName;
		this.city = city;
		this.cityName = cityName;
		this.district = district;
		this.districtName = districtName;
		this.street = street;
		this.streetName = streetName;
		this.address = address;
		this.merchantAddressType = merchantAddressType;
		this.orgId = orgId;
		this.tenantId = tenantId;
		this.opId = opId;
		this.createTime = createTime;
		this.nand = nand;
		this.eand = eand;
		this.adderssDefault = adderssDefault;
		this.state = state;
		this.userId = userId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public Long getProvince() {
		return province;
	}

	public void setProvince(Long province) {
		this.province = province;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getDistrict() {
		return district;
	}

	public void setDistrict(Long district) {
		this.district = district;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Long getStreet() {
		return street;
	}

	public void setStreet(Long street) {
		this.street = street;
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

	public Integer getMerchantAddressType() {
		return merchantAddressType;
	}

	public void setMerchantAddressType(Integer merchantAddressType) {
		this.merchantAddressType = merchantAddressType;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNand() {
		return nand;
	}

	public void setNand(String nand) {
		this.nand = nand;
	}

	public String getEand() {
		return eand;
	}

	public void setEand(String eand) {
		this.eand = eand;
	}

	public Integer getAdderssDefault() {
		return adderssDefault;
	}

	public void setAdderssDefault(Integer adderssDefault) {
		this.adderssDefault = adderssDefault;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
