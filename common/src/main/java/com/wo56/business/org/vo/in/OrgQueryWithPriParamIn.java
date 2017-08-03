package com.wo56.business.org.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrgQueryWithPriParamIn implements IParamIn {
	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.BASE.QUERY_CURRENT_USER_PRI_ORGS;
	}
}
