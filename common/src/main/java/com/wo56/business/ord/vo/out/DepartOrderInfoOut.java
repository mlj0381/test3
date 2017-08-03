package com.wo56.business.ord.vo.out;

import com.framework.core.inter.vo.OutParamVO;

public class DepartOrderInfoOut   extends OutParamVO{
   
	private String batchNum;
	private String transportContract;
	private String plateNumber;
	private String driverName;
	private String driverBill;
	private String sourceOrgId;
	private String descOrgId;
	private String number;
	private String departWeigth;
	private String departVolume;
	private String loadTime;
	private String departTime;
	private String arrivalTime;
	private String arrivalOpName;
	private String state;
	private String remark;
	private String departOpName;
	public String getDepartOpName() {
		return departOpName;
	}
	public void setDepartOpName(String departOpName) {
		this.departOpName = departOpName;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	
	public String getTransportContract() {
		return transportContract;
	}
	public void setTransportContract(String transportContract) {
		this.transportContract = transportContract;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
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
	public String getSourceOrgId() {
		return sourceOrgId;
	}
	public void setSourceOrgId(String sourceOrgId) {
		this.sourceOrgId = sourceOrgId;
	}
	public String getDescOrgId() {
		return descOrgId;
	}
	public void setDescOrgId(String descOrgId) {
		this.descOrgId = descOrgId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDepartWeigth() {
		return departWeigth;
	}
	public void setDepartWeigth(String departWeigth) {
		this.departWeigth = departWeigth;
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
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getArrivalOpName() {
		return arrivalOpName;
	}
	public void setArrivalOpName(String arrivalOpName) {
		this.arrivalOpName = arrivalOpName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getDepartVolume() {
		return departVolume;
	}
	public void setDepartVolume(String departVolume) {
		this.departVolume = departVolume;
	}	

}
