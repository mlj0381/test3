package com.wo56.business.sche.index.intf;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysCfg;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DateUtil;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.SysCfgUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.SysStaticDataEnum;

public class IndexTF {
	
	private static String INDEX_IMAGE_CODE="INDEX_IMAGE_CODE";
	
	private static final Log log = LogFactory.getLog(IndexTF.class);
	/***
	 * 判断师傅是否有新任务
	 * @return 返回统计待受理数量
	 * 
	 * **/
	public String isNewTask() throws Exception{
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		Query query = session.createSQLQuery("SELECT COUNT(1) FROM scheduler_task WHERE TASK_STATE=:taskState and sts=1 and DO_OBJ_ID=:userId and DO_OBJ_TYPE=1 and GX_END_TIME is not null and GX_END_TIME!='' ");
		query.setParameter("taskState", SysStaticDataEnum.TASK_STATE.DISTRIBUTION_WAIT_ACCEPT);
		query.setParameter("userId", user.getUserId());
		return query.list().get(0)+"";
	}
	
	/***
	 * 查询今日完成任务／收入
	 * @return 返回统计待受理数量
	 * @return taskNum 完成的任务
	 * @return	income 收人的钱（元）
	 * 
	 * **/
	public Map statisticsCompleteTask() throws Exception{
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		Query query = session.createSQLQuery("SELECT COUNT(1),SUM(s.TOTAL_MONEY) as money FROM scheduler_task as s WHERE s.TASK_STATE=:taskState and s.sts=1 and s.DO_OBJ_ID=:userId and s.DO_OBJ_TYPE=1 and s.SIGN_TIME<=:endTime and s.SIGN_TIME>=:startTime ");
		query.setParameter("taskState", SysStaticDataEnum.TASK_STATE.DO_SIGN);
		query.setParameter("userId", user.getUserId());
		query.setParameter("endTime", DateUtil.getCurrDateTimeEnd());
		query.setParameter("startTime", DateUtil.getCurrDateTimeBegin());
		List list = query.list();
		BigDecimal income=null;
		BigInteger taskNum=null;
		for (int i = 0; i < list.size(); i++) {
			Object[] object = (Object[]) list.get(i);
			taskNum=(BigInteger) object[0];
			income=(BigDecimal) object[1];
		}
		Map map = new HashMap();
		map.put("taskNum", taskNum==null ? "0":taskNum.intValue());
		map.put("income", income==null ? "0":income.intValue());
		return map;
	}

	/***
	 * 查询失效异常数量
	 * @return 返回统计待受理数量
	 * @return overtimeNum 统计时效数量
	 * 
	 * **/
	public Map statisticsTimeOutTask() throws Exception{
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		int overtimeNum=0;
		//统计受理超时
		SysCfg ACCEPT_TIMEOUT = SysCfgUtil.getSysCfg(EnumConsts.SysCfg.APP_ACCEPT_TIMEOUT);
		if(ACCEPT_TIMEOUT==null){
			throw new BusinessException("系统参数ACCEPT_TIMEOUT为空!");
		}
		//提货超时
		SysCfg PICK_UP_TIMEOUT = SysCfgUtil.getSysCfg(EnumConsts.SysCfg.APP_PICK_UP_TIMEOUT);
		if(PICK_UP_TIMEOUT==null){
			throw new BusinessException("系统参数PICK_UP_TIMEOUT为空!");
		}
		//预约超时
		SysCfg APPOINTMENT_TIMEOUT = SysCfgUtil.getSysCfg(EnumConsts.SysCfg.APP_APPOINTMENT_TIMEOUT);
		if(APPOINTMENT_TIMEOUT==null){
			throw new BusinessException("系统参数APPOINTMENT_TIMEOUT为空!");
		}
		//签收超时
		SysCfg SIGN_TIMEOUT = SysCfgUtil.getSysCfg(EnumConsts.SysCfg.APP_SIGN_TIMEOUT);
		if(SIGN_TIMEOUT==null){
			throw new BusinessException("系统参数SIGN_TIMEOUT为空!");
		}
//		Query query = session.createSQLQuery("SELECT count(1) FROM scheduler_task as s  WHERE s.TASK_STATE=:taskState AND s.TASK_type=1  AND s.is_repair=2 and s.sts=1 and s.DO_OBJ_ID=:userId and s.DO_OBJ_TYPE=1 and DATE_ADD(s.DISTRIBUTION_TIME, interval "+ACCEPT_TIMEOUT.getCfgValue()+" hour)<=:time");
//		query.setParameter("taskState", SysStaticDataEnum.TASK_STATE.DISTRIBUTION_WAIT_ACCEPT);
//		query.setParameter("userId", user.getUserId());
//		query.setParameter("time", DateUtil.getCurrDateTime());
//		overtimeNum = ((BigInteger) query.list().get(0)).intValue();
//		Query PICK_UP_TIMEOUT_QUERY = session.createSQLQuery("SELECT count(1) FROM scheduler_task as s  WHERE  s.TASK_STATE in (:taskState) AND s.is_repair=2 AND s.TASK_type=1 and s.sts=1 and s.DO_OBJ_ID=:userId and s.DO_OBJ_TYPE=1 and ( DATE_ADD(s.ACCEPT_TIME, interval "+PICK_UP_TIMEOUT.getCfgValue()+" hour)<=:time OR DATE_ADD(s.ACCEPT_TIME, interval "+APPOINTMENT_TIMEOUT.getCfgValue()+" hour)<=:time )");
//		PICK_UP_TIMEOUT_QUERY.setParameterList("taskState",new Integer[]{ SysStaticDataEnum.TASK_STATE.WAIT_PICK_UP,SysStaticDataEnum.TASK_STATE.WAIT_RESERVATION});
//		PICK_UP_TIMEOUT_QUERY.setParameter("userId", user.getUserId());
//		PICK_UP_TIMEOUT_QUERY.setParameter("time", DateUtil.getCurrDateTime());
//		overtimeNum =overtimeNum+ ((BigInteger) PICK_UP_TIMEOUT_QUERY.list().get(0)).intValue();
//		Query SIGN_TIMEOUT_QUERY = session.createSQLQuery("SELECT count(1) FROM scheduler_task as s WHERE  s.TASK_STATE=:taskState AND s.TASK_type=1 AND s.is_repair=2 and s.sts=1 and s.DO_OBJ_ID=:userId and s.DO_OBJ_TYPE=1 and DATE_ADD(s.UP_FIX_TIME, interval "+SIGN_TIMEOUT.getCfgValue()+" hour)<=:time ");
//		SIGN_TIMEOUT_QUERY.setParameter("taskState", SysStaticDataEnum.TASK_STATE.DO_SIGN);
//		SIGN_TIMEOUT_QUERY.setParameter("userId", user.getUserId());
//		SIGN_TIMEOUT_QUERY.setParameter("time", DateUtil.getCurrDateTime());
//		overtimeNum =overtimeNum+ ((BigInteger) SIGN_TIMEOUT_QUERY.list().get(0)).intValue();
		Query query = session.createSQLQuery("SELECT count(1) FROM way_bill_main AS w	INNER JOIN scheduler_task AS s ON s.buss_id = w.WayBillID	AND s.IS_SCHE = 1 AND w. STATUS = 1 	AND s.TASK_TYPE = 1 AND s.IS_REPAIR = 2 AND s.STS=1	LEFT"
				+ " JOIN scheduler_task AS st ON s.TASK_ID = st.PARENT_ID AND st.CANCER_USER_ID is null AND st.cancer_time IS NULL LEFT JOIN sche_to_friend_task AS t ON st.TASK_ID = t.TASK_ID AND t.sts = 1 "
				+ "WHERE	1 = 1 AND ( s.DO_OBJ_ID=:userId and s.DO_OBJ_TYPE=1 ) AND (( s.TASK_STATE=4 AND DATE_ADD(s.DISTRIBUTION_TIME, interval 24 hour)<=:time  ) "
				+ "OR ( s.TASK_STATE in (5,6)  and ( DATE_ADD(s.ACCEPT_TIME, interval 24 hour)<=:time OR DATE_ADD(s.ACCEPT_TIME, interval 24 hour)<=:time )) "
				+ "OR (s.TASK_STATE=7 AND  DATE_ADD(s.UP_FIX_TIME, interval 24 hour)<=:time )) union ALL SELECT count(1)"
				+ " FROM way_bill_main AS w	INNER JOIN scheduler_task AS s ON s.buss_id = w.WayBillID	AND s.IS_SCHE = 1 AND w. STATUS = 1 	"
				+ "AND s.TASK_TYPE = 1 AND s.IS_REPAIR = 2	LEFT JOIN scheduler_task AS st ON s.TASK_ID = st.PARENT_ID and st.sts=1   "
				+ "LEFT JOIN sche_to_friend_task AS t ON st.TASK_ID = t.TASK_ID  AND t.sts = 1 and t.turn_state!=3 WHERE	1 = 1  "
				+ "AND ( st.DO_OBJ_ID=:userId and st.DO_OBJ_TYPE=1 ) "
				+ "AND (( st.TASK_STATE=4 AND DATE_ADD(st.DISTRIBUTION_TIME, interval 24 hour)<=:time  ) OR ( st.TASK_STATE in (5,6)  "
				+ "and ( DATE_ADD(s.ACCEPT_TIME, interval 24 hour)<=:time OR DATE_ADD(st.ACCEPT_TIME, interval 24 hour)<=:time )) "
				+ "OR (st.TASK_STATE=7 AND  DATE_ADD(s.UP_FIX_TIME, interval 24 hour)<=:time ))");
		query.setParameter("userId", user.getUserId());
		query.setParameter("time", DateUtil.getCurrDateTime());
		overtimeNum= ((BigInteger) query.list().get(0)).intValue();
		Map map = new HashMap();
		map.put("overtimeNum", overtimeNum);
		return map;
	}
	

	/**
	 * 获取首页轮播的图片地址
	 * @return
	 * @throws Exception 
	 */
	public String getImageUrl() throws Exception{
		List<SysStaticData> datas= SysStaticDataUtil.getSysStaticDataList(INDEX_IMAGE_CODE);
		Map<String,Object> retMap=new HashMap<String, Object>();
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		if(datas != null && datas.size() > 0){
			for(SysStaticData sysStaticData : datas){
				Map<String,String> urlMap=new HashMap<String, String>();
				urlMap.put("url", sysStaticData.getCodeValue());
				urlMap.put("skipUrl", sysStaticData.getCodeName());
				list.add(urlMap);
			}
		}
		retMap.put("image", list);
		return JsonHelper.json(retMap);
	}
	
	/**
	 * 获取首页轮播的图片地址
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> getImageUrlYQ() throws Exception{
		List<SysStaticData> datas= SysStaticDataUtil.getSysStaticDataList(INDEX_IMAGE_CODE);
		//Map<String,Object> retMap=new HashMap<String, Object>();
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		if(datas != null && datas.size() > 0){
			for(SysStaticData sysStaticData : datas){
				Map<String,String> urlMap=new HashMap<String, String>();
				urlMap.put("url", sysStaticData.getCodeValue());
				urlMap.put("skipUrl", sysStaticData.getCodeName());
				list.add(urlMap);
			}
		}
		//retMap.put("image", list);
		return JsonHelper.parseJSON2Map(JsonHelper.json(list));
	}
	
	/***
	 * 用户帐号信息
	 * @return map
	 * 
	 * **/
	public Map statisticsBalance() throws Exception{
		Session session = SysContexts.getEntityManager(true);
		Map map = new HashMap();
		BaseUser user = SysContexts.getCurrentOperator();
		Query cmoney = session.createSQLQuery("SELECT sum(TOTAL_MONEY) FROM scheduler_task WHERE WITHDRAW_STS=:withdraw_sts and sts=1 and DO_OBJ_ID=:userId and DO_OBJ_TYPE=1 and BELONG_OBJ_TYPE=2 ");
		cmoney.setParameter("withdraw_sts", SysStaticDataEnum.WITHDRAW_STS.WITHDRAW_ING);
		cmoney.setParameter("userId", user.getUserId());
		map.put("cmoney", cmoney.list().get(0)+"");
		Query dmoney = session.createSQLQuery("SELECT sum(TOTAL_MONEY) FROM scheduler_task WHERE WITHDRAW_STS=:withdraw_sts and sts=1 and DO_OBJ_ID=:userId and DO_OBJ_TYPE=1 and BELONG_OBJ_TYPE=2 ");
		dmoney.setParameter("withdraw_sts", SysStaticDataEnum.WITHDRAW_STS.WAIT_WITHDRAW);
		dmoney.setParameter("userId", user.getUserId());
		map.put("dmoney", dmoney.list().get(0)+"");
		Query yquery = session.createSQLQuery("SELECT sum(TOTAL_MONEY) FROM scheduler_task WHERE WITHDRAW_STS=:withdraw_sts and sts=1 and DO_OBJ_ID=:userId and DO_OBJ_TYPE=1 and BELONG_OBJ_TYPE=2 ");
		yquery.setParameter("withdraw_sts", SysStaticDataEnum.WITHDRAW_STS.DO_WITHDRAW);
		yquery.setParameter("userId", user.getUserId());
		map.put("ymoney", StringUtils.defaultString(yquery.list().get(0)+"","0"));
		return map;
	}
	

	/***
	 * lyh
	 * 首页统计
	 * @param taskNum 今日完成数量
	 * @param income 今日收人
	 * @return map
	 * 
	 * **/
	public Map indexStatistics() throws Exception{
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		DecimalFormat df  = new DecimalFormat("###0.00");
		Query query = session.createSQLQuery("SELECT COUNT(1),SUM(s.TOTAL_MONEY) as money FROM scheduler_task as s ,ord_order_info as o  WHERE s.buss_id=o.order_id and s.TASK_STATE=:taskState and s.sts=1 and s.DO_OBJ_ID=:userId and s.DO_OBJ_TYPE=1 and s.SIGN_TIME<=:endTime and s.SIGN_TIME>=:startTime and s.task_type=1 ");
		query.setParameter("taskState", SysStaticDataEnum.TASK_STATE.DO_SIGN);
		query.setParameter("userId", user.getUserId());
		query.setParameter("endTime", DateUtil.getCurrDateTimeEnd());
		query.setParameter("startTime", DateUtil.getCurrDateTimeBegin());
		List list = query.list();
		BigDecimal income=null;
		BigInteger taskNum=null;
		for (int i = 0; i < list.size(); i++) {
			Object[] object = (Object[]) list.get(i);
			taskNum=(BigInteger) object[0];
			income=(BigDecimal) object[1];
		}
		Map map = new HashMap();
		map.put("taskNum", taskNum==null ? "0":taskNum.intValue());
		map.put("income", income==null ? "0":df.format((income.intValue()/100)));
		Integer waitComplete = waitComplete();//未完成数量
		map.put("waitComplete", waitComplete);
		map.put("noArriveGood", noArriveGoodsTask());
		map.put("afterTaskCount", aftermarketTask());
		map.put("timeOutCount", timeOutTask());
		map.put("questionCount", statisticsQuestion());
		return map;
		
	}
	
	/***
	 * lyh
	 * 统计未完成数量
	 * 
	 * 
	 * **/
	public Integer waitComplete() throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery("SELECT COUNT(1) FROM scheduler_task as s,ord_order_info as o  WHERE s.buss_id=o.order_id and s.TASK_STATE in (:taskState) and s.sts=1 and s.ARRIVE_GOODS_TIME is not null and s.ARRIVE_GOODS_TIME!='' and s.DO_OBJ_ID=:userId and s.DO_OBJ_TYPE=1  and s.task_type=1 ");
		query.setParameter("userId", user.getUserId());
		query.setParameterList("taskState", new Integer[]{SysStaticDataEnum.TASK_STATE.DISTRIBUTION_WAIT_ACCEPT,SysStaticDataEnum.TASK_STATE.WAIT_PICK_UP,SysStaticDataEnum.TASK_STATE.WAIT_RESERVATION,SysStaticDataEnum.TASK_STATE.WAIT_SIGN});
		BigInteger waitComplete = (BigInteger) query.uniqueResult();
		return waitComplete.intValue();
	}
	
	
	/***
	 * lyh
	 * 统计未到货数量
	 * 
	 * 
	 * **/
	public Integer noArriveGoodsTask() throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery("SELECT COUNT(1) FROM scheduler_task as s,ord_order_info as o  WHERE s.buss_id=o.order_id and  s.task_type=1 and  s.is_repair=2  AND s.STS=1 	AND s.IS_SCHE = 1 and s.DO_OBJ_ID=:userId and s.DO_OBJ_TYPE=1  and (s.ARRIVE_GOODS_TIME is null or s.ARRIVE_GOODS_TIME='') ");
		query.setParameter("userId", user.getUserId());
		BigInteger noArrive = (BigInteger) query.uniqueResult();
		return noArrive.intValue();
	}

	

	/***
	 * lyh
	 * 统计超时数量
	 * 
	 * 
	 * **/
	public Integer timeOutTask() throws Exception{
		Session session = SysContexts.getEntityManager(true);
		//统计受理超时
		SysCfg ACCEPT_TIMEOUT = SysCfgUtil.getSysCfg(EnumConsts.SysCfg.ACCEPT_TIMEOUT);
		if(ACCEPT_TIMEOUT==null){
			throw new BusinessException("系统参数ACCEPT_TIMEOUT为空!");
		}
		//预约超时
		SysCfg APPOINTMENT_TIMEOUT = SysCfgUtil.getSysCfg(EnumConsts.SysCfg.APPOINTMENT_TIMEOUT);
		if(APPOINTMENT_TIMEOUT==null){
			throw new BusinessException("系统参数APPOINTMENT_TIMEOUT为空!");
		}
		//签收超时
		SysCfg SIGN_TIMEOUT = SysCfgUtil.getSysCfg(EnumConsts.SysCfg.SIGN_TIMEOUT);
		if(SIGN_TIMEOUT==null){
			throw new BusinessException("系统参数SIGN_TIMEOUT为空!");
		}
		BaseUser user = SysContexts.getCurrentOperator();
		Query query = session.createSQLQuery("SELECT COUNT(1) FROM scheduler_task as s,ord_order_info as o  WHERE s.buss_id=o.order_id and  s.sts=1 and s.task_type=1 and s.DO_OBJ_ID=:userId and s.DO_OBJ_TYPE=1   AND ((s.DISTRIBUTION_TIME is not null and s.ARRIVE_GOODS_TIME is not  null and s.TASK_STATE=4 AND DATE_ADD(CASE when s.DISTRIBUTION_TIME<s.ARRIVE_GOODS_TIME then s.ARRIVE_GOODS_TIME else s.DISTRIBUTION_TIME end , interval "+ACCEPT_TIMEOUT.getCfgValue()+" hour)<=:time  ) OR ( s.TASK_STATE in (5,6)  "
				+ "and  DATE_ADD(s.ACCEPT_TIME, interval "+APPOINTMENT_TIMEOUT.getCfgValue()+" hour)<=:time ) "
				+ "OR (s.TASK_STATE=7 AND  DATE_ADD(s.UP_FIX_TIME, interval "+SIGN_TIMEOUT.getCfgValue()+" hour)<=:time ))");
		query.setParameter("userId", user.getUserId());
		query.setParameter("time", DateUtil.getCurrDateTime());
		BigInteger timeOut = (BigInteger) query.uniqueResult();
		return timeOut.intValue();
	}
	

	/***
	 * lyh
	 * 统计售后任务数量
	 * 
	 * 
	 * **/
	public Integer aftermarketTask() throws Exception{
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		Query query = session.createSQLQuery("SELECT count(1) FROM ord_order_info as w inner join  scheduler_fix_task AS e on  w.order_id=e.order_id  inner join  scheduler_task as s on s.buss_id=e.fix_Id  AND s.DO_OBJ_TYPE=1  AND s.TASK_TYPE=2  AND s.IS_SCHE=1  AND s.sts=1  WHERE  s.DO_OBJ_ID=:userId  and s.task_state in (:taskState) ");
		query.setParameter("userId", user.getUserId());
		query.setParameterList("taskState", new Integer[]{SysStaticDataEnum.TASK_STATE.WAIT_SIGN,SysStaticDataEnum.TASK_STATE.DISTRIBUTION_WAIT_ACCEPT,SysStaticDataEnum.TASK_STATE.WAIT_RESERVATION});
		BigInteger timeOut = (BigInteger) query.uniqueResult();
		return timeOut.intValue();
	}
	
	/***
	 * lyh
	 * 统计问题件数量
	 * 
	 * 
	 * **/
	public Integer statisticsQuestion() throws Exception{
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		Query query = session.createSQLQuery("SELECT COUNT(1) FROM yzt_ord_exception_info as e WHERE  e.CREATOR_ID=:userId and e.EXC_STATE in (1,2) ");
		query.setParameter("userId", user.getUserId());
		BigInteger timeOut = (BigInteger) query.uniqueResult();
		return timeOut.intValue();
	}

}

