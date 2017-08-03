package com.wo56.business.org.vo.out;

import java.util.List;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class OrganizationTreeOut extends BaseOutParamVO{
	
	
	private Long id;
	
	private String name;
	
	private Integer type;
	
	private List<OrganizationTreeOut> childOrgList; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<OrganizationTreeOut> getChildOrgList() {
		return childOrgList;
	}

	public void setChildOrgList(List<OrganizationTreeOut> childOrgList) {
		this.childOrgList = childOrgList;
	}
	
	
}
