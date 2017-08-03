package com.wo56.common.cache;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.AbstractCache;
import com.framework.core.cache.CacheFactory;
import com.wo56.business.sys.vo.SysRoleGrant;
import com.wo56.common.consts.PermissionConsts;

public class SysRoleGrantCache extends AbstractCache {
	@Override
	public void loadDate() throws Exception {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(SysRoleGrant.class);
		ca.add(Restrictions.eq(PermissionConsts.GrantConstant.STATE, PermissionConsts.GrantConstant.STATE_1));
		List<SysRoleGrant> list = ca.list();
		for(SysRoleGrant sysRoleGrant: list){
			String key=PermissionConsts.GrantConstant.SYS_ROLE_GRANT_CACHE_KEY+sysRoleGrant.getRoleId();
			if(CacheFactory.get(getClass().getName(), key) ==null){
				List<SysRoleGrant> valueList = new ArrayList();
				valueList.add(sysRoleGrant);
				CacheFactory.put(SysRoleGrantCache.class.getName(), key, valueList);
			}else{
				List<SysRoleGrant> valueList = (List<SysRoleGrant>) CacheFactory.get(getClass().getName(), key);
				valueList.add(sysRoleGrant);
			}
		}
	}
}
