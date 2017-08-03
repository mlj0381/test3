package com.wo56.business.cm.vo;

/**
 * CmUserAreaRel entity. @author MyEclipse Persistence Tools
 */

public class CmUserAreaRel implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private Long provinceId;
	private String provinceName;
	private Long cityId;
	private String cityName;
	private Long districtId;
	private String districtName;

	// Constructors

	/** default constructor */
	public CmUserAreaRel() {
	}

	/** minimal constructor */
	public CmUserAreaRel(Long userId, Long provinceId, String provinceName) {
		this.userId = userId;
		this.provinceId = provinceId;
		this.provinceName = provinceName;
	}

	/** full constructor */
	public CmUserAreaRel(Long userId, Long provinceId, String provinceName,
			Long cityId, String cityName, Long districtId, String districtName) {
		this.userId = userId;
		this.provinceId = provinceId;
		this.provinceName = provinceName;
		this.cityId = cityId;
		this.cityName = cityName;
		this.districtId = districtId;
		this.districtName = districtName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Long getCityId() {
		return this.cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

}