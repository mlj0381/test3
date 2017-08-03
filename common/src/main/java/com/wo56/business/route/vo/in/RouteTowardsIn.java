package com.wo56.business.route.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class RouteTowardsIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ROUTE.QUERY_ROUTE_TOWARD;
	}
	
	/**起始网点Id**/
	public Long beginOrgId;
	/**终止城市**/
	public String endOwnerRegion;
	private int orgType;
	private boolean needFilter;
	private int isSimulaet;
	
	public Long getBeginOrgId() {
		return beginOrgId;
	}
	public void setBeginOrgId(Long beginOrgId) {
		this.beginOrgId = beginOrgId;
	}
	public String getEndOwnerRegion() {
		return endOwnerRegion;
	}
	public void setEndOwnerRegion(String endOwnerRegion) {
		this.endOwnerRegion = endOwnerRegion;
	}
	public int getOrgType() {
		return orgType;
	}
	public void setOrgType(int orgType) {
		this.orgType = orgType;
	}
	public boolean isNeedFilter() {
		return needFilter;
	}
	public void setNeedFilter(boolean needFilter) {
		this.needFilter = needFilter;
	}
	public int getIsSimulaet() {
		return isSimulaet;
	}
	public void setIsSimulaet(int isSimulaet) {
		this.isSimulaet = isSimulaet;
	}
	
}
