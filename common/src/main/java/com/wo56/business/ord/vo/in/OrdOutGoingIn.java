package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdOutGoingIn extends PageInParamVO implements IParamIn {

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.QUERY_OUTGOING;
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

	/** 开始时间（0） **/
	private String transitTimeS;
	/** 结束时间（0） **/
	private String transitTimeE;
	
	/** 开始时间（0） **/
	private String beginTime;
	/** 结束时间（0） **/
	private String endTime;
	/** 批次号（0） */
	private long batchNum;
	/** 收货人电话 */
	private String consigneeBill;
	/** 发货人电话 */
	private String consignorBill;
	private String consignorName;
	private String consigneeName;
	/** d订单id */
	private long orderId;
	/** 开单网点id */
	private long orgId;
	/** 订单状态 */
	private Integer orderState;
	/** 中转状态 */
	private Integer transitOutgoingState;
	/** 运单号 **/
	private Long trackingNum;
	/** 回单号 **/
	private String receiptNum;
	/** 配送网点 **/
	private Integer descOrgId;
	/** 订单类型 */
	private int orderType;
	/** 当前网点 */
	private long currentOrgId;
	/** 对于中转外发，中转网点不需要根据org_id查询出结果，用current_org_id查询，为true，则忽略 */
	private boolean needIngoreOrgId;
	private long carrierCompanyId;
	private Integer queryType;
	private  Integer isTransferGoods;
	private  Integer isSecondTransfer;
	
	
	
	public Integer getIsTransferGoods() {
		return isTransferGoods;
	}

	public void setIsTransferGoods(Integer isTransferGoods) {
		this.isTransferGoods = isTransferGoods;
	}

	public Integer getIsSecondTransfer() {
		return isSecondTransfer;
	}

	public void setIsSecondTransfer(Integer isSecondTransfer) {
		this.isSecondTransfer = isSecondTransfer;
	}

	public String getTransitTimeS() {
		return transitTimeS;
	}

	public String getTransitTimeE() {
		return transitTimeE;
	}

	public void setTransitTimeS(String transitTimeS) {
		this.transitTimeS = transitTimeS;
	}

	public void setTransitTimeE(String transitTimeE) {
		this.transitTimeE = transitTimeE;
	}

	public long getCarrierCompanyId() {
		return carrierCompanyId;
	}

	public void setCarrierCompanyId(long carrierCompanyId) {
		this.carrierCompanyId = carrierCompanyId;
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

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Long getTrackingNum() {
		return trackingNum;
	}

	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}

	public String getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	public Integer getDescOrgId() {
		return descOrgId;
	}

	public void setDescOrgId(Integer descOrgId) {
		this.descOrgId = descOrgId;
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

	public Integer getTransitOutgoingState() {
		return transitOutgoingState;
	}

	public void setTransitOutgoingState(Integer transitOutgoingState) {
		this.transitOutgoingState = transitOutgoingState;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}
	
	
}
