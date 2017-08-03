package com.wo56.business.ord.vo.out;

import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.wo56.common.utils.CommonUtil;

public class OrdNotifyOut extends BaseOutParamVO{
	private static final long serialVersionUID = 1200002030408414345L;
	/**订单号*/
	private long orderId;
	/**订单号*/
	private long trackingNum;
	/**开单网点*/
	private Long orgId;
	/**订单状态*/
	private Integer orderState;
	/**总重量*/
	private String weight;
	/**总体积*/
	private String volume;
	/**目的城市*/
	private Long destCity;
	/**目的城市名*/
	private String destCityName;
	/**配送网点*/
	private Long distributionOrgId;
	/**配送城市名*/
	private String distributionOrgName;
	/**回单号*/
	private String receiptNum;
	/**回单份数*/
	private Integer receiptCount;
	/**付款方式*/
	private Integer paymentTypeName;
	/**现付*/
	private Long cashPayment;
	/**到付*/
	private Long freightCollect;
	/**代收货款*/
	private Long collectingMoney;
	
	/**到付（元）*/
	private Double freightCollectDouble;
	/**代收货款（元）*/
	private Double collectingMoneyDouble;
	
	
	public Double getFreightCollectDouble() {
		setFreightCollectDouble(CommonUtil.getDoubleFormatLongMoney(freightCollect, 1));
		return freightCollectDouble;
	}
	public void setFreightCollectDouble(Double freightCollectDouble) {
		this.freightCollectDouble = freightCollectDouble;
	}
	public Double getCollectingMoneyDouble() {
		setCollectingMoneyDouble(CommonUtil.getDoubleFormatLongMoney(collectingMoney, 1));
		return collectingMoneyDouble;
	}
	public void setCollectingMoneyDouble(Double collectingMoneyDouble) {
		this.collectingMoneyDouble = collectingMoneyDouble;
	}
	/**收货人*/
	private String consigneeName;
	/**收货联系人*/
	private String consigneeLinkmanName;
	/**收货联系人固话*/
	private String consigneeTelephone;
	/**收货联系人手机*/
	private String consigneeBill;
	/**配送方式、交接方式*/
	private String deliveryTypeName;
	/**配送方式、交接方式*/
	private Integer deliveryType;
	/**收货地址(这个数据看一下是否要组装)*/
	private String address;
	/**预约送货时间**/
	private  Date schedulingDate;
	/**等通知送货*/
	private String shipmentNoticeName;
	/**通知状态*/
	private String typeName;
	/**----------------------详情展示------------------------**/
	/**开单日期**/
	private Date createDate;
	private Integer type;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	/**楼层**/
	private Integer floor;
	/**配送区域**/
	private Long descRegion;
	/**货物名称**/
	private String goodsName;
	/**货物类型**/
	private Integer goodsType;
	/**货物数量**/
	private Integer goodsCount;
	/**包装类型**/
	private Integer packingType;
	/**配送方式、交接方式*/
	/**车牌号**/
	private String platenumber;
	/**电话号码**/
	private String driverPhone;
	/**备注**/
	private String notes;
	/**电话接听人**/
	private String receiveCall;
	/****/
	private String descRegionName;
	private int shipmentNotice;
	public int getShipmentNotice() {
		return shipmentNotice;
	}
	public void setShipmentNotice(int shipmentNotice) {
		this.shipmentNotice = shipmentNotice;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	public long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(long trackingNum) {
		this.trackingNum = trackingNum;
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
	public Long getDestCity() {
		return destCity;
	}
	public void setDestCity(Long destCity) {
		this.destCity = destCity;
	}
	public String getDestCityName() {
		return destCityName;
	}
	public void setDestCityName(String destCityName) {
		this.destCityName = destCityName;
	}
	public Long getDistributionOrgId() {
		return distributionOrgId;
	}
	public void setDistributionOrgId(Long distributionOrgId) {
		this.distributionOrgId = distributionOrgId;
	}
	public String getDistributionOrgName() {
		return distributionOrgName;
	}
	public void setDistributionOrgName(String distributionOrgName) {
		this.distributionOrgName = distributionOrgName;
	}
	public String getReceiptNum() {
		return receiptNum;
	}
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
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
	public Long getCashPayment() {
		return cashPayment;
	}
	public void setCashPayment(Long cashPayment) {
		this.cashPayment = cashPayment;
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

	public Date getSchedulingDate() {
		return schedulingDate;
	}
	public void setSchedulingDate(Date schedulingDate) {
		this.schedulingDate = schedulingDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getShipmentNoticeName() {
		return shipmentNoticeName;
	}
	public void setShipmentNoticeName(String shipmentNoticeName) {
		this.shipmentNoticeName = shipmentNoticeName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public Long getDescRegion() {
		return descRegion;
	}
	public void setDescRegion(Long descRegion) {
		this.descRegion = descRegion;
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
	public Integer getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getPlatenumber() {
		return platenumber;
	}
	public void setPlatenumber(String platenumber) {
		this.platenumber = platenumber;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getReceiveCall() {
		return receiveCall;
	}
	public void setReceiveCall(String receiveCall) {
		this.receiveCall = receiveCall;
	}
	public String getDescRegionName() {
		return descRegionName;
	}
	public void setDescRegionName(String descRegionName) {
		this.descRegionName = descRegionName;
	}
	
	
}
