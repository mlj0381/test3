package com.wo56.business.ac.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ac.interfaces.IAcAccountWalletIntf;
import com.wo56.business.ac.vo.out.AcAccountWalletListOut;

public class AcAccountWalletBO extends BaseBO{
	/**
	 * 列表查询
	 * @return
	 */
	public String queryCashTipFee(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		IAcAccountWalletIntf intf = CallerProxy.getSVBean(IAcAccountWalletIntf.class, "acAccountWalletTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.queryCashTipFee(inParam));
	}
	
	/**
	 * 获取拉包工账号详情
	 * @return
	 */
	/*public String getAcAccountWalletByPull(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		IAcAccountWalletIntf intf = CallerProxy.getSVBean(IAcAccountWalletIntf.class, "acAccountWalletTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.getAcAccountWalletByPull(inParam));
	}*/
	/**
	 * 待提现列表
	 * @return
	 */
	public String getAcMyWalletByUserId() throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		IAcAccountWalletIntf intf = CallerProxy.getSVBean(IAcAccountWalletIntf.class, "acAccountWalletTF", 
				new TypeToken<List<AcAccountWalletListOut>>(){}.getType());
		List<AcAccountWalletListOut> list = intf.getAcMyWalletByUserId(inParam);
		return JsonHelper.json(list);
	}
	/**
	 * 提现申请
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String applyTipFee() throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		IAcAccountWalletIntf intf = CallerProxy.getSVBean(IAcAccountWalletIntf.class, "acAccountWalletTF", 
				new TypeToken<List<Map<String,Object>>>(){}.getType());
		return intf.applyTipFee(inParam);
	}
	
	/**
	 * 审批
	 * @return
	 * @throws Exception 
	 */
	public String auditTip() throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		IAcAccountWalletIntf intf = CallerProxy.getSVBean(IAcAccountWalletIntf.class, "acAccountWalletTF", 
				new TypeToken<List<Map<String,Object>>>(){}.getType());
		return intf.auditTip(inParam);
	}
	/**
	 * 核销
	 * @return
	 */
	public String writeTip(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		IAcAccountWalletIntf intf = CallerProxy.getSVBean(IAcAccountWalletIntf.class, "acAccountWalletTF", 
				new TypeToken<List<Map<String,Object>>>(){}.getType());
		return intf.writeTip(inParam);
	}
}
