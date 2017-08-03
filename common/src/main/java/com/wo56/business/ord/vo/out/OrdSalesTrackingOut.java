package com.wo56.business.ord.vo.out;

import java.util.Date;

import com.framework.core.util.DateUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;


public class OrdSalesTrackingOut{
	private String[] noFiled; // 翻译过后前端不需要的字段

	public String[] getNoFiled() {
		String[] noFileds = new String[] {"trackDate","createDate","tenantId","opId","orgId"};

		setNoFiled(noFileds);
		return noFiled;
	}

	public void setNoFiled(String[] noFiled) {
		this.noFiled = noFiled;
	}

	private Long id;
	private String salesNum;
	private Long orderId;
	private Integer salesType;
	private String salesTypeString;
	private Integer salesState;
	private String salesStateString;
	private Date trackDate;
	private String trackDateString;
	private Date createDate;
	private String createDateString;
	private Long bearPartyOneId;
	private String bearPartyOneIdName;
	private Integer bearPartyOneType;
	private Long bearPartyOneMoney;
	private String bearPartyOneMoneyString;
	private Long bearPartyTwoId;
	private String bearPartyTwoIdName;
	private Integer bearPartyTwoType;
	private Long bearPartyTwoMoney;
	private String bearPartyTwoMoneyString;
	private Long bearPartyThreeId;
	private String bearPartyThreeIdName;
	private Integer bearPartyThreeType;
	private Long bearPartyThreeMoney;
	private String bearPartyThreeMoneyString;
	private String imagId;
	private String trackRemark;
	private Long tenantId;
	private Long orgId;
	private Long opId;
	private Date opDate;
	private String opDateString;
	private Long trackingNum;
	private String trackingNumString;
	private String creater;
	private Integer createrType;
	private String masterUser;
	private String masterName;
	private Integer serviceType;
	private String serviceTypeString;
	private String consigneeName;
	private String consigneeTelephone;
	private Long descRegion;
	private String descRegionString;
	private Integer orderState;
	private String orderStateString;
	
	public String getOpDateString() {
		if(opDate != null){
			return DateUtil.formatDate(opDate, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}

	public void setOpDateString(String opDateString) {
		this.opDateString = opDateString;
	}

	public String getTrackingNumString() {
		if (trackingNum != null && trackingNum > 0) {
			return String.valueOf(trackingNum);
		}
		return trackingNumString;
	}

	public void setTrackingNumString(String trackingNumString) {
		this.trackingNumString = trackingNumString;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public String getOrderStateString() {
		if (orderState != null && orderState > 0) {
			return SysStaticDataUtil.getSysStaticDataCodeName("APP_ORDER_STATE",
					String.valueOf(orderState));
		}
		return "";
	}

	public void setOrderStateString(String orderStateString) {
		this.orderStateString = orderStateString;
	}

	public String getCreateDateString() {
		if (createDate != null) {
			return DateUtil.formatDate(createDate, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}

	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}

	public String getBearPartyOneMoneyString() {
		if (bearPartyOneMoney != null && bearPartyOneMoney > 0) {
			return CommonUtil.getDoubleFixedNew2(String
					.valueOf(bearPartyOneMoney));
		}
		return "";
	}

	public void setBearPartyOneMoneyString(String bearPartyOneMoneyString) {
		this.bearPartyOneMoneyString = bearPartyOneMoneyString;
	}

	public String getBearPartyTwoMoneyString() {
		if (bearPartyTwoMoney != null && bearPartyTwoMoney > 0) {
			return CommonUtil.getDoubleFixedNew2(String
					.valueOf(bearPartyTwoMoney));
		}
		return "";
	}

	public void setBearPartyTwoMoneyString(String bearPartyTwoMoneyString) {
		this.bearPartyTwoMoneyString = bearPartyTwoMoneyString;
	}

	public String getBearPartyThreeMoneyString() {
		if (bearPartyThreeMoney != null && bearPartyThreeMoney > 0) {
			return CommonUtil.getDoubleFixedNew2(String
					.valueOf(bearPartyThreeMoney));
		}
		return "";
	}

	public void setBearPartyThreeMoneyString(String bearPartyThreeMoneyString) {
		this.bearPartyThreeMoneyString = bearPartyThreeMoneyString;
	}

	public String getTrackDateString() {
		if (trackDate != null) {
			return DateUtil.formatDate(trackDate, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}

	public void setTrackDateString(String trackDateString) {
		this.trackDateString = trackDateString;
	}

	public String getBearPartyOneIdName() {
		return bearPartyOneIdName;
	}

	public void setBearPartyOneIdName(String bearPartyOneIdName) {
		this.bearPartyOneIdName = bearPartyOneIdName;
	}

	public String getBearPartyTwoIdName() {
		return bearPartyTwoIdName;
	}

	public void setBearPartyTwoIdName(String bearPartyTwoIdName) {
		this.bearPartyTwoIdName = bearPartyTwoIdName;
	}

	public String getBearPartyThreeIdName() {
		return bearPartyThreeIdName;
	}

	public void setBearPartyThreeIdName(String bearPartyThreeIdName) {
		this.bearPartyThreeIdName = bearPartyThreeIdName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSalesNum() {
		return salesNum;
	}

	public void setSalesNum(String salesNum) {
		this.salesNum = salesNum;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getSalesType() {
		return salesType;
	}

	public void setSalesType(Integer salesType) {
		this.salesType = salesType;
	}

	public String getSalesTypeString() {
		if (salesType != null && salesType > 0) {
			return SysStaticDataUtil.getSysStaticDataCodeName("SALES_TYPE",
					String.valueOf(salesType));
		}
		return "";
	}

	public void setSalesTypeString(String salesTypeString) {
		this.salesTypeString = salesTypeString;
	}

	public Integer getSalesState() {
		return salesState;
	}

	public void setSalesState(Integer salesState) {
		this.salesState = salesState;
	}

	public String getSalesStateString() {
		if (salesState != null && salesState > 0) {
			return SysStaticDataUtil.getSysStaticDataCodeName("SALES_STATE",
					String.valueOf(salesState));
		}
		return "";
	}

	public void setSalesStateString(String salesStateString) {
		this.salesStateString = salesStateString;
	}

	public Date getTrackDate() {
		return trackDate;
	}

	public void setTrackDate(Date trackDate) {
		this.trackDate = trackDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getBearPartyOneId() {
		return bearPartyOneId;
	}

	public void setBearPartyOneId(Long bearPartyOneId) {
		this.bearPartyOneId = bearPartyOneId;
	}

	public Integer getBearPartyOneType() {
		return bearPartyOneType;
	}

	public void setBearPartyOneType(Integer bearPartyOneType) {
		this.bearPartyOneType = bearPartyOneType;
	}

	public Long getBearPartyOneMoney() {
		return bearPartyOneMoney;
	}

	public void setBearPartyOneMoney(Long bearPartyOneMoney) {
		this.bearPartyOneMoney = bearPartyOneMoney;
	}

	public Long getBearPartyTwoId() {
		return bearPartyTwoId;
	}

	public void setBearPartyTwoId(Long bearPartyTwoId) {
		this.bearPartyTwoId = bearPartyTwoId;
	}

	public Integer getBearPartyTwoType() {
		return bearPartyTwoType;
	}

	public void setBearPartyTwoType(Integer bearPartyTwoType) {
		this.bearPartyTwoType = bearPartyTwoType;
	}

	public Long getBearPartyTwoMoney() {
		return bearPartyTwoMoney;
	}

	public void setBearPartyTwoMoney(Long bearPartyTwoMoney) {
		this.bearPartyTwoMoney = bearPartyTwoMoney;
	}

	public Long getBearPartyThreeId() {
		return bearPartyThreeId;
	}

	public void setBearPartyThreeId(Long bearPartyThreeId) {
		this.bearPartyThreeId = bearPartyThreeId;
	}

	public Integer getBearPartyThreeType() {
		return bearPartyThreeType;
	}

	public void setBearPartyThreeType(Integer bearPartyThreeType) {
		this.bearPartyThreeType = bearPartyThreeType;
	}

	public Long getBearPartyThreeMoney() {
		return bearPartyThreeMoney;
	}

	public void setBearPartyThreeMoney(Long bearPartyThreeMoney) {
		this.bearPartyThreeMoney = bearPartyThreeMoney;
	}

	public String getImagId() {
		return imagId;
	}

	public void setImagId(String imagId) {
		this.imagId = imagId;
	}

	public String getTrackRemark() {
		return trackRemark;
	}

	public void setTrackRemark(String trackRemark) {
		this.trackRemark = trackRemark;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public Long getTrackingNum() {
		return trackingNum;
	}

	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Integer getCreaterType() {
		return createrType;
	}

	public void setCreaterType(Integer createrType) {
		this.createrType = createrType;
	}

	public String getMasterUser() {
		return masterUser;
	}

	public void setMasterUser(String masterUser) {
		this.masterUser = masterUser;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceTypeString() {
		if (serviceType != null && serviceType > 0) {
			return SysStaticDataUtil.getSysStaticDataCodeName(
					"SCHE_SERVICE_TYPE", String.valueOf(serviceType));
		}
		return "";
	}

	public void setServiceTypeString(String serviceTypeString) {
		this.serviceTypeString = serviceTypeString;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}

	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}

	public Long getDescRegion() {
		return descRegion;
	}

	public void setDescRegion(Long descRegion) {
		this.descRegion = descRegion;
	}

	public String getDescRegionString() {
		return descRegionString;
	}

	public void setDescRegionString(String descRegionString) {
		this.descRegionString = descRegionString;
	}

}
