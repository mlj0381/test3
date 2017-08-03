package com.wo56.business.cm.impl;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.intf.IPullBlackCarrierIntf;

public class PullBlackCarrierBO extends BaseBO{
	
	
	/**
	 * 查询拉黑列表
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doQueryPullBlackCarrier() throws Exception {
		Map<String,Object>map=SysContexts.getRequestParamFlatMap();
		IPullBlackCarrierIntf pullBlackCarrierTF=CallerProxy.getSVBean(IPullBlackCarrierIntf.class, "pullBlackCarrierTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(pullBlackCarrierTF.doQueryPullBlackCarrier(map));
	}
	
	/**
	 * 查询拉包工
	 * @return
	 * @throws Exception
	 */
	public String doQueryPullBlackCarrierByAccount()throws Exception{
		Map<String,Object>map=SysContexts.getRequestParamFlatMap();
		IPullBlackCarrierIntf pullBlackCarrierTF=CallerProxy.getSVBean(IPullBlackCarrierIntf.class, "pullBlackCarrierTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(pullBlackCarrierTF.doQueryPullBlackCarrierByAccount(map));
	}
	/**
	 * 拉黑拉包工
	 * @return
	 * @throws Exception
	 */
	public String doSavePullBlackCarrierByAccount() throws Exception{
		Map<String,Object>map=SysContexts.getRequestParamFlatMap();
		IPullBlackCarrierIntf pullBlackCarrierTF=CallerProxy.getSVBean(IPullBlackCarrierIntf.class, "pullBlackCarrierTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(pullBlackCarrierTF.doSavePullBlackCarrierByAccount(map));
	}
	
	/**
	 * 取消拉黑
	 * @return
	 * @throws Exception
	 */
	public String doUpdatePullBlackCarrierByAccount() throws Exception{
		Map<String,Object>map=SysContexts.getRequestParamFlatMap();
		IPullBlackCarrierIntf pullBlackCarrierTF=CallerProxy.getSVBean(IPullBlackCarrierIntf.class, "pullBlackCarrierTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(pullBlackCarrierTF.doUpdatePullBlackCarrierByAccount(map));
	}

}
