package com.wo56.business.cm.intf;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.components.websocket.thrift.WebPushService.Iface;
import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.wo56.business.cm.impl.OrganizationYunQiSV;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserOrgRel;
import com.wo56.business.cm.vo.out.OrganzationYunQiOut;
import com.wo56.business.ord.intf.IOrganizationYunQiIntf;
import com.wo56.business.ord.vo.OrderFeeRule;
import com.wo56.business.ord.vo.OrderTipRuleExt;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.route.impl.GraphRouteSV;
import com.wo56.business.route.vo.RouteAreaRelCfg;
import com.wo56.business.route.vo.RouteRouting;
import com.wo56.business.route.vo.RouteTowards;
import com.wo56.business.route.vo.RouteTowardsDtl;
import com.wo56.common.consts.SysStaticDataEnumYunQi;

public class OrganizationYunQiTF implements IOrganizationYunQiIntf{
	/**
	 * 拉包工获取专线或公司 -- 云启
	 * @param tenantType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getTenantByOrg(long tenantType){
		/*if(tenantType <0){
			throw new BusinessException("请选择公司或专线类型！");
		}*/
		OrganizationYunQiSV orgSV = (OrganizationYunQiSV) SysContexts.getBean("organizationYunQiSV");
		Query query = orgSV.getTenantByOrg(tenantType);
		List<Object[]> list = query.list();
		List<OrganzationYunQiOut> listOut = new ArrayList<OrganzationYunQiOut>();
		if(list != null && list.size() > 0){
			for (Object[] objects : list) {
				OrganzationYunQiOut out = new OrganzationYunQiOut();
				out.setTenantId(objects[0] != null ? String.valueOf(objects[0]) : "");
				out.setName( objects[1] != null ? String.valueOf(objects[1]) : "");
				out.setTenantType( objects[2] != null ? String.valueOf(objects[2]) : "");
				listOut.add(out);
			}
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(listOut));
	}
	/**
	 * 查询所有专线
	 * @return
	 */
	public List<Map<String,String>> selectOrgByLink(Map<String,Object> inParam){
		List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
		OrganizationYunQiSV orgSV = (OrganizationYunQiSV) SysContexts.getBean("organizationYunQiSV");
		Query query = orgSV.selectOrgByLink();
		List<Object[]> list = query.list();
		if(list != null && list.size() > 0){
			for(Object[] temp : list){
				Map<String,String> map = new HashMap<String, String>();
				map.put("tenantId", temp[0]+"");
				map.put("orgName", temp[1]+"");
				listMap.add(map);
			}
		}
		return listMap;
	}
	/**
	 * 删除网点
	 * @param inParam
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String delOrganization(Map<String,Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager();
		long orgId = DataFormat.getLongKey(inParam, "orgId");
		long tenantId = SysContexts.getCurrentOperator().getTenantId();
		if (orgId < 0) {
			throw new BusinessException("请传入网点id");
		}
		//查看网点的与用户的关系
		Criteria ca  = session.createCriteria(CmUserOrgRel.class);
		List<CmUserOrgRel> list = ca.add(Restrictions.eq("orgId", orgId)).list();
		List<Long> userIdList = new ArrayList<Long>();
		if (list != null && list.size() > 0) {
			for (CmUserOrgRel cmUserOrgRel : list) {
				userIdList.add(cmUserOrgRel.getUserId());
			}
		}
		//失效用户
		if (userIdList != null && userIdList.size() > 0) {
			Criteria caCmUserInfo = session.createCriteria(CmUserInfo.class);
			List<CmUserInfo> userList = caCmUserInfo.add(Restrictions.in("userId", userIdList)).add(Restrictions.eq("state", SysStaticDataEnumYunQi.STS.VALID)).list();
			if (userList != null && userList.size() > 0) {
				for (CmUserInfo cmUserInfo : userList) {
					cmUserInfo.setState(SysStaticDataEnumYunQi.STS.NULLITY);
					session.update(cmUserInfo);
				}
			}
		}
		//失效线路
		List<Long> towardsId = new ArrayList<Long>();
		List<Long> routingId = new ArrayList<Long>();
		Criteria caRouteRouting = session.createCriteria(RouteRouting.class);
		List<RouteRouting> listRoute = caRouteRouting.add(Restrictions.or(Restrictions.eq("beginOrgId", orgId), Restrictions.eq("endOrgId", orgId))).add(Restrictions.eq("status", SysStaticDataEnumYunQi.STS.VALID)).list();
		if (listRoute != null && listRoute.size() > 0) {
			for (RouteRouting routeRouting : listRoute) {
				routeRouting.setStatus(SysStaticDataEnumYunQi.STS.NULLITY);
				routingId.add(routeRouting.getRoutingId());
				session.update(routeRouting);
			}
		}
		//删除路由关系表
		if (routingId != null && routingId.size() > 0) {
			Criteria caRouteTowardsDtl = session.createCriteria(RouteTowardsDtl.class);
			List<RouteTowardsDtl> routeTowardsDtlList = caRouteTowardsDtl.add(Restrictions.in("routingId", routingId)).list();
			if (routeTowardsDtlList != null && routeTowardsDtlList.size() > 0) {
				for (RouteTowardsDtl routeTowardsDtl : routeTowardsDtlList) {
					towardsId.add(routeTowardsDtl.getTowardsId());
					session.delete(routeTowardsDtl);
				}
			}
		}
		//删除路由
		if (towardsId != null && towardsId.size() > 0) {
			Criteria caRouteTowards = session.createCriteria(RouteTowards.class);
			List<RouteTowards> routeTowardsList = caRouteTowards.add(Restrictions.in("towardsId", towardsId)).list();
			if (routeTowardsList != null && routeTowardsList.size() > 0) {
				for (RouteTowards routeTowards : routeTowardsList) {
					session.delete(routeTowards);
				}
			}
		}
		//删除费用规则
		List<Long> tipId = new ArrayList<Long>();
		Criteria caOrderFeeRule =  session.createCriteria(OrderFeeRule.class);
		caOrderFeeRule.add(Restrictions.eq("beginOrgId", orgId));
		caOrderFeeRule.add(Restrictions.eq("endOrgId", orgId));
		List<OrderFeeRule> listRule = caOrderFeeRule.list();
		if (listRule != null && listRule.size() > 0) {
			for (OrderFeeRule orderFeeRule : listRule) {
				if (orderFeeRule.getRuleType() == SysStaticDataEnumYunQi.RULE_TYPE.tip && orderFeeRule.getTipType() == SysStaticDataEnumYunQi.TIP_TYPE.WEIGHT_RANGE) {
					tipId.add(orderFeeRule.getId());
				}
				session.delete(orderFeeRule);
			}
		}
		if (tipId != null && tipId.size() > 0) {
			//删除费用扩张表
			Criteria caOrderTipRuleExt = session.createCriteria(OrderTipRuleExt.class);
			List<OrderTipRuleExt> listTip = caOrderTipRuleExt.add(Restrictions.in("tipId", tipId)).list();
			if (listTip !=null && listTip.size() > 0) {
				for (OrderTipRuleExt orderTipRuleExt : listTip) {
					session.delete(orderTipRuleExt);
				}
			}
		}
		//删除业务区域数据
		if (orgId > 0) {
			Criteria caRouteAreaRelCfg = session.createCriteria(RouteAreaRelCfg.class);
			List<RouteAreaRelCfg> listRouteAreaRelCfg = caRouteAreaRelCfg.add(Restrictions.eq("destOrgId", orgId)).list();
			if (listRouteAreaRelCfg != null && listRouteAreaRelCfg.size() > 0) {
				for (RouteAreaRelCfg routeAreaRelCfg : listRouteAreaRelCfg) {
					session.delete(routeAreaRelCfg);
				}
			}
		}
		//失效租户
		Organization org = (Organization) session.get(Organization.class, orgId);
		org.setState(SysStaticDataEnumYunQi.STS.NULLITY);
		session.update(org);
		//重现生成线路
		GraphRouteSV graphRouteSV = (GraphRouteSV) SysContexts.getBean("graphRouteSV");
		graphRouteSV.saveRoute(tenantId);
		return "Y";
	}
	/**
	 * 查询该网点下账号有
	 * @param inParam
	 * @return
	 */
	public String chackOrgByUser(Map<String,Object> inParam){
		Session session = SysContexts.getEntityManager(true);
		long orgId = DataFormat.getLongKey(inParam, "orgId");
		Query query = session.createSQLQuery(" select c.USER_NAME from cm_user_org_rel r INNER JOIN cm_user_info c ON r.user_id = c.USER_ID where r.org_id = :orgId ");
		List<Object> list = query.setParameter("orgId", orgId).list();
		String name = "";
		if (list != null && list.size() > 0) {
			if (list.size() == 1) {
				name = String.valueOf(list.get(0));
			}else{
				for (Object object : list) {
					name = object + ",";
				}
			}
		}
		return name;
	}
	
	/**
	 * 根据orgName 得到 orgId
	 * @return
	 */
	public List<Long>  selectOrgIdByOrgName(String orgName){
//		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		List<Long> listMap = new ArrayList<Long>();
		OrganizationYunQiSV orgSV = (OrganizationYunQiSV) SysContexts.getBean("organizationYunQiSV");
		//String orgName= DataFormat.getStringKey(inParam, "orgName");
		Query query = orgSV.selectOrgIdByOrgName(orgName);
		List<BigInteger> list = query.list();
		if(list != null && list.size() > 0){
			for(BigInteger  orgid : list){
//				Map<String,Object> map = new HashMap<String, Object>();
//				map.put("id", orgid);
//				listMap.add(map);
				listMap.add(orgid.longValue());
			}
		}
		return listMap;
	}
	
}
