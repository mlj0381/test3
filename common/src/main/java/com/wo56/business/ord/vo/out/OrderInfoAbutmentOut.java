package com.wo56.business.ord.vo.out;

import java.io.Serializable;

import com.framework.core.util.DateUtil;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SysTenantDefCacheDataUtil;

@SuppressWarnings("serial")
public class OrderInfoAbutmentOut implements Serializable{
	private String orderNumber;
	private String desProvinceName;
	private String desCityName;
	private String desDistrictName;
	private String consignee;
	private String consigneePhone;
	private String consigneeAddress;
	private String consignor;
	private String consignorPhone;
	private String consignorAddress;
	private String productName;
	private String packName;
	private String carrierName;
	private String pullName;
	private String pullPhone;
	private String createTime;
	private String createName;
	private String number;
	private String weight;
	private String volume;
	private String paymentName;
	private String interchangeName;
	private String remarks;
	private Long arrivedOrgId;
	private String arrivedOrgName;
	private Long billingOrgId;
	private String  billingOrgName;
	private Long freight;
	private Double freightDouble;
	private Long reputation;
	private Double reputationDouble;
	private Long premium;
	private Double premiumDouble;
	private Long deliveryCharge;
	private Double deliveryChargeDouble;
	private Long transitFee;
	private Double transitFeeDouble;
	private Long otherFee;
	private Double otherFeeDouble;
	private Long collectMoney;
	private Double collectMoneyDouble;
	private Long landFee;
	private Double landFeeDouble;
	private Long serviceCharge;
	private Double serviceChargeDouble;
	private Long totalFee;
	private Double totalFeeDouble;
	private Long tip;
	private Double tipDouble;
	
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getDesProvinceName() {
		return desProvinceName;
	}
	public void setDesProvinceName(String desProvinceName) {
		this.desProvinceName = desProvinceName;
	}
	public String getDesCityName() {
		return desCityName;
	}
	public void setDesCityName(String desCityName) {
		this.desCityName = desCityName;
	}
	public String getDesDistrictName() {
		return desDistrictName;
	}
	public void setDesDistrictName(String desDistrictName) {
		this.desDistrictName = desDistrictName;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getConsignor() {
		return consignor;
	}
	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}
	public String getConsignorPhone() {
		return consignorPhone;
	}
	public void setConsignorPhone(String consignorPhone) {
		this.consignorPhone = consignorPhone;
	}
	public String getConsignorAddress() {
		return consignorAddress;
	}
	public void setConsignorAddress(String consignorAddress) {
		this.consignorAddress = consignorAddress;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public String getPullName() {
		return pullName;
	}
	public void setPullName(String pullName) {
		this.pullName = pullName;
	}
	public String getPullPhone() {
		return pullPhone;
	}
	public void setPullPhone(String pullPhone) {
		this.pullPhone = pullPhone;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getInterchangeName() {
		return interchangeName;
	}
	public void setInterchangeName(String interchangeName) {
		this.interchangeName = interchangeName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getArrivedOrgId() {
		return arrivedOrgId;
	}
	public void setArrivedOrgId(Long arrivedOrgId) {
		this.arrivedOrgId = arrivedOrgId;
	}
	public String getArrivedOrgName() {
		if(arrivedOrgId != null && arrivedOrgId > 0){
			return OraganizationCacheDataUtil.getOrgName(arrivedOrgId);
		}
		return "";
	}
	public void setArrivedOrgName(String arrivedOrgName) {
		this.arrivedOrgName = arrivedOrgName;
	}
	public Long getBillingOrgId() {
		return billingOrgId;
	}
	public void setBillingOrgId(Long billingOrgId) {
		this.billingOrgId = billingOrgId;
	}
	public String getBillingOrgName() {
		if (billingOrgId != null && billingOrgId > 0) {
			return OraganizationCacheDataUtil.getOrgName(billingOrgId);
		}
		return "";
	}
	public void setBillingOrgName(String billingOrgName) {
		this.billingOrgName = billingOrgName;
	}
	public Long getFreight() {
		return freight;
	}
	public void setFreight(Long freight) {
		this.freight = freight;
	}
	public Double getFreightDouble() {
		if (freight != null && freight > 0) {
			return CommonUtil.getDoubleFormatLongMoney(freight, 2);
		}
		return 0.00;
	}
	public void setFreightDouble(Double freightDouble) {
		this.freightDouble = freightDouble;
	}
	public Long getReputation() {
		return reputation;
	}
	public void setReputation(Long reputation) {
		this.reputation = reputation;
	}
	public Double getReputationDouble() {
		if (reputation != null && reputation > 0) {
			return CommonUtil.getDoubleFormatLongMoney(reputation, 2);
		}
		return 0.00;
	}
	public void setReputationDouble(Double reputationDouble) {
		this.reputationDouble = reputationDouble;
	}
	public Long getPremium() {
		return premium;
	}
	public void setPremium(Long premium) {
		this.premium = premium;
	}
	public Double getPremiumDouble() {
		if (premium != null && premium > 0) {
			return CommonUtil.getDoubleFormatLongMoney(premium, 2);
		}
		return 0.00;
	}
	public void setPremiumDouble(Double premiumDouble) {
		this.premiumDouble = premiumDouble;
	}
	public Long getDeliveryCharge() {
		return deliveryCharge;
	}
	public void setDeliveryCharge(Long deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	public Double getDeliveryChargeDouble() {
		if (deliveryCharge != null && deliveryCharge > 0) {
			return CommonUtil.getDoubleFormatLongMoney(deliveryCharge, 2);
		}
		return 0.00;
	}
	public void setDeliveryChargeDouble(Double deliveryChargeDouble) {
		this.deliveryChargeDouble = deliveryChargeDouble;
	}
	public Long getTransitFee() {
		return transitFee;
	}
	public void setTransitFee(Long transitFee) {
		this.transitFee = transitFee;
	}
	public Double getTransitFeeDouble() {
		if (transitFee != null && transitFee > 0) {
			return CommonUtil.getDoubleFormatLongMoney(transitFee, 2);
		}
		return 0.00;
	}
	public void setTransitFeeDouble(Double transitFeeDouble) {
		this.transitFeeDouble = transitFeeDouble;
	}
	public Long getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(Long otherFee) {
		this.otherFee = otherFee;
	}
	public Double getOtherFeeDouble() {
		if (otherFee != null && otherFee > 0) {
			return CommonUtil.getDoubleFormatLongMoney(otherFee, 2);
		}
		return 0.00;
	}
	public void setOtherFeeDouble(Double otherFeeDouble) {
		this.otherFeeDouble = otherFeeDouble;
	}
	public Long getCollectMoney() {
		return collectMoney;
	}
	public void setCollectMoney(Long collectMoney) {
		this.collectMoney = collectMoney;
	}
	public Double getCollectMoneyDouble() {
		if (collectMoney != null && collectMoney > 0) {
			return CommonUtil.getDoubleFormatLongMoney(collectMoney, 2);
		}
		return 0.00;
	}
	public void setCollectMoneyDouble(Double collectMoneyDouble) {
		this.collectMoneyDouble = collectMoneyDouble;
	}
	public Long getLandFee() {
		return landFee;
	}
	public void setLandFee(Long landFee) {
		this.landFee = landFee;
	}
	public Double getLandFeeDouble() {
		if (landFee != null && landFee > 0) {
			return CommonUtil.getDoubleFormatLongMoney(landFee, 2);
		}
		return 0.00;
	}
	public void setLandFeeDouble(Double landFeeDouble) {
		this.landFeeDouble = landFeeDouble;
	}
	public Long getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(Long serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public Double getServiceChargeDouble() {
		if (serviceCharge != null && serviceCharge > 0) {
			return CommonUtil.getDoubleFormatLongMoney(serviceCharge, 2);
		}
		return 0.00;
	}
	public void setServiceChargeDouble(Double serviceChargeDouble) {
		this.serviceChargeDouble = serviceChargeDouble;
	}
	public Long getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}
	public Double getTotalFeeDouble() {
		if (totalFee != null && totalFee > 0) {
			return CommonUtil.getDoubleFormatLongMoney(totalFee,2);
		}
		return 0.00;
	}
	public void setTotalFeeDouble(Double totalFeeDouble) {
		this.totalFeeDouble = totalFeeDouble;
	}
	public Long getTip() {
		return tip;
	}
	public void setTip(Long tip) {
		this.tip = tip;
	}
	public Double getTipDouble() {
		if (tip != null && tip > 0) {
			return CommonUtil.getDoubleFormatLongMoney(tip,2);
		}
		return 0.00;
	}
	public void setTipDouble(Double tipDouble) {
		this.tipDouble = tipDouble;
	}
	
}
