package com.wo56.business.ord.intf;

import java.util.Map;



public interface IOrdSignIntf {
	
	/***中转批次签收*/
	public Map batchSign(Map inParam) throws Exception;
	
}
