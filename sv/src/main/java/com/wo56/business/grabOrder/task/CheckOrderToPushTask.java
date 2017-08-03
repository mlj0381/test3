package com.wo56.business.grabOrder.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.core.cache.vo.SysCfg;
import com.framework.core.exception.BusinessException;
import com.framework.core.scheduler.impl.BaseTask;
import com.framework.core.scheduler.intf.ITask;
import com.framework.core.util.SysCfgUtil;
import com.wo56.business.grabOrder.out.BusiGrabConsts;
import com.wo56.business.grabOrder.out.BusiGrabControlOut;
import com.wo56.business.grabOrder.task.GrabMessageTask.GrabPushThread;
import com.wo56.common.sms.utils.EnumConsts;
/**
 * 检查待推送的订单是不是满足条件，需要再进行推送
 * 
 * @author liyiye
 *
 */
public class CheckOrderToPushTask extends BaseTask implements ITask {
	private static final Log log = LogFactory.getLog(CheckOrderToPushTask.class);
	private static ExecutorService executor = null;
	
	public String timerMessage(Map<String, Object> obj) {
		int successNum = 0;
		SysCfg sysCfg=  SysCfgUtil.getSysCfg(BusiGrabConsts.GRAB_TIME_INTERVAL,0);
		long minute=0;
		if(sysCfg==null || StringUtils.isEmpty(sysCfg.getCfgValue())){
			throw new BusinessException("抢单功能：sys_cfg 表的 GRAB_TIME_INTERVAL 值没有配置！");
		}
		
		minute=Long.valueOf(sysCfg.getCfgValue().trim());
		
		try {
			Integer threadCount = Integer.valueOf(obj.get(EnumConsts.SysCfg.SMS_SEND_THREAD_COUNT).toString());
			int threadCountInt = threadCount == null || threadCount.intValue() == 0 ? 10 : threadCount.intValue();
			 List<String> list=new ArrayList<String>();
			 Set<String> set=BusiGrabControlOut.getAllMultToSendOrder();
			 list.addAll(set);
			 if (list.size() > 0) {
					int size = list.size();
					int mod = size % threadCountInt;
					int pageCount = (size - size % threadCountInt) / threadCountInt;
					if (pageCount == 0) {
						threadCountInt = size;
					}
					int fromIndex = 0;
					int toIndex = 0;
					executor = Executors.newFixedThreadPool(threadCountInt);
					for (int i = 0, j = 0; i < threadCountInt; i++, j++) {
						if (j < mod) {
							fromIndex = toIndex;
							toIndex = fromIndex + (pageCount + 1);
						} else {
							fromIndex = toIndex;
							toIndex = fromIndex + (pageCount);
						}

						if (toIndex > size) {
							toIndex = size;
						}
						log.info("GrabMessageTask 第" + i + "个线程启动.......from[" + fromIndex + "]to[" + toIndex + "]");
						
						CheckOrderToPushThread messageThread = new CheckOrderToPushThread(list.subList(fromIndex, toIndex),minute, "GrabPushThread" + i);
						executor.execute(messageThread);
						if (toIndex == list.size()) {
							break;
						}
					}
					// 关闭启动线程
					executor.shutdown();
					// 等待子线程结束，再继续执行下面的代码
					executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
				}
			
		} catch (Exception ex) {
			log.error("发送失败", ex);
			throw new RuntimeException("发送失败", ex);
		} finally {
			
		}
		return "抢单发送条数：" + successNum;
	}
	
	
	class CheckOrderToPushThread extends Thread {
		private Log log = LogFactory.getLog(GrabPushThread.class);
		private List<String> orderIds;
		private long minute;
		public CheckOrderToPushThread(List<String> orderIds,long minute,String threadName) {
			super();
			this.orderIds = orderIds;
			this.minute=minute;
			super.setName(threadName);
		}
		
		public void run() {
			if(this.orderIds!=null && this.orderIds.size()>0){
				long now =new Date().getTime();
				for(String orderId:orderIds){
					Map<String, Object> orderMap= BusiGrabControlOut.getMultOrderInfoMap(orderId);
					if(orderMap!=null){
						Object sendTime=orderMap.get(BusiGrabConsts.MultOrder.SEND_TIME);
						double diff=((double)(now-Long.valueOf(sendTime.toString())))/(1000*60);
						if(diff>Double.valueOf(minute)){
							//如果时间超过了配置的时间，加入到待配送的订单，并删除多次发送的订单
							BusiGrabControlOut.addOrderInfoSet(orderId);
							BusiGrabControlOut.delMultOrderInfo(orderId);
						}
					}else{
						//如果set结构里面的订单有数据，属性的结构里面数据，数据异常，需要把属性结构的数据干掉
						BusiGrabControlOut.delMultOrderInfo(orderId);
					}
				}
			}
		}
	}
	
	@Override
	public String doTask(Map<String, Object> paramMap) throws Exception {
		timerMessage(paramMap);
		return "";
	}
	public static void main(String[] args) throws Exception {
		Map paramMap = new HashMap();
		paramMap.put(EnumConsts.SysCfg.SMS_SEND_THREAD_COUNT, 3);
		CheckOrderToPushTask task = new CheckOrderToPushTask();
		task.doTask(paramMap);
		
	}
	
}
