package com.wo56.common.consts;

public class SysStaticDataEnumYunQi {
	
	
	public static class CACHE {
		public static final String CM_ORG_REL = "CM_ORG_REL"; // 租户关系表
	}
	
	
	
	/**
	 * 租户类型
	 * @author liyiye
	 *
	 */
	public static class TENANT_TYPE{
		/**平台**/
		public static final int PLATFORM=1;
		
		/**专线**/
		public static final int CHAIN=2;
		
		/**拉包公司*/
		public static final int COMPANY=10;
	}
	/**
	 * 登录类型
	 * @author kiya
	 *
	 */
	public static class LOGIN_TYPE{
		/**web*/
		public static final int WEB = 1;
		/**app*/
		public static final int APP = 2;
		/**web和app*/
		public static final int WEB_APP = 3;
		
		/**来自对外接口*/
		//public static final int YQ_TMS = 4;
		 
	}
	
	public static class CHANNEL_TYPE{
		/**来源微信*/
		public static final int WECHAT_CHANNEL_TYPE = 5;
		/**来源IOS*/
		public static final int IOS_CHANNEL_TYPE = 1;
		/**来源Android*/
		public static final int ANDROID_CHANNEL_TYPE = 2;
		
	}
	
	
	
	
	/**
	 * 云启用户角色
	 * 
	 * @author kiya
	 *
	 */
	public static class USER_TYPE_YUNQI {
		// 云企
		/** 平台 **/
		public static final int PLATFORM = 1;

		/** 专线 **/
		public static final int CHAIN = 2;

		/** 商家 **/
		public static final int BUSINESS = 3;

		/** 拉包工 **/
		public static final int PULL = 7;

		/** 开单员 **/
		public static final int MERCHANDISER = 8;

		/** 配送员 **/
		public static final int DISTRIBUTION = 9;
		
		/** 拉包公司 **/
		public static final int PULLCOM = 10;
		
		/**虚拟用户*/
		public static final int VIRTUAL_USER = 11;
		
		/**配送员与开单员*/
		public static final int MERCHANDISER_DISTRIBUTION = 12;

	}

	/**
	 * 拉包工上下班状态--云企
	 */
	public static class PULL_WORK_YUNQI {
		/** 休息 */
		public static final int PULL_WORK0 = 0;
		/** 上班 */
		public static final int PULL_WORK1 = 1;
	}

	/**
	 * 云企拉包工认证状态
	 */
	public static class PULL_STATE {
		/** 未认证 */
		public static final int NOT_CERTIFIED= 1;
		/** 已认证 */
		public static final int CERTIFIED = 2;
		/** 审核中 */
		public static final int AUDIT = 3;
		/** 认证不通过 */
		public static final int NOT_APPROVED = 4;
	}

	/**
	 * 流水
	 */
	public static class ACCOUNT_TYPE {
		/*** 支出 */
		public static final int EXPENDITURE = 1;
		/** 收入 */
		public static final int INCOME= 2;
	}

	public static class AMOUNT_STATE {
		/*** 1：未提现 */
		public static final int NOT_PRESENT= 1;
		/** 2：提现中 */
		public static final int CASH_WITHDRAWAL = 2;
		/** 3：已提现 */
		public static final int ALREADY_PRESENT = 3;
	}
	/****
	 * SERVICE_TYPE
	 * @author user
	 *
	 */
	public static class SERVICE_TYPE{
		/***1：自提*/
		public static final int TAKE_PICK = 1;
		/**2：送货*/
		public static final int DEVLIVERY = 2;
	}
	
	/****
	 * PAYMENT_TYPE_YQ
	 * @author user
	 *
	 */
	public static class PAYMENT_TYPE_YQ{
		/**现付*/
		public static final int NOW_PAY = 1;
		/**到付*/
		public static final int ARR_PAY = 2;
		/**月结*/
		public static final int MON_PAY = 3;
		/**其它*/
		public static final int OTHER_PAY = 4;
	}
	
	/****
	 * PAYMENT_TYPE_YQ
	 * @author user
	 *
	 */
	public static class ORDERS_STATE{
		/**1.派单中*/
		public static final int DIS_ING = 1;
		/**2.待提货*/
		public static final int WAIT_PCIKUP = 2;
		/**3.拉包中*/
		public static final int CARRY_PACKAGE_ING = 3;
		/**4.运输中*/
		public static final int IN_TRANSIT = 4;
		/**5.待配送*/
		public static final int WAIT_DELIVERY = 5;
		/**6.待签收*/
		public static final int WAIT_SIGN = 6;
		/**7.已签收*/
		public static final int DO_SIGN = 7;
		/**8.取消中*/
		public static final int CANCERING = 8;
		/**9.已取消*/
		public static final int DO_CANCER = 9;
		/**10.待发车*/
		public static final int PENDING_DEPARTURE = 10;
		/**11.部分签收*/
		public static final int PARTIAL_SIGN = 11;
	}

	/**对内开单状态*/
	public static class ORDER_INFO_STATE{
		/**1.待配载*/
		public static final int STOWAGE_PLAN = 1;
		/**3.待发车*/
		public static final int PENDING_DEPARTURE = 3;
		/**4.在途中*/
		public static final int IN_TRANSIT = 4;
		/**5.未到货*/
		public static final int FLOATION_CARGO = 5;
		/**2.待配送*/
		public static final int PENDING_DELIVERY = 2;
		/**6.待签收*/
		public static final int WAIT_SIGN = 6;
		/**7.已签收*/
		public static final int DO_SIGN = 7;
		/**8.取消中*/
		public static final int CANCERING = 8;
		/**9.已取消*/
		public static final int DO_CANCER = 9;
		/**10.部分签收*/
		public static final int PARTIAL_SIGN = 10;
	}
	/**对外开单状态*/
	public static class ORDER_INFO_STATE_OUT{
		/**2.待配送*/
		public static final int PENDING_DELIVERY = 2;
		/**3.待发车*/
		public static final int PENDING_DEPARTURE = 3;
		/**4.运输中*/
		public static final int IN_TRANSIT = 4;
		/**6.待签收*/
		public static final int WAIT_SIGN = 6;
		/**7.已签收*/
		public static final int DO_SIGN = 7;
		/**8.取消中*/
		public static final int CANCERING = 8;
		/**9.已取消*/
		public static final int DO_CANCER = 9;
		/**10.部分签收*/
		public static final int PARTIAL_SIGN = 10;
		
	}
	
	
	
	/**
	 * 操作日志对外
	 * 
	 * @author
	 *
	 */
	public static class OP_TYPE_YUNQI {
		/*** 50、下单-收货 */
		public static final int SAVE_COLLECT_ORDERS = 50;
		/*** 51、 下单-发货 */
		public static final int SAVE_DELIVERY_ORDERS = 51;
		/*** 52、发货 */
		public static final int DELIVER_GOODS = 52;
		/*** 53、催单 */
		public static final int OP_TYPE_REMINDER_ORDERS = 53;
		/*** 55、改单 */
		public static final int CHANGE_ORDERS = 55;
		/*** 56、取消下单 */
		public static final int CANCEL_ORDER = 56;
		/*** 54、提货 */
		public static final int CARRY_GOODS = 54;
		/*** 57、干线到货 */
		public static final int GX_ARRIVE_GOODS = 57;
		/*** 58、开单 */
		public static final int OPEN_ORDERS = 58;
		/*** 59、关联单号 */
		public static final int RELATION_WAYBILL = 59;
		/*** 60、配送 */
		public static final int DISTRIBUTION_ORDERS = 60;
		/*** 61、 签收 */
		public static final int SIGN_ORDERS = 61;
		/*** 62 打点小费 */
		public static final int SEND_TIP_MONEY = 62;
		/***63 分配*/
		public static final int DIS_OP = 63;
		/***64 调单*/
		public static final int CHANGE_RECEIPT = 64;
		/***65 取消审核*/
		public static final int CHANGE_AUTH_RECEIPT = 65;
		
		/***66 配载*/
		public static final int DEPART_OP = 66;
		
		/***67 发车*/
		public static final int PUT_OUT = 67;
		/***68 取消发车*/
		public static final int PUT_OUT_CANCEL = 68;
		/***69 到车*/
		public static final int ASTRREN = 69;
		/***70 取消到车*/
		public static final int CANCEL_ASTRREN = 70;
		/***71 取消到货*/
		public static final int CANCEL_ARRIVAL = 71;
	}

	
	
	/**
	 * 消息
	 */
	public static class IS_READ{
		/**未读*/
		public static final int unread=0;
		/**已读*/
		public static final int read=1;
	}
	/**
	 * 数据状态
	 * @author Administrator
	 *
	 */
	public static class STS{
		/**有效*/
		public static final int VALID = 1;
		/**无效*/
		public static final int NULLITY = 0;
	}	
	
	/**
	 * 消息objType
	 */
	public static class OBJ_TYPE{
		/**1：公共消息 */
		public static final int PUBLIC_MESSAGR=1;
		/**2：订单消息*/
		public static final int ORDER_MESSAGE =2;
	}
	/**
	 * 算费类型
	 * @author kiya
	 */
	public static class RULE_TYPE{
		/**1:小费*/
		public static final int tip = 1;
		/**2：运费*/
		public static final int freight = 2;
		/**3：保费*/
		public static final int premium = 3;
	}
	/**
	 * 小费计算规则（1：按重量递增；2：按重量范围；3：按运费占比）
	 * @author kiya
	 *
	 */
	public static class TIP_TYPE{
		/**按重量递增*/
		public static final int WEIGHT_INCREMENT = 1;
		/**按重量范围*/
		public static final int WEIGHT_RANGE = 2;
		/**按运费占比*/
		public static final int FREIGHT_RATE = 3;
	}
	/**
	 * 云企收款类型
	 */
	public static class RECEI_TYPE_YQ{
		/**微信*/
		public static final int WX_RECEI_TYPE_YQ=0;
		/**银行卡*/
		public static final int BANK_RECEI_TYPE_YQ=1;
		/**支付宝*/
		public static final int ZFB_RECEI_TYPE_YQ=2;
		
	}
	/**
	 * 拉黑类型
	 * @author kiya
	 *
	 */
	public static class BLACK_TYPE{
		/**0：客户*/
		public static final int BLACK_CUSTOMER=0;
		/**1：专线*/
		public static final int BLACK_CHAIN=1;
		/**2：拉包公司*/
		public static final int BLACK_COM=2;
		/**3：平台*/
		public static final int BLACK_PLAT=3;
		
	}
	
	public static class SEND_FLAG{
		/**0未发送*/
		public static final int NOT_SEND =0;
		/**1已发送*/
		public static final int IS_SEND = 1;
	}
	/**
	 * 是否通用
	 * @author kiya
	 *
	 */
	public static class IS_CURRENCY{
		/**是通用*/
		public static final int IS_CURRENCY = 1;
		/**不是通用*/
		public static final int NOT_CURRENCY = 0;
	}
	
	
	/**审核**/
	public static class AUDIT_STATUS_YQ{
		/**审核通过*/
		public static final int AUDIT_THROUGH= 1;
		/***审核不通过*/
		public static final int AUDIT_NOT = 2;
		/***未审核*/
		public static final int UNAUDITED = 3;
	}
	/**核销*/
	public static class WRITE_STATE_YQ{
		/**已核销*/
		public static final int WRITE_YES = 1;
		/**未核销*/
		public static final int WRITE_NOT = 2;
	}
	/**
	 * 客户类型
	 * @author kiya
	 *
	 */
	public static class  CUSTOMER_TYPE{
		/**发货人*/
		public static final int CONSIGNOR = 1;
		/**收货人*/
		public static final int CONSIGNEE = 2;
	}
	
	public static class ORDER_COUNT_SELECT{
		/**发货人*/
		public static final int CONSIGNOR = 1;
		/**收货人*/
		public static final int CONSIGNEE = 2;
		/**拉包工*/
		public static final int PULL = 3;
		/**物流公司*/
		public static final int TENANT_COM = 4;
		/**完成状态*/
		public static final int STATE = 5;
		/**货型*/
		public static final int GOODS_TYPE = 6;
	}
	
	public static class ORDER_COUNT_SELECT_TYPE{
		/**发货**/
		public static final String SEND_GOODS = "1";
		/**订货**/
		public static final String ORDERS_GOODS = "2";
		/**运输*/
		public static final String IN_TRANSIT = "1";
		/**到货*/
		public static final String WAIT_DELIVERY = "2";
		/**签收*/
		public static final String DO_SIGN = "3";
	}
	/**
	 * 云企TMS接入租户
	 * @author kiya
	 *
	 */
	public static class TMS_TENANT_YQ{
		/**科莱*/
		public static final String TMS_KL = "TMS_KL";
		/**中铁*/
		public static final String TMS_ZT = "TMS_ZT";
		/**云企*/
		public static final String TMS_YQ = "TMS_YQ";
	}
	/**
	 * 控制调用TMS
	 */
	public static class TMS_OPEN{
		/**取消订单*/
		public static final String TMS_CANCEL_ORDER = "TMS_CANCEL_ORDER";
		/**到货*/
		public static final String TMS_ARRIVAL = "TMS_ARRIVAL";
		/**配送*/
		public static final String TMS_DISPATCHING = "TMS_DISPATCHING";
		/**签收*/
		public static final String TMS_SIGN = "TMS_SIGN";
		
		/**开单*/
		public static final String TMS_BILLING = "TMS_BILLING";
	}
	
	/*
	 * TMS对接云贷宝静态数据配置
	 */
	
	public static class YQ_TENANT_TMS{
		/**科莱*/
		public static final String TMS_CODE_VALUE_KL = "3";
		/**中铁*/
		
		/**云企*/
		
	}
	
	public static final String TMS_KL_INFO = "73" ;
	
	
	/****
	 * 
	 * @author user
	 *
	 */
	public static class INTERCHANGE_YUNQI {
		/**1：配送*/
		public static final int DEVLIVERY_FLOOR_UP =1 ;
		/**2：配送下楼*/
		//public static final int DEVLIVERY_FLOOR_DOWN =2 ;
		/***3：自提*/
		public static final int TAKE_PICK = 3;
	
	}
	
	
	/**
	 * 车辆批次状态 VEHICLE_STATE_YQ
	 * @author 云启
	 *
	 */
	public static class VEHICLE_STATE_YQ{
		/**待发车*/
		public static final int WAIT_DEPART= 1;
		/**在途中*/
		public static final int ON_THE_WAY = 2;
		/**已到车*/
		public static final int ARRIVE = 3;
		/**全部到货*/
		public static final int ARRIVAL_OF_GOODS = 4;
		/**部分到货*/
		public static final int ARRIVAL_OF_GOODS_PART = 5;
	}
}
