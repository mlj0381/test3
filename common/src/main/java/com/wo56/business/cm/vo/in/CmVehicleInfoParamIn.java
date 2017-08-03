package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;
/**
 * 
 * @author zjy
 * (1) 表示必填 （0）  非必传
 * 
 * */
public class CmVehicleInfoParamIn extends PageInParamVO implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CM.QUERY_VEHICLE;
	}
	/**车牌号码**/
	private String plateNumber;
	/***
	 * 车辆状态
	 * */
	private Integer vehicleState;
	/**
	 * 创建车辆信息的网点
	 */
	private Long orgId;
	/**目的网点id*/
	private Long descOrgId;

	public Long getDescOrgId() {
		return descOrgId;
	}

	public void setDescOrgId(Long descOrgId) {
		this.descOrgId = descOrgId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getVehicleState() {
		return vehicleState;
	}

	public void setVehicleState(Integer vehicleState) {
		this.vehicleState = vehicleState;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	

}
