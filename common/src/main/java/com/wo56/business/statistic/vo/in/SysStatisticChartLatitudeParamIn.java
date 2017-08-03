package com.wo56.business.statistic.vo.in;

import java.util.Map;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class SysStatisticChartLatitudeParamIn extends PageInParamVO implements IParamIn {

	int chartId;
	Map sqlQueryParams;
	Map<String, String> macroVariablesParams;
	Map handlerParams;
	Map<String, String> pointUserOptions;
	Map<String, String> extMap;

	public Map<String, String> getExtMap() {
		return extMap;
	}

	public void setExtMap(Map<String, String> extMap) {
		this.extMap = extMap;
	}

	public int getChartId() {
		return chartId;
	}

	public void setChartId(int chartId) {
		this.chartId = chartId;
	}

	public Map getSqlQueryParams() {
		return sqlQueryParams;
	}

	public void setSqlQueryParams(Map sqlQueryParams) {
		this.sqlQueryParams = sqlQueryParams;
	}

	public Map<String, String> getMacroVariablesParams() {
		return macroVariablesParams;
	}

	public void setMacroVariablesParams(Map<String, String> macroVariablesParams) {
		this.macroVariablesParams = macroVariablesParams;
	}

	public Map getHandlerParams() {
		return handlerParams;
	}

	public void setHandlerParams(Map handlerParams) {
		this.handlerParams = handlerParams;
	}

	public Map<String, String> getPointUserOptions() {
		return pointUserOptions;
	}

	public void setPointUserOptions(Map<String, String> pointUserOptions) {
		this.pointUserOptions = pointUserOptions;
	}

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return "170002";
	}

}
