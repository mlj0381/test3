package com.wo56.business.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.framework.components.match.common.MatchConsts;
import com.framework.core.SysContexts;
import com.framework.core.scheduler.impl.BaseTask;
import com.framework.core.scheduler.intf.ITask;
import com.framework.core.util.DateUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ord.vo.OrderInfoTmsError;
import com.wo56.business.ord.vo.OrderInfoTmsErrorHis;
import com.wo56.business.order.out.OrderPostUtil;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.sms.utils.TableSplitRule;
import com.wo56.common.utils.BeanUtil;
import com.wo56.common.utils.CommonUtil;

public class OrderInfoTmsErrorTask extends BaseTask implements ITask{

	private static final String TABLE_SPLIT_RULE = "yyyyMM"; // 分表规则
	private static transient Log log = LogFactory.getLog(OrderInfoTmsErrorTask.class);

	
	public static void main(String[] args) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(MatchConsts.TaskParamKey.groupKey, "SCHE_MATCH");
		paramMap.put(MatchConsts.TaskParamKey.listenerKey, "com.wo56.business.task.OrderInfoTmsErrorTask");
		paramMap.put(MatchConsts.TaskParamKey.threadNum, 3);
		paramMap.put(MatchConsts.TaskParamKey.Limit, 5);
		paramMap.put("DeployStatus", 1);
		
		OrderInfoTmsErrorTask orderInfoTmsErrorTask = new OrderInfoTmsErrorTask();
		orderInfoTmsErrorTask.doTask(paramMap);
		SysContexts.commitTransation();
	}
		
	@Override
	public String doTask(Map<String, Object> arg0) throws Exception {
		timerMessage(arg0);
		return "进程启动成功";
	}
	/**
	 * 查询出前200条调TMS错误的单号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<OrderInfoTmsError> getOrderInfoList(Session session){
		Criteria ca = session.createCriteria(OrderInfoTmsError.class);
		//ca.addOrder(Order.desc("id"));
		ca.setFirstResult(0).setMaxResults(100);
		return ca.list();
	}
	/**
	 * 发送成功移除到历史表
	 * @param error
	 * @param session
	 * @throws Exception
	 */
	private void removeToHis(OrderInfoTmsError error,Session session) throws Exception{
		OrderInfoTmsError orderInfoTmsError = (OrderInfoTmsError) session.get(OrderInfoTmsError.class, error.getId());
		OrderInfoTmsErrorHis errorHis = new OrderInfoTmsErrorHis();
		BeanUtils.copyProperties(errorHis, orderInfoTmsError);
		errorHis.setId(orderInfoTmsError.getId());
		errorHis.setSendTime(new Date());
		errorHis.setSendFlag(1);
		session.save(errorHis);
		session.delete(orderInfoTmsError);
	}
	/**
	 * 调用TMS
	 * @param error
	 * @param session
	 * @throws Exception
	 */
	private void sendOrderInfoTms(OrderInfoTmsError error,Session session,Date date) throws Exception{
		if (StringUtils.isNotEmpty(error.getOrderNumber()) && error.getSendFlag() != 1) {
			String isArrival = "";
			if (error.getOrderState().intValue() == SysStaticDataEnumYunQi.ORDER_INFO_STATE.FLOATION_CARGO) {//到货
				isArrival = OrderPostUtil.arrivalKL(error.getOrderNumber(), StringUtils.defaultIfEmpty(error.getUserName(), ""),  StringUtils.defaultIfEmpty(DateUtil.formatDateByFormat(error.getCreateTime(), "yyyy-MM-dd"),""), error.getTenantId(),true);
			}else if (error.getOrderState().intValue() == SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY) {
				isArrival = OrderPostUtil.dispatcheingKL(error.getOrderNumber(), StringUtils.defaultIfEmpty(error.getUserName(), ""), StringUtils.defaultIfEmpty(DateUtil.formatDateByFormat(error.getCreateTime(), "yyyy-MM-dd"),""), error.getTenantId(),true);
			}else if (error.getOrderState().intValue() == SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN) {
				isArrival = OrderPostUtil.signKL(error.getOrderNumber(), StringUtils.defaultIfEmpty(error.getUserName(), ""), 
						StringUtils.defaultIfEmpty(DateUtil.formatDateByFormat(error.getCreateTime(), "yyyy-MM-dd"),""), 
						StringUtils.defaultIfEmpty(error.getCreateName(), ""), StringUtils.defaultIfEmpty(error.getPassPort(), ""), 
						StringUtils.defaultIfEmpty(error.getPassPortNo(), ""), StringUtils.defaultIfEmpty(DateUtil.formatDateByFormat(error.getSignTime(), "yyyy-MM-dd"),""),
						StringUtils.defaultIfEmpty(error.getSignName(),""), error.getTenantId(),true);
			}else if(error.getOrderState().intValue() == SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN){
				Map<String,Object> tmsInParam = new HashMap<String, Object>();
				tmsInParam.put("arrivedOrgName",error.getArrivedOrgName());
				tmsInParam.put("billingOrgName", error.getBillingOrgName());
				tmsInParam.put("carrierName", error.getCarrierName());
				tmsInParam.put("collectMoneyDouble",error.getCollectMoneyDouble());
				tmsInParam.put("consignee", error.getConsignee());
				tmsInParam.put("consigneeAddress", error.getConsigneeAddress());
				tmsInParam.put("consigneePhone", error.getConsigneePhone());
				tmsInParam.put("consignor", error.getConsignor());
				tmsInParam.put("consignorAddress", error.getConsignorAddress());
				tmsInParam.put("consignorPhone", error.getConsignorPhone());
				tmsInParam.put("createName", error.getCreateName());
				tmsInParam.put("createTime", DateUtil.formatDate(error.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				tmsInParam.put("deliveryChargeDouble", error.getDeliveryChargeDouble());
				tmsInParam.put("desCityName", error.getDesCityName());
				tmsInParam.put("desDistrictName",error.getDesDistrictName());
				tmsInParam.put("desProvinceName",error.getDesProvinceName());
				tmsInParam.put("freightDouble", error.getFreightDouble());
				tmsInParam.put("interchangeName", error.getInterchangeName());
				tmsInParam.put("landFeeDouble", error.getLandFeeDouble());
				tmsInParam.put("number", error.getNumber());
				tmsInParam.put("orderNumber", error.getOrderNumber());
				tmsInParam.put("otherFeeDouble", error.getOtherFeeDouble());
				tmsInParam.put("packName", error.getPackName());
				tmsInParam.put("paymentName", error.getPaymentName());
				tmsInParam.put("premiumDouble", error.getPremiumDouble());
				tmsInParam.put("productName", error.getProductName());
				tmsInParam.put("pullName", error.getPullName());
				tmsInParam.put("pullPhone", error.getPullPhone());
				tmsInParam.put("remarks", error.getRemarks());
				tmsInParam.put("reputationDouble", error.getReputationDouble());
				tmsInParam.put("serviceChargeDouble",error.getServiceChargeDouble());
				tmsInParam.put("tipDouble", error.getTipDouble());
				tmsInParam.put("totalFeeDouble",error.getTotalFeeDouble());
				tmsInParam.put("transitFeeDouble", error.getTransitFeeDouble());
				tmsInParam.put("volume", error.getVolume());
				tmsInParam.put("weight", error.getWeight());
				tmsInParam.put("selfNumber", error.getNumber());
				tmsInParam.put("pickingCostsDouble", error.getPickingCostsDouble());
				isArrival = OrderPostUtil.billingOrderKL(tmsInParam, error.getTenantId(), true);
			}else if(error.getOrderState().intValue() == SysStaticDataEnumYunQi.ORDER_INFO_STATE.DO_CANCER){
				//取消订单
				isArrival = OrderPostUtil.cancelOrderKL(error.getOrderNumber(), error.getUserName(), error.getTenantId(), true);
			}
			
			
			if (!"Y".equals(isArrival)) {
				error.setSendFlag(2);
				error.setSendTime(date);
				session.update(error);
			}else{
				try {
					removeToHis(error, session);
				} catch (Exception e) {
					log.error("移除历史表错误！");
				}
			}
			
			
		}
	}
	
	
	private static ExecutorService executor = null;
	
	public String timerMessage(Map<String, Object> obj) {
		int successNum = 0;
		Session session = null;
		Date date = new Date();
		try {	
				session = SysContexts.getEntityManager(true);
			    List<OrderInfoTmsError> list = getOrderInfoList(session);
				log.info("MessageTask 调用条数[" + list.size() + "]");
				int threadCountInt = 10;
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
		private List<OrderInfoTmsError> orderInfoTmsErrors;
		@SuppressWarnings("unused")
		private Map<String, Object> paramMap;

		public MessageThread(List<OrderInfoTmsError> orderInfoTmsErrors, String threadName, Map<String, Object> paramMap) {
			super();
			this.orderInfoTmsErrors = orderInfoTmsErrors;
			this.paramMap = paramMap;
			super.setName(threadName);
		}

		public void run() {
			log.info("MessageThread,threadName[ " + this.getName() + "]开始处理....");
			StopWatch watch = new StopWatch();
			watch.start();
			if (orderInfoTmsErrors.size() > 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put(TABLE_SPLIT_RULE, TableSplitRule.yyyyMM());
				Session session = null;
				for (OrderInfoTmsError error : orderInfoTmsErrors) {
					if (StringUtils.isNotBlank(error.getOrderNumber())) {
						try {
							session = SysContexts.getEntityManager(map);
							sendOrderInfoTms(error, session, new Date());
							// 框架会自己管理事务，这里特殊情况自己管理事务
							SysContexts.commitTransation();
						} catch (Exception e) {
							SysContexts.rollbackTransation();
							log.error("MessageThread threadName[ " + this.getName() + "]orderNumber: " + error.getOrderNumber(), e);
						} finally {
							closeSession(session);
						}
					}
				}
			}
			log.info("MessageThread,threadName[ " + this.getName() + "]处理结束，数量[" + orderInfoTmsErrors.size() + "],耗时[" + watch.toString() + "][" + watch.getTime() + "]");
		}

	}
	
}
