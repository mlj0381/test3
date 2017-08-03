package com.wo56.common.cache;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.cache.AbstractCache;
import com.framework.core.cache.CacheFactory;
import com.wo56.business.org.vo.Organization;

public class OraganizationCache extends AbstractCache{

	@Override
	public void loadDate() throws Exception {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria( Organization.class);
		List<Organization> dataList = ca.list();
		for(Organization organization:dataList){
			if(CacheFactory.get(this.getClass().getName(), "ORGANIZATION")==null){
				List<Organization> valueList = new ArrayList<Organization>();
				valueList.add(organization);
				CacheFactory.put(this.getClass().getName(), "ORGANIZATION", valueList);
			}else{
				List<Organization> valueList = (List)CacheFactory.get(this.getClass().getName(), "ORGANIZATION");
				valueList.add(organization);
			}
		} 
	}

}
