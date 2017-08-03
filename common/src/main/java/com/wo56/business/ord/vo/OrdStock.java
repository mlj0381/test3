package com.wo56.business.ord.vo;

import java.util.Date;

/**
 * OrdStock entity. @author MyEclipse Persistence Tools
 */

public class OrdStock implements java.io.Serializable {

	// Fields
	
	private long id;
	/**运单表ID*/
	private Long orderId;
	/**入库时间*/
	private Date stockInTime;
	/**入库类型：0 开单 1 到车*/
	private Integer stockInType;
	/**出库时间*/
	private Date stockOutTime;
	/**出库类型 0 发车  1 中转  2 送货上门*/
	private Integer stockOutType;
	/**网点*/
	private Long orgId;
	/**入库的操作人*/
	private Long stockInOpId;
	/**出库的操作人*/
	private Long stockOutOpId;
	
	private Long tenantId;
	
	
	
	// Constructors

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	/** default constructor */
	public OrdStock() {
	}

	/** full constructor */
	public OrdStock(Long id, Long orderId, Date stockInTime,
			Integer stockInType, Date stockOutTime, Integer stockOutType,
			Long orgId, Long stockInOpId, Long stockOutOpId) {
		this.id = id;
		this.orderId = orderId;
		this.stockInTime = stockInTime;
		this.stockInType = stockInType;
		this.stockOutTime = stockOutTime;
		this.stockOutType = stockOutType;
		this.orgId = orgId;
		this.stockInOpId = stockInOpId;
		this.stockOutOpId = stockOutOpId;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getStockInTime() {
		return this.stockInTime;
	}

	public void setStockInTime(Date stockInTime) {
		this.stockInTime = stockInTime;
	}

	public Integer getStockInType() {
		return this.stockInType;
	}

	public void setStockInType(Integer stockInType) {
		this.stockInType = stockInType;
	}

	public Date getStockOutTime() {
		return this.stockOutTime;
	}

	public void setStockOutTime(Date stockOutTime) {
		this.stockOutTime = stockOutTime;
	}

	public Integer getStockOutType() {
		return this.stockOutType;
	}

	public void setStockOutType(Integer stockOutType) {
		this.stockOutType = stockOutType;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getStockInOpId() {
		return this.stockInOpId;
	}

	public void setStockInOpId(Long stockInOpId) {
		this.stockInOpId = stockInOpId;
	}

	public Long getStockOutOpId() {
		return this.stockOutOpId;
	}

	public void setStockOutOpId(Long stockOutOpId) {
		this.stockOutOpId = stockOutOpId;
	}

}