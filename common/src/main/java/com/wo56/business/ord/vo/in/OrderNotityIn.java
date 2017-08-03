package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrderNotityIn implements IParamIn{
	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.SAVE_ORDER_NOTIFY;
	}
	private Long opId;
	private String imagePath;
	private String sourceInformation;
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
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
}
