package com.wo56.common.sms.cache;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.cache.AbstractCache;
import com.framework.core.cache.CacheFactory;
import com.wo56.common.sms.vo.SysTenantController;

public class SysTenantControllerCache  extends AbstractCache{

	@Override
	public void loadDate() throws Exception {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria( SysTenantController.class);
		List<SysTenantController> dataList = ca.list();
		for(SysTenantController sysTenantController:dataList){
			if(CacheFactory.get(this.getClass().getName(), "SYSTENANTCONTROLLER")==null){
				List<SysTenantController> valueList = new ArrayList<SysTenantController>();
				valueList.add(sysTenantController);
				CacheFactory.put(this.getClass().getName(), "SYSTENANTCONTROLLER", valueList);
			}else{
				List<SysTenantController> valueList = (List)CacheFactory.get(this.getClass().getName(), "SYSTENANTCONTROLLER");
				valueList.add(sysTenantController);
			}
		} 
	}
}
