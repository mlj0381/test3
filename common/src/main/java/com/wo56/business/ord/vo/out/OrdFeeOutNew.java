package com.wo56.business.ord.vo.out;

import java.util.Date;

import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;

/**
 * OrdFee entity. @author MyEclipse Persistence Tools
 */

public class OrdFeeOutNew{

	// Fields

	private long orderId;
	
	private Long freight;
	private Long pickingCosts;
	private Long handingCosts;
	private Long packingCosts;
	private Long deliveryCosts;
	private Long cashPayment;
	private Long freightCollect;
	private Long receiptPayment;
	private Long monthlyPayment;
	private Long discount;
	private Integer isPayDiscount;
	private Long pushMoney;
	private Long collectingMoney;
	private Long procedureFee;
	private Integer paymentType;
	private Integer paymentType2;
	private String paymentType2Name;
	private String paymentTypeName;
	private Integer isPayCash;
	private Date createDate;
	private Long offer;
	private Long upstairsFee;// 上楼费
	private Long transPayment;
	private Long installCosts;
	private Long cashMoney;
	private Long cashMoney2;
	private Long goodsPrice; //申明价值
	private Long advanceMoney;//垫付贷款
	private Long actualBillCosts;//实际提货费
	
	//分 钱转为 元
	private Double freightDouble;
	private Double pickingCostsDouble;
	private Double handingCostsDouble;
	private Double packingCostsDouble;
	private Double deliveryCostsDouble;
	private Double cashPaymentDouble;
	private Double freightCollectDouble;
	private Double receiptPaymentDouble;
	private Double monthlyPaymentDouble;
	private Double discountDouble;
	private Double pushMoneyDouble;
	private Double collectingMoneyDouble;
	private Double procedureFeeDouble;
	private Double offerDouble;
	private Double upstairsFeeDouble;// 上楼费
	private Double transPaymentDouble;
	private Double installCostsDouble;
	private Double cashMoneyDouble;
	private Double cashMoney2Double;
	private Double goodsPriceDouble; //申明价值
	private Double advanceMoneyDouble;
	private Double actualBillCostsDouble;
	
	public Long getAdvanceMoney() {
		return advanceMoney;
	}

	public void setAdvanceMoney(Long advanceMoney) {
		this.advanceMoney = advanceMoney;
	}

	public Long getActualBillCosts() {
		return actualBillCosts;
	}

	public void setActualBillCosts(Long actualBillCosts) {
		this.actualBillCosts = actualBillCosts;
	}

	public Double getAdvanceMoneyDouble() {
		setAdvanceMoneyDouble(CommonUtil.getDoubleFormatLongMoney(advanceMoney, 2));
		return advanceMoneyDouble;
	}

	public void setAdvanceMoneyDouble(Double advanceMoneyDouble) {
		this.advanceMoneyDouble = advanceMoneyDouble;
	}

	public Double getActualBillCostsDouble() {
		setActualBillCostsDouble(CommonUtil.getDoubleFormatLongMoney(actualBillCosts, 2));
		return actualBillCostsDouble;
	}

	public void setActualBillCostsDouble(Double actualBillCostsDouble) {
		this.actualBillCostsDouble = actualBillCostsDouble;
	}
	
	public Double getGoodsPriceDouble() {
		setGoodsPriceDouble(CommonUtil.getDoubleFormatLongMoney(goodsPrice, 2));
		return goodsPriceDouble;
	}

	public void setGoodsPriceDouble(Double goodsPriceDouble) {
		this.goodsPriceDouble = goodsPriceDouble;
	}

	public Double getFreightDouble() {
		setFreightDouble(CommonUtil.getDoubleFormatLongMoney(freight, 2));
		return freightDouble;
	}

	public void setFreightDouble(Double freightDouble) {
		this.freightDouble = freightDouble;
	}

	public Double getPickingCostsDouble() {
		setPickingCostsDouble(CommonUtil.getDoubleFormatLongMoney(pickingCosts, 2));
		return pickingCostsDouble;
	}

	public void setPickingCostsDouble(Double pickingCostsDouble) {
		this.pickingCostsDouble = pickingCostsDouble;
	}

	public Double getHandingCostsDouble() {
		setHandingCostsDouble(CommonUtil.getDoubleFormatLongMoney(handingCosts, 2));
		return handingCostsDouble;
	}

	public void setHandingCostsDouble(Double handingCostsDouble) {
		this.handingCostsDouble = handingCostsDouble;
	}

	public Double getPackingCostsDouble() {
		setPackingCostsDouble(CommonUtil.getDoubleFormatLongMoney(packingCosts, 2));
		return packingCostsDouble;
	}

	public void setPackingCostsDouble(Double packingCostsDouble) {
		this.packingCostsDouble = packingCostsDouble;
	}

	public Double getDeliveryCostsDouble() {
		setDeliveryCostsDouble(CommonUtil.getDoubleFormatLongMoney(deliveryCosts, 2));
		return deliveryCostsDouble;
	}

	public void setDeliveryCostsDouble(Double deliveryCostsDouble) {
		this.deliveryCostsDouble = deliveryCostsDouble;
	}

	public Double getCashPaymentDouble() {
		setCashPaymentDouble(CommonUtil.getDoubleFormatLongMoney(cashPayment, 2));
		return cashPaymentDouble;
	}

	public void setCashPaymentDouble(Double cashPaymentDouble) {
		this.cashPaymentDouble = cashPaymentDouble;
	}

	public Double getFreightCollectDouble() {
		setFreightCollectDouble(CommonUtil.getDoubleFormatLongMoney(freightCollect, 2));
		return freightCollectDouble;
	}

	public void setFreightCollectDouble(Double freightCollectDouble) {
		this.freightCollectDouble = freightCollectDouble;
	}

	public Double getReceiptPaymentDouble() {
		setReceiptPaymentDouble(CommonUtil.getDoubleFormatLongMoney(receiptPayment, 2));
		return receiptPaymentDouble;
	}

	public void setReceiptPaymentDouble(Double receiptPaymentDouble) {
		this.receiptPaymentDouble = receiptPaymentDouble;
	}

	public Double getMonthlyPaymentDouble() {
		setMonthlyPaymentDouble(CommonUtil.getDoubleFormatLongMoney( monthlyPayment, 2));
		return monthlyPaymentDouble;
	}

	public void setMonthlyPaymentDouble(Double monthlyPaymentDouble) {
		this.monthlyPaymentDouble = monthlyPaymentDouble;
	}

	public Double getDiscountDouble() {
		setDiscountDouble(CommonUtil.getDoubleFormatLongMoney( discount, 2));
		return discountDouble;
	}

	public void setDiscountDouble(Double discountDouble) {
		this.discountDouble = discountDouble;
	}

	public Double getPushMoneyDouble() {
		setPushMoneyDouble(CommonUtil.getDoubleFormatLongMoney( pushMoney, 2));
		return pushMoneyDouble;
	}

	public void setPushMoneyDouble(Double pushMoneyDouble) {
		this.pushMoneyDouble = pushMoneyDouble;
	}

	public Double getCollectingMoneyDouble() {
		setCollectingMoneyDouble(CommonUtil.getDoubleFormatLongMoney( collectingMoney, 2));
		return collectingMoneyDouble;
	}

	public void setCollectingMoneyDouble(Double collectingMoneyDouble) {
		this.collectingMoneyDouble = collectingMoneyDouble;
	}

	public Double getProcedureFeeDouble() {
		setProcedureFeeDouble(CommonUtil.getDoubleFormatLongMoney( procedureFee, 2));
		return procedureFeeDouble;
	}

	public void setProcedureFeeDouble(Double procedureFeeDouble) {
		this.procedureFeeDouble = procedureFeeDouble;
	}

	public Double getOfferDouble() {
		setOfferDouble(CommonUtil.getDoubleFormatLongMoney( offer, 2));
		return offerDouble;
	}

	public void setOfferDouble(Double offerDouble) {
		this.offerDouble = offerDouble;
	}

	public Double getUpstairsFeeDouble() {
		setUpstairsFeeDouble(CommonUtil.getDoubleFormatLongMoney( upstairsFee, 2));
		return upstairsFeeDouble;
	}

	public void setUpstairsFeeDouble(Double upstairsFeeDouble) {
		this.upstairsFeeDouble = upstairsFeeDouble;
	}

	public Double getTransPaymentDouble() {
		setTransPaymentDouble(CommonUtil.getDoubleFormatLongMoney( transPayment, 2));
		return transPaymentDouble;
	}

	public void setTransPaymentDouble(Double transPaymentDouble) {
		this.transPaymentDouble = transPaymentDouble;
	}

	public Double getInstallCostsDouble() {
		setInstallCostsDouble(CommonUtil.getDoubleFormatLongMoney( installCosts, 2));
		return installCostsDouble;
	}

	public void setInstallCostsDouble(Double installCostsDouble) {
		this.installCostsDouble = installCostsDouble;
	}

	public Double getCashMoneyDouble() {
		setCashMoneyDouble(CommonUtil.getDoubleFormatLongMoney( cashMoney, 2));
		return cashMoneyDouble;
	}

	public void setCashMoneyDouble(Double cashMoneyDouble) {
		this.cashMoneyDouble = cashMoneyDouble;
	}

	public Double getCashMoney2Double() {
		setCashMoney2Double(CommonUtil.getDoubleFormatLongMoney( cashMoney2, 2));
		return cashMoney2Double;
	}

	public void setCashMoney2Double(Double cashMoney2Double) {
		this.cashMoney2Double = cashMoney2Double;
	}

	public Long getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Long goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getPaymentType2Name() {
		if(paymentType2 != null && paymentType2>0){
			setPaymentType2Name(SysStaticDataUtil.getSysStaticData("PAYMENT_TYPE", paymentType2+"").getCodeName());
		}
		return paymentType2Name;
	}

	public void setPaymentType2Name(String paymentType2Name) {
		this.paymentType2Name = paymentType2Name;
	}

	public Long getCashMoney() {
		return cashMoney;
	}

	public void setCashMoney(Long cashMoney) {
		this.cashMoney = cashMoney;
	}

	public Long getCashMoney2() {
		return cashMoney2;
	}

	public void setCashMoney2(Long cashMoney2) {
		this.cashMoney2 = cashMoney2;
	}

	public Integer getPaymentType2() {
		return paymentType2;
	}

	public void setPaymentType2(Integer paymentType2) {
		this.paymentType2 = paymentType2;
	}

	public Long getInstallCosts() {
		return installCosts;
	}

	public void setInstallCosts(Long installCosts) {
		this.installCosts = installCosts;
	}

	public Long getTransPayment() {
		return transPayment;
	}

	public void setTransPayment(Long transPayment) {
		this.transPayment = transPayment;
	}

	public Long getOffer() {
		return offer;
	}

	public void setOffer(Long offer) {
		this.offer = offer;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	// Constructors

	public Integer getIsPayCash() {
		return isPayCash;
	}

	public void setIsPayCash(Integer isPayCash) {
		this.isPayCash = isPayCash;
	}

	public String getPaymentTypeName() {
		if(paymentType != null && paymentType>0){
			setPaymentTypeName(SysStaticDataUtil.getSysStaticData("PAYMENT_TYPE", paymentType+"").getCodeName());
		}
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	// Property accessors

	public long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Long getFreight() {
		return this.freight;
	}

	public void setFreight(Long freight) {
		this.freight = freight;
	}

	public Long getPickingCosts() {
		return this.pickingCosts;
	}

	public void setPickingCosts(Long pickingCosts) {
		this.pickingCosts = pickingCosts;
	}

	public Long getHandingCosts() {
		return this.handingCosts;
	}

	public void setHandingCosts(Long handingCosts) {
		this.handingCosts = handingCosts;
	}

	public Long getPackingCosts() {
		return this.packingCosts;
	}

	public void setPackingCosts(Long packingCosts) {
		this.packingCosts = packingCosts;
	}

	public Long getDeliveryCosts() {
		return this.deliveryCosts;
	}

	public void setDeliveryCosts(Long deliveryCosts) {
		this.deliveryCosts = deliveryCosts;
	}

	public Long getCashPayment() {
		return this.cashPayment;
	}

	public void setCashPayment(Long cashPayment) {
		this.cashPayment = cashPayment;
	}

	public Long getFreightCollect() {
		return this.freightCollect;
	}

	public void setFreightCollect(Long freightCollect) {
		this.freightCollect = freightCollect;
	}

	public Long getReceiptPayment() {
		return this.receiptPayment;
	}

	public void setReceiptPayment(Long receiptPayment) {
		this.receiptPayment = receiptPayment;
	}

	public Long getMonthlyPayment() {
		return this.monthlyPayment;
	}

	public void setMonthlyPayment(Long monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public Long getDiscount() {
		return this.discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Integer getIsPayDiscount() {
		return this.isPayDiscount;
	}

	public void setIsPayDiscount(Integer isPayDiscount) {
		this.isPayDiscount = isPayDiscount;
	}

	public Long getPushMoney() {
		return this.pushMoney;
	}

	public void setPushMoney(Long pushMoney) {
		this.pushMoney = pushMoney;
	}

	public Long getCollectingMoney() {
		return this.collectingMoney;
	}

	public void setCollectingMoney(Long collectingMoney) {
		this.collectingMoney = collectingMoney;
	}

	public Long getProcedureFee() {
		return this.procedureFee;
	}

	public void setProcedureFee(Long procedureFee) {
		this.procedureFee = procedureFee;
	}

	public Long getUpstairsFee() {
		return upstairsFee;
	}

	public void setUpstairsFee(Long upstairsFee) {
		this.upstairsFee = upstairsFee;
	}
}