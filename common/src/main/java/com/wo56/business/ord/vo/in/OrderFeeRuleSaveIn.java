package com.wo56.business.ord.vo.in;

import org.apache.commons.lang.StringUtils;

import com.framework.core.exception.BusinessException;
import com.wo56.common.utils.CommonUtil;

public class OrderFeeRuleSaveIn {
	private Long staFee;
	private String staFeeString;
	private Long topFee;
	private String topFeeString;
	private Long addWeight;
	private String addWeightString;
	private Long minFee;
	private String minFeeString;
	private Long minWeightFee;
	private String minWeightFeeString;
	private Long maxWeightFee;
	private String maxWeightFeeString;
	private Long minVolumeFee;
	private String minVolumeFeeString;
	private Long maxVolumeFee;
	private String maxVolumeFeeString;
	private Long numberFee;
	private String numberFeeString;
	
	private String ruleName;
	private Long tenantId;
	private Long staProvince;
	private String staProvinceName;
	private Long staCity;
	private String staCityName;
	private Long desProvince;
	private String desProvinceName;
	private Long desCity;
	private String desCityName;
	private Integer tipType;
	private Integer isCurrency;
	private Long beginOrgId;
	private Long endOrgId;
	
	
	
	public Long getBeginOrgId() {
		return beginOrgId;
	}
	public void setBeginOrgId(Long beginOrgId) {
		this.beginOrgId = beginOrgId;
	}
	public Long getEndOrgId() {
		return endOrgId;
	}
	public void setEndOrgId(Long endOrgId) {
		this.endOrgId = endOrgId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public Long getStaProvince() {
		return staProvince;
	}
	public void setStaProvince(Long staProvince) {
		this.staProvince = staProvince;
	}
	public String getStaProvinceName() {
		return staProvinceName;
	}
	public void setStaProvinceName(String staProvinceName) {
		this.staProvinceName = staProvinceName;
	}
	public Long getStaCity() {
		return staCity;
	}
	public void setStaCity(Long staCity) {
		this.staCity = staCity;
	}
	public String getStaCityName() {
		return staCityName;
	}
	public void setStaCityName(String staCityName) {
		this.staCityName = staCityName;
	}
	public Long getDesProvince() {
		return desProvince;
	}
	public void setDesProvince(Long desProvince) {
		this.desProvince = desProvince;
	}
	public String getDesProvinceName() {
		return desProvinceName;
	}
	public void setDesProvinceName(String desProvinceName) {
		this.desProvinceName = desProvinceName;
	}
	public Long getDesCity() {
		return desCity;
	}
	public void setDesCity(Long desCity) {
		this.desCity = desCity;
	}
	public String getDesCityName() {
		return desCityName;
	}
	public void setDesCityName(String desCityName) {
		this.desCityName = desCityName;
	}
	public Integer getTipType() {
		return tipType;
	}
	public void setTipType(Integer tipType) {
		this.tipType = tipType;
	}
	public Integer getIsCurrency() {
		return isCurrency;
	}
	public void setIsCurrency(Integer isCurrency) {
		this.isCurrency = isCurrency;
	}
	public Long getStaFee() {
		if(StringUtils.isNotEmpty(staFeeString)){
			if(CommonUtil.isNumber(staFeeString)){
				return CommonUtil.getStringByLong(staFeeString);
			}
		}
		return staFee;
	}
	public void setStaFee(Long staFee) {
		this.staFee = staFee;
	}
	public String getStaFeeString() {
		return staFeeString;
	}
	public void setStaFeeString(String staFeeString) {
		this.staFeeString = staFeeString;
	}
	public Long getTopFee() {
		if(StringUtils.isNotEmpty(topFeeString)){
			if(CommonUtil.isNumber(topFeeString)){
				return CommonUtil.getStringByLong(topFeeString);
			}
		}
		return topFee;
	}
	public void setTopFee(Long topFee) {
		this.topFee = topFee;
	}
	public String getTopFeeString() {
		return topFeeString;
	}
	public void setTopFeeString(String topFeeString) {
		this.topFeeString = topFeeString;
	}
	public Long getAddWeight() {
		if(StringUtils.isNotEmpty(addWeightString)){
			if(!CommonUtil.isNumber(addWeightString)){
				throw new BusinessException("请输入正确的续重费用！");
			}
			return CommonUtil.getStringByLong(addWeightString);
		}
		return addWeight;
	}
	public void setAddWeight(Long addWeight) {
		this.addWeight = addWeight;
	}
	public String getAddWeightString() {
		return addWeightString;
	}
	public void setAddWeightString(String addWeightString) {
		this.addWeightString = addWeightString;
	}
	public Long getMinFee() {
		if(StringUtils.isNotEmpty(minFeeString)){
			if(CommonUtil.isNumber(minFeeString)){
				return CommonUtil.getStringByLong(minFeeString);
			}
		}
		return minFee;
	}
	public void setMinFee(Long minFee) {
		this.minFee = minFee;
	}
	public String getMinFeeString() {
		return minFeeString;
	}
	public void setMinFeeString(String minFeeString) {
		this.minFeeString = minFeeString;
	}
	public Long getMinWeightFee() {
		if(StringUtils.isNotEmpty(minWeightFeeString)){
			if(CommonUtil.isNumber(minWeightFeeString)){
				return CommonUtil.getStringByLong(minWeightFeeString);
			}
		}
		return minWeightFee;
	}
	public void setMinWeightFee(Long minWeightFee) {
		this.minWeightFee = minWeightFee;
	}
	public String getMinWeightFeeString() {
		return minWeightFeeString;
	}
	public void setMinWeightFeeString(String minWeightFeeString) {
		this.minWeightFeeString = minWeightFeeString;
	}
	public Long getMaxWeightFee() {
		if(StringUtils.isNotEmpty(maxWeightFeeString)){
			if(CommonUtil.isNumber(maxWeightFeeString)){
				return CommonUtil.getStringByLong(maxWeightFeeString);
			}
		}
		return maxWeightFee;
	}
	public void setMaxWeightFee(Long maxWeightFee) {
		this.maxWeightFee = maxWeightFee;
	}
	public String getMaxWeightFeeString() {
		return maxWeightFeeString;
	}
	public void setMaxWeightFeeString(String maxWeightFeeString) {
		this.maxWeightFeeString = maxWeightFeeString;
	}
	public Long getMinVolumeFee() {
		if(StringUtils.isNotEmpty(minVolumeFeeString)){
			if(CommonUtil.isNumber(minVolumeFeeString)){
				return CommonUtil.getStringByLong(minVolumeFeeString);
			}
		}
		return minVolumeFee;
	}
	public void setMinVolumeFee(Long minVolumeFee) {
		this.minVolumeFee = minVolumeFee;
	}
	public String getMinVolumeFeeString() {
		return minVolumeFeeString;
	}
	public void setMinVolumeFeeString(String minVolumeFeeString) {
		this.minVolumeFeeString = minVolumeFeeString;
	}
	public Long getMaxVolumeFee() {
		if(StringUtils.isNotEmpty(maxVolumeFeeString)){
			if(CommonUtil.isNumber(maxVolumeFeeString)){
				return CommonUtil.getStringByLong(maxVolumeFeeString);
			}
		}
		return maxVolumeFee;
	}
	public void setMaxVolumeFee(Long maxVolumeFee) {
		this.maxVolumeFee = maxVolumeFee;
	}
	public String getMaxVolumeFeeString() {
		return maxVolumeFeeString;
	}
	public void setMaxVolumeFeeString(String maxVolumeFeeString) {
		this.maxVolumeFeeString = maxVolumeFeeString;
	}
	public Long getNumberFee() {
		if(StringUtils.isNotEmpty(numberFeeString)){
			if(CommonUtil.isNumber(numberFeeString)){
				return CommonUtil.getStringByLong(numberFeeString);
			}
		}
		return numberFee;
	}
	public void setNumberFee(Long numberFee) {
		this.numberFee = numberFee;
	}
	public String getNumberFeeString() {
		return numberFeeString;
	}
	public void setNumberFeeString(String numberFeeString) {
		this.numberFeeString = numberFeeString;
	}
	
}
