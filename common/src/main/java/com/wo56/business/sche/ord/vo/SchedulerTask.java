package com.wo56.business.sche.ord.vo;

import java.sql.Timestamp;
import java.util.Date;

import com.framework.core.base.POJO;
import com.framework.core.util.SysStaticDataUtil;

/**
 * SchedulerTask entity. @author MyEclipse Persistence Tools
 */

public class SchedulerTask extends POJO implements java.io.Serializable {

	// Fields

	private Long taskId;
	private Long parentId;
	private Integer taskType;
	private Integer taskState;
	private Long bussId;
	private Long belongObjId;
	private String belongObjName;
	private Integer belongObjType;
	private Long doObjId;
	private String doObjName;
	private Integer doObjType;
	private Long totalMoney;
	private Integer sts;
	private Date createTime;
	private Long opId;
	private String matchRemark;
	private Integer seq;
	//private Long tenantId;
	private Integer withdrawSts;
	private Integer isMacthData;
	private Integer matchType;
	private Integer turnReceiptType;
	private String creator;
	private Long cancerUserId;
	private String cancerUserName;
	private Integer cancerType;
	private String cancerRemark;
	private String dZPaper;
	private String taskStateName;
	private String cancerTypeName;
	private String matchTypeName;
	private Date cancerTime;
	private Date pickTime;
	private Date signTime;
	private String ipFixTime;
	private Date yyTime;
	private Date distributionTime;
	private Date acceptTime;
	private Integer isShowMoney;
	private String dzPaper;
	private Date upFixTime;
	private String belongTel;
	private String doTel;
	private String taskTypeName;
	private Integer source;
	private Integer isRepair;
	private Integer isSche;
	// Constructors
	private Date gxStartTime;
	private Date gxEndTime;
	private Date checkDate;
	private String branchAndInstallFee;
	private String trackNum;
	private Date arriveGoodsTime;
	private Long disUserId;
	private Long disOrgId;
	private String disUserNmae;
	
	
	
	
	public String getDisUserNmae() {
		return disUserNmae;
	}

	public void setDisUserNmae(String disUserNmae) {
		this.disUserNmae = disUserNmae;
	}

	public Long getDisUserId() {
		return disUserId;
	}

	public void setDisUserId(Long disUserId) {
		this.disUserId = disUserId;
	}

	public Long getDisOrgId() {
		return disOrgId;
	}

	public void setDisOrgId(Long disOrgId) {
		this.disOrgId = disOrgId;
	}

	public Date getArriveGoodsTime() {
		return arriveGoodsTime;
	}

	public void setArriveGoodsTime(Date arriveGoodsTime) {
		this.arriveGoodsTime = arriveGoodsTime;
	}

	public String getBranchAndInstallFee() {
		return branchAndInstallFee;
	}

	public void setBranchAndInstallFee(String branchAndInstallFee) {
		this.branchAndInstallFee = branchAndInstallFee;
	}

	private Date arrivalDate;
	
	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getTrackNum() {
		return trackNum;
	}

	public void setTrackNum(String trackNum) {
		this.trackNum = trackNum;
	}

	

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getGxStartTime() {
		return gxStartTime;
	}

	public void setGxStartTime(Date gxStartTime) {
		this.gxStartTime = gxStartTime;
	}

	public Date getGxEndTime() {
		return gxEndTime;
	}

	public void setGxEndTime(Date gxEndTime) {
		this.gxEndTime = gxEndTime;
	}

	public Integer getSource() {
		return source;
	}

	public Date getYyTime() {
		return yyTime;
	}

	public void setYyTime(Date yyTime) {
		this.yyTime = yyTime;
	}

	public Integer getIsSche() {
		return isSche;
	}

	public void setIsSche(Integer isSche) {
		this.isSche = isSche;
	}

	public Integer getIsRepair() {
		return isRepair;
	}

	public void setIsRepair(Integer isRepair) {
		this.isRepair = isRepair;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getTaskTypeName() {
		if(taskType!=null){
			setTaskTypeName(SysStaticDataUtil.getSysStaticDataCodeName("SCHE_TASK_TYPE", taskType+""));
		}
		return taskTypeName;
	}

	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	public Date getUpFixTime() {
		return upFixTime;
	}

	public String getBelongTel() {
		return belongTel;
	}

	public void setBelongTel(String belongTel) {
		this.belongTel = belongTel;
	}

	public String getDoTel() {
		return doTel;
	}

	public void setDoTel(String doTel) {
		this.doTel = doTel;
	}

	public void setUpFixTime(Date upFixTime) {
		this.upFixTime = upFixTime;
	}

	public String getDzPaper() {
		return dzPaper;
	}

	public void setDzPaper(String dzPaper) {
		this.dzPaper = dzPaper;
	}

	public Integer getIsShowMoney() {
		return isShowMoney;
	}

	public void setIsShowMoney(Integer isShowMoney) {
		this.isShowMoney = isShowMoney;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	/** default constructor */
	public SchedulerTask() {
	}

	public Date getPickTime() {
		return pickTime;
	}

	public void setPickTime(Date pickTime) {
		this.pickTime = pickTime;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getIpFixTime() {
		return ipFixTime;
	}

	public void setIpFixTime(String ipFixTime) {
		this.ipFixTime = ipFixTime;
	}

	public Date getDistributionTime() {
		return distributionTime;
	}

	public void setDistributionTime(Date distributionTime) {
		this.distributionTime = distributionTime;
	}

	/** minimal constructor */
	public SchedulerTask(Long parentId, Integer taskType, Integer taskState,
			Long bussId, Long belongObjId, String belongObjName,
			Integer belongObjType, Long doObjId, String doObjName,
			Integer doObjType, Long totalMoney, Integer sts,
			Timestamp createTime, Long opId, Integer seq, Long tenantId,
			Integer withdrawSts) {
		this.parentId = parentId;
		this.taskType = taskType;
		this.taskState = taskState;
		this.bussId = bussId;
		this.belongObjId = belongObjId;
		this.belongObjName = belongObjName;
		this.belongObjType = belongObjType;
		this.doObjId = doObjId;
		this.doObjName = doObjName;
		this.doObjType = doObjType;
		this.totalMoney = totalMoney;
		this.sts = sts;
		this.createTime = createTime;
		this.opId = opId;
		this.seq = seq;
		this.tenantId = tenantId;
		this.withdrawSts = withdrawSts;
	}

	/** full constructor */
	public SchedulerTask(Long parentId, Integer taskType, Integer taskState,
			Long bussId, Long belongObjId, String belongObjName,
			Integer belongObjType, Long doObjId, String doObjName,
			Integer doObjType, Long totalMoney, Integer sts,
			Timestamp createTime, Long opId, String matchRemark, Integer seq,
			Long tenantId, Integer withdrawSts, Integer isMacthData,
			Integer matchType, Integer turnReceiptType, String creator,
			Long cancerUserId, String cancerUserName, Integer cancerType,
			String cancerRemark) {
		this.parentId = parentId;
		this.taskType = taskType;
		this.taskState = taskState;
		this.bussId = bussId;
		this.belongObjId = belongObjId;
		this.belongObjName = belongObjName;
		this.belongObjType = belongObjType;
		this.doObjId = doObjId;
		this.doObjName = doObjName;
		this.doObjType = doObjType;
		this.totalMoney = totalMoney;
		this.sts = sts;
		this.createTime = createTime;
		this.opId = opId;
		this.matchRemark = matchRemark;
		this.seq = seq;
		this.tenantId = tenantId;
		this.withdrawSts = withdrawSts;
		this.isMacthData = isMacthData;
		this.matchType = matchType;
		this.turnReceiptType = turnReceiptType;
		this.creator = creator;
		this.cancerUserId = cancerUserId;
		this.cancerUserName = cancerUserName;
		this.cancerType = cancerType;
		this.cancerRemark = cancerRemark;
	}

	// Property accessors

	public Long getTaskId() {
		return this.taskId;
	}

	public String getdZPaper() {
		return dZPaper;
	}

	public void setdZPaper(String dZPaper) {
		this.dZPaper = dZPaper;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getTaskType() {
		return this.taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Integer getTaskState() {
		return this.taskState;
	}

	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
	}

	public Long getBussId() {
		return this.bussId;
	}

	public void setBussId(Long bussId) {
		this.bussId = bussId;
	}

	public Long getBelongObjId() {
		return this.belongObjId;
	}

	public void setBelongObjId(Long belongObjId) {
		this.belongObjId = belongObjId;
	}

	public String getBelongObjName() {
		return this.belongObjName;
	}

	public void setBelongObjName(String belongObjName) {
		this.belongObjName = belongObjName;
	}

	public Integer getBelongObjType() {
		return this.belongObjType;
	}

	public void setBelongObjType(Integer belongObjType) {
		this.belongObjType = belongObjType;
	}

	public Long getDoObjId() {
		return this.doObjId;
	}

	public void setDoObjId(Long doObjId) {
		this.doObjId = doObjId;
	}

	public String getDoObjName() {
		return this.doObjName;
	}

	public void setDoObjName(String doObjName) {
		this.doObjName = doObjName;
	}

	public Integer getDoObjType() {
		return this.doObjType;
	}

	public void setDoObjType(Integer doObjType) {
		this.doObjType = doObjType;
	}

	public Long getTotalMoney() {
		return this.totalMoney;
	}

	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getSts() {
		return this.sts;
	}

	public void setSts(Integer sts) {
		this.sts = sts;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getMatchRemark() {
		return this.matchRemark;
	}

	public void setMatchRemark(String matchRemark) {
		this.matchRemark = matchRemark;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Long getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getWithdrawSts() {
		return this.withdrawSts;
	}

	public void setWithdrawSts(Integer withdrawSts) {
		this.withdrawSts = withdrawSts;
	}

	public Integer getIsMacthData() {
		return this.isMacthData;
	}

	public void setIsMacthData(Integer isMacthData) {
		this.isMacthData = isMacthData;
	}


	public Integer getTurnReceiptType() {
		return this.turnReceiptType;
	}

	public void setTurnReceiptType(Integer turnReceiptType) {
		this.turnReceiptType = turnReceiptType;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Long getCancerUserId() {
		return this.cancerUserId;
	}

	public void setCancerUserId(Long cancerUserId) {
		this.cancerUserId = cancerUserId;
	}

	public String getCancerUserName() {
		return this.cancerUserName;
	}

	public void setCancerUserName(String cancerUserName) {
		this.cancerUserName = cancerUserName;
	}

	public Integer getCancerType() {
		return this.cancerType;
	}

	public void setCancerType(Integer cancerType) {
		this.cancerType = cancerType;
	}

	public String getCancerRemark() {
		return this.cancerRemark;
	}

	public void setCancerRemark(String cancerRemark) {
		this.cancerRemark = cancerRemark;
	}

	public Integer getMatchType() {
		return matchType;
	}

	public void setMatchType(Integer matchType) {
		this.matchType = matchType;
	}

	public String getTaskStateName() {
		if(taskState!=null&&taskState>0){
			setTaskStateName(SysStaticDataUtil.getSysStaticDataCodeName("TASK_STATE", taskState+""));
		}
		return taskStateName;
	}

	public void setTaskStateName(String taskStateName) {
		this.taskStateName = taskStateName;
	}

	public String getCancerTypeName() {
		if(cancerType!=null&&cancerType>0){
			setCancerTypeName(SysStaticDataUtil.getSysStaticDataCodeName("CANCER_TYPE", cancerType+""));
		}
		return cancerTypeName;
	}

	public void setCancerTypeName(String cancerTypeName) {
		this.cancerTypeName = cancerTypeName;
	}

	public String getMatchTypeName() {
		if(matchType!=null&&matchType>0){
			setMatchTypeName(SysStaticDataUtil.getSysStaticDataCodeName("MATCH_TYPE", matchType+""));
		}
		return matchTypeName;
	}

	public void setMatchTypeName(String matchTypeName) {
		this.matchTypeName = matchTypeName;
	}

	public Date getCancerTime() {
		return cancerTime;
	}

	public void setCancerTime(Date cancerTime) {
		this.cancerTime = cancerTime;
	}
	
	

}