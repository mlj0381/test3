package com.wo56.business.sche.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class ScheGxEnd extends PageInParamVO implements IParamIn{

	 private  String orderId ;
	 private String inCode;
	 private String pickUpAddr;
	 private String pickCode;
	 private String pickUpPhone;
	 private String taskId;
	 
	 
	public String getPickCode() {
		return pickCode;
	}
	public void setPickCode(String pickCode) {
		this.pickCode = pickCode;
	}
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPickUpAddr() {
		return pickUpAddr;
	}
	public void setPickUpAddr(String pickUpAddr) {
		this.pickUpAddr = pickUpAddr;
	}
	public String getPickUpPhone() {
		return pickUpPhone;
	}
	public void setPickUpPhone(String pickUpPhone) {
		this.pickUpPhone = pickUpPhone;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	 
}
