package com.wo56.business.ord.vo;

import org.apache.commons.lang.StringUtils;

import com.wo56.common.utils.CommonUtil;

/**
 * OrderTipRuleExt entity. @author MyEclipse Persistence Tools
 */

public class OrderTipRuleExt implements java.io.Serializable {

	// Fields

	private Long id;
	private Long tipId;
	private String minWieght;
	private String maxWieght;
	private Long fee;
	private String feeString;
	

	// Constructors

	public String getFeeString() {
		if(fee != null && fee > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(fee, 2));
		}
		return feeString;
	}

	public void setFeeString(String feeString) {
		this.feeString = feeString;
	}

	/** default constructor */
	public OrderTipRuleExt() {
	}

	/** minimal constructor */
	public OrderTipRuleExt(Long tipId) {
		this.tipId = tipId;
	}

	/** full constructor */
	public OrderTipRuleExt(Long tipId, String minWieght, String maxWieght,
			Long fee) {
		this.tipId = tipId;
		this.minWieght = minWieght;
		this.maxWieght = maxWieght;
		this.fee = fee;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTipId() {
		
		return this.tipId;
	}

	public void setTipId(Long tipId) {
		this.tipId = tipId;
	}

	public String getMinWieght() {
		if(!CommonUtil.isNumber(minWieght)){
			return "";
		}
		return this.minWieght;
	}

	public void setMinWieght(String minWieght) {
		this.minWieght = minWieght;
	}

	public String getMaxWieght() {
		if(!CommonUtil.isNumber(maxWieght)){
			return "";
		}
		return this.maxWieght;
	}

	public void setMaxWieght(String maxWieght) {
		this.maxWieght = maxWieght;
	}

	public Long getFee() {
		if(StringUtils.isNotEmpty(feeString)){
			if(CommonUtil.isNumber(feeString)){
				return CommonUtil.getStringByLong(feeString);
			}
		}
		return this.fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

}