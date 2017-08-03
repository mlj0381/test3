package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class OrdQuerySpellIn extends PageInParamVO implements IParamIn{

	private static final long serialVersionUID = -3107996056975599605L;
	@Override
	public String getInCode() {
		return this.inCode;
	}
   public OrdQuerySpellIn(String inCode){
	   super();
	   this.inCode = inCode;
	  
   }
	/**地址*/
	private String address;
	/**联系电话*/
	private String consigneeBill;

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getConsigneeBill() {
		return consigneeBill;
	}
	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}
	private String inCode;
	
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	
}
