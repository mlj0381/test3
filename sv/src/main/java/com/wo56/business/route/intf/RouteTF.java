package com.wo56.business.route.intf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.cm.intf.OrganizationTF;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.route.impl.GraphRouteSV;
import com.wo56.business.route.interfaces.IRouteIntf;
import com.wo56.business.route.vo.RouteRouting;
import com.wo56.business.route.vo.RouteTowards;
import com.wo56.business.route.vo.RouteTowardsDtl;
import com.wo56.business.route.vo.TaskRouteFeeRetry;
import com.wo56.business.route.vo.out.RouteRoutingOut;
import com.wo56.business.route.vo.out.RouteTowardsQueryOut;
import com.wo56.business.route.vo.out.RouteTowardsViewOut;
import com.wo56.business.route.vo.out.TowardsAndDtlOut;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.ObjectCompareUtils;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class RouteTF implements IRouteIntf{
     /**
      * 
      * @param inParam
      * @return
      * @throws Exception
      */
	public List doQueryRouteTowards(Map<String, String> inParam) throws Exception{
		Long beginOrgId = DataFormat.getLongKey(inParam, "beginOrgId");
		String endOwnerRegion = DataFormat.getStringKey(inParam, "endOwnerRegion");
		int orgType = DataFormat.getIntKey(inParam, "orgType");
		boolean needFilter = DataFormat.getBooleanKey(inParam, "needFilter");
		if(beginOrgId==null || beginOrgId<=0){
			throw new  BusinessException ("请输入起始网点编号!");
		}
		Session  session =  SysContexts.getEntityManager();
		Criteria  routeCa = session.createCriteria(RouteTowards.class);
		routeCa.add(Restrictions.eq("beginOrgId", beginOrgId));
		if(!StringUtils.isEmpty(endOwnerRegion)){
			routeCa.add(Restrictions.eq("endOwnerRegion", endOwnerRegion));
		}
		if (routeCa.list().size() > 0) {
			List routeList = routeCa.list();
			session.evict(routeList);
			// add by chenjun 过滤指定类型的网点数据
			if (needFilter && orgType > 0) {// 需要过滤
				List filterResult = new ArrayList();
				for (int i = 0; i < routeList.size(); i++) {
					RouteTowards towards = (RouteTowards) routeList.get(i);
					Organization organization = OraganizationCacheDataUtil.getOrganization(towards.getEndOrgId()); 
					if (null != organization && organization.getOrgType() == orgType) {
						filterResult.add(towards);
					}
				}
				return this.changeOrgNameList(filterResult);
			}
			if(!needFilter && orgType > 0){
				List filterResult = new ArrayList();
				for (int i = 0; i < routeList.size(); i++) {
					RouteTowards towards = (RouteTowards) routeList.get(i);
					Organization organization = OraganizationCacheDataUtil.getOrganization(towards.getEndOrgId()); 
					if (null != organization && organization.getOrgType() == orgType) {
						filterResult.add(towards);
					}
				}
				return this.changeOrgNameList(filterResult);
			}
			// add by chenjun 过滤指定类型的网点数据
			
			
			//转换网点名称
			return this.changeOrgNameList(routeList);
		}else{
			return new ArrayList();
		}
		
	}
	
	/**
	 * 转换网点名称
	 * 通过网点id从缓存获取网点
	 * 
	 * @param routeTowards
	 * @return
	 */
	private RouteTowards changeOrgName(RouteTowards routeTowards){
		if(routeTowards!=null){
			String bOrgName=OraganizationCacheDataUtil.getOrgName(routeTowards.getBeginOrgId());
			String eOrgName=OraganizationCacheDataUtil.getOrgName(routeTowards.getEndOrgId());;
			routeTowards.setBeginOrgName(bOrgName);
			routeTowards.setBeginOwnerName(bOrgName);
			routeTowards.setEndOrgName(eOrgName);
			routeTowards.setEndOwnerName(eOrgName);
		}
		return routeTowards;
	}
	/**
	 * 转换网点名称
	 * 
	 * 
	 */
	private List<RouteTowards> changeOrgNameList(List<RouteTowards> routeTowards){
		if(routeTowards!=null && routeTowards.size()>0){
			for(RouteTowards towards:routeTowards){
				this.changeOrgName(towards);
			}
		}
		return routeTowards;
	}
	
	/**
	 * 转换网点名称
	 * 通过网点id从缓存获取网点
	 * 
	 * @param routeTowards
	 * @return
	 */
	private RouteRouting changeOrgName(RouteRouting routeRouting){
		if(routeRouting!=null){
			String bOrgName=OraganizationCacheDataUtil.getOrgName(routeRouting.getBeginOrgId());
			String eOrgName=OraganizationCacheDataUtil.getOrgName(routeRouting.getEndOrgId());;
			
			routeRouting.setBeginOrgName(bOrgName);
			routeRouting.setEndOrgName(eOrgName);
		}
		return routeRouting;
	}
	/**
	 * 转换网点名称
	 * 
	 * 
	 */
	private List<RouteRouting> changeRoutingOrgNameList(List<RouteRouting> routeRoutings){
		if(routeRoutings!=null && routeRoutings.size()>0){
			for(RouteRouting towards:routeRoutings){
				this.changeOrgName(towards);
			}
		}
		return routeRoutings;
	}
	
	
	/**
	 * 转换网点名称
	 * 通过网点id从缓存获取网点
	 * 
	 * @param routeTowards
	 * @return
	 */
	private RouteTowardsDtl changeOrgName(RouteTowardsDtl routeTowardsDtl){
		if(routeTowardsDtl!=null){
			String bOrgName=OraganizationCacheDataUtil.getOrgName(routeTowardsDtl.getBeginOrgId());
			String eOrgName=OraganizationCacheDataUtil.getOrgName(routeTowardsDtl.getEndOrgId());;
			
			routeTowardsDtl.setBeginOrgName(bOrgName);
			routeTowardsDtl.setEndOrgName(eOrgName);
		}
		return routeTowardsDtl;
	}
	/**
	 * 转换网点名称
	 * 
	 * 
	 */
	private List<RouteTowardsDtl> changeTowardsDtlOrgNameList(List<RouteTowardsDtl> routeTowardsDtls){
		if(routeTowardsDtls!=null && routeTowardsDtls.size()>0){
			for(RouteTowardsDtl towards:routeTowardsDtls){
				this.changeOrgName(towards);
			}
		}
		return routeTowardsDtls;
	}
	
	
	
	
	
	/**
	 * 查询网点信息
	 * 
	 * collectType =1 查询所有到当前网点的网点   	 当前网点为终点
	 * collectType =3 查询当前网点能到的 所有网点	 当前网点为起点
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List queryRoateRuting(Map<String, String> inParam) throws Exception{
		//String   orgId= DataFormat.getStringKey(inParam, "orgId");
		Integer collectType= DataFormat.getIntKey(inParam, "collectType");
		Integer addType= DataFormat.getIntKey(inParam, "addType");
		if(collectType==null || collectType<0 || collectType>4){
			throw new  BusinessException ("请输入正确的网点类型!");
		}
		BaseUser base = SysContexts.getCurrentOperator();
		Long orgId=base.getOrgId();
		Session  session =  SysContexts.getEntityManager(true);
		Criteria  routeCa = session.createCriteria(RouteRouting.class);
		if(collectType==SysStaticDataEnum.COLLECT_TYPE.START_ORG){
			routeCa.add(Restrictions.eq("endOrgId", orgId));
		}else if(collectType==SysStaticDataEnum.COLLECT_TYPE.END_ORG){
			routeCa.add(Restrictions.eq("beginOrgId", orgId));
		}else if(collectType == SysStaticDataEnum.COLLECT_TYPE.ALL_ORG){
			BaseUser baseUser = SysContexts.getCurrentOperator();
			routeCa.add(Restrictions.eq("tenantId", baseUser.getTenantId()));
		}
		if(routeCa.list().size()>0){
			List<RouteRouting> routeList =routeCa.list(); 
			List<RouteRouting>  list = new ArrayList<RouteRouting>();
			if(addType==2){
				RouteRouting  rout= new RouteRouting();
				rout.setRoutingId(-1l);
				rout.setBeginOrgName("全部");
				rout.setBeginOrgId(-1l);
				rout.setEndOrgName("全部");
				rout.setEndOrgId(-1l);
				list.add(0, rout);
			}
			//网点名称从缓存获取
			session.evict(routeList);
			routeList=this.changeRoutingOrgNameList(routeList);
			for(RouteRouting rout:routeList){
				list.add(rout);
			}
			return list;
		}else{
			return new ArrayList();
		}
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 查询网点信息
	 * 
	 * collectType =1 查询所有到当前网点的网点   	 当前网点为终点
	 * collectType =3 查询当前网点能到的 所有网点	 当前网点为起点
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List queryOrgTypeRoateRuting(Map<String, String> inParam) throws Exception{
		Integer collectType= DataFormat.getIntKey(inParam, "collectType");
		Integer addType= DataFormat.getIntKey(inParam, "addType");
		if(collectType==null || collectType<0 || collectType>4){
			throw new  BusinessException ("请输入正确的网点类型!");
		}
		Integer collectState= DataFormat.getIntKey(inParam, "collectState");
		
		BaseUser base = SysContexts.getCurrentOperator();
		Long orgId=base.getOrgId();
		Session  session =  SysContexts.getEntityManager(true);
		StringBuffer buffer =  new StringBuffer();
		buffer.append("SELECT r.* FROM  ROUTE_ROUTING r  WHERE 1=1 ");
		if(collectType==SysStaticDataEnum.COLLECT_TYPE.START_ORG){
			buffer.append(" AND r.END_ORG_ID=:orgId");
		}else if(collectType==SysStaticDataEnum.COLLECT_TYPE.END_ORG){
			buffer.append(" AND r.BEGIN_ORG_ID=:orgId");
		}else if(collectType == SysStaticDataEnum.COLLECT_TYPE.ALL_ORG){
			buffer.append(" AND r.TENANT_ID=:tenantId ");
		}
		if(collectState!=null && collectState==2){
		    buffer.append("  GROUP BY r.BEGIN_ORG_ID ");
		}
		if(collectState!=null && collectState==1){
			buffer.append("  GROUP BY r.END_ORG_ID ");
		}
		SQLQuery query = session.createSQLQuery(buffer.toString()).addEntity("r", RouteRouting.class);
		if(collectType==SysStaticDataEnum.COLLECT_TYPE.START_ORG){
			query.setParameter("orgId", orgId);
		}else if(collectType==SysStaticDataEnum.COLLECT_TYPE.END_ORG){
			query.setParameter("orgId", orgId);
		}else if(collectType == SysStaticDataEnum.COLLECT_TYPE.ALL_ORG){
			BaseUser baseUser = SysContexts.getCurrentOperator();
			query.setParameter("tenantId", baseUser.getTenantId());
		}
		List<RouteRouting> routeList =new ArrayList<RouteRouting>();
		routeList =query.list();
		session.evict(routeList);
		return  this.changeRoutingOrgNameList(routeList);
	}
	
	
	
	
	
	/**
	 * 查询网点信息
	 * 
	 * collectType =1 查询所有到当前网点的网点   	 当前网点为终点
	 * collectType =3 查询当前网点能到的 所有网点	 当前网点为起点
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List queryRouteRutingById(Map<String, String> inParam) throws Exception{
		long beginOrgId = DataFormat.getLongKey(inParam, "beginOrgId");
		long endOrgId = DataFormat.getLongKey(inParam, "endOrgId");
		BaseUser base = SysContexts.getCurrentOperator();
		Session  session =  SysContexts.getEntityManager(true);
		Criteria  routeCa = session.createCriteria(RouteRouting.class);
		if(beginOrgId>-1){
			routeCa.add(Restrictions.eq("beginOrgId", beginOrgId));
		}
		if(routeCa.list().size()>0){
			List<RouteRouting> routeList =routeCa.list(); 
			session.evict(routeList);
			List<RouteRouting>  list = new ArrayList<RouteRouting>();
			for(RouteRouting rout:routeList){
				list.add(rout);
			}
			return this.changeRoutingOrgNameList(list);
		}else{
			return new ArrayList();
		}
	}
	/**
	 * 查询路由的线路 
	 * beginOrgId:起始网点Id
	 * endOrgId:结束网点ID
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map queryTowardsAndDtl(Map<String, String> inParam) throws Exception{
		Long   beginOrgId= DataFormat.getLongKey(inParam, "beginOrgId");
		Long   endOrgId= DataFormat.getLongKey(inParam, "endOrgId");
		if(beginOrgId==null || beginOrgId<=0){
			throw new  BusinessException ("请输入起始网点编号!");
		}
		if(endOrgId==null || endOrgId<=0){
			throw new  BusinessException ("请输入结束网点编号!");
		}
		Session  session =  SysContexts.getEntityManager(true);
		Criteria  towardCa = session.createCriteria(RouteTowards.class);
		towardCa.add(Restrictions.eq("beginOrgId", beginOrgId));
		towardCa.add(Restrictions.eq("endOrgId", endOrgId));
		TowardsAndDtlOut towOut = null;
		List<TowardsAndDtlOut> outList = new ArrayList<TowardsAndDtlOut>();
		Map reqMap = new HashMap();
		if(towardCa.list().size()>0){
			RouteTowards towards = (RouteTowards)towardCa.list().get(0);
			Long towardsId = towards.getTowardsId();
			Criteria dtlCa = session.createCriteria(RouteTowardsDtl.class);
			dtlCa.add(Restrictions.eq("towardsId", towardsId));
			dtlCa.addOrder(Order.asc("sequence"));
			if(dtlCa.list().size()>0){
				List<RouteTowardsDtl> towardsDtlList =dtlCa.list();
				session.evict(towardsDtlList);
				//转换网点名称
				towardsDtlList=this.changeTowardsDtlOrgNameList(towardsDtlList);
				for(RouteTowardsDtl towardsDtl: towardsDtlList){
					towOut=new TowardsAndDtlOut();
					BeanUtils.copyProperties(towOut, towardsDtl);
					outList.add(towOut);
				}
				reqMap.put("RouteTowardsDtl", outList);
				reqMap.put("towardsId", towardsId);
			}
		}
		return reqMap;
	}
	
	/**查询可到达网点**/
	public List queryTowardsDtl(Map<String, String> inParam) throws Exception{
		Session  session =  SysContexts.getEntityManager(true);
		Integer  collectType= DataFormat.getIntKey(inParam, "collectType");
		BaseUser user= SysContexts.getCurrentOperator();
		List list = new ArrayList();
		if(user!=null){
			Criteria ca = session.createCriteria(RouteTowardsDtl.class);
			if(collectType!=null && collectType==1){
				ca.add(Restrictions.eq("endOrgId", user.getOrgId()));
			}else{
			   ca.add(Restrictions.eq("beginOrgId", user.getOrgId()));
			}
			list = ca.list();
			session.evict(list);
			list=this.changeTowardsDtlOrgNameList(list);
		}
		return list;
		
	};
	
	/**查询可到达网点信息**/
	public List getTowards(Map<String, String> inParam) throws Exception{
		Session  session =  SysContexts.getEntityManager(true);
		Long  beginOrgId= DataFormat.getLongKey(inParam, "beginOrgId");
		Long  endOrgId= DataFormat.getLongKey(inParam, "endOrgId");
		String isAll = DataFormat.getStringKey(inParam,"hasAll");
		List<RouteTowards> list = new ArrayList();
		
		Criteria ca = session.createCriteria(RouteTowards.class);
		if(beginOrgId!=null && beginOrgId>0){
			ca.add(Restrictions.eq("beginOrgId",beginOrgId));	
		}
		if(endOrgId!=null && endOrgId>0){
			ca.add(Restrictions.eq("endOrgId",endOrgId));	
		}
		if(ca.list().size()>0){
			//list = ca.list();
			if (StringUtils.isNotEmpty(isAll) && "Y".equals(isAll)) {
				RouteTowards routeTowards = new RouteTowards();
				routeTowards.setTowardsId(-1l);
				routeTowards.setBeginOrgName("全部");
				routeTowards.setBeginOrgId(-1L);
				routeTowards.setEndOrgId(-1L);
				routeTowards.setEndOrgName("全部");
				list.add(routeTowards);
			}
			List<RouteTowards> routeTowards= ca.list();
			session.evict(routeTowards);
			routeTowards=this.changeOrgNameList(routeTowards);
			list.addAll(routeTowards);
		}
		return list;
		
	};
	
	
	
	/**
	 * 
	 * 记录费用重算任务表
	 * @param inParam
	 * beginOrgId:起始网点编号
	 * endOrgId：结束网点编号
	 * orderId：订单编号
	 * @return
	 * @throws Exception
	 */
	public Map doSaveTaskRoute(Map<String, String> inParam) throws Exception{
		Long   beginOrgId= DataFormat.getLongKey(inParam, "beginOrgId");
		Long   endOrgId= DataFormat.getLongKey(inParam, "endOrgId");
		Long   orderId= DataFormat.getLongKey(inParam, "orderId");
		if(beginOrgId==null || beginOrgId<=0){
			 throw new BusinessException("请输入起始网点编号");	
		}
		if(endOrgId==null || endOrgId<=0){
			 throw new BusinessException("请输入终止网点编号");	
		}
		if(orderId==null || orderId<=0){
			 throw new BusinessException("请输入订单");	
		}
		TaskRouteFeeRetry taskIn = new TaskRouteFeeRetry();
		BeanUtils.populate(taskIn, inParam);
		taskIn.setSts(0);
		//taskIn.s
		Session  session =  SysContexts.getEntityManager();
		session.save(taskIn);
		return new HashMap();
	}
	
	/***
	 * 接口编码：160001
	 * 
	 * 查询路由**/
	public Map doQuery(Map<String, String> inParam) throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		long startOrgId = DataFormat.getLongKey(inParam, "startOrgId");
		long endOrgId = DataFormat.getLongKey(inParam, "endOrgId");
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(RouteTowards.class);
		ca.add(Restrictions.eq("beginOrgId", user.getOrgId()));
		if(endOrgId>0){
			ca.add(Restrictions.eq("endOrgId", endOrgId));
		}
		IPage page = PageUtil.gridPage(ca);
		Pagination pagination = new Pagination(page);
		List<RouteTowards> list = pagination.getItems();
		session.evict(list);
		list=this.changeOrgNameList(list);
		List<RouteTowardsQueryOut> outList = new ArrayList<RouteTowardsQueryOut>();
		for (RouteTowards routeTowards : list) {
			RouteTowardsQueryOut out = new RouteTowardsQueryOut();
			BeanUtils.copyProperties(out, routeTowards);
			out.setBeginOwnerRegion(SysStaticDataUtil.getCityDataList("SYS_CITY", routeTowards.getBeginOwnerRegion()).getName());
			out.setEndOwnerRegion(SysStaticDataUtil.getCityDataList("SYS_CITY", routeTowards.getEndOwnerRegion()).getName());
			outList.add(out);
		}
		pagination.setItems(outList);
		return JsonHelper.parseJSON2Map(JsonHelper.json(pagination));
	}
	
	public boolean checkRount(RouteRouting routeRouting){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(RouteRouting.class);
		ca.add(Restrictions.eq("beginOrgId",routeRouting.getBeginOrgId()));
		ca.add(Restrictions.eq("endOrgId",routeRouting.getEndOrgId()));
		ca.add(Restrictions.eq("routeType",routeRouting.getRouteType()));
		ca.add(Restrictions.eq("isStandardLine",routeRouting.getIsStandardLine()));
		ca.add(Restrictions.eq("tenantId",routeRouting.getTenantId()));
		ca.add(Restrictions.eq("status", SysStaticDataEnumYunQi.STS.VALID));
		List<RouteRouting> list = (List<RouteRouting>)ca.list();
		if(list != null && list.size() > 0){
			return false;
		}
		return true;
	}
	
	public boolean checkRountByRuute(RouteRouting routeRouting){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(RouteRouting.class);
		ca.add(Restrictions.eq("beginOrgId",routeRouting.getBeginOrgId()));
		ca.add(Restrictions.eq("endOrgId",routeRouting.getEndOrgId()));
		ca.add(Restrictions.eq("routeType",routeRouting.getRouteType()));
		ca.add(Restrictions.eq("isStandardLine",routeRouting.getIsStandardLine()));
		ca.add(Restrictions.eq("tenantId",routeRouting.getTenantId()));
		ca.add(Restrictions.ne("routingId", routeRouting.getRoutingId()));
		List<RouteRouting> list = (List<RouteRouting>)ca.list();
		if(list != null && list.size() > 0){
			return false;
		}
		return true;
	}
	//---------------------基础配置-线路设置
	/**
	 * 线路设置
	 * 130008
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map setRount(Map<String, String> inParam)throws Exception{
		Session session = SysContexts.getEntityManager();
		RouteRouting routeRouting = new RouteRouting();
		BeanUtils.populate(routeRouting, inParam);
		BaseUser user = SysContexts.getCurrentOperator();
		long tenantId =user.getTenantId();
		int isStandardLine = DataFormat.getIntKey(inParam, "isStandardLine");
		ObjectCompareUtils.isNotBlankNames(routeRouting, new String[]{"beginOrgId","endOrgId","routeType","isStandardLine"});
		if(routeRouting.getTenantId() == null){
			throw new BusinessException("请选择租户信息");
		}
		if(!checkRount(routeRouting)){
			throw new BusinessException("已经存在该线路！");
		}
		Criteria ca = session.createCriteria(RouteRouting.class);
		ca.add(Restrictions.eq("beginOrgId", routeRouting.getBeginOrgId()));
		ca.add(Restrictions.eq("endOrgId", routeRouting.getEndOrgId()));
		ca.add(Restrictions.eq("isStandardLine", isStandardLine));
		ca.add(Restrictions.eq("routeType", routeRouting.getRouteType()));
		List<RouteRouting> list = ca.list();
		if (list != null && list.size() > 0) {
			routeRouting = list.get(0);
		}
		routeRouting.setIsStandardLine(isStandardLine);
		routeRouting.setStatus(1);
		//查询名称－开始网点
		Organization organizationBegin = (Organization) session.get(Organization.class, routeRouting.getBeginOrgId());
		if (organizationBegin != null) {
			routeRouting.setBeginOrgName(organizationBegin.getOrgName());
		}
		//查询名称－结束网点
		Organization organizationEnd = (Organization) session.get(Organization.class, routeRouting.getEndOrgId());
		if (organizationEnd != null) {
			routeRouting.setEndOrgName(organizationEnd.getOrgName());
		}
		routeRouting.setTenantId(tenantId);
		session.saveOrUpdate(routeRouting);
		GraphRouteSV graphRouteSV = (GraphRouteSV) SysContexts.getBean("graphRouteSV");
		graphRouteSV.saveRoute(tenantId);
		Map<String,String> map = new HashMap<String,String>();
		map.put("isOk", "Y");
		return map;
	}
	
	/**
	 * 查询线路
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map queryTowards(Map<String,String> inParam)throws Exception{
		long tenantId = DataFormat.getLongKey(inParam,"tenantId");
		long beginOrgId =DataFormat.getLongKey(inParam,"beginOrgId");
		long endOrgId = DataFormat.getLongKey(inParam,"endOrgId");
		int routeType = DataFormat.getIntKey(inParam,"routeType");
		int isStandardLine = DataFormat.getIntKey(inParam,"isStandardLine");
		Session session = SysContexts.getEntityManager(true);
		//Criteria ca = session.createCriteria(RouteRouting.class);
		StringBuffer sb = new StringBuffer(" select ROUTING_ID,(select org_name from organization where ORG_ID = BEGIN_ORG_ID) as beginOrgName,(select org_name from organization where ORG_ID = end_ORG_ID) as endOrgName,ROUTE_TYPE,IS_STANDARD_LINE from route_routing where status =:status ");
		Map<String,Object> map = new HashMap<String, Object>();
		if (tenantId >=0) {
			sb.append(" and tenant_id = :tenantId ");
			map.put("tenantId", tenantId);
		}
		if(beginOrgId >0){
			sb.append(" and begin_Org_Id = :beginOrgId ");
			map.put("beginOrgId", beginOrgId);
		}
		if(endOrgId >0){
			sb.append(" and end_Org_Id = :endOrgId ");
			map.put("endOrgId", endOrgId);
		}
		if(routeType >0){
			sb.append(" and route_Type = :routeType ");
			map.put("routeType", routeType);
		}
		if(isStandardLine >0){
			sb.append(" and is_Standard_Line = :isStandardLine ");
			map.put("isStandardLine", isStandardLine);
		}
		map.put("status", SysStaticDataEnumYunQi.STS.VALID);
		Query query = session.createSQLQuery(sb.toString());
		query.setProperties(map);
		
		IPage p = PageUtil.gridPage(query);
		Pagination pagination = new Pagination(p);
		List<Object[]> list = pagination.getItems();
		List<RouteRoutingOut> outList = new ArrayList<RouteRoutingOut>();
		for (Object[] obj : list) {
			RouteRoutingOut out = new RouteRoutingOut();
			out.setRoutingId(Long.valueOf(obj[0].toString()));
			out.setBeginOrgName(obj[1] != null ? obj[1].toString() : "");
			out.setEndOrgName(obj[2] != null ? obj[2].toString() : "");
			if (obj[3] != null) {
				out.setRouteTypeName(SysStaticDataUtil.getSysStaticData("ROUTE_TYPE", obj[3].toString()).getCodeName());
			}
			if (obj[4] != null) {
				out.setIsStandardLineName(SysStaticDataUtil.getSysStaticData("IS_STANDARD_LINE", obj[4].toString()).getCodeName());
			}
			outList.add(out);
		}
		pagination.setItems(outList);
		return JsonHelper.parseJSON2Map(JsonHelper.json(pagination));
	}
	
	
	/**
	 * 路由详情 
	 * 160002
	 * @return
	 * @throws Exception
	 */
	public List<RouteTowardsViewOut> toView(Map<String, String> inParam)throws Exception{
		long towardsId = DataFormat.getLongKey(inParam, "towardsId");
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(RouteTowardsDtl.class);
		ca.add(Restrictions.eq("towardsId", towardsId));
		ca.addOrder(Order.asc("sequence"));
		List<RouteTowardsDtl> list = ca.list();
		session.evict(list);
		list=this.changeTowardsDtlOrgNameList(list);
		ArrayList<RouteTowardsViewOut> outList = new ArrayList<RouteTowardsViewOut>();
		for (RouteTowardsDtl routeTowardsDtl : list) {
			RouteTowardsViewOut outParam = new RouteTowardsViewOut();
			BeanUtils.copyProperties(outParam,routeTowardsDtl);
			outList.add(outParam);
		}
		return outList;
	}
	
	
	/**
	 * 查询网点信息
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List<RouteRouting> queryCurrRoateRuting(Map<String, String> inParam) throws Exception{
		Long orgId= DataFormat.getLongKey(inParam, "orgId");
		Map<String,List<RouteRouting>> map=new HashMap<String, List<RouteRouting>>();
		if(orgId==null){
			return new ArrayList<RouteRouting>();
		}
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(RouteRouting.class);
		ca.add(Restrictions.eq("beginOrgId", orgId));
		ca.add(Restrictions.eq("status", SysStaticDataEnum.STS.VALID));
		ca.add(Restrictions.eq("routeType", SysStaticDataEnum.ROUTE_TYPE.ARTERY));
		
		List<RouteRouting> list = ca.list();
		
		return list;
	}
	/**
	 * 查询短驳的到达网点信息
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List<RouteRouting> queryRoateRutingIsShort(Map<String, Object> inParam) throws Exception{
		BaseUser baseUser = SysContexts.getCurrentOperator();
		Session  session =  SysContexts.getEntityManager(true);
		StringBuffer buffer =  new StringBuffer();
		int type = DataFormat.getIntKey(inParam,"type"); //默认查询短驳到达网点： 1：短驳开始的网点 到当前的所有网点
		buffer.append("SELECT r.* FROM  ROUTE_ROUTING r  WHERE 1=1 ");
		buffer.append(" AND r.TENANT_ID=:tenantId ");
		if(type == 1){
			//到货到车的开始网点查询
			buffer.append(" AND r.END_ORG_ID =:orgId");
			
		}else{
			buffer.append(" AND r.BEGIN_ORG_ID =:orgId");
			
		}
		
		buffer.append(" AND r.ROUTE_TYPE =:routeType ");
		SQLQuery query = session.createSQLQuery(buffer.toString()).addEntity("r", RouteRouting.class);

		query.setParameter("tenantId", baseUser.getTenantId());
		query.setParameter("orgId", baseUser.getOrgId());
		query.setParameter("routeType", SysStaticDataEnum.ROUTE_TYPE.SHORT_BARGE);
		List<RouteRouting> routeList =new ArrayList<RouteRouting>();
		routeList =query.list();
		session.evict(routeList);
		return  this.changeRoutingOrgNameList(routeList);
	}
	/**
	 * 获取当前开单员可以送达的到这城市
	 * @return
	 */
	public Map<String,Object> getCurOrgCity(){
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		List<Map<String,Object>> listOut = new ArrayList<Map<String,Object>>();
		Query query = session.createSQLQuery("select r.* from Route_Towards r where r.begin_Org_Id = :beginOrgId GROUP BY r.END_owner_region ").addEntity("r",RouteTowards.class);
		query.setParameter("beginOrgId", user.getOrgId());
		List<Object> list = query.list();
		if (list!=null&&list.size() > 0) {
			for (Object routeTowards : list) {
				RouteTowards routeTowards2 = (RouteTowards) routeTowards;
				if (routeTowards2!=null) {
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("cityId", routeTowards2.getEndOwnerRegion());
					map.put("cityName", routeTowards2.getEndOwnerRegionName());
					long provinceId = SysStaticDataUtil.getCityDataList("SYS_CITY", routeTowards2.getEndOwnerRegion()).getProvId();
					map.put("provinceId", provinceId);
					map.put("provinceName", SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(provinceId)).getName());
					listOut.add(map);
				}
			}
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(listOut));
	}
	/**
	 * 通过id查询线路
	 * @param inParam
	 * @return
	 */
	public RouteRoutingOut getRouteRoutingOut(Map<String,Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		long routingId = DataFormat.getLongKey(inParam, "routingId");
		if (routingId < 0) {
			throw new BusinessException("请传入线路id");
		}
		RouteRouting routeRouting = (RouteRouting) session.get(RouteRouting.class,routingId);
		RouteRoutingOut routeRoutingOut = new RouteRoutingOut();
		if (routeRouting != null) {
			BeanUtils.copyProperties(routeRoutingOut, routeRouting);
		}
		return routeRoutingOut;
	}
	/**
	 * 修改线路
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doUpdateRouteRouting(Map<String,Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager();
		long routingId = DataFormat.getLongKey(inParam, "routingId");
		BaseUser user = SysContexts.getCurrentOperator();
		long tenantId = user.getTenantId();
		RouteRouting routeRouting = null;
		if (routingId < 0) {
			throw new BusinessException("请传入线路id");
		}else{
			routeRouting = (RouteRouting) session.get(RouteRouting.class, routingId);
			BeanUtils.populate(routeRouting, inParam);
		}
		if(!checkRountByRuute(routeRouting)){
			throw new BusinessException("已经存在该线路！");
		}
		//删除原有路由的数据
		List<Long> towardsIds = new ArrayList<Long>();
		List<RouteTowardsDtl> dtlList = session.createCriteria(RouteTowardsDtl.class).add(Restrictions.eq("routingId", routingId)).list();
		if (dtlList!=null&&dtlList.size()>0) {
			for (RouteTowardsDtl routeTowardsDtl : dtlList) {
				towardsIds.add(routeTowardsDtl.getTowardsId());
				session.delete(routeTowardsDtl);
			}
		}
		if (towardsIds!=null&&towardsIds.size()>0) {
			for (Long towardsId : towardsIds) {
				RouteTowards towards = (RouteTowards) session.get(RouteTowards.class, towardsId);
				if (towards != null) {
					session.delete(towards);
				}
			}
		}
		//查询名称－开始网点
		OrganizationTF organizationTF =  (OrganizationTF) SysContexts.getBean("organizationTF");
		List<Organization> listBegin = organizationTF.getOrganizationByCondition(routeRouting.getBeginOrgId(), -1);
		if(listBegin.size()>0){
			Organization organization = listBegin.get(0);
			routeRouting.setBeginOrgName(organization.getOrgName());
		}
		//查询名称－结束网点
		List<Organization> listEnd = organizationTF.getOrganizationByCondition(routeRouting.getEndOrgId(), -1);
		if(listEnd.size()>0){
			Organization organization = listEnd.get(0);
			routeRouting.setEndOrgName(organization.getOrgName());
		}
		session.update(routeRouting);
		GraphRouteSV graphRouteSV = (GraphRouteSV) SysContexts.getBean("graphRouteSV");
		graphRouteSV.saveRoute(tenantId);
		return "Y";
	}
	/**
	 * 删除线路
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String doDelRouteRoutin(Map<String,Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		long tenantId = user.getTenantId();
		long routingId = DataFormat.getLongKey(inParam, "routingId");
		RouteRouting routeRouting = null;
		if (routingId < 0) {
			throw new BusinessException("请传入线路id");
		}else{
			routeRouting = (RouteRouting) session.get(RouteRouting.class, routingId);
		}
		routeRouting.setStatus(SysStaticDataEnumYunQi.STS.NULLITY);
		session.update(routeRouting);
		//删除路由关系表
		List<Long> towardsId = new ArrayList<Long>();
		Criteria caRouteTowardsDtl = session.createCriteria(RouteTowardsDtl.class);
		List<RouteTowardsDtl> routeTowardsDtlList = caRouteTowardsDtl.add(Restrictions.eq("routingId", routeRouting.getRoutingId())).list();
		if (routeTowardsDtlList != null && routeTowardsDtlList.size() > 0) {
			for (RouteTowardsDtl routeTowardsDtl : routeTowardsDtlList) {
				towardsId.add(routeTowardsDtl.getTowardsId());
				session.delete(routeTowardsDtl);
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
		GraphRouteSV graphRouteSV = (GraphRouteSV) SysContexts.getBean("graphRouteSV");
		graphRouteSV.saveRoute(tenantId);
		return "Y";
	}
	/**
	 * 到达网点查询
	 * @return
	 */
	public Map<String,Object> getRouteDescOrgId(Map<String,Object> inParam){
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(RouteRouting.class);
		ca.add(Restrictions.eq("beginOrgId", user.getOrgId()));
		ca.add(Restrictions.eq("status", SysStaticDataEnumYunQi.STS.VALID));
		List<RouteRouting> list = ca.list();
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		if (list!=null&&list.size()>0) {
			for (RouteRouting routeRouting : list) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("endOrgId", routeRouting.getEndOrgId());
				map.put("endOrgName", routeRouting.getEndOrgName());
				mapList.add(map);
			}
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(mapList));
	}
}
	
