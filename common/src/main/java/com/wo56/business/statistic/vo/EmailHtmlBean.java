package com.wo56.business.statistic.vo;

import java.util.List;

public class EmailHtmlBean {
	private String title;
	private List<String> css;
	private List<String> content;

	public EmailHtmlBean() {
	}

	public EmailHtmlBean(String title, List<String> css, List<String> content) {
		super();
		this.title = title;
		this.css = css;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getCss() {
		return css;
	}

	public void setCss(List<String> css) {
		this.css = css;
	}

	public List<String> getContent() {
		return content;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}
}
