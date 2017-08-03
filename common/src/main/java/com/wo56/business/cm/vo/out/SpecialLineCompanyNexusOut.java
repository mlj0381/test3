package com.wo56.business.cm.vo.out;

import java.util.Date;

public class SpecialLineCompanyNexusOut {
	private long relId;
	// 创建人
	private String creatorName;
	// 创建时间
	private Date createDate;
	// 专线公司名称
	private String speciaLineName;
	// 拉包公司名称
	private String carrierCompanyName;
	// 拉包公司法人
	private String carrierCompanyPrincipal;
	// 拉包公司电话：
	private String carrierCompanyBill;
	// 专线联系人
	private String specialLinePrincipal;
	// 专线公司电话
	private String specialLineBill;
	public long getRelId() {
		return relId;
	}
	public void setRelId(long relId) {
		this.relId = relId;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getSpeciaLineName() {
		return speciaLineName;
	}
	public void setSpeciaLineName(String speciaLineName) {
		this.speciaLineName = speciaLineName;
	}
	public String getCarrierCompanyName() {
		return carrierCompanyName;
	}
	public void setCarrierCompanyName(String carrierCompanyName) {
		this.carrierCompanyName = carrierCompanyName;
	}
	public String getCarrierCompanyPrincipal() {
		return carrierCompanyPrincipal;
	}
	public void setCarrierCompanyPrincipal(String carrierCompanyPrincipal) {
		this.carrierCompanyPrincipal = carrierCompanyPrincipal;
	}
	public String getCarrierCompanyBill() {
		return carrierCompanyBill;
	}
	public void setCarrierCompanyBill(String carrierCompanyBill) {
		this.carrierCompanyBill = carrierCompanyBill;
	}
	public String getSpecialLinePrincipal() {
		return specialLinePrincipal;
	}
	public void setSpecialLinePrincipal(String specialLinePrincipal) {
		this.specialLinePrincipal = specialLinePrincipal;
	}
	public String getSpecialLineBill() {
		return specialLineBill;
	}
	public void setSpecialLineBill(String specialLineBill) {
		this.specialLineBill = specialLineBill;
	}

	
	

}
