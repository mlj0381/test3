package com.wo56.business.ord.vo.out;

import java.util.Date;

import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.util.DateUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class OrdFeeOut {
/**科目类型**/
private Integer feeType;
/**科目名称**/
private String feeName;
/**收入金额**/
private double incomeAmount;
/**已收金额**/
private double isIncomeAmount;
/**未收金额**/
private double noIncomeAmount;
/**支出金额**/
private double payAmount;
/**已支出金额**/
private double isPayAmount;
/**未支出金额**/
private double noPayAmount;
/**收入票数**/
private int incomeCount;
/**支出票数**/
private int payCount;
/**创建时间**/
private Date createDate;
/**运单号**/
private Long trackingNum;
/** 总重量 */
private String weightString;
/** 总体积 */
private String volumeString;
private int checkSts;
private String checkStsName;
/** 发货方 */
private String consignorLinkmanName;
/*** 发货人联系电话 */
private String consignorTelephone;
/*** 发货人 */
private String consignorName;
/*** 发货人联系手机 */
private String consignorBill;
private String goodsName;
/** 货物数量 */
private Integer count;
/** 批次号 */
private Long batchNum;
/** 司机名称 */
private String driverName;
/** 司机手机 */
private String driverBill;
/** 车牌号 */
private String plateNumber;
/** 发车网点 */
private Long sourceOrgId;
/**目的网点 */
private Long descOrgId;



/** 发车网点 （中文） */
private String sourceOrgName;
/**目的网点（中文） */
private String descOrgName;


private Integer cashSts;
private Integer inoutSts;
private String cashStsName;


private String createDateString;
private String typeName;
private String comName;
private String inoutStsName;
private Integer isSysDo;

public String getCashStsName() {
	if(this.getCashSts()!=null && this.getCashSts()>-1){
		SysStaticData ssd = SysStaticDataUtil.getSysStaticData("AC_CASH_PROVE@CASH_STS", String.valueOf(this.getCashSts()));
		if(ssd != null){
			this.setCashStsName(ssd.getCodeName());
		}
	}
	return cashStsName;
}

public void setCashStsName(String cashStsName) {
	this.cashStsName = cashStsName;
}

public String getCheckStsName() {
	if( this.getCheckSts()>-1){
		SysStaticData ssd = SysStaticDataUtil.getSysStaticData("AC_CASH_PROVE@CHECK_STS", String.valueOf(this.getCheckSts()));
		if(ssd != null){
			this.setCheckStsName(ssd.getCodeName());
		}
	}
	return checkStsName;
}

public void setCheckStsName(String checkStsName) {
	this.checkStsName = checkStsName;
}
public Integer getCashSts() {
	return cashSts;
}

public void setCashSts(Integer cashSts) {
	this.cashSts = cashSts;
}

public String getWeightString() {
	return weightString;
}

public void setWeightString(String weightString) {
	this.weightString = weightString;
}

public String getVolumeString() {
	return volumeString;
}

public void setVolumeString(String volumeString) {
	this.volumeString = volumeString;
}


public String getTypeName() {
	return typeName;
}

public void setTypeName(String typeName) {
	this.typeName = typeName;
}


public String getComName() {
	return comName;
}

public void setComName(String comName) {
	this.comName = comName;
}

public String getCreateDateString() {
	if(createDate != null){
		setCreateDateString(DateUtil.formatDate(createDate, "yyyy-MM-dd"));
	}
	return createDateString;
}

public void setCreateDateString(String createDateString) {
	this.createDateString = createDateString;
}
public String getFeeName() {
	return feeName;
}
public void setFeeName(String feeName) {
	this.feeName = feeName;
}
public double getIncomeAmount() {
	return incomeAmount;
}
public void setIncomeAmount(double incomeAmount) {
	this.incomeAmount = incomeAmount;
}
public double getIsIncomeAmount() {
	return isIncomeAmount;
}
public void setIsIncomeAmount(double isIncomeAmount) {
	this.isIncomeAmount = isIncomeAmount;
}
public double getNoIncomeAmount() {
	return noIncomeAmount;
}
public void setNoIncomeAmount(double noIncomeAmount) {
	this.noIncomeAmount = noIncomeAmount;
}
public double getPayAmount() {
	return payAmount;
}
public void setPayAmount(double payAmount) {
	this.payAmount = payAmount;
}
public double getIsPayAmount() {
	return isPayAmount;
}
public void setIsPayAmount(double isPayAmount) {
	this.isPayAmount = isPayAmount;
}
public double getNoPayAmount() {
	return noPayAmount;
}
public void setNoPayAmount(double noPayAmount) {
	this.noPayAmount = noPayAmount;
}
public int getIncomeCount() {
	return incomeCount;
}
public void setIncomeCount(int incomeCount) {
	this.incomeCount = incomeCount;
}
public int getPayCount() {
	return payCount;
}
public void setPayCount(int payCount) {
	this.payCount = payCount;
}
public Integer getFeeType() {
	return feeType;
}
public void setFeeType(Integer feeType) {
	this.feeType = feeType;
}
public Date getCreateDate() {
	return createDate;
}
public void setCreateDate(Date createDate) {
	this.createDate = createDate;
}
public Long getTrackingNum() {
	return trackingNum;
}
public void setTrackingNum(Long trackingNum) {
	this.trackingNum = trackingNum;
}

public int getCheckSts() {
	return checkSts;
}

public void setCheckSts(int checkSts) {
	this.checkSts = checkSts;
}

public String getConsignorLinkmanName() {
	return consignorLinkmanName;
}

public void setConsignorLinkmanName(String consignorLinkmanName) {
	this.consignorLinkmanName = consignorLinkmanName;
}

public String getConsignorTelephone() {
	return consignorTelephone;
}

public void setConsignorTelephone(String consignorTelephone) {
	this.consignorTelephone = consignorTelephone;
}

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

public String getGoodsName() {
	return goodsName;
}

public void setGoodsName(String goodsName) {
	this.goodsName = goodsName;
}

public Integer getCount() {
	return count;
}

public void setCount(Integer count) {
	this.count = count;
}

public Integer getInoutSts() {
	return inoutSts;
}

public void setInoutSts(Integer inoutSts) {
	this.inoutSts = inoutSts;
}

public Long getBatchNum() {
	return batchNum;
}

public void setBatchNum(Long batchNum) {
	this.batchNum = batchNum;
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

public String getPlateNumber() {
	return plateNumber;
}

public void setPlateNumber(String plateNumber) {
	this.plateNumber = plateNumber;
}

public Long getSourceOrgId() {
	return sourceOrgId;
}

public void setSourceOrgId(Long sourceOrgId) {
	this.sourceOrgId = sourceOrgId;
}

public Long getDescOrgId() {
	return descOrgId;
}

public void setDescOrgId(Long descOrgId) {
	this.descOrgId = descOrgId;
}

public String getSourceOrgName() {
	if(sourceOrgId != null && sourceOrgId>0){
		setSourceOrgName(OraganizationCacheDataUtil.getOrgName(sourceOrgId));
	}
	return sourceOrgName;
}

public void setSourceOrgName(String sourceOrgName) {
	this.sourceOrgName = sourceOrgName;
}

public String getDescOrgName() {
	if(descOrgId != null && descOrgId>0){
		setDescOrgName(OraganizationCacheDataUtil.getOrgName(descOrgId));
	}
	return descOrgName;
}

public void setDescOrgName(String descOrgName) {
	this.descOrgName = descOrgName;
}

public String getInoutStsName() {
	if( this.getInoutSts()!=null && this.getInoutSts()>-1){
		SysStaticData ssd = SysStaticDataUtil.getSysStaticData("AC_CASH_PROVE@INOUT_STS", String.valueOf(this.getInoutSts()));
		if(ssd != null){
			this.setInoutStsName(ssd.getCodeName());
		}
	}
	return inoutStsName;
}

public void setInoutStsName(String inoutStsName) {
	this.inoutStsName = inoutStsName;
}

public Integer getIsSysDo() {
	return isSysDo;
}

public void setIsSysDo(Integer isSysDo) {
	this.isSysDo = isSysDo;
}


}
