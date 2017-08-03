package com.wo56.business.ord.vo.out;

import java.util.List;
import java.util.Map;

public class OrdDepartQueryOut {
	private String batchNum;//批次号
	private String batchNumAlias;//批次号别名
	private String plateNumber;//车牌号
	private String transportContract;//运输合同
	private String driverName;//司机名称
	private String driverBill;//司机电话
	private String vehicleType;//车辆类型
	private String length;//车长
	private String departmentAddress;//发往地址
	private String orgPrincipal;//负责人
	private String orgPrincipalPhone;//负责人电话
	private String departTime;//发车时间
	private String loadOpName;//配载人
	private String goodsArriveDate;//预计到达时间
	private String totalFee;//总费用
	private String orderNum;//总票数
	private String ordFreight;//运费
	private String installCosts;//配安费
	private String cashPayment;//现付
	private String transPayment;//转账
	private String monthlyPayment;//月结
	private String freightCollect;//到付
	private String receiptPayment;//回单付
	private String collectingMoney;//代收货款
	private String discount;//回扣
	private String handingCosts;//装卸费
	private String vFreight;//司机运费
	private String grossCharge;//总支出
	private List<Map<String,String>> totalFeeItems;
	private List<Map<String,String>> grossChargeItems;
	private String sourceOrgId;
	private String regionId;
	
	
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public List<Map<String, String>> getTotalFeeItems() {
		return totalFeeItems;
	}
	public void setTotalFeeItems(List<Map<String, String>> totalFeeItems) {
		this.totalFeeItems = totalFeeItems;
	}
	public List<Map<String, String>> getGrossChargeItems() {
		return grossChargeItems;
	}
	public void setGrossChargeItems(List<Map<String, String>> grossChargeItems) {
		this.grossChargeItems = grossChargeItems;
	}
	public String getSourceOrgId() {
		return sourceOrgId;
	}
	public void setSourceOrgId(String sourceOrgId) {
		this.sourceOrgId = sourceOrgId;
	}
	
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getBatchNumAlias() {
		return batchNumAlias;
	}
	public void setBatchNumAlias(String batchNumAlias) {
		this.batchNumAlias = batchNumAlias;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getTransportContract() {
		return transportContract;
	}
	public void setTransportContract(String transportContract) {
		this.transportContract = transportContract;
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
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getDepartmentAddress() {
		return departmentAddress;
	}
	public void setDepartmentAddress(String departmentAddress) {
		this.departmentAddress = departmentAddress;
	}
	public String getOrgPrincipal() {
		return orgPrincipal;
	}
	public void setOrgPrincipal(String orgPrincipal) {
		this.orgPrincipal = orgPrincipal;
	}
	public String getOrgPrincipalPhone() {
		return orgPrincipalPhone;
	}
	public void setOrgPrincipalPhone(String orgPrincipalPhone) {
		this.orgPrincipalPhone = orgPrincipalPhone;
	}
	public String getDepartTime() {
		return departTime;
	}
	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}
	public String getLoadOpName() {
		return loadOpName;
	}
	public void setLoadOpName(String loadOpName) {
		this.loadOpName = loadOpName;
	}
	public String getGoodsArriveDate() {
		return goodsArriveDate;
	}
	public void setGoodsArriveDate(String goodsArriveDate) {
		this.goodsArriveDate = goodsArriveDate;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrdFreight() {
		return ordFreight;
	}
	public void setOrdFreight(String ordFreight) {
		this.ordFreight = ordFreight;
	}
	public String getInstallCosts() {
		return installCosts;
	}
	public void setInstallCosts(String installCosts) {
		this.installCosts = installCosts;
	}
	public String getCashPayment() {
		return cashPayment;
	}
	public void setCashPayment(String cashPayment) {
		this.cashPayment = cashPayment;
	}
	public String getTransPayment() {
		return transPayment;
	}
	public void setTransPayment(String transPayment) {
		this.transPayment = transPayment;
	}
	public String getMonthlyPayment() {
		return monthlyPayment;
	}
	public void setMonthlyPayment(String monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}
	public String getFreightCollect() {
		return freightCollect;
	}
	public void setFreightCollect(String freightCollect) {
		this.freightCollect = freightCollect;
	}
	public String getReceiptPayment() {
		return receiptPayment;
	}
	public void setReceiptPayment(String receiptPayment) {
		this.receiptPayment = receiptPayment;
	}
	public String getCollectingMoney() {
		return collectingMoney;
	}
	public void setCollectingMoney(String collectingMoney) {
		this.collectingMoney = collectingMoney;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getHandingCosts() {
		return handingCosts;
	}
	public void setHandingCosts(String handingCosts) {
		this.handingCosts = handingCosts;
	}
	public String getvFreight() {
		return vFreight;
	}
	public void setvFreight(String vFreight) {
		this.vFreight = vFreight;
	}
	public String getGrossCharge() {
		return grossCharge;
	}
	public void setGrossCharge(String grossCharge) {
		this.grossCharge = grossCharge;
	}
	
}
