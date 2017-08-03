package com.wo56.business.cm.impl;

import java.util.Map;

/**
 * @author zjy
 * 
 * 
 * */
public interface ICmDriverInfoIntf {
	/**
	 *根据车牌号码查询最近合作是的司机
	 * 
	 * @param inParam
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map doQueryDriver(Map<String, Object> inParam) throws Exception;
	
}
