package com.wo56.business.sche.exc;

import java.util.HashMap;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.MapInParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ord.vo.in.OrdQueryManyIn;
import com.wo56.business.sche.ord.vo.in.ExcQueryIn;
import com.wo56.business.sche.ord.vo.in.ExceptionParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;
import com.wo56.common.consts.IntfCodeConsts;

public class ExceptionInfoBO extends BaseBO {
	
	/***
	 * 查询异常列表
	 * @param wayBillId 运单号
	 * @param receiveName 收货人名称
	 * @param receivePhone 收货人手机
	 * @param taskState 任务状态
	 * @param provinceId 省份id
	 * @param cityId  城市id
	 * @param countyId 县区id
	 * @param streetId 街道id
	 * @param source 订单数据来源
	 * @return 
	 * 
	 * **/
	public String doQuery() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		//直接将后台的JSON串输出到前台
		ExcQueryIn paramIn = new ExcQueryIn();
		BeanUtils.populate(paramIn, inParam);
		paramIn.setInCode(IntfCodeConsts.SCHE.EXC_QUERY);
		PageOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}

	/**
	 * 查询异常数量 
	 *     待处理，处理中
	 * @return
	 * @throws Exception
	 */
	public String doQueryCount() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		//直接将后台的JSON串输出到前台
		ExcQueryIn paramIn = new ExcQueryIn();
		paramIn.setInCode(IntfCodeConsts.SCHE.EXC_QUERY_COUNT);
		PageOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	
	public String doQueryForServe() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		//直接将后台的JSON串输出到前台
		ExcQueryIn paramIn = new ExcQueryIn();
		BeanUtils.populate(paramIn, inParam);
		paramIn.setInCode(IntfCodeConsts.SCHE.EXC_SERVE_QUERY);
		PageOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}

	
	/****
	 * 
	 * 查询原单上的师傅是否具备维修单的能力
	 * 
	 * */
	public String queryMatchSf()  throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String phone = DataFormat.getStringKey(map, "phone");
		Map paramMap = new HashMap();
		paramMap.put("phone", phone);
		MapInParamVO mapInParamVO = new MapInParamVO(IntfCodeConsts.SCHE.QUERY_SF_MAIN, paramMap);
		SimpleOutParamVO out = CallerProxy.call(mapInParamVO, new TypeToken<SimpleOutParamVO>(){}.getType());
		if(out.getContent()!=null){
			return JsonHelper.json( out.getContent());

		}else{
			return "Y";
			
		}
	}
	
	
	/****
	 * 异常保存、修改
	 * 
	 * */
	public String doSave()  throws Exception{
		ExceptionParamIn paramIn = new ExceptionParamIn();
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.EXCEPTION_SAVE_OR_MODIFY);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}

	/****
	 * 异常回显
	 * 
	 * */
	public String toView()  throws Exception{
		ExceptionParamIn paramIn = new ExceptionParamIn();
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.EXCE_TO_VIEW);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}

	/**
	 * 异常登记查询相关的订单
	 * 
	 * 异常查询关联的订单，需要查询出，跟当前网点有关系的订单，原来的逻辑是按租户查询，师傅合作商没有办法查询出对应的运单
	 * 
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String queryOrderInfoOfExc()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrdQueryManyIn query = new OrdQueryManyIn(InterFacesCodeConsts.ORD.ORD_EXC_QUERY);
		BeanUtils.populate(query, map);
		PageOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}	


}
