package com.wo56.business.route.vo;

import java.sql.Timestamp;

import com.framework.core.base.POJO;
import com.framework.core.util.SysStaticDataUtil;

/**
 * RouteAreaRelCfg entity. @author MyEclipse Persistence Tools
 */

public class RouteAreaRelCfg extends POJO {

	// Fields

	private long relId;
	private String relName;
	private Long orgId;
	private String orgName;
	private Long provinceId;
	private Long cityId;
	private Long countyId;
	private Long townId;
	private Long destOrgId;
	private String opName;
	private Timestamp opDate;
	
	private String countyName;
	private String cityName;
	private Integer isSeaTransport;
	//private Long tenantId;
	
	
	
	
	// Constructors

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getIsSeaTransport() {
		return isSeaTransport;
	}

	public void setIsSeaTransport(Integer isSeaTransport) {
		this.isSeaTransport = isSeaTransport;
	}

	public String getCityName() {
		if(cityId != null)
			setCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", cityId+"").getName());
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		if(countyId != null){
			setCountyName(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", countyId+"").getName());
		}
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/** default constructor */
	public RouteAreaRelCfg() {
	}

	/** full constructor */
	public RouteAreaRelCfg(String relName, Long orgId, String orgName,
			Long provinceId, Long cityId, Long countyId, Long townId,
			Long destOrgId, Long opId, String opName, Timestamp opDate,Long tenantId) {
		this.relName = relName;
		this.orgId = orgId;
		this.orgName = orgName;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.countyId = countyId;
		this.townId = townId;
		this.destOrgId = destOrgId;
		this.opId = opId;
		this.opName = opName;
		this.opDate = opDate;
		this.tenantId = tenantId;
	}

	// Property accessors

	public long getRelId() {
		return this.relId;
	}

	public void setRelId(long relId) {
		this.relId = relId;
	}

	public String getRelName() {
		return this.relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return this.cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getCountyId() {
		return this.countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public Long getTownId() {
		return this.townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}

	public Long getDestOrgId() {
		return this.destOrgId;
	}

	public void setDestOrgId(Long destOrgId) {
		this.destOrgId = destOrgId;
	}

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getOpName() {
		return this.opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public Timestamp getOpDate() {
		return this.opDate;
	}

	public void setOpDate(Timestamp opDate) {
		this.opDate = opDate;
	}

}