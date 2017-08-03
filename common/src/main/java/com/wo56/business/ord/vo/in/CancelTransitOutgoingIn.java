package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class CancelTransitOutgoingIn implements IParamIn {

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.CANCEL_TRANSIT_OUTGOING;
	}

	private long orderId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

}
