package com.wo56.business.cm.impl;

import java.net.MalformedURLException;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.vo.CmArea;
import com.wo56.common.utils.GpsUtil;

public class CmAreaBO {
	/**
	 * 关键字获取详细地址
	 * @return
	 * @throws Exception 
	 * @throws MalformedURLException 
	 */
	public String getAddressByCode() throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		String coords = DataFormat.getStringKey(inParam, "address");
		String provinceName = DataFormat.getStringKey(inParam, "provinceName");
		return JsonHelper.json(GpsUtil.getSuggestion(provinceName,coords));
	}
	/**
	 * 保存也修改
	 * @return
	 * @throws Exception
	 */
	public String doSaveOrUpdateArea() throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmAreaIntf intf = CallerProxy.getSVBean(ICmAreaIntf.class, "cmAreaTF", new TypeToken<Map<String,Object>>(){}.getType());
		return intf.doSaveOrUpdateArea(inParam);
	}
	/**
	 * 列表查询
	 * @return
	 * @throws Exception
	 */
	public String getCmAreaList() throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmAreaIntf intf = CallerProxy.getSVBean(ICmAreaIntf.class, "cmAreaTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.getCmAreaList(inParam));
	}
	/**
	 * 获取详情
	 * @return
	 * @throws Exception
	 */
	public String getCmArea() throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmAreaIntf intf = CallerProxy.getSVBean(ICmAreaIntf.class, "cmAreaTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.getCmArea(inParam));
	}
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public String delCmArea() throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmAreaIntf intf = CallerProxy.getSVBean(ICmAreaIntf.class, "cmAreaTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.delCmArea(inParam));
	}
}
