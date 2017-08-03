package com.wo56.business.transfer.vo.out;

import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class OutGoingInfo extends BaseOutParamVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 222349857573722L;
	private Long orderId;
	/**鲁脨脭唇脥酶碌茫*/
	private Long carrierOrgId;
	private String carrierOrgIdName;
	private String outgoingTrackingNum;
	private String linkerName;
	private String linkerPhone;
	private String deliveryPhone;
	private String deliveryAddress;
	private Long outgoingFee;
	private Long opId;
	private Integer currentOrderState;
	private String remarks;
	private Integer checkSts;
	private Date checkDate;
	private Long outgoingOrgId;
	private String outgoingOrgIdName;
	private Date createDate;
//	private Long transferValue;
//	private String transferValueString;
	private Long nextOrderId;
	private String opName;
	private Long trackingNum;
	private String outgoingFeeString;
	private Date outgoingTime;
	
	
	
	
	public Date getOutgoingTime() {
		return outgoingTime;
	}
	public void setOutgoingTime(Date outgoingTime) {
		this.outgoingTime = outgoingTime;
	}
	public String getOutgoingFeeString() {
		if(outgoingFee != null)
			if(outgoingFee.longValue() == 0){
				setOutgoingFeeString("");
			}else{
				setOutgoingFeeString((double)outgoingFee/100 +"");
			}
		return outgoingFeeString;
	}
	public void setOutgoingFeeString(String outgoingFeeString) {
		this.outgoingFeeString = outgoingFeeString;
	}
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public Long getNextOrderId() {
		return nextOrderId;
	}
	public void setNextOrderId(Long nextOrderId) {
		this.nextOrderId = nextOrderId;
	}
//	public String getTransferValueString() {
//		if(transferValue != null)
//			if (transferValue.longValue() == 0) {
//				setTransferValueString("");
//			} else {
//				setTransferValueString(((double) transferValue) / 100 + "");
//			}
//		return transferValueString;
//	}
//	public void setTransferValueString(String transferValueString) {
//		this.transferValueString = transferValueString;
//	}
//	public Long getTransferValue() {
//		return transferValue;
//	}
//	public void setTransferValue(Long transferValue) {
//		this.transferValue = transferValue;
//	}
	public String getOutgoingOrgIdName() {
		if(outgoingOrgId != null)
			setOutgoingOrgIdName(OraganizationCacheDataUtil.getOrgName(outgoingOrgId));
		return outgoingOrgIdName;
	}
	public void setOutgoingOrgIdName(String outgoingOrgIdName) {
		this.outgoingOrgIdName = outgoingOrgIdName;
	}
	public String getCarrierOrgIdName() {
		if(carrierOrgId != null)
			setCarrierOrgIdName(OraganizationCacheDataUtil.getOrgName(carrierOrgId));
		return carrierOrgIdName;
	}
	public void setCarrierOrgIdName(String carrierOrgIdName) {
		this.carrierOrgIdName = carrierOrgIdName;
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
	public String getOutgoingTrackingNum() {
	    if("-1".equals(outgoingTrackingNum)){
				setOutgoingTrackingNum(null);
		}
		return outgoingTrackingNum;
	}
	public void setOutgoingTrackingNum(String outgoingTrackingNum) {
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}

