package com.wo56.business.cm.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class QueryVehicleDriverOut extends BaseOutParamVO{
	private static final long serialVersionUID = 2321312345667772L;
	private String name ;
	private long id;
	private String bill;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	
	
}
