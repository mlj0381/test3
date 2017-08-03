package com.wo56.business.cm.impl;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.exception.BusinessException;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ac.vo.out.AcAccountWalletTipOut;
import com.wo56.business.cm.intf.ICmUserInfoPullIntf;

public class CmUserInfoPullBO extends BaseBO{
	/**
	 * 列表查询
	 * @return
	 * @author qlf
	 */
	public String queryCmUserInfoPull(){
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmUserInfoPullIntf intf = CallerProxy.getSVBean(ICmUserInfoPullIntf.class, "cmUserInfoPullTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		Map<String,Object> map = intf.queryCmUserInfoPull(inParam);
		
		return JsonHelper.json(map);
	}
	
	/**
	 * 拉黑拉包工
	 * @author qlf
	 */
	public String isBlackPull()throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmUserInfoPullIntf intf = CallerProxy.getSVBean(ICmUserInfoPullIntf.class, "cmUserInfoPullTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
				
		return intf.isBlackPull(inParam);
		
	}
	
	/**
	 * 保存与修改
	 * @author qlf
	 */
	public String doSavePull()throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmUserInfoPullIntf intf = CallerProxy.getSVBean(ICmUserInfoPullIntf.class, "cmUserInfoPullTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
				
		return intf.doSavePull(inParam);
		
	}
	
	/**
	 * 查询拉包工信息
	 * @author qlf
	 */
	public String getCmUserInfoPull()throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmUserInfoPullIntf intf = CallerProxy.getSVBean(ICmUserInfoPullIntf.class, "cmUserInfoPullTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
				
		return JsonHelper.json(intf.getCmUserInfoPull(inParam));
		
	}
	
	/**
	 * 失效用户
	 * @author qlf
	 */
	public String delUserInfoPull()throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmUserInfoPullIntf intf = CallerProxy.getSVBean(ICmUserInfoPullIntf.class, "cmUserInfoPullTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		long userId = DataFormat.getLongKey(inParam,"userId");
		if(userId < 0){
			throw new BusinessException("请传入用户编号！");
		}
		intf.delUserInfoPull(inParam);
		return "Y";
		
	}
	
	/**
	 * 失效用户
	 * @author qlf
	 */
	public String verifyCmUserInfoPull()throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmUserInfoPullIntf intf = CallerProxy.getSVBean(ICmUserInfoPullIntf.class, "cmUserInfoPullTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		long userId = DataFormat.getLongKey(inParam,"userId");
		if(userId < 0){
			throw new BusinessException("请传入用户编号！");
		}
		intf.verifyCmUserInfoPull(inParam);
		return "Y";
		
	}
	
	/**
	 * 查询拉包工账号
	 * @author qlf
	 */
	public String getCmUserInfoPullByBill()throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmUserInfoPullIntf intf = CallerProxy.getSVBean(ICmUserInfoPullIntf.class, "cmUserInfoPullTF", 
				new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(intf.getCmUserInfoPullByBill(inParam));
		
	}
	
	/**
	 * 审核费用拉包工信息
	 * @param inParam
	 * @return
	 */
	public String getAccountPullByUserId(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmUserInfoPullIntf intf = CallerProxy.getSVBean(ICmUserInfoPullIntf.class, "cmUserInfoPullTF", 
				new TypeToken<AcAccountWalletTipOut>(){}.getType());
		return JsonHelper.json(intf.getAccountPullByUserId(inParam));
	}
	/**
	 * 注销用户
	 * @return
	 */
	public String delCmUserInfoPull(){
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		ICmUserInfoPullIntf intf = CallerProxy.getSVBean(ICmUserInfoPullIntf.class, "cmUserInfoPullTF", 
				new TypeToken<AcAccountWalletTipOut>(){}.getType());
		return intf.delCmUserInfoPull(inParam);
	}
}
