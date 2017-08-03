package com.wo56.business.sys.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class QueryPrintConfigParamIn implements IParamIn {

	private int bizCode;

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.COMMON.QUERY_PRINT_CONFIG;
	}

	public int getBizCode() {
		return bizCode;
	}

	public void setBizCode(int bizCode) {
		this.bizCode = bizCode;
	}

}
