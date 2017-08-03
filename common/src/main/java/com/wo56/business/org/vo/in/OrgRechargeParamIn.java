package com.wo56.business.org.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;
/**
 * @author zjy
 * （1）必传 （0）选传
 * */
public class OrgRechargeParamIn implements IParamIn{

	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.BASE.RECHARGE;
	}
    /**充值金额 (1)*/
	private long money;
	/**网点id*/
	private String orgId;
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	
	
}
