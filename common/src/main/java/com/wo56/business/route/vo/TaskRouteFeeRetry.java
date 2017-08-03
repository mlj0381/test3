package com.wo56.business.route.vo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * TaskRouteFeeRetry entity. @author MyEclipse Persistence Tools
 */

public class TaskRouteFeeRetry implements java.io.Serializable {

	// Fields

	private Long taskId;
	private Long orderId;
	private Long beginOrgId;
	private Long endOrgId;
	private Date createDate;
	private Long opId;
	private Long batchId;
	private Long routingId;
	private Integer sts;

	// Constructors

	/** default constructor */
	public TaskRouteFeeRetry() {
	}

	/** minimal constructor */
	public TaskRouteFeeRetry(Long taskId) {
		this.taskId = taskId;
	}

	/** full constructor */
	public TaskRouteFeeRetry(Long taskId, Long orderId, Long beginOrgId,
			Long endOrgId, Timestamp createDate, Long opId, Long batchId,
			Integer sts) {
		this.taskId = taskId;
		this.orderId = orderId;
		this.beginOrgId = beginOrgId;
		this.endOrgId = endOrgId;
		this.createDate = createDate;
		this.opId = opId;
		this.batchId = batchId;
		this.sts = sts;
	}

	// Property accessors

	public Long getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getBeginOrgId() {
		return this.beginOrgId;
	}

	public void setBeginOrgId(Long beginOrgId) {
		this.beginOrgId = beginOrgId;
	}

	public Long getEndOrgId() {
		return this.endOrgId;
	}

	public void setEndOrgId(Long endOrgId) {
		this.endOrgId = endOrgId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Long getBatchId() {
		return this.batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Integer getSts() {
		return this.sts;
	}

	public void setSts(Integer sts) {
		this.sts = sts;
	}

	public Long getRoutingId() {
		return routingId;
	}

	public void setRoutingId(Long routingId) {
		this.routingId = routingId;
	}

}