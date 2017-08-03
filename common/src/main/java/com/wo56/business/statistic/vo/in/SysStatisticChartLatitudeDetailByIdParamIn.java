package com.wo56.business.statistic.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class SysStatisticChartLatitudeDetailByIdParamIn extends PageInParamVO implements IParamIn {

	int latitudeId;

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return "170004";
	}

	public int getLatitudeId() {
		return latitudeId;
	}

	public void setLatitudeId(int latitudeId) {
		this.latitudeId = latitudeId;
	}

}
