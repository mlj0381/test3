package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdNotifyIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.SAVE_ORD_NOTIFY;
	}
	/**订单编号**/
	private Long orderId;
	/**类型**/
	private Integer type;
	/**备注**/
	private String notes;
	/**接听人名称**/
	private String receiveCall;
	/**预约送货时间**/
	private String schedulingDate;
	/**车牌号**/
	private String platenumber;
	/**司机电话号码**/
	private String driverPhone;
	/**签收类型： 0 自提签收，1送货上门签收**/
	private Integer deliveryType;
	/**司机名称*/
	private String driverName;
	
	public String getNotes() {
		return notes;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getReceiveCall() {
		return receiveCall;
	}
	public void setReceiveCall(String receiveCall) {
		this.receiveCall = receiveCall;
	}
	
	public String getSchedulingDate() {
		return schedulingDate;
	}
	public void setSchedulingDate(String schedulingDate) {
		this.schedulingDate = schedulingDate;
	}
	public String getPlatenumber() {
		return platenumber;
	}
	public void setPlatenumber(String platenumber) {
		this.platenumber = platenumber;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}
	

}
