package com.wo56.business.ac.interfaces;

import java.util.Map;

public interface IAcMyWalletIntf {
	public Map<String,Object> billingAcMyWallet(Map<String,Object> inParam);
	
	public String accountTip(Map<String,Object> inParam)throws Exception;
}
