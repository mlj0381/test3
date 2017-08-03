package com.wo56.common.cache;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.AbstractCache;
import com.framework.core.cache.CacheFactory;
import com.wo56.business.sys.vo.SysTenantDef;
import com.wo56.common.consts.PermissionConsts;
import com.wo56.common.consts.SysStaticDataEnum;

public class SysTenantDefCache extends AbstractCache{

	@Override
	public void loadDate() throws Exception {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria( SysTenantDef.class);
		ca.add(Restrictions.eq("status", SysStaticDataEnum.STS.VALID));
		List<SysTenantDef> dataList = ca.list();
		for(SysTenantDef sysTenantDef:dataList){
			if(CacheFactory.get(this.getClass().getName(), PermissionConsts.GrantConstant.SYS_TENANT_DEF )==null){
				List<SysTenantDef> valueList = new ArrayList<SysTenantDef>();
				valueList.add(sysTenantDef);
				CacheFactory.put(this.getClass().getName(), PermissionConsts.GrantConstant.SYS_TENANT_DEF , valueList);
			}else{
				List<SysTenantDef> valueList = (List)CacheFactory.get(this.getClass().getName(), PermissionConsts.GrantConstant.SYS_TENANT_DEF );
				valueList.add(sysTenantDef);
			}
		} 
	}

}
