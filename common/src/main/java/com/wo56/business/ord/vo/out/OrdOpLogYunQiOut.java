package com.wo56.business.ord.vo.out;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrdOpLogYunQiOut {
	private long orderId;
	private String orderNo;
	private int state;
	private String stateName;
	private List<Map<String, String>> items;
	
	public List<Map<String, String>> getItems() {
		if (items == null) {
			items = new ArrayList();
		}
		return items;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setItems(List<Map<String, String>> items) {
		this.items = items;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
