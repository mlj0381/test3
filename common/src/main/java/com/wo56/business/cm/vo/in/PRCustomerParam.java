package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class PRCustomerParam implements IParamIn{

	/**查询发货人收货人信息**/
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.CM.QUERY_CUSTOMER;
	}
	
	/**发货人名字（必传）*/
	private String name;
	/**查询类型（必传）*/
	private int type;
	private int state;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
