package com.wo56.business.route.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class RouteRutingDetailIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ROUTE.QUERY_ROUTE_ROUTING_DETAIL_DTL;
	}
	/**网点编号**/
	public long beginOrgId;
	public long endOrgId;
	public long getBeginOrgId() {
		return beginOrgId;
	}
	public void setBeginOrgId(long beginOrgId) {
		this.beginOrgId = beginOrgId;
	}
	public long getEndOrgId() {
		return endOrgId;
	}
	public void setEndOrgId(long endOrgId) {
		this.endOrgId = endOrgId;
	}
	
}
