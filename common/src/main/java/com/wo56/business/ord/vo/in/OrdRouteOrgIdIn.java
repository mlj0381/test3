package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdRouteOrgIdIn implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ROUTE_ORG;
	}

	private String trackingNum;

	public String getTrackingNum() {
		return trackingNum;
	}

	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}

	
	
}
