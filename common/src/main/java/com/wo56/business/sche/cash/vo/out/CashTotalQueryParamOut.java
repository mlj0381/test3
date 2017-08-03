package com.wo56.business.sche.cash.vo.out;

import java.sql.Timestamp;
import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class CashTotalQueryParamOut extends BaseOutParamVO {
	
	private Long taskId;
	private Long parentId;
	private Integer taskType;
	private Integer taskState;
	private String bussId;
	private Long belongObjId;
	private String belongObjName;
	private Integer belongObjType;
	private Long doObjId;
	private String doObjName;
	private Integer doObjType;
	private String totalMoney;
	private Integer sts;
	private Timestamp createTime;
	private Long opId;
	private String matchRemark;
	private Integer seq;
	//private Long tenantId;
	private Integer withdrawSts;
	private Integer isMacthData;
	private Integer matchType;
	private Integer turnReceiptType;
	private String creator;
	private Long cancerUserId;
	private String cancerUserName;
	private Integer cancerType;
	private String cancerRemark;
	private String dZPaper;
	private String taskStateName;
	private String cancerTypeName;
	private String matchTypeName;
	private Date cancerTime;
	private Date pickTime;
	private Date signTime;
	private Date ipFixTime;
	private Date yyTime;
	private Date distributionTime;
	private Date acceptTime;
	private Integer isShowMoney;
	private String dzPaper;
	private Date upFixTime;
	private String belongTel;
	private String doTel;
	private String taskTypeName;
	private Integer source;
	private Integer isRepair;
	private Integer isSche;
	private String destCity;
	private String destCounty;
	private String products;
	private String volumes;
	private String receiveName;
	private String createTimeStr;
	
	
	
	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getDestCity() {
		return destCity;
	}

	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}

	public String getDestCounty() {
		return destCounty;
	}

	public void setDestCounty(String destCounty) {
		this.destCounty = destCounty;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}
	

	public String getVolumes() {
		return volumes;
	}

	public void setVolumes(String volumes) {
		this.volumes = volumes;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	/**   
	 * 明细主键ID.  
	 */
	private Long id;

	/**   
	 * 提现ID（关联cash_application的ID）.  
	 */
	private Long appId;

	/**   
	 * 收支流水ID.  
	 */
	private Long flowId;

	/**   
	 * 更新时间.  
	 */
	private Date updateDate;

	/**   
	 * 归属公司ID.  
	 */
	private String gscode;

	/**   
	 * 提现人员类型：1师傅2公司.  
	 */
	private int accType;

	/**   
	 * 提现人员ID：1师傅ID、2公司ID.  
	 */
	private Long workerId;

	/**   
	 * 提现人员登录ID.  
	 */
	private String workerLoginAcct;

	/**   
	 * 提现人姓名.  
	 */
	private String workerName;

	/**   
	 * 对账金额.  
	 */
	private Double dzMoney;

	/**   
	 * 审核人.  
	 */
	private String auditMan;

	/**   
	 * 审核日期.  
	 */
	private Date auditDate;
	private String strAuditDate;

	/**   
	 * 审核备注.  
	 */
	private String auditNote;

	/**   
	 * 状态:0未审核，1已审核,2 已否决,3 已作废.  
	 */
	private Integer state;
	
	private String stateName;

	/**   
	 * 备注.  
	 */
	private String note;

	/**   
	 * 应付单号#用于撤销.  
	 */
	private String billCode;

	/**   
	 * 付款方式#1银行,2支付宝.  
	 */
	private int receiType;

	/**   
	 * 银行帐号.  
	 */
	private String bankAccount;

	/**   
	 * 银行户名.  
	 */
	private String accountName;

	/**   
	 * 银行名称.  
	 */
	private String bankName;

	/**   
	 * 创建人.  
	 */
	private String createName;

	private String strCreateDate;

	/**   
	 * 流水ID#用于回写自动核销.  
	 */
	private Long lsId;

	/**   
	 * 核销时间.  
	 */
	private Date veriyDate;
	
	private String strVeriyDate;

	/**   
	 * 核销单号.  
	 */
	private String verifyId;

	/**   
	 * 审核人ID.  
	 */
	private Long authorId;

	/**   
	 * 审核人类型1调度人员2TMS财务人员.  
	 */
	private Integer authorType;
	
	private Double verifyMoney;
	
	private String expandId;
	
	private String GscodeName;
	
	private String verifyPerson;
	
	private Long verifyPersonId;
	private String feeName;
	
	
	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Integer getTaskState() {
		return taskState;
	}

	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
	}

	public String getBussId() {
		return bussId;
	}

	public void setBussId(String bussId) {
		this.bussId = bussId;
	}

	public Long getBelongObjId() {
		return belongObjId;
	}

	public void setBelongObjId(Long belongObjId) {
		this.belongObjId = belongObjId;
	}

	public String getBelongObjName() {
		return belongObjName;
	}

	public void setBelongObjName(String belongObjName) {
		this.belongObjName = belongObjName;
	}

	public Integer getBelongObjType() {
		return belongObjType;
	}

	public void setBelongObjType(Integer belongObjType) {
		this.belongObjType = belongObjType;
	}

	public Long getDoObjId() {
		return doObjId;
	}

	public void setDoObjId(Long doObjId) {
		this.doObjId = doObjId;
	}

	public String getDoObjName() {
		return doObjName;
	}

	public void setDoObjName(String doObjName) {
		this.doObjName = doObjName;
	}

	public Integer getDoObjType() {
		return doObjType;
	}

	public void setDoObjType(Integer doObjType) {
		this.doObjType = doObjType;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getSts() {
		return sts;
	}

	public void setSts(Integer sts) {
		this.sts = sts;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getMatchRemark() {
		return matchRemark;
	}

	public void setMatchRemark(String matchRemark) {
		this.matchRemark = matchRemark;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getWithdrawSts() {
		return withdrawSts;
	}

	public void setWithdrawSts(Integer withdrawSts) {
		this.withdrawSts = withdrawSts;
	}

	public Integer getIsMacthData() {
		return isMacthData;
	}

	public void setIsMacthData(Integer isMacthData) {
		this.isMacthData = isMacthData;
	}

	public Integer getMatchType() {
		return matchType;
	}

	public void setMatchType(Integer matchType) {
		this.matchType = matchType;
	}

	public Integer getTurnReceiptType() {
		return turnReceiptType;
	}

	public void setTurnReceiptType(Integer turnReceiptType) {
		this.turnReceiptType = turnReceiptType;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Long getCancerUserId() {
		return cancerUserId;
	}

	public void setCancerUserId(Long cancerUserId) {
		this.cancerUserId = cancerUserId;
	}

	public String getCancerUserName() {
		return cancerUserName;
	}

	public void setCancerUserName(String cancerUserName) {
		this.cancerUserName = cancerUserName;
	}

	public Integer getCancerType() {
		return cancerType;
	}

	public void setCancerType(Integer cancerType) {
		this.cancerType = cancerType;
	}

	public String getCancerRemark() {
		return cancerRemark;
	}

	public void setCancerRemark(String cancerRemark) {
		this.cancerRemark = cancerRemark;
	}

	public String getdZPaper() {
		return dZPaper;
	}

	public void setdZPaper(String dZPaper) {
		this.dZPaper = dZPaper;
	}

	public String getTaskStateName() {
		return taskStateName;
	}

	public void setTaskStateName(String taskStateName) {
		this.taskStateName = taskStateName;
	}

	public String getCancerTypeName() {
		return cancerTypeName;
	}

	public void setCancerTypeName(String cancerTypeName) {
		this.cancerTypeName = cancerTypeName;
	}

	public String getMatchTypeName() {
		return matchTypeName;
	}

	public void setMatchTypeName(String matchTypeName) {
		this.matchTypeName = matchTypeName;
	}

	public Date getCancerTime() {
		return cancerTime;
	}

	public void setCancerTime(Date cancerTime) {
		this.cancerTime = cancerTime;
	}

	public Date getPickTime() {
		return pickTime;
	}

	public void setPickTime(Date pickTime) {
		this.pickTime = pickTime;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public Date getIpFixTime() {
		return ipFixTime;
	}

	public void setIpFixTime(Date ipFixTime) {
		this.ipFixTime = ipFixTime;
	}

	public Date getYyTime() {
		return yyTime;
	}

	public void setYyTime(Date yyTime) {
		this.yyTime = yyTime;
	}

	public Date getDistributionTime() {
		return distributionTime;
	}

	public void setDistributionTime(Date distributionTime) {
		this.distributionTime = distributionTime;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Integer getIsShowMoney() {
		return isShowMoney;
	}

	public void setIsShowMoney(Integer isShowMoney) {
		this.isShowMoney = isShowMoney;
	}

	public String getDzPaper() {
		return dzPaper;
	}

	public void setDzPaper(String dzPaper) {
		this.dzPaper = dzPaper;
	}

	public Date getUpFixTime() {
		return upFixTime;
	}

	public void setUpFixTime(Date upFixTime) {
		this.upFixTime = upFixTime;
	}

	public String getBelongTel() {
		return belongTel;
	}

	public void setBelongTel(String belongTel) {
		this.belongTel = belongTel;
	}

	public String getDoTel() {
		return doTel;
	}

	public void setDoTel(String doTel) {
		this.doTel = doTel;
	}

	public String getTaskTypeName() {
		return taskTypeName;
	}

	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getIsRepair() {
		return isRepair;
	}

	public void setIsRepair(Integer isRepair) {
		this.isRepair = isRepair;
	}

	public Integer getIsSche() {
		return isSche;
	}

	public void setIsSche(Integer isSche) {
		this.isSche = isSche;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getGscode() {
		return gscode;
	}

	public void setGscode(String gscode) {
		this.gscode = gscode;
	}

	public int getAccType() {
		return accType;
	}

	public void setAccType(int accType) {
		this.accType = accType;
	}

	public Long getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}

	public String getWorkerLoginAcct() {
		return workerLoginAcct;
	}

	public void setWorkerLoginAcct(String workerLoginAcct) {
		this.workerLoginAcct = workerLoginAcct;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public Double getDzMoney() {
		return dzMoney;
	}

	public void setDzMoney(Double dzMoney) {
		this.dzMoney = dzMoney;
	}

	public String getAuditMan() {
		return auditMan;
	}

	public void setAuditMan(String auditMan) {
		this.auditMan = auditMan;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getStrAuditDate() {
		return strAuditDate;
	}

	public void setStrAuditDate(String strAuditDate) {
		this.strAuditDate = strAuditDate;
	}

	public String getAuditNote() {
		return auditNote;
	}

	public void setAuditNote(String auditNote) {
		this.auditNote = auditNote;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public int getReceiType() {
		return receiType;
	}

	public void setReceiType(int receiType) {
		this.receiType = receiType;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getStrCreateDate() {
		return strCreateDate;
	}

	public void setStrCreateDate(String strCreateDate) {
		this.strCreateDate = strCreateDate;
	}

	public Long getLsId() {
		return lsId;
	}

	public void setLsId(Long lsId) {
		this.lsId = lsId;
	}

	public Date getVeriyDate() {
		return veriyDate;
	}

	public void setVeriyDate(Date veriyDate) {
		this.veriyDate = veriyDate;
	}

	public String getStrVeriyDate() {
		return strVeriyDate;
	}

	public void setStrVeriyDate(String strVeriyDate) {
		this.strVeriyDate = strVeriyDate;
	}

	public String getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(String verifyId) {
		this.verifyId = verifyId;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Integer getAuthorType() {
		return authorType;
	}

	public void setAuthorType(Integer authorType) {
		this.authorType = authorType;
	}

	public Double getVerifyMoney() {
		return verifyMoney;
	}

	public void setVerifyMoney(Double verifyMoney) {
		this.verifyMoney = verifyMoney;
	}

	public String getExpandId() {
		return expandId;
	}

	public void setExpandId(String expandId) {
		this.expandId = expandId;
	}

	public String getGscodeName() {
		return GscodeName;
	}

	public void setGscodeName(String gscodeName) {
		GscodeName = gscodeName;
	}

	public String getVerifyPerson() {
		return verifyPerson;
	}

	public void setVerifyPerson(String verifyPerson) {
		this.verifyPerson = verifyPerson;
	}

	public Long getVerifyPersonId() {
		return verifyPersonId;
	}

	public void setVerifyPersonId(Long verifyPersonId) {
		this.verifyPersonId = verifyPersonId;
	}
	
	
}
