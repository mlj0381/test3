package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class QueryCmSfUserInfoIn extends PageInParamVO implements IParamIn {

	private String productId;
	private String provinceId;
	private String cityId;
	private String valueServiceId;
	private String cooperationType;
	private String commonServiceId;
	private String sfUserName;
	private String sfUserAcct;
	private String tenantCode ;
	private long tenantId  ;
	private int auditState;
	private Integer superManage;
	private Integer isArea;
	
	
	
	
	public Integer getIsArea() {
		return isArea;
	}


	public void setIsArea(Integer isArea) {
		this.isArea = isArea;
	}


	public Integer getSuperManage() {
		return superManage;
	}


	public void setSuperManage(Integer superManage) {
		this.superManage = superManage;
	}


	public String getCooperationType() {
		return cooperationType;
	}


	public void setCooperationType(String cooperationType) {
		this.cooperationType = cooperationType;
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
		return InterFacesCodeConsts.CM.SCHE_SF_USER_INF0_QUERY;
	}

}
