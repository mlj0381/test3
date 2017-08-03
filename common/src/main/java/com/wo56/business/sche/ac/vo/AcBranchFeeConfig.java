package com.wo56.business.sche.ac.vo;


import java.sql.Timestamp;

import com.framework.core.util.SysStaticDataUtil;


/**
 * AcBranchFeeConfig entity. @author MyEclipse Persistence Tools
 */

public class AcBranchFeeConfig implements java.io.Serializable {

	// Fields

	private Long branchId;
	private Long provinceId;
	private Long regionId;
	private Long countyId;
	private Long townId;
	private Double priceUnit;
	private Double exceedVolumePrice;
	private Double exceedDisPrice;
	private String tenantId;
	private String exceedVolume;
	private String exceedDis;
	private Timestamp createTime;
	private Long opId;
	private String remark;
	private Integer sts;
	private String provinceName;
	private String regionName;
	private String countyName;
	private String townName;
	
	
	// Constructors

	public String getProvinceName() {
		if (provinceId!=null && (provinceId)!=0){
			setProvinceName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", provinceId+"").getName());
			}
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getRegionName() {
		if (regionId!=null && (regionId)!=0){
			setRegionName(SysStaticDataUtil.getCityDataList("SYS_CITY", regionId+"").getName());
			}
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCountyName() {
		if (countyId!=null && (countyId)!=0){
			setCountyName(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", countyId+"").getName());
			}
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getTownName() {
		if (townId!=null && (townId)!=0){
			setTownName(SysStaticDataUtil.getStreetDataList("SYS_STREET", townId+"").getName());
			}
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	/** default constructor */
	public AcBranchFeeConfig() {
	}

	/** minimal constructor */
	public AcBranchFeeConfig(Long provinceId, Long regionId, Long countyId,
			Double priceUnit, String tenantId, Timestamp createTime, Integer sts) {
		this.provinceId = provinceId;
		this.regionId = regionId;
		this.countyId = countyId;
		this.priceUnit = priceUnit;
		this.tenantId = tenantId;
		this.createTime = createTime;
		this.sts = sts;
	}

	/** full constructor */
	public AcBranchFeeConfig(Long provinceId, Long regionId, Long countyId,
			Long townId, Double priceUnit, Double exceedVolumePrice,
			Double exceedDisPrice, String tenantId, String exceedVolume,
			String exceedDis, Timestamp createTime, Long opId, String remark,
			Integer sts) {
		this.provinceId = provinceId;
		this.regionId = regionId;
		this.countyId = countyId;
		this.townId = townId;
		this.priceUnit = priceUnit;
		this.exceedVolumePrice = exceedVolumePrice;
		this.exceedDisPrice = exceedDisPrice;
		this.tenantId = tenantId;
		this.exceedVolume = exceedVolume;
		this.exceedDis = exceedDis;
		this.createTime = createTime;
		this.opId = opId;
		this.remark = remark;
		this.sts = sts;
	}

	// Property accessors

	public Long getBranchId() {
		return this.branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
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

	public Double getPriceUnit() {
		return this.priceUnit;
	}

	public void setPriceUnit(Double priceUnit) {
		this.priceUnit = priceUnit;
	}

	public Double getExceedVolumePrice() {
		return this.exceedVolumePrice;
	}

	public void setExceedVolumePrice(Double exceedVolumePrice) {
		this.exceedVolumePrice = exceedVolumePrice;
	}

	public Double getExceedDisPrice() {
		return this.exceedDisPrice;
	}

	public void setExceedDisPrice(Double exceedDisPrice) {
		this.exceedDisPrice = exceedDisPrice;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getExceedVolume() {
		return this.exceedVolume;
	}

	public void setExceedVolume(String exceedVolume) {
		this.exceedVolume = exceedVolume;
	}

	public String getExceedDis() {
		return this.exceedDis;
	}

	public void setExceedDis(String exceedDis) {
		this.exceedDis = exceedDis;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSts() {
		return this.sts;
	}

	public void setSts(Integer sts) {
		this.sts = sts;
	}

}