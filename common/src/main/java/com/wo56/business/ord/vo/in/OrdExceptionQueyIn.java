package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdExceptionQueyIn implements IParamIn{

	/**
	 * 异常单查询
	 * 120025
	 */
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORD_EXCEPTION_QUERY;
	}
	/**开始时间**/
	private String beginTime;
	/**结束时间*/
	private String endTime;
	/**责任网点*/
	private Long dutyOrgId;
	/**登记网点*/
	private Long orgId;
	/**运单号*/
	private Long trackingNum;
	/**状态*/
	private Integer status;
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Long getDutyOrgId() {
		return dutyOrgId;
	}
	public void setDutyOrgId(Long dutyOrgId) {
		this.dutyOrgId = dutyOrgId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}
	
}
