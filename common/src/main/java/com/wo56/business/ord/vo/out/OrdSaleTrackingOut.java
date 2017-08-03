package com.wo56.business.ord.vo.out;

import java.util.Date;

import com.framework.core.util.DateUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class OrdSaleTrackingOut {
	private Long orderId;
	private Long ordOrgId;//开单网点
	private String ordOrdIdName;//开单网点
	private Date orderDate;//开单时间
	private String opName;//开单人
	private Long currentOrgId;//配送网点
	private String currentOrgIdName;//配送网点
	private Long carrierOrgId;//承运商
	private String carrierOrgIdName;//承运商
	private String linkerPhone;//承运商电话
	private String consignorName;//发货人
	private String consignorTelephone;//发货人电话
	private String consigneeName;//收货人
	private String consigneeTelephone;//收货人电话
	private String sfName;//师傅合作商
	private String sfTel;//合作商电话
	private String belongObjName;//个体师傅
	private String belongTel;//师傅电话
	private Integer paymentType;//付款方式
	private String paymentTypeString;//付款方式
	private Integer paymentType2;//付款方式2
	private String paymentType2String;//付款方式2
	private Long totalFee;//费用合计
	private String totalFeeString;//费用合计
	private String products;//货品
	private Integer count;//件数
	private Double volume;//体积
	private Double weight;//重量
	private Long descRegion;//目的区域
	private String descRegionString;//目的区域
	private String remarks;//运单备注
	private String orderDateString;
	
	public String getOrderDateString() {
		if(orderDate != null){
			return DateUtil.formatDate(orderDate, "yyyy-MM-dd");
		}
		return "";
	}
	public void setOrderDateString(String orderDateString) {
		this.orderDateString = orderDateString;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getOrdOrgId() {
		return ordOrgId;
	}
	public void setOrdOrgId(Long ordOrgId) {
		this.ordOrgId = ordOrgId;
	}
	public String getOrdOrdIdName() {
		if(ordOrgId != null && ordOrgId > 0){
			return OraganizationCacheDataUtil.getOrgName(ordOrgId);
		}
		return "";
	}
	public void setOrdOrdIdName(String ordOrdIdName) {
		this.ordOrdIdName = ordOrdIdName;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public Long getCurrentOrgId() {
		return currentOrgId;
	}
	public void setCurrentOrgId(Long currentOrgId) {
		this.currentOrgId = currentOrgId;
	}
	public String getCurrentOrgIdName() {
		if(currentOrgId != null && currentOrgId > 0){
			return OraganizationCacheDataUtil.getOrgName(currentOrgId);
		}
		return "";
	}
	public void setCurrentOrgIdName(String currentOrgIdName) {
		this.currentOrgIdName = currentOrgIdName;
	}
	public Long getCarrierOrgId() {
		return carrierOrgId;
	}
	public void setCarrierOrgId(Long carrierOrgId) {
		this.carrierOrgId = carrierOrgId;
	}
	public String getCarrierOrgIdName() {
		if(carrierOrgId != null && carrierOrgId >0 ){
			return OraganizationCacheDataUtil.getOrgName(carrierOrgId);
		}
		return carrierOrgIdName;
	}
	public void setCarrierOrgIdName(String carrierOrgIdName) {
		this.carrierOrgIdName = carrierOrgIdName;
	}
	public String getLinkerPhone() {
		return linkerPhone;
	}
	public void setLinkerPhone(String linkerPhone) {
		this.linkerPhone = linkerPhone;
	}
	public String getConsignorName() {
		return consignorName;
	}
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}
	public String getConsignorTelephone() {
		return consignorTelephone;
	}
	public void setConsignorTelephone(String consignorTelephone) {
		this.consignorTelephone = consignorTelephone;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}
	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}
	public String getSfName() {
		return sfName;
	}
	public void setSfName(String sfName) {
		this.sfName = sfName;
	}
	public String getSfTel() {
		return sfTel;
	}
	public void setSfTel(String sfTel) {
		this.sfTel = sfTel;
	}
	public String getBelongObjName() {
		return belongObjName;
	}
	public void setBelongObjName(String belongObjName) {
		this.belongObjName = belongObjName;
	}
	public String getBelongTel() {
		return belongTel;
	}
	public void setBelongTel(String belongTel) {
		this.belongTel = belongTel;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentTypeString() {
		if(paymentType != null && paymentType > 0){
			return SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE", paymentType.toString());
		}
		return "";
	}
	public void setPaymentTypeString(String paymentTypeString) {
		this.paymentTypeString = paymentTypeString;
	}
	public Integer getPaymentType2() {
		return paymentType2;
	}
	public void setPaymentType2(Integer paymentType2) {
		this.paymentType2 = paymentType2;
	}
	public String getPaymentType2String() {
		if(paymentType2 != null && paymentType2 > 0){
			return SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE", paymentType2.toString());
		}
		return "";
	}
	public void setPaymentType2String(String paymentType2String) {
		this.paymentType2String = paymentType2String;
	}
	public Long getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}
	public String getTotalFeeString() {
		if(totalFee != null && totalFee > 0){
			return CommonUtil.getDoubleFixedNew2(String.valueOf(totalFee));
		}
		return "";
	}
	public void setTotalFeeString(String totalFeeString) {
		this.totalFeeString = totalFeeString;
	}
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
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
	public Long getDescRegion() {
		return descRegion;
	}
	public void setDescRegion(Long descRegion) {
		this.descRegion = descRegion;
	}
	public String getDescRegionString() {
		return descRegionString;
	}
	public void setDescRegionString(String descRegionString) {
		this.descRegionString = descRegionString;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
