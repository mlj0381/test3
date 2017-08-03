package com.wo56.business.sche.serve;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.multitab.CommonVO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.sche.serve.vo.in.ServeComplaintSaveParamIn;
import com.wo56.business.sche.serve.vo.in.ServeQueryParamIn;
import com.wo56.business.sche.serve.vo.in.ServeVisitQueryParamIn;
import com.wo56.business.sche.serve.vo.in.ServeVisitSaveParamIn;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class ServeBO extends BaseBO {

	/**
	 * 投诉管理查询
	 * @return
	 * @throws Exception
	 */
	public String doQuery() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		int provinceId = DataFormat.getIntKey(inParam,"provinceId");
		int cityId = DataFormat.getIntKey(inParam,"cityId");
		int countyId = DataFormat.getIntKey(inParam,"countyId");
		int state = DataFormat.getIntKey(inParam,"state");//投诉状态
		int queryType = DataFormat.getIntKey(inParam,"queryType");
		String receiveName = DataFormat.getStringKey(inParam,"receiveName");
		String receivePhone = DataFormat.getStringKey(inParam,"receivePhone");
		String wayBillId = DataFormat.getStringKey(inParam,"wayBillId");
		String doObjName = DataFormat.getStringKey(inParam,"doObjName");
		String doTel = DataFormat.getStringKey(inParam,"doTel");
		long id = DataFormat.getLongKey(inParam,"id");
		int flag = DataFormat.getIntKey(inParam,"flag");
		if(flag==2 && id<=0){
			return "";
		}
		//直接将后台的JSON串输出到前台
		ServeQueryParamIn paramIn = new ServeQueryParamIn();
		paramIn.setId(id);
		paramIn.setProvinceId(provinceId);
		paramIn.setCityId(cityId);
		paramIn.setCountyId(countyId);
		paramIn.setState(state);
		paramIn.setReceiveName(receiveName);
		paramIn.setReceivePhone(receivePhone);
		paramIn.setWayBillId(wayBillId);
		paramIn.setQueryType(queryType);
		paramIn.setDoObjName(doObjName);
		paramIn.setDoTel(doTel);
		paramIn.setFlag(flag);
		paramIn.setTenantId(baseUser.getTenantId());
		Organization organization=OraganizationCacheDataUtil.getOrganization(baseUser.getOrgId());
		paramIn.setTenantType(Long.parseLong(organization.getTenantType()));
		paramIn.setOrgId(baseUser.getOrgId());
		paramIn.setDutyOrgs(baseUser.getTenantId().toString());
		PageOutParamVO<CommonVO> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<CommonVO>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/**
	 * 回访管理查询
	 * @return
	 * @throws Exception
	 */
	public String doQueryVisit() throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		int provinceId = DataFormat.getIntKey(inParam,"provinceId");
		int cityId = DataFormat.getIntKey(inParam,"cityId");
		int countyId = DataFormat.getIntKey(inParam,"countyId");
		int state = DataFormat.getIntKey(inParam,"state");//投诉状态
		String receiveName = DataFormat.getStringKey(inParam,"receiveName");
		String receivePhone = DataFormat.getStringKey(inParam,"receivePhone");
		String wayBillId = DataFormat.getStringKey(inParam,"wayBillId");
		long id = DataFormat.getLongKey(inParam,"id");
		int flag = DataFormat.getIntKey(inParam,"flag");
		if(flag==2 && id<=0){
			return "";
		}
		//直接将后台的JSON串输出到前台
		ServeVisitQueryParamIn paramIn = new ServeVisitQueryParamIn();
		paramIn.setProvinceId(provinceId);
		paramIn.setCityId(cityId);
		paramIn.setCountyId(countyId);
		paramIn.setState(state);
		paramIn.setReceiveName(receiveName);
		paramIn.setReceivePhone(receivePhone);
		paramIn.setWayBillId(wayBillId);
		paramIn.setId(id);
		paramIn.setTenantId(baseUser.getTenantId());
		Organization organization=OraganizationCacheDataUtil.getOrganization(baseUser.getOrgId());
		paramIn.setTenantType(Long.parseLong(organization.getTenantType()));
		paramIn.setOrgId(baseUser.getOrgId());
		PageOutParamVO<CommonVO> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<CommonVO>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}

	public String doSave()throws Exception{
		BaseUser baseUser = SysContexts.getCurrentOperator();
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		long mainId = DataFormat.getLongKey(inParam,"id");
		ServeComplaintSaveParamIn paramIn = new ServeComplaintSaveParamIn();
		BeanUtils.populate(paramIn, inParam);
		paramIn.setMainId(mainId);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	public String doVisitSave()throws Exception{
		BaseUser baseUser = SysContexts.getCurrentOperator();
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		ServeVisitSaveParamIn paramIn = new ServeVisitSaveParamIn();
		BeanUtils.populate(paramIn, inParam);
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
}
