package com.wo56.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.core.SysContexts;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.sms.intf.SysSmsTF;

public class SmsYQUtil {
	
	private static transient Log log = LogFactory.getLog(SmsYQUtil.class);
	
	
	/****
	 * 当专线/拉包公司在后台注册拉包工成功时，发送短信给拉包工
	 * @param tenantId
	 * @param loginAcct
	 * @param password
	 * @param companyName
	 * @throws Exception
	 */
	public static void sendRegisterWoker(long tenantId,String loginAcct,String password,String companyName) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("loginAcct", loginAcct);
		smsMap.put("password", password);
		smsMap.put("companyName", companyName);
		smsMap.put("objId", -1L);
		smsMap.put("objType", EnumConstsYQ.OBJ_TYPE.OBJ_TYPE1);
		smsMap.put("smsType", EnumConstsYQ.SmsType.NOTICE_TYPE);
		sysSmsParamTF.sendMesMap(loginAcct, EnumConstsYQ.SmsTemplate.REGISTER_TEMPLATE_ID, smsMap,tenantId);
	}
	

	
	
	/***
	 * 拉包工：亲，您有订单[订单号]需拉包，提货地址为：xxxxx，请尽快提货
	 * @param tenantId
	 * @param orderNo
	 * @param pickAddress
	 * @param objType
	 * @param smsType
	 * @param objId
	 * @param bill
	 * @throws Exception
	 */
	public static void sendPullWorker(long tenantId,String orderNo,String pickAddress,int objType,int smsType,long objId,String bill) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("orderNo", orderNo);
		smsMap.put("orderId", objId);
		smsMap.put("pickAddress", pickAddress);
		smsMap.put("objId", objId);
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.RECEIVER_ORDER_PUSH_WOKKER_TEMPLATE_ID, smsMap,tenantId);	
	}
	
	/***  
	 * 拉包工：亲，您有订单[订单号]需拉包，首单提货地址为：XXXXX，更多提货地址，请查看订单详情
	 * @param tenantId
	 * @param orderNo
	 * @param pickAddress
	 * @param objType
	 * @param smsType
	 * @param objId
	 * @param bill
	 * @throws Exception
	 */
	public static void sendPullWorkerMul(long tenantId,String orderNo,String pickAddress,int objType,int smsType,long objId,String bill) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("orderNo", orderNo);
		smsMap.put("orderId", objId);
		smsMap.put("pickAddress", pickAddress);
		smsMap.put("objId", objId);
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.RECEIVER_ORDER_PUSH_WOKKER_MUl_TEMPLATE_ID, smsMap,tenantId);
		
	}
	
	
	/**
	 *  * 商家：您好，[收货人]有单推送给您，订单号为[订单号]，请打开app查看详情，进行备货，
	 * @param tenantId
	 * @param orderNo
	 * @param consigneeName
	 * @param objType
	 * @param smsType
	 * @param objId
	 * @param bill
	 * @throws Exception
	 */
	public static void sendOrderBusi(long tenantId,String orderNo,String name,int objType,int smsType,long objId,String bill) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("orderNo", orderNo);
		smsMap.put("consigneeName", name);
		smsMap.put("objId", objId);
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.RECEIVER_ORDER_PUSH_BUSS_TEMPLATE_ID, smsMap,tenantId);
	}
	

	/***
	 *  商家：商家：您好，[收货人]有单推送给您，订单号为[订单号]，请打开app查看详情，下载地址为xxxxxx。
	 * @param tenantId
	 * @param orderNo
	 * @param consigneeName
	 * @param objId
	 * @param bill
	 * @throws Exception
	 */
	public static void sendSmsOrderBusi(long tenantId,String orderNo,String name,long objId,String bill) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("orderNo", orderNo);
		smsMap.put("consigneeName", name);
		smsMap.put("objId", objId);
		smsMap.put("objType",EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2);
		smsMap.put("smsType", EnumConstsYQ.SmsType.NOTICE_TYPE);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.RECEIVER_ORDER_SMS_BUSS_TEMPLATE_ID, smsMap,tenantId);
	}
	
	/***
	 * 收货人：您好，[发货人]已下单，订单号为[订单号]，正等待拉包，请关注
	 * @param tenantId
	 * @param orderNo
	 * @param name
	 * @param objType
	 * @param smsType
	 * @param objId
	 * @param bill
	 * @throws Exception
	 */
	public static void sendSmsOrderReceiver(long tenantId,String orderNo,String name,int objType,int smsType,long objId,String bill) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("orderNo", orderNo);
		smsMap.put("name", name);
		smsMap.put("objId", objId);
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.BUSI_ORDER_SMS_RECEIVER_TEMPLATE_ID, smsMap,tenantId);
	}

	
	/***
	 * 拉包工：您好，订单号：【订单号】已备好货，提货地址为：xxxxx，请尽快提货，
	 * @param tenantId
	 * @param orderNo
	 * @param pickAddress
	 * @param objType
	 * @param smsType
	 * @param objId
	 * @param bill
	 * @throws Exception
	 */
	public static void sendGoodsToWorker(long tenantId,String orderNo,String pickAddress,int objType,int smsType,long objId,String bill) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("orderNo", orderNo);
		smsMap.put("pickAddress", pickAddress);
		smsMap.put("objId", objId);
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.SEND_GOODS_TO_WORKER, smsMap,tenantId);
	}
	
	
	
	/****
	 *  您好：订单号为（*****）商家已经备货，正在等待拉包。
	 * @param tenantId
	 * @param orderNo
	 * @param objType
	 * @param smsType
	 * @param objId
	 * @param bill
	 * @throws Exception
	 */
	public static void sendGoodsToConSignee(long tenantId,String orderNo,int objType,int smsType,long objId,String bill) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", objId);
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.SEND_GOODS_TO_CONSIGNEE, smsMap,tenantId);
	}
	/**
	 * 您好，[物流公司]物流公司已收货，运单号为[运单号]，请关注!
	 * @param tenantId
	 * @param orderNo
	 * @param objType
	 * @param smsType
	 * @param objId
	 * @param companyName
	 * @param bill
	 * @throws Exception
	 */
	public static void sendBillingDelivery(long tenantId,String trackingNum,int objType,int smsType,long objId,String companyName,String bill) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("trackingNum", trackingNum);
		smsMap.put("objId", objId);
		smsMap.put("companyName", companyName);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.SEND_BILLING_DELIVERY, smsMap,tenantId);
	}
	/**
	 * 您好，您的订单（*****）已经提货，正拉往物流公司。
	 * @param tenantId
	 * @param orderNo
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exception
	 */
	public static void sendPickConsignee(long tenantId,String orderNo,int objType,long objId,int smsType,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", objId);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.SEND_PUSH_SMS, smsMap,tenantId);
	}
	/**
	 * 您好：收到**物流公司，订单号为(*****)费用30元，可以在钱包中查看。
	 * @param tenantId
	 * @param companyName
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exception
	 */
	public static void tipMoneySendSmsCarrier(long tenantId,long objId,String companyName,String money,int objType,int smsType,String bill,String orderNo)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("companyName", companyName);
		smsMap.put("objId", objId);
		smsMap.put("money", money);
		smsMap.put("orderNo", orderNo);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.TIP_MONEY_SEND_SMS_CARRIER, smsMap,tenantId);
	}
	/**
	 * 您发送的订单已到达目的地，正在安排配送。
	 * @param tenantId
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @throws Exception
	 */
	public static void arriveSendConsignor(long tenantId,long objId,int objType,int smsType,String bill,String orderNo)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("objId", objId);
		smsMap.put("orderNo", orderNo);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.ARRIVE_SEND_CONSIGNOR, smsMap,tenantId);
	
	}	
	/**
	 * 您的订单(****）已经到达***站，正在安排配送，配送网点客服电话11012011911
	 * @param tenantId
	 * @param orderNo
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exception
	 */
	public static void arriveSendConsignee(long tenantId,String orderNo,long objId,String customerServicePhone,String orgName,int objType,int smsType,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("customerServicePhone",customerServicePhone);
		smsMap.put("orgName", orgName);
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", objId);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.ARRIVE_SEND_CONSIGNEE, smsMap,tenantId);
	}
	/**
	 * 您的订单（*******）已到配送站点，详情请联系配送员：张三，电话12345678999，请留意收件。
	 * @param tenantId
	 * @param orderNo
	 * @param distribution
	 * @param phone
	 * @param bill
	 * @param objType
	 * @param smsType
	 * @throws Exception
	 */
	public static void distributionSendConsignee(long tenantId,long objId,String orderNo,String distribution,String phone,String bill,int objType,int smsType) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", objId);
		smsMap.put("distribution", distribution);
		smsMap.put("phone", phone);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.DISTRIBUTION_SEND_CONSIGNEE, smsMap,tenantId);
	}
	/**
	 * 您发送的订单已签收，签收人：某某某，期待再次为你服务。
	 * @param tenantId
	 * @param signer
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exception
	 */
	public static void signSendConsignor(long tenantId,long objId,String signer,int objType,int smsType,String bill,String orderNo)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("signer", signer);
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", objId);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.SIGN_SEND_CONSIGNOR, smsMap,tenantId);
	}
	/**
	 * 商家已催单，请尽快提货了，如果30分钟拉包工还不提货，系统更换拉包工。
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @throws Exception
	 */
	public static void reminderBusinessSendCarrier(long tenantId,int objType,int smsType,String bill) throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.Reminder_BUSINESS_SEND_CARRIER, smsMap,tenantId);
	}
	/**
	 * 您好，【订单号】商家已经备好货多时了，尽快提货哦！
	 * @param tenantId
	 * @param orderNo
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exception
	 */
	public static void reminderSendCarrier(long tenantId,String orderNo,long objId, int objType,int smsType,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", objId);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.Reminder_SEND_CARRIER, smsMap,tenantId);

	}
	/**
	 * 您好，（收货方）订单号****修改了订单，快去查看订单详情哦！
	 * @param tenantId
	 * @param orderNo
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exception
	 */
	public static void updateOrderSendConsignor(long tenantId,long objId,String orderNo,int objType,int smsType,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("objId", objId);
		smsMap.put("orderNo", orderNo);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.UPDATE_ORDER_SEND_CONSIGNOR, smsMap,tenantId);
	}
	/**
	 * 提货地址发生变化，请查看订单提货详情
	 * @param tenantId
	 * @param orderNo
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @throws Exception
	 */
	public static void updateOrderSendCarrier(long tenantId,String orderNo,int objType,int smsType,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("orderNo", orderNo);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.UPDATE_ORDER_SEND_CARRIER, smsMap,tenantId);
	}
	/**
	 * 您好，您的订单号****修改了订单，快去查看订单详情哦！
	 * @param tenantId
	 * @param orderNo
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exception
	 */
	public static void updateOrderSendConsignee(long tenantId,long objId,String orderNo,int objType,int smsType,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", objId);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.UPDATE_ORDER_SEND_CONSIGNEE, smsMap,tenantId);
	}
	/**
	 * 您好：你的订单（*****）已经取消，如有疑问，请拨打商家客服电话88888888.
	 * @param tenantId
	 * @param orderNo
	 * @param customerServicePhone
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exception
	 */
	public static void cancelOrderSendConsignee(long tenantId,long objId,String orderNo,String customerServicePhone,int objType,int smsType,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", objId);
		smsMap.put("customerServicePhone", customerServicePhone);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.CANCEL_ORDER_SEND_CONSIGNEE, smsMap,tenantId);
	}
	/**
	 * 您好：你的提货订单（*****）已取消，查看其它订单吧！
	 * @param tenantId
	 * @param orderNo
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exception
	 */
	public static void cancelOrderSendCarrier(long tenantId,long objId,String orderNo,int objType,int smsType,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", objId);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.CANCEL_ORDER_SEND_CARRIER, smsMap,tenantId);

	}
	/**
	 * 您好：你的开单订单（*****）客户已发起取消，取消原因：xxxxxxx，请确认！
	 * @param tenantId
	 * @param orderNo
	 * @param cancelReason
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exeption
	 */
	public static void cancelOrderSendMerchandiser(long tenantId,long objId,String orderNo,String cancelReason,int objType,int smsType,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", objId);
		smsMap.put("cancelReason", cancelReason);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.CANCEL_ORDER_SEND_MERCHANDISER, smsMap,tenantId);

	}
	/**
	 * 您好：你的订单（*****）已经取消，如有疑问，请拨打（收货方）电话88888888.
	 * @param tenantId
	 * @param orderNo
	 * @param consignee
	 * @param phone
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exception
	 */
	public static void cancelOrderSendConsignor(long tenantId,long objId,String consignee,String phone,String orderNo,int objType,int smsType,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("orderNo", orderNo);
		smsMap.put("consignee", consignee);
		smsMap.put("phone", phone);
		smsMap.put("objId", objId);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.CANCEL_ORDER_SEND_CONSIGNOR, smsMap,tenantId);
	}
	
	
	/**
	 * 您好：你的订单（*****）已经取消，如有疑问，请拨打（收货方）电话88888888.
	 * @param tenantId
	 * @param orderNo
	 * @param consignee
	 * @param phone
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exception
	 */
	public static void cancelOrderSendConsignorPull(long tenantId,long objId,String orderNo,int objType,int smsType,String bill,String pullName)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("orderId", orderNo);
		smsMap.put("name", pullName);
		smsMap.put("objId", objId);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.CANCEL_CONSIGNOR, smsMap,tenantId);
	}
	
	
	/**
	 * 您好：你的订单（*****）已经取消，如有疑问，请拨打拉包工电话88888888.
	 * @param tenantId
	 * @param orderNo
	 * @param carrierBill
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exception
	 */
	public static void cancelOrderCarrierSendConsignee(long tenantId,long objId,String orderNo,String carrierBill,int objType,int smsType,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("orderNo", orderNo);
		smsMap.put("carrierBill", carrierBill);
		smsMap.put("objId", objId);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.CANCEL_ORDER_CARRIER_SEND_CONSIGNEE, smsMap,tenantId);
	}
	/**
	 * 您没及时提货，现将订单（*****）重新分配给其他人进行拉包!
	 * @param tenantId
	 * @param orderNo
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @param objId
	 * @throws Exception
	 */
	public static void backstageSendCarrier(long tenantId,long objId,String orderNo,int objType,int smsType,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", objId);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.BACKSTAGE_SEND_CARRIER, smsMap,tenantId);
	}
	/**
	 * 您好！系统重新分配拉包工：张三，电话13800138000为您服务！
	 * @param tenantId
	 * @param carrier
	 * @param phone
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @throws Exception
	 */
	public static void backstageSendConsignor(long tenantId,String carrier,String phone,int objType,int smsType,String bill,long orderId,String orderNo)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",objType);
		smsMap.put("smsType", smsType);
		smsMap.put("phone", phone);
		smsMap.put("objId", orderId);
		smsMap.put("orderNo", orderNo);
		smsMap.put("carrier", carrier);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.BACKSTAGE_SEND_CONSIGNOR, smsMap,tenantId);
	}
	/**
	 * 【云货宝】云货宝验证码：123456，打死不能告诉别人哦！
	 * @param tenantId
	 * @param code
	 * @param bill
	 * @throws Exception
	 */
	public static void registerSendVerificationCode(long tenantId,String code,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType", EnumConstsYQ.OBJ_TYPE.OBJ_TYPE1);
		smsMap.put("smsType", EnumConstsYQ.SmsType.NOTICE_TYPE);
		smsMap.put("code", code);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.REGISTER_SEND_VERIFICATION_CODE, smsMap,tenantId);
	}
	/**
	 * 【云货宝】尊敬的用户，您于${date}向账号为${card}发起${money}元提现已成功，请关注您的到账提醒，具体到账时间已银行为准。
	 * @param tenantId
	 * @param date
	 * 				格式：2017年2月20日
	 * @param card
	 * 				格式：6212**********1124
	 * @param money
	 * 				单位：元
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @throws Exception
	 */
	public static void extractMoneySendSms(long tenantId,String date,String card,String money,int objType,int smsType,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType", objType);
		smsMap.put("smsType", smsType);
		smsMap.put("date", date);
		smsMap.put("card", card);
		smsMap.put("money", money);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.EXTRACT_MONEY_SEND_SMS, smsMap,tenantId);
	}
	
	/***
	 * 【云货宝】尊敬的用户，您于${date}向账号为${card}发起${money}元提现审核不通过，原因{****}，请重新申请提现。
	 */
	public static void notExtractMoneySendSms(long tenantId,String date,String card,String money,int objType,int smsType,String bill,String auditRemark)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType", objType);
		smsMap.put("smsType", smsType);
		smsMap.put("date", date);
		smsMap.put("card", card);
		smsMap.put("money", money);
		smsMap.put("auditRemark", auditRemark);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.NOT_EXTRACT_MONEY_SEND_SMS, smsMap,tenantId);
	}
	
	/****
	 * 订单号为[${orderNo}]的订单，系统已分配拉包工：${name}，电话${phone}为您服务！
	 * @param tenantId
	 * @param orderId
	 * @param orderNo
	 * @param name
	 * @param phone
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @throws Exception
	 */
	public static void disWorkerToConsigor(long tenantId,long orderId,String orderNo,String name,String phone,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType", EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2);
		smsMap.put("smsType", EnumConstsYQ.SmsType.NOTICE_TYPE);
		smsMap.put("name", name);
		smsMap.put("phone", phone);
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", orderId);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.DISTRIBUTION_CONTRACT, smsMap,tenantId);
	}
	
	/****
	 * 订单号为[${orderNo}]的订单，系统已分配拉包工：${name}，电话${phone}为您服务！
	 * @param tenantId
	 * @param orderId
	 * @param orderNo
	 * @param name
	 * @param phone
	 * @param objType
	 * @param smsType
	 * @param bill
	 * @throws Exception
	 */
	public static void disWorkerToConsigorWeCaht(long tenantId,long orderId,String orderNo,String name,String phone,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType", EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4);
		smsMap.put("smsType", EnumConstsYQ.SmsType.WECHAT_TYPE);
		smsMap.put("name", name);
		smsMap.put("phone", phone);
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", orderId);
		sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.DISTRIBUTION_CONTRACT, smsMap,tenantId);
	}
	
	/**
	 * 抢单
	 * 
	 * 亲，抢单啦！订单号（*****），提货地址为：xxxxx
	 * @param orderNo
	 * @param pickAddr
	 * @param pickTime
	 * @param handlerTime
	 * @param objType
	 * @param objId
	 * @param smsType
	 * @param bill
	 * @throws Exception
	 */
	public static void grabOrder(String orderNo,String pickAddr,String createTime,String handlerTime, long objId,String bill)throws Exception{
		SysSmsTF sysSmsParamTF = (SysSmsTF)SysContexts.getBean("sysSmsTF");
		Map<String, Object> smsMap=new HashMap<String, Object>();
		smsMap.put("objType",EnumConstsYQ.OBJ_TYPE.OBJ_TYPE3);
		smsMap.put("smsType", EnumConstsYQ.SmsType.MOPBILE_TYPE);
		smsMap.put("orderNo", orderNo);
		smsMap.put("objId", objId);
		smsMap.put("pickAddr", pickAddr);
		smsMap.put("createTime", createTime);
		smsMap.put("handlerTime", handlerTime);
		try {
			sysSmsParamTF.sendMesMap(bill, EnumConstsYQ.SmsTemplate.GRAB_ORDER_SEND_SMS, smsMap,-1);
		} catch (Exception e) {
			log.error("抢单写入短信表失败", e);
		}finally{
			SysContexts.commitTransation();
		}
		
	}
}
