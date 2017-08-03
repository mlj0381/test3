package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class QueryCmContractCompanyIn extends PageInParamVO implements IParamIn {

	private String productId;
	private String provinceId;
	private String cityId;
	private String valueServiceId;
	private String commonServiceId;
	private String companyName;
	private String companyAcct;

	
	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getCompanyAcct() {
		return companyAcct;
	}


	public void setCompanyAcct(String companyAcct) {
		this.companyAcct = companyAcct;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}



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



	public String getValueServiceId() {
		return valueServiceId;
	}



	public void setValueServiceId(String valueServiceId) {
		this.valueServiceId = valueServiceId;
	}



	public String getCommonServiceId() {
		return commonServiceId;
	}



	public void setCommonServiceId(String commonServiceId) {
		this.commonServiceId = commonServiceId;
	}



	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CM.SCHE_SF_CONTRACT_COMPANY_QUERY;
	}

}
