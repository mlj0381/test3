package com.wo56.business.route.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class TowardsAndDtlOut extends BaseOutParamVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3835937746019601078L;
	/**序号**/
	private Integer sequence;
	/**结束网点Id**/
	private Long endOrgId;
	/**结束网点名称**/
	private String endOrgName;
	/**路由编号**/
	private Long routingId;
	/**起始网点Id**/
	private Long beginOrgId;
	/**起始网点名称**/
	private String beginOrgName;
	
	
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Long getEndOrgId() {
		return endOrgId;
	}
	public void setEndOrgId(Long endOrgId) {
		this.endOrgId = endOrgId;
	}

	public String getEndOrgName() {
		return endOrgName;
	}
	public void setEndOrgName(String endOrgName) {
		this.endOrgName = endOrgName;
	}
	public Long getRoutingId() {
		return routingId;
	}
	public void setRoutingId(Long routingId) {
		this.routingId = routingId;
	}
	public Long getBeginOrgId() {
		return beginOrgId;
	}
	public void setBeginOrgId(Long beginOrgId) {
		this.beginOrgId = beginOrgId;
	}
	public String getBeginOrgName() {
		return beginOrgName;
	}
	public void setBeginOrgName(String beginOrgName) {
		this.beginOrgName = beginOrgName;
	}
	
}
