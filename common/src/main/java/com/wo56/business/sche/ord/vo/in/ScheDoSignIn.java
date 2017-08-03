package com.wo56.business.sche.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class ScheDoSignIn extends PageInParamVO implements IParamIn{

	 private  		long taskId ;
	 private String wayBillId;
	 private String inCode;
	private int idType;
	private String IDCard;
	 private String receiverName;
	 private int isException;
	 private String flowId;
	 private String signDesc;
	 private String orderId;
	 private Double totalMoney;
	 
	 
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
	 
	 
	
	public String getSignDesc() {
		return signDesc;
	}
	public void setSignDesc(String signDesc) {
		this.signDesc = signDesc;
	}
	public int getIdType() {
		return idType;
	}
	public void setIdType(int idType) {
		this.idType = idType;
	}
	public String getIDCard() {
		return IDCard;
	}
	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public int getIsException() {
		return isException;
	}
	public void setIsException(int isException) {
		this.isException = isException;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getWayBillId() {
		return wayBillId;
	}
	public void setWayBillId(String wayBillId) {
		this.wayBillId = wayBillId;
	}
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	 
}
