package com.wo56.business.route.vo;

/**
 * RouteTowardsDtl entity. @author MyEclipse Persistence Tools
 */

public class RouteTowardsDtl implements java.io.Serializable {

	// Fields

	private Long dtlId;
	private Long towardsId;
	private Long routingId;
	private Integer sequence;
	private Long beginOrgId;
	private Long endOrgId;
	private String beginOrgName;
	private String endOrgName;
	private Long tenantId;
	
	
	// Constructors

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	/** default constructor */
	public RouteTowardsDtl() {
	}

	/** full constructor */
	public RouteTowardsDtl(Long towardsId, Long routingId, Integer sequence,
			Long beginOrgId, Long endOrgId) {
		this.towardsId = towardsId;
		this.routingId = routingId;
		this.sequence = sequence;
		this.beginOrgId = beginOrgId;
		this.endOrgId = endOrgId;
	}

	// Property accessors

	public Long getDtlId() {
		return this.dtlId;
	}

	public void setDtlId(Long dtlId) {
		this.dtlId = dtlId;
	}

	public Long getTowardsId() {
		return this.towardsId;
	}

	public void setTowardsId(Long towardsId) {
		this.towardsId = towardsId;
	}

	public Long getRoutingId() {
		return this.routingId;
	}

	public void setRoutingId(Long routingId) {
		this.routingId = routingId;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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