package com.wo56.business.sche.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class ScheQueryIn extends PageInParamVO implements IParamIn{

	 private  	String receiverName ;
	 private  	String gxEndTime ;
	 private  	String endTime ;
	 private  	String beginTime ;
	 private  	String sfName;
	 private  	String sfPhone ;
	 private   int isTmall ;
	 private   int signState ;
	 private  String clientName ;
	 private  String wayBillId ;
	 private  		String receivePhone ;
	 private  		long provinceId ;
	 private  		long cityId ;
	 private  		long countyId ;
	 private  		long streetId ;
	 private  		int taskState;
	 private  		Integer   matchType;
	 private  		Integer   isService;
	 private String orderId;
	 private String inCode;
	 private Integer isQuery;
	 private Integer isGxEnd;
	 private Integer isArriveGoods;
	 private String endDate;
	 private String beginDate;
	 private String inputParamJson;
	 private String _sum;
	 
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
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
	public Integer getIsGxEnd() {
		return isGxEnd;
	}
	public void setIsGxEnd(Integer isGxEnd) {
		this.isGxEnd = isGxEnd;
	}
	public Integer getIsArriveGoods() {
		return isArriveGoods;
	}
	public void setIsArriveGoods(Integer isArriveGoods) {
		this.isArriveGoods = isArriveGoods;
	}
	public Integer getIsQuery() {
		return isQuery;
	}
	public void setIsQuery(Integer isQuery) {
		this.isQuery = isQuery;
	}
	public String getGxEndTime() {
		return gxEndTime;
	}
	public void setGxEndTime(String gxEndTime) {
		this.gxEndTime = gxEndTime;
	}
	public int getSignState() {
		return signState;
	}
	public void setSignState(int signState) {
		this.signState = signState;
	}
	public Integer getIsService() {
		return isService;
	}
	public void setIsService(Integer isService) {
		this.isService = isService;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Integer getMatchType() {
		return matchType;
	}
	public void setMatchType(Integer matchType) {
		this.matchType = matchType;
	}

	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public int getIsTmall() {
		return isTmall;
	}
	public void setIsTmall(int isTmall) {
		this.isTmall = isTmall;
	}
	public String getWayBillId() {
		return wayBillId;
	}
	public void setWayBillId(String wayBillId) {
		this.wayBillId = wayBillId;
	}
	public String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	public long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public long getCountyId() {
		return countyId;
	}
	public void setCountyId(long countyId) {
		this.countyId = countyId;
	}
	public long getStreetId() {
		return streetId;
	}
	public void setStreetId(long streetId) {
		this.streetId = streetId;
	}
	public int getTaskState() {
		return taskState;
	}
	public void setTaskState(int taskState) {
		this.taskState = taskState;
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
	public String get_sum() {
		return _sum;
	}
	public void set_sum(String _sum) {
		this._sum = _sum;
	}
	 
	 
	 
}
