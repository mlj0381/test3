package com.wo56.business.sche.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class ScheQueryMacthSfIn extends PageInParamVO implements IParamIn{

	 private  		String taskId ;
	 private String wayBillId;
	 private String isExceSche;
	 private String inCode;
	private String yyTime;
	private String ipFixTime;
	 private String orderId;
	 private String trackingNum;
	 private String sfPhone;
	 private String branchAndInstallFee;
	 private String repairFee;
	 
	 
	 
	public String getBranchAndInstallFee() {
		return branchAndInstallFee;
	}
	public void setBranchAndInstallFee(String branchAndInstallFee) {
		this.branchAndInstallFee = branchAndInstallFee;
	}
	public String getRepairFee() {
		return repairFee;
	}
	public void setRepairFee(String repairFee) {
		this.repairFee = repairFee;
	}
	
	public String getSfPhone() {
		return sfPhone;
	}
	public void setSfPhone(String sfPhone) {
		this.sfPhone = sfPhone;
	}
		public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
		public String getIpFixTime() {
		return ipFixTime;
	}
	public void setIpFixTime(String ipFixTime) {
		this.ipFixTime = ipFixTime;
	}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
	 
	public String getIsExceSche() {
		return isExceSche;
	}
	public void setIsExceSche(String isExceSche) {
		this.isExceSche = isExceSche;
	}
	public String getYyTime() {
		return yyTime;
	}
	public void setYyTime(String yyTime) {
		this.yyTime = yyTime;
	}
	public String getWayBillId() {
		return wayBillId;
	}
	public void setWayBillId(String wayBillId) {
		this.wayBillId = wayBillId;
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
	 
}
