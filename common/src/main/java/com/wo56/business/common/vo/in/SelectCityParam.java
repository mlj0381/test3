package com.wo56.business.common.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class SelectCityParam implements IParamIn{

	//接口编码
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.COMMON.GET_ALL_CITY;
	}

}
