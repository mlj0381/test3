package com.wo56.business.ord.vo.out;

import com.framework.core.inter.vo.OutParamVO;

public class OrdPickInfoOutParam extends OutParamVO {

	private String address;
	private String consignorBill;
	private String consignorName;
	private int isPickup;
	private long idNo;
	private String planPickupTime;
	private String product;
	private int count;
	private String color;
	private String standard;
	private String code;
//	private String ordersTime;
//	private String tipString;
	
	
//	public String getTipString() {
//		return tipString;
//	}
//	public void setTipString(String tipString) {
//		this.tipString = tipString;
//	}
//	public String getOrdersTime() {
//		return ordersTime;
//	}
//	public void setOrdersTime(String ordersTime) {
//		this.ordersTime = ordersTime;
//	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getConsignorBill() {
		return consignorBill;
	}
	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}
	public String getConsignorName() {
		return consignorName;
	}
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}
	public int getIsPickup() {
		return isPickup;
	}
	public void setIsPickup(int isPickup) {
		this.isPickup = isPickup;
	}
	public long getIdNo() {
		return idNo;
	}
	public void setIdNo(long idNo) {
		this.idNo = idNo;
	}
	public String getPlanPickupTime() {
		return planPickupTime;
	}
	public void setPlanPickupTime(String planPickupTime) {
		this.planPickupTime = planPickupTime;
	}
	
	
	
}