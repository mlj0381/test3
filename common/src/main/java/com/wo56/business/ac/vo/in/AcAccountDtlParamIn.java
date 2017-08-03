package com.wo56.business.ac.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class AcAccountDtlParamIn extends PageInParamVO implements IParamIn {

	private long orderId;
	private String inCode;
	private Long trackingNum;
	private String beginDate;
	private String endDate;
	private String orgId;
	private String feeType;
	private String payType;
	private String checkSts;
	private String tenantId;
	private String consignorName;
	private String paymentType;
	
	private String beginDateSign;
	private String endDateSign;
	private String sfName;
	private String sfPhone;
	private  int orderState;
	private long provinceId;
	private long cityId;
	private long countyId;
	private long streetId;
	private long distributionOrgId;
	
	private String beginDistributionTime;//分配时间
	private String endDistributionTime;
	
	
	
	public long getProvinceId() {
		return provinceId;
	}


	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}


	public long getCityId() {
		return cityId;
	}


	public void setCityId(long cityId) {
		this.cityId = cityId;
	}


	public long getCountyId() {
		return countyId;
	}


	public void setCountyId(long countyId) {
		this.countyId = countyId;
	}


	public long getStreetId() {
		return streetId;
	}


	public void setStreetId(long streetId) {
		this.streetId = streetId;
	}


	public long getDistributionOrgId() {
		return distributionOrgId;
	}


	public void setDistributionOrgId(long distributionOrgId) {
		this.distributionOrgId = distributionOrgId;
	}


	public String getBeginDateSign() {
		return beginDateSign;
	}


	public void setBeginDateSign(String beginDateSign) {
		this.beginDateSign = beginDateSign;
	}


	public String getEndDateSign() {
		return endDateSign;
	}


	public void setEndDateSign(String endDateSign) {
		this.endDateSign = endDateSign;
	}


	public String getSfName() {
		return sfName;
	}


	public void setSfName(String sfName) {
		this.sfName = sfName;
	}


	public String getSfPhone() {
		return sfPhone;
	}


	public void setSfPhone(String sfPhone) {
		this.sfPhone = sfPhone;
	}


	public int getOrderState() {
		return orderState;
	}


	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}


	public String getPaymentType() {
		return paymentType;
	}


	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}


	public String getConsignorName() {
		return consignorName;
	}


	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}


	public String getTenantId() {
		return tenantId;
	}


	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}


	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return this.inCode;
	}
	
	
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}


	public AcAccountDtlParamIn(String inCode) {
		super();
		this.inCode = inCode;
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


	public String getBeginDate() {
		return beginDate;
	}


	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	public String getFeeType() {
		return feeType;
	}


	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}


	public String getPayType() {
		return payType;
	}


	public void setPayType(String payType) {
		this.payType = payType;
	}


	public String getCheckSts() {
		return checkSts;
	}


	public void setCheckSts(String checkSts) {
		this.checkSts = checkSts;
	}


	public String getBeginDistributionTime() {
		return beginDistributionTime;
	}


	public void setBeginDistributionTime(String beginDistributionTime) {
		this.beginDistributionTime = beginDistributionTime;
	}


	public String getEndDistributionTime() {
		return endDistributionTime;
	}


	public void setEndDistributionTime(String endDistributionTime) {
		this.endDistributionTime = endDistributionTime;
	}

	
	 
}
