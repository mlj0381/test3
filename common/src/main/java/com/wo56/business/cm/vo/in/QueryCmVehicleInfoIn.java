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
public class QueryCmVehicleInfoIn extends PageInParamVO implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CM.QUERY_CM_VEHICLE;
	}
	/** 车牌号码 **/
	private String plateNumber;
	/***
	 * 车辆状态
	 */
	private Integer vehicleState;
	/**
	 * 创建车辆信息的网点
	 */
	private Long orgId;
	/** 目的网点id */
	private Integer vehicleType;
	/** 经营方式 */
	private Integer businessType;
	private String inputParamJson;
	

	public String getInputParamJson() {
		return inputParamJson;
	}

	public void setInputParamJson(String inputParamJson) {
		this.inputParamJson = inputParamJson;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Integer getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
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
