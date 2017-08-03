package com.wo56.business.cm;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.intf.ICmCustomerIntf;
import com.wo56.business.cm.vo.in.CmContractQueryIn;
import com.wo56.business.cm.vo.in.CmContractQueryPageIn;
import com.wo56.business.cm.vo.in.DelCmCustomerIn;
import com.wo56.business.cm.vo.in.GetCustomerIn;
import com.wo56.business.cm.vo.in.PRCustomerParam;
import com.wo56.business.cm.vo.in.QueryCustomerDtlIn;
import com.wo56.business.cm.vo.in.QueryCustomerIn;
import com.wo56.business.cm.vo.in.SaveCmCustomerIn;
import com.wo56.business.cm.vo.out.CmcustomerOut;
import com.wo56.common.consts.InterFacesCodeConsts;

public class CmCustomerBO {

	/**
	 * 查询收货方(此接口只能查询处状态为有效的收货方)
	 * @return
	 * @throws Exception
	 */
	public String queryCustomer() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String name = DataFormat.getStringKey(map, "name");
		int type = DataFormat.getIntKey(map,"type");
		if (type <= 0) {
			return null;
		}
		PRCustomerParam prCustomerParam = new PRCustomerParam();
		prCustomerParam.setName(name);
		prCustomerParam.setType(type);
		prCustomerParam.setState(1);
		ListOutParamVO<CmcustomerOut> list = CallerProxy.call(prCustomerParam, new TypeToken<ListOutParamVO<CmcustomerOut>>(){}.getType());
		List<CmcustomerOut> rtnList = list.getContent();
		if(rtnList != null){
			return JsonHelper.json(rtnList);
		}else{
			return null;
		}
	}
	
	/**
	 * 查询发货方(只能查询出状态为有效的发货方)
	 * @return
	 * @throws Exception
	 */
	public String getCustomer()throws Exception{
		Map<String,String[]> map = SysContexts.getRequestParameterMap();
		String name = DataFormat.getStringKey(map, "name");
		int type = DataFormat.getIntKey(map,"type");
		if (type <= 0) {
			return null;
		}
		GetCustomerIn getCustomerIn = new GetCustomerIn();
		getCustomerIn.setName(name);
		getCustomerIn.setType(type);
		getCustomerIn.setState(1);
		
		ListOutParamVO<CmcustomerOut> list = CallerProxy.call(getCustomerIn, new TypeToken<ListOutParamVO<CmcustomerOut>>(){}.getType());
		List<CmcustomerOut> rtnList = list.getContent();
		if(rtnList != null){
			return JsonHelper.json(rtnList);
		}else{
			return null;
		}
	}
	
	
	
	/**
	 *保存和修改发货人
	 * @return
	 * @throws Exception
	 */
	public String saveOrUpdateCustomer()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SaveCmCustomerIn saveCmCustomerIn = new SaveCmCustomerIn();
		BeanUtils.populate(saveCmCustomerIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(saveCmCustomerIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	
	/**
	 *查询收货人详情
	 * @return
	 * @throws Exception
	 */
	public String queyCustomerDtl()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryCustomerDtlIn queryCustomerDtlIn = new QueryCustomerDtlIn();
		BeanUtils.populate(queryCustomerDtlIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryCustomerDtlIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 查询发货人列表
	 * @return
	 * @throws Exception
	 */
	public String doQueryCustomer() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryCustomerIn queryCustomerIn = new QueryCustomerIn();
		BeanUtils.populate(queryCustomerIn, map);
		PageOutParamVO<Map> out = CallerProxy.call(queryCustomerIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent()); 
	}
	
	/**
	 *删除发货人
	 * @return
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public String delCmCustomer() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		DelCmCustomerIn delCmCustomerIn = new DelCmCustomerIn();
		BeanUtils.populate(delCmCustomerIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(delCmCustomerIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	/**
	 * lyh
	 * 查询发货人列表
	 * @return
	 * @throws Exception
	 */
	public String doQueryCus() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		CmContractQueryPageIn queryCustomerIn = new CmContractQueryPageIn(InterFacesCodeConsts.CM.QUERY_CU_IN);
		BeanUtils.populate(queryCustomerIn, map);
		PageOutParamVO<Map> out = CallerProxy.call(queryCustomerIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent()); 
	}
	/**
	 *查询商家组织
	 * @return
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public String doQueryOrg() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		CmContractQueryIn queryCustomerIn = new CmContractQueryIn(InterFacesCodeConsts.CM.QUERY_CU_ORG);
		BeanUtils.populate(queryCustomerIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryCustomerIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	/**
	 *保存发货人
	 * @return
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public String doSaveCm() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		CmContractQueryIn queryCustomerIn = new CmContractQueryIn(InterFacesCodeConsts.CM.SAVE_CU_IN);
		BeanUtils.populate(queryCustomerIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryCustomerIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	/**
	 *根据Id查询发货人、收货人
	 * @return
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public String doQueryByIdCm() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		CmContractQueryIn queryCustomerIn = new CmContractQueryIn(InterFacesCodeConsts.CM.QUERY_CU_BYID);
		BeanUtils.populate(queryCustomerIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryCustomerIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	/**
	 *删除发货人
	 *lyh
	 * @return
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public String delCmById() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		CmContractQueryIn delCmCustomerIn = new CmContractQueryIn(InterFacesCodeConsts.CM.DEL_CU);
		BeanUtils.populate(delCmCustomerIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(delCmCustomerIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	/**
	 * 云启发货人或收货人
	 * @return
	 */
	public String pageCmCustomer(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmCustomerIntf intf = CallerProxy.getSVBean(ICmCustomerIntf.class, "cmCustomerYQTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.pageCmCustomer(inParam));
	}
}
