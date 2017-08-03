package com.wo56.business.org.vo.out;

import org.apache.commons.lang.StringUtils;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.framework.core.util.SysStaticDataUtil;
/**
 * @author zjy
 * （1）必传 （0）选传
 * */
public class OrgQueryParamOut extends BaseOutParamVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7378073835710523506L;
	 /**网点名称 (1)*/
		private String orgName;
		
		private String orgCode;
		//公司编码
		private String tenantCode;
		public String getOrgCode() {
			return orgCode;
		}
		public void setOrgCode(String orgCode) {
			this.orgCode = orgCode;
		}
		public String getTenantCode() {
			return tenantCode;
		}
		public void setTenantCode(String tenantCode) {
			this.tenantCode = tenantCode;
		}
		/**网点名称 (1)*/
		private long orgId;
		public long getOrgId() {
			return orgId;
		}
		public void setOrgId(long orgId) {
			this.orgId = orgId;
		}
		/**网点简称 (1)*/
		private String orgShorter;
		/***父级网点 （1）*/
		private String parentOrgName;
		/**省份name （1）*/
		private String ProvinceIdName;
		/**城市anme （1）*/
		private String regionName;
		/**县区name（1）*/
		private String countyName;
		/**营业部详细地址（1）*/
		private String departmentAddress;
		/**接货电话（0）*/
		private String acceptLinkPhone;
		/**提货电话（0）*/
		private String carryLinkPhone;
		/***网点类型 （1）*/
		private String orgTypeName;
		/**网点负责人（0）*/
		private String orgPrincipal;
		/**网点负责人手机（0）*/
		private String orgPrincipalPhone;
		/***经营类型（0）1 直营 2 加盟*/
		private String businessTypeName;
		/**是否营业部门 1 是 2 否*/
		private Integer isDepartment;
		/**是否代收货款 1 代收 2 非代收**/
		private Integer isPaymentCollection;
		/**代收款限额 (1)*/
		private Long limitForCollection;
		/**是否结算到付单 1 是 2 否*/
		private Integer isSettleDocket;
		/**本位币类型 (1)*/
		private String currencyTypeName;
		/**提现基准 (1)*/
		private String cashReference;
		/**锁机额度 (1)*/
		private Long lockLimit;
		/**预警额度（1）*/
		private Long warningLimit;
		/***备注*/
		private String remark;
		private String provinceId;
		private String countyId;
		private String regionId;
		private String provinceName;
		private String streetId;
		private String streetName;
		private String address;
		private String isDepartmentName;
		private String orgManager;
		private String supportStaff;
		private String supportStaffPhone;
		private String fax;
		private String supportStaff2;
		private String supportStaffPhone2;
		
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
		public String getProvinceIdName() {
			return ProvinceIdName;
		}
		public void setProvinceIdName(String provinceIdName) {
			ProvinceIdName = provinceIdName;
		}
		public String getOrgManager() {
			return orgManager;
		}
		public void setOrgManager(String orgManager) {
			this.orgManager = orgManager;
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
		public String getFax() {
			return fax;
		}
		public void setFax(String fax) {
			this.fax = fax;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public String getIsDepartmentName() {
			return getIsDepartment() == 1 ? "是" : "否" ;
		}
		public void setIsDepartmentName(String isDepartmentName) {
			this.isDepartmentName = isDepartmentName;
		}
		public String getAddress() {
			return (getProvinceName() + getRegionName() + getCountyName() + getStreetName());
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getStreetId() {
			return streetId;
		}
		public void setStreetId(String streetId) {
			this.streetId = streetId;
		}
		public String getStreetName() {
			try {
				if(StringUtils.isEmpty(getStreetId())|| Long.parseLong(getStreetId()) <= 0){
					return "";
				}
				return SysStaticDataUtil.getStreetDataList("SYS_STREET", getStreetId()).getName();
			} catch (Exception e) {
				return "";
			}
			
		
		}
		public void setStreetName(String streetName) {
			this.streetName = streetName;
		}
		public String getProvinceId() {
			return provinceId;
		}
		public void setProvinceId(String provinceId) {
			this.provinceId = provinceId;
		}
		public String getCountyId() {
			return countyId;
		}
		public void setCountyId(String countyId) {
			this.countyId = countyId;
		}
		public String getRegionId() {
			return regionId;
		}
		public void setRegionId(String regionId) {
			this.regionId = regionId;
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
		public String getParentOrgName() {
			return parentOrgName;
		}
		public void setParentOrgName(String parentOrgName) {
			this.parentOrgName = parentOrgName;
		}
		public String getProvinceName() {
			if(StringUtils.isEmpty(getProvinceId()) ||  Long.parseLong(getProvinceId()) <= 0){
				return "";
			}
			return SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", getProvinceId()).getName();
		}
		public void setProvinceName(String provinceName) {
			this.provinceName = provinceName;
		}
		public String getRegionName() {
			if(StringUtils.isEmpty(getRegionId()) || Long.parseLong(getRegionId()) <= 0){
				return "";
			}
			return SysStaticDataUtil.getCityDataList("SYS_CITY", getRegionId()).getName();
		}
		public void setRegionName(String regionName) {
			this.regionName = regionName;
		}
		public String getCountyName() {
			if(StringUtils.isEmpty(getCountyId()) ||  Long.parseLong(getCountyId()) <= 0){
				return "";
			}
			return SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", getCountyId()).getName();
		}
		public void setCountyName(String countyName) {
			this.countyName = countyName;
		}
		public String getDepartmentAddress() {
			return departmentAddress;
		}
		public void setDepartmentAddress(String departmentAddress) {
			this.departmentAddress = departmentAddress;
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
		public String getOrgTypeName() {
			return orgTypeName;
		}
		public void setOrgTypeName(String orgTypeName) {
			this.orgTypeName = orgTypeName;
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
		public String getBusinessTypeName() {
			return businessTypeName;
		}
		public void setBusinessTypeName(String businessTypeName) {
			this.businessTypeName = businessTypeName;
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
		public String getCurrencyTypeName() {
			return currencyTypeName;
		}
		public void setCurrencyTypeName(String currencyTypeName) {
			this.currencyTypeName = currencyTypeName;
		}
		public String getCashReference() {
			return cashReference;
		}
		public void setCashReference(String cashReference) {
			this.cashReference = cashReference;
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
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}

		
}
