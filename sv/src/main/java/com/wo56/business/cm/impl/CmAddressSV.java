package com.wo56.business.cm.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.wo56.business.cm.vo.CmAddress;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;

public class CmAddressSV {
	public String doSaveAddress(CmAddress cmAddress){
		Session session = SysContexts.getEntityManager();
		session.saveOrUpdate(cmAddress);
		return "Y";
	}
	
	public CmAddress getCmAddress(long id){
		Session session = SysContexts.getEntityManager(true);
		return (CmAddress) session.get(CmAddress.class, id);
	}
	
	public void deleteAddressById(long addressId,long userId)throws Exception{
		if(addressId<0||addressId==0){
			return;
		}
		StringBuffer sf = new StringBuffer("delete from CmAddress where id=:addressId and userId=:userId");
		Query query = SysContexts.getEntityManager().createQuery(sf.toString());
		query.setParameter("addressId", addressId);
		query.setParameter("userId", userId);
		query.executeUpdate();
	}
	/**
	 * 校验是否存在默认地址
	 * @param userId
	 * @param type
	 * @return
	 */
	public CmAddress checkDefaultAddress(long userId,int type){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmAddress.class);
		ca.add(Restrictions.eq("userId", userId));
		ca.add(Restrictions.eq("merchantAddressType", type));
		ca.add(Restrictions.eq("adderssDefault", SysStaticDataEnum.ADDRESS_TYPE_YQ.ADDRESS_TYPE_IS_DEFAULT));
		List<CmAddress> list = ca.list();
		CmAddress address = null;
		if (list!= null&&list.size()>0) {
			 address = list.get(0);
		}
		return address;
	}
}
