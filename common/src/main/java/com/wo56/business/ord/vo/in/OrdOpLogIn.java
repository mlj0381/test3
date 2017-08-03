package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

@SuppressWarnings("serial")
public class OrdOpLogIn extends PageInParamVO implements IParamIn{

	private String trackingNum;
	private String inCode;
	private String vaildCode;
	private int consigneeBill;
	
	
	public int getConsigneeBill() {
		return consigneeBill;
	}
	public void setConsigneeBill(int consigneeBill) {
		this.consigneeBill = consigneeBill;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	public String getVaildCode() {
		return vaildCode;
	}
	public void setVaildCode(String vaildCode) {
		this.vaildCode = vaildCode;
	}
	
}
