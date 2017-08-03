package com.wo56.business.task;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.scheduler.TaskFrameWork;
import com.framework.core.scheduler.impl.BaseTask;
import com.framework.core.scheduler.intf.ITask;
import com.wo56.business.route.vo.TaskRouteFeeRetry;

public class RouteTask extends BaseTask implements ITask{

	@Override
	public String doTask(Map<String, Object> arg0) throws Exception {
		// TODO Auto-generated method stub
		queryRouteFeeRetry();
		return null;
	}
	
	public void queryRouteFeeRetry() throws Exception{

	}
	public static void main(String[] args) throws Exception {
		String[] param = { "SCAN", "ROUTE" };
		TaskFrameWork.getInstance();
		TaskFrameWork.main(param);
	}
}
