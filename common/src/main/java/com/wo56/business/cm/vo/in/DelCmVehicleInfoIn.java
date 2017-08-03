package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class DelCmVehicleInfoIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CM.DEL_CM_VEHICLE;
	}
  private String[]  vehicleIds;
	public String[] getVehicleIds() {
		return vehicleIds;
	}
	public void setVehicleIds(String[] vehicleIds) {
		this.vehicleIds = vehicleIds;
	}
  

}
