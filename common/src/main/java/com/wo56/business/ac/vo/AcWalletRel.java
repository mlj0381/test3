package com.wo56.business.ac.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class AcWalletRel implements Serializable{
	private Long id;
	private Long tenantId;//公司id
	private Long userId;//用户id
	private Date createTime;//创建时间
	private Long pullTenantId;//拉包工所属公司
	private Long showFee;//已提现金额
	private String tenantName;
	
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public AcWalletRel(){
		
	}
	public AcWalletRel(Long id, Long tenantId, Long userId, Date createTime,
			Long pullTenantId, Long showFee) {
		super();
		this.id = id;
		this.tenantId = tenantId;
		this.userId = userId;
		this.createTime = createTime;
		this.pullTenantId = pullTenantId;
		this.showFee = showFee;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
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
	public Long getPullTenantId() {
		return pullTenantId;
	}
	public void setPullTenantId(Long pullTenantId) {
		this.pullTenantId = pullTenantId;
	}
	public Long getShowFee() {
		return showFee;
	}
	public void setShowFee(Long showFee) {
		this.showFee = showFee;
	}
	
}
