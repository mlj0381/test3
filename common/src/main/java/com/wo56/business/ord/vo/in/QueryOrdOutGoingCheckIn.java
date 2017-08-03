package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class QueryOrdOutGoingCheckIn extends PageInParamVO implements IParamIn {

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.QUERY_OUTGOING_CHECK;
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
	private String beginTime;
	/** 结束时间（0） **/
	private String endTime;
	private Long trackingNum;
	private int inoutSts;// 收支类型
	private int checkSts;// 核销状态
	private long opId;//    操作员ID
	
	private String carrierCompanyName; //承运商
	
	public String getCarrierCompanyName() {
		return carrierCompanyName;
	}

	public void setCarrierCompanyName(String carrierCompanyName) {
		this.carrierCompanyName = carrierCompanyName;
	}

	public long getOpId() {
		return opId;
	}

	public void setOpId(long opId) {
		this.opId = opId;
	}

	public Long getTrackingNum() {
		return trackingNum;
	}

	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
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
}
