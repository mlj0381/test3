package com.wo56.business.sys.intf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.sms.vo.in.SysSmsSearchVO;
import com.wo56.business.sys.vo.out.SmsSendWeChatOut;
import com.wo56.business.sys.vo.out.SysSmsSendHisOut;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.sms.utils.TableSplitRule;
import com.wo56.common.sms.vo.SysSmsSend;
import com.wo56.common.sms.vo.SysSmsSendHis;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SmsUtil;

public class SysSmsSendTF {

	/**
	 * 发送预约信息
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doSearchMsm(Map<String ,Object> inParam) throws Exception{
		JSONArray sendListStr = JSONArray.fromObject(inParam.get("list"));
		List<SysSmsSearchVO> sendVO = (List<SysSmsSearchVO>) JSONArray.toList(sendListStr,SysSmsSearchVO.class);
		BaseUser user = SysContexts.getCurrentOperator();
		Organization  organization  = OraganizationCacheDataUtil.getOrganization(user.getOrgId());
		if(null != sendVO && sendVO.size()>0){
			for(SysSmsSearchVO vo : sendVO){
				if(vo.getDeliveryType() == 1){
					//自提
					if(StringUtils.isNotBlank(vo.getConsigneeBill())){
						SmsUtil.sendSearchSince(user.getTenantId(), vo.getConsigneeBill(), vo.getTrackingNum(), vo.getGoodsName(), organization.getOrgName(), organization.getDepartmentAddress(), CommonUtil.getOrgSupportStaffPhone());
					}
				}else{
					//非自提
					if(StringUtils.isNotBlank(vo.getConsigneeBill())){
						SmsUtil.sendSearchNoSince(user.getTenantId(), vo.getConsigneeBill(), vo.getTrackingNum(), vo.getGoodsName(), organization.getOrgName(), organization.getDepartmentAddress(), CommonUtil.getOrgSupportStaffPhone());
					}
				}
			}
		}
		return null;
	}
	
	public Query queryMessage(String billId,long smsId,int objType){
		if(StringUtils.isEmpty(billId)){
			throw new BusinessException("请传入手机号码！");
		}
		String date = DateUtil.formatDate(new Date(),DateUtil.YEAR_MONTH_FORMAT2);
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer("select p.template_name,s.SMS_CONTENT,s.send_date,s.SMS_ID,s.is_read,s.obj_type,s.obj_id from ");
		sb.append(" sys_sms_send_his_");
		sb.append(date);
		sb.append(" s left join sys_sms_template p on s.template_id = p.template_id ");
		sb.append("  where s.state = 1 AND s.BILL_ID = :billId AND s.SMS_TYPE = :smsType ");
		if(smsId > 0){
			sb.append(" AND sms_id = :smsId ");
		}
		if(objType > 0){
			sb.append("  AND s.obj_type = :objType ");
		}else{
			sb.append("  AND s.obj_type in :objType ");
		}
		sb.append(" order by s.sms_id desc ");
		Query query = session.createSQLQuery(sb.toString());
		query.setParameter("billId", billId);
		query.setParameter("smsType", EnumConsts.SmsType.MOPBILE_TYPE);
		if(objType > 0){
			query.setParameter("objType",objType);
		}else{
			query.setParameterList("objType",new Integer[]{1,2});
		}
		if(smsId > 0){
			query.setParameter("smsId", smsId);
		}
		return query;
	}
	/**
	 * 查询短信
	 * @param billId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPage getMessage(String billId,long smsId,int objType){
		List<SysSmsSendHisOut> listOut = new ArrayList<SysSmsSendHisOut>();
		IPage p = PageUtil.gridPage(queryMessage(billId, smsId, objType));
		//List<Object[]> list = query.list();
		List<Object[]> list = p.getThisPageElements();
		if(list.size() > 0){
			for(Object[] temp : list){
				SysSmsSendHisOut out = new SysSmsSendHisOut();
				out.setTemplateName(temp[0] != null ? String.valueOf(temp[0]) : "");
				out.setSmsContent(temp[1] != null ? String.valueOf(temp[1]) : "");
				out.setCreateDataString(temp[2] != null ? DateUtil.formatDate((Date)temp[2], "yyyy-MM-dd") : "");
				out.setSmsId(temp[3] != null ? String.valueOf(temp[3]) : "");
				out.setIsRead(temp[4] != null ? String.valueOf(temp[4]) : "");
				out.setObjType(temp[5] != null ? String.valueOf(temp[5]) : "");
				out.setBusiId(temp[6] != null ? String.valueOf(temp[6]) : "");
				listOut.add(out);
			}
		}
		p.setThisPageElements(listOut);
		return p;
	}
	/**
	 * 获取详情
	 * @param billId
	 * @param id
	 * @return
	 */
	public SysSmsSendHisOut getMessageDet(String billId,long id){
		if(id < 0){
			throw new BusinessException("请出入短信编号！");
		}
		Query query = queryMessage(billId, id, -1);
		List<Object[]> list = query.list();
		SysSmsSendHisOut out = new SysSmsSendHisOut();
		if(list.size() > 0){
			for(Object[] temp : list){
				out.setTemplateName(temp[0] != null ? String.valueOf(temp[0]) : "");
				out.setSmsContent(temp[1] != null ? String.valueOf(temp[1]) : "");
				out.setCreateDataString(temp[2] != null ? DateUtil.formatDate((Date)temp[2], "yyyy-MM-dd") : "");
				out.setSmsId(temp[3] != null ? String.valueOf(temp[3]) : "");
				out.setIsRead(temp[4] != null ? String.valueOf(temp[4]) : "");
			}
		}
		return out;
	}
	/**
	 * 删除消息
	 * @param id
	 * @return
	 */
	public String delMessage(List<String> id){
		Session session = SysContexts.getEntityManager();
		String date = DateUtil.formatDate(new Date(),DateUtil.YEAR_MONTH_FORMAT2);
		Query query = session.createSQLQuery("update sys_sms_send_his_"+date+" set state = 0 where sms_id in (:smsId)");
		query.setParameterList("smsId", id);
		int i = query.executeUpdate();
		if(i > 0){
			return "Y";
		}else{
			return "N";
		}
	}
	/**
	 * 是否已读
	 * @param smsId
	 * @return
	 */
	public String isReadMessage(long smsId){
		Map<String, Object> map =  new HashMap<String, Object>();
		map.put("yyyyMM", new String[] { DateUtil.formatDate(new Date(),DateUtil.YEAR_MONTH_FORMAT2) });
		Session session = SysContexts.getEntityManager(map);
		SysSmsSendHis his = (SysSmsSendHis) session.get(SysSmsSendHis.class, smsId);
		his.setIsRead(SysStaticDataEnumYunQi.IS_READ.read);
		return "Y";
	}
	
	/**
	 * 查询未读数
	 */
	@SuppressWarnings("unchecked")
	public Map queryNotRead(){
		String date= DateUtil.formatDate(new Date(),DateUtil.YEAR_MONTH_FORMAT2);
		Session session = SysContexts.getEntityManager(true);
		String sb = " select COUNT(*),OBJ_TYPE from sys_sms_send_his_"+date+" where BILL_ID = :billId and STATE = 1  AND IS_READ = 0 and SMS_TYPE in (1,2) and obj_type in (1,2) GROUP BY OBJ_TYPE order by OBJ_TYPE ";
		Query query = session.createSQLQuery(sb);
		query.setParameter("billId", SysContexts.getCurrentOperator().getTelphone());
		List<Object[]> list = query.list();
		Map outMap = new HashMap();
		if(list.size() > 0){
			for(Object[] temp : list){
				int type = temp[1] != null && StringUtils.isNotEmpty(temp[1].toString()) ? Integer.parseInt(String.valueOf(temp[1])) : 0;
				if(type == SysStaticDataEnumYunQi.OBJ_TYPE.PUBLIC_MESSAGR){
					outMap.put("isReadNumBase", temp[0] != null ? String.valueOf(temp[0]) : "");//未读数公共消息
				}else if (type == SysStaticDataEnumYunQi.OBJ_TYPE.ORDER_MESSAGE){
					outMap.put("isReadNumOrder", temp[0] != null ? String.valueOf(temp[0]) : "");
				}
			}
		}
		if(outMap.get("isReadNumBase")==null){
			outMap.put("isReadNumBase","");//未读数公共消息
		}
		if(outMap.get("isReadNumOrder")==null){
			outMap.put("isReadNumOrder","");
		}
		return outMap;
	}
	
	/**
	 * 查询微信推送消息
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getWeChatSms(){
		Session session = SysContexts.getEntityManager(true);
		Query query =session.createSQLQuery(" select s.sms_id,s.sms_Content,s.bill_Id,(select open_id from cm_user_info where login_acct =s.bill_Id and user_Type in (:userType) and state = :state and login_type in (:loginType)) as openId,s.OBJ_ID from sys_sms_send s where s.sms_type =:smsType and send_flag =:sendFlag  ");
		query.setParameter("smsType", EnumConstsYQ.SmsType.WECHAT_TYPE);
		query.setParameter("sendFlag", SysStaticDataEnumYunQi.SEND_FLAG.NOT_SEND);
		//query.setParameter("billId", billId);
		query.setParameterList("userType", new Integer[]{SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS,SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL});
		query.setParameterList("loginType", new Integer[]{SysStaticDataEnumYunQi.LOGIN_TYPE.APP,SysStaticDataEnumYunQi.LOGIN_TYPE.WEB_APP});
		query.setParameter("state", SysStaticDataEnumYunQi.STS.VALID);
		List<Object[]> list = query.list();
		List<SmsSendWeChatOut> listOut = new ArrayList<SmsSendWeChatOut>();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				SmsSendWeChatOut out = new SmsSendWeChatOut();
				out.setSmsId(obj[0] != null ? Long.parseLong(obj[0].toString()) : -1);
				out.setSmsContent(obj[1] != null ? obj[1].toString() : "");
				out.setBillId(obj[2] != null ? obj[2].toString() : "");
				out.setOpenId(obj[3] != null ? obj[3].toString() : "");
				out.setObjId(obj[4] != null ? obj[4].toString() : "");
				listOut.add(out);
			}
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(listOut));
	}
	/**
	 * 保存进历史表
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String removeSysSmsSend(Map<String,Object> inParam)throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		map.put("yyyyMM", TableSplitRule.yyyyMM());
		Session session = SysContexts.getEntityManager(map);
		long smsId = DataFormat.getLongKey(inParam, "smsId");
		String error = DataFormat.getStringKey(inParam, "error");
		SysSmsSend sysSmsSend = (SysSmsSend) session.get(SysSmsSend.class, smsId);
		SysSmsSendHis sysSmsSendHis = new SysSmsSendHis();
		BeanUtils.copyProperties(sysSmsSendHis, sysSmsSend);
		if (StringUtils.isNotEmpty(error)) {
			sysSmsSendHis.setChannelType(error);
		}else{
			sysSmsSendHis.setChannelType("微信");
		}
		sysSmsSendHis.setIsRead(SysStaticDataEnumYunQi.IS_READ.unread);
		sysSmsSendHis.setMd5(sysSmsSend.getMd5());
		sysSmsSendHis.setRealSendDate(new Date());
		sysSmsSendHis.setSendFlag(SysStaticDataEnumYunQi.SEND_FLAG.IS_SEND);
		sysSmsSendHis.setState(SysStaticDataEnumYunQi.STS.VALID);
		session.save(sysSmsSendHis);
		session.delete(sysSmsSend);
		return "Y";
	}
}
