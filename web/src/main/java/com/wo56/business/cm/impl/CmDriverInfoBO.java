package com.wo56.business.cm.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.vo.in.CmDriverInfoIn;
import com.wo56.business.cm.vo.in.CmDriverInfoInDel;
import com.wo56.business.cm.vo.in.DelCmDriverInfoIn;
import com.wo56.business.cm.vo.in.QueryDriverDtlIn;
import com.wo56.business.cm.vo.in.SaveCmDriverInfoIn;

public class CmDriverInfoBO extends BaseBO{
	
	/**
	 * 查询司机信息
	 * @return
	 * @throws Exception
	 */
	public String doQueryCmDriver() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		CmDriverInfoIn cmUser = new CmDriverInfoIn();
		BeanUtils.populate(cmUser, map);
		PageOutParamVO<Map> out = CallerProxy.call(cmUser, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent()); 
	}
	/**
	 * 保存司机信息
	 * @return
	 * @throws Exception
	 */
	public String doSaveCmDriverInfo() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SaveCmDriverInfoIn saveCmUser = new SaveCmDriverInfoIn();
		BeanUtils.populate(saveCmUser, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(saveCmUser, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	

	/**
	 *删除司机
	 * @return
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public String doDelVehicle() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		DelCmDriverInfoIn sysUrlIn = new DelCmDriverInfoIn();
		BeanUtils.populate(sysUrlIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(sysUrlIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	/**
	 * 查询司机详情
	 * @return
	 * @throws Exception
	 */
	public String queryDriverDtl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryDriverDtlIn driverDtlIn = new QueryDriverDtlIn();
		BeanUtils.populate(driverDtlIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(driverDtlIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	/**
	 *删除司机
	 * @return
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public String doDelDriverInfo() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		CmDriverInfoInDel sysUrlIn = new CmDriverInfoInDel();
		BeanUtils.populate(sysUrlIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(sysUrlIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	
	/**
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doQueryDriver() throws Exception {
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		ICmDriverInfoIntf sv = CallerProxy.getSVBean(ICmDriverInfoIntf.class, "cmDriverInfoTF", new TypeToken<Map<String,Object>>(){}.getType());
		Map rtnMap = sv.doQueryDriver(map);
		if(rtnMap!=null){
		return JsonHelper.json(rtnMap);
		}else{
			return "";
		}
	}
	
}
