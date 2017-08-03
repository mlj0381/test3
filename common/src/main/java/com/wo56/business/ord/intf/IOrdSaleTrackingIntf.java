package com.wo56.business.ord.intf;

import java.util.Map;

import com.framework.core.inter.vo.Pagination;
import com.wo56.business.ord.vo.out.OrdSalesTrackingOut;

public interface IOrdSaleTrackingIntf {
	public Map<String,Object> getOrderInfoByTrackingNum(Map<String,Object> inParam) throws Exception;
	
	public Map<String,Object> getBearParty(Map<String,Object> inParam);
	
	public Map<String,Object> doSaveSales(Map<String,Object> inParam) throws Exception;
	
	public Pagination<?> doQuerySales(Map<String,Object> inParam);
	
	public OrdSalesTrackingOut getOrdSalesById(Map<String,Object> inParam) throws Exception;
	
	public OrdSalesTrackingOut checkTrackingNumOrdSale(Map<String,Object> inParam) throws Exception;
}
