package com.wo56.business.ord;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ord.intf.IOrdDepartInfoYqIntf;
import com.wo56.business.ord.vo.out.OrdDepartInfoOutWeb;

public class OrdDepartInfoYqBO {
	/**
	 * 配载列表查询
	 * @return
	 */
	public String doQuery(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrdDepartInfoYqIntf intf = CallerProxy.getSVBean(IOrdDepartInfoYqIntf.class, "ordDepartInfoYqTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.doQuery(inParam));
	}
	
	/**
	 * 配载详情
	 * @return
	 */
	public String getOrdDepartInfoWeb(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrdDepartInfoYqIntf intf = CallerProxy.getSVBean(IOrdDepartInfoYqIntf.class, "ordDepartInfoYqTF", new TypeToken<OrdDepartInfoOutWeb>(){}.getType());
		OrdDepartInfoOutWeb departInfoOutWeb = intf.getOrdDepartInfoWeb(inParam);
		return JsonHelper.json(departInfoOutWeb);
	}
}
