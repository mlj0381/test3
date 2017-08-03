package com.wo56.business.cm.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class PRCustomerParamOut extends BaseOutParamVO{

	private static final long serialVersionUID = 2321312345667772L;
	
	/**发货人Id**/
	private long pId;
	/**发货人姓名**/
	private String pName;
	/**发货人联系人 **/
	private String pLinkmanName;
	/**发货人固话**/
	private String pTelephone;
	/**发货人手机**/
	private String pBill;
	
	/**收货人id**/
	private long rId;
	/**收货人姓名**/
	private String rName;
	/**收货联系人**/
	private String rLinkmanName;
	/**收货人固话**/
	private String rTelephone;
	/**收货人手机**/
	private String rBill;
	/**收货人详细地址**/
	private String rAddress;
	
	
	
	public String getrAddress() {
		return rAddress;
	}
	public void setrAddress(String rAddress) {
		this.rAddress = rAddress;
	}
	public long getrId() {
		return rId;
	}
	public void setrId(long rId) {
		this.rId = rId;
	}
	public long getpId() {
		return pId;
	}
	public void setpId(long pId) {
		this.pId = pId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getpLinkmanName() {
		return pLinkmanName;
	}
	public void setpLinkmanName(String pLinkmanName) {
		this.pLinkmanName = pLinkmanName;
	}
	public String getpTelephone() {
		return pTelephone;
	}
	public void setpTelephone(String pTelephone) {
		this.pTelephone = pTelephone;
	}
	public String getpBill() {
		return pBill;
	}
	public void setpBill(String pBill) {
		this.pBill = pBill;
	}
	public String getrName() {
		return rName;
	}
	public void setrName(String rName) {
		this.rName = rName;
	}
	public String getrLinkmanName() {
		return rLinkmanName;
	}
	public void setrLinkmanName(String rLinkmanName) {
		this.rLinkmanName = rLinkmanName;
	}
	public String getrTelephone() {
		return rTelephone;
	}
	public void setrTelephone(String rTelephone) {
		this.rTelephone = rTelephone;
	}
	public String getrBill() {
		return rBill;
	}
	public void setrBill(String rBill) {
		this.rBill = rBill;
	}
}
