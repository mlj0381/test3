package com.wo56.common.consts;
/**
 * 
 * @author liyiye
 *    众邦接口定义规范定义

	  100000-109999   系统 公告

      110000-119999   cm(三户)

      120000-129999   ord(订单)

      130000-139999   route(路由)

      140000-149999   ac(帐务)

      150000-159999   payment
      
      160000-169999  基础配置
      
      180000-189999  KPI(考核管理)
      
      200000-205000 短信
 */
public class InterFacesCodeConsts {
	
	/**
	 * CM(三户)业务的接口code
	 * @author zjy
	 *
	 */   
	public static class CM {
		/**查询当前网点 的开单员/业务员*/
		public static final String QUERY_BUSS_BILLING ="110000";
		/** 查询当前网点发货人收货人(主要查询收货人，关联发货人) **/
		public static final String QUERY_CUSTOMER = "110001";
		/** 查询车辆信息 **/
		public static final String QUERY_VEHICLE = "110002";
		/**查询发货人或收货人信息(主要查询发货人)**/
		public static final String GET_CUSTOMER = "110003";
		/**查询司机**/
		public static final String QUERY_CM_DRIVER= "110206";
		/**保存司机**/
		public static final String SAVE_CM_DRIVER= "110207";
		/**查询车辆信息**/
		public static final String QUERY_CM_VEHICLE= "110108";
		/**保存车辆信息**/
		public static final String SAVE_CM_VEHICLE= "110209";
		/**查询车辆司机**/
		public static final String QUERY_VEHICLE_DRIVER= "110040";
		/**lyh 查询公司管理员的列表**/
		public static final String QUERY_CM_USER_USERID= "110011";
		/**员工详情查询**/
		public static final String QUERY_PLATFORM_USER_USERID= "110012";
		/**删除员工**/
		public static final String DEL_CM_USER= "110013";
		/**删除车辆**/
		public static final String DEL_CM_VEHICLE= "110214";
		/**删除司机**/
		public static final String DEL_CM_DRIVER= "110015";
		/**删除司机**/
		public static final String DEL_DRIVER_CM= "110215";
		/**查询车辆详情**/
		public static final String QUERY_VEHICLE_DTL= "110041";
		/**查询司机详情**/
		public static final String QUERY_DRIVER_DTL= "110017";
		/**保存修改发货人信息**/
		public static final String SAVE_CUSTOMER_IN= "110018";
		/**查询发货人详情*/
		public static final String QUERY_CUSTOMER_DTL_IN= "110019";
		/**查询发货人列表*/
		public static final String QUERY_CUSTOMER_IN= "110020";
		/**删除发货人*/
		public static final String DEL_CUSTOMER= "110021";
		/**保存、修改承运商信息*/
		public static final String SAVE_CARR_COMPANY= "110222";
		/**查询承运商信息列表*/
		public static final String QUERY_CARR_COMPANY= "110223";
		/**查询承运商信息详情*/
		public static final String QUERY_COMPANY_DTL= "110224";
		/**删除承运商信息*/
		public static final String DEL_CARR_COMPANY= "110225";
		/**查询承运商信息（没有分页）*/
		public static final String GET_CARR_COMPANY= "110032";
		/**查询发货人列表(lyh)*/
		public static final String QUERY_CU_IN= "110220";
		/**保存发货人信息(lyh)**/
		public static final String SAVE_CU_IN= "110218";
		/**查询商家组织(lyh)*/
		public static final String QUERY_CU_ORG= "110226";
		/**根据ID查询收货人发货人(lyh)*/
		public static final String QUERY_CU_BYID= "110227";
		/**删除发货人、收货人（lyh）*/
		public static final String DEL_CU= "110221";
		/**查询司机详情(lyh)**/
		public static final String QUERY_DRIVER= "110217";
		
		/*
		 * 新增师傅
		 */
		public static final String SCHE_SF_USER_INF0_SAVE = "110005";
		/**
		 * 查询师傅
		 */
		public static final String SCHE_SF_USER_INF0_QUERY = "110008";
		
		public static final String SCHE_SF_LIST_QUERY = "110034";
		
		public static final String SCHE_SF_USER_INF0_NOPAGING_QUERY = "110033";
		/**
		 * 变更师傅
		 */
		public static final String SCHE_SF_USER_INF0_MODIFY = "110009";
		/**
		 * 删除师傅
		 */
		public static final String SCHE_SF_USER_INF0_DELETE = "110010";
		
		/**
		 * 认证师傅
		 */
		public static final String SCHE_SF_USER_INF0_AUDIT = "110018";
		
		/**
		 * 师傅资料审核
		 */
		public static final String SCHE_SF_CHAG_USER_INF0_AUDIT = "110025";
		
		/**
		 * 师傅账户管理查询
		 */
		public static final String SCHE_SF_USER_ACCT_MANAGE = "110029";
		
		/**
		 * 师傅账户变更
		 */
		public static final String SCHE_SF_USER_ACCT_MOD = "110030";
		
		/**
		 * 师傅资料变更列表查询
		 */
		public static final String SCHE_SF_CHAG_USER_INF0_AUDIT_LIST = "110024";
		
		/**
		 * 号码校验
		 */
		public static final String SCHE_SF_USER_INF0_CHECK_PHONE = "110012";
		
		/**
		 * 查询协作公司
		 */
		public static final String SCHE_SF_CONTRACT_COMPANY_QUERY = "110013";
		/**
		 * 查询新增协作公司
		 */
		public static final String SCHE_SF_CONTRACT_COMPANY_ADD = "110014";
		
		/**
		 * 查询删除协作公司
		 */
		public static final String SCHE_SF_CONTRACT_COMPANY_DELETE = "110015";
		
		/**
		 * 查询新增修改公司
		 */
		public static final String SCHE_SF_CONTRACT_COMPANY_GET = "110016";
		
		/**
		 * 查询公司列表
		 */
		public static final String SCHE_SF_COMPANY_LIST_QUERY = "110017";
		
		/**
		 * 师傅详情
		 */
		public static final String SCHE_SF_USER_INF0_DETAIL = "110035";
		/**
		 * 新增平台师傅
		 */
		public static final String ADD_PLAT_SF = "110036";
		
		public static final String GET_SF_CARRIER = "120076";
		/**
		 * 查询公司信息
		 */
		public static final String QUERY_COMPANY_INFO_BY_CODE ="110006";

		/** * APP注册接口*/
		 
		public static final String APP_USER_REG = "110007";
		
		/**查询用户**/
		public static final String QUERY_CM_USER = "110018";
		
		/** lyh 保存用户**/
		public static final String SAVE_CM_USER = "110019";
		/**删除用户**/
		public static final String GET_COMPANY = "110023";
		/**给操作员添加区域**/
		public static final String SAVE_USER_AREA = "110026";
		/**查询用户区域**/
		public static final String QUERY_USER_AREA = "110027";
		/**查询用户区域**/
		public static final String DEl_USER_AREA = "110028";
		
		/**查询一个公司下面的师傅数量**/
		public static final String CM_SF_USER_TOTAL = "110031";
		
		/**开单查询专线网点配置的协议商家费用基础信息（最多只查询1条）**/
		public static final String CM_ORDER_PLACE = "110228";
		
		/**查询承运商干线费用标准**/
		public static final String CM_TRUNK_COST = "110229";
		
		/**删除承运商干线费用标准**/
		public static final String DELETE_CM_TRUNK_COST = "110231";
		
		/**更新该专线的协议商家(发货商家)支付方式**/
		public static final String UPDATE_CM_TRUNK_COST_PARMENTTYPPE = "110232";
		
		/**更新干线费用标准**/
		public static final String UPDATE_CM_TRUNK_COST = "110234";
		
		/** 查询该专线的协议商家(发货商家)**/
		public static final String CM_ZX_PLACE = "110230";
		/** 新增干线费用配置**/
		public static final String CM_SAVE_FEE = "110233";
	}
	
	/**
	 * AC(账户)业务的接口code
	 * @author zjy
	 *
	 */   
	public static class AC {
		/**配送区域查询*/
		public static final String QUERY_AREA ="140000";
		/**核销信息查询*/
		public static final String QUERY_PROVE ="140001";
		/**核销处理*/
		public static final String PROVE_DEAL ="140002";
		/**反核销处理*/
		public static final String PROVE_UN_DEAL ="140003";
		/**现金状态处理*/
		public static final String CASH_STS ="140004";
		/**查询科目*/
		public static final String QUERY_FEE_CONFIG ="140005";
		/**保存科目*/
		
		public static final String SAVE_FEE_CONFIG ="140006";
		/**核销信息查询(运费和装卸费 )*/
		public static final String QUERY_PROVE_BATCH ="140007";
		
		/**详情查询核销信息*/
		public static final String QUERY_AC_ACCOUNT_DTL ="140008";
		/**详情查询核销信息(商家的物流对账)*/
		public static final String QUERY_AC_ACCOUNT_BUSINESS_DTL ="140009";
		
		/**详情查询核销信息(专线的商家对账)*/
		public static final String QUERY_AC_ACCOUNT_ZX_DTL ="140010";
		
		/**专线的师傅对账*/
		public static final String QUERY_SF_ZX_DTL ="140011";
		
		
	}
	
	/**
	 * common公共业务接口code
	 * @author zhouchao
	 *
	 */
	public static class COMMON{
		/**查询所有的市 **/
		public static final String GET_ALL_CITY = "100000";
		
		/**查询静态数据**/
		public static final String GET_STAITC_DATA = "100001";
		
		/**登录接口**/
		public static final String LOGIN = "100002";
		
		/**查询用户的角色**/
		public static final String USER_ENTITY = "100003";

		/**lyh 查询角色的租户为－1**/
		public static final String QUERY_SYS_ROLE = "100004";
		/**lyh 保存角色**/
		public static final String DOSAVE_SYS_ROLE = "100005";
		/**首页的网点的预付款，代收货款的查询接口**/
		public static final String INDEX_AMOUNT="100007";
		
		/**lyh 登录页面查询 租户信息**/
		public static final String LOGIN_QUERY_TENANT="100006";
		
		/**查询首页的快捷方式**/
		public static final String INDEX_SHORT="100008";
		/**保存用户角色**/
		public static final String SAVE_USER_ROLE = "100009";
		/**查询用户角色**/
		public static final String QUERY_USER_ROLE = "100010";
		/**查询系统菜单**/
		public static final String QUERY_SYS_MENU = "100011";
		/**查询系统URL**/
		public static final String QUERY_SYS_URL = "100012";
		/**保存系统菜单**/
		public static final String SAVE_SYS_MENU = "100013";
		/**保存系统URL**/
		public static final String SAVE_SYS_URL = "100014";
		/**检索菜单信息**/
		public static final String QUERY_SYS_MENUNAME = "100015";
		/**通过roleId查询**/
		public static final String QUERY_SYS_URL_ROLEID = "100016";
		/**保存角色URL**/
		public static final String SAVE_ROLE_URl= "100017";
		/**查询URL详情**/
		public static final String QUERY_URl_DTL= "100020";
		/**查询菜单详情**/
		public static final String QUERY_MENU_DTL= "100021";
		/**删除URL**/
		public static final String DEL_SYS_URL= "100022";
		/**删除菜单**/
		public static final String DEL_SYS_MENU= "100023";
		/**查询新闻列表**/
		public static final String NEWS_LIST="100018";
		/**查询新闻详情**/
		public static final String NEWS_DETAIL="100019";
		/**保存，更新新闻**/
		public static final String NEWS_SAVE="100024";
		/**给角色添加菜单**/
		public static final String SAVE_ROLE_MENU="100025";
		/**查询角色已添加的URl**/
		public static final String QUERY_ROLE_MENU="100026";
		/**删除角色**/
		public static final String DEL_ROLE="100027";
		/**查询角色详情**/
		public static final String QUERY_ROLE_DTL="100028";
		/**查询角色授权**/
		public static final String QUERY_OPER_URl="100029";
		/**发送短信**/
		public static final String SYS_SMS_SEND="100030";
		public static final String GET_SYS_TEMPLATE = "120093";
		
		/**导出公共类**/
		public static final String EXPORT_DATA="100031";
		/**查询首页的配置元素**/
		public static final String INDEX_ELEMENT = "100106"; 
		/**增加快捷栏**/
		public static final String ADD_SHUT_CUT = "100107"; 
		/**删除快捷栏**/
		public static final String DEL_SHUT_CUT = "100108"; 
		/**lyh 查询平台角色的租户为－1**/
		public static final String QUERY_SYS_PTROLE = "100009";
		/**lyh 查询员工角色的租户为当前租户**/
		public static final String QUERY_SYS_STROLE = "100010";
		/**lyh 删除角色**/
		public static final String DEL_SYS_STROLE = "100011";
		/**首页获取tableDiy数据*/
		public static final String TABLE_DIY = "100012";
		
		
		/** 根据租户+业务编码查询打印配置 */
		public static final String QUERY_PRINT_CONFIG = "100032";
		
		/**登录接口（切换账号）**/
		public static final String REL_LOGIN = "100034";
		
		/**查询登录账户关联的账号**/
		public static final String QUERY_REL_LOGIN = "100035";
		
		/** 读取表头列配置信息 */
		public static final String QUERY_TABLE_HEAD_CONFIG = "100043";
		
		/** 保存表头列配置信息 */
		public static final String SAVE_TABLE_HEAD_CONFIG = "100044";
	}
	
	public static class ROUTE{
		/**查询可到达网点信息*/
		public static final String QUERY_ROUTE_TOWARD ="130000";
		/**发车到达网店查询*/
		public static final String QUERY_ROUTE_RUTING ="130001";
		/**判断配送的网点是否支持代收货款*/
		public static final String QUERY_ORG ="130002";
		/**查询路由的线路 */
		public static final String QUERY_TOWARDS_DTl ="130003";
		/**标准路径费用查询 */
		public static final String QUERY_TOWARDS_COST ="130004";
		/**保存路由线路费用 */
		public static final String QUERY_TOWARDS_FEE ="130005";
		/**记录费用重算任务表 */
		public static final String DOSAVE_ROUTE_TASK ="130006";
		
		/**查询可到达网点 */
		public static final String QUERY_ROUTE_THOWARDS_DTL ="130007";
		
		public static final String QUERY_ROUTE_ROUTING_DETAIL_DTL ="160021";
		
		/**线路设置 */
		public static final String SET_ROUNT ="130008";
		/**模拟算费 */
		public static final String QUERY_SET_ROUNT ="130009";
		/**查询结束网点 */
		public static final String GET_ROUNT ="130010";
		
		/**查询分拨中心路由 */
		public static final String ROUTE_ORGTYPE_ROUTING ="130011";
		/**查询订单出货成本费用*/
		public static final String QUERY_ORDER_ROUTE = "130012";
		/**查询所有合作商*/
		public static final String CARRI_ORG ="130013";
	}

	
	public static class ORD{
		/**网点待支付费用查询（收货节点）*/
		public static final String QUERY_ORG_COST ="120000";
		/**到车*/
		public static final String ARRIVE_VEHICLE="120001";
		/**到货*/
		public static final String ARRIVE_GOOD  ="120002";
		/**保存、修改订单*/
		public static final String SAVE_OR_UPDATE="120003";
		/**预约送货*/
		public static final String SAVE_ORD_NOTIFY="120004";
		/**订单签收录入*/
		public static final String SAVE_ORD_SIGN="120005";
		/**配载发车*/
		public static final String STOWAGE_CAR="120006";
		/***回单返厂**/
		public static final String RETURN_RE_MANA="120007";
		/**查询签收订单*/
		public static final String QUERY_ORD_SIGN="120008";
		/**到货通知的订单查询(预约送货)*/
		public static final String QUERY_ORD_NOTIFY="120009";
		/**放货通知录入*/
		public static final String SAVE_ORDER_NOTIFY="120010";
		/**通知放货查询*/
		public static final String QUERY_ORDER_NOTIFY="120011";
		/**确认发车*/
		public static final String CON_MATCH_VEH="120012";
		/**取消发车*/
		public static final String CANCEL_MATCH_VEH="120085";
		/**查看配载详情*/
		public static final String MATCH_DETAIL="120013";
		/**查看配载*/
		public static final String QUERY_MATCH="120014";
		/**删除配载信息*/
		public static final String DELETE_DEPART="120015";
		/**查询到货通知订单详情*/
		public static final String QUERY_NOTIFY_DTL="120016";
		/**查询签收录入的详情*/
		public static final String QUERY_SIGN_DTL="120017";
		/**查询到货通知的详情*/
		public static final String QUERY_SHIPMENT_NOTICE="120018";
		/**订单查询*/
		public static final String ORD_QUERY="120019";
		/**订单详情**/
		public static final String ORDER_DETAIL="120020";
		/**订单删除**/
		public static final String DEL_ORDER = "120021";
		/**过账删除**/
		public static final String POSTING_ORDER = "120094";
		/**订单审核**/
		public static final String AUDIT_ORDER = "120022";
		/**订单查询（配载发车）**/
		public static final String ORD_DO_QUERY = "120023";
		public static final String MATCH_CURRENT_ORG = "120079";
		/**订单(回单)查询（回单管理）**/
		public static final String ORD_RECIPT_QUERY = "120024";
		/**订单(异常)查询**/
		public static final String ORD_EXCEPTION_QUERY = "120025";
		/**订单(异常)保存**/
		public static final String ORD_EXCEPTION_SAVE = "120026";
		/**订单(异常)查询详情**/
		public static final String ORD_EXCEPTION_DETAIL = "120027";
		/**订单(异常)处理**/
		public static final String ORD_EXCEPTION_DEAL = "120031";
		/**问题查询**/
		public static final String ORD_QUESTION_QUERY = "120028";
		/**问题保存**/
		public static final String ORD_QUESTION_SAVE = "120029";
		/**问题查询详情**/
		public static final String ORD_QUESTION_DETAIL = "120030";
		/**问题处理**/
		public static final String ORD_QUESTION_DEAL = "120032";
		/**操作日志查询**/
		public static final String ORD_LOG = "120033";
		/**运单号是否被使用*/
		public static final String CHECK_TRACKING_NUM_USED_STATE = "120034";
		/**查询订单详情，不根据网点查询*/
		public static final String ORD_DETAIL_IN = "120035";
		/**查看送货上门或中转送货上门信息*/
		public static final String QUERY_MATCH_OTHER="120049";
		/**操作日志查询(外网调用)**/
		public static final String ORD_DETAIL_LOG ="120036";
		/**查看送货上门或中转送货上门信息订单查询（配载发车）**/
		public static final String ORD_DO_QUERY_OTHER = "120050";
		/**新增中转送货查询*/
		public static final String QUERY_OTHER = "121050";
		/**中转外发保存*/
		public static final String TRANSFER_SAVE = "121051";
		/**根据订单号查询中转外发*/
		public static final String ORD_QUERY_REL_TRANSIT_OUTGOING = "120037";
		public static final String PRE_CHECK_FOR_NEW_TRANSIT_OUTGOING = "120038";
		public static final String SAVE_TRANSIT_OUTGOING = "120039";
		public static final String CANCEL_TRANSIT_OUTGOING = "120040";
		/**查询中转外发信息*/
		public static final String QUERY_OUTGOING = "120041";
		/**应收应付报表查询**/
		public static final String QUERY_ORD_FEE = "120070";
		/**获取运单走过的节点*/
		public static final String ROUTE_ORG = "120042";
		
		/**多条件订单查询*/
		public static final String ORD_MANY_QUERY="120043";
		
		/**发货/到货库存查询*/
		public static final String ORD_ARRIVE_OR_DELINERY_QUERY="120044";
		/**发货/到货库存查询(lyh)*/
		public static final String ORD_ARRIVE_OR_DELINERY_QUERY_LYH="120244";
		
		
		/**发货/到货库存统计查询*/
		public static final String ORD_ARRIVE_OR_DELINERY_COUNT_QUERY="120045";
		
		public static final String CONFIRM_TRANSIT_OUTGOING_RECEIVED_FEE="120046";
		
		/**送货上门配载发车*/
		public static final String STOWAGE_CAR_OTHER="120052";
		/**查看送货详情*/
		public static final String MATCH_DETAIL_OTHER="120051";
		
		/**删除送货上门配载信息*/
		public static final String DELETE_DEPART_OTHER="120053";
		
		/**中转送货上门配载发车*/
		public static final String STOWAGE_CAR_TARANSIT_OTHER="120055";
		/**删除中转送货上门配载信息*/
		public static final String DELETE_DEPART_TARANSIT_OTHER="120056";
		/**取消回单信息*/
		public static final String DELETE_RECEIPT_IN="120057";
		/**查询*/
		public static final String QUERY_ORDER_DEPAR= "120054";
		
		/** 查询中转核销 */
		public static final String QUERY_OUTGOING_CHECK = "120047";
		
		/** 中转核销 */
		public static final String SAVE_OUTGOING_CHECK = "120048";
		/**送货签收批次查询*/
		public static final String DEPART_TARANSIT_ORD_OTHER="120058";
		/**中转跟踪日志查询*/
		public static final String ORD_TRACKING_QUERY="120059";
		/**查询中转操作信息*/
		public static final String ORD_TRACKINGDETAIL_QUERY="120063";
		/**中转跟踪日志保存*/
		public static final String ORD_TRACKING_SAVE="120060";
		/**保存中转操作信息*/
		public static final String ORD_TRACKINGDETAIL_IN="120062";
		/**删除中转跟踪日志*/
		public static final String ORD_TRACKING_DEL="120061";
		/**根据ID查看常用备注*/
		public static final String ORD_QUERY_RID="120064";
		/**保存于更新常用备注*/
		public static final String ORD_SAVE="120065";
		/**删除常用备注*/
		public static final String ORD_DEL="120066";
		/**查询常用备注*/
		public static final String ORD_QUERY_NOTES="120067";
		/**根据ID查看交接方式*/
		public static final String SYS_QUERY_FID="120068";
		/**保存于更新交接方式*/
		public static final String SYS_SAVE="120069";
		/**删除交接方式*/
		public static final String SYS_DEL="120070";
		/**查询交接方式*/
		public static final String SYS_QUERY="120071";
		/**查询所有交接方式*/
		public static final String SYS_QUERYALL="120074";
		/**服务商干线到货*/
		public static final String GX_GOODS="120080";
		/**订单转运单查询*/
		public static final String ORD_SELLER_QUERY="120078";
		/**是否可以中转*/
		public static final String ORD_IS_TRANSFER="120081";
		/**查询中转批次*/
		public static final String QUERY_TRANSFER="120082";
		/**取消中转*/
		public static final String CANL_TRANS="120083";
		/**中转详情*/
		public static final String TRANS_DETAIL="120084";
		/**查询中转简单信息*/
		public static final String TRANS_SIMPLE_INFO = "120086";
		/**查询中转简单信息*/
		public static final String SERVICE_QUERY_MATCH_INFO = "120087";
		/**配送上门列表查询*/
		public static final String DELIVERY_QUERY = "120088";
		/**配送上门删除*/
		public static final String DELIVERY_DELETE= "120089";
		/**新增配送送货上门列表查询*/
		public static final String ADD_DELIVERY_QUERY= "120090";
		/**新增配送送货*/
		public static final String ADD_DELIVERY= "120091";
		/**新增配送送货*/
		public static final String TO_VIEW= "120092";
		
		
		/**异常登记－订单查询*/
		public static final String ORD_EXC_QUERY="120100";
		
		/**新增备注*/
		public static final String SAVE_REMARKS="120095";
		
		/**查询备注*/
		public static final String QUERY_REMARKS="120096";
		
		/**根据联系号码和地址查询拼单信息*/
		public static final String SPELL_QUERY_ORDER="120097";
		
		/**查询异动费用列表**/
		public static final String QUERY_CAST_CHANGE = "120098";
		
		/**保存异动费用列表**/
		public static final String SAVE_CAST_CHANGE = "120099";	
		
		/**对外查单*/
		public static final String ORD_TRACKING_NUM = "120200";
		
		/**短息查询*/
		public static final String QUERY_Id_And_TemplaId = "1110021";
	}
	
	/**基础配置*/
	public static class BASE{
		
		/**新增网点**/
		public static final String SAVE_ORG = "160000";
		/**路由管理查询**/
		public static final String ROUTE_QUERY = "160001";
		/**查看路由详情**/
		public static final String ROUTE_TO_VIEW = "160002";
		/**查看详细**/
		public static final String ROUTE_DETAIL = "160003";
		/**网点线路新增**/
		public static final String ROUTE_ORG_ADD = "160004";
		/**网点线路费用配置新增**/
		public static final String ORG_FEE_SAVE = "160005";
		/**线路费用配置新增**/
		public static final String ROUTE_FEE_SAVE = "160006";
		/**网点信息查询**/
		public static final String ORG_QUERY = "160007";
		/**线路查询**/
		public static final String TOWARDS_QUERY = "160008";
		/**线路费用保存**/
		public static final String ROUTE_FEE_CONFIG ="160010";
		/**网点费用查询**/
		public static final String ORG_FEE_CONFIG_QUERY ="160011";
		/**网点费用保存**/
		public static final String ROUTE_FEE_CONFIG_SAVE ="160012";
		/**网点充值**/
		public static final String RECHARGE ="160013";
		
		/** 查询当前用户可见的组织信息 */
		public static final String QUERY_CURRENT_USER_PRI_ORGS ="160055";
		/** lyh 查询所有租户信息 */
		public static final String QUERY_ORG_IN ="160215";
		/** lyh 查询所有租户信息 */
		public static final String QUERY_ORG_DEL ="160216";
		
		/** 查询租户下面的网点信息 */
		public static final String QUERY_ORG_BY_TENNANTID ="160015";
		
		/** 查询网点费用详情*/
		public static final String QUERY_ORG_FEE_DTL ="160016";
		/** 查询线路费用详情*/
		public static final String QUERY_ROUTE_FEE_DTL ="160017";


		/**
		 * 产品配置
		 */
		public static final String SCHE_SYS_PRODUCT_QUERY ="160014";//货品查询
		
		public static final String SCHE_GET_SYS_PRODUCT_CATALOG ="160015";//获取目录
		
		public static final String SCHE_UPDATE_SYS_PRODUCT_INFO ="160016";//更新产品信息
		
		public static final String SCHE_ADD_SYS_PRODUCT_INFO ="160017";//新增产品信息
		
		/**网点查询**/
		public static final String QUERY_ORG ="160018";
		/**地址库查询查询**/
		public static final String ADDRESS_QUERY ="160019";
		/**查询匹配规则**/
		public static final String QUERY_CHAIN ="160020";
		/**查询匹配信用值**/
		public static final String QUERY_CHAIN_SIMLAR ="160024";
		/**修改权重值**/
		public static final String MODIFY_PERCENT ="160022";
		/**修改权重值**/
		public static final String DOSAVE_SIMILAR ="160023";
		/**保存专线**/
		public static final String SAVE_ORGZX = "160025";
		/**查询专线**/
		public static final String QUERY_ORGZX = "160026";
		/**修改专线**/
		public static final String UPDATA_ORGZX = "160027";
		/**根据ID查询专线**/
		public static final String QUERYID_ORGZX = "160028";
		/**根据ID删除专线**/
		public static final String DEL_ORGZX = "160029";
		/**新增商家**/
		public static final String SAVE_ORGBUSS = "160030";
		/**查询专线商家信息**/
		public static final String QUERY_ORGBUSS = "160031";
		
		/**查询专线商家信息**/
		public static final String QUERY_ORGBUSS_OTHER = "160231";
		
		/**修改商家信息**/
		public static final String UPDATE_ORGBUSS = "160032";
		/**根据ID查看商家信息**/
		public static final String QUERYID_ORGBUSS = "160033";
		/**根据条件查看商家信息**/
		public static final String QUERYBYALL_ORGBUSS = "160034";
		/**根据ID删除商家信息**/
		public static final String DEL_ORGBUSS = "160035";
		/**查询专线名称**/
		public static final String QUERY_ALLLINK = "160036";
		/**查询平台商家信息**/
		public static final String QUERY_ALLBUS = "160037";
		/**根据商家查询专线**/
		public static final String QUERY_BUSLINK = "160038";
		/**根据对应订单的网点信息**/
		public static final String DUTY_INFO = "160039";
		/**查询师傅合作商**/
		public static final String QUERY_SFPARTNERS= "160040";
		
		/**查询师傅合作商异常查询**/
		public static final String QUERY_SFPARTNER_EXC= "160240";
		
		/**保存与修改师傅合作商**/
		public static final String SVAEUPDATE_SFPARTNERS= "160041";
		/**根据条件查询师傅合作商信息**/
		public static final String CHECK_SFPARTNERS= "160042";
		/**查询专线的所有网点**/
		public static final String QUERY_ORGAll= "160043";
		/**查询专线下的合作商**/
		public static final String QUERY_COOP_BUSS= "160044";
		/**保存与修改承运商**/
		public static final String SAVE_CMC= "160045";
		/**查询承运商**/
		public static final String QUERY_CMC= "160046";
		/**删除承运商**/
		public static final String DEL_CMC= "160047";
		/**查询专线合作商**/
		public static final String QUERY_SF_LINK= "160048";
		/** 查询服务商合作的合同公司**/
		public static final String QUERY_SERVICE_ORG= "160049";
		/**根据ID查看平台商家信息**/
		public static final String QUERYID_ORGBUS_LINK = "160050";
		/**查询当前网点信息*/
		public static final String GET_CURR_ORG = "160051";
		/**查询授权合作商租户信息*/
		public static final String DO_QUERY_AUTH_SF = "160052";
		/**查询删除授权关系*/
		public static final String DO_DEL_AUTH_MASTER_SF = "160053";
		
		public static final String QUERY_ROOT_PRO = "160054";
		
		public static final String DEAL_TENNAT_DATA_CONFIG_IN ="160241";
		
	}
	
	 /**180000-189999  KPI(考核管理)***/
	public static class KPI{
		/**KPI查询**/
		public static final String KPI_QUERY ="180000";
		/**KPI详细**/
		public static final String KPI_QUERY_DETAIL ="180001";
		/**KPI排行旁**/
		public static final String KPI_BILLBOARD="180002";
	}
	
	/**241000-241999  一智通账户 ***/
	public static class CASH{
		/**提现申请查询**/
		public static final String CASH_APPLICATION ="241000";
		/**提现申请保存**/
		public static final String CASH_APPLICATION_APPLY ="241001";
		/**师傅信息查询**/
		public static final String CASH_SF_QUERY ="241002";
		/**提现申请详情查询**/
		public static final String CASH_APP_QUERY ="241003";
		/**提现申请状态**/
		public static final String CASH_STATE_UPDATE ="241004";
		/**账户流水查询**/
		public static final String CASH_TOTAL_QUERY ="241009";
		public static final String CASH_COM_QUERY ="241010";
		/**核销*/
		public static final String VERIFICATION ="241015";
		/**配安核销查询*/
		public static final String SERVICE_QUERY ="241016";
		/**服务商提现申请*/
		public static final String SERVICE_APPLY_CASH ="241017";
	}
	public static class CREDIT{
		/**信用查询**/
		public static final String CREDIT_QUERY ="241005";
		/**信用请详情查询**/
		public static final String CREDIT_DETAIL_QUERY ="241006";
		public static final String CREDIT_KPI_QUERY ="241013";
	}
	public static class SERVE{
		/**投诉管理主页面查询**/
		public static final String SERVE_QUERY ="241007";
		/**回访管理主页面查询**/
		public static final String VISIT_QUERY ="241008";
		public static final String COMPLAINT_SAVE ="241011";
		public static final String VISIT_SAVE ="241012";
	}
	
	/**
	 * 发送短信
	 * @author zhouchao
	 *
	 */
	public static class SMS{
		/**发送通知*/
		public static final String NOTICE = "200000";
	}
	
	
	public static class SBITIP{
		/**
		 * 分享订单
		 */
		public static final String sbiTip="700007";
		/*** 获取分享订单信息*/
		public static final String SHARE_ORDER = "700011";
		
		/**查询订单明细*/
		public static String QUERY_ORDER_DETAIL="700010";
	}
}
