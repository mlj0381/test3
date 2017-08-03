package com.wo56.business.sche.ord.vo.out;

import java.util.List;
import java.util.Map;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class ScheduleOutParam extends BaseOutParamVO{
	
	private String taskId;
	private String orderState;
	private String orderStateName;
	private String acceptTime;
    private String azMoney;
	private String companyName;
	private String wayBillId;
	private String orderId;
	private String pickAddr;
	private String pickBillId;
	private String pickName;
	private String pickTime;
	private String receAddr;
	private String receiverBillId;
	private String receiverName;
	private String remainingTime;
	private String serverType;
	private String taskTime;
	private String yyTime;
	private String giveFrTime;
	private String giveFrTimeOut;
	private String isAssign;
	private Integer  isTmail;
	private String OrderSourceCode;
	private String toUserName;
	private String notes;//异常描述
	private Integer isSystem;
	private Integer isTurn;
	private String toDisName;
	private String fromDisName;
	private  String arrivalDate;//到达时间
	private String longitude;
	private String latitude;
	private String pickCode;
	private String picklongitude;
	private String picklatitude;
	
	
	
	
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

	
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	private List<Map<String, String>> imageList;
	
	public List<Map<String, String>> getImageList() {
		return imageList;
	}
	public void setImageList(List<Map<String, String>> imageList) {
		this.imageList = imageList;
	}
	public Integer getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
	public Integer getIsTurn() {
		return isTurn;
	}
	public void setIsTurn(Integer isTurn) {
		this.isTurn = isTurn;
	}
	public String getToDisName() {
		return toDisName;
	}
	public void setToDisName(String toDisName) {
		this.toDisName = toDisName;
	}
	public String getFromDisName() {
		return fromDisName;
	}
	public void setFromDisName(String fromDisName) {
		this.fromDisName = fromDisName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getOrderSourceCode() {
		return OrderSourceCode;
	}
	public void setOrderSourceCode(String orderSourceCode) {
		OrderSourceCode = orderSourceCode;
	}
	public Integer getIsTmail() {
		return isTmail;
	}
	public void setIsTmail(Integer isTmail) {
		this.isTmail = isTmail;
	}
	public String getGiveFrTime() {
		return giveFrTime;
	}
	public void setGiveFrTime(String giveFrTime) {
		this.giveFrTime = giveFrTime;
	}
	public String getGiveFrTimeOut() {
		return giveFrTimeOut;
	}
	public void setGiveFrTimeOut(String giveFrTimeOut) {
		this.giveFrTimeOut = giveFrTimeOut;
	}
	public String getIsAssign() {
		return isAssign;
	}
	public void setIsAssign(String isAssign) {
		this.isAssign = isAssign;
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
