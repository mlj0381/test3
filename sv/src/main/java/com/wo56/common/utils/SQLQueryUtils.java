package com.wo56.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.SQLQuery;

import com.framework.core.util.CommonUtil;

public class SQLQueryUtils {

	/**
	 * Map组装Object对象属性工具
	 * @param map
	 * @param obj 传递进来的对象，会根据vo中属性名的大小写自动增加 "_"来对应数据库中的字段名
	 * 比如:传递进来的为 orgId map中的key保存的名字为: org_id
	 * @param alias 别名 为每个属性名前增加别名 
	 * 比如: alias为"a" 传递进来的为 orgId map中的key保存的名字为: a.org_id
	 * @return  map的key为object对象的属性名，value 为null
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void addNames(Map<String,Integer> map,Object obj,String alias) throws IllegalArgumentException, IllegalAccessException{
		Field[] fields = obj.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			addName(map, fields[i].getName(), alias);
		}
	}
	
	/**
	 * 组装数据，不包含字段
	 * @param map
	 * @param obj
	 * @param alias
	 * @param removeCon
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void addNames(Map<String,Integer> map,Object obj,String alias,String[] removeCon) throws IllegalArgumentException, IllegalAccessException{
			Field[] fields = obj.getClass().getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				if(!Arrays.asList(removeCon).contains(fields[i].getName())){
					addName(map, fields[i].getName(), alias);
				}
			}
	}
	
	/**
	 * Map中添加单个字段
	 * @param map
	 * @param name 这个名字必须要与数据库中的字段对应
	 * @param alias
	 * @return
	 */
	public static void addName(Map<String,Integer> map,String name,String alias){
		StringBuffer sb = new StringBuffer("");
		char[] strChar = name.toCharArray();
		for(char a:strChar){
			//判断是否为大写
			if(a>='A'&&a<='Z'){
				sb.append("_"+a);
			}else{
				sb.append(a);
			}
		}
		if(CommonUtil.isNotEmpty(alias)){
			map.put(alias+"."+sb.toString(), null);
		}else{
			map.put(sb.toString(), null);
		}
	}
	
	/**
	 * 组装sqlQuery语句，并且更改map中的key值，使key值与实体类中的属性名保持一致
	 * @param sql
	 * @param map
	 */
	public static void setSQLQuery(SQLQuery sql,Map<String,Integer> map){
		Iterator it = map.entrySet().iterator();
		int size = 0;
		Map<String,Integer> rtnMap = new HashMap<String, Integer>();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
	       	String key = (String) entry.getKey();
	       	sql.addScalar(key);
	       	if(key.contains("_")){
				key=key.replace("_", "");
			}
	       	rtnMap.put(key,size);
	       	size++;
		}
		map.putAll(rtnMap);
	}
	
	
	/**
	 * 反射 给传入进来的obj设值
	 * @param objs
	 * @param obj
	 * @param alias
	 * @param map
	 * @throws Exception
	 */
	public static void setValueToObj(Object[] objs, Object obj,String alias,Map<String,Integer> map) throws Exception{
		Class<?> objClass = obj.getClass();
		//TODO 需要获取父级的类
		Field[] fields = obj.getClass().getDeclaredFields();
		
		for(int i=0;i<fields.length;i++){
			String name = fields[i].getName();
			Type type = fields[i].getType();
			String setMethodName = "set"+toFirstLetterUpperCase(name);
			if(map.get(alias+"."+name) != null){
				Object value = objs[map.get(alias+"."+name)]; 
				try {
					if(value.getClass().getName().equals("java.math.BigInteger")){
						if(type.toString().equals("long")){
							objClass.getMethod(setMethodName, long.class).invoke(obj, Long.valueOf(value.toString()).longValue());  
						}else if(type.toString().equals("class java.lang.Long")){
							objClass.getMethod(setMethodName, Long.class).invoke(obj, Long.valueOf(value.toString()));  
						}
					}else if(value.getClass().getName().equals("java.lang.String")){
						objClass.getMethod(setMethodName, String.class).invoke(obj, value.toString());  
					}else if(value.getClass().getName().equals("java.lang.Integer")){
						objClass.getMethod(setMethodName, Integer.class).invoke(obj, Integer.valueOf(value.toString()));  
					}else if(value.getClass().getName().equals("java.sql.Timestamp")){
						try {
							objClass.getMethod(setMethodName, Date.class).invoke(obj, value);  
						} catch (Exception e) {
							objClass.getMethod(setMethodName, Timestamp.class).invoke(obj, value);  
						}
					}else{
						objClass.getMethod(setMethodName, value.getClass()).invoke(obj, value);  
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
	}
	
	public static String toFirstLetterUpperCase(String str) {  
	    if(str == null || str.length() < 2){  
	        return str;  
	    }  
	     String firstLetter = str.substring(0, 1).toUpperCase();  
	     return firstLetter + str.substring(1, str.length());  
	 }  
	
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
//		Map map = new HashMap();
//		addName(map,CmCustomer.class, "a");
//		addName(map,CmCustomer.class, "b");
//		Iterator it = map.entrySet().iterator();
//		while(it.hasNext()){
//			Map.Entry entry = (Map.Entry) it.next();
//	       	Object key = entry.getKey();
//	       	System.out.println("key: " + key+ "----");
//		}
		
		
	}
}
