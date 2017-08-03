package com.wo56.business.ord.vo.out;

import java.util.Date;

import com.framework.core.util.DateUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.SysTenantDefCacheDataUtil;

@SuppressWarnings("serial")
public class OrderFeeTipParamOut{
	private String[] noFiled; //翻译过后前端不需要的字段
	  
	  public String[] getNoFiled() {
		return noFiled;
	  }
	  public void setNoFiled(String[] noFiled) {
		this.noFiled = noFiled;
	  }
	private Long id;
	private String ruleName;
	private Long tenantId;
	private String tenantName;
	private String staCityName;
	private String desCityName;
	private String link;
	private Integer tipType;
	private String isFirst;
	private String isWeight;
	private String isFreight;
	private String isCurrencyString;
	private Date createTime;
	private String createTimeString;
	private Integer isCurrency;
	private Long beginCity;
	private Long endCity;
	private Long minWeightFee;
	private String minWeightFeeString;
	private Long maxWeightFee;
	private String maxWeightFeeString;
	private Long minVolumeFee;
	private String minVolumeFeeString;
	private Long maxVolumeFee;
	private String maxVolumeFeeString;
	private Long minFee;
	private String minFeeString;
	private Long numberFee;
	private String numberFeeString;
	private String beginOrgName;
	private String endOrgName;
	
	
	
	public String getBeginOrgName() {
		return beginOrgName;
	}
	public void setBeginOrgName(String beginOrgName) {
		this.beginOrgName = beginOrgName;
	}
	public String getEndOrgName() {
		return endOrgName;
	}
	public void setEndOrgName(String endOrgName) {
		this.endOrgName = endOrgName;
	}
	public Long getNumberFee() {
		return numberFee;
	}
	public void setNumberFee(Long numberFee) {
		this.numberFee = numberFee;
	}
	public String getNumberFeeString() {
		if(numberFee != null && numberFee > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(numberFee, 2));
		}
		return numberFeeString;
	}
	public void setNumberFeeString(String numberFeeString) {
		this.numberFeeString = numberFeeString;
	}
	public Long getMinWeightFee() {
		return minWeightFee;
	}
	public void setMinWeightFee(Long minWeightFee) {
		this.minWeightFee = minWeightFee;
	}
	public String getMinWeightFeeString() {
		if(minWeightFee != null && minWeightFee > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(minWeightFee, 2));
		}
		return minWeightFeeString;
	}
	public void setMinWeightFeeString(String minWeightFeeString) {
		this.minWeightFeeString = minWeightFeeString;
	}
	public Long getMaxWeightFee() {
		return maxWeightFee;
	}
	public void setMaxWeightFee(Long maxWeightFee) {
		this.maxWeightFee = maxWeightFee;
	}
	public String getMaxWeightFeeString() {
		if(maxWeightFee != null && maxWeightFee > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(maxWeightFee, 2));
		}
		return maxWeightFeeString;
	}
	public void setMaxWeightFeeString(String maxWeightFeeString) {
		this.maxWeightFeeString = maxWeightFeeString;
	}
	public Long getMinVolumeFee() {
		return minVolumeFee;
	}
	public void setMinVolumeFee(Long minVolumeFee) {
		this.minVolumeFee = minVolumeFee;
	}
	public String getMinVolumeFeeString() {
		if(minVolumeFee != null && minVolumeFee > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(minVolumeFee, 2));
		}
		return minVolumeFeeString;
	}
	public void setMinVolumeFeeString(String minVolumeFeeString) {
		this.minVolumeFeeString = minVolumeFeeString;
	}
	public Long getMaxVolumeFee() {
		return maxVolumeFee;
	}
	public void setMaxVolumeFee(Long maxVolumeFee) {
		this.maxVolumeFee = maxVolumeFee;
	}
	public String getMaxVolumeFeeString() {
		if(maxVolumeFee != null && maxVolumeFee > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(maxVolumeFee, 2));
		}
		return maxVolumeFeeString;
	}
	public void setMaxVolumeFeeString(String maxVolumeFeeString) {
		this.maxVolumeFeeString = maxVolumeFeeString;
	}
	public Long getMinFee() {
		return minFee;
	}
	public void setMinFee(Long minFee) {
		this.minFee = minFee;
	}
	public String getMinFeeString() {
		if(minFee != null && minFee > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(minFee, 2));
		}
		return minFeeString;
	}
	public void setMinFeeString(String minFeeString) {
		this.minFeeString = minFeeString;
	}
	public Long getBeginCity() {
		return beginCity;
	}
	public void setBeginCity(Long beginCity) {
		this.beginCity = beginCity;
	}
	public Long getEndCity() {
		return endCity;
	}
	public void setEndCity(Long endCity) {
		this.endCity = endCity;
	}
	public Integer getIsCurrency() {
		return isCurrency;
	}
	public void setIsCurrency(Integer isCurrency) {
		this.isCurrency = isCurrency;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getTenantName() {
		if(tenantId != null&&tenantId > 0){
			return SysTenantDefCacheDataUtil.getTenantName(tenantId);
		}
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getStaCityName() {
		if(beginCity != null && beginCity > 0){
			return SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(beginCity)).getName();
		}
		return staCityName;
	}
	public void setStaCityName(String staCityName) {
		this.staCityName = staCityName;
	}
	public String getDesCityName() {
		if(endCity != null && endCity > 0){
			return SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(endCity)).getName();
		}
		return desCityName;
	}
	public void setDesCityName(String desCityName) {
		this.desCityName = desCityName;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Integer getTipType() {
		return tipType;
	}
	public void setTipType(Integer tipType) {
		this.tipType = tipType;
	}
	public String getIsFirst() {
		if(tipType != null && tipType == 1){
			return "是";
		}
		return isFirst;
	}
	public void setIsFirst(String isFirst) {
		this.isFirst = isFirst;
	}
	public String getIsWeight() {
		if(tipType != null && tipType == 2){
			return "是";
		}
		return isWeight;
	}
	public void setIsWeight(String isWeight) {
		this.isWeight = isWeight;
	}
	public String getIsFreight() {
		if(tipType != null && tipType == 3){
			return "是";
		}
		return isFreight;
	}
	public void setIsFreight(String isFreight) {
		this.isFreight = isFreight;
	}
	public String getIsCurrencyString() {
		if(isCurrency != null && isCurrency == 1){
			return "是";
		}
		return isCurrencyString;
	}
	public void setIsCurrencyString(String isCurrencyString) {
		this.isCurrencyString = isCurrencyString;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeString() {
		if(createTime != null){
			return DateUtil.formatDate(createTime, "yyyy-MM-dd HH:mm:ss");
		}
		return createTimeString;
	}
	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}
	
}
