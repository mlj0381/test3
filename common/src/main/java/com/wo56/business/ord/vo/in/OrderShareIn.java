package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;

public class OrderShareIn implements IParamIn{
	private String orderNo;
	private String inCode;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	
}
