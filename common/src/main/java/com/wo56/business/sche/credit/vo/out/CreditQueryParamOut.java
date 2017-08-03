package com.wo56.business.sche.credit.vo.out;

import java.text.DecimalFormat;
import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class CreditQueryParamOut extends BaseOutParamVO {
	private long sfUserId;
	private String phone;
	private String name;
	private Integer jdAmount;
	private String telPhone;
	private String provinceName;
	private Long provinceId;
	private String cityName;
	private Long cityId;
	private String docuType;
	private String docuId;
	private String native_;
	private String homeAddr;
	private Long opId;
	private String opName;
	private Date opDate;
	private String createName;
	private Date createDate;
	private Long createId;
	private String photoUrl;
	private String bankName;
	private String bankAccount;
	private String bankAddr;
	private String accountName;
	private Integer receiType;
	private String paypalAccount;
	private String imagePositive;
	private String imageCounter;
	private Integer auditState;
	private String auditPerson;
	private Date auditTime;
	private String channelId;
	private String regType;
	private Integer settlementPeriod;
	private Long withdrawalsTotal;
	private Long score;
	private Integer dr;
	private String dataSource;
	private String isJzt;
	private Date updateDate;
	private Integer vehicleNums;
	private Integer membersNums;
	private Integer largestAcceptOrder;
	private String storeAddr;
	private String storeEand;
	private String storeNand;
	private Long storeSquare;
	private String remark;
	private Long marginFee;
	private String cooperationType;
	private Long marginFeeLimit;
	private double KpiScore;
	private String strKpiScore;
	private double GxScore;
	private String strGxScore;
	private double ReserveScore;
	private String strReserveScore;
	private double CheckScore;
	private String strCheckScore;
	private double ComplaintScore;
	private String strComplaintScore;
	private String doTel;
	private String doObjName;
	
	public String getDoTel() {
		return doTel;
	}

	public void setDoTel(String doTel) {
		this.doTel = doTel;
	}

	public String getDoObjName() {
		return doObjName;
	}

	public void setDoObjName(String doObjName) {
		this.doObjName = doObjName;
	}

	DecimalFormat df=new DecimalFormat("#.00");
	DecimalFormat df1 = new DecimalFormat("##.00%");
	public double getGxScore() {
		return GxScore;
	}

	public void setGxScore(double gxScore) {
		GxScore = gxScore;
	}

	public String getStrGxScore() {
		if(GxScore!=0){
			setStrGxScore(df1.format(GxScore));
		}
		else
		{
			setStrGxScore("0");
		}
		return strGxScore;
	}

	public void setStrGxScore(String strGxScore) {
		this.strGxScore = strGxScore;
	}

	public double getReserveScore() {
		return ReserveScore;
	}

	public void setReserveScore(double reserveScore) {
		ReserveScore = reserveScore;
	}

	public String getStrReserveScore() {
		if(ReserveScore!=0){
			setStrReserveScore(df1.format(ReserveScore));
		}
		else
		{
			setStrReserveScore("0");
		}
		return strReserveScore;
	}

	public void setStrReserveScore(String strReserveScore) {
		this.strReserveScore = strReserveScore;
	}

	public double getCheckScore() {
		return CheckScore;
	}

	public void setCheckScore(double checkScore) {
		CheckScore = checkScore;
	}

	public String getStrCheckScore() {
		if(CheckScore!=0){
			setStrCheckScore(df1.format(CheckScore));
		}
		else
		{
			setStrCheckScore("0");
		}
		return strCheckScore;
	}

	public void setStrCheckScore(String strCheckScore) {
		this.strCheckScore = strCheckScore;
	}

	public double getComplaintScore() {
		return ComplaintScore;
	}

	public void setComplaintScore(double complaintScore) {
		ComplaintScore = complaintScore;
	}

	public String getStrComplaintScore() {
		if(ComplaintScore!=0){
			setStrComplaintScore(df1.format(ComplaintScore));
		}
		else
		{
			setStrComplaintScore("0");
		}
		return strComplaintScore;
	}

	public void setStrComplaintScore(String strComplaintScore) {
		this.strComplaintScore = strComplaintScore;
	}

	public String getStrKpiScore() {
		if(KpiScore!=0){
			setStrKpiScore(df.format(KpiScore));
		}
		else
		{
			setStrKpiScore("0");
		}
		return strKpiScore;
	}

	public void setStrKpiScore(String strKpiScore) {
		this.strKpiScore = strKpiScore;
	}

	public double getKpiScore() {
		
		return KpiScore;
	}

	public void setKpiScore(double kpiScore) {
		KpiScore = kpiScore;
	}

	/**   
	 * 记录编号.  
	 */
	private long creditId;

	/**   
	 * 用户编号.  
	 */
	private Long userId;

	/**   
	 * 信用类型.  
	 */
	private Integer creditType;

	/**   
	 * 信用级别.  
	 */
	private Integer creditLevel;

	/**   
	 * 信用值.  
	 */
	private Long creditValue;

	private Long flowId;

	private String storePath;

	/**   
	 * 信用状态.  
	 */
	private Integer sts;

	/**   
	 * 状态时间.  
	 */
	private Date stsDate;

	public long getSfUserId() {
		return sfUserId;
	}

	public void setSfUserId(long sfUserId) {
		this.sfUserId = sfUserId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getJdAmount() {
		return jdAmount;
	}

	public void setJdAmount(Integer jdAmount) {
		this.jdAmount = jdAmount;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getDocuType() {
		return docuType;
	}

	public void setDocuType(String docuType) {
		this.docuType = docuType;
	}

	public String getDocuId() {
		return docuId;
	}

	public void setDocuId(String docuId) {
		this.docuId = docuId;
	}

	public String getNative_() {
		return native_;
	}

	public void setNative_(String native_) {
		this.native_ = native_;
	}

	public String getHomeAddr() {
		return homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
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

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankAddr() {
		return bankAddr;
	}

	public void setBankAddr(String bankAddr) {
		this.bankAddr = bankAddr;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getReceiType() {
		return receiType;
	}

	public void setReceiType(Integer receiType) {
		this.receiType = receiType;
	}

	public String getPaypalAccount() {
		return paypalAccount;
	}

	public void setPaypalAccount(String paypalAccount) {
		this.paypalAccount = paypalAccount;
	}

	public String getImagePositive() {
		return imagePositive;
	}

	public void setImagePositive(String imagePositive) {
		this.imagePositive = imagePositive;
	}

	public String getImageCounter() {
		return imageCounter;
	}

	public void setImageCounter(String imageCounter) {
		this.imageCounter = imageCounter;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public String getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public Integer getSettlementPeriod() {
		return settlementPeriod;
	}

	public void setSettlementPeriod(Integer settlementPeriod) {
		this.settlementPeriod = settlementPeriod;
	}

	public Long getWithdrawalsTotal() {
		return withdrawalsTotal;
	}

	public void setWithdrawalsTotal(Long withdrawalsTotal) {
		this.withdrawalsTotal = withdrawalsTotal;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getIsJzt() {
		return isJzt;
	}

	public void setIsJzt(String isJzt) {
		this.isJzt = isJzt;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getVehicleNums() {
		return vehicleNums;
	}

	public void setVehicleNums(Integer vehicleNums) {
		this.vehicleNums = vehicleNums;
	}

	public Integer getMembersNums() {
		return membersNums;
	}

	public void setMembersNums(Integer membersNums) {
		this.membersNums = membersNums;
	}

	public Integer getLargestAcceptOrder() {
		return largestAcceptOrder;
	}

	public void setLargestAcceptOrder(Integer largestAcceptOrder) {
		this.largestAcceptOrder = largestAcceptOrder;
	}

	public String getStoreAddr() {
		return storeAddr;
	}

	public void setStoreAddr(String storeAddr) {
		this.storeAddr = storeAddr;
	}

	public String getStoreEand() {
		return storeEand;
	}

	public void setStoreEand(String storeEand) {
		this.storeEand = storeEand;
	}

	public String getStoreNand() {
		return storeNand;
	}

	public void setStoreNand(String storeNand) {
		this.storeNand = storeNand;
	}

	public Long getStoreSquare() {
		return storeSquare;
	}

	public void setStoreSquare(Long storeSquare) {
		this.storeSquare = storeSquare;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getMarginFee() {
		return marginFee;
	}

	public void setMarginFee(Long marginFee) {
		this.marginFee = marginFee;
	}

	public String getCooperationType() {
		return cooperationType;
	}

	public void setCooperationType(String cooperationType) {
		this.cooperationType = cooperationType;
	}

	public Long getMarginFeeLimit() {
		return marginFeeLimit;
	}

	public void setMarginFeeLimit(Long marginFeeLimit) {
		this.marginFeeLimit = marginFeeLimit;
	}

	public long getCreditId() {
		return creditId;
	}

	public void setCreditId(long creditId) {
		this.creditId = creditId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCreditType() {
		return creditType;
	}

	public void setCreditType(Integer creditType) {
		this.creditType = creditType;
	}

	public Integer getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(Integer creditLevel) {
		this.creditLevel = creditLevel;
	}

	public Long getCreditValue() {
		return creditValue;
	}

	public void setCreditValue(Long creditValue) {
		this.creditValue = creditValue;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public Integer getSts() {
		return sts;
	}

	public void setSts(Integer sts) {
		this.sts = sts;
	}

	public Date getStsDate() {
		return stsDate;
	}

	public void setStsDate(Date stsDate) {
		this.stsDate = stsDate;
	}
	
}
