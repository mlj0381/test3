package com.wo56.business.ac.impl;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ac.vo.in.AcFeeConfigAddIn;
import com.wo56.business.ac.vo.in.AcFeeConfigIn;

public class AcFeeConfigBO extends BaseBO{
    public String doQueryFeeConfig() throws Exception{
    	Map<String, String[]> map = SysContexts.getRequestParameterMap();
    	AcFeeConfigIn feeConfigIn = new AcFeeConfigIn();
		BeanUtils.populate(feeConfigIn, map);
		PageOutParamVO<Map> out = CallerProxy.call(feeConfigIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent()); 
    }
    
    
    
    
    public String doFeeConfig() throws Exception{
    	Map<String, String[]> map = SysContexts.getRequestParameterMap();
    	AcFeeConfigAddIn feeConfigIn = new AcFeeConfigAddIn();
		BeanUtils.populate(feeConfigIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(feeConfigIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent()); 
    }
}
