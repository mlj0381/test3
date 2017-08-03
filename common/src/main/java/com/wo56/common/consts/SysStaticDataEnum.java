package com.wo56.common.consts;




public class SysStaticDataEnum {
	
	/**
	 *用户类型USER_TYPE
	 * @author zjy
	 *
	 */   
	public static class USER_TYPE {
		/**操作员*/
		@Deprecated
		public static final int OPERATOR =1;	
		/**业务员*/
		@Deprecated
		public static final int BUSSINESS_OPER =2;
		/**开单员*/
		@Deprecated
		public static final int BILL_OPER =3;
		/**
		 * 师傅
		 */
		@Deprecated
		public static final int TMS_USER_TYPE_INSTALL_USER=4;
		
		//联运汇类型
		/**平台**/
		public static final int PLATFORM=1;
		
		/**专线**/
		public static final int CHAIN=2;
		
		/**商家**/
		public static final int BUSINESS=3;
		
		/**师傅**/
		public static final int MASTER=4;
		
		/**司机**/
		public static final int DRIVER=5;
		
		/**师傅合作商**/
		public static final int MASTER_PARTNERS=6;
		
		/***拉包公司 */
		public static final int PULL_PAG_COMPANY=10;
	}
	/**
	 * 用户表的登录类型
	 * **/
	public static class LOGIN_TYPE {
		/**登录web*/
		public static final int WEB =1;	
		/**登录app*/
		public static final int APP =2;
		/**登录web +app*/
		public static final int WEB_AND_APP =3;
	}
	
	/**
	 * 用户表的登录类型
	 * **/
	public static class ROLE_TYPE {
		/**平台管理员*/
		public static final int  PLATFORM=1;	
		/**公司管理员*/
		public static final int COMPANY =2;
		/**网点管理员*/
		public static final int ORG =3;
		
		/**普通员工*/
		public static final int COMMON=4;
	}
	/**
	 * 入库出库类型
	 */
	public static class STOCK_IN_TYPE{
		/**开单入库*/
		public static final int  STOCK_IN_ORD=0;	
		/**到车入库*/
		public static final int STOCK_IN_CAR =1;
	}
	/**
	 * 出库库出库类型
	 */
	public static class STOCK_OUT_TYPE{
		/**发车*/
		public static final int  STOCK_IN_STA=0;	
		/**中转*/
		public static final int STOCK_IN_TRA =1;
		/**送货上门*/
		public static final int STOCK_IN_SAV =2;
		/****签收出库**/
		public static final int STOCK_IN_SIGN =3;
	}
	
	/**
	 * 菜单类型
	 * **/
	public static class MENU_TYPE {
		/**平台*/
		public static final String  PLATFORM="1";	
		/**专线*/
		public static final String COMPANY ="2";
		/**商家*/
		public static final String ORG ="3";
		/**师傅合作商，拉包公司*/
		public static final String MASTER ="6";
		/**拉包公司*/
		public static final String PULL_PAG_COMPANY="10";
	}

	/**
	 * cm_customer客户信息表中的type
	 * @author cz
	 *
	 */
	public static class CUSTOMER_TYPE{
		/**发货人**/
		public static final int PUB_CUSTOMER = 1;
		/**收货人**/
		public static final int REC_CUSTOMER = 2;
	}
	/**
	 *线路费类型 LF_TYPE
            1、外部
            2、内部
	 * @author zjy
	 *
	 */   
	public static class LF_TYPE {
		/**外部*/
		public static final int WITHOUT =1;	
		/**内部*/
		public static final int INSIDE =2;
	}
	
	/**
	 *计算公式类型枚举 FORMULA_TYPE
          1 重量方式 2 体积方式 3 件数 4 有电梯 5 无电梯 6 距离 7 百分比
	 * @author zjy
	 *
	 */  
	public static class FORMULA_TYPE{
		/**重量方式*/
		public static final int WEIGHT_WAY =1;	
		/**体积方式*/
		public static final int VOLUME =2;
		/**件数*/
		public static final int NUMBER =3;
		/**有电梯*/
		public static final int HAS_LOFT =4;
		/**无电梯*/
		public static final int NO_LOFT =5;
		/**距离*/
		public static final int DISTANCE =6;
		/**百分比*/
		public static final int PERCENTAGE =7;
		/**楼层+重量*/
		public static final int WEIGHT_FLOOR =8;
		/**楼层+体积*/
		public static final int VOLUME_FLOOR =9;
	}

	/**
	 * 订单状态
	 * 1、待审核 2、待发车 3、待到车 4、待到货 5、签收 6、待通知 7、待签收 8、已删除
	 * @author zhouchao
	 *
	 */
	public static class ORDER_STATE{
		/**待审核*/
		public static final int WAIT_AUDIT = 1;
		/**待配载*/
		public static final int WAIT_GO = 2;
		/**待到车*/
		public static final int WAIT_ARRIVE = 3;
		/**待到货*/
		public static final int WAIT_RECEIVE = 4;
		/**签收*/
		public static final int RECEIVE = 5;
		/**待通知**/
		public static final int WAIT_NOTICE = 6;
		/**待签收*/
		public static final int WAIT_SIGN = 7;
		/**已删除*/
		public static final int IS_DEL = 8;
		/**待发车*/
		public static final int MATCH = 9;
		/**拼单*/
		public static final int SPELL = 10;
	}
	/**
	 * 导入数据的提取
	 */
	public static class IMPORT_STATE{
		/**未提取*/
		public static final int IMPORT_NO = 0;
		/**已提取*/
		public static final int IMPORT_IS = 1;
	}
	/**
	 *费用类型 ac_fee_config
	 * @author zjy
	 *
	 */  
	public static class COST_TYPE{
		/**配送区域*/
		public static final int SEND_AREA =1;	
		/**其他*/
		public static final int ELSE =2;
	}
	
	/**
	 * 订单日志操作类型
	 * @author zhouchao
	 *
	 */
	public static class OP_TYPE{
		/**保存、新增订单*/
		public static final int SAVE = 1;
		/**修改订单*/
		public static final int UPDATE = 2;
		/**审核订单*/
		public static final int AUDIT = 3;
		/**删除订单*/
		public static final int DEL = 4;
		/**到车*/
		public static final int ARRIVE = 5;
		/**发车*/
		public static final int GO = 6;
		/**到货*/
		public static final int RECEIVE = 7;
		/**签收*/
		public static final int SIGN = 8;
		/**预约通知*/
		public static final int REQUEST_NOTICE = 9;
		/**通知放货*/
		public static final int NOTICE_DELIVERY = 10;
		/**干线结束*/
		public static final int GX_END = 11;
		/**分配师傅*/
		public static final int DISTRIBUTION_SF = 12;
		/**取消分配/拒单*/
		public static final int CANCER_DISTRIBUTION = 13;
		/***接单*/
		public static final int ACCEPT = 14;
		/***提货*/
		public static final int PICK_UP = 15;
		/***预约*/
		public static final int YY = 16;
		/***签收*/
		public static final int SIGN_UP = 17;
		/***到货*/
		public static final int ARRIVE_GOODS = 18;
		
		/**已返厂*/
		public static final int NOT_RECEIPT = 11;
		
		/**已寄出*/
		public static final int SENDS = 12;
		
		/**已返回*/
		public static final int RETURN = 13;
		
		/**取消发车确认操作*/
		public static final int DEL_GO = 22;
		
		/**取消配载*/
		public static final int DEL_DEPART = 23;
		
		/**中转保存*/
		public static final int TRANSFER_SAVE = 19;
		/**中转修改*/
		public static final int TRANSFER_UPDATE = 20;
		/**中转取消*/
		public static final int TRANSFER_CANL = 21;
		
		
		/**中转订单再次开单*/
		public static final int TRANSFER_ORDERE_AUDIT = 33;
		
		/**短驳发车*/
		public static final int GO_SHORT  = 24;
		/**短驳到车*/
		public static final int ARRIVE_SHORT = 25;
		/**短驳到货*/
		public static final int RECEIVE_SHORT = 26;
		
		/**短驳取消发车确认操作*/
		public static final int DEL_GO_SHORT = 27;
		
		/**短驳取消配载*/
		/**短驳取消配载*/
		public static final int DEL_DEPART_SHORT  = 28;
		/***签收改单*/
		public static final int SIGN_MODIFY  = 29;
		/***新增配送上门*/
		public static final int ADD_DELIVERY  = 30;
		/***删除配送上门*/
		public static final int DELETE_DELIVERY  = 31;
		
		/**修改已经签收的运单*/
		public static final int UPDATE_ORDER_SIGN = 34;
		
		/**短驳新增*/
		public static final int NEW_SHORT = 35;
		
		/**干线新增*/
		public static final int GO_NEW = 36;
		
		/**中转中到货*/
		public static final int TRANSFER_ARRIVER_GOODS = 37;
		
		
		
		/**二次中转*/
		public static final int SECOND_TRANSFER = 38;
		
		/**拼单*/
		public static final int SAVE_SPELL = 39;
		/**拼单修改*/
		public static final int SAVE_SPELL_MODIFY = 40;
		/**拼单完成*/
		public static final int SPELL_END = 41;
		/**中转补录*/
		public static final int TRANSFER_IN = 42;
		/**新增船运配载*/
		public static final int ADD_SHIPPING = 43;
		/**修改船运配载*/
		public static final int MODIFY_SHIPPING = 44;
		/**取消船运配载*/
		public static final int CANCER_SHIPPING = 45;
		/**船运到达配载*/
		public static final int SHIPPING_ARRIVE = 46;
		/**船运到货配载*/
		public static final int SHIPPING_ARRIVE_GOODS = 47;
		/**取消发船*/
		public static final int CANCER_CONFIRM_SHIPPING = 48;
		/**发船*/
		public static final int SHIPPING_GO = 49;
		/**是否是对内：0 表示这条日志不对内，1表示对内可以显示*/
		public static final int IN_TYPE_NO = 0;
		/**1表示对内可以显*/
		public static final int IN_TYPE_YES = 1;
		
		/**是否对外:0 表示不对外， 1表示对外*/
		public static final int OUT_TYPE_NO = 0;
		/**1表示对外查询*/
		public static final int OUT_TYPE_YES = 1;
	}
	/**
	 * 是否签约客户 1:是 2:否
	 * @author kiya
	 *
	 */
	public static class SIGNING_TYPE{
		/**是签约客户**/
		public static final int SIGNING_IS = 1;
		/**不是签约客户**/
		public static final int SIGNING_NO = 2;
	}
	/**
	 * 是否默认提货点:0否1是，同一个组织对应多个提货点，只能有一个是默认提货点
	 * @author kiya
	 *
	 */
	public static class IS_SELECTED{
		/**是默认提货点**/
		public static final int BUS_IS = 1;
		/**不是默认提货点**/
		public static final int BUS_NO = 0;
	}
	/**
	 * 服务类型
	 * @author qlf
	 *
	 */
	public static class SERVE_TYPE{
		/**五包*/
		public static final int WUBAO_ORG = 1;
		/**三包*/
		public static final int SANBAO_ORG = 2;
		/**无*/
		public static final int WU_ORG = 3;
		
	}
	/**
	 * 无父级ID
	 * @author qlf
	 *
	 */
	public static class PARENT_ID{
		/**无*/
		public static final int PARENT = 0;
	}

	/**
	 * 网点类型
	 * @author zjy
	 *
	 */
	public static class COLLECT_TYPE{
		/**开始网点*/
		public static final int START_ORG = 1;
		/**中途网点*/
		public static final int MIDDLE_ORG = 2;
		/**到达网点*/
		public static final int END_ORG = 3;
		/**所有网点*/
		public static final int ALL_ORG = 4;
		
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
	 * 计算类型
	 * 1 1001-1002路由费计算
	 * 2 1001标准区域派送计算
	 * 3 1001特殊区域派送计算
	 * 4 1001卸货计算
	 * 5 1001无电梯计算
	 * 6 1001有电梯计算
	 * */
	public static class COUNT_TYPE{
		/**1001-1002路由费计算*/
		public static final int ROUTE_FEES = 1; 
		/**1001标准区域派送计算*/
		public static final int NORMAL_AREA = 2;
		/**1001特殊区域派送计算*/
		public static final int SPECIAL_AREA = 3; 
		/**1001卸货计算*/
		public static final int UNLOAD = 4;
		/**1001无电梯计算*/
		public static final int UN_HAS_LOFT = 5;
		/**1001有电梯计算*/
		public static final int HAS_LOFT = 6;
	}
	
	/**
	 * 付款方式
	 * @author zhouchao
	 *
	 */
	public static class PAYMENT_TYPE{
		/**现付*/
		public static final int NOW_PAY = 1;
		/**到付*/
		public static final int ARR_PAY = 2;
		/**月结*/
		public static final int MON_PAY = 3;
		/**回单付*/
		public static final int REC_PAY = 4;
		/**多款付*/
		public static final int MANY_PAY = 5;
	}
	
	/**
	 * 车辆批次状态 VEHICLE_STATE
	 * @author zjy
	 *
	 */
	public static class VEHICLE_STATE{
		/**未发车*/
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
	
	/**
	 * 船运配置状态 SHIP_STATE
	 * @author zjy
	 *
	 */
	public static class SHIP_STATE{
		/**待开船*/
		public static final int WAIT_DEPART= 1;
		/**在途中*/
		public static final int ON_THE_WAY = 2;
		/**已到港*/
		public static final int ARRIVE = 3;
		/**全部到货*/
		public static final int ARRIVAL_OF_GOODS = 4;
		/**部分到货*/
		public static final int ARRIVAL_OF_GOODS_PART = 5;
	}
	
	/**
	 * 回单状态 RECEIPT_STATE
	 * @author zjy
	 *
	 */
	public static class RECEIPT_STATE{
		/**待回单*/
		public static final int WAIT_RETURN= 1;
		/**待发车*/
		public static final int WAIT_GO = 2;
		/**回单寄出/待到车*/
		public static final int WAIT_ARRIVE = 3;
		/**待到货*/
		public static final int WAIT_RECEIVE = 4;
		/**签收*/
		public static final int RECEIVE = 5;
		/**待通知**/
		public static final int WAIT_NOTICE = 6;
		/**待签收*/
		public static final int WAIT_SIGN = 7;
		/**签单返回*/
		public static final int SIGN_RETURN = 8;
		/**回单返厂*/
		public static final int PALAUTUS = 9;
		/**配载中*/
		public static final int MATCH = 10;
		/**未寄出*/
		public static final int NOT_SEND = 11;
		/**已寄出*/
		public static final int SENDS = 12;
		/**已返回*/
		public static final int RETURN = 13;
	}
	
	/**
	 * 线路类型  ROUTE_TYPE
	 * @author lxh
	 *
	 */
	public static class ROUTE_TYPE{
		/**干线*/
		public static final int ARTERY= 1;
		/**短驳*/
		public static final int SHORT_BARGE = 2;
		/**中转*/
		public static final int TRANSIT = 3;
	}
	
	
	/**
	 * 是否等放货通知  RELEASE_NOTE
	 * @author lxh
	 */
	public static class RELEASE_NOTE{
		/**同意放货*/
		public static final int RELEASE_NOTE_YES= 0;
		/**等放货通知*/
		public static final int SHORT_BARGE_NO = 1;
	}
	
	
	/**
	 * 放货通知  RELEASE_NOTE
	 * @author lxh
	 */
	public static class SHIPMENT_NOTICE{
		/**同意放货*/
		public static final int SHIPMENT_NOTICE_YES= 0;
		/**等放货通知*/
		public static final int SHIPMENT_NOTICE_NO = 1;
	}
	
	public static class NOTIFY_TYPE{
		/**已通知*/
		public static final int NOTICE_YES= 1;
		/**未通知*/
		public static final int NOTICE_NOT= 2;
		/**已预约*/
		public static final int SUBSCRIBE_YES= 3;
		/**未预约*/
		public static final int SUBSCRIBE_NOT= 4;
	}
	
	/**
	 * 交接方式
	 * @author zhouchao
	 */
	public static class DELIVERY_TYPE{
		/**自提*/
		public static final int ONESELF = 1;
		/**送货上门*/
		public static final int HOME_DELIVERY = 2;
		/**送货上楼*/
		public static final int FLOOR_DELIVERY = 3;
		/**送货上门+安装*/
		public static final int HOME_DELIVERY_INSTALL = 4;
	}
	
	/****
	 * 运输方式
	 * @author user
	 *
	 */
	public static class TRANSPORT_TYPE{ 
		/**海运*/
		public static final int SEA_TRANSPORTATION = 1;
		/**汽运*/
		public static final int BUS_TRANSPORT = 2;
	}
	/**
	 * 签收状态
	 * @author lxh
	 */
	public static class SIGN_STATUS{
		/**已签收*/
		public static final int SIGN_FOR_YES = 1;
		/**未签收*/
		public static final int SIGN_FOR_NO = 2;
	}
	
	/**
	 * 车辆表信息状态 VEH_STATE
	 * @author zjy
	 *
	 */
	public static class VEH_STATE{
		/**空闲*/
		public static final int FREE= 1;
		/**待发车*/
		public static final int WAIT_DEPART= 2;
		/**在途中*/
		public static final int ON_THE_WAY = 3;
		
	}
	
	/**
	 * 是否需要安装车辆
	 */
	public static class IS_INSTALL{
		/**需要安装*/
		public static final int YES = 1;
		/**不需要安装*/
		public static final int NO = 2;
		
	}
	
	/**
	 * 异常类型
	 */
	public static class EXCEPTION_TYPE{
		/**有货无单*/
		public static final int GOODS_ORDER_NONE=1;
		/**有单无货*/
		public static final int ORDER_GOODS_NONE=2;
		/**少货*/
		public static final int LESS_GOODS=3;
		/**有单多货*/
		public static final int ORDER_GOODS_MORE=4;
		/**货物破损*/
		public static final int GOODS_BREAKAGE=5;
		/**夹货串货*/
	    public static final int CLIP_CARGO_LIST_GOODS =6;
		/**无标签*/
	    public static final int LABEL_NONE=7;
		/**标签差错*/
	    public static final int NOTHING_WRONG_LABEL =8;
	}
	
	/**
	 * 计费方式
	 * @author zhouchao
	 *
	 */
	public static class BILLING_TYPE{
		/**按重量**/
		public static final int WEIGHT = 1;
		/**按体积**/
		public static final int VOLUME = 2;
		/**按件数**/
		public static final int COUNT = 3;
	}
	
	/**
	 * 问题处理状态
	 * @author zhouchao
	 *
	 */
	public static class QUESTION_STATE{
		/**已处理*/
		public static final int IS_DEAL = 1;
		/**为处理*/
		public static final int NO_DEAL = 2;
	}
	
	/**
	 * 异常处理状态
	 * @author zhouchao
	 *
	 */
	public static class EXCEPTION_STATE{
		/**已处理*/
		public static final int IS_DEAL = 1;
		/**为处理*/
		public static final int NO_DEAL = 2;
	}
	
	/**
	 * 网点类型
	 * @author zjy
	 *
	 */
	public static class ORG_TYPE{
		/**营业网点*/
		public static final int TRADING_ORG = 1;
		/**集散中心*/
		public static final int FOXTOWN_CENTER = 2;
		/**总公司*/
		public static final int HEAD_OFFICE = 3;
		/**中转网点*/
		public static final int TRANSFER_ORG = 4;
		/**承运商*/
		public static final int CARRIER_ORG = 5;
	}
	
	/**
	 * 是否标准类型
	 * @author zjy
	 *
	 */
	public static class IS_STANDARD_LINE{
		/**标准线路*/
		public static final int STANDARD_ROUTE = 1;
		/**特殊线路*/
		public static final int SPECIAL_ROUTE = 2;
	}
	
	/**
	 * 本位币类型
	 * @author zjy
	 *
	 */
	public static class CURRENCY_TYPE{
		/**RMB*/
		public static final int RMB = 1;
		
	}
	
	
	/**
	 * 经营类型
	 * */
	public static class BUSINESS_TYPE{
		/**直营*/
		public static final int DIRECT = 1;
		/**加盟*/
		public static final int JOIN = 2;
	}
	
	/**问题方状态*/
	public static class QUESTION_AUDIT_STATUS{
		/**同意*/
		public static final int AGREE = 1;
		/**拒绝*/
		public static final int REFUSED = 2;
	}
	
	/**推送类型*/
	public static class TASK_TYPE{
		/**异常信息*/
		public static final int EXCEPTION = 1;
		/**问题件*/
		public static final int QUESTION = 2;
	}
	
	public static class KpiAssessConstant {
		public static final String HANDLER_PARAM_SYSTEM_TYPE = "SystemType";
		public static final String SYSTEM_TYPE_0 = "0";
		public static final String SYSTEM_TYPE_1 = "1";
		
		public static final String HANDLER_PARAM_USER_TYPE = "UserType";
		
		/*********** 握物流和握同城用户类型 *************/
		public static final int WYL_USER_TYPE_3 = 3;// (个体)车主/司机
		public static final int WYL_USER_TYPE_4 = 4;// (公司)货主
		public static final int WYL_USER_TYPE_6 = 6;// 物流公司/园区
		public static final int WYL_USER_TYPE_7 = 7;// (公司)车队/专线','物流车队
		public static final int WYL_USER_TYPE_9 = 9;// (个体)货主
		public static final int WTC_USER_TYPE_1 = 1;// 车主
		public static final int WTC_USER_TYPE_2 = 2;// 货主
		/*********** 握物流和握同城用户类型 *************/

		/************KPI考核类型****************/
		public static final String KPI_TYPE_M = "m";// 月度kpi
		public static final String KPI_TYPE_Q = "q";// 季度kpi
		public static final String KPI_TYPE_Y = "y";// 年度kpi
		/************KPI考核类型****************/
		
		public static final int OBJECT_TYPE_1 = 1;// 角色
		
		public static final String KEY_KPI_TYPE = "kpiType";
		public static final String KEY_KPI_KEY = "kpiKey";
		public static final String KEY_KPI_TYPE_VALUE = "kpiTypeValue";
		public static final String KEY_START_DATE = "startDate";
		public static final String KEY_END_DATE = "endDate";
		public static final String KEY_BILL_ID = "billId";
		public static final String KEY_USER_NAME = "userName";
		public static final String KEY_OP_ID = "opId";
		public static final String KEY_USER_ID = "userId";
		public static final String KEY_OBJECT_TYPE = "objectType";
		public static final String KEY_OBJECT_ID = "objectId";
		public static final String KEY_RULE_ID = "ruleId";
		public static final String KEY_YEAR = "year";
		public static final String KEY_MONTH_OR_QUARTER = "monthOrQuarter";
		public static final String KEY_ORIGINAL_RESULT = "originalResult";
		public static final String KEY_KPI_ORIGINAL_RESULT = "kpiOriginalResult";
		public static final String KEY_WEIGHTING = "weighting";
		public static final String KEY_TOTAL_WEIGHTING = "totalWeighting";
		public static final String KEY_KPI_WEIGHTING_RESULT = "kpiWeightingResult";
		
		
		/** 规则类型(1:自动计算;2:手动输入) **/
		public static final int RULE_TYPE_1 = 1;
		public static final int RULE_TYPE_2 = 2;
		/** 规则类型(1:自动计算;2:手动输入) **/
		
		/** 0 : 未完成; 1 : 完成 . 存在对象关联的kpi考核项需要他人评分，所以考核进程在处理数据的时候，需要生成一条明细记录 **/
		public static final int IS_FINISHED_0 = 0;
		public static final int IS_FINISHED_1 = 1;
		/** 0 : 未完成; 1 : 完成 . 存在对象关联的kpi考核项需要他人评分，所以考核进程在处理数据的时候，需要生成一条明细记录 **/
	
		public static final String CODE_TYPE_KPI_ASSESS_KPI_TYPE = "KPI_ASSESS@KPI_TYPE";
		public static final String CODE_TYPE_KPI_ASSESS_IS_FINISHED = "KPI_ASSESS@IS_FINISHED";
		public static final String CODE_TYPE_KPI_ASSESS_RULE_TYPE = "KPI_ASSESS@RULE_TYPE";
		
		
	}
	
	public static class MacroVariables {
		public static final String PREFIX_CHAR = "${";
		public static final String SUFFIX_CHAR = "}";
	}
	
	public static class WotcEntityManagerConstant {
		public static final String DB_WOTC = "DB_WOTC";
	}
	/**
	 * 账户类型
	 * @author Administrator
	 *
	 */
	public static class ACCOUNT_TYPE{
		/**现金账户*/
		public static final int CASH_ACCOUNT = 1;
		/**平台账户*/
		public static final int PLATFORM_ACCOUNT= 2;
		/**押金账户*/
		public static final int DEPOSIT_ACCOUNT= 3;
	}
	
	/**
	 * 管理员类型
	 * @author Administrator
	 */
	public static class ADMIN_ROLE{
		/**超级管理员角色*/
		public static final int SUPP_ADMINISTRATOR = 1;
		/**平台管理员角色*/
		public static final int  ADMIN_UPER= 2;
		
		/**网点用户角色类型*/
		public static final int  ORG_ADMIN_UPER= 3;
		
	}
	/**
	 * 短信业务类型
	 * @author Administrator
	 */
	public static class OBJ_TYPE{
		/**预约**/
		public static final int ORDER=1;
		
	}
	/**
	 * 短信业务类型
	 * @author Administrator
	 */
	public static class ROUTE_ORG_TYPE{
		/**线路费用**/
		public static final int ROUTE_FEE=1;
		/**网点费用**/
		public static final int ORG_TYPE=2;
		
	}
	/**
	 * 签收类型
	 * @author Administrator
	 */
	public static class SIGN_TYPE{
		/**正常签收**/
		public static final int NORMAL_SIGN=1;
		/**代签**/
		public static final int ISSUING=2;
	}
	
	/**
	 * 签收类型
	 * @author Administrator
	 */
	public static class IS_PAY_DISCOUNT{
		/**回扣未支付**/
		public static final int NOT_PAY_DISCOUNT=0;
		/**回扣已支付**/
		public static final int IS_PAY_DISCOUNT=1;
		
	}
	
	/**
	 * 增值服务
	 * @author Eric
	 *
	 */
	public static class SCHE_VALUE_SERVICE_TYPE{
		public static final String SCHE_VALUE_SERVICE_TYPE="SCHE_VALUE_SERVICE_TYPE";
		/**维修**/
		public static final long VALUE_SERVICE_TYPE_MAINTAINT=1;
		public static final String VALUE_SERVICE_TYPE_MAINTAINT_NAME="维护";
		/**返货**/
		public static final long VALUE_SERVICE_TYPE_RETURN_HUO=2;
		/**养护**/
		public static final long VALUE_SERVICE_TYPE_YANGHU=3;
	}
	
	/**
	 * 增值属性
	 * @author Eric
	 *
	 */
	public static class SCHE_VALUE_SERVICE_ATTR{
		public static final String SCHE_SERVICE_ATTR_TYPE="SCHE_VALUE_SERVICE_ATTR";
		/**皮具**/
		public static final long SERVICE_ATTR_SKIN=1;
		/**板材**/
		public static final long SERVICE_ATTR_WOOD=2;
		/**其他**/
		public static final long SERVICE_ATTR_OTHER=3;
	}
	
	//lyh 用户认证：1：未审核 2：已审核 3：审核中 4：已否决
	public static class ADUIT_TYPE{
		/**未审核*/
		public static final int SF_USER_UN_AUDIT=1; 
		/**已审核*/
		public static final int SF_USER_HAS_AUDIT=2;
		/**审核中*/
		public static final int SF_USER__AUDITING =3;
		/**已否决*/
		public static final int SF_USER_AUDIT_REJECT=4;
	}
	
	//师傅资料：1：待审核 2：已审核
	public static class SF_SERVICE_ADUIT_TYPE{
		public static final int SF_USER_UN_AUDIT=1; 
		public static final int SF_USER_HAS_AUDIT=2;
	}
	
	public static class AREA_CODE_TYPE{
		public static final String PROVINCE_TYPE="SYS_PROVINCE";
		public static final String CITY_TYPE="SYS_CITY";
		public static final String DISTRICE_TYPE="SYS_DISTRICT";
		public static final String STREET_TYPE="SYS_STREET";

	}
	
	/**
	 * 是否登陆
	 */
	public static class  IS_LOGIN{
		/**可以**/
		public static final int LOGIN_YES = 1;
		/**不能**/
		public static final int LOGIN_NOT = 0;
	}
	
	/**
	 * 常规服务
	 */
	public static class SCHE_SERVICE_TYPE{
		public static final String SCHE_SERVICE_TYPE="SCHE_SERVICE_TYPE";
		/**配送到家**/
		public static final long SERVICE_TYPE_SEND=12;
		/**安装**/
		public static final long SERVICE_TYPE_INSTALL=14;
		/**配送到家并安装**/
		public static final long SERVICE_TYPE_SEND_AND_INSTALL=13;
		/**无**/
		public static final long SERVICE_TYPE_NONE=11;
		
		/**区域自提**/
		public static final long SERVICE_TYPE_ONESELF=15;
		
		/**配送楼下**/
		public static final long SERVICE_TYPE_SEND_DOWNSTAIRS=16;
		
	}
	
	/**
	 * lyh
	 * 用户状态
	 * @author Eric
	 */
	public static class USER_STATE{
		public static final int USER_STATE_VAILDATE=1;
		public static final int USER_STATE_UN_VAILDATE=0;

	}
	
	/**节点类型：1师傅2公司3无**/
	public static class CASH_POINT_TYPE {
		public static final int PERSON = 1;
		public static final int COMPANY = 2;
		public static final int NO = 3;
	}
	
	/**1：提现申请2:审核通过3:否决申请5：签收6：取消签收7：充值保证金8：余额提现**/
	public static class CASH_OPER_TYPE {
		public static final int APPLY = 1;
		public static final int YES = 2;
		public static final int NO = 3;
		public static final int SIGN = 5;
		public static final int RECHARGE = 7;
		public static final int DEDUCT = 8;
	}
	
	/**1任务单号、2提现申请单号**/
	public static class CASH_OBJ_TYPE {
		public static final int TASK = 1;
		public static final int APPLY = 2;
	}
	/**TMS类型： 0审核，1撤销审核**/
	public static class CASH_TMS_TYPE {
		public static final String OK = "0";
		public static final String CANCEL = "1";
	}
	/**信用明细类型**/
	public static class CREDIT_DTL_TYPE {
		public static final int MARGIN = 1000;//保证金总分
		public static final int MARGIN_DETAIL = 1001;//保证金明细
		
		public static final int ORDER = 2000;//接单量总分
		public static final int ORDER_DETAIL = 2001;//接单数
		
		public static final int COMPLAIN = 3000;//投诉总分
		public static final int COMPLAIN_GENERAL = 3001;//普通投诉
		public static final int COMPLAIN_FATAL = 3002;//重大投诉
		
		public static final int SATISFY = 4000;//服务满意总分
		
		public static final int MAKE = 5000;//预约及时率总分
		public static final int MAKE_OK = 5001;//及时
		public static final int MAKE_NO = 5002;//不及时
		
		public static final int PROVE = 6000;//核销及时率总分
		public static final int PROVE_OK = 6001;//及时
		public static final int PROVE_NO = 6002;//不及时
		
		public static final int KPI_GX = 7000;//干线
		public static final int KPI_GX_NO = 7001;//干线超时结束
		
		public static final int KPI_RESERVE = 8000;//预约
		public static final int KPI_RESERVE_NO = 8001;//预约超时
		
		public static final int KPI_CHECK = 9000;//核销
		public static final int KPI_CHECK_NO = 9001;//核销超时
		
		public static final int KPI_COMPLAIN = 10000;//投诉
		public static final int KPI_COMPLAIN_NO = 10001;//投诉数
		
		public static final int KPI_ALL = 11000;//总单数
		public static final int KPI_ALL_NO = 11001;//总单数
	}
	
	/**费用科目类型**/
	public static class FEE_TYPE {
		public static final int BRANCH = 1;
		public static final int INSSTALL = 2;
		public static final int REPAIR = 3;
		public static final int EXCEPTION_FEE = 4;
		public static final int BRANCH_INSSTALL = 5;
		public static final int COLLECTING_MONEY = 6;
		public static final int FREIGHT_COLLECT = 7;
	}

	/**
	 * 
	 * 失效异常类型
	 * 
	 * **/
	public static class TIME_OUT_TYPE{
		/**干线结束超时**/
		public static final int GX_END_TIME_OUT_=1;
		/***预约超时*/
		public static final int YY_TIME_OUT=2;
		/**签收超时*/
		public static final int SIGN_TIME_OUT=3;
		/**接单超时*/
		public static final int ACCEPT_TIME_OUT=4;
	}
	
	/***状态# 1、未匹配 2、未分配(已匹配过)  3、已受理（公司转公司）  4 待受理(已分配)  5、待提货 6 待预约 7 待签收 8 已签收  9 取消签收*/
	public static class TASK_STATE {
		public static final int WAIT_MACTH = 1;
		public static final int WAIT_DISTRIBUTION = 2;
		public static final int ACCEPTED_CHECK = 3;
		public static final int DISTRIBUTION_WAIT_ACCEPT = 4;
		public static final int WAIT_PICK_UP = 5;
		public static final int WAIT_RESERVATION = 6;
		public static final int WAIT_SIGN = 7;
		public static final int DO_SIGN = 8;
		public static final int CANCER_SIGN = 9;
	}
	
	/**
	 * 
	 *处理状态
	 * 
	 * **/
	public static class DEAL_STATE{
		/**待处理**/
		public static final int WAIT_DEAL=1;
		/***处理中*/
		public static final int DEALING=2;
		/**已处理*/
		public static final int FINISH_DEAL=3;
	}
	
	/**
	 * 转单状态
	 * */
	public static class TURN_STATE{
		public static final int WAIT_ACCEPT=1;
		public static final int DO_ACCEPT=2;
		public static final int CAN_DIS=3;
		public static final int TIME_OUT_ACCEPT=4;
	}
	
	/**1 时效超时 2 人工取消**/
	public static class CANCRE_TYPE {
		public static final int TIMEOUT = 1;
		public static final int WORKER_CANCER = 2;
	}
	
	/**转单类型 转单类型 1 公司+师傅 2 师傅 3 不可转**/
	public static class TURN_RECEIPT_TYPE {
		public static final int COMPANY_SIFU = 1;
		public static final int SIFU = 2;
		public static final int REFUSE_TURN  = 3;
	}
	
	/**任务类型任务类型 1 安装单（运单表）
	2 维修任务（异常表）
	3、 异常单（异常表）**/
	public static class SCHE_TASK_TYPE {
		public static final int INSTALL_RECEIPT = 1;
		public static final int EXCE_TASK = 2;
		public static final int EXCE_TASK_RECEIPT = 3;
	}
	

	/**任务类型任务类型 1 配安单
	2 维修单）
	3、异常
	4、其他
	*/
	public static class KEY_TYPE {
		public static final int DIS_INST_ACCEPT = 1;
		public static final int REPAIR_ACCEPT = 2;
		public static final int EXCE = 3;
		public static final int OTHER = 4;
	}
	
	/**
	 * 
	 *证件类型
	 * 
	 * **/
	public static class ID_TYPE{
		/**身份证**/
		public static final int SHENG_FEN=1;
		/***驾驶证*/
		public static final int DRIVINE=2;
		/**军官证*/
		public static final int OFFICER=3;
	}
	
	/**是否匹配到数据 1 匹配到数据 2 没有匹配到数据**/
	public static class IS_MATCH_DATA {
		public static final int YES = 1;
		public static final int NO = 2;
	}
	
	/**任务提现状态
    1 不可提现  2 冻结中 3 待提现 4、提现中 5 已提现**/
	public static class WITHDRAW_STS {
		public static final int NO_WITHDRAW = 1;
		public static final int FREEZEING = 2;
		public static final int WAIT_WITHDRAW  = 3;
		public static final int WITHDRAW_ING = 4;
		public static final int DO_WITHDRAW = 5;
		public static final int DO_VER = 6;
	}
	
	/**
	 * 异常日志类型
	 * */
	public static class SCHE_EXCE_OP_TYPE{
		/**新建**/
		public static final int CREATE=1;
		/***修改*/
		public static final int MODIFY=2;
		/**处理*/
		public static final int DEAL=3;
		/**完成*/
		public static final int FINISH=4;
	}
	
	/**匹配类型 1 人工分配 2 智能分配 3、半自动匹配**/
	public static class MATCH_TYPE {
		public static final int ARTIFICIAL = 1;
		public static final int INTELLECT = 2;
		public static final int HALF_AUTO_MACTH = 3;
	}
	
	/**价格类型:1普通2协议. **/
	public static class IN_ROUTE_TYPE {
		public static final int COMMON = 1;
		public static final int PROTOCOL = 2;
	}
	
	/**组织数据1：有效2：无效 **/
	public static class ORG_STATE {
		/**有效**/
		public static final int COMMON = 1;
		/**无效**/
		public static final int PROTOCOL = 0;
	}
	
	
	/**收款方式**/
	public static class ACCT_RECC_TYPE {
		/**银行卡**/
		public static final int BANK = 1;
		/**支付宝**/
		public static final int ALIPAY = 2;
		/**微信**/
		public static final int WECHAT = 3;
	}
	
	/**责任类型
	 * 记录的责任类型  1 普通发货人、2 开单网点、3 到车网点、4 中转网点、5 配送网点、6 师傅、7收货人、8商家发货人\9 合作商
	 * */
	
	public static class DUTY_TYPE {
		public static final int CONSIGNOR = 1;
		public static final int INPUT_ORG = 2;
		public static final int ARRIVE_ORG = 3;
		public static final int CHANGE_ORG = 4;
		public static final int DISTRIBUTION_ORG = 5;
		public static final int SF = 6;
		public static final int CONSIGNEE = 7;
		public static final int BUSI_CONSIGNOR = 8;
		public static final int BUSINESS_COOPERATION = 9;
	}
	
	/**
	 * 订单类型-判断中转
	 * @author zhouchao
	 *
	 */
	public static class ORDER_TYPE{
		/**未中转*/
		public static final int NO_TRANSFER = 0;
		/**已中转*/
		public static final int IS_TRANSFER = 1;
	}
	
	/**
	 * 下单表订单类型
	 * @author zhouchao
	 *
	 */
	public static class SELLER_ORDER_TYPE{
		public static final int WO56 = 1;
	}

	/**
	 * 公司类型：1 平台 2 专线 3商家 6师傅合作商
	 * @author  dxb
	 *
	 */
	public static class TENANT_TYPE{
		/** 6师傅合作商**/
		public static final int TENANT_TYPE6 = 6;
		/** 3商家**/
		public static final int TENANT_TYPE3 = 3;
		/** 2专线**/
		public static final int TENANT_TYPE2 = 2;
	}
	
	
	/**
	 * 订单类型:1淘宝2天猫3京东4其他
	 * @author  dxb
	 *
	 */
	public static class OTHER_ORDER_TYPE{
		/** 1淘宝**/
		public static final int OTHER_ORDER_TYPE1 = 1;
		/** 2天猫**/
		public static final int OTHER_ORDER_TYPE2 = 2;
		/** 3京东**/
		public static final int OTHER_ORDER_TYPE3 = 3;
		/** 4其他**/
		public static final int OTHER_ORDER_TYPE4 = 4;
	}
	
	/**
	 * 展示类型 1、待发车 2、运输中3、待配送4、待签收5、已签收6、已删除、7拼单中
	 * @author  dxb
	 *
	 */
	public static class SEE_ORDER_TYPE{
		/** 1待发车**/
		public static final int SEE_ORDER_TYPE1 = 1;
		/** 2运输中**/
		public static final int SEE_ORDER_TYPE2 = 2;
		/** 3待配送**/
		public static final int SEE_ORDER_TYPE3 = 3;
		/** 4待签收**/
		public static final int SEE_ORDER_TYPE4 = 4;
		/** 5已签收**/
		public static final int SEE_ORDER_TYPE5 = 5;
		/** 6已删除**/
		public static final int SEE_ORDER_TYPE6 = 6;
		/** 7拼单中**/
		public static final int SEE_ORDER_TYPE7 = 7;
	}
	/**
	 * 展示类型 1、短驳 2干线
	 * @author  dxb
	 *
	 */
	public static class DEPART_SHORT{
		/** 1短驳**/
		public static final int SHORT_YES = 1;
		/** 2干线**/
		public static final int SHORT_NO = 2;
	}
	
	
	
	/**
	 * 云企地址类型
	 *
	 */
	public static class ADDRESS_TYPE_YQ{
		/**1默认地址*/
		public static final int ADDRESS_TYPE_IS_DEFAULT = 1;
		/**0非默认地址*/
		public static final int ADDRESS_TYPE_NOT_DEFAULT = 0;
	}
	/**
	 * 云企通讯录拉黑
	 */
	public static class MAILLIST_TYPE_YQ{
		/**未拉黑*/
		public static final int MAILLIST_TYPE_NOT_BLACK = 0;
		/**拉黑*/
		public static final int MAILLIST_TYPE_IS_BLACK = 1;
	}
	/**
	 * 云企通讯录拉黑类型
	 * 
	 */
	public static class PULL_BLACK_TYPE_YQ{
		/**客户*/
		public static final int PULL_BLACK_TYPE_CUSTOMER = 0;
		/**专线*/
		public static final int PULL_BLACK_TYPE_SPECIAL = 1;
		/**拉包公司*/
		public static final int PULL_BLACK_TYPE_PULLPAGCOMPANY = 2;
		/**平台*/
		public static final int PULL_BLACK_TYPE_PLATFORM = 3;
	}
	
}

