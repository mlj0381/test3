package com.wo56.business.cm.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class CmUserInfoOutParam extends BaseOutParamVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1044231334221235998L;
	/**用户类型*/
	private int userType;
	private String userTypeName;
	/**用户地*/
	private long  userId;
	/**用户名字*/
	private String userName;
	
	private String loginAcct;
	
	private String orgName;
	
	public String getLoginAcct() {
		return loginAcct;
	}
	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * 
	 * @return
	 */
	
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getUserTypeName() {
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
