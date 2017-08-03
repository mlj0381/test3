package com.wo56.business.org.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;

public class OrgUpdatePullPagParamIn implements IParamIn {

	// 组织id
	private long orgId;
	// 公司编码
	private String tenantCode;
	// 公司名称
	private String name;
	// 法人
	private String linkMan;
	// 联系人电话
	private String linkPhone;
	// 客服电话
	private String csPhones;
	// 公司地址
	private String address;
	// 法人身份证正面图片id
	private String corporateFrontCardId;
	// 法人身份证反面图片id
	private String corporatebackCardId;
	// 公司营业执照图片id
	private String businesslicensePicId;
	// 公司简介
	private String sellerNotes;
	private String inCode;
	private Long tenantId;
	

	 

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getInCode() {
		return inCode;
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

	public String getCorporateFrontCardId() {
		return corporateFrontCardId;
	}

	public void setCorporateFrontCardId(String corporateFrontCardId) {
		this.corporateFrontCardId = corporateFrontCardId;
	}

	public String getCorporatebackCardId() {
		return corporatebackCardId;
	}

	public void setCorporatebackCardId(String corporatebackCardId) {
		this.corporatebackCardId = corporatebackCardId;
	}

	public String getBusinesslicensePicId() {
		return businesslicensePicId;
	}

	public void setBusinesslicensePicId(String businesslicensePicId) {
		this.businesslicensePicId = businesslicensePicId;
	}

	public String getSellerNotes() {
		return sellerNotes;
	}

	public void setSellerNotes(String sellerNotes) {
		this.sellerNotes = sellerNotes;
	}

}
