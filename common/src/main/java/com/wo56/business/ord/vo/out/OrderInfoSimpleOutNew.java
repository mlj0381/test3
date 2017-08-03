package com.wo56.business.ord.vo.out;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class OrderInfoSimpleOutNew{

	/**
	 * 查询的字段  对应实体字段
	 * ord_order_info 表
	 * 
	 * private long orderId; //订单号
	 * private Long sellerOrderId //商家订单ID
	 * 
	 * private String trackingNum; //运单号
	 * private String orderState  //订单状态
	 * private String seeOrderState //运单展示状态
	 * 
	 * private Integer postingSts //过账
	 * private Long totalFee //费用合计
	 * private Long destProvince // 目的省
	 * private Long destCity    // 目的市
	 * private Long destCounty    // 目的区
	 * private Long orgId /开单网点
	 * private Long currentOrgId /当前网点
	 * private Long distributionOrgId //配送网点
	 * private String receiptNum //回单号
	 * private Integer receiptCount //回单份数
	 * private String address  //收货地址详细地址
	 * private Integer count //货物总数量
	 * private Double weight; //总重量
	 * private Double volume;//总体积
	 * private String remarks //备注
	 * private  Integer deliveryType; //交接方式	
	 * 
	 * private String consignorLinkmanName //发货方 
	 * private String consignorTelephone  //发货人联系电话
	 * private String consignorName  //发货人
	 * private String consignorBill  //发货人联系手机
	 * 
	 * private String consigneeLinkmanName //收货方
	 * private String consigneeName  //收货人 
	 * private String consigneeBill  //收货人手机
	 * private String consigneeTelephone //收货人电话
	 * 
	 * private Integer serviceType    //家装服务
	 * private String  inputUserName  //开单员 （制单人）
	 * private Integer releaseNote    //是否控货
	 * private Boolean isVerification //是否需要核销0否1是
	 * private Boolean isImportant    //是否贵重物品0否1是
	 * private Boolean isDirect      // 直送标识0非直送（默认）1直送
     *
	 * private String consignee //仓管员
	 * private String products  //货品名称
	 * private String goodsNumber //货号
	 * private Date createDate //创建时间
     *
     * scheduler_task 表 
     * 
	 * private String doObjName //合作商 
	 * private String doTel //合作商电话
	 * 
	 * 
	 * ord_fee 表
	 * 
	 * private Long cashPayment //现付
	 * private Long freightCollect 
	 * private Long monthlyPayment //月结
	 * private Long transPayment  //转账
	 * private Long receiptPayment //回单付
	 * private Long freight //运费
	 * private Long collectingMoney  //代收货款
	 * private Long procedureFee  //保险费
	 * private Long offer   //代收货款的手续费
	 * private Integer paymentType // 支付方式1
	 * private Integer paymentType2 // 支付方式2
	 * private Long cashMoney  //金额1
	 * private Long cashMoney2  //金额2
	 * private Long installCosts //安装费
	 * private Long goodsPrice //申明价值
	 * private Long pickingCosts //提货费
	 * private Long handingCosts //装卸费
	 * private Long packingCosts //装卸费
	 * private Long discount //回扣
	 * 
	 * 
	 * 
	 */ 
	/**
	 * 
	 * 
	 * 注意 ：出参
	 * 
	 * 全端 html 需多少个 字段 就出参多少个字段
	 * 
	 */
	
	
	private static final long serialVersionUID = 1200002030408414345L;
       
	  private String[] noFiled; //翻译过后前端不需要的字段
	  
	  public String[] getNoFiled() {
		String[]  noFileds = new String[]{"deliveryType","serviceType","releaseNote","isVerification",
				                           "isImportant","isDirect","postingSts","releaseNote",
				                           "isVerification","isImportant","postingSts",
				                           "postingSts","totalFee","destProvince",
				                           "destCity","destCounty","orgId","currentOrgId",
				                           "distributionOrgId","isReceipt","cashPayment",
				                           "freightCollect","monthlyPayment","transPayment",
				                           "receiptPayment","freight","","collectingMoney",
				                           "procedureFee","offer","paymentType","paymentType2",
				                           "cashMoney","cashMoney2","installCosts","goodsPrice",
				                           "pickingCosts","handingCosts","packingCosts","discount",
				                           "noFiled","isSeaTransport","advanceMoney","actualBillCosts"};
		
		setNoFiled(noFileds);
		return noFiled;
	  }
	  public void setNoFiled(String[] noFiled) {
		this.noFiled = noFiled;
	  }


	  private long orderId; //订单号
	  private Long sellerOrderId;//商家订单ID
	  private Long trackingNum; //运单号
	  private String receiptNum;//回单号
	  private Integer receiptCount;//回单份数
	  private String address;//收货地址详细地址
	  private Integer count;//货物总数量
	  private String remarks;//备注
	  private String consignorLinkmanName;//发货方 
	  private String consignorTelephone;//发货人联系电话
	  private String consignorName;//发货人
	  private String consignorBill;//发货人联系手机
	  private String consigneeLinkmanName;//收货方
	  private String consigneeName;//收货人 
	  private String consigneeBill;//收货人手机
	  private String consigneeTelephone;//收货人电话
	  private String  inputUserName;//开单员 （制单人）
	  private Date createDate;//创建时间
	  private String consignee;//仓管员
	  private String products;//货品名称
	  private String goodsNumber;//货号
	  private Double weight; //总重量
	  private Double volume;//总体积
	  
	  private String doObjName;//合作商 
	  private String doTel;//合作商电话
		
		
		
	  //需翻译
	  private  Integer deliveryType; //交接方式	
	  private Integer serviceType;//家装服务
	  private Integer releaseNote;//是否控货
	  private Boolean isVerification;//是否需要核销0否1是
	  private Boolean isImportant;//是否贵重物品0否1是
	  private Boolean isDirect;// 直送标识0非直送（默认）1直送
	  private Integer orderState;//订单状态
	  private Integer seeOrderState;//运单展示状态
	  private Integer postingSts;//过账
	  private Long totalFee;//费用合计
	  private Long destProvince;// 目的省
	  private Long destCity;// 目的市
	  private Long destCounty;// 目的区
	  private Long orgId; //开单网点
	  private Long currentOrgId; //开单网点
	  private Long distributionOrgId;//当前库存网点
	  private Boolean isReceipt; //是否回单
	  private Integer isSeaTransport;//是否海运
	  
	  private String deliveryTypeName;//交接方式
	  private String serviceTypeName;//家装服务
	  private String releaseNoteName;//是否控货
	  private String isVerificationName;//是否需要核销0否1是
	  private String isImportantName;//是否贵重物品0否1是
	  private String isDirectName;// 直送标识0非直送（默认）1直送
	  private String orderStateName;//订单状态
	  private String seeOrderStateName;//运单展示状态
	  private String postingStsName;//过账
	  private Double totalFeeDouble;//费用合计（元）
	  private String destProvinceName;// 目的省
	  private String destCityName;// 目的市
	  private String destCountyName;// 目的区
	  private String orgIdName; //开单网点
	  private String currentOrgIdName; //开单网点
	  private String distributionOrgIdName;//当前库存网点
	  private String isReceiptName; //是否回单
	  private String noValidTime; //时效时间
	  private String isSeaTransportName;//是否海运
	  
	  
	public Integer getIsSeaTransport() {
		return isSeaTransport;
	}
	public void setIsSeaTransport(Integer isSeaTransport) {
		this.isSeaTransport = isSeaTransport;
	}
	public String getIsSeaTransportName() {
		if(isSeaTransport != null){
			setIsSeaTransportName(SysStaticDataUtil.getSysStaticDataCodeName("TRANSPORT_TYPE", isSeaTransport+""));
		}
		return isSeaTransportName;
	}
	public void setIsSeaTransportName(String isSeaTransportName) {
		this.isSeaTransportName = isSeaTransportName;
	}
		public String getNoValidTime() {
		return noValidTime;
	}
	public void setNoValidTime(String noValidTime) {
		this.noValidTime = noValidTime;
	}


		/** 目的 区域区存在就是区 不存在就是 市*/
	  private String destCityNameAnddestCountyName;
	  public String getDeliveryTypeName() {
			if(deliveryType != null && deliveryType>0){
				setDeliveryTypeName(SysStaticDataUtil.getSysStaticData("DELIVERY_TYPE", deliveryType+"").getCodeName());
			}
			return deliveryTypeName;
	  }
	  public void setDeliveryTypeName(String deliveryTypeName) {
			this.deliveryTypeName = deliveryTypeName;
	  }
	  public String getServiceTypeName() {
			setServiceTypeName(SysStaticDataUtil.getSysStaticData("SCHE_SERVICE_TYPE", serviceType+"").getCodeName());  
			return serviceTypeName;
	  }
	  public void setServiceTypeName(String serviceTypeName) {
			 this.serviceTypeName = serviceTypeName;
	  }
	public String getReleaseNoteName() {
		if(releaseNote!=null && releaseNote==0){
			setReleaseNoteName("否");
		}else {
			setReleaseNoteName("是");
		}
		return releaseNoteName;
	}
	public void setReleaseNoteName(String releaseNoteName) {
		this.releaseNoteName = releaseNoteName;
	}
	public String getIsVerificationName() {
		setIsVerificationName(isVerification == null || !isVerification ? "否" : "是");
		return isVerificationName;
	}
	public void setIsVerificationName(String isVerificationName) {
		this.isVerificationName = isVerificationName;
	}
	public String getIsImportantName() {
		setIsImportantName(isImportant == null || !isImportant ? "否" : "是");
		return isImportantName;
	}
	public void setIsImportantName(String isImportantName) {
		this.isImportantName = isImportantName;
	}
	public String getIsDirectName() {
		setIsDirectName(isDirect == null || !isDirect ? "非直送" : "直送");
		return isDirectName;
	}
	public void setIsDirectName(String isDirectName) {
		this.isDirectName = isDirectName;
	}
	public String getOrderStateName() {
		setOrderStateName(SysStaticDataUtil.getSysStaticData(EnumConsts.SysStaticData.ORDER_STATE, orderState+"").getCodeName());
		return orderStateName;
	}
	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}
	public String getSeeOrderStateName() {
		setSeeOrderStateName(SysStaticDataUtil.getSysStaticData("APP_ORDER_STATE", seeOrderState+"").getCodeName());
		return seeOrderStateName;
	}
	public void setSeeOrderStateName(String seeOrderStateName) {
		this.seeOrderStateName = seeOrderStateName;
	}
	public String getPostingStsName() {
		if(postingSts!=null && postingSts.intValue()>-1){
			setPostingStsName(SysStaticDataUtil.getSysStaticData("ORDER_POSTING_STS", postingSts.intValue()+"").getCodeName());
		}
		return postingStsName;
	}
	public void setPostingStsName(String postingStsName) {
		this.postingStsName = postingStsName;
	}
	public Double getTotalFeeDouble() {
		setTotalFeeDouble(CommonUtil.getDoubleFormatLongMoney(totalFee,2));
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
	public String getOrgIdName() {
		if(orgId!=null)
			setOrgIdName(OraganizationCacheDataUtil.getOrgName(orgId));
		return orgIdName;
	}
	public void setOrgIdName(String orgIdName) {
		this.orgIdName = orgIdName;
	}
	public String getCurrentOrgIdName() {
		if(currentOrgId!=null)
			setCurrentOrgIdName(OraganizationCacheDataUtil.getOrgName(currentOrgId));
		return currentOrgIdName;
	}
	public void setCurrentOrgIdName(String currentOrgIdName) {
		this.currentOrgIdName = currentOrgIdName;
	}
	public String getDistributionOrgIdName() {
		if(distributionOrgId != null ){
			setDistributionOrgIdName(OraganizationCacheDataUtil.getOrgName(distributionOrgId));
		}
		return distributionOrgIdName;
	}
	public void setDistributionOrgIdName(String distributionOrgIdName) {
		this.distributionOrgIdName = distributionOrgIdName;
	}
	public String getIsReceiptName() {
		setIsReceiptName(isReceipt == null || !isReceipt ? "否" : "是");
		return isReceiptName;
	}
	public void setIsReceiptName(String isReceiptName) {
		this.isReceiptName = isReceiptName;
	}
	//所有的费用需翻译
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
	  private Long advanceMoney;//垫付贷款
	  private Long actualBillCosts;//实际提货费 
	  
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
	  private Double advanceMoneyDouble;//垫付贷款
	  private Double actualBillCostsDouble;//实际提货费
	  
	  private String tranferFalgName;
      
	
	public String getTranferFalgName() {
		return tranferFalgName;
	}
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
		setAdvanceMoneyDouble(CommonUtil.getDoubleFormatLongMoney(advanceMoney,2));
		return advanceMoneyDouble;
	}
	public void setAdvanceMoneyDouble(Double advanceMoneyDouble) {
		this.advanceMoneyDouble = advanceMoneyDouble;
	}
	public Double getActualBillCostsDouble() {
		setActualBillCostsDouble(CommonUtil.getDoubleFormatLongMoney(actualBillCosts,2));
		return actualBillCostsDouble;
	}
	public void setActualBillCostsDouble(Double actualBillCostsDouble) {
		this.actualBillCostsDouble = actualBillCostsDouble;
	}
	public void setTranferFalgName(String tranferFalgName) {
		this.tranferFalgName = tranferFalgName;
	}
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
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Long getSellerOrderId() {
		return sellerOrderId;
	}
	public void setSellerOrderId(Long sellerOrderId) {
		this.sellerOrderId = sellerOrderId;
	}
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getConsignorLinkmanName() {
		return consignorLinkmanName;
	}
	public void setConsignorLinkmanName(String consignorLinkmanName) {
		this.consignorLinkmanName = consignorLinkmanName;
	}
	public String getConsignorTelephone() {
		return consignorTelephone;
	}
	public void setConsignorTelephone(String consignorTelephone) {
		this.consignorTelephone = consignorTelephone;
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
	public String getConsigneeLinkmanName() {
		return consigneeLinkmanName;
	}
	public void setConsigneeLinkmanName(String consigneeLinkmanName) {
		this.consigneeLinkmanName = consigneeLinkmanName;
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
	public String getInputUserName() {
		return inputUserName;
	}
	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
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
	public Integer getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}
	public Integer getServiceType() {
		return serviceType;
	}
	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}
	public Integer getReleaseNote() {
		return releaseNote;
	}
	public void setReleaseNote(Integer releaseNote) {
		this.releaseNote = releaseNote;
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
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	public Integer getSeeOrderState() {
		return seeOrderState;
	}
	public void setSeeOrderState(Integer seeOrderState) {
		this.seeOrderState = seeOrderState;
	}
	public Integer getPostingSts() {
		return postingSts;
	}
	public void setPostingSts(Integer postingSts) {
		this.postingSts = postingSts;
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
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getCurrentOrgId() {
		return currentOrgId;
	}
	public void setCurrentOrgId(Long currentOrgId) {
		this.currentOrgId = currentOrgId;
	}
	public Long getDistributionOrgId() {
		return distributionOrgId;
	}
	public void setDistributionOrgId(Long distributionOrgId) {
		this.distributionOrgId = distributionOrgId;
	}
	public Boolean getIsReceipt() {
		return isReceipt;
	}
	public void setIsReceipt(Boolean isReceipt) {
		this.isReceipt = isReceipt;
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

	public String getDestCityNameAnddestCountyName() {
		String s =  getDestCountyName();
		setDestCityNameAnddestCountyName(StringUtils.isNotEmpty(s) ? s: destCityName);
		return destCityNameAnddestCountyName;
	}
	public void setDestCityNameAnddestCountyName(
				String destCityNameAnddestCountyName) {
		 this.destCityNameAnddestCountyName = destCityNameAnddestCountyName;
	 }
}