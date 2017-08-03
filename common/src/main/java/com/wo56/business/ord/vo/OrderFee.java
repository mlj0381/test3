package com.wo56.business.ord.vo;

/**
 * OrderFee entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class OrderFee implements java.io.Serializable {

	// Fields

	private Long id;
	private Long orderId;
	private String goodName;
	private Long freight;
	private Long reputation;
	private Long premium;
	private Long deliveryCharge;
	private Long transitFee;
	private Long otherFee;
	private Long collectMoney;
	private Long landFee;
	private Long serviceCharge;
	private Long totalFee;
	private Long tip;
	private Long defaultTip;
	//新加包装费
	private Long pickingCosts;
	// Constructors

	public Long getDefaultTip() {
		return defaultTip;
	}

	public void setDefaultTip(Long defaultTip) {
		this.defaultTip = defaultTip;
	}

	/** default constructor */
	public OrderFee() {
	}

	/** minimal constructor */
	public OrderFee(Long orderId) {
		this.orderId = orderId;
	}
	
	public Long getPickingCosts() {
		return pickingCosts;
	}

	public void setPickingCosts(Long pickingCosts) {
		this.pickingCosts = pickingCosts;
	}


	/** full constructor */
	public OrderFee(Long orderId, String goodName, Long freight,
			Long reputation, Long premium, Long deliveryCharge,
			Long transitFee, Long otherFee, Long collectMoney, Long landFee,
			Long serviceCharge, Long totalFee, Long tip,Long pickingCosts) {
		this.orderId = orderId;
		this.goodName = goodName;
		this.freight = freight;
		this.reputation = reputation;
		this.premium = premium;
		this.deliveryCharge = deliveryCharge;
		this.transitFee = transitFee;
		this.otherFee = otherFee;
		this.collectMoney = collectMoney;
		this.landFee = landFee;
		this.serviceCharge = serviceCharge;
		this.totalFee = totalFee;
		this.tip = tip;
		this.pickingCosts = pickingCosts;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getGoodName() {
		return this.goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public Long getFreight() {
		return this.freight;
	}

	public void setFreight(Long freight) {
		this.freight = freight;
	}

	public Long getReputation() {
		return this.reputation;
	}

	public void setReputation(Long reputation) {
		this.reputation = reputation;
	}

	public Long getPremium() {
		return this.premium;
	}

	public void setPremium(Long premium) {
		this.premium = premium;
	}

	public Long getDeliveryCharge() {
		return this.deliveryCharge;
	}

	public void setDeliveryCharge(Long deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public Long getTransitFee() {
		return this.transitFee;
	}

	public void setTransitFee(Long transitFee) {
		this.transitFee = transitFee;
	}

	public Long getOtherFee() {
		return this.otherFee;
	}

	public void setOtherFee(Long otherFee) {
		this.otherFee = otherFee;
	}

	public Long getCollectMoney() {
		return this.collectMoney;
	}

	public void setCollectMoney(Long collectMoney) {
		this.collectMoney = collectMoney;
	}

	public Long getLandFee() {
		return this.landFee;
	}

	public void setLandFee(Long landFee) {
		this.landFee = landFee;
	}

	public Long getServiceCharge() {
		return this.serviceCharge;
	}

	public void setServiceCharge(Long serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public Long getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public Long getTip() {
		return this.tip;
	}

	public void setTip(Long tip) {
		this.tip = tip;
	}

}