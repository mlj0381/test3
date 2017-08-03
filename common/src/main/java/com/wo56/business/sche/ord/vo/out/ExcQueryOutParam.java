package com.wo56.business.sche.ord.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.framework.core.util.SysStaticDataUtil;

public class ExcQueryOutParam extends BaseOutParamVO{
	
	private String taskId;
	private String taskState;
	private String excTypeName;
	private String taskStateName;
	private String companyName;
	private String wayBillId;
	private String orderId;
	private String cityName;
	private String serverType;
	private String createTime;
	private String sfName;
	private String sfPhone;
	private String sfUserId;
	private String repairFee;
	private String installFee;
	private String branchFee;
	private String clientName;
	private String repairType;
	private String excId;
	private String repairSfName;
	private String repairSfPhone;
	private String repairSfUserId;
	private String excType;
	private String maintainTypeName;
	private String excStateName;
	private String excState;
	private String receiveName;
	private String receivePhone;
	private String notes;
	private String dutyObjName;
	private String dutyObjId;
	private String inputOrgId;
	private String serverTypeName;
	private String creatorName;
	private String creatorPhone;
	private Integer creatorType;
	
	
	
	
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getCreatorPhone() {
		return creatorPhone;
	}
	public void setCreatorPhone(String creatorPhone) {
		this.creatorPhone = creatorPhone;
	}
	public Integer getCreatorType() {
		return creatorType;
	}
	public void setCreatorType(Integer creatorType) {
		this.creatorType = creatorType;
	}
	public String getServerTypeName() {
		if(getServerType() == null && getServerType() == ""){
			return "";
		}
		return SysStaticDataUtil.getSysStaticDataCodeName("DELIVERY_TYPE", getServerType());
	}
	public void setServerTypeName(String serverTypeName) {
		this.serverTypeName = serverTypeName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getDutyObjName() {
		return dutyObjName;
	}
	public void setDutyObjName(String dutyObjName) {
		this.dutyObjName = dutyObjName;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	public String getExcState() {
		return excState;
	}
	public void setExcState(String excState) {
		this.excState = excState;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	public String getExcTypeName() {
		if(getExcType() == null || getExcType()==""){
			return excTypeName;
		}
		return SysStaticDataUtil.getSysStaticDataCodeName("EXCEPTION_TYPE", getExcType());
	}
	public void setExcTypeName(String excTypeName) {
		this.excTypeName = excTypeName;
	}
	public String getTaskStateName() {
		return taskStateName;
	}
	public void setTaskStateName(String taskStateName) {
		this.taskStateName = taskStateName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getWayBillId() {
		return wayBillId;
	}
	public void setWayBillId(String wayBillId) {
		this.wayBillId = wayBillId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getServerType() {
		return serverType;
	}
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	
	public String getSfName() {
		return sfName;
	}
	public void setSfName(String sfName) {
		this.sfName = sfName;
	}
	public String getSfPhone() {
		return sfPhone;
	}
	public void setSfPhone(String sfPhone) {
		this.sfPhone = sfPhone;
	}
	public String getSfUserId() {
		return sfUserId;
	}
	public void setSfUserId(String sfUserId) {
		this.sfUserId = sfUserId;
	}
	public String getRepairFee() {
		return repairFee;
	}
	public void setRepairFee(String repairFee) {
		this.repairFee = repairFee;
	}
	public String getInstallFee() {
		return installFee;
	}
	public void setInstallFee(String installFee) {
		this.installFee = installFee;
	}
	public String getBranchFee() {
		return branchFee;
	}
	public void setBranchFee(String branchFee) {
		this.branchFee = branchFee;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getRepairType() {
		return repairType;
	}
	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}
	public String getExcId() {
		return excId;
	}
	public void setExcId(String excId) {
		this.excId = excId;
	}
	public String getRepairSfName() {
		return repairSfName;
	}
	public void setRepairSfName(String repairSfName) {
		this.repairSfName = repairSfName;
	}
	public String getRepairSfPhone() {
		return repairSfPhone;
	}
	public void setRepairSfPhone(String repairSfPhone) {
		this.repairSfPhone = repairSfPhone;
	}
	public String getRepairSfUserId() {
		return repairSfUserId;
	}
	public void setRepairSfUserId(String repairSfUserId) {
		this.repairSfUserId = repairSfUserId;
	}
	public String getExcType() {
		return excType;
	}
	public void setExcType(String excType) {
		this.excType = excType;
	}
	public String getMaintainTypeName() {
		return maintainTypeName;
	}
	public void setMaintainTypeName(String maintainTypeName) {
		this.maintainTypeName = maintainTypeName;
	}
	public String getExcStateName() {
		return excStateName;
	}
	public void setExcStateName(String excStateName) {
		this.excStateName = excStateName;
	}
	
	public String getInputOrgId() {
		return inputOrgId;
	}
	public void setInputOrgId(String inputOrgId) {
		this.inputOrgId = inputOrgId;
	}
	public String getDutyObjId() {
		return dutyObjId;
	}
	public void setDutyObjId(String dutyObjId) {
		this.dutyObjId = dutyObjId;
	}
	
	
	
}
