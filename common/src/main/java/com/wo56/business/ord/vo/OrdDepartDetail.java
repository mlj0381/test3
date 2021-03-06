package com.wo56.business.ord.vo;

// Generated 2016-3-15 16:26:20 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.framework.core.base.POJO;

/**
 * OrdDepartDetail generated by hbm2java
 */
public class OrdDepartDetail extends POJO implements java.io.Serializable {

	private OrdDepartDetailId id;
	//private Date createDate;
	private Long installFee;
	private Date arrivalTime;
	private Long arrivalOpId;
	private String arrivalOpName;
	
	
	
	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Long getArrivalOpId() {
		return arrivalOpId;
	}

	public void setArrivalOpId(Long arrivalOpId) {
		this.arrivalOpId = arrivalOpId;
	}

	public String getArrivalOpName() {
		return arrivalOpName;
	}

	public void setArrivalOpName(String arrivalOpName) {
		this.arrivalOpName = arrivalOpName;
	}

	public Long getInstallFee() {
		return installFee;
	}

	public void setInstallFee(Long installFee) {
		this.installFee = installFee;
	}

	public OrdDepartDetail() {
	}

	public OrdDepartDetail(OrdDepartDetailId id, Date createDate) {
		this.id = id;
		this.createDate = createDate;
	}

	public OrdDepartDetailId getId() {
		return this.id;
	}

	public void setId(OrdDepartDetailId id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
