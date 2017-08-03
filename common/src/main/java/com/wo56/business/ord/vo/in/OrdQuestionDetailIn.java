package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdQuestionDetailIn implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORD_QUESTION_DETAIL;
	}

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
