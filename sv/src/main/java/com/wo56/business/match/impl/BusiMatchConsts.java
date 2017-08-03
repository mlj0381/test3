package com.wo56.business.match.impl;


public class BusiMatchConsts {

	/* 字段属性, 由业务决定 */
	public final class Fields {
		public static final String area = "area";	//县(工人，多个，运单1个 , id以,分割)
		public static final String servType = "servType";	//服务能力(产品目录name｜服务能力name，　多个, 例如：床｜安装,床｜配送,维修,返货,养护)
		public static final String servExtAttr = "servExtAttr";	//增值服务能力扩展属性(维修服务能力name｜属性name，　多个, 例如：维修|皮具,维修|板材,返货)
		public static final String maxLoad = "maxLoad";	//可用最大接单数(运单无)
		public static final String credit = "credit";	//信用(运单无)
		public static final String storeCub = "storeCub";	//可用仓库面积、运单体积
		public static final String carType = "carType";	//车型
		public static final String carCub = "carCub";	//载方
		public static final String pointX = "pointX";	//纬度(工人仓库, 运单收货人地址)
		public static final String pointY = "pointY";	//经度(工人仓库, 运单收货人地址)
	}

}
