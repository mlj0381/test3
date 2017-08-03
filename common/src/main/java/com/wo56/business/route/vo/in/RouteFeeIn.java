package com.wo56.business.route.vo.in;

import java.util.Map;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class RouteFeeIn implements IParamIn{
	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ROUTE.QUERY_TOWARDS_FEE;
	}
	/**订单ID**/
	private Long orderId;
	
	/**计费规则
	 * 1：按重量计算
	 * 2：按体积计算
	 */
	private Integer feeType;
	/**起始网点Id**/
	private Long beginOrgId;
	/**结束网点ID**/
	private Long endOrgId;
	/**目的区域**/
	private String city;
	/**交接方式 0 自提 1 送货上门 2 送货上楼 DELIVERY_TYPE**/
	private Integer transitionWay; 
	/**重量**/
	private double weight;
	/**体积**/
	private double volume;
	/**计算规则明细
	 * 是否有电梯（key=1004 value=1注：1 表示有 0 表示没有）
	 * 楼层（key=1005 value=楼层） 
	 * 代收货款（key=1006 value=金额（单位:分））
	 * 是否存在配送区域 （key='isMap',value=N N 表示没有）
	 * 值定义
	 * 是否有电梯、楼层建立在上楼费存在的情况 （1）**/
	private Map iparmMap;
	/**开单员Id**/
	private Long inputUserId;
	/**业务员Id**/
	private  Long salesmanId;
	/**货物标识**/
	private  String goodMapType;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getFeeType() {
		return feeType;
	}
	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	public Long getBeginOrgId() {
		return beginOrgId;
	}
	public void setBeginOrgId(Long beginOrgId) {
		this.beginOrgId = beginOrgId;
	}
	public Long getEndOrgId() {
		return endOrgId;
	}
	public void setEndOrgId(Long endOrgId) {
		this.endOrgId = endOrgId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getTransitionWay() {
		return transitionWay;
	}
	public void setTransitionWay(Integer transitionWay) {
		this.transitionWay = transitionWay;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public Map getIparmMap() {
		return iparmMap;
	}
	public void setIparmMap(Map iparmMap) {
		this.iparmMap = iparmMap;
	}
	public Long getInputUserId() {
		return inputUserId;
	}
	public void setInputUserId(Long inputUserId) {
		this.inputUserId = inputUserId;
	}
	public Long getSalesmanId() {
		return salesmanId;
	}
	public void setSalesmanId(Long salesmanId) {
		this.salesmanId = salesmanId;
	}
	public String getGoodMapType() {
		return goodMapType;
	}
	public void setGoodMapType(String goodMapType) {
		this.goodMapType = goodMapType;
	}
	
	
}
	
	