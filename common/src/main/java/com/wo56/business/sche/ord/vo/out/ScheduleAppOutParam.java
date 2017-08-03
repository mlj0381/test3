package com.wo56.business.sche.ord.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class ScheduleAppOutParam extends BaseOutParamVO{
	
	private String taskId;
	private String orderId;
	private String taskState;
	private String taskStateName;
	private String acceptTime;
    private String azMoney;
	private String companyName;
	private String trackingNum;
	private String pickAddr;
	private String pickBillId;
	private String pickName;
	private String pickTime;
	private String receAddr;
	private String receiverBillId;
	private String receiverName;
	private String remainingTime;
	private String ServiceTypeName;
	private String taskTime;
	private String yyTime;
	private  String arrivalDate;//到达时间
	private String longitude;
	private String latitude;
	private String pickCode;
	private String picklongitude;
	private String picklatitude;
	private Integer isTmail;
	private Integer timeOutType;
	private String currName;
	private String currTime;
	private String arrivalGoodsTime;
	private String freightCollect;
	private String collectingMoney;
	
	
	
	
	public String getFreightCollect() {
		return freightCollect;
	}
	public void setFreightCollect(String freightCollect) {
		this.freightCollect = freightCollect;
	}
	public String getCollectingMoney() {
		return collectingMoney;
	}
	public void setCollectingMoney(String collectingMoney) {
		this.collectingMoney = collectingMoney;
	}
	public String getArrivalGoodsTime() {
		return arrivalGoodsTime;
	}
	public void setArrivalGoodsTime(String arrivalGoodsTime) {
		this.arrivalGoodsTime = arrivalGoodsTime;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCurrName() {
		return currName;
	}
	public void setCurrName(String currName) {
		this.currName = currName;
	}
	public String getCurrTime() {
		return currTime;
	}
	public void setCurrTime(String currTime) {
		this.currTime = currTime;
	}
	public Integer getTimeOutType() {
		return timeOutType;
	}
	public void setTimeOutType(Integer timeOutType) {
		this.timeOutType = timeOutType;
	}
	public Integer getIsTmail() {
		return isTmail;
	}
	public void setIsTmail(Integer isTmail) {
		this.isTmail = isTmail;
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
	public String getTaskStateName() {
		return taskStateName;
	}
	public void setTaskStateName(String taskStateName) {
		this.taskStateName = taskStateName;
	}
	public String getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}
	public String getAzMoney() {
		return azMoney;
	}
	public void setAzMoney(String azMoney) {
		this.azMoney = azMoney;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
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
	
	public String getServiceTypeName() {
		return ServiceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		ServiceTypeName = serviceTypeName;
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
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getPickCode() {
		return pickCode;
	}
	public void setPickCode(String pickCode) {
		this.pickCode = pickCode;
	}
	public String getPicklongitude() {
		return picklongitude;
	}
	public void setPicklongitude(String picklongitude) {
		this.picklongitude = picklongitude;
	}
	public String getPicklatitude() {
		return picklatitude;
	}
	public void setPicklatitude(String picklatitude) {
		this.picklatitude = picklatitude;
	}
	
	
	
	
	
}
