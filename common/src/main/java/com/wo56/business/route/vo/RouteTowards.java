package com.wo56.business.route.vo;

import java.util.Date;

import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

/**
 * RouteTowards entity. @author MyEclipse Persistence Tools
 */

public class RouteTowards implements java.io.Serializable {

	// Fields

	private Long towardsId;
	private Long beginOrgId;
	private Long endOrgId;
	private String beginOwnerRegion;
	private String endOwnerRegion;
	private String beginOwnerName;
	private String endOwnerName;
	private String endOwnerRegionName;
	private String beginOrgName;
	private String endOrgName;
	private Long beginTenantId;
	private Long endTenantId;
	private Date createDate;
	private Long tenantId;
	
	

	// Constructors

	

	public Date getCreateDate() {
		return createDate;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Long getBeginTenantId() {
		return beginTenantId;
	}

	public void setBeginTenantId(Long beginTenantId) {
		this.beginTenantId = beginTenantId;
	}

	public Long getEndTenantId() {
		return endTenantId;
	}

	public void setEndTenantId(Long endTenantId) {
		this.endTenantId = endTenantId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** default constructor */
	public RouteTowards() {
	}

	/** minimal constructor */
	public RouteTowards(Long beginOrgId, Long endOrgId,
			String beginOwnerRegion, String endOwnerRegion) {
		this.beginOrgId = beginOrgId;
		this.endOrgId = endOrgId;
		this.beginOwnerRegion = beginOwnerRegion;
		this.endOwnerRegion = endOwnerRegion;
	}

	/** full constructor */
	public RouteTowards(Long beginOrgId, Long endOrgId,
			String beginOwnerRegion, String endOwnerRegion,
			String beginOwnerName, String endOwnerName) {
		this.beginOrgId = beginOrgId;
		this.endOrgId = endOrgId;
		this.beginOwnerRegion = beginOwnerRegion;
		this.endOwnerRegion = endOwnerRegion;
		this.beginOwnerName = beginOwnerName;
		this.endOwnerName = endOwnerName;
	}

	// Property accessors

	public Long getTowardsId() {
		return this.towardsId;
	}

	public void setTowardsId(Long towardsId) {
		this.towardsId = towardsId;
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

	public String getBeginOwnerRegion() {
		return this.beginOwnerRegion;
	}

	public void setBeginOwnerRegion(String beginOwnerRegion) {
		this.beginOwnerRegion = beginOwnerRegion;
	}

	public String getEndOwnerRegion() {
		return this.endOwnerRegion;
	}

	public void setEndOwnerRegion(String endOwnerRegion) {
		this.endOwnerRegion = endOwnerRegion;
	}

	public String getBeginOwnerName() {
		return this.beginOwnerName;
	}

	public void setBeginOwnerName(String beginOwnerName) {
		this.beginOwnerName = beginOwnerName;
	}

	public String getEndOwnerName() {
		return this.endOwnerName;
	}

	public void setEndOwnerName(String endOwnerName) {
		this.endOwnerName = endOwnerName;
	}
	
	public String getEndOwnerRegionName() {
		if (endOwnerRegion != null)
			setEndOwnerRegionName(SysStaticDataUtil.getCityDataList("SYS_CITY", endOwnerRegion+"").getName());
		return endOwnerRegionName;
	}

	public void setEndOwnerRegionName(String endOwnerRegionName) {
		this.endOwnerRegionName = endOwnerRegionName;
	}

	public String getBeginOrgName() {
		if(beginOrgId!=null && beginOrgId>0){
			setBeginOrgName(OraganizationCacheDataUtil.getOrgName(beginOrgId));
		}
		
		return beginOrgName;
	}

	public void setBeginOrgName(String beginOrgName) {
		this.beginOrgName = beginOrgName;
	}

	public String getEndOrgName() {
		if(endOrgId!=null && endOrgId>0){
			setEndOrgName(OraganizationCacheDataUtil.getOrgName(endOrgId));
		}
		return endOrgName;
	}

	public void setEndOrgName(String endOrgName) {
		this.endOrgName = endOrgName;
	}

	
}