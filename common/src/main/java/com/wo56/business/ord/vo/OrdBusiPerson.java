package com.wo56.business.ord.vo;

import java.util.Date;

import com.framework.core.base.POJO;

/**
 * OrdBusiPersopn entity. @author MyEclipse Persistence Tools
 */

public class OrdBusiPerson extends POJO implements java.io.Serializable {

	// Fields

	private Long orderId;
	private Long workerId;
	private String workerPhone;
	private String workerName;
	private Long deliveryId;
	private String deliveryName;
	private String deliveryBill;
	//private Date createDate;
	private Long createId;
	private Long inputUserId;
	private Long gxEndOpId;
	private String inputUserName;
	private Date gxEndTime;
	private String gxEndOpName;
	private Date inputTime;
	private Date deliveryTime;

	
	// Constructors

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	/** default constructor */
	public OrdBusiPerson() {
	}

	/** minimal constructor */
	public OrdBusiPerson(Long orderId) {
		this.orderId = orderId;
	}

	/** full constructor */
	public OrdBusiPerson(Long orderId, Long workerId, String workerPhone,
			String workerName, Long deliveryId, String deliveryName,
			String deliveryBill, Date createDate, Long createId,
			Long inputUserId, Long gxEndOpId, String inputUserName,
			Date gxEndTime, String gxEndOpName, Date inputTime) {
		this.orderId = orderId;
		this.workerId = workerId;
		this.workerPhone = workerPhone;
		this.workerName = workerName;
		this.deliveryId = deliveryId;
		this.deliveryName = deliveryName;
		this.deliveryBill = deliveryBill;
		this.createDate = createDate;
		this.createId = createId;
		this.inputUserId = inputUserId;
		this.gxEndOpId = gxEndOpId;
		this.inputUserName = inputUserName;
		this.gxEndTime = gxEndTime;
		this.gxEndOpName = gxEndOpName;
		this.inputTime = inputTime;
	}

	// Property accessors

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getWorkerId() {
		return this.workerId;
	}

	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}

	public String getWorkerPhone() {
		return this.workerPhone;
	}

	public void setWorkerPhone(String workerPhone) {
		this.workerPhone = workerPhone;
	}

	public String getWorkerName() {
		return this.workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public Long getDeliveryId() {
		return this.deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getDeliveryName() {
		return this.deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDeliveryBill() {
		return this.deliveryBill;
	}

	public void setDeliveryBill(String deliveryBill) {
		this.deliveryBill = deliveryBill;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreateId() {
		return this.createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Long getInputUserId() {
		return this.inputUserId;
	}

	public void setInputUserId(Long inputUserId) {
		this.inputUserId = inputUserId;
	}

	public Long getGxEndOpId() {
		return this.gxEndOpId;
	}

	public void setGxEndOpId(Long gxEndOpId) {
		this.gxEndOpId = gxEndOpId;
	}

	public String getInputUserName() {
		return this.inputUserName;
	}

	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}

	public Date getGxEndTime() {
		return this.gxEndTime;
	}

	public void setGxEndTime(Date gxEndTime) {
		this.gxEndTime = gxEndTime;
	}

	public String getGxEndOpName() {
		return this.gxEndOpName;
	}

	public void setGxEndOpName(String gxEndOpName) {
		this.gxEndOpName = gxEndOpName;
	}

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

}