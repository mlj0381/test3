package com.wo56.business.org.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;

public class OrgQueryAllBusIn implements IParamIn{

	@Override
	public String getInCode() {
		return this.inCode;
	}
	private String inCode;
	public OrgQueryAllBusIn(String inCode){
		super();
		this.inCode = inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	private Long tenantId;
	private String tenantCode;
	private String name;
	private String linkMan;
	private String linkPhone;
	private String busiNotes;
	private Integer businessType;
	private String address;
	private String sellerNotes;
	private String provinceId;
	private String regionId;
	
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
