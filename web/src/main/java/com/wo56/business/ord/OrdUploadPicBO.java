package com.wo56.business.ord;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ord.intf.IOrdUploadPicIntf;

public class OrdUploadPicBO <E> extends BaseBO{
	/**
	 * 查询运单图片
	 * @return
	 * @throws Exception
	 */
	public String queryOrderPic()throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrdUploadPicIntf sv=CallerProxy.getSVBean(IOrdUploadPicIntf.class, "ordUploadPicTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(sv.queryOrderPic(inParam));
	}
	/**
	 * 保存运单图片
	 * @return
	 * @throws Exception
	 */
	public String doSaveOrderPic()throws Exception{
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrdUploadPicIntf sv=CallerProxy.getSVBean(IOrdUploadPicIntf.class, "ordUploadPicTF", new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(sv.doSaveOrderPic(inParam));
	}
}
