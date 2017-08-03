package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdMatchQueryIn extends PageInParamVO implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.QUERY_MATCH;
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
	
	private String flagSts;
	
	private String isShort; //默认干线 1:短驳
	

	private String batchNumAlias; //自定批次号
	
	private String loadOpName;//配载人名称
	private String plateNumber;//车牌号
	private String _sum;//是否汇总
	private String inputParamJson;//表格参数
	
	
	
	public String getLoadOpName() {
		return loadOpName;
	}
	public void setLoadOpName(String loadOpName) {
		this.loadOpName = loadOpName;
	}
	public String getBatchNumAlias() {
		return batchNumAlias;
	}
	public void setBatchNumAlias(String batchNumAlias) {
		this.batchNumAlias = batchNumAlias;
	}
	public String getIsShort() {
		return isShort;
	}
	public void setIsShort(String isShort) {
		this.isShort = isShort;
	}
	public String getFlagSts() {
		return flagSts;
	}
	public void setFlagSts(String flagSts) {
		this.flagSts = flagSts;
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
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String get_sum() {
		return _sum;
	}
	public void set_sum(String _sum) {
		this._sum = _sum;
	}
	public String getInputParamJson() {
		return inputParamJson;
	}
	public void setInputParamJson(String inputParamJson) {
		this.inputParamJson = inputParamJson;
	}
	
	
	
}
