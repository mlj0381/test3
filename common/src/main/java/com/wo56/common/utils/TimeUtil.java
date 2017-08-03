package com.wo56.common.utils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.framework.core.util.DateUtil;

public class TimeUtil {
	
	private static String DEFAULT_FORMAT = "yyyy-MM-dd";
	
	
	public static String getSysTime() {
		
		SimpleDateFormat df = new SimpleDateFormat(DateUtil.DATETIME12_FORMAT);// 设置日期格式
		return df.format(new Date());
	}
	
	public static String getTime() {
		SimpleDateFormat df = new SimpleDateFormat(DateUtil.YEAR_MONTH_FORMAT2);// 设置日期格式
		return df.format(new Date());
	}
	
	public static String getTime(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);// 设置日期格式
		return df.format(new Date());
	}
	public static String getTime(String format,Date time) {
		SimpleDateFormat df = new SimpleDateFormat(format);// 设置日期格式
		return df.format(time);
	}
	public static Date getTime(String format,String time)throws Exception{
		SimpleDateFormat df = new SimpleDateFormat(format);// 设置日期格式
		return df.parse(time);
	}
	public static String getTimeToStr(String format,String time)throws Exception{
		Date date = new SimpleDateFormat(DateUtil.DATETIME12_FORMAT).parse(time);
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 获取当前时间
	 * @param format
	 * @return
	 */
	public static Date getDataTime()throws Exception {
		try{
			return new Date();
		}catch(Exception e){
			throw new Exception ("获取当前时间错误:"+e.getMessage());
		}
	}
	
	/**
	 * 获取当前时间
	 * @param format
	 * @return
	 */
	public static Date getDateime()throws Exception {
		try{
			return new Date();
		}catch(Exception e){
			throw new Exception ("获取当前时间错误:"+e.getMessage());
		}
	}
	
	/** 
     * 格式化日期 
     * @param date 日期对象 
     * @return String 日期字符串 
     */  
    public static String formatDate(Date date){  
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);  
        String sDate = f.format(date);  
        return sDate;  
    }  
    
    /** 
     * 获取当年的第一天 
     * @param year 
     * @return 
     */  
    public static Date getCurrYearFirst(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearFirst(currentYear);  
    }  
      
    /** 
     * 获取当年的最后一天 
     * @param year 
     * @return 
     */  
    public static Date getCurrYearLast(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearLast(currentYear);  
    }  
      
    /** 
     * 获取某年第一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearFirst(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        Date currYearFirst = calendar.getTime();  
        return currYearFirst;  
    }  
      
    /** 
     * 获取某年最后一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
        Date currYearLast = calendar.getTime();  
          
        return currYearLast;  
    }
    /** 
     * 获取距离今晚0时的秒数
     */ 
    public static int getTodaySeconds(){
    	Date current = new Date();
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MINUTE,59);
        return Integer.parseInt(""+(calendar.getTimeInMillis()-current.getTime())/1000);
    }
    
    /**
     * 获取开始和结束时间的月份只限制2月
     * @param minDate
     * @param maxDate
     * @return
     * @throws Exception
     */
    public static Map getMonthBetween(String minDate, String maxDate) throws Exception {
		Map map = new HashMap();
	    ArrayList<String> result = new ArrayList<String>();
	    SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATETIME12_FORMAT);//格式化为年月
	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();
	    min.setTime(sdf.parse(minDate));
	    max.setTime(sdf.parse(maxDate));
	    int minYearNum=min.get(Calendar.YEAR);
	    int minMonthNum=min.get(Calendar.MONTH);
	    int minDateNum=min.get(Calendar.DATE);
	    int minHourNum=min.get(Calendar.HOUR_OF_DAY);
	    int minMinuteNum=min.get(Calendar.MINUTE);
	    int minSecondNum=min.get(Calendar.SECOND);
	    int maxYearNum=max.get(Calendar.YEAR);
	    int maxMonthNum=max.get(Calendar.MONTH);
	    int maxDateNum=max.get(Calendar.DATE);
	    int maxHourNum=max.get(Calendar.HOUR_OF_DAY);
	    int maxMinuteNum=max.get(Calendar.MINUTE);
	    int maxSecondNum=max.get(Calendar.SECOND);
	    if(minYearNum!=maxYearNum){
	    	return map;
	    }
	    if(minYearNum==maxYearNum && (maxMonthNum-minMonthNum)>1){
	    	return map;
	    }
	    if(minYearNum==maxYearNum && minMonthNum==maxMonthNum){
	    	result.add(sdf.format(min.getTime()));
	    	result.add(sdf.format(max.getTime()));
	    	map.put("1", result);
	    	return map;
	    }
	    if(minYearNum==maxYearNum && minMonthNum>maxMonthNum){
	    	return map;
	    }
	    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
	    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
	    Calendar curr = min;
	    for(int i=0;i<2;i++) {
	    	if(i==0){
	    		curr.set(Calendar.DATE, minDateNum);
		    	curr.set(Calendar.HOUR_OF_DAY, minHourNum);
		    	curr.set(Calendar.MINUTE, minMinuteNum);
		    	curr.set(Calendar.SECOND, minSecondNum);
	    	}else{
	    		curr.set(Calendar.DATE, maxDateNum);
		    	curr.set(Calendar.HOUR_OF_DAY, maxHourNum);
		    	curr.set(Calendar.MINUTE, maxMinuteNum);
		    	curr.set(Calendar.SECOND, maxSecondNum);
	    	}
	    	result.add(sdf.format(curr.getTime()));
	    	curr.add(Calendar.MONTH, 1);
	    }
	    map.put("2", result);
	    return map;
	  }
	/**
	 * 获取时间段（分钟）
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 * @return
	 */
    public static long getMinsBetween(Date startDate ,Date endDate){
    	long startDateMilliseconds = startDate.getTime();
    	long endDateMilliseconds = endDate.getTime();
    	long mins = (endDateMilliseconds - startDateMilliseconds)/(1000*60);
    	return mins;
    }
}
