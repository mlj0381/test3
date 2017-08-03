package com.wo56.business.org.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;


public class OrganizationQueryZXOut extends BaseOutParamVO{
	/**租户ID**/
	//private Long tenantId;
	/**租户编码**/
	private String tenantCode;
	/**租户名称**/
	private String name;
	/**起始省份ID**/
	private long provinceId;
	/**起始省份名**/
	private String provinceName;
	/**目的省份ID**/
	private String provinceIds;
	/**目的省份名**/
	private String provinceNames;
	/**租户联系人**/
	private String linkMan;
	/**租户联系人电话**/
	private String linkPhone;
	/**客服电话**/
	private String csPhones;
	/**专线地址**/
	private String address;
	/**orgId*/
	private String orgId;
	private String regionName;
	private String regionId;
	private String countyName;
	private String countyId;
	private String departmentAddress;
	private String orgName;
	private Integer orgType;
	private String orgPrincipal;
	private String orgPrincipalPhone;
	private String carryLinkPhone;
	private String streetId;
	
	public String getStreetId() {
		return streetId;
	}
	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}
	public String getCarryLinkPhone() {
		return carryLinkPhone;
	}
	public void setCarryLinkPhone(String carryLinkPhone) {
		this.carryLinkPhone = carryLinkPhone;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	public String getOrgPrincipal() {
		return orgPrincipal;
	}
	public void setOrgPrincipal(String orgPrincipal) {
		this.orgPrincipal = orgPrincipal;
	}
	public String getOrgPrincipalPhone() {
		return orgPrincipalPhone;
	}
	public void setOrgPrincipalPhone(String orgPrincipalPhone) {
		this.orgPrincipalPhone = orgPrincipalPhone;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getCountyId() {
		return countyId;
	}
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}
	public String getDepartmentAddress() {
		return departmentAddress;
	}
	public void setDepartmentAddress(String departmentAddress) {
		this.departmentAddress = departmentAddress;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getTenantCode() {
		return tenantCode;
	}
	public String getName() {
		return name;
	}
	public long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getProvinceNames() {
		return provinceNames;
	}
	public void setProvinceNames(String provinceNames) {
		this.provinceNames = provinceNames;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvinceIds() {
		return provinceIds;
	}
	public void setProvinceIds(String provinceIds) {
		this.provinceIds = provinceIds;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getCsPhones() {
		return csPhones;
	}
	public void setCsPhones(String csPhones) {
		this.csPhones = csPhones;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
