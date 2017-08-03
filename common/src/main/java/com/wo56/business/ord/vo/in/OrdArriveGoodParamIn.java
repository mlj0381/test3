package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

/**
 * @author zjy
 * (1) 必传 （0）选传
 * 
 * 
 * */
public class OrdArriveGoodParamIn  implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		 return InterFacesCodeConsts.ORD.ARRIVE_GOOD;
	}

	/***批次号id(1)*/
	private long batchNum;
	/**订单号 （1）支持多个（格式：2314423423434,34324234234324）多个时以","分隔**/
	private String orderId;
	public long getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(long batchNum) {
		this.batchNum = batchNum;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
