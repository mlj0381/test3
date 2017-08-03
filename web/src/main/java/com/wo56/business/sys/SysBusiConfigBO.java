package com.wo56.business.sys;

import java.util.HashMap;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.sys.intf.ISysBusiConfigIntf;

public class SysBusiConfigBO {
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryBusiConfig() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ISysBusiConfigIntf busiConfigIntf = CallerProxy.getSVBean(ISysBusiConfigIntf.class, "sysBusiConfigTF", new TypeToken<Map<String,Object>>(){}.getType());
		Map<String, Object> inMap=new HashMap<String, Object>();
		return JsonHelper.json(busiConfigIntf.queryBusiConfig(inMap));
	}
	
}
