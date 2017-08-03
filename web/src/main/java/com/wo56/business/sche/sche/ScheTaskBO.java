package com.wo56.business.sche.sche;

import java.util.HashMap;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.exception.BusinessException;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.MapInParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ord.vo.in.OrdOpLogIn;
import com.wo56.business.sche.ord.vo.in.ScheDisSfIn;
import com.wo56.business.sche.ord.vo.in.ScheDoSignIn;
import com.wo56.business.sche.ord.vo.in.ScheGxEnd;
import com.wo56.business.sche.ord.vo.in.ScheQueryIn;
import com.wo56.business.sche.ord.vo.in.ScheQueryMacthSfIn;
import com.wo56.business.sche.sche.intf.IScheTaskIntf;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.InterFacesCodeConsts;
import com.wo56.common.consts.IntfCodeConsts;
import com.wo56.common.utils.EncryPwdUtil;

public class ScheTaskBO  extends BaseBO{
	
	/***
	 * 查询待分配任务列表
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
	public String doTaskQuery() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		//直接将后台的JSON串输出到前台
		ScheQueryIn paramIn = new ScheQueryIn();
		BeanUtils.populate(paramIn, inParam);
		paramIn.setInCode(IntfCodeConsts.SCHE.WAIT_TASK_QUERY);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 干线结束
	 * @return 
	 * 
	 * **/
	public String arriveGoods() throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		Map rtn = new HashMap();
		rtn.put("taskId", DataFormat.getStringKey(map, "taskId"));
		rtn.put("orderId", DataFormat.getStringKey(map, "orderId"));
		MapInParamVO mapInParamVO = new MapInParamVO(IntfCodeConsts.SCHE.WEB_ARRIVE_GOODS,rtn);
		SimpleOutParamVO<Map> out = CallerProxy.call(mapInParamVO, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	

	/***
	 * 统计task任务数量
	 * @return 
	 * 
	 * **/
	public String statisticsTaskCount() throws Exception{
		//直接将后台的JSON串输出到前台
		ScheQueryIn paramIn = new ScheQueryIn();
		paramIn.setInCode(IntfCodeConsts.SCHE.TASK_COUNT);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 查询一条任务对于匹配到的师傅
	 * @return 
	 * 
	 * **/
	public String queryMatchSf() throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheQueryMacthSfIn paramIn = new ScheQueryMacthSfIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.QUERY_MACTH_SF);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 补录提货信息
	 * @return 
	 * 
	 * **/
	public String savePickInfo() throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheGxEnd paramIn = new ScheGxEnd();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.SAVE_PICK_INFO);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 分配师傅
	 * @return 
	 * 
	 * **/
	public String disSf() throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheDisSfIn paramIn = new ScheDisSfIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.DIS_SF);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 货物明细
	 * @return 
	 * 
	 * **/
	public String goodsDetail() throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheQueryMacthSfIn paramIn = new ScheQueryMacthSfIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.GOODS_DETAIL);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 查询符合能力的师傅跟公司
	 * @return 
	 * 
	 * **/
	public String querySfAndCompany() throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheQueryMacthSfIn paramIn = new ScheQueryMacthSfIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.QUERY_SF_COMPANY);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/***
	 * 查询待分配任务列表
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
	public String doAIQuery() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		//直接将后台的JSON串输出到前台
		ScheQueryIn paramIn = new ScheQueryIn();
		BeanUtils.populate(paramIn, inParam);
		paramIn.setInCode(IntfCodeConsts.SCHE.AI_QUERY_TASK);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 点击受理操作
	 * @param TaskId 任务编码
	 * @param wayBillId 运单号
	 *  @return 
	 * */
	public String acceptTask()throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheQueryMacthSfIn paramIn = new ScheQueryMacthSfIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.ACCEPT_RECEIPT);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 取消分配操作
	 * @param TaskId 任务编码
	 * @param wayBillId 运单号
	 *  @return 
	 * */
	public String cancerDis()throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheQueryMacthSfIn paramIn = new ScheQueryMacthSfIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.CANCER_DIS);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 提货操作
	 * @param TaskId 任务编码
	 * @param wayBillId 运单号
	 *  @return 
	 * */
	public String pickUp()throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheQueryMacthSfIn paramIn = new ScheQueryMacthSfIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.PICK_UP);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 预约操作
	 * @param TaskId 任务编码
	 * @param wayBillId 运单号
	 *  @return 
	 * */
	public String yy()throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheQueryMacthSfIn paramIn = new ScheQueryMacthSfIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.YY);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 签收操作
	 * @param TaskId 任务编码
	 * @param wayBillId 运单号
	 *  @return 
	 * */
	public String doSign()throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheDoSignIn paramIn = new ScheDoSignIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.SIGN);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	

	/***
	 * 查询签收图
	 * @param TaskId 任务编码
	 * @param wayBillId 运单号
	 *  @return 
	 * */
	public String querySignPic()throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheDoSignIn paramIn = new ScheDoSignIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.QUERY_SING_PIC);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	

	/***
	 * 保存签收图
	 * @param TaskId 任务编码
	 * @param wayBillId 运单号
	 *  @return 
	 * */
	public String doSaveSignPic()throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheDoSignIn paramIn = new ScheDoSignIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.DO_SAVE_SING_PIC);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	

	/***
	 * 查询运单详情
	 * @param wayBillId 运单号
	 * @param taskId 任务编号
	 * @return 
	 * 
	 * **/
	public String queryWayBillDetail() throws Exception{ 
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheQueryMacthSfIn paramIn = new ScheQueryMacthSfIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.QUERY_WAY_DEATAIL);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	
	/***
	 * 首页的任务统计 
	 * 统计智能匹配，人工 全部／今天
	 * @return 
	 * 
	 * **/
	public String statisticsIndexTaskCount() throws Exception{
		//直接将后台的JSON串输出到前台
		ScheQueryIn paramIn = new ScheQueryIn();
		paramIn.setInCode(IntfCodeConsts.SCHE.TASK_COUNT_INDEX);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 查询运单管理详情
	 * @param wayBillId 运单号
	 * @param taskId 任务编号
	 * @return 
	 * 
	 * **/
	public String queryOrdBillDetail() throws Exception{ 
		//直接将后台的JSON串输出到前台
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		Map<String,Object> map=new HashMap<String, Object>();
		long trackingNum = DataFormat.getLongKey(inParam, "trackingNum");
		map.put("trackingNum", trackingNum);
		//入参校验
		IScheTaskIntf sv = CallerProxy.getSVBean(IScheTaskIntf.class, "scheTaskTF", new TypeToken<Map<String,Object>>(){}.getType());
		Map retMap=sv.queryOrdBillDetail(map);
		return JsonHelper.json(retMap);
	}
	
	
	/***
	 * 查询运单管理详情---操作日志信息
	 * @param wayBillId 运单号
	 * @return 
	 * 
	 * **/
	public String queryOrdBillDetailOfLog() throws Exception{ 
		//直接将后台的JSON串输出到前台
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		Map<String,Object> map=new HashMap<String, Object>();
		long trackingNum = DataFormat.getLongKey(inParam, "trackingNum");
		map.put("trackingNum", trackingNum);
		//入参校验
		IScheTaskIntf sv = CallerProxy.getSVBean(IScheTaskIntf.class, "scheTaskTF", new TypeToken<Map<String,Object>>(){}.getType());
		Map retMap=sv.queryOrdBillDetailOfLog(map);
		return JsonHelper.json(retMap);
	}
	
	/***
	 * 对外查单
	 * @param trackingNum 运单号
	 * @return 
	 * 
	 * **/
	public String queryOrdBillDetailByTrackingNum() throws Exception{ 
		//直接将后台的JSON串输出到前台
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		String inValidCode = DataFormat.getStringKey(inParam,"vaildCode");
		inValidCode=EncryPwdUtil.pwdDecryption(inValidCode);
		String validCode = "";
		if(SysContexts.getHttpSession().getAttribute(EnumConsts.Common.VALID_CODE)!=null){
			validCode=(String)SysContexts.getHttpSession().getAttribute(EnumConsts.Common.VALID_CODE);
		}
		if(!inValidCode.equals(validCode)){
			throw new BusinessException("100001","验证码不正确！");
		}
		//校验码失效，只能使用一次，防止暴力破解密码
		SysContexts.getHttpSession().removeAttribute("loginValidCode");
		OrdOpLogIn in = new OrdOpLogIn();
		BeanUtils.populate(in, inParam);
		in.setInCode(InterFacesCodeConsts.ORD.ORD_TRACKING_NUM);
		//入参校验
		SimpleOutParamVO<Map> out = CallerProxy.call(in, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	
	/***
	 * 查询运单管理详情---操作日志信息
	 * @param wayBillId 运单号
	 * @return 
	 * 
	 * **/
	public String queryOrdBillDetailOfExp() throws Exception{ 
		//直接将后台的JSON串输出到前台
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		Map<String,Object> map=new HashMap<String, Object>();
		long trackingNum = DataFormat.getLongKey(inParam, "trackingNum");
		map.put("trackingNum", trackingNum);
		//入参校验
		IScheTaskIntf sv = CallerProxy.getSVBean(IScheTaskIntf.class, "scheTaskTF", new TypeToken<Map<String,Object>>(){}.getType());
		Map retMap=sv.queryOrdBillDetailOfExp(map);
		return JsonHelper.json(retMap);
	}
	
	
	/***
	 * 查询运单管理详情---操作日志信息
	 * @param wayBillId 运单号
	 * @return 
	 * 
	 * **/
	public String queryOrdBillDetailOfTask() throws Exception{ 
		//直接将后台的JSON串输出到前台
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		Map<String,Object> map=new HashMap<String, Object>();
		long trackingNum = DataFormat.getLongKey(inParam, "trackingNum");
		map.put("trackingNum", trackingNum);
		//入参校验
		IScheTaskIntf sv = CallerProxy.getSVBean(IScheTaskIntf.class, "scheTaskTF", new TypeToken<Map<String,Object>>(){}.getType());
		Map retMap=sv.queryOrdBillDetailOfTask(map);
		return JsonHelper.json(retMap);
	}
	
	/***
	 * 查询运单管理详情---操作日志信息
	 * @param wayBillId 运单号
	 * @return 
	 * 
	 * **/
	public String queryOrdBillDetailOfSign() throws Exception{ 
		//直接将后台的JSON串输出到前台
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		Map<String,Object> map=new HashMap<String, Object>();
		long trackingNum = DataFormat.getLongKey(inParam, "trackingNum");
		map.put("trackingNum", trackingNum);
		//入参校验
		IScheTaskIntf sv = CallerProxy.getSVBean(IScheTaskIntf.class, "scheTaskTF", new TypeToken<Map<String,Object>>(){}.getType());
		Map retMap=sv.queryOrdBillDetailOfSign(map);
		return JsonHelper.json(retMap);
	}
	
	/***
	 * 删除任务
	 * @param orderId 订单号
	 * @param taskId 任务编号
	 * @return 
	 * 
	 * **/
	public String deleteScheTask() throws Exception{  
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String orderId = DataFormat.getStringKey(map, "orderId");
		String taskId = DataFormat.getStringKey(map, "taskId");
		Map paramMap = new HashMap();
		paramMap.put("orderId", orderId);
		paramMap.put("taskId", taskId);
		MapInParamVO inParamVO = new MapInParamVO(IntfCodeConsts.SCHE.DELETE_TASK, paramMap);
		SimpleOutParamVO<Map> out = CallerProxy.call(inParamVO, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 查询运单管理详情---修改字段日志信息
	 * @param wayBillId 运单号
	 * @return 
	 * 
	 * **/
	public String queryOrdBusiLog() throws Exception{ 
		//直接将后台的JSON串输出到前台
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		Map<String,Object> map=new HashMap<String, Object>();
		long trackingNum = DataFormat.getLongKey(inParam, "trackingNum");
		map.put("trackingNum", trackingNum);
		//入参校验
		IScheTaskIntf sv = CallerProxy.getSVBean(IScheTaskIntf.class, "scheTaskTF", new TypeToken<Map<String,Object>>(){}.getType());
		Map retMap=sv.queryOrdBusiLog(map);
		return JsonHelper.json(retMap);
	}
	
	/***
	 * 修改费用
	 * @param TaskId 任务编码
	 *  @return 
	 * */
	public String doUpdateMoney()throws Exception{
		//直接将后台的JSON串输出到前台
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		ScheDoSignIn paramIn = new ScheDoSignIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setInCode(IntfCodeConsts.SCHE.UPDATE_PA_MONEY);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	
	/**
	 * 批量修改任务费用
	 * @param taskId 金额
	 * @param fee 费用
	 * */
	public String doModifyFee()throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		//入参校验
		IScheTaskIntf sv = CallerProxy.getSVBean(IScheTaskIntf.class, "scheTaskTF", new TypeToken<Map<String,Object>>(){}.getType());
		sv.doModifyFee(inParam);
		return "Y";
		
	}
	
	/**
	 * 签收更改师傅及费用
	 * @param taskId 金额
	 * @param fee 费用
	 * */
	public String doModifySfAndFee()throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		//入参校验
		IScheTaskIntf sv = CallerProxy.getSVBean(IScheTaskIntf.class, "scheTaskTF", new TypeToken<Map<String,Object>>(){}.getType());
		sv.doModifySfAndFee(inParam);
		return "Y";
	}
	
	/***
	 * 任务跟踪
	 * */
	public String taskTrace()throws Exception{ 
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		//入参校验
		IScheTaskIntf sv = CallerProxy.getSVBean(IScheTaskIntf.class, "scheTaskTF", new TypeToken<Map<String,Object>>(){}.getType());
		Map<String, Object> map=sv.taskTrace(inParam);
		return JsonHelper.json(map);
	}
	/***
	 * 任务跟踪
	 * */
	public String getOrdLogAndOrdBusi()throws Exception{ 
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		//入参校验
		IScheTaskIntf sv = CallerProxy.getSVBean(IScheTaskIntf.class, "scheTaskTF", new TypeToken<Map<String,Object>>(){}.getType());
		Map<String, Object> map=sv.getOrdLogAndOrdBusi(inParam);
		return JsonHelper.json(map);
	}
}
