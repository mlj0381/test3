package com.wo56.business.ord.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class SysSmsParmOut extends BaseOutParamVO{
	private static final long serialVersionUID = 1200002030408414345L;
	/**订单号*/
	private Long orderId;
	private String consigneeBill;
	/**货物名称**/
	private String goodsName;
	private Integer number;
	private String address;
	private String telphone;
	private Long  feeCoust;
	private Integer paymentType;
	private Integer deliveryType;
	private Long  collMoney;
	private Long orgId;

	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getConsigneeBill() {
		return consigneeBill;
	}
	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public Long getFeeCoust() {
		return feeCoust;
	}
	public void setFeeCoust(Long feeCoust) {
		this.feeCoust = feeCoust;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Integer getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}
	public Long getCollMoney() {
		return collMoney;
	}
	public void setCollMoney(Long collMoney) {
		this.collMoney = collMoney;
	}
	
	
	
	
	
}
