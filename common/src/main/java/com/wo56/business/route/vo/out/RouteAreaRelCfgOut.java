package com.wo56.business.route.vo.out;

import com.framework.core.util.SysStaticDataUtil;

public class RouteAreaRelCfgOut{
	public Long countyId;
	public String countyName;
	public Long destOrgId;
	public Long getCountyId() {
		return countyId;
	}
	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
	public String getCountyName() {
		if(countyId != null && countyId > 0){
			return SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", countyId+"")!=null?SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", countyId+"").getName():"";
		}
		return "";
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public Long getDestOrgId() {
		return destOrgId;
	}
	public void setDestOrgId(Long destOrgId) {
		this.destOrgId = destOrgId;
	}
	
}
