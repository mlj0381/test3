package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdDetail implements IParamIn {

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORDER_DETAIL;
	}

	private Long trackingNum;
	/** 类型 只有等于2的时候返回订单信息和货物信息 其他返回全部信息 */
	private Integer type;
	private Long orderId;

	/** 对于中转外发，中转网点不需要根据org_id查询出结果，用current_org_id查询，为true，则忽略 */
	private boolean needIngoreOrgId;

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

	public boolean isNeedIngoreOrgId() {
		return needIngoreOrgId;
	}

	public void setNeedIngoreOrgId(boolean needIngoreOrgId) {
		this.needIngoreOrgId = needIngoreOrgId;
	}
}
