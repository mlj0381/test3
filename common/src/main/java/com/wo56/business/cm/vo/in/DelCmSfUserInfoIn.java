package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;

public class DelCmSfUserInfoIn  implements IParamIn {

	private String sfUserIds;

	private String inCode;

	public void setInCode(String inCode) {
		this.inCode = inCode;
	}



	public String getSfUserIds() {
		return sfUserIds;
	}



	public void setSfUserIds(String sfUserIds) {
		this.sfUserIds = sfUserIds;
	}



	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return inCode;
	}

}
