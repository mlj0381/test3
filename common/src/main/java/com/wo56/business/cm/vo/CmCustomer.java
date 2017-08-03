package com.wo56.business.cm.vo;

import java.util.Date;

import com.framework.core.base.POJO;
import com.wo56.common.utils.OraganizationCacheDataUtil;

/**
 * CmCustomer entity. @author MyEclipse Persistence Tools
 */

public class CmCustomer extends POJO {

	// Fields

	private long id;
	private String name;
	private String linkmanName;
	private String telephone;
	private String bill;
	private String address;
	private Long orgId;
	//private Long tenantId;
	private Integer type;
	private Long parentId;
	private Integer state;
	//private Date createDate;
	private Integer signingType;
	private String orgName;
	private Long zxTenantId;
	
	// Constructors

	/** default constructor */
	public CmCustomer() {
	}

	/** full constructor */
	public CmCustomer(long id, String name, String linkmanName,
			String telephone, String bill, String address, Long orgId,
			Long tenantId, Integer type, Long parentId) {
		this.id = id;
		this.name = name;
		this.linkmanName = linkmanName;
		this.telephone = telephone;
		this.bill = bill;
		this.address = address;
		this.orgId = orgId;
		this.tenantId = tenantId;
		this.type = type;
		this.parentId = parentId;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLinkmanName() {
		return this.linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBill() {
		return this.bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOrgName() {
		if(orgId != null && orgId>0){
			setOrgName(OraganizationCacheDataUtil.getOrgName(orgId));
		}
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getSigningType() {
		return signingType;
	}

	public void setSigningType(Integer signingType) {
		this.signingType = signingType;
	}

	public Long getZxTenantId() {
		return zxTenantId;
	}

	public void setZxTenantId(Long zxTenantId) {
		this.zxTenantId = zxTenantId;
	}
}