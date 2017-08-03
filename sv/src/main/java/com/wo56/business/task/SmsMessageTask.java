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
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.scheduler.impl.BaseTask;
import com.framework.core.scheduler.intf.ITask;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.SysCfgUtil;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.sms.impl.BaseSmsSV;
import com.wo56.common.sms.utils.EnumConsts;
import com.wo56.common.sms.utils.SysSmsPlatformCacheUtil;
import com.wo56.common.sms.utils.SysTenantControllerCacheUtil;
import com.wo56.common.sms.utils.TableSplitRule;
import com.wo56.common.sms.vo.SysSmsPlatform;
import com.wo56.common.sms.vo.SysSmsSend;
import com.wo56.common.sms.vo.SysSmsSendHis;
import com.wo56.common.sms.vo.SysTenantController;

/**
 * 短信扫描进程：只是扫描sys_sms_send表短信类型sms_type 不等于4的类型 短信发送会先推送手机，推送失败会走短信
 * 
 * @author liyiye
 *
 */
public class SmsMessageTask extends BaseTask implements ITask {
	private static transient Log log = LogFactory.getLog(SmsMessageTask.class);

	private static ExecutorService executor = null;
	private static final String TABLE_SPLIT_RULE = "yyyyMM"; // 分表规则

	public String timerMessage(Map<String, Object> obj) {
		int successNum = 0;
		Session session = null;
		Date date = new Date(); 
		try {
			if (SysCfgUtil.getCfgBooleanVal(EnumConsts.SysCfg.IS_SEND_MEG)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put(TABLE_SPLIT_RULE, TableSplitRule.yyyyMM());
				session = SysContexts.getEntityManager(map);
				Criteria ca = session.createCriteria(SysSmsSend.class);
				ca.add(Restrictions.eq("sendFlag", 0)).add(Restrictions.le("sendDate", date)).add(Restrictions.isNotNull("tenantId"));
				ca.add(Restrictions.eq("smsType", EnumConstsYQ.SmsType.NOTICE_TYPE));
				List<SysSmsSend> list = ca.list();
				log.info("MessageTask 短信发送条数[" + list.size() + "]");
				Integer threadCount = Integer.valueOf(obj.get(EnumConsts.SysCfg.SMS_SEND_THREAD_COUNT).toString());
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
				}
			} else {
				return "IS_SEND_MEG参数未启用，不发送短信!";
			}
		} catch (Exception ex) {
			log.error("发送失败", ex);
			throw new RuntimeException("发送失败", ex);
		} finally {
			closeSession(session);
		}
		return "短信成功发送条数：" + successNum;
	}

	/**
	 * 获取发送的消息内容
	 * @param smsContent
	 * @param type
	 *            [APP或SMS]
	 * @return
	 */
	private String getMessageContent(String smsContent) {
		smsContent = StringUtils.replace(smsContent, "${", "");
		return StringUtils.replace(smsContent, "}", "");
	}

	public String doTask(Map<String, Object> map) throws Exception {
		return timerMessage(map);
	}

	public static void main(String[] args) throws Exception {

		// int size=18;
		// int threadCountInt=10;
		// int pageCount=
		// ((double)size)/threadCountInt>(size/threadCountInt)?(size/threadCountInt+1):(size/threadCountInt);
		// int fromIndex=0;
		// int toIndex=0;
		// for(int i=0;i<threadCountInt;i++){
		// fromIndex=i*pageCount;
		// toIndex=(i+1)*pageCount;
		// if(toIndex>size){
		// toIndex=size;
		// break;
		// }
		// System.out.println("MessageTask 第"+i+"个线程启动.......from["+fromIndex+"]to["+toIndex+"]");
		//
		// }
//		List<String> list = new ArrayList<String>();
//		for (int i = 0; i < 121; i++) {
//			list.add("" + i);
//		}
//		int size = list.size();
//		int threadCountInt = 10;
//		int mod = size % threadCountInt;
//		int pageCount = (size - size % threadCountInt) / 10;
//		if (pageCount == 0) {
//			threadCountInt = size;
//			// pageCount=1;
//		}
//
//		int fromIndex = 0;
//		int toIndex = 0;
//		for (int i = 0, j = 0; i < threadCountInt; i++, j++) {
//			if (j < mod) {
//				fromIndex = toIndex;
//				toIndex = fromIndex + (pageCount + 1);
//			} else {
//				fromIndex = toIndex;
//				toIndex = fromIndex + (pageCount);
//			}
//
//			if (toIndex > size) {
//				toIndex = size;
//				break;
//			}
//			System.out.println("MessageTask 第" + i + "个线程启动.......from[" + fromIndex + "]to[" + toIndex + "]");
//			for (String value : list.subList(fromIndex, toIndex)) {
//				System.out.println(value);
//			}
//		}

		 SmsMessageTask m = new SmsMessageTask();
		 Map<String,Object> map=new HashMap<String, Object>();
		 map.put("DeployStatus","1");
		 map.put(EnumConsts.SysCfg.SMS_SEND_THREAD_COUNT, 1);
		 m.doTask(map);

	}

	class MessageThread extends Thread {
		private Log log = LogFactory.getLog(MessageThread.class);
		private List<SysSmsSend> smsSends;
		private Map<String, Object> paramMap;

		public MessageThread(List<SysSmsSend> smsSends, String threadName, Map<String, Object> paramMap) {
			super();
			this.smsSends = smsSends;
			this.paramMap = paramMap;
			super.setName(threadName);
		}

		public void run() {
			log.info("MessageThread,threadName[ " + this.getName() + "]开始处理....");
			StopWatch watch = new StopWatch();
			watch.start();
			if (smsSends.size() > 0) {
				SysSmsSendHis his = null;
				BaseSmsSV baseSms = (BaseSmsSV) SysContexts.getBean("baseSmsSV");
				Map<String, String> map = new HashMap<String, String>();
				map.put(TABLE_SPLIT_RULE, TableSplitRule.yyyyMM());
				Session session = null;
				for (SysSmsSend sysSmsSend : smsSends) {
					if (StringUtils.isNotBlank(sysSmsSend.getBillId()) && StringUtils.isNotBlank(sysSmsSend.getSmsContent())) {
						try {
							Session session2 = null;
							Long platformId = null;
							try {
								//先移到历史
								session2 = SysContexts.getNewConnection();
								SysSmsSend sms2 = (SysSmsSend) session2.get(SysSmsSend.class, sysSmsSend.getSmsId());
								sms2.setSendFlag(1);
								session2.update(sms2);
								SysContexts.commitNewConn(session2);
							} catch (Exception ex) {
								log.error("MessageThread threadName[ " + this.getName() + "] 设置sendFlag失败,smsId[" + sysSmsSend.getSmsId() + "]", ex);
								SysContexts.rollbackNewConn(session2);
								continue;
							}
							
							//发送短信
							sysSmsSend.setChannelType("SMS");
							String content = getMessageContent(sysSmsSend.getSmsContent());//得到短信内容
							if(sysSmsSend.getTenantId() != null){
								//对应 sys_tenant_controller 根据租户ID得到租户列表 
								List<SysTenantController> tenantList = SysTenantControllerCacheUtil.getTenantIdList(sysSmsSend.getTenantId());
								if(tenantList != null && tenantList.size()>0){
									//Long platformId = null;
									try {
										//得到平台ID
										platformId = Long.parseLong(tenantList.get(0).getPlatformId());
									} catch (Exception e) {
										log.error("租户:["+sysSmsSend.getTenantId()+"]  Sys_Tenant_Controller表 平台id ： platform_id 配置不正确");
										continue;
									}
									//根据租户ID去赛选 platform.getName() 租户平台名称
									SysSmsPlatform platform = SysSmsPlatformCacheUtil.getPlatForm(platformId);
									baseSms.sendMessage(sysSmsSend.getBillId(), content, sysSmsSend.getExpId(),platform.getPlatformChoice() ,sysSmsSend.getTenantId());
									if(log.isDebugEnabled()){
										log.debug("tenantid为"+sysSmsSend.getTenantId()+",使用"+platform.getName()+"发送信息");
									}
								}else{
									log.info("没有找到该租户的配置信息 －－表 sys_tenant_controller 租户id为" + sysSmsSend.getTenantId());
								}
							}else{
								if(log.isDebugEnabled()){
									log.debug("tenantid为"+sysSmsSend.getTenantId()+",不发送信息");
								}
							}

							session = SysContexts.getEntityManager(map);
							his = new SysSmsSendHis();
							BeanUtils.copyProperties(his, sysSmsSend);
							his.setMd5(sysSmsSend.getMd5());
							his.setRealSendDate(new Date());
							his.setSendFlag(1);
							his.setPlatformId(platformId.intValue());
							session.save(his); // 记录历史数据
							session.delete(sysSmsSend); // 移除sys_sms_send 数据

							// 框架会自己管理事务，这里特殊情况自己管理事务
							SysContexts.commitTransation();
						} catch (Exception e) {
							SysContexts.rollbackTransation();
							log.error("MessageThread threadName[ " + this.getName() + "]smsId: " + sysSmsSend.getSmsId(), e);
						} finally {
							closeSession(session);
						}
					}
				}
			}
			log.info("MessageThread,threadName[ " + this.getName() + "]处理结束，数量[" + smsSends.size() + "],耗时[" + watch.toString() + "][" + watch.getTime() + "]");
		}

	}
}
