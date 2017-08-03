package com.wo56.business.org.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;
/**
 * @author zjy
 * （1）必传 （0）选传
 * */
public class OrgQueryParamIn extends PageInParamVO implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.BASE.ORG_QUERY;
	}
    /**网点名称 (1)*/
	private String orgName;
	/**省份id （1）*/
	private String provinceId;
	/**城市id （1）*/
	private String regionId;
	/**县区id （1）*/
	private String countyId;
	/**网点负责人（0）*/
	private String orgPrincipal;
	/**网点负责人手机（0）*/
	private String orgPrincipalPhone;
	/***网点类型 （1）*/
	private Integer orgType;
	/***经营类型（0）1 直营 2 加盟*/
	private Integer businessType;
	private String streetId;
	
	
	
	public String getStreetId() {
		return streetId;
	}
	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}
	public String getOrgPrincipal() {
		return orgPrincipal;
	}
	public void setOrgPrincipal(String orgPrincipal) {
		this.orgPrincipal = orgPrincipal;
	}
	public String getOrgPrincipalPhone() {
		return orgPrincipalPhone;
	}
	public void setOrgPrincipalPhone(String orgPrincipalPhone) {
		this.orgPrincipalPhone = orgPrincipalPhone;
	}
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getCountyId() {
		return countyId;
	}
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

}
