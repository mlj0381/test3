package com.wo56.business.ord;

import java.util.HashMap;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ord.intf.IOrdImportBillIntf;

public class OrdImportBillBO extends BaseBO{
	
	
	public String doQuery() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		//入参校验
		IOrdImportBillIntf sv = CallerProxy.getSVBean(IOrdImportBillIntf.class, "ordImportBillTF", new TypeToken<Map<String, Object>>(){}.getType());
		Map<String, Object> map = sv.doQuery(inParam);
		return JsonHelper.json(map);
	}
	
	public String dealOrdImportBill(){
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		//入参校验
		Map<String,Object> map=new HashMap<String, Object>();
		String ids = DataFormat.getStringKey(inParam, "ids");
		map.put("ids", ids);
		IOrdImportBillIntf sv = CallerProxy.getSVBean(IOrdImportBillIntf.class, "ordImportBillTF", new TypeToken<Map<String, Object>>(){}.getType());
		map = sv.dealOrdImportBill(map);
		return JsonHelper.json(map);
	}
	
	public String getOrdBill(){
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		//入参校验
		IOrdImportBillIntf sv = CallerProxy.getSVBean(IOrdImportBillIntf.class, "ordImportBillTF", new TypeToken<Map<String, Object>>(){}.getType());
		Map<String, Object> map = sv.getOrdBill(inParam);
		return JsonHelper.json(map);
	}
}
