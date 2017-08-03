package com.wo56.business.sche.cash.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class ServiceTaskQueryParamOut extends BaseOutParamVO {
	
	private String companyName;
	private String phone;
	private String trackingNum;
	private String serviceType;
	private String feeName;
	private String balance;
	private String signDate;
	private String applicationTime;
	private String applicationPerson;
	private String authDate;
	private String autyPerson;
	private String authNotes;
	
	private String verityId;
	private String appId;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public String getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}
	public String getApplicationPerson() {
		return applicationPerson;
	}
	public void setApplicationPerson(String applicationPerson) {
		this.applicationPerson = applicationPerson;
	}
	public String getAuthDate() {
		return authDate;
	}
	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}
	public String getAutyPerson() {
		return autyPerson;
	}
	public void setAutyPerson(String autyPerson) {
		this.autyPerson = autyPerson;
	}
	public String getAuthNotes() {
		return authNotes;
	}
	public void setAuthNotes(String authNotes) {
		this.authNotes = authNotes;
	}
	public String getVerityId() {
		return verityId;
	}
	public void setVerityId(String verityId) {
		this.verityId = verityId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}

}
