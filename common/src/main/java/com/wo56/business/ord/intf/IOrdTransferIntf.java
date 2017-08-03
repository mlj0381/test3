package com.wo56.business.ord.intf;

import java.util.Map;

/**
 * 中转业务逻辑
 * @author zhouchao
 *
 */
public interface IOrdTransferIntf {

	
	/**
	 * 中转信息跟承运商匹配
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> matchTransfer(Map<String,Object> inParam) throws Exception;
	
	/**
	 * 中转信息跟承运商匹配保存
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> matchSaveTransfer(Map<String,Object> inParam) throws Exception;

	/**
	 * 中转到货
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> transferArriveGoods(Map<String,Object> inParam) throws Exception;

	/**
	 * 二次中转
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> secondTransfer(Map<String,Object> inParam) throws Exception;
	
	/**
	 * 中转跟踪修改中转信息
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> modifyInfo(Map<String,Object> inParam) throws Exception;
	
}
