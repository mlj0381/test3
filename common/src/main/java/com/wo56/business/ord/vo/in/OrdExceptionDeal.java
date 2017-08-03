package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdExceptionDeal implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORD_EXCEPTION_DEAL;
	}
	
	/**异常单id*/
	private Long id;
	/**处理意见*/
	private String handleIdea;
	/**处理结果*/
	private String handleResult;
	/**责任部门意见*/
	private String auditIdea;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHandleIdea() {
		return handleIdea;
	}
	public void setHandleIdea(String handleIdea) {
		this.handleIdea = handleIdea;
	}
	public String getHandleResult() {
		return handleResult;
	}
	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}
	public String getAuditIdea() {
		return auditIdea;
	}
	public void setAuditIdea(String auditIdea) {
		this.auditIdea = auditIdea;
	}
	
}
