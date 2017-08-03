package com.wo56.business.sche.serve.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class ServeQueryParamIn extends PageInParamVO implements IParamIn {
	private int provinceId;
	private int cityId;
	private int countyId;
	private int state;
	private int queryType;
	private String receivePhone;
	private String receiveName;
	private String wayBillId;
	private String doObjName;
	private String doTel;
	private String dutyOrgs;
	private long id;
	private long  tenantType;
	private long orgId;
	private long tenantId;
	private int flag;

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public long getTenantType() {
		return tenantType;
	}

	public void setTenantType(long tenantType) {
		this.tenantType = tenantType;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public long getTenantId() {
		return tenantId;
	}

	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.SERVE.SERVE_QUERY;
	}

	public String getDutyOrgs() {
		return dutyOrgs;
	}

	public void setDutyOrgs(String dutyOrgs) {
		this.dutyOrgs = dutyOrgs;
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

	public int getQueryType() {
		return queryType;
	}

	public void setQueryType(int queryType) {
		this.queryType = queryType;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getWayBillId() {
		return wayBillId;
	}

	public void setWayBillId(String wayBillId) {
		this.wayBillId = wayBillId;
	}

}
