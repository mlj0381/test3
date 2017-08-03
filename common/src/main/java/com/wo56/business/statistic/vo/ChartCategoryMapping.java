package com.wo56.business.statistic.vo;

import java.io.Serializable;

public class ChartCategoryMapping implements Serializable {
	private String value;
	private String text;

	public ChartCategoryMapping() {
	}

	public ChartCategoryMapping(String value, String text) {
		super();
		this.value = value;
		this.text = text;
	}

	@Override
	public String toString() {
		return "ChartCategoryInfo [value=" + value + ", text=" + text + "]";
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
