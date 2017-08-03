package com.wo56.business.ord.vo.in;

import java.util.Date;

public class OrdDepartInfoIn {
	private int orderState;
	private Date arrivalTime;
	private String arrivalOpName;
	private Long arrivalOpId;
	public int getOrderState() {
		return orderState;
	}
	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	public Date getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getArrivalOpName() {
		return arrivalOpName;
	}
	public void setArrivalOpName(String arrivalOpName) {
		this.arrivalOpName = arrivalOpName;
	}
	public Long getArrivalOpId() {
		return arrivalOpId;
	}
	public void setArrivalOpId(Long arrivalOpId) {
		this.arrivalOpId = arrivalOpId;
	}
	
	
}
