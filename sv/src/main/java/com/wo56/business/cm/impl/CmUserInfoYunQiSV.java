package com.wo56.business.cm.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;

public class CmUserInfoYunQiSV {
	/**
	 * 获取个人中心
	 * @param userId
	 * @return
	 */
	public Query getCmUserInfo(long userId){
		if(userId < 0){
			throw new BusinessException("请选择用户！");
		}
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer(" select u.USER_ID,u.USER_TYPE,u.LOGIN_ACCT,u.USER_NAME,u.USER_PIC,p.PULL_STATE,p.FRONT_ID_CARD,p.BACK_ID_CARD,p.job_number,p.city,p.PULL_WORK ");
		sb.append(" from cm_user_info u LEFT JOIN cm_user_info_pull p ON u.USER_ID = p.USER_ID where u.user_id = :userId ");
		return session.createSQLQuery(sb.toString()).setParameter("userId", userId);
	}
	
	public Criteria getCmUserInfoBill(String billId){
		Session session = SysContexts.getEntityManager(true);
		return session.createCriteria(CmUserInfo.class).add(Restrictions.eq("loginAcct", billId)).add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
	}
	
	/**
	 * 修改数据
	 * @param user
	 * @return
	 */
	public String updateCmUserInfo(CmUserInfo user){
		Session session = SysContexts.getEntityManager();
		session.update(user);
		return "Y";
	}
	/**
	 * 手机号码查询用户
	 */
	public CmUserInfo getCmUserInfoByPhone(String billId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmUserInfo.class);
		List<CmUserInfo> list = ca.add(Restrictions.eq("loginAcct", billId)).add(Restrictions.eq("state", SysStaticDataEnumYunQi.STS.VALID)).list();
		if(list != null && list.size() == 1){
			return list.get(0);
		}
		return null;
	}
	
	public String doSaveOrUpdate(CmUserInfo user){
		Session session = SysContexts.getEntityManager();
		session.saveOrUpdate(user);
		return "Y";
	}
	
	/**
	 * 失效用户
	 */
	public String loseCmUserInfo(long userId){
		Session session = SysContexts.getEntityManager();
		Query query = session.createSQLQuery("update cm_user_info c  set c.STATE = :state where c.USER_ID =:userId");
		query.setParameter("state", SysStaticDataEnumYunQi.STS.NULLITY);
		query.setParameter("userId", userId);
		int i = 0;
		try{
			i = query.executeUpdate();
		}catch(Exception e){
			throw new BusinessException("删除失败！");
		}
		
		return i > 0 ? "Y" : "N";
	}
	
	public CmUserInfo getCmUserInfoByUserId(long userId){
		Session session = SysContexts.getEntityManager(true);
		return (CmUserInfo) session.get(CmUserInfo.class, userId);
	}
	/**
	 * 手机号获取用户
	 * @param userType
	 * @param bill
	 * @return
	 */
	public List<CmUserInfo> getCmUserInfoList(int userType,String bill){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmUserInfo.class);
		ca.add(Restrictions.eq("userType", userType));
		ca.add(Restrictions.eq("loginAcct", bill));
		ca.add(Restrictions.eq("state", SysStaticDataEnumYunQi.STS.VALID));
		return ca.list();
	}
}
