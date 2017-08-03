package com.wo56.business.ac.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.wo56.business.ac.vo.AcAccountWallet;
import com.wo56.business.ac.vo.AcMyWallet;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;

public class AcAccountWalletSV {
	/**
	 * 查询列表
	 * @param tenantId
	 * @return
	 */
	public Query queryAcAccoutnWallet(long tenantId,int amountState){
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery("select a.ID,a.ACCOUNT_NUM,a.AMOUNT,a.SHOW_TIME,a.APPLY_TIME,a.PAYMENT_TYPE from ac_account_wallet a  where a.AMOUNT_STATE = :amountState AND a.user_id = :userId AND a.tenant_id = :tenantId AND a.amount_Type = :amountType AND a.state = :state order by  a.id desc ");
		query.setParameter("amountState", amountState);
		query.setParameter("userId", SysContexts.getCurrentOperator().getUserId());
		query.setParameter("tenantId", tenantId);
		query.setParameter("amountType", SysStaticDataEnumYunQi.ACCOUNT_TYPE.INCOME);
		query.setParameter("state", SysStaticDataEnumYunQi.STS.VALID);
		return query;
	}
	/**
	 * id查询流水
	 * @param id
	 * @return
	 */
	public AcAccountWallet getAcMyWalletById(long id){
		Session session = SysContexts.getEntityManager(true);
		AcAccountWallet ac = (AcAccountWallet) session.get(AcAccountWallet.class, id);
		return ac;
	}
	public AcAccountWallet getAcMyWalletByAccountNum(String accountNum){
		
		Session session  = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(AcAccountWallet.class);
		ca.add(Restrictions.eq("accountNum", accountNum));
		List<AcAccountWallet> list = ca.list();
		if(list!=null && list.size()==1){
			return list.get(0);
		}
		
		return null;
	}
	
	public String applyAcAccoutnWallet(AcAccountWallet wallet){
		Session session = SysContexts.getEntityManager();
		session.saveOrUpdate(wallet);
		return "Y";
	}
	/**
	 * 查询提现总金额
	 * @param tenantId
	 * @return
	 */
	public String applyAmountList(long tenantId,int amountState){
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery("select sum(AMOUNT) from ac_account_wallet where tenant_id = :tenantId and AMOUNT_STATE = :amountState and user_id = :userId ");
		query.setParameter("tenantId", tenantId);
		query.setParameter("amountState", amountState);
		query.setParameter("userId", SysContexts.getCurrentOperator().getUserId());
		Object obj = query.uniqueResult();
		String amount =obj !=null ? String.valueOf(CommonUtil.parseDouble(Long.valueOf(String.valueOf(obj)))) : "0.00";
		return amount;
	}
	/**
	 * 提现管理
	 * @param inParam
	 * @return
	 */
	public Query queryCashTipFee(Map<String,Object> inParam,boolean isSum){
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		
		StringBuffer sb = new StringBuffer(" SELECT ");
		
		if(isSum){
			sb.append(" SUM(a.AMOUNT)");
		}else{
			sb.append(" a.ID,a.ACCOUNT_NUM,c.USER_NAME,c.LOGIN_ACCT,a.AMOUNT,  ");
			sb.append(" a.APPLY_TIME,(select user_name from cm_user_info where user_id = a.APPLY_ID), ");
			sb.append(" a.AUDIT_STATUS,(select user_name from cm_user_info where user_id = a.AUDIT_ID), ");
			sb.append(" a.AUDIT_TIME,a.WRITE_STATE,a.WRITE_TIME,(select user_name from cm_user_info where user_id = a.WRITE_ID),a.AUDIT_REMARK,a.user_id ");
		}
		
		sb.append(" FROM ac_account_wallet a LEFT JOIN cm_user_info c  ON a.USER_ID = c.USER_ID where a.tenant_Id = :tenantId and c.user_type in :userType ");
		Map<String,Object> map = new HashMap<String, Object>();
		//map.put("state", SysStaticDataEnumYunQi.STS.VALID);
		map.put("tenantId", user.getTenantId());
		map.put("userType",  new Integer[]{SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL,SysStaticDataEnumYunQi.USER_TYPE_YUNQI.VIRTUAL_USER});
		String applyBegin = DataFormat.getStringKey(inParam, "applyBegin");
		if(StringUtils.isNotEmpty(applyBegin)){
			sb.append(" and a.APPLY_TIME >= :applyBegin ");
			map.put("applyBegin", applyBegin+" 00:00:00");
		}
		String applyEnd = DataFormat.getStringKey(inParam, "applyEnd");
		if(StringUtils.isNotEmpty(applyEnd)){
			sb.append(" and a.APPLY_TIME <= :applyEnd ");
			map.put("applyEnd", applyEnd+" 23:59:59");
		}
		String writeBegin = DataFormat.getStringKey(inParam, "writeBegin");
		if(StringUtils.isNotEmpty(writeBegin)){
			sb.append(" and a.AUDIT_TIME >= :writeBegin ");
			map.put("writeBegin", writeBegin+" 00:00:00");
		}
		String writeEnd = DataFormat.getStringKey(inParam, "writeEnd");
		if(StringUtils.isNotEmpty(writeEnd)){
			sb.append(" and a.AUDIT_TIME <= :writeEnd ");
			map.put("writeEnd", writeEnd+" 23:59:59");
		}
		int writeState = DataFormat.getIntKey(inParam, "writeState");
		if(writeState > 0){
			sb.append(" and a.WRITE_STATE = :writeState");
			map.put("writeState", writeState);
		}
		int auditStatus = DataFormat.getIntKey(inParam, "auditStatus");
		if(auditStatus > 0){
			sb.append(" and a.audit_Status = :auditStatus");
			map.put("auditStatus", auditStatus);
		}
		String userName = DataFormat.getStringKey(inParam, "userName");
		if(StringUtils.isNotEmpty(userName)){
			sb.append(" and c.user_name like :userName");
			map.put("userName","%"+ userName+"%");
		}
		String loginAcct = DataFormat.getStringKey(inParam, "loginAcct");
		if(StringUtils.isNotEmpty(loginAcct)){
			sb.append(" and c.login_Acct like :loginAcct");
			map.put("loginAcct", "%"+ loginAcct+"%");
		}
		sb.append(" order by a.id desc ");
		Query query = session.createSQLQuery(sb.toString());
		query.setProperties(map);
		return query;
	}
	
	/**
	 * web查询申请提现
	 * @return
	 */
	public Query getAcAccountWalletByPull(long userId){
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer("SELECT  ");
		sb.append(" a.id,c.LOGIN_ACCT,c.USER_NAME,p.PAYMENT_TYPE,p.BANK_CARD, ");
		sb.append(" IF (p.PAYMENT_TYPE = 1,p.BANK_HOLDER,p.ACCOUNT_NAME) AS name,p.ACCOUNT_NUM, ");
		sb.append(" (select SUM(w.AMOUNT) from ac_my_wallet w where w.user_id = a.USER_ID and w.AMOUNT_STATE = 1), ");
		sb.append(" (select SUM(w.AMOUNT) from ac_my_wallet w where w.user_id = a.USER_ID and w.AMOUNT_STATE = 2), ");
		sb.append(" (select SUM(w.AMOUNT) from ac_my_wallet w where w.user_id = a.USER_ID and w.AMOUNT_STATE = 3) ");
		sb.append(" FROM ac_account_wallet a LEFT JOIN cm_user_info c ON c.USER_ID = a.USER_ID LEFT JOIN ac_payment_method p ON a.USER_ID = p.USER_ID where a.tenant_id = tenantId and a.user_id = :userId");
		Query query = session.createSQLQuery(sb.toString());
		query.setParameter("userId", userId);
		return query;
	}
	
	/**
	 * 待提现（公司下的拉包工）
	 * @param userId
	 * @param tenantId
	 * @return
	 */
	public Query getAcMyWalletByUserId(long userId,long tenantId,int amountState,long accountId){
		Session session = SysContexts.getEntityManager(true);
		String sql = "";
		if(accountId > 0){
			sql = " and m.ACCOUNT_ID = :accountId ";
		}
		if(amountState > 0){
			sql += " and m.AMOUNT_STATE = :amountState "; 
		}
		Query query = session.createSQLQuery("select m.id,m.AMOUNT_STATE,o.ORDER_NO,o.TRACKING_NUM,m.AMOUNT from ac_my_wallet m LEFT JOIN ord_orders_info o ON m.ORDER_ID = o.ORDER_ID where m.USER_ID = :userId and m.TENANT_ID = :tenantId  "+sql+" order by m.id desc ");
		query.setParameter("tenantId", tenantId);
		query.setParameter("userId", userId);
		if(accountId > 0){
			query.setParameter("accountId", accountId);
		}
		if(amountState > 0){
			query.setParameter("amountState", amountState);
		}
		return query;
	}
	
	/**
	 * 待提现（公司下的拉包工）
	 * @param userId
	 * @param tenantId
	 * @return
	 */
	public Query getAcMyWalletHisByUserId(long userId,long tenantId,int amountState,long accountId){
		Session session = SysContexts.getEntityManager(true);
		String sql = "";
		if(accountId > 0){
			sql = " and m.ACCOUNT_ID = :accountId ";
		}
		if(amountState > 0){
			sql += " and m.AMOUNT_STATE = :amountState "; 
		}
		Query query = session.createSQLQuery("select m.id,m.AMOUNT_STATE,o.ORDER_NO,o.TRACKING_NUM,m.AMOUNT from ac_my_wallet_his m LEFT JOIN ord_orders_info o ON m.ORDER_ID = o.ORDER_ID where m.USER_ID = :userId and m.TENANT_ID = :tenantId  "+sql+" order by m.id desc ");
		query.setParameter("tenantId", tenantId);
		query.setParameter("userId", userId);
		if(accountId > 0){
			query.setParameter("accountId", accountId);
		}
		if(amountState > 0){
			query.setParameter("amountState", amountState);
		}
		return query;
	}
	
	
	
	public AcAccountWallet getAcAccountWallet(long id){
		Session session  = SysContexts.getEntityManager(true);
		return (AcAccountWallet) session.get(AcAccountWallet.class, id);
	}
	
	public List<AcAccountWallet> getAcAccountWalletList(Long[] ids){
		Session session  = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(AcAccountWallet.class);
		List<AcAccountWallet> list = ca.add(Restrictions.in("id", ids)).list();
		return list;
	}
}
