package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdCheckTrackingNumUsedStateIn implements IParamIn {

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.CHECK_TRACKING_NUM_USED_STATE;
	}

	private long trackingNum;

	public long getTrackingNum() {
		return trackingNum;
	}

	public void setTrackingNum(long trackingNum) {
		this.trackingNum = trackingNum;
	}
}
