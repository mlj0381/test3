package com.wo56.common.sms.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.core.cache.CacheFactory;
import com.wo56.common.sms.cache.SmsControllerCache;
import com.wo56.common.sms.vo.SmsController;

public class SmsControllerCacheUtil {

	private static transient Log log = LogFactory.getLog(SmsControllerCacheUtil.class);
	
	/**
	 * 获取数据列表 key--> SysSmsPlatform
	 * @param 
	 * @return List-》
	 */
	public static List<SmsController> getSmsControllerList(){
			List<SmsController> smsControllerList = (List<SmsController>)CacheFactory.get(SmsControllerCache.class, "SYS_SMS_CONTROLLER");
			return smsControllerList;
	}
	
	/**
	 * 用户是否发送短信
	 * @param billId
	 * @return true 发送   false 不发送
	 */
	public static boolean  cUserIsSend(String billId){
		List<SmsController> allList = getSmsControllerList();
		if(StringUtils.isNotBlank(billId)){
			if(allList != null){
				for(SmsController smsController : allList){
					if(null != smsController.getSmsType() && smsController.getSmsType() == EnumConsts.SMS_CONTRLLER_SMS_TYPE.C_USER){
						if(StringUtils.isNotBlank(smsController.getBillId()) && smsController.getBillId().equals(billId)){
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 用户是否接收指定模版短信
	 * @param billId
	 * @param templateId
	 * @return false 不接收  true 接收
	 */
	public static boolean cUserTemplate(String billId,long templateId){
		List<SmsController> allList = getSmsControllerList();
		if(StringUtils.isNotBlank(billId)){
			if(allList != null){
				for(SmsController smsController : allList){
					if(null != smsController.getSmsType()){
						//全局不接收短信
						if(smsController.getSmsType() == EnumConsts.SMS_CONTRLLER_SMS_TYPE.C_USER){
							if(StringUtils.isNotBlank(smsController.getBillId()) && smsController.getBillId().equals(billId)){
								return false;
							}
						//该用户不接收特定模版短信
						}else if(smsController.getSmsType() == EnumConsts.SMS_CONTRLLER_SMS_TYPE.C_USER_TEMPLATE){
							if(StringUtils.isNotBlank(smsController.getBillId()) && smsController.getBillId().equals(billId) && null!=smsController.getTemplateId() && templateId == smsController.getTemplateId().longValue()){
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 用户是否接收特定租户的短信
	 * @param billId
	 * @param tenantId
	 * @return true 接收 false 不接收
	 */
	public static boolean cUserTenantId(String billId,long tenantId){
		List<SmsController> allList = getSmsControllerList();
		if(StringUtils.isNotBlank(billId)){
			if(allList != null){
				for(SmsController smsController : allList){
					if(null != smsController.getSmsType()){
						//全局不接收短信
						if(smsController.getSmsType() == EnumConsts.SMS_CONTRLLER_SMS_TYPE.C_USER){
							if(StringUtils.isNotBlank(smsController.getBillId()) && smsController.getBillId().equals(billId)){
								return false;
							}
						//该用户不接收特定租户短信
						}else if(smsController.getSmsType() == EnumConsts.SMS_CONTRLLER_SMS_TYPE.C_USER_TENANT){
							if(StringUtils.isNotBlank(smsController.getBillId()) && smsController.getBillId().equals(billId) && null!=smsController.getTenantId() && tenantId == smsController.getTenantId().longValue()){
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 该租户是否发送特定模版短信
	 * @param template
	 * @param tenantId
	 * @return true 发送 false 不发送
	 */
	public static boolean cTenantIdTemplate(long template,long tenantId){
		List<SmsController> allList = getSmsControllerList();
		if(allList != null){
			for(SmsController smsController : allList){
				if(null != smsController.getSmsType()){
					if(smsController.getSmsType() == EnumConsts.SMS_CONTRLLER_SMS_TYPE.C_TENANT_TEMPLATE){
						if(null != smsController.getTemplateId() && template == smsController.getTemplateId().longValue() && null != smsController.getTenantId() && smsController.getTenantId().longValue() == tenantId){
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 是否发送短信
	 * @param billId
	 * @param template
	 * @param tenantId
	 * @return true 发送 false 不发送
	 */
	public static boolean isSend(String billId,long templateId,long tenantId){
		List<SmsController> allList = getSmsControllerList();
		if(allList != null){
			for(SmsController smsController : allList){
				if(null != smsController.getSmsType() && StringUtils.isNotBlank(billId)){
					//全局不接收短信
					if(smsController.getSmsType() == EnumConsts.SMS_CONTRLLER_SMS_TYPE.C_USER){
						if(StringUtils.isNotBlank(smsController.getBillId()) && smsController.getBillId().equals(billId)){
							if(log.isDebugEnabled()){
								log.debug("手机号码["+billId+"]不接收短信");
							}
							return false;
						}
					//该用户不接收特定租户短信
					}else if(smsController.getSmsType() == EnumConsts.SMS_CONTRLLER_SMS_TYPE.C_USER_TENANT){
						if(StringUtils.isNotBlank(smsController.getBillId()) && smsController.getBillId().equals(billId) && null!=smsController.getTenantId() && tenantId == smsController.getTenantId().longValue()){
							if(log.isDebugEnabled()){
								log.debug("手机号码["+billId+"]不接收该租户["+tenantId+"]短信");
							}
							return false;
						}
					//该用户不接收特定模版
					}else if(smsController.getSmsType() == EnumConsts.SMS_CONTRLLER_SMS_TYPE.C_USER_TEMPLATE){
						if(StringUtils.isNotBlank(smsController.getBillId()) && smsController.getBillId().equals(billId) && null!=smsController.getTemplateId() && templateId == smsController.getTemplateId().longValue()){
							if(log.isDebugEnabled()){
								log.debug("手机号码["+billId+"]不接收该模版["+templateId+"]短信");
							}
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}
