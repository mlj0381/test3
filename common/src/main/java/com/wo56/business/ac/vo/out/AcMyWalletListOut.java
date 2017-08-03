package com.wo56.business.ac.vo.out;

import java.util.ArrayList;
import java.util.List;

public class AcMyWalletListOut {
	private String id;
	private String amountString;
	private String orderNumber;
	private String provinceName;
	private String cityName;
	private String createTime;
	private List<String> consignorList;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAmountString() {
		return amountString;
	}
	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public List<String> getConsignorList() {
		if(consignorList == null){
			consignorList = new ArrayList<String>();
			String str = "发货人：";
			consignorList.add(str);
			return consignorList;
		}
		return consignorList;
	}
	public void setConsignorList(List<String> consignorList) {
		this.consignorList = consignorList;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
