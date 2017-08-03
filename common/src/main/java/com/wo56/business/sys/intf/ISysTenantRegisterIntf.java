package com.wo56.business.sys.intf;

import java.util.List;
import java.util.Map;

public interface ISysTenantRegisterIntf {
	public Map<String,Object> querySysTenantRegister(Map<String,Object> inParam);

	public Map<String,Object> addSysTenantRegister(Map<String, Object> inParam);

	public Map<String,Object> updateSysTenantRegister(Map<String, Object> inParam);

	public Map<String,Object> delSysTenantRegister(Map<String, Object> inParam);

	public Map<String,Object> queryById(Map<String, Object> inParam);

}
