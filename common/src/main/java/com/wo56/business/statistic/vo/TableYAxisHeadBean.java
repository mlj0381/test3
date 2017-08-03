package com.wo56.business.statistic.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class TableYAxisHeadBean implements Serializable {
	private static final long serialVersionUID = 1918124770470224026L;
	private String name;
	private String className = "";
	private List<TableYAxisHeadBean> subYAxisHeads;

	public TableYAxisHeadBean(String name, List<TableYAxisHeadBean> subYAxisHeads) {
		this.name = name;
		this.subYAxisHeads = subYAxisHeads;
	}

	public TableYAxisHeadBean(String name) {
		this.name = name;
	}

	public TableYAxisHeadBean() {
	}

	public TableYAxisHeadBean(String name, String className, List<TableYAxisHeadBean> subYAxisHeads) {
		super();
		this.name = name;
		if(StringUtils.isNotBlank(className))
			this.className = className;
		this.subYAxisHeads = subYAxisHeads;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TableYAxisHeadBean> getSubYAxisHeads() {
		return subYAxisHeads;
	}

	public void setSubYAxisHeads(List<TableYAxisHeadBean> subYAxisHeads) {
		this.subYAxisHeads = subYAxisHeads;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return "TableYAxisHeadBean [name=" + name + ", subYAxisHeads="
				+ subYAxisHeads + "]";
	}
}
