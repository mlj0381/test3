package com.wo56.common.consts;

/**
 * 
 * @author liyiye
 *    yzt接口定义规范定义

	  200000-209999   系统 公告

      210000-219999   cm(三户)

      220000-229999   ord(订单)

      230000-239999   route(调度)

      240000-2249999   ac(帐务)

      250000-259999   payment
      
      260000-269999  基础配置
      
      280000-289999  KPI(考核管理)
      
       290000-299999  报表)
 */
public class IntfCodeConsts {
	
	/**
	 * 费用相关
	 * @author zjy
	 *
	 */   
	public static class AC {
		/**查询支线费用定义*/
		public static final String BRANCH_QUERY ="240000";
		/** 查询支线详情 **/
		public static final String BRANCH_VIEW = "240001";
		/** 新增、修改支线费用 **/
		public static final String BRANCH_SAVE_OR_MODIFY = "240002";
		/**删除支线费用 **/
		public static final String BRANCH_DELETE = "240003";
	}
	
	/**
	 * 费用相关
	 * @author zjy
	 *
	 */   
	public static class SCHE {
		/**待分配任务查询*/
		public static final String WAIT_TASK_QUERY ="230000";
		/**不同任务状态数据统计*/
		public static final String TASK_COUNT ="230001";
		/**查询匹配到师傅*/
		public static final String QUERY_MACTH_SF ="230002";
		/**分配师傅*/
		public static final String DIS_SF ="230003";
		/**干线结束*/
		public static final String SAVE_PICK_INFO ="230004";
		/**货物明细*/
		public static final String GOODS_DETAIL ="230005";
		/**查询符合能力的师傅跟公司*/
		public static final String QUERY_SF_COMPANY ="230006";
		/**人工调度查询*/
		public static final String AI_QUERY_TASK ="230007";
		/**接单*/
		public static final String ACCEPT_RECEIPT ="230008";
		/**取消分配*/
		public static final String CANCER_DIS ="230009";
		/**web提货操作*/
		public static final String PICK_UP ="230010";
		/**WEB预约*/
		public static final String YY ="230011";
		/**WEB签收*/
		public static final String SIGN ="230012";
		/**WEB时效异常*/
		public static final String TIME_OUT_QUERY ="230013";
		/**WEB时效异常保存*/
		public static final String TIME_OUT_SAVE ="230014";
		/**WEB时效异常处理明细*/
		public static final String TIME_OUT_DTLE ="230015";
		/**WEB维修任务查询*/
		public static final String REPAIR_DO_QUERY ="230016";
		/**WEB维修任务统计查询*/
		public static final String REPAIR_COUNT1 ="230017";
		/**保存签收图*/
		public static final String DO_SAVE_SING_PIC ="230018";
		/**查询签收图*/
		public static final String QUERY_SING_PIC ="230019";
		/**异常查询*/
		public static final String EXC_QUERY ="230020";
		
		public static final String EXC_SERVE_QUERY ="230021";
		/**运单管理详细*/
		public static final String QUERY_ORD_DEATAIL ="230042";
		/**修改配安费用*/
		public static final String UPDATE_PA_MONEY ="230043";
		
		/**运单详细*/
		public static final String QUERY_WAY_DEATAIL ="230022";
		/**异常新增或修改*/
		public static final String EXCEPTION_SAVE_OR_MODIFY ="230023";
		/**异常处理完成*/
		public static final String EXCEPTION_DEAL_FINISH ="230024";
		/**查询维修能力的师傅*/
		public static final String QUERY_SF_MAIN ="230025";
		/**异常回显*/
		public static final String EXCE_TO_VIEW ="230026";
		/**异常处理*/
		public static final String EXCE_TO_DEAL ="230027";

		/**异常数量查询*/
		public static final String EXC_QUERY_COUNT ="230028";
		
		/**不同任务状态数据统计*/
		public static final String TASK_COUNT_INDEX ="230029";
		public static final String REPAIR_SIGN ="230050";
		/**到货*/
		public static final String WEB_ARRIVE_GOODS ="300088";
		public static final String app_ARRIVE_GOODS ="300087";
		/**WEB删除任务*/
		public static final String DELETE_TASK ="230089";
		
		/**查询运单图片*/
		public static final String QUERY_ORDER_PIC ="230090";
		/**保存运单图片*/
		public static final String SAVE_ORDER_PIC="230091";
		

		
	}

}
