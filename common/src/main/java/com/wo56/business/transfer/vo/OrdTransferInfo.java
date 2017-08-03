package com.wo56.business.transfer.vo;

import java.sql.Date;

import com.framework.core.base.POJO;

/**
 * OrdTransferInfo entity. @author MyEclipse Persistence Tools
 */

public class OrdTransferInfo extends POJO implements java.io.Serializable {

	// Fields

	private Long batchNum;
	private Long sourceOrgId;
	private Long descOrgId;
	private Date loadTime;
	private Date departTime;
	private Date arrivalTime;
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
	private String remarks;
	private Long transferValue;
	private String transportContract;
	private Long collectingMoney;
	
	public Long getCollectingMoney() {
		return collectingMoney;
	}

	public void setCollectingMoney(Long collectingMoney) {
		this.collectingMoney = collectingMoney;
	}

	public String getTransportContract() {
		return transportContract;
	}

	public void setTransportContract(String transportContract) {
		this.transportContract = transportContract;
	}

	public Long getTransferValue() {
		return transferValue;
	}

	public void setTransferValue(Long transferValue) {
		this.transferValue = transferValue;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	// Constructors

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

	/** default constructor */
	public OrdTransferInfo() {
	}

	/** minimal constructor */
	public OrdTransferInfo(Long sourceOrgId, Long descOrgId,
			String plateNumber, String driverName, String driverBill,
			Integer state, Integer orderNum, Double volume, Double weight,
			Long tenantId) {
		this.sourceOrgId = sourceOrgId;
		this.descOrgId = descOrgId;
		this.plateNumber = plateNumber;
		this.driverName = driverName;
		this.driverBill = driverBill;
		this.state = state;
		this.orderNum = orderNum;
		this.volume = volume;
		this.weight = weight;
		this.tenantId = tenantId;
	}

	/** full constructor */
	public OrdTransferInfo(Long sourceOrgId, Long descOrgId,
			Date loadTime, Date departTime, Date arrivalTime,
			String plateNumber, Long driverId, String driverName,
			String driverBill, String loader, Long loaderId, Integer state,
			Long vehicleId, Integer orderNum, Double volume, Double weight,
			Long freight, Long tenantId, Integer payState, Long freightPayDot,
			Long stevedoring, Long stevedoringPayDot,
			Integer stevedoringPayState, Long freightCollect,
			Long receiptPayment) {
		this.sourceOrgId = sourceOrgId;
		this.descOrgId = descOrgId;
		this.loadTime = loadTime;
		this.departTime = departTime;
		this.arrivalTime = arrivalTime;
		this.plateNumber = plateNumber;
		this.driverId = driverId;
		this.driverName = driverName;
		this.driverBill = driverBill;
		this.loader = loader;
		this.loaderId = loaderId;
		this.state = state;
		this.vehicleId = vehicleId;
		this.orderNum = orderNum;
		this.volume = volume;
		this.weight = weight;
		this.freight = freight;
		this.tenantId = tenantId;
		this.payState = payState;
		this.freightPayDot = freightPayDot;
		this.stevedoring = stevedoring;
		this.stevedoringPayDot = stevedoringPayDot;
		this.stevedoringPayState = stevedoringPayState;
		this.freightCollect = freightCollect;
		this.receiptPayment = receiptPayment;
	}

	// Property accessors

	public Long getBatchNum() {
		return this.batchNum;
	}

	public void setBatchNum(Long batchNum) {
		this.batchNum = batchNum;
	}

	public Long getSourceOrgId() {
		return this.sourceOrgId;
	}

	public void setSourceOrgId(Long sourceOrgId) {
		this.sourceOrgId = sourceOrgId;
	}

	public Long getDescOrgId() {
		return this.descOrgId;
	}

	public void setDescOrgId(Long descOrgId) {
		this.descOrgId = descOrgId;
	}

	public Date getLoadTime() {
		return this.loadTime;
	}

	public void setLoadTime(Date loadTime) {
		this.loadTime = loadTime;
	}

	public Date getDepartTime() {
		return this.departTime;
	}

	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

	public Date getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getPlateNumber() {
		return this.plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public Long getDriverId() {
		return this.driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return this.driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverBill() {
		return this.driverBill;
	}

	public void setDriverBill(String driverBill) {
		this.driverBill = driverBill;
	}

	public String getLoader() {
		return this.loader;
	}

	public void setLoader(String loader) {
		this.loader = loader;
	}

	public Long getLoaderId() {
		return this.loaderId;
	}

	public void setLoaderId(Long loaderId) {
		this.loaderId = loaderId;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Double getVolume() {
		return this.volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Long getFreight() {
		return this.freight;
	}

	public void setFreight(Long freight) {
		this.freight = freight;
	}

	public Long getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getPayState() {
		return this.payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public Long getFreightPayDot() {
		return this.freightPayDot;
	}

	public void setFreightPayDot(Long freightPayDot) {
		this.freightPayDot = freightPayDot;
	}

	public Long getStevedoring() {
		return this.stevedoring;
	}

	public void setStevedoring(Long stevedoring) {
		this.stevedoring = stevedoring;
	}

	public Long getStevedoringPayDot() {
		return this.stevedoringPayDot;
	}

	public void setStevedoringPayDot(Long stevedoringPayDot) {
		this.stevedoringPayDot = stevedoringPayDot;
	}

	public Integer getStevedoringPayState() {
		return this.stevedoringPayState;
	}

	public void setStevedoringPayState(Integer stevedoringPayState) {
		this.stevedoringPayState = stevedoringPayState;
	}

	public Long getFreightCollect() {
		return this.freightCollect;
	}

	public void setFreightCollect(Long freightCollect) {
		this.freightCollect = freightCollect;
	}

	public Long getReceiptPayment() {
		return this.receiptPayment;
	}

	public void setReceiptPayment(Long receiptPayment) {
		this.receiptPayment = receiptPayment;
	}

}