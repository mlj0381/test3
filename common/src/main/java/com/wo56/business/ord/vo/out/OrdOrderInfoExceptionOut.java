package com.wo56.business.ord.vo.out;


public class OrdOrderInfoExceptionOut  {

	private String exceptionId;//异常编号
	private String trackingNum; //运单号
	private String sellerOrderId;//订单号
	private String exceptionTypeName ;//异常类型名称
	private String companyName;//公司名称
	private String receiverTel;//收货联系手机
	private String receiverName;//收货联系人
	private String receiverAddress;//收货地址
	private String notes; //异常描述
	private String goodsDesc; //货品描述  例如： 凳子 * 2 （凳子数量是2张）
	private String createDate;//异常创建时间
	private String handleResult; //处理结果
	private String handleDate; //处理时间
	
	private String imgPathUrls; // 多个以多个逗号隔开
	private String stateName; //异常状态名称
	
	private String pickupTel;//提货电话
	private String pickupAddress;//提货地址
	private String dutyDate;//任务时间
	private String state;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getDutyDate() {
		return dutyDate;
	}
	public void setDutyDate(String dutyDate) {
		this.dutyDate = dutyDate;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getImgPathUrls() {
		return imgPathUrls;
	}
	public void setImgPathUrls(String imgPathUrls) {
		this.imgPathUrls = imgPathUrls;
	}
	public String getHandleResult() {
		return handleResult;
	}
	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}
	public String getExceptionId() {
		return exceptionId;
	}
	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
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
	public String getExceptionTypeName() {
		return exceptionTypeName;
	}
	public void setExceptionTypeName(String exceptionTypeName) {
		this.exceptionTypeName = exceptionTypeName;
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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
	public String getHandleDate() {
		return handleDate;
	}
	public void setHandleDate(String handleDate) {
		this.handleDate = handleDate;
	}
	
}