package com.wo56.business.cm.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.wo56.business.cm.vo.CmCustomer;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class CmCustomerYQSV {
	/**
	 * 开单匹配发货人或者收货人
	 * @param inParam
	 * @return
	 */
	public Criteria likeCmCustomerByName(Map<String,String> inParam){
		BaseUser user = SysContexts.getCurrentOperator();
		String name = DataFormat.getStringKey(inParam, "name");
		if(StringUtils.isEmpty(name)){
			return null;
		}
		int type = DataFormat.getIntKey(inParam,"type");
		if(type < 0 || (type != SysStaticDataEnumYunQi.CUSTOMER_TYPE.CONSIGNEE && type != SysStaticDataEnumYunQi.CUSTOMER_TYPE.CONSIGNOR)){
			throw new BusinessException("传的客户类型有误！");
		}
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmCustomer.class);
		ca.add(Restrictions.like("name", "%"+name+"%"));
		ca.add(Restrictions.eq("type", type));
		ca.add(Restrictions.eq("orgId", user.getOrgId()));
		ca.add(Restrictions.eq("state", SysStaticDataEnumYunQi.STS.VALID));
		return ca;
	}
	/**
	 * id查客户
	 * @param id
	 * @return
	 */
	public CmCustomer getCmCustomer(long id){
		Session session = SysContexts.getEntityManager(true);
		return (CmCustomer) session.get(CmCustomer.class, id);
	}
	
	public void doSaveOrUpdate(CmCustomer cmCustomer){
		Session session = SysContexts.getEntityManager(true);
		session.saveOrUpdate(cmCustomer);
	}
	
	public List<CmCustomer> getCmCustomerList(String name,String bill){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmCustomer.class);
		//ca.add(Restrictions.eq("type", type));
		ca.add(Restrictions.eq("name", name));
		ca.add(Restrictions.eq("bill", bill));
		ca.add(Restrictions.eq("state", SysStaticDataEnumYunQi.STS.VALID));
		return ca.list();
	}
	/**
	 * 列表查询
	 * @param inParam
	 * @return
	 */
	public Criteria pageCmCustomer(Map<String,Object> inParam){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmCustomer.class);
		BaseUser user = SysContexts.getCurrentOperator();
		Organization org = OraganizationCacheDataUtil.getOrganization(user.getOrgId());
		
		int type = DataFormat.getIntKey(inParam, "type");
		ca.add(Restrictions.eq("type", type));
		ca.add(Restrictions.eq("state", SysStaticDataEnumYunQi.STS.VALID));
		ca.add(Restrictions.eq("zxTenantId", user.getTenantId()));
		String bill = DataFormat.getStringKey(inParam, "bill");
		if(StringUtils.isNotEmpty(bill)){
			ca.add(Restrictions.like("bill", "%"+bill+"%"));
		}
		String name = DataFormat.getStringKey(inParam, "name");
		if(StringUtils.isNotEmpty(name)){
			ca.add(Restrictions.like("name", "%"+name+"%"));
		}
		long orgId = DataFormat.getLongKey(inParam,"orgId");
		if(org.getParentOrgId() == -1){
			if(orgId > 0){
				ca.add(Restrictions.eq("orgId", orgId));
			}
		}else{
			ca.add(Restrictions.eq("orgId", user.getOrgId()));
		}
		return ca;
	}
}
