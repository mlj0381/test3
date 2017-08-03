package com.wo56.business.transfer.vo;

import java.sql.Timestamp;

import com.framework.core.base.POJO;

/**
 * OrdTransferDetail entity. @author MyEclipse Persistence Tools
 */

public class OrdTransferDetail extends POJO implements java.io.Serializable {

	// Fields

	private OrdTransferDetailId id;
	private Timestamp createDate;

	// Constructors

	/** default constructor */
	public OrdTransferDetail() {
	}


	// Property accessors

	public OrdTransferDetailId getId() {
		return this.id;
	}

	public void setId(OrdTransferDetailId id) {
		this.id = id;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}