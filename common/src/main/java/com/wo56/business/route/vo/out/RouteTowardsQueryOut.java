package com.wo56.business.route.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class RouteTowardsQueryOut extends BaseOutParamVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3556910259442304778L;
	/**线路走向id*/
	private long towardsId;
	public long getTowardsId() {
		return towardsId;
	}
	public void setTowardsId(long towardsId) {
		this.towardsId = towardsId;
	}
	/***开始网点所在城市*/
	private String beginOwnerRegion;
	/***终止网点所在城市*/
	private String endOwnerRegion;
	/**开始网点名称**/
	private String beginOwnerName;
	/**目的网点名称*/
	private String endOwnerName;
	public String getBeginOwnerRegion() {
		return beginOwnerRegion;
	}
	public void setBeginOwnerRegion(String beginOwnerRegion) {
		this.beginOwnerRegion = beginOwnerRegion;
	}
	public String getEndOwnerRegion() {
		return endOwnerRegion;
	}
	public void setEndOwnerRegion(String endOwnerRegion) {
		this.endOwnerRegion = endOwnerRegion;
	}
	public String getBeginOwnerName() {
		return beginOwnerName;
	}
	public void setBeginOwnerName(String beginOwnerName) {
		this.beginOwnerName = beginOwnerName;
	}
	public String getEndOwnerName() {
		return endOwnerName;
	}
	public void setEndOwnerName(String endOwnerName) {
		this.endOwnerName = endOwnerName;
	}

	
	
}
