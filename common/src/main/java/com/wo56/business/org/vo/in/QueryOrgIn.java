package com.wo56.business.org.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
/**
 * @author zjy
 * （1）必传 （0）选传
 * */
public class QueryOrgIn implements IParamIn{

	private Long orgId;
	private String inCode;
	private String tenantType;
	private String tenantId;
   
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	public String getTenantType() {
		return tenantType;
	}
	public void setTenantType(String tenantType) {
		this.tenantType = tenantType;
	}
	public String getInCode() {
		return inCode;
	}
	public QueryOrgIn(String inCode) {
		super();
		this.inCode = inCode;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	
}
