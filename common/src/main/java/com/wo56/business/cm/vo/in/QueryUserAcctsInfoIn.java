package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class QueryUserAcctsInfoIn extends PageInParamVO implements IParamIn {

	/**
	 */
	private static final long serialVersionUID = 1123213L;
	private String provinceId;
	private String cityId;
	private String receiType;
	private String bankName;
	private String receiTypeName;
	private String phone;
	private String name;


	public String getProvinceId() {
		return provinceId;
	}




	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}




	public String getCityId() {
		return cityId;
	}




	public void setCityId(String cityId) {
		this.cityId = cityId;
	}




	public String getReceiType() {
		return receiType;
	}




	public void setReceiType(String receiType) {
		this.receiType = receiType;
	}




	public String getBankName() {
		return bankName;
	}




	public void setBankName(String bankName) {
		this.bankName = bankName;
	}




	public String getReceiTypeName() {
		return receiTypeName;
	}




	public void setReceiTypeName(String receiTypeName) {
		this.receiTypeName = receiTypeName;
	}




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CM.SCHE_SF_USER_ACCT_MANAGE;
	}

}
