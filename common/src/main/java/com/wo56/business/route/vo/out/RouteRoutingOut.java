package com.wo56.business.route.vo.out;

import java.io.Serializable;


@SuppressWarnings("serial")
public class RouteRoutingOut implements Serializable{


	private String routeTypeName;
	private String tenantName;
	private String isStandardLineName;
	private String beginOrgName;
	private String endOrgName;
	private Long routingId;
	private Long beginOrgId;
	private Long endOrgId;
	private Integer routeType;
	private Integer isStandardLine;
	private Integer status;
	private Long tenantId;
	

	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Integer getIsStandardLine() {
		return isStandardLine;
	}
	public void setIsStandardLine(Integer isStandardLine) {
		this.isStandardLine = isStandardLine;
	}
	public Long getRoutingId() {
		return routingId;
	}
	public void setRoutingId(Long routingId) {
		this.routingId = routingId;
	}
	public String getRouteTypeName() {
		return routeTypeName;
	}
	public void setRouteTypeName(String routeTypeName) {
		this.routeTypeName = routeTypeName;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getIsStandardLineName() {
		return isStandardLineName;
	}
	public void setIsStandardLineName(String isStandardLineName) {
		this.isStandardLineName = isStandardLineName;
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
