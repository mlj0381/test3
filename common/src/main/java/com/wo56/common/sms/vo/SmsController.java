package com.wo56.common.sms.vo;

import java.sql.Timestamp;

import com.framework.core.base.POJO;

/**
 * SmsController entity. @author MyEclipse Persistence Tools
 */

public class SmsController extends POJO implements java.io.Serializable {

	// Fields

	private Integer smsControllerId;
	private String billId;
	private Long templateId;
	private Integer smsType;
	private Timestamp createDate;

	// Constructors

	/** default constructor */
	public SmsController() {
	}

	/** minimal constructor */
	public SmsController(String billId, Timestamp createDate) {
		this.billId = billId;
		this.createDate = createDate;
	}

	/** full constructor */
	public SmsController(String billId, Long templateId, Integer smsType,
			Timestamp createDate) {
		this.billId = billId;
		this.templateId = templateId;
		this.smsType = smsType;
		this.createDate = createDate;
	}

	// Property accessors

	public Integer getSmsControllerId() {
		return this.smsControllerId;
	}

	public void setSmsControllerId(Integer smsControllerId) {
		this.smsControllerId = smsControllerId;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public Long getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Integer getSmsType() {
		return this.smsType;
	}

	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}