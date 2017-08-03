package com.wo56.business.sche.ord.vo.out;

import java.util.Date;

import com.framework.core.util.DateUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;

public class OrdSalesOrFixOut {
	private String taskId;
	private Date acceptTime;
	private String acceptTimeString;
	private String trackingNum;
	private String orderId;
	private Date pickTime;
	private String pickTimeString;
	private Date taskTime;
	private String taskTimeString;
	private Date yyTime;
	private String yyTimeString;
	private Date upFixTime;
	private String upFixTimeString;
	private String doObjName;
	private String doTel;
	private Long amount;
	private String repairFeeString;
	private String remark;
	private String repairType;
	private String repairTypeString;
	private Date signTime;
	private String signTimeString;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Date getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}
	public String getAcceptTimeString() {
		if(acceptTime != null){
			return DateUtil.formatDate(acceptTime, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	public void setAcceptTimeString(String acceptTimeString) {
		this.acceptTimeString = acceptTimeString;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getPickTime() {
		return pickTime;
	}
	public void setPickTime(Date pickTime) {
		this.pickTime = pickTime;
	}
	public String getPickTimeString() {
		if(pickTime != null){
			return DateUtil.formatDate(pickTime, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	public void setPickTimeString(String pickTimeString) {
		this.pickTimeString = pickTimeString;
	}
	public Date getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}
	public String getTaskTimeString() {
		if(taskTime != null){
			return DateUtil.formatDate(taskTime, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	public void setTaskTimeString(String taskTimeString) {
		this.taskTimeString = taskTimeString;
	}
	public Date getYyTime() {
		return yyTime;
	}
	public void setYyTime(Date yyTime) {
		this.yyTime = yyTime;
	}
	public String getYyTimeString() {
		if(yyTime != null){
			return DateUtil.formatDate(yyTime, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	public void setYyTimeString(String yyTimeString) {
		this.yyTimeString = yyTimeString;
	}
	public Date getUpFixTime() {
		return upFixTime;
	}
	public void setUpFixTime(Date upFixTime) {
		this.upFixTime = upFixTime;
	}
	public String getUpFixTimeString() {
		if(upFixTime != null){
			return DateUtil.formatDate(upFixTime, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	public void setUpFixTimeString(String upFixTimeString) {
		this.upFixTimeString = upFixTimeString;
	}
	public String getDoObjName() {
		return doObjName;
	}
	public void setDoObjName(String doObjName) {
		this.doObjName = doObjName;
	}
	public String getDoTel() {
		return doTel;
	}
	public void setDoTel(String doTel) {
		this.doTel = doTel;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getRepairFeeString() {
		if(amount != null && amount > 0){
			return CommonUtil.getDoubleFixedNew2(String.valueOf(amount));
		}
		return "";
	}
	public void setRepairFeeString(String repairFeeString) {
		this.repairFeeString = repairFeeString;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRepairType() {
		return repairType;
	}
	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}
	public String getRepairTypeString() {
		if(repairType != null && Integer.parseInt(repairType) > 0){
			return SysStaticDataUtil.getSysStaticDataCodeName("SCHE_VALUE_SERVICE_ATTR", repairType); 
		}
		return "";
	}
	public void setRepairTypeString(String repairTypeString) {
		this.repairTypeString = repairTypeString;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String getSignTimeString() {
		if(signTime != null){
			return DateUtil.formatDate(signTime, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	public void setSignTimeString(String signTimeString) {
		this.signTimeString = signTimeString;
	}
	
}
