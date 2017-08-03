package com.wo56.business.org.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class OrgQueryAllBusOut extends BaseOutParamVO{
	private Long tenantId;
	private String tenantCode;
	private String name;
	private String linkMan;
	private String linkPhone;
	private String busiNotes;
	private Integer businessType;
	private String address;
	private String sellerNotes;
	private Long orgId;
	private String orgName;
	private Long lineOrgId;
	private String provinceId;
	private String regionId;
	private String depaetmentAdderss;
	private String countyId;
	private String streetId;
	private String lineOrgName;
	
	
	public String getLineOrgName() {
		return lineOrgName;
	}
	public void setLineOrgName(String lineOrgName) {
		this.lineOrgName = lineOrgName;
	}
	public String getCountyId() {
		return countyId;
	}
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}
	public String getStreetId() {
		return streetId;
	}
	public void setStreetId(String streetId) {
		this.streetId = streetId;
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
	public Long getLineOrgId() {
		return lineOrgId;
	}
	public void setLineOrgId(Long lineOrgId) {
		this.lineOrgId = lineOrgId;
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
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
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
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
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
