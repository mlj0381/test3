package com.wo56.business.ord.vo.out;

import com.wo56.common.utils.CommonUtil;


public class OrdDepartInfoListOut {


	private String departTime; //发车时间
	private String plateNumber;//车牌号
	private String driverName;//司机名称
	private String driverBill;//司机手机号码
	private String orderNum;//订单数量
	private String freight;//运费
	private String departmentAddress;//到达网点发车
	private String orgPrincipal;//到达网点联系人
	private String orgPrincipalPhone;//到达网点联系人手机
	private String vehicleTypeName;//车型
	private String length;//车长
	private String goodsArriveDate;//预计到达时间
	private String loadOpName;//配载人
	private String batchNum;//批次号
	private String transportContract;//运输合同
	
	public String getTransportContract() {
		return transportContract;
	}
	public void setTransportContract(String transportContract) {
		this.transportContract = transportContract;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getLoadOpName() {
		return loadOpName;
	}
	public void setLoadOpName(String loadOpName) {
		this.loadOpName = loadOpName;
	}
	public String getDepartTime() {
		return departTime;
	}
	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
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
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getFreight() {
		setFreight( CommonUtil.getDoubleFixedNew2(freight).equals("0.00") ? "": "￥"+ CommonUtil.getDoubleFixedNew2(freight) );
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
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
	public String getVehicleTypeName() {
		return vehicleTypeName;
	}
	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getGoodsArriveDate() {
		return goodsArriveDate;
	}
	public void setGoodsArriveDate(String goodsArriveDate) {
		this.goodsArriveDate = goodsArriveDate;
	}
	
	
}
