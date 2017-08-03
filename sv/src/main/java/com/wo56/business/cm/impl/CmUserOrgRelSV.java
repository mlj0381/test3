package com.wo56.business.cm.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.wo56.business.cm.vo.CmUserOrgRel;
import com.wo56.business.org.vo.Organization;

public class CmUserOrgRelSV {

	public void doSave(CmUserOrgRel orgRel)throws Exception{
		SysContexts.getEntityManager().saveOrUpdate(orgRel);
	}
	
	public CmUserOrgRel getOrgRel(long userId,String tenantCode)throws Exception{
		Criteria ca = SysContexts.getEntityManager().createCriteria(CmUserOrgRel.class);
		ca.add(Restrictions.eq("userId", userId));
		ca.add(Restrictions.eq("tenantCode",tenantCode));
		List<CmUserOrgRel> list = ca.list();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * lyh
	 * 查询用户加入了哪些公司
	 * 
	 * @return
	 */
	public List<Organization> queryOrgByUserId(long userId){
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer("select org from CmUserInfo userInfo, Organization org,CmUserOrgRel rel where userInfo.userId = rel.userId and rel.tenantId=org.tenantId and org.parentOrgId=-1 ");
		sb.append(" and userInfo.userId=:userId");
		
		Query ca = session.createQuery(sb.toString());
		ca.setParameter("userId", userId);
		List<Organization> organizations= ca.list();
		return organizations;
	}
	/**
	 * 云启
	 * @param userId
	 * @return
	 */
	public List<CmUserOrgRel> getUserRel(long userId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmUserOrgRel.class).add(Restrictions.eq("userId", userId));
		return ca.list();
	}
	
}
