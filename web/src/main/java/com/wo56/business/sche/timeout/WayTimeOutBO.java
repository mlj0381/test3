package com.wo56.business.sche.timeout;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.sche.timeout.vo.WayTimeOutDtl;
import com.wo56.business.sche.timeout.vo.in.TimeOutQueryIn;
import com.wo56.common.consts.IntfCodeConsts;

public class WayTimeOutBO extends BaseBO{
	
	/**
	 * 查询时效异常定义列表
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doQuery()throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		//直接将后台的JSON串输出到前台
		TimeOutQueryIn paramIn = new TimeOutQueryIn();
		BeanUtils.populate(paramIn, inParam);
		paramIn.setInCode(IntfCodeConsts.SCHE.TIME_OUT_QUERY);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	
	/****
	 * 保存异常处理
	 * 
	 * 
	 * */
	public String doSave()throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		//直接将后台的JSON串输出到前台
		TimeOutQueryIn paramIn = new TimeOutQueryIn();
		BeanUtils.populate(paramIn, inParam);
		paramIn.setInCode(IntfCodeConsts.SCHE.TIME_OUT_SAVE);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/****
	 * 查询处理明细
	 * 
	 * 
	 * */
	public String doQueryDtl()throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		//直接将后台的JSON串输出到前台
		TimeOutQueryIn paramIn = new TimeOutQueryIn();
		BeanUtils.populate(paramIn, inParam);
		paramIn.setInCode(IntfCodeConsts.SCHE.TIME_OUT_DTLE);
		ListOutParamVO<WayTimeOutDtl> out = CallerProxy.call(paramIn, new TypeToken<ListOutParamVO<WayTimeOutDtl>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	

}
