package com.wo56.common.sms.utils;

import java.util.ArrayList;
import java.util.List;

import com.framework.core.cache.CacheFactory;
import com.wo56.common.sms.cache.SysSmsPlatformInfoCache;
import com.wo56.common.sms.vo.SysSmsPlatformInfo;

public class SysSmsPlatformInfoCacheUtil {

	
	/**
	 * 获取数据列表 key--> SysSmsPlatform
	 * @param 
	 * @return List-》
	 */
	public static List<SysSmsPlatformInfo> getSysSmsPlatformInfoList(){
			List<SysSmsPlatformInfo> sysSmsPlatformList = (List<SysSmsPlatformInfo>)CacheFactory.get(SysSmsPlatformInfoCache.class, "SysSmsPlatformInfo");
			return sysSmsPlatformList;
	}
	
	/**
	 * @param platformId
	 * @return List<SysSmsPlatformInfo> or  new ArrayList<SysSmsPlatformInfo>
	 */
	public static List<SysSmsPlatformInfo> getPlatformInfoList(long platformId){
		List<SysSmsPlatformInfo> allList = getSysSmsPlatformInfoList();
		List<SysSmsPlatformInfo> returnList = new ArrayList<SysSmsPlatformInfo>();
		if(allList != null){
			for(SysSmsPlatformInfo sysSmsPlatformInfo : allList){
				if(sysSmsPlatformInfo.getPlatformId() != null && sysSmsPlatformInfo.getPlatformId() == platformId){
					returnList.add(sysSmsPlatformInfo);
				}
			}
		}
		return returnList;
	}
	
}
