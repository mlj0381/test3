package com.wo56.business.ord.vo.in;

import java.util.Date;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdExceptionSaveIn implements IParamIn{

	/**
	 * 异常单保存
	 */
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORD_EXCEPTION_SAVE;
	}

	private Long id;
	private Long orderId;
	private Long trackingNum;
	private Long orgId;
	private Long opId;
	private Long dutyOrgId;
	private Long dutyDriverId;
	private String dutyDriverName;
	private String dutyVehicleId;
	private String plateNumber;
	private Integer type;
	private String notes;
	private Date createDate;
	private String imagePath;
	private Integer status;
	private String handleIdea;
	private String handleOpId;
	private Date handleDate;
	private String handleOpIdName;
	private Long imageId;
	
	public Long getImageId() {
		return imageId;
	}
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public Long getDutyOrgId() {
		return dutyOrgId;
	}
	public void setDutyOrgId(Long dutyOrgId) {
		this.dutyOrgId = dutyOrgId;
	}
	public Long getDutyDriverId() {
		return dutyDriverId;
	}
	public void setDutyDriverId(Long dutyDriverId) {
		this.dutyDriverId = dutyDriverId;
	}
	public String getDutyDriverName() {
		return dutyDriverName;
	}
	public void setDutyDriverName(String dutyDriverName) {
		this.dutyDriverName = dutyDriverName;
	}
	public String getDutyVehicleId() {
		return dutyVehicleId;
	}
	public void setDutyVehicleId(String dutyVehicleId) {
		this.dutyVehicleId = dutyVehicleId;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getHandleIdea() {
		return handleIdea;
	}
	public void setHandleIdea(String handleIdea) {
		this.handleIdea = handleIdea;
	}
	public String getHandleOpId() {
		return handleOpId;
	}
	public void setHandleOpId(String handleOpId) {
		this.handleOpId = handleOpId;
	}
	public Date getHandleDate() {
		return handleDate;
	}
	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}
	public String getHandleOpIdName() {
		return handleOpIdName;
	}
	public void setHandleOpIdName(String handleOpIdName) {
		this.handleOpIdName = handleOpIdName;
	}
	
	
}
