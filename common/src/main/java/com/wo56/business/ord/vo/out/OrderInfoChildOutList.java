package com.wo56.business.ord.vo.out;

import java.util.List;

public class OrderInfoChildOutList {
	private String volume;
	private String weight;
	private String orderId;
	private List<OrderChildStowagePlanOut> child;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public List<OrderChildStowagePlanOut> getChild() {
		return child;
	}
	public void setChild(List<OrderChildStowagePlanOut> child) {
		this.child = child;
	}
	
	
}
