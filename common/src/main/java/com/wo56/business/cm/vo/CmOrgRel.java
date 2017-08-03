package com.wo56.business.cm.vo;

import java.util.Date;

/**
 * CmOrgRel entity. @author MyEclipse Persistence Tools
 */

public class CmOrgRel implements java.io.Serializable {

	private Long relId;
	private Long lineOrgId;
	private Long lineTenantId;
	private Long busiOrgId;
	private Long busiTenantId;
	private Integer state;
	private Long opId;
	private Date opDate;
	private String opName;
	private Date createDate;


	public CmOrgRel() {
	}

	public CmOrgRel(Long lineOrgId, Long lineTenantId, Long busiOrgId,
			Long busiTenantId, Integer state) {
		this.lineOrgId = lineOrgId;
		this.lineTenantId = lineTenantId;
		this.busiOrgId = busiOrgId;
		this.busiTenantId = busiTenantId;
		this.state = state;
	}

	public CmOrgRel(Long lineOrgId, Long lineTenantId, Long busiOrgId,
			Long busiTenantId, Integer state, Long opId, Date opDate,
			String opName) {
		this.lineOrgId = lineOrgId;
		this.lineTenantId = lineTenantId;
		this.busiOrgId = busiOrgId;
		this.busiTenantId = busiTenantId;
		this.state = state;
		this.opId = opId;
		this.opDate = opDate;
		this.opName = opName;
	}

	// Property accessors

	public Long getRelId() {
		return this.relId;
	}

	public void setRelId(Long relId) {
		this.relId = relId;
	}

	public Long getLineOrgId() {
		return this.lineOrgId;
	}

	public void setLineOrgId(Long lineOrgId) {
		this.lineOrgId = lineOrgId;
	}

	public Long getLineTenantId() {
		return this.lineTenantId;
	}

	public void setLineTenantId(Long lineTenantId) {
		this.lineTenantId = lineTenantId;
	}

	public Long getBusiOrgId() {
		return this.busiOrgId;
	}

	public void setBusiOrgId(Long busiOrgId) {
		this.busiOrgId = busiOrgId;
	}

	public Long getBusiTenantId() {
		return this.busiTenantId;
	}

	public void setBusiTenantId(Long busiTenantId) {
		this.busiTenantId = busiTenantId;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Date getOpDate() {
		return this.opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public String getOpName() {
		return this.opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	

}