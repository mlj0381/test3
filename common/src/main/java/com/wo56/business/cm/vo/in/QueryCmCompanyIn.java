package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class QueryCmCompanyIn extends PageInParamVO implements IParamIn {

	private String provinceId;
	private String cityId;
	private String companyName;
	private String tenantCode;

	
	public String getTenantCode() {
		return tenantCode;
	}


	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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



	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CM.SCHE_SF_COMPANY_LIST_QUERY;
	}

}
