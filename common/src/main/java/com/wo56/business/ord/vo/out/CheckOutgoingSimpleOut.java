package com.wo56.business.ord.vo.out;

import java.util.Date;

import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.framework.core.util.SysStaticDataUtil;

public class CheckOutgoingSimpleOut extends BaseOutParamVO {
	private String seeOrderStateName;
	public String getSeeOrderStateName() {
		return seeOrderStateName;
	}

	public void setSeeOrderStateName(String seeOrderStateName) {
		this.seeOrderStateName = seeOrderStateName;
	}

	/**
	 * 中转时间
	 */
	private Date tranferDate;
	public Date getTranferDate() {
		return tranferDate;
	}

	public void setTranferDate(Date tranferDate) {
		this.tranferDate = tranferDate;
	}

	private Long trackingNum;

	/**
	 * 自增主键.
	 */
	private Long id;
	
	/**
	 * 内部订单号.
	 */
	private Long orderId;

	/**
	 * 承运商ID.
	 */
	private Long carrierCompanyId;

	/**
	 * 承运商名称.
	 */
	private String carrierCompanyName;

	/**
	 * 外发单号.
	 */
	private Long outgoingTrackingNum;

	/**
	 * 本地联系人.
	 */
	private String linkerName;

	/**
	 * 联系电话.
	 */
	private String linkerPhone;

	/**
	 * 提货电话.
	 */
	private String deliveryPhone;

	/**
	 * 提货地址.
	 */
	private String deliveryAddress;

	/**
	 * 外发费
	 */
	private Double outgoingFee;
	/**
	 * 代收货款
	 */
	private Double collectingMoney;
    
	/** 到付款 */
	private Double freightCollect;

	private Double checkFee;// 核销金额
	
 

	public Double getCheckFee() {
		return checkFee;
	}

	public void setCheckFee(Double checkFee) {
		this.checkFee = checkFee;
	}

	public Double getOutgoingFee() {
		return outgoingFee;
	}

	public void setOutgoingFee(Double outgoingFee) {
		this.outgoingFee = outgoingFee;
	}

	public Double getCollectingMoney() {
		return collectingMoney;
	}

	public void setCollectingMoney(Double collectingMoney) {
		this.collectingMoney = collectingMoney;
	}

	public Double getFreightCollect() {
		return freightCollect;
	}

	public void setFreightCollect(Double freightCollect) {
		this.freightCollect = freightCollect;
	}

	/**
	 * 创建时间.
	 */
	private Date createDate;

	/**
	 * 操作员.
	 */
	private Long opId;

	/** 新增中转外发纪录时，当前订单的状态 */
	private Integer currentOrderState;

	/** 应收 */
	private Long shouldReceivables;

	/** 现金已收；0:未收; 1:已收 */
	private Integer isReceivedShouldReceivables;

	/** 应付 */
	private Long shouldPay;

	/** 现金已付；0:未付; 1:已付 */
	private Integer isReceivedShouldPay;

	/**
	 * 备注.
	 */
	private String remarks;

	/**
	 * 扩展字段.
	 */
	private String ext1;

	/**
	 * 扩展字段
	 */
	private String ext2;
	private String opIdName;


	/** 核销状态 1、已核销;2、未核销 */
	private int checkSts;
	private String checkStsName;

	/** 核销时间 */
	private Date checkDate;
	private String currentOrgIdName;
	

 

	private String inoutStsName;// 收支类型
	private Double weight;
	private Double volume;
	private Integer count;
	private String receiptNum;
	private Integer receiptCount;
	
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	public Integer getReceiptCount() {
		return receiptCount;
	}

	public void setReceiptCount(Integer receiptCount) {
		this.receiptCount = receiptCount;
	}

//	public Long getCheckFee() {
//		if (null != freightCollect && null != outgoingFee) {
//			long fee = freightCollect - outgoingFee;
//			if (fee < 0)
//				fee = -fee;
//			return fee / 100;
//		} else {
//			return 0l;
//		}
//	}

	public String getInoutStsName() {
		if (null != shouldPay && shouldPay > 0) {// 应付
			if (null != isReceivedShouldPay && isReceivedShouldPay == 1) {
				return "已付";
			} else {
				return "未付";
			}
		} else {
			if (null != isReceivedShouldReceivables
					&& isReceivedShouldReceivables == 1) {
				return "已收";
			} else {
				return "未收";
			}
		}
	}

	public String getCheckStsName() {
		if (this.getCheckSts() > -1) {
			SysStaticData ssd = SysStaticDataUtil.getSysStaticData(
					"AC_CASH_PROVE@CHECK_STS",
					String.valueOf(this.getCheckSts()));
			if (ssd != null) {
				this.setCheckStsName(ssd.getCodeName());
			}
		}
		return checkStsName;
	}

	public void setCheckStsName(String checkStsName) {
		this.checkStsName = checkStsName;
	}

	public CheckOutgoingSimpleOut() {
	}

	public String getOpIdName() throws Exception {
		return opIdName;
	}

	public void setOpIdName(String opIdName) {
		this.opIdName = opIdName;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCarrierCompanyId() {
		return this.carrierCompanyId;
	}

	public void setCarrierCompanyId(Long carrierCompanyId) {
		this.carrierCompanyId = carrierCompanyId;
	}

	public String getCarrierCompanyName() {
		return this.carrierCompanyName;
	}

	public void setCarrierCompanyName(String carrierCompanyName) {
		this.carrierCompanyName = carrierCompanyName;
	}

	public Long getOutgoingTrackingNum() {
		return this.outgoingTrackingNum;
	}

	public void setOutgoingTrackingNum(Long outgoingTrackingNum) {
		this.outgoingTrackingNum = outgoingTrackingNum;
	}

	public String getLinkerName() {
		return this.linkerName;
	}

	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	public String getLinkerPhone() {
		return this.linkerPhone;
	}

	public void setLinkerPhone(String linkerPhone) {
		this.linkerPhone = linkerPhone;
	}

	public String getDeliveryPhone() {
		return this.deliveryPhone;
	}

	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}

	public String getDeliveryAddress() {
		return this.deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

 

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Integer getCurrentOrderState() {
		return currentOrderState;
	}

	public void setCurrentOrderState(Integer currentOrderState) {
		this.currentOrderState = currentOrderState;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getExt1() {
		return this.ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return this.ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public Long getShouldReceivables() {
		return shouldReceivables;
	}

	public void setShouldReceivables(Long shouldReceivables) {
		this.shouldReceivables = shouldReceivables;
	}

	public Integer getIsReceivedShouldReceivables() {
		return isReceivedShouldReceivables;
	}

	public void setIsReceivedShouldReceivables(
			Integer isReceivedShouldReceivables) {
		this.isReceivedShouldReceivables = isReceivedShouldReceivables;
	}

	public Long getShouldPay() {
		return shouldPay;
	}

	public void setShouldPay(Long shouldPay) {
		this.shouldPay = shouldPay;
	}

	public Integer getIsReceivedShouldPay() {
		return isReceivedShouldPay;
	}

	public void setIsReceivedShouldPay(Integer isReceivedShouldPay) {
		this.isReceivedShouldPay = isReceivedShouldPay;
	}
 
	public int getCheckSts() {
		return checkSts;
	}

	public void setCheckSts(int checkSts) {
		this.checkSts = checkSts;
	}

	public Date getCheckDate() {
		return this.checkSts == 1 ? this.checkDate : null;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Long getTrackingNum() {
		return trackingNum;
	}

	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}

	public String getCurrentOrgIdName() {
		return currentOrgIdName;
	}

	public void setCurrentOrgIdName(String currentOrgIdName) {
		this.currentOrgIdName = currentOrgIdName;
	}
}
