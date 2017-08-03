package com.wo56.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.framework.core.exception.BusinessException;
import com.wo56.business.cm.vo.CmCustomer;

public class ObjectCompareUtils {

	/**
	  * 判断两个新旧对象指定字段是否发生改变
	 * @param oldObj
	 * @param newObj
	 * @param file 需要比较的字段数组
	 * @return
	 */
	@SuppressWarnings("unused")
	public static boolean isModifyObj(Object oldObj,Object newObj,String[] file){
   	 try {
   		//oldObj.getClass();
			Field[] objOld = oldObj.getClass().getDeclaredFields();
			Field[] objNew = newObj.getClass().getDeclaredFields();
			for(int i=0;i<objOld.length;i++){
				Field  objFile=objOld[i];
				Field  newFile=objNew[i];
				String oldFieldName=objFile.getName();
				String newFieldName=newFile.getName();
				//判断字段类型   
				Type type = objFile.getGenericType();
				//获取字段名
				String oldfieldNames = Character.toUpperCase(oldFieldName.charAt(0))+oldFieldName.substring(1, oldFieldName.length());
		    	String newfieldNames = Character.toUpperCase(newFieldName.charAt(0))+newFieldName.substring(1, newFieldName.length());
		    	//获取私有字段的get方法，得到私有属性的值
		    	Method oldGetMethod = oldObj.getClass().getMethod("get"+oldfieldNames);
		    	Method newGetMethod = newObj.getClass().getMethod("get"+newfieldNames);
		    	//设置获取私有属性
		    	objFile.setAccessible(true);
		    	newFile.setAccessible(true);
		    	for (int j = 0; j < file.length; j++) {
					if(oldFieldName.equals(file[j])){
						 if (type.toString().equals("class java.lang.String")) {
					    	String objOldValue =(String)oldGetMethod.invoke(oldObj);
					    	String objNewValue =(String)newGetMethod.invoke(newObj);
					    	if(objOldValue!=null &&  objNewValue!=null  && !objOldValue.equals(objNewValue)){
					    		return false;
					    	}
					    }
					    if (type.toString().equals("class java.lang.Integer")) {
					    	String objOldValue =String.valueOf((Integer)oldGetMethod.invoke(oldObj));
					    	String objNewValue =String.valueOf((Integer)newGetMethod.invoke(newObj));
					    	if( objOldValue!=null && objNewValue!=null &&  !objOldValue.equals(objNewValue)){
					    		return false;
					    	}
					    }
					    if (type.toString().equals("int")) {
					    	Integer objOldValue =(Integer)oldGetMethod.invoke(oldObj);
					    	Integer objNewValue =(Integer)newGetMethod.invoke(newObj);
					    	if(objOldValue!=null && objNewValue!=null && objOldValue!=objNewValue){
					    		return false;
					    	}
					    }
					    if (type.toString().equals("long")) {
					    	String objOldValue =String.valueOf((Long)oldGetMethod.invoke(oldObj));
					    	String objNewValue =String.valueOf((Long)newGetMethod.invoke(newObj));
					    	if(objOldValue!=null && objNewValue!=null && !objOldValue.equals(objNewValue)){
					    		return false;
					    	}
					    }
					    if (type.toString().equals("class java.lang.Long")) {
					    	String objOldValue =String.valueOf((Long)oldGetMethod.invoke(oldObj));
					    	String objNewValue =String.valueOf((Long)newGetMethod.invoke(newObj));
					    	if(objOldValue!=null &&  objNewValue!=null  && !objOldValue.equals(objNewValue)){
					    		return false;
					    	}
					    }
					
					}
				}
			  
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
   	 	return true;
    }
	
	public static boolean isIn(String substring, String[] source) {
		if (source == null || source.length == 0) {
			return false;
		}
		for (int i = 0; i < source.length; i++) {
			String aSource = source[i];
			if (aSource.equals(substring)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 必较两个相同对象 是否有修改值
	 * @param oldObj
	 * @param newObj
	 * @param file 不比较传入的参数 属性名
	 * @return 有修改返回false 没有修改返回true
	 */
	public static boolean isModifyObjNotIn(Object oldObj,Object newObj,String[] file){
	   	 try {
	   		//oldObj.getClass();
				Field[] objOld = oldObj.getClass().getDeclaredFields();
				Field[] objNew = newObj.getClass().getDeclaredFields();
				for(int i=0;i<objOld.length;i++){
					Field  objFile=objOld[i];
					String oldFieldName=objFile.getName();
					//判断字段类型   
					Type type = objFile.getGenericType();
					//获取字段名
					String oldfieldNames = Character.toUpperCase(oldFieldName.charAt(0))+oldFieldName.substring(1, oldFieldName.length());
					//获取私有字段的get方法，得到私有属性的值
					Method oldGetMethod = oldObj.getClass().getMethod("get"+oldfieldNames);
					//设置获取私有属性
					objFile.setAccessible(true);
					for(int h=0;h< objNew.length;h++){
						if(!isIn(oldFieldName,file)){
								Field  newFile=objNew[i];
								String newFieldName=newFile.getName();
								String newfieldNames = Character.toUpperCase(newFieldName.charAt(0))+newFieldName.substring(1, newFieldName.length());
								Method newGetMethod = newObj.getClass().getMethod("get"+newfieldNames);
								newFile.setAccessible(true);

								 if (type.toString().equals("class java.lang.String")) {
							    	String objOldValue =(String)oldGetMethod.invoke(oldObj);
							    	String objNewValue =(String)newGetMethod.invoke(newObj);
							    	if(objOldValue!=null &&  objNewValue!=null  && !objOldValue.equals(objNewValue)){
							    		return false;
							    	}
							    }
							    if (type.toString().equals("class java.lang.Integer")) {
							    	String objOldValue =String.valueOf((Integer)oldGetMethod.invoke(oldObj));
							    	String objNewValue =String.valueOf((Integer)newGetMethod.invoke(newObj));
							    	if( objOldValue!=null && objNewValue!=null &&  !objOldValue.equals(objNewValue)){
							    		return false;
							    	}
							    }
							    if (type.toString().equals("int")) {
							    	Integer objOldValue =(Integer)oldGetMethod.invoke(oldObj);
							    	Integer objNewValue =(Integer)newGetMethod.invoke(newObj);
							    	if(objOldValue!=null && objNewValue!=null && objOldValue!=objNewValue){
							    		return false;
							    	}
							    }
							    if (type.toString().equals("long")) {
							    	String objOldValue =String.valueOf((Long)oldGetMethod.invoke(oldObj));
							    	String objNewValue =String.valueOf((Long)newGetMethod.invoke(newObj));
							    	if(objOldValue!=null && objNewValue!=null && !objOldValue.equals(objNewValue)){
							    		return false;
							    	}
							    }
							    if (type.toString().equals("class java.lang.Long")) {
							    	String objOldValue =String.valueOf((Long)oldGetMethod.invoke(oldObj));
							    	String objNewValue =String.valueOf((Long)newGetMethod.invoke(newObj));
							    	if(objOldValue!=null &&  objNewValue!=null  && !objOldValue.equals(objNewValue)){
							    		return false;
							    	}
							    }
							    if (type.toString().equals("double")) {
							    	String objOldValue =String.valueOf((Double)oldGetMethod.invoke(oldObj));
							    	String objNewValue =String.valueOf((Double)newGetMethod.invoke(newObj));
							    	if(objOldValue!=null && objNewValue!=null && !objOldValue.equals(objNewValue)){
							    		return false;
							    	}
							    }
							    if (type.toString().equals("class java.lang.Double")) {
							    	String objOldValue =String.valueOf((Double)oldGetMethod.invoke(oldObj));
							    	String objNewValue =String.valueOf((Double)newGetMethod.invoke(newObj));
							    	if(objOldValue!=null &&  objNewValue!=null  && !objOldValue.equals(objNewValue)){
							    		return false;
							    	}
							    }
							    if (type.toString().equals("class java.lang.Boolean")) {
							    	String objOldValue =String.valueOf((Boolean)oldGetMethod.invoke(oldObj));
							    	String objNewValue =String.valueOf((Boolean)newGetMethod.invoke(newObj));
							    	if(objOldValue!=null &&  objNewValue!=null  && !objOldValue.equals(objNewValue)){
							    		return false;
							    	}
							    }
							    if (type.toString().equals("boolean")) {
							    	String objOldValue =String.valueOf((Boolean)oldGetMethod.invoke(oldObj));
							    	String objNewValue =String.valueOf((Boolean)newGetMethod.invoke(newObj));
							    	if(objOldValue!=null &&  objNewValue!=null  && !objOldValue.equals(objNewValue)){
							    		return false;
							    	}
							    }
							    
							}
						}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
	   	 	return true;
	    }
	
	public static boolean isNotBlank(Object obj) throws Exception{
		Field[] fileds = obj.getClass().getDeclaredFields();
		for(int i=0;i<fileds.length;i++){
			Field  objFile=fileds[i];
			String fieldName=objFile.getName();
			//判断字段类型   
			Type type = objFile.getGenericType();
			//获取字段名
			String fieldNames = Character.toUpperCase(fieldName.charAt(0))+fieldName.substring(1, fieldName.length());
	    	//获取私有字段的get方法，得到私有属性的值
	    	Method getMethod = obj.getClass().getMethod("get"+fieldNames);
	    	//设置获取私有属性
	    	objFile.setAccessible(true);
	    	if (type.toString().equals("class java.lang.String")) {
		    	String objOldValue =(String)getMethod.invoke(obj);
		    	if(StringUtils.isBlank(objOldValue)){
		    		throw new BusinessException((fieldName+"不能为空"));
		    	}
		    }
		    if (type.toString().equals("class java.lang.Integer")) {
		    	Integer objOldValue = (Integer)getMethod.invoke(obj);
		    	if(null == objOldValue || objOldValue <0){
		    		throw new BusinessException(fieldName+"");
		    	}
		    }
		    if (type.toString().equals("int")) {
		    	Integer objOldValue =(Integer)getMethod.invoke(obj);
		    	if(null == objOldValue || objOldValue <0){
		    		throw new BusinessException((fieldName+"不能为空"));
		    	}
		    }
		    if (type.toString().equals("long")) {
		    	Long objOldValue = (Long)getMethod.invoke(obj);
		    	if(null == objOldValue || objOldValue <0){
		    		throw new BusinessException((fieldName+"不能为空"));
		    	}
		    }
		    if (type.toString().equals("class java.lang.Long")) {
		    	Long objOldValue =(Long)getMethod.invoke(obj);
		    	if(null == objOldValue || objOldValue <0){
		    		throw new BusinessException((fieldName+"不能为空"));
		    	}
		    }
		}
		return true;
	}

	
	
	/**
	 * 判断obj中的字段是否为空 
	 * @param obj
	 * @param names 需要判断obj的字段数组
	 * @return
	 * @throws Exception
	 */
	public static boolean isNotBlankNames(Object obj,String names[]) throws Exception{
		for(int j=0;j<names.length;j++){
			 isNotBlankName(obj, names[j],null);
		}
		return true;
	}
	
	
	/**
	 * 判断obj中的字段是否为空 
	 * @param obj
	 * @param names 需要判断obj的字段数组
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public static boolean isNotBlankNames(Object obj,String names[],Map<String,String> map) throws Exception{
		for(int j=0;j<names.length;j++){
			 isNotBlankName(obj, names[j],map.get(names[j]));
		}
		return true;
	}
	
	
	/**
	 * 判断obj中name字段是否为空
	 * @param obj
	 * @param name 必须是obj中的字段名，否则跳过不判断
	 * @param notice 提示语
	 * @return 
	 * @throws Exception
	 */
	public static boolean isNotBlankName(Object obj,String name,String notice)throws Exception{
		if(StringUtils.isNotBlank(name)){
			Field[] fileds = obj.getClass().getDeclaredFields();
			for(int i=0;i<fileds.length;i++){
				if(name.equals(fileds[i].getName())){
					Field  objFile=fileds[i];
					String fieldName=objFile.getName();
					//判断字段类型   
					Type type = objFile.getGenericType();
					//获取字段名
					String fieldNames = Character.toUpperCase(fieldName.charAt(0))+fieldName.substring(1, fieldName.length());
			    	//获取私有字段的get方法，得到私有属性的值
					Method getMethod = null;
					try {
						getMethod = obj.getClass().getMethod("get"+fieldNames);
					} catch (Exception e) {
						fieldNames = Character.toLowerCase(fieldName.charAt(0))+fieldName.substring(1, fieldName.length());
						getMethod = obj.getClass().getMethod("get"+fieldNames);
					}
			    	//设置获取私有属性
			    	objFile.setAccessible(true);
			    	if (type.toString().equals("class java.lang.String")) {
				    	String objOldValue =(String)getMethod.invoke(obj);
				    	if(StringUtils.isBlank(objOldValue)){
				    		if(StringUtils.isBlank(notice)){
				    			throw new BusinessException((fieldName+"不能为空"));
				    		}else{
				    			throw new BusinessException((notice+"不能为空"));
				    		}
				    	}
				    }
				    if (type.toString().equals("class java.lang.Integer")) {
				    	Integer objOldValue = (Integer)getMethod.invoke(obj);
				    	if(null == objOldValue || objOldValue <=0){
				    		if(StringUtils.isBlank(notice)){
				    			throw new BusinessException((fieldName+"不能为空"));
				    		}else{
				    			throw new BusinessException((notice+"不能为空"));
				    		}
				    	}
				    }
				    if (type.toString().equals("int")) {
				    	Integer objOldValue =(Integer)getMethod.invoke(obj);
				    	if(null == objOldValue || objOldValue <=0){
				    		if(StringUtils.isBlank(notice)){
				    			throw new BusinessException((fieldName+"不能为空"));
				    		}else{
				    			throw new BusinessException((notice+"不能为空"));
				    		}
				    	}
				    }
				    if (type.toString().equals("long")) {
				    	Long objOldValue = (Long)getMethod.invoke(obj);
				    	if(null == objOldValue || objOldValue <=0){
				    		if(StringUtils.isBlank(notice)){
				    			throw new BusinessException((fieldName+"不能为空"));
				    		}else{
				    			throw new BusinessException((notice+"不能为空"));
				    		}
				    	}
				    }
				    if (type.toString().equals("class java.lang.Long")) {
				    	Long objOldValue =(Long)getMethod.invoke(obj);
				    	if(null == objOldValue || objOldValue <=0){
				    		if(StringUtils.isBlank(notice)){
				    			throw new BusinessException((fieldName+"不能为空"));
				    		}else{
				    			throw new BusinessException((notice+"不能为空"));
				    		}
				    	}
				    }
				}
			}
		}
		return true;
	}
	
	
	public static void main(String[] args) throws Exception {
		CmCustomer cmCustomer = new CmCustomer();
		cmCustomer.setId(2);
		cmCustomer.setBill("hh");
//		cmCustomer.setType(1);
		isNotBlankNames(cmCustomer,new String[]{"id","hh"});
		System.out.println("OK");
		
//		Field[] fileds = obj.getClass().getDeclaredFields();
//		for(int i=0;i<fileds.length;i++){
//			if(names[j].equals(fileds[i].getName())){
//				isNotBlankStr(obj, fileds[i].getName());
//			}
//		}
	}
}
 