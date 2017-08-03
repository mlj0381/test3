package com.wo56.business.ord.vo.out;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.wo56.business.sche.ord.vo.out.DeliveryQueryOutParam;

public class DeliveryInfoDetailOut extends BaseOutParamVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4890259842603517242L;
	private long batchNum;
	private long sourceOrgId;
	private long descOrgId;
	private Long loaderId;
	private int taskNum;
	private String sourceOrgIdName;
	private String descOrgIdName;
	private Long loadOpId;
	private String loadOpName;
	private String loadTime;
	private Long departOpId;
	private String departOpName;
	private Date departTime;
	private Long arrivalVehOpId;
	private String arrivalVehOpName;
	private Date arrivalVehTime;
	private Long arrivalOpId;
	private String arrivalOpName;
	private Long collectingMoney; 
	private Long freightCollect;
	private Long branchAndInstall;
	private Long totalGetMoney;
	private Integer isGet; 
	private Integer isPay; 
	private Long totalPayMoney;
	
		
	public Long getLoaderId() {
		return loaderId;
	}
	public void setLoaderId(Long loaderId) {
		this.loaderId = loaderId;
	}
	public int getTaskNum() {
		return taskNum;
	}
	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}
	public String getLoadTime() {
		return loadTime;
	}
	public void setLoadTime(String loadTime) {
		this.loadTime = loadTime;
	}
	public Date getDepartTime() {
		return departTime;
	}
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}
	public Long getCollectingMoney() {
		return collectingMoney;
	}
	public void setCollectingMoney(Long collectingMoney) {
		this.collectingMoney = collectingMoney;
	}
	public Long getFreightCollect() {
		return freightCollect;
	}
	public void setFreightCollect(Long freightCollect) {
		this.freightCollect = freightCollect;
	}
	public Long getBranchAndInstall() {
		return branchAndInstall;
	}
	public void setBranchAndInstall(Long branchAndInstall) {
		this.branchAndInstall = branchAndInstall;
	}
	public Long getTotalGetMoney() {
		return totalGetMoney;
	}
	public void setTotalGetMoney(Long totalGetMoney) {
		this.totalGetMoney = totalGetMoney;
	}
	public Integer getIsGet() {
		return isGet;
	}
	public void setIsGet(Integer isGet) {
		this.isGet = isGet;
	}
	public Integer getIsPay() {
		return isPay;
	}
	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}
	public Long getTotalPayMoney() {
		return totalPayMoney;
	}
	public void setTotalPayMoney(Long totalPayMoney) {
		this.totalPayMoney = totalPayMoney;
	}
	public Long getLoadOpId() {
		return loadOpId;
	}
	public void setLoadOpId(Long loadOpId) {
		this.loadOpId = loadOpId;
	}
	public String getLoadOpName() {
		return loadOpName;
	}
	public void setLoadOpName(String loadOpName) {
		this.loadOpName = loadOpName;
	}
	public Long getDepartOpId() {
		return departOpId;
	}
	public void setDepartOpId(Long departOpId) {
		this.departOpId = departOpId;
	}
	public String getDepartOpName() {
		return departOpName;
	}
	public void setDepartOpName(String departOpName) {
		this.departOpName = departOpName;
	}
	public Long getArrivalVehOpId() {
		return arrivalVehOpId;
	}
	public void setArrivalVehOpId(Long arrivalVehOpId) {
		this.arrivalVehOpId = arrivalVehOpId;
	}
	public String getArrivalVehOpName() {
		return arrivalVehOpName;
	}
	public void setArrivalVehOpName(String arrivalVehOpName) {
		this.arrivalVehOpName = arrivalVehOpName;
	}
	public Date getArrivalVehTime() {
		return arrivalVehTime;
	}
	public void setArrivalVehTime(Date arrivalVehTime) {
		this.arrivalVehTime = arrivalVehTime;
	}
	public Long getArrivalOpId() {
		return arrivalOpId;
	}
	public void setArrivalOpId(Long arrivalOpId) {
		this.arrivalOpId = arrivalOpId;
	}
	public String getArrivalOpName() {
		return arrivalOpName;
	}
	public void setArrivalOpName(String arrivalOpName) {
		this.arrivalOpName = arrivalOpName;
	}
	/***到达时间*/
	private String arrivalTime;
	/***车牌号码*/
	private String plateNumber;
	/**司机姓名*/
	private String driverName;
	/**司机电话*/
	private String driverBill;
	/**装货人*/
	private String loader;
	/***批次状态*/
	private int state;
	private String stateName;
	/**票数*/
	private int orderNum;
	/**重量*/
	private double volume;
	/**体积*/
	private double weight;
	/**车辆类型*/
	private String vehicleTypeName;
	private Integer vehicleType;
	private int businessType;
	private String remarks;
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getBusinessType() {
		return businessType;
	}
	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	public Integer getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}
	/**车辆类型*/
	private long vehicleId;
	/**运费*/
	private double freight;
	private Long freightPayDot;
	private Integer payState;
	private Long driverId;
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	public Long getFreightPayDot() {
		return freightPayDot;
	}
	public void setFreightPayDot(Long freightPayDot) {
		this.freightPayDot = freightPayDot;
	}
	public Integer getPayState() {
		return payState;
	}
	public void setPayState(Integer payState) {
		this.payState = payState;
	}
	public Long getStevedoring() {
		return stevedoring;
	}
	public void setStevedoring(Long stevedoring) {
		this.stevedoring = stevedoring;
	}
	public Long getStevedoringPayDot() {
		return stevedoringPayDot;
	}
	public void setStevedoringPayDot(Long stevedoringPayDot) {
		this.stevedoringPayDot = stevedoringPayDot;
	}
	public Integer getStevedoringPayState() {
		return stevedoringPayState;
	}
	public void setStevedoringPayState(Integer stevedoringPayState) {
		this.stevedoringPayState = stevedoringPayState;
	}
	private Long stevedoring;
	private Long stevedoringPayDot;
	private Integer stevedoringPayState;
	
	
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
	public double getFreight() {
		return freight;
	}
	public void setFreight(double freight) {
		this.freight = freight;
	}
	public long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}
	private List<DeliveryQueryOutParam> list;
	
	
	public String getSourceOrgIdName() {
		return sourceOrgIdName;
	}
	public void setSourceOrgIdName(String sourceOrgIdName) {
		this.sourceOrgIdName = sourceOrgIdName;
	}
	public String getDescOrgIdName() {
		return descOrgIdName;
	}
	public void setDescOrgIdName(String descOrgIdName) {
		this.descOrgIdName = descOrgIdName;
	}
	public String getVehicleTypeName() {
		return vehicleTypeName;
	}
	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List getList() {
		if(list==null){
			 list = new ArrayList();
		}
		return list;
	}
	public void setList(List list) {
		this.list = list;
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
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
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
		public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	

}
