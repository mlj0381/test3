package com.wo56.common.sms.vo;

import com.framework.core.base.POJO;



/**
 * SysSmsParam entity. @author MyEclipse Persistence Tools
 */

public class SysSmsParam extends POJO implements java.io.Serializable {


    // Fields    

     private Long paramId;
     private Long templateId;
     private String paramName;
     private String paramCode;
     private String paramValueExpr;
     private String remarks;
     private Integer state;


    // Constructors

    /** default constructor */
    public SysSmsParam() {
    }

	/** minimal constructor */
    public SysSmsParam(Long paramId, Long templateId, String paramCode) {
        this.paramId = paramId;
        this.templateId = templateId;
        this.paramCode = paramCode;
    }
    
    /** full constructor */
    public SysSmsParam(Long paramId, Long templateId, String paramName, String paramCode, String paramValueExpr, String remarks, Integer state) {
        this.paramId = paramId;
        this.templateId = templateId;
        this.paramName = paramName;
        this.paramCode = paramCode;
        this.paramValueExpr = paramValueExpr;
        this.remarks = remarks;
        this.state = state;
    }

   
    // Property accessors

    public Long getParamId() {
        return this.paramId;
    }
    
    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public Long getTemplateId() {
        return this.templateId;
    }
    
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getParamName() {
        return this.paramName;
    }
    
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamCode() {
        return this.paramCode;
    }
    
    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamValueExpr() {
        return this.paramValueExpr;
    }
    
    public void setParamValueExpr(String paramValueExpr) {
        this.paramValueExpr = paramValueExpr;
    }

    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getState() {
        return this.state;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }
}