package com.wo56.business.common.intf;

import java.util.Map;

import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.wo56.business.common.impl.SelectStaticSV;

public class SelectStaticDataTF {
	
	/**
	 * 获取静态数据
	 * @param inParam
	 * @return
	 */
	public String getData(Map<String,Object> inParam){
		String codeValue = DataFormat.getStringKey(inParam, "codeValue");
		String codeType = DataFormat.getStringKey(inParam, "codeType");
		SelectStaticSV selectStaticSV = new SelectStaticSV();
		Map map = selectStaticSV.getSelectStatic(codeType, codeValue);
		return JsonHelper.json(map);
	}
}
