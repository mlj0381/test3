package com.wo56.business.ord.vo.in;

import java.util.Date;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class OrdSellerNotifyIn extends PageInParamVO implements IParamIn{
	private String inCode;
	private Long notifyId;
	private String prodAbout;
	private String stere;
	private String weight;
	private Integer gcount;
	private String goodsRemark;
	private Date pickupDate;
	private Integer periodType;
	private String pickupPeriod;
	private Long orgId;
	private String orgName;
	private String orgContactor;
	private String orgTel;
	private String orgAddress;
	private Integer lineType;
	private Long companyOrgId;
	private Long tenantId;
	private String companyContactor;
	private String companyTel;
	private Integer state;
	private String platenumber;
	private String driverPhone;
	private String contactor;
	private String contactorTel;
	private String createDate;
	private Long opId;
	private String opName;
	private String opDate;
	private String finishDate;
	private String notifyIds;
	
	public String getNotifyIds() {
		return notifyIds;
	}
	public void setNotifyIds(String notifyIds) {
		this.notifyIds = notifyIds;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public Long getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(Long notifyId) {
		this.notifyId = notifyId;
	}
	public String getProdAbout() {
		return prodAbout;
	}
	public void setProdAbout(String prodAbout) {
		this.prodAbout = prodAbout;
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
	public Integer getGcount() {
		return gcount;
	}
	public void setGcount(Integer gcount) {
		this.gcount = gcount;
	}
	public String getGoodsRemark() {
		return goodsRemark;
	}
	public void setGoodsRemark(String goodsRemark) {
		this.goodsRemark = goodsRemark;
	}
	public Date getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}
	public Integer getPeriodType() {
		return periodType;
	}
	public void setPeriodType(Integer periodType) {
		this.periodType = periodType;
	}
	public String getPickupPeriod() {
		return pickupPeriod;
	}
	public void setPickupPeriod(String pickupPeriod) {
		this.pickupPeriod = pickupPeriod;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgContactor() {
		return orgContactor;
	}
	public void setOrgContactor(String orgContactor) {
		this.orgContactor = orgContactor;
	}
	public String getOrgTel() {
		return orgTel;
	}
	public void setOrgTel(String orgTel) {
		this.orgTel = orgTel;
	}
	public String getOrgAddress() {
		return orgAddress;
	}
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	public Integer getLineType() {
		return lineType;
	}
	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}
	public Long getCompanyOrgId() {
		return companyOrgId;
	}
	public void setCompanyOrgId(Long companyOrgId) {
		this.companyOrgId = companyOrgId;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public String getCompanyContactor() {
		return companyContactor;
	}
	public void setCompanyContactor(String companyContactor) {
		this.companyContactor = companyContactor;
	}
	public String getCompanyTel() {
		return companyTel;
	}
	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getPlatenumber() {
		return platenumber;
	}
	public void setPlatenumber(String platenumber) {
		this.platenumber = platenumber;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getContactor() {
		return contactor;
	}
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	public String getContactorTel() {
		return contactorTel;
	}
	public void setContactorTel(String contactorTel) {
		this.contactorTel = contactorTel;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getOpDate() {
		return opDate;
	}
	public void setOpDate(String opDate) {
		this.opDate = opDate;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	private String notes;
	
	public OrdSellerNotifyIn(String inCode) {
		this.inCode=inCode;
	}
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	
}
