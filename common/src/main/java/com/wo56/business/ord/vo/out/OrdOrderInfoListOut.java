package com.wo56.business.ord.vo.out;

import com.wo56.common.utils.CommonUtil;


public class OrdOrderInfoListOut  {

	private String orderId; //详情查询编号
	private String trackingNum; //运单号
	private String sellerOrderId;//订单号
	private String orderStateName;//订单状态
	private String totalFee;//费用
	private String companyName;//公司名称
	private String receiverTel;//收货联系手机
	private String receiverName;//收货联系人
	private String receiverAddress;//收货地址
	private String isTmail;// 是否是天猫表示  1是 2否
	private String remarks; //备注
	private String goodsDesc; //货品描述  例如： 凳子 * 2 （凳子数量是2张）
	private String createDate;//开单时间
	private String orderTime;//开单时间
	private String departDate;//发车时间
	private String goodsArraiveDate;//到货时间
	private String sfPhone; //师傅手机号码
	private String sfName;//师傅姓名
	private String pickupDate;//预约时间
	private String deliveryType; //是否存在安装师傅 ：1存在2：不存在
	private String signDate; //签收时间
	private String zxPhone; //专线电话
	
	public String getZxPhone() {
		return zxPhone;
	}
	public void setZxPhone(String zxPhone) {
		this.zxPhone = zxPhone;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getGoodsArraiveDate() {
		return goodsArraiveDate;
	}
	public void setGoodsArraiveDate(String goodsArraiveDate) {
		this.goodsArraiveDate = goodsArraiveDate;
	}
	public String getDepartDate() {
		return departDate;
	}
	public void setDepartDate(String departDate) {
		this.departDate = departDate;
	}
	public String getSfPhone() {
		return sfPhone;
	}
	public void setSfPhone(String sfPhone) {
		this.sfPhone = sfPhone;
	}
	public String getSfName() {
		return sfName;
	}
	public void setSfName(String sfName) {
		this.sfName = sfName;
	}
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getSellerOrderId() {
		return sellerOrderId;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public void setSellerOrderId(String sellerOrderId) {
		this.sellerOrderId = sellerOrderId;
	}
	public String getOrderStateName() {
		return orderStateName;
	}
	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}
	public String getTotalFee() {
		setTotalFee( CommonUtil.getDoubleFixedNew2(totalFee).equals("0.00") ? "": "￥"+ CommonUtil.getDoubleFixedNew2(totalFee) );
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getReceiverTel() {
		return receiverTel;
	}
	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getIsTmail() {
		return isTmail;
	}
	public void setIsTmail(String isTmail) {
		this.isTmail = isTmail;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
	 
}