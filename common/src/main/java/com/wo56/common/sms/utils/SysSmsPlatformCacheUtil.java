package com.wo56.common.sms.utils;

import java.util.List;

import com.framework.core.cache.CacheFactory;
import com.wo56.common.sms.cache.SysSmsPlatformCache;
import com.wo56.common.sms.vo.SysSmsPlatform;

public class SysSmsPlatformCacheUtil {

	
	/**
	 * 获取数据列表 key--> SysSmsPlatform
	 * @param 
	 * @return List-》
	 */
	public static List<SysSmsPlatform> getSysSmsPlatformList(){
			List<SysSmsPlatform> sysSmsPlatformList = (List<SysSmsPlatform>)CacheFactory.get(SysSmsPlatformCache.class, "SYSSMSPLATFORM");
			return sysSmsPlatformList;
	}
	
	/**
	 * 根据tenantId筛选
	 * @param tenantId
	 * @return
	 */
	public static SysSmsPlatform getPlatForm(long platformId){
		List<SysSmsPlatform> allList = getSysSmsPlatformList();
		if(allList != null){
			for(SysSmsPlatform sysSmsPlatform : allList){
				if(sysSmsPlatform.getState()){
					if(sysSmsPlatform.getId() == platformId){
						return sysSmsPlatform;
					}
				}
			}
		}
		return null;
	}
	
	
}
