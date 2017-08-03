package com.wo56.business.ac.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.wo56.business.ac.vo.AcWalletRel;
import com.wo56.business.sys.vo.SysTenantDef;

public class AcWalletRelSV {
	 /**
	  * 我的钱包获取公司
	  * 接口编号：601011
	  * @return
	  */
	public Query getAcWalletRel(){
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery("select * from ac_wallet_rel a left join sys_tenant_def s on a.tenant_id = s.tenant_id where a.user_id = :id order by a.id desc ")
				.addEntity("a",AcWalletRel.class).addEntity("s",SysTenantDef.class);
		query.setParameter("id", SysContexts.getCurrentOperator().getUserId());
		return query;
	}
	
	public String doSave(AcWalletRel acWalletRel){
		Session session = SysContexts.getEntityManager(false);
		session.save(acWalletRel);
		return "Y";
	}
	
	public List<AcWalletRel> checkAcWalletRel(long userId,long tenantId){
		Session session = SysContexts.getEntityManager(true);
		List<AcWalletRel> list = session.createCriteria(AcWalletRel.class).add(Restrictions.eq("userId", userId)).add(Restrictions.eq("tenantId", tenantId)).list();
		return list;
	}
}
