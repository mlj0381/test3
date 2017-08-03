package com.wo56.business.cm.vo;

import java.sql.Timestamp;
import java.util.Date;

import com.framework.core.base.POJO;

/**
 * CmUserRelationshipCorrespond entity. @author MyEclipse Persistence Tools
 */

public class CmUserRelationshipCorrespond extends POJO implements java.io.Serializable {

	// Fields

	private Long relationshipId;
	private String loginAcct;
	private String relationshipLoginAcct;
	private String relationshipOrgId;
	//private Date createDate;
	private String remark;

	// Constructors

	/** default constructor */
	public CmUserRelationshipCorrespond() {
	}

	/** minimal constructor */
	public CmUserRelationshipCorrespond(String loginAcct, String relationshipLoginAcct,
			Timestamp createDate) {
		this.loginAcct = loginAcct;
		this.relationshipLoginAcct = relationshipLoginAcct;
		this.createDate = createDate;
	}

	/** full constructor */
	public CmUserRelationshipCorrespond(String loginAcct, String relationshipLoginAcct,
			String relationshipOrgId, Timestamp createDate, String remark) {
		this.loginAcct = loginAcct;
		this.relationshipLoginAcct = relationshipLoginAcct;
		this.relationshipOrgId = relationshipOrgId;
		this.createDate = createDate;
		this.remark = remark;
	}

	// Property accessors

	public Long getRelationshipId() {
		return this.relationshipId;
	}

	public void setRelationshipId(Long relationshipId) {
		this.relationshipId = relationshipId;
	}

	

	public String getRelationshipOrgId() {
		return this.relationshipOrgId;
	}

	public void setRelationshipOrgId(String relationshipOrgId) {
		this.relationshipOrgId = relationshipOrgId;
	}

	

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLoginAcct() {
		return loginAcct;
	}

	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}

	public String getRelationshipLoginAcct() {
		return relationshipLoginAcct;
	}

	public void setRelationshipLoginAcct(String relationshipLoginAcct) {
		this.relationshipLoginAcct = relationshipLoginAcct;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	

}