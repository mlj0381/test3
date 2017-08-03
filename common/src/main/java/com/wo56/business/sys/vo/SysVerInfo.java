package com.wo56.business.sys.vo;

import java.sql.Timestamp;

/**
 * SysVerInfo entity. @author MyEclipse Persistence Tools
 */

public class SysVerInfo implements java.io.Serializable {

	// Fields

	private Long verId;
	private Long osType;
	private Long updateType;
	private String verNum;
	private Long verType;
	private String verPath;
	private String verDesc;
	private String remark;
	private Long state;
	private Timestamp upTime;

	// Constructors

	/** default constructor */
	public SysVerInfo() {
	}

	/** minimal constructor */
	public SysVerInfo(Long verId) {
		this.verId = verId;
	}

	/** full constructor */
	public SysVerInfo(Long verId, Long osType, Long updateType, String verNum,
			Long verType, String verPath, String verDesc, String remark,
			Long state, Timestamp upTime) {
		this.verId = verId;
		this.osType = osType;
		this.updateType = updateType;
		this.verNum = verNum;
		this.verType = verType;
		this.verPath = verPath;
		this.verDesc = verDesc;
		this.remark = remark;
		this.state = state;
		this.upTime = upTime;
	}

	// Property accessors

	public Long getVerId() {
		return this.verId;
	}

	public void setVerId(Long verId) {
		this.verId = verId;
	}

	public Long getOsType() {
		return this.osType;
	}

	public void setOsType(Long osType) {
		this.osType = osType;
	}

	public Long getUpdateType() {
		return this.updateType;
	}

	public void setUpdateType(Long updateType) {
		this.updateType = updateType;
	}

	public String getVerNum() {
		return this.verNum;
	}

	public void setVerNum(String verNum) {
		this.verNum = verNum;
	}

	public Long getVerType() {
		return this.verType;
	}

	public void setVerType(Long verType) {
		this.verType = verType;
	}

	public String getVerPath() {
		return this.verPath;
	}

	public void setVerPath(String verPath) {
		this.verPath = verPath;
	}

	public String getVerDesc() {
		return this.verDesc;
	}

	public void setVerDesc(String verDesc) {
		this.verDesc = verDesc;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Timestamp getUpTime() {
		return this.upTime;
	}

	public void setUpTime(Timestamp upTime) {
		this.upTime = upTime;
	}

}