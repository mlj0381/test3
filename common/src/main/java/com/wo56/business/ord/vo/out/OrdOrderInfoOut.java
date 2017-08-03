package com.wo56.business.ord.vo.out;

import java.util.List;
import java.util.Map;

import com.wo56.common.utils.CommonUtil;

public class OrdOrderInfoOut  {

	private String companyName;
	private String trackingNum;
	private String sellerOrderId;
	private String orderStateName;
	private String totalFee;
	private List<Map<String,String>> logItems;
	private String receiverTel;
	private String receiverName;
	private String receiverAddress;
	private String isTmail;//表示天猫字段:是否是天猫表示  1是 2否
	private List<Map<String,String>> goodItems;
	private String remarks;
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
	public List<Map<String, String>> getLogItems() {
		return logItems;
	}
	public void setLogItems(List<Map<String, String>> logItems) {
		this.logItems = logItems;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<Map<String, String>> getGoodItems() {
		return goodItems;
	}
	public void setGoodItems(List<Map<String, String>> goodItems) {
		this.goodItems = goodItems;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

	
	 

}