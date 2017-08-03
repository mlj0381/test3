package com.wo56.business.task;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.framework.components.match.common.MatchConsts;
import com.framework.core.SysContexts;
import com.framework.core.scheduler.impl.BaseTask;
import com.framework.core.scheduler.intf.ITask;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DateUtil;
import com.framework.core.util.SysCfgUtil;
import com.wo56.business.app.vo.AppPushBillRelat;
import com.wo56.business.app.vo.aps;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.sms.utils.EnumConsts;
import com.wo56.common.sms.utils.TableSplitRule;
import com.wo56.common.sms.vo.SysSmsSend;
import com.wo56.common.sms.vo.SysSmsSendHis;

public class MessageTask  extends BaseTask implements ITask {
	private static final String TABLE_SPLIT_RULE = "yyyyMM"; // 分表规则
	private static transient Log log = LogFactory.getLog(MessageTask.class);

	private static BaiduPushClient andoirdPushClient = null;
	private static BaiduPushClient iosPushClient = null;
	
	static {
		String andorid_apiKey = (String) SysCfgUtil.getCfgVal(EnumConsts.SysCfg.SYS_ANDROID_APIKEY, 3, String.class);
		String andorid_secretKey = (String) SysCfgUtil.getCfgVal(EnumConsts.SysCfg.SYS_ANDROID_SECRETKEY, 3, String.class);
		
		String ios_apiKey = (String) SysCfgUtil.getCfgVal(EnumConsts.SysCfg.SYS_IOS_APIKEY, 3, String.class);
		String ios_secretKey = (String) SysCfgUtil.getCfgVal(EnumConsts.SysCfg.SYS_IOS_SECRETKEY, 3, String.class);
		
		
		PushKeyPair androidPair = new PushKeyPair(andorid_apiKey, andorid_secretKey);
		andoirdPushClient = new BaiduPushClient(androidPair, BaiduPushConstants.CHANNEL_REST_URL);
		
		PushKeyPair iosPair = new PushKeyPair(ios_apiKey, ios_secretKey);
		iosPushClient = new BaiduPushClient(iosPair, BaiduPushConstants.CHANNEL_REST_URL);
		
		andoirdPushClient.setChannelLogHandler(new YunLogHandler() {
			public void onHandle(YunLogEvent event) {
				if (log.isDebugEnabled())
					log.debug(event.getMessage());
			}
		});
		
		iosPushClient.setChannelLogHandler(new YunLogHandler() {
			public void onHandle(YunLogEvent event) {
				if (log.isDebugEnabled())
					log.debug(event.getMessage());
			}
		});
	}
	
	/**
	 * 获取发送的消息内容
	 * 
	 * @param smsContent
	 * @param type
	 *            [APP或SMS]
	 * @return
	 */
	private String getMessageContent(String smsContent, String type) {
		if ("APP".equals(type)) {
			StringBuffer retBuf = new StringBuffer();
			String tmp = smsContent;
			int idx;
			while ((idx = tmp.indexOf("${")) >= 0) {
				retBuf.append(tmp.substring(0, idx));
				tmp = tmp.substring(idx + 2);
				if ((idx = tmp.indexOf("}")) >= 0) {
					tmp = tmp.substring(idx + 1);
				}
			}
			// 没有需要替换的了，追加最后一截字符
			retBuf.append(tmp);
			return retBuf.toString();
		} else if ("SMS".equals(type)) {
			smsContent = StringUtils.replace(smsContent, "${", "");
			return StringUtils.replace(smsContent, "}", "");
		}
		return null;
	}
	
	/**
	 * 追加自定义字段
	 * @param sysSmsSend
	 * @param jsonCustormCont
	 */
	private void putOtherInfo(SysSmsSend sysSmsSend, JSONObject custormCont) {
		custormCont.put("smsId", sysSmsSend.getSmsId());
		custormCont.put("smsContent", sysSmsSend.getSmsContent());
		custormCont.put("smsType", sysSmsSend.getSmsType());
		custormCont.put("smsTypeName", sysSmsSend.getSmsTypeName());
		custormCont.put("objType", sysSmsSend.getObjType());
		custormCont.put("objTypeName", sysSmsSend.getObjTypeName());
		custormCont.put("objId", sysSmsSend.getObjId());
		custormCont.put("sendDate", DateUtil.formatDate(sysSmsSend.getSendDate(), DateUtil.DATETIME_FORMAT));
	}
	
	public boolean pushMsgToUser(SysSmsSend sysSmsSend,Session session,List<Map<String,Object>> mapList)
			throws PushClientException, PushServerException {
		return pushMsgToUser(sysSmsSend, null,session,mapList);
	}
	
	/**
	 * 获取app绑定关系
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> appPushBillRelatMap(List<SysSmsSend> list){
		Set<String> setBillId = new HashSet<String>();
		for (SysSmsSend temp : list) {
			if (temp != null && StringUtils.isNotEmpty(temp.getBillId())){
				setBillId.add(temp.getBillId());
			}
		}
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(AppPushBillRelat.class);
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		if (setBillId != null && setBillId.size() > 0) {
			for (String string : setBillId) {
				List<AppPushBillRelat> appList = ca.add(Restrictions.eq("billId", string)).list();
				if (appList!=null&&appList.size()>0) {
					map.put(string, appList);
					listMap.add(map);
				}
			}
		}
		return listMap;
	}
	
	/**
	 * 推送消息
	 * 
	 * @param billId
	 * @param content
	 * @throws PushClientException
	 * @throws PushServerException
	 */
	@SuppressWarnings("unchecked")
	public boolean pushMsgToUser(SysSmsSend sysSmsSend, Map<String, Object> map,Session session,List<Map<String,Object>> mapList) throws PushClientException, PushServerException {
		if (sysSmsSend != null && StringUtils.isNotBlank(sysSmsSend.getBillId())) {
			PushMsgToSingleDeviceRequest request = null;
			PushMsgToSingleDeviceResponse response = null;
//			Criteria ca = SysContexts.getEntityManager().createCriteria(AppPushBillRelat.class);
//			ca.add(Restrictions.eq("billId", sysSmsSend.getBillId()));
			List<AppPushBillRelat> list = null;
			for (Map<String, Object> map2 : mapList) {
				Object obj = map2.get(sysSmsSend.getBillId());
				if (obj != null && obj instanceof ArrayList<?>){
					list = (List<AppPushBillRelat>) obj;
				}
			}
			boolean hasPush = false;
			if(list!=null&&list.size()>0){
				for (AppPushBillRelat app : list) {
					try {
						JSONObject notification = new JSONObject();
						// 翻译字段
						String content = getMessageContent(sysSmsSend.getSmsContent(), "APP");
						request = new PushMsgToSingleDeviceRequest().addChannelId(app.getPushChannelId()).addMsgExpires(new Integer(86400)); // message有效时间,一天
						notification.put("title", sysSmsSend.getSmsTypeName());
						log.error("正在向" +sysSmsSend.getBillId()+ "推送消息["+content+"]，渠道类型["+ app.getChannelType()+"]");
						if (StringUtils.contains(app.getChannelType(), "IOS")) {
							notification.put("aps", new aps(content));
							// ios自定义字段
							putOtherInfo(sysSmsSend, notification);
							request.addMessageType(1); // 1：通知,0:消息. IOS只有通知.
							request.addDeviceType(4); // 3:android,4:ios
							//1: Developer 2: Production.
							if (map != null && StringUtils.isNumeric((String) map.get("DeployStatus"))) {
								request.addDeployStatus(Integer.parseInt((String) map.get("DeployStatus")));
							} else {
								// 默认生产环境
								request.addDeployStatus(2);
							}
							request.addMessage(notification.toString());
							try {
								response = iosPushClient.pushMsgToSingleDevice(request);
							} catch (Exception e) {
							}
							sysSmsSend.setSendFlag(SysStaticDataEnumYunQi.SEND_FLAG.IS_SEND);
						} else {
							// android自定义字段
							JSONObject jsonCustormCont = new JSONObject();
							putOtherInfo(sysSmsSend, jsonCustormCont);
							notification.put("custom_content", jsonCustormCont);
							notification.put("description", content);
							if (map != null && StringUtils.isNumeric((String) map.get("DeployStatus"))) {
								request.addDeployStatus(Integer.parseInt((String) map.get("DeployStatus")));
							} else {
								// 默认生产环境
								request.addDeployStatus(2);
							}
							request.addMessageType(1); // 1：通知,0:消息.默认为0 注：IOS只有通知.
							request.addDeviceType(3); // 3:android,4:ios
							request.addMessage(notification.toString());
							response = andoirdPushClient.pushMsgToSingleDevice(request);
							sysSmsSend.setSendFlag(SysStaticDataEnumYunQi.SEND_FLAG.IS_SEND);
						}
						//记录推送渠道
						String channelType = sysSmsSend.getChannelType();
						if (StringUtils.isNotEmpty(channelType) && !channelType.contains(app.getChannelType())) {
							sysSmsSend.setChannelType(channelType +"&"+ app.getChannelType());
						} else{
							sysSmsSend.setChannelType(app.getChannelType());						
						}
						copyToHis(sysSmsSend, session);
						
						if(response!=null){
							// Http请求结果解析打印
							log.info("msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime());
						}
						
						hasPush = true;
					} catch (PushClientException e) {
						log.error("推送消息客户端出错", e);
					} catch (PushServerException e) {
						if ("30608".equals(String.valueOf(e.getErrorCode()))) {
							//绑定关系不存在，删除关系
							log.info("绑定关系不存在，删除绑定关系表!");
							SysContexts.getEntityManager().delete(app);
						}
						log.error("推送消息服务端出错", e);
					}
				}
			}else{
				//如果app_push_bill_relat这个表没有关系，也移除到历史表
				sysSmsSend.setSendFlag(SysStaticDataEnumYunQi.SEND_FLAG.IS_SEND);
				copyToHis(sysSmsSend, session);
				log.error("exception:手机号码["+sysSmsSend.getBillId()+"]在app_push_bill_relat表没有数据");
			}
			return hasPush;
		}
		return false;
	}
	
	private void copyToHis(SysSmsSend sysSmsSend,Session session ){
		SysSmsSendHis his = new SysSmsSendHis();
		try {
			BeanUtils.copyProperties(his, sysSmsSend);
			his.setMd5(sysSmsSend.getMd5());
			his.setRealSendDate(new Date());
			his.setSendFlag(1);
			his.setState(1);
			his.setIsRead(0);
			session.save(his); // 记录历史数据
			session.delete(sysSmsSend); // 移除在用表数据
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			log.error("移历史表出错", e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String doTask(Map<String, Object> arg0) throws Exception {
		timerMessage(arg0);
		return "进程启动成功";
	}
	public static void main(String[] args) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(MatchConsts.TaskParamKey.groupKey, "SCHE_MATCH");
		paramMap.put(MatchConsts.TaskParamKey.listenerKey, "com.wo56.business.task.MessageTask");
		paramMap.put(MatchConsts.TaskParamKey.threadNum, 3);
		paramMap.put(MatchConsts.TaskParamKey.Limit, 5);
		paramMap.put("DeployStatus", 1);
		
		MessageTask cmUserInfoPullWorkTask = new MessageTask();
		cmUserInfoPullWorkTask.doTask(paramMap);
		SysContexts.commitTransation();
	}
	private static ExecutorService executor = null;
	
	
	@SuppressWarnings("unchecked")
	public String timerMessage(Map<String, Object> obj) {
		int successNum = 0;
		Session session = null;
		Date date = new Date();
		try {
				Map<String, String> map = new HashMap<String, String>();
				map.put(TABLE_SPLIT_RULE, TableSplitRule.yyyyMM());
				session = SysContexts.getEntityManager(map);
				Criteria ca = session.createCriteria(SysSmsSend.class);
				ca.add(Restrictions.le("sendDate", date)).add(Restrictions.eq("smsType",(int)com.wo56.common.consts.EnumConstsYQ.SmsType.MOPBILE_TYPE));
				ca.add(Restrictions.eq("sendFlag", SysStaticDataEnumYunQi.SEND_FLAG.NOT_SEND));
				List<SysSmsSend> list = ca.list();
				List<Map<String,Object>> mapList = appPushBillRelatMap(list);
				log.info("MessageTask 推送条数[" + list.size() + "]");
				Object oCount = obj.get(EnumConsts.SysCfg.SMS_SEND_THREAD_COUNT);
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

						MessageThread messageThread = new MessageThread(list.subList(fromIndex, toIndex), "MessageThread" + i, obj,mapList);
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
		private List<SysSmsSend> smsSends;
		private Map<String, Object> paramMap;
		private List<Map<String,Object>> mapList;

		public MessageThread(List<SysSmsSend> smsSends, String threadName, Map<String, Object> paramMap,List<Map<String,Object>> mapList) {
			super();
			this.smsSends = smsSends;
			this.paramMap = paramMap;
			this.mapList = mapList;
			super.setName(threadName);
		}

		public void run() {
			log.info("MessageThread,threadName[ " + this.getName() + "]开始处理....");
			StopWatch watch = new StopWatch();
			watch.start();
			if (smsSends.size() > 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put(TABLE_SPLIT_RULE, TableSplitRule.yyyyMM());
				Session session = null;
				for (SysSmsSend sysSmsSend : smsSends) {
					if (StringUtils.isNotBlank(sysSmsSend.getBillId()) && StringUtils.isNotBlank(sysSmsSend.getSmsContent())) {
						try {
							Session session2 = null;
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
							session = SysContexts.getEntityManager(map);
							pushMsgToUser(sysSmsSend, session,mapList);
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
