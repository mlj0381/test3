package com.wo56.business.sys.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.wo56.business.cm.vo.CmUserOrgRel;
import com.wo56.business.sys.vo.SysRoleOperRel;
import com.wo56.business.sys.vo.SysTenantDef;
import com.wo56.business.sys.vo.out.UserRoleOut;
import com.wo56.common.consts.EnumConsts;


public class PortalBusiSV {
	/**
	 * 根据用户id查询改用户的角色
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<UserRoleOut> getOperatorOwnerRole(Long userId) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(SysRoleOperRel.class);
		ca.add(Restrictions.eq("operatorId", userId));
		
		
		List<UserRoleOut> retList=new ArrayList<UserRoleOut>();
		
		List<SysRoleOperRel> operRels=ca.list();
		if(operRels!=null){
			for(SysRoleOperRel operRel:operRels){
				UserRoleOut dest=new UserRoleOut();
				dest.setRoleId(operRel.getRoleId());
				retList.add(dest);
			}
		}
		
		return retList;
	}
	/**
	 * lyh
	 * 根据公司编码获取
	 * 
	 * 
	 * @return
	 */
	public SysTenantDef getSysTenantDefByCode(String code,String tenantType){
		Session session = SysContexts.getEntityManager();
		StringBuffer hql=new StringBuffer();
		hql.append("select s  from SysTenantDef s ,Organization o where o.tenantId =s.tenantId  and o.parentOrgId=:parentOrgId");
		hql.append(" and s.tenantCode=:tenantCode and o.tenantType=:tenantType and s.status=1 and o.state=1");
		
		Query query = session.createQuery(hql.toString());
		query.setParameter("parentOrgId", EnumConsts.ROOT_ORG_ID);
		query.setParameter("tenantCode", code);
		query.setParameter("tenantType", tenantType);
		
		List list= query.list();
		if(list!=null && list.size()==1){
			return (SysTenantDef)list.get(0);
		}
		
		return null;
	}
	/**
	 * 根据用户ID，租户ID 查询用户是否归属某个租户
	 * 
	 * @param userId
	 * @param tenantId
	 * @return
	 */
	public CmUserOrgRel getUserOrgRel(Long userId,Long tenantId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmUserOrgRel.class);
		ca.add(Restrictions.eq("userId", userId));
		ca.add(Restrictions.eq("tenantId", tenantId));
		List<CmUserOrgRel> sysUserOrgRels=  ca.list();
		if(sysUserOrgRels!=null && sysUserOrgRels.size()==1){
			return sysUserOrgRels.get(0);
		}
		return null;
	}
	
}
