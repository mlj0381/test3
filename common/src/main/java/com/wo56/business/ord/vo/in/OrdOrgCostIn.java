package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdOrgCostIn implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.QUERY_ORG_COST;
	}
   /**订单Id(必填)**/
	public Long orderId;
	/**网点Id(必填)**/
	public Long orgId;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	
	
}
