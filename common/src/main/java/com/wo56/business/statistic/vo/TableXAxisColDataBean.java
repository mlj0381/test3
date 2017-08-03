package com.wo56.business.statistic.vo;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class TableXAxisColDataBean implements Serializable {
	private static final long serialVersionUID = 3791121239281066911L;
	private String value = "&nbsp";
	private String className = "";

	public TableXAxisColDataBean() {
	}

	public TableXAxisColDataBean(String value, String className) {
		if (StringUtils.isNotBlank(value))
			this.value = value;
		if (StringUtils.isNotBlank(className))
			this.className = className;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
