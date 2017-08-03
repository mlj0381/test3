package com.wo56.common.sms.utils;

import java.util.ArrayList;
import java.util.List;

import com.framework.core.cache.CacheFactory;
import com.wo56.common.sms.cache.SysTenantControllerCache;
import com.wo56.common.sms.vo.SysTenantController;

public class SysTenantControllerCacheUtil {

	
	/**
	 * 获取数据列表 key--> sysTenantController
	 * @param 
	 * @return List-》
	 */
	public static List<SysTenantController> getSysTenantControllerList(){
			List<SysTenantController> sysTenantControllerList = (List<SysTenantController>)CacheFactory.get(SysTenantControllerCache.class, "SYSTENANTCONTROLLER");
			return sysTenantControllerList;
	}
	
	/**
	 * 根据tenantId筛选
	 * @param tenantId
	 * @return
	 */
	public static List<SysTenantController> getTenantIdList(long tenantId){
		List<SysTenantController> allList = getSysTenantControllerList();
		List<SysTenantController> returnList = new ArrayList<SysTenantController>();
		if(allList != null){
			for(SysTenantController sysTenantController : allList){
				if(sysTenantController.getState()){
					if(sysTenantController.getTenantId() != null && sysTenantController.getTenantId() == tenantId){
						returnList.add(sysTenantController);
					}
				}
			}
		}
		return returnList;
	}
	
	
}
