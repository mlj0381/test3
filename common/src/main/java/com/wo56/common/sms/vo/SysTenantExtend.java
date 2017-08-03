package com.wo56.common.sms.vo;

import com.framework.core.base.POJO;

/**
 * SysTenantExtend entity. @author MyEclipse Persistence Tools
 */

public class SysTenantExtend extends POJO implements java.io.Serializable {

	// Fields

	private Long flowId;
	private String EKey;
	private String EValue;
	private Boolean status;

	// Constructors

	/** default constructor */
	public SysTenantExtend() {
	}

	/** minimal constructor */
	public SysTenantExtend(Long tenantId) {
		this.tenantId = tenantId;
	}

	/** full constructor */
	public SysTenantExtend(Long tenantId, String EKey, String EValue,
			Boolean status) {
		this.tenantId = tenantId;
		this.EKey = EKey;
		this.EValue = EValue;
		this.status = status;
	}

	// Property accessors

	public Long getFlowId() {
		return this.flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public String getEKey() {
		return this.EKey;
	}

	public void setEKey(String EKey) {
		this.EKey = EKey;
	}

	public String getEValue() {
		return this.EValue;
	}

	public void setEValue(String EValue) {
		this.EValue = EValue;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}