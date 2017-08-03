package com.wo56.business.ord.vo.out;

import org.apache.commons.lang.StringUtils;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.framework.core.util.DateUtil;
/**
 * 新增中转的页面数据展示
 * 
 * @author liyiye
 *
 */
public class OrderOutgoingAddOut extends BaseOutParamVO{

	/** 订单号 */
	private long orderId;
	
	/** 运单号 */
	private String trackingNum;
	
	/*** 收货方 */
	private String consigneeLinkmanName;
	
	private String createDateString;
	
	/** 目的城市名 */
	private String destCityName;

	private String destCityNameAnddestCountyName;
	
	private String destCountyName;
	
	/** 配送城市名 */
	private String distributionOrgName;
	/***库存网点名称**/
	private String kcOrgName;
	
	/**入库时间*/
	private String stockInTime;
	
	/** 货物总数量 */
	private Integer count;
	
	/** 总体积 */
	private double volume;
	
	/** 总重量 */
	private double weight;
	
	private String consigneeTelephone;
	private String consigneeBill;
	
	private String address;
	
	/** 发货方 */
	private String consignorLinkmanName;
	/*** 发货人联系电话 */
	private String consignorTelephone;
	
	private String totalFeeString;
	
	/** 到付(元) */
	private String freightCollectString;
	
	/** 代收货款(元) */
	private String collectingMoneyString;
	
	private String installCostsString;
	
	/** 配送方式、交接方式 */
	private String deliveryTypeName;
	
	private String serviceTypeName;
	
	/** 回单号 */
	private String receiptNum;
	/** 回单份数 */
	private Integer receiptCount;
	
	/** 备注 */
	private String remarks;
	
	private Long taskId;
	
	/** 到付 */
	private Long freightCollect;
	
	/** 代收货款 */
	private Long collectingMoney;
	
	private String orgIdName;
	private String currentOrgIdName;
	private Double discountString;
	private String consignorName;
	private String consignorBill;
	
	public String getConsigneeBill() {
		return consigneeBill;
	}

	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
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

	public Double getDiscountString() {
		return discountString;
	}

	public void setDiscountString(Double discountString) {
		this.discountString = discountString;
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

	public String getCreateDateString() {
		if(createDate != null){
			setCreateDateString(DateUtil.formatDate(createDate, "yyyy-MM-dd HH:mm:ss"));
		}
		return createDateString;
	}

	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}
	
	public String getDestCityNameAnddestCountyName() {
		setDestCityNameAnddestCountyName(StringUtils.isNotEmpty(destCountyName) ? destCountyName : destCityName);
		return destCityNameAnddestCountyName;
	}
	public void setDestCityNameAnddestCountyName(
			String destCityNameAnddestCountyName) {
		this.destCityNameAnddestCountyName = destCityNameAnddestCountyName;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getTrackingNum() {
		return trackingNum;
	}

	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}

	public String getConsigneeLinkmanName() {
		return consigneeLinkmanName;
	}

	public void setConsigneeLinkmanName(String consigneeLinkmanName) {
		this.consigneeLinkmanName = consigneeLinkmanName;
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

	public String getDistributionOrgName() {
		return distributionOrgName;
	}

	public void setDistributionOrgName(String distributionOrgName) {
		this.distributionOrgName = distributionOrgName;
	}

	public String getKcOrgName() {
		return kcOrgName;
	}

	public void setKcOrgName(String kcOrgName) {
		this.kcOrgName = kcOrgName;
	}

	public String getStockInTime() {
		return stockInTime;
	}

	public void setStockInTime(String stockInTime) {
		this.stockInTime = stockInTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}

	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getTotalFeeString() {
		return totalFeeString;
	}

	public void setTotalFeeString(String totalFeeString) {
		this.totalFeeString = totalFeeString;
	}

	public String getFreightCollectString() {
		return freightCollectString;
	}

	public void setFreightCollectString(String freightCollectString) {
		this.freightCollectString = freightCollectString;
	}

	public String getCollectingMoneyString() {
		return collectingMoneyString;
	}

	public void setCollectingMoneyString(String collectingMoneyString) {
		this.collectingMoneyString = collectingMoneyString;
	}

	public String getInstallCostsString() {
		return installCostsString;
	}

	public void setInstallCostsString(String installCostsString) {
		this.installCostsString = installCostsString;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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
	
	
	
}
