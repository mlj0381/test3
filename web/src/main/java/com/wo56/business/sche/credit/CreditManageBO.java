package com.wo56.business.sche.credit;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.sche.credit.vo.in.CreditDtlQueryParamIn;
import com.wo56.business.sche.credit.vo.in.CreditQueryParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;
import com.wo56.common.consts.LoginConst;

public class CreditManageBO extends BaseBO {

	/**
	 * 师傅信用查询
	 * @return
	 * @throws Exception
	 */
	public String doQuery() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		String workerName = DataFormat.getStringKey(inParam,"workerName");//提现人姓名
		String workerLoginAcct = DataFormat.getStringKey(inParam,"workerLoginAcct");//登录帐号
		//直接将后台的JSON串输出到前台
		CreditQueryParamIn paramIn = new CreditQueryParamIn();
		paramIn.setWorkerLoginAcct(workerLoginAcct);
		paramIn.setWorkerName(workerName);
		paramIn.setInterFacesCode(InterFacesCodeConsts.CREDIT.CREDIT_QUERY);
		paramIn.setTenantCode(String.valueOf(baseUser.getAttrs().get(LoginConst.BaseUserAttr.TENANT_CODE)));
		PageOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/**
	 * 师傅KPI查询
	 * @return
	 * @throws Exception
	 */
	public String doQueryKpi() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		String workerName = DataFormat.getStringKey(inParam,"workerName");//提现人姓名
		String workerLoginAcct = DataFormat.getStringKey(inParam,"workerLoginAcct");//登录帐号
		int provinceId = DataFormat.getIntKey(inParam,"provinceId");
		int cityId = DataFormat.getIntKey(inParam,"cityId");
		int countyId = DataFormat.getIntKey(inParam,"countyId");
		int isTmail = DataFormat.getIntKey(inParam,"isTmail");
		String beginDate = DataFormat.getStringKey(inParam,"beginDate");
		String endDate = DataFormat.getStringKey(inParam,"endDate");
		String provinceIdName = DataFormat.getStringKey(inParam,"provinceIdName");
		String cityIdName = DataFormat.getStringKey(inParam,"cityIdName");
		String doObjName = DataFormat.getStringKey(inParam,"doObjName");
		String doTel = DataFormat.getStringKey(inParam,"doTel");
		//直接将后台的JSON串输出到前台
		CreditQueryParamIn paramIn = new CreditQueryParamIn();
		paramIn.setWorkerLoginAcct(workerLoginAcct);
		paramIn.setWorkerName(workerName);
		paramIn.setInterFacesCode(InterFacesCodeConsts.CREDIT.CREDIT_KPI_QUERY);
		paramIn.setTenantCode(String.valueOf(baseUser.getAttrs().get(LoginConst.BaseUserAttr.TENANT_CODE)));
		paramIn.setProvinceId(provinceId);
		paramIn.setCityId(cityId);
		paramIn.setCountyId(countyId);
		paramIn.setBeginDate(beginDate);
		paramIn.setProvinceIdName(provinceIdName);
		paramIn.setCityIdName(cityIdName);
		paramIn.setEndDate(endDate);
		paramIn.setDoObjName(workerName);
		paramIn.setDoTel(workerLoginAcct);
		paramIn.setIsTmail(isTmail);
		PageOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}

	public String getDtlById()throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		long userId = DataFormat.getLongKey(inParam,"userId");
		CreditDtlQueryParamIn paramIn = new CreditDtlQueryParamIn();
		paramIn.setUserId(userId);
		paramIn.setTenantId(baseUser.getTenantId().toString());
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
}
