package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdersTipWeCahtIn implements IParamIn{

	@Override
	public String getInCode() {
		return this.inCode;
	}
	private Long orderId;
	private String inCode;
	
	
	
	String qrCodeUrl;
	String qrCodeId;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getQrCodeUrl() {
		return qrCodeUrl;
	}
	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}
	public String getQrCodeId() {
		return qrCodeId;
	}
	public void setQrCodeId(String qrCodeId) {
		this.qrCodeId = qrCodeId;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	
	
}
