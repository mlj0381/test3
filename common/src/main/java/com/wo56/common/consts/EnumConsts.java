package com.wo56.common.consts;

import com.framework.core.identity.interfaces.ISysLogin;

/**
 * 系统常量定义
 * @author wengxk
 *
 */
public class EnumConsts {

	/**
	 * 远程缓存Key定义
	 * @author wengxk
	 *
	 */   
	public static class RemoteCache {
		/**限制一个用户，一天只能收到多少条短信**/
		public static final String SMS_BILL = "SMS_BILL";
		/**短信黑名单前缀，key值，Map结构，key:手机号码,value:0表示只是接收推送信息，不接收短信，1表示不接受推送，短信**/
		public static final String SMS_BLACKLIST="SMS_BLACKLIST";
		public static final String SYS_INTF_TOKEN_PROFIX = "SYS_TOKEN_";	//接口TOKEN, Key:"SYS_TOKEN_"+uid, value:token
		public static final String SYS_PARTNER_INTF_TOKEN_PROFIX = ISysLogin.SYS_PARTNER_INTF_TOKEN_PROFIX;	//第三方接入接口TOKEN, key: "APP_TOKEN_"+token, value: uId
		public static final String SMS_MD5 = "SMS_MD5";	//短息MD5值
		public static final String SMS_BILL_TEMPLATE="SMS_BILL_TEMPLATE";//限制一个用户，一天只能收到某个模版的短信的条数
		
		public static final String Vehicle_Gps_X_Set = "VGP_X_SHARD_";	//记录纬度分片(key: VGP_X_SHARD_分片值, value: Set[billId])
		public static final String Vehicle_Gps_Y_Set = "VGP_Y_SHARD_";	//记录经度分片(key: VGP_Y_SHARD_分片值, value: Set[billId])
		
		public static final String UPLOAD_LATITUDE  = "UPLOAD_LATITUDE"; // 上传经纬度 rpush/lpop
		
		public static final String BillId_Gps_Position = "VGP_";// 记录车辆当前位置信息(key:VGP_手机,value:经度|纬度|地市city|省份province|区域district)
		
		//---------------云企------------------------------
		public static final String ORDERID_Gps_Position = "ORDERID_Gps_";// 记录订单当前位置信息(key:VGP_手机,value:经度|纬度|地市city|省份province|区域district)
		public static final String ORDERID_Gps_LAT_Set = "ORDERID_Gps_LAT_Set";	//记录订单的纬度的分片
		public static final String ORDERID_Gps_LONG_Set = "ORDERID_Gps_LONG_Set";	//记录订单的经度的分片
		
		
		public static final String USERID_Gps_LAT_Set = "USERID_Gps_LAT_Set";	//记录用户的纬度的分片
		public static final String USERID_Gps_LONG_Set = "USERID_Gps_LONG_Set";	//记录用户的经度的分片
		
		public static final String ORDER_ALL_SET_KEY="ORDER_ALL_SET_KEY";//订单的set key值数据
		public static final String ORDER_ALL_HASH_KEY="ORDER_ALL_HASH_KEY";//订单的hash key值数据
		
		public static final String USER_ALL_SET_KEY="USER_ALL_SET_KEY";//拉包工的set key值数据
		public static final String USER_ALL_HASH_KEY="USER_ALL_HASH_KEY";//拉包工的hash key值数据
		
		
		public static final String MULT_ORDER_ALL_SET_KEY="MULT_ORDER_ALL_SET_KEY";//待多次推送订单的set key值数据
		public static final String MULT_ORDER_ALL_HASH_KEY="MULT_ORDER_ALL_HASH_KEY";//待多次推送的订单的hash key值数据
		
	}
	
	/**
	 * 枚举数据常量定义
	 * @author wengxk
	 *
	 */
	public static class SysStaticData {
		/**客户信息类型**/
		public static final String CUSTOMER_TYPE = "CUSTOMER_TYPE";
		/**订单状态**/
		public static final String ORDER_STATE = "ORDER_STATE";
		/**货物类型**/
		public static final String GOODS_TYPE = "GOODS_TYPE";
		/**订单操作类型**/
		public static final String OP_TYPE = "OP_TYPE";
		/**包装类型**/
		public static final String PACKING_TYPE = "PACKING_TYPE";
		/**计费方式**/
		public static final String BILLING_TYPE = "BILLING_TYPE";
		/**注意事项**/
		public static final String NOTES = "NOTES";
		/**运输方式**/
		public static final String TRANSPORT_TYPE = "AC_ROUTE_FEE_CONFIG@CARRIAGE_MODE";
		/**支付方式*/
		public static final String PAYMENT_TYPE = "PAYMENT_TYPE";
		/**交接方式*/
		public static final String DELIVERY_TYPE = "DELIVERY_TYPE";
		/**查询方式*/
		public static final String QUERY_TYPE = "QUERY_TYPE";
		/**车辆状态*/
		public static final String VEHICLE_STATE = "VEHICLE_STATE";
		/**是否需要安装车辆*/
		public static final String IS_INSTALL = "IS_INSTALL";
		/**问题状态*/
		public static final String AUDIT_STATE = "AUDIT_STATE";
		/**异常状态*/
		public static final String EXCEPTION_STATE = "EXCEPTION_STATE";
		/**异常状态*/
		public static final String EXCEPTION_TYPE = "EXCEPTION_TYPE";
		/** 网点类型 */
		public static final String  ORG_TYPE="ORG_TYPE";
		/*** 是否标准类型*/
		public static final String  IS_STANDARD_LINE="IS_STANDARD_LINE";
		/*** 本位币类型*/
		public static final String  CURRENCY_TYPE="CURRENCY_TYPE";
		/** 经营类型*/
		public static final String  BUSINESS_TYPE="BUSINESS_TYPE";
		/**问题处理状态*/
		public static final String QUESTION_STATE = "QUESTION_STATE";
		/**问题类型*/
		public static final String QUESTION_TYPE = "QUESTION_TYPE";
		/**问题审核状态*/
		public static final String QUESTION_AUDIT_STATUS = "QUESTION_AUDIT_STATUS";
		/**对象类型枚举*/
		public static final String OBJ_TYPE = "OBJ_TYPE";
		/**消息类型枚举*/
		public static final String SMS_TYPE = "SMS_TYPE";
		/**常见品名*/
		public static final String COMMON_TYPE_GOODS_NAME = "COMMON_TYPE_GOODS_NAME";
		/**师傅的审核状态**/
		public static final String SF_AUDIT_TYPE="AUDIT_TYPE";
		/**银行类型**/
		public static final String BANK_TYPE="BANK_TYPE";
		
		/**租户的类型**/
		public static final String TENANT_TYPE="TENANT_TYPE";
		
		/**角色类型**/
		public static final String ROLE_TYPE="ROLE_TYPE";
		/**帐户的钱的状态**/
		public static final String WITHDRAW_STS="WITHDRAW_STS";
		/**预约提货状态**/
		public static final String ORD_SELLER_NOTIFY_STATE="ORD_SELLER_NOTIFY_STATE";
		/**预约提货类型**/
		public static final String ORD_SELLER_NOTIFY_PERIODTYPE="ORD_SELLER_NOTIFY_PERIODTYPE";	
		public static final String VISIT_INFO_STATE ="VISIT_INFO@STATE";
		/**服务类型**/
		public static final String SERVE_TYPE="SERVE_TYPE";
		public static final String COMPLAINT_INFO_STATE="COMPLAINT_INFO@STATE";
		public static final String COMPLAINT_INFO_SOURCE_TYPE="COMPLAINT_INFO@SOURCE_TYPE";
		/***直送，南哥物流配置直送区域**/
		public static final String DIRECT_SENDING="DIRECT_SENDING";
		/***核销状态类型*/
		public static final String AC_CASH_PROVE_CHECK_STS="AC_CASH_PROVE@CHECK_STS";
		
	}

	/**
	 * 系统参数常量定义
	 * @author wengxk
	 *
	 */
	public static class SysCfg {
		public static final String IS_SEND_MEG = "IS_SEND_MEG"; // 是否发送短信
		
		public static final String SMS_SEND_THREAD_COUNT = "SMS_SEND_THREAD_COUNT"; // 短信发送的进程数量
		public static final String CENSUSINFO_TASK_THREAD_COUNT = "CENSUSINFO_TASK_THREAD_COUNT"; // 用户全量信息扫描进程数量
		public static final String CENSUSINFO_ADD_TASK_THREAD_COUNT = "CENSUSINFO_ADD_TASK_THREAD_COUNT"; // 用户增量信息扫描进程数量
		public static final String RECOMMEND_TASK_THREAD_COUNT = "RECOMMEND_TASK_THREAD_COUNT"; // 用户邀请增量进程数量
		//APP推送
		public static final String SYS_ANDROID_APIKEY = "SYS_ANDROID_APIKEY";
		public static final String SYS_ANDROID_SECRETKEY = "SYS_ANDROID_SECRETKEY";
		public static final String SYS_IOS_APIKEY = "SYS_IOS_APIKEY";
		public static final String SYS_IOS_SECRETKEY = "SYS_IOS_SECRETKEY";
		
		public static final String EFFECTIVE_TIME = "EFFECTIVE_TIME";   //单位为秒
		
		//门户相关
		public static final String JOIN_IS_SEND_MSG = "JOIN_IS_SEND_MSG";   //申请联盟是否发送短信
		
		//APP相关
		public static final String APP_VERSION_IOS ="APP_VERSION_IOS";//苹果手机客户端版本cfg_system=0不强制，cfg_system=1强制
		public static final String APP_VERSION_ANDROID = "APP_VERSION_ANDROID";//安卓手机客户端版本cfg_system=0不强制，cfg_system=1强制
		public static final String APP_CACHE_IDENT = "APP_CACHE_IDENT"; //app缓存标识 1++
		public static final String INTERVAL_TIME="INTERVAL_TIME";//超过分钟再去查询发货统计和发车统计，单位是毫秒
		public static final String INVITE_URL="INVITE_URL";//好友推荐短息url
		public static final String ANDROID_INVITATION_URL = "ANDROID_INVITATION_URL"; //安卓邀请下载地址
		public static final String IOS_INVITATION_URL = "IOS_INVITATION_URL"; //IOS邀请下载地址
		public static final String ANDROID_DOWNLOADS_URL = "ANDROID_DOWNLOAD_URL"; //安卓下载安装包
		public static final String IOS_DOWNLOADS_URL = "IOS_DOWNLOAD_URL"; //IOS下载安装包
		//保险web页面
		public static final String PREMIUM_FLAG="PREMIUM_FLAG";//保险显示与隐藏
		public static final String PREMIUM_MIN="PREMIUM_MIN";//保险的最低费用
		/**综合险费率*/
		public static final String PREMIUM_RATE="PREMIUM_RATE";//保险的综合险费率
		/**基本险费率*/
		public static final String PREMIUM_RATE_J="PREMIUM_RATE_J";//保险的基本险费率
		//二维码扫码签收验证码的有效时间
		public static final String ER_WEI_MA_PASSWORD_DAY="ER_WEI_MA_PASSWORD_DAY";
		public static final String IS_OPEN_CAPTCHA="IS_OPEN_CAPTCHA";//验证码后台校验
		public static final String IS_PAY="IS_PAY";//屏蔽支付参数
		
		//提现金额限制
		public static final String CASH_MAX_MONEY ="CASH_MAX_MONEY";
		public static final String CASH_MIN_MONEY ="CASH_MIN_MONEY";
		//当天可提现总次数
		public static final String CASH_NUM = "CASH_NUM"; 
		
		//充值金额次数
		public static final String  RECHARGE_NUM = "RECHARGE_NUM";
		public static final String  RECHARGE_MAX_MONEY  = "RECHARGE_MAX_MONEY";
		public static final String  RECHARGE_MIN_MONEY  = "RECHARGE_MIN_MONEY";
		
		/**个人名片的url配置**/
		public static final String  QR_CODE_URL  = "QR_CODE_URL";
		
		public static final String QR_CODE_PRE="QR_CODE_PRE";
		
		public static final String HELPER_URL ="HELPER_URL ";
		
		/**环信注册用户名前缀**/
		public static final String  HX_USER_NAME  = "HX_USER_NAME";
		
		/**短信发送配置*/
		//验证类短信发送
		public static final String SMS_VERIFY = "SMS_VERIFY";
		//其余短信发送方式
		public static final String SMS_APP_SEND = "SMS_APP_SEND";
		
		public static final String RECOMMEND_TASK_SMS_TIME = "RECOMMEND_TASK_SMS_TIME"; // 用户邀请增量进程数量
		
		public static final String  GX_ENT_TIMEOUT="GX_ENT_TIMEOUT"; 
		public static final String  ACCEPT_TIMEOUT="ACCEPT_TIMEOUT"; 
		public static final String  PICK_UP_TIMEOUT="PICK_UP_TIMEOUT";
		public static final String  APPOINTMENT_TIMEOUT="APPOINTMENT_TIMEOUT";
		public static final String 	SIGN_TIMEOUT="SIGN_TIMEOUT";
		public static final String  APP_ACCEPT_TIMEOUT="APP_ACCEPT_TIMEOUT"; 
		public static final String  APP_PICK_UP_TIMEOUT="APP_PICK_UP_TIMEOUT";
		public static final String  APP_APPOINTMENT_TIMEOUT="APP_APPOINTMENT_TIMEOUT";
		public static final String 	APP_SIGN_TIMEOUT="APP_SIGN_TIMEOUT";
		public static final String 	FRIEND_ACCEPT="FRIEND_ACCEPT";//好友受理时间配置
		public static final String 	OPER_DES_URL="OPER_DES_URL";
		public static final String 	APP_VERSION="APP_VERSION";
		public static final String  BUSINESS_APPWEB = "BUSINESS_APPWEB";
		public static final String  SF_PARTNERS = "SF_PARTNERS";

		public static final String ORDER_DEPART_TIMEOUT = "ORDER_DEPART_TIMEOUT";//运单发车超时

		public static final String ORDER_DISPATCH_TIMEOUT = "ORDER_DISPATCH_TIMEOUT"; //运单配送超时

		public static final String ORDER_DISPATCH_TIME = "ORDER_DISPATCH_TIME";//运单配送天数配置
		
		
	}
	
	/**
	 * 用户服务实例类型
	 *
	 */
	public static class SERVICE_INST_TYPE{
		public static final int DDPT_USER_SERVICE_INST=1; //师傅类型
		public static final int DDPT_CONTRACT_SERVICE_INST=2;//协议公司类型
		public static final int VALUE_SERVICE_TYPE=2; //增值服务
		public static final int COMMON_SERVICE_TYPE=1; //普通服务
	}
	
	public static class GPS_SHARDING {
		public static final int shardingNum = 20;
		public static final double[] latitude = {3, 55};  //纬度范围X(中国）
		public static final double[] longitude = {65, 140};	//经度范围Y(中国）
		public static final double xRang = (latitude[1] - latitude[0]) / shardingNum;
		public static final double yRang = (longitude[1] - longitude[0]) / shardingNum;
	}
	
	/**
	 * 页面返回到前端的编码
	 * @author liyiye
	 *
	 */
	public static class Result{
		public static final String  SUCCESS="0";
		public static final String  FAIL="1";
	}
	
	/**
	 * 系统公共参数定义
	 * @author wengxk
	 *
	 */
	public static class Common {
		/**登录账号**/
		public static final String LOGIN_ACCT ="LOGIN_ACCT";
		/**验证码**/
		public static final String VALID_CODE="VALID_CODE";
		
		/**保存在session里面的角色*/
		public static final String SESSION_ROLES="SESSION_ROLES";
		
		/**
		 * 家装通
		 */
		public static final int TMS_IS_JZT=1;
		public static final int TMS_IS_NOT_JZT=0;
		
		public static final String TENANT_CODE="wo56";
		
		public static final String DATA_SOURCE_APP="APP";
		
		/**
		 * 师傅默认单数
		 * 30
		 */
		public static final int SF_LARGEST_ORDER_COUNT=30;
	}
	/**短信类型**/
	public static class SmsType{
		/**短信发送**/
		public static final long NOTICE_TYPE=1;
		
		/**推送**/
		public static final long MOPBILE_TYPE=2;
	}
	
	/**短信类型**/
	public static class OBJ_TYPE{
		/**公共消息**/
		public static final long OBJ_TYPE1=1;
		
		/**订单消息**/
		public static final long OBJ_TYPE2=2;
	}
	
	
	/**
	 * 短信模版ID
	 * @author wangbq
	 *
	 */
	public static class SmsTemplate{

		public static final long WARM_TEMPLATE_ID = 1000000;
		public static final long CAR_TEMPLATE_ID = 1000000042;//车源对应车辆提交订单后，取消其它对应车源订单短信,发给车主
		public static final long GOODS_TEMPLATE_ID = 1000000043;//车源对应车辆提交订单后，取消其它对应车源订单短信，发给货主
		
		public static final long REGIST_CODE = 1000000001; // 注册验证码
		public static final long RESET_CODE = 1000000005; // 重置验证码
		public static final long send_message_car = 1000000006; //发布货源时发短信给收藏车辆和我的车辆
		public static final long car_huan_che = 1000000007; //车主换车发送信息给货主
		public static final long good_huan_che = 1000000008; //车主换车发送信息给货主
		public static final long good_qu_xiao = 1000000019; //货主取消订单（发给车主）
		public static final long car_qu_xiao = 1000000020; //车主取消订单（发给货主）
		public static final long car_pi_pei= 1000000021; //车主匹配并确认订单（发给货主）
		public static final long good_pi_pei= 1000000022; //货主匹配并确认订单（发给车主）
		public static final long car_wan_cheng= 1000000023;//车主完成运输后（发给货主要货主确认完成）
		public static final long good_que_ren= 1000000025;//货主确认（发给车主）
		public static final long car_que_ren= 1000000026;//车主确认（发给货主）
		public static final long good_zx_que_ren= 1000000027;//货主发货发到专线（发给专线联系人）
		public static final long good_que_ren_wan_bi= 1000000028;//双方确认后（发给货主）
		public static final long car_que_ren_wan_bi= 1000000029;//双方确认后（发给车主）
		public static final long good_tou_bao= 1000000033;//发信息给货主提醒投保
		public static final long good_fa_bu= 1000000035;//货主发布信息成功通知货主
		public static final long car_fa_bu= 1000000034;//车主发布信息成功通知车主
		public static final long good_ti_jiao= 1000000036;//货主提交订单发给货主
		public static final long car_ti_jiao= 1000000037;//车主提交订单发给车主
		public static final long car_wan_cheng_good= 1000000038;//货主完成发信息给车主
		public static final long RE_TEMPLATE_ID = 1000000017;//充值短信模板id
		public static final long PAY_TEMPLATE_ID = 1000000018;//支付短信模板id
		public static final long REVOKE_TEMPLATE_ID = 1000000050;//支付撤销短信模板id
		public static final long TURNOUT_TEMPLATE_ID = 1000000051;//支付转出短信模板id
		public static final long TURNIN_TEMPLATE_ID = 1000000052;//支付转入短信模板id
		public static final long CAR_CONF_TEMPLATE_ID = 1000000040;//车主没确认取消订单短信模板ID
		public static final long GOODS_CONF_TEMPLATE_ID = 1000000041;//货主没确认取消订单短信模板ID
		public static final long GOODS_REGI_TEMPLATE_ID = 1000000046;//邀请收货人注册短信模板ID
		public static final long CARS_LINK_REGI_TEMPLATE_ID = 1000000045;//邀请车源联系人注册短信模板ID
		public static final long INVITE_FRIENDS_ID=1000000047;//邀请好友短息模版ID
		public static final long REGISTER_ID=1000000049;//组织方新增用户的告知短信模板
		public static final long MATCH_RESULT=100001001; // 匹配结果 通知车主、货主双方模版
		public static final long COLLECTION_CARS=1000000053;//收藏发送短信模板
		public static final long GOOD_ZHUANG_HUO_WAN_CHENG = 1000000054; //装货完成,发送给货方
		public static final long CAR_ZHUANG_HUO_WAN_CHENG = 1000000055; //装货完成，发送给车方
		public static final long GOODS_BUY_PREMIUM = 1000000056;//双方确认后、发信息提醒给货方购买保险
		public static final long TRAMSFER_REGIST_CODE= 1000000057; //转账短信验证码
		public static final long MODIFY_PWD_CODE= 1000000059; //支付密码修改短信验证码
		public static final long ZHI_FU_CODE= 1000000060; //支付货物保险短信验证码
		public static final long WITHDRAWALS_TEMPLATE_ID= 1000000062; //提现短信模版ID
		public static final long WITHDRAWALS_ROLL_TEMPLATE_ID= 100001082; //提现短信模版ID
		public static final long WITHDRAWALS_REGIST_CODE = 1000000061;  //提现短信验证码
		public static final long ORDER_PAY = 1000000063;  //订单支付短信验证码
		public static final long RESET_PWD_CODE = 1000000066;  //我的钱包重置密码验证码
		public static final long TELL_CAR_TIPS = 1000000064;  //推送短信给车主，说明货主选线下支付。
		public static final long PI_PEI_GOOD = 1000000069;  //增加匹配时订单已提交发给货主的短信
		public static final long PI_PEI_CAR = 1000000070;  //增加匹配时订单已提交发给车主的短信
		public static final long NO_SUBMIT=1000000071; // 未认证不能确认订单
		public static final long NO_COMPLETE=1000000072; //未认证不能签收订单
		public static final long PREMIUM_SEND=1000000073; //保险费用支付通知
		public static final long BINGING_BANK=1000000074;//告知用户已绑定银行卡
		public static final long PAY_INTERFACE=1000000076;//接口发送支付验证码接口模板
		public static final long JOIN_TO_COMPANY=1000000032L;//申请加盟短信
		
		public static final long SEND_PWD=1000000002L;//短信秘密
		public static final long REGISTER_TIPS=1000000081L;//注册成功提醒用户完善资料
		public static final long MACTH_BIDD_GOOD=1000000082L;//注册成功提醒用户完善资料
		
		public static final long IDENTITY_CHECK=1000000083L;//验证身份证发送短信
		public static final long REGIST_HTML5=1000000084;//html5注册密码提醒
		public static final long MODIFY_PHONE=1000000090;//html5注册密码提醒
		public static final long ATTENTION_CAR=1000000092; //关注车源线路发短信
		public static final long ATTENTION_GOOD=1000000093; //关注货源线路发短信
		public static final long BOND_RECHARGE=1000000091;//充值保证金短信
		public static final long RECOMMEND_NOTIFY=1000000111;//邀请每天增量通知短信
		public static final long LOGIN_SMS=1000000112;//登陆短信验证码信息
		/**信息费支付验证码的短信模板id**/
		public static final long INFORMAT_PAY=1000000118L;
		/**货主（或中介）主动申请信息费，车主也可以进行“确认支付”短信模板id**/
		public static final long APPLY_FOR_CREDIT=1000000115L;
		/**车主预付信息费，平台发短信通知货主短信模板id**/
		public static final long PREPAY =1000000116L;
		/**车主申请退回信息费，平台发短信通知货主短信模板id**/
		public static final long APPLY_FOR_RETURN=1000000117L;

		/**在发布评论时需要增加短信实时通知给永州招商粮食局管理员短信模板id**/
		public static final long YONGZHOU_SAY=1000000120L;

		public static final long MATCH_UPDATE_INFO = 1000000121L; //匹配提醒完善资料
		/**确认订单（货主）  货主确认订单-未支付*/
		public static final long GOODS_CON_WAIT_PAY = 1000000147L;//
		/**确认订单（货主）   货主确认订单-已支付*/
		public static final long GOODS_CON_FINISH_PAY = 1000000122L;
		/***确认订单（货主）  货主确认订单-已支付发给车方*/
		public static final long GOODS_CON_FINISH_PAY_TO_CAR = 1000000123L; //
		/**确认订单（货主）   货主短信：车主已支付*/
		public static final long CAR_FINISH_PAY = 1000000124L; 
		/***确认订单（货主）  车主短信：车主已支付*/
		public static final long CAR_FINISH_PAY_TO_GOOD = 1000000125L; 
		/***确认订单（车主） 车主短信：车主确认订单-未支付*/
		public static final long CARS_CON_WAIT_PAY = 1000000126L; 
		/*****确认订单（车主） 车主短信：车主确认订单-已支付（未支付—已支付）*/
		public static final long CARS_CON_FINISH_PAY = 1000000127L; 
		/***确认订单（车主） 货主短信：车主确认订单-已支付（未支付—已支付）*/
		public static final long CARS_CON_FINISH_PAY_TO_GOOD = 1000000128L; 
		/***确认订单（车主） 车主短信：货主已支付*/
		public static final long CARS_CON_GOOD_FINISH_PAY_TO_CAR = 1000000129L;
		/****确认订单（车主） 货主短信：货主已支付*/
		public static final long CARS_CON_GOOD_FINISH_PAY_TO_GOOD = 1000000130L; 
		/*****取消订单（货主）货主短信：货主取消----由我担责*/
		public static final long GOOD_CANCE_ME_LIABILITY = 1000000131L; 
		/****取消订单（货主）车主短信：货主取消----由我担责***/
		public static final long GOOD_CANCE_ME_LIABILITY_TO_CAR = 1000000132L; 
		/***取消订单（货主）货主短信：货主取消----对方担责*/
		public static final long GOOD_CANCE_OPPOSITE_LIABILITY = 1000000133L; 
		/**取消订单（货主）车主短信：货主取消----对方担责*/
		public static final long GOOD_CANCE_OPPOSITE_LIABILITY_TO_CAE = 1000000134L; 
		/****取消订单（货主） 货主（车主）短信：货主（车主）取消----双方担责*/
		public static final long GOOD_CANCE_TOGETHER_LIABILITY= 1000000135L; 
		/***取消订单（车主） 车主短信：车主取消----由我担责*/
		public static final long CAR_CANCE_ME_LIABILITY = 1000000136L; 
		/**取消订单（车主） 货主短信：车主取消----由我担责*/
		public static final long CAR_CANCE_ME_LIABILITY_TO_GOOD = 1000000137L;
		/**取消订单（车主） 车主短信：车主取消----对方担责*/
		public static final long CAR_CANCE_OPPOSITE_LIABILITY = 1000000138L; 
		/**取消订单（车主） 货主短信：车主取消----对方担责*/
		public static final long CAR_CANCE_OPPOSITE_LIABILITY_TO_GOOD = 1000000139L; 
		/**对方同意 货主短信：由我担责*/
		public static final long OPPOSITE_AGREE_ME_LIABILITY_TO_GOOD = 1000000140L; 
		/***对方同意 车主短信：对方担责*/
		public static final long OPPOSITE_AGREE_OPPOSITE_LIABILITY_TO_CAR = 1000000141L;
		/***对方同意 货主短信：对方担责*/
		public static final long OPPOSITE_AGREE_OPPOSITE_LIABILITY_TO_GOOD = 1000000142L;
		/**对方同意 车主短信：由我担责*/
		public static final long OPPOSITE_AGREE_ME_LIABILITY_TO_CAR = 1000000143L;
		/***对方同意 货主短信：双方担责*/
		public static final long OPPOSITE_AGREE_TOGETHER_LIABILITY_TO_GOOD = 1000000144L;
		/**对方同意 车主短信：双方担责*/
		public static final long OPPOSITE_AGREE_TOGETHER_LIABILITY_TO_CAR = 1000000145L; 
		/***对方拒绝 双方短信*/
		public static final long OPPOSITE_REFUSE_TOGETHER_LIABILITY = 1000000146L;
		
		//送货上门功能短信模板
		public static final long HOME_NOW_PAY_DRIVER = 1000000009;
		/**自提、代收、现付**/
		public static final long ONESELF_COLLMONEY_ARR_PAY_DRIVER = 1000000010;
		/**送货、代收、现付**/
		public static final long HOME_COLLMONEY_ARR_PAY_DRIVER = 1000000011;
		
		
		public static final long ONESELF_TEMPLATE_ID = 1000000001;
		public static final long NOT_ONESELF_TEMPLATE_ID = 1000000002;
		
		public static final long ONESELF_NOW_PAY = 1000000003;
		public static final long  ONESELF_ARR_PAY = 1000000004;
		public static final long HOME_NOW_PAY = 1000000005;
		public static final long HOME_ARR_PAY = 1000000006;
		/**自提、代收、现付**/
		public static final long ONESELF_COLLMONEY_ARR_PAY = 1000000007;
		/**送货、代收、现付**/
		public static final long HOME_COLLMONEY_ARR_PAY = 1000000008;
	}
	
	/**
	 * 产品配置相关常量
	 * @author Eric
	 */
	public static class SysProduct{
		/**
		 * 缓存常量
		 */
		public static final String SYS_PRODUCT_CACHE ="SYS_PRODUCT_CACHE";
		
		/**
		 * 产品根目录
		 */
		public static final long SYS_PRODUCT_ROOT_LEVEL =0L;
	}
	
	/*
	 * 公司接单、仓库缓存
	 * @author Eric
	 *
	 */
	public static class REMOTE_CACHE_KEY{
		public static final String COMPANY_JD_CACHE_KEY="COMPANY_JD_CACHE_KEY"; //接单数
		public static final String COMPANY_JD_CACHE_KEY_LEAVE="COMPANY_JD_CACHE_KEY_LEAVE"; //剩下接单数

		public static final String COMPANY_STORE_CACHE_KEY="COMPANY_STORE_CACHE_KEY"; //仓库容积数
		public static final String COMPANY_STORE_CACHE_KEY_LEAVE="COMPANY_STORE_CACHE_KEY_LEAVE"; //剩下仓库容积数

		public static final String INSTALL_USER_JD_CACHE_KEY="INSTALL_USER_JD_CACHE_KEY"; // 调度平台
	}
	
	/**
	 * 数据来源
	 * @author Eric
	 *
	 */
	public static class DATA_SOURCE{
		public static final String DATA_SOURCE_TMS="TMS"; //TMS
		public static final String DATA_SOURCE_DDPT="DDPT"; // 调度平台
		public static final String DATA_SOURCE_IMPORT="IMPORT";//导入
		public static final String DATA_SOURCE_APP="APP";//APP
	}
	/**组织表里面的顶级组织的parent_org_id **/
	public static long ROOT_ORG_ID=-1L;
	/**远行 租户ID**/
	public static long YX_TENANT_ID = 209;
	
	public static class KEY_TYPE{
		public static final int SF_TASK=1;//1师傅任务
		public static final int EXCEPTION=2;//异常
		public static final int OTHER=3;//其它
		
	}
	
	/**
	 * 运单号生成存放REDIS的KEY
	 * @author zx
	 *
	 */
	public static class TRACKING_NUM_REDIS{
		public static final String ORD_SEQ = "ord_seq_";//运单号生成规则
		public static final String ORD_SEQ_MANUAL = "ord_seq_manual_";//人工手动输入的运单号
		public static final String TANANT_TRACKING_NUM_BEGIN = "TRACKING_NUM_BEGIN";//运单号前2位编码，根据租户配置
		/***运单号的生成规则:如果配载了1 ：根据租户＋用户 ＋运单号 进行设置运单号规则。0 或者没有设置，默认使用随机生成的运单号 **/
		public static final String TRACKING_NUM_GEN_TYPE = "TRACKING_NUM_GEN_TYPE";
		//设置的值为1的枚举值
		public static final String TRACKING_NUM_GEN_TYPE_1 = "1";
		
	}
	/**
	 * 中转对外配置
	 * @author thuiyu
	 *
	 */
	public static class TRANSFER_CONFIGURATION{
		public static final String TRANSFER_OUT_CONFIGURATION="TRANSFER_OUT_CONFIGURATION";
	}
	
	/**
	 * 打印配置业务编码
	 * @author mcfly
	 *
	 */
	public static class PrintConfigBizCode {
		/** 打印运单 */
		public static final int BIZ_CODE_10000 = 10000;
		/** 打印回单 */
		public static final int BIZ_CODE_10001 = 10001;
		/** 打印标签 */
		public static final int BIZ_CODE_10002 = 10002;
	}
	
}
