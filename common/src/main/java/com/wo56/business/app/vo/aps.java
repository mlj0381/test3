package com.wo56.business.app.vo;

/**
 * ios消息推送
 * @author wengxk
 *
 */
public class aps {
	private String alert = "联运汇";
	private String sound = "default";
	private int badge = 1;

	public aps(String alert) {
		if (alert.length() > 30) {
			this.alert = alert.substring(0, 30).concat("...");
		} else {
			this.alert = alert;
		}
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public int getBadge() {
		return badge;
	}

	public void setBadge(int badge) {
		this.badge = badge;
	}
}