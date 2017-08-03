package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class CmDriverInfoInDel implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CM.DEL_DRIVER_CM;
	}
	private String[]  driverIds;
	public String[] getDriverIds() {
		return driverIds;
	}
	public void setDriverIds(String[] driverIds) {
		this.driverIds = driverIds;
	}
	
	
}
