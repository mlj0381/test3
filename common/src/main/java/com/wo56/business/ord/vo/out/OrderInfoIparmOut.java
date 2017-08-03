package com.wo56.business.ord.vo.out;

import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class OrderInfoIparmOut extends BaseOutParamVO{
	private static final long serialVersionUID = 1200002030408414345L;
	/**订单号*/
	private long orderId;
	/**开单网点*/
	private Long orgId;
	private String remarks; 
	private String remark; //签收备注
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	private String orgIdName; 
	
	public String getOrgIdName() {
		if(orgId != null && orgId > 0){
			setOrgIdName(OraganizationCacheDataUtil.getOrgName(orgId));
		}
		return orgIdName;
	}
	public void setOrgIdName(String orgIdName) {
		this.orgIdName = orgIdName;
	}
	/**审核状态*/
	private Integer orderState;
	/**总重量*/
	private Double weight;
	/**总体积*/
	private Double volume;
	/**回单号*/
	private String receiptNum;
	/**回单份数*/
	private Integer receiptCount;
	/**付款方式*/
	private Integer paymentTypeName;
	/**到付（分）*/
	private Long freightCollect;
	/**到付（元）*/
	private String freightCollectDouble;
	/**配送区域*/
	private Long descRegion;
	/**代收货款（分）*/
	private Long collectingMoney;
	/**代收货款（元）*/
	private String collectingMoneyDouble;
	public String getFreightCollectDouble() {
		if(freightCollect != null && freightCollect > 0){
			setFreightCollectDouble(CommonUtil.getDoubleFixedNew2(freightCollect+""));
		}
		return freightCollectDouble;
	}
	public void setFreightCollectDouble(String freightCollectDouble) {
		this.freightCollectDouble = freightCollectDouble;
	}
	public String getCollectingMoneyDouble() {
		if(collectingMoney != null && collectingMoney > 0){
			setCollectingMoneyDouble(CommonUtil.getDoubleFixedNew2(collectingMoney+""));
		}
		return collectingMoneyDouble;
	}
	public void setCollectingMoneyDouble(String collectingMoneyDouble) {
		this.collectingMoneyDouble = collectingMoneyDouble;
	}
	/**收货联系人*/
	private String consigneeLinkmanName;
	/**收货人*/
	private String consigneeName;
	/**收货人电话*/
	private String consigneeBill;
	/**收货人固话*/
	private String consigneeTelephone;
	/**配送方式、交接方式*/
	private String deliveryTypeName;
	/**收货地址(这个数据看一下是否要组装)*/
	private String address;
	/**计费方式*/
	private Integer billingType;
	/**运单号*/
	private Long trackingNum;
	/**开单日期*/
	private Date createDate;
	/**发货人名称*/
	private String consignorName;
	/**发货方－联系人**/
	private String consignorLinkmanName;
	/**发货方－固话**/
	private String consignorTelephone;
	/**发货方－手机号**/
	private String consignorBill;
	/**楼层**/
	private Integer floor;
	/**货物名称**/
	private String goodsName;
	/**货物类型**/
	private Integer goodsType;
	/**货物数量**/
	private Integer goodsCount;
	/**打包方式**/
	private Integer packingType;
	/**签收人**/
	private String signName;
	/**签收状态**/
	private Integer signStatus;
	/**签收状态**/
	private String signStatusName;
	private Integer signType;
	public Integer getSignType() {
		return signType;
	}
	public void setSignType(Integer signType) {
		this.signType = signType;
	}
	/**派件员**/
	private String deliveryManName;
	/**货物类型中文名**/
	private String goodsTypeName;
	
	/**打包方式中文名**/
	private String packingTypeName;
	private String shipmentNoticeName;
	/**图片**/
	private String imagePath;
	private String sourceInformation;
	/**区域名称**/
	private String descRegionName;
	/**图片Id**/
	private long imageId;
	
	private Integer certificateType;
	private String  certificateNumber;
	private Integer shipmentNotice;
	
	public Integer getShipmentNotice() {
		return shipmentNotice;
	}
	public void setShipmentNotice(Integer shipmentNotice) {
		this.shipmentNotice = shipmentNotice;
	}
	public Integer getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(Integer certificateType) {
		this.certificateType = certificateType;
	}
	public String getCertificateNumber() {
		return certificateNumber;
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	private Integer releaseNote;
	
	public Integer getReleaseNote() {
		return releaseNote;
	}
	public void setReleaseNote(Integer releaseNote) {
		this.releaseNote = releaseNote;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	
	public String getReceiptNum() {
		return receiptNum;
	}
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Integer getReceiptCount() {
		return receiptCount;
	}
	public void setReceiptCount(Integer receiptCount) {
		this.receiptCount = receiptCount;
	}
	public Integer getPaymentTypeName() {
		return paymentTypeName;
	}
	public void setPaymentTypeName(Integer paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}
	public Long getFreightCollect() {
		return freightCollect;
	}
	public void setFreightCollect(Long freightCollect) {
		this.freightCollect = freightCollect;
	}
	public Long getCollectingMoney() {
		return collectingMoney;
	}
	public void setCollectingMoney(Long collectingMoney) {
		this.collectingMoney = collectingMoney;
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
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}
	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getBillingType() {
		return billingType;
	}
	public void setBillingType(Integer billingType) {
		this.billingType = billingType;
	}
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getConsignorName() {
		return consignorName;
	}
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}
	public String getConsignorLinkmanName() {
		return consignorLinkmanName;
	}
	public void setConsignorLinkmanName(String consignorLinkmanName) {
		this.consignorLinkmanName = consignorLinkmanName;
	}
	public String getConsignorTelephone() {
		return consignorTelephone;
	}
	public void setConsignorTelephone(String consignorTelephone) {
		this.consignorTelephone = consignorTelephone;
	}
	public String getConsignorBill() {
		return consignorBill;
	}
	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	public Integer getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}
	public Integer getPackingType() {
		return packingType;
	}
	public void setPackingType(Integer packingType) {
		this.packingType = packingType;
	}
	public String getConsigneeLinkmanName() {
		return consigneeLinkmanName;
	}
	public void setConsigneeLinkmanName(String consigneeLinkmanName) {
		this.consigneeLinkmanName = consigneeLinkmanName;
	}
	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}
	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public Integer getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}
	public String getDeliveryManName() {
		return deliveryManName;
	}
	public void setDeliveryManName(String deliveryManName) {
		this.deliveryManName = deliveryManName;
	}
	public Long getDescRegion() {
		return descRegion;
	}
	public void setDescRegion(Long descRegion) {
		this.descRegion = descRegion;
	}
	public String getSignStatusName() {
		return signStatusName;
	}
	public void setSignStatusName(String signStatusName) {
		this.signStatusName = signStatusName;
	}
	public String getGoodsTypeName() {
		return goodsTypeName;
	}
	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}
	public String getPackingTypeName() {
		return packingTypeName;
	}
	public void setPackingTypeName(String packingTypeName) {
		this.packingTypeName = packingTypeName;
	}
	public String getShipmentNoticeName() {
		return shipmentNoticeName;
	}
	public void setShipmentNoticeName(String shipmentNoticeName) {
		this.shipmentNoticeName = shipmentNoticeName;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getSourceInformation() {
		return sourceInformation;
	}
	public void setSourceInformation(String sourceInformation) {
		this.sourceInformation = sourceInformation;
	}
	public String getDescRegionName() {
		return descRegionName;
	}
	public void setDescRegionName(String descRegionName) {
		this.descRegionName = descRegionName;
	}
	public long getImageId() {
		return imageId;
	}
	public void setImageId(long imageId) {
		this.imageId = imageId;
	}
	
}
