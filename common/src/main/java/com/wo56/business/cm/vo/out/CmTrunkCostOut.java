package com.wo56.business.cm.vo.out;

import org.apache.commons.lang.StringUtils;

import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class CmTrunkCostOut{
	private String costId;
	private String costDetailId; //详情主键
	private String orgId; //归属组织ID
	private String orgIdName; //归属组织ID名称
	private String businessOrgId; //商家ID
	private String businessOrgIdName; //商家ID名称
	private String paymentType; //支付枚举
	private String paymentTypeName; //支付枚举名称
	private String costPic;   //干线费用图片ID
	private String costPicUrl; //干线费用图片半URL
	private String costPicUrlFull; //干线费用图片全URL
	private String costInstallPic; //配安费用图片ID
	private String costInstallPicUrl;  //配安费用图片半URL
	private String costInstallPicUrlFull;  //配安费用图片全URL
	private String province; //省枚举
	private String city;  //市枚举
	private String county; //区枚举
	private String provinceName; //省枚举名称
	private String cityName;  //市枚举名称
	private String countyName; //区枚举名称
	private String weightPrice; //单位分
	private String volumePrice; //单位分
	private String countPrice;  //单位分 
	private String weightPriceDouble; //单位元
	private String volumePriceDouble; //单位元
	private String countPriceDouble;  //单位元
	

	public String getCostId() {
		return costId;
	}
	public void setCostId(String costId) {
		this.costId = costId;
	}
	public String getCostDetailId() {
		return costDetailId;
	}
	public void setCostDetailId(String costDetailId) {
		this.costDetailId = costDetailId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgIdName() {
		if(StringUtils.isNotBlank(orgId)){
			setOrgIdName(OraganizationCacheDataUtil.getOrgName(Long.valueOf(orgId)));
		}
		return orgIdName;
	}
	public void setOrgIdName(String orgIdName) {
	
		this.orgIdName = orgIdName;
	}
	public String getBusinessOrgId() {
		return businessOrgId;
	}
	public void setBusinessOrgId(String businessOrgId) {
		this.businessOrgId = businessOrgId;
	}
	public String getBusinessOrgIdName() {
		if(StringUtils.isNotEmpty(businessOrgId)){
			setBusinessOrgIdName(OraganizationCacheDataUtil.getOrgName(Long.valueOf(businessOrgId)));
		}
		return businessOrgIdName;
	}
	public void setBusinessOrgIdName(String businessOrgIdName) {
		
		this.businessOrgIdName = businessOrgIdName;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentTypeName() {
		if(StringUtils.isNotEmpty(paymentType)){
			setPaymentTypeName(SysStaticDataUtil.getSysStaticData("PAYMENT_TYPE", paymentType).getCodeName());
		}
		return paymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}
	public String getCostPic() {
		return costPic;
	}
	public void setCostPic(String costPic) {
		this.costPic = costPic;
	}
	public String getCostPicUrl() {
		return costPicUrl;
	}
	public void setCostPicUrl(String costPicUrl) {
		this.costPicUrl = costPicUrl;
	}
	public String getCostPicUrlFull() {
		return costPicUrlFull;
	}
	public void setCostPicUrlFull(String costPicUrlFull) {
		this.costPicUrlFull = costPicUrlFull;
	}
	public String getCostInstallPic() {
		return costInstallPic;
	}
	public void setCostInstallPic(String costInstallPic) {
		this.costInstallPic = costInstallPic;
	}
	public String getCostInstallPicUrl() {
		return costInstallPicUrl;
	}
	public void setCostInstallPicUrl(String costInstallPicUrl) {
		this.costInstallPicUrl = costInstallPicUrl;
	}
	public String getCostInstallPicUrlFull() {
		return costInstallPicUrlFull;
	}
	public void setCostInstallPicUrlFull(String costInstallPicUrlFull) {
		this.costInstallPicUrlFull = costInstallPicUrlFull;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getProvinceName() {
		if(StringUtils.isNotEmpty(province)){
			setProvinceName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", province).getName());
		}
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		if(StringUtils.isNotEmpty(city)){
			setCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", city).getName());
		}
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountyName() {
		if(StringUtils.isNotEmpty(county)){
			setCountyName(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", county).getName());
		}
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getWeightPrice() {
		return weightPrice;
	}
	public void setWeightPrice(String weightPrice) {
		this.weightPrice = weightPrice;
	}
	public String getVolumePrice() {
		return volumePrice;
	}
	public void setVolumePrice(String volumePrice) {
		this.volumePrice = volumePrice;
	}
	public String getCountPrice() {
		return countPrice;
	}
	public void setCountPrice(String countPrice) {
		this.countPrice = countPrice;
	}
	public String getWeightPriceDouble() {
		setWeightPriceDouble(StringUtils.isNotEmpty(weightPrice) ? CommonUtil.getDoubleFixedNew2(weightPrice) : "");
		return weightPriceDouble;
	}
	public void setWeightPriceDouble(String weightPriceDouble) {
		this.weightPriceDouble = weightPriceDouble;
	}
	public String getVolumePriceDouble() {
		setVolumePriceDouble(StringUtils.isNotEmpty(volumePrice) ? CommonUtil.getDoubleFixedNew2(volumePrice) : "");
		return volumePriceDouble;
	}
	public void setVolumePriceDouble(String volumePriceDouble) {
		this.volumePriceDouble = volumePriceDouble;
	}
	public String getCountPriceDouble() {
		setCountPriceDouble(StringUtils.isNotEmpty(countPrice) ? CommonUtil.getDoubleFixedNew2(countPrice) : "");
		return countPriceDouble;
	}
	public void setCountPriceDouble(String countPriceDouble) {
		this.countPriceDouble = countPriceDouble;
	}

}
