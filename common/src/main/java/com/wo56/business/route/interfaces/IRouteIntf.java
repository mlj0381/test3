package com.wo56.business.route.interfaces;

import java.util.List;
import java.util.Map;

import com.wo56.business.route.vo.RouteRouting;
import com.wo56.business.route.vo.out.RouteRoutingOut;


public interface IRouteIntf {
	
	public List<RouteRouting> queryCurrRoateRuting(Map<String, String> inParam) throws Exception;
	
	public List<RouteRouting> queryRoateRutingIsShort(Map<String, Object> inParam) throws Exception;
	
	public RouteRoutingOut getRouteRoutingOut(Map<String,Object> inParam) throws Exception;
	
	public String doDelRouteRoutin(Map<String,Object> inParam) throws Exception;
	
	public String doUpdateRouteRouting(Map<String,Object> inParam) throws Exception;
	
	public Map<String,Object> getRouteDescOrgId(Map<String,Object> inParam);
}
	
