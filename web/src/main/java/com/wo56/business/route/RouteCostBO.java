package com.wo56.business.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
//import com.wo56.business.ac.vo.AcFeeConfig;
import com.wo56.business.ac.vo.out.AcOrgIdFeeParamOut;
import com.wo56.business.ord.vo.in.OrdRouteCostIn;
import com.wo56.business.route.vo.in.QueryRouteCostIn;
import com.wo56.business.route.vo.in.RouteCostIn;
import com.wo56.business.route.vo.in.RouteFeeIn;
//import com.wo56.common.utils.AcFeeCacheDataUtil;
public class RouteCostBO extends BaseBO{
	/**
	 * 130004
	 * 标准路径费用查询 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String queryRouteCost() throws Exception{
	    RouteCostIn routeIn = new RouteCostIn();
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BeanUtils.populate(routeIn, map);
		int floor = DataFormat.getIntKey(map,"floor");
		int descRegion = DataFormat.getIntKey(map,"descRegion");
		
		int isLift = DataFormat.getIntKey(map,"isLift");
		int collectingMoney = DataFormat.getIntKey(map,"collectingMoney");
		Map reqmap = new HashMap();
		reqmap.put("1004", isLift);
		reqmap.put("1005", floor);
		reqmap.put("1007", descRegion);
		reqmap.put("1006", collectingMoney);
		routeIn.setIparmMap(reqmap);
		BaseUser baseUser = SysContexts.getCurrentOperator();
		routeIn.setBeginOrgId(baseUser.getOrgId());
		routeIn.setInputUserId(Long.valueOf(baseUser.getTenantId()));
		Map feeMap = new HashMap();
		Map paramMap = new HashMap();
		if(StringUtils.isNotEmpty(DataFormat.getStringKey(map, "g_0.weight")) && StringUtils.isNotEmpty(DataFormat.getStringKey(map, "g_0.volume")) ){
			double w = Double.parseDouble(DataFormat.getStringKey(map, "g_0.weight"));
			double v = Double.parseDouble(DataFormat.getStringKey(map, "g_0.volume"));
			String handingCosts = DataFormat.getStringKey(map, "g_0.handingCosts");
			if(StringUtils.isNotEmpty(handingCosts) && !handingCosts.equals("0")){
				routeIn.setIsCalHandling(true);
			}else{
				routeIn.setIsCalHandling(false);
			}
			routeIn.setWeight(w);
			routeIn.setVolume(v);
			routeIn.setGoodMapType("g_0");
			SimpleOutParamVO<Map> out = CallerProxy.call(routeIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
			Map map1 = out.getContent();
			paramMap.put("g_0", map1.get("billingType"));
			List list = (List)map1.get("g_0");
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Map outMap=(Map)list.get(i);
					feeMap.put(Long.parseLong(outMap.get("feeId").toString()), outMap.get("costAmount"));
				}
			 }
		}
		if(StringUtils.isNotEmpty(DataFormat.getStringKey(map, "g_1.weight")) && StringUtils.isNotEmpty(DataFormat.getStringKey(map, "g_1.volume")) ){
			double w = Double.parseDouble(DataFormat.getStringKey(map, "g_1.weight"));
			double v = Double.parseDouble(DataFormat.getStringKey(map, "g_1.volume"));
			String handingCosts = DataFormat.getStringKey(map, "g_1.handingCosts");
			if(StringUtils.isNotEmpty(handingCosts) && !handingCosts.equals("0")){
				routeIn.setIsCalHandling(true);
			}else{
				routeIn.setIsCalHandling(false);	
			}
			routeIn.setWeight(w);
			routeIn.setVolume(v);
			routeIn.setGoodMapType("g_1");
			SimpleOutParamVO<Map> out = CallerProxy.call(routeIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
			Map map2 = out.getContent();
			List list = (List)map2.get("g_1");
			feeMap = getFeeMap(feeMap,list);
			paramMap.put("g_1", map2.get("billingType"));
		}
		if(StringUtils.isNotEmpty(DataFormat.getStringKey(map, "g_2.weight")) && StringUtils.isNotEmpty(DataFormat.getStringKey(map, "g_2.volume"))){
			double w = Double.parseDouble(DataFormat.getStringKey(map, "g_2.weight"));
			double v = Double.parseDouble(DataFormat.getStringKey(map, "g_2.volume"));
			routeIn.setWeight(w);
			routeIn.setVolume(v);
			routeIn.setGoodMapType("g_2");
			SimpleOutParamVO<Map> out = CallerProxy.call(routeIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
			Map map3 = out.getContent();
			List list = (List)map3.get("g_2");
			feeMap = getFeeMap(feeMap,list);
			paramMap.put("g_2", map3.get("billingType"));
		}
		if(StringUtils.isNotEmpty(DataFormat.getStringKey(map, "g_3.weight")) && StringUtils.isNotEmpty(DataFormat.getStringKey(map, "g_3.volume"))){
			double w = Double.parseDouble(DataFormat.getStringKey(map, "g_3.weight"));
			double v = Double.parseDouble(DataFormat.getStringKey(map, "g_3.volume"));
			routeIn.setWeight(w);
			routeIn.setVolume(v);
			routeIn.setGoodMapType("g_3");
			SimpleOutParamVO<Map> out = CallerProxy.call(routeIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
			Map map4 = out.getContent();
			List list = (List)map4.get("g_3");
			feeMap = getFeeMap(feeMap,list);
			paramMap.put("g_3", map4.get("billingType"));
		}
		if(StringUtils.isNotEmpty(DataFormat.getStringKey(map, "g_4.weight")) && StringUtils.isNotEmpty(DataFormat.getStringKey(map, "g_4.volume"))){
			double w = Double.parseDouble(DataFormat.getStringKey(map, "g_4.weight"));
			double v = Double.parseDouble(DataFormat.getStringKey(map, "g_4.volume"));
			routeIn.setWeight(w);
			routeIn.setVolume(v);
			routeIn.setGoodMapType("g_4");
			SimpleOutParamVO<Map> out = CallerProxy.call(routeIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
			Map map5 = out.getContent();
			List list = (List)map5.get("g_4");
			feeMap = getFeeMap(feeMap,list);
			paramMap.put("g_4", map5.get("billingType"));
		}
		List costList = queryCost(feeMap);
		/**总成本**/
		paramMap.put("costList", costList);
		return JsonHelper.json(paramMap);
	}

	
	/**
	 * 130009
	 * 模拟算费接口
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getRouteCost() throws Exception{
		QueryRouteCostIn routeIn = new QueryRouteCostIn();
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BeanUtils.populate(routeIn, map);
		int floor = DataFormat.getIntKey(map,"floor");
		int descRegion = DataFormat.getIntKey(map,"descRegion");
		//String handingCosts = DataFormat.getStringKey(map, "handingCosts");
		int isLift = DataFormat.getIntKey(map,"isLift");
		int collectingMoney = DataFormat.getIntKey(map,"collectingMoney");
		Map reqmap = new HashMap();
		reqmap.put("1004", isLift);
		reqmap.put("1005", floor);
		reqmap.put("1007", descRegion);
		reqmap.put("1006", collectingMoney);
		routeIn.setIparmMap(reqmap);
		BaseUser baseUser = SysContexts.getCurrentOperator();
		routeIn.setInputUserId(Long.valueOf(baseUser.getTenantId()));
		Map feeMap = new HashMap();
		Map paramMap = new HashMap();
		SimpleOutParamVO<Map> out = CallerProxy.call(routeIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		Map map1 = out.getContent();
		
		return JsonHelper.json(map1);
	}

	
	
	
	
	/**计算各科目项的费用**/
	private Map getFeeMap(Map feeMap,List list ){
		if(list.size()>0){
			
			 OUT:
			 for(int i=0;i<list.size();i++){
				 Iterator<Map.Entry<Object, Object>> it = feeMap.entrySet().iterator();
				Map outMap = (Map)list.get(i);
				while (it.hasNext()) {
					 Map.Entry<Object, Object> entry = it.next();
					 long keys =Long.parseLong(entry.getKey().toString());
					 if((outMap.get("feeId").toString()).equals(entry.getKey().toString())){
						long value = Long.parseLong(entry.getValue().toString())+Long.parseLong(outMap.get("costAmount").toString());
						//feeOut.setCostAmount(value);
						feeMap.put(keys, value);
						continue OUT;
					}
				 }
				feeMap.put(outMap.get("feeId"), outMap.get("costAmount"));
			}
		}
		return feeMap;
	}
	
	/**封装返回结果各项费用Map**/
	private List queryCost (Map costMap){
		  Iterator<Map.Entry<Object, Object>> it = costMap.entrySet().iterator();
		  List costList = new ArrayList();
		  AcOrgIdFeeParamOut out = null;
		 while (it.hasNext()) {
		   Map.Entry<Object, Object> entry = it.next();
			   long keys = Long.parseLong(entry.getKey().toString());
			   long value = Long.parseLong(entry.getValue().toString());
			  // AcFeeConfig fig = AcFeeCacheDataUtil.getAcFeeConfig(keys);
			   out= new AcOrgIdFeeParamOut();
			   out.setCostAmount(value);
			   out.setFeeId(keys);
			   //out.setFeeName(fig.getFeeName());
			   costList.add(out);
		 }
		return costList;
	} 
	/**
	 * 130005
	 * 保存路由线路费用
	 * @return
	 * @throws Exception
	 */
	public String doSaveOrdRouteAndOrdOrg() throws Exception{
		RouteFeeIn routeIn = new RouteFeeIn();
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BeanUtils.populate(routeIn, map);
		int floor = DataFormat.getIntKey(map,"floor");
		int descRegion = DataFormat.getIntKey(map,"descRegion");
		int isLift = DataFormat.getIntKey(map,"isLift");
		int collectingMoney = DataFormat.getIntKey(map,"collectingMoney");
		Map reqmap = new HashMap();
		reqmap.put("1004", isLift);
		reqmap.put("1005", floor);
		reqmap.put("1007", descRegion);
		reqmap.put("1006", collectingMoney);
		routeIn.setIparmMap(reqmap);
		BaseUser baseUser = SysContexts.getCurrentOperator();
		routeIn.setBeginOrgId(baseUser.getOrgId());
		routeIn.setInputUserId(Long.valueOf(baseUser.getTenantId()));
		String rtn="";
		if(map.get("g_0.volume")!=null){
			double w = Double.parseDouble(DataFormat.getStringKey(map, "g_0.weight"));
			double v = Double.parseDouble(DataFormat.getStringKey(map, "g_0.volume"));
			routeIn.setWeight(w);
			routeIn.setVolume(v);
			routeIn.setGoodMapType("g_0");
			 rtn = CallerProxy.call(routeIn);
		}
		if(map.get("g_1.weight")!=null){
			double w = Double.parseDouble(DataFormat.getStringKey(map, "g_1.weight"));
			double v = Double.parseDouble(DataFormat.getStringKey(map, "g_1.volume"));
			routeIn.setWeight(w);
			routeIn.setVolume(v);
			routeIn.setGoodMapType("g_1");
			rtn = CallerProxy.call(routeIn);
		}
		if(map.get("g_2.weight")!=null){
			double w = Double.parseDouble(DataFormat.getStringKey(map, "g_2.weight"));
			double v = Double.parseDouble(DataFormat.getStringKey(map, "g_2.volume"));
			routeIn.setWeight(w);
			routeIn.setVolume(v);
			routeIn.setGoodMapType("g_2");
			rtn= CallerProxy.call(routeIn);
		}
		if(map.get("g_3.weight")!=null){
			double w = Double.parseDouble(DataFormat.getStringKey(map, "g_3.weight"));
			double v = Double.parseDouble(DataFormat.getStringKey(map, "g_3.volume"));
			routeIn.setWeight(w);
			routeIn.setVolume(v);
			routeIn.setGoodMapType("g_3");
		    rtn = CallerProxy.call(routeIn);
		}
		if(map.get("g_4.weight")!=null){
			double w = Double.parseDouble(DataFormat.getStringKey(map, "g_4.weight"));
			double v = Double.parseDouble(DataFormat.getStringKey(map, "g_4.volume"));
			routeIn.setWeight(w);
			routeIn.setVolume(v);
			routeIn.setGoodMapType("g_4");
		    rtn = CallerProxy.call(routeIn);
			/*SimpleOutParamVO<Map> out = CallerProxy.call(routeIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
			Map map5 = out.getContent();
			List list = (List)map5.get("g_4");
			feeMap = getFeeMap(feeMap,list);
			paramMap.put("g_4", map5.get("billingType"));*/
			//Long amountCoust  = Long.parseLong(map5.get("amountCoust").toString());
			//amountFee+=amountCoust;
		}
		return rtn;
	}
	/*public static void main(String[] args) throws Exception {
		RouteCostBO outBo = new RouteCostBO();
	   System.out.println(outBo.queryRouteCost());
   }*/
	
	/**
	 * 查询订单费用
	 * @return
	 * @throws Exception
	 */
	public String queryOrderCost() throws Exception{
		OrdRouteCostIn route = new OrdRouteCostIn();
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BeanUtils.populate(route, map);
	//	ListOutParamVO<OrdOrderCostOut> out = CallerProxy.call(route, new TypeToken<ListOutParamVO<OrdOrderCostOut>>(){}.getType());
		SimpleOutParamVO<Map> out = CallerProxy.call(route, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
}
