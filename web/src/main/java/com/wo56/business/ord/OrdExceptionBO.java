package com.wo56.business.ord;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ord.vo.in.OrdExceptionDeal;
import com.wo56.business.ord.vo.in.OrdExceptionDetailIn;
import com.wo56.business.ord.vo.in.OrdExceptionQueyIn;
import com.wo56.business.ord.vo.in.OrdExceptionSaveIn;

public class OrdExceptionBO {

	
	/**
	 * 查询异常列表 分页处理
	 * 120025
	 * @return
	 * @throws Exception
	 */
	public String doQuery()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrdExceptionQueyIn query = new OrdExceptionQueyIn();
		BeanUtils.populate(query, map);
		PageOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		if(out.getContent() != null){
			return JsonHelper.json(out.getContent());
		}else{
			return null;
		}
	}
	
	/**
	 * 查询异常详情
	 * 120027
	 * @return
	 * @throws Exception
	 */
	public String queryDetail()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrdExceptionDetailIn query = new OrdExceptionDetailIn();
		BeanUtils.populate(query, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		if(out.getContent() != null){
			return JsonHelper.json(out.getContent());
		}else{
			return null;
		}
	}
	
	/**
	 * 保存
	 * 120026
	 */
	public String doSave()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrdExceptionSaveIn query = new OrdExceptionSaveIn();
		BeanUtils.populate(query, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		if(out.getContent() != null){
			return JsonHelper.json(out.getContent());
		}else{
			return null;
		}
	}
	
	/**
	 * 异常处理
	 * 120031
	 * @return
	 * @throws Exception
	 */
	public String deal()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrdExceptionDeal query = new OrdExceptionDeal();
		BeanUtils.populate(query, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		if(out.getContent() != null){
			return JsonHelper.json(out.getContent());
		}else{
			return null;
		}
	}
}
