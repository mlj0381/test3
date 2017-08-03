package com.wo56.business.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.components.match.common.MatchConsts;
import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.scheduler.impl.BaseTask;
import com.framework.core.scheduler.intf.ITask;
import com.wo56.business.cm.intf.CmUserInfoPullTF;
import com.wo56.business.cm.vo.CmPullBlack;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.business.ord.intf.OrdLogNewTF;
import com.wo56.business.ord.vo.OrdBusiPerson;
import com.wo56.business.ord.vo.OrdOrdersInfo;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.SmsYQUtil;

public class TestOrderTask  extends BaseTask implements ITask {
	private static ExecutorService executor = null;
	private static transient Log log = LogFactory.getLog(TestOrderTask.class);
	
	@SuppressWarnings("unchecked")
	public String timerMessage(Map<String, Object> obj) {
		int successNum = 0;
		Session session = null;
		try {
				
				session = SysContexts.getEntityManager();
				Criteria ca = session.createCriteria(CmUserInfo.class);
				ca.add(Restrictions.eq("state", SysStaticDataEnumYunQi.STS.VALID));
				ca.add(Restrictions.eq("userType", SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL));
				List<CmUserInfo> list = ca.list();
				log.info("MessageTask 抢单次数[" + list.size() + "]");
				Object oCount = 10;
				Integer threadCount = oCount != null ? Integer.valueOf(oCount.toString()) : null;
				int threadCountInt = threadCount == null || threadCount.intValue() == 0 ? 10 : threadCount.intValue();
				// 当列表数量大于于配置的进程，例如 发送的短信18，配置的进程10
				// 当列表数量少于配置的进程，例如 发送的短信5，配置的进程10
				// 当列表数量等于配置的进程，例如 发送的短信10，配置的进程10
				session.flush();
				session.clear();
				if (list.size() > 0) {
					int size = list.size();
					int mod = size % threadCountInt;
					int pageCount = (size - size % threadCountInt) / threadCountInt;
					if (pageCount == 0) {
						threadCountInt = size;
					}
					int fromIndex = 0;
					int toIndex = 0;
					//long sta = new Date().getTime();
					
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
						log.info("MessageTask 第" + i + "个线程启动.......from[" + fromIndex + "]to[" + toIndex + "]");

						MessageThread messageThread = new MessageThread(list.subList(fromIndex, toIndex), "MessageThread" + i, obj);
						executor.execute(messageThread);
						if (toIndex == list.size()) {
							break;
						}
					}
					// 关闭启动线程
					executor.shutdown();
					// 等待子线程结束，再继续执行下面的代码
					executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
					//System.out.println(new Date().getTime() - sta);
				}
		} catch (Exception ex) {
			log.error("推送失败", ex);
			throw new RuntimeException("推送失败", ex);
		} finally {
			closeSession(session);
		}
		return "短信成功发送条数：" + successNum;
	}
	
	
	
	class MessageThread extends Thread {
		private Log log = LogFactory.getLog(MessageThread.class);
		private List<CmUserInfo> list;
		private Map<String, Object> paramMap;

		public MessageThread(List<CmUserInfo> list, String threadName, Map<String, Object> paramMap) {
			super();
			this.list = list;
			this.paramMap = paramMap;
			super.setName(threadName);
		}

		public void run() {
			log.info("MessageThread,threadName[ " + this.getName() + "]开始处理....");
			StopWatch watch = new StopWatch();
			watch.start();
			if (list.size() > 0) {
				for (CmUserInfo temp : list) {
					try {
						Session session = SysContexts.getEntityManager();
						Date date = new Date();
						long userId = temp.getUserId();
						if (temp.getUserType() != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL) {
							throw new BusinessException("抢单失败！");
						}
						CmUserInfoPullTF cmUserInfoPullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
						CmUserInfoPull pull = cmUserInfoPullTF.getByUserPull(userId);
						if (pull == null) {
							throw new BusinessException("抢单失败！");
						}
						//（1）校验考勤：是否上班状态；
						if (pull.getPullWork() != SysStaticDataEnumYunQi.PULL_WORK_YUNQI.PULL_WORK1) {
							throw new BusinessException("抢单失败，请打卡上班！");
						}
						//（2）校验接单数：是否接单已经超过最大接单数；
						if (pull.getSingularNum() != null && pull.getDefaultSingularNum() != null && pull.getSingularNum().longValue() >=  pull.getDefaultSingularNum().longValue()) {
							throw new BusinessException("抢单失败，已经超过当天可以接的单数，请完成单再接单，谢谢！");
						}
						Criteria ca = session.createCriteria(CmPullBlack.class);
						List<CmPullBlack> blackList = ca.add(Restrictions.eq("bill", temp.getLoginAcct())).add(Restrictions.eq("status", SysStaticDataEnumYunQi.STS.VALID)).list();
						CmPullBlack black = null;
						boolean isBlack = false;
						if(blackList != null && blackList.size()>0){
							black = blackList.get(0);
							isBlack = true;
						}else{
							isBlack = false;
						}
						long orderId = 80005436L;
						OrdOrdersInfo order = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
						OrdBusiPerson busi = (OrdBusiPerson) session.get(OrdBusiPerson.class, orderId);
						//（3）校验拉黑：是否被客户拉黑、是否被专线拉黑、是否被平台拉黑、是否有效；
						if(isBlack && black.getType().intValue() == SysStaticDataEnumYunQi.BLACK_TYPE.BLACK_PLAT){//平台
							throw new BusinessException("抢单失败，该单已经被抢了！");
						}
						if((isBlack && order.getCreateId() != null) && 
								((black.getType().intValue() == SysStaticDataEnumYunQi.BLACK_TYPE.BLACK_CUSTOMER 
									&& black.getBusinessId().longValue() == order.getCreateId().longValue()))){//客户
							throw new BusinessException("抢单失败，该单已经被抢了！");
						}
						if ((isBlack && order.getTenantId() != null) && 
								((black.getType().intValue() == SysStaticDataEnumYunQi.BLACK_TYPE.BLACK_CHAIN 
										&& black.getBusinessId().longValue() == order.getTenantId().longValue()))) {//专线
							throw new BusinessException("抢单失败，该单已经被抢了！");
						}
						
						//（4）校验单号：是否已经被抢的单，是否存在该单；
						if(order == null || order.getOrderState() != SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING 
								|| (busi.getWorkerId() != null && busi.getWorkerId() > 0)
								|| StringUtils.isNotEmpty(busi.getWorkerName())){
							throw new BusinessException("抢单失败，该单已经被抢了！");
						}
						//（5）抢单成功：修改订单信息、删除抢单的号码；
						StringBuffer upSql = new StringBuffer(" UPDATE ord_busi_person p,ord_orders_info o SET ");
						upSql.append(" p.WORKER_ID = :pullId, ");
						upSql.append(" p.WORKER_PHONE = :pullPhone, ");
						upSql.append(" p.WORKER_NAME = :pullName, ");
						upSql.append(" o.ORDER_STATE = :orderState, ");
						upSql.append(" o.DIS_ID = :disId, ");
						upSql.append(" o.DIS_TIME = :disTime, ");
						upSql.append(" o.DIS_OP_NAME = :disOpName ");
						upSql.append(" WHERE p.ORDER_ID = o.ORDER_ID AND o.ORDER_ID = :orderId AND o.ORDER_STATE = :queryState AND p.WORKER_ID is NULL ");
						
						Query query = session.createSQLQuery(upSql.toString());
						query.setParameter("pullId", userId);
						query.setParameter("pullPhone", temp.getLoginAcct());
						query.setParameter("pullName", temp.getUserName());
						query.setParameter("orderState", SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP);
						query.setParameter("disId", 0L);
						query.setParameter("disTime", date);
						query.setParameter("disOpName", "抢单");
						query.setParameter("orderId", orderId);
						query.setParameter("queryState", SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING);
						boolean isSuccess = false;
						int i = 0;
						try {
							i = query.executeUpdate();
						} catch (Exception e) {
							throw new BusinessException("抢单失败，该订单已被抢走了！");
						}
						if (i != 2) {
							throw new BusinessException("抢单失败，该订单已被抢走了！");
						}else{
							isSuccess = true;
						}
						
						//TODO 调用删除redis里的单号方法
						if (isSuccess) {
							cmUserInfoPullTF.addSingularNum(userId);
							//pull.setSingularNum(singularNum);
							OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
							//OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, orderId);
							// 写日志
							OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
							ordLogNewTF.disWorkerLog(0, temp.getLoginAcct(), null, ordOrdersInfo, temp.getUserName());
							
							Query queryLog = session.createSQLQuery("select PROVINCE_ID,city_id,address,consignor_bill,sum(consignor_bill) from ord_goods_info where order_id=:orderId");
							queryLog.setParameter("orderId", ordOrdersInfo.getOrderId());
							List<Object[]> list = queryLog.list();
					        //sendPullWorkerMul  一个拉包公 多个提货地址
							//判断 
							if(list.size() >1 ){
								for (Object[] objects : list) {
									SmsYQUtil.sendPullWorkerMul(-1L, ordOrdersInfo.getOrderNo(), 
											CommonUtil.getCityName(Long.parseLong(objects[0]+""),null, null)+CommonUtil.getCityName(null,Long.parseLong(objects[1]+""), null)+objects[2],EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, 
											ordOrdersInfo.getOrderId(), temp.getLoginAcct());
									if(objects[3]!=null&&StringUtils.isNotEmpty(objects[3]+"")){
										SmsYQUtil.disWorkerToConsigor(-1L, ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(), temp.getUserName(), temp.getLoginAcct(), objects[3].toString());
									}
								}
							}else {
								for (Object[] objects : list) {
									SmsYQUtil.sendPullWorker(-1L, ordOrdersInfo.getOrderNo(), 
											CommonUtil.getCityName(Long.parseLong(objects[0]+""),null, null)+CommonUtil.getCityName(null,Long.parseLong(objects[1]+""), null)+objects[2],EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, 
											ordOrdersInfo.getOrderId(), temp.getLoginAcct());
									if(objects[3]!=null&&StringUtils.isNotEmpty(objects[3]+"")){
										SmsYQUtil.disWorkerToConsigor(-1L, ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(), temp.getUserName(), temp.getLoginAcct(), objects[3].toString());
									}
								}
							}
//							for (Object[] objects : list) {
//								SmsYQUtil.sendPullWorker(-1L, ordOrdersInfo.getOrderNo(), 
//										CommonUtil.getCityName(Long.parseLong(objects[0]+""),null, null)+CommonUtil.getCityName(null,Long.parseLong(objects[1]+""), null)+objects[2],EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, 
//										ordOrdersInfo.getOrderId(), temp.getLoginAcct());
//								if(objects[3]!=null&&StringUtils.isNotEmpty(objects[3]+"")){
//									SmsYQUtil.disWorkerToConsigor(-1L, ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(), temp.getUserName(), temp.getLoginAcct(), objects[3].toString());
//								}
//							}
							log.error(temp.getLoginAcct()+ " 抢单成功！");
						}
						
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("info", "抢单成功！");
						// 框架会自己管理事务，这里特殊情况自己管理事务
						SysContexts.commitTransation();
					} catch (Exception e) {
						SysContexts.rollbackTransation();
						log.error("MessageThread threadName[ " + this.getName() + "]smsId: " + temp.getLoginAcct(), e);
					} 
				}
			}
			log.info("MessageThread,threadName[ " + this.getName() + "]处理结束，数量[" + list.size() + "],耗时[" + watch.toString() + "][" + watch.getTime() + "]");
		}

	}



	@Override
	public String doTask(Map<String, Object> arg0) throws Exception {
		timerMessage(arg0);
		return "进程启动成功";
	}
	
	public static void main(String[] args) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(MatchConsts.TaskParamKey.groupKey, "SCHE_MATCH_ORDER");
		paramMap.put(MatchConsts.TaskParamKey.listenerKey, "com.wo56.business.task.TestOrderTask");
		paramMap.put(MatchConsts.TaskParamKey.threadNum, 3);
		paramMap.put(MatchConsts.TaskParamKey.Limit, 5);
		paramMap.put("DeployStatus", 1);
		
		TestOrderTask cmUserInfoPullWorkTask = new TestOrderTask();
		cmUserInfoPullWorkTask.doTask(paramMap);
		SysContexts.commitTransation();
	}
}
