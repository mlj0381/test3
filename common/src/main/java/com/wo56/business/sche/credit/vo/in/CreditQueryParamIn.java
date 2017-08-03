package com.wo56.business.sche.credit.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class CreditQueryParamIn extends PageInParamVO implements IParamIn {

	private String workerName;
	private String workerLoginAcct;
	private String tenantCode;
	private String interFacesCode ;
	
	private int provinceId ;
	private int cityId ;
	private int countyId ;
	private int isTmail ;
	private String beginDate ;
	private String endDate ;
	private String cityIdName;
	private String provinceIdName;
	private String doObjName ;
	private String doTel ;
	
	public int getIsTmail() {
		return isTmail;
	}

	public void setIsTmail(int isTmail) {
		this.isTmail = isTmail;
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

	public String getCityIdName() {
		return cityIdName;
	}

	public void setCityIdName(String cityIdName) {
		this.cityIdName = cityIdName;
	}

	public String getProvinceIdName() {
		return provinceIdName;
	}

	public void setProvinceIdName(String provinceIdName) {
		this.provinceIdName = provinceIdName;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getCountyId() {
		return countyId;
	}

	public void setCountyId(int countyId) {
		this.countyId = countyId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return this.interFacesCode;
	}
	
	public String getInterFacesCode() {
		return interFacesCode;
	}



	public void setInterFacesCode(String interFacesCode) {
		this.interFacesCode = interFacesCode;
	}



	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getWorkerLoginAcct() {
		return workerLoginAcct;
	}

	public void setWorkerLoginAcct(String workerLoginAcct) {
		this.workerLoginAcct = workerLoginAcct;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}


}
