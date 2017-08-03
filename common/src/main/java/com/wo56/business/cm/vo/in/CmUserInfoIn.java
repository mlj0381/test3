package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class CmUserInfoIn extends PageInParamVO implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CM.QUERY_CM_USER;
	}
	private String userName;
	private int userType;
	private long orgId;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	
	

}
