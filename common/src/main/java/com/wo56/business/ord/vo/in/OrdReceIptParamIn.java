package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdReceIptParamIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.RETURN_RE_MANA;
	}
	
	/**订单编号（1）**/
	private String orderId;
	/**是否已经支付 1 未支付 2 已支付**/
	private int isPay;
	
	private int receiptStat;
	/**订单编号集合(已英文逗号隔开)**/
	private String orderIds;
	
	public String getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}
	public int getIsPay() {
		return isPay;
	}
	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getReceiptStat() {
		return receiptStat;
	}
	public void setReceiptStat(int receiptStat) {
		this.receiptStat = receiptStat;
	}
	
}
