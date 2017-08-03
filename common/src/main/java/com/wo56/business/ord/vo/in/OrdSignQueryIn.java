package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdSignQueryIn extends PageInParamVO  implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.QUERY_ORD_SIGN;
	}
	/**开始时间**/
	private String beginTime;
	/**结束时间**/
	private String endTime;
	/**订单号*/
	private long orderId;
	/**开单网点*/
	private Long orgId;
	/**配送网点*/
	private Long distributionOrgId;
	/**收货人－固话**/
	private String consigneeTelephone;
	/**收货人手机*/
	private String consigneeBill;
	/**配送方式、交接方式*/
	private Integer deliveryType;
	/**运单号*/
	private Long trackingNum;
	/**发货方－固话**/
	private String consignorTelephone;
	/**发货方－手机号**/
	private String consignorBill;
	/**货物名称**/
	private String goodsName;
	/**签收状态**/
	private int signStatus;
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public Long getDistributionOrgId() {
		return distributionOrgId;
	}
	public void setDistributionOrgId(Long distributionOrgId) {
		this.distributionOrgId = distributionOrgId;
	}
	public String getConsigneeBill() {
		return consigneeBill;
	}
	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}
	public Integer getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
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
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(int signStatus) {
		this.signStatus = signStatus;
	}
	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}
	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}
	
	
	
}
