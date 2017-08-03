package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdTrackingDetailQueryIn implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORD_TRACKINGDETAIL_QUERY;
	}

	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
