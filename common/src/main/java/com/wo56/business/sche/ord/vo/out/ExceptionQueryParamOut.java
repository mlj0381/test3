package com.wo56.business.sche.ord.vo.out;

import java.util.ArrayList;
import java.util.List;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class ExceptionQueryParamOut extends BaseOutParamVO{

	 private String excState;
	 private String excStateName;
	 private String exceptionAmount;
	 private String exceptionDes;
	 private String exceptionDeal;
	 private String exceptionId;
	 private String exceptionType;
	 private String wayBillId;
	 private String companyName;
	 private String createTime;
	 private String pickAddr;
	 private String pickPhone;
	 private String receiveName;
	 private String receiveAddr;
	 private String receivePhone;
	 private String prodInfo;//货品信息
	 private String dealResult;//处理结果
	 private List<String> exceptionImage;
	 private String dealTime;
	 private String taskTime;
	 
	 
	 
	 
	 public String getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getExceptionDeal() {
		return exceptionDeal;
	}
	public void setExceptionDeal(String exceptionDeal) {
		this.exceptionDeal = exceptionDeal;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPickAddr() {
		return pickAddr;
	}
	public void setPickAddr(String pickAddr) {
		this.pickAddr = pickAddr;
	}
	public String getPickPhone() {
		return pickPhone;
	}
	public void setPickPhone(String pickPhone) {
		this.pickPhone = pickPhone;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getReceiveAddr() {
		return receiveAddr;
	}
	public void setReceiveAddr(String receiveAddr) {
		this.receiveAddr = receiveAddr;
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
	public String getExcStateName() {
		return excStateName;
	}
	public void setExcStateName(String excStateName) {
		this.excStateName = excStateName;
	}
	public String getExceptionAmount() {
		return exceptionAmount;
	}
	public void setExceptionAmount(String exceptionAmount) {
		this.exceptionAmount = exceptionAmount;
	}
	public String getExceptionDes() {
		return exceptionDes;
	}
	public void setExceptionDes(String exceptionDes) {
		this.exceptionDes = exceptionDes;
	}
	public String getExceptionId() {
		return exceptionId;
	}
	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getWayBillId() {
		return wayBillId;
	}
	public void setWayBillId(String wayBillId) {
		this.wayBillId = wayBillId;
	}
	public List<String> getExceptionImage() {
		if(exceptionImage==null){
			exceptionImage=new ArrayList<String>();
		}
		return exceptionImage;
	}
	public void setExceptionImage(List<String> exceptionImage) {
		this.exceptionImage = exceptionImage;
	}
	public String getProdInfo() {
		return prodInfo;
	}
	public void setProdInfo(String prodInfo) {
		this.prodInfo = prodInfo;
	}
	public String getDealResult() {
		return dealResult;
	}
	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}
	 
}
