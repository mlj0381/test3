package com.wo56.common.sms.vo;

import com.framework.core.base.POJO;

/**
 * SysSmsPlatform entity. @author MyEclipse Persistence Tools
 */

public class SysSmsPlatform extends POJO implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private Boolean state;
	private Integer platformChoice;
	
	public Integer getPlatformChoice() {
		return platformChoice;
	}
	public void setPlatformChoice(Integer platformChoice) {
		this.platformChoice = platformChoice;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}

}