package com.wo56.business.org.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class OrgQueryZXParamIn extends PageInParamVO implements IParamIn{

	@Override
	public String getInCode() {
		return this.inCode;
	}
	private String inCode;
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	public OrgQueryZXParamIn(String inCode) {
		super();
		this.inCode = inCode;
	};
	/** 租户Id **/
	private long tenantId;
	/** 租户编码 **/
	private String tenantCode;
	/** 租户名称 **/
	private String name;
	/** 租户联系人 **/
	private String linkMan;
	/** 租户联系人电话 **/
	private String linkPhone;
	/** 客服电话 **/
	private String csPhones;
	/** 起始省份ID **/
	private String provinceId;
	/** 目的省份ID **/
	private String provinceIds;
	/** 专线地址 **/
	private String address;
	/**是否查询自己租户*/
	private int myself;
	
	
	public int getMyself() {
		return myself;
	}
	public void setMyself(int myself) {
		this.myself = myself;
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
	public String getCsPhones() {
		return csPhones;
	}
	public void setCsPhones(String csPhones) {
		this.csPhones = csPhones;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getProvinceIds() {
		return provinceIds;
	}
	public void setProvinceIds(String provinceIds) {
		this.provinceIds = provinceIds;
	}
	
}
