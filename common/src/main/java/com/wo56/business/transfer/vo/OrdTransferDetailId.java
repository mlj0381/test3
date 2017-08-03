package com.wo56.business.transfer.vo;

import com.framework.core.base.POJO;

/**
 * OrdTransferDetailId entity. @author MyEclipse Persistence Tools
 */

public class OrdTransferDetailId extends POJO implements java.io.Serializable {

	// Fields

	private Long batchNum;
	private Long orderId;

	// Constructors

	/** default constructor */
	public OrdTransferDetailId() {
	}

	/** full constructor */
	public OrdTransferDetailId(Long batchNum, Long orderId) {
		this.batchNum = batchNum;
		this.orderId = orderId;
	}

	// Property accessors

	public Long getBatchNum() {
		return this.batchNum;
	}

	public void setBatchNum(Long batchNum) {
		this.batchNum = batchNum;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OrdTransferDetailId))
			return false;
		OrdTransferDetailId castOther = (OrdTransferDetailId) other;

		return ((this.getBatchNum() == castOther.getBatchNum()) || (this
				.getBatchNum() != null && castOther.getBatchNum() != null && this
				.getBatchNum().equals(castOther.getBatchNum())))
				&& ((this.getOrderId() == castOther.getOrderId()) || (this
						.getOrderId() != null && castOther.getOrderId() != null && this
						.getOrderId().equals(castOther.getOrderId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getBatchNum() == null ? 0 : this.getBatchNum().hashCode());
		result = 37 * result
				+ (getOrderId() == null ? 0 : this.getOrderId().hashCode());
		return result;
	}

}