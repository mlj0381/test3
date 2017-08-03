package com.wo56.business.org.vo.out;

import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class OrgSavePullPagParamOut extends BaseOutParamVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2551616271761400069L;
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
	// 公司简介
	private String sellerNotes;
	//private Date createDate;
	private String createName;
	private String orgId;
	private String corporateBackCard;
	private String corporateFrontCard;
	private String businesslicensePic;
	//private Long tenantId;
	
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
	public String getSellerNotes() {
		return sellerNotes;
	}
	public void setSellerNotes(String sellerNotes) {
		this.sellerNotes = sellerNotes;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getCorporateBackCard() {
		return corporateBackCard;
	}
	public void setCorporateBackCard(String corporateBackCard) {
		this.corporateBackCard = corporateBackCard;
	}
	public String getCorporateFrontCard() {
		return corporateFrontCard;
	}
	public void setCorporateFrontCard(String corporateFrontCard) {
		this.corporateFrontCard = corporateFrontCard;
	}
	public String getBusinesslicensePic() {
		return businesslicensePic;
	}
	public void setBusinesslicensePic(String businesslicensePic) {
		this.businesslicensePic = businesslicensePic;
	}
	 
	
	
	
  
	

}
