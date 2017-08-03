package com.wo56.business.ord.vo.out;

import java.io.Serializable;
import java.util.Date;

import com.framework.core.base.POJO;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.utils.CommonUtil;

/**
 * OrdGoodsDetail entity. @author MyEclipse Persistence Tools
 */

public class OrdGoodsDetailOutNew extends POJO implements Serializable{

	// Fields

	private long id;
	private long orderId;
	private Long freight;
	private Long pickingCosts;
	private Long handingCosts;
	private Long packingCosts;
	private Long deliveryCosts;
	private Long discount;
	private String goodsName;
	private Integer goodsType;
	private Integer goodsCount;
	private Integer packingType;
	private Double volume;
	private Date createDate;
	private Integer billingType;
	private String billingTypeName;
	private String goodsTypeName;
	private String packingTypeName;
	private String custOrder;
	private Double weight;
	private Long goodsPrice;

	private Long collectingMoney;// 代收货款
	private Long procedureFee;// 代收手续费
	private Long offer;// 保险费
	private Long upstairsFee;// 上楼费
	/**   
	 * 配安费.  
	 */
	private Long installCosts;
	
	private Long volumeUnit;
	private Long weightUnit;
	private Long advanceMoney;//垫付贷款
	private Long actualBillCosts;//实际提货费
	
	private Double freightDouble;
	private Double pickingCostsDouble;
	private Double handingCostsDouble;
	private Double packingCostsDouble;
	private Double deliveryCostsDouble;
	private Double discountDouble;
	private Double goodsPriceDouble;
	private Double collectingMoneyDouble;// 代收货款
	private Double procedureFeeDouble;// 代收手续费
	private Double offerDouble;// 保险费
	private Double upstairsFeeDouble;// 上楼费
	private Double installCostsDouble;
	private Double volumeUnitDouble;
	private Double weightUnitDouble;
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

	public Double getVolumeUnitDouble() {
		setVolumeUnitDouble(CommonUtil.getDoubleFormatLongMoney(volumeUnit, 2));
		return volumeUnitDouble;
	}

	public void setVolumeUnitDouble(Double volumeUnitDouble) {
		this.volumeUnitDouble = volumeUnitDouble;
	}

	public Double getWeightUnitDouble() {
		setWeightUnitDouble(CommonUtil.getDoubleFormatLongMoney(weightUnit, 2));
		return weightUnitDouble;
	}

	public void setWeightUnitDouble(Double weightUnitDouble) {
		this.weightUnitDouble = weightUnitDouble;
	}

	public Double getInstallCostsDouble() {
		setInstallCostsDouble(CommonUtil.getDoubleFormatLongMoney(installCosts, 2));
		return installCostsDouble;
	}

	public void setInstallCostsDouble(Double installCostsDouble) {
		this.installCostsDouble = installCostsDouble;
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

	public Double getDiscountDouble() {
		setDiscountDouble(CommonUtil.getDoubleFormatLongMoney(discount, 2));
		return discountDouble;
	}

	public void setDiscountDouble(Double discountDouble) {
		this.discountDouble = discountDouble;
	}

	public Double getGoodsPriceDouble() {
		setGoodsPriceDouble(CommonUtil.getDoubleFormatLongMoney(goodsPrice, 2));
		return goodsPriceDouble;
	}

	public void setGoodsPriceDouble(Double goodsPriceDouble) {
		this.goodsPriceDouble = goodsPriceDouble;
	}

	public Double getCollectingMoneyDouble() {
		setCollectingMoneyDouble(CommonUtil.getDoubleFormatLongMoney(collectingMoney, 2));
		return collectingMoneyDouble;
	}

	public void setCollectingMoneyDouble(Double collectingMoneyDouble) {
		this.collectingMoneyDouble = collectingMoneyDouble;
	}

	public Double getProcedureFeeDouble() {
		setProcedureFeeDouble(CommonUtil.getDoubleFormatLongMoney(procedureFee, 2));
		return procedureFeeDouble;
	}

	public void setProcedureFeeDouble(Double procedureFeeDouble) {
		this.procedureFeeDouble = procedureFeeDouble;
	}

	public Double getOfferDouble() {
		setOfferDouble(CommonUtil.getDoubleFormatLongMoney(offer, 2));
		return offerDouble;
	}

	public void setOfferDouble(Double offerDouble) {
		this.offerDouble = offerDouble;
	}

	public Double getUpstairsFeeDouble() {
		setUpstairsFeeDouble(CommonUtil.getDoubleFormatLongMoney(upstairsFee, 2));
		return upstairsFeeDouble;
	}

	public void setUpstairsFeeDouble(Double upstairsFeeDouble) {
		this.upstairsFeeDouble = upstairsFeeDouble;
	}

	public Long getVolumeUnit() {
		return volumeUnit;
	}

	public void setVolumeUnit(Long volumeUnit) {
		this.volumeUnit = volumeUnit;
	}

	public Long getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(Long weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getCustOrder() {
		return custOrder;
	}

	public void setCustOrder(String custOrder) {
		this.custOrder = custOrder;
	}

	public String getBillingTypeName() {
		if(billingType!=null&&billingType>0)
			setBillingTypeName(SysStaticDataUtil.getSysStaticDataCodeName(EnumConsts.SysStaticData.BILLING_TYPE, billingType+""));
		return billingTypeName;
	}

	public void setBillingTypeName(String billingTypeName) {
		this.billingTypeName = billingTypeName;
	}

	private Integer installCount;
	/**   
	 * 服务类型:1自提2送货上门3送货上楼4送货上楼+安装5纯安装.  
	 */
	private Integer deliveryType;
	private String deliveryTypeName;
	
	/**   
	 * 包装件数.  
	 */
	private Integer packageCounts;


	
	
	
	// Constructors

	public String getDeliveryTypeName() {
		if(deliveryType!=null)
		{
			setDeliveryTypeName(SysStaticDataUtil.getSysStaticDataCodeName(EnumConsts.SysStaticData.DELIVERY_TYPE, deliveryType+""));
		}
		return deliveryTypeName;
	}

	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Integer getPackageCounts() {
		return packageCounts;
	}

	public void setPackageCounts(Integer packageCounts) {
		this.packageCounts = packageCounts;
	}

	public Long getInstallCosts() {
		return installCosts;
	}

	public void setInstallCosts(Long installCosts) {
		this.installCosts = installCosts;
	}

	public Integer getInstallCount() {
		return installCount;
	}

	public void setInstallCount(Integer installCount) {
		this.installCount = installCount;
	}

	public Long getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Long goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public Integer getBillingType() {
		return billingType;
	}

	public void setBillingType(Integer billingType) {
		this.billingType = billingType;
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

	public Long getDiscount() {
		return this.discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGodsType() {
		return this.goodsType;
	}

	public void setGodsType(Integer godsType) {
		this.goodsType = godsType;
	}

	public Integer getGoodsCount() {
		return this.goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public Integer getPackingType() {
		return this.packingType;
	}

	public void setPackingType(Integer packingType) {
		this.packingType = packingType;
	}

	public Double getVolume() {
		return this.volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public String getGoodsTypeName() {
		setGoodsTypeName(SysStaticDataUtil.getSysStaticData("GOODS_TYPE", goodsType+"").getCodeName());
		return goodsTypeName;
	}

	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}

	public String getPackingTypeName() {
		setPackingTypeName(SysStaticDataUtil.getSysStaticData("PACKING_TYPE",packingType+"").getCodeName());
		return packingTypeName;
	}

	public void setPackingTypeName(String packingTypeName) {
		this.packingTypeName = packingTypeName;
	}
	

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public Long getUpstairsFee() {
		return upstairsFee;
	}

	public void setUpstairsFee(Long upstairsFee) {
		this.upstairsFee = upstairsFee;
	}
}