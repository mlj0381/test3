package com.wo56.business.ac.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.wo56.business.ac.vo.AcPaymentMethod;

public class AcPaymentMethodSV {
	
	
	public AcPaymentMethod getAcPaymentMethod(long id){
		Session session = SysContexts.getEntityManager(true);
		return (AcPaymentMethod)session.get(AcPaymentMethod.class, id);
	}
	
	public String doSaveAcPaymentMethod(AcPaymentMethod acPaymentMethod){
		Session session = SysContexts.getEntityManager();
		session.saveOrUpdate(acPaymentMethod);
		return "Y";
	}
	
	public Criteria getAcPaymentMethodByUserId(long userId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(AcPaymentMethod.class);
		ca.add(Restrictions.eq("userId", userId));
		return ca;
	}
}
