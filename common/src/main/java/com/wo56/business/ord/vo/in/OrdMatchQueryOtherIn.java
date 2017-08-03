package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdMatchQueryOtherIn extends PageInParamVO implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.QUERY_MATCH_OTHER;
	}
	/**开始时间（0）**/
	private String beginTime;
	/**结束时间（0）**/
	private String endTime;
	/**批次号（0）*/
	private long batchNum;
	/**始发网点（0）*/
	private long startOrgId;
	/**目的网点（0）*/
	private String descOrgId;
	/**车辆状态*/
	private Integer vehicleState;
	/**配载标记*/
	private String flag;
	/**类型 1:送货上门 2：中转送货上门*/
	private int businessType;
	
	public int getBusinessType() {
		return businessType;
	}
	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public long getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(long batchNum) {
		this.batchNum = batchNum;
	}
	public long getStartOrgId() {
		return startOrgId;
	}
	public void setStartOrgId(long startOrgId) {
		this.startOrgId = startOrgId;
	}
	public String getDescOrgId() {
		return descOrgId;
	}
	public void setDescOrgId(String descOrgId) {
		this.descOrgId = descOrgId;
	}
	public Integer getVehicleState() {
		return vehicleState;
	}
	public void setVehicleState(Integer vehicleState) {
		this.vehicleState = vehicleState;
	}
	
	
}
