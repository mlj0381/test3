package com.wo56.common.consts;


/**
 * 系统常量定义
 * @author wengxk
 *
 */
public class EnumConstsYQ {

	/**短信类型**/
	public static class SmsType{
		/**短信发送**/
		public static final int NOTICE_TYPE=1;
		
		/**推送**/
		public static final int MOPBILE_TYPE=2;
		
		/**微信推送*/
		public static final int WECHAT_TYPE = 3;
	}
	
	/**推送类型**/
	public static class OBJ_TYPE{
		/**公共消息**/
		public static final int OBJ_TYPE1=1;
		
		/**订单消息**/
		public static final int OBJ_TYPE2=2;
		
		/**抢单消息**/
		public static final int OBJ_TYPE3=3;
		
		/**微信消息**/
		public static final int OBJ_TYPE4=4;
	}
	
	
	/**
	 * 短信模版ID
	 * @author wangbq
	 *
	 */
	public static class SmsTemplate{
		/**亲，【公司名称】已帮你注册拉包工用户，账号为：13800138000，密码为：380000；请下载【云货宝】app进行接单；下载地址为xxxxx；*/
		public static final long REGISTER_TEMPLATE_ID = 1000000001;
		/**拉包工：亲，您有订单[订单号]需拉包，提货地址为：xxxxx，请尽快提货*/
		public static final long RECEIVER_ORDER_PUSH_WOKKER_TEMPLATE_ID = 1000000002;
		/**商家：您好，[收货人]有单推送给您，订单号为[订单号]，请打开app查看详情，进行备货，*/
		public static final long RECEIVER_ORDER_PUSH_BUSS_TEMPLATE_ID = 1000000003;
		/**商家：您好，[收货人]有单推送给您，订单号为[订单号]，请打开app查看详情，下载地址为xxxxxx。*/
		public static final long RECEIVER_ORDER_SMS_BUSS_TEMPLATE_ID = 1000000004;
		/**收货人：您好，[发货人]已下单，订单号为[订单号]，正等待拉包，请关注*/
		public static final long BUSI_ORDER_SMS_RECEIVER_TEMPLATE_ID = 1000000005;
		/**发货-拉包工：拉包工：您好，订单号：【订单号】已备好货，提货地址为：xxxxx，请尽快提货，*/
		public static final long SEND_GOODS_TO_WORKER = 1000000006;
		/**发货-收货人： 您好：订单号为【${orderNo}】,商家已经备货，正在等待拉包。*/
		public static final long SEND_GOODS_TO_CONSIGNEE = 1000000007;
		/**您好，[${companyName}]物流公司已收货，运单号为[${trackingNum}]*/
		public static final long SEND_BILLING_DELIVERY=1000000008;
		/**提货-收货人:您好，您的订单（${orderNo}）已经提货，正拉往物流公司。，请关注!*/
		public static final long SEND_PUSH_SMS =1000000009;
		/**打点小费-拉包工:您好：收到${companyName}物流公司，订单号为(${orderNo})费用${money}元，可以在钱包中查看。*/
		public static final long TIP_MONEY_SEND_SMS_CARRIER=1000000010;
		/**到货-发货人:您发送的订单已到达目的地，正在安排配送。*/
		public static final long ARRIVE_SEND_CONSIGNOR=1000000011;
		/**到货-收货人:您的订单(${orderNo}）已经到达${orgName}站，正在安排配送，配送网点客服电话${customerServicePhone}*/
		public static final long ARRIVE_SEND_CONSIGNEE=1000000012;
		/**配送-收货人：您的订单（${orderNo}）已到配送站点，详情请联系配送员：${distribution}，电话${phone}，请留意收件*/
		public static final long DISTRIBUTION_SEND_CONSIGNEE=1000000013;
		/**签收-发货人:您发送的订单已签收，签收人：${signer}，期待再次为你服务*/
		public static final long SIGN_SEND_CONSIGNOR=1000000014;
		/**催单-拉包工:商家已催单，请尽快提货了，如果30分钟拉包工还不提货，系统更换拉包工。*/
		public static final long Reminder_BUSINESS_SEND_CARRIER =1000000015;
		/**催单-拉包工:您好，【${orderNo}】商家已经备好货多时了，尽快提货哦！",1,"2017-02-09*/
		public static final long Reminder_SEND_CARRIER=1000000016;
		/**修改订单-发货人：您好，（收货方）订单号${orderNo}修改了订单，快去查看订单详情哦！*/
		public static final long UPDATE_ORDER_SEND_CONSIGNOR=1000000017;
		/**修改订单-拉包工：您好：订单号${orderNo}提货地址发生变化，请查看订单提货详情。*/
		public static final long UPDATE_ORDER_SEND_CARRIER=1000000018;
		/**修改订单-收货人：您好，您的订单号${orderNo}修改了订单，快去查看订单详情哦！*/
		public static final long UPDATE_ORDER_SEND_CONSIGNEE=1000000019;
		/**取消订单-收货人：您好：你的订单（${orderNo}）已经取消，如有疑问，请拨打商家客服电话${customerServicePhone}.*/
		public static final long CANCEL_ORDER_SEND_CONSIGNEE=1000000020;
		/**取消订单-拉包工:您好：你的提货订单（${orderNo}）已取消，查看其它订单吧！*/
		public static final long CANCEL_ORDER_SEND_CARRIER=1000000021;
		/**取消订单-开单员：您好：你的开单订单（${orderNo}）客户已发起取消，取消原因：${cancelReason}，请确认！*/
		public static final long CANCEL_ORDER_SEND_MERCHANDISER=1000000022;
		/**取消订单-发货人：您好：你的订单（${orderNo}）已经取消，如有疑问，请拨打（${consignee}）电话${phone}.*/
		public static final long CANCEL_ORDER_SEND_CONSIGNOR=1000000023;
		/**取消订单-收货人：您好：你的订单（${orderNo}）已经取消，如有疑问，请拨打拉包工电话${carrierBill}.*/
		public static final long CANCEL_ORDER_CARRIER_SEND_CONSIGNEE=1000000025;
		/**后台调单-拉包工:您没及时提货，现将订单（${orderNo}）重新分配给其他人进行拉包!*/
		public static final long BACKSTAGE_SEND_CARRIER=1000000026;
		/**后台调单-发货人","您好！系统重新分配拉包工：{$carrier}，电话${phone}为您服务！*/
		public static final long BACKSTAGE_SEND_CONSIGNOR=1000000027;
		/**注册修改密码验证码短信","【云货宝】云货宝验证码：${code}，打死不能告诉别人哦！*/
		public static final long REGISTER_SEND_VERIFICATION_CODE=1000000028;
		/**【云货宝】尊敬的用户，您于${date}向账号为${card}发起${money}元提现已成功，请关注您的到账提醒，具体到账时间已银行为准。*/
		public static final long EXTRACT_MONEY_SEND_SMS=1000000029;
		
		/**【云货宝】尊敬的用户，您于${date}向账号为${card}发起${money}元提现审核不通过，原因${auditRemark}，请重新申请提现。*/
		public static final long NOT_EXTRACT_MONEY_SEND_SMS=1000000031;
		/**抢单  亲，抢单啦！订单号（*****），提货地址为：xxxxx*****/
		public static final long GRAB_ORDER_SEND_SMS=1000000032;
		
		/**订单号为[${orderNo}]的订单，系统已分配拉包工：${name}，电话${phone}为您服务！*/
		public static final long DISTRIBUTION_CONTRACT=1000000030;
		
		/**拉包工：亲，您有订单[订单号]需拉包，提货地址为：XXXXX，更多提货地址，请查看订单详情*/
		public static final long RECEIVER_ORDER_PUSH_WOKKER_MUl_TEMPLATE_ID = 1000000033;
		
		/**订单号[订单号]多加了提货地址，请查看订单提货详情！**/
		public static final long UPDATE_ORDERS_ONE = 1000000034;
		
		/**订单号[订单号]多删除了提货地址，请查看订单提货详情！**/
		public static final long UPDATE_ORDERS_MORE = 1000000035;
		
		/**发货人：订单[orderId]拉包工：${name}，已经取消了，请重新分配拉包工！**/
		public static final long CANCEL_CONSIGNOR = 1000000036;
	}
	
	/**
	 * REDIS的KEY
	 */
	public static class ORDER_NO_REDIS{
		/**订单号生成前缀*/
		public static String ORDER_NO_TYPE = "ORDER_NO_TYPE";
	}
	
	public static class GRAB_ORDERS{
		/**抢单查询距离范围*/
		public static String GRAB_ORDER_DISTANCE = "GRAB_ORDER_DISTANCE";
	}
	/**
	 * 柯莱url
	 * @author kiya
	 *
	 */
	public static class KL_URL{
		public static String KL_URL = "http://122.13.138.14/Mobile/Action/action.ashx";
	}
	/**
	 * 柯莱订单操作
	 * @author kiya
	 *
	 */
	public static class KL_INFO{
		/**取消订单*/
		public static String CANCEL_ORDER = "1001";
		/**到货*/
		public static String ARRIVAL_GOODS = "1002";
		/***配送*/
		public static String DISPATCHING = "1003";
		/***签收*/
		public static String SIGN = "1004";
		/**开单*/
		public static String BILLING = "1005";
	}
	/**
	 * 柯莱请求参数
	 * @author kiya
	 */
	public static class KL_PARAM{
		public static final String ACTION = "action";
		public static final String CARGO_NO = "cargoNO";
		public static final String USERID = "userid";
		public static final String CREATE_DATE = "createDate";
		public static final String CREATE_ID = "createID";
		public static final String PASS_PORT  = "passPort";
		public static final String PASS_PORT_NO  = "passPortNO";
		public static final String SIGN_DATE  = "sign_Date";
		public static final String SIGN_MAN  = "sign_man";
	}
	/**
	 * 微信redis，session
	 */
	public static class WX_TEKON{
		public static final String WX_USER_TOKEN_PRE = "wx_user_token_";
	}
	/**
	 * TMS租户id
	 * @author kiya
	 *
	 */
	public static class TMS_TENANT_ID{
		public static final long KL_TENANT_ID = 7;
	}
}
