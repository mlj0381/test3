package com.wo56.business.cm.vo.in;

import java.util.Date;

import com.framework.core.svcaller.interfaces.IParamIn;

public class CmContractQueryIn implements IParamIn{
	@Override
	public String getInCode() {
		return this.inCode;
	}
	public CmContractQueryIn(String inCode) {
		super();
		this.inCode = inCode;
	}
	private String inCode;
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	private long id;
	private String name;
	private String linkmanName;
	private String telephone;
	private String bill;
	private String address;
	private Long orgId;
	private Integer type;
	private Long parentId;
	private Integer state;
	private Date createDate;
	private Integer signingType;
	private String orgName;
	private Long tenantId;
	
	
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkmanName() {
		return linkmanName;
	}
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getSigningType() {
		return signingType;
	}
	public void setSigningType(Integer signingType) {
		this.signingType = signingType;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
