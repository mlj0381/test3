package com.wo56.business.ac.impl;
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
import com.wo56.business.ac.interfaces.IAcCashProveIntf;
import com.wo56.business.ac.vo.in.AcAccountDtlParamIn;
import com.wo56.business.ac.vo.in.AcBatchAccountParamIn;
import com.wo56.business.ac.vo.in.AcCashProveDealParamIn;
import com.wo56.business.ac.vo.in.AcCashProveParamIn;
import com.wo56.business.ac.vo.in.AcCashProveUnDealParamIn;
import com.wo56.business.ac.vo.in.AcCashStsDealParamIn;
import com.wo56.business.ac.vo.out.AcCashProveParamNewOut;
import com.wo56.business.ac.vo.out.AcCashSimpleOut;
import com.wo56.business.ac.vo.out.AcProveShipBatchParamOut;
import com.wo56.business.ac.vo.out.AcProveTranferParamOut;
import com.wo56.business.ord.vo.out.CheckOutgoingSimpleNewOut;
import com.wo56.common.consts.AccountManageConsts;
import com.wo56.common.consts.InterFacesCodeConsts;

public class AcProveManageBO extends BaseBO {
    
	
	
	
	/***
	 * 运单详情查询核销信息
	 * 入参：orderId  订单ID
	 * 入参：trackingNum  运单ID
	 * */
	public String doQueryAcAccountDtlByOrderId() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		long orderId = DataFormat.getLongKey(map,"orderId");
		long trackingNum = DataFormat.getLongKey(map,"trackingNum");
		//直接将后台的JSON串输出到前台
		AcAccountDtlParamIn proveParamIn = new AcAccountDtlParamIn(InterFacesCodeConsts.AC.QUERY_AC_ACCOUNT_DTL);
		proveParamIn.setOrderId(orderId);
		proveParamIn.setTrackingNum(trackingNum);
		PageOutParamVO<Map> out = CallerProxy.call(proveParamIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	
	/**
	 * 查询核销信息物流对账（商家web）
	 * @return
	 * @throws Exception
	 */
	public String queryBusinessOrgAcDtl()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		AcAccountDtlParamIn proveParamIn = new AcAccountDtlParamIn(InterFacesCodeConsts.AC.QUERY_AC_ACCOUNT_BUSINESS_DTL);
		BeanUtils.populate(proveParamIn, map);
		PageOutParamVO<Map> out = CallerProxy.call(proveParamIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	/**
	 * 查询核销信息商家对账（专线web）
	 * @return
	 * @throws Exception
	 */
	public String queryZxOrgAcDtl()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		AcAccountDtlParamIn proveParamIn = new AcAccountDtlParamIn(InterFacesCodeConsts.AC.QUERY_AC_ACCOUNT_ZX_DTL);
		BeanUtils.populate(proveParamIn, map);
		PageOutParamVO<Map> out = CallerProxy.call(proveParamIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	/***
     * 查询师傅对账信息
     * 
     * ord_fee  
     * ord_order_info  
     * scheduler_task  
     * yzt_ord_exception_info  查询师傅的异常费用
     * 
     */
	@SuppressWarnings("rawtypes")
	public String querySfFeeDtl() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
//		IAcCashProveIntf acCashProveTF = CallerProxy.getSVBean(IAcCashProveIntf.class, "acCashProveTF", new TypeToken<Pagination<AcCashSimpleOut>>(){}.getType());
//		Pagination p =   acCashProveTF.querySfFeeDtl(inParam);
		AcAccountDtlParamIn proveParamIn = new AcAccountDtlParamIn(InterFacesCodeConsts.AC.QUERY_SF_ZX_DTL);
		BeanUtils.populate(proveParamIn, inParam);
		PageOutParamVO<AcCashSimpleOut> out = CallerProxy.call(proveParamIn, new TypeToken<PageOutParamVO<AcCashSimpleOut>>(){}.getType());
		return JsonHelper.json(out.getContent(),new AcCashSimpleOut().getNoFiled());
	}
	
	
	/***
     * 查询基础核销对账信息(查询基础的运单信息)
     * 
     * ord_fee  
     * ord_order_info  
     * 
     * 
     */
	@SuppressWarnings("rawtypes")
	public String queryAccountDtlNew() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IAcCashProveIntf acCashProveTF = CallerProxy.getSVBean(IAcCashProveIntf.class, "acCashProveTF", new TypeToken<Pagination<AcCashProveParamNewOut>>(){}.getType());
		Pagination p =   acCashProveTF.queryAccountDtlNew(inParam);
		return JsonHelper.json(p,new AcCashProveParamNewOut().getNoFiled());
	}
	
	/***
	 * 查询核销信息
	 * 入参：checkType 核销类型
	 *      inoutSts 收支类型 
	 * 		checkSts 核销状态	
	 * 		orderId 订单ID
	 * */
	public String doQueryPorve() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String checkType = DataFormat.getStringKey(map,"checkType");
		int inoutSts = DataFormat.getIntKey(map,"inoutSts");
		int checkSts = DataFormat.getIntKey(map,"checkSts");
		long orderId = DataFormat.getLongKey(map,"orderId");
		String trackingNum = DataFormat.getStringKey(map,"trackingNum");
		String consignorName = DataFormat.getStringKey(map,"consignorName");
		String consignorLinkmanName = DataFormat.getStringKey(map,"consignorLinkmanName");
		String consignorBill = DataFormat.getStringKey(map,"consignorBill");
		String endDate = DataFormat.getStringKey(map,"endDate");
		String beginDate = DataFormat.getStringKey(map,"beginDate");
		BaseUser baseUser = SysContexts.getCurrentOperator();
		
		String consigneeName = DataFormat.getStringKey(map,"consigneeName");
		String consigneeBill = DataFormat.getStringKey(map,"consigneeBill");
		//直接将后台的JSON串输出到前台
		AcCashProveParamIn proveParamIn = new AcCashProveParamIn();
		proveParamIn.setCheckSts(checkSts);
		proveParamIn.setInoutSts(inoutSts);
		proveParamIn.setCheckType(checkType);
		proveParamIn.setTrackingNum(trackingNum);
		proveParamIn.setOrgId(baseUser.getOrgId());
		proveParamIn.setOrderId(orderId);
		proveParamIn.setConsignorName(consignorName);
		proveParamIn.setConsignorLinkmanName(consignorLinkmanName);
		proveParamIn.setConsignorBill(consignorBill);
		proveParamIn.setConsigneeName(consigneeName);
		proveParamIn.setConsigneeBill(consigneeBill);
		proveParamIn.setEndDate(endDate);
		proveParamIn.setBeginDate(beginDate);
		proveParamIn.setCashSts(-1);
		PageOutParamVO<Map> out = CallerProxy.call(proveParamIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	
	
	
	
	/***
	 * 查询核销信息 （运费、装卸费 核销 关联的是批次号）
	 * 入参：checkType 核销类型
	 *      inoutSts 收支类型 
	 * 		checkSts 核销状态	
	 * 		orderId  批次ID
	 * */
	public String doQueryPorveBatch() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String checkType = DataFormat.getStringKey(map,"checkType");
		int inoutSts = DataFormat.getIntKey(map,"inoutSts");
		int checkSts = DataFormat.getIntKey(map,"checkSts");
		int queryType = DataFormat.getIntKey(map,"queryType");
		String driverName = DataFormat.getStringKey(map,"driverName");
		String driverPhone = DataFormat.getStringKey(map,"driverPhone");
		BaseUser baseUser = SysContexts.getCurrentOperator();
		String orderId = DataFormat.getStringKey(map,"orderId"); //批次号
		//直接将后台的JSON串输出到前台
		AcBatchAccountParamIn proveParamIn = new AcBatchAccountParamIn();
		proveParamIn.setCheckSts(checkSts);
		proveParamIn.setInoutSts(inoutSts);
		proveParamIn.setCheckType(checkType);
		proveParamIn.setOrgId(baseUser.getOrgId());
		proveParamIn.setDriverName(driverName);
		proveParamIn.setDriverPhone(driverPhone);
		proveParamIn.setQueryType(queryType);
		proveParamIn.setOrderId(orderId);
		PageOutParamVO<Map> out = CallerProxy.call(proveParamIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	

	/***
	 * 查询核销信息
	 * 入参：checkType 核销类型
	 *      inoutSts 收支类型 
	 * 		checkSts 核销状态	
	 * 		cashSts 现金收取状态
	 * 		orderId 订单ID
	 * */
	public String doQuery() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String checkType = DataFormat.getStringKey(map,"checkType");
		int inoutSts = DataFormat.getIntKey(map,"inoutSts");
		int checkSts = DataFormat.getIntKey(map,"checkSts");
		long orderId = DataFormat.getLongKey(map,"orderId");
		String trackingNum = DataFormat.getStringKey(map,"trackingNum");
		String consignorName = DataFormat.getStringKey(map,"consignorName");
		String consignorLinkmanName = DataFormat.getStringKey(map,"consignorLinkmanName");
		String consignorBill = DataFormat.getStringKey(map,"consignorBill");
		BaseUser baseUser = SysContexts.getCurrentOperator();
		//直接将后台的JSON串输出到前台
		AcCashProveParamIn proveParamIn = new AcCashProveParamIn();
		proveParamIn.setCheckSts(checkSts);
		proveParamIn.setInoutSts(inoutSts);
		proveParamIn.setCheckType(checkType);
		proveParamIn.setOrgId(baseUser.getOrgId());
		proveParamIn.setOrderId(orderId);
		proveParamIn.setTrackingNum(trackingNum);
		proveParamIn.setConsignorName(consignorName);
		proveParamIn.setConsignorLinkmanName(consignorLinkmanName);
		proveParamIn.setConsignorBill(consignorBill);
		proveParamIn.setCashSts(AccountManageConsts.AcCashProve.CASH_STS_NO);
		PageOutParamVO<Map> out = CallerProxy.call(proveParamIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/**
	 * 核销处理
	 * @return
	 * @throws Exception
	 */
	public String dealAcCashProve()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		AcCashProveDealParamIn proveParamIn = new AcCashProveDealParamIn();
		String checkIds = DataFormat.getStringKey(map, "checkIds");
		proveParamIn.setCheckedIds(checkIds);
		SimpleOutParamVO<Map> out = CallerProxy.call(proveParamIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 核销处理 NEW
	 * @return
	 * @throws Exception
	 */
	public String dealAcCashProveNew()throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IAcCashProveIntf acCashProveTF = CallerProxy.getSVBean(IAcCashProveIntf.class, "acCashProveTF", new TypeToken<Map>(){}.getType());
		return JsonHelper.json(acCashProveTF.dealAcCashProveNew(inParam));
	}
	
	/**
	 * 反核销处理
	 * @return
	 * @throws Exception
	 */
	public String dealUnAcCashProve()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		AcCashProveUnDealParamIn proveParamIn = new AcCashProveUnDealParamIn();
		String checkIds = DataFormat.getStringKey(map, "checkIds");
		proveParamIn.setCheckedIds(checkIds);
		SimpleOutParamVO<Map> out = CallerProxy.call(proveParamIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 处理现金状态
	 * @return
	 * @throws Exception
	 */
	public String dealCashSts()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		AcCashStsDealParamIn proveParamIn = new AcCashStsDealParamIn();
		String checkIds = DataFormat.getStringKey(map, "checkIds");
		proveParamIn.setCheckedIds(checkIds);
		SimpleOutParamVO<Map> out = CallerProxy.call(proveParamIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 中转查询核销信息 20161114 新增
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String queryOutGoingCheckInfo() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IAcCashProveIntf acCashProveTF = CallerProxy.getSVBean(IAcCashProveIntf.class, "acCashProveTF", new TypeToken<Pagination<CheckOutgoingSimpleNewOut>>(){}.getType());
		Pagination p =   acCashProveTF.queryOutGoingCheckInfo(inParam);
		return JsonHelper.json(p,new CheckOutgoingSimpleNewOut().getNoFiled());
	}

	/***
	 * 查询核销信息配载的大车费和装卸费信息
	 */
	@SuppressWarnings("rawtypes")
	public String doQueryPorveBatchNew() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IAcCashProveIntf acCashProveTF = CallerProxy.getSVBean(IAcCashProveIntf.class, "acCashProveTF", new TypeToken<Pagination<AcProveTranferParamOut>>(){}.getType());
		Pagination p =   acCashProveTF.queryAcDtlBatchNew(inParam);
		return JsonHelper.json(p,new AcProveTranferParamOut().getNoFiled());
	}
	
	/***
	 * 查询核销信息船运配载的大车费和装卸费信息
	 */
	@SuppressWarnings("rawtypes")
	public String queryAcDtlShipBatchNew() throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IAcCashProveIntf acCashProveTF = CallerProxy.getSVBean(IAcCashProveIntf.class, "acCashProveTF", new TypeToken<Pagination<AcProveShipBatchParamOut>>(){}.getType());
		Pagination p =   acCashProveTF.queryAcDtlShipBatchNew(inParam);
		return JsonHelper.json(p,new AcProveTranferParamOut().getNoFiled());
	}
	
	
	
}
