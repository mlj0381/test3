package com.wo56.business.sys.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class DelSysMenuIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.COMMON.DEL_SYS_MENU;
	}
	private String[] menuIds;
	public String[] getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(String[] menuIds) {
		this.menuIds = menuIds;
	}
}
