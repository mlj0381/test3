package com.wo56.business.sys.intf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
//import com.wo56.business.sys.vo.SysBusiConfig;
//import com.wo56.business.sys.vo.SysBusiConfigItem;

public class SysBusiConfigTF implements ISysBusiConfigIntf{
	
	public Map<String, Object> queryBusiConfig(Map<String, Object> inParam) throws Exception {
//		Map retMap = new HashMap();
//		
//		Map<String,List<SysBusiConfigItem>> busiconfig=new HashMap<String, List<SysBusiConfigItem>>();
//		retMap.put("resultCode", "0");
//		BaseUser baseUser = SysContexts.getCurrentOperator();
//		long tenantId = baseUser.getTenantId();
//	
//		List<SysBusiConfig> config = getBusiConfig(tenantId);
//		
//		if(config!=null ){
//			List<Long> cIds=new ArrayList<Long>();
//			for(SysBusiConfig busiConfig:config){
//				cIds.add(busiConfig.getId());
//			}
//			List<SysBusiConfigItem> busiConfigItems= getBusiConfigItemByConfig(cIds);
//			if(busiConfigItems!=null && busiConfigItems.size()>0){
//				for(SysBusiConfig busiConfig:config){
//					Long id=busiConfig.getId();
//					String name=busiConfig.getName();
//					for(SysBusiConfigItem busiConfigItem:busiConfigItems){
//						if(id.longValue()==busiConfigItem.getConfigId()){
//							List<SysBusiConfigItem> configItems=  busiconfig.get(name);
//							if(configItems==null){
//								configItems=new ArrayList<SysBusiConfigItem>();
//							}
//							configItems.add(busiConfigItem);
//							busiconfig.put(name, configItems);
//						}
//					}
//				}
//				
//				retMap.put("resultCode", "1");
//			}
//		}
//		retMap.put("busiConfig", busiconfig);
		Map retMap = new HashMap();
		return retMap;
	}

	/**
	 * 根据主表的id获取
	 * @param cIds
	 * @return
	 * @throws Exception
	 */
//	private List<SysBusiConfigItem> getBusiConfigItemByConfig(List<Long> cIds) throws Exception {
//		if(cIds == null || cIds.size() == 0){
//			return new ArrayList();
//		}
//		Session session = SysContexts.getEntityManager(true);
//		Criteria criteria = session.createCriteria(SysBusiConfigItem.class);
//		criteria.add(Restrictions.in("configId", cIds));
//		criteria.add(Restrictions.eq("state", 1));
//		return criteria.list();
//	}
	
	/**
	 * 根据租户id获取
	 * @param tenantId
	 * @return
	 * @throws Exception
	 */
//	private List<SysBusiConfig> getBusiConfig(long tenantId) throws Exception {
//		Session session = SysContexts.getEntityManager(true);
//		Criteria criteria = session.createCriteria(SysBusiConfig.class);
//		criteria.add(Restrictions.eq("tenantId", tenantId));
//		criteria.add(Restrictions.eq("state", 1));
//		List<SysBusiConfig> list = criteria.list();
//		return list;
//	}
}
