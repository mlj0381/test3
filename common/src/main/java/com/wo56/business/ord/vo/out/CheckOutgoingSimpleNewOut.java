package com.wo56.business.ord.vo.out;

import java.util.Date;

import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;

public class CheckOutgoingSimpleNewOut{
	private String[] noFiled; //翻译过后前端不需要的字段
	  public String[] getNoFiled() {
		String[]  noFileds = new String[]{"outgoingFee","collectingMoney","freightCollect",
				"checkFee","checkAmount","withoutAmount","discount","sellerTenantId"};
		setNoFiled(noFileds);
		return noFiled;
	 }
	 public void setNoFiled(String[] noFiled) {
		this.noFiled = noFiled;
	}
	 private String checkOpName;
	public String getCheckOpName() {
		return checkOpName;
	}
	public void setCheckOpName(String checkOpName) {
		this.checkOpName = checkOpName;
	}
	private Long checkId;//核销ID
	public Long getCheckId() {
		return checkId;
	}
	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}
	private String seeOrderStateName;//运单状态
	private Integer seeOrderState;
	private String tranferDate;//中转时间
	private Long trackingNum;
	private Long orderId;//内部订单号
	private Long carrierCompanyId;//承运商
	private String carrierCompanyName;//承运商名称.
	private Long outgoingTrackingNum;//外发单号
	private String linkerName;//本地联系人
	private String linkerPhone;//联系电话
	private String deliveryPhone;//提货电话
	private String deliveryAddress;//提货地址
	
	private Long outgoingFee;//外发费
	private Long collectingMoney;//代收货款
	private Long freightCollect;//到付款 
	private Long checkFee;// 核销金额
	private Long checkAmount; // 已核销金额
	private Long withoutAmount; // 未核销金额
	private Long discount; //回扣
	private Double outgoingFeeDouble;//外发费
	private Double checkAmountDouble; // 已核销金额
	private Double withoutAmountDouble; // 未核销金额
	private Double collectingMoneyDouble;//代收货款
	private Double freightCollectDouble;//到付款
	private Double checkFeeDouble;// 核销金额
	private Double discountDouble; //回扣

	public Double getDiscountDouble() {
		setDiscountDouble(CommonUtil.getDoubleFormatLongMoney(discount,2));
		return discountDouble;
	}
	public void setDiscountDouble(Double discountDouble) {
		this.discountDouble = discountDouble;
	}
	public Double getOutgoingFeeDouble() {
		setOutgoingFeeDouble(CommonUtil.getDoubleFormatLongMoney(outgoingFee,2));
		return outgoingFeeDouble;
	}
	public void setOutgoingFeeDouble(Double outgoingFeeDouble) {
		this.outgoingFeeDouble = outgoingFeeDouble;
	}
	public Double getCheckAmountDouble() {
		setCheckAmountDouble(CommonUtil.getDoubleFormatLongMoney(checkAmount,2));
		return checkAmountDouble;
	}
	public void setCheckAmountDouble(Double checkAmountDouble) {
		this.checkAmountDouble = checkAmountDouble;
	}
	public Double getWithoutAmountDouble() {
		setWithoutAmountDouble(CommonUtil.getDoubleFormatLongMoney(withoutAmount,2));
		return withoutAmountDouble;
	}
	public void setWithoutAmountDouble(Double withoutAmountDouble) {
		this.withoutAmountDouble = withoutAmountDouble;
	}
	public Double getCollectingMoneyDouble() {
		setCollectingMoneyDouble(CommonUtil.getDoubleFormatLongMoney(collectingMoney,2));
		return collectingMoneyDouble;
	}
	public void setCollectingMoneyDouble(Double collectingMoneyDouble) {
		this.collectingMoneyDouble = collectingMoneyDouble;
	}
	public Double getFreightCollectDouble() {
		setFreightCollectDouble(CommonUtil.getDoubleFormatLongMoney(freightCollect,2));
		return freightCollectDouble;
	}
	public void setFreightCollectDouble(Double freightCollectDouble) {
		this.freightCollectDouble = freightCollectDouble;
	}
	public Double getCheckFeeDouble() {
		setCheckFeeDouble(CommonUtil.getDoubleFormatLongMoney(checkFee,2));
		return checkFeeDouble;
	}
	public void setCheckFeeDouble(Double checkFeeDouble) {
		this.checkFeeDouble = checkFeeDouble;
	}
	private Date createDate;//创建时间
	private int checkSts;//核销状态 1、已核销;2、未核销
	private String checkStsName;//核销状态
	private Date checkDate;//核销时间 
	private String currentOrgIdName;

	private String remarks;//运单备注
	
	private String inputUserName;//制单人
	private String consignorName;//发货人
	private String consignorBill;//发货人联系手机
	 
	private String consigneeName;//收货人 
	private String consigneeBill;//收货人手机
	private String consigneeTelephone;//收货人电话
	
	
	private String products;//货品名称
	private String goodsNumber;//货号
	private Double weight; //总重量
	private Double volume;//总体积
	private Integer count;//总件数
	
	
	private Long sellerTenantId; //发货商家
	private String  sellerTenantIdName;//发货商家名称
	
	public String getSeeOrderStateName() {
		return seeOrderStateName;
	}
	public void setSeeOrderStateName(String seeOrderStateName) {
		this.seeOrderStateName = seeOrderStateName;
	}
	public Integer getSeeOrderState() {
		return seeOrderState;
	}
	public void setSeeOrderState(Integer seeOrderState) {
		this.seeOrderState = seeOrderState;
	}
	public String getTranferDate() {
		return tranferDate;
	}
	public void setTranferDate(String tranferDate) {
		this.tranferDate = tranferDate;
	}
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getCarrierCompanyId() {
		return carrierCompanyId;
	}
	public void setCarrierCompanyId(Long carrierCompanyId) {
		this.carrierCompanyId = carrierCompanyId;
	}
	public String getCarrierCompanyName() {
		return carrierCompanyName;
	}
	public void setCarrierCompanyName(String carrierCompanyName) {
		this.carrierCompanyName = carrierCompanyName;
	}
	public Long getOutgoingTrackingNum() {
		return outgoingTrackingNum;
	}
	public void setOutgoingTrackingNum(Long outgoingTrackingNum) {
		this.outgoingTrackingNum = outgoingTrackingNum;
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
	public Long getOutgoingFee() {
		return outgoingFee;
	}
	public void setOutgoingFee(Long outgoingFee) {
		this.outgoingFee = outgoingFee;
	}
	public Long getCollectingMoney() {
		return collectingMoney;
	}
	public void setCollectingMoney(Long collectingMoney) {
		this.collectingMoney = collectingMoney;
	}
	public Long getFreightCollect() {
		return freightCollect;
	}
	public void setFreightCollect(Long freightCollect) {
		this.freightCollect = freightCollect;
	}
	public Long getCheckFee() {
		return checkFee;
	}
	public void setCheckFee(Long checkFee) {
		this.checkFee = checkFee;
	}
	public Long getCheckAmount() {
		return checkAmount;
	}
	public void setCheckAmount(Long checkAmount) {
		this.checkAmount = checkAmount;
	}
	public Long getWithoutAmount() {
		return withoutAmount;
	}
	public void setWithoutAmount(Long withoutAmount) {
		this.withoutAmount = withoutAmount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getCheckSts() {
		return checkSts;
	}
	public void setCheckSts(int checkSts) {
		this.checkSts = checkSts;
	}
	public String getCheckStsName() {
		setCheckStsName(SysStaticDataUtil.getSysStaticDataCodeName("AC_CASH_PROVE@CHECK_STS", checkSts+""));
		return checkStsName;
	}
	public void setCheckStsName(String checkStsName) {
		this.checkStsName = checkStsName;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getCurrentOrgIdName() {
		return currentOrgIdName;
	}
	public void setCurrentOrgIdName(String currentOrgIdName) {
		this.currentOrgIdName = currentOrgIdName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getInputUserName() {
		return inputUserName;
	}
	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
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
	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}
	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Long getDiscount() {
		return discount;
	}
	public void setDiscount(Long discount) {
		this.discount = discount;
	}
	public Long getSellerTenantId() {
	
		return sellerTenantId;
	}
	public void setSellerTenantId(Long sellerTenantId) {
		this.sellerTenantId = sellerTenantId;
	}
	public String getSellerTenantIdName() {
		if(sellerTenantId != null){
			setSellerTenantIdName(CommonUtil.getTennatNameById(sellerTenantId));
		}
		return sellerTenantIdName;
	}
	public void setSellerTenantIdName(String sellerTenantIdName) {
		this.sellerTenantIdName = sellerTenantIdName;
	}
	
}
