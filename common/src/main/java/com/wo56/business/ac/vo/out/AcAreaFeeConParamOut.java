package com.wo56.business.ac.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class AcAreaFeeConParamOut extends BaseOutParamVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2634870793910790404L;
	/**片区id*/
	private long areaId;
	/**片区名称*/
	private String areaName;
	public long getAreaId() {
		return areaId;
	}
	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
}
