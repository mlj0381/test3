package com.wo56.business.ac.impl;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ac.interfaces.IAcMyWalletIntf;

public class AcMyWalletBO extends BaseBO{
	/**
	 * 对账列表查询
	 * @return
	 */
	public String billingAcMyWallet(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		IAcMyWalletIntf intf = CallerProxy.getSVBean(IAcMyWalletIntf.class, "acMyWalletTF", 
				new TypeToken<Map<String,Object>>(){}.getType()); 
		return JsonHelper.json(intf.billingAcMyWallet(inParam));
	} 
	/**
	 * 调账保存
	 * @return
	 * @throws Exception
	 */
	public String accountTip()throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		IAcMyWalletIntf intf = CallerProxy.getSVBean(IAcMyWalletIntf.class, "acMyWalletTF", 
				new TypeToken<Map<String,Object>>(){}.getType()); 
		return intf.accountTip(inParam);
	} 
}
