package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class QueryCmSfListIn extends PageInParamVO implements IParamIn {

	private String productId;
	private String provinceId;
	private String cityId;
	private String valueServiceId;
	private String commonServiceId;
	private String sfUserName;
	private String sfUserAcct;
	private String tenantCode ;
	private long tenantId  ;
	private int auditState;
	private long lineOrgId;

	
	public long getLineOrgId() {
		return lineOrgId;
	}


	public void setLineOrgId(long lineOrgId) {
		this.lineOrgId = lineOrgId;
	}


	public int getAuditState() {
		return auditState;
	}


	public void setAuditState(int auditState) {
		this.auditState = auditState;
	}


	public long getTenantId() {
		return tenantId;
	}


	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}


	public String getTenantCode() {
		return tenantCode;
	}


	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
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



	public String getSfUserName() {
		return sfUserName;
	}



	public void setSfUserName(String sfUserName) {
		this.sfUserName = sfUserName;
	}



	public String getSfUserAcct() {
		return sfUserAcct;
	}



	public void setSfUserAcct(String sfUserAcct) {
		this.sfUserAcct = sfUserAcct;
	}



	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CM.SCHE_SF_LIST_QUERY;
	}

}
