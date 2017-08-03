package com.wo56.business.statistic.vo;

import java.io.Serializable;

public class TableXAxisHeadsMapping implements Serializable {
	private static final long serialVersionUID = -5525185907189397661L;

	private String[] xAxisHeadsName;
	private String[] xAxisHeadsValue;

	public TableXAxisHeadsMapping(String[] xAxisHeadsName, String[] xAxisHeadsValue) {
		this.xAxisHeadsName = xAxisHeadsName;
		this.xAxisHeadsValue = xAxisHeadsValue;
	}

	public String[] getxAxisHeadsName() {
		return xAxisHeadsName;
	}

	public void setxAxisHeadsName(String[] xAxisHeadsName) {
		this.xAxisHeadsName = xAxisHeadsName;
	}

	public String[] getxAxisHeadsValue() {
		return xAxisHeadsValue;
	}

	public void setxAxisHeadsValue(String[] xAxisHeadsValue) {
		this.xAxisHeadsValue = xAxisHeadsValue;
	}
}
