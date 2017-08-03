package com.wo56.business.sche.serve.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class ServeComplaintSaveParamIn extends PageInParamVO implements IParamIn {

	private long mainId;
	/**   
	 * 投诉严重级别.  
	 */
	private Integer level;
	
	private String levelName;

	/**   
	 * 来源.  
	 */
	private Integer sourceType;

	/**   
	 * 投诉内容.  
	 */
	private String complaintContent;

	/**   
	 * 被投诉方企业名称.  
	 */
	private String bcComName;

	/**   
	 * 被投诉方企业ID.  
	 */
	private Long bcOrgId;

	/**   
	 * 被投诉方企业部门.  
	 */
	private String bcDepName;

	/**   
	 * 被投诉方企业部门ID.  
	 */
	private Long bcDepId;

	/**   
	 * 被投诉方负责人.  
	 */
	private String bcLeader;

	/**   
	 * 被投诉师傅名称.  
	 */
	private String sfUserName;

	/**   
	 * 被投诉师傅ID.  
	 */
	private Long bcSfUserId;

	/**   
	 * 被投诉方联系电话.  
	 */
	private String bcBillId;

	/**   
	 * 被投诉方通讯地址.  
	 */
	private String bcAddress;

	/**   
	 * 投诉方联系电话.  
	 */
	private String CPhone;

	/**   
	 * 投诉方用户编号.  
	 */
	private Long CUserId;

	/**   
	 * 投诉方通讯地址.  
	 */
	private String CAddress;

	/**   
	 * 投诉方要求.  
	 */
	private String CWangTo;

	/**   
	 * 关联单号.  
	 */
	private String relateOrder;

	/**   
	 * 处理状态.  
	 */
	private Integer state;
	
	private String stateName;

	/**   
	 * 处理人.  
	 */
	private String dealOpName;

	/**   
	 * 处理人ID.  
	 */
	private Long dealOpId;

	/**   
	 * 处理情况说明.  
	 */
	private String dealResult;
	
	private long  tenantType;
	
	private long tenantId;

	public long getTenantType() {
		return tenantType;
	}


	public void setTenantType(long tenantType) {
		this.tenantType = tenantType;
	}


	public long getTenantId() {
		return tenantId;
	}


	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}


	public long getMainId() {
		return mainId;
	}


	public void setMainId(long mainId) {
		this.mainId = mainId;
	}

	/**   
	 * 备注.  
	 */
	private String remark;
	
	public Integer getLevel() {
		return level;
	}


	public void setLevel(Integer level) {
		this.level = level;
	}


	public String getLevelName() {
		return levelName;
	}


	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}


	public Integer getSourceType() {
		return sourceType;
	}


	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}


	public String getComplaintContent() {
		return complaintContent;
	}


	public void setComplaintContent(String complaintContent) {
		this.complaintContent = complaintContent;
	}


	public String getBcComName() {
		return bcComName;
	}


	public void setBcComName(String bcComName) {
		this.bcComName = bcComName;
	}


	public Long getBcOrgId() {
		return bcOrgId;
	}


	public void setBcOrgId(Long bcOrgId) {
		this.bcOrgId = bcOrgId;
	}


	public String getBcDepName() {
		return bcDepName;
	}


	public void setBcDepName(String bcDepName) {
		this.bcDepName = bcDepName;
	}


	public Long getBcDepId() {
		return bcDepId;
	}


	public void setBcDepId(Long bcDepId) {
		this.bcDepId = bcDepId;
	}


	public String getBcLeader() {
		return bcLeader;
	}


	public void setBcLeader(String bcLeader) {
		this.bcLeader = bcLeader;
	}


	public String getSfUserName() {
		return sfUserName;
	}


	public void setSfUserName(String sfUserName) {
		this.sfUserName = sfUserName;
	}


	public Long getBcSfUserId() {
		return bcSfUserId;
	}


	public void setBcSfUserId(Long bcSfUserId) {
		this.bcSfUserId = bcSfUserId;
	}


	public String getBcBillId() {
		return bcBillId;
	}


	public void setBcBillId(String bcBillId) {
		this.bcBillId = bcBillId;
	}


	public String getBcAddress() {
		return bcAddress;
	}


	public void setBcAddress(String bcAddress) {
		this.bcAddress = bcAddress;
	}


	public String getCPhone() {
		return CPhone;
	}


	public void setCPhone(String cPhone) {
		CPhone = cPhone;
	}


	public Long getCUserId() {
		return CUserId;
	}


	public void setCUserId(Long cUserId) {
		CUserId = cUserId;
	}


	public String getCAddress() {
		return CAddress;
	}


	public void setCAddress(String cAddress) {
		CAddress = cAddress;
	}


	public String getCWangTo() {
		return CWangTo;
	}


	public void setCWangTo(String cWangTo) {
		CWangTo = cWangTo;
	}


	public String getRelateOrder() {
		return relateOrder;
	}


	public void setRelateOrder(String relateOrder) {
		this.relateOrder = relateOrder;
	}

	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	public String getStateName() {
		return stateName;
	}


	public void setStateName(String stateName) {
		this.stateName = stateName;
	}


	public String getDealOpName() {
		return dealOpName;
	}


	public void setDealOpName(String dealOpName) {
		this.dealOpName = dealOpName;
	}


	public Long getDealOpId() {
		return dealOpId;
	}


	public void setDealOpId(Long dealOpId) {
		this.dealOpId = dealOpId;
	}


	public String getDealResult() {
		return dealResult;
	}


	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.SERVE.COMPLAINT_SAVE;
	}

}
