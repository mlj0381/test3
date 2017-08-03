package com.wo56.business.route.interfaces;

import java.util.List;
import java.util.Map;

import com.wo56.business.route.vo.out.RouteAreaRelCfgOut;


public interface IRouteAreaRelCfgIntf {

	public List<Map> getRouteAreaRelListCfgList(Map<String,Object> inParam) throws Exception;
	
	public RouteAreaRelCfgOut getRouteAreaRel(Map<String,Object> inParam) throws Exception;
}
