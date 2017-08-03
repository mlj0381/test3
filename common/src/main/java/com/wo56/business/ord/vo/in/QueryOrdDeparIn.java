package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class QueryOrdDeparIn implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.QUERY_ORDER_DEPAR;
	}
	private Long trackingNum;
	private Long orderId;

	public Long getTrackingNum() {
		return trackingNum;
	}

	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
}
