package com.wo56.business.statistic.vo;

/**
 * 订阅者订阅的统计项对应的邮件内容
 * 
 * @author mcfly
 *
 */
public class SubscribeStatisticItemEmailContent {
	private int itemId;// 统计项编号
	private int subscriberId;// 订阅者编号
	private String title;// 邮件标题（主题）
	private String emailContent;// 统计项内容
	private String emailContentCssPath;
	private String emailContentCss;// 邮件可能存在css样式

	public SubscribeStatisticItemEmailContent() {
	}

	public SubscribeStatisticItemEmailContent(int itemId, int subscriberId, String title, String emailContent, String emailContentCssPath, String emailContentCss) {
		super();
		this.itemId = itemId;
		this.subscriberId = subscriberId;
		this.title = title;
		this.emailContent = emailContent;
		this.emailContentCssPath = emailContentCssPath;
		this.emailContentCss = emailContentCss;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(int subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public String getEmailContentCss() {
		return emailContentCss;
	}

	public void setEmailContentCss(String emialContentCss) {
		this.emailContentCss = emialContentCss;
	}
	
	

	public String getEmailContentCssPath() {
		return emailContentCssPath;
	}

	public void setEmailContentCssPath(String emailContentCssPath) {
		this.emailContentCssPath = emailContentCssPath;
	}

	@Override
	public String toString() {
		return "StatisticItemEmailContent [itemId=" + itemId
				+ ", subscriberId=" + subscriberId + ", emailContent="
				+ emailContent + ", emialContentCss=" + emailContentCss + "]";
	}
}
