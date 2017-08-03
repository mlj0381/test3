package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;
/**
 * @author zjy 
 * 
 * (1) 必传 （0） 选传
 * 
 * */
public class OrdMatchVeParamTransitOtherIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.STOWAGE_CAR_TARANSIT_OTHER;
	}
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
	private double voluem;
	/***体积（1）*/
	private double weight;
	/**批次号（0） 修改时需要传入**/
	private String batchNum;
	/***车辆id**/
	private Long vehicleId;
	/***freight 运费 （0）**/
	private String freight;
	private Integer businessType;
	private Integer VehicleType;
	private Integer payState;
	private int businessType2; // //送货类型 1:普通送货上门 2:中转送货上门
	private int isSend; // 1:发送短信 2：不发送短信
	
	public int getIsSend() {
		return isSend;
	}
	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}
	public int getBusinessType2() {
		return businessType2;
	}
	public void setBusinessType2(int businessType2) {
		this.businessType2 = businessType2;
	}
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
	public double getVoluem() {
		return voluem;
	}
	public void setVoluem(double voluem) {
		this.voluem = voluem;
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
	public Integer getVehicleType() {
		return VehicleType;
	}
	public void setVehicleType(Integer vehicleType) {
		VehicleType = vehicleType;
	}
	public Integer getPayState() {
		return payState;
	}
	public void setPayState(Integer payState) {
		this.payState = payState;
	}

}
