package com.wo56.business.base.intf;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import com.framework.core.SysContexts;
import com.wo56.business.base.impl.TennatDataConfigSV;

public class TennatDataConfigTF {
	public Map<String, Object> dealTennatDataConfig(Map<String, Object> inParam) throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("resultCode", "1");
		
		JSONArray configDatas = (JSONArray) inParam.get("configDatas");
		if (null == configDatas || configDatas.size() == 0) {
			ret.put("resultCode", "0");
			ret.put("resultMessage", "无法获取Excel中的配置数据，请检查Excel中各Sheet页是否存在数据");
			return ret;
		}
		TennatDataConfigSV infoSV = (TennatDataConfigSV) SysContexts.getBean("tennatDataConfigSV");
		return infoSV.dealTennatDataConfig(configDatas);
	}
}
