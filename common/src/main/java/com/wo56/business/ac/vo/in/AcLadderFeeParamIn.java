package com.wo56.business.ac.vo.in;

/**
 * 
 * @author zjy 
 * （1） 表示必传 （0）  非必传
 * 
 * */
public class AcLadderFeeParamIn {
	
	/**起始网点id (1)**/
	private long startOrgId;
	/**到达网点id (1)**/
	private long endOrgId;
	/***路由id (1)**/
	private long routeId;
	/**开单人租户id （1）*/
	private long tenantId;
    /**重量*/
	private double weight;
    /**体积*/
	private double volume;
	/**是否标准*/
	private Integer isStandardLine;
	
	
	public Integer getIsStandardLine() {
		return isStandardLine;
	}
	public void setIsStandardLine(Integer isStandardLine) {
		this.isStandardLine = isStandardLine;
	}
	public long getStartOrgId() {
		return startOrgId;
	}
	public void setStartOrgId(long startOrgId) {
		this.startOrgId = startOrgId;
	}
	public long getEndOrgId() {
		return endOrgId;
	}
	public void setEndOrgId(long endOrgId) {
		this.endOrgId = endOrgId;
	}
	public long getRouteId() {
		return routeId;
	}
	public void setRouteId(long routeId) {
		this.routeId = routeId;
	}
	public long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
	
}
