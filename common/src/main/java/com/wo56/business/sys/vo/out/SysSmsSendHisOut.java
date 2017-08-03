package com.wo56.business.sys.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class SysSmsSendHisOut extends BaseOutParamVO{
	
	private String templateId;
	private String smsContent;
	private String objType;
	private String busiId;
	/**结束时间*/
	private String realSendDate;
	/**手机号码*/
	private String billId;
	/**短信数量*/
	private String number;
	
	
	private String tenantName;
	private String templateName;
	private String createDataString;
	private String smsId;
	private String isRead;
	
	
	public String getObjType() {
		return objType;
	}
	public void setObjType(String objType) {
		this.objType = objType;
	}
	public String getBusiId() {
		return busiId;
	}
	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	public String getSmsId() {
		return smsId;
	}
	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}
	public String getCreateDataString() {
		return createDataString;
	}
	public void setCreateDataString(String createDataString) {
		this.createDataString = createDataString;
	}
	public SysSmsSendHisOut() {
		super();
	}
	public SysSmsSendHisOut(String templateId, String smsContent,
			String realSendDate, String billId, String number, String tenantId,
			String tenantName, String templateName) {
		super();
		this.templateId = templateId;
		this.smsContent = smsContent;
		this.realSendDate = realSendDate;
		this.billId = billId;
		this.number = number;
		this.tenantName = tenantName;
		this.templateName = templateName;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	/** get and set */
	
	
	public String getTemplateId() {
		return templateId;
	}
	
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public String getRealSendDate() {
		return realSendDate;
	}
	public void setRealSendDate(String realSendDate) {
		this.realSendDate = realSendDate;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	

	
}