package com.wo56.business.ac.vo.out;

import java.util.Date;

import com.framework.core.util.SysStaticDataUtil;

public class AcCashProveParamOut{
	private String checkOpName;

	/**   
	 * 核销ID.  
	 */
	private long checkedId;

	/**   
	 * 订单ID.  
	 */
	private Long orderId;
	/**   
	 * 运单号.  
	 */
	private String trackingNum;

	/**   
	 * 核销金额.  
	 */
	private Long fee;
	/**   
	 * 核销科目
	 */
	private String feeTypeName;
 


	public String getFeeTypeName() {
		return feeTypeName;
	}


	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}


	/**   
	 * 核销金额.  
	 */
	private double feeDouble;
	
	public double getFeeDouble() {
		setFeeDouble(((double)fee)/100);
		return feeDouble;
	}


	public void setFeeDouble(double feeDouble) {
		this.feeDouble = feeDouble;
	}


	/**   
	 * 支付类型:1支付宝2微信3银联4现金.  
	 */
	private Integer payMold;

	/**   
	 * 已核销金额.  
	 */
	private Long checkAmount;

	/**   
	 * 剩余核销金额.  
	 */
	private Long withoutAmount;

	/**   
	 * 核销状态 1、已核销2、未核销3、部分核销.  
	 */
	private Integer checkSts;
	


	/**   
	 * 创建日期.  
	 */
	private Date createDate;

	/**   
	 * 核销日期.  
	 */
	private Date checkDate;

	/**   
	 * 操作员ID.  
	 */
	private Long opId;

	/**   
	 * 核销网点ID.  
	 */
	private Long checkOrg;


	/**   
	 * 支付状态1、未收2、已收3、未支4、已支.
	 */
	private int paySts;

	/**   
	 * 收支类型:1收入2支出. .  
	 */
	private int payType;

	/**   
	 * 对方对象类型：1操作员2业务员3开单员4网点5 客户 6中转方7发货方8收货方9司机10师傅
	 */
	private int faceObjType;
	/**   
	 * 对方网点.  
	 */
	private Long faceObjId;
	private String faceObjName;

	/**   
	 * 备注.  
	 */
	private String remark;
	
	/**   
	 * 系统备注.  
	 */
	private String sysRemark;
	
	private String checkStsName;
	private String payStsName;
	private String payTypeName;
	private Integer objType;


	public String getCheckOpName() {
		return checkOpName;
	}


	public void setCheckOpName(String checkOpName) {
		this.checkOpName = checkOpName;
	}


	private String objName;
	private String objTypeName;
	private String plateNumber;
	private String driverName;
	private String driverBill;
	
	private String batchNumAlias;
	private String inputUserName;
	private String goodsNumber;
	private String seeOrderStateName;
	
	public String getInputUserName() {
		return inputUserName;
	}


	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}


	public String getGoodsNumber() {
		return goodsNumber;
	}


	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}


	public String getSeeOrderStateName() {
		return seeOrderStateName;
	}


	public void setSeeOrderStateName(String seeOrderStateName) {
		this.seeOrderStateName = seeOrderStateName;
	}


	public String getBatchNumAlias() {
		return batchNumAlias;
	}


	public void setBatchNumAlias(String batchNumAlias) {
		this.batchNumAlias = batchNumAlias;
	}


	public String getPlateNumber() {
		return plateNumber;
	}


	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}


	public String getDriverName() {
		return driverName;
	}


	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}


	public String getDriverBill() {
		return driverBill;
	}


	public void setDriverBill(String driverBill) {
		this.driverBill = driverBill;
	}


	public Integer getObjType() {
		return objType;
	}


	public void setObjType(Integer objType) {
		this.objType = objType;
	}


	public String getObjName() {
		return objName;
	}


	public void setObjName(String objName) {
		this.objName = objName;
	}


	public String getObjTypeName() {
		setObjTypeName(SysStaticDataUtil.getSysStaticDataCodeName("AC_CASH_PROVE@FACE_OBJ_TYPE", objType+""));
		return objTypeName;
	}


	public void setObjTypeName(String objTypeName) {
		this.objTypeName = objTypeName;
	}


	public String getPayStsName() {
		setPayStsName(SysStaticDataUtil.getSysStaticDataCodeName("PAY_STS@ACC_DTL", paySts+""));
		return payStsName;
	}


	public void setPayStsName(String payStsName) {
		this.payStsName = payStsName;
	}


	public String getPayTypeName() {
		setPayTypeName(SysStaticDataUtil.getSysStaticDataCodeName("AC_CASH_PROVE@INOUT_STS", payType+""));
		return payTypeName;
	}


	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}


	private String faceObjTypeName;

	private String consignorName;
	private String consignorLinkmanName;
	private String consignorBill;
	
	private String consigneeName;
	private String consigneeBill;
	private String consigneeTelephone;
	
	public String getConsigneeName() {
		return consigneeName;
	}


	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}


	public String getConsigneeBill() {
		return consigneeBill;
	}


	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}


	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}


	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}


	public String getConsignorName() {
		return consignorName;
	}


	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}


	public String getConsignorLinkmanName() {
		return consignorLinkmanName;
	}


	public void setConsignorLinkmanName(String consignorLinkmanName) {
		this.consignorLinkmanName = consignorLinkmanName;
	}


	public String getConsignorBill() {
		return consignorBill;
	}


	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}


	public long getCheckedId() {
		return checkedId;
	}


	public void setCheckedId(long checkedId) {
		this.checkedId = checkedId;
	}


	public Long getOrderId() {
		return orderId;
	}


	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	public Long getFee() {
		return fee;
	}


	public void setFee(Long fee) {
		this.fee = fee;
	}


	public Integer getPayMold() {
		return payMold;
	}


	public void setPayMold(Integer payMold) {
		this.payMold = payMold;
	}


	public Long getCheckAmount() {
		return checkAmount;
	}


	public void setCheckAmount(Long checkAmount) {
		this.checkAmount = checkAmount;
	}


	public Long getWithoutAmount() {
		return withoutAmount;
	}


	public void setWithoutAmount(Long withoutAmount) {
		this.withoutAmount = withoutAmount;
	}


	public Integer getCheckSts() {
		return checkSts;
	}


	public void setCheckSts(Integer checkSts) {
		this.checkSts = checkSts;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getCheckDate() {
		return checkDate;
	}


	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}


	public Long getOpId() {
		return opId;
	}


	public void setOpId(Long opId) {
		this.opId = opId;
	}


	public Long getCheckOrg() {
		return checkOrg;
	}


	public void setCheckOrg(Long checkOrg) {
		this.checkOrg = checkOrg;
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


	public int getFaceObjType() {
		return faceObjType;
	}


	public void setFaceObjType(int faceObjType) {
		this.faceObjType = faceObjType;
	}


	public Long getFaceObjId() {
		return faceObjId;
	}


	public void setFaceObjId(Long faceObjId) {
		this.faceObjId = faceObjId;
	}

 


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getSysRemark() {
		return sysRemark;
	}


	public void setSysRemark(String sysRemark) {
		this.sysRemark = sysRemark;
	}


	public String getCheckStsName() {
		setCheckStsName(SysStaticDataUtil.getSysStaticDataCodeName("AC_CASH_PROVE@CHECK_STS", checkSts+""));
		return checkStsName;
	}


	public void setCheckStsName(String checkStsName) {
		this.checkStsName = checkStsName;
	}


	public String getFaceObjTypeName() {
		setFaceObjTypeName(SysStaticDataUtil.getSysStaticDataCodeName("AC_CASH_PROVE@FACE_OBJ_TYPE", faceObjType+""));
		return faceObjTypeName;
	}


	public void setFaceObjTypeName(String faceObjTypeName) {
		this.faceObjTypeName = faceObjTypeName;
	}


	public String getTrackingNum() {
		return trackingNum;
	}


	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}


	public String getFaceObjName() {
		return faceObjName;
	}


	public void setFaceObjName(String faceObjName) {
		this.faceObjName = faceObjName;
	}
	
	
}
