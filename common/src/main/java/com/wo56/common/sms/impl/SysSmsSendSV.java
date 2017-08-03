package com.wo56.common.sms.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.components.redis.RemoteCacheUtil;
import com.framework.core.SysContexts;
import com.framework.core.encrypt.MD5;
import com.framework.core.exception.BusinessException;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.SysCfgUtil;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.sms.utils.EnumConsts;
import com.wo56.common.sms.utils.SmsControllerCacheUtil;
import com.wo56.common.sms.utils.SysTenantControllerCacheUtil;
import com.wo56.common.sms.vo.SysSmsParam;
import com.wo56.common.sms.vo.SysSmsSend;
import com.wo56.common.sms.vo.SysSmsTemplate;
import com.wo56.common.sms.vo.SysTenantController;
import com.wo56.common.utils.SysMagUtil;

/**
 * 用户业务调用，保存短信表以及之前的业务控制
 * @author zhouchao
 *
 */
public class SysSmsSendSV {

	private static final Log log = LogFactory.getLog(SysSmsSendSV.class);
	
	/**
	 * 转换短信内容
	 * @param templateId
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public  String getSmsContent(SysSmsTemplate sysSmsTem,long templateId,Map paramMap,long tenantId) throws Exception{
		String smsContent ="";
		try{
			List<SysSmsParam> smsParams = querySmsParamsByTemplateId(templateId);
			if(sysSmsTem != null){
				smsContent = sysSmsTem.getTemplateContent();
				if(smsParams!=null&&smsParams.size()>0){
					for(SysSmsParam smsParamItem : smsParams){
						String paraCode ="${"+smsParamItem.getParamCode()+"}";
						String paraValue = String.valueOf(paramMap.get(smsParamItem.getParamCode()));
						if(smsContent.indexOf(paraCode)>-1){
							smsContent = StringUtils.replace(smsContent, paraCode, paraValue);
						}
					}
				}
			}
		}catch(Exception e){
			log.error("转换短信内容异常"+e);
			throw new Exception("转换短信内容异常");
		}
		return smsContent;
	}
	
	/**
	 * 查询短信模版
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public List<SysSmsParam> querySmsParamsByTemplateId(long templateId)throws Exception{
		Session session = SysContexts.getEntityManager();
		Criteria ca = session.createCriteria(SysSmsParam.class);
		if(templateId>0){
			ca.add(Restrictions.eq("templateId",templateId));
		}
		//ca.add(Restrictions.eq("tenantId", tenantId));
		ca.add(Restrictions.eq("state",1));
		List<SysSmsParam> sysSmsParams = ca.list();
		return sysSmsParams;
	}
	
	/**
	 * 判断该租户是否发送短信
	 * @param tenantId
	 * @return
	 */
	public boolean isSendOfTenantId(long tenantId)throws Exception{
		List<SysTenantController> list = SysTenantControllerCacheUtil.getTenantIdList(tenantId); 
		if(list!= null && list.size()>0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 发送短信，延迟N分钟发送
	 * @param smsMap
	 * @param delayMinis  延迟时间
	 * @throws Exception
	 */
	public void sendSms(Map smsMap, int delayMinis) throws Exception {
		log.info("---------------start for sending sms-----------");
		if(smsMap!=null){
			String billId = DataFormat.getStringKey(smsMap,"$billId");
			long tenantId = DataFormat.getLongKey(smsMap,"$tenantId");
			long templateId = DataFormat.getLongKey(smsMap,"$templateId");
			String content = DataFormat.getStringKey(smsMap, "$smsContent");
			
			Map paramMap =(Map) smsMap.get("paramMap");
			
			if(!isSendOfTenantId(tenantId)){
				log.error("短信控制表Sys_tenant_controller没有配置租户, 或者租户为："+tenantId+" state不为1，不发送信息");
				return;
			}
			
			
			if(templateId <0 ){
				log.error("手机号码为:["+billId+"]模板id不能为空,不发送短信");
				return;
			}
			if(billId ==null || "".equals(billId) ){
				throw new BusinessException("手机号码不能为空");
			}
			if(!SmsControllerCacheUtil.isSend(billId, templateId, tenantId)){
				log.error("手机号码:["+billId+"]不接收租户为:["+tenantId+"]模版为：["+templateId+"]");
				return;
			}
			
			
			SysSmsTemplate sysSmsTem = (SysSmsTemplate)SysMagUtil.getObjById(SysSmsTemplate.class,templateId);
			int smsType=DataFormat.getIntKey(paramMap, "smsType")>0?DataFormat.getIntKey(paramMap, "smsType"):sysSmsTem.getSmsType()==null?1:sysSmsTem.getSmsType();
			
			if(smsType==EnumConstsYQ.SmsType.NOTICE_TYPE){
				if(!SmsControllerCacheUtil.cTenantIdTemplate(templateId, tenantId)){
					log.error("租户:["+tenantId+"]不发送模版为["+templateId+"]的短信");
					return;
				}
			}
			
			//保存发送短信表
			SysSmsSend smsSend = new SysSmsSend();
			smsSend.setBillId(billId);
//			if(templateId == EnumConsts.TEMPLATE_ID.TEMP_SYSTEM){
//				smsSend.setTemplateId(templateId);
//				smsSend.setObjId(EnumConsts.OBJ_ID.SYSTEM);
//				smsSend.setObjType(EnumConsts.OBJ_TYPE.SYSTEM);
//				smsSend.setSmsType(EnumConsts.SMS_TYPE.SMS_SYSTEM);
//				smsSend.setSmsContent(content);
//				smsSend.setSendFlag(0);
//			}else{
			
			if(sysSmsTem == null){
				log.error("找不到指定模版"+templateId);
				return;
			}
			String smsContent = getSmsContent(sysSmsTem,templateId, paramMap,tenantId);
			if(log.isDebugEnabled()){
				log.debug("替换后的短信内容为："+smsContent);
			}
			if(smsContent.contains("#<")){
				smsContent = smsReplace(smsContent);
			}
			smsSend.setSmsContent(smsContent);
			smsSend.setTemplateId(templateId);
			smsSend.setObjId(DataFormat.getLongKey(paramMap, "objId")>0?DataFormat.getLongKey(paramMap, "objId"):sysSmsTem.getObjId()==null?0:sysSmsTem.getObjId());
			smsSend.setObjType(StringUtils.isEmpty(DataFormat.getStringKey(paramMap, "objType"))?sysSmsTem.getObjType():DataFormat.getStringKey(paramMap, "objType"));
			smsSend.setSmsType(DataFormat.getIntKey(paramMap, "smsType")>0?DataFormat.getIntKey(paramMap, "smsType"):sysSmsTem.getSmsType()==null?1:sysSmsTem.getSmsType());
			if(null != sysSmsTem.getState() && sysSmsTem.getState() == 1){
				smsSend.setSendFlag(0);
			}else{
				//如果模版未启用，设置为2
				smsSend.setSendFlag(2);
			}
//			}
			smsSend.setTenantId(tenantId);
			Date sendDate = DateUtil.addMinis(new Date(), delayMinis);
			smsSend.setSendDate(sendDate);
			smsSend.setExpId("0");
			
			
			
			
			doSave(smsSend);
		}
	}
	
	private String smsReplace(String content){
		if(StringUtils.isNotBlank(content)){
			try {
				String[] ss = content.split("#<");
				List<String> l = new ArrayList<String>();
				//截取需要替换的数据，并且存放在list中
				for(int i=0;i<ss.length;i++){
					if(ss[i].indexOf(">#")>-1){
						l.add(ss[i].substring(0,ss[i].indexOf(">#")));
					}
				}
				if(l.size()>0){
					//从静态数据中获取数据，替换短信中的内容
					Iterator it = l.iterator();
					while(it.hasNext()){
						String oldStr = (String) it.next();
						String replaceStr = (String) SysCfgUtil.getCfgVal(oldStr, 3, String.class);
						content = StringUtils.replace(content, "#<"+oldStr+">#", replaceStr);
					}
				}
			} catch (Exception e) {
				log.error("替换静态数据出错");
			}
		}
		return content;
	}
	
	/**
	 * 保存数据到表
	 * @param sysSmsSendObj
	 * @throws Exception
	 */
	public void doSave(SysSmsSend sysSmsSendObj) throws Exception {
		String smsContent = sysSmsSendObj.getSmsContent();
		String billId = sysSmsSendObj.getBillId();
		String sendDate = DateUtil.formatDateByFormat(sysSmsSendObj.getSendDate(), DateUtil.DATE_FORMAT2);
		String md5=MD5.eccryptMD5(sysSmsSendObj.getTenantId()+smsContent+billId+sendDate+sysSmsSendObj.getSmsType()).toUpperCase();
		boolean isSend = true;
		Session session = SysContexts.getEntityManager();
		//判断是否限制发送短信
		Query query = session.createSQLQuery("select count(1) from sms_controller where (bill_id =:billId and sms_type = '"+EnumConsts.SmsControllerSmsType.USER+"') or (bill_id =:billId and (sms_type is null or sms_type = '"+EnumConsts.SmsControllerSmsType.TEMPLATE+"') and template_id =:templateId) or (bill_id =:billId and sms_type = '"+EnumConsts.SmsControllerSmsType.TENANT+"')");
		query.setParameter("billId", billId);
		query.setParameter("templateId", sysSmsSendObj.getTemplateId());
		BigInteger count = (BigInteger) query.uniqueResult();
		if(count.intValue()>0){
			isSend = false;
		}
		if(isSend){
			if(!RemoteCacheUtil.sismember(EnumConsts.RemoteCache.SMS_MD5, md5)){
				RemoteCacheUtil.sadd(EnumConsts.RemoteCache.SMS_MD5, md5);
				sysSmsSendObj.setMd5(md5);
				session.save(sysSmsSendObj);
			}else{
				log.error("短信MD5值存在，不保存短信表"+sysSmsSendObj.getTenantId()+smsContent+billId+sendDate+sysSmsSendObj.getSmsType());
			}
		}else{
			log.error("用户设置不接收改模板的短信,手机号码:["+billId+"],短信模板:["+sysSmsSendObj.getTemplateId()+"]"+"租户id为："+sysSmsSendObj.getTenantId());
		}
	}
	
	public SysSmsTemplate getSysTemplate(long templateId) throws Exception {
		SysSmsTemplate sysSmsTem = (SysSmsTemplate)SysMagUtil.getObjById(SysSmsTemplate.class,templateId);
		if(sysSmsTem == null){
			throw new BusinessException("找不到指定模版");
		}
		return sysSmsTem;
	}
	
}
