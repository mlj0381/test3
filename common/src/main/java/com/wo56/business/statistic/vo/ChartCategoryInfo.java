package com.wo56.business.statistic.vo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ChartCategoryInfo implements java.io.Serializable {
	private List<ChartCategoryMapping> mapping;
	private String[] categories;
	private List<ChartCategoryInfo> childCategoryInfos;
	private Map<String,String> colIndexMappingCodeType;

	public ChartCategoryInfo() {
	}

	public ChartCategoryInfo(List<ChartCategoryMapping> mapping, String[] categories) {
		super();
		this.mapping = mapping;
		this.categories = categories;
	}

	public List<ChartCategoryMapping> getMapping() {
		return mapping;
	}

	public void setMapping(List<ChartCategoryMapping> mapping) {
		this.mapping = mapping;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}
	
	public List<ChartCategoryInfo> getChildCategoryInfos() {
		return childCategoryInfos;
	}

	public void setChildCategoryInfos(List<ChartCategoryInfo> childCategoryInfos) {
		this.childCategoryInfos = childCategoryInfos;
	}
	
	public Map<String, String> getColIndexMappingCodeType() {
		return colIndexMappingCodeType;
	}

	public void setColIndexMappingCodeType(
			Map<String, String> colIndexMappingCodeType) {
		this.colIndexMappingCodeType = colIndexMappingCodeType;
	}

	@Override
	public String toString() {
		return "ChartCategoryInfo [mapping=" + mapping + ", categories=" + Arrays.toString(categories) + ", childCategoryInfos=" + childCategoryInfos + "]";
	}
}
