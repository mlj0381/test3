package com.wo56.business.cm.vo.in;

import java.util.Date;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class SaveCmVehicleInfoIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CM.SAVE_CM_VEHICLE;
	}
	private Long vehicleId;
	private String plateNumber;
	private String orgId;
	private long currentOrgId;
	private int type;
	private Long mainDriverId;
	private String mainDriverName;
	private Long secondDriverId;
	private String secondDriverName;
	private Double length;
	private Double wide;
	private Double high;
	private Double volume;
	private double actualVolume;
	private double actualWeight;
	private Integer vehicleType;
	private Date registDate;
	private Date pullDate;
	private String systemType;
	private int businessType;
	private String contractNo;
	private Integer vehicleState;
	private String bill;
	private String engineNo;
	private String frameNo;
	private String drivingPic;
	
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public String getFrameNo() {
		return frameNo;
	}
	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}
	public String getDrivingPic() {
		return drivingPic;
	}
	public void setDrivingPic(String drivingPic) {
		this.drivingPic = drivingPic;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public long getCurrentOrgId() {
		return currentOrgId;
	}
	public void setCurrentOrgId(long currentOrgId) {
		this.currentOrgId = currentOrgId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Long getMainDriverId() {
		return mainDriverId;
	}
	public void setMainDriverId(Long mainDriverId) {
		this.mainDriverId = mainDriverId;
	}
	public Long getSecondDriverId() {
		return secondDriverId;
	}
	public void setSecondDriverId(Long secondDriverId) {
		this.secondDriverId = secondDriverId;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public Double getWide() {
		return wide;
	}
	public void setWide(Double wide) {
		this.wide = wide;
	}
	public Double getHigh() {
		return high;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
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
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	public Date getPullDate() {
		return pullDate;
	}
	public void setPullDate(Date pullDate) {
		this.pullDate = pullDate;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public int getBusinessType() {
		return businessType;
	}
	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Integer getVehicleState() {
		return vehicleState;
	}
	public void setVehicleState(Integer vehicleState) {
		this.vehicleState = vehicleState;
	}
	public String getMainDriverName() {
		return mainDriverName;
	}
	public void setMainDriverName(String mainDriverName) {
		this.mainDriverName = mainDriverName;
	}
	public String getSecondDriverName() {
		return secondDriverName;
	}
	public void setSecondDriverName(String secondDriverName) {
		this.secondDriverName = secondDriverName;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public Long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	
}
