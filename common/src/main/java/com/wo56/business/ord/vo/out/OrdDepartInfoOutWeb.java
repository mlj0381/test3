package com.wo56.business.ord.vo.out;

import java.util.List;

public class OrdDepartInfoOutWeb {
	private String batchNum;
	private String batchNumAlias;
	private String state;
	private String loadOpName;
	private String loadTime;
	private String departTime;
	private String sourceOrgName;
	private String descOrgName;
	private String arrivalVehTime;
	private String driverName;
	private String driverBill;
	private String plateNumber;
	private String vehicleType;
	private String volume;
	private String weight;
	private String totalFee;
	private String collectMoney;
	
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getCollectMoney() {
		return collectMoney;
	}
	public void setCollectMoney(String collectMoney) {
		this.collectMoney = collectMoney;
	}
	private List<OrderDepartChildOutWeb> childList;
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getBatchNumAlias() {
		return batchNumAlias;
	}
	public void setBatchNumAlias(String batchNumAlias) {
		this.batchNumAlias = batchNumAlias;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLoadOpName() {
		return loadOpName;
	}
	public void setLoadOpName(String loadOpName) {
		this.loadOpName = loadOpName;
	}
	public String getLoadTime() {
		return loadTime;
	}
	public void setLoadTime(String loadTime) {
		this.loadTime = loadTime;
	}
	public String getDepartTime() {
		return departTime;
	}
	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}
	public String getSourceOrgName() {
		return sourceOrgName;
	}
	public void setSourceOrgName(String sourceOrgName) {
		this.sourceOrgName = sourceOrgName;
	}
	public String getDescOrgName() {
		return descOrgName;
	}
	public void setDescOrgName(String descOrgName) {
		this.descOrgName = descOrgName;
	}
	public String getArrivalVehTime() {
		return arrivalVehTime;
	}
	public void setArrivalVehTime(String arrivalVehTime) {
		this.arrivalVehTime = arrivalVehTime;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverBill() {
		return driverBill;
	}
	public void setDriverBill(String driverBill) {
		this.driverBill = driverBill;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public List<OrderDepartChildOutWeb> getChildList() {
		return childList;
	}
	public void setChildList(List<OrderDepartChildOutWeb> childList) {
		this.childList = childList;
	}
	
}
