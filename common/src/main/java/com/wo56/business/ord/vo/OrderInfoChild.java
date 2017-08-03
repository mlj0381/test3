package com.wo56.business.ord.vo;

import java.util.Date;

public class OrderInfoChild {
	private Long childOrderId;
	private Long orderId;
	private String  trackingNum;
	private String childTrackingNum;
	private Long dispatchingOrgId;
	private Long currentOrgId;
	private Long createId;
	private String createName;
	private Date createTime;
	private Long opId;
	private String opName;
	private Date opTime;
	private Long tenantId;
	private Integer childOrderState;
	private String childTrackingNumAli;
	private Long dispatchingId;
	private String dispatchingName;
	private Date dispatchingTime;
	
	
	
	public Long getDispatchingId() {
		return dispatchingId;
	}
	public void setDispatchingId(Long dispatchingId) {
		this.dispatchingId = dispatchingId;
	}
	public String getDispatchingName() {
		return dispatchingName;
	}
	public void setDispatchingName(String dispatchingName) {
		this.dispatchingName = dispatchingName;
	}
	public Date getDispatchingTime() {
		return dispatchingTime;
	}
	public void setDispatchingTime(Date dispatchingTime) {
		this.dispatchingTime = dispatchingTime;
	}
	public Long getChildOrderId() {
		return childOrderId;
	}
	public void setChildOrderId(Long childOrderId) {
		this.childOrderId = childOrderId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getChildTrackingNum() {
		return childTrackingNum;
	}
	public void setChildTrackingNum(String childTrackingNum) {
		this.childTrackingNum = childTrackingNum;
	}
	public Long getDispatchingOrgId() {
		return dispatchingOrgId;
	}
	public void setDispatchingOrgId(Long dispatchingOrgId) {
		this.dispatchingOrgId = dispatchingOrgId;
	}
	public Long getCurrentOrgId() {
		return currentOrgId;
	}
	public void setCurrentOrgId(Long currentOrgId) {
		this.currentOrgId = currentOrgId;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public Integer getChildOrderState() {
		return childOrderState;
	}
	public void setChildOrderState(Integer childOrderState) {
		this.childOrderState = childOrderState;
	}
	public String getChildTrackingNumAli() {
		return childTrackingNumAli;
	}
	public void setChildTrackingNumAli(String childTrackingNumAli) {
		this.childTrackingNumAli = childTrackingNumAli;
	}
	
	
	
	
}
