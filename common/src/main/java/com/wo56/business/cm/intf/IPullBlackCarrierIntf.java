package com.wo56.business.cm.intf;

import java.util.Map;

public interface IPullBlackCarrierIntf {
	
	/****查询拉黑列表**/
	public Map<String,Object>doQueryPullBlackCarrier(Map<String, Object> inParam) throws Exception;
	/****查询拉包工**/
	public Map<String,Object>doQueryPullBlackCarrierByAccount(Map<String, Object> inParam) throws Exception;
	/****拉黑拉包工**/
	public Map<String,String>doSavePullBlackCarrierByAccount(Map<String, Object> inParam) throws Exception;
	/****取消拉黑拉包工**/
	public Map<String,String>doUpdatePullBlackCarrierByAccount(Map<String, Object> inParam) throws Exception;
	

}
