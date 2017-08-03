package com.wo56.common.cache;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.AbstractCache;
import com.framework.core.cache.CacheFactory;
import com.wo56.business.sys.vo.SysEntity;
import com.wo56.business.sys.vo.SysObjectGrant;
import com.wo56.common.consts.PermissionConsts;

public class SysObjectGrantCache extends AbstractCache {

	@Override
	public void loadDate() throws Exception {
		// TODO Auto-generated method stub
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(SysObjectGrant.class);
		ca.add(Restrictions.eq(PermissionConsts.GrantConstant.STATE, PermissionConsts.GrantConstant.STATE_1));
		List<SysEntity> list = ca.list();
		CacheFactory.put(SysObjectGrantCache.class.getName(), "sysObjectGrantCache", list);
	}

}
