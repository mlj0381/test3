package com.wo56.business.sche.credit.vo;

import java.util.Date;

import org.hibernate.Session;

public class CalculateParam {
	//private CreditCalculateRule rule;
	private Session session;
	private Date startDate;
	private Date endDate;

	public CalculateParam() {
	}

//	public CalculateParam(CreditCalculateRule rule, Session session,
//			Date startDate, Date endDate) {
//		super();
//		this.rule = rule;
//		this.session = session;
//		this.startDate = startDate;
//		this.endDate = endDate;
//	}

//	public CreditCalculateRule getRule() {
//		return rule;
//	}
//
//	public void setRule(CreditCalculateRule rule) {
//		this.rule = rule;
//	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
