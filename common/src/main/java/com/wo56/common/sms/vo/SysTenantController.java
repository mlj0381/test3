package com.wo56.common.sms.vo;

import java.sql.Timestamp;

import com.framework.core.base.POJO;

/**
 * SysSmsController entity. @author MyEclipse Persistence Tools
 */

public class SysTenantController extends POJO implements java.io.Serializable {

	// Fields

	private Long id;
	//private Long tenantId;
	private Integer sendType;
	private String platformId;
	private Boolean state;
	private Timestamp createDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public Integer getSendType() {
		return sendType;
	}
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
}