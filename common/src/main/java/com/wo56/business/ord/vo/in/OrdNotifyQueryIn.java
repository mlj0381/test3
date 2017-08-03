package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdNotifyQueryIn extends PageInParamVO implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.QUERY_ORD_NOTIFY;
	}
	/**开始时间**/
	private String beginTime;
	/**结束时间**/
	private String endTime;
	/**订单号*/
	private long orderId;
	/**配送网点*/
	private Long distributionOrgId;
	/**收货人电话*/
	private String consigneeBill;
	/**配送方式、交接方式*/
	private int deliveryType;
	/**运单号*/
	private Long trackingNum;
	/**货物名称**/
	private String goodsName;
	/**通知状态**/
	private int type;
	/**预约送货时间**/
	private String schedulingDate;
	/**等放货通知**/
	private int shipmentNotice;
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
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getSchedulingDate() {
		return schedulingDate;
	}
	public void setSchedulingDate(String schedulingDate) {
		this.schedulingDate = schedulingDate;
	}
	public int getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(int deliveryType) {
		this.deliveryType = deliveryType;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getShipmentNotice() {
		return shipmentNotice;
	}
	public void setShipmentNotice(int shipmentNotice) {
		this.shipmentNotice = shipmentNotice;
	}
	@Override
	public String toString() {
		return "OrdNotifyQueryIn [beginTime=" + beginTime + ", endTime="
				+ endTime + ", orderId=" + orderId + ", distributionOrgId="
				+ distributionOrgId + ", consigneeBill=" + consigneeBill
				+ ", deliveryType=" + deliveryType + ", trackingNum="
				+ trackingNum + ", goodsName=" + goodsName + ", type=" + type
				+ ", schedulingDate=" + schedulingDate + ", shipmentNotice="
				+ shipmentNotice + "]";
	}
	
	
}
