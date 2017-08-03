package com.wo56.business.route.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class RouteTowardsViewIn implements IParamIn {

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.BASE.ROUTE_TO_VIEW;
	}
	
	/*路线走向id**/
	public Long towardsId;

	public Long getTowardsId() {
		return towardsId;
	}

	public void setTowardsId(Long towardsId) {
		this.towardsId = towardsId;
	}
	
	
	
}
