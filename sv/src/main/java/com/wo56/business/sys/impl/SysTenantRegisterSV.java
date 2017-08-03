package com.wo56.business.sys.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.util.DataFormat;
import com.wo56.business.ord.vo.OrdOrdersInfo;
import com.wo56.business.sys.vo.SysTenantRegister;

public class SysTenantRegisterSV {
	/**
	 * 保存与修改
	 * @param register
	 */
	public void doSave(SysTenantRegister register){
		Session session = SysContexts.getEntityManager();
		register.setBoardTime(new Date());
		session.save(register);
	}
	/**
	 * 列表查询
	 * @param inParam
	 * @return
	 */
	public Criteria querySysTenantRegister(Map<String,Object> inParam){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(SysTenantRegister.class);
		String logisticsName = DataFormat.getStringKey(inParam, "logisticsName");
		if (StringUtils.isNotEmpty(logisticsName)) {
			ca.add(Restrictions.like("logisticsName", "%"+logisticsName+"%"));
		}
		String linkName = DataFormat.getStringKey(inParam, "linkName");
		if (StringUtils.isNotEmpty(linkName)) {
			ca.add(Restrictions.like("linkName", "%"+linkName+"%"));
		}
		String telephone = DataFormat.getStringKey(inParam, "telephone");
		if (StringUtils.isNotEmpty(telephone)) {
			ca.add(Restrictions.like("telephone", "%"+telephone+"%"));
		}
		ca.addOrder(Order.desc("id"));
		return ca;
	}
		
	/**
	 * 增加物流登记信息
	 * @param inParam
	 * @return
	 */
	public void addSysTenantRegister(Map<String,Object> inParam){
		Session session = SysContexts.getEntityManager(true);
		SysTenantRegister register= new SysTenantRegister();
		long id = DataFormat.getLongKey(inParam, "id");
		String logisticsName = DataFormat.getStringKey(inParam, "logisticsName");//物流公司名称
		String linkName = DataFormat.getStringKey(inParam, "linkName");//联系人
		String telephone = DataFormat.getStringKey(inParam, "telephone");//联系电话
		String remark = DataFormat.getStringKey(inParam, "remark");//备注
		String address = DataFormat.getStringKey(inParam, "address");//公司地址
		register.setAddress(address);
		register.setLinkName(linkName);
		register.setRemark(remark);
		register.setTelephone(telephone);
		register.setLogisticsName(logisticsName);
		register.setBoardName(SysContexts.getCurrentOperator().getUserName());
		register.setBoardTime(new Date());
		session.save(register);
		
	}
	
	/**
	 * 更新物流登记信息
	 * @param inParam
	 * @return
	 */
	public void updateSysTenantRegister(Map<String,Object> inParam){
		Session session = SysContexts.getEntityManager(true);
		//SysTenantRegister register= new SysTenantRegister();
		long id = DataFormat.getLongKey(inParam, "id");
		String logisticsName = DataFormat.getStringKey(inParam, "logisticsName");//物流公司名称
		String linkName = DataFormat.getStringKey(inParam, "linkName");//联系人
		String telephone = DataFormat.getStringKey(inParam, "telephone");//联系电话
		String remark = DataFormat.getStringKey(inParam, "remark");//备注
		String address = DataFormat.getStringKey(inParam, "address");//公司地址
//		if(id>0){
//			register.setId(id);	
//		}	
		SysTenantRegister register = (SysTenantRegister) session.get(SysTenantRegister.class, id);
		register.setAddress(address);
		register.setLinkName(linkName);
		register.setRemark(remark);
		register.setTelephone(telephone);
		register.setLogisticsName(logisticsName);	
		session.update(register);
		
	}
	
	
	/**
	 * 删除物流登记信息
	 * @param inParam
	 * @return
	 */
	public void delSysTenantRegister(long id){
		Session session = SysContexts.getEntityManager(true);
		String sql = "delete FROM sys_tenant_register  where id = :id";
		SQLQuery query  =  session.createSQLQuery(sql);
		query.setParameter("id", id);
		query.executeUpdate();
	}
	
	/**
	 * 根据id查询物流登记信息
	 * @param inParam
	 * @return
	 */
	public Criteria queryById(long id){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(SysTenantRegister.class);
		ca.add(Restrictions.eq("id", id));
		return ca;
	}
	
	
	
}
