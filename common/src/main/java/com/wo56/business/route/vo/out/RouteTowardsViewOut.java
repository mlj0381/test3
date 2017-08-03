package com.wo56.business.route.vo.out;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class RouteTowardsViewOut extends BaseOutParamVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3582458247602030112L;
	/**线路走向id*/
	private long towardsId;
	/**开始网点名称**/
	private String beginOrgName;
	/**目的网点名称*/
	private String endOrgName;
	/**路由编码*/
	private Long routingId;
	/**顺序*/
	private Integer  sequence;
	public long getTowardsId() {
		return towardsId;
	}
	public void setTowardsId(long towardsId) {
		this.towardsId = towardsId;
	}
	
	public String getBeginOrgName() {
		return beginOrgName;
	}
	public void setBeginOrgName(String beginOrgName) {
		this.beginOrgName = beginOrgName;
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
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	
	
	
}
