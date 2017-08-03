package com.wo56.business.cm.impl;

import java.util.HashMap;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.vo.in.DelCmCarrierCompanyIn;
import com.wo56.business.cm.vo.in.GetCmCarrierCompanyIn;
import com.wo56.business.cm.vo.in.QueryCmCarrierCompanyDtlIn;
import com.wo56.business.cm.vo.in.QueryCmCarrierCompanyIn;
import com.wo56.business.cm.vo.in.SaveCmCarrierCompanyIn;

public class CmCarrierCompanyBO {
	
	
	/**
	 * 保存承运商信息
	 * @return
	 * @throws Exception
	 */
	public String doSaveCmCarrCampany() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SaveCmCarrierCompanyIn saveCmCompany = new SaveCmCarrierCompanyIn();
		BeanUtils.populate(saveCmCompany, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(saveCmCompany, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	/**查询承运商详情**/
	public String doQueryCmCarrCompanyDtl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryCmCarrierCompanyDtlIn cmCompanyDtl = new QueryCmCarrierCompanyDtlIn();
		BeanUtils.populate(cmCompanyDtl, map);
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(cmCompanyDtl, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	
	/**
	 * 查询承运商信息
	 * @return
	 * @throws Exception
	 */
	public String doQueryCmCarrCompany() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryCmCarrierCompanyIn cmCompany = new QueryCmCarrierCompanyIn();
		BeanUtils.populate(cmCompany, map);
		PageOutParamVO<Map> out = CallerProxy.call(cmCompany, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent()); 
	}
	
	/**
	 * 删除承运商信息
	 * @return
	 * @throws Exception
	 */
	public String doDelCmCarrCompany() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		DelCmCarrierCompanyIn  delCmCompany= new DelCmCarrierCompanyIn();
		BeanUtils.populate(delCmCompany, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(delCmCompany, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	
	
	/**
	 * 查询承运商信息(没有分页)
	 * @return
	 * @throws Exception
	 */
	public String getCmCarrCompany() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		GetCmCarrierCompanyIn getCompany = new GetCmCarrierCompanyIn();
		BeanUtils.populate(getCompany, map);
		ListOutParamVO<HashMap> out = CallerProxy.call(getCompany, new TypeToken<ListOutParamVO<HashMap>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
}
