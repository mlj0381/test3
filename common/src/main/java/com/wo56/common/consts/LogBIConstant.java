package com.wo56.common.consts;


public class LogBIConstant {
	public static class LogbiRedisConstant {
		// 用户短信黑名单控制，KEY对应的value为Map类型。Map的key为用户手机号， Map的value为0/1(0表示只是接收推送信息，不接收短信，1表示不接受推送，短信);
		//测试
		public static final String SMS_BLACKLIST = "SMS_BLACKLIST";
	}
	
	public static class AdviseInfoConstant {
		public static final String CODE_TYPE_STATUS = "ADVISE_INFO@STATUS";
		public static final String CODE_TYPE_ADVISE_TYPE = "ADVISE_INFO@ADVISE_TYPE";
		
		public static final String AID = "AId";
		public static final String HTTP_REQ_PARAM_FLAG = "flag";
		public static final String FLAG_ADD = "add";
		public static final String FLAG_DEAL = "deal";
	}
	
	public static class DialRecConstant {
		public static final String REC_ID = "recId";
		public static final String SOURCE_TYPE = "sourceType";
		public static final String SOURCE_ID = "sourceId";
		public static final String LINK_MAN = "linkMan";
		public static final String LINK_PHONE = "linkPhone";
		public static final String USER_ID = "userId";
		public static final String USER_PHONE = "userPhone";
		public static final String OPER_DATE = "operDate";
		public static final String CHANEL_TYPE = "chanelType";
		public static final String OPER_PAGE = "operPage";
		public static final String CHANEL_TYPE_NAME = "chanelTypeName";
		public static final String OPER_PAGE_NAME = "operPageName";
		public static final String CALL_NUMBER = "callNumber";
		
		public static final String CODE_TYPE_SOURCE_TYPE = "DIAL_REC@SOURCE_TYPE";
		public static final String CODE_TYPE_CHANEL_TYPE = "DIAL_REC@CHANEL_TYPE";
		public static final String CODE_TYPE_OPER_PAGE = "DIAL_REC@OPER_PAGE";
		
		public static final String OPER_DATE_START_TIME = "operDateStartTime";
		public static final String OPER_DATE_ENDTIME = "operDateEndTime";
	}
	
	public static class UserCreditConstant {
		public static final int CREDIT_TYPE_1 = 1;// 车主
		public static final int CREDIT_TYPE_2 = 2;// 货主
		public static final int CREDIT_TYPE_3 = 3;// 不分车主货主
		
		public static final int OBJECT_TYPE_0 = 0; // 其他
		public static final int OBJECT_TYPE_1 = 1;// 订单
		public static final int OBJECT_TYPE_2 = 2;// 投诉单
		
		public static final int CREDIT_DETAIL_SUMMERY = 1;// 信用明细分类，汇总项目一级
		public static final int CREDIT_DETAIL_SUB_SUMMERY = 2;// 信用明细分类，汇总项目二级
		
		public static final String USER_TYPE_MAPPING_USER_CREDIT_TYPE = "USER_TYPE_MAPPING_USER_CREDIT_TYPE";
		public static final int STATE_1 = 1;
		public static final int STATE_0 = 0;
		
		public static final int CALCULATE_TYPE_1 = 1;// 全量
		public static final int CALCULATE_TYPE_2 = 2;// 每日增量
	}
	
	public static class BlackListConstant {// 黑名单
		public static final int STATUS_ALL = -1;// 所有状态
		public static final int STATUS_0 = 0;// 已生效
		public static final int STATUS_1 = 1;// 待生效
		public static final int STATUS_2 = 2;// 已解除
		public static final String CODE_TYPE_STATUS = "SYS_BLACK_LIST@STATUS";
		
		public static final String BLACK_ID = "blackId";
		public static final String MOBILE_PHONE = "mobilePhone";
		public static final String USER_NAME = "userName";
		public static final String CREATE_DATE = "createDate";
		public static final String CREATE_DATE_START_TIME = "createDateStartTime";
		public static final String CREATE_DATE_ENDTIME = "createDateEndTime";
		public static final String FAIL_DATE = "failDate";
		public static final String REASON = "reason";
		public static final String REASON_DETAIL = "reasonDetail";
		public static final String STATUS = "status";
		public static final String OP_ID = "opId";
		public static final String APPEAL_SUCCESS_REASON = "appealSuccessReason";
		public static final String APPEAL_SUCCESS_DEFAULT_REASON = "操作员手动解除";
		
		// 将用户是否为黑名单的信息保存到redis缓存中
		public static final String SYS_BLACK_LIST_USER_KEY = "SYS_BLACK_LIST_USER";
		public static final String IS_BLACK_USER_0 = "0";
		public static final String IS_BLACK_USER_1 = "1";// 黑名单用户
		
		// 移除黑名单的来源(分两种：操作员手动移除和用户申述)
		public static final String REMOVE_BLACK_SOURCE = "removeBlackSource";
		public static final String REMOVE_BLACK_SOURCE_FROM_OPERATOR = "fromOperator";
		public static final String REMOVE_BLACK_SOURCE_FROM_USER_APPEAL = "fromUserAppeal";
		
		public static final String REMOVE_ALL = "removeAll";
		public static final String NEED_SEND_SMS = "needSendSms";
	
		public static final String CODE_TYPE_LOCK_DEFAULT_DAY = "SYS_BLACK_LIST@LOCK_DEFAULT_DAY";
		public static final int LOCK_DEFAULT_DAY = 14;
		public static final String RET_OTHER_BLACKS = "otherBlacks";
		
		public static final long ADD_TEMPLATE_ID = 1000000095L;
		public static final long LOCK_TEMPLATE_ID = 1000000096L;
		public static final long UNLOCK_TEMPLATE_ID = 1000000097L;
	}

	public static class VisitTaskConstant {
		// 访问类型
		public static final int V_TYPE_1 = 1;// 新用户注册
		public static final int V_TYPE_2 = 2;// 老用户回访
		public static final int V_TYPE_3 = 3;// 问卷调查
		public static final int V_TYPE_4 = 4;// 投诉处理
		public static final String CODE_TYPE_V_TYPE = "V_TYPE";// 访问类型枚举配置，对应sys_static_data表的code_type字段值
		
		// 状态
		public static final int V_STS_0 = 0;// 初始
		public static final int V_STS_1 = 1;// 已完成
		public static final int V_STS_2 = 2;// 持续跟进
		public static final String CODE_TYPE_V_STS = "V_STS";// “状态枚举”配置，对应sys_static_data表的code_type字段值
		
		// 渠道类型
		public static final int CHANNEL_TYPE_1 = 1;// APP-ANDROID
		public static final int CHANNEL_TYPE_2 = 2;// WEB
		public static final int CHANNEL_TYPE_3 = 3;// APP-IOS
		public static final int CHANNEL_TYPE_4 = 4;// WeiXin
		public static final String CODE_TYPE_CHANNEL_TYPE = "VISIT_TASK@CHANNEL_TYPE";// “渠道类型”枚举配置，对应sys_static_data表的code_type字段值

		// 资料是否完善
		public static final int IS_COMPLETE_1 = 1;// 资料不完善
		public static final int IS_COMPLETE_2 = 2;// 资料完善
		public static final String CODE_TYPE_IS_COMPLETE = "VISIT_TASK@IS_COMPLETE";// “资料是否完善”枚举配置，对应sys_static_data表的code_type字段值
		
		// 是否下载APP
		public static final int IS_DOWNLOAD_APP_0 = 0;// 未下载
		public static final int IS_DOWNLOAD_APP_1 = 1;// 已下载
		public static final String CODE_TYPE_IS_DOWNLOAD_APP = "VISIT_TASK@IS_DOWNLOAD_APP";
		
		// 是否接通
		public static final int IS_CALL_SUCCESS_0 = 0;// 未接通
		public static final int IS_CALL_SUCCESS_1 = 1;// 已接通
		public static final String CODE_TYPE_IS_CALL_SUCCESS = "VISIT_TASK@IS_CALL_SUCCESS";
		
		public static final int SPECIAL_USER_FLAG_0 = 0;// 普通用户
		public static final int SPECIAL_USER_FLAG_1 = 1;// 重点用户 
		public static final String CODE_TYPE_SPECIAL_USER_FLAG = "VISIT_TASK@SPECIAL_USER_FLAG";
		
	}
	
	public static class SYS_STATE_DESC {
		/** 无效 **/
		public static final int INVALID = 0;
		/** 有效 **/
		public static final int EFFECTive = 1;
	}
	
	public static class CyCarSourceConstant {
		public static final String KEY_DATA_LENGTH = "dataLength"; 
		public static final String KEY_CAR_SOURCE_ID = "carSourceId";
		public static final String KEY_CAR_ID = "carId";
		public static final String KEY_DELIVERY_BEGIN = "deliveryBegin";
		public static final String KEY_PUB_STS = "pubSts";
		public static final String KEY_EXPIRE_DATE = "expireDate";
		
		public static final int PUB_STS_0 = 0;// 求火种
		public static final int PUB_STS_3 = 3;// 已到达
		public static final int PUB_STS_4 = 4;// 已完成
		public static final int PUB_STS_5 = 5;// 已取消
	}

	public static class HyGoodsPublishConstant {
		public static final String KEY_DATA_LENGTH = "dataLength"; 
		public static final String KEY_GOODS_ID = "goodsId";
		public static final String KEY_DELIVERY_BEGIN = "deliveryBegin";
		public static final String KEY_PUB_STS = "pubSts";
		public static final String KEY_EXPIRE_DATE = "expireDate";
		
		public static final int PUB_STS_0 = 0;// 求车中
		public static final int PUB_STS_3 = 3;// 已到达
		public static final int PUB_STS_4 = 4;// 已完成
		public static final int PUB_STS_5 = 5;// 已取消
		
		public static final int NOT_BIDD=0;
    	/**货源有竞价**/
    	public static final int IS_BIDD=1;
	}
	
	public static class GoodsCenterConstant {
		public static final int STS_1 = 1;// 1：待审核
		public static final int STS_2 = 2;// 2：审核通过
		public static final int STS_3 = 3;// 3：审核不通过
		
		public static final String CENTER_ID = "centerId";
		public static final String CENTER_NAME = "centerName";
		public static final String CHECK_DESC = "checkDesc";
		public static final String VERIFY_FLAG = "verifyFlag";
		public static final String STS_NAME = "stsName";
		public static final String PROVINCE_COUNTY_REGION = "provinceCountyRegion";
		public static final String CREATE_DATE = "createDate";
		public static final String VERIFY_FLAG_SUCCESS = "verifyFlagSuccess";
		public static final String VERIFY_FLAG_FAIL = "verifyFlagFail";
		public static final String SYS_GOODS_CENTER_STS = "SYS_GOODS_CENTER@STS";
		public static final String RET_KEY_GOODS_CENTER_INFO = "goodsCenterInfo";
	}
	
	public static class SysSmsSendConstant {
		public static final int SMS_TYPE_1 = 1;// 活动公告
		public static final int SMS_TYPE_2 = 2;// 进度通知
		public static final int SMS_TYPE_3 = 3;// 操作确认
		public static final int SMS_TYPE_4 = 4;// 短信验证
		
		/** 订单 **/
		public static final int OBJ_TYPE_1 = 1;
		/** 货源 **/
		public static final int OBJ_TYPE_2 = 2;
		/** 车源 **/
		public static final int OBJ_TYPE_3 = 3;
		/** 待支付 **/
		public static final int OBJ_TYPE_4 = 4;
		/** 充值 **/
		public static final int OBJ_TYPE_5 = 5;
		/** 提现 **/
		public static final int OBJ_TYPE_6 = 6;
		/** 邀请 **/
		public static final int OBJ_TYPE_7 = 7;
		/** 通知 **/
		public static final int OBJ_TYPE_8 = 8;
		/** 保证金 **/
		public static final int OBJ_TYPE_9 = 9;
		/** 关注线路 **/
		public static final int OBJ_TYPE_10 = 10;

		public static final String CODE_TYPE_MARKETING_SMS_TEMPLATE = "MARKETING_SMS_TEMPLATE";
		public static final String CODE_TYPE_MARKETING_NOT_INPUT_SMS_PARAM_CODE = "MARKETING_NOT_INPUT_SMS_PARAM_CODE";// 不需要输入的短信参数
		public static final String CODE_TYPE_SMS_PLATFORM_INFO = "SMS_PLATFORM_INFO";
		
		public static final int MARKETING_SMS_SEND_FLAG_0 = 0;//未发送
		public static final int MARKETING_SMS_SEND_FLAG_1 = 1;// 已发送
		
		public static final int MARKETING_SMS_SRC_TYPE_0 = 0;// 短信来源；0：单条发送, 1：批量发送, 2：TASK批量发送
		public static final int MARKETING_SMS_SRC_TYPE_1 = 1;// 短信来源；0：单条发送, 1：批量发送, 2：TASK批量发送
		public static final int MARKETING_SMS_SRC_TYPE_2 = 2;// 短信来源；0：单条发送, 1：批量发送, 2：TASK批量发送
		
		// 亲爱的${userName}用户，您的[握物流]平台注册验证码为${CODE},请尽快输入。
		public static final long TEMPLATE_ID_1000000001 = 1000000001L;
		// 亲爱的${userName}用户，您的[握物流]平台密码重置验证码为${CODE},请尽快输入。
		public static final long TEMPLATE_ID_1000000005 = 1000000005L;
		// ${content}
		public static final long TEMPLATE_ID_1000000080 = 1000000080L;
		// 尊敬的${USER_NAME}用户，您的帐号为：${LOGIN_ACCT}，密码：${PASSWORD}！打开地址：http://z.wo56.com/down_new.html 或搜”握物流“下载。需要修改密码可通过“个人中心-系统设置-重置密码”操作。用握物流，发货调车不用愁，咨询热线4008250056。退订回复TD
		public static final long TEMPLATE_ID_1000000099 = 1000000099L;
		// 尊敬的车主：平台每天更新大量货源，无论走到哪，自动为您推荐周边货源。请及时登录上传证件，方便查看联系方式，点击“竞价货源”找货。http://z.wo56.com/down_new.html 或应用市场搜索“握物流”下载APP。热线4008250056。
		public static final long TEMPLATE_ID_1000000100 = 1000000100L;// 短信回访
		// 亲爱的${USER_NAME}用户，您已成为平台会员，用户名:${userName},密码:${password}，请尽快修改密码，搜“握物流“下载APP或直接登录WWW.WO56.COM。咨询热线4008250056。退订回复TD
		public static final long TEMPLATE_ID_1000000065 = 1000000065L;// 用户注册短信
		// 亲爱的${USER_NAME}用户，您的用户资料已经审核通过，请知悉。
		public static final long TEMPLATE_ID_1000000075 = 1000000075L;// 用户审核通过短信
		// 亲爱的${USER_NAME}用户，您的账户因资料不齐全未通过认证，为保证顺利查看货源/车源信息，请及时上传身份证、个人头像等真实信息。详情咨询4008250056。
		public static final long TEMPLATE_ID_1000000114 = 1000000114L;// 用户审核不通过短信
		
		// 亲爱的${USER_NAME}用户，恭喜您成为握物流智能匹配平台体验用户，用户名:${userName},密码:${password}。有了握物流，找货找车不用愁。APP下载http://z.wo56.com/down_new.html或应用市场搜索“握物流”。请及时登陆修改密码，握物流祝您工作顺利
		public static final long TEMPLATE_ID_1000000094 = 1000000094L;
		
		// 亲爱的${userName}用户,您已成功发布货${goodsName}从${goodsSource}到${goodsDes}的货源信息,更多详情可点击http://z.wo56.com/down.html。
		public static final long TEMPLATE_ID_1000000046 = 1000000046L;
		
		// 亲爱的[${userName}]用户,您的订单[${orderId}]已成功取消,如有疑问请联系客服。
		public static final long TEMPLATE_ID_1000000113 = 1000000113L;
	}
	
	public static class AccountIntegral {// 账户积分
		public static final long INTEGRAL_BUSINESSID_21000011 = 21000011L;
		public static final String VERIFY_INFORMATION_CODE_TYPE = "VERIFY_INFORMATION_CODE_TYPE";// 资料审核静态参数配置
		public static final String VERIFY_INFORMATION_USER = "VERIFY_INFORMATION_USER";// 用户资料审核
		public static final String VERIFY_INFORMATION_GOODS_CENTER = "VERIFY_INFORMATION_GOODS_CENTER";// 配货站审核
		public static final String VERIFY_INFORMATION_VEHICLE = "VERIFY_INFORMATION_VEHICLE";// 车辆资料审核
		public static final String VERIFY_INFORMATION_USER_REMARK = "用户审核通过积分赠送";
		public static final String VERIFY_INFORMATION_VEHICLE_REMARK = "车辆审核通过积分赠送";
		public static final String VERIFY_INFORMATION_GOODS_CENTER_REMARK = "配货站审核通过积分赠送";
		public static final long VERIFY_INFORMATION_USER_TEMPID = 1000000087L;// 用户审核通过，赠送积分对应的短信提醒
		public static final long VERIFY_INFORMATION_VEHICLE_TEMPID = 1000000088L;// 车辆审核通过，赠送积分对应的短信提醒
		public static final long VERIFY_INFORMATION_GOODS_CENTER_TEMPID = 1000000089L;// 配货站审核通过，赠送积分对应的短信提醒
	}
	
	public static class UserDataInfoConstant {
		public static final int USER_TYPE_3 = 3;// (个体)车主/司机
		public static final int USER_TYPE_4 = 4;// (公司)货主
		public static final int USER_TYPE_5 = 5;// (公司)专线
		public static final int USER_TYPE_6 = 6;// 物流公司/园区
		public static final int USER_TYPE_7 = 7;// (公司)车队/专线','物流车队
		public static final int USER_TYPE_9 = 9;// (个体)货主
		
		public static final int VERIFY_STS_1 = 1;// 未认证
		public static final int VERIFY_STS_2 = 2;// 认证中
		public static final int VERIFY_STS_3 = 3;// 认证未通过
		public static final int VERIFY_STS_4 = 4;// 虚拟用户
		public static final int VERIFY_STS_5 = 5;// 认证通过
		
		public static final int ID_TYPE_1 = 1;// 身份证
		public static final int ID_TYPE_2 = 2;// 驾驶证
		
		public static final int IS_PERFECT_INFO_0 = 0;// 资料是否完善－否
		public static final int IS_PERFECT_INFO_1 = 1;// 资料是否完善-是
		public static final String CODE_TYPE_USER_DATA_INFO_IS_PERFECT_INFO = "USER_DATA_INFO@IS_PERFECT_INFO";
		public static final String CODE_TYPE_USER_TYPE = "USER_TYPE";
		public static final String CODE_TYPE_VERIFY_STS = "VERIFY_STS";
		public static final String CODE_TYPE_ID_TYPE = "USER_DATA_INFO@ID_TYPE";
		public static final String SMS_BLACKLIST_PUSH_TYPE = "SMS_BLACKLIST@PUSH_TYPE";
		
		public static final String DEFAULT_PASSWORD = "888888";
		public static final String USER_ID = "userId";
		
	}
	
	public static class RetInfo {
		public static final String RESULT_CODE = "resultCode";
		public static final String RESULT_CODE_SUCCESS = "1"; // 业务受理成功返回码
		public static final String RESULT_CODE_FAILURE = "0";// 业务受理失败返回码
		public static final String RESULT_CODE_SESSION_FAILURE = "-1";// 登录失效返回码
		public static final String RESULT_MESSAGE = "resultMessage";
		public static final String RESULT_MESSAGE_SUCCESS_DEFAULT_MESSAGE = "业务受理成功";
		public static final String RESULT_MESSAGE_FAILURE_DEFAULT_MESSAGE = "业务受理失败";
		public static final String RESULT_MESSAGE_SESSION_FAILURE_DEFAULT_MESSAGE = "登录会话失效，请重新登录";
		public static final String RESULT_DATA = "resultData";
	}

	public static class ChartInfo {
		public static final String CHECK_INFO = "checkInfo"; // 统计校验信息
		public static final String CHART_INFO = "chartInfo";// 统计图表信息
		public static final String SERIES_INFO = "seriesInfo";// 统计项信息
		public static final String TABLE_INFO = "tableInfo";// 统计项信息
		public static final String DETAIL_INFO = "detailInfo";// 详细信息
		public static final int LOAD_DATA_TYPE_3 = 3;// 用于对table数据的统计 
		public static final String EXPORT_TABLE_DATA_INFO = "exportTableDataInfo";// 统计项信息
		
		public static final String CHART_ACTIVE = "chartActive";
		public static final String CHART_ID = "chartId";
		public static final String CHART_NAME = "chartName";
		public static final String CHART_TYPE = "chartType";
		public static final String CHART_SUB_TYPE = "subType";// 详细信息的时候会用到
		public static final String CHART_TITLE = "chartTitle";
		public static final String CHART_SUBTITLE = "chartSubtitle";
		public static final String CHART_YAXIS_TITLE = "chartYaxisTitle";
		public static final String CHART_TOOLTIP_VALUE_SUFFIX = "chartTooltipValueSuffix";
		public static final String CHART_TOOLTIP_VALUE_POINT_FORMAT = "chartTooltipValuePointFormat";
		public static final String CHART_IS_SHOW_LEGEND = "showLegend";
		public static final String CHART_LEGEND_LAYOUT = "legendLayout";
		public static final String CHART_LEGEND_ALIGN = "legendAlign";
		public static final String CHART_LEGEND_VERTICALALIGN = "legendVerticalAlign";
		public static final String CHART_SERIES_ID=  "seriesId";
		public static final String CHART_IMG = "chartImg";
		public static final String CHART_DESC = "chartDesc";
		public static final String CODE_TYPE_TABLE_FOOTER_SQL_CONFIG = "TABLE_FOOTER_SQL_CONFIG";// Table页脚sql配置
		public static final String SERIES_NAME = "name";
		public static final String SERIES = "data";
		public static final String CATEGORIES = "categories";
		public static final String USER_OPTIONS = "userOptions";
		public static final String EVENT_NAME = "eventName";
		public static final String EVENT_METHOD_NAME = "eventMethodName";
		public static final String ASSOCIATE_CHART_IDS = "associateChartIds";
		public static final String ASSOCIATE_CHART_NAMES = "associateChartNames";
		public static final String MACRO_VARIABLES_PARAMS = "macroVariablesParams";
		public static final String SQL_QUERY_PARAMS = "sqlQueryParams";
		public static final String HANDLER_PARAMS = "handlerParams";
		public static final String POINT_USER_OPTIONS = "pointUserOptions";
		public static final String CHART_SERIES_RANDOM_KEY = "chartSeriesRandomKey";

		// http请求参数部分
		public static final String HTTP_REQUEST_PARAM_QUERY_MONTH = "queryMonth";
		public static final String HTTP_REQUEST_PARAM_SELECTED_ORG = "selectedOrg";
		public static final String HTTP_REQUEST_PARAM_SELECTED_TENANT = "selectedTenant";
		public static final String HTTP_REQUEST_PARAM_MULTI_MONTH_COMPARE = "multiMonthCompare";
		public static final String HTTP_REQUEST_PARAM_MULTI_MONTH_STATISTIC = "multiMonthStatistic";
		public static final String HTTP_REQUEST_PARAM_XINDEX = "xIndex";
		public static final String HTTP_REQUEST_PARAM_PAGE_SIZE = "pageSize";
		public static final String HTTP_REQUEST_PARAM_CURRENT_PAGE = "currentPage";
		public static final String HTTP_REQUEST_PARAM_ROW_DATA = "rowData";
		
		// ************************** Sql参数部分 ************************** //
		public static final String SQL_QUERY_PARAM_YYYY_START = "yyyyStart";
		public static final String SQL_QUERY_PARAM_YYYY_END = "yyyyEnd";
		public static final String SQL_QUERY_PARAM_YYYYMM_START = "yyyyMMStart";
		public static final String SQL_QUERY_PARAM_YYYYMM_END = "yyyyMMEnd";
		public static final String SQL_QUERY_PARAM_ORG_ID = "orgId";
		public static final String SQL_QUERY_PARAM_ROOT_ORG_ID = "rootOrgId";
		public static final String SQL_QUERY_PARAM_TENANT_ID = "tenantId";
		public static final String SQL_QUERY_PARAM_CURRENT_USER_ORG_ID = "currentUserOrgId";
		public static final String SQL_QUERY_PARAM_CURRENT_MONTH_START_TIME = "currentMonthStartTime";// 当月开始时间
		public static final String SQL_QUERY_PARAM_QUERY_MONTH_START_TIME = "queryMonthStartTime";// 查询月开始时间
		public static final String SQL_QUERY_PARAM_QUERY_MONTH_END_TIME = "queryMonthEndTime";// 查询月结束时间
		public static final String SQL_QUERY_PARAM_QUERY_YEAR_START_TIME = "queryYearStartTime";// 查询年开始时间
		public static final String SQL_QUERY_PARAM_QUERY_YEAR_END_TIME = "queryYearEndTime";// 查询年结束时间
		public static final String SQL_QUERY_PARAM_QUERY_QUARTER_START_TIME = "queryQuarterStartTime";// 查询季度开始时间
		public static final String SQL_QUERY_PARAM_QUERY_QUARTER_END_TIME = "queryQuarterEndTime";// 查询季度结束时间
		public static final String SQL_QUERY_PARAM_QUERY_DAY_START_TIME = "queryDayStartTime";// 查询天开始时间
		public static final String SQL_QUERY_PARAM_QUERY_DAY_END_TIME = "queryDayEndTime";// 查询天结束时间
		public static final String SQL_QUERY_PARAM_QUERY_LAST_MONTHS_START_TIME = "queryLastMonthsStartTime";// 查询最近几个月开始时间
		public static final String SQL_QUERY_PARAM_QUERY_LAST_MONTHS_END_TIME = "queryLastMonthsEndTime";// 查询最近几个月结束时间
		public static final String SQL_QUERY_PARAM_CUSTOM_QUERY_SCOPE_START_TIME = "customQueryStartTime";// 自定义查询开始时间
		public static final String SQL_QUERY_PARAM_CUSTOM_QUERY_SCOPE_END_TIME = "customQueryEndTime";// 自定义查询结束时间
		
		public static final String SQL_QUERY_PARAM_QUERY_MONTH = "queryMonth";// 查询月
		public static final String SQL_QUERY_PARAM_STATE = "state";// 状态
		public static final String SQL_QUERY_PARAM_DATA_SOURCE = "dataSource";// 数据来源
		public static final String SQL_QUERY_PARAM_SOURCE_PROVINCE = "sourceProvince";
		public static final String SQL_QUERY_PARAM_PARAMS_MIN_COUNT = "minCount";
		public static final String SQL_QUERY_PARAM_PARAMS_MAX_COUNT = "maxCount";
		
		// ************************** Sql参数部分 ************************** //
		
		// ************************** “宏变量”替换部分对应的KEY************************** //
		public static final String YYYY_CH = "YYYY_CH";
		public static final String YYYY_MONTH_CH = "YYYY_MONTH_CH";
		public static final String YYYY_EN = "YYYY_EN";
		public static final String YYYY_MONTH_EN = "YYYY_MONTH_EN";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_MONTH_CH = "queryMonthCh";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_MONTH_EN = "queryMonthEn";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_MONTH_DAY_CH = "queryMonthDayCh";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_MONTH_DAY_EN = "queryMonthDayEn";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_LAST_MONTHS_START_TIME_CH = "queryLastMonthsStartTimeCh";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_LAST_MONTHS_START_TIME_EN = "queryLastMonthsStartTimeEn";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_LAST_MONTHS_END_TIME_CH = "queryLastMonthsEndTimeCh";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_LAST_MONTHS_END_TIME_EN = "queryLastMonthsEndTimeEn";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_ORG_NAME = "queryOrgName";
		
		public static final String MACRO_VARIABLES_PARAMS_CUSTOM_QUERY_START_TIME_EN = "customQueryStartTimeEn";
		public static final String MACRO_VARIABLES_PARAMS_CUSTOM_QUERY_START_TIME_CH = "customQueryStartTimeCh";
		public static final String MACRO_VARIABLES_PARAMS_CUSTOM_QUERY_END_TIME_EN = "customQueryEndTimeEn";
		public static final String MACRO_VARIABLES_PARAMS_CUSTOM_QUERY_END_TIME_CH = "customQueryEndTimeCh";

		// Sql宏变量替换相关
		public static final String MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ORG_O_POINT = "queryConditionOrg_suffix_o";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ORG_OBJ = "queryConditionOrg_obj";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ORG = "queryConditionOrg";
		
		public static final String MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ORGS = "queryConditionOrgs";
		
		public static final String MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ROOT_ORG = "queryConditionRootOrg";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ROOT_ORG_O_POINT = "queryConditionRootOrg_suffix_o";

		// 租户条件对应的宏
		public static final String MACRO_VARIABLES_PARAMS_QUERY_CONDITION_TENANTID_O = "queryConditionTenantId_o";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_CONDITION_TENANTID_OBJECT = "queryConditionTenantId_obj";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_CONDITION_TENANTID = "queryConditionTenantId";
		
		public static final String MACRO_VARIABLES_PARAMS_CURRENT_MONTH_YYYYMMM = "currentMonthYYYYMMM";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_MONTH_YYYYMMM = "queryMonthYYYYMMM";
		public static final String MACRO_VARIABLES_PARAMS_USER_STATE_NAME = "userStateName";
		public static final String MACRO_VARIABLES_PARAMS_SOURCE_PROVINCE_NAME = "sourceProvinceName";
		public static final String MACRO_VARIABLES_PARAMS_DATA_SOURCE_NAME = "dataSourceName";
		public static final String MACRO_VARIABLES_PARAMS_SELECTED_XAXIS_NAME = "selectedXAxisName";
		public static final String QUERY_CONDTION_GT_MIN_COUNT = "QUERY_CONDTION_GT_MIN_COUNT";
		public static final String QUERY_CONDTION_GT_MIN_COUNT_VALUE = " AND COUNT >:minCount ";
		
		public static final String QUERY_CONDTION_LE_MAX_COUNT = "QUERY_CONDTION_LE_MAX_COUNT";
		public static final String QUERY_CONDTION_LE_MAX_COUNT_VALUE = " AND COUNT <=:maxCount ";

		public static final String MACRO_VARIABLES_PARAMS_MIN_COUNT = "minCount";
		public static final String MACRO_VARIABLES_PARAMS_MAX_COUNT = "maxCount";
		public static final String MACRO_VARIABLES_PARAMS_PAGE_LIMIT = "PAGE_LIMIT";
		public static final String MACRO_VARIABLES_PARAMS_OPER_PAGE_NAME = "OPER_PAGE_NAME";
		public static final String MACRO_VARIABLES_PARAMS_TYPE_NAME = "TYPE_NAME";
		public static final String MACRO_VARIABLES_PARAMS_TYPE_VALUE = "TYPE_VALUE";
		public static final String MACRO_VARIABLES_PARAMS_PIE_SECTOR_NAME = "pieSectorName";
		public static final String MACRO_VARIABLES_PARAMS_PIE_SECTOR_VALUE = "pieSectorValue";
		
		// ************************** “宏变量”替换部分对应的KEY************************** //
		
		// *************** 统计图表类型 *************** //
		public static final int CHART_TYPE_1 = 1;// 1:曲线图
		public static final int CHART_TYPE_2 = 2;// 2:3D柱状图
		public static final int CHART_TYPE_3 = 3;// 3:饼图
		@Deprecated
		public static final int CHART_TYPE_4 = 4;// 4:双饼图
		public static final int CHART_TYPE_5 = 5;// 5:3D饼图
		@Deprecated
		public static final int CHART_TYPE_6 = 6;// 6:用户存活率图表(此类型图表特殊，后续考虑可否公用)
		public static final int CHART_TYPE_7 = 7;// 7:table表格
		public static final int CHART_TYPE_8 = 8;// 8:详细数据
		// *************** 统计图表类型 *************** //
		
		// *************** 加载数据方式 *************** //
		public static final int LOAD_DATA_TYPE_0 = 0;// 加载数据方式，通过实现加载
		public static final int LOAD_DATA_TYPE_1 = 1;// 在饼图的时候设置为1，表示用总数减去其他的统计项
		public static final int LOAD_DATA_TYPE_2 = 2;// 原本一条sys_statistic_series只可能返回一条线，为了满足可以返回多条数据，特此加此中类型，详细见AbstractStatisticChartHandler的getChartSeriesData方法

		// *************** 统计图表类型 *************** //
		
		// *************** Hander处理类中需要的参数 *************** //
		public static final String HANDLER_PARAM_QUERY_DATE = "HANDLER_PARAM_QUERY_DATE";
		public static final String HANDLER_PARAM_CURRENT_DATE = "HANDLER_PARAM_CURRENT_DATE";
		public static final String HANDLER_PARAM_LAST_MONTHS_NUMBER = "HANDLER_PARAM_LAST_MONTHS_NUMBER";
		public static final String HANDLER_PARAM_LATITUDE_TYPE = "HANDLER_PARAM_LATITUDE_TYPE";
		public static final String HANDLER_PARAM_CUSTOM_QUERY_START_TIME = "HANDLER_PARAM_CUSTOM_QUERY_START_TIME";
		public static final String HANDLER_PARAM_CUSTOM_QUERY_END_TIME = "HANDLER_PARAM_CUSTOM_QUERY_END_TIME";
		public static final String HANDLER_PARAM_MULTI_MONTH_COMPARE = "HANDLER_PARAM_MULTI_MONTH_COMPARE";
		public static final String HANDLER_PARAM_MULTI_MONTH_STATISTIC = "HANDLER_PARAM_MULTI_MONTH_STATISTIC";
		public static final String HANDLER_PARAM_TABLE_ROW_COL_MAPPING = "tableRowColMapping";
		// *************** Hander处理类中需要的参数 *************** //
		
		// *************** 饼图部分常量 *************** // 
		public static final String PIE_NAME = "name";
		public static final String PIE_Y = "y";
		public static final String PIE_SLICED = "sliced";
		public static final String PIE_COLOR = "color";
		public static final String PIE_SELECTED_TRUE = "true";
		public static final String PIE_SELECTED = "selected";
		public static final String PIE_TYPE = "type";
		public static final String PIE = "pie";
		public static final String PIE_DATA = "data";
		public static final String PIE_SIZE = "size";
		public static final String PIE_INNER_SIZE = "innerSize";

		public static final String PIE_HANDLER_RESULT_ITEM_COUNT = "PIE_HANDLER_RESULT_ITEM_COUNT";// 双饼图时，需要给其他统计传递统计数量
		public static final String DOUBLE_PIE_SERIES_NAME = "DOUBLE_PIE_SERIES_NAME";// 用来配置双饼图内圈和外圈的提示
		// *************** 饼图部分常量 *************** // 

		
		// *************** 统计纬度部分 *************** //
		public static final String LATITUDE_LATITUDE_ID = "latitudeId";
		public static final String LATITUDE_CHART_ID = "latitudeChartId";
		public static final String LATITUDE_ASSOCIATE_CHART_ID = "associateChartId";
		public static final String LATITUDE_LATITUDE_NAME = "latitudeName";
		public static final String LATITUDE_LATITUDE_TYPE = "latitudeType";
		public static final String LATITUDE_IS_DEFAULT = "isDefault";

		public static final String RET_CHART_ASSOCIATE_LATITUDE_INFO = "associateLatitudeInfo";
		public static final String RET_IS_SHOW_LATITUDE = "isShowLatitude";
		public static final String RET_IS_SHOW_ORG_SELECT = "isShowOrgSelect";
		public static final String RET_IS_SHOW_TENANT_SELECT = "isShowTenandSelect";
		public static final String RET_IS_SHOW_LATITUDE_TYPE_SELECT = "isShowLatitudeTypeSelect";
		public static final String RET_MULTI_MONTH_STATISTIC = "multiMonthStatistic";
		public static final String RET_MULTI_MONTH_COMPARE = "multiMonthCompare";

		public static final String RET_DEFAULT_LATITUDE_ID = "defaultLatitudeId";
		public static final String RET_DEFAULT_LATITUDE_TYPE = "defaultLatitudeType";
		
		public static final String RET_TABLE_DATA_TOTAL = "tableDataTotal";
		public static final String RET_TABLE_ROW = "row";
		public static final String RET_TABLE_COL_VALUE = "value";
		public static final String RET_TABLE_COL_NAME = "name";
		public static final String RET_TABLE_RESULT_DATA = "tableResultData";
		public static final String RET_TABLE_FOOTS = "tableFoots";
		// *************** 统计纬度部分 *************** //
		
		// *************** 统计纬度部分--纬度类型 *************** //
		public static final int LATITUDE_TYPE_1 = 1;// 月/年
		public static final int LATITUDE_TYPE_2 = 2;// 季度/年
		public static final int LATITUDE_TYPE_3 = 3;// 天/月
		public static final int LATITUDE_TYPE_4 = 4;// 小时/天
		public static final int LATITUDE_TYPE_5 = 5;// 近半年
		public static final int LATITUDE_TYPE_6 = 6;// 组织
		public static final int LATITUDE_TYPE_7 = 7;// 按范围(天)查询
		public static final int LATITUDE_TYPE_8 = 8;// 多月按日统计
		public static final int LATITUDE_TYPE_9 = 9;// 多月对比

		// *************** 统计纬度部分--纬度类型 *************** //
		public static final String CODE_TYPE_USER_RETENTION_SHOW_COLS = "USER_RETENTION_SHOW_COLS";// 配置留存率图表需要展示的列
		public static final String CODE_TYPE_USER_ACTIVITY_SCOPE = "USER_ACTIVITY_SCOPE";// 
		public static final String CODE_TYPE_CHART_DETAIL_TABLE_HEAD = "CHART_DETAIL_TABLE_HEAD";// table表格时，配置table的头
		public static final String CODE_TYPE_MULTI_TYPE_PIE = "CHART_MULTI_TYPE_PIE";// 一个圆饼图对应的区域
		public static final String CODE_TYPE_SERIES_START_YEAR_MONTH = "SERIES_START_YEAR_MONTH";// 图表统计时，年月份表的开始月份，避免不必要的数据加载
		public static final String CODE_TYPE_FOOTER_TABLE_COL_VALUE = "FOOTER_TABLE_COL_VALUE";// table表格时，获取页脚数据。
	}

	public static class MacroVariables {
		public static final String PREFIX_CHAR = "${";
		public static final String SUFFIX_CHAR = "}";
	}

	public static class Quarter {
		public static final int QUARTER_NUMBER_1 = 1;
		public static final String QUARTER_NUMBER_1_CH = "一季度";
		public static final int QUARTER_NUMBER_2 = 2;
		public static final String QUARTER_NUMBER_2_CH = "二季度";
		public static final int QUARTER_NUMBER_3 = 3;
		public static final String QUARTER_NUMBER_3_CH = "三季度";
		public static final int QUARTER_NUMBER_4 = 4;
		public static final String QUARTER_NUMBER_4_CH = "四季度";
	}
	
	public static class PaymentIntfConstant {
		public static final String CODE_TYPE_PAY_CHANNEL = "PAYMENT_INTF@PAY_CHANNEL";
		public static final String CODE_TYPE_RESP_CODE = "PAYMENT_INTF@RESP_CODE";
	}
	
	public static class SysOperLogConstant {
		public static final String CODE_TYPE_SYS_OPER_LOG_OPER_CLASS = "SYS_OPER_LOG@OPER_CLASS";
		public static final String CODE_TYPE_SYS_OPER_LOG_OBJECT_TYPE = "SYS_OPER_LOG@OBJECT_TYPE";
		public static final String CODE_TYPE_SYS_OPER_LOG_CHANNEL_TYPE = "SYS_OPER_LOG@CHANNEL_TYPE";
	}
	
	public static class JoinIntentionConstant {
		public static final String CODE_TYPE_JOIN_INTENTION_APPLICATION_STATUS = "JOIN_INTENTION@APPLICATION_STATUS";
		public static final String CODE_TYPE_JOIN_INTENTION_JOIN_TYPE = "JOIN_INTENTION@JOIN_TYPE";
	}
	
	/************************* 权限/角色/实体/菜单 **Start ***********************/
	public static class GrantConstant {
		public static final String STATE = "state";
		public static final int STATE_0 = 0;// 
		public static final int STATE_1 = 1;
		public static final String CODE_TYPE_GRANT_STATE = "GRANT_STATE";

		public static final String OBJECT_TYPE = "objectType";
		public static final String OBJECT_ID = "objectId";
		
		public static final String SYS_OBJECT_GRANT_CACHE_KEY = "allObjectGrant";// sys_object_grant表数据全量缓存KEY
		public static final String SYS_ROLE_CACHE_KEY = "allRole";// sys_role表数据全量缓存KEY
		public static final String SYS_ENTITY_CACHE_KEY = "allRole";// sys_entity表数据全量缓存KEY
		public static final String SYS_ROLE_OPER_REL_CACHE_KEY = "allRoleOperRel";// sys_role_oper_rel表数据全量缓存KEY

		public static final String CODE_TYPE_SYS_OBJECT_GRANT_OBJECT_TYPE = "SYS_OBJECT_GRANT@OBJECT_TYPE";
		public static final int OBJECT_TYPE_1 = 1;// 角色
		public static final int OBJECT_TYPE_2 = 2;// 组织
		public static final int OBJECT_TYPE_3 = 3;// 操作员
		
		public static final String CODE_TYPE_SYS_OBJECT_GRANT_IS_PERMIT = "SYS_OBJECT_GRANT@IS_PERMIT";
		public static final int IS_PERMIT_0 = 0;// 禁止
		public static final int IS_PERMIT_1 = 1;// 允许
		
		public static final String CODE_TYPE_SYS_ENTITY_ENTITY_TYPE = "SYS_ENTITY@ENTITY_TYPE";
		public static final int SYS_ENTITY_ENTITY_TYPE_MENU = 1;// 菜单权限
		public static final int SYS_ENTITY_ENTITY_TYPE_OPERATE = 2;// 实体权限
	}

	public static class EntityIdConstant {
		public static final int SYS_ENTITY_ENTITY_TYPE_MENU = 1;// 菜单权限
		public static final int SYS_ENTITY_ENTITY_TYPE_OPERATE = 2;// 实体权限
		
		public static final int ENTITY_ID_10000000 = 10000000;// 物流中心--菜单实体
		public static final int ENTITY_ID_10000001 = 10000001;// 用户管理--菜单实体
		public static final int ENTITY_ID_10000002 = 10000002;// 车辆资料管理--菜单实体
		public static final int ENTITY_ID_10000003 = 10000003;// 货源管理--菜单实体
		public static final int ENTITY_ID_10000004 = 10000004;// 车源管理--菜单实体
		public static final int ENTITY_ID_10000005 = 10000005;// 订单管理--菜单实体
		public static final int ENTITY_ID_10000006 = 10000006;// 专线管理--菜单实体
		public static final int ENTITY_ID_10000007 = 10000007;// 取消所有货源权限
		public static final int ENTITY_ID_10000008 = 10000008;// 取消本组织下货源权限
		public static final int ENTITY_ID_10000009 = 10000009;// 给本组织下用户新增货源权限
		public static final int ENTITY_ID_10000010 = 10000010;// 给所有用户新增货源权限'
		public static final int ENTITY_ID_10000011 = 10000011;// 自动补齐功能，只能补全本组织下的用户
		public static final int ENTITY_ID_10000012 = 10000012;// 自动补齐功能，可以补齐所有的用户
		public static final int ENTITY_ID_10000013 = 10000013;// 给本组织下用户新增专线权限
		public static final int ENTITY_ID_10000014 = 10000014;// 给所有用户新增专线权限
		public static final int ENTITY_ID_10000015 = 10000015;// 物流跟踪--菜单实体
		public static final int ENTITY_ID_10000016 = 10000016;// 修改所有货源权限
		public static final int ENTITY_ID_10000017 = 10000017;// 修改本组织货源权限
		public static final int ENTITY_ID_10000018 = 10000018;// 修改所有专线权限
		public static final int ENTITY_ID_10000019 = 10000019;// 修改本组织专线权限
		public static final int ENTITY_ID_10000020 = 10000020;// 取消所有专线权限
		public static final int ENTITY_ID_10000021 = 10000021;// 取消本组织专线权限
		public static final int ENTITY_ID_10000022 = 10000022;// 身份验证菜单权限
		public static final int ENTITY_ID_10000023 = 10000023;// 配货站管理菜单权限
		public static final int ENTITY_ID_10000024 = 10000024;// 配货站审核权限
		public static final int ENTITY_ID_10000025 = 10000025;// 数据来源查看权限
		public static final int ENTITY_ID_10000026 = 10000026;// 查看用户密码权限
		public static final int ENTITY_ID_10000027 = 10000027;// 取消订单权限
		
		public static final int ENTITY_ID_10000028 = 10000028;// 修改本组织用户信息的权限
		public static final int ENTITY_ID_10000029 = 10000029;// 修改所有组织用户信息的权限
		public static final int ENTITY_ID_10000030 = 10000030;// 用户审核权限
		public static final int ENTITY_ID_10000031 = 10000031;// 车辆审核权限
		
		public static final int ENTITY_ID_10000032 = 10000032;// 新增所有组织用户的权限
		public static final int ENTITY_ID_10000033 = 10000033;// 新增本组织用户的权限
		
		public static final int ENTITY_ID_20000000 = 20000000;// 系统管理--菜单实体
		public static final int ENTITY_ID_20000001 = 20000001;// 组织信息管理--菜单实体
		public static final int ENTITY_ID_20000002 = 20000002;// 批量导入结果查询
		public static final int ENTITY_ID_20000003 = 20000003;// 缓存刷新
		public static final int ENTITY_ID_20000005 = 20000005;// 建议
		public static final int ENTITY_ID_20000006 = 20000006;// 投诉
		public static final int ENTITY_ID_21000000 = 21000000;// 登录Logbi权限实体
		public static final int ENTITY_ID_21000001 = 21000001;// 所有组织查询
		public static final int ENTITY_ID_21000002 = 21000002;// 本组织和下级组织查询
		public static final int ENTITY_ID_21000005 = 21000005;// 删除车辆信息
		
		public static final int ENTITY_ID_30000000 = 30000000;// 运营管理--菜单实体
		public static final int ENTITY_ID_30000001 = 30000001;// 邀请管理--菜单实体
		public static final int ENTITY_ID_30000002 = 30000002;// 提现管理--菜单实体
		public static final int ENTITY_ID_30000003 = 30000003;// 银行对帐查询--菜单实体
		public static final int ENTITY_ID_30000004 = 30000004;// 短信发送--菜单实体
		public static final int ENTITY_ID_30000005 = 30000005;// 加盟商管理--菜单实体
		public static final int ENTITY_ID_30000006 = 30000006;// 评价管理--菜单实体
		public static final int ENTITY_ID_30000007 = 30000007;// 红包管理--菜单实体
		public static final int ENTITY_ID_30000008 = 30000008;// 现金管理--菜单实体
		public static final int ENTITY_ID_30000009 = 30000009;// 查询所有组织账户现金信息权限实体[现金管理菜单功能]
		public static final int ENTITY_ID_30000010 = 30000010;// 查询本组织账户现金信息权限实体[现金管理菜单功能]
		public static final int ENTITY_ID_30000011 = 30000011;// 查询所有组织账户积分信息权限实体[积分管理菜单功能]
		public static final int ENTITY_ID_30000012 = 30000012;// 查询本组织账户积分信息权限实体[积分管理菜单功能]
		public static final int ENTITY_ID_30000013 = 30000013;// 积分管理--菜单实体
		public static final int ENTITY_ID_30000014 = 30000014;// 赠送积分权限
		public static final int ENTITY_ID_30000015 = 30000015;// 赠送红包权限
		public static final int ENTITY_ID_30000016 = 30000016;// 查询所有组织账户红包信息权限实体[红包管理菜单功能]
		public static final int ENTITY_ID_30000017 = 30000017;// 查询本组织账户红包信息权限实体[红包管理菜单功能]
		public static final int ENTITY_ID_30000018 = 30000018;// 黑名单管理菜单权限
		public static final int ENTITY_ID_30000019 = 30000019;// 拔打电话管理菜单权限
		public static final int ENTITY_ID_30000020 = 30000020;// 手动提现菜单权限
		public static final int ENTITY_ID_30000021 = 30000021;// 短信发送管理菜单权限
		public static final int ENTITY_ID_30000022 = 30000022;// 回复短信管理菜单权限
		public static final int ENTITY_ID_30000023 = 30000023;// 仲裁信息费权限
		
		public static final int ENTITY_ID_40000000 = 40000000;// 统计分析--菜单实体
		public static final int ENTITY_ID_40000001 = 40000001;// 客户回访管理--菜单实体
		public static final int ENTITY_ID_40000002 = 40000002;// 货源统计图表--菜单实体
		public static final int ENTITY_ID_40000003 = 40000003;// 车源统计图表--菜单实体
		public static final int ENTITY_ID_40000004 = 40000004;// 订单统计图表--菜单实体
		public static final int ENTITY_ID_40000005 = 40000005;// 用户统计图表--菜单实体
		public static final int ENTITY_ID_40000006 = 40000006;// 车辆统计图表--菜单实体
		public static final int ENTITY_ID_40000007 = 40000007;// 红包统计图表--菜单实体
		public static final int ENTITY_ID_40000008 = 40000008;// 充值统计图表--菜单实体'
		public static final int ENTITY_ID_40000009 = 40000009;// 车源匹配统计图表--菜单实体
		public static final int ENTITY_ID_40000010 = 40000010;// 货源统计图表--菜单实体
		public static final int ENTITY_ID_40000011 = 40000011;// 运营统计--菜单实体
		
		// **********************[角色/权限/授权]等基础功能部分实体**********************//
		public static final int ENTITY_ID_50000000 = 50000000;// 新增角色权限
		public static final int ENTITY_ID_50000001 = 50000001;// 删除角色权限
		public static final int ENTITY_ID_50000002 = 50000002;// 修改角色权限
		
		public static final int ENTITY_ID_50000003 = 50000003;// 角色授权或关联操作员权限
		public static final int ENTITY_ID_50000004 = 50000004;// 解除角色与关联操作员权限
		public static final int ENTITY_ID_50000005 = 50000005;// 新增或修改操作员授权操作权限
		public static final int ENTITY_ID_50000006 = 50000006;// 解除授权(包括角色授权、操作员授权和组织授权)权限
		
		public static final int ENTITY_ID_50000007 = 50000007;// 新闻公告审核权限
		
		// **********************[角色/权限/授权]等基础功能功能部分实体**********************//
		
		// *****************************统计功能实体部分**************************//
		public static final int ENTITY_ID_60000001 = 60000001;// 注册用户运营分析--统计分析菜单
		public static final int ENTITY_ID_60000002 = 60000002;// 用户活跃度统计--统计分析菜单
		public static final int ENTITY_ID_60000003 = 60000003;// 用户存活率统计--统计分析菜单
		public static final int ENTITY_ID_60000004 = 60000004;// 用户活跃率运营分析--统计分析菜单
		public static final int ENTITY_ID_60000005 = 60000005;// 货源运营分析--统计分析菜单
		public static final int ENTITY_ID_60000006 = 60000006;// 货源匹配运营分析--统计分析菜单
		public static final int ENTITY_ID_60000007 = 60000007;// 车辆运营分析--统计分析菜单
		public static final int ENTITY_ID_60000008 = 60000008;// 车源匹配运营分析--统计分析菜单
		public static final int ENTITY_ID_60000009 = 60000009;// 车源运营分析--统计分析菜单
		public static final int ENTITY_ID_60000010 = 60000010;// 车主货主运营分析--统计分析菜单
		public static final int ENTITY_ID_60000011 = 60000011;// 订单运营分析--统计分析菜单
		public static final int ENTITY_ID_60000012 = 60000012;// 交易金额--统计分析菜单
		public static final int ENTITY_ID_60000013 = 60000013;// 红包赠送--统计分析菜单'
		public static final int ENTITY_ID_60000014 = 60000014;// 充值运营分析--统计分析菜单
		public static final int ENTITY_ID_60000015 = 60000015;// 电话拨打运营分析--统计分析菜单
		public static final int ENTITY_ID_60000016 = 60000016;// 客户回访运营分析--统计分析菜单
		public static final int ENTITY_ID_60000017 = 60000017;// 操作运营分析--统计分析菜单
		//*****************************统计功能实体部分**************************//
		
		//*****************************菜单实体部分**************************//
		public static final int ENTITY_ID_70000000 = 70000000;// 系统管理菜单实体
		public static final int ENTITY_ID_70000001 = 70000001;// 角色管理菜单实体
		public static final int ENTITY_ID_70000002 = 70000002;// 握同城--菜单实体
		public static final int ENTITY_ID_70000003 = 70000003;// 用户管理--菜单实体
		public static final int ENTITY_ID_70000004 = 70000004;// 诚信管理--菜单实体
		public static final int ENTITY_ID_70000005 = 70000005;// 授权--菜单实体
		public static final int ENTITY_ID_70000006 = 70000006;// 菜单管理--菜单实体
		public static final int ENTITY_ID_70000007 = 70000007;// 匹配结果管理--菜单实体
		public static final int ENTITY_ID_70000008 = 70000008;// 新闻公告管理--菜单实体
		public static final int ENTITY_ID_70000009 = 70000009;// 新闻公告管理--菜单实体

		// *****************************菜单实体部分**************************//
	}

	public static class SysRoleConstant {
		public static final int SYS_ROLE_ID_XTGLY = 10000000; // 系统管理员角色,拥有所有权限
		public static final int SYS_ROLE_ID_GLRY = 10000001; // 管理人员角色
		public static final int SYS_ROLE_ID_KF = 10000002; // 客服角色
		public static final int SYS_ROLE_ID_DLS = 10000003;// 代理商
		public static final int SYS_ROLE_ID_PTTGJS = 10000004;// 平台推广角色
	}
	
	/************************* 权限/角色/实体/菜单 **End ***********************/
	public static class CacheRefreshConstant{
		public static final String CODE_TYPE_CACHE_REFRESH_SYSTEM = "CacheRefreshSystem";
	}
	
	public static class SysNewsConstant {
		public static final String CODE_TYPE_SYS_NEWS_STATE = "CODE_TYPE_SYS_NEWS@STATE";
		public static final String CODE_TYPE_SYS_NEWS_TYPE = "CODE_TYPE_SYS_NEWS@TYPE";
	}
	
	public static class PayInfoRecConstant {
		public static final String CODE_TYPE_PAY_INFO_REC_STATUS = "PAY_INFO_REC@STATUS";
		/** 信息费--费用状态 */
		public static final int STATUS_1 = 1;// 预付
		public static final int STATUS_2 = 2;// 申请退回
		public static final int STATUS_3 = 3;// 申请收款
		public static final int STATUS_4 = 4;// 支付完成
		public static final int STATUS_5 = 5;// 支付完成(仲裁)
		public static final int STATUS_6 = 6;// 退回(仲裁)
		/** 信息费--费用状态 */
	}
	
	/**
	 * 数据统计相关的常量
	 * 其中会使用到{@link LogBIConstant.ChartInfo}中的一些常量
	 * 
	 * @author chenjun
	 *
	 */
	public static class StatisticConstant {
		// 时间维度
		public static final int TIME_DIMENSION_1 = 1;// 统计今天的数据
		public static final int TIME_DIMENSION_2 = 2;// 统计昨天的数据
		public static final int TIME_DIMENSION_3 = 3;// 统计当月的数据
		public static final int TIME_DIMENSION_4 = 4;// 统计上月的数据
		public static final int STATE_1 = 1; // 有效
		public static final int IS_CAN_GET_ALL_ORG_DATA_1 = 1;
		public static final String OTHER = "OTHER";
		public static final String BEETL_TEMPLATE_SUFFIX = ".btl";
		public static final String BEETL_EMAIL_HTML_CSS = "Css";
		public static final String STATISTIC_EMAIL_SENDER = "STATISTIC_EMAIL_SENDER";
		
		public static final int RECEIVE_TYPE_0 = 0;// 合并发送
		public static final int RECEIVE_TYPE_1 = 1;// 分开发送
		
		// ************************** Sql参数部分 ************************** //
		public static final String SQL_QUERY_PARAM_ORG_ID = "orgId";
		public static final String SQL_QUERY_PARAM_ROOT_ORG_ID = "rootOrgId";
		public static final String SQL_QUERY_PARAM_CURRENT_TIME = "currentTime";// 当月开始时间
		public static final String SQL_QUERY_PARAM_QUERY_MONTH_START_TIME = "queryMonthStartTime";// 查询月开始时间
		public static final String SQL_QUERY_PARAM_QUERY_MONTH_END_TIME = "queryMonthEndTime";// 查询月结束时间
		public static final String SQL_QUERY_PARAM_QUERY_DAY_START_TIME = "queryDayStartTime";// 查询天开始时间
		public static final String SQL_QUERY_PARAM_QUERY_DAY_END_TIME = "queryDayEndTime";// 查询天结束时间
		
		public static final String SQL_QUERY_PARAM_QUERY_DAY_CURRENT_MONTH_START_TIME = "queryDayCurrentMonthStartTime";// 查询天对应月的开始时间
		public static final String SQL_QUERY_PARAM_QUERY_DAY_CURRENT_MONTH_END_TIME = "queryDayCurrentMonthEndTime";// 查询天对应月的结束时间
		
		public static final String SQL_QUERY_PARAM_QUERY_DAY_LAST_MONTH_START_TIME = "queryDayLastMonthStartTime";// 查询天上个月的开始时间
		public static final String SQL_QUERY_PARAM_QUERY_DAY_LAST_MONTH_END_TIME = "queryDayLastMonthEndTime";// 查询天上个月的结束时间
		
		public static final String SQL_QUERY_PARAM_PARENT_VALUE = "parentValue";// table项存在子项时，此值代表table项对应的值
		
		// ************************** Sql参数部分 ************************** //
		
		// ************************** “宏变量”替换部分对应的KEY************************** //
		public static final String MACRO_VARIABLES_PARAMS_QUERY_MONTH_YYYYMMM = "queryMonthYYYYMMM";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_MONTH_CH = "queryMonthCh";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_MONTH_EN = "queryMonthEn";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_MONTH_DAY_CH = "queryMonthDayCh";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_MONTH_DAY_EN = "queryMonthDayEn";
		public static final String MACRO_VARIABLES_PARAMS_CURRENT_MONTH_YYYYMMM = "currentMonthYYYYMMM";

		public static final String MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ORG = "queryConditionOrg";
		public static final String MACRO_VARIABLES_PARAMS_QUERY_CONDITION_ROOT_ORG = "queryConditionRootOrg";
		public static final String MACRO_VARIABLES_PARAMS_ORG_NAME = "orgName";
		
		public static final String CODE_TYPE_EMAIL_TABLE_YAXIS_HEADS = "EMAIL_TABLE_YAXIS_HEADS";
		public static final String CODE_TYPE_EMAIL_TABLE_YAXIS_SUB_HEADS = "EMAIL_TABLE_YAXIS_SUB_HEADS";
		// ************************** “宏变量”替换部分对应的KEY**************************//
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
}
