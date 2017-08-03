package com.wo56.business.cm.vo.out;

import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.framework.core.util.SysStaticDataUtil;

public class CmUserInfoOutQuery extends BaseOutParamVO{

	private String loginTypeName;
	private long userId;
	private Integer userType;
	private String loginAcct;
	private String loginPwd;
	private String userName;
	private Integer state;
	// private String orgCode;
	// private Long orgId;
	private Long opId;
	private Date createTime;
	private String dataSource;
	private Integer loginType;
	private String userPic;// 头像
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getLoginAcct() {
		return loginAcct;
	}

	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public String getUserPic() {
		return userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	public String getLoginTypeName() {
		if (getLoginType() == null) {
			return "";
		}
		return SysStaticDataUtil.getSysStaticData("MENU_TYPE",
				String.valueOf(getLoginType())).getCodeName();

	}

	public void setLoginTypeName(String loginTypeName) {
		this.loginTypeName = loginTypeName;
	}
}
