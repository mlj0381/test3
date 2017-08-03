package com.wo56.business.route;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.wo56.business.org.vo.Organization;
import com.wo56.business.route.interfaces.IRouteIntf;
import com.wo56.business.route.vo.RouteRouting;
import com.wo56.business.route.vo.RouteTowards;
import com.wo56.business.route.vo.RouteTowardsDtl;
import com.wo56.business.route.vo.in.GetRouteTowardsIn;
import com.wo56.business.route.vo.in.RountSet;
import com.wo56.business.route.vo.in.RouteOrgtypeRutingIn;
import com.wo56.business.route.vo.in.RouteRutingIn;
import com.wo56.business.route.vo.in.RouteTowardsDtlIn;
import com.wo56.business.route.vo.in.RouteTowardsIn;
import com.wo56.business.route.vo.in.RouteTowardsQueryIn;
import com.wo56.business.route.vo.in.RouteTowardsViewIn;
import com.wo56.business.route.vo.in.TaskRouteIn;
import com.wo56.business.route.vo.in.TowardsAndDtlIn;
import com.wo56.business.route.vo.in.TowardsQuery;
import com.wo56.business.route.vo.out.RouteRoutingOut;
import com.wo56.business.route.vo.out.RouteTowardsViewOut;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class RouteBO extends BaseBO{
	/**
	 * 根据起始网点查询可到达网点
	 * @return
	 * @throws Exception
	 */
	public String queryRoateTowards() throws Exception{
		RouteTowardsIn route = new RouteTowardsIn();
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BeanUtils.populate(route, map);
		long orgId = DataFormat.getLongKey(map, "orgId");
		if (orgId > 0) {
			route.setBeginOrgId(orgId);
		} else {
			BaseUser baseUser = SysContexts.getCurrentOperator();
			route.setBeginOrgId(baseUser.getOrgId());
			if (route.getIsSimulaet() != 1) {
				route.setBeginOrgId(baseUser.getOrgId());
			}
		}
		ListOutParamVO<RouteTowards> out = CallerProxy.call(route, new TypeToken<ListOutParamVO<RouteTowards>>(){}.getType());
		List<RouteTowards> list = out.getContent();
		return JsonHelper.json(list);
		
	}

	/**
	 * 根据起始网点查询可到达网点
	 * @return
	 * @throws Exception
	 */
	public String getTowards() throws Exception{
		GetRouteTowardsIn route = new GetRouteTowardsIn();
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BeanUtils.populate(route, map);
		ListOutParamVO<RouteTowards> out = CallerProxy.call(route, new TypeToken<ListOutParamVO<RouteTowards>>(){}.getType());
		List<RouteTowards> list = out.getContent();
		return JsonHelper.json(list);
		
	}
	
	
	/**
	 * 发车到达网店查询
	 * @return
	 * @throws Exception
	 */
	public String queryRoateRuting() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		RouteRutingIn rout = new RouteRutingIn();
		BeanUtils.populate(rout, map);
		ListOutParamVO<RouteRouting> out = CallerProxy.call(rout, new TypeToken<ListOutParamVO<RouteRouting>>(){}.getType());
		List<RouteRouting> list = out.getContent();
		return JsonHelper.json(list);
	}
	
	/**
	 * 查询路由线路
	 * @return
	 * @throws Exception
	 */
	public String queryTowardsAndTowardsDetil() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		TowardsAndDtlIn toward = new TowardsAndDtlIn();
		BeanUtils.populate(toward, map);
		/*toward.setBeginOrgId(1L);
		toward.setEndOrgId(1l);*/
		/** Delete By chenjun. 如果没有起始网点，则默认去操作员的orgId
		if(toward.getBeginOrgId()==null || toward.getBeginOrgId()<=0){
			throw new BusinessException("请输入起始网点编号");
		}
		*/
		if (toward.getBeginOrgId() == null || toward.getBeginOrgId() <= 0) {
			BaseUser baseUser = SysContexts.getCurrentOperator();
			toward.setBeginOrgId(baseUser.getOrgId());
		}
		if(toward.getEndOrgId()==null || toward.getEndOrgId()<=0){
			return "";
		}
		//String rtn = CallerProxy.call(toward);
		SimpleOutParamVO<Map> out = CallerProxy.call(toward, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		Map map3 = out.getContent();
		return JsonHelper.json(map3);
	}
	
	/**
	 * 记录费用重算任务表 
	 * @return
	 * @throws Exception
	 */
	public String doSaveRouteTask() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		TaskRouteIn taskIn = new TaskRouteIn();
		BeanUtils.populate(taskIn, map);
		String rtn = CallerProxy.call(taskIn);
		return rtn;
	}
	/**
	 * 查询可到达网点 
	 * @return
	 * @throws Exception
	 */
	public String queryTowardsDtl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		RouteTowardsDtlIn rout = new RouteTowardsDtlIn();
		BeanUtils.populate(rout, map);
		ListOutParamVO<RouteTowardsDtl> out = CallerProxy.call(rout, new TypeToken<ListOutParamVO<RouteTowardsDtl>>(){}.getType());
		List<RouteTowardsDtl> list = out.getContent();
		return JsonHelper.json(list);
	};
		
	/**
	 * 路由管理查询分页 160001
	 * @return
	 * @throws Exception
	 */
	public String doQuery() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		RouteTowardsQueryIn rout = new RouteTowardsQueryIn();
		BeanUtils.populate(rout, map);
		PageOutParamVO<Map> out = CallerProxy.call(rout, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	};
	
	/**
	 * 查看路由详情 160002
	 * @return
	 * @throws Exception
	 */
	public String toView() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		RouteTowardsViewIn rout = new RouteTowardsViewIn();
		BeanUtils.populate(rout, map);
		ListOutParamVO<RouteTowardsViewOut> out = CallerProxy.call(rout, new TypeToken<ListOutParamVO<RouteTowardsViewOut>>(){}.getType());
		return JsonHelper.json( out.getContent());
	};
	
	/**
	 * 线路设置
	 * 130008
	 * @return
	 * @throws Exception
	 */
	public String setRount() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		RountSet rout = new RountSet();
		long orgId = DataFormat.getLongKey(map,"orgId");
		BeanUtils.populate(rout, map);
		BaseUser baseUser = SysContexts.getCurrentOperator();
		rout.setTenantId(Long.valueOf(baseUser.getTenantId()));
		SimpleOutParamVO<Map> out = CallerProxy.call(rout, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		if(out.getContent() != null){
			return JsonHelper.json(out.getContent());
		}else{
			return null;
		}
	}
	
	
	/**
	 * 查询线路
	 * 160008
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String queryTowards()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		TowardsQuery query = new TowardsQuery();
		BeanUtils.populate(query, map);
		BaseUser user=SysContexts.getCurrentOperator();
		query.setTenantId(user.getTenantId());
		
		PageOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		if(out.getContent() != null){
			return JsonHelper.json(out.getContent());
		}else{
			return null;
		}
	}
	
	
	
	/**
	 * 短驳到货，干线到货中心的线路
	 * @return
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public String getUserRoute() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BaseUser user=SysContexts.getCurrentOperator();
		Organization organ = OraganizationCacheDataUtil.getOrganization(user.getOrgId());
		RouteOrgtypeRutingIn rout = new RouteOrgtypeRutingIn();
		BeanUtils.populate(rout, map);
		List<Organization> orgList = new ArrayList<Organization>();
		ListOutParamVO<RouteRouting> out = CallerProxy.call(rout, new TypeToken<ListOutParamVO<RouteRouting>>(){}.getType());
		List<RouteRouting> list = out.getContent();
		
		Map parMap = new HashMap();
		parMap.put("routeList", list);
		parMap.put("orgType", organ.getOrgType());
		return JsonHelper.json(parMap);
	}
	
	
	
	/**
	 * 获取当前网点能够达到的线路
	 * @return
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public String getCurrRoute() throws Exception{
//		IRouteIntf routeIntf = CallerProxy.getSVBean(IRouteIntf.class, "routeTF", new TypeToken<List<RouteRouting>>(){}.getType());
//		Map<String, String> inParam=new HashMap<String, String>();
//		BaseUser user=SysContexts.getCurrentOperator();
//		inParam.put("orgId", String.valueOf(user.getOrgId()));
//		List<RouteRouting> list= routeIntf.queryCurrRoateRuting(inParam);
//		if(list!=null && list.size()>0){
//			return JsonHelper.json(list);
//		}
		return "";
	}
	
	/**
	 * 查询短驳的到达网点信息
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryRoateRutingIsShort() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IRouteIntf routeIntf = CallerProxy.getSVBean(IRouteIntf.class, "routeTF", new TypeToken<List<RouteRouting>>(){}.getType());
		List lists = routeIntf.queryRoateRutingIsShort(inParam);
		Map parMap = new HashMap();
		parMap.put("routeList", lists);
		return JsonHelper.json(parMap);
	}
	
	/**
	 * 通过id查线路
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String getRouteRoutingOut() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IRouteIntf routeIntf = CallerProxy.getSVBean(IRouteIntf.class, "routeTF", new TypeToken<RouteRoutingOut>(){}.getType());
		return JsonHelper.json(routeIntf.getRouteRoutingOut(inParam));
	}
	
	/**
	 * 删除线路
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doDelRouteRoutin() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IRouteIntf routeIntf = CallerProxy.getSVBean(IRouteIntf.class, "routeTF", new TypeToken<RouteRoutingOut>(){}.getType());
		return routeIntf.doDelRouteRoutin(inParam);
	}
	
	/**
	 * 修改线路
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doUpdateRouteRouting() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IRouteIntf routeIntf = CallerProxy.getSVBean(IRouteIntf.class, "routeTF", new TypeToken<RouteRoutingOut>(){}.getType());
		return routeIntf.doUpdateRouteRouting(inParam);
	}
	/**
	 * 修改线路
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String getRouteDescOrgId() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IRouteIntf routeIntf = CallerProxy.getSVBean(IRouteIntf.class, "routeTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(routeIntf.getRouteDescOrgId(inParam));
	}
}
