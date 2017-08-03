package com.wo56.business.cm.intf;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.wo56.business.cm.impl.CmUserOrgRelSV;
import com.wo56.business.cm.vo.CmUserOrgRel;
import com.wo56.business.org.vo.Organization;

public class CmUserOrgRelTF {

	private CmUserOrgRelSV userOrgRelSV ;

	
	public void setUserOrgRelSV(CmUserOrgRelSV userOrgRelSV) {
		this.userOrgRelSV = userOrgRelSV;
	}


	public void doSave(CmUserOrgRel orgRel)throws Exception{
		userOrgRelSV.doSave(orgRel);
	}
	
	public CmUserOrgRel getOrgRel(long userId,String tenantCode)throws Exception{
		return userOrgRelSV.getOrgRel(userId, tenantCode);
	}
	/**
	 * lyh
	 * 查询用户加入了哪些公司
	 * 
	 * @return
	 */
	public List<Organization> queryOrgByUserId(long userId){
		return userOrgRelSV.queryOrgByUserId(userId);
	}
	
	public CmUserOrgRel querySfOrgRel(long userId,long tenantCode)throws Exception{
		Session session = SysContexts.getEntityManager();
		Criteria ca =session.createCriteria(CmUserOrgRel.class);
		ca.add(Restrictions.eq("userId", userId));
		ca.add(Restrictions.eq("tenantId",tenantCode));
		List<CmUserOrgRel> list = ca.list();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	} 
}
