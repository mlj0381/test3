package com.wo56.business.ord.vo.out;

import java.util.List;

import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ord.vo.OrderTipRuleExt;

public class OrderFeeTipOut {
	private Long id;
	private String ruleName;
	private Long tenantId;
	private String tenantName;
	private String staProvinceName;
	private String staCityName;
	private String desProvinceName;
	private String desCityName;
	private Integer tipType;
	private String tipTypeName;
	private Long staFee;
	private String staFeeName;
	private Long topFee;
	private String topFeeName;
	private String first;
	private Long addWeight;
	private String addWeightName;
	private String percentage;
	private List<OrderTipRuleExt> orderTipList;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getStaProvinceName() {
		return staProvinceName;
	}
	public void setStaProvinceName(String staProvinceName) {
		this.staProvinceName = staProvinceName;
	}
	public String getStaCityName() {
		return staCityName;
	}
	public void setStaCityName(String staCityName) {
		this.staCityName = staCityName;
	}
	public String getDesProvinceName() {
		return desProvinceName;
	}
	public void setDesProvinceName(String desProvinceName) {
		this.desProvinceName = desProvinceName;
	}
	public String getDesCityName() {
		return desCityName;
	}
	public void setDesCityName(String desCityName) {
		this.desCityName = desCityName;
	}
	public Integer getTipType() {
		return tipType;
	}
	public void setTipType(Integer tipType) {
		this.tipType = tipType;
	}
	public String getTipTypeName() {
		return tipTypeName;
	}
	public void setTipTypeName(String tipTypeName) {
		this.tipTypeName = tipTypeName;
	}
	public Long getStaFee() {
		return staFee;
	}
	public void setStaFee(Long staFee) {
		this.staFee = staFee;
	}
	public String getStaFeeName() {
		return staFeeName;
	}
	public void setStaFeeName(String staFeeName) {
		this.staFeeName = staFeeName;
	}
	public Long getTopFee() {
		return topFee;
	}
	public void setTopFee(Long topFee) {
		this.topFee = topFee;
	}
	public String getTopFeeName() {
		return topFeeName;
	}
	public void setTopFeeName(String topFeeName) {
		this.topFeeName = topFeeName;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public Long getAddWeight() {
		return addWeight;
	}
	public void setAddWeight(Long addWeight) {
		this.addWeight = addWeight;
	}
	public String getAddWeightName() {
		return addWeightName;
	}
	public void setAddWeightName(String addWeightName) {
		this.addWeightName = addWeightName;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public List<OrderTipRuleExt> getOrderTipList() {
		return orderTipList;
	}
	public void setOrderTipList(List<OrderTipRuleExt> orderTipList) {
		this.orderTipList = orderTipList;
	}
	
}
