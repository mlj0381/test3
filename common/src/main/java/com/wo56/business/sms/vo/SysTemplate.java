package com.wo56.business.sms.vo;

import java.util.Date;


/**
 * SysTemplate entity. @author MyEclipse Persistence Tools
 */

public class SysTemplate  implements java.io.Serializable {


    // Fields    

     private Long templateId;
     private String templateName;
     private String templateContent;
     private Integer state;
     private Date createDate;
     private String remarks;


    // Constructors

    /** default constructor */
    public SysTemplate() {
    }

	/** minimal constructor */
    public SysTemplate(Long templateId, String templateName, String templateContent) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.templateContent = templateContent;
    }
    
    /** full constructor */
    public SysTemplate(Long templateId, String templateName, String templateContent, Integer state, Date createDate, String remarks) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.templateContent = templateContent;
        this.state = state;
        this.createDate = createDate;
        this.remarks = remarks;
    }

   
    // Property accessors



    public String getTemplateName() {
        return this.templateName;
    }
    
    public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateContent() {
        return this.templateContent;
    }
    
    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public Integer getState() {
        return this.state;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}