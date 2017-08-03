package com.wo56.business.route.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.multitab.CommonCol;
import com.framework.core.multitab.CommonVO;
import com.framework.core.util.DateUtil;
import com.framework.core.util.IPage;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.route.vo.RouteAreaRelCfg;
import com.wo56.business.route.vo.out.RouteAreaRelCfgOut;
import com.wo56.common.utils.CommonUtil;
public class RouteAreaRelCfgSV {
	
	/**
	 * 分页查询route_area_rel_cfg
	 * @param relName
	 * @param orgId
	 * @param provinceId
	 * @param cityId
	 * @param countyId
	 * @return
	 * @throws Exception
	 */
	public IPage getRouteAreaRelCfg(String relName,long destOrgId,long provinceId,long cityId,long countyId,long orgId)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		Map queryMap = new HashMap();
		String sql ="SELECT r.reL_id,r.rel_name,r.org_id," +
				"(SELECT org_name FROM organization o WHERE o.org_id=r.org_id) org_name ," +
				"r.province_id,r.city_id,r.county_id," +
				"(SELECT org_name FROM organization o WHERE o.org_id=r.dest_org_id) dest_org_Name," +
				"r.dest_org_id,r.op_id,r.op_name,r.op_date,r.is_Sea_Transport "+
				"FROM route_area_rel_cfg r " +
				"WHERE 1=1 ";
		if(relName!=null && !relName.equals("")){
			sql+="AND r.rel_name like :relName ";
			queryMap.put("relName", "%"+relName+"%");
		}
		if(destOrgId>0){
			sql+="AND r.dest_org_id = :destOrgId ";
			queryMap.put("destOrgId", destOrgId);
		}
		if(provinceId>0){
			sql+="AND r.province_id = :provinceId ";
			queryMap.put("provinceId", provinceId);
		}
		if(cityId>0){
			sql+="AND r.city_id = :cityId ";
			queryMap.put("cityId", cityId);
		}
		if(countyId>0){
			sql+="AND r.county_id = :countyId ";
			queryMap.put("countyId", countyId);
		}
		if(orgId>0){
			sql+="AND r.ORG_ID = :orgId ";
			queryMap.put("orgId", orgId);
		}
		sql+="ORDER BY op_date DESC ";
		SQLQuery query = session.createSQLQuery(sql.toString());
		Iterator i = queryMap.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry entry=(Map.Entry)i.next();
			query.setParameter(entry.getKey()+"", entry.getValue());
		}
		IPage p = PageUtil.gridPage(query);
		List list = p.getThisPageElements();
		for (int j = 0; null != list && j < list.size(); j++) {
			Object[] obj = (Object[]) list.get(j);
			CommonVO commonVO = new CommonVO();
			commonVO.addColunm(new CommonCol("reLId", obj[0]));
			commonVO.addColunm(new CommonCol("relName", obj[1]));
			commonVO.addColunm(new CommonCol("orgId", obj[2]));
			commonVO.addColunm(new CommonCol("orgName", obj[3]));
			commonVO.addColunm(new CommonCol("provinceId", obj[4]));
			commonVO.addColunm(new CommonCol("cityId", obj[5]));
			commonVO.addColunm(new CommonCol("countyId", obj[6]));
			if(obj[6]!=null&&!"".equals(obj[6])){
				commonVO.addColunm(new CommonCol("countyName", SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", obj[6]+"")!=null?SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", obj[6]+"").getName():""));
			}
			commonVO.addColunm(new CommonCol("destOrgName", obj[7]));
			commonVO.addColunm(new CommonCol("destOrgId", obj[8]));
			commonVO.addColunm(new CommonCol("opId", obj[9]));
			commonVO.addColunm(new CommonCol("opName", obj[10]));
			commonVO.addColunm(new CommonCol("opDate", DateUtil.formatDate(DateUtil.convertStrToTimestamp(obj[11]+""), DateUtil.DATETIME_FORMAT)));
			if(obj[6] != null){
				commonVO.addColunm(new CommonCol("proCityCount", CommonUtil.getCityName(Long.parseLong(obj[4]+""), Long.parseLong(obj[5]+""), Long.parseLong(obj[6]+""))));
			}else{
				commonVO.addColunm(new CommonCol("proCityCount", CommonUtil.getCityName(Long.parseLong(obj[4]+""), Long.parseLong(obj[5]+""), null)));
			}
			commonVO.addColunm(new CommonCol("cityName", SysStaticDataUtil.getCityDataList("SYS_CITY", obj[5]+"").getName()));
			commonVO.addColunm(new CommonCol("isSeaTransport","1".equals(String.valueOf(obj[12])) ? "是" : "否"));
			list.set(j, commonVO);
		}
		return p;
	}
	
	/**
	 * 根据主键查询route_area_rel_cfg
	 * @param relId
	 * @return
	 * @throws Exception
	 */
	public RouteAreaRelCfg getRouteAreaRelCfg (long relId)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		return (RouteAreaRelCfg)session.get(RouteAreaRelCfg.class, relId);
	}
	
	/**
	 * 新增、修改route_area_rel_cfg
	 * @param routeAreaRelCfg
	 * @throws Exception
	 */
	public void saveRouteAreaRelCfg(RouteAreaRelCfg routeAreaRelCfg)throws Exception{
		Session session = SysContexts.getEntityManager();
		session.saveOrUpdate(routeAreaRelCfg);
	}
	
	/**
	 * 批量新增、修改
	 * @param routeAreaRelCfgList
	 * @throws Exception
	 */
	public void batchSave(List<RouteAreaRelCfg> routeAreaRelCfgList)throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager();
		if(routeAreaRelCfgList!=null && routeAreaRelCfgList.size()>0){
			for(int i=0;i<routeAreaRelCfgList.size();i++){
				RouteAreaRelCfg routeAreaRelCfg = routeAreaRelCfgList.get(i);
				session.saveOrUpdate(routeAreaRelCfg);
				if (i % 10 == 0) {
                    session.flush();  
                    session.clear();  
                }  
			}
		}
	}
	
	/**
	 * 批量删除
	 * @param relIdList
	 * @throws Exception
	 */
	public void batchDelet(List relIdList)throws Exception{
		Session session = SysContexts.getEntityManager();
		Map queryMap = new HashMap();
		String hql = "";
		for(int i=0;i<relIdList.size();i++) {
            if(i==0) {
                hql = " REL_ID=:relId"+i;
                queryMap.put("relId"+i, Long.parseLong(relIdList.get(i)+""));
            } else {
                hql += " or REL_ID=:relId"+i;
                queryMap.put("relId"+i, Long.parseLong(relIdList.get(i)+""));
            }
        }   
		SQLQuery query= session.createSQLQuery("delete from route_area_rel_cfg where "+hql);
        Iterator i = queryMap.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry entry=(Map.Entry)i.next();
			query.setParameter(entry.getKey()+"", entry.getValue());
		}
		query.executeUpdate();
	}
	
	/**
	 * 根据组织和区查询
	 * @param orgId
	 * @param destOrgIdList
	 * @return
	 * @throws Exception
	 */
	public List<RouteAreaRelCfg> getRouteAreaRelCfg(long orgId,List destOrgIdList,List cityList,int isSeaTransport)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		Map queryMap = new HashMap();
		String sql = "FROM  RouteAreaRelCfg WHERE orgId =:orgId ";
		queryMap.put("orgId", orgId);
		if(isSeaTransport == 1){
			sql +=" AND isSeaTransport = :isSeaTransport";
		}else{
			sql +=" AND (isSeaTransport = :isSeaTransport or isSeaTransport is null)";
		}
		queryMap.put("isSeaTransport", isSeaTransport);
		String whereSql = " AND ( ";
		if(destOrgIdList.size()>0){
			for(int i=0;i<destOrgIdList.size();i++){
				if(i==0){
					whereSql+=" ( countyId=:countyId"+i+" ) ";
					queryMap.put("countyId"+i, destOrgIdList.get(i));
				}else{
					whereSql+=" OR (countyId=:countyId"+i +" ) ";
					queryMap.put("countyId"+i, destOrgIdList.get(i));
				}
			}
		}
		if(destOrgIdList.size()>0 && cityList.size()>0){
			whereSql += " OR  ";
		}
		if(cityList.size()>0){
			for(int j=0;j<cityList.size();j++){
				if(j==0){
					whereSql += " ( cityId=:cityId"+j + " and  countyId is null )";
					queryMap.put("cityId"+j, cityList.get(j));
				}else{
					whereSql += " OR (cityId=:cityId"+j + " and  countyId is null )";
					queryMap.put("cityId"+j, cityList.get(j));
				}
			}
		}
		whereSql+=" ) ";
		sql = sql + whereSql;
		Query query = session.createQuery(sql);
		Iterator i = queryMap.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry entry=(Map.Entry)i.next();
			query.setParameter(entry.getKey()+"", entry.getValue());
		}
		List<RouteAreaRelCfg> list = query.list();
		return list;
	}
	
	
	
	/**
	 * 分页查询route_area_rel_cfg
	 * @param relName
	 * @param orgId
	 * @param provinceId
	 * @param cityId
	 * @param countyId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRouteAreaRelCfgList(String relName,long destOrgId,long provinceId,long cityId,long countyId,long orgId,int isSeaTransport)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		Map queryMap = new HashMap();
		String sql ="SELECT r.reL_id,r.rel_name,r.org_id," +
				"(SELECT org_name FROM organization o WHERE o.org_id=r.org_id) org_name ," +
				"r.province_id,r.city_id,r.county_id," +
				"(SELECT org_name FROM organization o WHERE o.org_id=r.dest_org_id) dest_org_Name," +
				"r.dest_org_id,r.op_id,r.op_name,r.op_date "+
				"FROM route_area_rel_cfg r " +
				"WHERE 1=1 ";
		if(relName!=null && !relName.equals("")){
			sql+="AND r.rel_name like :relName ";
			queryMap.put("relName", "%"+relName+"%");
		}
		if(destOrgId>0){
			sql+="AND r.dest_org_id = :destOrgId ";
			queryMap.put("destOrgId", destOrgId);
		}
		if(provinceId>0){
			sql+="AND r.province_id = :provinceId ";
			queryMap.put("provinceId", provinceId);
		}
		if(cityId>0){
			sql+="AND r.city_id = :cityId ";
			queryMap.put("cityId", cityId);
		}else{
			sql+="AND r.city_id is null ";
		}
		
		if(countyId>0){
			sql+="AND r.county_id = :countyId ";
			queryMap.put("countyId", countyId);
		}else{
			sql+="AND r.county_id is null ";
		}
		if(orgId>0){
			sql+="AND r.ORG_ID = :orgId ";
			queryMap.put("orgId", orgId);
		}
		if(isSeaTransport >= 0){
			if(isSeaTransport == 1){
				sql+="AND r.IS_SEA_TRANSPORT = :isSeaTransport ";
			}else if(isSeaTransport == 0){
				sql+="AND (r.IS_SEA_TRANSPORT = :isSeaTransport or r.IS_SEA_TRANSPORT is null) ";
			}
			queryMap.put("isSeaTransport", isSeaTransport);
		}
		sql+="ORDER BY op_date DESC ";
		SQLQuery query = session.createSQLQuery(sql.toString());
		Iterator i = queryMap.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry entry=(Map.Entry)i.next();
			query.setParameter(entry.getKey()+"", entry.getValue());
		}
		List list = query.list();
		List<Map> retList=new ArrayList<Map>();
		for (int j = 0; null != list && j < list.size(); j++) {
			Object[] obj = (Object[]) list.get(j);
			Map<String,Object> retMap=new HashMap<String, Object>();
			CommonVO commonVO = new CommonVO();
			retMap.put("reLId", obj[0]);
			retMap.put("relName", obj[1]);
			retMap.put("orgId", obj[2]);
			retMap.put("orgName", obj[3]);
			retMap.put("provinceId", obj[4]);
			retMap.put("cityId", obj[5]);
			retMap.put("countyId", obj[6]);
			if(obj[6]!=null&&!"".equals(obj[6])){
				retMap.put("countyName", SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", obj[6]+"")!=null?SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", obj[6]+"").getName():"");
			}
			retMap.put("destOrgName", obj[7]);
			retMap.put("destOrgId", obj[8]);
			retMap.put("opId", obj[9]);
			retMap.put("opName", obj[10]);
			retMap.put("opDate", DateUtil.formatDate(DateUtil.convertStrToTimestamp(obj[11]+""), DateUtil.DATETIME_FORMAT));
			if(obj[6] != null){
				retMap.put("proCityCount", CommonUtil.getCityName(Long.parseLong(obj[4]+""), Long.parseLong(obj[5]+""), Long.parseLong(obj[6]+"")));
			}else{
				retMap.put("proCityCount", CommonUtil.getCityName(Long.parseLong(obj[4]+""), Long.parseLong(obj[5]+""), null));
			}
			retMap.put("cityName", SysStaticDataUtil.getCityDataList("SYS_CITY", obj[5]+"").getName());
			retList.add(retMap);
		}
		return retList;
	}
	
	
	public RouteAreaRelCfgOut getRouteAreaRel(long destOrgId,long provinceId,long cityId,long countyId,long orgId,int isSeaTransport)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer("SELECT r.dest_org_id,r.county_Id from route_area_rel_cfg r where 1=1 ");
		if(provinceId>0){
			sb.append(" AND r.province_id = :provinceId  ");
		}
		if(cityId>0){
			sb.append(" AND r.city_id = :cityId ");
		}else{
			sb.append("AND r.city_id is null ");
		}
		if(countyId>0){
			sb.append("AND r.county_id = :countyId ");
		}else{
			sb.append("AND r.county_id is null ");
		}
		if(orgId>0){
			sb.append(" AND r.ORG_ID = :orgId ");
		}
		if(isSeaTransport >= 0){
			if(isSeaTransport == 1){
				sb.append("AND r.IS_SEA_TRANSPORT = :isSeaTransport ");
			}else if(isSeaTransport == 0){
				sb.append(" AND (r.IS_SEA_TRANSPORT = :isSeaTransport or r.IS_SEA_TRANSPORT is null) ");
			}
		}
		sb.append(" ORDER BY op_date DESC ");
		SQLQuery query = session.createSQLQuery(sb.toString());
		if(provinceId>0){
			query.setParameter("provinceId", provinceId);
		}
		if(cityId>0){
			query.setParameter("cityId", cityId);
		}
		if(countyId>0){
			query.setParameter("countyId", countyId);
		}
		if(orgId>0){
			query.setParameter("orgId", orgId);
		}
		if(isSeaTransport >= 0){
			query.setParameter("isSeaTransport", isSeaTransport);
		}
		RouteAreaRelCfgOut out = new RouteAreaRelCfgOut();
		List<Object[]> list = query.list();
		if(list !=null && list.size() == 1){
			for (Object[] obj : list) {
				if(obj[1]!=null){
				out.setCountyId(Long.valueOf(String.valueOf(obj[1])));
				}
				if(obj[0]!=null){
				out.setDestOrgId(Long.valueOf(String.valueOf(obj[0])));
				}
			}
		}
		return out;
	}
	
}
