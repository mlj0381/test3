package com.wo56.business.ac.vo.out;

import java.util.Date;

import com.framework.core.util.DateUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.CommonUtil;

public class AcProveShipBatchParamOut{
	private String[] noFiled; //翻译过后前端不需要的字段
	public String[] getNoFiled() {
		String[]  noFileds = new String[]{ "fee","checkAmount",
				                           "checkAmount","withoutAmount","paySts"
				                           ,"payType","objType","noFiled"};
		
		setNoFiled(noFileds);
		return noFiled;
	}
	public void setNoFiled(String[] noFiled) {
		this.noFiled = noFiled;
	}
	private String checkOpName;
	private long checkedId;
	private String feeTypeName;
	
	private Long fee;
	private Long checkAmount;
	private Long withoutAmount;
	
	private Double feeDouble;
	private Double checkAmountDouble;
	private Double withoutAmountDouble;
	
	public Double getFeeDouble() {
		setFeeDouble(CommonUtil.getDoubleFormatLongMoney(fee, 2));
		return feeDouble;
	}
	public void setFeeDouble(Double feeDouble) {
		this.feeDouble = feeDouble;
	}
	public Double getCheckAmountDouble() {
		setCheckAmountDouble(CommonUtil.getDoubleFormatLongMoney(checkAmount, 2));
		return checkAmountDouble;
	}
	public void setCheckAmountDouble(Double checkAmountDouble) {
		this.checkAmountDouble = checkAmountDouble;
	}
	public Double getWithoutAmountDouble() {
		setWithoutAmountDouble(CommonUtil.getDoubleFormatLongMoney(withoutAmount, 2));
		return withoutAmountDouble;
	}
	public void setWithoutAmountDouble(Double withoutAmountDouble) {
		this.withoutAmountDouble = withoutAmountDouble;
	}
	private Integer checkSts;
	private Date checkDate;
	private int paySts;
	private int payType;
	private Integer faceObjType;
	private String faceObjName;
	private String faceObjTypeName;
	private String checkStsName;
	private String payStsName;
	private String payTypeName;
	private Integer objType;
	private String objName;
	private String objTypeName;
	private String goodsNumber;
	private String batchNumAlias;
	private String sourceOrgIdName;
	private String descOrgIdName;
	private String transportContract;
	private String loadOpName;
	private Date loadTime;
	private String remark;
	private String batchNum;
	private String shipDateStr;
	private String carrierNo;
	private String cntrNo;
	private String sealNo;
	
	
	


	public String getCarrierNo() {
		return carrierNo;
	}
	public void setCarrierNo(String carrierNo) {
		this.carrierNo = carrierNo;
	}
	public String getCntrNo() {
		return cntrNo;
	}
	public void setCntrNo(String cntrNo) {
		this.cntrNo = cntrNo;
	}
	public String getSealNo() {
		return sealNo;
	}
	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
	}
	public String getShipDateStr() {
		return shipDateStr;
	}

	public void setShipDateStr(String shipDateStr) {
		this.shipDateStr = shipDateStr;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public long getCheckedId() {
		return checkedId;
	}
	public void setCheckedId(long checkedId) {
		this.checkedId = checkedId;
	}
	public String getFeeTypeName() {
		return feeTypeName;
	}
	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}
	public Long getFee() {
		return fee;
	}
	public void setFee(Long fee) {
		this.fee = fee;
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
	public Integer getFaceObjType() {
		return faceObjType;
	}
	public void setFaceObjType(Integer faceObjType) {
		this.faceObjType = faceObjType;
	}
	public String getFaceObjName() {
		setFaceObjName(SysStaticDataUtil.getSysStaticDataCodeName("AC_CASH_PROVE@FACE_OBJ_TYPE", faceObjType+""));
		return faceObjName;
	}
	public void setFaceObjName(String faceObjName) {
		this.faceObjName = faceObjName;
	}
	public String getFaceObjTypeName() {
		return faceObjTypeName;
	}
	public void setFaceObjTypeName(String faceObjTypeName) {
		this.faceObjTypeName = faceObjTypeName;
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
		setObjName(SysStaticDataUtil.getSysStaticDataCodeName("AC_CASH_PROVE@FACE_OBJ_TYPE", objType+""));
		return objTypeName;
	}
	public void setObjTypeName(String objTypeName) {
		this.objTypeName = objTypeName;
	}
	
	public String getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public String getBatchNumAlias() {
		return batchNumAlias;
	}
	public void setBatchNumAlias(String batchNumAlias) {
		this.batchNumAlias = batchNumAlias;
	}
	public String getSourceOrgIdName() {
		return sourceOrgIdName;
	}
	public void setSourceOrgIdName(String sourceOrgIdName) {
		this.sourceOrgIdName = sourceOrgIdName;
	}
	public String getDescOrgIdName() {
		return descOrgIdName;
	}
	public void setDescOrgIdName(String descOrgIdName) {
		this.descOrgIdName = descOrgIdName;
	}
	public String getTransportContract() {
		return transportContract;
	}
	public void setTransportContract(String transportContract) {
		this.transportContract = transportContract;
	}
	public String getLoadOpName() {
		return loadOpName;
	}
	public void setLoadOpName(String loadOpName) {
		this.loadOpName = loadOpName;
	}
	public Date getLoadTime() {
		return loadTime;
	}
	public void setLoadTime(Date loadTime) {
		this.loadTime = loadTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCheckOpName() {
		return checkOpName;
	}
	public void setCheckOpName(String checkOpName) {
		this.checkOpName = checkOpName;
	}
	
	
}
