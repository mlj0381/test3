package com.wo56.business.cm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.PageUtil;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.common.consts.SysStaticDataEnumYunQi;

public class CmUserInfoPullSV {
	/**
	 * 保存与修改
	 * @param pull
	 * @return
	 */
	public String doSaveOrUpdate(CmUserInfoPull pull){
		Session session = SysContexts.getEntityManager(false);
		session.saveOrUpdate(pull);
		return "Y";
	}
	/**
	 * 同过用户id查询拉包工扩展表
	 * @param userId
	 * @return
	 */
	public Criteria getCmUserInfoPullByUserId(long userId){
		Session session = SysContexts.getEntityManager(true);
		return  session.createCriteria(CmUserInfoPull.class).add(Restrictions.eq("userId", userId));
	}
	/**
	 * 拉包工列表查询
	 */
	@SuppressWarnings({ "rawtypes"})
	public Query queryCmUserInfoPull(Map<String,Object> inParam){
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer("SELECT ");
		
		sb.append(" c.USER_ID,t.`NAME`,c.USER_NAME,c.LOGIN_ACCT,p.PROVINCE_NAME, ");
		sb.append(" p.CITY_NAME,a.PAYMENT_TYPE,a.BANK_NAME,a.ACCOUNT_NUM,a.BANK_CARD,p.PULL_STATE,p.COOPERATION_MODE,p.DISTRICT_NAME,a.ACCOUNT_NAME,a.BANK_HOLDER,p.JOB_NUMBER,p.DEFAULT_SINGULAR_NUM,p.city ");
		sb.append(" FROM cm_user_info c INNER JOIN cm_user_info_pull p ON c.USER_ID = p.USER_ID  and c.user_type = :userType AND c.STATE = :state");
		sb.append(" LEFT JOIN sys_tenant_def t ON p.TENANT_ID = t.TENANT_ID ");
		sb.append(" LEFT JOIN ac_payment_method a ON a.USER_ID = c.USER_ID LEFT JOIN cm_pull_black b ON b.USER_ID = p.USER_ID  where 1=1 AND p.tenant_id = :tenantId ");
		sb.append(" AND (b.`STATUS` = 0 or b.`STATUS` is null ) ");
		
		String tenantName = DataFormat.getStringKey(inParam, "tenantName");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tenantId", SysContexts.getCurrentOperator().getTenantId());
		map.put("userType", SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL);
		map.put("state", SysStaticDataEnumYunQi.STS.VALID);
		long userId = DataFormat.getLongKey(inParam,"userId");
		if(userId > 0){
			sb.append(" AND c.USER_ID = :userId ");
		}
		if(StringUtils.isNotEmpty(tenantName)){
			sb.append(" AND t.NAME like :tenantName");
			map.put("tenantName", "%"+tenantName+"%");
		}
		String userName = DataFormat.getStringKey(inParam, "userName");
		if(StringUtils.isNotEmpty(userName)){
			sb.append(" AND c.USER_NAME like :userName ");
			map.put("userName", "%"+userName+"%");
		}
		String billId = DataFormat.getStringKey(inParam, "billId");
		if(StringUtils.isNotEmpty(billId)){
			sb.append(" AND c.LOGIN_ACCT like :billId ");
			map.put("billId", "%"+billId+"%");
		}
		int pullState = DataFormat.getIntKey(inParam, "pullState");
		if(pullState > 0){
			sb.append(" AND p.PULL_STATE = :pullState ");
			map.put("pullState", pullState);
		}
		int cooperationMode = DataFormat.getIntKey(inParam,"cooperationMode");
		if(cooperationMode > 0){
			sb.append(" AND p.COOPERATION_MODE = :cooperationMode ");
			map.put("cooperationMode", cooperationMode);
		}
		String jobNumber = DataFormat.getStringKey(inParam, "jobNumber");
		if (StringUtils.isNotEmpty(jobNumber)) {
			sb.append(" AND p.JOB_NUMBER like :jobNumber");
			map.put("jobNumber", "%"+jobNumber+"%");
		}
		sb.append(" order by c.user_id desc ");
		Query query = session.createSQLQuery(sb.toString());
		query.setProperties(map);
		return query;
	}
	
	/**
	 * 审核拉包工
	 */
	public String verifyCmUserInfoPull(long userId,int pullState,String remark){
		Session session = SysContexts.getEntityManager();
		Query query = session.createSQLQuery("update cm_user_info_pull c set c.pull_state = :pullState,c.REMARK = :remark where c.user_id = :userId");
		query.setParameter("pullState", pullState).setParameter("remark", remark).setParameter("userId", userId);
		int i = 0;
		try{
			i = query.executeUpdate();
		}catch(Exception e){
			throw new BusinessException("审核失败！");
		}
		return i > 0 ? "Y" : "N";
	}
	
	/**
	 * 查询租户下拉包工
	 * @param loginAcct
	 * @param tenantId
	 * @return
	 */
	public Query getCmUserInfoPullByBill(String loginAcct,long tenantId,long userId,long accountId){
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer(" select  c.user_id, c.LOGIN_ACCT, c.USER_NAME, p.PAYMENT_TYPE, p.BANK_CARD, ");
		sb.append("  IF (p.PAYMENT_TYPE = 1, p.BANK_HOLDER, p.ACCOUNT_NAME) AS NAME, p.ACCOUNT_NUM, ");
		sb.append("  (SELECT SUM(w.AMOUNT) FROM ac_my_wallet w WHERE w.user_id = c.USER_ID AND w.AMOUNT_STATE = 1), ");
		sb.append("  (SELECT SUM(w.AMOUNT) FROM ac_my_wallet w WHERE w.user_id = c.USER_ID AND w.AMOUNT_STATE = 2), ");
		sb.append("  (SELECT SUM(w.AMOUNT) FROM ac_my_wallet w WHERE w.user_id = c.USER_ID AND w.AMOUNT_STATE = 3)  ");
		if(accountId > 0){
			sb.append(" ,a.AUDIT_REMARK ");
		}
		sb.append(" from ac_wallet_rel r LEFT JOIN cm_user_info c ON c.USER_ID = r.USER_ID  ");
		sb.append(" LEFT JOIN ac_payment_method p ON p.USER_ID = r.USER_ID  ");
		if(accountId > 0){
			sb.append(" LEFT JOIN ac_account_wallet a ON a.USER_ID = r.USER_ID  ");
		}
		sb.append(" where r.tenant_id = :tenantId and c.STATE = :state and (c.user_type = :userType or c.user_type = :userTypeTwo) ");
		if(userId > 0){
			sb.append(" and r.user_id =:userId ");
		}
		if(StringUtils.isNotEmpty(loginAcct)){
			sb.append("  and c.login_Acct = :loginAcct ");
		}
		if(accountId > 0){
			sb.append("  and a.id = :accountId ");
		}
		sb.append("  order by r.id desc ");
		Query query = session.createSQLQuery(sb.toString());
		if(userId > 0){
			query.setParameter("userId", userId);
		}
		query.setParameter("tenantId", tenantId);
		if(StringUtils.isNotEmpty(loginAcct)){
			query.setParameter("loginAcct", loginAcct);
		}
		if(accountId > 0){
			query.setParameter("accountId", accountId);
		}
		query.setParameter("state", SysStaticDataEnumYunQi.STS.VALID);
		query.setParameter("userType", SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL);
		query.setParameter("userTypeTwo", SysStaticDataEnumYunQi.USER_TYPE_YUNQI.VIRTUAL_USER);
		return query;
	}
	

	/**
	 * 检验工号的唯一
	 * true 已经存在
	 * false 不存在
	 */
	public boolean checkJobNumber(String jobNumber,long userId){
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer(" select * from cm_user_info c,cm_user_info_pull p where c.USER_ID=p.USER_ID and c.user_type = :userType and p.job_number = :jobNumber and c.state = :state  ");
		if(userId > 0){
			sb.append(" and c.user_id <> :userId ");
		}
		Query query = session.createSQLQuery(sb.toString());
		query.setParameter("jobNumber", jobNumber);
		query.setParameter("userType", SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL);
		query.setParameter("state", SysStaticDataEnumYunQi.STS.VALID);
		if(userId > 0){
			query.setParameter("userId", userId);
		}
		List<Object[]> list = query.list();
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 删除用户
	 * @param userId
	 */
	public void delCmUserInfoPull(long userId){
		Session session  = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		CmUserInfo cmUserInfo = (CmUserInfo) session.get(CmUserInfo.class, userId);
		if(cmUserInfo != null && cmUserInfo.getState() != SysStaticDataEnumYunQi.STS.NULLITY){
			cmUserInfo.setState(SysStaticDataEnumYunQi.STS.NULLITY);
			cmUserInfo.setOpId(user.getUserId());
		}else{
			throw new BusinessException("该用户编号有误！");
		}
	}
}
