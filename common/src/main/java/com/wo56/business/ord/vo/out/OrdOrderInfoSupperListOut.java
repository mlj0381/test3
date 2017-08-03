package com.wo56.business.ord.vo.out;

import com.wo56.common.utils.CommonUtil;


public class OrdOrderInfoSupperListOut  {

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
	private String goodsDesc; //货品描述  例如： 凳子 * 2 （凳子数量是2张）
	private String dateString;//时间描述字符串 ：格式：例如：开单时间：04-08 15:18
	private String buttomShowType; //APP按钮显示
	private String sfPhone; //师傅手机号码
	private String sfName;//师傅姓名
	private String deliveryType; //是否存在安装师傅 ：1存在2：不存在
	
	private String deliveryTypeName; //交接方式（APP展示）
	private String zxPhone; //专线公司联系电话
	
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}
	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}
	public String getZxPhone() {
		return zxPhone;
	}
	public void setZxPhone(String zxPhone) {
		this.zxPhone = zxPhone;
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
	public String getButtomShowType() {
		return buttomShowType;
	}
	public void setButtomShowType(String buttomShowType) {
		this.buttomShowType = buttomShowType;
	}
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
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	
}