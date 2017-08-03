package com.wo56.business.sche.ord.vo.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class ScheduleDetailOutParam extends BaseOutParamVO{
	
	private String taskId;
	private String taskState;
	private String taskStateName;
    private String azMoney;
	private String companyName;
	private String trackingNum;
	private Long orderId;
	private String pickAddr;
	private String pickBillId;
	private String pickName;
	private String receAddr;
	private String receiverBillId;
	private String receiverName;
    private String volume;
    private String isTmail;
    private List<GoodInfo> productList;
    private List<Map> timeList;
    private String longitude;
	private String latitude;
	private String pickCode;
	private String picklongitude;
	private String picklatitude;
	private Integer timeOutType;
	private String count;
	private String remainingTime;
	private String currTime;
	private String remark;
	private String arrivalGoodsTime;
	private String serviceTypeName;
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
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public String getArrivalGoodsTime() {
		return arrivalGoodsTime;
	}
	public void setArrivalGoodsTime(String arrivalGoodsTime) {
		this.arrivalGoodsTime = arrivalGoodsTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getCurrTime() {
		return currTime;
	}
	public void setCurrTime(String currTime) {
		this.currTime = currTime;
	}
	public List<Map> getTimeList() {
		if(timeList==null){
			timeList = new ArrayList<Map>();
		}
		return timeList;
	}
	public void setTimeList(List<Map> timeList) {
		this.timeList = timeList;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
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
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getIsTmail() {
		return isTmail;
	}
	public void setIsTmail(String isTmail) {
		this.isTmail = isTmail;
	}
	public List<GoodInfo> getProductList() {
		if(productList==null){
			 productList = new ArrayList<GoodInfo>();
		}
		return productList;
	}
	public void setProductList(List<GoodInfo> productList) {
		this.productList = productList;
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
	
	public Integer getTimeOutType() {
		return timeOutType;
	}
	public void setTimeOutType(Integer timeOutType) {
		this.timeOutType = timeOutType;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
	public String getRemainingTime() {
		return remainingTime;
	}
	public void setRemainingTime(String remainingTime) {
		this.remainingTime = remainingTime;
	}
	
	
	
	
	
	
}
