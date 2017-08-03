package com.wo56.business.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ac.interfaces.IShortMessageintf;
import com.wo56.business.sys.vo.out.SysSmsSendHisOut;


public class ShortMessageBO extends BaseBO {
	
	/**
	 * 当用户要查询指定月份的历史记录的时候，根据yearAndMonth查到对应的表
	 * 	再s根据templateId和租户ID查到相应的数据
	 * @param templateId    模板ID 
	 * 		  yearAndMonth  年月
	 * @return json字符串
	 */
	public String queryMessage() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String yearAndMonth = DataFormat.getStringKey(map, "yearAndMonth");	
		String templateId = DataFormat.getStringKey(map, "templateId");
		IShortMessageintf iShortMessageintf = CallerProxy.getSVBean(IShortMessageintf.class, "shortMessageTF", new TypeToken<Pagination<SysSmsSendHisOut>>(){}.getType());
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("yearAndMonth", yearAndMonth);
		data.put("templateId",templateId);
		Pagination<SysSmsSendHisOut> pagination = iShortMessageintf.tenantIdAndTemplaId(data);
		return JsonHelper.json(pagination);
	}
	
	/**
	 * 查询所以短信模板，
	 * map 是空
	 *
	 * */
	public String queryModule() throws Exception{
		IShortMessageintf sm = CallerProxy.getSVBean(IShortMessageintf.class, "shortMessageTF", new TypeToken<List<Map>>(){}.getType());
		Map<String, Object> m = new HashMap<String,Object>();
		List<Map<String, String>> listMap = sm.getTempLate(m);
		return JsonHelper.json(listMap);
	}
	
	/**
	 * 根据yearAndMonth 和 templateId和租户ID查到相应的数据
	 * @param templateId    模板ID 
	 * 		  yearAndMonth  年月
	 * @return json字符串
	 */
	public String allShortMessage() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String yearAndMonth = DataFormat.getStringKey(map, "yearAndMonth");	
		String templateId = DataFormat.getStringKey(map, "templateId");
		IShortMessageintf iShortMessageintf = CallerProxy.getSVBean(IShortMessageintf.class, "shortMessageTF", new TypeToken<Pagination<SysSmsSendHisOut>>(){}.getType());
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("yearAndMonth", yearAndMonth);
		data.put("templateId",templateId);
		Pagination<SysSmsSendHisOut> pagination = iShortMessageintf.allShortMessage(data);
		return JsonHelper.json(pagination);
	}
	
	
}


