package com.wo56.business.org.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class OrgQueryBusOut extends BaseOutParamVO{
		// 商家ID
		private Long tenantId;
		// 组织id
		private Long orgId;
		// 商家编码
		private String tenantCode;
		// 商家名称
		private String name;
		// 商家联系人
		private String linkMan;
		// 商家联系人电话
		private String linkPhone;
		// 主营业务
		private String busiNotes;
		// 服务类型
		private String businessType;
		// 提货地址
		private String address;
		// 商家简介
		private String sellerNotes;
		private String provinceId;
		private String depaetmentAdderss;
		private String orgName;
		private String sfOrgName;
		private String regionName;
		private String regionId;
		private String countyName;
		private String countyId;
		/**起始省份名**/
		private String provinceName;
		private String streetId;
		private long lineOrgId;
		private String lineOrgName;
		private long relId;
		
		private String loginAcct;
		private Integer isAuth;
		private String isAuthName;
		
		
		
		
		
		
		
		public String getIsAuthName() {
			return isAuthName;
		}
		public void setIsAuthName(String isAuthName) {
			this.isAuthName = isAuthName;
		}
		public Integer getIsAuth() {
			return isAuth;
		}
		public void setIsAuth(Integer isAuth) {
			this.isAuth = isAuth;
		}
		public String getLoginAcct() {
			return loginAcct;
		}
		public void setLoginAcct(String loginAcct) {
			this.loginAcct = loginAcct;
		}
		public long getRelId() {
			return relId;
		}
		public void setRelId(long relId) {
			this.relId = relId;
		}
		public long getLineOrgId() {
			return lineOrgId;
		}
		public void setLineOrgId(long lineOrgId) {
			this.lineOrgId = lineOrgId;
		}
		public String getLineOrgName() {
			return lineOrgName;
		}
		public void setLineOrgName(String lineOrgName) {
			this.lineOrgName = lineOrgName;
		}
		public String getStreetId() {
			return streetId;
		}
		public void setStreetId(String streetId) {
			this.streetId = streetId;
		}
		public String getRegionName() {
			return regionName;
		}
		public void setRegionName(String regionName) {
			this.regionName = regionName;
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
		public String getProvinceName() {
			return provinceName;
		}
		public void setProvinceName(String provinceName) {
			this.provinceName = provinceName;
		}
		public String getSfOrgName() {
			return sfOrgName;
		}
		public void setSfOrgName(String sfOrgName) {
			this.sfOrgName = sfOrgName;
		}
		public String getOrgName() {
			return orgName;
		}
		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}
		public String getDepaetmentAdderss() {
			return depaetmentAdderss;
		}
		public void setDepaetmentAdderss(String depaetmentAdderss) {
			this.depaetmentAdderss = depaetmentAdderss;
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
		public Long getTenantId() {
			return tenantId;
		}
		public void setTenantId(Long tenantId) {
			this.tenantId = tenantId;
		}
		public Long getOrgId() {
			return orgId;
		}
		public void setOrgId(Long orgId) {
			this.orgId = orgId;
		}
		public String getTenantCode() {
			return tenantCode;
		}
		public void setTenantCode(String tenantCode) {
			this.tenantCode = tenantCode;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
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
		public String getBusiNotes() {
			return busiNotes;
		}
		public void setBusiNotes(String busiNotes) {
			this.busiNotes = busiNotes;
		}
		public String getBusinessType() {
			return businessType;
		}
		public void setBusinessType(String businessType) {
			this.businessType = businessType;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getSellerNotes() {
			return sellerNotes;
		}
		public void setSellerNotes(String sellerNotes) {
			this.sellerNotes = sellerNotes;
		}
		
}
