package com.wo56.business.sys.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.util.IPage;
import com.framework.core.util.PageUtil;


public class SysShortMessageSV {

	
	/**
	 * 查询历史表中的数据
	 * @param templateId 模板ID userId 租户Id yearAndMonth 年月
	 * @return IPage 分页对象
	 */
	public IPage tenantIdAndTemplaId(Long templateId,Long tenantId,String yearAndMonth) throws Exception{
		Session session = SysContexts.getEntityManager();
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT t_send.SMS_CONTENT,count(t_send.TEMPLATE_ID),t_template.TEMPLATE_NAME,t_send.TEMPLATE_ID ");
		buffer.append(" from sys_sms_send_his_"+yearAndMonth+" t_send  ");
		buffer.append(" LEFT JOIN sys_sms_template t_template ON t_template.TEMPLATE_ID = t_send.TEMPLATE_ID ");
		buffer.append(" WHERE t_send.TENANT_ID = :tenantId " );
		if(templateId>1){
			buffer.append(" AND t_send.TEMPLATE_ID=:templateId ");
		}
		/* 不需要子查询
		 * else{
			buffer.append(" AND t_send.TEMPLATE_ID in (SELECT t_sst.TEMPLATE_ID FROM sys_sms_template t_sst where t_sst.TENANT_ID=:tenantId) ");
		}*/
		buffer.append(" GROUP BY t_send.TEMPLATE_ID ");
		Query query = session.createSQLQuery(buffer.toString());
		if(templateId>1){
			query.setParameter("templateId",templateId);
		}
		query.setParameter("tenantId",tenantId);
		IPage iPage = PageUtil.gridPage(query);
		return iPage;
	}
	
	/**
	 * 查询所以模板
	 * @param map
	 * @return list
	 */
	public List getTempLate(Long tenantId){
		Session session = SysContexts.getEntityManager();
		String sql ="SELECT t_template.TEMPLATE_NAME,t_template.TEMPLATE_ID FROM sys_sms_template t_template where t_template.TENANT_ID=:tenantId";
		Query query = session.createSQLQuery(sql.toString());
		query.setParameter("tenantId",tenantId);
		List list = query.list();
		return list;
	}
	
	/**
	 * 第二次跳转页面的方法
	 * @param map
	 * @return 分页对象
	 */
	public IPage allShortMessage(Long templateId,Long tenantId,String yearAndMonth){
		Session session = SysContexts.getEntityManager();
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT t_send.SMS_CONTENT,t_send.BILL_ID,t_template.TEMPLATE_NAME ");
		buffer.append(" from sys_sms_send_his_"+yearAndMonth+" t_send  ");
		buffer.append(" LEFT JOIN sys_sms_template t_template ON t_template.TEMPLATE_ID = t_send.TEMPLATE_ID ");
		buffer.append(" WHERE t_send.TENANT_ID = :tenantId");
		if(templateId>1){
			buffer.append(" AND t_send.TEMPLATE_ID=:templateId ");
		}
		Query query = session.createSQLQuery(buffer.toString());
		if(templateId>1){
			query.setParameter("templateId",templateId);
		}
		query.setParameter("tenantId",tenantId);
		IPage iPage = PageUtil.gridPage(query);
		return iPage;
	}
}
