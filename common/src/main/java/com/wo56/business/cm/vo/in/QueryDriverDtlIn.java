package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class QueryDriverDtlIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		//return InterFacesCodeConsts.CM.QUERY_DRIVER_DTL;
		return InterFacesCodeConsts.CM.QUERY_DRIVER;
	}
    private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
    
}
