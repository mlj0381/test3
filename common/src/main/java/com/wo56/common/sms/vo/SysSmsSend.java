package com.wo56.common.sms.vo;

import java.sql.Timestamp;
import java.util.Date;

import com.framework.core.base.POJO;



/**
 * SysSmsSend entity. @author MyEclipse Persistence Tools
 */

public class SysSmsSend  extends POJO implements java.io.Serializable {


    // Fields    

     private Long smsId;
     private Long templateId;
     private String smsContent;
     private Integer smsType;
     private String smsTypeName;
     private Integer sendFlag;
     private Date sendDate;
     private Integer srcType;
     private String billId;
     private String expId;
     private String objType;
     private String objTypeName;
     private Long objId;
    // private Date createDate;
     private String md5;
     private Integer state;

     
    // Constructors

    public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	/** default constructor */
    public SysSmsSend() {
    }

	/** minimal constructor */
    public SysSmsSend(String expId) {
        this.expId = expId;
    }
    
    /** full constructor */
    public SysSmsSend(Long templateId, String smsContent, Integer smsType, Integer sendFlag, Timestamp sendDate, Integer srcType, String billId, String expId, String objType, Long objId, Timestamp createDate, String md5) {
        this.templateId = templateId;
        this.smsContent = smsContent;
        this.smsType = smsType;
        this.sendFlag = sendFlag;
        this.sendDate = sendDate;
        this.srcType = srcType;
        this.billId = billId;
        this.expId = expId;
        this.objType = objType;
        this.objId = objId;
        this.createDate = createDate;
        this.md5 = md5;
    }

   
    // Property accessors

    public Long getSmsId() {
        return this.smsId;
    }
    
    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public Long getTemplateId() {
        return this.templateId;
    }
    
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getSmsContent() {
        return this.smsContent;
    }
    
    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public Integer getSmsType() {
        return this.smsType;
    }
    
    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public Integer getSendFlag() {
        return this.sendFlag;
    }
    
    public void setSendFlag(Integer sendFlag) {
        this.sendFlag = sendFlag;
    }

  

    public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getSrcType() {
        return this.srcType;
    }
    
    public void setSrcType(Integer srcType) {
        this.srcType = srcType;
    }

    public String getBillId() {
        return this.billId;
    }
    
    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getExpId() {
        return this.expId;
    }
    
    public void setExpId(String expId) {
        this.expId = expId;
    }

    public String getObjType() {
        return this.objType;
    }
    
    public void setObjType(String objType) {
        this.objType = objType;
    }

    public Long getObjId() {
        return this.objId;
    }
    
    public void setObjId(Long objId) {
        this.objId = objId;
    }

   

    public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMd5() {
        return this.md5;
    }
    
    public void setMd5(String md5) {
        this.md5 = md5;
    }
   
    public String getObjTypeName() {
//		if (StringUtils.isBlank(objTypeName)) {
//			SysStaticData data = SysStaticDataUtil.getSysStaticData(EnumConsts.SysStaticData.OBJ_TYPE, objType);
//			if (data != null) {
//				objTypeName = data.getCodeName();
//			} else {
//				objTypeName = "";
//			}
//		}
		return objTypeName;
	}

	public void setObjTypeName(String objTypeName) {
		this.objTypeName = objTypeName;
	}


	public String getSmsTypeName() {
//		if (StringUtils.isBlank(smsTypeName)) {
//			SysStaticData data = SysStaticDataUtil.getSysStaticData(EnumConsts.SysStaticData.SMS_TYPE, String.valueOf(smsType));
//			if (data != null) {
//				smsTypeName = data.getCodeName();
//			} else {
//				smsTypeName = "";
//			}
//		}
		return smsTypeName;
	}

	public void setSmsTypeName(String smsTypeName) {
		this.smsTypeName = smsTypeName;
	}




}