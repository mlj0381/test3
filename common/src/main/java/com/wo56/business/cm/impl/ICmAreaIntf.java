package com.wo56.business.cm.impl;

import java.util.Map;

public interface ICmAreaIntf {
	public String doSaveOrUpdateArea(Map<String, Object> inParam) throws Exception;
	public Map<String,Object> getCmAreaList(Map<String,Object> inParam);
	public Map<String,Object> getCmArea(Map<String,Object> inParam);
	public String delCmArea(Map<String,Object> inParam);
}
