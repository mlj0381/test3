package com.wo56.business.ord.vo.out;

import java.util.Date;

import com.wo56.common.utils.OraganizationCacheDataUtil;

public class OrdDeparQueryOut {
	
	private long batchNum;
	private long sourceOrgId;
	private long descOrgId;
	private Date loadTime;//配载时间
	private Date arrivalGoodsTime;//到货时间
	private Date departTime;//发车时间
	private Date arrivalTime;//到车时间
	private String plateNumber;
	private String driverName;
	private String driverBill;
	private String loader;
	private Long loaderId;
	private int state;
	private String stateName;
	private int orderNum;
	private double volume;
	private double weight;
	private Long freight;
	private int count;
	
	private String sourceOrgIdName;
	private String descOrgIdName;
	private  String vehicleDrivrName;//发车确认人
	private  String goodOpName;//到货确认人
	private  String transportContract;
	private  String batchNumAlias;
	private String remarks;
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getBatchNumAlias() {
		return batchNumAlias;
	}
	public void setBatchNumAlias(String batchNumAlias) {
		this.batchNumAlias = batchNumAlias;
	}
	public String getTransportContract() {
		return transportContract;
	}
	public void setTransportContract(String transportContract) {
		this.transportContract = transportContract;
	}
	public long getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(long batchNum) {
		this.batchNum = batchNum;
	}
	public long getSourceOrgId() {
		return sourceOrgId;
	}
	public void setSourceOrgId(long sourceOrgId) {
		this.sourceOrgId = sourceOrgId;
	}
	public long getDescOrgId() {
		return descOrgId;
	}
	public void setDescOrgId(long descOrgId) {
		this.descOrgId = descOrgId;
	}
	public Date getLoadTime() {
		return loadTime;
	}
	public void setLoadTime(Date loadTime) {
		this.loadTime = loadTime;
	}
	public Date getDepartTime() {
		return departTime;
	}
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}
	public Date getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
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
	public String getLoader() {
		return loader;
	}
	public void setLoader(String loader) {
		this.loader = loader;
	}
	public Long getLoaderId() {
		return loaderId;
	}
	public void setLoaderId(Long loaderId) {
		this.loaderId = loaderId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public Long getFreight() {
		return freight;
	}
	public void setFreight(Long freight) {
		this.freight = freight;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getVehicleDrivrName() {
		return vehicleDrivrName;
	}
	public void setVehicleDrivrName(String vehicleDrivrName) {
		this.vehicleDrivrName = vehicleDrivrName;
	}
	public String getGoodOpName() {
		return goodOpName;
	}
	public void setGoodOpName(String goodOpName) {
		this.goodOpName = goodOpName;
	}
	public String getSourceOrgIdName() {
		if(sourceOrgId>0){
			setSourceOrgIdName(OraganizationCacheDataUtil.getOrgName(sourceOrgId));
		}
		return sourceOrgIdName;
	}

	public void setSourceOrgIdName(String sourceOrgIdName) {
		this.sourceOrgIdName = sourceOrgIdName;
	}

	public String getDescOrgIdName() {
		if(descOrgId>0){
			setDescOrgIdName(OraganizationCacheDataUtil.getOrgName(descOrgId));
		}
		return descOrgIdName;
	}

	public void setDescOrgIdName(String descOrgIdName) {
		this.descOrgIdName = descOrgIdName;
	}
	public Date getArrivalGoodsTime() {
		return arrivalGoodsTime;
	}
	public void setArrivalGoodsTime(Date arrivalGoodsTime) {
		this.arrivalGoodsTime = arrivalGoodsTime;
	}
	
}
