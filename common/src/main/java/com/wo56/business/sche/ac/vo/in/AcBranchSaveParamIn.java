package com.wo56.business.sche.ac.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.IntfCodeConsts;

/***
 * @author zjy
 * 支线费用管理
 * */
public class AcBranchSaveParamIn  implements IParamIn {

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return IntfCodeConsts.AC.BRANCH_SAVE_OR_MODIFY;
	}
	/**主键 修改时需要回传 */
	private String branchId;
	private Long provinceId;
	private Long regionId;
	private Long countyId;
	private Long townId;
	private Double priceUnit;
	private Double exceedVolumePrice;
	private Double exceedDisPrice;
	private String exceedVolume;
	private String exceedDis;
	private String remark;
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
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
	public Long getTownId() {
		return townId;
	}
	public void setTownId(Long townId) {
		this.townId = townId;
	}
	public Double getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(Double priceUnit) {
		this.priceUnit = priceUnit;
	}
	public Double getExceedVolumePrice() {
		return exceedVolumePrice;
	}
	public void setExceedVolumePrice(Double exceedVolumePrice) {
		this.exceedVolumePrice = exceedVolumePrice;
	}
	public Double getExceedDisPrice() {
		return exceedDisPrice;
	}
	public void setExceedDisPrice(Double exceedDisPrice) {
		this.exceedDisPrice = exceedDisPrice;
	}
	public String getExceedVolume() {
		return exceedVolume;
	}
	public void setExceedVolume(String exceedVolume) {
		this.exceedVolume = exceedVolume;
	}
	public String getExceedDis() {
		return exceedDis;
	}
	public void setExceedDis(String exceedDis) {
		this.exceedDis = exceedDis;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
