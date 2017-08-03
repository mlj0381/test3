package com.wo56.business.transfer.vo.out;

import org.apache.commons.lang.StringUtils;

import com.framework.core.util.DateUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class TransferQueryOut {

	
	private Long batchNum;
	private Long sourceOrgId;
	private Long descOrgId;
	private String loadTime;
	private String departTime;
	private String arrivalTime;
	private String plateNumber;
	private Long driverId;
	private String driverName;
	private String driverBill;
	private String loader;
	private Long loaderId;
	private Integer state;
	private Long vehicleId;
	private Integer orderNum;
	private Double volume;
	private Double weight;
	private Long freight;
	private Long tenantId;
	private Integer payState;
	private Long freightPayDot;
	private Long stevedoring;
	private Long stevedoringPayDot;
	private Integer stevedoringPayState;
	private Long freightCollect;
	private Long receiptPayment;
	private Long totalPayMoney;
	private Integer isPay;
	private Long totalGetMoney;
	private Integer isGet;
	private String createDate;
	private String sourceOrgIdName;
	private String descOrgIdName;
	private String transportContract;
	
	
	
	public String getTransportContract() {
		return transportContract;
	}

	public void setTransportContract(String transportContract) {
		this.transportContract = transportContract;
	}

	public String getSourceOrgIdName() {
		if(sourceOrgId>0){
			setSourceOrgIdName(OraganizationCacheDataUtil.getOrgName(sourceOrgId));
		}
		return sourceOrgIdName;
	}

	public void setSourceOrgIdName(String sourceOrgIdName) {
		this.sourceOrgIdName = sourceOrgIdName;
	}

	public String getDescOrgIdName() {
		if(descOrgId>0){
			setDescOrgIdName(OraganizationCacheDataUtil.getOrgName(descOrgId));
		}
		return descOrgIdName;
	}

	public void setDescOrgIdName(String descOrgIdName) {
		this.descOrgIdName = descOrgIdName;
	}
	
	public String getCreateDate() {
		if(StringUtils.isNotBlank(createDate)){
			setCreateDate(DateUtil.formatDate(DateUtil.parseDate(createDate), DateUtil.DATETIME12_FORMAT));
		}
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Long getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(Long batchNum) {
		this.batchNum = batchNum;
	}
	public Long getSourceOrgId() {
		return sourceOrgId;
	}
	public void setSourceOrgId(Long sourceOrgId) {
		this.sourceOrgId = sourceOrgId;
	}
	public Long getDescOrgId() {
		return descOrgId;
	}
	public void setDescOrgId(Long descOrgId) {
		this.descOrgId = descOrgId;
	}
	public String getLoadTime() {
		return loadTime;
	}
	public void setLoadTime(String loadTime) {
		this.loadTime = loadTime;
	}
	public String getDepartTime() {
		return departTime;
	}
	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverBill() {
		return driverBill;
	}
	public void setDriverBill(String driverBill) {
		this.driverBill = driverBill;
	}
	public String getLoader() {
		return loader;
	}
	public void setLoader(String loader) {
		this.loader = loader;
	}
	public Long getLoaderId() {
		return loaderId;
	}
	public void setLoaderId(Long loaderId) {
		this.loaderId = loaderId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Long getFreight() {
		return freight;
	}
	public void setFreight(Long freight) {
		this.freight = freight;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public Integer getPayState() {
		return payState;
	}
	public void setPayState(Integer payState) {
		this.payState = payState;
	}
	public Long getFreightPayDot() {
		return freightPayDot;
	}
	public void setFreightPayDot(Long freightPayDot) {
		this.freightPayDot = freightPayDot;
	}
	public Long getStevedoring() {
		return stevedoring;
	}
	public void setStevedoring(Long stevedoring) {
		this.stevedoring = stevedoring;
	}
	public Long getStevedoringPayDot() {
		return stevedoringPayDot;
	}
	public void setStevedoringPayDot(Long stevedoringPayDot) {
		this.stevedoringPayDot = stevedoringPayDot;
	}
	public Integer getStevedoringPayState() {
		return stevedoringPayState;
	}
	public void setStevedoringPayState(Integer stevedoringPayState) {
		this.stevedoringPayState = stevedoringPayState;
	}
	public Long getFreightCollect() {
		return freightCollect;
	}
	public void setFreightCollect(Long freightCollect) {
		this.freightCollect = freightCollect;
	}
	public Long getReceiptPayment() {
		return receiptPayment;
	}
	public void setReceiptPayment(Long receiptPayment) {
		this.receiptPayment = receiptPayment;
	}
	public Long getTotalPayMoney() {
		return totalPayMoney;
	}
	public void setTotalPayMoney(Long totalPayMoney) {
		this.totalPayMoney = totalPayMoney;
	}
	public Integer getIsPay() {
		return isPay;
	}
	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}
	public Long getTotalGetMoney() {
		return totalGetMoney;
	}
	public void setTotalGetMoney(Long totalGetMoney) {
		this.totalGetMoney = totalGetMoney;
	}
	public Integer getIsGet() {
		return isGet;
	}
	public void setIsGet(Integer isGet) {
		this.isGet = isGet;
	}
	
}
