package com.wo56.business.ord.vo;

import java.util.Date;

import com.framework.core.base.POJO;

/**
 * OrdSignInfo entity. @author MyEclipse Persistence Tools
 */

public class OrdSignInfo extends POJO implements java.io.Serializable {

	// Fields

	private Long orderId;
	private String signName;
	private Date signDate;
	private String flowId;
	private Long createId;
	private Integer source;
	private String trackingNum;
	private String signDesc;
	private String ext1;
	private String ext2;
	private String orderNo;
	//新增字段 证件号和证件类型
	private String certificateNo;
	private Integer certificateType;
	
	private String certificateTypeString;
	
	public String getCertificateTypeString() {
		return certificateTypeString;
	}

	public void setCertificateTypeString(String certificateTypeString) {
		this.certificateTypeString = certificateTypeString;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public Integer getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(Integer certificateType) {
		this.certificateType = certificateType;
	}

	// Constructors

	/** default constructor */
	public OrdSignInfo() {
	}

	/** minimal constructor */
	public OrdSignInfo(Long orderId, Long createId) {
		this.orderId = orderId;
		this.createId = createId;
	}

	/** full constructor */
	public OrdSignInfo(Long orderId, String signName, Date signDate,
			String flowId, Long createId, Integer source, String trackingNum,
			String signDesc, String ext1, String ext2, String orderNo) {
		this.orderId = orderId;
		this.signName = signName;
		this.signDate = signDate;
		this.flowId = flowId;
		this.createId = createId;
		this.source = source;
		this.trackingNum = trackingNum;
		this.signDesc = signDesc;
		this.ext1 = ext1;
		this.ext2 = ext2;
		this.orderNo = orderNo;
	}
	
	
	public OrdSignInfo(Long orderId, String signName, Date signDate,
			String flowId, Long createId, Integer source, String trackingNum,
			String signDesc, String ext1, String ext2, String orderNo, Integer certificateType, String  certificateNo) {
		this.orderId = orderId;
		this.signName = signName;
		this.signDate = signDate;
		this.flowId = flowId;
		this.createId = createId;
		this.source = source;
		this.trackingNum = trackingNum;
		this.signDesc = signDesc;
		this.ext1 = ext1;
		this.ext2 = ext2;
		this.orderNo = orderNo;
		this.certificateNo = certificateNo;
		this.certificateType = certificateType;
	}

	// Property accessors

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getSignName() {
		return this.signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public Date getSignDate() {
		return this.signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getFlowId() {
		return this.flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public Long getCreateId() {
		return this.createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Integer getSource() {
		return this.source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getTrackingNum() {
		return this.trackingNum;
	}

	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}

	public String getSignDesc() {
		return this.signDesc;
	}

	public void setSignDesc(String signDesc) {
		this.signDesc = signDesc;
	}

	public String getExt1() {
		return this.ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return this.ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}