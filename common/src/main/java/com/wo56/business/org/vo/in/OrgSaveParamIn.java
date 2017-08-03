package com.wo56.business.org.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;
/**
 * @author zjy
 * （1）必传 （0）选传
 * */
public class OrgSaveParamIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.BASE.SAVE_ORG;
	}
	private long orgId;
    /**网点名称 (1)*/
	private String orgName;
	/**网点简称 (1)*/
	private String orgShorter;
	/***父级网点 （1）*/
	private Long parentOrgId;
	/**省份id （1）*/
	private String provinceId;
	/**城市id （1）*/
	private String regionId;
	/**县区id （1）*/
	private String countyId;
	/**营业部详细地址（1）*/
	private String departmentAddress;
	/**经度（0）*/
	private String longitude;
	/**纬度 （0）*/
	private String latitude;
	/**接货电话（0）*/
	private String acceptLinkPhone;
	/**提货电话（0）*/
	private String carryLinkPhone;
	/**网点经理（1）*/
	private String orgManager;
	/***网点类型 （1）*/
	private Integer orgType;
	/**网点负责人（0）*/
	private String orgPrincipal;
	/**网点负责人手机（0）*/
	private String orgPrincipalPhone;
	/***传真（0）*/
	private String fax;
	/**客服人员1（0）*/
	private String supportStaff;
	/**客服电话1（0）*/
	private String supportStaffPhone;
	
	/**客服人员2（0） */
	private String supportStaff2;
	/**客服电话2（0）*/
	private String supportStaffPhone2;
	/***经营类型（0）1 直营 2 加盟*/
	private Integer businessType;
	/**是否营业部门 1 是 2 否*/
	private int isDepartment;
	/**是否代收货款 1 代收 2 非代收**/
	private Integer isPaymentCollection;
	/**代收款限额 (1)*/
	private Long limitForCollection;
	/**是否结算到付单 1 是 2 否*/
	private Integer isSettleDocket;
	/**本位币类型 (1)*/
	private Integer currencyType;
	/**提现基准 (1)*/
	private String casReference;
	/**锁机额度 (1)*/
	private Long lockLimit;
	/**预警额度（1）*/
	private Long warningLimit;
	/**管理费（1）*/
	private Integer managementCos;
	/***备注*/
	private String remark;
	private String streetName;
	private String streetId;
	
	
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getStreetId() {
		return streetId;
	}
	public void setStreetId(String streetId) {
		this.streetId = streetId;
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
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
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
	
	
}
