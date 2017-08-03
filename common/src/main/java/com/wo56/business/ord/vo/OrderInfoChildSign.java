package com.wo56.business.ord.vo;

import java.util.Date;

public class OrderInfoChildSign {
	private Long childOrderId;
	private String childTrackingNum;
	private String signName;
	private Date signTime;
	private Integer signType;
	private String imageUrl;
	private String imageId;
	private String createName;
	private Long createId;
	private String remark;
	private Date createTime;
	public Long getChildOrderId() {
		return childOrderId;
	}
	public void setChildOrderId(Long childOrderId) {
		this.childOrderId = childOrderId;
	}
	public String getChildTrackingNum() {
		return childTrackingNum;
	}
	public void setChildTrackingNum(String childTrackingNum) {
		this.childTrackingNum = childTrackingNum;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public Integer getSignType() {
		return signType;
	}
	public void setSignType(Integer signType) {
		this.signType = signType;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
