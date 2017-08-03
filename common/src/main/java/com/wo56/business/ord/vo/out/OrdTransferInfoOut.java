package com.wo56.business.ord.vo.out;


public class OrdTransferInfoOut{

	
	private static final long serialVersionUID = 1200002030408414345L;
       
	  

	  private long idNo;
	  private long orderId; //订单号
	  private Long trackingNum; //运单号
	  private String orgName;
	  private String orgPrincipalPhone;
	  private String carryLinkPhone;
	  private String orgPrincipal;
	  private String provinceName;
	  private String regionName;
	  private String countyName;
	  private String departmentAddress;
	  private String address;//收货地址详细地址
	  private Integer count;//货物总数量
	  private String remarks;//备注
	  private String consignorName;//发货人
	  private String consignorBill;//发货人联系手机
	  private String consigneeName;//收货人 
	  private String consigneeBill;//收货人手机
	  private String createDate;//创建时间
	  private String products;//货品名称
	  private String goodsNumber;//货号
	  private String weight; //总重量
	  private String volume;//总体积
	  private String totalFee;//费用合计
	  private String deliveryTypeName;//交接方式
	  private String serviceTypeName;//家装服务
	  private String seeOrderStateName;//运单展示状态
	  private String postingStsName;//过账
	  private Double totalFeeDouble;//费用合计（元）
	  private String destProvinceName;// 目的省
	  private String destCityName;// 目的市
	  private String destCountyName;// 目的区
	  private String destStreetName;// 目的区
	  private String orgIdName; //开单网点
	  private String currentOrgIdName; //开单网点
	  private String freight;
	  private String freightCollect;
	  private String collectingMoney;
	  private String cashPayment;
	  private String monthlyPayment;
	  private String transPayment;
	  private String receiptPayment;
	  private String discount;
	  private long orgId;
	  private String inputOrgName;
	  
	  private String paymentTypeName;// 支付方式1
	  private String paymentType2Name;// 支付方式2
	  private String cashMoney;//金额1
	  private String cashMoney2;//金额2
	  
	  
	public String getPaymentTypeName() {
		return paymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}
	public String getPaymentType2Name() {
		return paymentType2Name;
	}
	public void setPaymentType2Name(String paymentType2Name) {
		this.paymentType2Name = paymentType2Name;
	}
	public String getCashMoney() {
		return cashMoney;
	}
	public void setCashMoney(String cashMoney) {
		this.cashMoney = cashMoney;
	}
	public String getCashMoney2() {
		return cashMoney2;
	}
	public void setCashMoney2(String cashMoney2) {
		this.cashMoney2 = cashMoney2;
	}
	public String getInputOrgName() {
		return inputOrgName;
	}
	public void setInputOrgName(String inputOrgName) {
		this.inputOrgName = inputOrgName;
	}
	public String getCashPayment() {
		return cashPayment;
	}
	public void setCashPayment(String cashPayment) {
		this.cashPayment = cashPayment;
	}
	public String getCollectingMoney() {
		return collectingMoney;
	}
	public void setCollectingMoney(String collectingMoney) {
		this.collectingMoney = collectingMoney;
	}
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	public long getIdNo() {
		return idNo;
	}
	public void setIdNo(long idNo) {
		this.idNo = idNo;
	}
	public String getDestStreetName() {
		return destStreetName;
	}
	public void setDestStreetName(String destStreetName) {
		this.destStreetName = destStreetName;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgPrincipalPhone() {
		return orgPrincipalPhone;
	}
	public void setOrgPrincipalPhone(String orgPrincipalPhone) {
		this.orgPrincipalPhone = orgPrincipalPhone;
	}
	public String getCarryLinkPhone() {
		return carryLinkPhone;
	}
	public void setCarryLinkPhone(String carryLinkPhone) {
		this.carryLinkPhone = carryLinkPhone;
	}
	public String getOrgPrincipal() {
		return orgPrincipal;
	}
	public void setOrgPrincipal(String orgPrincipal) {
		this.orgPrincipal = orgPrincipal;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getDepartmentAddress() {
		return departmentAddress;
	}
	public void setDepartmentAddress(String departmentAddress) {
		this.departmentAddress = departmentAddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getConsignorName() {
		return consignorName;
	}
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}
	public String getConsignorBill() {
		return consignorBill;
	}
	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public String getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}
	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public String getSeeOrderStateName() {
		return seeOrderStateName;
	}
	public void setSeeOrderStateName(String seeOrderStateName) {
		this.seeOrderStateName = seeOrderStateName;
	}
	public String getPostingStsName() {
		return postingStsName;
	}
	public void setPostingStsName(String postingStsName) {
		this.postingStsName = postingStsName;
	}
	public Double getTotalFeeDouble() {
		return totalFeeDouble;
	}
	public void setTotalFeeDouble(Double totalFeeDouble) {
		this.totalFeeDouble = totalFeeDouble;
	}
	public String getDestProvinceName() {
		return destProvinceName;
	}
	public void setDestProvinceName(String destProvinceName) {
		this.destProvinceName = destProvinceName;
	}
	public String getDestCityName() {
		return destCityName;
	}
	public void setDestCityName(String destCityName) {
		this.destCityName = destCityName;
	}
	public String getDestCountyName() {
		return destCountyName;
	}
	public void setDestCountyName(String destCountyName) {
		this.destCountyName = destCountyName;
	}
	public String getOrgIdName() {
		return orgIdName;
	}
	public void setOrgIdName(String orgIdName) {
		this.orgIdName = orgIdName;
	}
	public String getCurrentOrgIdName() {
		return currentOrgIdName;
	}
	public void setCurrentOrgIdName(String currentOrgIdName) {
		this.currentOrgIdName = currentOrgIdName;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getFreightCollect() {
		return freightCollect;
	}
	public void setFreightCollect(String freightCollect) {
		this.freightCollect = freightCollect;
	}
	public String getMonthlyPayment() {
		return monthlyPayment;
	}
	public void setMonthlyPayment(String monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}
	public String getTransPayment() {
		return transPayment;
	}
	public void setTransPayment(String transPayment) {
		this.transPayment = transPayment;
	}
	public String getReceiptPayment() {
		return receiptPayment;
	}
	public void setReceiptPayment(String receiptPayment) {
		this.receiptPayment = receiptPayment;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	  
	  
	  
	
}