package com.wo56.business.ord;

import java.util.HashMap;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ord.intf.IOrderFeeRuleIntf;

public class OrderFeeRuleBO {
	/**
	 * 列表查询
	 * @author qlf
	 * @return
	 * @throws Exception 
	 */
	public String queryOrderFeeTip() throws Exception {
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrderFeeRuleIntf intf = CallerProxy.getSVBean(IOrderFeeRuleIntf.class, "orderFeeRuleTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.queryOrderFeeTip(inParam));
	}
	/**
	 * 保存与修改
	 * @return
	 * @throws Exception
	 */
	public String doSaveTipFee() throws Exception {
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrderFeeRuleIntf intf = CallerProxy.getSVBean(IOrderFeeRuleIntf.class, "orderFeeRuleTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		return intf.doSaveTipFee(inParam);
	}
	/**
	 * 获取详情
	 * @return
	 * @throws Exception
	 */
	public String getOrderFeeTipOut() throws Exception {
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrderFeeRuleIntf intf = CallerProxy.getSVBean(IOrderFeeRuleIntf.class, "orderFeeRuleTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.getOrderFeeTipOut(inParam));
	}
	/**
	 * 获取通用模板
	 * @return
	 * @throws Exception
	 */
	public String isCurrencyRule() throws Exception {
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrderFeeRuleIntf intf = CallerProxy.getSVBean(IOrderFeeRuleIntf.class, "orderFeeRuleTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.isCurrencyRule(inParam));
	}
	
	/**
	 * 删除小费计费规则
	 * @return
	 * @throws Exception
	 */
	public String delOrderFeeRule() throws Exception {
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrderFeeRuleIntf intf = CallerProxy.getSVBean(IOrderFeeRuleIntf.class, "orderFeeRuleTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		return intf.delOrderFeeRule(inParam);
	}
	
	/**
	 * 运费列表查询
	 * @author qlf
	 * @return
	 * @throws Exception 
	 */
	public String queryOrderFeeFreight() throws Exception {
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrderFeeRuleIntf intf = CallerProxy.getSVBean(IOrderFeeRuleIntf.class, "orderFeeRuleTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		Map<String,Object> map  = intf.queryOrderFeeFreight(inParam);
		if(map == null){
			map = new HashMap<String, Object>();
		}
		return JsonHelper.json(map);
	}
	
	/**
	 * 运费新增
	 * @author qlf
	 * @return
	 * @throws Exception 
	 */
	public String doSaveFreight() throws Exception {
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrderFeeRuleIntf intf = CallerProxy.getSVBean(IOrderFeeRuleIntf.class, "orderFeeRuleTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		return intf.doSaveFreight(inParam);
	}
	
	
	/**
	 * 列表查询
	 * @author qlf
	 * @return
	 * @throws Exception 
	 */
	public String queryOrderFeePremium() throws Exception {
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrderFeeRuleIntf intf = CallerProxy.getSVBean(IOrderFeeRuleIntf.class, "orderFeeRuleTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.queryOrderFeePremium(inParam));
	}
	
	/**
	 * 运费新增
	 * @author qlf
	 * @return
	 * @throws Exception 
	 */
	public String doSavePremium() throws Exception {
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrderFeeRuleIntf intf = CallerProxy.getSVBean(IOrderFeeRuleIntf.class, "orderFeeRuleTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		return intf.doSavePremium(inParam);
	}
}
