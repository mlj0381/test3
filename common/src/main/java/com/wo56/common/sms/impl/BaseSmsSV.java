package com.wo56.common.sms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.StringUtils;

import com.esms.MessageData;
import com.esms.PostMsg;
import com.esms.common.entity.Account;
import com.esms.common.entity.GsmsResponse;
import com.esms.common.entity.MTPack;
import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.util.DateUtil;
import com.wo56.common.sms.utils.EnumConsts;
import com.wo56.common.sms.utils.SysSmsPlatformInfoCacheUtil;
import com.wo56.common.sms.vo.SysSmsPlatformInfo;


/**
 * 发送短信，用户发送时业务逻辑控制
 * @author zhouchao
 *
 */
public class BaseSmsSV {
	
	private static final Log log = LogFactory.getLog(BaseSmsSV.class);
	
	public BaseSmsSV(){
	}
	/*/
	 * expId 扩展号 ？ 
	 * platformChoice 平台选择：1：默认平台 2：E讯通 3、创蓝
	 */
	public  void sendMessage(String phoneNum,String sms,String expId,Integer platformChoice,Long tenantId)throws Exception{
		log.info("发送短信：手机["+phoneNum+"],sms["+sms+"]");
		if(platformChoice != null){
			List<SysSmsPlatformInfo> sysSmsPlatformInfo = SysSmsPlatformInfoCacheUtil.getPlatformInfoList(platformChoice); 
			Map<String,String> map = getEKey(sysSmsPlatformInfo);//组装平台信息
			
			if(EnumConsts.SysSmsPlatformValue.SMS_DEFAULT == platformChoice){
				log.info("发送短信：手机["+phoneNum+"],sms["+sms+"],使用[默认平台]");
				sendMsg(phoneNum, sms, expId,map);
			}else if(EnumConsts.SysSmsPlatformValue.SMS_EXT == platformChoice){
				log.info("发送短信：手机["+phoneNum+"],sms["+sms+"],使用[E讯通]");
				sendMsgByE(phoneNum, sms, expId,map);
			}else if(EnumConsts.SysSmsPlatformValue.SMS_CL == platformChoice){
				log.info("发送短信：手机["+phoneNum+"],sms["+sms+"],使用[创蓝]");
				sendMsgByCL(phoneNum, sms, expId,map);
			}else if(EnumConsts.SysSmsPlatformValue.SWITCH_XUANWU == platformChoice){
				log.info("发送短信：手机["+phoneNum+"],sms["+sms+"],使用[玄武]");
				sendMsgByWx(phoneNum, sms, expId,map);
			}
			else {
				log.error("发送短信失败：table sys_sms_platform  platformChoice= "+platformChoice+" is not equal 1默认平台 or 2E讯通 or 3 创蓝");
			}
		}else{
			log.error(" sys_sms_platform 表没有设置指定短信平台发送 ：tenantId为"+tenantId);
		}
		
	
	}
	public static void main(String[] args) throws Exception {
		Map<String, String> valueMap = new HashMap<String, String>();		
		BaseSmsSV baseSms = (BaseSmsSV) SysContexts.getBean("baseSmsSV");
		baseSms.sendMsgByWx("18620166271", "您好，测试短信，收到qq上联系我","0",valueMap);
		
	
	}
	/**
	 * 用 E讯通平台发送短信
	 * @param phoneNum
	 * @param sms
	 * @param expId
	 * @throws Exception
	 */
	public  void sendMsgByE(String phoneNum,String sms,String expId,Map<String,String> valueMap)throws Exception{
		if(valueMap == null){
			log.error("平台发送：找不到E讯通账户配置信息");
			throw new BusinessException("平台发送：找不到E讯通账户配置信息");
		}
		String url = valueMap.get("url");
		String uid = valueMap.get("uid");
		String password = valueMap.get("pwd");
		
		if(expId == null || "".equals(expId)){
			expId = "2700";
		}else{
			if(expId.length()==1){
				expId="270"+expId;
			}else if(expId.length()==2){
				expId="27"+expId;
			}else{
				expId="27"+expId.substring(expId.length()-2);
			}
		}
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
        //发送即时消息
//		NameValuePair[] data = {
//				new NameValuePair("userid",uid),
//				new NameValuePair("password", password),
//				new NameValuePair("destnumbers",phoneNum),
//				new NameValuePair("msg", sms+"[握物流]"),
//				new NameValuePair("spnumber",expId)//字端口号
//		};
		postMethod.setParameter("userid", uid);
		postMethod.setParameter("password", password);
		postMethod.setParameter("spnumber", expId);
		postMethod.setParameter("destnumbers", phoneNum);
		postMethod.setParameter("msg", sms+"[握物流]");
		
		postMethod.getParams().setContentCharset("UTF-8");
		int statusCode = httpClient.executeMethod(postMethod);
		String resp = postMethod.getResponseBodyAsString();
		log.info("E讯通发送短信："+resp);
		if (statusCode == HttpStatus.SC_OK) {
			String respStr = postMethod.getResponseBodyAsString();
			Map<String,String> map= xml2map(respStr);
			String returnAttr=map.get("return");
			if(!StringUtils.isEmpty(returnAttr) && "0".equals(returnAttr)){
				return;
			}
			
			if(!StringUtils.isEmpty(returnAttr) && !"0".equals(returnAttr)){
				String infoAttr=map.get("info");
				throw new BusinessException("sendMsgByE is send error:billId["+phoneNum+"],return["+returnAttr+"],info["+infoAttr+"]");
			}else{
				throw new BusinessException("sendMsgByE is response parse error,xml["+respStr+"]");
			}
		}else{
			log.error("E讯通发送失败：url="+url+",uid"+uid+",password="+password);
			throw new BusinessException("sendMsgByE is post error ,statusCode["+statusCode+"]");
		}
	}
	
	
	/**
	 * 
	 * @param phoneNum
	 * @param sms
	 * @param expId
	 * @throws Exception
	 */
	private void sendMsg(String phoneNum,String sms,String expId,Map<String,String> valueMap)throws Exception{
		if(valueMap == null){
			log.error("平台发送：找不到默认平台账户配置信息");
			throw new BusinessException("找不到默认平台账户配置信息");
		}
		String url = valueMap.get("url");
		String uid = valueMap.get("uid");
		String auth = valueMap.get("pwd");
		
		if(expId == null || "".equals(expId)){
			expId = "0";
		}
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
        //发送即时消息
		NameValuePair[] data = {
				new NameValuePair("uid",uid),
				new NameValuePair("auth", auth),
				new NameValuePair("mobile",phoneNum),
				new NameValuePair("msg", java.net.URLEncoder.encode(sms,"GBK")),
				new NameValuePair("expid",expId)
				};
		postMethod.setRequestBody(data);
		int statusCode = httpClient.executeMethod(postMethod);
		String resp = postMethod.getResponseBodyAsString();
		log.info("默认平台发送短信："+resp);
		if (statusCode != HttpStatus.SC_OK) {
			StringBuffer expText=new StringBuffer("send sms error ");
			expText.append(" statusCode[").append(statusCode).append("]")
			.append(" uid[").append(uid).append("]")
			.append(" auth[").append(auth).append("]")
			.append(" mobile[").append(phoneNum).append("]")
			.append(" msg[").append(java.net.URLEncoder.encode(sms,"GBK")).append("]")
			.append(" expid[").append(expId).append("]");
			log.error("默认平台发送失败：url="+url+",uid"+uid+",auth="+auth);
			throw new BusinessException(expText.toString());
		}
	}
	
	private   Map<String, String> xml2map(String xmlString) throws DocumentException {  
		  Document doc = DocumentHelper.parseText(xmlString);  
		  Element rootElement = doc.getRootElement();  
		  Map<String, String> map = new HashMap<String, String>();  
		  String returnAttr=rootElement.attribute("return").getValue();
		  String infoAttr=rootElement.attribute("info").getValue();
		  map.put("return", returnAttr);
		  map.put("info", infoAttr);
		  return map;  
	}
	/**
	 * 创蓝短信平台
	 * @param phoneNum   手机号码
	 * @param sms        短信内容
	 * @param expId      扩展码
	 * @throws Exception
	 */
	public  void sendMsgByCL(String phoneNum,String sms,String extno,Map<String,String> valueMap)throws Exception{
		if(valueMap == null){
			log.error("平台发送：找不到创蓝账户配置信息");
			throw new BusinessException("平台发送：找不到创蓝账户配置信息");
		}
		String url = valueMap.get("url");
		String account = valueMap.get("uid");
		String pswd = valueMap.get("pwd");

		
		String needstatus="false";
		if(extno == null || "".equals(extno)){
			extno = "0";
		}
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
        //发送即时消息
		NameValuePair[] data = {
				new NameValuePair("un",account),
				new NameValuePair("pw", pswd),
				new NameValuePair("phone",phoneNum),
				new NameValuePair("msg", java.net.URLEncoder.encode(sms,"utf-8")),
				new NameValuePair("rd",extno)
//				new NameValuePair("ex",needstatus)
				};
		postMethod.setRequestBody(data);
		int statusCode = httpClient.executeMethod(postMethod);
		String resp = postMethod.getResponseBodyAsString();
		log.info("创蓝平台发送短信返回值:"+resp);
		if (statusCode != HttpStatus.SC_OK) {
			StringBuffer expText=new StringBuffer("send sms error ");
			expText.append(" statusCode[").append(statusCode).append("]")
			.append(" un[").append(account).append("]")
			.append(" pw[").append(pswd).append("]")
			.append(" phone[").append(phoneNum).append("]")
			.append(" msg[").append(java.net.URLEncoder.encode(sms,"utf-8")).append("]")
			.append(" rd[").append(extno).append("]");
			log.error("创蓝平台发送失败：url="+url+",un="+account+",pw="+pswd);
			throw new BusinessException(expText.toString());
		}
		
		String[] resps=resp.split("\n")[0].split(",");
		if(resps.length>1){
			if(!"0".equals(resps[1])){
				throw new BusinessException("创蓝平台发送失败：url="+url+",un="+account+",pw="+pswd+",错误码["+resps[1]+"]");
			}
		}
		
	}
	
	/*********玄武无线
	 * @param map ************/
//	public static void sendMsgByWx(String phoneNum,String sms,String extno, Map<String, String> valueMap) throws Exception {
//		if(valueMap == null){
//			log.error("平台发送：找不到创蓝账户配置信息");
//			throw new BusinessException("平台发送：找不到创蓝账户配置信息");
//		}
//		String url = valueMap.get("url");
//		String account = valueMap.get("uid");
//		String pswd = valueMap.get("pwd");
//
//		
//		String needstatus="false";
//		if(extno == null || "".equals(extno)){
//			extno = "0";
//		}
//		HttpClient httpClient = new HttpClient();
//		PostMethod postMethod = new PostMethod(url);
//        //发送即时消息
//		NameValuePair[] data = {
//				new NameValuePair("un",account),
//				new NameValuePair("pw", pswd),
//				new NameValuePair("phone",phoneNum),
//				new NameValuePair("msg", java.net.URLEncoder.encode(sms,"utf-8")),
//				new NameValuePair("rd",extno)
////				new NameValuePair("ex",needstatus)
//				};
//		postMethod.setRequestBody(data);
//		int statusCode = httpClient.executeMethod(postMethod);
//		String resp = postMethod.getResponseBodyAsString();
//		log.info("玄武平台发送短信返回值:"+resp);
//		if (statusCode != HttpStatus.SC_OK) {
//			StringBuffer expText=new StringBuffer("send sms error ");
//			expText.append(" statusCode[").append(statusCode).append("]")
//			.append(" un[").append(account).append("]")
//			.append(" pw[").append(pswd).append("]")
//			.append(" phone[").append(phoneNum).append("]")
//			.append(" msg[").append(java.net.URLEncoder.encode(sms,"utf-8")).append("]")
//			.append(" rd[").append(extno).append("]");
//			log.error("玄武平台发送失败：url="+url+",un="+account+",pw="+pswd);
//			throw new BusinessException(expText.toString());
//		}
//		
//		String[] resps=resp.split("\n")[0].split(",");
//		if(resps.length>1){
//			if(!"0".equals(resps[1])){
//				throw new BusinessException("玄武平台发送失败：url="+url+",un="+account+",pw="+pswd+",错误码["+resps[1]+"]");
//			}
//		}
//		
//		
//	}
	
	/*********玄武无线************/
	public static void sendMsgByWx(String phoneNum,String sms,String extno, Map<String, String> valueMap) throws Exception {
		
		if(valueMap == null){
			log.error("平台发送：找不到玄武无线账户配置信息");
			throw new BusinessException("平台发送：找不到玄武无线账户配置信息");
		}
		 
		String account = valueMap.get("account");
		String pswd = valueMap.get("pswd");
		String url = valueMap.get("url");
		String port = valueMap.get("port");
//		Account ac = new Account("wo56@wo56", "m0nPH9s0");//
		Account ac = new Account(account, pswd);//
		PostMsg pm = new PostMsg();
//		pm.getCmHost().setHost("211.147.239.62",9080);
		pm.getCmHost().setHost(url,Integer.parseInt(port));
		MTPack pack = new MTPack();
		pack.setBatchID(UUID.randomUUID());//序号
		pack.setBatchName("批次名称");//批次名称
		pack.setMsgType(MTPack.MsgType.SMS);
		pack.setBizType(0);//业务id
		if (org.apache.commons.lang.StringUtils.isNotBlank(extno)) {
			pack.setCustomNum(extno);//拓展码
		}
		pack.setDistinctFlag(false);//是否过滤重复号码

		pack.setSendType(MTPack.SendType.MASS);
		ArrayList<MessageData> msgs = new ArrayList<MessageData>();

		msgs.add(new MessageData(phoneNum, sms));
		pack.setMsgs(msgs);
		GsmsResponse resp = pm.post(ac, pack);
		if (resp != null) {
			log.info("玄武无线科技发送短信，返回码为："+resp.getResult());
			if (resp.getResult() != 0) {
				log.info(resp);
			}
		}else{
			throw new BusinessException("发送失败，返回信息为null");
		}
	}
	
	
	
	/**
	 * 获取短信上行回复的内容
	 * 
	 * 校验过来的地址
	 * 校验过来的用户名，密码
	 * 
	 * lyh-暂时不处理
	 * 
	 * @throws Exception
	 */
	public void smsReceiveByCL()throws Exception {
//		Map<String, String[]> DateMap =  new HashMap<String, String[]>();
//		DateMap.put("yyyyMM", new String[] { DateUtil.formatDate(new Date(),DateUtil.YEAR_MONTH_FORMAT2)});
//		Session session = SysContexts.getEntityManager(DateMap);
//		Map<String, String[]> map = SysContexts.getRequestParameterMap();
//		
//		String ip=InterAddrUtil.getPcIp(SysContexts.getRequest());
//		
//		String receiver=DataFormat.getStringKey(map, "receiver");
//		String pwd=DataFormat.getStringKey(map, "pswd");
//		String mobile=DataFormat.getStringKey(map, "mobile");
//		String msg=DataFormat.getStringKey(map, "msg");
//		String destcode=DataFormat.getStringKey(map, "destcode");
//		String moTime=DataFormat.getStringKey(map, "moTime");//格式YYMMDDhhmm
//		StringBuffer loginfo=new StringBuffer();
//		loginfo.append("创蓝上行短信：receiver:["+receiver+"]")
//		.append("ip:["+ip+"]")
//		.append("pwd:["+pwd+"]")
//		.append("mobile:["+mobile+"]")
//		.append("msg:["+msg+"]")
//		.append("moTime:["+moTime+"]")
//		.append("destcode:["+destcode+"]");
//		log.info(loginfo);
//		
//		
//		
//		if(StringUtils.isEmpty(receiver)){
//			log.error("创蓝上行短信：receiver 为空");
//			return;
//		}
//		if(StringUtils.isEmpty(pwd)){
//			log.error("创蓝上行短信：pwd 为空");
//			return;
//		}
//		if(StringUtils.isEmpty(mobile)){
//			log.error("创蓝上行短信：mobile 为空");
//			return;
//		}
//		if(StringUtils.isEmpty(msg)){
//			log.error("创蓝上行短信：msg 为空");
//			return;
//		}
//		if(StringUtils.isEmpty(destcode)){
//			log.error("创蓝上行短信：destcode 为空");
//			return;
//		}
//		
//		if(destcode.length()<3){
//			log.error("创蓝上行短信：destcode 长度不对");
//			return;
//		}
//		
//		
//		SysCfg  cfgIp= SysCfgUtil.getSysCfg(EnumConsts.SmsParam.SYS_SMS_CHUANGLAN_IP);
//		SysCfg cfgReceiver=SysCfgUtil.getSysCfg(EnumConsts.SmsParam.SYS_SMS_CHUANGLAN_RECEIVER);
//		SysCfg cfgPwd=SysCfgUtil.getSysCfg(EnumConsts.SmsParam.SYS_SMS_CHUANGLAN_PWD);
//		
//		if(cfgIp==null || StringUtils.isEmpty(cfgIp.getCfgValue())){
//			log.error("创蓝上行短信：没有在表sys_cfg 设置 SYS_SMS_CHUANGLAN_IP 参数");
//			return;
//		}
//		
//		if(cfgReceiver==null || StringUtils.isEmpty(cfgReceiver.getCfgValue())){
//			log.error("创蓝上行短信：没有在表sys_cfg 设置 SYS_SMS_CHUANGLAN_RECEIVER 参数");
//			return;
//		}
//		
//		if(cfgPwd==null || StringUtils.isEmpty(cfgPwd.getCfgValue())){
//			log.error("创蓝上行短信：没有在表sys_cfg 设置 cfgPwd 参数");
//			return;
//		}
//		
//		if(cfgIp.getCfgValue().indexOf(ip)==-1){
//			log.error("创蓝上行短信：ip 不对,ip["+ip+"],cfgIp["+cfgIp.getCfgValue()+"]");
//			return;
//		}
//		if(!cfgReceiver.getCfgValue().equals(receiver)){
//			log.error("创蓝上行短信：receiver 不对,receiver["+receiver+"],cfgReceiver["+cfgReceiver.getCfgValue()+"]");
//			return;
//		}
//		if(!cfgPwd.getCfgValue().equals(pwd)){
//			log.error("创蓝上行短信：pwd 不对,pwd["+pwd+"],cfgPwd["+cfgPwd.getCfgValue()+"]");
//			return;
//		}
//		//反正对方这个参数传入多个
//		destcode=destcode.split(",")[0];
//		
//		Criteria ca = session.createCriteria(SysSmsReceive.class);
//		destcode=destcode.substring(destcode.length()-3,destcode.length());
//		SysSmsReceive sysSmsRecevice = new SysSmsReceive();
//		sysSmsRecevice.setReceviceDate(new Date());
//		sysSmsRecevice.setExpId(destcode);
//		sysSmsRecevice.setBillId(mobile);
//		if(!StringUtils.isEmpty(msg)){
//			sysSmsRecevice.setSmsContent(URLDecoder.decode(msg, "utf-8"));
//		}
//		if("000".equals(destcode)){
//			sysSmsRecevice.setReceviceFlag(0);
//			ca.add(Restrictions.eq("receviceFlag", 0));
//		}else{
//			sysSmsRecevice.setReceviceFlag(1);
//			ca.add(Restrictions.eq("receviceFlag", 1));
//		}
//		ca.add(Restrictions.eq("smsContent", URLDecoder.decode(msg, "utf-8")));
//		ca.add(Restrictions.eq("expId", destcode));
//		ca.add(Restrictions.eq("billId", mobile));
//		List<SysSmsReceive> list=  ca.list();
//		if(list!=null && list.size()>0){
//			log.error("创蓝上行短信：该数据已经保存了，"+loginfo);
//			return ;
//		}
//		session.save(sysSmsRecevice);
	}
	
	
	
	/**
	 * 获取SysTenantExtend表中第三方账户数据
	 * @param tenantId
	 * @param EKey
	 * @return
	 */
	public Map<String,String> getEKey(List<SysSmsPlatformInfo> list){
		if(list != null){
			Map<String,String> map = new HashMap<String, String>();
			for(SysSmsPlatformInfo sysSmsPlatformInfo : list){
				if(!StringUtils.isEmpty(sysSmsPlatformInfo.getKey())){
					map.put(sysSmsPlatformInfo.getKey().trim(), sysSmsPlatformInfo.getValue().trim());
				}
			}
			return map;
		}
		return null;
	}
}
