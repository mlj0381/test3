package com.wo56.common.sms.cache;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.AbstractCache;
import com.framework.core.cache.CacheFactory;
import com.wo56.common.sms.vo.SysTenantExtend;

public class SysTenantExtendCache  extends AbstractCache{

	@Override
	public void loadDate() throws Exception {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria( SysTenantExtend.class);
		ca.add(Restrictions.eq("status", true));
		List<SysTenantExtend> dataList = ca.list();
		for(SysTenantExtend sysTenantExtend:dataList){
			if(CacheFactory.get(this.getClass().getName(), "SYSTENANTEXTEND")==null){
				List<SysTenantExtend> valueList = new ArrayList<SysTenantExtend>();
				valueList.add(sysTenantExtend);
				CacheFactory.put(this.getClass().getName(), "SYSTENANTEXTEND", valueList);
			}else{
				List<SysTenantExtend> valueList = (List)CacheFactory.get(this.getClass().getName(), "SYSTENANTEXTEND");
				valueList.add(sysTenantExtend);
			}
		} 
	}
}
