package com.wo56.business.sys;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.sys.vo.in.CommonParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class SysTableHeadConfigBO {
	
	public String saveSysTableHeadConfigs() throws Exception{
		Map map = SysContexts.getRequestParameterMap();
		//先不计算，取出参数变化成
		String paramStr = DataFormat.getStringKey(map, "paramStr");
		
		CommonParamIn paramIn = new CommonParamIn();
		paramIn.setInCode(InterFacesCodeConsts.COMMON.SAVE_TABLE_HEAD_CONFIG);
		paramIn.setParamStr(paramStr);
		
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return "";
	}
	
	public String getSysTableHeadConfigs() throws Exception{
		CommonParamIn query = new CommonParamIn();
		query.setInCode(InterFacesCodeConsts.COMMON.QUERY_TABLE_HEAD_CONFIG);
		SimpleOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
}
