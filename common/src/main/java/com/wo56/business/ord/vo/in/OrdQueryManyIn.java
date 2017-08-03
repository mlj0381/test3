package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class OrdQueryManyIn extends PageInParamVO implements IParamIn{

	private static final long serialVersionUID = -3107996056975599605L;
//	@Override
//	public String getInCode() {
//		return InterFacesCodeConsts.ORD.ORD_MANY_QUERY;
//	}
	
	private String inCode;
	public OrdQueryManyIn(String inCode) {
		super();
		this.inCode = inCode;
	}

	public String getInCode() {
		return inCode;
	}

	public void setInCode(String inCode) {
		this.inCode = inCode;
	}

	/**发货方*/
	private String consignorName;
	/** 发货方电话 */
	private String consignorTelephone;
	/**发货人*/
	private String consignorLinkmanName;
	/**发货人手机*/
	private String consignorBill;

	/**收货方*/
	private String consigneeName;
	/** 收货方电话 */
	private String consigneeTelephone;
	/***收货人*/
	private String consigneeLinkmanName;
	/**收货人手机*/
	private String consigneeBill;

	/***外发单号*/
	private String outgoingTrackingNum;
	/**订单id*/
	private long orderId;
	/**订单状态*/
	private Integer orderState;
	/**运单号**/
	private Long trackingNum;
	/** 件数 */
	private int goodsCount;
	/** 货品 */
	private String goodsName;
	
	private int signState;
	private int transitOutgoingState;
	private Long distributionOrgId;//到站
	private long provinceId;
	private long cityId;
	private long countyId;
	private long streetId;
	private String fee;
	
	
	public long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getCountyId() {
		return countyId;
	}

	public void setCountyId(long countyId) {
		this.countyId = countyId;
	}

	public long getStreetId() {
		return streetId;
	}

	public void setStreetId(long streetId) {
		this.streetId = streetId;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getConsigneeBill() {
		return consigneeBill;
	}
	public void setConsigneeBill(String consigneeBill) {
		this.consigneeBill = consigneeBill;
	}
	public String getConsignorBill() {
		return consignorBill;
	}
	public void setConsignorBill(String consignorBill) {
		this.consignorBill = consignorBill;
	}
	public String getConsignorName() {
		return consignorName;
	}
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneeLinkmanName() {
		return consigneeLinkmanName;
	}
	public void setConsigneeLinkmanName(String consigneeLinkmanName) {
		this.consigneeLinkmanName = consigneeLinkmanName;
	}
	public String getConsignorLinkmanName() {
		return consignorLinkmanName;
	}
	public void setConsignorLinkmanName(String consignorLinkmanName) {
		this.consignorLinkmanName = consignorLinkmanName;
	}
	public String getOutgoingTrackingNum() {
		return outgoingTrackingNum;
	}
	public void setOutgoingTrackingNum(String outgoingTrackingNum) {
		this.outgoingTrackingNum = outgoingTrackingNum;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}

	public String getConsignorTelephone() {
		return consignorTelephone;
	}

	public void setConsignorTelephone(String consignorTelephone) {
		this.consignorTelephone = consignorTelephone;
	}

	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}

	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}

	public int getSignState() {
		return signState;
	}

	public void setSignState(int signState) {
		this.signState = signState;
	}

	public int getTransitOutgoingState() {
		return transitOutgoingState;
	}

	public void setTransitOutgoingState(int transitOutgoingState) {
		this.transitOutgoingState = transitOutgoingState;
	}

	public Long getDistributionOrgId() {
		return distributionOrgId;
	}

	public void setDistributionOrgId(Long distributionOrgId) {
		this.distributionOrgId = distributionOrgId;
	}
	



	
}
