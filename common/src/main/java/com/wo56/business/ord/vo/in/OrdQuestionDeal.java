package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdQuestionDeal implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORD_QUESTION_DEAL;
	}

	/**主键*/
	private Long id;
	/**审核意见*/
	private String auditIdea;
	/**审核状态*/
	private String auditStatus;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAuditIdea() {
		return auditIdea;
	}
	public void setAuditIdea(String auditIdea) {
		this.auditIdea = auditIdea;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
}
