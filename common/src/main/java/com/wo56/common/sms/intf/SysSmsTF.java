package com.wo56.common.sms.intf;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.sms.impl.SysSmsSendSV;
import com.wo56.common.sms.utils.EnumConsts;
import com.wo56.common.sms.vo.SysSmsTemplate;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class SysSmsTF {
	
	private SysSmsSendSV sysSmsSendSV;
	public void setSysSmsSendSV(SysSmsSendSV sysSmsSendSV) {
		this.sysSmsSendSV = sysSmsSendSV;
	}
	
	private static final Log log = LogFactory.getLog(SysSmsTF.class);
	
	
	public void sendMessage(String billId,String smsContent,long tenantId)throws Exception{
		if(StringUtils.isBlank(billId)){
			log.error("手机号码不能为空,不发送短信");
			return;
		}
		if(StringUtils.isBlank(smsContent)){
			log.error("短信内容不能为空,不发送短信");
			return;
		}
		if(!CommonUtil.isCheckPhone(billId)){
			log.error("手机号码不正确,不发送短信");
			return;
		}
		if(tenantId<0 && tenantId != -1){
			log.error("租户id不正确,不发送短信");
			return;
		}
		Map<String,Object> smsMap = new HashMap<String,Object>();
		smsMap.put("$billId", billId);
		smsMap.put("$tenantId", tenantId);
		smsMap.put("$smsContent", smsContent);
		smsMap.put("$templateId", EnumConsts.TEMPLATE_ID.TEMP_SYSTEM);
		sysSmsSendSV.sendSms(smsMap,0);
	}
	
	
	public void sendMessage(String billId,String smsContent)throws Exception{
		sendMessage(billId, smsContent, -1L);
	}
	
	/**
	 * 
	 * @param billId 手机
	 * @param templateId 模版id 
	 * @param smsMap｛模版替换内容｝ 
	 * @param tenantId
	 * @throws Exception
	 */
	public void sendMesMap(String billId,long templateId,Map<String,Object> paramMap,long tenantId) throws Exception{
		if(StringUtils.isBlank(billId)){
			log.error("手机号码不能为空,不发送短信");
			return;
		}
		if(!CommonUtil.isCheckPhone(billId)){
			log.error("手机号码不正确");
			return;
		}
		if(templateId<0){
			log.error("模版id不能为空");
			return;
		}
		if(tenantId<0 && tenantId != -1){
			log.error("租户id不正确");
			return;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("$billId", billId);
		map.put("$tenantId", tenantId);
		map.put("$templateId", templateId);
		map.put("paramMap", paramMap);
		sysSmsSendSV.sendSms(map, 0);
	}
	
	/**
	 * 
	 * @param billId 手机
	 * @param templateId 模版id 
	 * @param smsMap｛ smsType：短信类型（必传）；objType:实体类型；objId：实体id；content：短信内容（默认取模版中内容，不需要传）；paraMap：模版替换内容｝ 
	 * @param tenantId
	 * @throws Exception
	 */
	public void sendMesMap(String billId,long templateId,Map<String,Object> paramMap) throws Exception{
		sendMesMap(billId, templateId, paramMap, -1L);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getSysTemplate(Map<String,Object> inParam)throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		Organization  organization  = OraganizationCacheDataUtil.getOrganization(user.getOrgId());
		SysSmsSendSV sysSmsSendSV = (SysSmsSendSV)SysContexts.getBean("sysSmsSendSV");
		//预约通知短信模版(自提)
		SysSmsTemplate  sysSmsTemplateT= sysSmsSendSV.getSysTemplate(1000000013L);
		
		Map paramMapT = new HashMap();
		paramMapT.put("TRACKING_NUM", "****");
		paramMapT.put("GOODS_NAME", "****");
		paramMapT.put("DEST_ORG", organization.getOrgName());
		paramMapT.put("ADDER", organization.getDepartmentAddress());
		paramMapT.put("TELEPHONE", CommonUtil.getOrgSupportStaffPhone() );
		//预约通知短信模版(非自提)
		SysSmsTemplate  sysSmsTemplateL= sysSmsSendSV.getSysTemplate(1000000014L);
		Map paramMapL = new HashMap();
		paramMapL.put("TRACKING_NUM", "****");
		paramMapL.put("GOODS_NAME", "****");
		paramMapL.put("DEST_ORG", organization.getOrgName());
		paramMapL.put("ADDER", organization.getDepartmentAddress());
		paramMapL.put("TELEPHONE", CommonUtil.getOrgSupportStaffPhone());
		Map map = new HashMap();
		map.put("sysSmsTemplateT", sysSmsSendSV.getSmsContent(sysSmsTemplateT, 1000000013L, paramMapT, -1));
		map.put("sysSmsTemplateL", sysSmsSendSV.getSmsContent(sysSmsTemplateL, 1000000014L, paramMapL, -1));
		return map;
	}
}
