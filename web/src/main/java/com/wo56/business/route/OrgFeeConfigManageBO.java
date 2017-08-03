package com.wo56.business.route;

import java.util.Date;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.route.vo.in.OrgFeeConfigParamDtlIn;
import com.wo56.business.route.vo.in.OrgFeeConfigParamIn;
import com.wo56.business.route.vo.in.OrgFeeConfigSaveParamIn;

public class OrgFeeConfigManageBO extends BaseBO {

	/***
	 * 查询
	 * */
	public String doQuery() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		OrgFeeConfigParamIn paramIn = new OrgFeeConfigParamIn();

		long orgId = DataFormat.getLongKey(map,"orgId");
		long feeId = DataFormat.getLongKey(map,"feeId");
	      		paramIn.setOrgId(orgId);
	      		paramIn.setFeeId(feeId);
	    PageOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	/***
	 * 查询网点费用详情
	 * */
	public String getOrgFeeDtl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		//BaseUser baseUser = SysContexts.getCurrentOperator();
		OrgFeeConfigParamDtlIn paramIn = new OrgFeeConfigParamDtlIn();
		BeanUtils.populate(paramIn, map);
		ListOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<ListOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	
	/***
	 * 保存
	 * */
	public String doSave() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		OrgFeeConfigSaveParamIn paramIn = new OrgFeeConfigSaveParamIn();
		BeanUtils.populate(paramIn, map);
		paramIn.setCreateTime(new Date());
		paramIn.setOpId(baseUser.getOperId());
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
}
