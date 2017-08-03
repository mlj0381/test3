package com.wo56.business.matchyq.impl;


public class BusiMatchConstsYq {

	/* 字段属性, 由业务决定 */
	public final class Fields {
		public static final String maxLoad = "maxLoad";	//拉包工的最大的接单数
		public static final String pointX = "pointX";	//纬度(工人仓库, 运单收货人地址)
		public static final String pointY = "pointY";	//经度(工人仓库, 运单收货人地址)
		public static final String tenantId="tenantId"; //租户id
		
		public static final String createId="createId";//订单的创建的人的userId
		
	}
	
	public static final String z_tenantId="-1";//z端的默认的租户ID，z端是全量数据，没有分租户
}
