package com.wo56.business.route.vo.in;

import java.util.Date;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrgFeeConfigSaveParamIn extends PageInParamVO implements IParamIn {

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.BASE.ROUTE_FEE_CONFIG_SAVE;
	}

	private long ofcId;
	private long orgId;
	private String orgIdName;
	private long feeId;
	private int countType;
	private long tenantId;
	private String tenantIdName;
	private long opId;
	private Date createTime;
	private String countTypeName;
	private String feeIdName;
	private long obcId;
	private int collectType;
	private long ladderId;
	private String topLimit;
	private String lowerLimit;
	private long fee;
	private String formula;
	private Long salesmanId;
	private int formulaType;
	private String formulaTypeName;
	private String areaName;
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public long getOfcId() {
		return ofcId;
	}
	public void setOfcId(long ofcId) {
		this.ofcId = ofcId;
	}
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	public String getOrgIdName() {
		return orgIdName;
	}
	public void setOrgIdName(String orgIdName) {
		this.orgIdName = orgIdName;
	}
	public long getFeeId() {
		return feeId;
	}
	public void setFeeId(long feeId) {
		this.feeId = feeId;
	}
	public int getCountType() {
		return countType;
	}
	public void setCountType(int countType) {
		this.countType = countType;
	}
	public long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
	public String getTenantIdName() {
		return tenantIdName;
	}
	public void setTenantIdName(String tenantIdName) {
		this.tenantIdName = tenantIdName;
	}
	public long getOpId() {
		return opId;
	}
	public void setOpId(long opId) {
		this.opId = opId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCountTypeName() {
		return countTypeName;
	}
	public void setCountTypeName(String countTypeName) {
		this.countTypeName = countTypeName;
	}
	public String getFeeIdName() {
		return feeIdName;
	}
	public void setFeeIdName(String feeIdName) {
		this.feeIdName = feeIdName;
	}
	public long getObcId() {
		return obcId;
	}
	public void setObcId(long obcId) {
		this.obcId = obcId;
	}
	public int getCollectType() {
		return collectType;
	}
	public void setCollectType(int collectType) {
		this.collectType = collectType;
	}
	public long getLadderId() {
		return ladderId;
	}
	public void setLadderId(long ladderId) {
		this.ladderId = ladderId;
	}
	public String getTopLimit() {
		return topLimit;
	}
	public void setTopLimit(String topLimit) {
		this.topLimit = topLimit;
	}
	public String getLowerLimit() {
		return lowerLimit;
	}
	public void setLowerLimit(String lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	public long getFee() {
		return fee;
	}
	public void setFee(long fee) {
		this.fee = fee;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public Long getSalesmanId() {
		return salesmanId;
	}
	public void setSalesmanId(Long salesmanId) {
		this.salesmanId = salesmanId;
	}
	public int getFormulaType() {
		return formulaType;
	}
	public void setFormulaType(int formulaType) {
		this.formulaType = formulaType;
	}
	public String getFormulaTypeName() {
		return formulaTypeName;
	}
	public void setFormulaTypeName(String formulaTypeName) {
		this.formulaTypeName = formulaTypeName;
	}
	}
