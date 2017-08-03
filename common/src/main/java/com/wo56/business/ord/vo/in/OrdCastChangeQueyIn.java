package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdCastChangeQueyIn  extends PageInParamVO implements IParamIn{

	/**
	 * 异常单查询
	 * 120025
	 */
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.QUERY_CAST_CHANGE;
	}
	/**开始时间**/
	private String beginTime;
	/**结束时间*/
	private String endTime;
	/**开始时间**/
	private String inputBeginTime;
	/**结束时间*/
	private String inputEndTime;
	/**责任网点*/
	private Long dutyOrgId;
	/**登记网点*/
	private Long orgId;
	/**运单号*/
	private Long trackingNum;
	/**状态*/
	private Integer status;
	/**问题类型  1、问题处理 2、问题登记*/
	private Integer questionType;
	
	private String consignorName; // 付款方
	
	private String consigneeName;
	private String consigneeBill;
	
	
	public String getInputBeginTime() {
		return inputBeginTime;
	}
	public void setInputBeginTime(String inputBeginTime) {
		this.inputBeginTime = inputBeginTime;
	}
	public String getInputEndTime() {
		return inputEndTime;
	}
	public void setInputEndTime(String inputEndTime) {
		this.inputEndTime = inputEndTime;
	}
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
	public String getConsignorName() {
		return consignorName;
	}
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}
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
	public Integer getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	
	
}
