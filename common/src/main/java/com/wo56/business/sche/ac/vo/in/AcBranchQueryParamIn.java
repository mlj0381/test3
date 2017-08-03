package com.wo56.business.sche.ac.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.IntfCodeConsts;

/***
 * @author zjy
 * 支线费用管理
 * */
public class AcBranchQueryParamIn extends PageInParamVO implements IParamIn {

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return IntfCodeConsts.AC.BRANCH_QUERY;
	}
	
	private Long provinceId;
	private Long regionId;
	private Long countyId;
	private Long streetId;
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public Long getCountyId() {
		return countyId;
	}
	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
	public Long getStreetId() {
		return streetId;
	}
	public void setStreetId(Long streetId) {
		this.streetId = streetId;
	}
	
	

}
