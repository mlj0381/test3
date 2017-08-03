package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class ConfirmTransitOutgoingReceivedFeeIn extends PageInParamVO implements IParamIn {

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.CONFIRM_TRANSIT_OUTGOING_RECEIVED_FEE;
	}

	private long orderId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

}
