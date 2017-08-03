package com.wo56.business.ac.vo;

import java.io.Serializable;
import java.util.Date;

import com.wo56.common.utils.CommonUtil;

@SuppressWarnings("serial")
public class AcMyWalletHis implements Serializable{
	private Long id;
	private Long userId;
	private Date createTime;
	private Date applyTime;
	private Date showTime;
	private Long orderId;
	private Long amount;
	private Integer amountState;
	private Integer state;
	private Long province;
	private String provinceName;
	private String consignor;
	private String consignorPhone;
	private Date orderTime;
	private Long accountId;
	private String orderNumber;
	private Long tenantId;
	private String amountString;
	private Long hisWalletId;
	
	
	public Long getHisWalletId() {
		return hisWalletId;
	}
	public void setHisWalletId(Long hisWalletId) {
		this.hisWalletId = hisWalletId;
	}
	public String getAmountString() {
		if(amount != null && amount > 0){
			return String.valueOf(CommonUtil.parseDouble(amount));
		}else{
			return "0.00";
		}
	}
	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public AcMyWalletHis(){
		
	}
	public AcMyWalletHis(Long id, Long userId, Date createTime, Date applyTime,
			Date showTime, Long orderId, Long amount, Integer amountState,
			Integer state, Long province, String provinceName,
			String consignor, String consignorPhone, Date orderTime,
			Long acountId) {
		super();
		this.id = id;
		this.userId = userId;
		this.createTime = createTime;
		this.applyTime = applyTime;
		this.showTime = showTime;
		this.orderId = orderId;
		this.amount = amount;
		this.amountState = amountState;
		this.state = state;
		this.province = province;
		this.provinceName = provinceName;
		this.consignor = consignor;
		this.consignorPhone = consignorPhone;
		this.orderTime = orderTime;
		this.accountId = accountId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
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
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getShowTime() {
		return showTime;
	}
	public void setShowTime(Date showTime) {
		this.showTime = showTime;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Integer getAmountState() {
		return amountState;
	}
	public void setAmountState(Integer amountState) {
		this.amountState = amountState;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getProvince() {
		return province;
	}
	public void setProvince(Long province) {
		this.province = province;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getConsignor() {
		return consignor;
	}
	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}
	public String getConsignorPhone() {
		return consignorPhone;
	}
	public void setConsignorPhone(String consignorPhone) {
		this.consignorPhone = consignorPhone;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long acountId) {
		this.accountId = acountId;
	}
	

}
