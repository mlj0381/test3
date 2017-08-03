package com.wo56.business.cm.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.exception.BusinessException;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.vo.in.CmVehicleInfoParamIn;
import com.wo56.business.cm.vo.in.DelCmVehicleInfoIn;
import com.wo56.business.cm.vo.in.QueryCmVehicleInfoIn;
import com.wo56.business.cm.vo.in.QueryVehicleDriverIn;
import com.wo56.business.cm.vo.in.QueryVehicleDtlIn;
import com.wo56.business.cm.vo.in.SaveCmVehicleInfoIn;
import com.wo56.business.cm.vo.out.QueryVehicleDriverOut;

public class CmVehicleInfoBO  extends BaseBO{
	
	/**
	 * 查询车辆信息
	 * @param plateNumber 车牌号码
	 * */
	public String doQuery() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		CmVehicleInfoParamIn cmInParam = new CmVehicleInfoParamIn();
		BeanUtils.populate(cmInParam, map);
		//直接将后台的JSON串输出到前台
		PageOutParamVO<Map> out = CallerProxy.call(cmInParam, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	
	
	/**
	 * 查询车辆信息
	 * @param plateNumber 车牌号码
	 * */
	public String doQueryVehicle() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryCmVehicleInfoIn cmInParam = new QueryCmVehicleInfoIn();
		BeanUtils.populate(cmInParam, map);
		//直接将后台的JSON串输出到前台
		PageOutParamVO<Map> out = CallerProxy.call(cmInParam, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}

	
	/**
	 * 保存车辆信息
	 * @param plateNumber 车牌号码
	 */
	public String doSaveVehicle() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SaveCmVehicleInfoIn cmInParam = new SaveCmVehicleInfoIn();
		String mainDriverName = DataFormat.getStringKey(map, "mainDriverName");
		if(mainDriverName.length() > 20){
			throw new BusinessException("司机名称不能超过20字符，请重新填写！");
		}
		BeanUtils.populate(cmInParam, map);
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(cmInParam, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	/**
	 * 检索司机信息
	 * @return
	 * @throws Exception
	 */
	public String doQueryVehicleDriver() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryVehicleDriverIn query = new QueryVehicleDriverIn();
		String name  = DataFormat.getStringKey(map,"name");
		query.setName(name);
		ListOutParamVO<QueryVehicleDriverOut> out = CallerProxy.call(query, new TypeToken<ListOutParamVO<QueryVehicleDriverOut>>(){}.getType());
		List<QueryVehicleDriverOut> iparmList = out.getContent();
		return JsonHelper.json(out,new String[]{"errorCode","message","status","time"});
	}
	
	/**
	 * 查询车辆详情
	 * @return
	 * @throws Exception
	 */
	public String queryVehicleDtl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryVehicleDtlIn vehicleDtl = new QueryVehicleDtlIn();
		BeanUtils.populate(vehicleDtl, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(vehicleDtl, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	/**
	 *删除车辆
	 * @return
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public String doDelVehicle() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		DelCmVehicleInfoIn sysUrlIn = new DelCmVehicleInfoIn();
		BeanUtils.populate(sysUrlIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(sysUrlIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
}
