package com.wo56.business.route.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class TaskRouteIn implements IParamIn{
	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ROUTE.DOSAVE_ROUTE_TASK;
	}
	/**订单Id**/
	private Long ordreId;
	/**起始网点编号**/
	private Long beginOrgId;
	/**结束网点编号**/
	private Long EndOrgId;
	public Long getOrdreId() {
		return ordreId;
	}
	public void setOrdreId(Long ordreId) {
		this.ordreId = ordreId;
	}
	public Long getBeginOrgId() {
		return beginOrgId;
	}
	public void setBeginOrgId(Long beginOrgId) {
		this.beginOrgId = beginOrgId;
	}
	public Long getEndOrgId() {
		return EndOrgId;
	}
	public void setEndOrgId(Long endOrgId) {
		EndOrgId = endOrgId;
	}
	
	

}
