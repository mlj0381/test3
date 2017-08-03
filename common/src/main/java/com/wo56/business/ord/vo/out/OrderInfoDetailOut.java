package com.wo56.business.ord.vo.out;

import java.sql.Timestamp;
import java.util.List;

import com.framework.core.util.SysStaticDataUtil;
//import com.wo56.business.ord.vo.OrdGoodsDetail;
import com.wo56.common.consts.EnumConsts;

public class OrderInfoDetailOut {

	private long orderId;
	private Long trackingNum;
	private Integer orderState;
	private Long orgId;
	private Long currentOrgId;
	private Timestamp createDate;
	private Long tenantId;
	private Long destProvince;
	private Long destCity;
	private Long destCounty;
	private Long descRegion;
	private Long sourceProvince;
	private Long sourceCity;
	private Long sourceCounty;
	private String address;
	private Long distributionOrgId;
	private String distributionOrgName;
	private String consignorName;
	private String consignorLinkmanName;
	private String consignorTelephone;
	private String consignorBill;
	private String consigneeName;
	private String consigneeLinkmanName;
	private String consigneeTelephone;
	private String consigneeBill;
	private Integer deliveryType;
	private String receiptNum;
	private Integer receiptCount;
	private Long salesmanId;
	private String salesmanName;
	private Long inputUserId;
	private String inputUserName;
	private Long opId;
	private String opName;
	private Integer releaseNote;
	private String rountOrgId;
	private Long rountId;
	private Integer valuables;
	private String remarks;
	private Integer floor;
	private Long weight;
	private Long volume;
	private Integer notes;
	private Integer transportType;
	private Integer isLift;
	
	private String orderStateName;
	private String destProvinceName;
	private String destCityName;
	private String destCountyName;

	private String sourceProvinceName;
	private String sourceCityName;
	private String sourceCountyName;
	
	private String transportTypeName;
	private String notesName;
	private String deliveryTypeName;
	
	//--ordFee--//
	private Long freight;
	private Long pickingCosts;
	private Long handingCosts;
	private Long packingCosts;
	private Long deliveryCosts;
	private Long cashPayment;
	private Long freightCollect;
	private Long receiptPayment;
	private Long monthlyPayment;
	private Long discount;
	private Integer isPayDiscount;
	private Long pushMoney;
	private Long collectingMoney;
	private Long procedureFee;
	private Integer paymentType;
	private String paymentTypeName;
	
	

	
//	//---ordGoodsDetail--//
//	private List<OrdGoodsDetail> goodsList;
//	
//	
//	// Constructors
//
//	public List<OrdGoodsDetail> getGoodsList() {
//		return goodsList;
//	}
//
//	public void setGoodsList(List<OrdGoodsDetail> goodsList) {
//		this.goodsList = goodsList;
//	}

	public String getPaymentTypeName() {
		if(paymentType != null && paymentType>0){
			setPaymentTypeName(SysStaticDataUtil.getSysStaticData("PAYMENT_TYPE", paymentType+"").getCodeName());
		}
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

	public Long getFreight() {
		return this.freight;
	}

	public void setFreight(Long freight) {
		this.freight = freight;
	}

	public Long getPickingCosts() {
		return this.pickingCosts;
	}

	public void setPickingCosts(Long pickingCosts) {
		this.pickingCosts = pickingCosts;
	}

	public Long getHandingCosts() {
		return this.handingCosts;
	}

	public void setHandingCosts(Long handingCosts) {
		this.handingCosts = handingCosts;
	}

	public Long getPackingCosts() {
		return this.packingCosts;
	}

	public void setPackingCosts(Long packingCosts) {
		this.packingCosts = packingCosts;
	}

	public Long getDeliveryCosts() {
		return this.deliveryCosts;
	}

	public void setDeliveryCosts(Long deliveryCosts) {
		this.deliveryCosts = deliveryCosts;
	}

	public Long getCashPayment() {
		return this.cashPayment;
	}

	public void setCashPayment(Long cashPayment) {
		this.cashPayment = cashPayment;
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

	public Long getMonthlyPayment() {
		return this.monthlyPayment;
	}

	public void setMonthlyPayment(Long monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public Long getDiscount() {
		return this.discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Integer getIsPayDiscount() {
		return this.isPayDiscount;
	}

	public void setIsPayDiscount(Integer isPayDiscount) {
		this.isPayDiscount = isPayDiscount;
	}

	public Long getPushMoney() {
		return this.pushMoney;
	}

	public void setPushMoney(Long pushMoney) {
		this.pushMoney = pushMoney;
	}

	public Long getCollectingMoney() {
		return this.collectingMoney;
	}

	public void setCollectingMoney(Long collectingMoney) {
		this.collectingMoney = collectingMoney;
	}

	public Long getProcedureFee() {
		return this.procedureFee;
	}

	public void setProcedureFee(Long procedureFee) {
		this.procedureFee = procedureFee;
	}

	
	
	public String getDeliveryTypeName() {
		if(deliveryType != null && deliveryType>0){
			setDeliveryTypeName(SysStaticDataUtil.getSysStaticData("DELIVERY_TYPE", deliveryType+"").getCodeName());
		}
		return deliveryTypeName;
	}


	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}


	public String getNotesName() {
		if(notes != null && notes>0)
			setNotesName(SysStaticDataUtil.getSysStaticData("NOTES", notes+"").getCodeName());
		return notesName;
	}


	public void setNotesName(String notesName) {
		this.notesName = notesName;
	}


	public String getTransportTypeName() {
		if(transportType != null && transportType>0){
			setTransportTypeName(SysStaticDataUtil.getSysStaticData("TRANSPOR_TTYPE", transportType+"").getCodeName());
		}
		return transportTypeName;
	}


	public void setTransportTypeName(String transportTypeName) {
		this.transportTypeName = transportTypeName;
	}


	public Integer getIsLift() {
		return isLift;
	}


	public void setIsLift(Integer isLift) {
		this.isLift = isLift;
	}
	// Constructors

	public String getDestProvinceName() {
		if (destProvince !=null&&( destProvince )!=0)
			setDestProvinceName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", destProvince+"").getName());
		return destProvinceName;
	}
	

	public void setDestProvinceName(String destProvinceName) {
		this.destProvinceName = destProvinceName;
	}

	public String getDestCityName() {
		if (destCity !=null&&( destCity )!=0)
			setDestCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", destCity+"").getName());
		return destCityName;
	}

	public void setDestCityName(String destCityName) {
		this.destCityName = destCityName;
	}

	public String getDestCountyName() {
		if (destCounty !=null&&( destCounty )!=0)
			setDestCountyName(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", destCounty+"").getName());
		return destCountyName;
	}

	public void setDestCountyName(String destCountyName) {
		this.destCountyName = destCountyName;
	}

	public String getSourceProvinceName() {
		if (sourceProvince !=null&&( sourceProvince )!=0)
			setSourceProvinceName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", sourceProvince+"").getName());
		return sourceProvinceName;
	}

	public void setSourceProvinceName(String sourceProvinceName) {
		this.sourceProvinceName = sourceProvinceName;
	}

	public String getSourceCityName() {
		if (sourceCity !=null&&( sourceCity )!=0)
			setSourceCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", sourceCity+"").getName());
		return sourceCityName;
	}

	public void setSourceCityName(String sourceCityName) {
		this.sourceCityName = sourceCityName;
	}

	public String getSourceCountyName() {
		if (sourceCounty !=null&&( sourceCounty )!=0)
			setSourceCountyName(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", sourceCounty+"").getName());
		return sourceCountyName;
	}

	public void setSourceCountyName(String sourceCountyName) {
		this.sourceCountyName = sourceCountyName;
	}

	public String getOrderStateName() {
		if(orderState != null && orderState >0){
			setOrderStateName(SysStaticDataUtil.getSysStaticData(EnumConsts.SysStaticData.ORDER_STATE, orderState+"").getCodeName());
		}
		return orderStateName;
	}

	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}

	// Property accessors

	public long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Long getTrackingNum() {
		return this.trackingNum;
	}

	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}

	public Integer getOrderState() {
		return this.orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCurrentOrgId() {
		return this.currentOrgId;
	}

	public void setCurrentOrgId(Long currentOrgId) {
		this.currentOrgId = currentOrgId;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Long getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Long getDestProvince() {
		return this.destProvince;
	}

	public void setDestProvince(Long destProvince) {
		this.destProvince = destProvince;
	}

	public Long getDestCity() {
		return this.destCity;
	}

	public void setDestCity(Long destCity) {
		this.destCity = destCity;
	}

	public Long getDestCounty() {
		return this.destCounty;
	}

	public void setDestCounty(Long destCounty) {
		this.destCounty = destCounty;
	}

	public Long getDescRegion() {
		return this.descRegion;
	}

	public void setDescRegion(Long descRegion) {
		this.descRegion = descRegion;
	}

	public Long getSourceProvince() {
		return this.sourceProvince;
	}

	public void setSourceProvince(Long sourceProvince) {
		this.sourceProvince = sourceProvince;
	}

	public Long getSourceCity() {
		return this.sourceCity;
	}

	public void setSourceCity(Long sourceCity) {
		this.sourceCity = sourceCity;
	}

	public Long getSourceCounty() {
		return this.sourceCounty;
	}

	public void setSourceCounty(Long sourceCounty) {
		this.sourceCounty = sourceCounty;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getDistributionOrgId() {
		return this.distributionOrgId;
	}

	public void setDistributionOrgId(Long distributionOrgId) {
		this.distributionOrgId = distributionOrgId;
	}

	public String getDistributionOrgName() {
		return this.distributionOrgName;
	}

	public void setDistributionOrgName(String distributionOrgName) {
		this.distributionOrgName = distributionOrgName;
	}

	public String getConsignorName() {
		return this.consignorName;
	}

	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}

	public String getConsignorLinkmanName() {
		return this.consignorLinkmanName;
	}

	public void setConsignorLinkmanName(String consignorLinkmanName) {
		this.consignorLinkmanName = consignorLinkmanName;
	}

	public String getConsignorTelephone() {
		return this.consignorTelephone;
	}

	public void setConsignorTelephone(String consignorTelephone) {
		this.consignorTelephone = consignorTelephone;
	}

	public String getConsignorBill() {
		return this.consignorBill;
	}

	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}

	public String getConsigneeName() {
		return this.consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeLinkmanName() {
		return this.consigneeLinkmanName;
	}

	public void setConsigneeLinkmanName(String consigneeLinkmanName) {
		this.consigneeLinkmanName = consigneeLinkmanName;
	}

	public String getConsigneeTelephone() {
		return this.consigneeTelephone;
	}

	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}

	public String getConsigneeBill() {
		return this.consigneeBill;
	}

	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}

	public Integer getDeliveryType() {
		return this.deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getReceiptNum() {
		return this.receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	public Integer getReceiptCount() {
		return this.receiptCount;
	}

	public void setReceiptCount(Integer receiptCount) {
		this.receiptCount = receiptCount;
	}

	public Long getSalesmanId() {
		return this.salesmanId;
	}

	public void setSalesmanId(Long salesmanId) {
		this.salesmanId = salesmanId;
	}

	public String getSalesmanName() {
		return this.salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public Long getInputUserId() {
		return this.inputUserId;
	}

	public void setInputUserId(Long inputUserId) {
		this.inputUserId = inputUserId;
	}

	public String getInputUserName() {
		return this.inputUserName;
	}

	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getOpName() {
		return this.opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public Integer getReleaseNote() {
		return this.releaseNote;
	}

	public void setReleaseNote(Integer releaseNote) {
		this.releaseNote = releaseNote;
	}

	public String getRountOrgId() {
		return this.rountOrgId;
	}

	public void setRountOrgId(String rountOrgId) {
		this.rountOrgId = rountOrgId;
	}

	public Long getRountId() {
		return this.rountId;
	}

	public void setRountId(Long rountId) {
		this.rountId = rountId;
	}

	public Integer getValuables() {
		return this.valuables;
	}

	public void setValuables(Integer valuables) {
		this.valuables = valuables;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getFloor() {
		return this.floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Long getWeight() {
		return this.weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public Long getVolume() {
		return this.volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public Integer getNotes() {
		return this.notes;
	}

	public void setNotes(Integer notes) {
		this.notes = notes;
	}

	public Integer getTransportType() {
		return this.transportType;
	}

	public void setTransportType(Integer transportType) {
		this.transportType = transportType;
	}

}
