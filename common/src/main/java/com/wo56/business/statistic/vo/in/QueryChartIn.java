package com.wo56.business.statistic.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class QueryChartIn extends PageInParamVO implements IParamIn {

	private int chartId;

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return "170005";
	}

	public int getChartId() {
		return chartId;
	}

	public void setChartId(int chartId) {
		this.chartId = chartId;
	}

}
