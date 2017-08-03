package com.wo56.business.route;

import java.util.List;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.route.vo.RouteRouting;
import com.wo56.business.route.vo.in.RouteFeeConfigParamDtlIn;
import com.wo56.business.route.vo.in.RouteFeeConfigParamIn;
import com.wo56.business.route.vo.in.RouteFeeConfigSaveParamIn;
import com.wo56.business.route.vo.in.RouteRutingDetailIn;

public class RouteFeeConfigManageBO extends BaseBO {

	/***
	 * 查询
	 * */
	public String doQuery() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		RouteFeeConfigParamIn paramIn = new RouteFeeConfigParamIn();

		long startOrgid = DataFormat.getLongKey(map,"startOrgid");
	      		paramIn.setStartOrgid(startOrgid);
		long endOrgid = DataFormat.getLongKey(map,"endOrgid");
	      		paramIn.setEndOrgid(endOrgid);
		int lfType = DataFormat.getIntKey(map,"lfType");
	      		paramIn.setLfType(lfType);
	    PageOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/***
	 * 保存
	 * */
	public String doSave() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		RouteFeeConfigSaveParamIn paramIn = new RouteFeeConfigSaveParamIn();
		BeanUtils.populate(paramIn, map);
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/***
	 * 通过起始网点查找线路终止网点
	 * */
	public String queryLinePoint() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		long startOrgid = DataFormat.getLongKey(map,"startOrgid");
		RouteRutingDetailIn route = new RouteRutingDetailIn();
		route.setBeginOrgId(startOrgid);
		ListOutParamVO<RouteRouting> out = CallerProxy.call(route, new TypeToken<ListOutParamVO<RouteRouting>>(){}.getType());
		List<RouteRouting> list = out.getContent();
		return JsonHelper.json(list);
	}
	
	
	/***
	 * 查询线路费用详情
	 * */
	public String getRouteFeeDtl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		//BaseUser baseUser = SysContexts.getCurrentOperator();
		RouteFeeConfigParamDtlIn paramIn = new RouteFeeConfigParamDtlIn();
		BeanUtils.populate(paramIn, map);
		ListOutParamVO out = CallerProxy.call(paramIn, new TypeToken<ListOutParamVO>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
}
