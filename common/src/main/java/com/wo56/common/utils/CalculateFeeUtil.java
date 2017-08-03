package com.wo56.common.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 根据公式计算线路费用
 * */
public class CalculateFeeUtil {
	private static final Log log = LogFactory.getLog(CalculateFeeUtil.class);

	 static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");  
	/***
	 * @param forMula 计算公式
	 * @param weight 重量 
	 * @param volume 体积
	 * */
	public static long calculateFee(String forMula){
		double fees=0d;
        try {  
             fees =  (Double) jse.eval(forMula); 
        } catch (Exception t) { 
        	log.error("公式["+forMula+"]转换出错",t);
        } 
        long fee=(long) (Math.round(fees));
		return fee;
	}
	
	/***
	 * @param forMula 计算公式
	 * @param weight 重量 
	 * @param volume 体积
	 * */
	public static double calculateDFee(String forMula){
		double fees=0d;
        try {  
             fees =  (Double) jse.eval(forMula); 
        } catch (Exception t) { 
        	log.error("公式["+forMula+"]转换出错",t);
        } 
		return fees;
	}
	
}
