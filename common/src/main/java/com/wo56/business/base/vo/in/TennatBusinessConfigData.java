package com.wo56.business.base.vo.in;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class TennatBusinessConfigData implements Serializable {
	private String businessName;
	private List<Object> businessDatas;

	public TennatBusinessConfigData() {
	}

	public TennatBusinessConfigData(String businessName, List<Object> businessDatas) {
		super();
		this.businessName = businessName;
		this.businessDatas = businessDatas;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public List<Object> getBusinessDatas() {
		return businessDatas;
	}

	public void setBusinessDatas(List<Object> businessDatas) {
		this.businessDatas = businessDatas;
	}

}
