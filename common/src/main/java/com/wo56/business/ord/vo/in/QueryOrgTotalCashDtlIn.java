package com.wo56.business.ord.vo.in;

import java.util.Date;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class QueryOrgTotalCashDtlIn extends PageInParamVO  implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return "170008";
	}
   private Date checkDate;
	private Long orgId;
	private Integer feeType;
	private Date endDate;
	private Integer selectDate;
	private Integer checkSts;
	private Integer isSysDo;
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Integer getFeeType() {
		return feeType;
	}
	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getSelectDate() {
		return selectDate;
	}
	public void setSelectDate(Integer selectDate) {
		this.selectDate = selectDate;
	}
	public Integer getCheckSts() {
		return checkSts;
	}
	public void setCheckSts(Integer checkSts) {
		this.checkSts = checkSts;
	}
	public Integer getIsSysDo() {
		return isSysDo;
	}
	public void setIsSysDo(Integer isSysDo) {
		this.isSysDo = isSysDo;
	}
	
	
}
