package com.wo56.business.route.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.JsonHelper;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.route.vo.RouteRouting;
import com.wo56.business.route.vo.RouteTowards;
import com.wo56.business.route.vo.RouteTowardsDtl;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class GraphRouteSV{
	private static final Log log = LogFactory.getLog(GraphRouteSV.class);
	// 存储节点（网点）信息
	@SuppressWarnings("rawtypes")
	private List points;
	// 存储无向边信息（不连通0，联通 1）
	private int[][] lines;
	// 存储由于业务规则限制，X节点不能再访问的节点序列
	@SuppressWarnings("rawtypes")
	private Map ruleLineMap = new HashMap();
	// 节点类型
	private int[] pointType;
	// 存储访问过的节点信息，用于自造递归信息，必须为二维数组才可以
	private  boolean[][] visited;
	// 节点总数
	private int pc = 0;
	
	//路由信息Map 用于判断路由信息是否存在以及原有路由信息的长度等
	public RouteTowardsMap routeTowardsMap;

	// 添加联通的边，由于是无向边，所以双向联通
	public void setEdge(int i, int j) {
		try {
			if (i == j)
				return;
			lines[i][j] = 1;
//			lines[j][i] = 1;//单向边注释掉这里即可，如果通过后面的判断会加到整个的逻辑
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GraphRouteSV() {
		
	}
	
	// 初始化设置所有的边都不连通
	public GraphRouteSV(int n) {
		lines = new int[n][n];
		pointType = new int[n];
		visited = new boolean[n][n];
		pc = n;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				lines[i][j] = 0;
				visited[i][j] = false;
			}
			pointType[i] = 0;
			
		}
	}

	public void setPointType(int i) {
		pointType[i] = 1;
	}

	public int getPointType(int i) {
		return pointType[i];
	}

	/**
	 * 
	 *   操作线路重新处理路由信息
	 * 
	 **/
	@SuppressWarnings({ "unchecked" })
	public String saveRoute(long tenantId) throws Exception{
		log.info("<<<<<<<<<<<<<web线路  操作线路生成路由 信息 开始>>>>>>>>>>>>>");
			Session session = SysContexts.getEntityManager();
			BaseUser user = SysContexts.getCurrentOperator();
			Criteria ca = session.createCriteria(RouteRouting.class);
			ca.add(Restrictions.eq("status", SysStaticDataEnum.STS.VALID));
			ca.add(Restrictions.eq("isStandardLine", SysStaticDataEnum.IS_STANDARD_LINE.STANDARD_ROUTE));
			ca.add(Restrictions.eq("tenantId", tenantId));
			List<RouteRouting> list2 = ca.list();
			
			Criteria routeCa = session.createCriteria(RouteTowards.class);
			routeCa.add(Restrictions.eq("tenantId", tenantId));
			List<RouteTowards> routeTowardsList = routeCa.list();
			
			RouteTowardsMap routeTowardsMap = new RouteTowardsMap(routeTowardsList);
			//log.info("routeTowardsMap:"+JsonHelper.json(routeTowardsList));
			List<Organization> list = OraganizationCacheDataUtil.getOrganizationByTenantIdAndOrgType(user.getTenantId());
			// 网点设置
			OUT:
			for(int j = 0; j < list.size(); j++){
				try {	
					List<String> points = new ArrayList<String>();
					List<String> centerPoints = new ArrayList<String>();
					Organization org = list.get(j);
					if(org.getOrgType()==null ||  org.getOrgType()==SysStaticDataEnum.ORG_TYPE.HEAD_OFFICE){
						continue OUT;
					}
					points.add(String.valueOf(org.getOrgId()));
					for (int i = 0; i < list.size(); i++) {
						// 起始节点
						Organization organization = list.get(i);
						if(org.getOrgId() == organization.getOrgId()){
							
						}else{
							if(organization.getOrgType()!=null && organization.getOrgType() != SysStaticDataEnum.ORG_TYPE.HEAD_OFFICE){
								// 其他非起始节点
								points.add(organization.getOrgId()+"");
								if(organization.getOrgType()==SysStaticDataEnum.ORG_TYPE.FOXTOWN_CENTER){
									// 中心节点
									centerPoints.add(organization.getOrgId()+"");
								}
							}
						}
					}
					GraphRouteSV g = new GraphRouteSV(points.size());
					g.points = points;
					g.routeTowardsMap = routeTowardsMap;
					for (int i = 0; i < centerPoints.size(); i++) {
						g.setPointType(g.getIndex(points, centerPoints.get(i)));
					}
					for (RouteRouting routeRouting : list2) {
						// 联通网点设置
						if(g.getIndex(points, routeRouting.getBeginOrgId().toString())!=-1&&g.getIndex(points, routeRouting.getEndOrgId().toString())!=-1){
							g.setEdge(g.getIndex(points, routeRouting.getBeginOrgId().toString()), g.getIndex(points, routeRouting.getEndOrgId().toString()));
						}
					}
					//log.error("points:"+JsonHelper.json(points));
					g.printRoute(0,routeTowardsList,list2,tenantId);
		
				} catch (Exception e) {
					log.info("<<<<<<<<<<<<< 操作线路生成路由信息 异常>>>>>>>>>>>>>",e);
					SysContexts.rollbackTransation();
					e.printStackTrace();
					
				}finally{
				}
			}
		
		log.info("<<<<<<<<<<<<<web线路  操作线路生成路由 信息结束>>>>>>>>>>>>>");
		return "Y";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void print(Stack s,List<RouteTowards>  list,List<RouteRouting> routingList, long tenantId) {
		Session session = SysContexts.getEntityManager(false);
		StringBuffer towards=new StringBuffer();
		for (int i = 0; i < s.size(); i++) {
			if(i==s.size()){
				towards.append(points.get(Integer.parseInt(s.get(i)+"")));
			}else{
				towards.append(points.get(Integer.parseInt(s.get(i)+"")) + "-");
			}
		}
		String[] orgArr = towards.toString().split("-");
		long towardsId=0L;
		boolean isExist=false;
		if(orgArr.length>=2){
			int count = routeTowardsMap.getCount(Long.parseLong(orgArr[0]), Long.parseLong(orgArr[orgArr.length-1]));
			if(count!=0 && count <= orgArr.length){
				isExist=true;
			}
			if(!isExist){
				RouteTowards routeTowards = new RouteTowards();
				Organization beginOrg = OraganizationCacheDataUtil.getOrganization(Long.parseLong(orgArr[0]));
				Organization endOrg = OraganizationCacheDataUtil.getOrganization(Long.parseLong(orgArr[orgArr.length-1]));
				routeTowards.setBeginOrgId(Long.parseLong(orgArr[0]));
				routeTowards.setEndOrgId(Long.parseLong(orgArr[orgArr.length-1]));
				routeTowards.setBeginOwnerName(beginOrg.getOrgName());
				routeTowards.setEndOwnerName(endOrg.getOrgName());
				routeTowards.setBeginOwnerRegion(beginOrg.getRegionId());
				routeTowards.setEndOwnerRegion(endOrg.getRegionId());
				routeTowards.setTenantId(tenantId);
				routeTowards.setBeginTenantId(beginOrg.getTenantId());
				routeTowards.setEndTenantId(endOrg.getTenantId());
				routeTowards.setCreateDate(new Date());
				session.saveOrUpdate(routeTowards);
				towardsId=routeTowards.getTowardsId();
				for (int j = 0; j < orgArr.length; j++) {
					if(j<orgArr.length-1){
						RouteTowardsDtl routeTowardsDtl = new RouteTowardsDtl();
						for (RouteRouting routeRouting : routingList) {
							if(Long.parseLong(orgArr[j])==routeRouting.getBeginOrgId().longValue()&&Long.parseLong(orgArr[j+1])==routeRouting.getEndOrgId().longValue()){
								routeTowardsDtl.setRoutingId(routeRouting.getRoutingId());
							}
						}
						if(routeTowardsDtl.getRoutingId()!=null){
							routeTowardsDtl.setTowardsId(towardsId);
							routeTowardsDtl.setBeginOrgId(Long.parseLong(orgArr[j]));
							routeTowardsDtl.setEndOrgId(Long.parseLong(orgArr[j+1]));
							routeTowardsDtl.setSequence(j+1);
							routeTowardsDtl.setBeginOrgName(OraganizationCacheDataUtil.getOrgName(Long.parseLong(orgArr[j])));
							routeTowardsDtl.setEndOrgName(OraganizationCacheDataUtil.getOrgName(Long.parseLong(orgArr[j+1])));
							routeTowardsDtl.setTenantId(tenantId);
							session.saveOrUpdate(routeTowardsDtl);
						}
					}
				}
				Criteria catd = session.createCriteria(RouteTowardsDtl.class);
				catd.add(Restrictions.eq("towardsId", routeTowards.getTowardsId()));
				List<RouteTowardsDtl> l = catd.list();
				if(l.size()==0 || l.size() != (orgArr.length-1) ){
					Criteria cat = session.createCriteria(RouteTowards.class);
					cat.add(Restrictions.eq("towardsId", routeTowards.getTowardsId()));
					List<RouteTowards> lt = cat.list();
					RouteTowards temp = lt.get(0);
					session.delete(temp);
					for(RouteTowardsDtl rtd:l){
						session.delete(rtd);
					}
				} else {
					//放入新的数据
					routeTowardsMap.put(routeTowards, orgArr.length);
					
				}
		    }
		}
	}
	
	// 计算线路
	private  void   printRoute(int x,List<RouteTowards>  list,List<RouteRouting> routingList,long tenantId) {
		Stack<Integer> s = new Stack<Integer>();
		if (x == 0) {
			s.push(x);
			this.visited[x][x] = true;
		}
		while (!s.empty()) {
			int last = s.lastElement();
			int next = getLTPoint(last,s);
			if (next == -1) {
				s.pop();
				continue;
			}
			//由last 到 next已经访问过，不能说整个next都访问过
			visited[last][next] = true;
			s.push(next);
			if (this.checkRuleVisited(s) > 2) {
				this.setRuleVisited(last, next);
				s.pop();
				visited[last][next] = false;
				continue;
			}
			this.print(s, list,routingList,tenantId);
		}
		
	}
	
	// 查找节点在数组中的位置
	@SuppressWarnings("rawtypes")
	private int getIndex(List l, String str) {
		for (int i = 0; i < l.size(); i++) {
			String value = String.valueOf(l.get(i));
			if (value.equals(str)) {
				return i;
			}
		}
		return -1;
	}



	// 查找入参节点能联通的节点
	private int getLTPoint(int i,Stack<Integer> s) {
		for (int j = 0; j < pc; j++) {
			if (lines[i][j] == 1) {
				if (this.isRuleVisited(i, j)) {
					continue;
				}
				if (this.visited[i][j] == true) {
					continue;
				}
				if(s.contains(j)){
					continue;//节点不能重复访问
				}
				return j;
			}
		}
		return -1;
	}

	@SuppressWarnings("rawtypes")
	private boolean isRuleVisited(int i, int j) {
		if (this.ruleLineMap.get(i + "") == null) {
			return false;
		} else {
			List l = (ArrayList) this.ruleLineMap.get(i + "");
			for (int k = 0; k < l.size(); k++) {
				if (l.get(k).equals(j)) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setRuleVisited(int i, int j) {
		List l = new ArrayList();
		if (this.ruleLineMap.get(i + "") == null) {
			l.add(j);
			ruleLineMap.put("" + i, l);
		} else {
			l = (ArrayList) this.ruleLineMap.get(i + "");
			l.add(j);
		}
	}

	@SuppressWarnings("rawtypes")
	private int checkRuleVisited(Stack s) {
		int count = 0;
		for (int i = 0; i < s.size(); i++) {
			count = count + this.getPointType(Integer.parseInt(s.get(i) + ""));
		}
		return count;
	}
		
	public class RouteTowardsMap { 
		public Map<String,String> routeTowardsMap;
		public String regex = "#";
		
		public RouteTowardsMap(){
			routeTowardsMap = new HashMap<String,String>();
	}
		
	@SuppressWarnings("unchecked")
	public RouteTowardsMap(List<RouteTowards> routeTowardsList){
		Session session = SysContexts.getEntityManager(true);
		routeTowardsMap = new HashMap<String,String>();
		if(routeTowardsList != null){
			for(RouteTowards routeTowards:routeTowardsList){
				Criteria catd = session.createCriteria(RouteTowardsDtl.class);
				catd.add(Restrictions.eq("towardsId", routeTowards.getTowardsId()));
				List<RouteTowardsDtl> l = catd.list();
				routeTowardsMap.put(routeTowards.getBeginOrgId()+regex+routeTowards.getEndOrgId(), l.size()+regex+routeTowards.getTowardsId());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void put(RouteTowards routeTowards,int count){
		Session session = SysContexts.getEntityManager(false);
		long deltowardsId = this.getTowardsId(routeTowards.getBeginOrgId(), routeTowards.getEndOrgId());
		if(deltowardsId>0){
			//删除原来存在的数据
			RouteTowards routetowards = (RouteTowards)session.get(RouteTowards.class, deltowardsId);
			if(routetowards != null){
				session.delete(routetowards);
			}
			
			Criteria del = session.createCriteria(RouteTowardsDtl.class);
			del.add(Restrictions.eq("towardsId", deltowardsId));
			List<RouteTowardsDtl> dtlList = del.list();
			if(dtlList != null && dtlList.size()>0){
				for(RouteTowardsDtl rtd:dtlList){
					session.delete(rtd);
				}
			}
			
		}
		routeTowardsMap.put(routeTowards.getBeginOrgId()+regex+routeTowards.getEndOrgId(), count+regex+routeTowards.getTowardsId());
	}
	
	public int getCount(long beginOrgId,long endOrgId){
		int count=0;
		if(this.containsKey(beginOrgId,endOrgId)){
			String[] values = this.get(beginOrgId,endOrgId);
			count = Integer.parseInt(values[0]);
		}
		return count;
	}
			
	public long getTowardsId(long beginOrgId,long endOrgId){
		long towardsId = 0L;
		if(this.containsKey(beginOrgId,endOrgId)){
			String[] values = this.get(beginOrgId,endOrgId);
			towardsId = Long.parseLong(values[1]);
		}
		return towardsId;
	}
			
	public boolean containsKey(long beginOrgId,long endOrgId){
		boolean isContains = false;
		if(routeTowardsMap.containsKey(beginOrgId+regex+endOrgId)){
			isContains = true;
		}
		return isContains;
	}
			
	public String[] get(long beginOrgId,long endOrgId){
		String[] values = null;
		if(this.containsKey(beginOrgId,endOrgId)){
			String value = routeTowardsMap.get(beginOrgId+regex+endOrgId);
			values = value.split(regex);
		}
		return values;
	}
   } 
	
}
