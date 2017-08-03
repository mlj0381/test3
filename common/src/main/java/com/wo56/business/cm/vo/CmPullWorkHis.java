package com.wo56.business.cm.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class CmPullWorkHis implements java.io.Serializable {
	private Long id;
	private Integer pullWork;
	private Long userId;
	private Date createTime;
	private Long tenantId;

	public CmPullWorkHis() {
	}

	public CmPullWorkHis(Long id, Integer pullWork, Long userId, Date createTime, Long tenantId) {
		super();
		this.id = id;
		this.pullWork = pullWork;
		this.userId = userId;
		this.createTime = createTime;
		this.tenantId = tenantId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPullWork() {
		return pullWork;
	}

	public void setPullWork(Integer pullWork) {
		this.pullWork = pullWork;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

}
