package com.wo56.business.org.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

/**
 * @author 邱林锋
 * 
 * */
public class OrgQueryBusParamIn extends PageInParamVO implements IParamIn {
	
	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return this.inCode;
	}

	public OrgQueryBusParamIn(String inCode) {
		super();
		this.inCode = inCode;
	}

	private String inCode;

	public void setInCode(String inCode) {
		this.inCode = inCode;
	}

	// 商家ID
	private long tenantId;
	// 组织id
	private long orgId;
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
	private String regionId;
	private String depaetmentAdderss;
	private String regionName;
	private String countyName;
	private String countyId;
	private String departmentAddress;
	private String orgName;
	private Integer orgType;
	private String orgPrincipal;
	private String orgPrincipalPhone;
	private String carryLinkPhone;
	private Long relId;
	
	public Long getRelId() {
		return relId;
	}

	public void setRelId(Long relId) {
		this.relId = relId;
	}

	public String getCarryLinkPhone() {
		return carryLinkPhone;
	}

	public void setCarryLinkPhone(String carryLinkPhone) {
		this.carryLinkPhone = carryLinkPhone;
	}

	public String getDepaetmentAdderss() {
		return depaetmentAdderss;
	}

	public void setDepaetmentAdderss(String depaetmentAdderss) {
		this.depaetmentAdderss = depaetmentAdderss;
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

	public String getDepartmentAddress() {
		return departmentAddress;
	}

	public void setDepartmentAddress(String departmentAddress) {
		this.departmentAddress = departmentAddress;
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

	public long getTenantId() {
		return tenantId;
	}

	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
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
