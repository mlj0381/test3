package com.wo56.business.sys.vo;

import java.sql.Timestamp;

/**
 * SysLoginLog entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class SysLoginLog implements java.io.Serializable {

	// Fields

	private Long loginRecId;
	private Long userId;
	private String billId;
	private Timestamp opDate;
	private Integer loginType;
	private Integer channelType;
	private String appVerNo;
	private String appOsVer;
	private String mobileType;
	private String mobileBrand;
	private String toolType;
	private String toolVer;

	// Constructors

	/** default constructor */
	public SysLoginLog() {
	}

	/** full constructor */
	public SysLoginLog(Long userId, String billId, Timestamp opDate,
			Integer loginType, Integer channelType, String appVerNo,
			String appOsVer, String mobileType, String mobileBrand,
			String toolType, String toolVer) {
		this.userId = userId;
		this.billId = billId;
		this.opDate = opDate;
		this.loginType = loginType;
		this.channelType = channelType;
		this.appVerNo = appVerNo;
		this.appOsVer = appOsVer;
		this.mobileType = mobileType;
		this.mobileBrand = mobileBrand;
		this.toolType = toolType;
		this.toolVer = toolVer;
	}

	// Property accessors

	public Long getLoginRecId() {
		return this.loginRecId;
	}

	public void setLoginRecId(Long loginRecId) {
		this.loginRecId = loginRecId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBillId() {
		return this.billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public Timestamp getOpDate() {
		return this.opDate;
	}

	public void setOpDate(Timestamp opDate) {
		this.opDate = opDate;
	}

	public Integer getLoginType() {
		return this.loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public Integer getChannelType() {
		return this.channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public String getAppVerNo() {
		return this.appVerNo;
	}

	public void setAppVerNo(String appVerNo) {
		this.appVerNo = appVerNo;
	}

	public String getAppOsVer() {
		return this.appOsVer;
	}

	public void setAppOsVer(String appOsVer) {
		this.appOsVer = appOsVer;
	}

	public String getMobileType() {
		return this.mobileType;
	}

	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}

	public String getMobileBrand() {
		return this.mobileBrand;
	}

	public void setMobileBrand(String mobileBrand) {
		this.mobileBrand = mobileBrand;
	}

	public String getToolType() {
		return this.toolType;
	}

	public void setToolType(String toolType) {
		this.toolType = toolType;
	}

	public String getToolVer() {
		return this.toolVer;
	}

	public void setToolVer(String toolVer) {
		this.toolVer = toolVer;
	}

}