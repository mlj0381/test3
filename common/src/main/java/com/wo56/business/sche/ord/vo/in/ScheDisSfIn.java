package com.wo56.business.sche.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class ScheDisSfIn extends PageInParamVO implements IParamIn{

	 private  		String taskId ;
	 private String inCode;
	 private String branchAndInstallFee;
	 private String installFee;
	 private String repairFee;
	 private Long sfUserId;
	 private Integer userType;
	 /**调度类型 1 维修调度 2 安装单调度*/
	 private Integer scheType;
	 private String orderId;
	 
	 
	 
	public String getBranchAndInstallFee() {
		return branchAndInstallFee;
	}
	public void setBranchAndInstallFee(String branchAndInstallFee) {
		this.branchAndInstallFee = branchAndInstallFee;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRepairFee() {
		return repairFee;
	}
	public void setRepairFee(String repairFee) {
		this.repairFee = repairFee;
	}
	public Integer getScheType() {
		return scheType;
	}
	public void setScheType(Integer scheType) {
		this.scheType = scheType;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getInstallFee() {
		return installFee;
	}
	public void setInstallFee(String installFee) {
		this.installFee = installFee;
	}
	public Long getSfUserId() {
		return sfUserId;
	}
	public void setSfUserId(Long sfUserId) {
		this.sfUserId = sfUserId;
	}
	 
}
