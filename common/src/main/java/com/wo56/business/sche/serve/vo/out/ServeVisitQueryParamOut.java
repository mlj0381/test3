package com.wo56.business.sche.serve.vo.out;

import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class ServeVisitQueryParamOut extends BaseOutParamVO {

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
	private Integer source;
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
	/**   
	 * 回访任务ID.  
	 */
	private Long id;

	/**   
	 * 服务态度.  
	 */
	private Integer level;

	/**   
	 * 访问内容.  
	 */
	private String question;

	/**   
	 * 回访状态.  
	 */
	private Integer state;
	
	private String stateName;

	/**   
	 * 操作人ID.  
	 */
	private Long opId;

	/**   
	 * 操作人姓名.  
	 */
	private String opName;

	/**   
	 * 操作时间.  
	 */
	private Date opDate;

	/**   
	 * 拓展字段1.  
	 */
	private String ext1;

	/**   
	 * 拓展字段2.  
	 */
	private String ext2;

	/**   
	 * 拓展字段3.  
	 */
	private String ext3;

	/**   
	 * 拓展字段4.  
	 */
	private String ext4;

	/**   
	 * 拓展字段5.  
	 */
	private String ext5;
	
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
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public String getExt4() {
		return ext4;
	}
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}
	public String getExt5() {
		return ext5;
	}
	public void setExt5(String ext5) {
		this.ext5 = ext5;
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
