package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class OrdQueryRemarkIn extends PageInParamVO implements IParamIn{

	private static final long serialVersionUID = -3107996056975599605L;
	@Override
	public String getInCode() {
		return this.inCode;
	}
   public OrdQueryRemarkIn(String inCode){
	   super();
	   this.inCode = inCode;
	  
   }
	/**订单ID*/
	private long orderId;
	/**备注*/
	private String remarks;

	private String inCode;
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	
}
