package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class DelReceiptIn implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.DELETE_RECEIPT_IN;
	}

	private Integer receiptState ;
	private String[] OrderId;
	public Integer getReceiptState() {
		return receiptState;
	}
	public void setReceiptState(Integer receiptState) {
		this.receiptState = receiptState;
	}
	public String[] getOrderId() {
		return OrderId;
	}
	public void setOrderId(String[] orderId) {
		OrderId = orderId;
	}
	
	
	
}
