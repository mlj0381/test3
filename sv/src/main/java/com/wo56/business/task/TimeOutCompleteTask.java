package com.wo56.business.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.scheduler.impl.BaseTask;
import com.framework.core.scheduler.intf.ITask;
import com.wo56.business.sche.ord.vo.SchedulerTask;
import com.wo56.business.sche.timeout.vo.WayTimeOut;
import com.wo56.business.sche.timeout.vo.WayTimeOutDtl;
import com.wo56.common.consts.SysStaticDataEnum;

/***
 * 当时效异常对于的节点完成，处理状态自动完成
 * 
 * */
public class TimeOutCompleteTask  extends BaseTask implements ITask{
	private static final Log log = LogFactory.getLog(TimeOutCompleteTask.class);
	
	@Override
	public String doTask(Map<String, Object> arg0) throws Exception {
		// TODO Auto-generated method stub
		queryTimeOutTask();
		return null;
	}
	
	public void queryTimeOutTask() throws Exception{
		log.info("<<<<<<<<<<<<<w每天2点扫描时效异常的任务task Start>>>>>>>>>>>>>");
		Session session = SysContexts.getEntityManager();
		Query sqlQuery = session.createSQLQuery("SELECT TASK_ID FROM WAY_TIME_OUT WHERE DEAL_STATE IN (:dealState)");
		sqlQuery.setParameterList("dealState", new Integer[]{SysStaticDataEnum.DEAL_STATE.DEALING,SysStaticDataEnum.DEAL_STATE.WAIT_DEAL});
		List<Long> taskIdList = sqlQuery.list();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT s.* FROM  scheduler_task as s  WHERE 1=1 AND s.sts=1  AND s.TASK_TYPE=1  AND s.IS_SCHE=1 AND s.TASK_ID IN (:taskId) ");
		try {
			Query query = session.createSQLQuery(sb.toString()).addEntity("s",SchedulerTask.class);
			query.setParameterList("taskId",taskIdList);
			List list = query.list();
			for (int i=0;i<list.size();i++) {
//				Object obj[] = (Object[]) list.get(i);
				SchedulerTask schedulerTask=(SchedulerTask)list.get(i);
				if(schedulerTask.getBelongObjType()==1&&schedulerTask.getDoObjType()==1){
					continue;
				}
				//干线超时
				if(schedulerTask.getDistributionTime()!=null&&schedulerTask.getAcceptTime()!=null){
					updateTimeOut(SysStaticDataEnum.TIME_OUT_TYPE.GX_END_TIME_OUT_,schedulerTask.getTaskId(),session);
				}
				 if(schedulerTask.getTaskState()==SysStaticDataEnum.TASK_STATE.WAIT_SIGN){
					 updateTimeOut(SysStaticDataEnum.TIME_OUT_TYPE.YY_TIME_OUT,schedulerTask.getTaskId(),session);
					}
				 if(schedulerTask.getTaskState()==SysStaticDataEnum.TASK_STATE.DO_SIGN){
					 updateTimeOut(SysStaticDataEnum.TIME_OUT_TYPE.SIGN_TIME_OUT,schedulerTask.getTaskId(),session);
					}
			}
			
		} catch (Exception e) {
			log.info("扫描时效异常的任务task异常信息"+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("<<<<<<<<<<<<<每天2点扫描时效异常的任务task end>>>>>>>>>>>>>");
		
		
	}

	private void updateTimeOut(Integer timeOutType,long taskId,Session session){
		Criteria ca = session.createCriteria(WayTimeOut.class);
		ca.add(Restrictions.eq("taskId", taskId));
		ca.add(Restrictions.eq("timeOueType", timeOutType));
		List<WayTimeOut> list2 = ca.list();
		for (WayTimeOut wayTimeOut : list2) {
			wayTimeOut.setDealState(SysStaticDataEnum.DEAL_STATE.FINISH_DEAL);
			session.saveOrUpdate(wayTimeOut);
			WayTimeOutDtl wayTimeOutDtl=new WayTimeOutDtl();
			wayTimeOutDtl.setCreateTime(new Date(System.currentTimeMillis()));
			wayTimeOutDtl.setDealId(0L);
			wayTimeOutDtl.setDealIdea("系统扫描到"+wayTimeOut.getTimeOutTypeName()+"已完成。系统自动设置为已完成！");
			wayTimeOutDtl.setDealSatte(SysStaticDataEnum.DEAL_STATE.FINISH_DEAL);
			wayTimeOutDtl.setSts(SysStaticDataEnum.STS.VALID);
			wayTimeOutDtl.setTimeOutId(wayTimeOut.getTimeOutId());
			session.saveOrUpdate(wayTimeOutDtl);
		}
		SysContexts.commitTransation();
		
	}
	

}
