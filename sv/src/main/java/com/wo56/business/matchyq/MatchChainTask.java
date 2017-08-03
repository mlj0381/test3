package com.wo56.business.matchyq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.components.match.MatchMutilThread;
import com.framework.components.match.chain.MatchCommandChain;
import com.framework.components.match.common.MatchConsts;
import com.framework.components.match.common.MatchDataUtils;
import com.framework.core.exception.BusinessException;
import com.framework.core.scheduler.impl.BaseTask;
import com.framework.core.scheduler.intf.ITask;
import com.framework.core.util.DataFormat;
import com.wo56.business.matchyq.impl.BusiMatchControlYq;

public class MatchChainTask extends BaseTask implements ITask {
	private static final Log logger = LogFactory.getLog(MatchChainTask.class);
	
	@Override
	public String doTask(Map<String, Object> paramMap) throws Exception {
		if(logger.isInfoEnabled()){
			logger.info("智能匹配开始执行...");
		}
		StopWatch watch=new StopWatch();
		watch.start();
		
		if (!paramMap.containsKey(MatchConsts.TaskParamKey.groupKey)) {
			throw new BusinessException("未配置task参数[匹配执行链分组]: " +MatchConsts.TaskParamKey.groupKey);
		}
		String groupName = DataFormat.getStringKey(paramMap, MatchConsts.TaskParamKey.groupKey);
		int threadNum = 1;
		if (paramMap.containsKey(MatchConsts.TaskParamKey.threadNum)) {
			threadNum = DataFormat.getIntKey(paramMap, MatchConsts.TaskParamKey.threadNum);
		}
		final MatchCommandChain process = new MatchCommandChain(groupName);
		
		//多线程处理
		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
		final List<String> aJsonList = MatchDataUtils.getAQueue();
		if(logger.isInfoEnabled()){
			logger.info("匹配线程数："+threadNum+", 任务数>>>" + aJsonList.size());
		}
		Thread matchThread = null;
		for (int i=0; i<threadNum; i++) {
			matchThread = new MatchMutilThread(paramMap, process, i, threadNum, aJsonList);
			executor.execute(matchThread);
		}
		
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.HOURS);
		
		watch.stop();
		if(logger.isInfoEnabled()){
			logger.info("智能匹配结束! 耗时["+watch.toString()+"]");
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		
		//添加A端数据 租户2001，添加 三个拉包工 2个属于同一个租户，一个距离近一些，一个距离远一些， 
		
		
		BusiMatchControlYq.addOrderInfo(1000001L, 2001L, "23.169629", "113.457109",1L);
		
		BusiMatchControlYq.loginAndWork(30001L, 2001L, 5, 23.168159, 113.459948);
		
		BusiMatchControlYq.loginAndWork(30002L, 2001L, 5, 23.169721, 113.458946);
		
		BusiMatchControlYq.loginAndWork(30003L, 2002L, 5, 23.168159, 113.459948);
		
		Map paramMap = new HashMap();
		paramMap.put(MatchConsts.TaskParamKey.groupKey, "SCHE_MATCH");
		paramMap.put(MatchConsts.TaskParamKey.listenerKey, "com.wo56.business.matchyq.impl.BusiMatchResultListenerYq");
		paramMap.put(MatchConsts.TaskParamKey.threadNum, 3);
		paramMap.put(MatchConsts.TaskParamKey.Limit, 1);
		MatchChainTask task = new MatchChainTask();
		task.doTask(paramMap);
		
		BusiMatchControlYq.removeOrderInfo(1000001L);
		
		BusiMatchControlYq.rest(30001L);
		BusiMatchControlYq.rest(30002L);
		BusiMatchControlYq.rest(30003L);
		  
		
//		System.out.println(GpsUtil.getLongDistance(23.169629, 113.457109, 23.168159, 113.459948));
//		System.out.println(GpsUtil.getLongDistance(23.169629, 113.457109, 23.169721, 113.458946));
		
//		BusiMatchControlYq.addOrderInfo(1000002L, 2001L, 23.168159, 113.459948);
		
//		System.out.println(GpsUtil.getLongDistance(23.169629, 113.457109, 23.168159, 113.459948));
		
	}

}
