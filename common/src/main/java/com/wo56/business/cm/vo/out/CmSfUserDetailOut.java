package com.wo56.business.cm.vo.out;

import java.util.List;
import java.util.Map;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class CmSfUserDetailOut  extends BaseOutParamVO{

	private static final long serialVersionUID = 232123123217772L;

	private String name;
	private String phone;
	private String members;
	private String largestAcceptOrder;
	private String vehicleNums;
	private String provinceName;
	private String provinceId;
	private String cityName;
	private String cityId;
	private String districtIds;
	private String districtNames;
	private String storeAddr;
	private String storeEand;
	private String storeNand;
	private String storeSquare;
	private String cooperationTypeName;
	private long cooperationTypeId;
	private long commonServiceId;
	private String commonServiceName;
	private String commonChildServiceId;
	private String commonChildServiceName;
	private List<Map> showSfServices;
	private List<Map> valueService;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMembers() {
		return members;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	public String getLargestAcceptOrder() {
		return largestAcceptOrder;
	}
	public void setLargestAcceptOrder(String largestAcceptOrder) {
		this.largestAcceptOrder = largestAcceptOrder;
	}
	public String getVehicleNums() {
		return vehicleNums;
	}
	public void setVehicleNums(String vehicleNums) {
		this.vehicleNums = vehicleNums;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getDistrictIds() {
		return districtIds;
	}
	public void setDistrictIds(String districtIds) {
		this.districtIds = districtIds;
	}
	public String getDistrictNames() {
		return districtNames;
	}
	public void setDistrictNames(String districtNames) {
		this.districtNames = districtNames;
	}
	public String getStoreAddr() {
		return storeAddr;
	}
	public void setStoreAddr(String storeAddr) {
		this.storeAddr = storeAddr;
	}
	public String getStoreEand() {
		return storeEand;
	}
	public void setStoreEand(String storeEand) {
		this.storeEand = storeEand;
	}
	public String getStoreNand() {
		return storeNand;
	}
	public void setStoreNand(String storeNand) {
		this.storeNand = storeNand;
	}
	public String getStoreSquare() {
		return storeSquare;
	}
	public void setStoreSquare(String storeSquare) {
		this.storeSquare = storeSquare;
	}
	public String getCooperationTypeName() {
		return cooperationTypeName;
	}
	public void setCooperationTypeName(String cooperationTypeName) {
		this.cooperationTypeName = cooperationTypeName;
	}
	public long getCooperationTypeId() {
		return cooperationTypeId;
	}
	public void setCooperationTypeId(long cooperationTypeId) {
		this.cooperationTypeId = cooperationTypeId;
	}
	public long getCommonServiceId() {
		return commonServiceId;
	}
	public void setCommonServiceId(long commonServiceId) {
		this.commonServiceId = commonServiceId;
	}
	public String getCommonServiceName() {
		return commonServiceName;
	}
	public void setCommonServiceName(String commonServiceName) {
		this.commonServiceName = commonServiceName;
	}
	public String getCommonChildServiceId() {
		return commonChildServiceId;
	}
	public void setCommonChildServiceId(String commonChildServiceId) {
		this.commonChildServiceId = commonChildServiceId;
	}
	public String getCommonChildServiceName() {
		return commonChildServiceName;
	}
	public void setCommonChildServiceName(String commonChildServiceName) {
		this.commonChildServiceName = commonChildServiceName;
	}
	public List<Map> getShowSfServices() {
		return showSfServices;
	}
	public void setShowSfServices(List<Map> showSfServices) {
		this.showSfServices = showSfServices;
	}
	public List<Map> getValueService() {
		return valueService;
	}
	public void setValueService(List<Map> valueService) {
		this.valueService = valueService;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
