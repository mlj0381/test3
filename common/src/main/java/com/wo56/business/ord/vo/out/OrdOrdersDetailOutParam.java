package com.wo56.business.ord.vo.out;

import java.util.ArrayList;
import java.util.List;

import com.framework.core.inter.vo.OutParamVO;

public class OrdOrdersDetailOutParam extends OutParamVO {

	private String cityName;
	private String companyName;
	private Long companyId;
	private String consigneeBill;
	private String consigneeId;
	private String consigneeName;
	private int isOrders;
	private long orderId;
	private String orderNo;
	private int orderState;
	private String orderStateName;
	private String paymentTypeName;
	private String serviceTypeName;
	private String trackingNum;
	private String address;
	private String workerBill;
	private String workerName;
	private Integer paymentType;
	private Integer serviceType;
	private Long provinceId;
	private Long cityId;
	private Long countyId;
	private Long streetId;
	private String provinceName;
	private String countyName;
	private String streetName;
	
	private int orderType;
	private String orderTimeString;
	private String tenantPhone;
	private String tipString;
	private String freightString;
	private String latitude;
	private String longitude;
	private String isGradOrders;
	//新增包装费
    private String pickingFee;
    
    private String supportStaffPhone;
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSupportStaffPhone() {
		return supportStaffPhone;
	}
	public void setSupportStaffPhone(String supportStaffPhone) {
		this.supportStaffPhone = supportStaffPhone;
	}
	public String getPickingFee() {
		return pickingFee;
	}
	public void setPickingFee(String pickingFee) {
	    this.pickingFee = pickingFee;
	}
	
	
    public String getIsGradOrders() {
		return isGradOrders;
	}
	public void setIsGradOrders(String isGradOrders) {
		this.isGradOrders = isGradOrders;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getTenantPhone() {
		return tenantPhone;
	}
	public void setTenantPhone(String tenantPhone) {
		this.tenantPhone = tenantPhone;
	}
	public String getTipString() {
		return tipString;
	}
	public void setTipString(String tipString) {
		this.tipString = tipString;
	}
	public String getFreightString() {
		return freightString;
	}
	public void setFreightString(String freightString) {
		this.freightString = freightString;
	}
	public String getOrderTimeString() {
		return orderTimeString;
	}
	public void setOrderTimeString(String orderTimeString) {
		this.orderTimeString = orderTimeString;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getConsigneeId() {
		return consigneeId;
	}
	public void setConsigneeId(String consigneeId) {
		this.consigneeId = consigneeId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public Long getCountyId() {
		return countyId;
	}
	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
	public Long getStreetId() {
		return streetId;
	}
	public void setStreetId(Long streetId) {
		this.streetId = streetId;
	}

	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getPaymentTypeName() {
		return paymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public Integer getServiceType() {
		return serviceType;
	}
	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private List<OrdPickDetailOutParam> pickList;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getConsigneeBill() {
		return consigneeBill;
	}
	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public int getIsOrders() {
		return isOrders;
	}
	public void setIsOrders(int isOrders) {
		this.isOrders = isOrders;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getOrderState() {
		return orderState;
	}
	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	public String getOrderStateName() {
		return orderStateName;
	}
	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}
	
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getWorkerBill() {
		return workerBill;
	}
	public void setWorkerBill(String workerBill) {
		this.workerBill = workerBill;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public List<OrdPickDetailOutParam> getPickList() {
		if(pickList==null){
			pickList=new ArrayList<OrdPickDetailOutParam>();
		}
		return pickList;
	}
	public void setPickList(List<OrdPickDetailOutParam> pickList) {
		this.pickList = pickList;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
    
    
    
}