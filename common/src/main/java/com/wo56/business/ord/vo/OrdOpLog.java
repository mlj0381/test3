package com.wo56.business.ord.vo;

import java.sql.Timestamp;

import com.framework.core.base.POJO;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

/**
 * OrdOpLog entity. @author MyEclipse Persistence Tools
 */

public class OrdOpLog extends POJO {

	// Fields

	private long id;
	private Long orderId;
	private Long trackingNum;
	private Long orgId;
	private Integer opType;
	private Long opId;
	private String opName;
	private String inContent;
	private Integer inType;
	private String outContent;
	private Integer outType;
	private String opTypeName;
	private String orgIdName;
	private String batchId;
	


	public String getBatchId() {
		return batchId;
	}


	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}


	public String getOrgIdName() {
		if(orgId != null && orgId>0){
			setOrgIdName(OraganizationCacheDataUtil.getOrgName(orgId));
		}
		return orgIdName;
	}


	public void setOrgIdName(String orgIdName) {
		this.orgIdName = orgIdName;
	}
	

	// Constructors

	public String getOpTypeName() {
		if(opType>0){
			setOpTypeName(SysStaticDataUtil.getSysStaticData("OP_TYPE_YUNQI", opType+"").getCodeName());
		}
		return opTypeName;
	}

	public void setOpTypeName(String opTypeName) {
		this.opTypeName = opTypeName;
	}


	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/** default constructor */
	public OrdOpLog() {
	}

	/** full constructor */
	public OrdOpLog(long id, Long orderId, Long trackingNum, Long orgId,
			Integer opType, Long opId, String opName, String opContent,Timestamp createDate,String batchId) {
		this.id = id;
		this.orderId = orderId;
		this.trackingNum = trackingNum;
		this.orgId = orgId;
		this.opType = opType;
		this.opId = opId;
		this.opName = opName;
		this.createDate = createDate;
		this.batchId = batchId;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public String getInContent() {
		return inContent;
	}


	public void setInContent(String inContent) {
		this.inContent = inContent;
	}


	public Integer getInType() {
		return inType;
	}


	public void setInType(Integer inType) {
		this.inType = inType;
	}


	public String getOutContent() {
		return outContent;
	}


	public void setOutContent(String outContent) {
		this.outContent = outContent;
	}


	public Integer getOutType() {
		return outType;
	}


	public void setOutType(Integer outType) {
		this.outType = outType;
	}


	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getTrackingNum() {
		return this.trackingNum;
	}

	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getOpType() {
		return this.opType;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getOpName() {
		return this.opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}


}