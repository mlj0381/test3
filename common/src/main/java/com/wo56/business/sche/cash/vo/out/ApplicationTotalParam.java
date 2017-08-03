package com.wo56.business.sche.cash.vo.out;

import java.util.Date;

import com.framework.core.base.POJO;

/**
 * SchedulerTask entity. @author MyEclipse Persistence Tools
 */

public class ApplicationTotalParam extends POJO implements java.io.Serializable {

	// Fields
	/**   
	 * 提现ID.  
	 */
	private Long id;

	/**   
	 * 归属公司ID.  
	 */
	private String gscode;

	/**   
	 * 归属网点ID.  
	 */
	private String deptId;

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
	 * 对账单号（业务主键，系统同步使用）.  
	 */
	private String dzPaper;

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

	/**   
	 * 创建日期.  
	 */
	//private Date createDate;
	
	private String strCreateDate;

	/**   
	 * 更新人.  
	 */
	private String modifyName;

	/**   
	 * 更新日期.  
	 */
	private Date modifyDate;

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
	
	/**   
	 * 任务ID.  
	 */
	private Long taskId;
	
	/**   
	 * 任务类型.  
	 */
	private Integer taskType;

	/**   
	 * 业务ID  eq：运单号.  
	 */
	private String bussId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGscode() {
		return gscode;
	}

	public void setGscode(String gscode) {
		this.gscode = gscode;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
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

	public String getDzPaper() {
		return dzPaper;
	}

	public void setDzPaper(String dzPaper) {
		this.dzPaper = dzPaper;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStrCreateDate() {
		return strCreateDate;
	}

	public void setStrCreateDate(String strCreateDate) {
		this.strCreateDate = strCreateDate;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
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

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public String getBussId() {
		return bussId;
	}

	public void setBussId(String bussId) {
		this.bussId = bussId;
	}

}