package com.wo56.common.sms.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.framework.core.cache.CacheFactory;
import com.wo56.common.sms.cache.SysTenantExtendCache;
import com.wo56.common.sms.vo.SysTenantExtend;

public class SysTenantExtendCacheUtil {

	
	public static String LOGO_URL="LOGO_URL";
	
	/**
	 * 获取数据列表 key--> SYSTENANTEXTEND
	 * @param 
	 * @return List-》Organization
	 */
	public static List<SysTenantExtend> getTenantExtendList(){
			List<SysTenantExtend> sysTenantExtendList = (List<SysTenantExtend>)CacheFactory.get(SysTenantExtendCache.class, "SYSTENANTEXTEND");
			return sysTenantExtendList;
	}
	
	
	/**
	 * 根据tenantId筛选
	 * @param tenantId
	 * @return
	 */
	public static List<SysTenantExtend> getTenantIdList(long tenantId){
		List<SysTenantExtend> allList = getTenantExtendList();
		List<SysTenantExtend> returnList = new ArrayList<SysTenantExtend>();
		if(allList != null){
			for(SysTenantExtend sysTenantExtend : allList){
				if(sysTenantExtend.getTenantId() != null && sysTenantExtend.getTenantId() == tenantId){
					returnList.add(sysTenantExtend);
				}
			}
		}
		return returnList;
	}
	

	/**
	 * 根据tenantId和EKey取值
	 * @param tenantId
	 * @param EKey
	 * @return
	 */
	public static List<SysTenantExtend> getTenantIdAndEKey(long tenantId,String EKey){
		List<SysTenantExtend> allList = getTenantIdList(tenantId);
		List<SysTenantExtend> returnList = new ArrayList<SysTenantExtend>();
		for(SysTenantExtend sysTenantExtend : allList){
			if(StringUtils.isNotBlank(sysTenantExtend.getEKey()) && StringUtils.isNotBlank(EKey) && sysTenantExtend.getEKey().equals(EKey)){
				returnList.add(sysTenantExtend);
			}
		}
		return returnList;
	}
	
	
	/**
	 * 如果查询出有且只有一组数据就返回
	 * @param tenantId
	 * @param EKey
	 * @return
	 */
	public static String getValue(long tenantId,String EKey){
		List<SysTenantExtend> list = getTenantIdAndEKey(tenantId,EKey);
		if(list.size()>0){
			return list.get(0).getEValue();
		}
		return null;
	}
}
