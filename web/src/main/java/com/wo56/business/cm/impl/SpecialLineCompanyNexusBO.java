package com.wo56.business.cm.impl;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.intf.ISpecialLineCompanyNexusIntf;

public class SpecialLineCompanyNexusBO {
	
	/**
	 * 查询专线拉与包公司关系
	 * @return
	 * @throws Exception
	 */
	public String doQuerySpecialLineCompanyNexus()throws Exception{
		Map<String,Object>map=SysContexts.getRequestParamFlatMap();
		ISpecialLineCompanyNexusIntf orgTF = CallerProxy.getSVBean(ISpecialLineCompanyNexusIntf.class,"specialLineCompanyNexusTF" ,new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(orgTF.doQuerySpecialLineCompanyNexus(map));
		
	}
	/**
	 * 新增专线拉与包公司关系
	 * @return
	 * @throws Exception
	 */
	public String doSaveSpecialLineCompanyNexus()throws Exception{
		Map<String,Object>map=SysContexts.getRequestParamFlatMap();
		ISpecialLineCompanyNexusIntf orgTF = CallerProxy.getSVBean(ISpecialLineCompanyNexusIntf.class,"specialLineCompanyNexusTF" ,new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(orgTF.doSaveSpecialLineCompanyNexus(map));
	}
	/**
	 * 删除专线与拉包公司关系
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String deleteSpecialLineCompanyNexus() throws Exception{
		Map<String,Object>inParam=SysContexts.getRequestParamFlatMap();
		ISpecialLineCompanyNexusIntf orgTF = CallerProxy.getSVBean(ISpecialLineCompanyNexusIntf.class,"specialLineCompanyNexusTF" ,new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(orgTF.deleteSpecialLineCompanyNexus(inParam));
	}
	/**
	 * 根据Id查询拉包公司与专线关系
	 * @return
	 * @throws Exception
	 */
	public String queryByIdSpecialLineCompanyNexus()throws Exception{
		Map<String,Object>inParam=SysContexts.getRequestParamFlatMap();
		ISpecialLineCompanyNexusIntf orgTF = CallerProxy.getSVBean(ISpecialLineCompanyNexusIntf.class,"specialLineCompanyNexusTF" ,new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(orgTF.queryByIdSpecialLineCompanyNexus(inParam));
	}
	public String doUpdateSpecialLineCompanyNexus()throws Exception{
		Map<String,Object>inParam=SysContexts.getRequestParamFlatMap();
		ISpecialLineCompanyNexusIntf orgTF = CallerProxy.getSVBean(ISpecialLineCompanyNexusIntf.class,"specialLineCompanyNexusTF" ,new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(orgTF.doUpdateSpecialLineCompanyNexus(inParam));
	}
}
