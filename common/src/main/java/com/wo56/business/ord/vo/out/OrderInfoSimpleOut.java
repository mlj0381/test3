package com.wo56.business.ord.vo.out;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.framework.core.util.DateUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class OrderInfoSimpleOut extends BaseOutParamVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1200002030408414345L;
	/** 订单号 */
	private long orderId;
	/** 运单号 */
	private String trackingNum;
	/** 开单网点 */
	private Long orgId;
	/** 开单网点名称 */
	private String orgIdName;
	/** 订单状态 */
	private String orderStateName;
	/** 订单状态 */
	private Integer orderState;
	/** 总重量 */
	private double weight;
	/** 总体积 */
	private double volume;
	/**目的省份*/
	private String destProvinceName;
	/** 目的城市 */
	private Long destCity;
	/** 目的城市名 */
	private String destCityName;
	private String destCountyName;
	private String destStreetName;
	private String cityCountyStreetName;
	/** 配送网点 */
	private Long distributionOrgId;

	/** 配送城市名 */
	private String distributionOrgName;
	/** 回单号 */
	private String receiptNum;
	/** 回单份数 */
	private Integer receiptCount;
	/** 付款方式 */
	private String paymentTypeName;
	/** 付款方式2 */
	private String paymentType2Name;
	private Integer paymentType2;
	private Long taskId;
	public Integer getPaymentType2() {
		return paymentType2;
	}

	public void setPaymentType2(Integer paymentType2) {
		this.paymentType2 = paymentType2;
	}
	/** 现付 */
	private Long cashPayment;
	/** 到付 */
	private Long freightCollect;
	/** 月结 */
	private Long monthlyPayment;
	/** 转账*/
	private Long transPayment;
	/** 代收货款 */
	private Long collectingMoney;
	/** 收货人网点 */
	private String consigneeName;
	/** 收货人电话 */
	private String consigneeBill;
	private String consigneeTelephone;
	/** 配送方式、交接方式 */
	private String deliveryTypeName;
	private Integer deliveryType;
	/** 收货地址(这个数据看一下是否要组装) */
	private String address;
	/** 货物总数量 */
	private Integer count;
	/** 备注 */
	private String remarks;
	private String shipmentNoticeName;
	/** 回单状态id */
	private String receiptState;
	/** 回单状态name */
	private String receiptStateName;
	/** 回单付 */
	private Long receiptPayment;
	/*** 收货方 */
	private String consigneeLinkmanName;
	/** 发货方 */
	private String consignorLinkmanName;
	/*** 发货人联系电话 */
	private String consignorTelephone;
	/*** 发货人 */
	private String consignorName;
	/*** 发货人联系手机 */
	private String consignorBill;
	private String goodsNames;
	private String goodsName;
	private String packingTypeNames;
	private Integer orderType;// 订单类型
	private String orderTypeName;

	private Long freight; // 运费
	private Long actualFreight; // 实际运费

	private Long pickingCosts;// '提货费'
	private Long handingCosts;// 装卸费
	private Long packingCosts;// 包装费
	private Long deliveryCosts;// 送货费
	private Integer isPayDiscount;// 回扣是否支付 ：0表示没有支付，1表示已经支付
	private Long pushMoney;// 业务员提成费用
	private Long procedureFee;// 代收货款的手续费
	private Integer paymentType;// 支付方式1、现付 2、到付、3、月结 4、回单付 5、多款付
	private Integer isPayCash;// 现付尚欠 0表示不欠 1表示欠
	private Long offer; //保险费
	private Long upstairsFee;// 上楼费
	/** 总重量 */
	private String weightString;
	/** 总体积 */
	private String volumeString;
	/** 回单份数 */
	private String receiptCountString;
	/** 现付(元) */
	private String cashPaymentString;
	/** 到付(元) */
	private String freightCollectString;
	/** 月结(元) */
	private String monthlyPaymentString;
	/** 代收货款(元) */
	private String collectingMoneyString;
	/** 运费(元) **/
	private String freightString; // 运费
	/** 转账*/
	private String transPaymentString;
	
	/** 回单付 (元)*/
	private String receiptPaymentString;
	private Long tootalMoney;//师傅配安费
	private Long installCosts;//配安费
	private String tootalMoneyString;
	private String installCostsString;
	
	/** 服务方式 */
	private String serviceType;
	private String serviceTypeName;
	/** 开单员 */
	private long inputUserId;
	private String inputUserName;
	private String outgoingTimeStr;
	
	/** 是否控货 */
	private Integer releaseNote;
	/** 是否控货(中文) */
	private String notes;
	
	/** 发货人地址 */
	private String fromAddress;
	
	private long kcOrgId;
	private String kcOrgName;
	private String doObjName;
	private String doTel;
	private Integer postingSts;
	private String postingStsName;
	/** 目的 区域区存在就是区 不存在就是 市*/
	private String destCityNameAnddestCountyName;
	private String consignee;//仓管员
	
	/**   
	 * 商家订单ID.  
	 */
	private Long sellerOrderId;
	/**是否需要核销0否1是*/
	private Boolean isVerification;
	/**是否贵重物品0否1是*/
	private Boolean isImportant;
	/**直送标识0非直送（默认）1直送*/
	private Boolean isDirect;
	/**是否需要核销0否1是 (中文)*/
	private String isVerificationString;
	/**是否贵重物品0否1是(中文)*/
	private String isImportantString;
	/**直送标识0非直送（默认）1直送(中文)*/
	private String isDirectString;
	/**保险费(元)*/
	private String offerString; //
	
	
	private Long goodsPrice; //申明价值

	private String goodsPriceString; //申明价值(元)

	/**签回单标识0无回单1有回单*/
	private Boolean isReceipt;
	/**签回单标识0无回单1有回单(中文)*/
	private String isReceiptString;
	
	private String outgoingOrgName;
	private Date outgoingTime;
	private String outgoingOpName;
	private String sfUserId;
	private  Double outgoingFeeDouble;
	
	
	
	public String getSfUserId() {
		return sfUserId;
	}

	public void setSfUserId(String sfUserId) {
		this.sfUserId = sfUserId;
	}

	public String getOutgoingTimeStr() {
		return outgoingTimeStr;
	}

	public void setOutgoingTimeStr(String outgoingTimeStr) {
		this.outgoingTimeStr = outgoingTimeStr;
	}

	public String getOutgoingOpName() {
		return outgoingOpName;
	}

	public void setOutgoingOpName(String outgoingOpName) {
		this.outgoingOpName = outgoingOpName;
	}

	public Double getOutgoingFeeDouble() {
		setOutgoingFeeDouble(CommonUtil.getDoubleFormatLongMoney(outgoingFee, 2) );
		return outgoingFeeDouble;
	}

	public void setOutgoingFeeDouble(Double outgoingFeeDouble) {
		this.outgoingFeeDouble = outgoingFeeDouble;
	}

	public String getOutgoingOrgName() {
		return outgoingOrgName;
	}

	public void setOutgoingOrgName(String outgoingOrgName) {
		this.outgoingOrgName = outgoingOrgName;
	}

	public Date getOutgoingTime() {
		return outgoingTime;
	}

	public void setOutgoingTime(Date outgoingTime) {
		this.outgoingTime = outgoingTime;
	}

 

	public Boolean getIsReceipt() {
		return isReceipt;
	}

	public void setIsReceipt(Boolean isReceipt) {
		this.isReceipt = isReceipt;
	}

	public String getIsReceiptString() {
		setIsReceiptString(isReceipt == null || !isReceipt ? "否" : "是");
		return isReceiptString;
	}

	public void setIsReceiptString(String isReceiptString) {
		this.isReceiptString = isReceiptString;
	}

	public Long getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Long goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsPriceString() {
		if (goodsPrice != null) {
			if(goodsPrice.longValue() == 0){
				setGoodsPriceString("0");
			}else{
				setGoodsPriceString(((double) goodsPrice) / 100 + "");
			}
				
			
			
		} else {
			setGoodsPriceString("");
		}
		return goodsPriceString;
	}

	public void setGoodsPriceString(String goodsPriceString) {
		this.goodsPriceString = goodsPriceString;
	}

	public String getOfferString() {
		if (offer != null) {
			if(offer.longValue() == 0){
				setOfferString("0");
			}else{
				setOfferString(((double) offer) / 100 + "");
			}
			
			
		} else {
			setOfferString("");
		}
		return offerString;
	}

	public void setOfferString(String offerString) {
		this.offerString = offerString;
	}

	public Boolean getIsVerification() {
		return isVerification;
	}

	public void setIsVerification(Boolean isVerification) {
		this.isVerification = isVerification;
	}

	public Boolean getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(Boolean isImportant) {
		this.isImportant = isImportant;
	}

	public Boolean getIsDirect() {
		return isDirect;
	}

	public void setIsDirect(Boolean isDirect) {
		this.isDirect = isDirect;
	}

	public String getIsVerificationString() {
		setIsVerificationString(isVerification == null || !isVerification ? "否" : "是");
		return isVerificationString;
	}

	public void setIsVerificationString(String isVerificationString) {
		this.isVerificationString = isVerificationString;
	}

	public String getIsImportantString() {
		setIsImportantString(isImportant == null || !isImportant ? "否" : "是");
		return isImportantString;
	}

	public void setIsImportantString(String isImportantString) {
		this.isImportantString = isImportantString;
	}

	public String getIsDirectString() {
		setIsDirectString(isDirect == null || !isDirect ? "非直送" : "直送");
		return isDirectString;
	}

	public void setIsDirectString(String isDirectString) {
		this.isDirectString = isDirectString;
	}

	public Long getSellerOrderId() {
		return sellerOrderId;
	}

	public void setSellerOrderId(Long sellerOrderId) {
		this.sellerOrderId = sellerOrderId;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getDestCityNameAnddestCountyName() {
		setDestCityNameAnddestCountyName(StringUtils.isNotEmpty(destCountyName) ? destCountyName : destCityName);
		return destCityNameAnddestCountyName;
	}

	public void setDestCityNameAnddestCountyName(
			String destCityNameAnddestCountyName) {
		this.destCityNameAnddestCountyName = destCityNameAnddestCountyName;
	}

	public Integer getPostingSts() {
		return postingSts;
	}

	public void setPostingSts(Integer postingSts) {
		this.postingSts = postingSts;
	}

	public String getPostingStsName() {
		return postingStsName;
	}

	public void setPostingStsName(String postingStsName) {
		this.postingStsName = postingStsName;
	}

	public String getDoObjName() {
		return doObjName;
	}

	public void setDoObjName(String doObjName) {
		this.doObjName = doObjName;
	}

	public String getDoTel() {
		return doTel;
	}

	public void setDoTel(String doTel) {
		this.doTel = doTel;
	}

	public Long getTransPayment() {
		return transPayment;
	}

	public void setTransPayment(Long transPayment) {
		this.transPayment = transPayment;
	}

	public String getTransPaymentString() {
		if (transPayment != null) {
			if(transPayment.longValue() == 0){
				setTransPaymentString("0");
			}else{
				setTransPaymentString(((double) transPayment) / 100 + "");
			}
			
		} else {
			setTransPaymentString("");
		}
		return transPaymentString;
	}

	public void setTransPaymentString(String transPaymentString) {
		this.transPaymentString = transPaymentString;
	}

	public long getKcOrgId() {
		return kcOrgId;
	}

	public void setKcOrgId(long kcOrgId) {
		this.kcOrgId = kcOrgId;
	}

	public String getKcOrgName() {
		setKcOrgName(OraganizationCacheDataUtil.getOrgName(kcOrgId));
		return kcOrgName;
	}

	public void setKcOrgName(String kcOrgName) {
		this.kcOrgName = kcOrgName;
	}

	public String getTootalMoneyString() {
		if (tootalMoney != null) {
			if(tootalMoney.longValue() == 0){
				setTootalMoneyString("0");
			}else{
				setTootalMoneyString(((double) tootalMoney) / 100 + "");
			}
			
		} else {
			setTootalMoneyString("");
		}
		return tootalMoneyString;
	}

	public void setTootalMoneyString(String tootalMoneyString) {
		this.tootalMoneyString = tootalMoneyString;
	}

	public String getInstallCostsString() {
		if (installCosts != null) {
			if(installCosts.longValue() == 0){
				setInstallCostsString("0");
			}else{
				setInstallCostsString(((double) installCosts) / 100 + "");
			}
			
		} else {
			setInstallCostsString("");
		}
		return installCostsString;
	}

	public void setInstallCostsString(String installCostsString) {
		this.installCostsString = installCostsString;
	}

	public Long getTootalMoney() {
		return tootalMoney;
	}

	public void setTootalMoney(Long tootalMoney) {
		this.tootalMoney = tootalMoney;
	}

	public Long getInstallCosts() {
		return installCosts;
	}

	public void setInstallCosts(Long installCosts) {
		this.installCosts = installCosts;
	}
	/**当前网点*/
	private String currentOrgId;
	private String currentOrgIdName;
	private Long cashMoney;
	private Long cashMoney2;
	private String cashMoneyString;
	private String cashMoney2String;
	private String transferValue;
	/**入库时间*/
	private String stockInTime;
	/**入库时长*/
	private String stockDuration;
	private String products;
	private Long stevedoring;
	private String seeOrderStateName;
	private Integer seeOrderState;
	private String goodsNumber;
	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public Integer getSeeOrderState() {
		return seeOrderState;
	}

	public void setSeeOrderState(Integer seeOrderState) {
		this.seeOrderState = seeOrderState;
	}

	public String getSeeOrderStateName() {
		return seeOrderStateName;
	}

	public void setSeeOrderStateName(String seeOrderStateName) {
		this.seeOrderStateName = seeOrderStateName;
	}

	public Long getStevedoring() {
		return stevedoring;
	}
	private Integer tranState;
	public void setStevedoring(Long stevedoring) {
		this.stevedoring = stevedoring;
	}

	public String getStevedoringString() {
		if (stevedoring != null) {
			if (stevedoring.longValue() == 0) {
				setStevedoringString("0");
			} else {
				setStevedoringString(((double) stevedoring) / 100 + "");
			}

		}else{
			setStevedoringString("");
		}
		return stevedoringString;
	}

	public void setStevedoringString(String stevedoringString) {
		this.stevedoringString = stevedoringString;
	}
	private String stevedoringString;

	public Integer getTranState() {
		return tranState;
	}

	public void setTranState(Integer tranState) {
		this.tranState = tranState;
	}

	public String getCityCountyStreetName() {
		setCityCountyStreetName(SysStaticDataUtil.getCityDataList("SYS_CITY", destCity+"").getName());
		return cityCountyStreetName;
	}

	public void setCityCountyStreetName(String cityCountyStreetName) {
		this.cityCountyStreetName = cityCountyStreetName;
	}
	/**费用合计*/
	private Long totalFee;
	private String totalFeeString;
	public String getOtherOrderId() {
		return otherOrderId;
	}

	public void setOtherOrderId(String otherOrderId) {
		this.otherOrderId = otherOrderId;
	}
	private String otherOrderId;
	
	public String getTotalFeeString() {
		if (totalFee != null) {
			if (totalFee.longValue() == 0) {
				setTotalFeeString("0");
			} else {
				setTotalFeeString(((double) totalFee) / 100 + "");
			}

		}else{
			setTotalFeeString("");
		}
		return totalFeeString;
	}

	public void setTotalFeeString(String totalFeeString) {
		this.totalFeeString = totalFeeString;
	}

	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public String getStockInTime() {
		return stockInTime;
	}

	public void setStockInTime(String stockInTime) {
		this.stockInTime = stockInTime;
	}

	public String getStockDuration() {
		return stockDuration;
	}

	public void setStockDuration(String stockDuration) {
		this.stockDuration = stockDuration;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getDestProvinceName() {
		return destProvinceName;
	}

	public void setDestProvinceName(String destProvinceName) {
		this.destProvinceName = destProvinceName;
	}

	public String getTransferValue() {
		return transferValue;
	}

	public void setTransferValue(String transferValue) {
		this.transferValue = transferValue;
	}

	public String getPaymentType2Name() {
		return paymentType2Name;
	}

	public void setPaymentType2Name(String paymentType2Name) {
		this.paymentType2Name = paymentType2Name;
	}

	public String getCashMoneyString() {
		if (cashMoney != null) {
			if (cashMoney.longValue() == 0) {
				setCashMoneyString("0");
			} else {
				setCashMoneyString(((double) cashMoney) / 100 + "");
			}

		}else{
			setCashMoneyString("");
		}
		return cashMoneyString;
	}

	public void setCashMoneyString(String cashMoneyString) {
		this.cashMoneyString = cashMoneyString;
	}

	public String getCashMoney2String() {
		if (cashMoney2 != null) {
			if (cashMoney2.longValue() == 0) {
				setCashMoney2String("0");
			} else {
				setCashMoney2String(((double) cashMoney2) / 100 + "");
			}

		}else{
			setCashMoney2String("");
		}
		return cashMoney2String;
	}

	public void setCashMoney2String(String cashMoney2String) {
		this.cashMoney2String = cashMoney2String;
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

	public String getCurrentOrgId() {
		return currentOrgId;
	}

	public void setCurrentOrgId(String currentOrgId) {
		this.currentOrgId = currentOrgId;
	}

	public String getCurrentOrgIdName() {
		return currentOrgIdName;
	}

	public void setCurrentOrgIdName(String currentOrgIdName) {
		this.currentOrgIdName = currentOrgIdName;
	}

	public String getReceiptPaymentString() {
		if (receiptPayment != null) {
			if (receiptPayment.longValue() == 0) {
				setReceiptPaymentString("0");
			} else {
				setReceiptPaymentString(((double) receiptPayment) / 100 + "");
			}
		}else{
			setReceiptPaymentString("");
		}
		return receiptPaymentString;
	}

	public void setReceiptPaymentString(String receiptPaymentString) {
		this.receiptPaymentString = receiptPaymentString;
	}

	private String procedureFeeString;// 代收货款的手续费(元)
	private String pickingCostsString;// '提货费'(元)
	private String handingCostsString;// 装卸费(元)
	private String packingCostsString;// 包装费(元)
	private String deliveryCostsString;// 送货费(元)
	private String discountString; //回扣
	
	public String getDiscountString() {
		if (discount != null) {
			if (discount.longValue() == 0) {
				setDiscountString("0");
			} else {
				setDiscountString(((double) discount) / 100 + "");
			}
		}else{
			setDiscountString("");
		}
		return discountString;
	}

	public void setDiscountString(String discountString) {
		this.discountString = discountString;
	}

	public String getProcedureFeeString() {
		if (procedureFee != null) {
			if (procedureFee.longValue() == 0) {
				setProcedureFeeString("0");
			} else {
				setProcedureFeeString(((double) procedureFee) / 100 + "");
			}
		}else{
			setProcedureFeeString("");
		}
		return procedureFeeString;
	}

	public void setProcedureFeeString(String procedureFeeString) {
		this.procedureFeeString = procedureFeeString;
	}

	public String getPickingCostsString() {
		if (pickingCosts != null) {
			if (pickingCosts.longValue() == 0) {
				setPickingCostsString("0");
			} else {
				setPickingCostsString(((double) pickingCosts) / 100 + "");
			}
		}else{
			setPickingCostsString("");
		}
		return pickingCostsString;
	}

	public void setPickingCostsString(String pickingCostsString) {
		this.pickingCostsString = pickingCostsString;
	}

	public String getHandingCostsString() {
		if (handingCosts != null) {
			if (handingCosts.longValue() == 0) {
				setHandingCostsString("0");
			} else {
				setHandingCostsString(((double) handingCosts) / 100 + "");
			}
		}else{
			setHandingCostsString("");
		}
		return handingCostsString;
	}

	public void setHandingCostsString(String handingCostsString) {
		this.handingCostsString = handingCostsString;
	}

	public String getPackingCostsString() {
		if (packingCosts != null) {
			if (packingCosts.longValue() == 0) {
				setPackingCostsString("0");
			} else {
				setPackingCostsString(((double) packingCosts) / 100 + "");
			}
		}else{
			setPackingCostsString("");
		}
		return packingCostsString;
	}

	public void setPackingCostsString(String packingCostsString) {
		this.packingCostsString = packingCostsString;
	}

	public String getDeliveryCostsString() {
		if (deliveryCosts != null) {
			if (deliveryCosts.longValue() == 0) {
				setDeliveryCostsString("0");
			} else {
				setDeliveryCostsString(((double) deliveryCosts) / 100 + "");
			}
		}else{
			setDeliveryCostsString("");
		}
		return deliveryCostsString;
	}
	private String outgoingFeeString;
	
	public String getOutgoingFeeString() {
		if (outgoingFee != null) {
			if (outgoingFee.longValue() == 0) {
				setOutgoingFeeString("0");
			} else {
				setOutgoingFeeString(((double) outgoingFee) / 100 + "");
			}
		}else{
			setDeliveryCostsString("");
		}
		return outgoingFeeString;
	}

	public void setOutgoingFeeString(String outgoingFeeString) {
		this.outgoingFeeString = outgoingFeeString;
	}

	public void setDeliveryCostsString(String deliveryCostsString) {
		this.deliveryCostsString = deliveryCostsString;
	}

	/** 签收日期 **/
	private Date signDate;
	/** 签收状态 **/
	private String signStatusName;
	
	private String carrierCompanyName ;// 承运商
	private String carrierCompanyId ;// 承运商Id
	private String outgoingTrackingNum;
	private Long outgoingFee;
	private String linkerName;
	private String linkerPhone;
	private String deliveryPhone;
	private String deliveryAddress;
	private Long shouldReceivables;
	private Long shouldPay;

	public String getCarrierCompanyName() {
		return carrierCompanyName;
	}

	public void setCarrierCompanyName(String carrierCompanyName) {
		this.carrierCompanyName = carrierCompanyName;
	}

	public String getCarrierCompanyId() {
		return carrierCompanyId;
	}

	public void setCarrierCompanyId(String carrierCompanyId) {
		this.carrierCompanyId = carrierCompanyId;
	}

	public String getOutgoingTrackingNum() {
		return outgoingTrackingNum;
	}

	public void setOutgoingTrackingNum(String outgoingTrackingNum) {
		this.outgoingTrackingNum = outgoingTrackingNum;
	}

	public Long getOutgoingFee() {
		return outgoingFee;
	}

	public void setOutgoingFee(Long outgoingFee) {
		this.outgoingFee = outgoingFee;
	}

	public String getLinkerName() {
		return linkerName;
	}

	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	public String getLinkerPhone() {
		return linkerPhone;
	}

	public void setLinkerPhone(String linkerPhone) {
		this.linkerPhone = linkerPhone;
	}

	public String getDeliveryPhone() {
		return deliveryPhone;
	}

	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Long getShouldReceivables() {
		return shouldReceivables;
	}

	public void setShouldReceivables(Long shouldReceivables) {
		this.shouldReceivables = shouldReceivables;
	}

	public Long getShouldPay() {
		return shouldPay;
	}

	public void setShouldPay(Long shouldPay) {
		this.shouldPay = shouldPay;
	}

	/**预约放货时间**/
	private Date schedulingDate;
	
	public Date getSchedulingDate() {
		return schedulingDate;
	}
	public void setSchedulingDate(Date schedulingDate) {
		this.schedulingDate = schedulingDate;
	}

	public void setActualFreight(Long actualFreight) {
		this.actualFreight = actualFreight;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getSignStatusName() {
		return signStatusName;
	}

	public void setSignStatusName(String signStatusName) {
		this.signStatusName = signStatusName;
	}

	public String getWeightString() {
		setWeightString(CommonUtil.getDoubleFixed(weight, 1));
		return weightString;
	}

	public void setWeightString(String weightString) {
		this.weightString = weightString;
	}

	public String getVolumeString() {
		setVolumeString(CommonUtil.getDoubleFixed(volume, 2));
		return volumeString;
	}

	public void setVolumeString(String volumeString) {
		this.volumeString = volumeString;
	}

	public String getReceiptCountString() {
		if (receiptCount != null) {
			setReceiptCountString("");
		}
		return receiptCountString;
	}

	public void setReceiptCountString(String receiptCountString) {
		this.receiptCountString = receiptCountString;
	}

	public String getCashPaymentString() {
		if (cashPayment != null) {
			if (cashPayment.longValue() == 0) {
				setCashPaymentString("0");
			} else {
				setCashPaymentString(((double) cashPayment) / 100 + "");
			}

		}else{
			setCashPaymentString("");
		}
		return cashPaymentString;
	}

	public void setCashPaymentString(String cashPaymentString) {
		this.cashPaymentString = cashPaymentString;
	}

	public String getFreightCollectString() {
		if (freightCollect != null) {
			if (freightCollect.longValue() == 0) {
				setFreightCollectString("0");
			} else {
				setFreightCollectString(((double) freightCollect) / 100 + "");
			}
		}else{
			setFreightCollectString("");
		}
		return freightCollectString;
	}

	public void setFreightCollectString(String freightCollectString) {
		this.freightCollectString = freightCollectString;
	}

	public String getMonthlyPaymentString() {
		if (monthlyPayment != null) {
			if (monthlyPayment.longValue() == 0) {
				setMonthlyPaymentString("0");
			} else {
				setMonthlyPaymentString(((double) monthlyPayment) / 100 + "");
			}
		}else{
			setFreightCollectString("");
		}
		return monthlyPaymentString;
	}

	public void setMonthlyPaymentString(String monthlyPaymentString) {
		this.monthlyPaymentString = monthlyPaymentString;
	}

	public String getCollectingMoneyString() {
		if (collectingMoney != null) {
			if (collectingMoney.longValue() == 0) {
				setMonthlyPaymentString("");
			} else {
				setCollectingMoneyString(((double) collectingMoney) / 100 + "");
			}

		}else{
			setCollectingMoneyString("");
		}
		return collectingMoneyString;
	}

	public void setCollectingMoneyString(String collectingMoneyString) {
		this.collectingMoneyString = collectingMoneyString;
	}

	public String getFreightString() {
		if (freight != null) {
			if (freight.longValue() == 0) {
				setMonthlyPaymentString("");
			} else {
				setFreightString(((double) freight) / 100 + "");
			}

		}else{
			setFreightString("");
		}
		return freightString;
	}

	public void setFreightString(String freightString) {
		this.freightString = freightString;
	}

	public Long getFreight() {
		return freight;
	}

	public void setFreight(Long freight) {
		this.freight = freight;
	}

	/** 回扣 */
	private Long discount;
	/** 库存天数 */
	private String dateInt;
	private String createDateString;

	public String getCreateDateString() {
		if(createDate != null){
			setCreateDateString(DateUtil.formatDate(createDate, "yyyy-MM-dd HH:mm:ss"));
		}
		return createDateString;
	}

	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}

	public String getDateInt() {
		return dateInt;
	}

	public void setDateInt(String dateInt) {
		this.dateInt = dateInt;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getConsigneeLinkmanName() {
		return consigneeLinkmanName;
	}

	public void setConsigneeLinkmanName(String consigneeLinkmanName) {
		this.consigneeLinkmanName = consigneeLinkmanName;
	}

	public Long getReceiptPayment() {
		return receiptPayment;
	}

	public void setReceiptPayment(Long receiptPayment) {
		this.receiptPayment = receiptPayment;
	}

	public String getReceiptState() {
		return receiptState;
	}

	public void setReceiptState(String receiptState) {
		this.receiptState = receiptState;
	}

	public String getReceiptStateName() {
		return receiptStateName;
	}

	public void setReceiptStateName(String receiptStateName) {
		this.receiptStateName = receiptStateName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOrgIdName() {
		return orgIdName;
	}

	public void setOrgIdName(String orgIdName) {
		this.orgIdName = orgIdName;
	}

	public String getOrderStateName() {
		return orderStateName;
	}

	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getTrackingNum() {
		return trackingNum;
	}

	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public Long getDestCity() {
		return destCity;
	}

	public void setDestCity(Long destCity) {
		this.destCity = destCity;
	}

	public String getDestCityName() {
		return destCityName;
	}

	public void setDestCityName(String destCityName) {
		this.destCityName = destCityName;
	}

	public Long getDistributionOrgId() {
		return distributionOrgId;
	}

	public void setDistributionOrgId(Long distributionOrgId) {
		this.distributionOrgId = distributionOrgId;
	}

	public String getDistributionOrgName() {
		return distributionOrgName;
	}

	public void setDistributionOrgName(String distributionOrgName) {
		this.distributionOrgName = distributionOrgName;
	}

	public String getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	public Integer getReceiptCount() {
		return receiptCount;
	}

	public void setReceiptCount(Integer receiptCount) {
		this.receiptCount = receiptCount;
	}

	public String getPaymentTypeName() {
		if (paymentType != null && paymentType > 0) {
			setPaymentTypeName(SysStaticDataUtil.getSysStaticData(
					"PAYMENT_TYPE", paymentType + "").getCodeName());
		}
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	public Long getCashPayment() {
		return cashPayment;
	}

	public void setCashPayment(Long cashPayment) {
		this.cashPayment = cashPayment;
	}

	public Long getFreightCollect() {
		return freightCollect;
	}

	public void setFreightCollect(Long freightCollect) {
		this.freightCollect = freightCollect;
	}

	public Long getCollectingMoney() {
		return collectingMoney;
	}

	public void setCollectingMoney(Long collectingMoney) {
		this.collectingMoney = collectingMoney;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeBill() {
		return consigneeBill;
	}

	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}

	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}

	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShipmentNoticeName() {
		return shipmentNoticeName;
	}

	public void setShipmentNoticeName(String shipmentNoticeName) {
		this.shipmentNoticeName = shipmentNoticeName;
	}

	public String getGoodsNames() {
		return goodsNames;
	}

	public void setGoodsNames(String goodsNames) {
		this.goodsNames = goodsNames;
	}

	public Long getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(Long monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public String getConsignorLinkmanName() {
		return consignorLinkmanName;
	}

	public void setConsignorLinkmanName(String consignorLinkmanName) {
		this.consignorLinkmanName = consignorLinkmanName;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public String getPackingTypeNames() {
		return packingTypeNames;
	}

	public void setPackingTypeNames(String packingTypeNames) {
		this.packingTypeNames = packingTypeNames;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getConsignorName() {
		return consignorName;
	}

	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}

	public String getConsignorBill() {
		return consignorBill;
	}

	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}

	public String getConsignorTelephone() {
		return consignorTelephone;
	}

	public void setConsignorTelephone(String consignorTelephone) {
		this.consignorTelephone = consignorTelephone;
	}

	public Long getActualFreight() {// 实际运费 = 运费 - 回扣
		if (this.freight == null || this.discount == null)
			return 0l;
		return this.freight - this.discount;
	}

	public String getDestCountyName() {
		return destCountyName;
	}

	public void setDestCountyName(String destCountyName) {
		this.destCountyName = destCountyName;
	}

	public String getDestStreetName() {
		return destStreetName;
	}

	public void setDestStreetName(String destStreetName) {
		this.destStreetName = destStreetName;
	}

	public Long getPickingCosts() {
		return pickingCosts;
	}

	public void setPickingCosts(Long pickingCosts) {
		this.pickingCosts = pickingCosts;
	}

	public Long getHandingCosts() {
		return handingCosts;
	}

	public void setHandingCosts(Long handingCosts) {
		this.handingCosts = handingCosts;
	}

	public Long getPackingCosts() {
		return packingCosts;
	}

	public void setPackingCosts(Long packingCosts) {
		this.packingCosts = packingCosts;
	}

	public Long getDeliveryCosts() {
		return deliveryCosts;
	}

	public void setDeliveryCosts(Long deliveryCosts) {
		this.deliveryCosts = deliveryCosts;
	}

	public Integer getIsPayDiscount() {
		return isPayDiscount;
	}

	public void setIsPayDiscount(Integer isPayDiscount) {
		this.isPayDiscount = isPayDiscount;
	}

	public Long getPushMoney() {
		return pushMoney;
	}

	public void setPushMoney(Long pushMoney) {
		this.pushMoney = pushMoney;
	}

	public Long getProcedureFee() {
		return procedureFee;
	}

	public void setProcedureFee(Long procedureFee) {
		this.procedureFee = procedureFee;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getIsPayCash() {
		return isPayCash;
	}

	public void setIsPayCash(Integer isPayCash) {
		this.isPayCash = isPayCash;
	}

	public Long getOffer() {
		return offer;
	}

	public void setOffer(Long offer) {
		this.offer = offer;
	}

	public Long getUpstairsFee() {
		return upstairsFee;
	}

	public void setUpstairsFee(Long upstairsFee) {
		this.upstairsFee = upstairsFee;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}

	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}

	public String getOrderTypeName() {
		if (orderType != null && orderType >= 0) {
			setOrderTypeName(SysStaticDataUtil.getSysStaticData("ORD_ORDER_INFO@ORDER_TYPE", orderType + "").getCodeName());
		}
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}
	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceTypeName() {
		if(StringUtils.isNotEmpty(serviceType)){
			setServiceTypeName(SysStaticDataUtil.getSysStaticData("SCHE_SERVICE_TYPE", serviceType).getCodeName());
		}
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public long getInputUserId() {
		return inputUserId;
	}

	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
	}

	public String getInputUserName() {
		return inputUserName;
	}

	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}

	public Integer getReleaseNote() {
		
		return releaseNote;
	}

	public void setReleaseNote(Integer releaseNote) {
		this.releaseNote = releaseNote;
	}

	public String getNotes() {
		if(releaseNote!=null && releaseNote==0){
			setNotes("否");
		}else {
			setNotes("是");
		}
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

}