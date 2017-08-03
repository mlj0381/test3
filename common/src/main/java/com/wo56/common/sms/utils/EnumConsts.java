package com.wo56.common.sms.utils;

public class EnumConsts {

	/**
	 * 短息redis key
	 * @author zhouchao
	 *
	 */
	public static class RemoteCache{
		public static final String SMS_MD5 = "SMS_MD5";
	}
	
	/**短信类型**/
	public static class SmsType{
		/**活动公告 1**/
		public static final long NOTICE_TYPE=1;
		/**进度通知 2**/
		public static final long PROGRESS_TYPE=2;
		/**操作确认 3**/
		public static final long OPERATION_CONFIRMATION_TYPE=3;
		/**验证确认 4**/
		public static final long VALIDATE_TYPE=4;
	}
	
	/**
	 * 短信配置
	 * @author zhouchao
	 *
	 */
	public static class SysCfg{
		/**发送短信进程数**/
		public static final String SMS_SEND_THREAD_COUNT = "SMS_SEND_THREAD_COUNT";
		/**是否发送短信全局变量*/
		public static final String IS_SEND_MEG = "IS_SEND_MEG";
		/**短信发送方式*/
		public static final String SMS_SEND_STYLE = "SMS_SEND_STYLE";
		
		public static final String SYS_IOS_APPID = "SYS_IOS_APPID";
		public static final String SYS_IOS_APIKEY = "SYS_IOS_APIKEY";
		public static final String SYS_IOS_SECRETKEY = "SYS_IOS_SECRETKEY";
		
		public static final String SYS_ANDROID_APPID = "SYS_ANDROID_APPID";
		public static final String SYS_ANDROID_APIKEY = "SYS_ANDROID_APIKEY";
		public static final String SYS_ANDROID_SECRETKEY = "SYS_ANDROID_SECRETKEY";
	}
	
	/**
	 * sysTenantExtend表 EKey枚举值－表示不同第三方短信平台
	 * @author zhouchao
	 *
	 */
	public static class SysSmsPlatformKey{
		public static final String PLATFORM = "PLATFORM";
	}
	
	/**
	 * 指定平台
	 * @author zhouchao
	 *
	 */
	public static class SysSmsPlatformValue{
		/**默认－已不使用*/
		public static final int SMS_DEFAULT = 1;
		/**E讯通*/
		public static final int SMS_EXT = 2;
		/**创蓝*/
		public static final int SMS_CL = 3;
		/**玄武*/
		public static final int SWITCH_XUANWU = 4;
		
	}
	
	/**
	 * 租户控制-key
	 * @author zhouchao
	 *
	 */
	public static class SysTenantControllerKey{
		/**是否发送短信*/
		public static final String SMS_IS_SEND = "SMS_IS_SEND";
	}
	
	/**
	 * 租户控制－value
	 * @author zhouchao
	 *
	 */
	public static class SysTenantControllerValue{
		/**发送短信*/
		public static final String SMS_IS_SEND_SEND = "1";
	}
	
	
	/**
	 * 短信控制类型 sms_controller
	 * @author zhouchao
	 *
	 */
	public static class SmsControllerSmsType{
		public static final int USER = 1 ;
		public static final int TEMPLATE = 2 ;
		public static final int TENANT = 3 ;
	}
	
	
	/**
	 * 短信类型
	 * @author zhouchao
	 *
	 */
	public static class SMS_TYPE{
		/**系统短信*/
		public static final int SMS_SYSTEM = 0;
	}
	
	/**
	 * 短信模版
	 * @author zhouchao
	 *
	 */
	public static class TEMPLATE_ID{
		/**空内容，系统短信*/
		public static final long TEMP_SYSTEM = 0L;
	}
	
	/**
	 * 业务
	 * @author zhouchao
	 *
	 */
	public static class OBJ_TYPE{
		/**系统短信*/
		public static final String SYSTEM = "0";
	}
	
	/**
	 * 业务id
	 * @author zhouchao
	 *
	 */
	public static class OBJ_ID{
		public static final long SYSTEM = 0;
	}
	
	/**
	 * 短信控制表 sms_type类型
	 * @author zhouchao
	 *
	 */
	public static class SMS_CONTRLLER_SMS_TYPE{
		/**
		 * 用户不接收短信
		 */
		public static final int C_USER = 1;//
		/**
		 * 用户不接收某个模版短信
		 */
		public static final int C_USER_TEMPLATE = 2;//
		/**
		 * 用户不接收租户短信
		 */
		public static final int C_USER_TENANT = 3;//
		/**
		 * 租户不发送特定模版短信
		 */
		public static final int C_TENANT_TEMPLATE = 4;
		
	}
}
