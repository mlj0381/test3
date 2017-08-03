package com.wo56.business.sys.intf;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.wo56.business.ac.interfaces.IShortMessageintf;
import com.wo56.business.sys.impl.SysShortMessageSV;
import com.wo56.business.sys.vo.out.SysSmsSendHisOut;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class ShortMessageTF implements IShortMessageintf {
	
	/**
	 * 查询历史表中的数据
	 * @param templateId    模板ID 
	 * 		  tenantId        租户Id 
	 * 		  yearAndMonth  年月
	 * @return Pagination<SysSmsSendHisVO>
	 */
	public Pagination<SysSmsSendHisOut> tenantIdAndTemplaId(Map<String,Object> map) throws Exception {
		BaseUser user = SysContexts.getCurrentOperator();
		long tenantId = user.getTenantId();
		long templateId = DataFormat.getLongKey(map, "templateId");
		String yearAndMonth ="201610";
		if(map.get("yearAndMonth").toString()!=null &&!"".equals(map.get("yearAndMonth").toString())){
			yearAndMonth=yearAndMonthUtils(map.get("yearAndMonth").toString());
		}
		SysShortMessageSV sysShortMessageSV = (SysShortMessageSV)SysContexts.getBean("sysShortMessageSV");
		//templateId 模板ID   tenantId 租户ID   yearAndMonth  年月
		IPage iPage = sysShortMessageSV.tenantIdAndTemplaId(templateId,tenantId,yearAndMonth);
		
		//分页
		List<Object[]> searchList = (List<Object[]>)iPage.getThisPageElements();
		List<SysSmsSendHisOut> sysSmsSendHisVOs = new ArrayList<SysSmsSendHisOut>();
		for (int i = 0; i < searchList.size(); i++) {
			Object[] obj = (Object[]) searchList.get(i);
			SysSmsSendHisOut vo = new SysSmsSendHisOut();
			vo.setSmsContent(obj[0]+"");
			vo.setNumber(obj[1]+"");
			vo.setTemplateName(obj[2]+"");
			vo.setTenantName(OraganizationCacheDataUtil.getOrganizationByTenantId(tenantId)==null?"":OraganizationCacheDataUtil.getOrganizationByTenantId(tenantId).getOrgName());
			vo.setTemplateId(obj[3]+"");
			sysSmsSendHisVOs.add(vo);
		}	
		iPage.setThisPageElements(sysSmsSendHisVOs);
		Pagination<SysSmsSendHisOut> pagination = new Pagination<SysSmsSendHisOut>(iPage);
		//分页
		return pagination;
	}
	
	/**
	 * 查询历史表中的数据
	 * @param tenantId        租户Id 
	 * 根据租户ID查询租户的所有模板  
	 * @return list
	 */
	public List<Map<String,String>> getTempLate(Map<String,Object> map){
		BaseUser user = SysContexts.getCurrentOperator();
		Long tenantId = user.getTenantId();
		map.put("tenantId", tenantId);
		SysShortMessageSV sysShortMessageSV = (SysShortMessageSV)SysContexts.getBean("sysShortMessageSV");
		List tempLates = sysShortMessageSV.getTempLate(tenantId);
	
		List<Map<String,String>> returnList=new ArrayList<Map<String,String>>();
		Map<String,String> allMap = new HashMap<String,String>();
		allMap.put("templateId", "-1");
		allMap.put("templateName","所有");
		returnList.add(allMap);
		for (int i = 0; i < tempLates.size(); i++) {
			Object[] object = (Object[]) tempLates.get(i);
			Map<String,String> rtnMap = new HashMap<String,String>();
			rtnMap.put("templateId", object[1]+"");
			rtnMap.put("templateName", object[0]+"");
			returnList.add(rtnMap);
		}
		return returnList;
	}
	
	/**
	 * @param map
	 * 第二次跳转页面的方法
	 * @return map
	 */
	public Pagination<SysSmsSendHisOut> allShortMessage(Map<String,Object> map) throws Exception {
		BaseUser user = SysContexts.getCurrentOperator();
		long templateId = DataFormat.getLongKey(map, "templateId");
		long tenantId = user.getTenantId();
		
		//默认查最小的表201610
		String yearAndMonth ="201610";
		if(map.get("yearAndMonth").toString()!=null &&!"".equals(map.get("yearAndMonth").toString())){
			yearAndMonth= yearAndMonthUtils(map.get("yearAndMonth").toString());
		}
		
		SysShortMessageSV sysShortMessageSV = (SysShortMessageSV)SysContexts.getBean("sysShortMessageSV");
		IPage iPage = sysShortMessageSV.allShortMessage(templateId,tenantId,yearAndMonth);
		
		//分页
		List<Object[]> searchList = (List<Object[]>)iPage.getThisPageElements();
		List<SysSmsSendHisOut> sysSmsSendHisVOs = new ArrayList<SysSmsSendHisOut>();
		for (int i = 0; i < searchList.size(); i++) {
			Object[] obj = (Object[]) searchList.get(i);
			SysSmsSendHisOut vo = new SysSmsSendHisOut();
			vo.setSmsContent(obj[0]+"");
			vo.setBillId(obj[1]+"");
			vo.setTenantName(OraganizationCacheDataUtil.getOrganizationByTenantId(tenantId)==null?"":OraganizationCacheDataUtil.getOrganizationByTenantId(tenantId).getOrgName());
			vo.setTemplateName(obj[2]+"");
			sysSmsSendHisVOs.add(vo);
		}	
		iPage.setThisPageElements(sysSmsSendHisVOs);
		Pagination<SysSmsSendHisOut> pagination = new Pagination<SysSmsSendHisOut>(iPage);
		//分页
		return pagination;
	}
	

	
	
	
	
	
	
	
	
	
/**----------------工具------------------------*/	
	/**返回yearAndMonth*/
	private String yearAndMonthUtils(String str){
		char[] cs = str.toCharArray();
		String s="";
		for(int i=0;i<cs.length;i++){
			if(cs[i]>='0'&&cs[i]<='9'){
				s+=cs[i];
			}
		}
		return s;
	}
	
	/**返回yyyy-MM-dd*/
	private String yd(String str,String date) throws Exception{
		String s1 = str.substring(0, 4);
		String s2 = str.substring(4);
		return s1+"-"+s2+"-"+date;
	}

}
