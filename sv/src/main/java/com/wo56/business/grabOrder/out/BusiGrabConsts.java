package com.wo56.business.grabOrder.out;


public class BusiGrabConsts {

	public final class  Order{
		/**商家的用户userId*/
		public static final String BUSI_USER_ID="GRAB_BUSI_USER_ID";
		/**专线的租户ID**/
		public static final String ZX_TENANT_ID="GRAB_ZX_TENANT_ID";
		/**发送次数**/
		public static final String SEND_COUNT="GRAB_SEND_COUNT";
		/**纬度**/
		public static final String LATITUDE="GRAB_POINT_LATITUDE";
		/**经度**/
		public static final String LONGITUDE="GRAB_POINT_LONGITUDE";
		/***订单号***/
		public static final String ORDER_NO ="ORDER_NO";
		/***创建时间***/
		public static final String CREATE_TIME ="CREATE_TIME";
		/***预约时间***/
		public static final String HANDLER_TIME ="HANDLER_TIME";
		/***提货地址***/
		public static final String PICK_ADDR ="PICK_ADDR";
		/***到站***/
		public static final String DEST_CITY_NAME="DEST_CITY_NAME";
		/**订单id**/
		public static final String ORDER_ID="ORDER_ID";
	}
	
	public final class  User{
		/**纬度**/
		public static final String LATITUDE="GRAB_POINT_LATITUDE";
		/**经度**/
		public static final String LONGITUDE="GRAB_POINT_LONGITUDE";
		/**租户**/
		public static final String TENANT_ID="GRAB_TENANT_ID";
		/**接单数**/
		public static final String SINGULAR_NUM="GRAB_SINGULAR_NUM";
		/**最大接单数**/
		public static final String MAX_SINGULAR_NUM="GRAB_MAX_SINGULAR_NUM";
		/**上下班**/
		public static final String REST="GRAB_REST";
		/**手机号码**/
		public static final String BILL="GRAB_BILL";
	}
	
	public final class  MultOrder{
		/**发送次数**/
		public static final String SEND_COUNT="GRAB_SEND_COUNT";
		/**时间戳**/
		public static final String SEND_TIME="GRAB_SEND_TIME";
	}
	
	/***静态参数，定义发送次数，跟距离的关系  code_type=COUNT_AND_DISTANCE, code_value=次数，code_name=范围，单位是米 ***/
	public static final String COUNT_AND_DISTANCE="COUNT_AND_DISTANCE";
	/**抢单的时间间隔 值的单位为分钟***/
	public static final String GRAB_TIME_INTERVAL = "GRAB_TIME_INTERVAL";
}
