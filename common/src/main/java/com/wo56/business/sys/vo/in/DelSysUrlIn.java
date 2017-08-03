package com.wo56.business.sys.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class DelSysUrlIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.COMMON.DEL_SYS_URL;
	}
	private String[] urlIds;
	public String[] getUrlIds() {
		return urlIds;
	}
	public void setUrlIds(String[] urlIds) {
		this.urlIds = urlIds;
	}
	
   
}
