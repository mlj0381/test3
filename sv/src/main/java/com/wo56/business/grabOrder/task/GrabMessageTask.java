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

import com.framework.core.SysContexts;
import com.framework.core.cache.CacheFactory;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.scheduler.impl.BaseTask;
import com.framework.core.scheduler.intf.ITask;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.cm.vo.CmOrgRel;
import com.wo56.business.grabOrder.out.BusiGrabConsts;
import com.wo56.business.grabOrder.out.BusiGrabControlOut;
import com.wo56.common.cache.CmOrgRelCache;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.sms.utils.EnumConsts;
import com.wo56.common.utils.SmsYQUtil;
/**
 * 推送抢单的消息
 * 
 * 
 * @author liyiye
 *
 */
public class GrabMessageTask extends BaseTask implements ITask {
	private static final Log log = LogFactory.getLog(GrabMessageTask.class);
	private static ExecutorService executor = null;

	public String timerMessage(Map<String, Object> obj) {
		int successNum = 0;
		try {
			Integer threadCount = Integer.valueOf(obj.get(EnumConsts.SysCfg.SMS_SEND_THREAD_COUNT).toString());
			int threadCountInt = threadCount == null || threadCount.intValue() == 0 ? 10 : threadCount.intValue();
			 List<String> list=new ArrayList<String>();
			 Set<String> set=BusiGrabControlOut.getAllToSendOrder();
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
						
						GrabPushThread messageThread = new GrabPushThread(list.subList(fromIndex, toIndex), "GrabPushThread" + i);
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
	
	/**
	 * 根据订单号，订单推送过的次数（次数决定推送的范围）
	 * 
	 * 
	 * @author liyiye
	 */
	class GrabPushThread extends Thread {
		private Log log = LogFactory.getLog(GrabPushThread.class);
		private List<String> orderIds;

		public GrabPushThread(List<String> orderIds,String threadName) {
			super();
			this.orderIds = orderIds;
			super.setName(threadName);
		}
		
		public void run() {
			long dateTime=0;
			List<CmOrgRel> cmOrgRels=null;
			List<String> busiTenantIdList=null;
			
			for(String orderId :orderIds){
				log.error("抢单进程:订单号["+orderId+"]开始匹配");
				//获取订单的信息
				Map<String,Object> orderMap= BusiGrabControlOut.getOrderInfoMap(orderId);
				if(orderMap==null){
					continue;
				}
				Object x=orderMap.get(BusiGrabConsts.Order.LATITUDE);
				Object y=orderMap.get(BusiGrabConsts.Order.LONGITUDE);
				Object count=orderMap.get(BusiGrabConsts.Order.SEND_COUNT);
				Object orderNo=orderMap.get(BusiGrabConsts.Order.ORDER_NO);
				Object pickAddr=orderMap.get(BusiGrabConsts.Order.PICK_ADDR);
				Object handlerTime=orderMap.get(BusiGrabConsts.Order.HANDLER_TIME);
				Object createTime=orderMap.get(BusiGrabConsts.Order.PICK_ADDR);
				Object zxTenantId=orderMap.get(BusiGrabConsts.Order.ZX_TENANT_ID);
				
				
				if(x==null || y==null || orderNo==null || pickAddr==null || createTime==null){
					//没有经纬度的，不需要处理，需要删除掉
					BusiGrabControlOut.delAllOrderInfo(orderId);
					continue;
				}
				
				int countInt=(count==null ? 0:Integer.valueOf(count.toString()));
				
				log.error("抢单进程:订单号["+orderId+"]开始匹配,第["+countInt+"]轮");
				
				//数据为空，或者已经第三轮了，需要把订单的数据在抢单缓存里面全部删除 
				if(countInt==3){
					// 删除抢单的缓存数据
					BusiGrabControlOut.delOrderInfo(orderId);
					return;
				}
				
				
				
				double distance =getDistanceFromCount(countInt);
				//获取一定范围内的用户的数据,如果在一定范围内没有用户，放大范围，最多放大2次
				Set<String> userIds= BusiGrabControlOut.getUserByDistance(Double.valueOf(x.toString()), Double.valueOf(y.toString()), distance);
				if(userIds.size()==0 && countInt==0){
					countInt=countInt+1;
					distance =getDistanceFromCount(countInt);
					//获取一定范围内的用户的数据
					userIds= BusiGrabControlOut.getUserByDistance(Double.valueOf(x.toString()), Double.valueOf(y.toString()), distance);
					if(userIds.size()==0 && countInt==1){
						countInt=countInt+1;
						distance =getDistanceFromCount(countInt);
						//获取一定范围内的用户的数据
						userIds= BusiGrabControlOut.getUserByDistance(Double.valueOf(x.toString()), Double.valueOf(y.toString()), distance);
					}
				}
				log.error("抢单进程:订单号["+orderId+"]开始匹配,用户数量["+userIds.size()+"]");
				
				//指定专线 并且是第二轮抢单，需要查询订单的指定的专线，跟拿拉包公司的数据
				if(countInt==1 && zxTenantId!=null){
					cmOrgRels= (List<CmOrgRel>)CacheFactory.get(CmOrgRelCache.class.getName(), SysStaticDataEnumYunQi.CACHE.CM_ORG_REL+zxTenantId.toString());
					busiTenantIdList=new ArrayList<String>();
					if(cmOrgRels!=null){
						for(CmOrgRel cmOrgRel:cmOrgRels){
							busiTenantIdList.add(cmOrgRel.getBusiOrgId().toString());
						}
					}
					
				}else{
					cmOrgRels=null;
					busiTenantIdList=null;
				}
				
				try {
					if(userIds!=null && userIds.size()>0){
						for(String userId:userIds){
							log.error("抢单进程:订单号["+orderId+"]开始匹配,匹配的用户["+userId+"]");
							pushMsg(orderNo, pickAddr, handlerTime, createTime, orderId, userId, countInt, zxTenantId, busiTenantIdList);
						}
					}
					//发送成功后，需要在待推送的订单里面进行删除，并且把订单号加入到多次推送的数据
					dateTime=new Date().getTime();
					BusiGrabControlOut.delOrderInfo(orderId);
					BusiGrabControlOut.addMultOrderInfo(orderId, dateTime, (countInt+1));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		/**
		 * 保存需要推送的抢单的信息
		 * 
		 * @param orderNo
		 * @param pickAddr
		 * @param handlerTime
		 * @param createTime
		 * @param orderId
		 * @param userId
		 * @throws NumberFormatException
		 * @throws Exception
		 */
		private void pushMsg(Object orderNo,Object pickAddr,Object handlerTime
				,Object createTime,String orderId,String userId
				,int count,Object zxTenantId
				,List<String> busiTenantIdList) throws NumberFormatException, Exception{
			
			Map<String,Object> userMap=BusiGrabControlOut.getUserInfo(userId);
			
			Object rest= userMap.get(BusiGrabConsts.User.REST);//上下班的状态
			Object bill=userMap.get(BusiGrabConsts.User.BILL);//手机号码
			Object tanantId=userMap.get(BusiGrabConsts.User.TENANT_ID );//拉包工的租户
			
			if(rest!=null && SysStaticDataEnumYunQi.PULL_WORK_YUNQI.PULL_WORK1==Integer.valueOf(rest.toString())){
				//拉包工的状态为上班的时候，才进行推送
				Object maxNum= userMap.get(BusiGrabConsts.User.MAX_SINGULAR_NUM);//最大接单数
				Object num= userMap.get(BusiGrabConsts.User.SINGULAR_NUM);//当前的接单数
				//接单数的值都为空的时候 或者 接单数小于 最大的接单数,才进行推送
				if((maxNum==null || num==null)  
							|| (Integer.valueOf(num.toString()).intValue()<Integer.valueOf(maxNum.toString()).intValue())){
					String handlerTimeStr=(handlerTime==null ? "" :handlerTime.toString());
					if(zxTenantId==null){
						//客户下单--不指定专线
						//推送消息
						log.error("抢单进程:订单号["+orderId+"]开始匹配,匹配的用户["+userId+"],没有指定专线");
						SmsYQUtil.grabOrder(orderNo.toString(), pickAddr.toString(), createTime.toString(), handlerTimeStr, Long.valueOf(orderId), bill.toString());
					}else{
						//客户下单--选择了专线
						log.error("抢单进程:订单号["+orderId+"]开始匹配,匹配的用户["+userId+"],指定专线["+zxTenantId+"]");
						if(count==0){
							//第一轮，推送给专线公司下面的拉包工
							if(zxTenantId.toString().equals(tanantId.toString())){
								log.error("抢单进程:订单号["+orderId+"]开始匹配,匹配的用户["+userId+"],第一轮匹配成功，指定专线["+zxTenantId+"],用户的专线["+tanantId.toString()+"]");
								SmsYQUtil.grabOrder(orderNo.toString(), pickAddr.toString(), createTime.toString(), handlerTimeStr, Long.valueOf(orderId), bill.toString());
							}else{
								log.error("抢单进程:订单号["+orderId+"]开始匹配,匹配的用户["+userId+"],第一轮匹配不成功，指定专线["+zxTenantId+"],用户的专线["+tanantId.toString()+"]");
							}
						}else if(count==1){
							//第二轮 专线下面的拉包工
							if(zxTenantId.toString().equals(tanantId.toString())){
								log.error("抢单进程:订单号["+orderId+"]开始匹配,匹配的用户["+userId+"],第二轮匹配专线成功，指定专线["+zxTenantId+"],用户的专线["+tanantId.toString()+"]");
								SmsYQUtil.grabOrder(orderNo.toString(), pickAddr.toString(), createTime.toString(), handlerTimeStr, Long.valueOf(orderId), bill.toString());
							}else  if(busiTenantIdList.contains(tanantId.toString())){
								log.error("抢单进程:订单号["+orderId+"]开始匹配,匹配的用户["+userId+"],第二轮匹配拉包公司成功，指定专线["+zxTenantId+"],用户的专线["+tanantId.toString()+"]");
								//推送给跟专线有关的拉包公司
								SmsYQUtil.grabOrder(orderNo.toString(), pickAddr.toString(), createTime.toString(), handlerTimeStr, Long.valueOf(orderId), bill.toString());
							}else{
								log.error("抢单进程:订单号["+orderId+"]开始匹配,匹配的用户["+userId+"],第二轮匹配不成功，指定专线["+zxTenantId+"],用户的专线["+tanantId.toString()+"]");
							}
							
						}else if(count==2){
							//第三轮，推送给所有的拉包工
							log.error("抢单进程:订单号["+orderId+"]开始匹配,匹配的用户["+userId+"],第三轮匹配成功");
							SmsYQUtil.grabOrder(orderNo.toString(), pickAddr.toString(), createTime.toString(), handlerTimeStr, Long.valueOf(orderId), bill.toString());
						}else{
							log.error("抢单进程:订单号["+orderId+"]开始匹配,匹配的用户["+userId+"],失败，次数不对["+count+"]");
						}
					}
				}else{
					log.error("抢单进程:订单号["+orderId+"]开始匹配,匹配的用户["+userId+"],接单数["+(num==null ?"null":num.toString()) +"],最大接单数["+(num==null ?"null":maxNum.toString()) +"]");
				}
				
			}else{
				log.error("抢单进程:订单号["+orderId+"]开始匹配,匹配的用户["+userId+"],上班的状态["+rest+"]");
			}
		}
		
		
		/**
		 * 根据发送次数获取
		 * @param count
		 * @return
		 */
		private double getDistanceFromCount(int count){
			SysStaticData data= SysStaticDataUtil.getSysStaticData(BusiGrabConsts.COUNT_AND_DISTANCE, String.valueOf(count));
			String distance=data.getCodeName();
			if(StringUtils.isEmpty(distance)){
				throw new BusinessException("抢单进程报错：sys_static_data 没有配置 COUNT_AND_DISTANCE 数据,count["+count+"]");
			}
			return Double.valueOf(distance);
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
		GrabMessageTask task = new GrabMessageTask();
		task.doTask(paramMap);
		SysContexts.commitTransation();
	}
}
