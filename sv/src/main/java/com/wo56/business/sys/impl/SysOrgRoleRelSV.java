package com.wo56.business.sys.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.wo56.business.sys.vo.SysOrgRoleRel;

public class SysOrgRoleRelSV {
    
	
	public void doSave(SysOrgRoleRel sysOrgRoleRel){
		Session session = SysContexts.getEntityManager();
		session.saveOrUpdate(sysOrgRoleRel);
	}
	
	public List<SysOrgRoleRel> querySysOrgRoleRelList(Long tenantId){
		 Session session  = SysContexts.getEntityManager();
		 Criteria  roleCa = session.createCriteria(SysOrgRoleRel.class);
		 roleCa.add(Restrictions.eq("tenantId", tenantId));
		 return roleCa.list();
	}
	
	public SysOrgRoleRel querySysOrgRoleRelList(Long orgId,Long roleId){
		 Session session  = SysContexts.getEntityManager();
		 Criteria  roleCa = session.createCriteria(SysOrgRoleRel.class);
		 roleCa.add(Restrictions.eq("roleId", roleId));
		 roleCa.add(Restrictions.eq("orgId", orgId));
		 
		List<SysOrgRoleRel> list=  roleCa.list();
		if(list!=null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
	
}
