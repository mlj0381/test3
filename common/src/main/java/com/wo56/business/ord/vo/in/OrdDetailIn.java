package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdDetailIn implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORD_DETAIL_IN;
	}
	private Long trackingNum;
	/**类型 只有等于2的时候返回订单信息和货物信息 其他返回全部信息*/
	private Integer type;
	private Long orderId;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

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
