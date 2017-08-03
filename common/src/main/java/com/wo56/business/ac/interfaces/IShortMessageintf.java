package com.wo56.business.ac.interfaces;

import java.util.List;
import java.util.Map;

import com.framework.core.inter.vo.Pagination;
import com.wo56.business.sys.vo.out.SysSmsSendHisOut;

public interface IShortMessageintf {
	/**
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Pagination<SysSmsSendHisOut> tenantIdAndTemplaId(Map<String,Object> map)throws Exception;
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getTempLate(Map<String,Object> map);
	
	/**
	 * 所以短信
	 * @param map
	 * @return
	 */
	public Pagination<SysSmsSendHisOut> allShortMessage(Map<String,Object> map) throws Exception ;

 	
}
