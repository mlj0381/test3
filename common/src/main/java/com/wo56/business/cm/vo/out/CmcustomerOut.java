package com.wo56.business.cm.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class CmcustomerOut extends BaseOutParamVO{

	private static final long serialVersionUID = 2321312345667772L;
	
	/**用户id*/
	private long id;
	/**用户名*/
	private String name;
	/**联系人*/
	private String linkmanName;
	/**固话*/
	private String telephone;
	/**手机*/
	private String bill;
	private Long orgId;
	private String strSign;
	private String address;
	private int signingType;
	
	public int getSigningType() {
		return signingType;
	}
	public void setSigningType(int signingType) {
		this.signingType = signingType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStrSign() {
		return strSign;
	}
	public void setStrSign(String strSign) {
		this.strSign = strSign;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkmanName() {
		return linkmanName;
	}
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	
}
