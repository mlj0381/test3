package com.wo56.common.utils;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 通过反射机制执行方法
 * @author Administrator
 *
 */
public class ReflectUtil {

	private static final Log log = LogFactory.getLog(ReflectUtil.class);
	 /**
	  * 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。 
	 * @param classPath 类的全路径
	 * @param methodName 方法名
	 * @param params 参数（json格式）
     * @param classes   方法参数类型数
     * @return 方法对象 
     * @throws Exception 
     */  
	public static void useReflect(String classPath,String methodName,Object params,final Class classes){  
          try {  
              Class clazz=null;
              if(StringUtils.isNotEmpty(classPath)){
            	  clazz=Class.forName(classPath);
              }
              //利用获取到的类的Class对象新建一个实例（相当于Reflect new了个对象）  
              Object obj=clazz.newInstance();  
               //获取Reflect的方法，第一个参数是方法名；第二个参数是参数的类型，注意是参数的类型！  
              Method   method=getMethod(clazz,methodName,classes);
              //开始调用方法，第一个参数是调用该方法的对象；第二个参数是值
              method.invoke(obj,params);  
          } catch (Exception e){
        	  log.error("执行"+methodName+"方法报错,类的全路径:"+classPath+",参数值："+params,e);
          }
     }  
        
	/** 
     * 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。 
     *  
     * @param clazz 
     *            目标类 
     * @param methodName 
     *            方法名 
     * @param classes 
     *            方法参数类型
     * @return 方法对象 
     * @throws Exception 
     */  
    private static Method getMethod(Class clazz, String methodName,  
            final Class classes) throws Exception {  
        Method method = null;  
        try {  
            method = clazz.getDeclaredMethod(methodName, classes);  
        } catch (NoSuchMethodException e) {  
            try {  
                method = clazz.getMethod(methodName, classes);  
            } catch (NoSuchMethodException ex) {  
                if (clazz.getSuperclass() == null) {  
                    return method;  
                } else {  
                    method = getMethod(clazz.getSuperclass(), methodName,  
                            classes);  
                }  
            }  
        }  
        return method;  
    }  
  
   
        
}
