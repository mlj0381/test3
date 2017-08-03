package com.wo56.business.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wo56.business.base.handler.OrdImportBillDataConfigHandler;
import com.wo56.business.base.vo.OrdImportBillInfo;
import com.wo56.common.handler.AbstractTennatDataConfigHandler;
import com.wo56.common.handler.TennatDataConfigHelper;



public class TennatDataConfigSV {
	static{
		TennatDataConfigHelper.addHandler(new OrdImportBillDataConfigHandler());
	}
		
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> dealTennatDataConfig(JSONArray configDatas) throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();
		for (int i = 0; i < configDatas.size(); i++) {
			JSONObject configData = configDatas.getJSONObject(i); 
			String businessName = configData.getString("businessName");
			AbstractTennatDataConfigHandler<OrdImportBillInfo> handler = (AbstractTennatDataConfigHandler<OrdImportBillInfo>)TennatDataConfigHelper.getHandler( businessName);
			if (null == handler) {
				ret.put("resultCode", "0");
				ret.put("resultMessage", "无法解析Excel文件中名字为[" + businessName + "]的数据");
				return ret;
			}
			// 处理数据
			List<OrdImportBillInfo> importBillInfos= (List<OrdImportBillInfo>) TennatDataConfigHelper.getParser(businessName).parseHttpRequestDataToVo(configData.getJSONArray("businessDatas"));
			ret = handler.doDealConfigData(importBillInfos);
		}
		ret.put("resultCode", "1");
		return ret;
	}
}
