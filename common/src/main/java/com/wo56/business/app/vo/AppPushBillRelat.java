package com.wo56.business.app.vo;

import com.framework.core.base.POJO;

@SuppressWarnings("serial")
public class AppPushBillRelat  extends POJO implements java.io.Serializable {
	private long pushRelatId;
	private String billId;
	private String pushAppId;
	private String pushChannelId;
	private String pushUserId;
	//extends from POJO
	//private String channelType;

	public AppPushBillRelat() {
	}

	public AppPushBillRelat(long pushRelatId) {
		this.pushRelatId = pushRelatId;
	}

	public AppPushBillRelat(long pushRelatId, String billId, String pushAppId,
			String pushChannelId, String pushUserId, String channelType) {
		this.pushRelatId = pushRelatId;
		this.billId = billId;
		this.pushAppId = pushAppId;
		this.pushChannelId = pushChannelId;
		this.pushUserId = pushUserId;
		this.channelType = channelType;
	}

	public long getPushRelatId() {
		return this.pushRelatId;
	}

	public void setPushRelatId(long pushRelatId) {
		this.pushRelatId = pushRelatId;
	}

	public String getBillId() {
		return this.billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public String getPushAppId() {
		return this.pushAppId;
	}

	public void setPushAppId(String pushAppId) {
		this.pushAppId = pushAppId;
	}

	public String getPushChannelId() {
		return this.pushChannelId;
	}

	public void setPushChannelId(String pushChannelId) {
		this.pushChannelId = pushChannelId;
	}

	public String getPushUserId() {
		return this.pushUserId;
	}

	public void setPushUserId(String pushUserId) {
		this.pushUserId = pushUserId;
	}

}
