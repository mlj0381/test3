package com.wo56.business.ord.intf;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ord.vo.OrdDepartInfo;
import com.wo56.business.ord.vo.OrdOpLog;
import com.wo56.business.ord.vo.OrdOrdersInfo;
import com.wo56.business.ord.vo.OrderInfo;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SysTenantDefCacheDataUtil;


public class OrdLogNewTF {
	/**服务电话*/
	private final static String  serivePhone = "${serivePhone}";
	private final static String  name = "${name}";
	private final static String  newName = "${newName}";
	private final static String  orderNo = "${orderNo}";
	private final static String  trackingNum = "${trackingNum}";
	private final static String  orgName ="${orgName}";
	private final static String  tenantName = "${tenantName}";
	private final static String  staCity = "${staCity}";
	private final static String  desCity = "${desCity}";
	private final static String desOrgName = "${desOrgName}";
	  private final static String  phone ="${phone}";
	
	/**下单-订货对内的内容展示模板*/
    private final static String SVAE_ORDER_TEMPLATE_IN = "客户已发货，电话：["+serivePhone+"]";
    /**下单-发货对内的内容展示模板*/
    private final static String SVAE_DELIVER_ORDER_TEMPLATE_IN = "商家已发货，电话：["+serivePhone+"]";
    
    /**下单-发货对外的内容展示模板*/
    private final static String SVAE_DELIVER_ORDER_TEMPLATE_OUT = "订单开始处理";
    /**分配拉包工对内的内容展示模板*/
    private final static String DIS_WORKER_IN = "已分配"+name+"，["+serivePhone+"]，进行拉包";
    /**分配拉包工对内的内容展示模板*/
    private final static String DIS_WORKER_OUT = "已分配【"+name+"】进行拉包揽货";
    /**调单分配拉包工对内的内容展示模板*/
    private final static String CHANGE_WORKER_IN = "拉包工【"+name+"】没及时拉货，将订单【"+orderNo+"】重新分配给拉包工【"+newName+"】";
    /**调单分配拉包工对外的内容展示模板*/
    private final static String CHANGE_WORKER_OUT = "已重新分配给【"+name+"】进行拉包揽货";

    /**改单对内的内容展示模板*/
    private final static String MODIFY_ORDER_IN = "订单修改";

    /**取消对内的内容展示模板*/
    private final static String CANCER_IN = "订单已取消";
    /**取消对外的内容展示模板*/
    private final static String CANCER_OUT = "订单已取消";
    /**取消对内的内容展示模板*/
    private final static String CANCER_AUTH_IN = "订单已发起取消申请";
    /**取消对外的内容展示模板*/
    private final static String CANCER_AUTH_OUT = "已发起取消申请";
    /**取消通过对内的内容展示模板*/
    private final static String DO_CANCER_AUTH_IN = "取消申请专线已审核通过";
    /**取消通过对外的内容展示模板*/
    private final static String DO_CANCER_AUTH_OUT = "订单已取消";
    /**发货对内的内容展示模板*/
    private final static String DELIVERY_GOODS_IN = "商家已备货，预计提货时间：";
    /**发货对外的内容展示模板*/
    private final static String DELIVERY_GOODS_OUT = "商家已打包好货物";
    /**催单对内的内容展示模板*/
    private final static String REMINDER_ORDER_IN = "商家进行催单";
    /**提货对外的内容展示模板*/
    private final static String PICK_UP_OUT = "“"+name+"”已揽货";
    /**提货对内的内容展示模板*/
    private final static String PICK_UP_IN = "“"+name+"”进行提货";
    /**关联运单号对外的内容展示模板*/
    private final static String LINKAGE_OUT = "【"+name+"】已收件";
    /**关联运单号对内的内容展示模板*/
    private final static String LINKAGE_IN = "“"+name+"”填写运单，运单号："+trackingNum+"，";
    /**打小费对外的内容展示模板*/
    private final static String PAY_TIPS_OUT = "【"+name+"】已收件,客服电话："+serivePhone;
    /**打小费对内的内容展示模板*/
    private final static String PAY_TIPS_IN = "网点的“"+name+"”打点小费成功";
    /**运单干线到货对外的内容展示模板*/
    private final static String GX_ARRIVER_GOODS_OUT = "已到达["+desOrgName+"]网点,上一站["+orgName+"]网点,客服电话：["+phone+"]";
    /**运单干线到货对内的内容展示模板*/
    private final static String GX_ARRIVER_GOODS_IN = "已到达["+desOrgName+"]网点,上一站["+orgName+"]网点,客服电话：["+phone+"]";
    /**派单干线到货对外的内容展示模板*/
    private final static String GX_ARRIVER_GOODS_OUT_ORDER = "货物已到达,客服电话："+serivePhone;
    /**派单干线到货对内的内容展示模板*/
    private final static String GX_ARRIVER_GOODS_IN_ORDER = "网点的“"+name+"”进行到货";
    /**配送对外的内容展示模板*/
    private final static String DELIVERY_OUT = "货物将由“"+name+"”进行配送";
    /**配送对内的内容展示模板*/
    private final static String DELIVERY_IN = "网点的“"+name+"”进行送货";
    /**签收对内的内容展示模板*/
    private final static String SIGN_OUT = "货物已签收";
    /**签收对外的内容展示模板*/
    private final static String SIGN_IN = "网点的“"+name+"”进行签收";
    /**签收对外的内容展示模板*/
    private final static String SIGN_IN_2 = "“"+name+"”进行签收操作"; 
  
    /**开单对内日记（“专线物流-网点”的“开单人”开单成功）*/
//    private final static String BILLING_IN = "“"+tenantName+"-"+orgName+"”的“"+name+"”开单成功";
    
    /**开单对外日记（“专线物流+网点”已收件，从“起始城市”发往“到达城市”，开单网点客服号码）*/
    private final static String BILLING_OUT = "“"+tenantName+"-"+orgName+"”已收件，从“"+staCity+"”发往“"+desCity+"”，开单网点客服号码："+serivePhone;
    
    /**取消对内的内容展示模板*/
    private final static String CANCER_PULL_IN = "拉包工已取消任务，请重新分配";
    /**取消对外的内容展示模板*/
    private final static String CANCER_PULL_ON = "拉包工已取消任务，请重新分配";	
	/**干线配载[增信1]网点新增配载*/
    private final static String TRANSPORT_IN="干线配载["+orgName+"]网点新增配载";
    /**配载对外日记*/
    private final static String TRANSPORT_ON="干线配载["+orgName+"]网点新增配载";
    
    private final static String DEPART_IN = "发车,前往["+desOrgName+"]网点,上一站["+orgName+"]网点,客服电话：["+phone+"]";
    
    private final static String DEPART_ON = "发车,前往["+desOrgName+"]网点,上一站["+orgName+"]网点,客服电话：["+phone+"]";
    
    private final static String CANCEL_DEPART_IN = "取消发车";
    
    private final static String CANCEL_DRPART_NO = "取消发车";
    
    private final static String ASTERN_IN = "到车,["+desOrgName+"]网点,上一站["+orgName+"]网点,客服电话：["+phone+"]";
    
    private final static String ASTERN_ON = "到车,["+desOrgName+"]网点,上一站["+orgName+"]网点,客服电话：["+phone+"]";
    
    private final static String CANCEL_ASTERN_IN = "取消到车";
    
    private final static String CANCEL_ASTERN_ON = "取消到车";
    
    private final static String CANCEL_ARRIVAL_IN ="取消到货";
    
    private final static String CANCEL_ARRIVAL_ON = "取消到货";
    
    private final static String BILLING_IN = "已揽货,客服电话：["+phone+"]";
    
    private final static String BILLING_ON = "已揽货,客服电话：["+phone+"]";
    
    private final static String CANCEL_TRANSPORT_IN = "取消配载";
    
    private final static String CANCEL_TRANSPORT_ON = "取消配载"; 
    private final static String DISPATCHING_IN = "["+orgName+"]网点“"+name+"”进行配送成功";
    
    private final static String DISPATCHING_ON = "["+orgName+"]网点“"+name+"”进行配送成功";
    
    private final static String DO_SIGN_IN = "“"+name+"”进行签收";
    
    private final static String DO_SIGN_ON = "货物已签收";
    
    public void departLog(BaseUser baseUser,int type,long orderId,OrdDepartInfo ordDepartInfo) throws Exception{
    	OrdOpLog ordOpLog =new OrdOpLog();
  		ordOpLog.setOpId(baseUser.getUserId());
  		ordOpLog.setOpName(baseUser.getUserName());
  		ordOpLog.setOrderId(orderId);
  		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
  		String inContent = "";
  		String onContent = "";
    	OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		OrdChildOpLogTF ordChildOpLogTF = (OrdChildOpLogTF) SysContexts.getBean("ordChildOpLogTF"); 
  		Organization organization = OraganizationCacheDataUtil.getOrganization(baseUser.getOrgId());
  		String tenantName  = SysTenantDefCacheDataUtil.getTenantName(baseUser.getTenantId().longValue());
  		int opType = 0;
  		if (type == 1) {
  			//发车
  			inContent = DEPART_IN.replace(desOrgName, ordDepartInfo.getDescOrgIdName()!=null?ordDepartInfo.getDescOrgIdName():"" )
  					.replace(orgName, ordDepartInfo.getSourceOrgIdName())
  					.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");;
  			onContent = DEPART_ON.replace(desOrgName, ordDepartInfo.getDescOrgIdName()!=null?ordDepartInfo.getDescOrgIdName():"" )
  					.replace(orgName, ordDepartInfo.getSourceOrgIdName())
  					.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");;
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.PUT_OUT;
		}else if(type == 2){//取消发车
			inContent = CANCEL_DEPART_IN;
  			onContent = CANCEL_DRPART_NO;
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.PUT_OUT_CANCEL;
		}else if(type == 3){
			//到车
			inContent = ASTERN_IN.replace(desOrgName, ordDepartInfo.getDescOrgIdName())
					.replace(orgName, ordDepartInfo.getSourceOrgIdName())
					.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");
  			onContent = ASTERN_ON.replace(desOrgName, ordDepartInfo.getDescOrgIdName())
					.replace(orgName, ordDepartInfo.getSourceOrgIdName())
					.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.ASTRREN;
		}else if(type == 4){//取消到车
			inContent = CANCEL_ASTERN_IN;
  			onContent = CANCEL_ASTERN_ON;
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.CANCEL_ASTRREN;
		}else if(type == 5){//取消到货
			inContent = CANCEL_ARRIVAL_IN;
  			onContent = CANCEL_ARRIVAL_ON;
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.CANCEL_ARRIVAL;
		}else if(type == 6){//配载
			inContent = TRANSPORT_IN.replace(orgName,ordDepartInfo.getSourceOrgIdName()!= null ? ordDepartInfo.getSourceOrgIdName() : "");
  			onContent = TRANSPORT_ON.replace(orgName, ordDepartInfo.getSourceOrgIdName()!= null ? ordDepartInfo.getSourceOrgIdName() : "");
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.DEPART_OP;
		}else if (type == 7){//开单
			inContent = BILLING_IN.replace(phone, organization.getSupportStaffPhone()!=null?organization.getSupportStaffPhone():"");
  			onContent = BILLING_ON.replace(phone, organization.getSupportStaffPhone()!=null?organization.getSupportStaffPhone():"");
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.OPEN_ORDERS;
		}else if(type == 8){
			inContent = CANCEL_TRANSPORT_IN;
  			onContent = CANCEL_TRANSPORT_ON;
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.DEPART_OP;
		}else if(type == 9){//到货
			inContent = GX_ARRIVER_GOODS_IN.replace(desOrgName, ordDepartInfo.getDescOrgIdName() != null ? ordDepartInfo.getDescOrgIdName() : "")
					.replace(orgName, ordDepartInfo.getSourceOrgIdName() != null ? ordDepartInfo.getSourceOrgIdName() : "")
					.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");
  			onContent = GX_ARRIVER_GOODS_OUT.replace(desOrgName, ordDepartInfo.getDescOrgIdName() != null ? ordDepartInfo.getDescOrgIdName() : "")
  					.replace(orgName, ordDepartInfo.getSourceOrgIdName() != null ? ordDepartInfo.getSourceOrgIdName() : "")
  					.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.GX_ARRIVE_GOODS;
		}else if(type == 10){//配送
			inContent = DISPATCHING_IN.replace(orgName, organization.getOrgName()).replace(name, baseUser.getUserName());
  			onContent = DISPATCHING_ON.replace(orgName, organization.getOrgName()).replace(name, baseUser.getUserName());
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.DISTRIBUTION_ORDERS;
		}else if(type == 11){
			inContent = DO_SIGN_IN.replace(name, baseUser.getUserName());
  			onContent = DO_SIGN_ON;
  			opType= SysStaticDataEnumYunQi.OP_TYPE_YUNQI.SIGN_ORDERS;
		}
  		ordOpLog.setOpType(opType);
  		ordOpLog.setInContent(inContent);
  		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
  		ordOpLog.setOutContent(onContent);
  		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
  		doSave(ordOpLog);
    }
       
    /**
     * 配送日记
     * @param userId
     * @param baseUser
     * @param orderInfo
     * @param ordersInfo
     * @throws Exception
     */
    public void departOrderLog(BaseUser baseUser,long orderId)throws Exception{
    	OrdOpLog ordOpLog =new OrdOpLog();
  		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.DEPART_OP);
  		ordOpLog.setOpId(baseUser.getUserId());
  		ordOpLog.setOpName(baseUser.getUserName());
  		ordOpLog.setOrderId(orderId);
  		Organization org = OraganizationCacheDataUtil.getOrganization(baseUser.getOrgId());
  		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
  		String inContent = "" ;
  		String onContent = "" ;
  		 inContent = DISPATCHING_IN.replace(orgName, org.getOrgName()).replace(name, baseUser.getUserName());
		 onContent = DISPATCHING_ON.replace(orgName, org.getOrgName()).replace(name, baseUser.getUserName());
		ordOpLog.setInContent(inContent!=null? inContent:"");	
  		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
  		ordOpLog.setOutContent(onContent!=null?onContent:"");
  	   //ordOpLog.setInContent(TRANSPORT_IN.replace(tenantName, org.getOrgName() != null ? org.getOrgName() : "")
	   //.replace(orgName, org.getOrgName() != null ? org.getOrgName() : ""));
  	   //ordOpLog.setOutContent(TRANSPORT_ON.replace(tenantName, org.getOrgName()  != null ? org.getOrgName() : "")
  		//.replace(orgName, org.getOrgName() != null ? org.getOrgName() : ""));
  		ordOpLog.setOutType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.DISTRIBUTION_ORDERS);
  		doSave(ordOpLog);
    }
    
    
    
    /**
     * 开单日记
     * @param userId
     * @param baseUser
     * @param orderInfo
     * @param ordersInfo
     * @throws Exception
     */
    public void billingInOrderLog(long userId,BaseUser baseUser,OrdOrdersInfo ordersInfo)throws Exception{
    	OrdOpLog ordOpLog =new OrdOpLog();
  		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.OPEN_ORDERS);
  		ordOpLog.setOpId(baseUser.getUserId());
  		ordOpLog.setOpName(baseUser.getUserName());
  		ordOpLog.setOrderId(ordersInfo.getOrderId());
  		Organization orgTenant = OraganizationCacheDataUtil.getOrganizationByTenantId(baseUser.getTenantId());
  		Organization org = OraganizationCacheDataUtil.getOrganization(baseUser.getOrgId());
  		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
  		String inContent = "";
  		String onContent = "";
  		inContent = BILLING_IN.replace(phone, org.getSupportStaffPhone()!=null?org.getSupportStaffPhone():"");
		onContent = BILLING_ON.replace(phone, org.getSupportStaffPhone()!=null?org.getSupportStaffPhone():"");
		ordOpLog.setInContent(inContent!=null? inContent:"");	
  		ordOpLog.setOutContent(onContent!=null?onContent:"");
  		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
  		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
  		doSave(ordOpLog);
    }
    
    
    /**
  	 * 签收操作日志
  	 * @param userId  操作员ID
  	 * @param deliveryName   配送人名称
  	 * @param baseUser 当前登录信息
  	 * @param orgName 网点名称
  	 * @param companyName 公司名称
  	 *  @param ordersInfo 下单信息
  	 *  @param type 1 客户签收 
  	 */
  	public void signUpOrderLog(long userId,BaseUser baseUser,OrdOrdersInfo ordersInfo,String companyName,String orgName,int type)throws Exception{
  		OrdOpLog ordOpLog =new OrdOpLog();
  		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.SIGN_ORDERS);
  		ordOpLog.setOpId(userId);
  		ordOpLog.setOpName(baseUser.getUserName());
  		ordOpLog.setOrderId(ordersInfo.getOrderId());
  		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
  		//ordOpLog.setInContent(type==1?SIGN_IN_2.replace(name, baseUser.getUserName()):"“"+companyName+"-"+orgName+"”"+SIGN_IN.replace(name, baseUser.getUserName()));
  		ordOpLog.setInContent(DO_SIGN_IN.replace(name, baseUser.getUserName()));
  		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
  		ordOpLog.setOutContent(DO_SIGN_ON);
  		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
  		doSave(ordOpLog);
  	}	
    
  	
    
    /**
  	 * 配送操作日志
  	 * @param userId  操作员ID
  	 * @param deliveryName   配送人名称
  	 * @param baseUser 当前登录信息
  	 * @param orgName 网点名称
  	 * @param companyName 公司名称
  	 *  @param ordersInfo 下单信息
  	 */
  	public void deliveryOrderLog(long userId,BaseUser baseUser,OrdOrdersInfo ordersInfo,String companyName,String deliveryName,String orgName1)throws Exception{
  		OrdOpLog ordOpLog =new OrdOpLog();
  		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.DISTRIBUTION_ORDERS);
  		ordOpLog.setOpId(userId);
  		ordOpLog.setOpName(baseUser.getUserName());
  		ordOpLog.setOrderId(ordersInfo.getOrderId());
  		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
	    Organization organization = OraganizationCacheDataUtil.getOrganization(baseUser.getOrgId());
  		String inContent = "";
  		String onContent = "";
  		inContent = DISPATCHING_IN.replace(orgName, organization.getOrgName()).replace(name, baseUser.getUserName());
	    onContent = DISPATCHING_ON.replace(orgName, organization.getOrgName()).replace(name, baseUser.getUserName());
  		//ordOpLog.setInContent("“"+companyName+"-"+orgName+"”"+DELIVERY_IN.replace(name, deliveryName));
		ordOpLog.setInContent(inContent!=null?inContent:"");
  		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
  		ordOpLog.setOutContent(onContent!=null?onContent:"");
  		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
  		doSave(ordOpLog);
  	}	
    
    /**
  	 * 干线到货操作日志
  	 * @param userId  操作员ID
  	 * @param deliveryName   配送人名称
  	 * @param baseUser 当前登录信息
  	 * @param orgName 网点名称
  	 * @param companyName 公司名称
  	 *  @param ordersInfo 下单信息
  	 */
  	public void gxArriverGoodsOrderLog(long userId,BaseUser baseUser,OrdOrdersInfo ordersInfo,String companyName,String deliveryName,String orgNames,String phones,OrdDepartInfo ordDepartInfo)throws Exception{
  		OrdOpLog ordOpLog =new OrdOpLog();
  		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.GX_ARRIVE_GOODS);
  		ordOpLog.setOpId(userId);
  		ordOpLog.setOpName(baseUser.getUserName());
  		ordOpLog.setOrderId(ordersInfo.getOrderId());
  		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
  		Organization organization = OraganizationCacheDataUtil.getOrganization(baseUser.getOrgId());
  		String inContent = "";
  		String onContent = "";
  		inContent = GX_ARRIVER_GOODS_IN.replace(desOrgName, ordDepartInfo.getDescOrgIdName() != null ? ordDepartInfo.getDescOrgIdName() : "")
				.replace(orgName, ordDepartInfo.getSourceOrgIdName() != null ? ordDepartInfo.getSourceOrgIdName() : "")
				.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");
			onContent = GX_ARRIVER_GOODS_OUT.replace(desOrgName, ordDepartInfo.getDescOrgIdName() != null ? ordDepartInfo.getDescOrgIdName() : "")
					.replace(orgName, ordDepartInfo.getSourceOrgIdName() != null ? ordDepartInfo.getSourceOrgIdName() : "")
					.replace(phone, organization.getSupportStaffPhone() != null ? organization.getSupportStaffPhone() : "");
			ordOpLog.setInContent(inContent!=null? inContent:"");	
	  		ordOpLog.setOutContent(onContent!=null?onContent:"");
			//ordOpLog.setInContent("“"+companyName+"-"+orgName+"”"+GX_ARRIVER_GOODS_IN.replace(name, deliveryName));
  		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
  		//ordOpLog.setOutContent(GX_ARRIVER_GOODS_OUT.replace(serivePhone, phone));
  		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
  		doSave(ordOpLog);
  	}	
  	
  	
  	/**
  	 * 干线到货操作日志
  	 * @param userId  操作员ID
  	 * @param deliveryName   配送人名称
  	 * @param baseUser 当前登录信息
  	 * @param orgName 网点名称
  	 * @param companyName 公司名称
  	 *  @param ordersInfo 下单信息
  	 */
  	public void gxArriverGoodsOrderLog(long userId,BaseUser baseUser,OrdOrdersInfo ordersInfo,String companyName,String deliveryName,String orgName,String phone)throws Exception{
  		OrdOpLog ordOpLog =new OrdOpLog();
  		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.GX_ARRIVE_GOODS);
  		ordOpLog.setOpId(userId);
  		ordOpLog.setOpName(baseUser.getUserName());
  		ordOpLog.setOrderId(ordersInfo.getOrderId());
  		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
  		Organization organization = OraganizationCacheDataUtil.getOrganization(baseUser.getOrgId());
		ordOpLog.setInContent("“"+companyName+"-"+orgName+"”"+GX_ARRIVER_GOODS_IN_ORDER.replace(name, deliveryName));
  		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
  		ordOpLog.setOutContent(GX_ARRIVER_GOODS_OUT_ORDER.replace(serivePhone, phone));
  		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
  		doSave(ordOpLog);
  	}	
  	
  	
  	
    
    /**
  	 * 打点小费操作日志
  	 * @param userId  操作员ID
  	 * @param inputUserName   开单人
  	 * @param baseUser 当前登录信息
  	 * @param orgName 网点名称
  	 * @param companyName 公司名称
  	 *  @param ordersInfo 下单信息
  	 */
  	public void payTipsOrderLog(long userId,BaseUser baseUser,OrdOrdersInfo ordersInfo,String companyName,String inputUserName,String orgName,String phone)throws Exception{
  		OrdOpLog ordOpLog =new OrdOpLog();
  		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.SEND_TIP_MONEY);
  		ordOpLog.setOpId(userId);
  		ordOpLog.setOpName(baseUser.getUserName());
  		ordOpLog.setOrderId(ordersInfo.getOrderId());
  		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
  		ordOpLog.setInContent(companyName+"-"+orgName+PAY_TIPS_IN.replace(name, inputUserName));
  		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
  		ordOpLog.setOutContent(PAY_TIPS_OUT.replace(name, companyName).replace(serivePhone, phone));
  		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
  		doSave(ordOpLog);
  	}	
    
    /**
  	 * 填写运单操作日志
  	 * @param userId  操作员ID
  	 * @param trackingNum   运单号
  	 * @param baseUser 当前登录信息
  	 * @param workerName 拉包工名称
  	 * @param companyName 公司名称
  	 *  @param ordersInfo 下单信息
  	 */
  	public void linkageOrderLog(long userId,BaseUser baseUser,OrdOrdersInfo ordersInfo,String companyName,String workerName,String trackNum)throws Exception{
  		OrdOpLog ordOpLog =new OrdOpLog();
  		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.RELATION_WAYBILL);
  		ordOpLog.setOpId(userId);
  		ordOpLog.setOpName(baseUser.getUserName());
  		ordOpLog.setOrderId(ordersInfo.getOrderId());
  		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
  		ordOpLog.setInContent(LINKAGE_IN.replace(name, workerName).replace(trackingNum, trackNum)+companyName+"已收件");
  		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
  		ordOpLog.setOutContent(LINKAGE_OUT.replace(name, companyName));
  		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
  		doSave(ordOpLog);
  	}	
  	
  	
    /**
  	 * 提货操作日志
  	 * @param userId  操作员ID
  	 * @param baseUser 当前登录信息
  	 * @param name 拉包工名称
  	 *  @param ordersInfo 下单信息
  	 */
  	public void pickupOrderLog(long userId,BaseUser baseUser,OrdOrdersInfo ordersInfo,String workerName)throws Exception{
  		OrdOpLog ordOpLog =new OrdOpLog();
  		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.CARRY_GOODS);
  		ordOpLog.setOpId(userId);
  		ordOpLog.setOpName(baseUser.getUserName());
  		ordOpLog.setOrderId(ordersInfo.getOrderId());
  		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
  		ordOpLog.setInContent(PICK_UP_IN.replace(name, workerName));
  		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
  		ordOpLog.setOutContent(PICK_UP_OUT.replace(name, workerName));
  		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
  		doSave(ordOpLog);
  	}	
  	
  	
    /**
  	 * 催单操作日志
  	 * @param userId  操作员ID
  	 * @param baseUser 当前登录信息
  	 *  @param ordersInfo 下单信息
  	 */
  	public void reminderOrderLog(long userId,BaseUser baseUser,OrdOrdersInfo ordersInfo)throws Exception{
  		OrdOpLog ordOpLog =new OrdOpLog();
  		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.OP_TYPE_REMINDER_ORDERS);
  		ordOpLog.setOpId(userId);
  		ordOpLog.setOpName(baseUser.getUserName());
  		ordOpLog.setOrderId(ordersInfo.getOrderId());
  		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
  		ordOpLog.setInContent(REMINDER_ORDER_IN);
  		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
  		ordOpLog.setOutContent("");
  		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_NO);
  		doSave(ordOpLog);
  	}	
    
    /**
  	 * 发货操作日志
  	 * @param userId  操作员ID
  	 * @param planPickTime  预计提货时间
  	 * @param baseUser 当前登录信息
  	 *  @param ordersInfo 下单信息
  	 */
  	public void deliveryGoodsOrderLog(long userId,BaseUser baseUser,OrdOrdersInfo ordersInfo,String planPickTime)throws Exception{
  		OrdOpLog ordOpLog =new OrdOpLog();
  		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.DELIVER_GOODS);
  		//正常运单开单
  		ordOpLog.setOpId(userId);
  		ordOpLog.setOpName(baseUser.getUserName());
  		ordOpLog.setOrderId(ordersInfo.getOrderId());
  		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
  		ordOpLog.setInContent(DELIVERY_GOODS_IN+planPickTime);
  		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
  		ordOpLog.setOutContent(DELIVERY_GOODS_OUT);
  		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
  		doSave(ordOpLog);
  	}	
  	
    /**
	 * 取消审核日志
	 * @param userId  操作员ID
	 * @param type  1已开单 
	 * @param baseUser 当前登录信息
	 *  @param ordersInfo 下单信息
	 */
	public void cancerAuthOrderLog(long userId,BaseUser baseUser,OrdOrdersInfo ordersInfo)throws Exception{
		OrdOpLog ordOpLog =new OrdOpLog();
		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.CHANGE_AUTH_RECEIPT);
		//正常运单开单
		ordOpLog.setOpId(userId);
		ordOpLog.setOpName(baseUser.getUserName());
		ordOpLog.setOrderId(ordersInfo.getOrderId());
		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
		ordOpLog.setInContent(DO_CANCER_AUTH_IN);
		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
		ordOpLog.setOutContent(DO_CANCER_AUTH_OUT);
		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
		doSave(ordOpLog);
	}	
	
    /**
	 * 取消日志
	 * @param userId  操作员ID
	 * @param type  1已开单 
	 * @param baseUser 当前登录信息
	 *  @param ordersInfo 下单信息
	 */
	public void cancerOrderLog(long userId,int type,BaseUser baseUser,OrdOrdersInfo ordersInfo)throws Exception{
		OrdOpLog ordOpLog =new OrdOpLog();
		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.CANCEL_ORDER);
		//正常运单开单
		ordOpLog.setOpId(userId);
		ordOpLog.setOpName(baseUser.getUserName());
		ordOpLog.setOrderId(ordersInfo.getOrderId());
		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
		ordOpLog.setInContent(type==1?CANCER_AUTH_IN:CANCER_IN);
		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
		ordOpLog.setOutContent(type==1?CANCER_OUT:CANCER_AUTH_OUT);
		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
		doSave(ordOpLog);
	}	
	

	/**
	 * 取消日志
	 * @param userId  操作员ID
	 * @param type  1已开单 
	 * @param baseUser 当前登录信息
	 *  @param ordersInfo 下单信息
	 */
	public void cancerOrderLogPull(long userId,int type,BaseUser baseUser,OrdOrdersInfo ordersInfo)throws Exception{
		OrdOpLog ordOpLog =new OrdOpLog();
		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.CANCEL_ORDER);
		//正常运单开单
		ordOpLog.setOpId(userId);
		ordOpLog.setOpName(baseUser.getUserName());
		ordOpLog.setOrderId(ordersInfo.getOrderId());
		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
		ordOpLog.setInContent(CANCER_PULL_IN);
		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
		ordOpLog.setOutContent(CANCER_PULL_ON);
		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
		doSave(ordOpLog);
	}
	
	/**
	 * 下单日志
	 * @param userId  操作员ID
	 * @param orderType 下单类型 1、发货 2、订货
	 * @param baseUser 当前登录信息
	 *  @param ordersInfo 下单信息
	 */
	public void saveOrderLog(long userId,int orderType,String phone,BaseUser baseUser,OrdOrdersInfo ordersInfo)throws Exception{
		OrdOpLog ordOpLog =new OrdOpLog();
		ordOpLog.setOpType(orderType==1?SysStaticDataEnumYunQi.OP_TYPE_YUNQI.SAVE_DELIVERY_ORDERS:SysStaticDataEnumYunQi.OP_TYPE_YUNQI.SAVE_COLLECT_ORDERS);
		//正常运单开单
		ordOpLog.setOpId(userId);
		ordOpLog.setOpName(baseUser.getUserName());
		ordOpLog.setOrderId(ordersInfo.getOrderId());
		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
		ordOpLog.setInContent(orderType==1?SVAE_DELIVER_ORDER_TEMPLATE_IN.replace(serivePhone, phone):SVAE_ORDER_TEMPLATE_IN.replace(serivePhone, phone));
		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
		ordOpLog.setOutContent(SVAE_DELIVER_ORDER_TEMPLATE_OUT);
		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
		doSave(ordOpLog);
	}	
	
	/**
	 * 分配拉包工日志
	 * @param userId  操作员ID
	 * @param workerName 拉包工名称
	 * @param baseUser 当前登录信息
	 *  @param ordersInfo 下单信息
	 */
	public void disWorkerLog(long userId,String phone,BaseUser baseUser,OrdOrdersInfo ordersInfo,String workerName)throws Exception{
		OrdOpLog ordOpLog =new OrdOpLog();
		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.DIS_OP);
		//正常运单开单
		ordOpLog.setOpId(userId);
		ordOpLog.setOpName(baseUser!=null?baseUser.getUserName():"系统自动匹配");
		ordOpLog.setOrderId(ordersInfo.getOrderId());
		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
		ordOpLog.setInContent(DIS_WORKER_IN.replace(serivePhone, phone).replace(name, workerName));
		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
		ordOpLog.setOutContent(DIS_WORKER_OUT.replace(name, workerName));
		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
		doSave(ordOpLog);
	}	

	/**
	 * 更改拉包工日志
	 * @param userId  操作员ID
	 * @param oldworkerName 旧拉包工名称
	 * @param newWorkerName 新的拉包工名称
	 * @param baseUser 当前登录信息
	 *  @param ordersInfo 下单信息
	 */
	public void changeWorkerLog(long userId,String  newWorkerName,BaseUser baseUser,OrdOrdersInfo ordersInfo,String oldWorkerName)throws Exception{
		OrdOpLog ordOpLog =new OrdOpLog();
		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.CHANGE_RECEIPT);
		//正常运单开单
		ordOpLog.setOpId(userId);
		ordOpLog.setOpName(baseUser.getUserName());
		ordOpLog.setOrderId(ordersInfo.getOrderId());
		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
		ordOpLog.setInContent(CHANGE_WORKER_IN.replace(newName, newWorkerName).replace(name, oldWorkerName).replace(orderNo, ordersInfo.getOrderNo()));
		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
		ordOpLog.setOutContent(CHANGE_WORKER_OUT.replace(newName, newWorkerName));
		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_YES);
		doSave(ordOpLog);
	}	
	
	/**
	 *改单日志
	 * @param userId  操作员ID
	 * @param oldworkerName 旧拉包工名称
	 * @param newWorkerName 新的拉包工名称
	 * @param baseUser 当前登录信息
	 *  @param ordersInfo 下单信息
	 */
	public void modifyOrderLog(long userId,BaseUser baseUser,OrdOrdersInfo ordersInfo)throws Exception{
		OrdOpLog ordOpLog =new OrdOpLog();
		ordOpLog.setOpType(SysStaticDataEnumYunQi.OP_TYPE_YUNQI.CHANGE_RECEIPT);
		//正常运单开单
		ordOpLog.setOpId(userId);
		ordOpLog.setOpName(baseUser.getUserName());
		ordOpLog.setOrderId(ordersInfo.getOrderId());
		ordOpLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
		ordOpLog.setInContent(MODIFY_ORDER_IN);
		ordOpLog.setInType(SysStaticDataEnum.OP_TYPE.IN_TYPE_YES);
		ordOpLog.setOutContent("");
		ordOpLog.setOutType(SysStaticDataEnum.OP_TYPE.OUT_TYPE_NO);
		doSave(ordOpLog);
	}	
	
	
	/**
	 * 保存
	 * @param ordOpLog
	 * @throws Exception
	 */
	protected void doSave(OrdOpLog ordOpLog) throws Exception{
		Session session = SysContexts.getEntityManager();
		session.saveOrUpdate(ordOpLog);
	}
}
