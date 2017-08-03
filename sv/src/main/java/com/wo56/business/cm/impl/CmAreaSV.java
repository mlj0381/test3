package com.wo56.business.cm.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.util.DataFormat;
import com.wo56.business.cm.vo.CmArea;

public class CmAreaSV {
	
	/**
	 * 保存与修改
	 * @param cmArea
	 */
	public void doSaveOrUpdate(CmArea cmArea){
		Session sessionUpdate = SysContexts.getEntityManager(false);
		sessionUpdate.saveOrUpdate(cmArea);
	}
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	public CmArea getCmAreaById(long id){
		Session sessionQuery = SysContexts.getEntityManager(true);
		return (CmArea) sessionQuery.get(CmArea.class,id);
	}
	/**
	 * 列表查询
	 * @param inParam
	 * @return
	 */
	public Criteria getCmAreaList(Map<String,Object> inParam){
		Session sessionQuery = SysContexts.getEntityManager(true);
		Criteria ca = sessionQuery.createCriteria(CmArea.class);
		long province = DataFormat.getLongKey(inParam, "province");
		if (province > 0) ca.add(Restrictions.eq("province", province));
		long city = DataFormat.getLongKey(inParam, "city");
		if (city > 0) ca.add(Restrictions.eq("city", city));
		long district = DataFormat.getLongKey(inParam, "district");
		if (district > 0)  ca.add(Restrictions.eq("district", district));
		String keyWords = DataFormat.getStringKey(inParam, "keyWords");
		if (StringUtils.isNotEmpty(keyWords)) ca.add(Restrictions.like("keyWords", "%"+keyWords+"%"));
		String clothCityName  = DataFormat.getStringKey(inParam, "clothCityName");
		if(StringUtils.isNotEmpty(clothCityName)) ca.add(Restrictions.eq("clothCityName", "%"+clothCityName+"%"));
		ca.addOrder(Order.desc("id"));
		return ca;
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public String delCmArea(String[] id){
		Session sessionUpdate = SysContexts.getEntityManager(false);
		int i = 0;
		try {
			i = sessionUpdate.createSQLQuery("delete from cm_area where id in :ids ").setParameterList("ids", id).executeUpdate();
		} catch (Exception e) {
			return "N";
		}
		if (i > 0) {
			return "Y";
		}
		return "N";
	}
	/**
	 * 
	 * @param province
	 * @param city
	 * @param district
	 * @param address
	 * @return
	 */
	public Criteria getCmAreaByCityOrAddress(long province,long city,long district){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmArea.class);
		if (province > 0) {
			ca.add(Restrictions.eq("province", province));
		}
		if (city > 0) {
			ca.add(Restrictions.eq("city", city));
		}
		if (district > 0) {
			ca.add(Restrictions.eq("district", district));
		}
		return ca;
	}
}
