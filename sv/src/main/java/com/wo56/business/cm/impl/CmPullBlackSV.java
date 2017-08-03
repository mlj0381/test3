package com.wo56.business.cm.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.wo56.business.cm.vo.CmPullBlack;

public class CmPullBlackSV {
	/**
	 * 保存拉黑
	 * @param black
	 * @return
	 */
	public String doSavePull(CmPullBlack black){
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager();
		black.setTargetId(user.getUserId());
		black.setBusinessId(user.getTenantId());
		black.setOpDate(new Date());
		black.setOpId(user.getUserId());
		session.saveOrUpdate(black);
		return "Y";
	}
	/**
	 * 同过用户查询拉黑信息
	 * @param userId
	 * @return
	 */
	public CmPullBlack getCmPullBlackByUserId(long userId){
		Session session = SysContexts.getEntityManager(true);
		List<CmPullBlack> list = session.createCriteria(CmPullBlack.class).add(Restrictions.eq("userId", userId)).list();
		if(list != null && list.size() == 1){
			return list.get(0);
		}
		return null;
	}
}
