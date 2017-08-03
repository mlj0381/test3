package com.wo56.business.ac.vo.out;

import java.util.Date;

import com.framework.core.util.SysStaticDataUtil;

public class AcAccountDtlParamOut{
	
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
	
	private int feeType;
	/**   
	 * 核销科目
	 */
	private String feeTypeName;
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
	private int payType;
	public int getPayType() {
		return payType;
	}


	public void setPayType(int payType) {
		this.payType = payType;
	}

	/**   
	 * 核销状态 1、已核销2、未核销3、部分核销.  
	 */
	private Integer checkSts;
	
	
	private String checkStsName;
	/**   
	 * 核销日期.  
	 */
	private Date checkDate;
	
	private Date createDate;

	public int getFeeType() {
		return feeType;
	}


	public void setFeeType(int feeType) {
		this.feeType = feeType;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**   
	 * 核销人ID.  
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
 
	private String payStsName;
	private String payTypeName;
	private Integer objType;
	/**   
	 * 核销人名称.  
	 */
	private String opIdName;
	
	public String getOpIdName() {
		return opIdName;
	}


	public void setOpIdName(String opIdName) {
		this.opIdName = opIdName;
	}


	public String getFeeTypeName() {
		setFeeTypeName(SysStaticDataUtil.getSysStaticDataCodeName("AC_FEE_CONFIG@FEE_TYPE", feeType+""));
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


	public String getTrackingNum() {
		return trackingNum;
	}


	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
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


	public Integer getObjType() {
		return objType;
	}


	public void setObjType(Integer objType) {
		this.objType = objType;
	}
	
	public String getCheckStsName() {
		setCheckStsName(SysStaticDataUtil.getSysStaticDataCodeName("AC_CASH_PROVE@CHECK_STS", checkSts+""));
		return checkStsName;
	}


	public void setCheckStsName(String checkStsName) {
		this.checkStsName = checkStsName;
	}

	
}
