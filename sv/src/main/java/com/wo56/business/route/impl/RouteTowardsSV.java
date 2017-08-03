package com.wo56.business.route.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.wo56.business.route.vo.RouteAreaRelCfg;
import com.wo56.business.route.vo.RouteTowards;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class RouteTowardsSV {
	/**
	 * 开始网点和目的网点查询线路
	 * @param beginOrgId
	 * @param endOrgId
	 * @return
	 */
	public RouteTowards getRouteTowardsByOrg(long beginOrgId,long endOrgId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(RouteTowards.class);
		ca.add(Restrictions.eq("beginOrgId", beginOrgId));
		ca.add(Restrictions.eq("endOrgId", endOrgId));
		List<RouteTowards> list = ca.list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * app查询中转站
	 * @param beginOrgId
	 * @param desCity
	 * @return
	 */
	public List<Map<String, Object>> getRouteTowardsByDesCity(long tenantId,long desCity,long desProvince,long desRegion,long orgId){
		Session session = SysContexts.getEntityManager(true);
		StringBuffer hql = new StringBuffer();
		hql.append(" select c.dest_Org_Id from Route_Area_Rel_Cfg c inner join Route_Towards t ON c.dest_Org_Id = t.end_Org_Id where 1=1 ");
		if (desRegion  > 0) {
			hql.append(" and c.county_Id = :countyId ");
		}else{
			hql.append(" and c.county_Id is NULL ");
		}
		hql.append(" and c.province_Id = :provinceId ");
		hql.append(" and c.tenant_Id = :tenantId ");
		hql.append(" and c.city_Id = :cityId ");
		hql.append(" and t.begin_Org_Id = :beginOrgId ");
		Query query = session.createSQLQuery(hql.toString());
		if (desRegion  > 0) {
			query.setParameter("countyId", desRegion);
		}
		query.setParameter("cityId", desCity);
		query.setParameter("provinceId", desProvince);
		query.setParameter("tenantId", tenantId);
		query.setParameter("beginOrgId", orgId);
		List<Object> list = query.list();
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		if(list != null && list.size() > 0){
			map.put("arrivedOrgId", list.get(0));
			map.put("arrivedOrgName", OraganizationCacheDataUtil.getOrgName(Long.valueOf(list.get(0).toString())));
			listMap.add(map);
		}
		return listMap;
	}
}
