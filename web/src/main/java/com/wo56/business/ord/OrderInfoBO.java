package com.wo56.business.ord;

import java.util.HashMap;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ord.intf.IOrderInfoIntf;

public class OrderInfoBO extends BaseBO{
	/**
	 * 变更专线订单
	 * @return
	 */
	public String queryOrderInfo(){
		Map<String,Object> map = SysContexts.getRequestParamFlatMap();
		IOrderInfoIntf intf = CallerProxy.getSVBean(IOrderInfoIntf.class, "orderInfoTF", new TypeToken<Map<String,Object>>(){}.getType());
		Map<String,Object> pageMap = intf.queryOrderInfo(map);
		if(pageMap == null){
			pageMap = new HashMap<String, Object>();
		}
		return JsonHelper.json(pageMap);
	}
	
	/**
	 * 运单查询
	 * @return
	 */
	public String queryOrderInfoWeb(){
		Map<String,Object> map = SysContexts.getRequestParamFlatMap();
		IOrderInfoIntf intf = CallerProxy.getSVBean(IOrderInfoIntf.class, "orderInfoTF", new TypeToken<Map<String,Object>>(){}.getType());
		Map<String,Object> pageMap = intf.queryOrderInfoWeb(map);
		if(pageMap == null){
			pageMap = new HashMap<String, Object>();
		}
		return JsonHelper.json(pageMap);
	}
}
