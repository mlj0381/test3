package com.wo56.business.sms.vo.in;

import java.io.Serializable;

public class SysSmsSearchVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9842283492348392L;
	
	private String consigneeBill;
	private int deliveryType;
	private String trackingNum;
	private String goodsName;
	public String getConsigneeBill() {
		return consigneeBill;
	}
	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}
	public int getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(int deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
}
