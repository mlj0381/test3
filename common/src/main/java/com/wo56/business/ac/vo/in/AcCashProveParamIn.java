package com.wo56.business.ac.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class AcCashProveParamIn extends PageInParamVO implements IParamIn {

	/**核销类型 (1)**/
	private String checkType;
	/**收支类型(1)**/
	private int inoutSts;
	/**核销状态 (1)**/
	private int checkSts;
	private long orderId;
	private long orgId;
	private String trackingNum;
	/**现金收取状态**/
	private int cashSts;
	private String consignorName;
	private String consignorLinkmanName;
	private String consignorBill;
	private String endDate;
	private String beginDate;
	private String consigneeName;
	private String consigneeBill;
	
	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeBill() {
		return consigneeBill;
	}

	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.AC.QUERY_PROVE;
	}

	public int getCashSts() {
		return cashSts;
	}

	public void setCashSts(int cashSts) {
		this.cashSts = cashSts;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public int getInoutSts() {
		return inoutSts;
	}
	public void setInoutSts(int inoutSts) {
		this.inoutSts = inoutSts;
	}
	public int getCheckSts() {
		return checkSts;
	}
	public void setCheckSts(int checkSts) {
		this.checkSts = checkSts;
	}

	public String getTrackingNum() {
		return trackingNum;
	}

	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}

	public String getConsignorName() {
		return consignorName;
	}

	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}

	public String getConsignorLinkmanName() {
		return consignorLinkmanName;
	}

	public void setConsignorLinkmanName(String consignorLinkmanName) {
		this.consignorLinkmanName = consignorLinkmanName;
	}

	public String getConsignorBill() {
		return consignorBill;
	}

	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}

}
