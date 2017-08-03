package com.wo56.business.route.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class RouteAreaRelCfgIn extends PageInParamVO implements IParamIn {
	private String inCode;
	private long relId;
	private String relName;
	private Long orgId;
	private String orgName;
	private Long provinceId;
	private Long cityId;
	private Long countyId;
	private Long townId;
	private Long destOrgId;
	private Integer flag;
	private long[] selectedDistricts;
	private String relIds;
	private long[] choiceCitys;
	private int isSeaTransport;
	
	public int getIsSeaTransport() {
		return isSeaTransport;
	}

	public void setIsSeaTransport(int isSeaTransport) {
		this.isSeaTransport = isSeaTransport;
	}

	public long[] getChoiceCitys() {
		return choiceCitys;
	}

	public void setChoiceCitys(long[] choiceCitys) {
		this.choiceCitys = choiceCitys;
	}

	public String getRelIds() {
		return relIds;
	}

	public void setRelIds(String relIds) {
		this.relIds = relIds;
	}

	public long[] getSelectedDistricts() {
		return selectedDistricts;
	}

	public void setSelectedDistricts(long[] selectedDistricts) {
		this.selectedDistricts = selectedDistricts;
	}

	public long getRelId() {
		return relId;
	}

	public void setRelId(long relId) {
		this.relId = relId;
	}

	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public Long getTownId() {
		return townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}

	public Long getDestOrgId() {
		return destOrgId;
	}

	public void setDestOrgId(Long destOrgId) {
		this.destOrgId = destOrgId;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setInCode(String inCode) {
		this.inCode = inCode;
	}

	public RouteAreaRelCfgIn(String inCode) {
		this.inCode = inCode;
	}

	@Override
	public String getInCode() {
		return inCode;
	}

}
