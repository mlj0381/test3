package com.wo56.business.ord.vo.in;

import org.apache.commons.lang.StringUtils;

import com.framework.core.exception.BusinessException;
import com.wo56.common.utils.CommonUtil;

public class OrderInfoSaveIn {
	
	private Long freight;
	private String freightString;
	private Long reputation;
	private String reputationString;
	private Long premium;
	private String premiumString;
	private Long deliveryCharge;
	private String deliveryChargeString;
	private Long transitFee;
	private String transitFeeString;
	private Long otherFee;
	private String otherFeeString; 
	private Long collectMoney;
	private String collectMoneyString;
	private Long landFee;
	private String landFeeString;
	private Long serviceCharge;
	private String serviceChargeString;
	private Long totalFee;
	private String totalFeeString;
	private Long tip;
	private String tipString;
	
	private Long pickingCosts;
	public Long getPickingCosts() {
		if(StringUtils.isNotEmpty(pickingCostsString)){
			if(!CommonUtil.isNumber(pickingCostsString)){
				throw new BusinessException("包装的费用金额不是数字！");
			}
			return CommonUtil.getStringByLong(pickingCostsString);
		}
		return pickingCosts;
	}
	public void setPickingCosts(Long pickingCosts) {
		this.pickingCosts = pickingCosts;
	}
	public String getPickingCostsString() {
		return pickingCostsString;
	}
	public void setPickingCostsString(String pickingCostsString) {
		this.pickingCostsString = pickingCostsString;
	}
	private String pickingCostsString;
	
	public Long getFreight() {
		if(StringUtils.isNotEmpty(freightString)){
			if(!CommonUtil.isNumber(freightString)){
				throw new BusinessException("运费的费用金额不是数字！");
			}
			return CommonUtil.getStringByLong(freightString);
		}
		return freight;
	}
	public void setFreight(Long freight) {
		this.freight = freight;
	}
	public String getFreightString() {
		return freightString;
	}
	public void setFreightString(String freightString) {
		this.freightString = freightString;
	}
	public Long getReputation() {
		if(StringUtils.isNotEmpty(reputationString)){
			if(!CommonUtil.isNumber(reputationString)){
				throw new BusinessException("声价的费用金额不是数字！");
			}
			return CommonUtil.getStringByLong(reputationString);
		}
		return reputation;
	}
	public void setReputation(Long reputation) {
		this.reputation = reputation;
	}
	public String getReputationString() {
		return reputationString;
	}
	public void setReputationString(String reputationString) {
		this.reputationString = reputationString;
	}
	public Long getPremium() {
		if(StringUtils.isNotEmpty(premiumString)){
			if(!CommonUtil.isNumber(premiumString)){
				throw new BusinessException("保费的费用金额不是数字！");
			}
			return CommonUtil.getStringByLong(premiumString);
		}
		return premium;
	}
	public void setPremium(Long premium) {
		this.premium = premium;
	}
	public String getPremiumString() {
		return premiumString;
	}
	public void setPremiumString(String premiumString) {
		this.premiumString = premiumString;
	}
	public Long getDeliveryCharge() {
		if(StringUtils.isNotEmpty(deliveryChargeString)){
			if(!CommonUtil.isNumber(deliveryChargeString)){
				throw new BusinessException("送货费的费用金额不是数字！");
			}
			return CommonUtil.getStringByLong(deliveryChargeString);
		}
		return deliveryCharge;
	}
	public void setDeliveryCharge(Long deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	public String getDeliveryChargeString() {
		return deliveryChargeString;
	}
	public void setDeliveryChargeString(String deliveryChargeString) {
		this.deliveryChargeString = deliveryChargeString;
	}
	public Long getTransitFee() {
		if(StringUtils.isNotEmpty(transitFeeString)){
			if(!CommonUtil.isNumber(transitFeeString)){
				throw new BusinessException("中转费的费用金额不是数字！");
			}
			return CommonUtil.getStringByLong(transitFeeString);
		}
		return transitFee;
	}
	public void setTransitFee(Long transitFee) {
		this.transitFee = transitFee;
	}
	public String getTransitFeeString() {
		return transitFeeString;
	}
	public void setTransitFeeString(String transitFeeString) {
		this.transitFeeString = transitFeeString;
	}
	public Long getOtherFee() {
		if(StringUtils.isNotEmpty(otherFeeString)){
			if(!CommonUtil.isNumber(otherFeeString)){
				throw new BusinessException("其他费用的费用金额不是数字！");
			}
			return CommonUtil.getStringByLong(otherFeeString);
		}
		return otherFee;
	}
	public void setOtherFee(Long otherFee) {
		this.otherFee = otherFee;
	}
	public String getOtherFeeString() {
		return otherFeeString;
	}
	public void setOtherFeeString(String otherFeeString) {
		this.otherFeeString = otherFeeString;
	}
	public Long getCollectMoney() {
		if(StringUtils.isNotEmpty(collectMoneyString)){
			if(!CommonUtil.isNumber(collectMoneyString)){
				throw new BusinessException("代收货款的费用金额不是数字！");
			}
			return CommonUtil.getStringByLong(collectMoneyString);
		}
		return collectMoney;
	}
	public void setCollectMoney(Long collectMoney) {
		this.collectMoney = collectMoney;
	}
	public String getCollectMoneyString() {
		return collectMoneyString;
	}
	public void setCollectMoneyString(String collectMoneyString) {
		this.collectMoneyString = collectMoneyString;
	}
	public Long getLandFee() {
		if(StringUtils.isNotEmpty(landFeeString)){
			if(!CommonUtil.isNumber(landFeeString)){
				throw new BusinessException("落地费的费用金额不是数字！");
			}
			return CommonUtil.getStringByLong(landFeeString);
		}
		return landFee;
	}
	public void setLandFee(Long landFee) {
		this.landFee = landFee;
	}
	public String getLandFeeString() {
		return landFeeString;
	}
	public void setLandFeeString(String landFeeString) {
		this.landFeeString = landFeeString;
	}
	public Long getServiceCharge() {
		if(StringUtils.isNotEmpty(serviceChargeString)){
			if(!CommonUtil.isNumber(serviceChargeString)){
				throw new BusinessException("服务费的费用金额不是数字！");
			}
			return CommonUtil.getStringByLong(serviceChargeString);
		}
		return serviceCharge;
	}
	public void setServiceCharge(Long serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public String getServiceChargeString() {
		return serviceChargeString;
	}
	public void setServiceChargeString(String serviceChargeString) {
		this.serviceChargeString = serviceChargeString;
	}
	public Long getTotalFee() {
		if(StringUtils.isNotEmpty(totalFeeString)){
			if(!CommonUtil.isNumber(totalFeeString)){
				throw new BusinessException("总费用的费用金额不是数字！");
			}
			return CommonUtil.getStringByLong(totalFeeString);
		}
		return totalFee;
	}
	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}
	public String getTotalFeeString() {
		return totalFeeString;
	}
	public void setTotalFeeString(String totalFeeString) {
		this.totalFeeString = totalFeeString;
	}
	public Long getTip() {
		if(StringUtils.isNotEmpty(tipString)){
			if(!CommonUtil.isNumber(tipString)){
				throw new BusinessException("小费的费用金额不是数字！");
			}
			return CommonUtil.getStringByLong(tipString);
		}
		return tip;
	}
	public void setTip(Long tip) {
		this.tip = tip;
	}
	public String getTipString() {
		return tipString;
	}
	public void setTipString(String tipString) {
		this.tipString = tipString;
	}
	
	
}
