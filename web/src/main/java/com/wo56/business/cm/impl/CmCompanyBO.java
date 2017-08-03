package com.wo56.business.cm.impl;

import java.util.HashMap;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.MapInParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.vo.in.QueryCmCompanyIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class CmCompanyBO {

	
	/**
	 * 提供合同公司查询
	 * @return
	 * @throws Exception
	 */
	public String queryCompanyList() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		QueryCmCompanyIn queryUserIn = new  QueryCmCompanyIn();
		BeanUtils.populate(queryUserIn, dataMap);
		queryUserIn.setRows(5);
		PageOutParamVO<Map> out = CallerProxy.call(queryUserIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
	//	paramMap.put(SysConst.REQUEST_JQGRID_PAGE_SIZE,"5");

		return JsonHelper.json( out.getContent());
	}
	

	/**
	 * 合同公司查询
	 * @return
	 * @throws Exception
	 */
	public String getCompanyList() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		int type = DataFormat.getIntKey(dataMap, "type");
			Map paramMap = new HashMap();
			MapInParamVO mapInParamVO = new MapInParamVO(InterFacesCodeConsts.CM.GET_COMPANY,paramMap);
			if(type==2){
				paramMap.put("type","5");	
			}
			
			ListOutParamVO out = CallerProxy.call(mapInParamVO, new TypeToken<ListOutParamVO>(){}.getType());
			return JsonHelper.json( out.getContent());
		}

	/**
	 * 公司查询
	 * @return
	 * @throws Exception
	 */
	public String queryCompanyListByPage() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		QueryCmCompanyIn queryUserIn = new  QueryCmCompanyIn();
		BeanUtils.populate(queryUserIn, dataMap);
		PageOutParamVO<Map> out = CallerProxy.call(queryUserIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}

}
