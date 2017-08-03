package com.wo56.common.sms.vo;

import java.sql.Timestamp;

import com.framework.core.base.POJO;

/**
 * SysSmsPlatform entity. @author MyEclipse Persistence Tools
 */

public class SysSmsPlatformInfo extends POJO implements java.io.Serializable {

	// Fields

	private Long id;
	private Long platformId;
	private String key;
	private String value;
	private String remark;

	// Constructors

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	/** default constructor */
	public SysSmsPlatformInfo() {
	}

	/** full constructor */
	public SysSmsPlatformInfo(String key, String value,
			Boolean state, Timestamp createDate) {
		this.key = key;
		this.value = value;
		this.createDate = createDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}