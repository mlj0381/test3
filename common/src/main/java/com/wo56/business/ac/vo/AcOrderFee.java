package com.wo56.business.ac.vo;
public class AcOrderFee implements java.io.Serializable {
	
	//交易类型:1现金2平台账户
	private int busiType;
	//支付状态1、未收2、已收3、已支4、未支
	private int paySts;
	//收支类型:1收入2支出
	private int payType;
	//费用ID
	private long feeId;
	//费用名称
	private String feeName;
	//费用类型
	private int feeType;
	//费用类型名称
	private String feeTypeName;
	//费用金额
	private long amount;
	//对象类型：1操作员2业务员3开单员4网点5  客户 6中转方7发货方8收货方9司机10师傅
	private int objType;
	//对象ID(业务员ID、客户ID、网点ID)
	private long objId;
	//固定值:如1现付、2到付、3回单付、4月结、5费用托管、6代收货款、7代收核销等用此值标识，程序用此字段判断费用项。0或位空为非固定值。（固定值非可配置，必须割接上线）
	private int constantValue;
	//对象名称
	private String objName;
	/**   
	 * 现金状态：1现金已收 2现金未收
	 */
	private int cashSts;
	/**   
	 * 判断对象和对方对象是否是同一租户：1同租户2：不同租户 
	 */
	private int tenantType;
	/**   
	 * 反转后的核销网点ID （1：如果对方对象是网点或协议发货方，存在网点就保存对方对象网点2：其他情况就写反转之前的核销网点）
	 */
	private long reversalOrgId;
	
	/**   
	 * 是否要反转： 1:需要反转 2：不需要反转
	 */
	private long isReversal;
	/**   
	 * 反转后的对方对象名称
	 */
	private String reversalName;
	
	/**   
	 * 对象类型
	 */
	private int otype;
	
	
	/**   
	 *  异常类型（异常费用登记 还是 异动费用登记）
	 *  1：异动费用登记  其他待扩展
	 */
	private int taskType;
	
	private String remarks;
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public int getOtype() {
		return otype;
	}
	public void setOtype(int otype) {
		this.otype = otype;
	}
	public String getReversalName() {
		return reversalName;
	}
	public void setReversalName(String reversalName) {
		this.reversalName = reversalName;
	}
	public int getTenantType() {
		return tenantType;
	}
	public void setTenantType(int tenantType) {
		this.tenantType = tenantType;
	}
	public long getReversalOrgId() {
		return reversalOrgId;
	}
	public void setReversalOrgId(long reversalOrgId) {
		this.reversalOrgId = reversalOrgId;
	}
	public long getIsReversal() {
		return isReversal;
	}
	public void setIsReversal(long isReversal) {
		this.isReversal = isReversal;
	}
	public int getCashSts() {
		return cashSts;
	}
	public void setCashSts(int cashSts) {
		this.cashSts = cashSts;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public int getConstantValue() {
		return constantValue;
	}
	public void setConstantValue(int constantValue) {
		this.constantValue = constantValue;
	}
	public int getBusiType() {
		return busiType;
	}
	public void setBusiType(int busiType) {
		this.busiType = busiType;
	}
	public int getPaySts() {
		return paySts;
	}
	public void setPaySts(int paySts) {
		this.paySts = paySts;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public long getFeeId() {
		return feeId;
	}
	public void setFeeId(long feeId) {
		this.feeId = feeId;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public int getFeeType() {
		return feeType;
	}
	public void setFeeType(int feeType) {
		this.feeType = feeType;
	}
	public String getFeeTypeName() {
		return feeTypeName;
	}
	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public int getObjType() {
		return objType;
	}
	public void setObjType(int objType) {
		this.objType = objType;
	}
	public long getObjId() {
		return objId;
	}
	public void setObjId(long objId) {
		this.objId = objId;
	}
 
}
