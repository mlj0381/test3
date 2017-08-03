package com.wo56.business.ord.vo;

import java.util.Date;

import com.wo56.common.utils.CommonUtil;

/**
 * OrderFeeRule entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class OrderFeeRule implements java.io.Serializable {

	// Fields

	private Long id;
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
	private Long staFee;
	private String staFeeString;
	private Long topFee;
	private String topFeeString;
	private String first;
	private Long addWeight;
	private String addWeightString;
	private String percentage;
	private Integer ruleType;
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
	private Date createTime;
	private String createTimeString;
	private Integer isCurrency;
	private Long beginOrgId;
	private Long endOrgId;
	private Long towardsId;
	
	
	// Constructors

	public Long getTowardsId() {
		return towardsId;
	}

	public void setTowardsId(Long towardsId) {
		this.towardsId = towardsId;
	}

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

	public String getStaFeeString() {
		if(staFee != null && staFee > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(staFee, 2));
		}
		return staFeeString;
	}

	public void setStaFeeString(String staFeeString) {
		this.staFeeString = staFeeString;
	}

	public String getTopFeeString() {
		if(topFee != null && topFee > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(topFee, 2));
		}
		return topFeeString;
	}

	public void setTopFeeString(String topFeeString) {
		this.topFeeString = topFeeString;
	}

	public String getAddWeightString() {
		if(addWeight != null && addWeight > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(addWeight, 2));
		}
		return addWeightString;
	}

	public void setAddWeightString(String addWeightString) {
		this.addWeightString = addWeightString;
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

	public String getMinWeightFeeString() {
		if(minWeightFee != null && minWeightFee > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(minWeightFee, 2));
		}
		return minWeightFeeString;
	}

	public void setMinWeightFeeString(String minWeightFeeString) {
		this.minWeightFeeString = minWeightFeeString;
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

	public String getMinVolumeFeeString() {
		if(minVolumeFee != null && minVolumeFee > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(minVolumeFee, 2));
		}
		return minVolumeFeeString;
	}

	public void setMinVolumeFeeString(String minVolumeFeeString) {
		this.minVolumeFeeString = minVolumeFeeString;
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

	public String getNumberFeeString() {
		if(numberFee != null && numberFee > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(numberFee, 2));
		}
		return numberFeeString;
	}

	public void setNumberFeeString(String numberFeeString) {
		this.numberFeeString = numberFeeString;
	}

	public String getCreateTimeString() {
		return createTimeString;
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	public Integer getIsCurrency() {
		return isCurrency;
	}

	public void setIsCurrency(Integer isCurrency) {
		this.isCurrency = isCurrency;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/** default constructor */
	public OrderFeeRule() {
	}

	/** full constructor */
	public OrderFeeRule(String ruleName, Long tenantId, Long staProvince,
			String staProvinceName, Long staCity, String staCityName,
			Long desProvince, String desProvinceName, Long desCity,
			String desCityName, Integer tipType, Long staFee, Long topFee,
			String first, Long addWeight, String percentage,
			Integer ruleType, Long minFee, Long minWeightFee,
			Long maxWeightFee, Long minVolumeFee, Long maxVolumeFee,
			Long numberFee) {
		this.ruleName = ruleName;
		this.tenantId = tenantId;
		this.staProvince = staProvince;
		this.staProvinceName = staProvinceName;
		this.staCity = staCity;
		this.staCityName = staCityName;
		this.desProvince = desProvince;
		this.desProvinceName = desProvinceName;
		this.desCity = desCity;
		this.desCityName = desCityName;
		this.tipType = tipType;
		this.staFee = staFee;
		this.topFee = topFee;
		this.first = first;
		this.addWeight = addWeight;
		this.percentage = percentage;
		this.ruleType = ruleType;
		this.minFee = minFee;
		this.minWeightFee = minWeightFee;
		this.maxWeightFee = maxWeightFee;
		this.minVolumeFee = minVolumeFee;
		this.maxVolumeFee = maxVolumeFee;
		this.numberFee = numberFee;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Long getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Long getStaProvince() {
		return this.staProvince;
	}

	public void setStaProvince(Long staProvince) {
		this.staProvince = staProvince;
	}

	public String getStaProvinceName() {
		return this.staProvinceName;
	}

	public void setStaProvinceName(String staProvinceName) {
		this.staProvinceName = staProvinceName;
	}

	public Long getStaCity() {
		return this.staCity;
	}

	public void setStaCity(Long staCity) {
		this.staCity = staCity;
	}

	public String getStaCityName() {
		return this.staCityName;
	}

	public void setStaCityName(String staCityName) {
		this.staCityName = staCityName;
	}

	public Long getDesProvince() {
		return this.desProvince;
	}

	public void setDesProvince(Long desProvince) {
		this.desProvince = desProvince;
	}

	public String getDesProvinceName() {
		return this.desProvinceName;
	}

	public void setDesProvinceName(String desProvinceName) {
		this.desProvinceName = desProvinceName;
	}

	public Long getDesCity() {
		return this.desCity;
	}

	public void setDesCity(Long desCity) {
		this.desCity = desCity;
	}

	public String getDesCityName() {
		return this.desCityName;
	}

	public void setDesCityName(String desCityName) {
		this.desCityName = desCityName;
	}

	public Integer getTipType() {
		return this.tipType;
	}

	public void setTipType(Integer tipType) {
		this.tipType = tipType;
	}

	public Long getStaFee() {
		return this.staFee;
	}

	public void setStaFee(Long staFee) {
		this.staFee = staFee;
	}

	public Long getTopFee() {
		return this.topFee;
	}

	public void setTopFee(Long topFee) {
		this.topFee = topFee;
	}

	public String getFirst() {
		return this.first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public Long getAddWeight() {
		return this.addWeight;
	}

	public void setAddWeight(Long addWeight) {
		this.addWeight = addWeight;
	}

	public String getPercentage() {
		return this.percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public Integer getRuleType() {
		return this.ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public Long getMinFee() {
		return this.minFee;
	}

	public void setMinFee(Long minFee) {
		this.minFee = minFee;
	}

	public Long getMinWeightFee() {
		return this.minWeightFee;
	}

	public void setMinWeightFee(Long minWeightFee) {
		this.minWeightFee = minWeightFee;
	}

	public Long getMaxWeightFee() {
		return this.maxWeightFee;
	}

	public void setMaxWeightFee(Long maxWeightFee) {
		this.maxWeightFee = maxWeightFee;
	}

	public Long getMinVolumeFee() {
		return this.minVolumeFee;
	}

	public void setMinVolumeFee(Long minVolumeFee) {
		this.minVolumeFee = minVolumeFee;
	}

	public Long getMaxVolumeFee() {
		return this.maxVolumeFee;
	}

	public void setMaxVolumeFee(Long maxVolumeFee) {
		this.maxVolumeFee = maxVolumeFee;
	}

	public Long getNumberFee() {
		return this.numberFee;
	}

	public void setNumberFee(Long numberFee) {
		this.numberFee = numberFee;
	}

}