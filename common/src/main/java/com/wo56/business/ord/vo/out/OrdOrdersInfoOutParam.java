package com.wo56.business.ord.vo.out;

import java.util.ArrayList;
import java.util.List;

import com.framework.core.inter.vo.OutParamVO;

public class OrdOrdersInfoOutParam extends OutParamVO {

	private String cityName;
	private String companyName;
	private String consigneeBill;
	private String consigneeName;
	private int isOrders;
	private int orderType;
	private long orderId;
	private String orderNo;
	private int orderState;
	private String orderStateName;
	private String paymentTypeName;
	private String serviceTypeName;
	private String trackingNum;
	private String workerBill;
	private String workerName;
    private List<OrdPickInfoOutParam> pickList;
    private String tenantPhone;
    private String ordersTime;
    private String tipString;
    private String freightString;
    private int isGradOrders;
    private String supportStaffPhone;
    
    
    
    
	public String getSupportStaffPhone() {
		return supportStaffPhone;
	}
	public void setSupportStaffPhone(String supportStaffPhone) {
		this.supportStaffPhone = supportStaffPhone;
	}
	public int getIsGradOrders() {
		return isGradOrders;
	}
	public void setIsGradOrders(int isGradOrders) {
		this.isGradOrders = isGradOrders;
	}
	public String getFreightString() {
		return freightString;
	}
	public void setFreightString(String freightString) {
		this.freightString = freightString;
	}
	public String getTipString() {
		return tipString;
	}
	public void setTipString(String tipString) {
		this.tipString = tipString;
	}
	public String getTenantPhone() {
		return tenantPhone;
	}
	public void setTenantPhone(String tenantPhone) {
		this.tenantPhone = tenantPhone;
	}
	public String getOrdersTime() {
		return ordersTime;
	}
	public void setOrdersTime(String ordersTime) {
		this.ordersTime = ordersTime;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
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
	public String getPaymentTypeName() {
		return paymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
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
	public List<OrdPickInfoOutParam> getPickList() {
		if(pickList==null){
			pickList=new ArrayList<OrdPickInfoOutParam>();
		}
		return pickList;
	}
	public void setPickList(List<OrdPickInfoOutParam> pickList) {
		this.pickList = pickList;
	}
    
    
    
}