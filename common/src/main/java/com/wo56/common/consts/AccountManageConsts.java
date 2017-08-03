package com.wo56.common.consts;
/**
 * 账户管理枚举类型
 * @author zx
 *
 */
public class AccountManageConsts {
	
	public static class Common{
		
		/**
		 * 现在有效记录的科目（#TODO20161113）
		 * 
		 * 1现付2到付3回单付4月结5代收货款8回扣 22转账23配安费24运费25装卸费 26 异常费27维修费 28免费补发29提货费30实际提货费31垫付货款
		 * 
		 * 
		 * 
		 */
		/***签收后、修改费运单 可删除费用的集合*/
		public static final Integer[] signCanDelFeeTypes = 
				   new Integer[]{Common.FEE_TYPE_CASH_PAY,Common.FEE_TYPE_ARRIVE_PAY,
			                      Common.FEE_TYPE_BACK_PAY,Common.FEE_TYPE_MONTH_PAY,
			                        Common.FEE_TYPE_GET_GOODS_PAY,Common.FEE_TYPE_GET_POUN_PAY,
			                          Common.FEE_TYPE_FORWARD_ACCOUNT_FEE,Common.FEE_TYPE_SF_ACCOUNT_FEE,
			                            Common.FEE_TYPE_FREE_MONEY,Common.FEE_TYPE_PROVE_TIP,
			                              Common.FEE_TYPE_DRIVERLY_GOODS_MONEY,
			                                Common.FEE_TYPE_ACTUAL_DRIVERLY_GOODS_MONEY,
			                                  Common.FEE_TYPE_MAT_GOODS_MONEY,
		                           };
		/***运费是否核销的标志*/
		public static final Integer[] feeTranferFlag = 
				   new Integer[]{  AccountManageConsts.Common.FEE_TYPE_CASH_PAY,
			                        AccountManageConsts.Common.FEE_TYPE_ARRIVE_PAY,
	                                  AccountManageConsts.Common.FEE_TYPE_BACK_PAY,
	                                    AccountManageConsts.Common.FEE_TYPE_MONTH_PAY,
	                                      AccountManageConsts.Common.FEE_TYPE_FORWARD_ACCOUNT_FEE
		                           };
		//1现付2到付3回单付4月结5代收货款8回扣 22转账23配安费24运费25装卸费 26 异常费27维修费 28免费补发29提货费30实际提货费31垫付货款
		/**现付*/
		public static final int FEE_TYPE_CASH_PAY = 1;//现付
		/**到付*/
		public static final int FEE_TYPE_ARRIVE_PAY = 2;//到付
		/**回单付*/
		public static final int FEE_TYPE_BACK_PAY = 3;//回单付
		/**月结*/
		public static final int FEE_TYPE_MONTH_PAY = 4;//月结
		/**代收货款*/
		public static final int FEE_TYPE_GET_GOODS_PAY = 5;//代收货款
		/**代收核销*/
		public static final int FEE_TYPE_PROVE_GOODS_PAY = 6;//代收核销
		/**回扣*/
		public static final int FEE_TYPE_PROVE_TIP = 8;//回扣
		/**充值*/
		public static final int FEE_TYPE_PROVE_CHONGZHI = 9;//充值
		/**问题费用*/
		public static final int FEE_TYPE_QUESTION = 11;//问题费用
		/**异常费用*/
		public static final int FEE_TYPE_EXCEPTION = 12;//异常费用
		/**中转费用*/
		public static final int FEE_TYPE_TRANSFER = 13;//中转费用
		/**代收货款手续费*/
		public static final int FEE_TYPE_GET_POUN_PAY= 14;//代收货款手续费
		/**费用托管*/
		public static final int FEE_TYPE_COMPANY_MANAGE_FEE = 99;//费用托管
		
		/**转账费用*/
		public static final int FEE_TYPE_FORWARD_ACCOUNT_FEE = 22;//转账费用
		
		/**师傅配安费*/
		public static final int FEE_TYPE_SF_ACCOUNT_FEE = 23;//配安费
		
		/**司机运费*/
		public static final int FEE_TYPE_TRANSPORT_FEE = 24;//运费
		
		/**配载装卸费*/
		public static final int FEE_TYPE_ZX_FEE = 25;//装卸费
		
		/**异常费用*/
		public static final int FEE_TYPE_EXCEPTION_FEE = 26;//异常费
		
		/**维修费用*/
		public static final int FEE_TYPE_MAINTAIN_FEE = 27;//维修费
		/**免费补发*/
		public static final int FEE_TYPE_FREE_MONEY = 28;//免费补发
		
		/**提货费*/
		public static final int FEE_TYPE_DRIVERLY_GOODS_MONEY = 29;//提货费
		
		/**实际提货费*/
		public static final int FEE_TYPE_ACTUAL_DRIVERLY_GOODS_MONEY = 30;//实际提货费
		
		/**垫付货款*/
		public static final int FEE_TYPE_MAT_GOODS_MONEY = 31;//垫付货款
		/**船运费运费*/
		public static final int FEE_TYPE_SHIP_TRANSPORT_FEE = 32;//运费
		
		/**船运配载装卸费*/
		public static final int FEE_TYPE_SHIP_ZX_FEE = 33;//装卸费
		
		//支付状态1、未收2、已收3、未支4、已支.  
		/**未收*/
		public static final int PAY_STS_NO_RECEIVE = 1;//未收
		/**已收*/
		public static final int PAY_STS_HAS_RECEIVE = 2;//已收
		/**未支*/
		public static final int PAY_STS_NO_PAY = 3;//未支
		/**已支*/
		public static final int PAY_STS_HAS_PAY = 4;//已支
		//对象类型：1操作员2业务员3开单员4网点5  客户 6中转方7发货方8收货方9司机10师傅11服务商
		/**操作员*/
		public static final int OBJ_TYPE_OPERATOR = 1;//操作员
		/**业务员*/
		public static final int OBJ_TYPE_SALES = 2;//业务员
		/**开单员*/
		public static final int OBJ_TYPE_OPENER = 3;//开单员
		/**网点*/
		public static final int OBJ_TYPE_POINT = 4;//网点
		/**客户*/
		public static final int OBJ_TYPE_CUSTOMER = 5;//客户
		/**中转方*/
		public static final int OBJ_TYPE_TRANSFER = 6;//中转方
		/**发货方*/
		public static final int OBJ_TYPE_SHIPPER = 7;//发货方
		/**收货方*/
		public static final int OBJ_TYPE_RECEIVE = 8;//收货方
		/**司机*/
		public static final int OBJ_TYPE_DRIVERER = 9;//司机
		/**师傅*/
		public static final int OBJ_TYPE_SF = 10;//师傅
		/**服务商*/
		public static final int OBJ_TYPE_SF_PARTANERS = 11;//服务商
		/**船运公司*/
		public static final int OBJ_TYPE_SHIP_COMPANY = 12;//船运公司
		
		public static final int FEE_TYPE_COMPANY_FREIGHT_FEE = 24;//运费
		
		
		
		
		//收支类型:1收入2支出. 
		/**收入*/
		public static final int PAY_TYPE_INCOME = 1;//收入
		/**支出*/
		public static final int PAY_TYPE_OUTCOME = 2;//支出
		/**需要反转*/
		public static final  int isReversalYes = 1; //需要反转
		/**不需要反转*/
		public static final  int isReversalNo = 2;//不需要反转
		/**同租户*/
		public static final  int tenantType1 = 1; //同租户 
		/**不同租户 */
		public static final  int tenantType2 = 2; //不同租户 
		
	}
	/**
	 * 账户主表
	 * @author zx
	 *
	 */
	public static class AcAccount {
		/**帐户网点类型1、网点账户2、用户账户*/  
		/**网点账户*/
		public static final int OBJECT_TYPE_POINT = 1;//网点账户
		/**用户账户*/
		public static final int OBJECT_TYPE_USER = 2;//用户账户
		/**账户类型:1现金账户2平台账户3押金账户*/ 
		/**现金账户*/
		public static final int ACCOUNT_TYPE_CASH = 1;//现金账户
		/**平台账户*/
		public static final int ACCOUNT_TYPE_SYSTEM = 2;//平台账户
		/**押金账户*/
		public static final int ACCOUNT_TYPE_DEPOSIT = 3;//押金账户
	}
	/**
	 * 账户明细
	 * @author zx
	 *
	 */
	public static class AcAccountDtl {
		//交易类型:1现金2平台账户.  
		/**现金账户*/
		public static final int BUSI_TYPE_CASH = 1;//现金账户
		/**平台账户*/
		public static final int BUSI_TYPE_SYSTEM = 2;//平台账户
		//网点环节类型:1开单节点2服务节点3配送4中转5配载发车
		/**开单节点*/
		public static final int NODE_TYPE_BEGIN = 1;//开单节点
		/**服务节点*/
		public static final int NODE_TYPE_MID = 2;//服务节点
		/**配送节点*/
		public static final int NODE_TYPE_END = 3;//配送节点
		/**中转*/
		public static final int NODE_TYPE_TRANSIT = 4;//中转
		/**配载发车*/
		public static final int NODE_TYPE_DEPART = 5;//配载发车
		
		/**船运配载*/
		public static final int NODE_TYPE_SHIPPING_DEPART = 6;//船运配载
		/**其他*/  
		public static final int NODE_TYPE_OTHER = 0;//其他
		//回单付是否签收：0未签收1已签收.   
		/**未签收*/
		public static final int IS_SIGN_IN_NO = 0;//未签收
		/**已签收*/
		public static final int IS_SIGN_IN_YES = 1;//已签收
	}
	/**
	 * 核销表
	 * @author zx
	 *
	 */
	public static class AcCashProve {
		//核销状态 1、已核销 2、未核销.
		/**已核销 */
		public static final int CHECK_STS_OK = 1;//已核销
		/**未核销 */
		public static final int CHECK_STS_NON = 2;//未核销.
		/**部分核销 */
		public static final int CHECK_STS_OK_PART = 3;//部分核销.
		//现金状态：1现金已收 2现金未收
		/**现金已收 */
		public static final int CASH_STS_YES = 1;//现金已收
		/**现金未收 */
		public static final int CASH_STS_NO = 2;//现金未收
		//回单付是否签收：0未签收1已签收
		/**未签收 */
		public static final int IS_SIGN_IN_0 = 0;//未签收
		/**已签收 */
		public static final int IS_SIGN_IN_1 = 1;//已签收
	}
	/**
	 * web 展示的支付方式枚举
	 * @author dxb
	 *
	 */
	public static class PAYMENT_TYPE {
	    //支付方式1-1、现付 2、到付、3、月结 4、回单付 6、转账。code_type='PAYMENT_TYPE'
		/**现付*/
		public static final int PAYMENT_TYPE1 = 1;//现付
		/**到付*/
		public static final int PAYMENT_TYPE2 = 2;//到付
		/**月结 */
		public static final int PAYMENT_TYPE3 = 3;//月结
		/**回单付 */
		public static final int PAYMENT_TYPE4 = 4;//回单付
		/**转账 */
		public static final int PAYMENT_TYPE6 = 6;//转账
		/**免费补发 */
		public static final int PAYMENT_TYPE7 = 7;//免费补发
	
	}
	  
}