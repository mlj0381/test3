package com.wo56.business.sms.vo.in;

import java.util.List;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class SysSmsSearchList implements IParamIn{

	
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.SMS.NOTICE;
	}
	
	private List<SysSmsSearchVO> list;

	public List<SysSmsSearchVO> getList() {
		return list;
	}

	public void setList(List<SysSmsSearchVO> list) {
		this.list = list;
	}

	
}
