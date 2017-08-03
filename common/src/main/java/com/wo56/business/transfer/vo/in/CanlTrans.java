package com.wo56.business.transfer.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;

public class CanlTrans implements IParamIn{
	
	public CanlTrans(String inCode){
		super();
		this.inCode = inCode;
	}
	@Override
	public String getInCode() {
		return this.inCode;
	}
	private String inCode;
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	/**批次号*/
	private String batchNum;
	private Long orderId;
	private Long trackingNum;
	 
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	
}
