package com.wo56.business.route.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class TowardsQuery extends PageInParamVO implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.BASE.TOWARDS_QUERY;
	}

	private Long tenantId;
	private Long beginOrgId;
	private Long endOrgId;
	private Integer routeType;
	private Integer isStandardLine;
	
	
	public Integer getIsStandardLine() {
		return isStandardLine;
	}
	public void setIsStandardLine(Integer isStandardLine) {
		this.isStandardLine = isStandardLine;
	}
	public Integer getRouteType() {
		return routeType;
	}
	public void setRouteType(Integer routeType) {
		this.routeType = routeType;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public Long getBeginOrgId() {
		return beginOrgId;
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
