package com.wo56.business.sche.cash;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.MapInParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.sche.cash.vo.in.CashApplicationParamIn;
import com.wo56.business.sche.cash.vo.in.CashApplicationQueryParamIn;
import com.wo56.business.sche.cash.vo.in.CashApplicationStateUpdateParamIn;
import com.wo56.business.sche.cash.vo.in.CashApplyParamIn;
import com.wo56.business.sche.cash.vo.in.CashComParamIn;
import com.wo56.business.sche.cash.vo.in.CashServiceApplyParamIn;
import com.wo56.business.sche.cash.vo.in.CashSfParamIn;
import com.wo56.business.sche.cash.vo.in.CashTotalParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;
import com.wo56.common.consts.SysStaticDataEnum;

public class CashManageBO extends BaseBO {

	/**
	 * 提现申请查询
	 * @return
	 * @throws Exception
	 */
	public String doQuery() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		int state = DataFormat.getIntKey(inParam,"state");//提现状态
		String workerName = DataFormat.getStringKey(inParam,"workerName");//提现人姓名
		String workerLoginAcct = DataFormat.getStringKey(inParam,"workerLoginAcct");//登录帐号
		String beginDate = DataFormat.getStringKey(inParam,"beginDate");//申请开始时间
		String endDate = DataFormat.getStringKey(inParam,"endDate");//申请结束时间
		int userType = DataFormat.getIntKey(inParam,"userType");//申请结束时间
		//直接将后台的JSON串输出到前台
		CashApplicationParamIn paramIn = new CashApplicationParamIn();
		paramIn.setBeginDate(beginDate);
		paramIn.setEndDate(endDate);
		paramIn.setState(state);
		paramIn.setWorkerLoginAcct(workerLoginAcct);
		paramIn.setWorkerName(workerName);
		paramIn.setTenantId(baseUser.getTenantId().toString());
		paramIn.setUserId(baseUser.getUserId());
		paramIn.setUserName(baseUser.getUserName());
		paramIn.setUserType(userType);
		PageOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	

	/***
	 *  合作商配按收人列表查询
	 *  241016
	 * 
	 * */
	public String queryServiceApplication() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		int state = DataFormat.getIntKey(map,"state");//提现状态
		String workerName = DataFormat.getStringKey(map,"workerName");//提现人姓名
		String workerLoginAcct = DataFormat.getStringKey(map,"workerLoginAcct");//登录帐号
		String beginDate = DataFormat.getStringKey(map,"beginDate");//申请开始时间
		String endDate = DataFormat.getStringKey(map,"endDate");//申请结束时间
		Map paramMap = new HashMap();
		paramMap.put("state", state);
		paramMap.put("workerLoginAcct", workerLoginAcct);
		paramMap.put("beginDate", beginDate);
		paramMap.put("workerName", workerName);
		paramMap.put("endDate", endDate);
		MapInParamVO vo = new MapInParamVO(InterFacesCodeConsts.CASH.SERVICE_QUERY, paramMap);
		PageOutParamVO<Map> out = CallerProxy.call(vo, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	
	/**
	 * 查询师傅金额汇总信息
	 * @return
	 * @throws Exception
	 */
	public String doQuerySf() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		String phone = DataFormat.getStringKey(inParam,"phone");//提现人账户
		String doObjName = DataFormat.getStringKey(inParam,"doObjName");
		String doTel = DataFormat.getStringKey(inParam,"doTel");
		int state = DataFormat.getIntKey(inParam,"state");
		int isQuery = DataFormat.getIntKey(inParam,"isQuery");
		int feeId = DataFormat.getIntKey(inParam,"feeId");
		String signTimeBegin = DataFormat.getStringKey(inParam,"signTimeBegin");
		String signTimeEnd = DataFormat.getStringKey(inParam,"signTimeEnd");
		//直接将后台的JSON串输出到前台
		CashSfParamIn paramIn = new CashSfParamIn();
		paramIn.setPhone(StringUtils.isNotEmpty(doTel)?doTel:phone);
		paramIn.setTenantId(baseUser.getTenantId().toString());
		paramIn.setUserId(baseUser.getUserId());
		paramIn.setUserName(baseUser.getUserName());
		paramIn.setDoObjName(doObjName);
		paramIn.setFeeId(feeId);
		paramIn.setSignTimeBegin(signTimeBegin);
		paramIn.setSignTimeEnd(signTimeEnd);
		paramIn.setIsQuery(isQuery);
		paramIn.setState(state);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/**
	 * 查询公司金额汇总信息
	 * @return
	 * @throws Exception
	 */
	public String doQueryCom() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		//直接将后台的JSON串输出到前台
		CashComParamIn paramIn = new CashComParamIn();
		if(baseUser.getUserType()!=null&&baseUser.getUserType()==SysStaticDataEnum.USER_TYPE.MASTER_PARTNERS){
			paramIn.setTenantId(DataFormat.getStringKey(inParam, "serviceId"));
			paramIn.setServiceId(baseUser.getTenantId().toString());
		}else{
			paramIn.setTenantId(baseUser.getTenantId().toString());
			paramIn.setServiceId(DataFormat.getStringKey(inParam, "serviceId"));
		}
		paramIn.setUserId(baseUser.getUserId());
		paramIn.setUserName(baseUser.getUserName());
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/**
	 * 提现申请保存
	 * @return
	 * @throws Exception
	 */
	public String doSave() throws Exception{
		BaseUser baseUser = SysContexts.getCurrentOperator();
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		long userId = DataFormat.getLongKey(inParam,"userId");//提现人用户ID
		String strTaskIds = DataFormat.getStringKey(inParam,"taskIds");//提现任务列表
		CashApplyParamIn paramIn = new CashApplyParamIn();
		paramIn.setGscode(baseUser.getTenantId().toString());
		paramIn.setStrTaskIds(strTaskIds);
		paramIn.setUserId(userId);
		paramIn.setTenantId(baseUser.getTenantId().toString());
		paramIn.setUserName(baseUser.getUserName());
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	

	/**
	 * 提现申请保存
	 * @return
	 * @throws Exception
	 */
	public String servieSaveApplication() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		String taskIds = DataFormat.getStringKey(inParam, "taskIds");
		CashServiceApplyParamIn paramIn = new CashServiceApplyParamIn();
		BeanUtils.populate(paramIn, inParam);
		paramIn.setStrTaskIds(taskIds);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	
	public String getApplicationById()throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		long id = DataFormat.getLongKey(inParam,"id");//提现人用户ID
		CashApplicationQueryParamIn paramIn = new CashApplicationQueryParamIn();
		paramIn.setAppId(id);
		paramIn.setTenantId(baseUser.getTenantId().toString());
		paramIn.setUserId(baseUser.getUserId());
		paramIn.setUserName(baseUser.getUserName());
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	public String doUpdateApplication()throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		long appId = DataFormat.getLongKey(inParam,"appId");//提现ID
		int state = DataFormat.getIntKey(inParam,"state");
		String auditNote = DataFormat.getStringKey(inParam,"auditNote");	
		CashApplicationStateUpdateParamIn paramIn = new CashApplicationStateUpdateParamIn();
		paramIn.setAppId(appId);
		paramIn.setAuditNote(auditNote);
		paramIn.setState(state);
		paramIn.setTenantId(baseUser.getTenantId().toString());
		paramIn.setUserId(baseUser.getUserId());
		paramIn.setUserName(baseUser.getUserName());
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	
	/***
	 * 提现核销
	 * 
	 * **/
	public String verification()throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		long appId = DataFormat.getLongKey(inParam,"appId");//提现ID
		int verityType=DataFormat.getIntKey(inParam, "verityType");
		Map map = new HashMap();
		map.put("appId", appId);
		map.put("verityType", verityType);
		MapInParamVO paramMap=new MapInParamVO(InterFacesCodeConsts.CASH.VERIFICATION, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramMap, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	
	public String doAccountTotalQuery() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		String doObjName = DataFormat.getStringKey(inParam,"doObjName");
		String doTel = DataFormat.getStringKey(inParam,"doTel");
		int state = DataFormat.getIntKey(inParam,"state");
		int feeId = DataFormat.getIntKey(inParam,"feeId");
		int userType = DataFormat.getIntKey(inParam,"userType");
		String signTimeBegin = DataFormat.getStringKey(inParam,"signTimeBegin");
		String signTimeEnd = DataFormat.getStringKey(inParam,"signTimeEnd");
		//直接将后台的JSON串输出到前台
		CashTotalParamIn paramIn = new CashTotalParamIn();
		paramIn.setDoObjName(doObjName);
		paramIn.setDoTel(doTel);
		paramIn.setFeeId(feeId);
		paramIn.setSignTimeBegin(signTimeBegin);
		paramIn.setSignTimeEnd(signTimeEnd);
		paramIn.setState(state);
		//1 师傅 2 公司
		paramIn.setBelongObjType(2);
		paramIn.setBelongObjId(baseUser.getTenantId());
		//执行实体类型 1 师傅 2 公司
		paramIn.setDoObjType(userType<0?1:userType);
		PageOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	public String doCompanyAccountTotalQuery() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		String doObjName = DataFormat.getStringKey(inParam,"doObjName");
		long doObjId = DataFormat.getLongKey(inParam,"doObjId");
		int state = DataFormat.getIntKey(inParam,"state");
		String signTimeBegin = DataFormat.getStringKey(inParam,"signTimeBegin");
		String signTimeEnd = DataFormat.getStringKey(inParam,"signTimeEnd");
		//直接将后台的JSON串输出到前台
		CashTotalParamIn paramIn = new CashTotalParamIn();
		paramIn.setDoObjName(doObjName);
		paramIn.setDoObjId(doObjId);
		paramIn.setSignTimeBegin(signTimeBegin);
		paramIn.setSignTimeEnd(signTimeEnd);
		paramIn.setUserType(SysStaticDataEnum.USER_TYPE.MASTER_PARTNERS);
		paramIn.setState(state);
		//1 师傅 2 公司
		paramIn.setBelongObjType(2);
		paramIn.setBelongObjId(baseUser.getTenantId());
		//执行实体类型 1 师傅 2 公司
		paramIn.setDoObjType(2);
		PageOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	public String doBusiAccountTotalQuery() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		String doObjName = DataFormat.getStringKey(inParam,"doObjName");
		String doTel = DataFormat.getStringKey(inParam,"doTel");
		int state = DataFormat.getIntKey(inParam,"state");
		String signTimeBegin = DataFormat.getStringKey(inParam,"signTimeBegin");
		String signTimeEnd = DataFormat.getStringKey(inParam,"signTimeEnd");
		//直接将后台的JSON串输出到前台
		CashTotalParamIn paramIn = new CashTotalParamIn();
		paramIn.setBelongObjName(doObjName);
		paramIn.setBelongTel(doTel);
		paramIn.setSignTimeBegin(signTimeBegin);
		paramIn.setSignTimeEnd(signTimeEnd);
		paramIn.setState(state);
		//1 师傅 2 公司
		paramIn.setBelongObjType(2);
		paramIn.setBelongObjId(baseUser.getTenantId());
		//执行实体类型 1 师傅 2 公司
		//paramIn.setDoObjType(1);
		PageOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
}
