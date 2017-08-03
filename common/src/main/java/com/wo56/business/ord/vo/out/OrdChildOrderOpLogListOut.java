package com.wo56.business.ord.vo.out;

import java.sql.Timestamp;

public class OrdChildOrderOpLogListOut {

	private Long orderId;
	private Long trackingNum;
	private String opName;
	private String opContent;
	private Timestamp createDate;
	private String opTypeName;
	private String orgIdName;
	private String trackingNumString;
	private String time;
	private String yearTime;
	private Long id;
	private Integer opType;
	private Long childOrderId;
	private String childTrackingNumString;
	private String childTrackingNum;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getOpContent() {
		return opContent;
	}
	public void setOpContent(String opContent) {
		this.opContent = opContent;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getOpTypeName() {
		return opTypeName;
	}
	public void setOpTypeName(String opTypeName) {
		this.opTypeName = opTypeName;
	}
	public String getOrgIdName() {
		return orgIdName;
	}
	public void setOrgIdName(String orgIdName) {
		this.orgIdName = orgIdName;
	}
	public String getTrackingNumString() {
		return trackingNumString;
	}
	public void setTrackingNumString(String trackingNumString) {
		this.trackingNumString = trackingNumString;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getYearTime() {
		return yearTime;
	}
	public void setYearTime(String yearTime) {
		this.yearTime = yearTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getOpType() {
		return opType;
	}
	public void setOpType(Integer opType) {
		this.opType = opType;
	}
	public Long getChildOrderId() {
		return childOrderId;
	}
	public void setChildOrderId(Long childOrderId) {
		this.childOrderId = childOrderId;
	}
	public String getChildTrackingNumString() {
		return childTrackingNumString;
	}
	public void setChildTrackingNumString(String childTrackingNumString) {
		this.childTrackingNumString = childTrackingNumString;
	}
	public String getChildTrackingNum() {
		return childTrackingNum;
	}
	public void setChildTrackingNum(String childTrackingNum) {
		this.childTrackingNum = childTrackingNum;
	}
	
	
	
	
	
}
