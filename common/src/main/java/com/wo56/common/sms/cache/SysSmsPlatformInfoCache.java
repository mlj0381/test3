package com.wo56.common.sms.cache;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.cache.AbstractCache;
import com.framework.core.cache.CacheFactory;
import com.wo56.common.sms.vo.SysSmsPlatformInfo;

public class SysSmsPlatformInfoCache  extends AbstractCache{

	@Override
	public void loadDate() throws Exception {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria( SysSmsPlatformInfo.class);
		List<SysSmsPlatformInfo> dataList = ca.list();
		for(SysSmsPlatformInfo sysSmsPlatformInfoInfo:dataList){
			if(CacheFactory.get(this.getClass().getName(), "SysSmsPlatformInfo")==null){
				List<SysSmsPlatformInfo> valueList = new ArrayList<SysSmsPlatformInfo>();
				valueList.add(sysSmsPlatformInfoInfo);
				CacheFactory.put(this.getClass().getName(), "SysSmsPlatformInfo", valueList);
			}else{
				List<SysSmsPlatformInfo> valueList = (List)CacheFactory.get(this.getClass().getName(), "SysSmsPlatformInfo");
				valueList.add(sysSmsPlatformInfoInfo);
			}
		} 
	}
}
