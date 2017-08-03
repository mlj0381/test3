package com.wo56.business.cm.intf;

import java.util.Map;

import com.framework.core.inter.vo.Pagination;
import com.wo56.business.ac.vo.out.AcAccountWalletTipOut;
import com.wo56.business.cm.vo.out.CmUserInfoPullParamOut;

public interface ICmUserInfoPullIntf{
	public Map<String,Object> queryCmUserInfoPull(Map<String,Object> inParam);
	
	public String isBlackPull(Map<String,Object> inParam)throws Exception;
	
	public String doSavePull(Map<String,Object> inParam) throws Exception;
	
	public Map<String,Object> getCmUserInfoPull(Map<String,Object> inParam)throws Exception;
	
	public String delUserInfoPull(Map<String,Object> inParam);
	
	public String verifyCmUserInfoPull(Map<String,Object> inParam);
	
	public Map<String,Object> getCmUserInfoPullByBill(Map<String,Object> inParam);
	/**
	 * 审核费用拉包工信息
	 * @param inParam
	 * @return
	 */
	public AcAccountWalletTipOut getAccountPullByUserId(Map<String,Object> inParam);
	
	
	public String delCmUserInfoPull(Map<String,Object> inParam);
}
