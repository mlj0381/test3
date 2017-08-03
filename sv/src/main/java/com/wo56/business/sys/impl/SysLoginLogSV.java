package com.wo56.business.sys.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.util.DateUtil;
import com.wo56.business.cm.intf.CmUserInfoTF;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.sys.vo.SysLoginLog;

public class SysLoginLogSV {
	private static transient Log logger = LogFactory.getLog(SysLoginLogSV.class);
	private static String AppAndroid="APP-ANDROID";
	private static String AppIos="APP-IOS";
	//用户登出
	public void userLoginOut(String  channelType,String appVerNo,String appOsVer,
			   String mobileType,String mobileBrand,String toolType,String toolVer){
		userLogin( 2, channelType, appVerNo, appOsVer, mobileType, mobileBrand, toolType, toolVer);
	}
	public void userLogins(String  channelType,String appVerNo,String appOsVer,
			   String mobileType,String mobileBrand,String toolType,String toolVer){
		userLogin(1, channelType, appVerNo, appOsVer, mobileType, mobileBrand, toolType, toolVer);
	}
	public void userLogins(int loginType,String  channelType,String appVerNo,String appOsVer,
			   String mobileType,String mobileBrand,String toolType,String toolVer){
		userLogin(loginType, channelType, appVerNo, appOsVer, mobileType, mobileBrand, toolType, toolVer);
	}
	
	public void getUserLoginOrLoginOut(int loginType,String  channelType,String appVerNo,String appOsVer,
			   String mobileType,String mobileBrand,String toolType,String toolVer,long userId,String billId){
		userLoginLog(loginType, channelType, appVerNo, appOsVer, mobileType, mobileBrand, toolType, toolVer,userId,billId);
	}
	
	/**
	 * 用户登录
	 */
   private void userLogin(int loginType,String  channelType,String appVerNo,String appOsVer,
		   String mobileType,String mobileBrand,String toolType,String toolVer){
	   try{
		   /*SysOperator sysOper  = null;
			if(channelType.equals(AppAndroid) || channelType.equals(AppIos)){
				Session session = SysContexts.getEntityManager();
				Criteria ca = session.createCriteria(SysOperator.class);
				ca.add(Restrictions.eq("userId", SysContexts.getCurrentOperator().getUserId()));
				if(ca.list().size()>0){
					sysOper=(SysOperator)ca.list().get(0);
				}
			}*/
		   	CmUserInfoTF userInfoTF = (CmUserInfoTF) SysContexts.getBean("cmUserInfoTF");
		   	CmUserInfo user = userInfoTF.getUserInfo(SysContexts.getCurrentOperator().getUserId(), 0);
			SysLoginLog sysLogin = new SysLoginLog();
			if(user!=null){
				long userId =  user.getUserId();
				 String billId = user.getLoginAcct();
				 if(userId>0){
						sysLogin.setUserId(userId);
					}
				 if(billId!=null && StringUtils.isNotEmpty(billId)){
						sysLogin.setBillId(billId);
					}
			}
		    Map<String, Object> map =  new HashMap<String, Object>();
			map.put("yyyyMM", new String[] { DateUtil.formatDate(new Date(),DateUtil.YEAR_MONTH_FORMAT2) });
			Session session = SysContexts.getEntityManager(map);
			
			
			if(channelType!=null && StringUtils.isNotEmpty(channelType) && channelType.equals(AppIos)){
				sysLogin.setChannelType(2);
			}else if(channelType!=null && StringUtils.isNotEmpty(channelType) && channelType.equals(AppAndroid)){
				sysLogin.setChannelType(1);
			}else{
					sysLogin.setChannelType(3);
			}
			Timestamp time = new Timestamp(System.currentTimeMillis()); 
			sysLogin.setOpDate(time);
			sysLogin.setLoginType(loginType);
			if(appVerNo!=null && StringUtils.isNotEmpty(appVerNo)){
				sysLogin.setAppVerNo(appVerNo);
			}
			if(appOsVer!=null   && StringUtils.isNotEmpty(appOsVer)){
				sysLogin.setAppOsVer(appOsVer);
			}
			if(mobileType!=null &&  StringUtils.isNotEmpty(mobileType)){
				sysLogin.setMobileType(mobileType);
			}
			if( mobileBrand!=null && StringUtils.isNotEmpty(mobileBrand)){
				sysLogin.setMobileBrand(mobileBrand);
			}
			if(toolType!=null && StringUtils.isNotEmpty(toolType)){
				sysLogin.setToolType(toolType);
			}
			if( toolVer!=null && StringUtils.isNotEmpty(toolVer)){
				sysLogin.setToolVer(toolVer);
			}
			session.save(sysLogin);
	   }catch(Exception e){
		   if(loginType==1){
			   logger.info("登录失败",e );
		   }else if(loginType==2){
			   logger.info("登出失败",e);
		   }
		   
	   }
	   
   }
   
   /**
	 * 用户登录
	 */
  private void userLoginLog(int loginType,String  channelType,String appVerNo,String appOsVer,
		   String mobileType,String mobileBrand,String toolType,String toolVer,long userId,String billId){
	   try{
			SysLoginLog sysLogin = new SysLoginLog();
				 if(userId>0){
						sysLogin.setUserId(userId);
					}
				 if(billId!=null && StringUtils.isNotEmpty(billId)){
						sysLogin.setBillId(billId);
					}
		    Map<String, Object> map =  new HashMap<String, Object>();
			map.put("yyyyMM", new String[] { DateUtil.formatDate(new Date(),DateUtil.YEAR_MONTH_FORMAT2) });
			Session session = SysContexts.getEntityManager(map);
			if(channelType!=null && StringUtils.isNotEmpty(channelType) && channelType.equals(AppIos)){
				sysLogin.setChannelType(2);
			}else if(channelType!=null && StringUtils.isNotEmpty(channelType) && channelType.equals(AppAndroid)){
				sysLogin.setChannelType(1);
			}else{
					sysLogin.setChannelType(3);
			}
			Timestamp time = new Timestamp(System.currentTimeMillis()); 
			sysLogin.setOpDate(time);
			sysLogin.setLoginType(loginType);
			if(appVerNo!=null && StringUtils.isNotEmpty(appVerNo)){
				sysLogin.setAppVerNo(appVerNo);
			}
			if(appOsVer!=null   && StringUtils.isNotEmpty(appOsVer)){
				sysLogin.setAppOsVer(appOsVer);
			}
			if(mobileType!=null &&  StringUtils.isNotEmpty(mobileType)){
				sysLogin.setMobileType(mobileType);
			}
			if( mobileBrand!=null && StringUtils.isNotEmpty(mobileBrand)){
				sysLogin.setMobileBrand(mobileBrand);
			}
			if(toolType!=null && StringUtils.isNotEmpty(toolType)){
				sysLogin.setToolType(toolType);
			}
			if( toolVer!=null && StringUtils.isNotEmpty(toolVer)){
				sysLogin.setToolVer(toolVer);
			}
			session.save(sysLogin);
	   }catch(Exception e){
		   if(loginType==1){
			   logger.info("登录失败",e );
		   }else if(loginType==2){
			   logger.info("登出失败",e);
		   }
		   
	   }
	   
  }
   
   
   
}
