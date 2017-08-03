package com.wo56.business.sys.vo;

import java.sql.Timestamp;

import com.framework.core.base.POJO;

/**
 * TableDiy entity. @author MyEclipse Persistence Tools
 */

public class TableDiy extends POJO {

	// Fields

	private Long id;
	//private Long tenantId;
	private String title;
	private String tableName;
	private Integer state;

	// Constructors

	/** default constructor */
	public TableDiy() {
	}

	/** minimal constructor */
	public TableDiy(Long tenantId) {
		this.tenantId = tenantId;
	}

	/** full constructor */
	public TableDiy(Long tenantId, String title, String tableName,
			Integer state, Timestamp createDate) {
		this.tenantId = tenantId;
		this.title = title;
		this.tableName = tableName;
		this.state = state;
		this.createDate = createDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}