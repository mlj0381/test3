package com.wo56.business.statistic.vo;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EMailAuthenticator extends Authenticator {
	private String username = null;
	private String password = null;

	public EMailAuthenticator() {
	}

	public EMailAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
}
