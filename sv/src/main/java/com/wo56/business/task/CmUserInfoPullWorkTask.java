package com.wo56.business.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.framework.components.match.common.MatchConsts;
import com.framework.core.SysContexts;
import com.framework.core.scheduler.impl.BaseTask;
import com.framework.core.scheduler.intf.ITask;
import com.framework.core.util.DateUtil;
import com.wo56.business.cm.vo.CmPullWorkHis;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.business.grabOrder.out.BusiGrabControlOut;
import com.wo56.business.matchyq.impl.BusiMatchControlYq;

public class CmUserInfoPullWorkTask extends BaseTask implements ITask {
	private static final Log log = LogFactory.getLog(CmUserInfoPullWorkTask.class);

	@Override
	public String doTask(Map<String, Object> arg0) throws Exception {
			try{
				doUpdatePullSate();
			}catch(Exception e){
				
			}
			
		return "成功";
	}

	/**
	 * 所有拉包工在00:00:00:00状态为休息中
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public void doUpdatePullSate() throws Exception {
		log.error("所有拉包工在00:00:00:00状态为休息中");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("yyyyMM", new String[] { DateUtil.formatDate(new Date(), DateUtil.YEAR_MONTH_FORMAT2) });
		Session session = SysContexts.getEntityManager(map);
		String sql = "update cm_user_info_pull set PULL_WORK=:state WHERE PULL_WORK=1 ";
		Integer state = 0;
		session.createSQLQuery(sql).setParameter("state", state).executeUpdate();
		Criteria ca = session.createCriteria(CmUserInfoPull.class);
		List<CmUserInfoPull> CmUserInfoPullList = ca.list();
		for (CmUserInfoPull cmUserInfoPull : CmUserInfoPullList) {
			Long userId = cmUserInfoPull.getUserId();
			CmPullWorkHis cmPullWorkHis = new CmPullWorkHis();
			Date date = new Date();
			cmPullWorkHis.setUserId(userId);
			cmPullWorkHis.setPullWork(state);
			cmPullWorkHis.setCreateTime(date);
			session.save(cmPullWorkHis);
//			BusiMatchControlYq busiMatchControlYq = new BusiMatchControlYq();
//			busiMatchControlYq.rest(userId);
			BusiGrabControlOut.updateUserInfoRest(userId, state);
		}
		
	}
	public static void main(String[] args) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(MatchConsts.TaskParamKey.groupKey, "TIMING_CLOCK");
		paramMap.put(MatchConsts.TaskParamKey.listenerKey, "com.wo56.business.task.CmUserInfoPullWorkTask");
	//	paramMap.put(MatchConsts.TaskParamKey.threadNum, 3);
		//.put(MatchConsts.TaskParamKey.Limit, 5);
		CmUserInfoPullWorkTask cmUserInfoPullWorkTask = new CmUserInfoPullWorkTask();
		cmUserInfoPullWorkTask.doTask(paramMap);
		SysContexts.commitTransation();
	}

}
