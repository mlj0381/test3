package com.wo56.business.sys.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.wo56.business.sys.vo.SysRoleOperRel;
import com.wo56.common.consts.SysStaticDataEnum;

public class SysRoleOperRelSV {
   
	/**
	 * 
	 * @param roleOperRel
	 */
	public void saveSysRoleOperRel(SysRoleOperRel roleOperRel){
		Session session = SysContexts.getEntityManager();
		session.saveOrUpdate(roleOperRel);
	}
	/**
	 * @param userId
	 * @return
	 */
	public SysRoleOperRel querySysRoleOperRel(Long userId){
		 Session session  = SysContexts.getEntityManager();
		 Criteria  roleCa = session.createCriteria(SysRoleOperRel.class);
		 roleCa.add(Restrictions.eq("operatorId", userId));
		 roleCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		 List<SysRoleOperRel> roleList = roleCa.list();
		 if(roleList!=null && roleList.size()==1){
			return roleList.get(0);
		 }
		 return null;
	}
	
}
