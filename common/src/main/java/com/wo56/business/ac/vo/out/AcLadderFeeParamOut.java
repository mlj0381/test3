package com.wo56.business.ac.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class AcLadderFeeParamOut  extends BaseOutParamVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -409433298923510029L;
	/**起始网点id (1)**/
	private long startOrgId;
	/**到达网点id (1)**/
	private long endOrgId;
	/**开单人租户id （1）*/
	//private long tenantId;
    /**重量*/
	private double weight;
    /**体积*/
	private double volume;
	/**费用id*/
	private long feeId;
	/***费用 (单位：分)*/
	private long costAmount;
	/***应收网点ID*/
	private long receiOrgId;
	/***线路费类型id 1 外部 2 内部*/
	private long lfType;
	/***线路费类型name*/
	private String lfTypeName;
	private String startOrgName;
	private String endOrgName;
	private String receiOrgName;
	
	private Integer sequence;
	
	
	public long getReceiOrgId() {
		return receiOrgId;
	}
	public void setReceiOrgId(long receiOrgId) {
		this.receiOrgId = receiOrgId;
	}
	public long getLfType() {
		return lfType;
	}
	public void setLfType(long lfType) {
		this.lfType = lfType;
	}
	public String getLfTypeName() {
		return lfTypeName;
	}
	public void setLfTypeName(String lfTypeName) {
		this.lfTypeName = lfTypeName;
	}
	public long getStartOrgId() {
		return startOrgId;
	}
	public void setStartOrgId(long startOrgId) {
		this.startOrgId = startOrgId;
	}
	public long getEndOrgId() {
		return endOrgId;
	}
	public void setEndOrgId(long endOrgId) {
		this.endOrgId = endOrgId;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public long getFeeId() {
		return feeId;
	}
	public void setFeeId(long feeId) {
		this.feeId = feeId;
	}
	public long getCostAmount() {
		return costAmount;
	}
	public void setCostAmount(long costAmount) {
		this.costAmount = costAmount;
	}
	public String getStartOrgName() {
		if(startOrgId>0){
		    setStartOrgName(OraganizationCacheDataUtil.getOrgName(startOrgId));
		}
		return startOrgName;
	}
	public void setStartOrgName(String startOrgName) {
		this.startOrgName = startOrgName;
	}
	public String getEndOrgName() {
		if(endOrgId>0){
			setEndOrgName(OraganizationCacheDataUtil.getOrgName(endOrgId));
		}
		return endOrgName;
	}
	public void setEndOrgName(String endOrgName) {
		this.endOrgName = endOrgName;
	}
	public String getReceiOrgName() {
		if(receiOrgId>0){
			setReceiOrgName(OraganizationCacheDataUtil.getOrgName(receiOrgId));
		}
		return receiOrgName;
	}
	public void setReceiOrgName(String receiOrgName) {
		this.receiOrgName = receiOrgName;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	
	
}
