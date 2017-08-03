package com.wo56.business.ac.vo.out;

import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SysTenantDefCacheDataUtil;

public class AcMyWalletTipFeeParamOut {
	private Long id;
	private Long ordersId;
	private Long orderId;
	private String orderNo;
	private String orderNumber;
	private Long billingOrgId;
	private String billingOrgName;
	private Long tenantId;
	private String tenantName;
	private Long desCity;
	private String desCityName;
	private String pullName;
	private String pullPhone;
	private Long tip;
	private String tipString;
	private Long defaultTip;
	private String defaultTipString;
	private Integer number;
	private String weight;
	private String volume;
	private Long freight;
	private String freightString;
	private String consignor;
	private String consignorPhone;
	private String consignorAddress;
	private String consignee;
	private String consigneePhone;
	private String consigneeAddress;
	private Integer interchange;
	private String interchangeString;
	private Integer payment;
	private String paymentString;
	private String productName;
	private String packName;
	private String createName;
	private String remarks;
	
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(Long ordersId) {
		this.ordersId = ordersId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Long getBillingOrgId() {
		return billingOrgId;
	}
	public void setBillingOrgId(Long billingOrgId) {
		this.billingOrgId = billingOrgId;
	}
	public String getBillingOrgName() {
		if(billingOrgId != null && billingOrgId > 0){
			return OraganizationCacheDataUtil.getOrgName(billingOrgId);
		}
		return billingOrgName;
	}
	public void setBillingOrgName(String billingOrgName) {
		this.billingOrgName = billingOrgName;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public String getTenantName() {
		if(tenantId != null && tenantId > 0){
			return SysTenantDefCacheDataUtil.getTenantName(tenantId);
		}
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public Long getDesCity() {
		return desCity;
	}
	public void setDesCity(Long desCity) {
		this.desCity = desCity;
	}
	public String getDesCityName() {
		if(desCity != null && desCity > 0){
			return SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(desCity)).getName();
		}
		return desCityName;
	}
	public void setDesCityName(String desCityName) {
		this.desCityName = desCityName;
	}
	public String getPullName() {
		return pullName;
	}
	public void setPullName(String pullName) {
		this.pullName = pullName;
	}
	public String getPullPhone() {
		return pullPhone;
	}
	public void setPullPhone(String pullPhone) {
		this.pullPhone = pullPhone;
	}
	public Long getTip() {
		return tip;
	}
	public void setTip(Long tip) {
		this.tip = tip;
	}
	public String getTipString() {
		if(tip != null && tip > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(tip, 2));
		}
		return tipString;
	}
	public void setTipString(String tipString) {
		this.tipString = tipString;
	}
	public Long getDefaultTip() {
		return defaultTip;
	}
	public void setDefaultTip(Long defaultTip) {
		this.defaultTip = defaultTip;
	}
	public String getDefaultTipString() {
		if(defaultTip != null && defaultTip > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(defaultTip, 2));
		}
		return defaultTipString;
	}
	public void setDefaultTipString(String defaultTipString) {
		this.defaultTipString = defaultTipString;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
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
	public Long getFreight() {
		return freight;
	}
	public void setFreight(Long freight) {
		this.freight = freight;
	}
	public String getFreightString() {
		if(freight != null && freight > 0){
			return String.valueOf(CommonUtil.getDoubleFormatLongMoney(freight, 2));
		}
		return freightString;
	}
	public void setFreightString(String freightString) {
		this.freightString = freightString;
	}
	public String getConsignor() {
		return consignor;
	}
	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}
	public String getConsignorPhone() {
		return consignorPhone;
	}
	public void setConsignorPhone(String consignorPhone) {
		this.consignorPhone = consignorPhone;
	}
	public String getConsignorAddress() {
		return consignorAddress;
	}
	public void setConsignorAddress(String consignorAddress) {
		this.consignorAddress = consignorAddress;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public Integer getInterchange() {
		return interchange;
	}
	public void setInterchange(Integer interchange) {
		this.interchange = interchange;
	}
	public String getInterchangeString() {
		return interchangeString;
	}
	public void setInterchangeString(String interchangeString) {
		this.interchangeString = interchangeString;
	}
	public Integer getPayment() {
		return payment;
	}
	public void setPayment(Integer payment) {
		this.payment = payment;
	}
	public String getPaymentString() {
		return paymentString;
	}
	public void setPaymentString(String paymentString) {
		this.paymentString = paymentString;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
