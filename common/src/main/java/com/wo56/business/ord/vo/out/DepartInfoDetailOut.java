package com.wo56.business.ord.vo.out;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class DepartInfoDetailOut extends BaseOutParamVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4890259842603517242L;
	/**批次号*/
	private long batchNum;
	/**始发网点ID */
	private long sourceOrgId;
	/**目的网点id*/
	private long descOrgId;
	/**始发网点ID */
	private String sourceOrgIdName;
	/**目的网点id*/
	private String descOrgIdName;
	/**装货时间**/
	private String loadTime;
	/**发车时间*/
	private String departTime;
	private Long loadOpId;
	private String loadOpName;
	private Long departOpId;
	private String departOpName;
	private Long arrivalVehOpId;
	private String arrivalVehOpName;
	private Date arrivalVehTime;
	private Long arrivalOpId;
	private String arrivalOpName;
	private String batchNumAlias;
	
	public String getBatchNumAlias() {
		return batchNumAlias;
	}
	public void setBatchNumAlias(String batchNumAlias) {
		this.batchNumAlias = batchNumAlias;
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
	/**运输合同*/
	private String transportContract;
	
	private Double freightDb;
	
	private Double stevedoringDb;
	
	
	public String getTransportContract() {
		return transportContract;
	}
	public void setTransportContract(String transportContract) {
		this.transportContract = transportContract;
	}
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
	private Long freight;
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
	
	
	public Long getFreight() {
		return freight;
	}
	public void setFreight(Long freight) {
		this.freight = freight;
	}
	public long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}
	private List list;
	
	
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
	public Double getFreightDb() {
		if(this.freight!=null && this.freight>0){
			return com.wo56.common.utils.CommonUtil.getDoubleFormatLongMoney(this.freight, 2);
		}
		return freightDb;
	}

	public void setFreightDb(Double freightDb) {
		this.freightDb = freightDb;
	}
	public Double getStevedoringDb() {
		
		if(this.stevedoring!=null && this.stevedoring>0){
			return com.wo56.common.utils.CommonUtil.getDoubleFormatLongMoney(this.stevedoring, 2);
		}
		
		return stevedoringDb;
	}

	public void setStevedoringDb(Double stevedoringDb) {
		this.stevedoringDb = stevedoringDb;
	}
	

}
