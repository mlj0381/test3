package com.wo56.business.ord.vo.out;

import java.util.Date;

import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

/**
 * OrdOrderInfo entity. @author MyEclipse Persistence Tools
 */

public class OrdOrderInfoOutNew  {

	// Fields
	private Long tenantId;
	private long orderId;
	private Long trackingNum;
	private Integer orderState;
	private Long orgId;
	private Long currentOrgId;
	//private Long tenantId;
	private Long destProvince;
	private String destCity;
	private Long destCounty;
	private Long destStreet;
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
	private String opName;
	private Integer releaseNote;
	private String rountOrgId;
	private Long rountId;
	private Integer valuables;
	private String remarks;
	private Integer floor;
	private Double weight;
	private Double volume;
	private Integer notes;
	private Integer transportType;
	private Integer isLift;
	private Integer shipmentNotice;
	
	private Integer count;
	private Integer isInstall;
	private String plateNumber;
	private String driverName;
	private Long vehicleId;
	private Long driverId;
	private Date createDate;
	private Long consignorId;
	private Long consigneeId;
	private Integer orderType;
	private String pickAddr;//提货地址
	private String pickLinkMan;//提货联系人
	private String pickTel;//提货联系电话
	
	private int isTmail;//表示天猫字段
	private String sfName;
	private String sfTel;
	private long sfId;
	private long sfFee;
	private String longitude;
	private String latitude;
	private String pickCode;
	private String pickLongitude;
	private String pickLatitude;
	private Long totalFee;
	private String otherOrderId;
	private String products;
	private Integer deliveryStatus;
	private String currentOrgIdName;
	private Long desPoint;
	private String desPointName;
	private String fromAddress;
	private Boolean isSign;
	private Boolean isVerification;
	private Boolean isImportant;
	private Boolean isSfReceiver;
	private Boolean isReceipt;
	private Boolean isDirect;
	private Boolean serviceDelivery;
	private Boolean serviceFix;
	private Integer serviceType;
	private String serviceTypeName;
	private Integer postingSts;
	private String postingStsName;
	private Integer seeOrderState;
	private String seeOrderStateName;
	private String goodsNumber;
	private Integer getGoodsType;
	
	private String consignee;//仓管员
	
	private Integer isInsurance;//0未投保 1：已投保
	
	private Double sfFeeDouble;
	private Double totalFeeDouble;
	
	private String rountOrgName;
	
	private Integer isSeaTransport;
	
	public Integer getIsSeaTransport() {
		return isSeaTransport;
	}

	public void setIsSeaTransport(Integer isSeaTransport) {
		this.isSeaTransport = isSeaTransport;
	}

	public String getRountOrgName() {
		return rountOrgName;
	}

	public void setRountOrgName(String rountOrgName) {
		this.rountOrgName = rountOrgName;
	}

	public Double getSfFeeDouble() {
		setSfFeeDouble(CommonUtil.getDoubleFormatLongMoney(sfFee, 2));
		return sfFeeDouble;
	}

	public void setSfFeeDouble(Double sfFeeDouble) {
		this.sfFeeDouble = sfFeeDouble;
	}

	public Double getTotalFeeDouble() {
		setTotalFeeDouble(CommonUtil.getDoubleFormatLongMoney(totalFee, 2));
		return totalFeeDouble;
	}

	public void setTotalFeeDouble(Double totalFeeDouble) {
		this.totalFeeDouble = totalFeeDouble;
	}

	public Integer getIsInsurance() {
		return isInsurance;
	}

	public void setIsInsurance(Integer isInsurance) {
		this.isInsurance = isInsurance;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public Integer getGetGoodsType() {
		return getGoodsType;
	}

	public void setGetGoodsType(Integer getGoodsType) {
		this.getGoodsType = getGoodsType;
	}


	public String getPostingStsName() {
		if(postingSts!=null && postingSts.intValue()>-1){
			setPostingStsName(SysStaticDataUtil.getSysStaticData("ORDER_POSTING_STS", postingSts.intValue()+"").getCodeName());
		}
		return postingStsName;
	}


	public void setPostingStsName(String postingStsName) {
		this.postingStsName = postingStsName;
	}


	public Integer getPostingSts() {
		return postingSts;
	}


	public void setPostingSts(Integer postingSts) {
		this.postingSts = postingSts;
	}


	public Integer getSeeOrderState() {
		return seeOrderState;
	}

	public void setSeeOrderState(Integer seeOrderState) {
		this.seeOrderState = seeOrderState;
	}


	public String getSeeOrderStateName() {
		if(seeOrderState!=null && seeOrderState > 0){
			setSeeOrderStateName(SysStaticDataUtil.getSysStaticData("APP_ORDER_STATE", seeOrderState+"").getCodeName());
		}
		return seeOrderStateName;
	}


	public void setSeeOrderStateName(String seeOrderStateName) {
		this.seeOrderStateName = seeOrderStateName;
	}


	public String getServiceTypeName() {
		if(serviceType!=null && serviceType.intValue()>-1){
			setServiceTypeName(SysStaticDataUtil.getSysStaticData("SCHE_SERVICE_TYPE", serviceType.intValue()+"").getCodeName());
		}
		return serviceTypeName;
	}


	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}


	public Integer getServiceType() {
		return serviceType;
	}


	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}


	public Boolean getServiceDelivery() {
		return serviceDelivery;
	}


	public void setServiceDelivery(Boolean serviceDelivery) {
		this.serviceDelivery = serviceDelivery;
	}


	public Boolean getServiceFix() {
		return serviceFix;
	}


	public void setServiceFix(Boolean serviceFix) {
		this.serviceFix = serviceFix;
	}


	public Boolean getIsSign() {
		return isSign;
	}


	public void setIsSign(Boolean isSign) {
		this.isSign = isSign;
	}


	public Boolean getIsVerification() {
		return isVerification;
	}


	public void setIsVerification(Boolean isVerification) {
		this.isVerification = isVerification;
	}


	public Boolean getIsImportant() {
		return isImportant;
	}


	public void setIsImportant(Boolean isImportant) {
		this.isImportant = isImportant;
	}


	public Boolean getIsSfReceiver() {
		return isSfReceiver;
	}


	public void setIsSfReceiver(Boolean isSfReceiver) {
		this.isSfReceiver = isSfReceiver;
	}


	public Boolean getIsReceipt() {
		return isReceipt;
	}


	public void setIsReceipt(Boolean isReceipt) {
		this.isReceipt = isReceipt;
	}


	public Boolean getIsDirect() {
		return isDirect;
	}


	public void setIsDirect(Boolean isDirect) {
		this.isDirect = isDirect;
	}


	public String getFromAddress() {
		return fromAddress;
	}


	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public Long getDesPoint() {
		return desPoint;
	}


	public void setDesPoint(Long desPoint) {
		this.desPoint = desPoint;
	}


	public String getDesPointName() {
		return desPointName;
	}


	public void setDesPointName(String desPointName) {
		this.desPointName = desPointName;
	}


	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}


	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public String getProducts() {
		return products;
	}


	public void setProducts(String products) {
		this.products = products;
	}

	/**   
	 * 异常签收标识：0正常签收1异常签收.  
	 */
	private Integer isException;
	/**   
	 * 商家订单ID.  
	 */
	private Long sellerOrderId;
	/**   
	 * 订单类型:1淘宝2天猫3京东4其他.  
	 */
	private Integer otherOrderType;
	/**   
	 * 商家组织ID.  
	 */
	private Long sellerOrgId;

	/**   
	 * 商家租户ID.  
	 */
	private Long sellerTenantId;
	
	public Long getSellerOrderId() {
		return sellerOrderId;
	}


	public void setSellerOrderId(Long sellerOrderId) {
		this.sellerOrderId = sellerOrderId;
	}





	public Integer getOtherOrderType() {
		return otherOrderType;
	}


	public void setOtherOrderType(Integer otherOrderType) {
		this.otherOrderType = otherOrderType;
	}


	public Long getSellerOrgId() {
		return sellerOrgId;
	}


	public void setSellerOrgId(Long sellerOrgId) {
		this.sellerOrgId = sellerOrgId;
	}


	public Long getSellerTenantId() {
		return sellerTenantId;
	}


	public void setSellerTenantId(Long sellerTenantId) {
		this.sellerTenantId = sellerTenantId;
	}


	public String getOtherOrderId() {
		return otherOrderId;
	}


	public void setOtherOrderId(String otherOrderId) {
		this.otherOrderId = otherOrderId;
	}


	public Integer getIsException() {
		return isException;
	}


	public void setIsException(Integer isException) {
		this.isException = isException;
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


	public long getSfId() {
		return sfId;
	}


	public void setSfId(long sfId) {
		this.sfId = sfId;
	}


	public long getSfFee() {
		return sfFee;
	}


	public void setSfFee(long sfFee) {
		this.sfFee = sfFee;
	}


	public Long getTotalFee() {
		return totalFee;
	}


	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getPickCode() {
		return pickCode;
	}


	public void setPickCode(String pickCode) {
		this.pickCode = pickCode;
	}


	public String getPickLongitude() {
		return pickLongitude;
	}


	public void setPickLongitude(String pickLongitude) {
		this.pickLongitude = pickLongitude;
	}


	public String getPickLatitude() {
		return pickLatitude;
	}


	public void setPickLatitude(String pickLatitude) {
		this.pickLatitude = pickLatitude;
	}

	public Integer getIsTmail() {
		return isTmail;
	}


	public void setIsTmail(int isTmail) {
		this.isTmail = isTmail;
	}


	public String getPickLinkMan() {
		return pickLinkMan;
	}


	public void setPickLinkMan(String pickLinkMan) {
		this.pickLinkMan = pickLinkMan;
	}


	public String getPickTel() {
		return pickTel;
	}


	public void setPickTel(String pickTel) {
		this.pickTel = pickTel;
	}


	public String getPickAddr() {
		return pickAddr;
	}


	public void setPickAddr(String pickAddr) {
		this.pickAddr = pickAddr;
	}


	public Long getConsignorId() {
		return consignorId;
	}


	public void setConsignorId(Long consignorId) {
		this.consignorId = consignorId;
	}


	public Long getConsigneeId() {
		return consigneeId;
	}


	public void setConsigneeId(Long consigneeId) {
		this.consigneeId = consigneeId;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Integer getIsInstall() {
		return isInstall;
	}


	public void setIsInstall(Integer isInstall) {
		this.isInstall = isInstall;
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


	public Long getVehicleId() {
		return vehicleId;
	}


	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}


	public Long getDriverId() {
		return driverId;
	}


	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}

	//开单网点名称
	private String orgIdName;
	private String orderStateName;
	private String destProvinceName;
	private String destCityName;
	private String destCountyName;
	private String destStreetName;

	private String sourceProvinceName;
	private String sourceCityName;
	private String sourceCountyName;
	
	private String transportTypeName;
	private String notesName;
	private String deliveryTypeName;
	private String shipmentNoticeName;
	//private String descRegionName;
	
	
	
	
	public String getOrgIdName() {
		if(orgId != null && orgId>0){
			setOrgIdName(OraganizationCacheDataUtil.getOrgName(orgId));
		}
		return orgIdName;
	}


	public void setOrgIdName(String orgIdName) {
		this.orgIdName = orgIdName;
	}


	public String getShipmentNoticeName() {
		if(shipmentNotice!=null && shipmentNotice>-1){
			setShipmentNoticeName(SysStaticDataUtil.getSysStaticData("SHIPMENT_NOTICE", shipmentNotice+"").getCodeName());
		}
		return shipmentNoticeName;
	}


	public void setShipmentNoticeName(String shipmentNoticeName) {
		this.shipmentNoticeName = shipmentNoticeName;
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
		if (destCity !=null&& !"".equals(destCity))
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

	public String getDestCity() {
		return this.destCity;
	}

	public void setDestCity(String destCity) {
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
		if(distributionOrgId != null && distributionOrgId>0){
			setDistributionOrgName(OraganizationCacheDataUtil.getOrgName(distributionOrgId));
		}
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

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getVolume() {
		return this.volume;
	}

	public void setVolume(Double volume) {
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
	public Integer getShipmentNotice() {
		return shipmentNotice;
	}

	public void setShipmentNotice(Integer shipmentNotice) {
		this.shipmentNotice = shipmentNotice;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}


	public Long getDestStreet() {
		return destStreet;
	}

	public void setDestStreet(Long destStreet) {
		this.destStreet = destStreet;
	}

	public String getDestStreetName() {
		if (destStreet != null && (destStreet) != 0)
			setDestStreetName(SysStaticDataUtil.getStreetDataList("SYS_STREET", destStreet + "").getName());
		return destStreetName;
	}

	public void setDestStreetName(String destStreetName) {
		this.destStreetName = destStreetName;
	}
	
	public void setCurrentOrgIdName(String currentOrgIdName) {
		this.currentOrgIdName = currentOrgIdName;
	}
	
	public String getCurrentOrgIdName() {
		if(currentOrgId != null && currentOrgId>0){
			setCurrentOrgIdName(OraganizationCacheDataUtil.getOrgName(currentOrgId));
		}
		return currentOrgIdName;
	}
	/*public String getDescRegionName() {
		if(descRegion!=null && descRegion>0){
	  	   AcAreaFeeConfigTF acTF = (AcAreaFeeConfigTF)SysContexts.getBean("acAreaFeeConfigTF");
	  	   setDescRegionName(acTF.queryAreaName(descRegion));
		 }
		return descRegionName;
	}
	public void setDescRegionName(String descRegionName) {
		this.descRegionName = descRegionName;
	}*/
	

}