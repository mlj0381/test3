package com.wo56.business.sche.macth;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.sche.ord.vo.in.MatchQueryIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class MatchWeightBO {

	/**
	 * 查询匹配项
	 * */
	public String doQueryItem( ) throws  Exception{
		MatchQueryIn matchQueryIn = new MatchQueryIn();
		matchQueryIn.setInCode(InterFacesCodeConsts.BASE.QUERY_CHAIN);
		SimpleOutParamVO<Map> out = CallerProxy.call(matchQueryIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	

	/**
	 * 查询匹配信用值
	 * */
	public String doQueryChain( ) throws  Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		MatchQueryIn matchQueryIn = new MatchQueryIn();
		BeanUtils.populate(matchQueryIn, map);
		matchQueryIn.setInCode(InterFacesCodeConsts.BASE.QUERY_CHAIN_SIMLAR);
		SimpleOutParamVO<Map> out = CallerProxy.call(matchQueryIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
		
	}
	
	/**
	 * 修改匹配项的权重比
	 * */
	public String modifyPercent( ) throws  Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		MatchQueryIn matchQueryIn = new MatchQueryIn();
		BeanUtils.populate(matchQueryIn, map);
		matchQueryIn.setInCode(InterFacesCodeConsts.BASE.MODIFY_PERCENT);
		SimpleOutParamVO<Map> out = CallerProxy.call(matchQueryIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/**
	 * 新增匹配项的信用比
	 * */
	public String doSave() throws  Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		MatchQueryIn matchQueryIn = new MatchQueryIn();
		BeanUtils.populate(matchQueryIn, map);
		matchQueryIn.setInCode(InterFacesCodeConsts.BASE.DOSAVE_SIMILAR);
		SimpleOutParamVO<Map> out = CallerProxy.call(matchQueryIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	
	
}
