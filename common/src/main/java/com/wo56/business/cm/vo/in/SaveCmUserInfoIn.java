package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;

public class SaveCmUserInfoIn implements IParamIn {

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return this.inCode;
	}

	public void setInCode(String inCode) {
		this.inCode = inCode;
	}

	public SaveCmUserInfoIn(String inCode) {
		super();
		this.inCode = inCode;
	}

	private Long userId;
	private String loginAcct;
	private String password;
	private String userName;
	private Long tenantId;
	private Integer roleId;
	private long orgId;
	private String inCode;
	private Integer loginType;
	private Integer userType;// 用户类型

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public String getLoginAcct() {
		return loginAcct;
	}

	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

}
