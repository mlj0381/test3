package com.wo56.business.ord.vo.in;

import java.util.Date;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class OrdTransitOutgoingIn extends PageInParamVO implements IParamIn{
	
	private String inCode;
	private Long id;
	private Long orderId;
	private Long carrierOrgId;
	private Long outgoingTrackingNum;
	private String linkerName;
	private String linkerPhone;
	private String deliveryPhone;
	private String deliveryAddress;
	private Long outgoingFee;
	private Long opId;
	private Integer currentOrderState;
	private String remarks;
	private Long shouldReceivables;
	private Integer isReceivedShouldReceivables;
	private Long shouldPay;
	private Integer isReceivedShouldPay;
	private Long freightCollect;
	private Integer checkSts;
	private Date checkDate;
	private Long outgoingOrgId;
	
	public OrdTransitOutgoingIn(String inCode){
		this.inCode=inCode;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getCarrierOrgId() {
		return carrierOrgId;
	}
	public void setCarrierOrgId(Long carrierOrgId) {
		this.carrierOrgId = carrierOrgId;
	}
	public Long getOutgoingTrackingNum() {
		return outgoingTrackingNum;
	}
	public void setOutgoingTrackingNum(Long outgoingTrackingNum) {
		this.outgoingTrackingNum = outgoingTrackingNum;
	}
	public String getLinkerName() {
		return linkerName;
	}
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}
	public String getLinkerPhone() {
		return linkerPhone;
	}
	public void setLinkerPhone(String linkerPhone) {
		this.linkerPhone = linkerPhone;
	}
	public String getDeliveryPhone() {
		return deliveryPhone;
	}
	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public Long getOutgoingFee() {
		return outgoingFee;
	}
	public void setOutgoingFee(Long outgoingFee) {
		this.outgoingFee = outgoingFee;
	}
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public Integer getCurrentOrderState() {
		return currentOrderState;
	}
	public void setCurrentOrderState(Integer currentOrderState) {
		this.currentOrderState = currentOrderState;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getShouldReceivables() {
		return shouldReceivables;
	}
	public void setShouldReceivables(Long shouldReceivables) {
		this.shouldReceivables = shouldReceivables;
	}
	public Integer getIsReceivedShouldReceivables() {
		return isReceivedShouldReceivables;
	}
	public void setIsReceivedShouldReceivables(Integer isReceivedShouldReceivables) {
		this.isReceivedShouldReceivables = isReceivedShouldReceivables;
	}
	public Long getShouldPay() {
		return shouldPay;
	}
	public void setShouldPay(Long shouldPay) {
		this.shouldPay = shouldPay;
	}
	public Integer getIsReceivedShouldPay() {
		return isReceivedShouldPay;
	}
	public void setIsReceivedShouldPay(Integer isReceivedShouldPay) {
		this.isReceivedShouldPay = isReceivedShouldPay;
	}
	public Long getFreightCollect() {
		return freightCollect;
	}
	public void setFreightCollect(Long freightCollect) {
		this.freightCollect = freightCollect;
	}
	public Integer getCheckSts() {
		return checkSts;
	}
	public void setCheckSts(Integer checkSts) {
		this.checkSts = checkSts;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public Long getOutgoingOrgId() {
		return outgoingOrgId;
	}
	public void setOutgoingOrgId(Long outgoingOrgId) {
		this.outgoingOrgId = outgoingOrgId;
	}
	@Override
	public String getInCode() {
		
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}

}
