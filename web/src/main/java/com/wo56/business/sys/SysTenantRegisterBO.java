package com.wo56.business.sys;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.org.vo.in.OrgUpdateParamIn;
import com.wo56.business.sys.intf.ISysTenantRegisterIntf;
import com.wo56.common.consts.InterFacesCodeConsts;

public class SysTenantRegisterBO {
	/**
	 * 列表查询
	 */
	public String querySysTenantRegister(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ISysTenantRegisterIntf intf = CallerProxy.getSVBean(ISysTenantRegisterIntf.class, "sysTenantRegisterTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.querySysTenantRegister(inParam));
	}
	/**
	 * 删除
	 */
	public String delSysTenantRegister(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ISysTenantRegisterIntf intf = CallerProxy.getSVBean(ISysTenantRegisterIntf.class, "sysTenantRegisterTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.delSysTenantRegister(inParam));
	}
	
	/**
	 **修改
	 */
	public String updateSysTenantRegister(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ISysTenantRegisterIntf intf = CallerProxy.getSVBean(ISysTenantRegisterIntf.class, "sysTenantRegisterTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.updateSysTenantRegister(inParam));
	}
	
	/**
	 * 新增
	 */
	public String addSysTenantRegister(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ISysTenantRegisterIntf intf = CallerProxy.getSVBean(ISysTenantRegisterIntf.class, "sysTenantRegisterTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.addSysTenantRegister(inParam));
	}
	
	/**
	 * 根据ID查询信息
	 * @return
	 * @throws Exception
	 */
	public String queryById() throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ISysTenantRegisterIntf intf = CallerProxy.getSVBean(ISysTenantRegisterIntf.class, "sysTenantRegisterTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.queryById(inParam));
	}
	
	
	
}
