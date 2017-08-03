package com.wo56.business.sche.timeout.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class TimeOutQueryIn extends PageInParamVO implements IParamIn{

	 private  	String sfPhone ;
	 private String dealIdea;
	 private  	long timeOutId ;
	 private  	String sfName ;
	 private  String wayBillId ;
	 private  		Integer taskState;
	 private  		Integer dealState;
	 private  		Integer timeOutType;
	 private String inCode;
	 private String inputParamJson;
	 
	 
	 
	public String getDealIdea() {
		return dealIdea;
	}
	public void setDealIdea(String dealIdea) {
		this.dealIdea = dealIdea;
	}
	public long getTimeOutId() {
		return timeOutId;
	}
	public void setTimeOutId(long timeOutId) {
		this.timeOutId = timeOutId;
	}
	public String getSfPhone() {
		return sfPhone;
	}
	public void setSfPhone(String sfPhone) {
		this.sfPhone = sfPhone;
	}
	public String getSfName() {
		return sfName;
	}
	public void setSfName(String sfName) {
		this.sfName = sfName;
	}
	public String getWayBillId() {
		return wayBillId;
	}
	public void setWayBillId(String wayBillId) {
		this.wayBillId = wayBillId;
	}
	public Integer getTaskState() {
		return taskState;
	}
	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
	}
	public Integer getDealState() {
		return dealState;
	}
	public void setDealState(Integer dealState) {
		this.dealState = dealState;
	}
	public Integer getTimeOutType() {
		return timeOutType;
	}
	public void setTimeOutType(Integer timeOutType) {
		this.timeOutType = timeOutType;
	}
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	public String getInputParamJson() {
		return inputParamJson;
	}
	public void setInputParamJson(String inputParamJson) {
		this.inputParamJson = inputParamJson;
	}
	
	
}
