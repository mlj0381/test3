package com.wo56.business.ac.vo.out;

import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.framework.core.util.SysStaticDataUtil;

public class AcOrgFeeAllParamOut  extends BaseOutParamVO {


	private long ofcId;
	private long orgId;
	private String orgIdName;
	private long feeId;
	private int countType;
	//private long tenantId;
	private String tenantIdName;
	private long opId;
	private String countTypeName;
	private String feeIdName;
	private long ladderId;
	private String topLimit;
	private String lowerLimit;
	private long fee;
	private String formula;
	private Long salesmanId;
	private int formulaType;
	private String formulaTypeName;
	private long obcId;
	private int collectType;
	private String collectTypeName;
	private int isStandardLine;
	private long rfcId;
	private long routeId;
	private long startOrgid;
	private String startOrgidName;
	private String endOrgidName;
	private long endOrgid;
	private long receiOrgid;
	private String receiOrgidName;
	private int lfType;
	private int carriageMode;
	private String carriageModeName;
	private Long minPrice;
	private long maxPrice;
	private String remarks;
	private Date createTime;
    private String lfTypeName;
    
	public int getIsStandardLine() {
		return isStandardLine;
	}
	public void setIsStandardLine(int isStandardLine) {
		this.isStandardLine = isStandardLine;
	}
	public long getRfcId() {
		return rfcId;
	}
	public void setRfcId(long rfcId) {
		this.rfcId = rfcId;
	}
	public long getRouteId() {
		return routeId;
	}
	public void setRouteId(long routeId) {
		this.routeId = routeId;
	}
	public long getStartOrgid() {
		return startOrgid;
	}
	public void setStartOrgid(long startOrgid) {
		this.startOrgid = startOrgid;
	}
	public String getStartOrgidName() {
		return startOrgidName;
	}
	public void setStartOrgidName(String startOrgidName) {
		this.startOrgidName = startOrgidName;
	}
	public String getEndOrgidName() {
		return endOrgidName;
	}
	public void setEndOrgidName(String endOrgidName) {
		this.endOrgidName = endOrgidName;
	}
	public long getEndOrgid() {
		return endOrgid;
	}
	public void setEndOrgid(long endOrgid) {
		this.endOrgid = endOrgid;
	}
	public long getReceiOrgid() {
		return receiOrgid;
	}
	public void setReceiOrgid(long receiOrgid) {
		this.receiOrgid = receiOrgid;
	}
	public String getReceiOrgidName() {
		return receiOrgidName;
	}
	public void setReceiOrgidName(String receiOrgidName) {
		this.receiOrgidName = receiOrgidName;
	}
	public int getLfType() {
		return lfType;
	}
	public void setLfType(int lfType) {
		this.lfType = lfType;
	}
	public int getCarriageMode() {
		return carriageMode;
	}
	public void setCarriageMode(int carriageMode) {
		this.carriageMode = carriageMode;
	}
	public String getCarriageModeName() {
		return carriageModeName;
	}
	public void setCarriageModeName(String carriageModeName) {
		this.carriageModeName = carriageModeName;
	}
	public Long getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Long minPrice) {
		this.minPrice = minPrice;
	}
	public long getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(long maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getLfTypeName() {
		return lfTypeName;
	}
	public void setLfTypeName(String lfTypeName) {
		this.lfTypeName = lfTypeName;
	}
	public String getCollectTypeName() {
		if(collectType>-1){
			setCollectTypeName(SysStaticDataUtil.getSysStaticData("COLLECT_TYPE", countType+"").getCodeName());
		}
		return collectTypeName;
	}
	public void setCollectTypeName(String collectTypeName) {
		this.collectTypeName = collectTypeName;
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
	public Long getTenantId() {
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
	public Long getOpId() {
		return opId;
	}
	public void setOpId(long opId) {
		this.opId = opId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
