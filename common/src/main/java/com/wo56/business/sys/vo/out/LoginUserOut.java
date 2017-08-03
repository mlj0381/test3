package com.wo56.business.sys.vo.out;

import java.util.List;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class LoginUserOut extends BaseOutParamVO{
	private static final long serialVersionUID = 2305542754140149493L;
	/**用户id**/
	private long userId;
	/**租户编码**/
	private String tenantCode;
	/**用户类型**/
	private Integer userType;
	/**用户名称**/
	private String userName;
	/**用户账号**/
	private String loginAcct;
	/**用户的网点信息，所属网点**/
	private Long orgId;
	/**
	 * 网点编码
	 */
	private String orgCode;
	
	/**网点名称**/
	private String orgName;
	
	private String tenantName;
	
	/**登录结果**/
	private String result;
	
	public String getTenantCode() {
		return tenantCode;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	private List<Integer> roles;
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginAcct() {
		return loginAcct;
	}
	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public List<Integer> getRoles() {
		return roles;
	}
	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	
	
}
