package com.wo56.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.core.SysContexts;
import com.wo56.common.sms.intf.SysSmsTF;

public class SmsUtil {
	
	private static transient Log log = LogFactory.getLog(SmsUtil.class);
	
	//短信验证码
	/**app发送注册短信:【联运汇平台】您的验证码：${code}*/
	private static Long SYS_TEMP_VAILD=10001L;
	/**web+app发送商家账号密码:【联运汇平台】您好，您的商家编码是：${tenantCode},账号是：${loginAcct},密码是：${loginPwd}！http://sj.lyh.wo56.com/index.html*/
	private static Long SYS_TEMP_BUS_APP=10004L;
	/**web+app发送师傅合作商账号密码:【联运汇平台】您好，您的师傅编码是：${tenantCode},账号是：${loginAcct},密码是：${loginPwd}！http://sf.lyh.wo56.com/index.html*/
	private static Long SYS_TEMP_SF_APP=10005L;
	/**增加分配合作商:【联运汇平台】您好，${orgName}将运单号${trackingNum},的配安任务已分配给您，请关注运单情况，到货后即可配送。*/
	private static Long SYS_SP_HZ = 10006L;
	/**增加分配个体师傅发送短信: 【联运汇平台】您好，${orgName}将运单号${trackingNum},的配安任务已分配给您，请登录联运到家app进行接单，配送。*/
	private static Long SYS_SP_SF = 10007L;
	/**增加配载分配司机: 【联运汇平台】您好，${orgName}将批次号${batchNum}，从${beginOrgName}发往${endOrgName}的运输任务已指派给您，请关注！*/
	private static Long SYS_SP_SJ = 10008L;
	/**进行预约送货: 【联运汇平台】您好，运单号${trackingNum}将由${userName}:${billId}，在${time}给您配送安装，请关注！*/
	private static Long SYS_YYSH = 10009L;
	
//	private static Long ONESELF_ARR_PAY = 1000000004L;
//	private static Long ONESELF_NOW_PAY = 1000000003L;
//	private static Long HOME_NOW_PAY = 1000000005L;
//	private static Long HOME_ARR_PAY = 1000000006L;
//	private static Long ONESELF_COLLMONEY_ARR_PAY = 1000000007L;
//	private static Long HOME_COLLMONEY_ARR_PAY = 1000000008L;
//	private static Long HOME_NOW_PAY_DRIVER = 1000000009L;
//	private static Long HOME_COLLMONEY_ARR_PAY_DRIVER = 1000000011L;
//	private static Long ONESELF_COLLMONEY_ARR_PAY_DRIVER = 1000000010L;
	/**新增个体师傅发送短信：【联运汇平台】尊敬的${USER_NAME}用户，您的帐号为：${LOGIN_ACCT}，密码：${PASSWORD}！打开#<SMS_DOWNLOAD_URL>#或搜“联运到家”下载。需要修改密码可通过“个人中心-系统设置-重置密码”操作。详情请咨询热线#<SMS_TELPHONE>#。*/
	private static Long ADD_PLAT_SF = 1000000012L;
	/**预约自提：【联运汇平台】您好,您的运单号${TRACKING_NUM},货品${GOODS_NAME},已经到达${DEST_ORG},地址为:${ADDER},请及时过来提货;客服电话:${TELEPHONE}.*/
	private static Long SEARCH_SINCE = 1000000013L;
	/**预约送货上门：【联运汇平台】您好,您的运单号${TRACKING_NUM},货品${GOODS_NAME},已经到达${DEST_ORG},地址为:${ADDER},请及时过来提货;客服电话:${TELEPHONE}.*/
	private static Long SEARCH_NO_SINCE = 1000000014L;
	
	public static void sendVaildCode(String billId,String vaildCode) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("code", vaildCode);
		sysSmsParamTF.sendMesMap(billId, SYS_TEMP_VAILD, smsMap);
	}
	public static void sendBusinessVaildCode(long tenantId,String billId,String tenantCode,String loginAcct,String loginPwd) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("tenantCode", tenantCode);
		smsMap.put("loginAcct", loginAcct);
		smsMap.put("loginPwd", loginPwd);
		sysSmsParamTF.sendMesMap(billId, SYS_TEMP_BUS_APP, smsMap,tenantId);
	}
	public static void sendSFVaildCode(long tenantId,String billId,String tenantCode,String loginAcct,String loginPwd) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("tenantCode", tenantCode);
		smsMap.put("loginAcct", loginAcct);
		smsMap.put("loginPwd", loginPwd);
		sysSmsParamTF.sendMesMap(billId, SYS_TEMP_SF_APP, smsMap,tenantId);
	}
	
	public static void spHz(long tenantId,String billId,String orgName,String trackingNum) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("orgName", orgName);
		smsMap.put("trackingNum", trackingNum);
		sysSmsParamTF.sendMesMap(billId, SYS_SP_HZ, smsMap,tenantId);
	}
	
	public static void spSf(long tenantId,String billId,String orgName,String trackingNum) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("orgName", orgName);
		smsMap.put("trackingNum", trackingNum);
		sysSmsParamTF.sendMesMap(billId, SYS_SP_SF, smsMap,tenantId);
	}
	
	public static void spSj(long tenantId,String billId,String orgName,String batchNum,String beginOrgName,String endOrgName) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("orgName", orgName);
		smsMap.put("batchNum", batchNum);
		smsMap.put("beginOrgName", beginOrgName);
		smsMap.put("endOrgName", endOrgName);
		sysSmsParamTF.sendMesMap(billId, SYS_SP_SJ, smsMap,tenantId);
	}
	public static void yysh(long tenantId,String billId,String trackingNum,String userName,String telPhone,String time) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("trackingNum", trackingNum);
		smsMap.put("userName", userName);
		smsMap.put("billId", telPhone);
		smsMap.put("time", time);
		sysSmsParamTF.sendMesMap(billId, SYS_YYSH, smsMap,tenantId);
	}
	
	
	/**
	 * 预约通知自提短信
	 * @param tenantId
	 * @param billId
	 * @param trackingNum
	 * @param goodsName
	 * @param destOrg
	 * @param adder
	 * @param telephone
	 * @throws Exception
	 */
	public static void sendSearchSince(long tenantId,String billId,String trackingNum,String goodsName,String destOrg,String adder,String telephone) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("TRACKING_NUM", trackingNum);
		smsMap.put("GOODS_NAME", goodsName);
		smsMap.put("DEST_ORG", destOrg);
		smsMap.put("ADDER", adder);
		smsMap.put("TELEPHONE",telephone);
		sysSmsParamTF.sendMesMap(billId, SEARCH_SINCE, smsMap,tenantId);
	}
	
	/**
	 * 预约通知非自提短信
	 * @param tenantId
	 * @param billId
	 * @param trackingNum
	 * @param goodsName
	 * @param destOrg
	 * @param adder
	 * @param telephone
	 * @throws Exception
	 */
	public static void sendSearchNoSince(long tenantId,String billId,String trackingNum,String goodsName,String destOrg,String adder,String telephone) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("TRACKING_NUM", trackingNum);
		smsMap.put("GOODS_NAME", goodsName);
		smsMap.put("DEST_ORG", destOrg);
		smsMap.put("ADDER", adder);
		smsMap.put("TELEPHONE",telephone);
		sysSmsParamTF.sendMesMap(billId, SEARCH_NO_SINCE, smsMap,tenantId);
	}
	
	
	/**
	 *预约送货发送短信
	 * @param phone
	 * @throws Exception
	 */
//	public static void sendSay(SysSmsParmOut parmOut) throws Exception{
//		Map<String, Object> smsMap = new HashMap<String, Object>();
//		// 自提     现付、月结、回单付
//		String billId = parmOut.getConsigneeBill();
//		if(parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.ONESELF
//				&& parmOut.getPaymentType()!=SysStaticDataEnum.PAYMENT_TYPE.ARR_PAY 
//				&& parmOut.getPaymentType()!=SysStaticDataEnum.PAYMENT_TYPE.MANY_PAY){
//			sendOneselfNowPay(parmOut,billId);
//		}
//		// 自提    到付
//		if(parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.ONESELF 
//				&& parmOut.getPaymentType()==SysStaticDataEnum.PAYMENT_TYPE.ARR_PAY
//				&& parmOut.getCollMoney()==0){
//			sendOneselfArrPay(parmOut, billId);
//			//自提    到付  代收
//		}else if(parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.ONESELF 
//				&& parmOut.getPaymentType()==SysStaticDataEnum.PAYMENT_TYPE.ARR_PAY
//				&& parmOut.getCollMoney()>0){
//			  sendOneselfCollMoneyArrPay(parmOut,billId);
//			
//		}
//		// 自提   多笔付  (包含到付)
//		if(parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.ONESELF 
//				&& parmOut.getPaymentType()==SysStaticDataEnum.PAYMENT_TYPE.MANY_PAY 
//				&& parmOut.getFeeCoust()!=null &&parmOut.getFeeCoust()!=0 ){
//			sendOneselfArrPay(parmOut,billId);
//		}
//		// 自提   多笔付  (不包含到付)
//		if(parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.ONESELF 
//				&& parmOut.getPaymentType()==SysStaticDataEnum.PAYMENT_TYPE.MANY_PAY 
//				&& (parmOut.getFeeCoust()==null || parmOut.getFeeCoust()==0) ){
//			sendOneselfNowPay(parmOut,billId);
//		}
//		// 送货     现付、月结、回单付
//		if((parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.HOME_DELIVERY 
//				|| parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.FLOOR_DELIVERY)
//				&& parmOut.getPaymentType()!=SysStaticDataEnum.PAYMENT_TYPE.ARR_PAY 
//				&& parmOut.getPaymentType()!=SysStaticDataEnum.PAYMENT_TYPE.MANY_PAY){
//			sendHomeNowPay(parmOut,billId);
//		}
//		// 送货    到付
//		if((parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.HOME_DELIVERY 
//				|| parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.FLOOR_DELIVERY)
//                && parmOut.getPaymentType()==SysStaticDataEnum.PAYMENT_TYPE.ARR_PAY
//                && parmOut.getCollMoney()==0){
//			sendHomeArrPay(parmOut,billId);
//			//送货    到付 代收货款
//		}else if((parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.HOME_DELIVERY 
//				|| parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.FLOOR_DELIVERY)
//                && parmOut.getPaymentType()==SysStaticDataEnum.PAYMENT_TYPE.ARR_PAY
//                && parmOut.getCollMoney()>0){
//			sendHomeCollMoneyArrPay(parmOut,billId);
//			
//		}
//		// 送货  多笔付  (包含到付)
//		if((parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.HOME_DELIVERY 
//				|| parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.FLOOR_DELIVERY) 
//				&& parmOut.getPaymentType()==SysStaticDataEnum.PAYMENT_TYPE.MANY_PAY 
//				&& parmOut.getFeeCoust()!=null && parmOut.getFeeCoust()!=0 ){
//			sendHomeArrPay(parmOut,billId);
//		}
//		// 送货   多笔付  (不包含到付)
//		if((parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.HOME_DELIVERY 
//				|| parmOut.getDeliveryType()==SysStaticDataEnum.DELIVERY_TYPE.FLOOR_DELIVERY)
//				&& parmOut.getPaymentType()==SysStaticDataEnum.PAYMENT_TYPE.MANY_PAY 
//				&& (parmOut.getFeeCoust()==null || parmOut.getFeeCoust()==0) ){
//			sendHomeNowPay(parmOut,billId);
//		}
//	}
	
	//自提 现付
//		public static void sendOneselfNowPay(SysSmsParmOut parmOut,String billId) throws Exception{
//			Map<String,Object> paraMap = new HashMap<String,Object>();
//	  	    Organization organ =  OraganizationCacheDataUtil.getOrganization(parmOut.getOrgId());
//			paraMap.put("NUMBER", parmOut.getNumber());
//			paraMap.put("GOODSNAME", parmOut.getGoodsName());
//			paraMap.put("ORDERID", parmOut.getOrderId());
//			paraMap.put("ADDRESS", parmOut.getAddress());
//			if(organ!=null ){
//				      paraMap.put("TELPHONE", organ.getSupportStaffPhone());
//			}
//			BaseUser baseUser = SysContexts.getCurrentOperator();
//			SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
//			sysSmsParamTF.sendMesMap(billId, ONESELF_NOW_PAY, paraMap,baseUser.getTenantId());
//		}
//		
//		//自提 到付
//	    public static void sendOneselfArrPay(SysSmsParmOut parmOut,String billId) throws Exception{
//			Map<String,Object> paraMap = new HashMap<String,Object>();
//	  	    Organization organ =  OraganizationCacheDataUtil.getOrganization(parmOut.getOrgId());
//			paraMap.put("NUMBER", parmOut.getNumber());
//			paraMap.put("GOODSNAME", parmOut.getGoodsName());
//			paraMap.put("ORDERID", parmOut.getOrderId());
//			paraMap.put("FEECOUST", ((double)parmOut.getFeeCoust()/100));
//			paraMap.put("ADDRESS", parmOut.getAddress());
//			if(organ!=null ){
//				      paraMap.put("TELPHONE", organ.getSupportStaffPhone());
//			}
//			BaseUser baseUser = SysContexts.getCurrentOperator();
//			SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
//			sysSmsParamTF.sendMesMap(billId, ONESELF_ARR_PAY, paraMap,baseUser.getTenantId());
//		}
//	  //送货 现付
//	    public static void sendHomeNowPay(SysSmsParmOut parmOut,String billId) throws Exception{
//	    	Map<String,Object> paraMap = new HashMap<String,Object>();
//	  	    Organization organ =  OraganizationCacheDataUtil.getOrganization(parmOut.getOrgId());
//			paraMap.put("NUMBER", parmOut.getNumber());
//			paraMap.put("GOODSNAME", parmOut.getGoodsName());
//			paraMap.put("ORDERID", parmOut.getOrderId());
//			if(organ!=null ){
//				      paraMap.put("TELPHONE", organ.getSupportStaffPhone());
//			}
//			BaseUser baseUser = SysContexts.getCurrentOperator();
//			SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
//			sysSmsParamTF.sendMesMap(billId,HOME_NOW_PAY, paraMap,baseUser.getTenantId());
//		}
//	  //送货 到付
//	    public static void sendHomeArrPay(SysSmsParmOut parmOut,String billId) throws Exception{
//	    	Map<String,Object> paraMap = new HashMap<String,Object>();
//	  	    Organization organ =  OraganizationCacheDataUtil.getOrganization(parmOut.getOrgId());
//			paraMap.put("NUMBER", parmOut.getNumber());
//			paraMap.put("GOODSNAME", parmOut.getGoodsName());
//			paraMap.put("ORDERID", parmOut.getOrderId());
//			paraMap.put("FEECOUST", ((double)parmOut.getFeeCoust())/100);
//			if(organ!=null ){
//				      paraMap.put("TELPHONE", organ.getSupportStaffPhone());
//			}
//			BaseUser baseUser = SysContexts.getCurrentOperator();
//			SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
//			sysSmsParamTF.sendMesMap(billId,HOME_ARR_PAY, paraMap,baseUser.getTenantId());
//		}
//	    
	    
	    
	    
	    //自提  到付 代收
//	    public static void sendOneselfCollMoneyArrPay(SysSmsParmOut parmOut,String billId) throws Exception{
//			Map<String,Object> paraMap = new HashMap<String,Object>();
//	  	    Organization organ =  OraganizationCacheDataUtil.getOrganization(parmOut.getOrgId());
//			paraMap.put("NUMBER", parmOut.getNumber());
//			paraMap.put("GOODSNAME", parmOut.getGoodsName());
//			paraMap.put("ORDERID", parmOut.getOrderId());
//			paraMap.put("FEECOUST", ((double)parmOut.getFeeCoust())/100);
//			paraMap.put("COLLMONEY", ((double)parmOut.getCollMoney())/100);
//			paraMap.put("ADDRESS", parmOut.getAddress());
//			if(organ!=null ){
//				      paraMap.put("TELPHONE", organ.getSupportStaffPhone());
//			}
//			BaseUser baseUser = SysContexts.getCurrentOperator();
//			SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
//			sysSmsParamTF.sendMesMap(billId,ONESELF_COLLMONEY_ARR_PAY, paraMap,baseUser.getTenantId());
//		}
//	    
	    
	    
	    //送货 到付 代收
//	    public static void sendHomeCollMoneyArrPay(SysSmsParmOut parmOut,String billId) throws Exception{
//	    	Map<String,Object> paraMap = new HashMap<String,Object>();
//	  	    Organization organ =  OraganizationCacheDataUtil.getOrganization(parmOut.getOrgId());
//			paraMap.put("NUMBER", parmOut.getNumber());
//			paraMap.put("GOODSNAME", parmOut.getGoodsName());
//			paraMap.put("ORDERID", parmOut.getOrderId());
//			paraMap.put("FEECOUST", ((double)parmOut.getFeeCoust())/100);
//			paraMap.put("COLLMONEY",((double) parmOut.getCollMoney())/100);
//			if(organ!=null ){
//				      paraMap.put("TELPHONE", organ.getSupportStaffPhone());
//			}
//			BaseUser baseUser = SysContexts.getCurrentOperator();
//			SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
//			sysSmsParamTF.sendMesMap(billId,HOME_COLLMONEY_ARR_PAY, paraMap,baseUser.getTenantId());
//		}
//	    
//	    
//
//	    //送货 到付 代收(送货上门)
//	    public static void sendHomeCollMoneyArrPayDelivery(SysSmsParmOut parmOut,Map<String, Object> smsMap,Map<String, Object> paraMap) throws Exception{
//	  	    Organization organ =  OraganizationCacheDataUtil.getOrganization(parmOut.getOrgId());
//			paraMap.put("NUMBER", parmOut.getNumber());
//			paraMap.put("GOODSNAME", parmOut.getGoodsName());
//			paraMap.put("ORDERID", parmOut.getOrderId());
//			paraMap.put("FEECOUST", ((double)parmOut.getFeeCoust())/100);
//			paraMap.put("COLLMONEY",((double) parmOut.getCollMoney())/100);
//			if(organ!=null ){
//				      paraMap.put("TELPHONE", organ.getSupportStaffPhone());
//			}
//			smsMap.put("paraMap", paraMap);
//			BaseUser baseUser = SysContexts.getCurrentOperator();
//			SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
//			String billId = (String) smsMap.get("billId");
//			sysSmsParamTF.sendMesMap(billId,HOME_COLLMONEY_ARR_PAY_DRIVER, paraMap,baseUser.getTenantId());			
//		}
	    
	    
	    
	    //送货 到付(送货上门)
//		public static void sendHomeArrPayDelivery(SysSmsParmOut parmOut,Map<String, Object> smsMap,Map<String, Object> paraMap) throws Exception{
//	  	    Organization organ =  OraganizationCacheDataUtil.getOrganization(parmOut.getOrgId());
//			paraMap.put("NUMBER", parmOut.getNumber());
//			paraMap.put("GOODSNAME", parmOut.getGoodsName());
//			paraMap.put("ORDERID", parmOut.getOrderId());
//			paraMap.put("FEECOUST", ((double)parmOut.getFeeCoust())/100);
//			if(organ!=null ){
//				      paraMap.put("TELPHONE", organ.getSupportStaffPhone());
//			}
//			BaseUser baseUser = SysContexts.getCurrentOperator();
//			SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
//			String billId = (String) smsMap.get("billId");
//			sysSmsParamTF.sendMesMap(billId,ONESELF_COLLMONEY_ARR_PAY_DRIVER, paraMap,baseUser.getTenantId());		
//		}
	    
	    //送货 现付(送货上门)
//	    public static void sendHomeNowPayDelivery(SysSmsParmOut parmOut,Map<String, Object> smsMap,Map<String, Object> paraMap) throws Exception{
//	  	    Organization organ =  OraganizationCacheDataUtil.getOrganization(parmOut.getOrgId());
//			paraMap.put("NUMBER", parmOut.getNumber());
//			paraMap.put("GOODSNAME", parmOut.getGoodsName());
//			paraMap.put("ORDERID", parmOut.getOrderId());
//			if(organ!=null ){
//				      paraMap.put("TELPHONE", organ.getSupportStaffPhone());
//			}
//			BaseUser baseUser = SysContexts.getCurrentOperator();
//			SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
//			String billId = (String) smsMap.get("billId");
//			sysSmsParamTF.sendMesMap(billId,HOME_NOW_PAY_DRIVER, paraMap,baseUser.getTenantId());		
//		}
	    
	    
	    //新增师傅，发送短信
	    public static void addPlatSf(Map<String, Object> smsMap) throws Exception{
	    	Map<String, Object> paraMap=new HashMap<String, Object>();
			paraMap.put("PASSWORD", smsMap.get("PASSWORD"));
			paraMap.put("LOGIN_ACCT", smsMap.get("LOGIN_ACCT"));
			paraMap.put("USER_NAME", smsMap.get("USER_NAME"));
			SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
			String billId = (String) smsMap.get("billId");
			sysSmsParamTF.sendMesMap(billId,ADD_PLAT_SF, paraMap,Long.parseLong(smsMap.get("tenantId")+""));		
		}
	    
	    /**
		 * 送货上门发送通知短信
		 * @param inParam
		 * @return
		 * @throws Exception
		 */
//		@SuppressWarnings({ "unchecked", "rawtypes" })
//		public static String doSendMessageDelivery(List<Long> ordIds,String plateNumber,String driverPhone) throws Exception{
//			Session session  = SysContexts.getEntityManager();
//			if(ordIds != null && ordIds.size() <= 0){
//				throw new BusinessException("请传入订单编号");	
//			}
//			List<Long> ordIdList = new ArrayList<Long>();
//			for(int i=0;i<ordIds.size();i++){
//			Long  orderId=Long.valueOf(ordIds.get(i)+"");
//			ordIdList.add(orderId);
//			}
//			SysSmsParmOut iparmOut = new SysSmsParmOut();
//			Criteria ca  = session.createCriteria(OrdOrderInfo.class);
//			ca.add(Restrictions.in("orderId", ordIdList));
//			List<OrdOrderInfo> ordList = new ArrayList<OrdOrderInfo>();
//			Map<String, Object> iparmMap = new HashMap();
//			if(ca.list().size()>0){
//				ordList=ca.list();
//				for(OrdOrderInfo order:ordList){
//					iparmOut = new SysSmsParmOut();
//					Criteria goodCa =  session.createCriteria(OrdGoodsDetail.class);
//					goodCa.add(Restrictions.eq("orderId", order.getOrderId()));
//					List<OrdGoodsDetail> list = new ArrayList<OrdGoodsDetail>();
//					list=goodCa.list();
//					String name="";
//					int goodCount=0;
//					
//					if(list.size()>0){
//						for(int i=0;i<list.size();i++){
//							OrdGoodsDetail detail = list.get(i);
//							goodCount+=detail.getGoodsCount();
//							if(i==list.size()-1){
//								name+=detail.getGoodsName();
//							}else{
//								name+=detail.getGoodsName()+",";
//							}
//						}
//						iparmOut.setGoodsName(name);
//						iparmOut.setNumber(goodCount);
//					}
//					
//					OrdFee ordFee = (OrdFee)session.get(OrdFee.class, order.getOrderId());
//					iparmOut.setPaymentType(ordFee.getPaymentType());
//					iparmOut.setFeeCoust(ordFee.getFreightCollect());
//					iparmOut.setConsigneeBill(order.getConsigneeBill());
//					iparmOut.setOrgId(order.getCurrentOrgId());
//					iparmOut.setOrderId(order.getTrackingNum());
//					iparmOut.setAddress(order.getAddress());
//					iparmOut.setDeliveryType(order.getDeliveryType());
//					if(ordFee.getCollectingMoney()!=null && ordFee.getCollectingMoney()>0){
//						iparmOut.setCollMoney(ordFee.getCollectingMoney());
//					}else{
//						iparmOut.setCollMoney(0L);
//					}
//					if(StringUtils.isNotEmpty(order.getConsigneeBill())){
//						iparmMap.put(order.getConsigneeBill(), iparmOut);
//					}
//				}	
//			}
//			SysSmsParmOut parmOut= new SysSmsParmOut();
//			for(Map.Entry<String, Object> entry:iparmMap.entrySet()){ 
//				parmOut= (SysSmsParmOut)entry.getValue();
//				sendDeliveryGoods(parmOut,plateNumber,driverPhone);
//		  } 
//			return "Y";
//		}
	    
	    /**
		 * 送货上门发送短信
		 * @param phone
		 * @throws Exception
		 */
//		public static void sendDeliveryGoods(SysSmsParmOut parmOut,String plateNumer,String driverPhone) throws Exception{
//			    Map<String, Object> smsMap = new HashMap<String, Object>();
//			    Map<String, Object> paramMap = new HashMap<String, Object>();
//				if(StringUtils.isEmpty(parmOut.getConsigneeBill())){
//					log.error("收货联系手机号码为空！！！！！！！！！！！！！！！！！！！！！");
//					return;
//				}
//			     // 自提     现付、月结、回单付
//				smsMap.put("billId", parmOut.getConsigneeBill());
////				smsMap.put("sms_type", EnumConsts.SmsType.NOTICE_TYPE);
////				smsMap.put("OBJ_TYPE", SysStaticDataEnum.OBJ_TYPE.ORDER);
//				
//				paramMap.put("plateNumber", plateNumer);
//				paramMap.put("driverPhone", driverPhone);
//				
//				// 送货  现付、到付、多笔付
//				if(parmOut.getPaymentType()!=SysStaticDataEnum.PAYMENT_TYPE.MON_PAY  && parmOut.getPaymentType()!=SysStaticDataEnum.PAYMENT_TYPE.REC_PAY){
//					if(parmOut.getPaymentType() == SysStaticDataEnum.PAYMENT_TYPE.NOW_PAY){
//						sendHomeNowPayDelivery(parmOut,smsMap,paramMap); //09
//					}else{
//						if(parmOut.getFeeCoust() > 0 && parmOut.getCollMoney() > 0){
//							sendHomeCollMoneyArrPayDelivery(parmOut,smsMap,paramMap);  //11
//						}else if(parmOut.getFeeCoust() > 0){
//							sendHomeArrPayDelivery(parmOut,smsMap,paramMap); //10
//						}else if(parmOut.getCollMoney() > 0){
//							sendHomeCollMoneyArrPayDelivery(parmOut,smsMap,paramMap);  //11
//						}else{
//							sendHomeNowPayDelivery(parmOut,smsMap,paramMap); //09
//						}
//			
//					}
//				}
//				
//
//		
//		}
		
}
