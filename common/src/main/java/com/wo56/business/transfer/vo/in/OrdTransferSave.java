package com.wo56.business.transfer.vo.in;

import java.util.List;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.business.transfer.vo.in.save.OrdTransVehicle;
import com.wo56.business.transfer.vo.in.save.OrdTransferCost;
import com.wo56.business.transfer.vo.in.save.OrderSomeInfo;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdTransferSave implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.TRANSFER_SAVE;
	}

	//承运商
	private Long orgId;
	private String departmentAddress;
	private String linkPhone;
	private String csPhones;
	private Integer isMakeup;
	
	
	
	public Integer getIsMakeup() {
		return isMakeup;
	}
	public void setIsMakeup(Integer isMakeup) {
		this.isMakeup = isMakeup;
	}
	public String getDepartmentAddress() {
		return departmentAddress;
	}
	public void setDepartmentAddress(String departmentAddress) {
		this.departmentAddress = departmentAddress;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getCsPhones() {
		return csPhones;
	}
	public void setCsPhones(String csPhones) {
		this.csPhones = csPhones;
	}

	//批次号
	private String batchNum;
	//运单列表
	private List<OrderSomeInfo> orderList;
	//车辆信息
	private OrdTransVehicle vehicleInfo;
	//费用信息
	private OrdTransferCost ordTransferCost;
	
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
