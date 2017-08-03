package com.wo56.business.sys.intf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.HtmlEncoder;
import com.framework.core.util.JsonHelper;
import com.wo56.business.sys.impl.SysTableHeadConfigSV;
import com.wo56.business.sys.vo.SysTableHeadConfig;


public class SysTableHeadConfigTF {
	//sv
	private SysTableHeadConfigSV sysTableHeadConfigSV;
	
	public SysTableHeadConfigSV getSysTableHeadConfigSV() {
		return sysTableHeadConfigSV;
	}

	public void setSysTableHeadConfigSV(SysTableHeadConfigSV sysTableHeadConfigSV) {
		this.sysTableHeadConfigSV = sysTableHeadConfigSV;
	}
	//sv
	
	/**
	 * 获取表头显示的列
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object>  getSysTableHeadConfigs(Map<String, Object> inParam) throws Exception {
		Map retMap = new HashMap();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		long tenantId = baseUser.getTenantId();
		long orgId = baseUser.getOrgId();
		long userId = baseUser.getUserId();
		List<SysTableHeadConfig> sysTableHeadConfigLst = sysTableHeadConfigSV.getSysTableHeadConfigs(tenantId, orgId, userId,null);
		//可以为空，查不到外围做判断
		for(SysTableHeadConfig config:sysTableHeadConfigLst){
			String tableName = config.getTableName();
			if(retMap.containsKey(tableName)){
				Map map = (Map)retMap.get(tableName);
				map.put(config.getHeadName()+"#"+config.getHeadCode(), config.getHeadIndex());
				retMap.put(tableName, map);
			} else {
				Map map = new HashMap();
				map.put(config.getHeadName()+"#"+config.getHeadCode(), config.getHeadIndex());
				retMap.put(tableName, map);
			}
		}
//		retMap.put("sysTableHeadConfigList", sysTableHeadConfigLst);
		return retMap;
	}
	
	/**
	 * 保存表头显示的列
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> saveSysTableHeadConfigs(Map<String, Object> inParam) throws Exception {
		String paramStr = DataFormat.getStringKey(inParam, "paramStr");
		paramStr = HtmlEncoder.decode(paramStr);
		Map<String, Object> map = JsonHelper.parseJSON2Map(paramStr);
		
		String tableName = DataFormat.getStringKey(map, "tableName");
		BaseUser baseUser = SysContexts.getCurrentOperator();
		long tenantId = baseUser.getTenantId();
		long orgId = baseUser.getOrgId();
		long userId = baseUser.getUserId();
		//查出原始的数据
		List<SysTableHeadConfig> sysTableHeadConfigLst = sysTableHeadConfigSV.getSysTableHeadConfigs(tenantId, orgId, userId, tableName);
		Map<String,SysTableHeadConfig> sysTableHeadConfigMap = new HashMap<String,SysTableHeadConfig>();
		if(sysTableHeadConfigLst != null && sysTableHeadConfigLst.size() > 0){
			for(SysTableHeadConfig sysTableHeadConfig:sysTableHeadConfigLst){
				sysTableHeadConfigMap.put(sysTableHeadConfig.getHeadName()+"#"+sysTableHeadConfig.getHeadCode(), sysTableHeadConfig);
			}
		}

		List<Map> tableList = (List<Map>)map.get("sysTableHeadConfigList");
		
		List<SysTableHeadConfig> newSysTableHeadConfigLst = new ArrayList<SysTableHeadConfig>();
		
		List<SysTableHeadConfig> delSysTableHeadConfigLst = new ArrayList<SysTableHeadConfig>();
		for(Map configMap : tableList){
			SysTableHeadConfig head = null;
			String key = DataFormat.getStringKey(configMap, "headName") + "#" + DataFormat.getStringKey(configMap, "headCode");
			if(sysTableHeadConfigMap.containsKey(key)){
				//原来有这个数据
				head = sysTableHeadConfigMap.get(key);
				int headIndex = DataFormat.getIntKey(configMap,"headIndex");
				head.setHeadIndex(headIndex);
				sysTableHeadConfigMap.remove(key);
				newSysTableHeadConfigLst.add(head);
			}else {
				head = new SysTableHeadConfig();
				BeanUtils.populate(head, configMap);
				
				head.setTableName(tableName);
				head.setOrgId(orgId);
				head.setTenantId(tenantId);
				head.setUserId(userId);
				head.setState(1);
				newSysTableHeadConfigLst.add(head);
			}
		}
		sysTableHeadConfigSV.saveSysTableHeadConfigs(newSysTableHeadConfigLst);
		
		if(!sysTableHeadConfigMap.isEmpty()){
			Set<String> keySet= sysTableHeadConfigMap.keySet();
			for(String key:keySet){
				SysTableHeadConfig delConfig = sysTableHeadConfigMap.get(key);
				sysTableHeadConfigSV.delSysTableHeadConfig(delConfig);
			}
		}
		
		return null;
	}

}
