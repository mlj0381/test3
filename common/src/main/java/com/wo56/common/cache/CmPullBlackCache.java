package com.wo56.common.cache;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.AbstractCache;
import com.framework.core.cache.CacheFactory;
import com.wo56.business.cm.vo.CmPullBlack;
import com.wo56.common.consts.SysStaticDataEnum;

/**
 * 
 * @author liyiye
 *
 */
public class CmPullBlackCache extends AbstractCache {

	@Override
	public void loadDate() {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmPullBlack.class);
		ca.add(Restrictions.eq("status", 1));
		List<CmPullBlack> list = ca.list();
		if(list!=null){
			for(CmPullBlack cmPullBlack:list){
				Integer type=cmPullBlack.getType();
				Long userId=cmPullBlack.getTargetId();
				Long businessId=cmPullBlack.getBusinessId();
				if(type!=null){
					
					if(type.intValue()==SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_CUSTOMER){
						CacheFactory.put(CmPullBlackCache.class.getName(), SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_CUSTOMER+"_"+businessId+"_"+userId, cmPullBlack);
					}else if(type.intValue()==SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_SPECIAL){
						CacheFactory.put(CmPullBlackCache.class.getName(), SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_SPECIAL+"_"+businessId+"_"+userId, cmPullBlack);
					}else if(type.intValue()==SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_PULLPAGCOMPANY){
						CacheFactory.put(CmPullBlackCache.class.getName(), SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_PULLPAGCOMPANY+"_"+userId, cmPullBlack);
					}else if(type.intValue()==SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_PLATFORM){
						CacheFactory.put(CmPullBlackCache.class.getName(), SysStaticDataEnum.PULL_BLACK_TYPE_YQ.PULL_BLACK_TYPE_PLATFORM+"_"+userId, cmPullBlack);
					}
					
				}
			}
			
		}
	}

}
