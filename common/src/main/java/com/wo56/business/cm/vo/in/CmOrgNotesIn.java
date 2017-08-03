package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;

public class CmOrgNotesIn implements IParamIn{
	public CmOrgNotesIn(String inCode){
		super();
		this.inCode = inCode;
	}
	@Override
	public String getInCode() {
		return this.inCode;
	}
	private String inCode;
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	
	private Long RId;
	private Long orgId;
	private Long tenantId;
	private String notes;
	private Integer sortId;
	private String orgName;
	public Long getRId() {
		return RId;
	}
	public void setRId(Long rId) {
		RId = rId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getSortId() {
		return sortId;
	}
	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
