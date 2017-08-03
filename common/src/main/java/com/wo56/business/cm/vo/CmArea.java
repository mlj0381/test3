package com.wo56.business.cm.vo;

import com.framework.core.util.SysStaticDataUtil;

public class CmArea {
	private long id;
	private String clothCityName;
	private Long province;
	private String provinceName;
	private Long city;
	private String cityName;
	private Long district;
	private String districtName;
	private String address;
	private String keyWords;
	private String longitude;
	private String latitude;
	private String latAndLng;
	private Long tenantId;
	private String provinceCityDistrict;
	
	public String getProvinceCityDistrict() {
		String pcd = "";
		if (getProvinceName() != null) {
			pcd = getProvinceName();
		}
		if (getCityName() != null) {
			pcd = pcd + getCityName();
		}
		if (getDistrictName() != null) {
			pcd = pcd + getDistrictName();
		}
		return pcd;
	}
	public void setProvinceCityDistrict(String provinceCityDistrict) {
		this.provinceCityDistrict = provinceCityDistrict;
	}
	public String getProvinceName() {
		if (province != null && province > 0) {
			return SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(province)).getName();
		}
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		if (city != null && city > 0) {
			return SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(city)).getName();
		}
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDistrictName() {
		if (district != null && district > 0) {
			return SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(district)).getName();
		}
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getLatAndLng() {
		if (latitude !=null&&longitude!=null) {
			return latitude+","+longitude;
		}
		return "";
	}
	public void setLatAndLng(String latAndLng) {
		this.latAndLng = latAndLng;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClothCityName() {
		return clothCityName;
	}
	public void setClothCityName(String clothCityName) {
		this.clothCityName = clothCityName;
	}
	public Long getProvince() {
		return province;
	}
	public void setProvince(Long province) {
		this.province = province;
	}
	public Long getCity() {
		return city;
	}
	public void setCity(Long city) {
		this.city = city;
	}
	public Long getDistrict() {
		return district;
	}
	public void setDistrict(Long district) {
		this.district = district;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	
}
