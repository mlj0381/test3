package com.wo56.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.TransactionBlock;
import redis.clients.jedis.exceptions.JedisException;

import com.framework.components.citys.City;
import com.framework.components.citys.District;
import com.framework.components.citys.Province;
import com.framework.components.citys.Street;
import com.framework.components.redis.RedisHelper;
import com.framework.components.redis.RemoteCacheUtil;
import com.framework.core.SysContexts;
import com.framework.core.cache.CacheFactory;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DateUtil;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.RandomGenerator;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ord.vo.out.OrdersSelectOut;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.sys.vo.SysTenantDef;
import com.wo56.common.cache.SysTenantDefCache;
import com.wo56.common.consts.AccountManageConsts;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.EnumConsts.TRANSFER_CONFIGURATION;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.consts.LogBIConstant;
import com.wo56.common.consts.LoginConst;
import com.wo56.common.consts.PermissionConsts;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.sms.utils.SysTenantExtendCacheUtil;

/**
 * @author zx
 *
 */
public class CommonUtil {
	
	private static final Log log = LogFactory.getLog(CommonUtil.class);
	
	/**
	 * 检测正确的手机号码
	 * 
	 * @param billId
	 * @return
	 * @throws Exception
	 */
	public static boolean isCheckPhone(String billId) throws Exception {
		Pattern pat = Pattern.compile("^(13[0-9]|15[0-9]|18[0-9]|14[57]|17[035678])[0-9]{8}$");
		Matcher mat = pat.matcher(billId);
		return mat.matches();
	}
	public static boolean isCheckMobiPhone(String billId) throws Exception {
		Pattern pat = Pattern.compile("^((0\\d{2,3}-?\\d{7,8})|(1[35784]\\d{9}))$");
		Matcher mat = pat.matcher(billId);
		return mat.matches();
	}
	/**
	 * 验证车牌号
	 * @param plateNumber
	 * @return
	 * @throws Exception
	 */
	public static boolean isPlateNumber(String plateNumber) throws Exception{
		Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5|WJ]{1}[A-Z0-9]{6}$");
		Matcher mat = pattern.matcher(plateNumber);
		return mat.matches();
	}

	/** * 判断字符串是否是整数 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/** * 判断字符串是否是整数 */
	public static boolean isLong(String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/** * 判断字符串是否是浮点数 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/** * 判断字符串是否是数字 */
	public static boolean isNumber(String value) {
		return isInteger(value) || isDouble(value) || isLong(value);
	}
	
	/**
	 * 判断是否是车长格式
	 * @param value
	 * @return
	 */
	public static boolean isPositiveNumber(String value){
		Pattern pat = Pattern.compile("^(([1-9]+)|([0-9]+\\.[0-9]{1,2}))$");
		Matcher mat = pat.matcher(value);
		return mat.matches();
	}
	
	/**
	 * 验证密码格式
	 * @param value
	 * @return
	 */
	public static boolean isCheckPwd(String value){
		Pattern pat = Pattern.compile("^[\\dA-Za-z(!@#$%&)]{6,16}$");
		Matcher mat = pat.matcher(value);
		return mat.matches();
	}
	
	/**
	 * 验证注册名格式
	 * @param value
	 * @return
	 */
	public static boolean isLoginAcct(String value){
		Pattern pat = Pattern.compile("^[a-zA-Z0-9]{1}[a-zA-Z0-9|-|_]{3,16}$");
		Matcher mat = pat.matcher(value);
		return mat.matches();
	}
	
	/**
	 * 转换城市名称 省市县
	 * @param provinceId
	 * @param regionId
	 * @param countyId
	 * @return
	 */
	public static String getCityName(Long provinceId,Long regionId,Long countyId){
		String ctiyName="";
		if(provinceId!=null&&provinceId > 0){
			ctiyName+=SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", provinceId+"").getName();
		}
		if(regionId!=null&&regionId > 0){
			ctiyName+=SysStaticDataUtil.getCityDataList("SYS_CITY", regionId+"").getName();
	    }
		if(countyId!=null&&countyId > 0){
			ctiyName+=SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", countyId+"").getName();
	    }
		return ctiyName;
	}
	
	/**
	 * 短信专用 市县
	 * @param regionId
	 * @param countyId
	 * @return
	 */
	public static String getCityName(Integer regionId,Integer countyId){
		String ctiyName="";
		if(regionId!=null&&regionId > 0){
			ctiyName+=SysStaticDataUtil.getCityDataList("SYS_CITY", regionId+"").getName();
	    }
		if(countyId!=null&&countyId > 0){
			ctiyName+=SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", countyId+"").getName();
	    }
		return ctiyName;
	}
	
	 public static String getRanPass(){
		    String  passWord=""; 
	        char[] passwordLit = new char[62];
	        char fword = 'A';
	        char mword = 'a';
	        char bword = '0';
	        for (int i = 0; i < 62; i++) {
	            if (i < 26) {
	                passwordLit[i] = fword;
	                fword++;
	            }else if(i<52){
	                passwordLit[i] = mword;
	                mword++;
	            }else{
	                passwordLit[i] = bword;
	                bword++;
	        }
	        }
	        Random rr = new Random();
	        char[] pw= new char[6];
	        for(int i=0;i<pw.length;i++){
	            int num = rr.nextInt(62);
	            pw[i]=passwordLit[num];
	            passWord+=pw[i];
	        }
	     return passWord;
	    }
	 
	
		
		public static String replaceMacroVariables(String str, Map<String, String> params, String defaultValue) {
			if (StringUtils.isBlank(str) || params == null) {
				if (defaultValue != null) {
					return defaultValue;
				}
				return str;
			}
			Iterator<String> keyIte = params.keySet().iterator();
			while (keyIte.hasNext()) {
				String key = keyIte.next();
				String paraCode = LogBIConstant.MacroVariables.PREFIX_CHAR + key + LogBIConstant.MacroVariables.SUFFIX_CHAR;
				String paraValue = params.get(key);
				str = StringUtils.replace(str, paraCode, paraValue);
			}
			return str;
		}
		
		
		public static String createChartRandomKey() throws Exception {
			Random  r = new Random();
			int randomNumber = r.nextInt(899) + 100;
			return "chartSeries" + DateUtil.formatDateByFormat(CommonUtil.getDataTime(), "yyMMddHHmmss") + randomNumber;
		}
	
		
		/**
		 * 获取数据库时间
		 * @param format
		 * @return
		 */
		public static Date getDataTime()throws Exception {
			Date date = null;
			try{
				Session session = SysContexts.getEntityManager();
				Query query = session.createSQLQuery("select now() ");           
				date=(Date)query.uniqueResult();
			}catch(Exception e){
				throw new Exception ("获取数据库时间错误:"+e.getMessage());
			}
			return date;
		}
		
		/**
		 * 获取当前月的开始时间
		 * @return
		 * @throws ParseException
		 */
		public static Date getCurrentMonthStartTime() throws ParseException {
			return getSpecialMonthStartTime(new Date());
		}
		
		/**
		 * 获取指定时间的前后dayNumber的时间
		 * 
		 * @param date : 指定时间
		 * @param dayNumber : 正数为前，负数为后
		 * @return
		 */
		public static Date getSpecialDayBeforeOrAfterTime(Date date, int dayNumber) {
			if(null == date)
				date = getCurrentDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_YEAR, -dayNumber);
			return cal.getTime();
		}
		
		public static Date getSpecialDayBeforeOrAfterDay(int dayNumber) {
			return getSpecialDayBeforeOrAfterTime(null, dayNumber);
		}
		
		public static Date addMinis(Date date, int minis) {
			if (date == null) {
				return null;
			}
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(getMillis(date) + minis * 60L * 1000L);
			return c.getTime();
		}

		public static long getMillis(java.util.Date date) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.getTimeInMillis();
		}
		
		/**
		 * 获取指定月的开始时间
		 * @param date
		 * @return
		 * @throws ParseException
		 */
		public static Date getSpecialMonthStartTime(Date date) throws ParseException {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(sdf.format(cal.getTime()));
		}
		
		/**
		 * 获取指定月的结束时间
		 * @param date
		 * @return
		 * @throws ParseException
		 */
		public static Date getSpecialMonthEndTime(Date date) throws ParseException {
			Date startDate = getSpecialMonthStartTime(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.SECOND, -1);
			return cal.getTime();
		}
		
		/**
		 * 获取指定年的开始时间
		 * @param date
		 * @return
		 * @throws ParseException
		 */
		public static Date getSpecialYearStartTime(Date date) throws ParseException {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(sdf.format(cal.getTime()));
		}
		
		/**
		 * 获取指定年的结束时间
		 * @param date
		 * @return
		 * @throws ParseException
		 */
		public static Date getSpecialYearEndTime(Date date) throws ParseException {
			Date startDate = getSpecialYearStartTime(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.YEAR, 1);
			cal.add(Calendar.SECOND, -1);
			return cal.getTime();
		}
		
		/**
		 * 获取指定年某个季度的结束时间(季度分为1，2，3，4季度)
		 * @param date
		 * @param quarterNumber
		 * @return
		 * @throws ParseException
		 */
		public static Date getSpecialYearSpecialQuarterEndTime(Date date,int quarterNumber) throws ParseException {
			Date yearStartTime = getSpecialYearStartTime(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(yearStartTime);
			cal.add(Calendar.MONTH, quarterNumber * 3);
			cal.add(Calendar.SECOND, -1);
			return cal.getTime();
		}
		
		/**
		 * 获取指定年某个季度的开始时间(季度分为1，2，3，4季度)
		 * @param date
		 * @param quarterNumber
		 * @return
		 * @throws ParseException
		 */
		public static Date getSpecialYearSpecialQuarterStartTime(Date date,int quarterNumber) throws ParseException {
			Date yearStartTime = getSpecialYearStartTime(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(yearStartTime);
			cal.add(Calendar.MONTH, (quarterNumber - 1) * 3);
			return cal.getTime();
		}
		
		/**
		 * 获取指定天的结束时间
		 * @param date
		 * @return
		 * @throws ParseException
		 */
		public static Date getSpecialDayEndTime(Date date) throws ParseException {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getSpecialDayStartTime(date));
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.SECOND, -1);
			return cal.getTime();
		}
		
		/**
		 * 获取指定天的开始时间
		 * @param date
		 * @return
		 * @throws ParseException
		 */
		public static Date getSpecialDayStartTime(Date date) throws ParseException {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			return cal.getTime();
		}
		
		/**
		 * 获取指定月对应的N个月前的时间
		 * @param date
		 * @param number
		 * @return
		 * @throws Exception
		 */
		public static Date getLastNumberMonth(Date date, int number) throws Exception {
			if (number <= 0)
				return date;
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, -number);
			return cal.getTime();
		}
		
		/**
		 * 计算两个日期之间相差的天数
		 * 
		 * @param smdate
		 *            较小的时间
		 * @param bdate
		 *            较大的时间
		 * @return 相差天数
		 * @throws ParseException
		 */
		public static int daysBetween(Date smdate, Date bdate)throws ParseException {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days)) + ((time2 - time1) % (1000 * 3600 * 24) == 0 ? 0 : 1);
		}
		
		/**
		 * 获取特定时间的“前后”分钟数
		 * @param date
		 * @param minuteNumber
		 * @return
		 */
		public static Date getSpecialDateBeforeOrAfterMinute(Date date, int minuteNumber) {
			if (date == null)
				date = getCurrentDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MINUTE, minuteNumber);
			return cal.getTime();
		}
		
		
		public static Date getCurrentDate(){
			return new Date();
		}
		
		/**
		 * 获取 double 
		 * @param number
		 * @param dig //最多保留几位小数
		 * @return
		 */
		public static String getDoubleFixed(double number, int dig){
			 String  numberStr = "";
			 BigDecimal bg = new BigDecimal(number);  
	         double x = bg.setScale(dig, BigDecimal.ROUND_HALF_UP).doubleValue();  
	         if(Math.abs(x-Math.round(x)) <= 0.000000001 ){
	        	 numberStr =  Math.round(x)+".0";
	         }else{
	        	 numberStr = String.valueOf(x);
	         }
			  return numberStr;
		}
		
		public static double getDoubleToFixed(double number, int dig){
			 String  numberStr = "";
			 BigDecimal bg = new BigDecimal(number);  
	         double x = bg.setScale(dig, BigDecimal.ROUND_HALF_UP).doubleValue();  
	         if(Math.abs(x-Math.round(x)) <= 0.000000001 ){
	        	 numberStr =  Math.round(x)+".0";
	        	 return Double.valueOf(numberStr);
	         }else{
	        	return x;
	         }
		}
		
		/**
		 * 获取  字符串 保留2位小数  元的数字
		 * @param moneyStr  单位分的数字
		 * 
		 * @return
		 */
		public static String getDoubleFixedNew2(String moneyStr){
			 if (StringUtils.isNotEmpty(moneyStr) && Long.valueOf(moneyStr) > 0) {
				  double money = Double.valueOf(moneyStr) / 100;
				  BigDecimal bg = new BigDecimal(money);  
		          double xo = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
		          DecimalFormat  df = new DecimalFormat("######0.00");  
				  return String.valueOf(df.format(xo));
			  }else {
				  return "0.00";
			}
		}
		
		/**
		 * 获取  字符串 保留1位小数的数字
		 * @param dealNumber  double 数字
		 * 
		 * @return
		 */
		public static String getDoubleFixedNew1(Double dealNumber){
			 BigDecimal bg = new BigDecimal(dealNumber);  
		     double xo = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();  
		     DecimalFormat  df = new DecimalFormat("######0.0");  
			 return String.valueOf(df.format(xo));
		}
		
		/**
		 * 获取  字符串 保留2位小数的数字
		 * @param dealNumber  double 数字
		 * 
		 * @return
		 */
		public static String getDoubleFixedNew2(Double dealNumber){
			 BigDecimal bg = new BigDecimal(dealNumber);  
		     double xo = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
		     DecimalFormat  df = new DecimalFormat("######0.00");  
			 return String.valueOf(df.format(xo));
		}
		
		/**
		 * 获取公司编码
		 * @return
		 */
		public static String getTenantCode(){
			BaseUser user = SysContexts.getCurrentOperator();
			if(user==null){
				return "";
			}
			Map map = user.getAttrs();
			if(map!=null){
				String tanentCode = (String)map.get(LoginConst.BaseUserAttr.TENANT_CODE);
				return tanentCode;
			}
			return null;
		}
		
		/**
		 * 获取操作员
		 * @return
		 */
		public static BaseUser getBaseUser(){
			BaseUser user = SysContexts.getCurrentOperator();
			return user;
		}
		/**
		 * 根据城市名称获取id
		 * @param cityName
		 * @return
		 */
		public static Long getCityIdByName(String cityName){
			List<City> cityList = SysStaticDataUtil.getCityDataList(SysStaticDataEnum.AREA_CODE_TYPE.CITY_TYPE);
			for(City city:cityList){
				if(city.getName().equals(cityName)){
					return city.getId();
				}
			}
			return null;
		}
		/**
		 * 根据省份名称获取id
		 * @param provinceName
		 * @return
		 */
		public static Long getProvinceIdByName(String provinceName){
			List<Province> provinces = SysStaticDataUtil.getProvinceDataList(SysStaticDataEnum.AREA_CODE_TYPE.PROVINCE_TYPE);
			for(Province province:provinces){
				if(province.getName().equals(provinceName)){
					return province.getId();
				}
			}
			return null;
		}
		
		/**
		 * 根据区域名称获取id
		 * @param districtName
		 * @return
		 */
		public static Long getDistrictIdByName(String districtName){
			List<District> districts = SysStaticDataUtil.getDistrictDataList(SysStaticDataEnum.AREA_CODE_TYPE.DISTRICE_TYPE);
			for(District district:districts){
				if(district.getName().equals(districtName)){
					return district.getId();
				}
			}
			return null;
		}
		
		/**
		 * 根据区域名称获取id
		 * @param districtName
		 * @return
		 */
		public static Long getStreetIdByName(String streetName){
			List<Street> streets = SysStaticDataUtil.getStreetDataList(SysStaticDataEnum.AREA_CODE_TYPE.STREET_TYPE);
			for(Street street:streets){
				if(street.getName().equals(streetName)){
					return street.getId();
				}
			}
			return null;
		}
		
		public static Long getRandomNumber(int type,int len,int groupNum){
			String[] num = RandomGenerator.random(type,len,groupNum);
			if(num.length>0){
				String  numb  = num[0];
				Long number  = Long.valueOf(numb);
				return number;
			}
			return null;
		}
		
		/**
		 * 获取组织编码
		 * @return
		 */
		public static String getOrgCode(){
			BaseUser user = SysContexts.getCurrentOperator();
			if(user==null){
				return "";
			}
			Map map = user.getAttrs();
			if(map!=null){
				String orgCode = (String)map.get(LoginConst.BaseUserAttr.ORG_CODE);
				return orgCode;
			}
			return null;
		}
		
		/**
		 * 获取操作员ID
		 * @return
		 */
		public static long getOpId(){
			BaseUser user = SysContexts.getCurrentOperator();
			if(user==null){
				return 0;
			}
			return user.getUserId();
		}
		
		/***
		 * 计算时间差
		 * 
		 * **/
		public static String  subtractTime(Date  maxDate,Date  minDate) {
			  long diff =   maxDate.getTime()- minDate.getTime();//这样得到的差值是微秒级别
			  long days = diff / (1000 * 60 * 60 * 24);
			  long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			  long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
			  if(days>0){
				 return  days*24+hours+"."+Math.round((minutes/60));
			  }else{
				  return hours+"."+Math.round((minutes/60))+"";
			  }
		}
		
		/**
		 * 生成订单ID 2+yyMMddHHmmss+3位随机数
		 * 
		 * @return
		 * @throws Exception
		 */
		public static long createOrderId() throws Exception {
			long userId = 0;
			try {
				Random  r = new Random();
				int randomNumber = r.nextInt(899) + 100;
				String userIdStr = "2"
						+ DateUtil.formatDateByFormat(TimeUtil.getDataTime(),
								"yyMMddHHmmss") + randomNumber;
				userId = Long.parseLong(userIdStr);
			} catch (Exception e) {
				throw new Exception("生成用户编号错误:" + e.getMessage());
			}
			return userId;
		}
		/**
		 * 根据租户id查询租户名称
		 * 
		 * @param tenantId
		 * @return
		 */
		public static String getTennatNameById(long tenantId){
			List<SysTenantDef> sysTenantDefs=(List<SysTenantDef>)CacheFactory.get(SysTenantDefCache.class.getName(), PermissionConsts.GrantConstant.SYS_TENANT_DEF);
			if(sysTenantDefs==null || sysTenantDefs.size()<=0){
				return "";
			}
			for(SysTenantDef sysTenantDef:sysTenantDefs){
				if(sysTenantDef.getTenantId()==tenantId){
					return sysTenantDef.getName();
				}
			}
			return "";
		}
		
		/**
		 * 根据租户id查询租户编码
		 * 
		 * @param tenantId
		 * @return
		 */
		public static String getTennatCodeById(long tenantId){
			List<SysTenantDef> sysTenantDefs=(List<SysTenantDef>)CacheFactory.get(SysTenantDefCache.class.getName(), PermissionConsts.GrantConstant.SYS_TENANT_DEF);
			if(sysTenantDefs==null || sysTenantDefs.size()<=0){
				return "";
			}
			for(SysTenantDef sysTenantDef:sysTenantDefs){
				if(sysTenantDef.getTenantId()==tenantId){
					return sysTenantDef.getTenantCode();
				}
			}
			return "";
		}
		
		public static boolean isCollection(String string){
			boolean flag = true;
			if(StringUtils.isEmpty(string) ||string.indexOf("[") < 0 || string.indexOf("]") < 0){
				 flag = false;
		    }
			return flag;
		}
	/**
	 * 获取运单号,去掉前缀的号码的
	 * @param tenantId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static String getTrackingNum(Long tenantId,Long userId)	throws Exception {
		if(tenantId==null || tenantId<0){
			throw new BusinessException("传入的租户为空");
		}
		if(userId==null || userId<0){
			throw new BusinessException("传入的用户为空");
		}
		
		String trackingBeginNum = "";
		trackingBeginNum = SysTenantExtendCacheUtil.getValue(tenantId, EnumConsts.TRACKING_NUM_REDIS.TANANT_TRACKING_NUM_BEGIN);
		if(trackingBeginNum==null){
			trackingBeginNum = "";
		}
		String longTrackingNum="";
		//如果配载了1 ：根据租户＋用户 ＋运单号 进行设置运单号规则。0 或者没有设置，默认使用随机生成的运单号
		String genType=SysTenantExtendCacheUtil.getValue(tenantId, EnumConsts.TRACKING_NUM_REDIS.TRACKING_NUM_GEN_TYPE);
		if(StringUtils.isNotEmpty(genType) && EnumConsts.TRACKING_NUM_REDIS.TRACKING_NUM_GEN_TYPE_1.equals(genType)){
			String key=tenantId+"_"+userId;
			longTrackingNum=CommonUtil.getTrackingNumByUser(key,tenantId);
			
		}else{
			longTrackingNum=CommonUtil.getTrackingNum(tenantId.toString());
		}
		//缓存保存的是前缀加＋手动输入的数据
		String trackingNum=longTrackingNum.replaceFirst(trackingBeginNum,"");
		return trackingNum;
	}
	/**
	 * 返回完整的运动号
	 * @param tenantId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static String getTrackingNumAll(Long tenantId,Long userId)	throws Exception {
		if(tenantId==null || tenantId<0){
			throw new BusinessException("传入的租户为空");
		}
		if(userId==null || userId<0){
			throw new BusinessException("传入的用户为空");
		}
		
		String longTrackingNum="";
		String trackingBeginNum = SysTenantExtendCacheUtil.getValue(tenantId, EnumConsts.TRACKING_NUM_REDIS.TANANT_TRACKING_NUM_BEGIN);
		//如果配载了1 ：根据租户＋用户 ＋运单号 进行设置运单号规则。0 或者没有设置，默认使用随机生成的运单号
		String genType=SysTenantExtendCacheUtil.getValue(tenantId, EnumConsts.TRACKING_NUM_REDIS.TRACKING_NUM_GEN_TYPE);
		if(StringUtils.isNotEmpty(genType) && EnumConsts.TRACKING_NUM_REDIS.TRACKING_NUM_GEN_TYPE_1.equals(genType)){
			String key=tenantId+"_"+userId;
			longTrackingNum=CommonUtil.getTrackingNumByUser(key,tenantId);
			
		}else{
			longTrackingNum=CommonUtil.getTrackingNum(tenantId.toString());
		}
		return longTrackingNum;
	}
	
	
	/**
	 * 获取中转对外配置租户
	 */
	public static String getTenantExtend(Long tenantId)throws Exception{
		if(tenantId==null||tenantId<0){
			throw new BusinessException("传入的租户为空");
		}
		String transferConfiguration="";
		transferConfiguration=SysTenantExtendCacheUtil.getValue(tenantId, EnumConsts.TRANSFER_CONFIGURATION.TRANSFER_OUT_CONFIGURATION);
		if(StringUtils.isEmpty(transferConfiguration)){
			transferConfiguration=SysTenantExtendCacheUtil.getValue(-1L, EnumConsts.TRANSFER_CONFIGURATION.TRANSFER_OUT_CONFIGURATION);
		}
		return transferConfiguration;
	}
	
	/**
	 *根据租户的运单号的生成规则，决定是走那种方式：
	 *1 根据租户递增的方式
	 *2 根据开单用户设置运单号递增 
	 * 
	 * 需要设置一下：
	 * 			  EnumConsts.TRACKING_NUM_REDIS.ORD_SEQ_MANUAL，按租户保存全的运单号，用户后面获取的时候进行校验
	 * 			  
	 * 
	 * @param tenantId
	 * @param userId
	 * @param trackingNum
	 * @throws Exception
	 */
	public static void setTrackingNumToRedis(Long tenantId,Long userId,Long trackingNum)	throws Exception{
		if(tenantId==null || tenantId<0){
			throw new BusinessException("传入的租户为空");
		}
		if(userId==null || userId<0){
			throw new BusinessException("传入的用户为空");
		}
		if(trackingNum==null || trackingNum<0){
			throw new BusinessException("传入的运单号为空");
		}
		String genType=SysTenantExtendCacheUtil.getValue(tenantId, EnumConsts.TRACKING_NUM_REDIS.TRACKING_NUM_GEN_TYPE);
		if(StringUtils.isNotEmpty(genType) && EnumConsts.TRACKING_NUM_REDIS.TRACKING_NUM_GEN_TYPE_1.equals(genType)){
			String key=tenantId+"_"+userId;
			RemoteCacheUtil.sadd(EnumConsts.TRACKING_NUM_REDIS.ORD_SEQ_MANUAL + tenantId.toString(), trackingNum.toString());
			RemoteCacheUtil.set(EnumConsts.TRACKING_NUM_REDIS.ORD_SEQ + key,trackingNum.toString());
		}else{
			RemoteCacheUtil.sadd(EnumConsts.TRACKING_NUM_REDIS.ORD_SEQ_MANUAL + tenantId.toString(), trackingNum.toString());
		}
	}
	
	private static String getTrackingNum(String key) throws Exception {
		Jedis jedis = null;
		final Object[] objs = new Object[2];
		objs[1] = key;
		try {
			jedis = RedisHelper.getInstance().getJedisWithOutShare();
			jedis.multi(new TransactionBlock() {
				long trackingNum = 0;
				@Override
				public void execute() throws JedisException {
					trackingNum = RemoteCacheUtil.incr(EnumConsts.TRACKING_NUM_REDIS.ORD_SEQ + objs[1]);
					while (RemoteCacheUtil.sismember(
							EnumConsts.TRACKING_NUM_REDIS.ORD_SEQ_MANUAL + objs[1],
							String.valueOf(trackingNum))) {
						trackingNum = RemoteCacheUtil.incr(EnumConsts.TRACKING_NUM_REDIS.ORD_SEQ + objs[1]);
					}
					objs[0] = trackingNum;
				}
			});
		} catch (Exception ex) {
			objs[0] = 0;
			log.warn("Problem update access time to redis", ex);
		} finally {
			RedisHelper.getInstance().returnResource(jedis);
		}
		JSONObject ret = new JSONObject();
		// 运单号为0，则说明出现问题，需要从运单表中读取最大运单号+1前台使用
		if ("0".equals(objs[0])) {

		}
		return String.valueOf(objs[0]);
	}
	
	/**
	 * 获取数据是需要按租户跟用户获取，比较该运单号是否已经被使用了，需要按租户进行查询
	 * 
	 * @param key  租户＋用户id
	 * @param tenantId 租户
	 * @return
	 * @throws Exception
	 */
	private static String getTrackingNumByUser(String key,Long tenantId) throws Exception {
		Jedis jedis = null;
		final Object[] objs = new Object[3];
		objs[1] = key;
		objs[2] = tenantId;
		try {
			jedis = RedisHelper.getInstance().getJedisWithOutShare();
			jedis.multi(new TransactionBlock() {
				long trackingNum = 0;
				@Override
				public void execute() throws JedisException {
					trackingNum = RemoteCacheUtil.incr(EnumConsts.TRACKING_NUM_REDIS.ORD_SEQ + objs[1]);
					if(RemoteCacheUtil.sismember(
							EnumConsts.TRACKING_NUM_REDIS.ORD_SEQ_MANUAL + objs[2],
							String.valueOf(trackingNum))) {
						trackingNum =-1;
					}
					objs[0] = trackingNum;
				}
			});
		} catch (Exception ex) {
			objs[0] = 0;
			log.warn("Problem update access time to redis", ex);
		} finally {
			RedisHelper.getInstance().returnResource(jedis);
		}
		JSONObject ret = new JSONObject();
		// 运单号为0，则说明出现问题，需要从运单表中读取最大运单号+1前台使用
		if ("0".equals(objs[0])) {

		}
		
		if("-1".equals(objs[0].toString())){
			objs[0]="";
		}
		
		return String.valueOf(objs[0]);
	}
	
		
		/**
		 * 金额分转元
		 * */
		public static String  parseDouble(Long balance){
			if (balance == null) {
				return "0.00";
			}
			DecimalFormat df  = new DecimalFormat("###0.00");
			Double money=(double)balance/100;
			String moenyS = df.format(money);
			return moenyS;
			
		}
		
		/**
		 * 金额分转元
		 * */
		public static String  parseDouble(long balance,int num){
			DecimalFormat df  = null;
			if (num == 2) {
				df  = new DecimalFormat("###0.00");
			}else if(num == 3){
				df  = new DecimalFormat("###0.000");
			}else if(num == 4){
				df  = new DecimalFormat("###0.0000");
			}else{
				df  = new DecimalFormat("###0.0");
			}
			
			Double money=(double)balance/100;
			String moenyS = df.format(money);
			return moenyS;
		}
		
		/**
		 * 根据运单状态获取APP展示的状态名称
		 * @param  isTrue     是否是 开单网点 == 当前网点  （主要是  判断 待发车、待配载状态是在 运输中 、 还是开单的待发车） true 待发车  false 运输中
		 * @param  orderState 运单状态
		 */
		public static String getAppOrderStateName(Integer orderState, boolean isTrue) {
			List<SysStaticData> lists = SysStaticDataUtil.getSysStaticDataList("APP_ORDER_STATE");
			String codeName = "";
			for(SysStaticData os : lists){
				String[] ss = os.getCodeDesc().split(",");
				for(String s : ss){
					if(s.equals(orderState+"")){
						codeName =  os.getCodeName();
					}
				}
			}
			if(orderState == SysStaticDataEnum.ORDER_STATE.MATCH || orderState == SysStaticDataEnum.ORDER_STATE.WAIT_GO){
				if(isTrue){
					//待发车
					codeName = SysStaticDataUtil.getSysStaticDataCodeName("APP_ORDER_STATE", "1");
				}else{
					//运输中
					codeName = SysStaticDataUtil.getSysStaticDataCodeName("APP_ORDER_STATE", "2");
				}
				
				
				
			}
			return codeName;
		}
		/**
		 * 根据运单交接方式获取APP展示的状态名称
		 */
		public static String getAppDeliveryTypeName(String scheServiceType,Long tenantId) {
			SysStaticData sysStaticData = null;
			if(tenantId != null && tenantId > 0){
				sysStaticData = SysStaticDataUtil.getSysStaticData(tenantId,"SCHE_SERVICE_TYPE",scheServiceType);
			}else{
				sysStaticData = SysStaticDataUtil.getSysStaticData("SCHE_SERVICE_TYPE",scheServiceType);
			}
			 

			String deliveryTypeName = "自提";
			if(sysStaticData != null && !"11".equals(sysStaticData.getCodeValue())){
				deliveryTypeName = sysStaticData.getCodeName();
			}
			return deliveryTypeName;
		}
		/**
		 * 根据运单状态获取APP展示的状态
		 */
		public static String getAppOrderStateValue(Integer orderState, boolean isTrue) {
			List<SysStaticData> lists = SysStaticDataUtil.getSysStaticDataList("APP_ORDER_STATE");
			String codeValue = "";
			for(SysStaticData os : lists){
				String[] ss = os.getCodeDesc().split(",");
				for(String s : ss){
					if(s.equals(orderState+"")){
						codeValue =  os.getCodeValue();
					}
				}
			}
			
		   if(orderState == SysStaticDataEnum.ORDER_STATE.MATCH || orderState == SysStaticDataEnum.ORDER_STATE.WAIT_GO){
				if(isTrue){
					//待发车
					codeValue = "1";
				}else{
					//运输中
					codeValue = "2";
				}
			}
			
			return codeValue;
		}
		/**
		 * 毫秒转化时分 
		 */  
		public static String formatTime(Long ms) {  
		    Integer ss = 1000;  
		    Integer mi = ss * 60;  
		    Integer hh = mi * 60;  
		    Integer dd = hh * 24;  
		    Long day = ms / dd;  
		    Long hour = (ms - day * dd) / hh;  
		    Long minute = (ms - day * dd - hour * hh) / mi;  
		    StringBuffer sb = new StringBuffer();  
		    if(day > 0) {  
		        sb.append(day+"天");  
		    }  
		    if(hour > 0) {  
		        sb.append(hour+"小时");  
		    }  
		    if(minute > 0) {  
		        sb.append(minute+"分");  
		    }
		    return sb.toString();  
		}  
		
		/**
		 * 判断数组是否包含该数字
		 * @param numbers 数组
		 * @param value   判断数字
		 *  true 包含 false不包含
		 */
		public static boolean isContains(int [] numbers,int value){
			boolean flag = false;
			OUT:
			for(int i : numbers){
				if(i == value){
					flag = true;
					break OUT;
				}
			}
			return flag;
		}
		/**
		 * 
		 * 判断该网点是否为父级网点
		 * @param orgId
		 */
		public static boolean isParentOrg(Long orgId) {
			Organization on = OraganizationCacheDataUtil.getOrganization(orgId);
			if(on != null){
				if(on.getParentOrgId() == EnumConsts.ROOT_ORG_ID){
					return true;
				}
			}
			 
			return false;
		}
		/**
		 * 金额分转元 并保留几位小数 的Double类型数据
		 */
		public static Double getDoubleFormatLongMoney(Long balance, int bl) {
			    if(balance == null){
			    	return null;
			    }
			    if(balance.longValue() == 0 ){
			    	return 0.0;
			    }
				Double money = ((double)balance)/100;
				BigDecimal bg = new BigDecimal(money);  
				double re = bg.setScale(bl, BigDecimal.ROUND_HALF_UP).doubleValue();  
				return re;
		}
		/**
		 * 乘法
		 */
		public static long multiply(double x,int y){
			double result;
			BigDecimal aa = new BigDecimal(x);
			BigDecimal bb = new BigDecimal(y);
			result = aa.multiply(bb).doubleValue();
			return Math.round(result);
		}
//		public static void main(String[] args) {
//			System.out.println(getOrderNO());
//		}
		/***
		 * 判断是否是费用字段
		 * @author qlfeng
		 */
		public static boolean freeType(String fee){
			String[] d = new String[] { "freight", "pickingCosts", "handingCosts",
					"packingCosts", "deliveryCosts", "cashPayment",
					"freightCollect", "receiptPayment", "monthlyPayment",
					"discount", "pushMoney", "collectingMoney", "procedureFee",
					"offer", "upstairsFee", "transPayment", "installCosts",
					"cashMoney", "cashMoney2", "goodsPrice", "totalFee",
					"stevedoring", " stevedoringPayDot","volumeUnit","weightUnit",
					"advanceMoney","actualBillCosts" };
			for(String str : d){
				if(str.equals(fee)){
					return true;
				}
			}
			return false;
		} 
		/***
		 * 路由转换名称
		 * @param rountOrgId
		 * @return qlfeng
		 */
		public static String getRountOrgIdbyName(String rountOrgId){
			String rountOrgIdName = "";
			if(StringUtils.isEmpty(rountOrgId)){
				return "";
			}
			rountOrgIdName = rountOrgId.replaceAll("\\[","");
			rountOrgIdName = rountOrgIdName.replaceAll("\\]","");
			String[] arr = rountOrgIdName.split("-");
			String orgName = "";
			if(arr != null && arr.length > 0){
				for(int i = 0;i<arr.length;i++){
					if(i == 0){
						orgName = OraganizationCacheDataUtil.getOrgName(Long.valueOf(arr[i]));
					}else{
						orgName =orgName + "-"+ OraganizationCacheDataUtil.getOrgName(Long.valueOf(arr[i]));
					}
				}
			}
			return orgName;
		}
		/**
		 * 所有类型日记电话的截取方式
		 * @param opContent
		 * @return
		 * @author qlfeng
		 */
		public static Map<String,String> getOrdOpLogByPhone(String opContent){
			Map<String,String> map = new HashMap<String, String>();
			String text1 = "";
		     String text2 = "";
		     String tel = "";
		     //师傅电话
		     String phone = "";
		     //只有客服电话的情况下 -- 成立
		     if(opContent.indexOf("电话：[") > 0 &&  opContent.indexOf(",备") < 0 && opContent.indexOf("联系电话：") < 0){
		    	 text1 = opContent.substring(0,opContent.indexOf("：[")+1);
		    	//客服电话
		    	 tel = opContent.substring(opContent.indexOf("：[")+2,opContent.length()-1);
		    	 if(StringUtils.isEmpty(tel)){
		    		 tel="";
		    	 }
		     }else if(opContent.indexOf("联系电话：") > 0 && opContent.indexOf("客服电话：[") > 0){
		    	 //派送中,派送人：小周,联系电话：13800138777,请保持通话畅通!,客服电话：[13800138006] -- 成立
		    	 text1 = opContent.substring(0,opContent.indexOf("电话：")+3);
		    	 phone = opContent.substring(opContent.indexOf("电话：")+3,opContent.indexOf(",请"));
		    	 text2 = opContent.substring(opContent.indexOf(",请"),opContent.indexOf("：[")+1);
		    	 tel = opContent.substring(opContent.indexOf("：[")+2,opContent.length()-1);
		    	 if(StringUtils.isEmpty(phone)){
			    	 phone =""; 
			     }
		    	 if(StringUtils.isEmpty(tel)){
		    		 tel="";
		    	 }
		     }else if(opContent.indexOf("客服电话：[") > 0 && opContent.indexOf(",备") > 0){
		    	 //有客服电话和备注的情况下   -- 成立
		    	 text1 = opContent.substring(0,opContent.indexOf("：[")+1);
		    	 tel = opContent.substring(opContent.indexOf("：[")+2,opContent.indexOf("],备"));
		    	 text2 = opContent.substring(opContent.indexOf(",备"),opContent.length());
		    	 if(StringUtils.isEmpty(tel)){
		    		 tel="";
		    	 }
		     }else if(opContent.indexOf("提货电话[") > 0){
		    	 //从["+beginOrg+"]网点，中转到["+endOrgName+"]网点。提货电话["+ (csPhones == null ? "" : csPhones)+"]
		    	 text1 = opContent.substring(0,opContent.indexOf("提货电话["));
		    	 phone = opContent.substring(opContent.indexOf("提货电话[")+5,opContent.length()-1);
		    	 if(StringUtils.isEmpty(phone)){
			    	 phone =""; 
		    	 }
		     }else if (opContent.indexOf("手机：") > 0){// 预约，预约上门时间：2016-10-26 下午,师傅联系手机：15350345358 --成立
		    	 text1 = opContent.substring(0,opContent.indexOf("手机：")+3);
		    	 phone = opContent.substring(opContent.indexOf("机：")+2,opContent.length());
		    	 if(StringUtils.isEmpty(phone)){
			    	 phone =""; 
		    	 }
		     }else if(opContent.indexOf("司机电话:") > 0 && opContent.indexOf("师傅电话:") > 0){
		    	 //【配安任务】送货上门，车牌:粤A6QU54，司机:何中远，司机电话:13728435595，师傅:何忠远，师傅电话:13728435595--成立
		    	 text1 = opContent.substring(0,opContent.indexOf("司机电话:")+5);
		    	 phone = opContent.substring(opContent.indexOf("司机电话:")+5,opContent.indexOf("，师傅"));
		    	 text2 = opContent.substring(opContent.indexOf("，师傅"),opContent.indexOf("师傅电话:")+5);
		    	 tel = opContent.substring(opContent.indexOf("师傅电话:")+5,opContent.length());
		    	 if(StringUtils.isEmpty(phone)){
			    	 phone =""; 
			     }
		    	 if(StringUtils.isEmpty(tel)){
		    		 tel="";
		    	 }
		     }else{
		    	 text1 = opContent;
		     }
		     //情况一：text1
		     //情况二：text1 + phone
		     //情况二：text1+phone+text2+tel 
		     opContent = text1+tel +text2+phone;
		     map.put("text1", text1);
		     map.put("text2", text2);
		     map.put("tel", tel);
		     map.put("phone", phone);
			return map;
		}
		/**
		 * double乘于100转换Long类型
		 * @param str
		 * @return qlfeng
		 */
		public static Long getStringByLong(String str){
			long type = 0L;
			if(StringUtils.isNotEmpty(str) && isNumber(str) && !(Double.valueOf(str) <= 0)){
				try{
					double dd = Double.valueOf(str)* 100;
					DecimalFormat  df = new DecimalFormat("######0");
					type  = Long.valueOf(df.format(dd));
				}catch(Exception e){
					throw new BusinessException("你输入的金额有误！");
				}
			}
			return type;
		}
		/**
		 * 城市名称获取id 
		 * @param pName
		 * @param cName 
		 * @param dName
		 * @author qlfeng
		 */
		public static Long getCityId(String pName,String cName,String dName){
			Long name = -1L;
			if(StringUtils.isNotEmpty(pName)){
				List<Province> p = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE");
				for(Province temp : p){
					if(pName.equals(temp.getName())){
						name = temp.getId();
					}else if(pName.indexOf("省") != -1 && temp.getName().equals(pName.substring(0, pName.indexOf("省")))){
						name = temp.getId();
					}
				}
			}
			if(StringUtils.isNotEmpty(cName)){
				List<City> p = SysStaticDataUtil.getCityDataList("SYS_CITY");
				for(City temp : p){
					if(cName.equals(temp.getName())){
						name = temp.getId();
					}else if(temp.getName().equals(cName+"市")){
						name = temp.getId();
					}
				}
			}
			if(StringUtils.isNotEmpty(dName)){
				List<District> p = SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT");
				for(District temp : p){
					if(dName.equals(temp.getName())){
						name = temp.getId();
					}else if(temp.getName().equals(dName+"区")){
						name = temp.getId();
					}
				}
			}
			return name;
		}
        /**
         * 获取展示名称
         * 1：未结 2、部分核销、3已结
         */   
		public static String getTranferFalgName(Long cashMoney,
				Long cashMoney2, Integer paymentType, Integer paymentType2,
				Object object ) {
			List<Integer> lists = new ArrayList<Integer>();
			if(object != null){
				String [] ss = (object+"").split(",");
				for(String s : ss){
					lists.add(Integer.valueOf(s));
				}

			}
			Integer value = getTranferFalg(cashMoney, cashMoney2, paymentType, paymentType2, lists); 
			if(value == 1){
				return  "运费未结";
			}else if(value == 2){
				return  "部分核销";
			}else if(value == 3){
				return  "运费已结";
			}else {
				return "";
			}
					
			
		}
        /**
         * 获取展示枚举
         * 1：未结 2、部分核销、3已结  (0 不展示)
         */  		
		public static Integer getTranferFalg(Long cashMoney,
				Long cashMoney2, Integer paymentType, Integer paymentType2,
				List<Integer> lists) {
			Integer value = 0;
			//如果免费补发不展示
	        if(paymentType != null&& paymentType == 7){
	        	return 0;
	        }
	        //如果免费补发不展示
	        if(paymentType2 != null && paymentType2 == 7){
	        	return 0;
	        }
	        
			//获取核销科目(如果科目费用为0 的支付方式不展示)
			int size = 0;
			if(paymentType != null && paymentType != 7 && paymentType != -1 && cashMoney != null && cashMoney > 0 ){
				size = size + 1;
			}
	        if(paymentType2 != null && paymentType2 != 7 && paymentType2 != -1 && cashMoney2 != null && cashMoney2 > 0){
	        	size = size + 1;
			}
	        if(size == 0){
	        	//如果付款方式为0，不展示
	        	return 0;
	        }
	                
	        List<Integer> c1 = new ArrayList<Integer>();
			List<Integer> c2 = new ArrayList<Integer>();
			boolean isPart = false;
			OUT:
			for(Integer s : lists){
				if(s == AccountManageConsts.AcCashProve.CHECK_STS_NON){
					c2.add(s);
				}else{
					c1.add(s);
				}
				if(s == AccountManageConsts.AcCashProve.CHECK_STS_OK_PART){
					isPart = true;
					continue OUT;
				}
			}
			//部分核销
			if(isPart){
				value =  2;
			}else{
				if(lists.size() == size){
					//核销明细都存在
					if(c1.size() ==  size ){
						value =  3;
					}else if(c2.size() == size){
						value = 1;
					}else{
						value =  2;
					}
				}else{
					//核销明细未生成全部
					if(c1.size() > 0){
						value =  2;
					}else{
						value =  1;
					}
				}

			}
			return value;
		}
		
		/**
		 * 根据当前的网点获取 客服电话 （支持2个）
		 * 格式 1个 020-88888888
		 * 格式 2个 020-88888888；020-88888887
		 * @param 当前登录的网点组织 可能存在2个客服号码
		 */
		public static String getOrgSupportStaffPhone(){
			BaseUser user = SysContexts.getCurrentOperator();
			long orgId = user.getOrgId();
			Organization organ =  OraganizationCacheDataUtil.getOrganization(orgId);
			String orgPhone = organ.getSupportStaffPhone() == null ? "" :organ.getSupportStaffPhone();
			if(StringUtils.isEmpty(orgPhone)){
				 orgPhone = StringUtils.isNotEmpty(organ.getSupportStaffPhone2()) ? organ.getSupportStaffPhone2() : "";
			}else{
				orgPhone = orgPhone + (StringUtils.isNotEmpty(organ.getSupportStaffPhone2()) ? "；"+organ.getSupportStaffPhone2() : "");
			}
			return orgPhone;
		}
		/**
		 * 根据当前的网点获取 客服电话 （支持2个）
		 * 格式 1个 020-88888888
		 * 格式 2个 020-88888888；020-88888887
		 * @param 当前登录的网点组织 可能存在2个客服号码
		 */
		public static String getOrgSupportStaffPhone(long orgId){
			Organization organ =  OraganizationCacheDataUtil.getOrganization(orgId);
			String orgPhone = organ.getSupportStaffPhone() == null ? "" :organ.getSupportStaffPhone();
			if(StringUtils.isEmpty(orgPhone)){
				 orgPhone = StringUtils.isNotEmpty(organ.getSupportStaffPhone2()) ? organ.getSupportStaffPhone2() : "";
			}else{
				orgPhone = orgPhone + (StringUtils.isNotEmpty(organ.getSupportStaffPhone2()) ? "；"+organ.getSupportStaffPhone2() : "");
			}
			return orgPhone;
		}
		/**
		 * 对数组进行去重
		 * @param str
		 * @return List<String>
		 */
		public static List<String> arraysByRepeat(String[] str){
			for (int i = 0; i < str.length; i++) {
				for (int j = i; j < str.length - 1; j++) {
					if (str[i].equals(str[j + 1])) {
						str[j + 1] = "";
					}
				}
			}
			List<String> numList = new ArrayList<String>();
			for(String temp : str){
				if(StringUtils.isNotEmpty(temp)){
					numList.add(temp); 
				}
			}
			return numList;
		}
		public static String timeChina(String time){
			
			if(time.indexOf("年") != -1){
				time = time.replace("年", "-");
			}
			if(time.indexOf("月") != -1){
				time = time.replace("月", "-");
			}
			if(time.indexOf("日") != -1){
				time = time.replace("日", "");
			}
			return time;
		}
		
		
		/**
		 * 检查验证码
		 * @param billId
		 * @param code
		 * @param flag
		 * @return
		 * @throws Exception
		 */
		public static boolean checkCode(String billId,String code,String businessType)throws Exception{
			String randomCode = "";
			boolean isOk=false;
			randomCode=RemoteCacheUtil.get(businessType + billId);
			
	    	if(code!=null && code.equals(randomCode)){
	    		isOk=true;
	    		RemoteCacheUtil.del(businessType + billId);
	    	}
	    	return isOk;
		}
		
		
		/***
		 * 生成交易单号
		 * */
		public static String getOrderNO() {
			String yyyyMMdd= EnumConstsYQ.ORDER_NO_REDIS.ORDER_NO_TYPE + DateUtil.formatDate(new Date(), DateUtil.DATE_FORMAT2);
			String cacheValue = RemoteCacheUtil.get(yyyyMMdd);
			String yyyyMMddReplace = yyyyMMdd.replace(EnumConstsYQ.ORDER_NO_REDIS.ORDER_NO_TYPE, "");
			//cacheValue = cacheValue.replace(EnumConstsYQ.ORDER_NO_REDIS.ORDER_NO_TYPE, "");
			String orderNo="";
			if(StringUtils.isEmpty(cacheValue)){
				RemoteCacheUtil.set(yyyyMMdd, "2");
				orderNo=yyyyMMddReplace+"0001";
			}else{
				RemoteCacheUtil.incr(yyyyMMdd);
				orderNo=yyyyMMddReplace;
				for (int i=0;i<4-cacheValue.length();i++) {
					orderNo=orderNo+"0";
				}
				orderNo=orderNo+cacheValue;
			}
			return orderNo;
		}
		
		public static void main(String[] args) {
			System.out.println(getOrderNO());
		}
		
		////需要注意的是：月份是从0开始的，比如说如果输入5的话，实际上显示的是4月份的最后一天
		public static String getLastDayOfMonth(int year, int month) {     
	         Calendar cal = Calendar.getInstance();     
	         cal.set(Calendar.YEAR, year);     
	         cal.set(Calendar.MONTH, month);     
	         cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
	        return  new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());  
	     }		
		public static String getFirstDayOfMonth(int year, int month) {     
	         Calendar cal = Calendar.getInstance();     
	         cal.set(Calendar.YEAR, year);     
	         cal.set(Calendar.MONTH, month);  
	         cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));  
	        return   new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());  
	     }  
		
		
		 public static boolean containsEmoji(String source) {
		        int len = source.length();
		        boolean isEmoji = false;
		        for (int i = 0; i < len; i++) {
		            char hs = source.charAt(i);
		            if (0xd800 <= hs && hs <= 0xdbff) {
		                if (source.length() > 1) {
		                    char ls = source.charAt(i + 1);
		                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
		                    if (0x1d000 <= uc && uc <= 0x1f77f) {
		                        return true;
		                    }
		                }
		            } else {
		                // non surrogate
		                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
		                    return true;
		                } else if (0x2B05 <= hs && hs <= 0x2b07) {
		                    return true;
		                } else if (0x2934 <= hs && hs <= 0x2935) {
		                    return true;
		                } else if (0x3297 <= hs && hs <= 0x3299) {
		                    return true;
		                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
		                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
		                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
		                    return true;
		                }
		                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
		                    char ls = source.charAt(i + 1);
		                    if (ls == 0x20e3) {
		                        return true;
		                    }
		                }
		            }
		        }
		        return isEmoji;
		    }
		 /**
		  * string[]转long[]
		  * @param stringArray
		  * @return
		  */
		 public static Long[] stringToLong(String stringArray[]) {
				if (stringArray == null || stringArray.length < 1) {
					return null;
				}
				Long longArray[] = new Long[stringArray.length];
				for (int i = 0; i < longArray.length; i++) {
					try {
						longArray[i] = Long.valueOf(stringArray[i]);
					} catch (NumberFormatException e) {
						longArray[i] = 0L;
						continue;
					}
				}
				return longArray;
			}
		 /**
		  * 字符串转double
		  * @param num
		  * @return
		  */
		 public static double getDoubleByString(String num){
			 if(isNumber(num)){
				 BigDecimal bg = new BigDecimal(num);  
			     return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//首重
			 }else{
				 return 0;
			 }
		 }
	/**
	 * 拉包工工号输出
	 * @param jobNumber
	 * @param city
	 * @return
	 */
	 public static String jobNumberRepByCity(String jobNumber,String city){
		 if(StringUtils.isNotEmpty(jobNumber)){
			 jobNumber = jobNumber.replaceAll(city, "");
		 }
		 return jobNumber;
	 }
	 /**
	  * 获取默认最大接单数！
	  */
	 public static long getDefaultSingularNum(long tenantId){
		 List<SysStaticData> list = SysStaticDataUtil.getSysStaticDataList(tenantId, "DEFAULT_SINGULAR_NUM");
		 if(list!=null&&list.size()>0){
			 return Long.valueOf(list.get(0).getCodeValue());
		 }
		 return 3;
	 }
	 
	 /**
	  * 获取统计条件查询
	  */
	 public static Map<String,Object> getSelectOrderCount(String merchanOrDistri){
		
		BaseUser user = SysContexts.getCurrentOperator();
		long tenantId = -1;
		if (user.getTenantId() != null && user.getTenantId() > 0) {
			tenantId = user.getTenantId();
		}
		String userType = String.valueOf(user.getUserType());
		if (user.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION) {
			userType = userType + merchanOrDistri;
		}
		List<SysStaticData> list = SysStaticDataUtil.getSysStaticDataList(tenantId, "ORDER_COUNT_SELECT");
		List<SysStaticData> listSelect = SysStaticDataUtil.getSysStaticDataList(tenantId, "ORDER_COUNT_SELECT_TYPE");
		List<OrdersSelectOut> listOut = new ArrayList<OrdersSelectOut>();
		if (list != null && list.size() > 0) {
			for (SysStaticData sysStaticData : list) {
				String[] codeTypeAlias = sysStaticData.getCodeTypeAlias().split(",");
				if(Arrays.asList(codeTypeAlias).contains(userType)){
					OrdersSelectOut out = new OrdersSelectOut();
					out.setCodeName(sysStaticData.getCodeName());
					out.setCodeValue(sysStaticData.getCodeValue());
					out.setType(sysStaticData.getCodeDesc());
					List<Map<String,String>> listType = new ArrayList<Map<String,String>>();
					if (listSelect!=null&&listSelect.size()>0) {
						for (SysStaticData temp : listSelect) {
							if (temp.getCodeTypeAlias().equals(sysStaticData.getCodeValue())) {
								Map<String,String> mapType = new HashMap<String, String>();
								mapType.put("selectName", temp.getCodeName());
								mapType.put("selectValue", temp.getCodeValue());
								listType.add(mapType);
							}
						}
					}
					out.setList(listType);
					listOut.add(out);
				}
			}
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(listOut));
	 }
	 /**
	  * 获取TMS租户
	  * @param tms
	  * @return
	  */
	 public static long tmsTenantId(String tms){
		 long tenantId = -1;
		List<SysStaticData> listOut =SysStaticDataUtil.getSysStaticDataList("TMS_TENANT_YQ");
		if (listOut!=null&&listOut.size()>0) {
			for (SysStaticData sysStaticData : listOut) {
				if (tms.equals(sysStaticData.getCodeTypeAlias())) {
					tenantId = Long.valueOf(sysStaticData.getCodeValue());
				}
			}
		}
		return tenantId;
	}
}
