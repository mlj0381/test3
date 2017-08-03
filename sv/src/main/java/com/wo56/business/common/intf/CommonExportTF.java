package com.wo56.business.common.intf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.SysStaticDataUtil;

public class CommonExportTF {
	
	/**
	 * 导出Excel基础类TF
	 * 
	 * 
	 * @param sqlKeyValue  查询SQL
	 * @param sqlParams {     //查询参数
	 *               key1: value1, key1代表 :key1   value代表 :key1 的值
	 *               key2: value2
	 *          } 
	 *          key要以数据库配置的参数一致
	 *        
	 *           
	 */
	@SuppressWarnings({ "rawtypes",})
	public Map queryExportData(Map<String,Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		String sqlKey =  DataFormat.getStringKey(inParam, "sqlKey");  
		String sqlParams =  DataFormat.getStringKey(inParam, "sqlParams");
        
		SysStaticData staticData = SysStaticDataUtil.getSysStaticData("EXCEL_EXPORT_KEY", sqlKey);
		if(staticData == null || staticData.getFlowId() <= 0 || StringUtils.isEmpty(staticData.getCodeDesc())){
			throw new BusinessException("查找导出模板失败，请确认模版是否已配置！");
		}
		String sqlPar = "";
		
		if(sqlParams.contains("&quot")){
			sqlPar = sqlParams.replaceAll("&quot;", "\"");
		}else{
			sqlPar = sqlParams;
		}
		Map<String, Object> sqlParam = JsonHelper.parseJSON2Map(sqlPar);
		String sql = staticData.getCodeDesc(); //sql 格式 select * from  a where 1=1 ${key:value}$  key代表参数 value= AND B=:key
	    
		//替换参数模板(处理SQL模板)
		String sqlQuery = "";
		String [] sqls = sql.split("\\$");
		Map<String,String> sqlMap = new HashMap<String,String>();
		for(String s : sqls){
			if(s.contains("{") && s.contains("}")){
				Map<String, Object> m = JsonHelper.parseJSON2Map(s);
				for(Map.Entry<String, Object> e: m.entrySet()){
					sqlQuery = sqlQuery + "_"+e.getKey();
					sqlMap.put(e.getKey(), e.getValue()+"");
				}
				
			}else{
				sqlQuery = sqlQuery + s;
			}
		}

		//替换查询语句
		Map<String, Object> newSqlParam = new HashMap<String, Object>(); //通过的条件参数
		String newQuery = "";
		for(Map.Entry<String, String> entry: sqlMap.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			boolean isTo = true;
			if(sqlParam.containsKey(key)){
				Object paramValue = sqlParam.get(key);
				if(paramValue != null && StringUtils.isNotEmpty(paramValue+"")){
					newSqlParam.put(key, paramValue);
					if(StringUtils.isNotEmpty(newQuery)){
						newQuery = newQuery.replaceAll("_"+key, " "+value+" ");
					}else{
						newQuery = sqlQuery.replaceAll("_"+key, " "+value+" ");
					}
					isTo = false;
				}
			}   
		    if(isTo){
		    	if(StringUtils.isNotEmpty(newQuery)){
		    		newQuery = newQuery.replaceAll("_"+key, " ");
		    	}else{
		    		newQuery = sqlQuery.replaceAll("_"+key, " ");
		    	}
		    	
		    }
		}
		
		System.out.println("newQuery======"+newQuery);
		Query query = session.createSQLQuery(newQuery);
		for(Map.Entry<String, Object> entry: newSqlParam.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		List list = query.list();
		return JsonHelper.parseJSON2Map(JsonHelper.json(list));
		
	}
}
