package com.wo56.business.statistic.vo;

import java.util.Map;
import java.util.Set;

import org.hibernate.Session;

public class ContentBuilderParam {
	//private StatisticItem item;
	private Set<Integer> itemSubscriberIdSet;
	private Map<Integer, Integer> subscriberOrgMap;
	private Map<String, String> macroParams;
	private Map sqlParams;
	private Session session;

	public ContentBuilderParam() {
	}

//	public ContentBuilderParam(StatisticItem item,
//			Set<Integer> itemSubscriberIdSet,
//			Map<Integer, Integer> subscriberOrgMap,
//			Map<String, String> macroParams, Map sqlParams, Session session) {
//		super();
//		this.item = item;
//		this.itemSubscriberIdSet = itemSubscriberIdSet;
//		this.subscriberOrgMap = subscriberOrgMap;
//		this.macroParams = macroParams;
//		this.sqlParams = sqlParams;
//		this.session = session;
//	}

//	public StatisticItem getItem() {
//		return item;
//	}
//
//	public void setItem(StatisticItem item) {
//		this.item = item;
//	}

	public Set<Integer> getItemSubscriberIdSet() {
		return itemSubscriberIdSet;
	}

	public void setItemSubscriberIdSet(Set<Integer> itemSubscriberIdSet) {
		this.itemSubscriberIdSet = itemSubscriberIdSet;
	}

	public Map<Integer, Integer> getSubscriberOrgMap() {
		return subscriberOrgMap;
	}

	public void setSubscriberOrgMap(Map<Integer, Integer> subscriberOrgMap) {
		this.subscriberOrgMap = subscriberOrgMap;
	}

	public Map<String, String> getMacroParams() {
		return macroParams;
	}

	public void setMacroParams(Map<String, String> macroParams) {
		this.macroParams = macroParams;
	}

	public Map getSqlParams() {
		return sqlParams;
	}

	public void setSqlParams(Map sqlParams) {
		this.sqlParams = sqlParams;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
