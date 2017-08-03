package com.wo56.business.ord.vo.in;

import java.util.Date;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class TrackingDetailSaveIn implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORD_TRACKINGDETAIL_IN;
	}

	private Long id;
	private Long orderId;
	private Date arrGoodsTime;
	private Date starVehicleTime;
	private Date expectDate;
	private Integer signState;
	private Date signDate;
	private Long signOpId;
	private Integer signCertificateType;
	private Long signCertificate;
	private Integer signType;
	private Date createDate;
	private Date receiptDate;
	private Integer isArrGoods;
	private Integer isSendMessage;
	private String signName;
	private Long tenantId;
	private Long isReceipt;
	private Integer isTracking;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Date getArrGoodsTime() {
		return arrGoodsTime;
	}
	public void setArrGoodsTime(Date arrGoodsTime) {
		this.arrGoodsTime = arrGoodsTime;
	}
	public Date getStarVehicleTime() {
		return starVehicleTime;
	}
	public void setStarVehicleTime(Date starVehicleTime) {
		this.starVehicleTime = starVehicleTime;
	}
	public Date getExpectDate() {
		return expectDate;
	}
	public void setExpectDate(Date expectDate) {
		this.expectDate = expectDate;
	}
	public Integer getSignState() {
		return signState;
	}
	public void setSignState(Integer signState) {
		this.signState = signState;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public Long getSignOpId() {
		return signOpId;
	}
	public void setSignOpId(Long signOpId) {
		this.signOpId = signOpId;
	}
	public Integer getSignCertificateType() {
		return signCertificateType;
	}
	public void setSignCertificateType(Integer signCertificateType) {
		this.signCertificateType = signCertificateType;
	}
	public Long getSignCertificate() {
		return signCertificate;
	}
	public void setSignCertificate(Long signCertificate) {
		this.signCertificate = signCertificate;
	}
	public Integer getSignType() {
		return signType;
	}
	public void setSignType(Integer signType) {
		this.signType = signType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}
	public Integer getIsArrGoods() {
		return isArrGoods;
	}
	public void setIsArrGoods(Integer isArrGoods) {
		this.isArrGoods = isArrGoods;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public Integer getIsTracking() {
		return isTracking;
	}
	public void setIsTracking(Integer isTracking) {
		this.isTracking = isTracking;
	}

	public Integer getIsSendMessage() {
		return isSendMessage;
	}
	public void setIsSendMessage(Integer isSendMessage) {
		this.isSendMessage = isSendMessage;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public Long getIsReceipt() {
		return isReceipt;
	}
	public void setIsReceipt(Long isReceipt) {
		this.isReceipt = isReceipt;
	}
	

	
}
