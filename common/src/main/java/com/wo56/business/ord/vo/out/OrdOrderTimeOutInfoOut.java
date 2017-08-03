package com.wo56.business.ord.vo.out;

import com.wo56.common.utils.CommonUtil;


public class OrdOrderTimeOutInfoOut  {

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
	private String sfPhone; //师傅手机号码
	private String sfName;//师傅姓名
	private String deliveryType; //是否存在安装师傅 ：1存在2：不存在
	private String stringDate; //时间(格式：04-16 21:30 )（开单时间、发车时间、到货时间）
	
	private String zxPhone; //专线电话（联系电话（联运汇客服、提醒发车、发车网点））
	private String timeOutNum; //超时时间（8.5h）
	private String pickupMan;
	private String pickupTel;
	private String pickupAddress;
	private String stere;
	private String weight;
	private String gcount;
	private String pickupDate;//预约时间(格式：04-16 上午 )
	
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getStringDate() {
		return stringDate;
	}
	public void setStringDate(String stringDate) {
		this.stringDate = stringDate;
	}
	public String getZxPhone() {
		return zxPhone;
	}
	public void setZxPhone(String zxPhone) {
		this.zxPhone = zxPhone;
	}
	public String getTimeOutNum() {
		return timeOutNum;
	}
	public void setTimeOutNum(String timeOutNum) {
		this.timeOutNum = timeOutNum;
	}
	public String getPickupMan() {
		return pickupMan;
	}
	public void setPickupMan(String pickupMan) {
		this.pickupMan = pickupMan;
	}
	public String getPickupTel() {
		return pickupTel;
	}
	public void setPickupTel(String pickupTel) {
		this.pickupTel = pickupTel;
	}
	public String getPickupAddress() {
		return pickupAddress;
	}
	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}
	public String getStere() {
		return stere;
	}
	public void setStere(String stere) {
		this.stere = stere;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getGcount() {
		return gcount;
	}
	public void setGcount(String gcount) {
		this.gcount = gcount;
	}
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	
	
	
}