package com.wo56.business.ord.intf;

import java.util.Map;

import com.wo56.business.ord.vo.OrderFeeRule;

public interface IOrderFeeRuleIntf {
	/***小费列表查询*/
	public Map<String,Object> queryOrderFeeTip(Map<String,Object> inParam)throws Exception;
	/***保存小费*/
	public String doSaveTipFee(Map<String,Object> inParam)throws Exception;
	/***查询小费列表*/
	public Map<String,Object> getOrderFeeTipOut(Map<String,Object> inParam);
	/***获取通用规则*/
	public Map<String,Object>  isCurrencyRule(Map<String,Object> inParam);
	/***删除小费规则*/
	public String delOrderFeeRule(Map<String,Object> inParam);
	/***运费列表查询*/
	public Map<String,Object> queryOrderFeeFreight(Map<String,Object> inParam) throws Exception;
	
	public String doSaveFreight(Map<String,Object> inParam)throws Exception;
	/***保费查询**/
	public Map<String,Object> queryOrderFeePremium(Map<String,Object> inParam) throws Exception;
	
	public String doSavePremium(Map<String,Object> inParam)throws Exception;
}
