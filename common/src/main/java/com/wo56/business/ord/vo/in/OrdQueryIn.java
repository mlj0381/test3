package com.wo56.business.ord.vo.in;

import java.util.Map;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdQueryIn extends PageInParamVO implements IParamIn{

	private static final long serialVersionUID = -3107996056975599605L;
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORD_QUERY;
	}
	/**开始时间（0）**/
	private String beginTime;
	/**结束时间（0）**/
	private String endTime;
	/**作废时间（0）**/
	private String delBeginTime;
	/**作废时间（0）**/
	private String delEndTime;
	/**批次号（0）*/
	private long batchNum;
	/**收货人电话*/
	private String consigneeBill;
	/**发货人电话*/
	private String consignorBill;
	
	private String consignorName;
	private String consigneeName;
	/**d订单id*/
	private long orderId;
	/**开单网点id*/
	private long orgId;
	/**订单状态*/
	private Integer orderState;
	/**运单号**/
	private Long trackingNum;
	/**回单号**/
	private String receiptNum;
	/**配送网点**/
	private Integer descOrgId;
	/**订单类型*/
	private int orderType;
	/**当前网点*/
	private long currentOrgId;
	/** 对于中转外发，中转网点不需要根据org_id查询出结果，用current_org_id查询，为true，则忽略 */
	private boolean needIngoreOrgId;
	
	private int queryType;
	private String queryValue;
	private String inputUserName;
	private String isAscTrue;
	private Integer paymentType;
	private String  inputParamJson;
	private String _sum;//判断是否需要汇总
	
	
	
	
	public String get_sum() {
		return _sum;
	}
	public void set_sum(String _sum) {
		this._sum = _sum;
	}
	public String getInputParamJson() {
		return inputParamJson;
	}
	public void setInputParamJson(String inputParamJson) {
		this.inputParamJson = inputParamJson;
	}
	public String getDelBeginTime() {
		return delBeginTime;
	}
	public void setDelBeginTime(String delBeginTime) {
		this.delBeginTime = delBeginTime;
	}
	public String getDelEndTime() {
		return delEndTime;
	}
	public void setDelEndTime(String delEndTime) {
		this.delEndTime = delEndTime;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getIsAscTrue() {
		return isAscTrue;
	}
	public void setIsAscTrue(String isAscTrue) {
		this.isAscTrue = isAscTrue;
	}
	public String getInputUserName() {
		return inputUserName;
	}
	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}
	public int getQueryType() {
		return queryType;
	}
	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}
	public String getQueryValue() {
		return queryValue;
	}
	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}
	public Integer getDescOrgId() {
		return descOrgId;
	}
	public void setDescOrgId(Integer descOrgId) {
		this.descOrgId = descOrgId;
	}
	public String getReceiptNum() {
		return receiptNum;
	}
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public long getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(long batchNum) {
		this.batchNum = batchNum;
	}
	public String getConsigneeBill() {
		return consigneeBill;
	}
	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}
	public String getConsignorBill() {
		return consignorBill;
	}
	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public long getCurrentOrgId() {
		return currentOrgId;
	}
	public void setCurrentOrgId(long currentOrgId) {
		this.currentOrgId = currentOrgId;
	}
	public boolean isNeedIngoreOrgId() {
		return needIngoreOrgId;
	}
	public void setNeedIngoreOrgId(boolean needIngoreOrgId) {
		this.needIngoreOrgId = needIngoreOrgId;
	}
	public String getConsignorName() {
		return consignorName;
	}
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	
}
