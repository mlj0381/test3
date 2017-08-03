package com.wo56.business.ord.vo.in;

import java.util.Date;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class DoQueryOrdFeeIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.QUERY_ORD_FEE;
	}
   private Date checkDate;
	private Long orgId;
	private Date endDate;
	private Integer selectDate;
	private Integer checkSts;
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
   
	
}
