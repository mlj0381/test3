package com.wo56.business.transfer.vo.out;

import java.util.List;

import com.framework.core.svcaller.vo.BaseOutParamVO;
import com.wo56.business.ord.vo.out.OrderInfoSimpleOut;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.transfer.vo.in.save.OrdTransVehicle;
import com.wo56.business.transfer.vo.in.save.OrdTransferCost;
import com.wo56.business.transfer.vo.in.save.OrderSomeInfo;

public class QueryTransOut extends BaseOutParamVO{

	/**
	 * 中转详情
	 */
	private static final long serialVersionUID = 19992884764672L;
	//承运商
	private Organization orgInfo;
	//运单列表
	private List<OrderSomeInfo> orderList;
	//车辆信息
	private OrdTransVehicle vehicleInfo;
	//费用信息
	private OrdTransferCost ordTransferCost;
	private List<OrderInfoSimpleOut> orderInfoList;
	
	
	public List<OrderInfoSimpleOut> getOrderInfoList() {
		return orderInfoList;
	}
	public void setOrderInfoList(List<OrderInfoSimpleOut> orderInfoList) {
		this.orderInfoList = orderInfoList;
	}
	public Organization getOrgInfo() {
		return orgInfo;
	}
	public void setOrgInfo(Organization orgInfo) {
		this.orgInfo = orgInfo;
	}
	public List<OrderSomeInfo> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrderSomeInfo> orderList) {
		this.orderList = orderList;
	}
	public OrdTransVehicle getVehicleInfo() {
		return vehicleInfo;
	}
	public void setVehicleInfo(OrdTransVehicle vehicleInfo) {
		this.vehicleInfo = vehicleInfo;
	}
	public OrdTransferCost getOrdTransferCost() {
		return ordTransferCost;
	}
	public void setOrdTransferCost(OrdTransferCost ordTransferCost) {
		this.ordTransferCost = ordTransferCost;
	}
	
}
