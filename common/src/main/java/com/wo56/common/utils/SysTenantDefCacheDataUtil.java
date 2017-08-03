package com.wo56.common.utils;

import java.util.List;

import com.framework.core.cache.CacheFactory;
import com.wo56.business.sys.vo.SysTenantDef;
import com.wo56.common.cache.SysTenantDefCache;
import com.wo56.common.consts.PermissionConsts;



/**
 * @author zjy 
 * 
 * 网点费用配置缓存工具类
 * */
public class SysTenantDefCacheDataUtil{


	/**
	 * 获取数据列表 key--> SysTenantDef
	 * @param 
	 * @return List-》Organization
	 */
	public static List<SysTenantDef> getSysTenantDefDataList(){
			List<SysTenantDef> sysTenantDefCacheDataList = (List<SysTenantDef>)CacheFactory.get(SysTenantDefCache.class,PermissionConsts.GrantConstant.SYS_TENANT_DEF);
			return sysTenantDefCacheDataList;
	}
	
	
	/**
	 * 获取数据列表对象
	 * @param orgId 网点id
 	 * @return Organization
	 */
	public static String getTenantName(long tenantId){
		List<SysTenantDef> sysTenantDefCacheDataList = (List<SysTenantDef>)CacheFactory.get(SysTenantDefCache.class,PermissionConsts.GrantConstant.SYS_TENANT_DEF);
		if(sysTenantDefCacheDataList!=null&&sysTenantDefCacheDataList.size()>0){
			for(SysTenantDef sysTenantDef:sysTenantDefCacheDataList){
				if(sysTenantDef.getTenantId().longValue()== tenantId){
					return sysTenantDef.getName();
				}
			}
		}
		return null;
	}
	
	
	
	
	
	
	
}
