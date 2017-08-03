package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class GetCustomerIn implements IParamIn {

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.CM.GET_CUSTOMER;
	}

	/** 查询的名称 */
	private String name;
	/** 查询的类型 1:发货方 2 收货方 */
	private int type;
	/** 状态：0: 有效; 1:无效 */
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
