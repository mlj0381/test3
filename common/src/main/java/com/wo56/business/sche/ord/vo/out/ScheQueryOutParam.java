package com.wo56.business.sche.ord.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class ScheQueryOutParam extends BaseOutParamVO{
	
	private String taskId;
	private String taskState;
	private String taskStateName;
	private String acceptTime;
	private String companyName;
	private String wayBillId;
	private String orderId;
	private String pickAddr;
	private String pickBillId;
	private String pickName;
	private String pickTime;
	private String cityName;
	private String receAddr;
	private String receiverBillId;
	private String receiverName;
	private String serverType;
	private String taskTime;
	private String yyTime;
	private String upFixTime;
	private String isTmail;
	private String products;
	private String count;
	private String volumes;
	private String weight;
	private String GxEndTime;
	private String remainingTime;
	private String sfName;
	private String sfPhone;
	private String sfUserId;
	private String repairFee;
	private String clientName;
	private String distance;
	private String remark;
	private String repairType;
	private String signTime;
	private String excId;
	
	
	
	public String getExcId() {
		return excId;
	}
	public void setExcId(String excId) {
		this.excId = excId;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	public String getTaskStateName() {
		return taskStateName;
	}
	public void setTaskStateName(String taskStateName) {
		this.taskStateName = taskStateName;
	}
	public String getRepairType() {
		return repairType;
	}
	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}
	public String getUpFixTime() {
		return upFixTime;
	}
	public void setUpFixTime(String upFixTime) {
		this.upFixTime = upFixTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPickAddr() {
		return pickAddr;
	}
	public void setPickAddr(String pickAddr) {
		this.pickAddr = pickAddr;
	}
	public String getPickBillId() {
		return pickBillId;
	}
	public void setPickBillId(String pickBillId) {
		this.pickBillId = pickBillId;
	}
	public String getPickName() {
		return pickName;
	}
	public void setPickName(String pickName) {
		this.pickName = pickName;
	}
	public String getPickTime() {
		return pickTime;
	}
	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getRepairFee() {
		return repairFee;
	}
	public void setRepairFee(String repairFee) {
		this.repairFee = repairFee;
	}
	public String getIsTmail() {
		return isTmail;
	}
	public void setIsTmail(String isTmail) {
		this.isTmail = isTmail;
	}
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getVolumes() {
		return volumes;
	}
	public void setVolumes(String volumes) {
		this.volumes = volumes;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getGxEndTime() {
		return GxEndTime;
	}
	public void setGxEndTime(String gxEndTime) {
		GxEndTime = gxEndTime;
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
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
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
//	public String getPickAddr() {
//		return pickAddr;
//	}
//	public void setPickAddr(String pickAddr) {
//		this.pickAddr = pickAddr;
//	}
//	public String getPickBillId() {
//		return pickBillId;
//	}
//	public void setPickBillId(String pickBillId) {
//		this.pickBillId = pickBillId;
//	}
//	public String getPickName() {
//		return pickName;
//	}
//	public void setPickName(String pickName) {
//		this.pickName = pickName;
//	}
//	public String getPickTime() {
//		return pickTime;
//	}
//	public void setPickTime(String pickTime) {
//		this.pickTime = pickTime;
//	}
	public String getReceAddr() {
		return receAddr;
	}
	public void setReceAddr(String receAddr) {
		this.receAddr = receAddr;
	}
	public String getReceiverBillId() {
		return receiverBillId;
	}
	public void setReceiverBillId(String receiverBillId) {
		this.receiverBillId = receiverBillId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getRemainingTime() {
		return remainingTime;
	}
	public void setRemainingTime(String remainingTime) {
		this.remainingTime = remainingTime;
	}
	public String getServerType() {
		return serverType;
	}
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	public String getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}
	public String getYyTime() {
		return yyTime;
	}
	public void setYyTime(String yyTime) {
		this.yyTime = yyTime;
	}
	
	
}
