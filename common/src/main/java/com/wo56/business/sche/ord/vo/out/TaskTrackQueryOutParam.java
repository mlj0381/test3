package com.wo56.business.sche.ord.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class TaskTrackQueryOutParam extends BaseOutParamVO{
	
	private String taskId;
	private String orderState;
	private String orderStateName;
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
	private String signTime;
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
	private String branchFee;
	private String installFee;
	private String sfUserId;
	private String clientName;
	private String remark;
	private String pickVerifyBillId;
	private String branchAndInstall;
	private String signStateName;
	private String isGxEnd;
	private String gxTime;
	private String isArriveGoods;
	private String collectingMoney;
	private String freightCollect;
	private String destCity;
	private String destCounty;
	private String inputOrdTime;
	private String serviceSfUserId;
	private String serviceSfName;
	private String serviceSfTel;
	private String disTime;
	private String distributionTime;
	
	
	
	
	
	public String getDistributionTime() {
		return distributionTime;
	}
	public void setDistributionTime(String distributionTime) {
		this.distributionTime = distributionTime;
	}
	public String getServiceSfUserId() {
		return serviceSfUserId;
	}
	public void setServiceSfUserId(String serviceSfUserId) {
		this.serviceSfUserId = serviceSfUserId;
	}
	public String getServiceSfName() {
		return serviceSfName;
	}
	public void setServiceSfName(String serviceSfName) {
		this.serviceSfName = serviceSfName;
	}
	public String getServiceSfTel() {
		return serviceSfTel;
	}
	public void setServiceSfTel(String serviceSfTel) {
		this.serviceSfTel = serviceSfTel;
	}
	public String getDisTime() {
		return disTime;
	}
	public void setDisTime(String disTime) {
		this.disTime = disTime;
	}
	public String getInputOrdTime() {
		return inputOrdTime;
	}
	public void setInputOrdTime(String inputOrdTime) {
		this.inputOrdTime = inputOrdTime;
	}
	public String getDestCity() {
		return destCity;
	}
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}
	public String getDestCounty() {
		return destCounty;
	}
	public void setDestCounty(String destCounty) {
		this.destCounty = destCounty;
	}
	public String getCollectingMoney() {
		return collectingMoney;
	}
	public void setCollectingMoney(String collectingMoney) {
		this.collectingMoney = collectingMoney;
	}
	public String getFreightCollect() {
		return freightCollect;
	}
	public void setFreightCollect(String freightCollect) {
		this.freightCollect = freightCollect;
	}
	public String getIsGxEnd() {
		return isGxEnd;
	}
	public void setIsGxEnd(String isGxEnd) {
		this.isGxEnd = isGxEnd;
	}
	public String getGxTime() {
		return gxTime;
	}
	public void setGxTime(String gxTime) {
		this.gxTime = gxTime;
	}
	public String getIsArriveGoods() {
		return isArriveGoods;
	}
	public void setIsArriveGoods(String isArriveGoods) {
		this.isArriveGoods = isArriveGoods;
	}
	public String getSignStateName() {
		return signStateName;
	}
	public void setSignStateName(String signStateName) {
		this.signStateName = signStateName;
	}
	public String getBranchAndInstall() {
		return branchAndInstall;
	}
	public void setBranchAndInstall(String branchAndInstall) {
		this.branchAndInstall = branchAndInstall;
	}
	public String getPickVerifyBillId() {
		return pickVerifyBillId;
	}
	public void setPickVerifyBillId(String pickVerifyBillId) {
		this.pickVerifyBillId = pickVerifyBillId;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
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
	public String getBranchFee() {
		return branchFee;
	}
	public void setBranchFee(String branchFee) {
		this.branchFee = branchFee;
	}
	public String getInstallFee() {
		return installFee;
	}
	public void setInstallFee(String installFee) {
		this.installFee = installFee;
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
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getOrderStateName() {
		return orderStateName;
	}
	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
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
