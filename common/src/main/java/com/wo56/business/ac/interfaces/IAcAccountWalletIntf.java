package com.wo56.business.ac.interfaces;

import java.util.List;
import java.util.Map;

import com.wo56.business.ac.vo.out.AcAccountWalletListOut;
import com.wo56.business.ac.vo.out.AcAccountWalletTipOut;

public interface IAcAccountWalletIntf {
	public Map<String,Object> queryCashTipFee(Map<String,Object> inParam);
	
	//public AcAccountWalletTipOut getAcAccountWalletByPull(Map<String,Object> inParam);
	
	public List<AcAccountWalletListOut> getAcMyWalletByUserId(Map<String,Object> inParam) throws Exception;
	
	public String applyTipFee(Map<String,Object> inParam) throws Exception;
	
	public String auditTip(Map<String,Object> inParam) throws Exception;
	
	public String writeTip(Map<String,Object> inParam);
}
