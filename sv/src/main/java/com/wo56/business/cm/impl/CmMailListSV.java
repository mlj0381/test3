package com.wo56.business.cm.impl;

import org.hibernate.Query;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.wo56.business.cm.vo.CmMailList;

public class CmMailListSV {

	
	public String doSaveMailList(CmMailList cnMailList)  {
		Session session = SysContexts.getEntityManager(false);
		session.saveOrUpdate(cnMailList);
		return "Y";
	}
	public CmMailList getCnMailList(long id){
		Session session = SysContexts.getEntityManager(true);
		return (CmMailList)session.get(CmMailList.class, id);
	}
	public void deleteMailListById(long id,long userId)throws Exception{
		if(id<0||id==0){
			return;
		}
		StringBuffer sf = new StringBuffer("delete from CmMailList where id=:id and userId=:userId");
		Query query = SysContexts.getEntityManager().createQuery(sf.toString());
		query.setParameter("id", id);
		query.setParameter("userId", userId);
		query.executeUpdate();
	}

}
