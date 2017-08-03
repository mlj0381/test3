package com.wo56.business.cm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.vo.in.CmTrunkCostIn;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.org.vo.in.OrgQueryBusParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class CmTrunkCostBO  extends BaseBO{
	/**
	 * 开单查询专线网点配置的协议商家费用基础信息（最多只查询1条）
	 * 调用接口编码： 110228
	 * @param inParam
	 * 入参：
	 * 		tenantId 协议商家租户id
	 *      province 省
	 *      city     市
	 *      county   区 
	 *  出参：  （查找不到数据为空字符串）
	 *      weightPrice               //单位分（每千克价格）
	 *      volumePrice               //单位分（每方价格）
	 *      countPrice;               //单位分 （每件价格）
	 *      weightPriceDouble         //单位元 （每千克价格）
	 *      volumePriceDouble         //单位元 （每方价格）
	 *      countPriceDouble          //单位元 （每件价格）
	 *      costPicUrlFull            //线费用图片全URL
	 *      costInstallPicUrlFull     //配安费用图片全URL
	 *      paymentType               //支付方式
	 */
	@SuppressWarnings("rawtypes")
	public String doQueryOneCostInfo() throws Exception{
		CmTrunkCostIn cmInParam = new CmTrunkCostIn(InterFacesCodeConsts.CM.CM_ORDER_PLACE);
		Map<String, String[]> mapIn = SysContexts.getRequestParameterMap();
		BeanUtils.populate(cmInParam, mapIn);
//		if(StringUtils.isNotEmpty(cmInParam.getTenantId())){
//			cmInParam.setBusinessOrgId(OraganizationCacheDataUtil.getOrganizationByTenantId(Long.valueOf(cmInParam.getTenantId())).getOrgId()+"");
//		}
//		cmInParam.setTenantId("");
		//直接将后台的JSON串输出到前台
	 	SimpleOutParamVO<HashMap> out = CallerProxy.call(cmInParam, new TypeToken<SimpleOutParamVO<HashMap>>(){}.getType());
	 	HashMap map = out.getContent();
	 	if(map  == null){
	 		return "";
	 	}else{
	 		return JsonHelper.json(map);
	 	}
		
	}

	/**
	 * 查询承运商信息
	 * @param inParam
	 * 入参：
	 * 		orgId 网点
	 * 	 	businessOrgId 商家信息
	 *      province 省
	 *      city     市
	 *      county   区
	 * @return
	 * @throws Exception
	 */
	public String doQueryCmTrunkCost() throws Exception{
		CmTrunkCostIn cmInParam = new CmTrunkCostIn(InterFacesCodeConsts.CM.CM_TRUNK_COST);
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		cmInParam.setBusinessOrgId(DataFormat.getStringKey(map, "businessOrgId"));
		cmInParam.setOrgId(DataFormat.getStringKey(map, "orgId"));
		cmInParam.setProvince(DataFormat.getStringKey(map, "province"));
		cmInParam.setCity(DataFormat.getStringKey(map, "city"));
		PageOutParamVO<Map> out = CallerProxy.call(cmInParam, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 删除干线费用
	 * @param inParam
	 * 入参：
	 * 		costDetailId 费用标准详情主键
	 * @return
	 * @throws Exception
	 */
	public String doDelCmTrunkCostDetail() throws Exception{
		CmTrunkCostIn cmInParam = new CmTrunkCostIn(InterFacesCodeConsts.CM.DELETE_CM_TRUNK_COST);
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		cmInParam.setCostDetailId(DataFormat.getStringKey(map, "costDetailId"));
		PageOutParamVO<Map> out = CallerProxy.call(cmInParam, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 更新该专线的协议商家(发货商家)支付方式
	 * @param inParam
	 * 入参：
	 * 		costDetailId 费用标准详情主键
	 * @return
	 * @throws Exception
	 */
	public String updatePaymentType() throws Exception{
		CmTrunkCostIn cmTrunkCostInParam = new CmTrunkCostIn(InterFacesCodeConsts.CM.UPDATE_CM_TRUNK_COST_PARMENTTYPPE);
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		cmTrunkCostInParam.setCostId(DataFormat.getLongKey(map, "costId"));
		cmTrunkCostInParam.setPaymentType(DataFormat.getStringKey(map, "paymentType"));
		SimpleOutParamVO<Map> out = CallerProxy.call(cmTrunkCostInParam, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 修改干线费用标准
	 * @param inParam
	 * 入参：
	 * 		costDetailId 费用标准详情主键
	 * 		weightPrice  每千克价格（单位分）
	 * 		volumePrice  每方价格（单位分）
	 * 		countPrice	   每件价格（单位分）
	 * @return
	 * @throws Exception
	 */
	public String updateCmTrunkCostDetail() throws Exception{
		CmTrunkCostIn cmTrunkCostInParam = new CmTrunkCostIn(InterFacesCodeConsts.CM.UPDATE_CM_TRUNK_COST);
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		cmTrunkCostInParam.setCostDetailId(DataFormat.getStringKey(map, "costDetailId"));
		cmTrunkCostInParam.setWeightPrice(DataFormat.getStringKey(map, "weightPriceDouble"));
		cmTrunkCostInParam.setVolumePrice(DataFormat.getStringKey(map, "volumePriceDouble"));
		cmTrunkCostInParam.setCountPrice(DataFormat.getStringKey(map, "countPriceDouble"));
		SimpleOutParamVO<Map> out = CallerProxy.call(cmTrunkCostInParam, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}

	/**
	 * 查询该专线的协议商家(发货商家) (顶级查所有 ，非顶级查自己的)
	 * @param 查询该专线的协议商家
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String doQueryMerchant() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrgQueryBusParamIn queryOrgIn = new  OrgQueryBusParamIn(InterFacesCodeConsts.CM.CM_ZX_PLACE);
		BeanUtils.populate(queryOrgIn, map);
		PageOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		Pagination p  = (Pagination) out.getContent();
		List<Map>lists = p.getItems();
		Map<String,String> m = new HashMap<String, String>();
		List listOut = new ArrayList();
		for(Map o : lists){
			 m = new HashMap<String, String>();
			 long tanantId = Long.valueOf(DataFormat.getLongKey(o, "tenantId"));
			 Organization oo =  OraganizationCacheDataUtil.getOrganizationByTenantId(tanantId);
			 if(oo != null ){
				 m.put("tenantId",tanantId+"");
				 m.put("orgId", oo.getOrgId()+"");
				 m.put("orgName", oo.getOrgName());
				 listOut.add(m);
			 }
		}
		
		return JsonHelper.json(listOut);
	}
	
	
	/**
	 * 查询该专线的归属网点(获取登陆专线下的网点列表（如果登陆的是专线，不是父级只查当前）)
	 * @throws Exception
	 */
	public String doQueryZxLine() throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		return JsonHelper.json(OraganizationCacheDataUtil.getOrganizationByLogin(user.getOrgId()));
	}
	/**
	 * 保存干线费用配置信息
	 * @throws Exception
	 */
	public String doSave() throws Exception{
		CmTrunkCostIn cmInParam = new CmTrunkCostIn(InterFacesCodeConsts.CM.CM_SAVE_FEE);
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BeanUtils.populate(cmInParam, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(cmInParam, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return "Y";
	}

}