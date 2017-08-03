package com.wo56.business.ac.vo;

import java.util.Date;

/**
 * AcPaymentMethod entity. @author MyEclipse Persistence Tools
 */

public class AcPaymentMethod implements java.io.Serializable {

	// Fields

	private Long id;
	private String bankHolder;
	private String idCard;
	private String bill;
	private String bankCard;
	private String bankName;
	private Long province;
	private String provinceName;
	private Long city;
	private String cityName;
	private Integer paymentType;
	private String accountNum;
	private String accountName;
	private Long userId;
	private Date createTime;
	private Integer state;
	private Date opDate;
	private String bankCode;

	// Constructors

	/** default constructor */
	public AcPaymentMethod() {
	}

	// Property accessors

	 

	public Long getId() {
		return this.id;
	}

	
	
	public AcPaymentMethod(Long id, String bankHolder, String idCard, String bill, String bankCard, String bankName,
			Long province, String provinceName, Long city, String cityName, Integer paymentType, String accountNum,
			String accountName, Long userId, Date createTime, Integer state, Date opDate, String bankCode) {
		super();
		this.id = id;
		this.bankHolder = bankHolder;
		this.idCard = idCard;
		this.bill = bill;
		this.bankCard = bankCard;
		this.bankName = bankName;
		this.province = province;
		this.provinceName = provinceName;
		this.city = city;
		this.cityName = cityName;
		this.paymentType = paymentType;
		this.accountNum = accountNum;
		this.accountName = accountName;
		this.userId = userId;
		this.createTime = createTime;
		this.state = state;
		this.opDate = opDate;
		this.bankCode = bankCode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankHolder() {
		return this.bankHolder;
	}

	public void setBankHolder(String bankHolder) {
		this.bankHolder = bankHolder;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getBill() {
		return this.bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public String getBankCard() {
		return this.bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getProvince() {
		return this.province;
	}

	public void setProvince(Long province) {
		this.province = province;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Long getCity() {
		return this.city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public String getAccountNum() {
		return this.accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	

}