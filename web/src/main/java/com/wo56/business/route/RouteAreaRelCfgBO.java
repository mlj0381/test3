package com.wo56.business.route;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.multitab.CommonVO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.route.interfaces.IRouteAreaRelCfgIntf;
import com.wo56.business.route.vo.RouteAreaRelCfg;
import com.wo56.business.route.vo.in.RouteAreaRelCfgIn;
import com.wo56.business.route.vo.out.RouteAreaRelCfgOut;
import com.wo56.common.consts.IntfCodeConstsLyh;

public class RouteAreaRelCfgBO extends BaseBO{
	
	/**
	 * 分页查询
	 * @return
	 * @throws Exception
	 */
	public String doQueryPage() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		RouteAreaRelCfgIn routeAreaRelCfgIn = new RouteAreaRelCfgIn(IntfCodeConstsLyh.ROUTE.ROUTE_AREA_REL_LIST_QUERY);
		BeanUtils.populate(routeAreaRelCfgIn, inParam);
		PageOutParamVO<CommonVO> out = CallerProxy.call(routeAreaRelCfgIn, new TypeToken<PageOutParamVO<CommonVO>>(){}.getType());
//		Set<String> includeSet=new HashSet<String>();
//		includeSet.add("id");
//		includeSet.add("dzMoney");
//		includeSet.add("createDate");
//		includeSet.add("veriyDate");
//		includeSet.add("receiType");
//		String ret=JsonHelper.jsonPageInclude(retPage, includeSet);
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 运单录入的界面查询配送区域
	 * @return
	 * @throws Exception
	 */
	public String doQueryList() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IRouteAreaRelCfgIntf areaRelCfgIntf = CallerProxy.getSVBean(IRouteAreaRelCfgIntf.class, "routeAreaRelCfgTF", new TypeToken<List>(){}.getType());
		List<Map> list= areaRelCfgIntf.getRouteAreaRelListCfgList(inParam);
		
		return JsonHelper.json(list);
	}
	
	/**
	 * 运单录入的界面查询配送区域
	 * @return
	 * @throws Exception
	 */
	public String doQueryListNew() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IRouteAreaRelCfgIntf areaRelCfgIntf = CallerProxy.getSVBean(IRouteAreaRelCfgIntf.class, "routeAreaRelCfgTF", new TypeToken<RouteAreaRelCfgOut>(){}.getType());
		return JsonHelper.json(areaRelCfgIntf.getRouteAreaRel(inParam));
	}
	
	/**
	 * 根据主键查询
	 * @return
	 * @throws Exception
	 */
	public String doQuery() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		long reLId = DataFormat.getLongKey(inParam,"reLId");
		RouteAreaRelCfgIn routeAreaRelCfgIn = new RouteAreaRelCfgIn(IntfCodeConstsLyh.ROUTE.ROUTE_AREA_REL_QUERY);
		routeAreaRelCfgIn.setRelId(reLId);
		SimpleOutParamVO<RouteAreaRelCfg> out = CallerProxy.call(routeAreaRelCfgIn, new TypeToken<SimpleOutParamVO<RouteAreaRelCfg>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 新增、修改
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		RouteAreaRelCfgIn routeAreaRelCfgIn = new RouteAreaRelCfgIn(IntfCodeConstsLyh.ROUTE.ROUTE_AREA_REL_EDIT);
		BeanUtils.populate(routeAreaRelCfgIn, inParam);
		SimpleOutParamVO<Map> out = CallerProxy.call(routeAreaRelCfgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 批量新增、修改
	 * @return
	 * @throws Exception
	 */
	public String batchEdit() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		long relId = DataFormat.getLongKey(inParam,"relId");
		String relName = DataFormat.getStringKey(inParam,"relName");
		long destOrgId = DataFormat.getLongKey(inParam,"destOrgId");
		long provinceId = DataFormat.getLongKey(inParam,"provinceId");
		String selectedDistrict = DataFormat.getStringKey(inParam,"selectedDistrict");
		int isSeaTransport = DataFormat.getIntKey(inParam,"isSeaTransport");
		String[] selectedDistricts=selectedDistrict.split(",");
		RouteAreaRelCfgIn routeAreaRelCfgIn = new RouteAreaRelCfgIn(IntfCodeConstsLyh.ROUTE.ROUTE_AREA_REL_BATCH_EDIT);
		if(relId>0){
			routeAreaRelCfgIn.setRelId(relId);
		}
		routeAreaRelCfgIn.setRelName(relName);
		routeAreaRelCfgIn.setDestOrgId(destOrgId);
		routeAreaRelCfgIn.setProvinceId(provinceId);
		routeAreaRelCfgIn.setIsSeaTransport(isSeaTransport);
		if(StringUtils.isNotBlank(selectedDistrict)){
			long[] setSelectedDistrictes = new long[selectedDistricts.length];
			for(int i=0;i<selectedDistricts.length;i++){
				setSelectedDistrictes[i]=Long.parseLong(selectedDistricts[i]);
			}
			routeAreaRelCfgIn.setSelectedDistricts(setSelectedDistrictes);
		}
		//城市－
		String choiceCitys = DataFormat.getStringKey(inParam,"choiceCitys");
		String[] choiceCitysStr=choiceCitys.split(",");
		if(StringUtils.isNotBlank(choiceCitys)){
			long[] setChoiceCitysStr = new long[choiceCitysStr.length];
			for(int i=0;i<choiceCitysStr.length;i++){
				setChoiceCitysStr[i]=Long.parseLong(choiceCitysStr[i]);
			}
			routeAreaRelCfgIn.setChoiceCitys(setChoiceCitysStr);
		}
		SimpleOutParamVO<Map> out = CallerProxy.call(routeAreaRelCfgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 批量删除
	 * @return
	 * @throws Exception
	 */
	public String batchDelete() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		String relIds = DataFormat.getStringKey(inParam,"relId");
		RouteAreaRelCfgIn routeAreaRelCfgIn = new RouteAreaRelCfgIn(IntfCodeConstsLyh.ROUTE.ROUTE_AREA_REL_BATCH_DELET);
		routeAreaRelCfgIn.setRelIds(relIds);
		SimpleOutParamVO<Map> out = CallerProxy.call(routeAreaRelCfgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 根据组织和区查询
	 * @return
	 * @throws Exception
	 */
	public String getRouteAreaRelCfgOrg() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		String selectedDistrict = DataFormat.getStringKey(inParam,"selectedDistrict");
		int isSeaTransport = DataFormat.getIntKey(inParam, "isSeaTransport");
		RouteAreaRelCfgIn routeAreaRelCfgIn = new RouteAreaRelCfgIn(IntfCodeConstsLyh.ROUTE.ROUTE_AREA_REL_BATCH_ORG);
		routeAreaRelCfgIn.setIsSeaTransport(isSeaTransport);
		if(StringUtils.isNotBlank(selectedDistrict)){
			String[] selectedDistricts=selectedDistrict.split(",");
			if(selectedDistricts!=null && selectedDistricts.length>0){
				long[] setSelectedDistrictes = new long[selectedDistricts.length];
				for(int i=0;i<selectedDistricts.length;i++){
					setSelectedDistrictes[i]=Long.parseLong(selectedDistricts[i]);
				}
				routeAreaRelCfgIn.setSelectedDistricts(setSelectedDistrictes);
			}
		}
		String choiceCitys = DataFormat.getStringKey(inParam,"choiceCitys");
		if(StringUtils.isNotBlank(choiceCitys)){
			String[] choiceCitysStr=choiceCitys.split(",");
			if(choiceCitysStr!=null && choiceCitysStr.length>0){
				long[] setChoiceCitysStr = new long[choiceCitysStr.length];
				for(int i=0;i<choiceCitysStr.length;i++){
					setChoiceCitysStr[i]=Long.parseLong(choiceCitysStr[i]);
				}
				routeAreaRelCfgIn.setChoiceCitys(setChoiceCitysStr);
			}
		}
		SimpleOutParamVO<Map> out = CallerProxy.call(routeAreaRelCfgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}

}
