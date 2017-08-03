package com.wo56.business.sys.vo;
// default package
// Generated 2015-7-8 15:23:30 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.framework.core.base.POJO;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SysRoleCacheUtil;


public class SysOrgRoleRel extends POJO {

	private Long id;
	private Long roleId;
	private Long orgId;
	
	
	private String roleName;
	
	private String orgName;
	
	private String opName;
	
	public String getRoleName() {
		if(this.getRoleId()!=null){
			return SysRoleCacheUtil.getSysRoleName(this.getRoleId().intValue());
		}
		return "";
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getOrgName() {
		if(this.getOrgId()!=null){
			return OraganizationCacheDataUtil.getOrgName(this.getOrgId());
		}
		return "";
	}
	
	
	
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	
	
}
