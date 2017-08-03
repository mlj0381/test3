package com.wo56.common.cache;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.AbstractCache;
import com.framework.core.cache.CacheFactory;
import com.wo56.business.sys.vo.SysEntity;
import com.wo56.common.consts.PermissionConsts;

/**
 * entity实体缓存
 * @author zhouchao
 *
 */
public class SysEntityCache extends AbstractCache {
	@Override
	public void loadDate() throws Exception {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(SysEntity.class);
		ca.add(Restrictions.eq(PermissionConsts.GrantConstant.STATE, PermissionConsts.GrantConstant.STATE_1));
		List<SysEntity> list = ca.list();
		CacheFactory.put(SysEntityCache.class.getName(), PermissionConsts.GrantConstant.SYS_ENTITY_CACHE_KEY, list);
	}
}
