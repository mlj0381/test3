package com.wo56.business.sche.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class ExcQueryIn extends PageInParamVO implements IParamIn{

	 private  	String receiveName ;
	 private   int excState ;
	 private  String wayBillId ;
	 private  		String receivePhone ;
	 private  		long provinceId ;
	 private  		long cityId ;
	 private  		long countyId ;
	 private  		long streetId ;
	 private  		int taskState;
	 private  		String  excType;
	 private  		Integer  maintainType;
	 private String inCode;
	 
	 
	public Integer getMaintainType() {
		return maintainType;
	}
	public void setMaintainType(Integer maintainType) {
		this.maintainType = maintainType;
	}
	public String getExcType() {
		return excType;
	}
	public void setExcType(String excType) {
		this.excType = excType;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	
	public int getExcState() {
		return excState;
	}
	public void setExcState(int excState) {
		this.excState = excState;
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
