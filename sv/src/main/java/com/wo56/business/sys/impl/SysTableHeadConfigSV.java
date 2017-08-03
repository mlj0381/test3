package com.wo56.business.sys.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.wo56.business.sys.vo.SysTableHeadConfig;

public class SysTableHeadConfigSV {
	/**
	 * 获取表头显示的列
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<SysTableHeadConfig> getSysTableHeadConfigs(long tenantId, long orgId, long userId, String tableName) throws Exception {
		Session session = SysContexts.getEntityManager(true);
		
		Criteria criteria = session.createCriteria(SysTableHeadConfig.class);
		criteria.add(Restrictions.eq("tenantId", tenantId));
		criteria.add(Restrictions.eq("orgId", orgId));
		criteria.add(Restrictions.eq("userId", userId));
		if(StringUtils.isNotBlank(tableName)){
			criteria.add(Restrictions.eq("tableName", tableName));
		}
		criteria.add(Restrictions.eq("state", 1));
		List<SysTableHeadConfig> list = criteria.list();
		return list;
	}
	
	/**
	 * 保存表头显示的列
	 * 
	 * @return
	 * @throws Exception
	 */
	public void saveSysTableHeadConfigs(List<SysTableHeadConfig> sysTableHeadConfigLst) throws Exception {
		Session session = SysContexts.getEntityManager();
		for(SysTableHeadConfig sysTableHeadConfig:sysTableHeadConfigLst){
			session.saveOrUpdate(sysTableHeadConfig);
		}
	}

	
	public void delSysTableHeadConfig(SysTableHeadConfig sysTableHeadConfig) throws Exception {
		Session session = SysContexts.getEntityManager();
		session.delete(sysTableHeadConfig);
	}
}
