package com.wo56.business.transfer.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class TransferBatch implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.QUERY_TRANSFER;
	}
	/**发车网点*/
	private Long startOrgId; 
	/**到达网点*/
	private Long descOrgId ;
	/**批次号*/
	private Long batchNum ;
	/**订单号*/
	private Long orderId ;
	/**开始时间*/
	private String beginTime;
	/**结束时间*/
	private String endTime;
	private int page;
	private int rows;
	private Long trackingNum;
	
	
	
	public Long getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(Long trackingNum) {
		this.trackingNum = trackingNum;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public Long getStartOrgId() {
		return startOrgId;
	}
	public void setStartOrgId(Long startOrgId) {
		this.startOrgId = startOrgId;
	}
	public Long getDescOrgId() {
		return descOrgId;
	}
	public void setDescOrgId(Long descOrgId) {
		this.descOrgId = descOrgId;
	}
	public Long getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(Long batchNum) {
		this.batchNum = batchNum;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	
}
