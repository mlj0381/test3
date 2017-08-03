package com.wo56.business.system.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;

public class DemoPageParam implements IParamIn {

	// 接口编号
	@Override
	public String getInCode() {
		return "0000";
	}

	// 业务参数
	public String loginAcct;
	public String pwd;

	public String getLoginAcct() {
		return loginAcct;
	}

	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}