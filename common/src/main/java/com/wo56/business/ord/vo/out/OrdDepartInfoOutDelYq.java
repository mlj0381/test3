package com.wo56.business.ord.vo.out;

import java.util.List;

public class OrdDepartInfoOutDelYq {
	private String batchNum;
	private String batchNumAlias;
	private String descOrgId;
	private String driverBill;
	private String driverId;
	private String driverName;
	private String plateNumber;
	private String remarks;
	private String vehicleId;
	private String vehicleLength;
	private String vehicleType;
	private String isDepart;
	private String descOrgName;
	private String vehicleTypeName;

	
	public String getVehicleTypeName() {
		return vehicleTypeName;
	}
	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}
	public String getDescOrgName() {
		return descOrgName;
	}
	public void setDescOrgName(String descOrgName) {
		this.descOrgName = descOrgName;
	}
	private List<OrderChildStowagePlanOut> items;
	
	
	public String getIsDepart() {
		return isDepart;
	}
	public void setIsDepart(String isDepart) {
		this.isDepart = isDepart;
	}
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
	public String getDescOrgId() {
		return descOrgId;
	}
	public void setDescOrgId(String descOrgId) {
		this.descOrgId = descOrgId;
	}
	public String getDriverBill() {
		return driverBill;
	}
	public void setDriverBill(String driverBill) {
		this.driverBill = driverBill;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getVehicleLength() {
		return vehicleLength;
	}
	public void setVehicleLength(String vehicleLength) {
		this.vehicleLength = vehicleLength;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public List<OrderChildStowagePlanOut> getItems() {
		return items;
	}
	public void setItems(List<OrderChildStowagePlanOut> items) {
		this.items = items;
	}
	
}
