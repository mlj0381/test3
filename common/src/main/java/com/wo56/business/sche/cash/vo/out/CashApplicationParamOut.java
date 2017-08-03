package com.wo56.business.sche.cash.vo.out;

import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class CashApplicationParamOut extends BaseOutParamVO {
	/**   
	 * 提现ID.  
	 */
	private Long id;

	/**   
	 * 归属公司ID.  
	 */
	private String gscode;
	private String gscodeName;
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
	 * 对账单号（业务主键，系统同步使用）.  
	 */
	private String dzPaper;

	/**   
	 * 审核人.  
	 */
	private String auditMan;

	/**   
	 * 审核日期.  
	 */
	private Date auditDate;

	/**   
	 * 审核备注.  
	 */
	private String auditNote;

	/**   
	 * 状态:0未审核，1已审核,2 已否决,3 已作废.  
	 */
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
	 * 提现ID（关联cash_application的ID）.  
	 */
	private Long appId;

	/**   
	 * 运单ID.  
	 */
	private String wayBillId;

	/**   
	 * 订单号.  
	 */
	private String orderId;

	/**   
	 * 金额.  
	 */
	private Double dzMoney;

	/**   
	 * 收支流水ID.  
	 */
	private Long flowId;

	/**   
	 * 创建时间.  
	 */
	private Date createDate;

	/**   
	 * 更新时间.  
	 */
	private Date updateDate;

	/**   
	 * 状态（备用）.  
	 */
	private Integer state;
	private Long feeId;
	private String feeName;
	private Long amount;
	private Date createTime;
	private Integer sts;
	private Long taskId ;
	private Date signTime;
	private String receiverName;
	
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String getGscodeName() {
		return gscodeName;
	}
	public void setGscodeName(String gscodeName) {
		this.gscodeName = gscodeName;
	}
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
	public String getDzPaper() {
		return dzPaper;
	}
	public void setDzPaper(String dzPaper) {
		this.dzPaper = dzPaper;
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
	public String getAuditNote() {
		return auditNote;
	}
	public void setAuditNote(String auditNote) {
		this.auditNote = auditNote;
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
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getWayBillId() {
		return wayBillId;
	}
	public void setWayBillId(String wayBillId) {
		this.wayBillId = wayBillId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getDzMoney() {
		return dzMoney;
	}
	public void setDzMoney(Double dzMoney) {
		this.dzMoney = dzMoney;
	}
	public Long getFlowId() {
		return flowId;
	}
	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getFeeId() {
		return feeId;
	}
	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getSts() {
		return sts;
	}
	public void setSts(Integer sts) {
		this.sts = sts;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
}
