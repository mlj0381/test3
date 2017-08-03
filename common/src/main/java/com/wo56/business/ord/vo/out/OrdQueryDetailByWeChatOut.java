package com.wo56.business.ord.vo.out;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrdQueryDetailByWeChatOut {
	private Long orderId;// 订单号
    private String cityName;// 到站城市
    private Date createDate;// 创建时间
    private String orderState;// 订单状态
    private String consigneeName;// 收货人名字
    private String consigneeBill;// 收货人手机号
    private String deliveryAddress;// 收货地址
    private String trackingNum;// 运单号
    private String carrierName;// 拉包工名称
    private String companyName;// 物流公司名称
    private String serviceTypeName;// 配送方式
    private String paymentTypeName;// 付款方式名称
    private Date planPickupTime;//预约提货时间
    private String carrierBill;//拉包工电话
    private String orderNo;
    
    
    
    
    public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCarrierBill() {
		return carrierBill;
	}
	public void setCarrierBill(String carrierBill) {
		this.carrierBill = carrierBill;
	}
	private List<Map<String, String>> pickList;
	public List<Map<String, String>> getPickList() {
		if(pickList==null){
			pickList=new ArrayList<>();
		}
		return pickList;
	}
	public void setPickList(List<Map<String, String>> pickList) {
		this.pickList = pickList;
	}
 
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneeBill() {
		return consigneeBill;
	}
	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	 
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public String getPaymentTypeName() {
		return paymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}
	public Date getPlanPickupTime() {
		return planPickupTime;
	}
	public void setPlanPickupTime(Date planPickupTime) {
		this.planPickupTime = planPickupTime;
	}
    

}
