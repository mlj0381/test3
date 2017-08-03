package com.wo56.business.sche.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class RepairScheQueryIn extends PageInParamVO implements IParamIn{

	 private  	String receiveName ;
	 private  	String clientName ;
	 private   int isTmall ;
	 private  String wayBillId ;
	 private  String receivePhone ;
	 private  String sfName ;
	 private  String sfPhone ;
	 private  long provinceId ;
	 private  long cityId ;
	 private  long countyId ;
	 private  long streetId ;
	 private  int taskState;
	 private String inCode;
	 /***调度查询类型 1 待分配 2 人工调度*/
	 private int queryType;
	 private int isService;
	 private Integer isQuery;
	 
	 
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
		public Integer getIsQuery() {
			return isQuery;
		}
		public void setIsQuery(Integer isQuery) {
			this.isQuery = isQuery;
		}
	 
	 
	public int getIsService() {
		return isService;
	}
	public void setIsService(int isService) {
		this.isService = isService;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public int getQueryType() {
		return queryType;
	}
	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
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
	 
	 
	 
}
