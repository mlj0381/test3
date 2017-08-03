package com.wo56.business.ord.intf;

import java.util.Map;

public interface IOrdCastChangeIntf {

	/**
	 * 保存方法
	 * 
	 * @param map
	 * @return
	 */
	public Map doSaveOrUpdateOrdCastChangeInfo(Map<String, Object> map)
			throws Exception;
}
