package com.wo56.common.sms.cache;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.cache.AbstractCache;
import com.framework.core.cache.CacheFactory;
import com.wo56.common.sms.vo.SysSmsPlatform;

public class SysSmsPlatformCache  extends AbstractCache{

	@Override
	public void loadDate() throws Exception {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria( SysSmsPlatform.class);
		List<SysSmsPlatform> dataList = ca.list();
		for(SysSmsPlatform sysSmsPlatform:dataList){
			if(CacheFactory.get(this.getClass().getName(), "SYSSMSPLATFORM")==null){
				List<SysSmsPlatform> valueList = new ArrayList<SysSmsPlatform>();
				valueList.add(sysSmsPlatform);
				CacheFactory.put(this.getClass().getName(), "SYSSMSPLATFORM", valueList);
			}else{
				List<SysSmsPlatform> valueList = (List)CacheFactory.get(this.getClass().getName(), "SYSSMSPLATFORM");
				valueList.add(sysSmsPlatform);
			}
		} 
	}
}
