package com.wo56.business.ord.intf;

import java.util.Map;


public interface IOrdImportBillIntf {
	public Map<String, Object> doQuery(Map<String, Object> inParam) throws Exception;
	
	public Map<String,Object> dealOrdImportBill(Map<String, Object> inParam);
	
	public Map<String,Object> getOrdBill(Map<String,Object> inParam);
}
