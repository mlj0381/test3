package com.wo56.business.ac.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;
/**
 * 
 * @author zjy 
 * （1） 表示必传 （0）  非必传
 * 
 * */
public class AcAreaFeeConParamIn implements IParamIn {

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.AC.QUERY_AREA;
	}
	/**配送网点id (1)**/
	private long orgId;
	
	/**租户id （1）*/
	private long tenantId;
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	public long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
	
	
}
