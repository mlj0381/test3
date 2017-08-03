package com.wo56.common.cache;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.AbstractCache;
import com.framework.core.cache.CacheFactory;
import com.wo56.business.cm.vo.CmOrgRel;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.consts.SysStaticDataEnumYunQi;

/**
 * 
 * @author liyiye
 *
 */
public class CmOrgRelCache extends AbstractCache {

	@Override
	public void loadDate() {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmOrgRel.class);
		ca.add(Restrictions.eq("state", 1));
		List<CmOrgRel> list = ca.list();
		if(list!=null && list.size()>0){
			for(CmOrgRel cmOrgRel:list){
				Long tenantId=cmOrgRel.getLineTenantId();//专线的租户
				Long busiTenantId= cmOrgRel.getBusiTenantId();//拉包公司的租户
				CacheFactory.put(CmOrgRelCache.class.getName(), SysStaticDataEnumYunQi.CACHE.CM_ORG_REL+tenantId+"_"+busiTenantId, cmOrgRel);
				String key=SysStaticDataEnumYunQi.CACHE.CM_ORG_REL+tenantId;
				if(CacheFactory.get(this.getClass().getName(), key)==null){
					List<CmOrgRel> valueList = new ArrayList<CmOrgRel>();
					valueList.add(cmOrgRel);
					CacheFactory.put(this.getClass().getName(), key, valueList);
				}else{
					List<CmOrgRel> valueList = (List)CacheFactory.get(this.getClass().getName(), key);
					valueList.add(cmOrgRel);
				}
			
			}
			
		}
	}

}
