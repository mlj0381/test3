package com.wo56.business.org.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class OrganizationOut extends BaseOutParamVO{
	
	
	private static final long serialVersionUID = 3835937746019601078L;
   /**是否支持代收货款**/
    public Integer isPaymentCollection;

	public Integer getIsPaymentCollection() {
		return isPaymentCollection;
	}

	public void setIsPaymentCollection(Integer isPaymentCollection) {
		this.isPaymentCollection = isPaymentCollection;
	}
	

}
