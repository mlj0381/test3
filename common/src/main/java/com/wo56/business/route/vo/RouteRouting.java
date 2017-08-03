package com.wo56.business.route.vo;

/**
 * RouteRouting entity. @author MyEclipse Persistence Tools
 */

public class RouteRouting implements java.io.Serializable {

	// Fields

	private Long routingId;
	private Long beginOrgId;
	private Long endOrgId;
	private Integer routeType;
	private Long tenantId;
	private Integer status;
	private Integer isStandardLine;
	private String beginOrgName;
	private String endOrgName;

	// Constructors

	/** default constructor */
	public RouteRouting() {
	}

	/** full constructor */
	public RouteRouting(Long beginOrgId, Long endOrgId, Integer routeType,
			Long tenantId, Integer status, Integer isStandardLine) {
		this.beginOrgId = beginOrgId;
		this.endOrgId = endOrgId;
		this.routeType = routeType;
		this.tenantId = tenantId;
		this.status = status;
		this.isStandardLine = isStandardLine;
	}

	// Property accessors

	public Long getRoutingId() {
		return this.routingId;
	}

	public void setRoutingId(Long routingId) {
		this.routingId = routingId;
	}

	public Long getBeginOrgId() {
		return this.beginOrgId;
	}

	public void setBeginOrgId(Long beginOrgId) {
		this.beginOrgId = beginOrgId;
	}

	public Long getEndOrgId() {
		return this.endOrgId;
	}

	public void setEndOrgId(Long endOrgId) {
		this.endOrgId = endOrgId;
	}

	public Integer getRouteType() {
		return this.routeType;
	}

	public void setRouteType(Integer routeType) {
		this.routeType = routeType;
	}

	public Long getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsStandardLine() {
		return this.isStandardLine;
	}

	public void setIsStandardLine(Integer isStandardLine) {
		this.isStandardLine = isStandardLine;
	}

	public String getBeginOrgName() {
		return beginOrgName;
	}

	public void setBeginOrgName(String beginOrgName) {
		this.beginOrgName = beginOrgName;
	}

	public String getEndOrgName() {
		return endOrgName;
	}

	public void setEndOrgName(String endOrgName) {
		this.endOrgName = endOrgName;
	}
	

}