package com.wo56.business.statistic.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class SysStatisticChartLatitudeDetailParamIn extends PageInParamVO implements IParamIn {

	int chartId;

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return "170003";
	}

	public int getChartId() {
		return chartId;
	}

	public void setChartId(int chartId) {
		this.chartId = chartId;
	}

}
