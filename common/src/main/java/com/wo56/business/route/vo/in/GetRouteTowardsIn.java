package com.wo56.business.route.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class GetRouteTowardsIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ROUTE.GET_ROUNT;
	}
	
	/**起始网点Id**/
	private Long beginOrgId;
	private Long endOrgId;
	private String hasAll;
	public Long getBeginOrgId() {
		return beginOrgId;
	}
	
	public String getHasAll() {
		return hasAll;
	}

	public void setHasAll(String hasAll) {
		this.hasAll = hasAll;
	}

	public void setBeginOrgId(Long beginOrgId) {
		this.beginOrgId = beginOrgId;
	}
	public Long getEndOrgId() {
		return endOrgId;
	}
	public void setEndOrgId(Long endOrgId) {
		this.endOrgId = endOrgId;
	}

}
