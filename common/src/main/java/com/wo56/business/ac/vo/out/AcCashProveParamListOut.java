package com.wo56.business.ac.vo.out;

import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class AcCashProveParamListOut{
	
	/**   
	 * 核销ID.  
	 */
	private long checkedId;
	private String trackingNum;

	private Long fee;
	private Integer feeType;
	private String feeTypeName;
	private double feeDouble;
	private String consignorName;
	private String consignorBill;
	


	public String getConsignorName() {
		return consignorName;
	}


	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}


	public String getConsignorBill() {
		return consignorBill;
	}


	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}


	public double getFeeDouble() {
		setFeeDouble(CommonUtil.getDoubleFormatLongMoney(fee, 1));
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
	 * 支付类型名称
	 */
	private String payMoldName;
	/**   
	 * 核销状态 1、已核销2、未核销3、部分核销.  
	 */
	private Integer checkSts;

	/**   
	 * 创建日期.  
	 */
	private String createDate;
	/**   
	 * 核销日期.  
	 */
	private String checkDate;
	/**   
	 * 操作员ID.  
	 */
	private String opIdName;
	/**   
	 * 收支类型:1收入2支出. .  
	 */
	private int payType;

	/**   
	 * 备注.  
	 */
	private String remark;
	/**   
	 * 系统备注.  
	 */
	private String sysRemark;
	private String checkStsName;
	private Integer paySts;
	private String payStsName;
	private String payTypeName;
	private long orgId;
	private String orgIdName;
	private long tenantId;
	private String tenantIdName;
	public Integer getPaySts() {
		return paySts;
	}


	public void setPaySts(Integer paySts) {
		this.paySts = paySts;
	}


	public long getTenantId() {
		return tenantId;
	}


	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}


	public String getTenantIdName() {
		setTenantIdName(CommonUtil.getTennatNameById(tenantId));
		return tenantIdName;
	}


	public void setTenantIdName(String tenantIdName) {
		this.tenantIdName = tenantIdName;
	}
	private long sellerTenantId;
	private String sellerTenantIdName;

	public long getCheckedId() {
		return checkedId;
	}


	public void setCheckedId(long checkedId) {
		this.checkedId = checkedId;
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


	public Integer getFeeType() {
		return feeType;
	}


	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}


	public String getFeeTypeName() {
		setFeeTypeName(SysStaticDataUtil.getSysStaticDataCodeName("AC_FEE_CONFIG@FEE_TYPE", feeType+""));
		return feeTypeName;
	}


	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}


	public Integer getPayMold() {
		return payMold;
	}


	public void setPayMold(Integer payMold) {
		this.payMold = payMold;
	}

	
	public String getPayMoldName() {
		if(payMold != null && payMold >0 ){
			setPayMoldName(SysStaticDataUtil.getSysStaticDataCodeName("AC_FEE_CONFIG@PAY_MODE", payMold+""));
		}
		
		return payMoldName;
	}


	public void setPayMoldName(String payMoldName) {
		this.payMoldName = payMoldName;
	}


	public Integer getCheckSts() {
		return checkSts;
	}


	public void setCheckSts(Integer checkSts) {
		this.checkSts = checkSts;
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public String getCheckDate() {
		return checkDate;
	}


	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}


	public String getOpIdName() {
		return opIdName;
	}


	public void setOpIdName(String opIdName) {
		this.opIdName = opIdName;
	}


	public int getPayType() {
		return payType;
	}


	public void setPayType(int payType) {
		this.payType = payType;
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


	public long getOrgId() {
		return orgId;
	}


	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}


	public String getOrgIdName() {
		setOrgIdName(OraganizationCacheDataUtil.getOrgName(orgId));
		return orgIdName;
	}


	public void setOrgIdName(String orgIdName) {
		this.orgIdName = orgIdName;
	}


	public long getSellerTenantId() {
		
		return sellerTenantId;
	}


	public void setSellerTenantId(long sellerTenantId) {
		this.sellerTenantId = sellerTenantId;
	}


	public String getSellerTenantIdName() {
		setSellerTenantIdName(CommonUtil.getTennatNameById(sellerTenantId));
		return sellerTenantIdName;
	}


	public void setSellerTenantIdName(String sellerTenantIdName) {
		this.sellerTenantIdName = sellerTenantIdName;
	}
	
}
