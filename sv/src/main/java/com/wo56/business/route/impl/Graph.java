package com.wo56.business.route.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.util.DataFormat;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.route.vo.RouteRouting;
import com.wo56.business.route.vo.RouteTowards;
import com.wo56.business.route.vo.RouteTowardsDtl;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class Graph {
	// 存储节点（网点）信息
	private List points;
	// 存储无向边信息（不连通0，联通 1）
	private int[][] lines;
	// 存储由于业务规则限制，X节点不能再访问的节点序列
	Map ruleLineMap = new HashMap();
	// 节点类型
	int[] pointType;
	// 存储访问过的节点信息
	public  boolean[] visited;
	// 节点总数
	int pc = 0;

	// 添加联通的边，由于是无向边，所以双向联通
	public void setEdge(int i, int j) {
		if (i == j)
			return;
		lines[i][j] = 1;
		lines[j][i] = 1;
	}

	// 初始化设置所有的边都不连通
	public Graph(int n) {
		lines = new int[n][n];
		pointType = new int[n];
		visited = new boolean[n];
		pc = n;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				lines[i][j] = 0;
			}
			pointType[i] = 0;
			visited[i] = false;
		}
	}

	public void setPointType(int i) {
		pointType[i] = 1;
	}

	public int getPointType(int i) {
		return pointType[i];
	}

	/***保存路由信息**/
	public String saveRoute() throws Exception{
		Session session = SysContexts.getEntityManager();
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		long orgId = DataFormat.getLongKey(map, "orgId");
		if(orgId<0){
			throw new BusinessException("网点ID不能为空!");
		}
		// 网点设置
		List<String> points = new ArrayList<String>();
		List<String> centerPoints = new ArrayList<String>();
		// 起始节点
		points.add(orgId+"");
		List<Organization> list = OraganizationCacheDataUtil.getOrganizationDataList();
		for (int i = 0; i < list.size(); i++) {
			Organization organization = list.get(i);
			if(orgId==organization.getOrgId()){
				list.remove(i);
			}else{
				// 其他非起始节点
				points.add(organization.getOrgId()+"");
				if(organization.getOrgType()==SysStaticDataEnum.ORG_TYPE.FOXTOWN_CENTER){
					// 中心节点
					centerPoints.add(organization.getOrgId()+"");
				}
			}
		}
		Graph g = new Graph(points.size());
		g.points = points;
		for (int i = 0; i < centerPoints.size(); i++) {
			g.setPointType(g.getIndex(points, centerPoints.get(i)));
		}
		Criteria ca = session.createCriteria(RouteRouting.class);
		ca.add(Restrictions.eq("status", SysStaticDataEnum.STS.VALID));
		ca.add(Restrictions.eq("isStandardLine", SysStaticDataEnum.IS_STANDARD_LINE.STANDARD_ROUTE));
		List<RouteRouting> list2 = ca.list();
		for (RouteRouting routeRouting : list2) {
			// 联通网点设置
			g.setEdge(g.getIndex(points, routeRouting.getBeginOrgId().toString()), g.getIndex(points, routeRouting.getEndOrgId().toString()));
		}
		Criteria routeCa = session.createCriteria(RouteTowards.class);
		List<RouteTowards> routeTowardsList = routeCa.list();
		g.printRoute(0,routeTowardsList,list2);
		return "Y";
	}
	
	private void print(Stack s,List<RouteTowards>  list,List<RouteRouting> routingList) {
		Session session = SysContexts.getEntityManager();
		StringBuffer towards=new StringBuffer();
		for (int i = 0; i < s.size(); i++) {
			if(i==s.size()){
				towards.append(s.get(i));
			}else{
				towards.append(s.get(i) + "-");
			}
		}
		String[] orgArr = towards.toString().split("-");
		long towardsId=0L;
		boolean isExist=false;
		if(orgArr.length>=2){
			for (RouteTowards routeTowards : list) {
				if(routeTowards.getBeginOrgId().longValue()==Long.parseLong(orgArr[0])&&routeTowards.getEndOrgId().longValue()==Long.parseLong(orgArr[orgArr.length-1])){
					isExist=true;
				}
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
				session.saveOrUpdate(routeTowards);
				towardsId=routeTowards.getTowardsId();
				for (int j = 0; j < orgArr.length; j++) {
					if(j<orgArr.length-2){
						RouteTowardsDtl routeTowardsDtl = new RouteTowardsDtl();
						for (RouteRouting routeRouting : routingList) {
							if(Long.parseLong(orgArr[j])==routeRouting.getBeginOrgId().longValue()&&Long.parseLong(orgArr[j+1])==routeRouting.getEndOrgId().longValue()){
								routeTowardsDtl.setRoutingId(routeRouting.getRoutingId());
								break;
							}
						}
						routeTowardsDtl.setTowardsId(towardsId);
						routeTowardsDtl.setBeginOrgId(Long.parseLong(orgArr[j]));
						routeTowardsDtl.setEndOrgId(Long.parseLong(orgArr[j+1]));
						routeTowardsDtl.setSequence(j+1);
						routeTowardsDtl.setBeginOrgName(OraganizationCacheDataUtil.getOrgName(Long.parseLong(orgArr[j])));
						routeTowardsDtl.setEndOrgName(OraganizationCacheDataUtil.getOrgName(Long.parseLong(orgArr[j+1])));
						session.saveOrUpdate(routeTowardsDtl);
					}
				}
		    }
		}
	}
	
	// 计算线路
	private  void   printRoute(int x,List<RouteTowards>  list,List<RouteRouting> routingList) {
		Stack<Integer> s = new Stack<Integer>();
		if (x == 0) {
			s.push(x);
			this.visited[x] = true;
		}
		while (!s.empty()) {
			int last = s.lastElement();
			int next = getLTPoint(last);
			if (next == -1) {
				s.pop();
				continue;
			}
			visited[next] = true;
			s.push(next);
			if (this.checkRuleVisited(s) > 2) {
				this.setRuleVisited(last, next);
				s.pop();
				visited[next] = false;
				continue;
			}
			this.print(s, list,routingList);
		}
		
	}
	
	// 查找节点在数组中的位置
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
		private int getLTPoint(int i) {
			for (int j = 0; j < pc; j++) {
				if (lines[i][j] == 1) {
					if (this.isRuleVisited(i, j)) {
						continue;
					}
					if (this.visited[j] == true) {
						continue;
					}
					return j;
				}
			}
			return -1;
		}

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

		private int checkRuleVisited(Stack s) {
			int count = 0;
			for (int i = 0; i < s.size(); i++) {
				count = count + this.getPointType(Integer.parseInt(s.get(i) + ""));
			}
			return count;
		}
		
		
	public static void main(String[] args) {
		// 网点设置
		List<String> points = new ArrayList<String>();
		// 起始节点
		points.add("A");
		// 其他非起始节点
		points.add("B");
		points.add("C");
		points.add("D");
		points.add("E");
		points.add("F");
		points.add("G");
		points.add("H");
		points.add("I");
		points.add("K");
		points.add("J");
		Graph g = new Graph(points.size());
		g.points = points;
		// 中心节点
		g.setPointType(g.getIndex(points, "C"));
		g.setPointType(g.getIndex(points, "D"));
		g.setPointType(g.getIndex(points, "G"));
		g.setPointType(g.getIndex(points, "K"));
		// 联通网点设置
		g.setEdge(g.getIndex(points, "A"), g.getIndex(points, "C"));
		g.setEdge(g.getIndex(points, "B"), g.getIndex(points, "C"));
		g.setEdge(g.getIndex(points, "C"), g.getIndex(points, "D"));
		g.setEdge(g.getIndex(points, "C"), g.getIndex(points, "G"));
		g.setEdge(g.getIndex(points, "D"), g.getIndex(points, "E"));
		g.setEdge(g.getIndex(points, "D"), g.getIndex(points, "F"));
		g.setEdge(g.getIndex(points, "D"), g.getIndex(points, "G"));
		g.setEdge(g.getIndex(points, "G"), g.getIndex(points, "H"));
		g.setEdge(g.getIndex(points, "G"), g.getIndex(points, "I"));
		g.setEdge(g.getIndex(points, "G"), g.getIndex(points, "C"));

		g.setEdge(g.getIndex(points, "K"), g.getIndex(points, "J"));
		g.setEdge(g.getIndex(points, "K"), g.getIndex(points, "C"));
		g.setEdge(g.getIndex(points, "K"), g.getIndex(points, "D"));
		g.setEdge(g.getIndex(points, "K"), g.getIndex(points, "G"));
		//g.printRoute(0,new ArrayList<RouteTowards>());
	}

	
}
