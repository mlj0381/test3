package com.wo56.business.statistic.vo;

public class StaticDataSet {
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	private long tanentId;
	private long orgId;
	
	private String codeTypePackage;
	private String codeTypeProduct;
	
	public String getCodeTypeProduct() {
		return codeTypeProduct;
	}
	public void setCodeTypeProduct(String codeTypeProduct) {
		this.codeTypeProduct = codeTypeProduct;
	}
	public String getCodeValueProduct() {
		return codeValueProduct;
	}
	public void setCodeValueProduct(String codeValueProduct) {
		this.codeValueProduct = codeValueProduct;
	}
	private String codeValuePackage;
	private String codeValueProduct;
	private String orgName;	
	public long getTanentId() {
		return tanentId;
	}
	public void setTanentId(long tanentId) {
		this.tanentId = tanentId;
	}
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCodeTypePackage() {
		return codeTypePackage;
	}
	public void setCodeTypePackage(String codeTypePackage) {
		this.codeTypePackage = codeTypePackage;
	}
	
	public String getCodeValuePackage() {
		return codeValuePackage;
	}
	public void setCodeValuePackage(String codeValuePackage) {
		this.codeValuePackage = codeValuePackage;
	}
	
	

}
