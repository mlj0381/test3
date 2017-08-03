package com.wo56.business.ac.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class AcFeeConfigAddIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.AC.SAVE_FEE_CONFIG;
	}
	private String feeName;
	private int feeType;
	private int costType;
	private int routeOrgType;
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public int getFeeType() {
		return feeType;
	}
	public void setFeeType(int feeType) {
		this.feeType = feeType;
	}
	public int getCostType() {
		return costType;
	}
	public void setCostType(int costType) {
		this.costType = costType;
	}
	public int getRouteOrgType() {
		return routeOrgType;
	}
	public void setRouteOrgType(int routeOrgType) {
		this.routeOrgType = routeOrgType;
	}
	

}
