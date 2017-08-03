package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdQueryReceiptIn extends PageInParamVO implements IParamIn{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463079451170462589L;

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORD_RECIPT_QUERY;
	}
	/**开始时间（0）**/
	private String beginTime;
	/**结束时间（0）**/
	private String endTime;
	/**批次号（0）*/
	private long batchNum;
	/**收货人*/
	private String consigneeBill;
	/**收货方*/
	private String consigneeName;
	/**发货人*/
	private String consignorBill;
	/**发货方*/
	private String consignorName;
	/**d订单id*/
	private long orderId;
	/**订单状态*/
	private Integer orderState;
	/**运单号**/
	private Long trackingNum;
	/**回单号**/
	private String receiptNum;
	/***目的网点*/
	private String descOrgId;
	/**回单状态*/
	private Integer receiptSatate;
	/** 回单类型  1:回单寄出  2:回单返回 3:回单返厂**/
	private Integer receiptType;
	private Integer paymentType;//付款方式
	
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsignorName() {
		return consignorName;
	}
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}
	private String[] repeictNumber;
	
	public String getDescOrgId() {
		return descOrgId;
	}
	public void setDescOrgId(String descOrgId) {
		this.descOrgId = descOrgId;
	}
	public String getReceiptNum() {
		return receiptNum;
	}
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public long getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(long batchNum) {
		this.batchNum = batchNum;
	}
	public String getConsigneeBill() {
		return consigneeBill;
	}
	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}
	public String getConsignorBill() {
		return consignorBill;
	}
	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	public Integer getReceiptSatate() {
		return receiptSatate;
	}
	public void setReceiptSatate(Integer receiptSatate) {
		this.receiptSatate = receiptSatate;
	}
	public String[] getRepeictNumber() {
		return repeictNumber;
	}
	public void setRepeictNumber(String[] repeictNumber) {
		this.repeictNumber = repeictNumber;
	}
	public Integer getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(Integer receiptType) {
		this.receiptType = receiptType;
	}

	
	
}
