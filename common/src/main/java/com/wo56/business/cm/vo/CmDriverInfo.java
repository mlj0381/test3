package com.wo56.business.cm.vo;

// Generated 2016-3-15 16:26:20 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.wo56.business.org.vo.Organization;
import com.wo56.common.utils.OraganizationCacheDataUtil;

/**
 * CmDriverInfo generated by hbm2java
 */
public class CmDriverInfo implements java.io.Serializable {

	private long id;
	private long orgId;
	private long currentOrgId;
	private long tenantId;
	private String name;
	private String telephone;
	private String bill;
	private String identityCard;
	private String drivingLicense;
	private String bankAccount;
	private String bankName;
	private String certificate;
	private int status;
	private Date createDate;
	private long opId;
	private String orgName;
	private String identityCardNo;
	private String identityCardBack;
	private String opName;
	private Long creatorId;
	private String creatorName;
	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getOrgName() {
		Organization org = OraganizationCacheDataUtil.getOrganization(this.orgId);

		if (org != null) {
			return org.getOrgName();
		}
		return "";
	}

	public CmDriverInfo() {
	}

	public CmDriverInfo(long id, long orgId, long currentOrgId, long tenantId, String name, String telephone,
			String bill, String identityCard, String drivingLicense, String bankAccount, String bankName,
			String certificate, int status, Date createDate, long opId) {
		this.id = id;
		this.orgId = orgId;
		this.currentOrgId = currentOrgId;
		this.tenantId = tenantId;
		this.name = name;
		this.telephone = telephone;
		this.bill = bill;
		this.identityCard = identityCard;
		this.drivingLicense = drivingLicense;
		this.bankAccount = bankAccount;
		this.bankName = bankName;
		this.certificate = certificate;
		this.status = status;
		this.createDate = createDate;
		this.opId = opId;
	}

	public String getIdentityCardNo() {
		return identityCardNo;
	}

	public void setIdentityCardNo(String identityCardNo) {
		this.identityCardNo = identityCardNo;
	}

	public String getIdentityCardBack() {
		return identityCardBack;
	}

	public void setIdentityCardBack(String identityCardBack) {
		this.identityCardBack = identityCardBack;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public long getCurrentOrgId() {
		return this.currentOrgId;
	}

	public void setCurrentOrgId(long currentOrgId) {
		this.currentOrgId = currentOrgId;
	}

	public long getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBill() {
		return this.bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public String getIdentityCard() {
		return this.identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getDrivingLicense() {
		return this.drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCertificate() {
		return this.certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public long getOpId() {
		return this.opId;
	}

	public void setOpId(long opId) {
		this.opId = opId;
	}


}
