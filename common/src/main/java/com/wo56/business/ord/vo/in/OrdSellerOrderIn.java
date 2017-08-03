package com.wo56.business.ord.vo.in;

import java.sql.Timestamp;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class OrdSellerOrderIn extends PageInParamVO implements IParamIn{
	private String inCode;
	private Long sellerOrderId;
	private Integer lineType;
	private Integer source;
	private Integer level;
	private String prodId;
	private String prodName;
	private String prodAbout;
	private String stere;
	private String weight;
	private Integer GCount;
	private Long provinceId;
	private String provinceName;
	private Long cityId;
	private String cityName;
	private Long countyId;
	private String countyName;
	private Long townId;
	private String townName;
	private String receiverTel;
	private String receiverName;
	private String receiverAddress;
	private Integer transportType;
	private Long orgId;
	private Long tenantId;
	private String orgName;
	private String orgTel;
	private String orgRemark;
	private Integer orderType;
	private String orderId;
	private String trackNum;
	private Long lineOrgId;
	private Long lineTenantId;
	private Integer state;
	private String lineContractor;
	private String lineTel;
	private String lineRemark;
	private Long lineContractorId;
	private Timestamp opDate;
	private Long notifyId;
	private Integer singlesSts;
	private String sellerOrderIds;
	private String createDate;
	private Long trackingNum;
	
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public OrdSellerOrderIn(String inCode){
		this.inCode=inCode;
	}
	public Long getSellerOrderId() {
		return sellerOrderId;
	}
	public void setSellerOrderId(Long sellerOrderId) {
		this.sellerOrderId = sellerOrderId;
	}
	public Integer getLineType() {
		return lineType;
	}
	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdAbout() {
		return prodAbout;
	}
	public void setProdAbout(String prodAbout) {
		this.prodAbout = prodAbout;
	}
	public String getStere() {
		return stere;
	}
	public void setStere(String stere) {
		this.stere = stere;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public Integer getGCount() {
		return GCount;
	}
	public void setGCount(Integer gCount) {
		GCount = gCount;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Long getCountyId() {
		return countyId;
	}
	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public Long getTownId() {
		return townId;
	}
	public void setTownId(Long townId) {
		this.townId = townId;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public String getReceiverTel() {
		return receiverTel;
	}
	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public Integer getTransportType() {
		return transportType;
	}
	public void setTransportType(Integer transportType) {
		this.transportType = transportType;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgTel() {
		return orgTel;
	}
	public void setOrgTel(String orgTel) {
		this.orgTel = orgTel;
	}
	public String getOrgRemark() {
		return orgRemark;
	}
	public void setOrgRemark(String orgRemark) {
		this.orgRemark = orgRemark;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTrackNum() {
		return trackNum;
	}
	public void setTrackNum(String trackNum) {
		this.trackNum = trackNum;
	}
	public Long getLineOrgId() {
		return lineOrgId;
	}
	public void setLineOrgId(Long lineOrgId) {
		this.lineOrgId = lineOrgId;
	}
	public Long getLineTenantId() {
		return lineTenantId;
	}
	public void setLineTenantId(Long lineTenantId) {
		this.lineTenantId = lineTenantId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getLineContractor() {
		return lineContractor;
	}
	public void setLineContractor(String lineContractor) {
		this.lineContractor = lineContractor;
	}
	public String getLineTel() {
		return lineTel;
	}
	public void setLineTel(String lineTel) {
		this.lineTel = lineTel;
	}
	public String getLineRemark() {
		return lineRemark;
	}
	public void setLineRemark(String lineRemark) {
		this.lineRemark = lineRemark;
	}
	public Long getLineContractorId() {
		return lineContractorId;
	}
	public void setLineContractorId(Long lineContractorId) {
		this.lineContractorId = lineContractorId;
	}
	public Timestamp getOpDate() {
		return opDate;
	}
	public void setOpDate(Timestamp opDate) {
		this.opDate = opDate;
	}
	public Long getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(Long notifyId) {
		this.notifyId = notifyId;
	}
	public Integer getSinglesSts() {
		return singlesSts;
	}
	public void setSinglesSts(Integer singlesSts) {
		this.singlesSts = singlesSts;
	}
	public String getSellerOrderIds() {
		return sellerOrderIds;
	}
	public void setSellerOrderIds(String sellerOrderIds) {
		this.sellerOrderIds = sellerOrderIds;
	}
	@Override
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	
}
