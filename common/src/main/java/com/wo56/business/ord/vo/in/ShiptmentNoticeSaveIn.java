package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class ShiptmentNoticeSaveIn implements IParamIn{
	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.SAVE_ORDER_NOTIFY;
	}
	private String imagePath;
	private Long imageId;
	private String sourceInformation;
	private Long orderId;
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getSourceInformation() {
		return sourceInformation;
	}
	public void setSourceInformation(String sourceInformation) {
		this.sourceInformation = sourceInformation;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getImageId() {
		return imageId;
	}
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	
	
}
