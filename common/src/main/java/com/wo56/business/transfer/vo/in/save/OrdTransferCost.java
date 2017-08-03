package com.wo56.business.transfer.vo.in.save;

import java.io.Serializable;

import com.wo56.common.utils.OraganizationCacheDataUtil;

public class OrdTransferCost implements Serializable{

	private Integer payState;
	private double freight;
	private Long freightPayDot;
	private Long stevedoringPayDot;
	private double stevedoring;
	private Integer stevedoringPayState;
	private String departmentAddress;
	private String linkPhone;
	private String csPhones;
	
	
	
	public String getDepartmentAddress() {
		return departmentAddress;
	}
	public void setDepartmentAddress(String departmentAddress) {
		this.departmentAddress = departmentAddress;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getCsPhones() {
		return csPhones;
	}
	public void setCsPhones(String csPhones) {
		this.csPhones = csPhones;
	}
	
	//票数
	private Integer transferNum;
	//到付
	private double freightCollect;
	//贷款
	private double collectingMoney;
	//中转费
	private double transferValue;
	//重量
	private double actualWeight;
	//体积
	private double actualVolume;
	private String remark;
	//外发单号
	private Long outgoingTrackingNum; 
	public Long getOutgoingTrackingNum() {
		return outgoingTrackingNum;
	}
	public void setOutgoingTrackingNum(Long outgoingTrackingNum) {
		this.outgoingTrackingNum = outgoingTrackingNum;
	}
	//应收 应付金额
	private double gatherValue;
	//是否收取
	private Integer isGet;
	//是否支付
	private Integer isPay;
	private String stevedoringPayDotName;
	private String freightPayDotName;
	
	/**运输合同*/
	private String transportContract;
	
	public String getTransportContract() {
		return transportContract;
	}
	public void setTransportContract(String transportContract) {
		this.transportContract = transportContract;
	}
	public String getStevedoringPayDotName() {
		if(stevedoringPayDot != null)
			setStevedoringPayDotName(OraganizationCacheDataUtil.getOrgName(stevedoringPayDot));
		return stevedoringPayDotName;
	}
	public void setStevedoringPayDotName(String stevedoringPayDotName) {
		this.stevedoringPayDotName = stevedoringPayDotName;
	}
	public String getFreightPayDotName() {
		if(freightPayDot != null)
			setFreightPayDotName(OraganizationCacheDataUtil.getOrgName(freightPayDot));
		return freightPayDotName;
	}
	public void setFreightPayDotName(String freightPayDotName) {
		this.freightPayDotName = freightPayDotName;
	}
	public double getGatherValue() {
		return gatherValue;
	}
	public void setGatherValue(double gatherValue) {
		this.gatherValue = gatherValue;
	}
	public Integer getIsGet() {
		return isGet;
	}
	public void setIsGet(Integer isGet) {
		this.isGet = isGet;
	}
	public Integer getIsPay() {
		return isPay;
	}
	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}
	public double getActualVolume() {
		return actualVolume;
	}
	public void setActualVolume(double actualVolume) {
		this.actualVolume = actualVolume;
	}
	public Integer getTransferNum() {
		return transferNum;
	}
	public void setTransferNum(Integer transferNum) {
		this.transferNum = transferNum;
	}
	public double getFreightCollect() {
		return freightCollect;
	}
	public void setFreightCollect(double freightCollect) {
		this.freightCollect = freightCollect;
	}
	public double getCollectingMoney() {
		return collectingMoney;
	}
	public void setCollectingMoney(double collectingMoney) {
		this.collectingMoney = collectingMoney;
	}
	public double getTransferValue() {
		return transferValue;
	}
	public void setTransferValue(double transferValue) {
		this.transferValue = transferValue;
	}
	public Integer getPayState() {
		return payState;
	}
	public void setPayState(Integer payState) {
		this.payState = payState;
	}
	public double getFreight() {
		return freight;
	}
	public void setFreight(double freight) {
		this.freight = freight;
	}
	public Long getFreightPayDot() {
		return freightPayDot;
	}
	public void setFreightPayDot(Long freightPayDot) {
		this.freightPayDot = freightPayDot;
	}
	public Long getStevedoringPayDot() {
		return stevedoringPayDot;
	}
	public void setStevedoringPayDot(Long stevedoringPayDot) {
		this.stevedoringPayDot = stevedoringPayDot;
	}
	public double getStevedoring() {
		return stevedoring;
	}
	public void setStevedoring(double stevedoring) {
		this.stevedoring = stevedoring;
	}
	public Integer getStevedoringPayState() {
		return stevedoringPayState;
	}
	public void setStevedoringPayState(Integer stevedoringPayState) {
		this.stevedoringPayState = stevedoringPayState;
	}
	
	
}
