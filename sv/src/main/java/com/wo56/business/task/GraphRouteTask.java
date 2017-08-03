package com.wo56.business.task;

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
import com.framework.core.scheduler.impl.BaseTask;
import com.framework.core.scheduler.intf.ITask;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.route.vo.RouteRouting;
import com.wo56.business.route.vo.RouteTowards;
import com.wo56.business.route.vo.RouteTowardsDtl;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class GraphRouteTask extends BaseTask implements ITask{
	private static final Log log = LogFactory.getLog(GraphRouteTask.class);
	@Override
	public String doTask(Map<String, Object> arg0) throws Exception {
		// TODO Auto-generated method stub
		return saveRoute();
	}
	
	
	// 存储节点（网点）信息
	private List points;
	// 存储无向边信息（不连通0，联通 1）
	private int[][] lines;
	// 存储由于业务规则限制，X节点不能再访问的节点序列
	private Map ruleLineMap = new HashMap();
	// 节点类型
	private int[] pointType;
	// 存储访问过的节点信息
	private  boolean[] visited;
	// 节点总数
	private int pc = 0;

	// 添加联通的边，由于是无向边，所以双向联通
	public void setEdge(int i, int j) {
		try {
			if (i == j)
				return;
			lines[i][j] = 1;
			lines[j][i] = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public GraphRouteTask() {
		
	}
	
	// 初始化设置所有的边都不连通
	public GraphRouteTask(int n) {
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
		log.info("<<<<<<<<<<<<<web线路自动生成路由进程Task Start>>>>>>>>>>>>>");
		
			Session session = SysContexts.getEntityManager(false);
			Criteria ca = session.createCriteria(RouteRouting.class);
			ca.add(Restrictions.eq("status", SysStaticDataEnum.STS.VALID));
			ca.add(Restrictions.eq("isStandardLine", SysStaticDataEnum.IS_STANDARD_LINE.STANDARD_ROUTE));
			List<RouteRouting> list2 = ca.list();
			Criteria routeCa = session.createCriteria(RouteTowards.class);
			List<RouteTowards> routeTowardsList = routeCa.list();
			List<Organization> list = OraganizationCacheDataUtil.getOrganizationDataList();
			// 网点设置
			for(int j = 0; j < list.size(); j++){
				try {
				session = SysContexts.getEntityManager(false);
				List<String> points = new ArrayList<String>();
				List<String> centerPoints = new ArrayList<String>();
				Organization org = list.get(j);
				if(org.getOrgType()==null ||  org.getOrgType()==SysStaticDataEnum.ORG_TYPE.HEAD_OFFICE){
					continue;
				}
				points.add(String.valueOf(org.getOrgId()));
				for (int i = 0; i < list.size(); i++) {
					// 起始节点
					Organization organization = list.get(i);
					if(org.getOrgId()==organization.getOrgId()){
					}else{
						if(organization.getOrgType()!=null && organization.getOrgType()!=SysStaticDataEnum.ORG_TYPE.HEAD_OFFICE){
							// 其他非起始节点
							points.add(organization.getOrgId()+"");
							if(organization.getOrgType()==SysStaticDataEnum.ORG_TYPE.FOXTOWN_CENTER){
								// 中心节点
								centerPoints.add(organization.getOrgId()+"");
							}
						}
					}
				}
				GraphRouteTask g = new GraphRouteTask(points.size());
				g.points = points;
				for (int i = 0; i < centerPoints.size(); i++) {
					g.setPointType(g.getIndex(points, centerPoints.get(i)));
				}
				for (RouteRouting routeRouting : list2) {
					// 联通网点设置
					if(g.getIndex(points, routeRouting.getBeginOrgId().toString())!=-1&&g.getIndex(points, routeRouting.getEndOrgId().toString())!=-1){
						g.setEdge(g.getIndex(points, routeRouting.getBeginOrgId().toString()), g.getIndex(points, routeRouting.getEndOrgId().toString()));
					}
				}
				g.printRoute(0,routeTowardsList,list2);
				SysContexts.commitTransation();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.info("<<<<<<<<<<<<<web线路自动生成路由进程Task 异常>>>>>>>>>>>>>",e);
					SysContexts.rollbackTransation();
					e.printStackTrace();
				}finally{
				}
			}
		
		log.info("<<<<<<<<<<<<<web线路自动生成路由进程Task end>>>>>>>>>>>>>");
		return "Y";
	}
	
	private void print(Stack s,List<RouteTowards>  list,List<RouteRouting> routingList) {
		Date date=new Date(); 
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
				routeTowards.setBeginTenantId(beginOrg.getTenantId());
				routeTowards.setBeginOwnerName(beginOrg.getOrgName());
				routeTowards.setEndOwnerName(endOrg.getOrgName());
				routeTowards.setBeginOwnerRegion(beginOrg.getRegionId());
				routeTowards.setEndOwnerRegion(endOrg.getRegionId());
				routeTowards.setEndTenantId(endOrg.getTenantId());
				routeTowards.setCreateDate(date);
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
							session.saveOrUpdate(routeTowardsDtl);
						}
					}
				}
				Criteria catd = session.createCriteria(RouteTowardsDtl.class);
				catd.add(Restrictions.eq("towardsId", routeTowards.getTowardsId()));
				List<RouteTowardsDtl> l = catd.list();
				if(l.size()==0||l.size()!=(orgArr.length-1)){
					Criteria cat = session.createCriteria(RouteTowards.class);
					cat.add(Restrictions.eq("towardsId", routeTowards.getTowardsId()));
					List<RouteTowards> lt = cat.list();
					RouteTowards temp = lt.get(0);
					session.delete(temp);
					for(RouteTowardsDtl rtd:l){
						session.delete(rtd);
					}
				}
//				if(orgArr.length==2){
//						RouteTowardsDtl routeTowardsDtl = new RouteTowardsDtl();
//						for (RouteRouting routeRouting : routingList) {
//							if(Long.parseLong(orgArr[0])==routeRouting.getBeginOrgId().longValue()&&Long.parseLong(orgArr[1])==routeRouting.getEndOrgId().longValue()){
//								routeTowardsDtl.setRoutingId(routeRouting.getRoutingId());
//							}
//						}
//						routeTowardsDtl.setTowardsId(towardsId);
//						routeTowardsDtl.setBeginOrgId(Long.parseLong(orgArr[0]));
//						routeTowardsDtl.setEndOrgId(Long.parseLong(orgArr[1]));
//						routeTowardsDtl.setSequence(1);
//						routeTowardsDtl.setBeginOrgName(OraganizationCacheDataUtil.getOrgName(Long.parseLong(orgArr[0])));
//						routeTowardsDtl.setEndOrgName(OraganizationCacheDataUtil.getOrgName(Long.parseLong(orgArr[1])));
//						session.saveOrUpdate(routeTowardsDtl);
//				}
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
	
		public static void main(String[] args) throws Exception {
//			String[] param = { "SCAN", "AUTO_ROUTE" };
//			TaskFrameWork.getInstance();
//			TaskFrameWork.main(param);
			Map<String, Object> map=new HashMap<String, Object>();
			GraphRouteTask graphRouteTask= new GraphRouteTask();
			graphRouteTask.doTask(map);
		}
}
