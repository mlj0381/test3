package com.wo56.business.cm.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

/**
 * 
 * @author zjy
 * 
 * 
 * */
public class CmVehicleInfoParamOut_old extends BaseOutParamVO{
	
	/***车牌号码 */
	private String plateNumber;
	/***司机名称 */
	private String name;
	/***手机号码 */
	private String bill;
	/***手机号码 */
	private String vehicleId;
	/***体积 */
	private double actualVolume;
	/***重量 */
	private double actualWeight;
	/***车辆类型 */
	private Integer vehicleType;
	/***车辆类型 name*/
	private String vehicleTypeName;
	/***车辆状态id*/
	private Integer vehicleState;
	/***车辆状态name*/
	private String vehicleStateName;
	
	private Long id;
	private int businessType;
	
	public int getBusinessType() {
		return businessType;
	}
	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVehicleState() {
		return vehicleState;
	}
	public void setVehicleState(Integer vehicleState) {
		this.vehicleState = vehicleState;
	}
	public String getVehicleStateName() {
		return vehicleStateName;
	}
	public void setVehicleStateName(String vehicleStateName) {
		this.vehicleStateName = vehicleStateName;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getVehicleTypeName() {
		return vehicleTypeName;
	}
	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public double getActualVolume() {
		return actualVolume;
	}
	public void setActualVolume(double actualVolume) {
		this.actualVolume = actualVolume;
	}
	public double getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}
	public Integer getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}

	
}
