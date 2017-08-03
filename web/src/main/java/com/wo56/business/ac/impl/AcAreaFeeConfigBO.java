package com.wo56.business.ac.impl;

import java.util.List;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ac.vo.in.AcAreaFeeConParamIn;

public class AcAreaFeeConfigBO extends BaseBO{

	/***
	 * 查询配送区域
	 * 调用接口编码：140000
	 * 入参：feeId 费用id 
	 *      orgId 网点id
	 * 		tenantId 租户id	
	 * */
	public String doQueryArea() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		long orgId = DataFormat.getLongKey(map,"orgId");
		BaseUser baseUser = SysContexts.getCurrentOperator();
		long tenantId = baseUser.getTenantId();
		//直接将后台的JSON串输出到前台
		AcAreaFeeConParamIn feeConParamIn = new AcAreaFeeConParamIn();
		feeConParamIn.setOrgId(orgId);
		feeConParamIn.setTenantId(tenantId);
		ListOutParamVO<Map> list = CallerProxy.call(feeConParamIn, new TypeToken<ListOutParamVO<Map>>(){}.getType());
		List<Map> rtnList = list.getContent();
		if(rtnList != null){
			return JsonHelper.json(rtnList);
		}else{
			return null;
		}
	}
	
}
