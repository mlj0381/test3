package com.wo56.business.ord.vo.in;

import java.util.Date;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdQuestionSaveIn implements IParamIn{

	/**
	 * 异常单保存
	 */
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORD_QUESTION_SAVE;
	}

	private Long id;
	private Long orderId;
	private Long trackingNum;
	private Long orgId;
	private Long dutyOrgId;
	private Integer type;
	private String notes;
	private Long amount;
	private Integer auditStatus;
	private String auditIdea;
	private Long opId;
	private String  opIdName;
	private Long dutyOpId;
	private Long arbitrateOpId;
	private Date createDate;
	private Date auditDate;
	private Date arbitrateDate;
	private Long resultOrgId;
	private Long resultAmount;
	private String resultNotes;
	private String driverName;
	private String plateNumber;
	private String dutyOpIdName;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getDutyOrgId() {
		return dutyOrgId;
	}
	public void setDutyOrgId(Long dutyOrgId) {
		this.dutyOrgId = dutyOrgId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAuditIdea() {
		return auditIdea;
	}
	public void setAuditIdea(String auditIdea) {
		this.auditIdea = auditIdea;
	}
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public String getOpIdName() {
		return opIdName;
	}
	public void setOpIdName(String opIdName) {
		this.opIdName = opIdName;
	}
	public Long getDutyOpId() {
		return dutyOpId;
	}
	public void setDutyOpId(Long dutyOpId) {
		this.dutyOpId = dutyOpId;
	}
	public Long getArbitrateOpId() {
		return arbitrateOpId;
	}
	public void setArbitrateOpId(Long arbitrateOpId) {
		this.arbitrateOpId = arbitrateOpId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public Date getArbitrateDate() {
		return arbitrateDate;
	}
	public void setArbitrateDate(Date arbitrateDate) {
		this.arbitrateDate = arbitrateDate;
	}
	public Long getResultOrgId() {
		return resultOrgId;
	}
	public void setResultOrgId(Long resultOrgId) {
		this.resultOrgId = resultOrgId;
	}
	public Long getResultAmount() {
		return resultAmount;
	}
	public void setResultAmount(Long resultAmount) {
		this.resultAmount = resultAmount;
	}
	public String getResultNotes() {
		return resultNotes;
	}
	public void setResultNotes(String resultNotes) {
		this.resultNotes = resultNotes;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getDutyOpIdName() {
		return dutyOpIdName;
	}
	public void setDutyOpIdName(String dutyOpIdName) {
		this.dutyOpIdName = dutyOpIdName;
	}
}
