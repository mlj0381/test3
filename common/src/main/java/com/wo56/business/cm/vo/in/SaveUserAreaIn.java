package com.wo56.business.cm.vo.in;

import java.util.List;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class SaveUserAreaIn implements IParamIn{
	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CM.SAVE_USER_AREA;
	}
	
	private List<UserAreaParam> userAreaList ;

	public List<UserAreaParam> getUserAreaList() {
		return userAreaList;
	}

	public void setUserAreaList(List<UserAreaParam> userAreaList) {
		this.userAreaList = userAreaList;
	}
}
