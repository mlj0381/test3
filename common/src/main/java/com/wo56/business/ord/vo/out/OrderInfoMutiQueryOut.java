package com.wo56.business.ord.vo.out;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.framework.core.util.SysStaticDataUtil;
/**
 * 多条件查询的返回数据的
 * @author liyiye
 *
 */
public class OrderInfoMutiQueryOut{

	
	private static final long serialVersionUID = 1200002030408414345L;
    


	  private long orderId; //订单号
	  private Long trackingNum; //运单号
	  private Date createDate;//创建时间
	  private Integer count;//货物总数量
	  private String goodsName;//货品名称
	  private String consignorName;//发货人
	  private String consignorTelephone;//发货人联系电话
	  private String consigneeName;//收货人
	  private String consigneeTelephone;//收货人备用电话
	  private String consigneeBill;//收货人电话
	  private String seeOrderStateName;//运单展示状态
	  private String orgIdName; //开单网点
	  private String distributionOrgName;//达到网点
	  private String address;//收货地址详细地址
	  private Double freightString;//运费
	  private Double pickingCostsString;//提货费
	  private Double handingCostsString;//装卸费
	  private Double packingCostsString;//包装费
	  private Double deliveryCostsString;//送货费
	  private Double cashPaymentString;//现付金额
	  private Double freightCollectString;//到付金额
	  private Double receiptPaymentString;//回单付金额
	  private Double monthlyPaymentString;//月结金额
	  private Double discountString;//回扣金额
	  private String installCost;//回扣金额
	  private Double collectingMoneyString;//代收货款金额
	  private Double procedureFeeString;//代收货款手续费
	  private String paymentTypeName;// 支付方式1
	  private String carrierCompanyName;//承运公司
	  private String outgoingTrackingNum;//外发单号
	  private Double outgoingFeeString;//外发费用
	  private String linkerName;//本地联系人
	  private String linkerPhone;//联系电话
	  private String deliveryPhone;//提货电话
	  private String deliveryAddress;//提货地址
	  private String provinceName;
	  private String cityName;
	  private String countyName;
	  private String streetName;
	  private Double volume;//体积
	  private String isSeaTransportName;//是否海运
	  private Integer isSeaTransport;
	  
		public Integer getIsSeaTransport() {
			return isSeaTransport;
		}
		public void setIsSeaTransport(Integer isSeaTransport) {
			this.isSeaTransport = isSeaTransport;
		}
		public String getIsSeaTransportName() {
			if(isSeaTransport != null){
				setIsSeaTransportName(SysStaticDataUtil.getSysStaticDataCodeName("TRANSPORT_TYPE", isSeaTransport+""));
			}
			return isSeaTransportName;
		}
		public void setIsSeaTransportName(String isSeaTransportName) {
			this.isSeaTransportName = isSeaTransportName;
		}
    
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	private String destCityNameAnddestCountyName;  
	public String getDestCityNameAnddestCountyName() {
		setDestCityNameAnddestCountyName(StringUtils.isNotEmpty(countyName) ? countyName : cityName);
		return destCityNameAnddestCountyName;
	}
	public void setDestCityNameAnddestCountyName(
			String destCityNameAnddestCountyName) {
		this.destCityNameAnddestCountyName = destCityNameAnddestCountyName;
	}
	  
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getInstallCost() {
		return installCost;
	}
	public void setInstallCost(String installCost) {
		this.installCost = installCost;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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
	public String getSeeOrderStateName() {
		return seeOrderStateName;
	}
	public void setSeeOrderStateName(String seeOrderStateName) {
		this.seeOrderStateName = seeOrderStateName;
	}
	public String getOrgIdName() {
		return orgIdName;
	}
	public void setOrgIdName(String orgIdName) {
		this.orgIdName = orgIdName;
	}
	public String getDistributionOrgName() {
		return distributionOrgName;
	}
	public void setDistributionOrgName(String distributionOrgName) {
		this.distributionOrgName = distributionOrgName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getFreightString() {
		return freightString;
	}
	public void setFreightString(Double freightString) {
		this.freightString = freightString;
	}
	public Double getPickingCostsString() {
		return pickingCostsString;
	}
	public void setPickingCostsString(Double pickingCostsString) {
		this.pickingCostsString = pickingCostsString;
	}
	public Double getHandingCostsString() {
		return handingCostsString;
	}
	public void setHandingCostsString(Double handingCostsString) {
		this.handingCostsString = handingCostsString;
	}
	public Double getPackingCostsString() {
		return packingCostsString;
	}
	public void setPackingCostsString(Double packingCostsString) {
		this.packingCostsString = packingCostsString;
	}
	public Double getDeliveryCostsString() {
		return deliveryCostsString;
	}
	public void setDeliveryCostsString(Double deliveryCostsString) {
		this.deliveryCostsString = deliveryCostsString;
	}
	public Double getCashPaymentString() {
		return cashPaymentString;
	}
	public void setCashPaymentString(Double cashPaymentString) {
		this.cashPaymentString = cashPaymentString;
	}
	public Double getFreightCollectString() {
		return freightCollectString;
	}
	public void setFreightCollectString(Double freightCollectString) {
		this.freightCollectString = freightCollectString;
	}
	public Double getReceiptPaymentString() {
		return receiptPaymentString;
	}
	public void setReceiptPaymentString(Double receiptPaymentString) {
		this.receiptPaymentString = receiptPaymentString;
	}
	public Double getMonthlyPaymentString() {
		return monthlyPaymentString;
	}
	public void setMonthlyPaymentString(Double monthlyPaymentString) {
		this.monthlyPaymentString = monthlyPaymentString;
	}
	public Double getDiscountString() {
		return discountString;
	}
	public void setDiscountString(Double discountString) {
		this.discountString = discountString;
	}
	public Double getCollectingMoneyString() {
		return collectingMoneyString;
	}
	public void setCollectingMoneyString(Double collectingMoneyString) {
		this.collectingMoneyString = collectingMoneyString;
	}
	public Double getProcedureFeeString() {
		return procedureFeeString;
	}
	public void setProcedureFeeString(Double procedureFeeString) {
		this.procedureFeeString = procedureFeeString;
	}
	public String getPaymentTypeName() {
		return paymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}
	public String getCarrierCompanyName() {
		return carrierCompanyName;
	}
	public void setCarrierCompanyName(String carrierCompanyName) {
		this.carrierCompanyName = carrierCompanyName;
	}
	public String getOutgoingTrackingNum() {
		return outgoingTrackingNum;
	}
	public void setOutgoingTrackingNum(String outgoingTrackingNum) {
		this.outgoingTrackingNum = outgoingTrackingNum;
	}
	
	public Double getOutgoingFeeString() {
		return outgoingFeeString;
	}
	public void setOutgoingFeeString(Double outgoingFeeString) {
		this.outgoingFeeString = outgoingFeeString;
	}
	public String getLinkerName() {
		return linkerName;
	}
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}
	public String getLinkerPhone() {
		return linkerPhone;
	}
	public void setLinkerPhone(String linkerPhone) {
		this.linkerPhone = linkerPhone;
	}
	public String getDeliveryPhone() {
		return deliveryPhone;
	}
	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getConsigneeBill() {
		return consigneeBill;
	}
	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}
	  
	  
	  
}