package com.wo56.business.sche.serve.vo.out;

import java.sql.Timestamp;
import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class ServeQueryParamOut extends BaseOutParamVO {

	private String wayBillId;
	private String orderId;
	private String customerId;
	private String lineName;
	private String transportType;
	private String orgId;
	private String initStat;
	private String deptId;
	private String deptName;
	private String clientCode;
	private String clientName;
	private String clientTel;
	private String clientPhone;
	private String clientAddress;
	private String receiveCode;
	private String receiveName;
	private String receiveTel;
	private String receivePhone;
	private String receiveProvince;
	private String receiveCity;
	private String receiveCounty;
	private String receiveTown;
	private String receiveAddress;
	private String startId;
	private String startStation;
	private String exchangeType;
	private String serviceType;
	private String serviceOrg;
	private String trunkLineOrg;
	private String returnType;
	private Integer returnTimes;
	private String arriveId;
	private String arriveStation;
	private String takeAddress;
	private String takeTel;
	private String products;
	private String packings;
	private Double items;
	private Double weights;
	private Double volumes;
	private Integer payType;
	private Double payCash;
	private Double payReturn;
	private Double payMonth;
	private Double payArrive;
	private Double payOrder;
	private String salesName;
	private Integer status;
	private String cancelReason;
	private Date cancelDate;
	private String cancelName;
	private String remark;
	private Integer prints;
	private String openName;
	private Date openDate;
	private String driverNo;
	private String driverName;
	private Date createDate;
	private String modifyName;
	private Date modifyDate;
	private Integer isCheck;
	private String oldWayBillId;
	private String tranCity;
	private String cargoStatus;
	private Integer collectMovePay;
	private Integer isCommission;
	private Integer isWaiting;
	private Double tranSportChareg;
	private Double replaceCharge;
	private Double serviceCharge;
	private Double valueCharge;
	private Double protectionChareg;
	private Double payOutChareg;
	private Double deliveryChareg;
	private Double commissionChareg;
	private Double installChareg;
	private Double upstairsChareg;
	private Double stevedoreChareg;
	private Double declarantChareg;
	private Double taxChareg;
	private Double changeChareg;
	private Double forkliftChareg;
	private Double takeChareg;
	private Double orderChareg;
	private Double totalCost;
	private Integer isAgreement;
	private String cardNo;
	private Integer floors;
	private Integer isTmail;
	private Integer isMsfCheck;
	private Date checkDate;
	private String orderSourceCode;
	private Integer checkType;
	private String productsType;
	private Integer isSendClient;
	private Integer isSendReceiv;
	private Integer isCombination;
	private Long receiveProvinceId;
	private Long receiveCityId;
	private Long receiveCountyId;
	private Long receiveTownId;
	private String longitude;
	private String latitude;
	private String carType;
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
	private Double totalMoney;
	private Integer sts;
	private Timestamp createTime;
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
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
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
	/**   
	 * 投诉受理编号.  
	 */
	private Long id;

	/**   
	 * 投诉严重级别.  
	 */
	private Integer level;
	private String levelName;
	/**   
	 * 来源.  
	 */
	private Integer sourceType;

	/**   
	 * 投诉内容.  
	 */
	private String complaintContent;

	/**   
	 * 被投诉方企业名称.  
	 */
	private String bcComName;

	/**   
	 * 被投诉方企业ID.  
	 */
	private Long bcOrgId;

	/**   
	 * 被投诉方企业部门.  
	 */
	private String bcDepName;

	/**   
	 * 被投诉方企业部门ID.  
	 */
	private Long bcDepId;

	/**   
	 * 被投诉方负责人.  
	 */
	private String bcLeader;

	/**   
	 * 被投诉师傅名称.  
	 */
	private String sfUserName;

	/**   
	 * 被投诉师傅ID.  
	 */
	private Long bcSfUserId;

	/**   
	 * 被投诉方联系电话.  
	 */
	private String bcBillId;

	/**   
	 * 被投诉方通讯地址.  
	 */
	private String bcAddress;

	/**   
	 * 投诉方联系电话.  
	 */
	private String CPhone;

	/**   
	 * 投诉方用户编号.  
	 */
	private Long CUserId;

	/**   
	 * 投诉方通讯地址.  
	 */
	private String CAddress;

	/**   
	 * 投诉方要求.  
	 */
	private String CWangTo;

	/**   
	 * 关联单号.  
	 */
	private String relateOrder;

	/**   
	 * 关联单类型.  
	 */
	private Integer relateType;

	/**   
	 * 操作人ID.  
	 */
	private Long opId;

	/**   
	 * 处理日期.  
	 */
	private Date dealDate;
	/**   
	 * 受理日期.  
	 */
	private Date acceptDate;

	/**   
	 * 处理状态.  
	 */
	private Integer state;
	
	private String stateName;

	/**   
	 * 处理人.  
	 */
	private String dealOpName;

	/**   
	 * 处理人ID.  
	 */
	private Long dealOpId;

	/**   
	 * 处理情况说明.  
	 */
	private String dealResult;
	
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	public String getComplaintContent() {
		return complaintContent;
	}
	public void setComplaintContent(String complaintContent) {
		this.complaintContent = complaintContent;
	}
	public String getBcComName() {
		return bcComName;
	}
	public void setBcComName(String bcComName) {
		this.bcComName = bcComName;
	}
	public Long getBcOrgId() {
		return bcOrgId;
	}
	public void setBcOrgId(Long bcOrgId) {
		this.bcOrgId = bcOrgId;
	}
	public String getBcDepName() {
		return bcDepName;
	}
	public void setBcDepName(String bcDepName) {
		this.bcDepName = bcDepName;
	}
	public Long getBcDepId() {
		return bcDepId;
	}
	public void setBcDepId(Long bcDepId) {
		this.bcDepId = bcDepId;
	}
	public String getBcLeader() {
		return bcLeader;
	}
	public void setBcLeader(String bcLeader) {
		this.bcLeader = bcLeader;
	}
	public String getSfUserName() {
		return sfUserName;
	}
	public void setSfUserName(String sfUserName) {
		this.sfUserName = sfUserName;
	}
	public Long getBcSfUserId() {
		return bcSfUserId;
	}
	public void setBcSfUserId(Long bcSfUserId) {
		this.bcSfUserId = bcSfUserId;
	}
	public String getBcBillId() {
		return bcBillId;
	}
	public void setBcBillId(String bcBillId) {
		this.bcBillId = bcBillId;
	}
	public String getBcAddress() {
		return bcAddress;
	}
	public void setBcAddress(String bcAddress) {
		this.bcAddress = bcAddress;
	}
	public String getCPhone() {
		return CPhone;
	}
	public void setCPhone(String cPhone) {
		CPhone = cPhone;
	}
	public Long getCUserId() {
		return CUserId;
	}
	public void setCUserId(Long cUserId) {
		CUserId = cUserId;
	}
	public String getCAddress() {
		return CAddress;
	}
	public void setCAddress(String cAddress) {
		CAddress = cAddress;
	}
	public String getCWangTo() {
		return CWangTo;
	}
	public void setCWangTo(String cWangTo) {
		CWangTo = cWangTo;
	}
	public String getRelateOrder() {
		return relateOrder;
	}
	public void setRelateOrder(String relateOrder) {
		this.relateOrder = relateOrder;
	}
	public Integer getRelateType() {
		return relateType;
	}
	public void setRelateType(Integer relateType) {
		this.relateType = relateType;
	}
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public Date getAcceptDate() {
		return acceptDate;
	}
	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getDealOpName() {
		return dealOpName;
	}
	public void setDealOpName(String dealOpName) {
		this.dealOpName = dealOpName;
	}
	public Long getDealOpId() {
		return dealOpId;
	}
	public void setDealOpId(Long dealOpId) {
		this.dealOpId = dealOpId;
	}
	public String getDealResult() {
		return dealResult;
	}
	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getInitStat() {
		return initStat;
	}
	public void setInitStat(String initStat) {
		this.initStat = initStat;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientTel() {
		return clientTel;
	}
	public void setClientTel(String clientTel) {
		this.clientTel = clientTel;
	}
	public String getClientPhone() {
		return clientPhone;
	}
	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}
	public String getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	public String getReceiveCode() {
		return receiveCode;
	}
	public void setReceiveCode(String receiveCode) {
		this.receiveCode = receiveCode;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getReceiveTel() {
		return receiveTel;
	}
	public void setReceiveTel(String receiveTel) {
		this.receiveTel = receiveTel;
	}
	public String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	public String getReceiveProvince() {
		return receiveProvince;
	}
	public void setReceiveProvince(String receiveProvince) {
		this.receiveProvince = receiveProvince;
	}
	public String getReceiveCity() {
		return receiveCity;
	}
	public void setReceiveCity(String receiveCity) {
		this.receiveCity = receiveCity;
	}
	public String getReceiveCounty() {
		return receiveCounty;
	}
	public void setReceiveCounty(String receiveCounty) {
		this.receiveCounty = receiveCounty;
	}
	public String getReceiveTown() {
		return receiveTown;
	}
	public void setReceiveTown(String receiveTown) {
		this.receiveTown = receiveTown;
	}
	
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getStartId() {
		return startId;
	}
	public void setStartId(String startId) {
		this.startId = startId;
	}
	public String getStartStation() {
		return startStation;
	}
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}
	public String getExchangeType() {
		return exchangeType;
	}
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceOrg() {
		return serviceOrg;
	}
	public void setServiceOrg(String serviceOrg) {
		this.serviceOrg = serviceOrg;
	}
	public String getTrunkLineOrg() {
		return trunkLineOrg;
	}
	public void setTrunkLineOrg(String trunkLineOrg) {
		this.trunkLineOrg = trunkLineOrg;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public Integer getReturnTimes() {
		return returnTimes;
	}
	public void setReturnTimes(Integer returnTimes) {
		this.returnTimes = returnTimes;
	}
	public String getArriveId() {
		return arriveId;
	}
	public void setArriveId(String arriveId) {
		this.arriveId = arriveId;
	}
	public String getArriveStation() {
		return arriveStation;
	}
	public void setArriveStation(String arriveStation) {
		this.arriveStation = arriveStation;
	}
	public String getTakeAddress() {
		return takeAddress;
	}
	public void setTakeAddress(String takeAddress) {
		this.takeAddress = takeAddress;
	}
	public String getTakeTel() {
		return takeTel;
	}
	public void setTakeTel(String takeTel) {
		this.takeTel = takeTel;
	}
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public String getPackings() {
		return packings;
	}
	public void setPackings(String packings) {
		this.packings = packings;
	}
	public Double getItems() {
		return items;
	}
	public void setItems(Double items) {
		this.items = items;
	}
	public Double getWeights() {
		return weights;
	}
	public void setWeights(Double weights) {
		this.weights = weights;
	}
	public Double getVolumes() {
		return volumes;
	}
	public void setVolumes(Double volumes) {
		this.volumes = volumes;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Double getPayCash() {
		return payCash;
	}
	public void setPayCash(Double payCash) {
		this.payCash = payCash;
	}
	public Double getPayReturn() {
		return payReturn;
	}
	public void setPayReturn(Double payReturn) {
		this.payReturn = payReturn;
	}
	public Double getPayMonth() {
		return payMonth;
	}
	public void setPayMonth(Double payMonth) {
		this.payMonth = payMonth;
	}
	public Double getPayArrive() {
		return payArrive;
	}
	public void setPayArrive(Double payArrive) {
		this.payArrive = payArrive;
	}
	public Double getPayOrder() {
		return payOrder;
	}
	public void setPayOrder(Double payOrder) {
		this.payOrder = payOrder;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	public String getCancelName() {
		return cancelName;
	}
	public void setCancelName(String cancelName) {
		this.cancelName = cancelName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getPrints() {
		return prints;
	}
	public void setPrints(Integer prints) {
		this.prints = prints;
	}
	public String getOpenName() {
		return openName;
	}
	public void setOpenName(String openName) {
		this.openName = openName;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public String getDriverNo() {
		return driverNo;
	}
	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public Integer getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}
	public String getOldWayBillId() {
		return oldWayBillId;
	}
	public void setOldWayBillId(String oldWayBillId) {
		this.oldWayBillId = oldWayBillId;
	}
	public String getTranCity() {
		return tranCity;
	}
	public void setTranCity(String tranCity) {
		this.tranCity = tranCity;
	}
	public String getCargoStatus() {
		return cargoStatus;
	}
	public void setCargoStatus(String cargoStatus) {
		this.cargoStatus = cargoStatus;
	}
	public Integer getCollectMovePay() {
		return collectMovePay;
	}
	public void setCollectMovePay(Integer collectMovePay) {
		this.collectMovePay = collectMovePay;
	}
	public Integer getIsCommission() {
		return isCommission;
	}
	public void setIsCommission(Integer isCommission) {
		this.isCommission = isCommission;
	}
	public Integer getIsWaiting() {
		return isWaiting;
	}
	public void setIsWaiting(Integer isWaiting) {
		this.isWaiting = isWaiting;
	}
	public Double getTranSportChareg() {
		return tranSportChareg;
	}
	public void setTranSportChareg(Double tranSportChareg) {
		this.tranSportChareg = tranSportChareg;
	}
	public Double getReplaceCharge() {
		return replaceCharge;
	}
	public void setReplaceCharge(Double replaceCharge) {
		this.replaceCharge = replaceCharge;
	}
	public Double getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public Double getValueCharge() {
		return valueCharge;
	}
	public void setValueCharge(Double valueCharge) {
		this.valueCharge = valueCharge;
	}
	public Double getProtectionChareg() {
		return protectionChareg;
	}
	public void setProtectionChareg(Double protectionChareg) {
		this.protectionChareg = protectionChareg;
	}
	public Double getPayOutChareg() {
		return payOutChareg;
	}
	public void setPayOutChareg(Double payOutChareg) {
		this.payOutChareg = payOutChareg;
	}
	public Double getDeliveryChareg() {
		return deliveryChareg;
	}
	public void setDeliveryChareg(Double deliveryChareg) {
		this.deliveryChareg = deliveryChareg;
	}
	public Double getCommissionChareg() {
		return commissionChareg;
	}
	public void setCommissionChareg(Double commissionChareg) {
		this.commissionChareg = commissionChareg;
	}
	public Double getInstallChareg() {
		return installChareg;
	}
	public void setInstallChareg(Double installChareg) {
		this.installChareg = installChareg;
	}
	public Double getUpstairsChareg() {
		return upstairsChareg;
	}
	public void setUpstairsChareg(Double upstairsChareg) {
		this.upstairsChareg = upstairsChareg;
	}
	public Double getStevedoreChareg() {
		return stevedoreChareg;
	}
	public void setStevedoreChareg(Double stevedoreChareg) {
		this.stevedoreChareg = stevedoreChareg;
	}
	public Double getDeclarantChareg() {
		return declarantChareg;
	}
	public void setDeclarantChareg(Double declarantChareg) {
		this.declarantChareg = declarantChareg;
	}
	public Double getTaxChareg() {
		return taxChareg;
	}
	public void setTaxChareg(Double taxChareg) {
		this.taxChareg = taxChareg;
	}
	public Double getChangeChareg() {
		return changeChareg;
	}
	public void setChangeChareg(Double changeChareg) {
		this.changeChareg = changeChareg;
	}
	public Double getForkliftChareg() {
		return forkliftChareg;
	}
	public void setForkliftChareg(Double forkliftChareg) {
		this.forkliftChareg = forkliftChareg;
	}
	public Double getTakeChareg() {
		return takeChareg;
	}
	public void setTakeChareg(Double takeChareg) {
		this.takeChareg = takeChareg;
	}
	public Double getOrderChareg() {
		return orderChareg;
	}
	public void setOrderChareg(Double orderChareg) {
		this.orderChareg = orderChareg;
	}
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	public Integer getIsAgreement() {
		return isAgreement;
	}
	public void setIsAgreement(Integer isAgreement) {
		this.isAgreement = isAgreement;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Integer getFloors() {
		return floors;
	}
	public void setFloors(Integer floors) {
		this.floors = floors;
	}
	public Integer getIsTmail() {
		return isTmail;
	}
	public void setIsTmail(Integer isTmail) {
		this.isTmail = isTmail;
	}
	public Integer getIsMsfCheck() {
		return isMsfCheck;
	}
	public void setIsMsfCheck(Integer isMsfCheck) {
		this.isMsfCheck = isMsfCheck;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getOrderSourceCode() {
		return orderSourceCode;
	}
	public void setOrderSourceCode(String orderSourceCode) {
		this.orderSourceCode = orderSourceCode;
	}
	public Integer getCheckType() {
		return checkType;
	}
	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}
	public String getProductsType() {
		return productsType;
	}
	public void setProductsType(String productsType) {
		this.productsType = productsType;
	}
	public Integer getIsSendClient() {
		return isSendClient;
	}
	public void setIsSendClient(Integer isSendClient) {
		this.isSendClient = isSendClient;
	}
	public Integer getIsSendReceiv() {
		return isSendReceiv;
	}
	public void setIsSendReceiv(Integer isSendReceiv) {
		this.isSendReceiv = isSendReceiv;
	}
	public Integer getIsCombination() {
		return isCombination;
	}
	public void setIsCombination(Integer isCombination) {
		this.isCombination = isCombination;
	}
	public Long getReceiveProvinceId() {
		return receiveProvinceId;
	}
	public void setReceiveProvinceId(Long receiveProvinceId) {
		this.receiveProvinceId = receiveProvinceId;
	}
	public Long getReceiveCityId() {
		return receiveCityId;
	}
	public void setReceiveCityId(Long receiveCityId) {
		this.receiveCityId = receiveCityId;
	}
	public Long getReceiveCountyId() {
		return receiveCountyId;
	}
	public void setReceiveCountyId(Long receiveCountyId) {
		this.receiveCountyId = receiveCountyId;
	}
	public Long getReceiveTownId() {
		return receiveTownId;
	}
	public void setReceiveTownId(Long receiveTownId) {
		this.receiveTownId = receiveTownId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}

}
