package com.wo56.business.ac.vo.out;

import java.util.Date;

import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class AcCashProveParamNewOut{
	private String[] noFiled; //翻译过后前端不需要的字段
	  public String[] getNoFiled() {
		String[]  noFileds = new String[]{ "totalFee","destProvince",
				                           "destCity","destCounty","orgId","currentOrgId",
				                           "freightCollect","monthlyPayment","transPayment",
				                           "receiptPayment","freight","","collectingMoney",
				                           "procedureFee","offer", "noFiled",
				                           "cashMoney","cashMoney2","installCosts","goodsPrice",
				                           "pickingCosts","handingCosts","packingCosts","discount",
				                           "tenantId","sellerOrderId","checkAmount","sellerTenantId"
				                           };
		
		setNoFiled(noFileds);
		return noFiled;
	  }
	  public void setNoFiled(String[] noFiled) {
		this.noFiled = noFiled;
	  }
	private String checkOpName;
	
	private  Integer feeType;
	
	public Integer getFeeType() {
		return feeType;
	}
	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	private long checkedId; //核销ID
	private Long orderId;  //订单号
	private String trackingNum; //运单号
	private String feeTypeName; //核销类型
	private Integer checkSts;  // 核销状态
	private Date createDate;  // 开单时间
	private Date checkDate;   // 核销时间
	
	private String remark;   // 核销网点
	private String checkStsName;   // 核销名称
	private String inputUserName; // 开单员姓名

	private String consignorName;//发货人
	private String consignorBill;//发货人联系手机
	 
	private String consigneeName;//收货人 
	private String consigneeBill;//收货人手机
	private String consigneeTelephone;//收货人电话
	
	
	private String products;//货品名称
	private String goodsNumber;//货号
	private Double weight; //总重量
	private Double volume;//总体积
	private Integer count;//总件数
	
	private Long fee;//核销金额
	private Long checkAmount; // 已核销金额
	private Long withoutAmount; // 未核销金额
	private Long totalFee;//费用合计
	private Long destProvince;// 目的省
	private Long destCity;// 目的市
	public String getCheckOpName() {
		return checkOpName;
	}
	public void setCheckOpName(String checkOpName) {
		this.checkOpName = checkOpName;
	}
	private Long destCounty;// 目的区
	
	private Double feeDouble;//核销金额
	private Double checkAmountDouble; // 已核销金额
	private Double withoutAmountDouble; // 未核销金额
	private Double totalFeeDouble;//费用合计
	private String destProvinceName;// 目的省
	private String destCityName;// 目的市
	private String destCountyName;// 目的区
	
	private Long checkOrg;    // 核销网点
	private Long orgId; //开单网点
	private String checkOrgName;    // 核销网点
	private String orgIdName; //开单网点
	private String seeOrderStateName;//运单展示状态
	private Integer seeOrderState;
	
	private Long sellerTenantId;
	private String  sellerTenantIdName;
	
	public Long getSellerTenantId() {
	
		return sellerTenantId;
	}
	public void setSellerTenantId(Long sellerTenantId) {
		this.sellerTenantId = sellerTenantId;
	}
	public String getSellerTenantIdName() {
		if(sellerTenantId != null){
			setSellerTenantIdName(CommonUtil.getTennatNameById(sellerTenantId));
		}
		return sellerTenantIdName;
	}
	public void setSellerTenantIdName(String sellerTenantIdName) {
		this.sellerTenantIdName = sellerTenantIdName;
	}
	public Integer getSeeOrderState() {
		return seeOrderState;
	}
	public void setSeeOrderState(Integer seeOrderState) {
		this.seeOrderState = seeOrderState;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getCheckOrgName() {
		setCheckOrgName(checkOrg != null ? OraganizationCacheDataUtil.getOrgName(checkOrg) : "");
		return checkOrgName;
	}
	public void setCheckOrgName(String checkOrgName) {
		this.checkOrgName = checkOrgName;
	}
	public String getOrgIdName() {
		setCheckOrgName(orgId != null ? OraganizationCacheDataUtil.getOrgName(orgId) : "");
		return orgIdName;
	}
	public void setOrgIdName(String orgIdName) {
		this.orgIdName = orgIdName;
	}
	public Double getFeeDouble() {
		setFeeDouble(CommonUtil.getDoubleFormatLongMoney(fee, 2));
		return feeDouble;
	}
	public void setFeeDouble(Double feeDouble) {
		this.feeDouble = feeDouble;
	}
	public Double getCheckAmountDouble() {
		setCheckAmountDouble(CommonUtil.getDoubleFormatLongMoney(checkAmount, 2));
		return checkAmountDouble;
	}
	public void setCheckAmountDouble(Double checkAmountDouble) {
		this.checkAmountDouble = checkAmountDouble;
	}
	public Double getWithoutAmountDouble() {
		setWithoutAmountDouble(CommonUtil.getDoubleFormatLongMoney(withoutAmount, 2));
		return withoutAmountDouble;
	}
	public void setWithoutAmountDouble(Double withoutAmountDouble) {
		this.withoutAmountDouble = withoutAmountDouble;
	}
	public Double getTotalFeeDouble() {
		setTotalFeeDouble(CommonUtil.getDoubleFormatLongMoney(totalFee, 2));
		return totalFeeDouble;
	}
	public void setTotalFeeDouble(Double totalFeeDouble) {
		this.totalFeeDouble = totalFeeDouble;
	}
	public String getDestProvinceName() {
		if (destProvince !=null&&( destProvince )!=0){
			setDestProvinceName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", destProvince+"").getName());
		}
		return destProvinceName;
	}
	public void setDestProvinceName(String destProvinceName) {
		
		this.destProvinceName = destProvinceName;
	}
	public String getDestCityName() {
		if (destCity !=null&& !"".equals(destCity)){
			setDestCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", destCity+"").getName());
		}
		return destCityName;
	}
	public void setDestCityName(String destCityName) {
		
		this.destCityName = destCityName;
	}
	public String getDestCountyName() {
		if (destCounty !=null){
			setDestCountyName(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", destCounty+"").getName());
		}
		return destCountyName;
	}
	public void setDestCountyName(String destCountyName) {
		this.destCountyName = destCountyName;
	}
	  private Long cashPayment;//现付
	  private Long freightCollect;  //到付
	  private Long monthlyPayment;//月结
	  private Long transPayment;//转账
	  private Long receiptPayment;//回单付
	  private Long freight;//运费
	  private Long collectingMoney;//代收货款
	  private Long procedureFee;//保险费
	  private Long offer;//代收货款的手续费
	  private Integer paymentType;// 支付方式1
	  private Integer paymentType2;// 支付方式2
	  private Long cashMoney;//金额1
	  private Long cashMoney2;//金额2
	  private Long installCosts;//安装费
	  private Long goodsPrice;//申明价值
	  private Long pickingCosts;//提货费
	  private Long handingCosts;//装卸费
	  private Long packingCosts;//装卸费
	  private Long discount; //回扣
	  
	  private Double cashPaymentDouble;//现付
	  private Double freightCollectDouble;  //到付
	  private Double monthlyPaymentDouble;//月结
	  private Double transPaymentDouble;//转账
	  private Double receiptPaymentDouble;//回单付
	  private Double freightDouble;//运费
	  private Double collectingMoneyDouble;//代收货款
	  private Double procedureFeeDouble;//保险费
	  private Double offerDouble;//代收货款的手续费
	  private String paymentTypeName;// 支付方式1
	  private String paymentType2Name;// 支付方式2
	  private Double cashMoneyDouble;//金额1
	  private Double cashMoney2Double;//金额2
	  private Double installCostsDouble;//安装费
	  private Double goodsPriceDouble;//申明价值
	  private Double pickingCostsDouble;//提货费
	  private Double handingCostsDouble;//装卸费
	  private Double packingCostsDouble;//装卸费
	  private Double discountDouble; //回扣
	  
		public Double getCashPaymentDouble() {
			setCashPaymentDouble(CommonUtil.getDoubleFormatLongMoney(cashPayment,2));
			return cashPaymentDouble;
		}
		public void setCashPaymentDouble(Double cashPaymentDouble) {
			this.cashPaymentDouble = cashPaymentDouble;
		}
		public Double getFreightCollectDouble() {
			setFreightCollectDouble(CommonUtil.getDoubleFormatLongMoney(freightCollect,2));
			return freightCollectDouble;
		}
		public void setFreightCollectDouble(Double freightCollectDouble) {
			this.freightCollectDouble = freightCollectDouble;
		}
		public Double getMonthlyPaymentDouble() {
			setMonthlyPaymentDouble(CommonUtil.getDoubleFormatLongMoney(monthlyPayment,2));
			return monthlyPaymentDouble;
		}
		public void setMonthlyPaymentDouble(Double monthlyPaymentDouble) {
			this.monthlyPaymentDouble = monthlyPaymentDouble;
		}
		public Double getTransPaymentDouble() {
			setTransPaymentDouble(CommonUtil.getDoubleFormatLongMoney(transPayment,2));
			return transPaymentDouble;
		}
		public void setTransPaymentDouble(Double transPaymentDouble) {
			this.transPaymentDouble = transPaymentDouble;
		}
		public Double getReceiptPaymentDouble() {
			setReceiptPaymentDouble(CommonUtil.getDoubleFormatLongMoney(receiptPayment,2));
			return receiptPaymentDouble;
		}
		public void setReceiptPaymentDouble(Double receiptPaymentDouble) {
			this.receiptPaymentDouble = receiptPaymentDouble;
		}
		public Double getFreightDouble() {
			setFreightDouble(CommonUtil.getDoubleFormatLongMoney(freight,2));
			return freightDouble;
		}
		public void setFreightDouble(Double freightDouble) {
			this.freightDouble = freightDouble;
		}
		public Double getCollectingMoneyDouble() {
			setCollectingMoneyDouble(CommonUtil.getDoubleFormatLongMoney(collectingMoney,2));
			return collectingMoneyDouble;
		}
		public void setCollectingMoneyDouble(Double collectingMoneyDouble) {
			this.collectingMoneyDouble = collectingMoneyDouble;
		}
		public Double getProcedureFeeDouble() {
			setProcedureFeeDouble(CommonUtil.getDoubleFormatLongMoney(procedureFee,2));
			return procedureFeeDouble;
		}
		public void setProcedureFeeDouble(Double procedureFeeDouble) {
			this.procedureFeeDouble = procedureFeeDouble;
		}
		public Double getOfferDouble() {
			setOfferDouble(CommonUtil.getDoubleFormatLongMoney(offer,2));
			return offerDouble;
		}
		public void setOfferDouble(Double offerDouble) {
			this.offerDouble = offerDouble;
		}
		public String getPaymentTypeName() {
			if (paymentType != null && paymentType > 0) {
				setPaymentTypeName(SysStaticDataUtil.getSysStaticData("PAYMENT_TYPE", paymentType + "").getCodeName());
			}
			return paymentTypeName;
		}
		public void setPaymentTypeName(String paymentTypeName) {
			this.paymentTypeName = paymentTypeName;
		}
		public String getPaymentType2Name() {
			setPaymentType2Name(SysStaticDataUtil.getSysStaticData("PAYMENT_TYPE", paymentType2 + "").getCodeName());
			return paymentType2Name;
		}
		public void setPaymentType2Name(String paymentType2Name) {
			this.paymentType2Name = paymentType2Name;
		}
		public Double getCashMoneyDouble() {
			setCashMoneyDouble(CommonUtil.getDoubleFormatLongMoney(cashMoney,2));
			return cashMoneyDouble;
		}
		public void setCashMoneyDouble(Double cashMoneyDouble) {
			this.cashMoneyDouble = cashMoneyDouble;
		}
		public Double getCashMoney2Double() {
			setCashMoney2Double(CommonUtil.getDoubleFormatLongMoney(cashMoney2,2));
			return cashMoney2Double;
		}
		public void setCashMoney2Double(Double cashMoney2Double) {
			this.cashMoney2Double = cashMoney2Double;
		}
		public Double getInstallCostsDouble() {
			setInstallCostsDouble(CommonUtil.getDoubleFormatLongMoney(installCosts,2));
			return installCostsDouble;
		}
		public void setInstallCostsDouble(Double installCostsDouble) {
			this.installCostsDouble = installCostsDouble;
		}
		public Double getGoodsPriceDouble() {
			setGoodsPriceDouble(CommonUtil.getDoubleFormatLongMoney(goodsPrice,2));
			return goodsPriceDouble;
		}
		public void setGoodsPriceDouble(Double goodsPriceDouble) {
			this.goodsPriceDouble = goodsPriceDouble;
		}
		public Double getPickingCostsDouble() {
			setPickingCostsDouble(CommonUtil.getDoubleFormatLongMoney(pickingCosts,2));
			return pickingCostsDouble;
		}
		public void setPickingCostsDouble(Double pickingCostsDouble) {
			this.pickingCostsDouble = pickingCostsDouble;
		}
		public Double getHandingCostsDouble() {
			setHandingCostsDouble(CommonUtil.getDoubleFormatLongMoney(handingCosts,2));
			return handingCostsDouble;
		}
		public void setHandingCostsDouble(Double handingCostsDouble) {
			this.handingCostsDouble = handingCostsDouble;
		}
		public Double getPackingCostsDouble() {
			setPackingCostsDouble(CommonUtil.getDoubleFormatLongMoney(packingCosts,2));
			return packingCostsDouble;
		}
		public void setPackingCostsDouble(Double packingCostsDouble) {
			this.packingCostsDouble = packingCostsDouble;
		}
		public Double getDiscountDouble() {
			setDiscountDouble(CommonUtil.getDoubleFormatLongMoney(discount,2));
			return discountDouble;
		}
		public void setDiscountDouble(Double discountDouble) {
			this.discountDouble = discountDouble;
		}
		public long getCheckedId() {
			return checkedId;
		}
		public void setCheckedId(long checkedId) {
			this.checkedId = checkedId;
		}
		public Long getOrderId() {
			return orderId;
		}
		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
		public String getTrackingNum() {
			return trackingNum;
		}
		public void setTrackingNum(String trackingNum) {
			this.trackingNum = trackingNum;
		}
		public Long getFee() {
			return fee;
		}
		public void setFee(Long fee) {
			this.fee = fee;
		}
		public String getFeeTypeName() {
			return feeTypeName;
		}
		public void setFeeTypeName(String feeTypeName) {
			this.feeTypeName = feeTypeName;
		}
		public Long getCheckAmount() {
			return checkAmount;
		}
		public void setCheckAmount(Long checkAmount) {
			this.checkAmount = checkAmount;
		}
		public Long getWithoutAmount() {
			return withoutAmount;
		}
		public void setWithoutAmount(Long withoutAmount) {
			this.withoutAmount = withoutAmount;
		}
		public Integer getCheckSts() {
			return checkSts;
		}
		public void setCheckSts(Integer checkSts) {
			this.checkSts = checkSts;
		}
		public Date getCreateDate() {
			return createDate;
		}
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		public Date getCheckDate() {
			return checkDate;
		}
		public void setCheckDate(Date checkDate) {
			this.checkDate = checkDate;
		}
		public Long getCheckOrg() {
			return checkOrg;
		}
		public void setCheckOrg(Long checkOrg) {
			this.checkOrg = checkOrg;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getCheckStsName() {
			setCheckStsName(SysStaticDataUtil.getSysStaticDataCodeName("AC_CASH_PROVE@CHECK_STS", checkSts+""));
			return checkStsName;
		}
		public void setCheckStsName(String checkStsName) {
			this.checkStsName = checkStsName;
		}
		public String getInputUserName() {
			return inputUserName;
		}
		public void setInputUserName(String inputUserName) {
			this.inputUserName = inputUserName;
		}
		public String getSeeOrderStateName() {
			setSeeOrderStateName(SysStaticDataUtil.getSysStaticData("APP_ORDER_STATE", seeOrderState+"").getCodeName());
			return seeOrderStateName;
		}
		public void setSeeOrderStateName(String seeOrderStateName) {
			this.seeOrderStateName = seeOrderStateName;
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
		public String getConsigneeTelephone() {
			return consigneeTelephone;
		}
		public void setConsigneeTelephone(String consigneeTelephone) {
			this.consigneeTelephone = consigneeTelephone;
		}
		public String getProducts() {
			return products;
		}
		public void setProducts(String products) {
			this.products = products;
		}
		public String getGoodsNumber() {
			return goodsNumber;
		}
		public void setGoodsNumber(String goodsNumber) {
			this.goodsNumber = goodsNumber;
		}
		public Double getWeight() {
			return weight;
		}
		public void setWeight(Double weight) {
			this.weight = weight;
		}
		public Double getVolume() {
			return volume;
		}
		public void setVolume(Double volume) {
			this.volume = volume;
		}
		public Long getTotalFee() {
			return totalFee;
		}
		public void setTotalFee(Long totalFee) {
			this.totalFee = totalFee;
		}
		public Long getDestProvince() {
			return destProvince;
		}
		public void setDestProvince(Long destProvince) {
			this.destProvince = destProvince;
		}
		public Long getDestCity() {
			return destCity;
		}
		public void setDestCity(Long destCity) {
			this.destCity = destCity;
		}
		public Long getDestCounty() {
			return destCounty;
		}
		public void setDestCounty(Long destCounty) {
			this.destCounty = destCounty;
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
		public Long getMonthlyPayment() {
			return monthlyPayment;
		}
		public void setMonthlyPayment(Long monthlyPayment) {
			this.monthlyPayment = monthlyPayment;
		}
		public Long getTransPayment() {
			return transPayment;
		}
		public void setTransPayment(Long transPayment) {
			this.transPayment = transPayment;
		}
		public Long getReceiptPayment() {
			return receiptPayment;
		}
		public void setReceiptPayment(Long receiptPayment) {
			this.receiptPayment = receiptPayment;
		}
		public Long getFreight() {
			return freight;
		}
		public void setFreight(Long freight) {
			this.freight = freight;
		}
		public Long getCollectingMoney() {
			return collectingMoney;
		}
		public void setCollectingMoney(Long collectingMoney) {
			this.collectingMoney = collectingMoney;
		}
		public Long getProcedureFee() {
			return procedureFee;
		}
		public void setProcedureFee(Long procedureFee) {
			this.procedureFee = procedureFee;
		}
		public Long getOffer() {
			return offer;
		}
		public void setOffer(Long offer) {
			this.offer = offer;
		}
		public Integer getPaymentType() {
			return paymentType;
		}
		public void setPaymentType(Integer paymentType) {
			this.paymentType = paymentType;
		}
		public Integer getPaymentType2() {
			return paymentType2;
		}
		public void setPaymentType2(Integer paymentType2) {
			this.paymentType2 = paymentType2;
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
		public Long getInstallCosts() {
			return installCosts;
		}
		public void setInstallCosts(Long installCosts) {
			this.installCosts = installCosts;
		}
		public Long getGoodsPrice() {
			return goodsPrice;
		}
		public void setGoodsPrice(Long goodsPrice) {
			this.goodsPrice = goodsPrice;
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
		public Long getDiscount() {
			return discount;
		}
		public void setDiscount(Long discount) {
			this.discount = discount;
		}
		public Integer getCount() {
			return count;
		}
		public void setCount(Integer count) {
			this.count = count;
		}
		
		private Long advanceMoney;//垫付贷款
		private Long actualBillCosts;//实际提货费
	
		private Double advanceMoneyDouble;//垫付贷款（元）
		private Double actualBillCostsDouble;//实际提货费（元）
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
		
}
