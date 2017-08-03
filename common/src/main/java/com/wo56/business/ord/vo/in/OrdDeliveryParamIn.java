package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;
/**
 * @author zjy 
 * 
 * (1) 必传 （0） 选传
 * 
 * */
public class OrdDeliveryParamIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.ADD_DELIVERY;
	}
	private Long collectingMoney;
	private Long freightCollect;
	private Long freightFee;
	private Long stevedoringFee;
	private Integer isGet;
	private Integer isPay;
	private String taskId;
	private String taskFee;
	private Long totalGetMoney;
	private Long totalPayMoney;
	private Integer vehicleType;
	/**订单编号 (1) 短号分割“,”**/
	private String  orderId;
	/***目的网点 （1）*/
	private long descOrgId;
	/**装车时间 （1）**/
	private String loadTime;
	/**发车时间 （1）*/
	private String departTime;
	/**到达时间 （1）**/
	private String arrivalTime;
	/**车牌号码 （1）*/
	private String plateNumber;
	/**司机姓名（1）*/
	private String driverName;
	/**司机手机（1）*/
	private String driverBill;
	/**装货人id*/
	private long loaderId;
	/***重量 （1）**/
	private double volume;
	/***体积（1）*/
	private double weight;
	/**批次号（0） 修改时需要传入**/
	private String batchNum;
	/***车辆id**/
	private Long vehicleId;
	/***freight 运费 （0）**/
	private String freight;
	private Integer businessType;
	private Integer payState;
	private Long freightPayDot;
	private Long driverId;
	private String remarks;
	private String tootalMoneys;
	private Long sfUserId;
	private String sfPhone;
	private String sfName;
	private Long branchAndInstall;
	
	
	
	public Long getBranchAndInstall() {
		return branchAndInstall;
	}
	public void setBranchAndInstall(Long branchAndInstall) {
		this.branchAndInstall = branchAndInstall;
	}
	public Long getSfUserId() {
		return sfUserId;
	}
	public void setSfUserId(Long sfUserId) {
		this.sfUserId = sfUserId;
	}
	public String getSfPhone() {
		return sfPhone;
	}
	public void setSfPhone(String sfPhone) {
		this.sfPhone = sfPhone;
	}
	public String getSfName() {
		return sfName;
	}
	public void setSfName(String sfName) {
		this.sfName = sfName;
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
	public Long getFreightFee() {
		return freightFee;
	}
	public void setFreightFee(Long freightFee) {
		this.freightFee = freightFee;
	}
	public Long getStevedoringFee() {
		return stevedoringFee;
	}
	public void setStevedoringFee(Long stevedoringFee) {
		this.stevedoringFee = stevedoringFee;
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
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskFee() {
		return taskFee;
	}
	public void setTaskFee(String taskFee) {
		this.taskFee = taskFee;
	}
	public Long getTotalGetMoney() {
		return totalGetMoney;
	}
	public void setTotalGetMoney(Long totalGetMoney) {
		this.totalGetMoney = totalGetMoney;
	}
	public Long getTotalPayMoney() {
		return totalPayMoney;
	}
	public void setTotalPayMoney(Long totalPayMoney) {
		this.totalPayMoney = totalPayMoney;
	}
	public Integer getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getTootalMoneys() {
		return tootalMoneys;
	}
	public void setTootalMoneys(String tootalMoneys) {
		this.tootalMoneys = tootalMoneys;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
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

	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public Long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public long getLoaderId() {
		return loaderId;
	}
	public void setLoaderId(long loaderId) {
		this.loaderId = loaderId;
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
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public Integer getPayState() {
		return payState;
	}
	public void setPayState(Integer payState) {
		this.payState = payState;
	}

}
