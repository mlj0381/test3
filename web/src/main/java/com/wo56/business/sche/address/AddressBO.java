package com.wo56.business.sche.address;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.sche.ord.vo.in.ScheQueryIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class AddressBO extends BaseBO{

	/**
	 * 地址库查询
	 * 
	 * */
	
	public String doQuery() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		//直接将后台的JSON串输出到前台
		ScheQueryIn paramIn = new ScheQueryIn();
		BeanUtils.populate(paramIn, inParam);
		paramIn.setInCode(InterFacesCodeConsts.BASE.ADDRESS_QUERY);
		PageOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		if(out.getContent() != null){
			return JsonHelper.json(out.getContent());
		}else{
			return null;
		}
		
	}
}
