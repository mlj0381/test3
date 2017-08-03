package com.wo56.business.org.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class OrganizationParamOut extends BaseOutParamVO{
	
	private Long orgId;
	private String orgName;
	private String orgShorter;
	private Long parentOrgId;
	private String provinceId;
	private String regionId;
	private String countyId;
	private String departmentAddress;
	private String longitude;
	private String latitude;
	private String acceptLinkPhone;
	private String carryLinkPhone;
	private String orgManager;
	private Integer orgType;
	private String orgPrincipal;
	private String orgPrincipalPhone;
	private String fax;
	private String supportStaff;
	private String supportStaffPhone;
	private String supportStaff2;
	private String supportStaffPhone2;
	private Integer businessType;
	private Integer isDepartment;
	private Integer isPaymentCollection;
	private Long limitForCollection;
	private Integer isSettleDocket;
	private Integer currencyType;
	private String casReference;
	private Long lockLimit;
	private Long warningLimit;
	private Integer managementCos;
	private String remark;
	//private Long tenantId;
	private String businessTypeName;
	private String orgTypeName;
	private String currencyTypeName;
	private String provinceName;
	private String countyName;
	private String regionName;
	public String getSupportStaff2() {
		return supportStaff2;
	}
	public void setSupportStaff2(String supportStaff2) {
		this.supportStaff2 = supportStaff2;
	}
	public String getSupportStaffPhone2() {
		return supportStaffPhone2;
	}
	public void setSupportStaffPhone2(String supportStaffPhone2) {
		this.supportStaffPhone2 = supportStaffPhone2;
	}
	private String streetId;
	private String streetName;
	
	public String getStreetId() {
		return streetId;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgShorter() {
		return orgShorter;
	}
	public void setOrgShorter(String orgShorter) {
		this.orgShorter = orgShorter;
	}
	public Long getParentOrgId() {
		return parentOrgId;
	}
	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
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
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAcceptLinkPhone() {
		return acceptLinkPhone;
	}
	public void setAcceptLinkPhone(String acceptLinkPhone) {
		this.acceptLinkPhone = acceptLinkPhone;
	}
	public String getCarryLinkPhone() {
		return carryLinkPhone;
	}
	public void setCarryLinkPhone(String carryLinkPhone) {
		this.carryLinkPhone = carryLinkPhone;
	}
	public String getOrgManager() {
		return orgManager;
	}
	public void setOrgManager(String orgManager) {
		this.orgManager = orgManager;
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
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getSupportStaff() {
		return supportStaff;
	}
	public void setSupportStaff(String supportStaff) {
		this.supportStaff = supportStaff;
	}
	public String getSupportStaffPhone() {
		return supportStaffPhone;
	}
	public void setSupportStaffPhone(String supportStaffPhone) {
		this.supportStaffPhone = supportStaffPhone;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public Integer getIsDepartment() {
		return isDepartment;
	}
	public void setIsDepartment(Integer isDepartment) {
		this.isDepartment = isDepartment;
	}
	public Integer getIsPaymentCollection() {
		return isPaymentCollection;
	}
	public void setIsPaymentCollection(Integer isPaymentCollection) {
		this.isPaymentCollection = isPaymentCollection;
	}
	public Long getLimitForCollection() {
		return limitForCollection;
	}
	public void setLimitForCollection(Long limitForCollection) {
		this.limitForCollection = limitForCollection;
	}
	public Integer getIsSettleDocket() {
		return isSettleDocket;
	}
	public void setIsSettleDocket(Integer isSettleDocket) {
		this.isSettleDocket = isSettleDocket;
	}
	public Integer getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(Integer currencyType) {
		this.currencyType = currencyType;
	}
	public String getCasReference() {
		return casReference;
	}
	public void setCasReference(String casReference) {
		this.casReference = casReference;
	}
	public Long getLockLimit() {
		return lockLimit;
	}
	public void setLockLimit(Long lockLimit) {
		this.lockLimit = lockLimit;
	}
	public Long getWarningLimit() {
		return warningLimit;
	}
	public void setWarningLimit(Long warningLimit) {
		this.warningLimit = warningLimit;
	}
	public Integer getManagementCos() {
		return managementCos;
	}
	public void setManagementCos(Integer managementCos) {
		this.managementCos = managementCos;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public String getBusinessTypeName() {
		return businessTypeName;
	}
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
	public String getOrgTypeName() {
		return orgTypeName;
	}
	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}
	public String getCurrencyTypeName() {
		return currencyTypeName;
	}
	public void setCurrencyTypeName(String currencyTypeName) {
		this.currencyTypeName = currencyTypeName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

}
