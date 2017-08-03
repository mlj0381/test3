package com.wo56.business.cm.impl;

import java.util.Map;

/**
 * @author zjy
 * 
 * 
 * */
public interface ICmUserInfoIntf {
	/**
	 * 修改用户的密码
	 * 
	 * @param inParam
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map modifyUserInfoPwd(Map<String, Object> inParam) throws Exception;
	/**
	 * 商户管理查询
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> queryMerchan(Map<String,Object> inParam);
}
