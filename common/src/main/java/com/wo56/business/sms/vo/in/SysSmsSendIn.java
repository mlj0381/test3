package com.wo56.business.sms.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;

public class SysSmsSendIn implements IParamIn{
	
	private String inCode;
	
	public SysSmsSendIn(String inCode){
		this.inCode=inCode;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return inCode;
	}
	public Long[] ordIds;
	private Long[] bills;
	private Long templateId;
	private String content;
	public Long[] getBills() {
		return bills;
	}
	public void setBills(Long[] bills) {
		this.bills = bills;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long[] getOrdIds() {
		return ordIds;
	}
	public void setOrdIds(Long[] ordIds) {
		this.ordIds = ordIds;
	}
	
	
}
