package com.wo56.business.sche.ord.vo.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class RepairTaskDetailOutParam extends BaseOutParamVO{
	
	private String taskId;
	private String taskState;
	private String taskStateName;
    private String azMoney;
	private String companyName;
	private String trackingNum;
	private Long orderId;
	private String receAddr;
	private String receiverBillId;
	private String receiverName;
    private String isTmail;
    private List<Map> timeList;
    private List<String> imageList;
    private String longitude;
	private String latitude;
	private String currTime;
	private String currName;
	private String remark;
	private String yyTime;
	
	
	
	
	public String getYyTime() {
		return yyTime;
	}
	public void setYyTime(String yyTime) {
		this.yyTime = yyTime;
	}
	public List<String> getImageList() {
		if(imageList==null){
			 imageList = new ArrayList();
		}
		return imageList;
	}
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
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
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	public String getIsTmail() {
		return isTmail;
	}
	public void setIsTmail(String isTmail) {
		this.isTmail = isTmail;
	}
	public List<Map> getTimeList() {
		if(timeList==null){
			timeList=new ArrayList<Map>();
		}
		return timeList;
	}
	public void setTimeList(List<Map> timeList) {
		this.timeList = timeList;
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
	public String getCurrTime() {
		return currTime;
	}
	public void setCurrTime(String currTime) {
		this.currTime = currTime;
	}
	public String getCurrName() {
		return currName;
	}
	public void setCurrName(String currName) {
		this.currName = currName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
}
