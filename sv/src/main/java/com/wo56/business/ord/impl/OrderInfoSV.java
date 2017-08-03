package com.wo56.business.ord.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.components.fdfs.impl.SysAttachFileBO;
import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ord.intf.OrderInfoChildTF;
import com.wo56.business.ord.vo.OrdGoodsInfo;
import com.wo56.business.ord.vo.OrdOrdersInfo;
import com.wo56.business.ord.vo.OrdSignInfo;
import com.wo56.business.ord.vo.OrderFee;
import com.wo56.business.ord.vo.OrderInfo;
import com.wo56.business.ord.vo.OrderInfoChild;
import com.wo56.business.sys.impl.SysAttachSV;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;

public class OrderInfoSV {
	/**
	 * 保存与修改
	 * @param orderInfo
	 * @return
	 */
	public String doSaveOrUpdate(OrderInfo orderInfo){
		Session session = SysContexts.getEntityManager(false);
		session.saveOrUpdate(orderInfo);
		return "Y";
	}
	
	
	public String doSaveOrUpdateFee(OrderFee fee){
		Session session = SysContexts.getEntityManager(false);
		session.saveOrUpdate(fee);
		return "Y";
	}
	/**
	 * 查询开单信息
	 * @param orderId
	 * @return
	 */
	public OrderInfo getOrderInfo(long orderId){
		Session session = SysContexts.getEntityManager(true);
		return (OrderInfo) session.get(OrderInfo.class, orderId);
	}
	/**
	 * 查询费用
	 * @param orderId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public OrderFee getOrderFee(long orderId){
		Session session = SysContexts.getEntityManager(true);
		List<OrderFee> list = session.createCriteria(OrderFee.class).add(Restrictions.eq("orderId", orderId)).list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 变更专线订单
	 * @return
	 */
	public Query queryOrderInfo(Map<String,Object> inParam,boolean isSum){
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer("SELECT ");
		if(isSum){
			sb.append(" SUM(f.tip) ");
		}else{
			sb.append(" o.ORDER_NO,t.ORDER_NUMBER,t.BILLING_ORG_ID,t.TENANT_ID as ordTenantId,o.CON_TENANT_ID as ordsTenantId,t.DES_CITY_NAME,t.PULL_NAME,t.PULL_PHONE,t.CONSIGNOR,t.CONSIGNOR_PHONE,t.CONSIGNOR_ADDRESS,f.TIP,t.CREATE_TIME,o.order_id ");
		}
		sb.append(" FROM ");
		
		sb.append(" ord_orders_info o ");
		sb.append(" INNER JOIN order_info t ON o.ORDER_ID = t.ORDS_ID LEFT JOIN order_fee f ON t.ORDER_ID = f.ORDER_ID  WHERE t.TENANT_ID <> o.CON_TENANT_ID ");
		String ordTenantName = DataFormat.getStringKey(inParam, "ordTenantName");
		Map<String,Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(ordTenantName)) {
			sb.append(" AND t.tenant_id in (select tenant_id from sys_tenant_def where name like :ordTenantName) ");
			map.put("ordTenantName", "%"+ordTenantName+"%");
		}
		String ordsTenantName = DataFormat.getStringKey(inParam, "ordsTenantName");
		if (StringUtils.isNotEmpty(ordsTenantName)) {
			sb.append(" AND o.CON_TENANT_ID in (select tenant_id from sys_tenant_def where name like :ordsTenantName) ");
			map.put("ordsTenantName", "%"+ordsTenantName+"%");
		}
		String pullName = DataFormat.getStringKey(inParam, "pullName");
		if(StringUtils.isNotEmpty(pullName)){
			sb.append(" AND t.PULL_NAME like :pullName ");
			map.put("pullName", "%"+pullName+"%");
		}
		String billingBegin = DataFormat.getStringKey(inParam, "billingBegin");
		if (StringUtils.isNotEmpty(billingBegin)) {
			sb.append(" and t.CREATE_TIME >= :billingBegin ");
			map.put("billingBegin", billingBegin+" 00:00:00");
		}
		String billingEnd = DataFormat.getStringKey(inParam, "billingEnd");
		if (StringUtils.isNotEmpty(billingEnd)) {
			sb.append(" and t.CREATE_TIME <= :billingEnd ");
			map.put("billingEnd", billingEnd+" 23:59:59");
		}
		sb.append(" order by t.order_id desc ");
		Query query = session.createSQLQuery(sb.toString());
		query.setProperties(map);
		return query;
	}
	/**
	 * 统计数据
	 * @param inParam
	 * @return
	 */
	public Query getConuntOrderInfo(Map<String,Object> inParam,boolean isSum){
		//Integer[] complete = new Integer[]{SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN};//已完成
//		Integer[] complete = new Integer[]{
//				//运输、到站、签收 
//		        SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT,//4
//		        SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY,//5,
//		        SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN};
//		Integer[] notComplete = new Integer[]{
//				SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING,//1
//				SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP,//2
//				SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING,//3
//				SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN};
		BaseUser user = SysContexts.getCurrentOperator();
		int userType = user.getUserType();
		Session session = SysContexts.getEntityManager(true);
		int merchanOrDistri = DataFormat.getIntKey(inParam, "merchanOrDistri");
		int selectType = DataFormat.getIntKey(inParam, "selectType");
		String selectText = DataFormat.getStringKey(inParam, "selectText");
		StringBuffer sb = new StringBuffer("SELECT ");
		if (isSum) {
			sb.append(" SUM(o.TIP_MONEY),SUM(o.FREIGHT),COUNT(1),SUM(t.NUMBER) ");
		}else{
			sb.append(" o.TRACKING_NUM,o.TIP_MONEY,IF(t.ORDER_ID is null,o.CITY_ID,t.DES_CITY) as desCity,o.FREIGHT AS ordersFre,o.WEIGHT AS ordersWe,t.NUMBER,p.INPUT_TIME,IF(t.ORDER_ID is NULL,o.CONSIGNEE_NAME,t.CONSIGNEE),o.CREATE_DATE,o.CONSIGNEE_NAME,o.COMPANY_NAME,o.order_id as ordersId ");
		}
		if(user.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
			sb.append(",(SELECT group_concat(CONSIGNOR_NAME) FROM ord_goods_info WHERE ORDER_ID = o.ORDER_ID) ");
		}
		sb.append(" FROM ord_orders_info o  ");
		sb.append(" LEFT JOIN order_info t ON o.ORDER_ID = t.ORDS_ID ");
		sb.append(" LEFT JOIN ord_busi_person p ON p.ORDER_ID = o.ORDER_ID ");
		sb.append(" where o.order_state in :orderState ");
		Map<String,Object> map = new HashMap<String, Object>();
		//除了已取消和取消中的不要
		map.put("orderState",new Integer[]{
				SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING,//1
				SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP,//2
				SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING,//3
				SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT,//4
				SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY,//5
				SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN,//6
				SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN,
				SysStaticDataEnumYunQi.ORDERS_STATE.PENDING_DEPARTURE
				});//7
		if (userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER) {
			sb.append(" and t.CREATE_ID = :userId ");
			map.put("userId", user.getUserId());
		}
		if(userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.DISTRIBUTION){
			sb.append("  and p.DELIVERY_ID =:userId ");
			map.put("userId", user.getUserId());
		}
		if (userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION) {
			if (1 == merchanOrDistri) {//开单员
				sb.append(" and t.CREATE_ID = :userId ");
				map.put("userId", user.getUserId());
			}else if(2 == merchanOrDistri){//配送员
				if(selectType == SysStaticDataEnumYunQi.ORDER_COUNT_SELECT.STATE) {
					if (SysStaticDataEnumYunQi.ORDER_COUNT_SELECT_TYPE.DO_SIGN.equals(selectText)) {
						sb.append(" and (p.DELIVERY_ID =:userId or t.ARRIVED_ORG_ID = :orgId) ");
						map.put("orgId",user.getOrgId());
					}
				}else{
					sb.append(" and p.DELIVERY_ID =:userId  ");
				}
				map.put("userId", user.getUserId());
			}
		}
		if(userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS){
			sb.append(" and (o.order_id in (select distinct order_id from ord_goods_info where CONSIGNOR_BILL = :bill) or (o.consignee_bill = :bill or o.CREATE_ID = :userId )) and o.ORDER_TYPE IN (1,2) ");
			map.put("bill", user.getTelphone());
			map.put("userId", user.getUserId());
		}
		if(userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
			sb.append(" and (p.WORKER_PHONE = :bill or p.WORKER_ID = :userId or t.pull_id = :userId or t.pull_phone = :bill ) ");
			map.put("bill", user.getTelphone());
			map.put("userId", user.getUserId());
		}
		if(StringUtils.isNotEmpty(selectText)){
			if (selectType == SysStaticDataEnumYunQi.ORDER_COUNT_SELECT.CONSIGNEE) {
				sb.append(" and (t.CONSIGNEE like :consignee or o.CONSIGNEE_NAME like :consignee) ");
				map.put("consignee", "%"+selectText+"%");
			}else if(selectType == SysStaticDataEnumYunQi.ORDER_COUNT_SELECT.CONSIGNOR){
				sb.append(" and (t.CONSIGNOR like :consignor or o.order_id in (select order_id from ord_goods_info where consignor_name like :consignor )) ");
				map.put("consignor", "%"+selectText+"%");
			}else if(selectType == SysStaticDataEnumYunQi.ORDER_COUNT_SELECT.PULL){
				sb.append(" and (t.PULL_NAME like :pull or p.WORKER_NAME like :pull) ");
				map.put("pull", "%"+selectText+"%");
			}else if(selectType == SysStaticDataEnumYunQi.ORDER_COUNT_SELECT.STATE){
				if (SysStaticDataEnumYunQi.ORDER_COUNT_SELECT_TYPE.IN_TRANSIT.equals(selectText)) {
					sb.append(" and o.order_state = :state ");
					map.put("state", SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT);
				}else if(SysStaticDataEnumYunQi.ORDER_COUNT_SELECT_TYPE.WAIT_DELIVERY.equals(selectText)){
					sb.append(" and o.order_state = :state ");
					map.put("state",SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY);
					
				}else if(SysStaticDataEnumYunQi.ORDER_COUNT_SELECT_TYPE.DO_SIGN.equals(selectText)){
					sb.append(" and o.order_state = :state ");
					map.put("state",SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN);
				}
			}else if(selectType == SysStaticDataEnumYunQi.ORDER_COUNT_SELECT.TENANT_COM){
				sb.append(" and (t.CARRIER_NAME like :carrierName or o.COMPANY_NAME like :carrierName) ");
				map.put("carrierName", "%"+selectText+"%");
			}else if (selectType == SysStaticDataEnumYunQi.ORDER_COUNT_SELECT.GOODS_TYPE) {
				if (SysStaticDataEnumYunQi.ORDER_COUNT_SELECT_TYPE.SEND_GOODS.equals(selectText)) {
					sb.append(" and (o.ORDER_TYPE = 1 ) ");
				}else if(SysStaticDataEnumYunQi.ORDER_COUNT_SELECT_TYPE.ORDERS_GOODS.equals(selectText)){
					sb.append(" and (o.ORDER_TYPE = 2 ) ");
				}
			}
		}
		String orderTime = DataFormat.getStringKey(inParam, "orderTime");
		String date = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
		//zy  现在改为 orderTime 为number 1 昨天   2 三天内  3 一周   4 一个月 ， 5 不传默认为统计当天  
		int orderTimeType = DataFormat.getIntKey(inParam, "orderTimeType");
		String yesterday   =  getBeforOrAfterDaytime(-1);//昨天
	    String BeforTwoDay = getBeforOrAfterDaytime(-2);//前两天
	    String BeforSixDay = getBeforOrAfterDaytime(-6);//前六天
		
		
		if (userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS) {
			//orderTimeType =1 是昨天， 2  三天内  3 一周    4  一个月   
			//商家用下单时间查询
			if(orderTimeType == 1){
				//昨天
				sb.append(" and o.CREATE_DATE >= :oneDay ");
				sb.append(" and o.CREATE_DATE <= :lastDay ");
				map.put("oneDay", yesterday+" 00:00:00");
				map.put("lastDay", yesterday+" 23:59:59");
			}else if(orderTimeType == 2){
				//三天以内
				sb.append(" and o.CREATE_DATE >= :oneDay ");
				sb.append(" and o.CREATE_DATE <= :lastDay ");
				map.put("oneDay", BeforTwoDay+" 00:00:00");
				map.put("lastDay", date+" 23:59:59");
			}else if(orderTimeType == 3){
				//一周以内
				sb.append(" and o.CREATE_DATE >= :oneDay ");
				sb.append(" and o.CREATE_DATE <= :lastDay ");
				map.put("oneDay", BeforSixDay +" 00:00:00");
				map.put("lastDay", date+" 23:59:59");
			}else if(StringUtils.isNotEmpty(orderTime)){
				//一个月
				String[] arr = orderTime.split("-");
				int month = Integer.parseInt(arr[1])-1;
				String lastDay = CommonUtil.getLastDayOfMonth(Integer.parseInt(arr[0]), month);
				String oneDay = CommonUtil.getFirstDayOfMonth(Integer.parseInt(arr[0]),month);
				sb.append(" and o.CREATE_DATE >= :oneDay ");
				sb.append(" and o.CREATE_DATE <= :lastDay ");
				map.put("oneDay", oneDay+" 00:00:00");
				map.put("lastDay", lastDay+" 23:59:59");
			}else  {
				//今天
				sb.append(" and o.CREATE_DATE >= :dateSta ");
				sb.append(" and o.CREATE_DATE <= :dateEnd ");
				map.put("dateSta", date+" 00:00:00");
				map.put("dateEnd", date+" 23:59:59");
			}			
		}else{	
			//专线用开单时间查询
			if(orderTimeType == 1){
				//昨天
				sb.append(" and p.INPUT_TIME >= :oneDay ");
				sb.append(" and  p.INPUT_TIME <= :lastDay ");
				map.put("oneDay", yesterday+" 00:00:00");
				map.put("lastDay", yesterday+" 23:59:59");
			}else if(orderTimeType == 2){
				//三天以内
				sb.append(" and  p.INPUT_TIME >= :oneDay ");
				sb.append(" and  p.INPUT_TIME <= :lastDay ");
				map.put("oneDay", BeforTwoDay+" 00:00:00");
				map.put("lastDay", date+" 23:59:59");
			}else if(orderTimeType == 3){
				//一周以内
				sb.append(" and  p.INPUT_TIME >= :oneDay ");
				sb.append(" and p.INPUT_TIME <= :lastDay ");
				map.put("oneDay", BeforSixDay +" 00:00:00");
				map.put("lastDay", date+" 23:59:59");
			}else if(StringUtils.isNotEmpty(orderTime)){
				//上个月
				String[] arr = orderTime.split("-");
				int month = Integer.parseInt(arr[1])-1;
				String lastDay = CommonUtil.getLastDayOfMonth(Integer.parseInt(arr[0]), month);
				String oneDay = CommonUtil.getFirstDayOfMonth(Integer.parseInt(arr[0]),month);
				sb.append(" and p.INPUT_TIME >= :oneDay ");
				sb.append(" and  p.INPUT_TIME <= :lastDay ");
				map.put("oneDay", oneDay+" 00:00:00");
				map.put("lastDay", lastDay+" 23:59:59");
			}else {
				//今天
				sb.append(" and  p.INPUT_TIME >= :dateSta ");
				sb.append(" and  p.INPUT_TIME <= :dateEnd ");
				map.put("dateSta", date+" 00:00:00");
				map.put("dateEnd", date+" 23:59:59");
			}	
		}
		
		if(userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS){
			sb.append(" order by o.order_id desc ");
		}else{
			sb.append(" order by t.order_id desc ");
		}
		
		Query query = session.createSQLQuery(sb.toString());
		query.setProperties(map);
		return query;
	}
	
	/**
	 * 对接获取开单信息
	 * @param inParam
	 * @return
	 */
	public Query abutmentGetOrderInfo(Map<String,Object> inParam,String TMS){
		Session session = SysContexts.getEntityManager(true);
		String staTime = DataFormat.getStringKey(inParam, "staTime");
		List<SysStaticData> list = SysStaticDataUtil.getSysStaticDataList("TMS_TENANT_YQ");
		long tenantId = -1;
		if (list != null && list.size() > 0) {
			for (SysStaticData sysStaticData : list) {
				if (TMS.equals(sysStaticData.getCodeTypeAlias())) {
					tenantId = Long.valueOf(sysStaticData.getCodeValue());
				}
			}
		}
		if (tenantId < 0) {
			throw new BusinessException("没有对接的租户配置！");
		}
		if (StringUtils.isEmpty(staTime)) {
			throw new BusinessException("请传入开始开单时间！");
		}
		String endTime = DataFormat.getStringKey(inParam, "endTime");
		if (StringUtils.isEmpty(endTime)) {
			throw new BusinessException("请传入结束开单时间！");
		}
		int count = DataFormat.getIntKey(inParam, "count");
		if (count < 0) {
			throw new BusinessException("请传入每页的行数！");
		}
		if (count > 200) {
			throw new BusinessException("获取的每页的行数不能大于200！");
		}
		StringBuffer sb = new StringBuffer(" select {o.*},{f.*} from order_info {o} LEFT JOIN order_fee {f} ON {o}.ORDER_ID = {f}.ORDER_ID WHERE 1=1  ");
		sb.append(" AND {o}.create_time >= :staTime ");
		sb.append(" AND {o}.create_time <= :endTime ");
		sb.append(" AND {o}.tenant_id = :tenantId ");
		sb.append(" order by {o}.order_id desc ");
		Query query = session.createSQLQuery(sb.toString()).addEntity("o",OrderInfo.class).addEntity("f",OrderFee.class);
		query.setParameter("staTime", staTime);
		query.setParameter("endTime", endTime);
		query.setParameter("tenantId", tenantId);
		return query;
	}


	public String getFlowId(String pictureUrl)  {
		// TODO Auto-generated method stub
		String flowId="";
		//分割图片Id
	    List<String> imageUrlList = new ArrayList<String>();
	    String urlStr[] = pictureUrl.split(",");
	    for(int i=0;i< urlStr.length; i++){
	    	imageUrlList.add(urlStr[i]);
	    }
		if(StringUtils.isEmpty(pictureUrl)){
			return flowId;
		}
		//根据图片URL 得到图片id 
		SysAttachFileBO attach= (SysAttachFileBO) SysContexts.getBean("attach");
		for (int  i=0;i<imageUrlList.size();i++) {
			 String imagePath = "";
			 imagePath =imageUrlList.get(i);
			 //TODO 调用翁哥写的接口上传图片
		    try {
				   if(i==0){
				        flowId=attach.doUploadWithPath(imagePath); 
				   }else{
				    	flowId=flowId+","+attach.doUploadWithPath(imagePath);//多个图片id 
				    }
						
				} catch (Exception e) {
						// TODO Auto-generated catch block
		 			//e.printStackTrace();
		 			throw new BusinessException("加载图片需要全路径，图片以http://开头!");
				}
			 }
		return flowId;	
	}
	
	public long getOrderIdByTrackingNum(Map<String, Object> inParam, String TMS, long tenantId )  {
		List<SysStaticData> list = SysStaticDataUtil.getSysStaticDataList("YQ_TENANT_TMS");
		long tenantIdNew = -1;
		if (list != null && list.size() > 0) {
			for (SysStaticData sysStaticData : list) {
				if (TMS.equals(sysStaticData.getCodeTypeAlias())) {
					tenantIdNew = Long.valueOf(sysStaticData.getCodeValue());
				}
			}
		}
		if (tenantIdNew< 0) {
			throw new BusinessException("没有对接的租户配置！");
		}
		Session session = SysContexts.getEntityManager(true);
		String orderNumber = DataFormat.getStringKey(inParam, "orderNumber");
		Criteria ca = session.createCriteria(OrdOrdersInfo.class);
		ca.add(Restrictions.eq("trackingNum",orderNumber ));//运单号
		ca.add(Restrictions.eq("tenantId", tenantId));//租户ID
		List<OrdOrdersInfo> orderInfoList = ca.list();	
	   if(orderInfoList == null || orderInfoList.size()<=0){
		   throw new BusinessException("没有此订单信息"); 
	   }
		return orderInfoList.get(0).getOrderId();
		
	}
	//得到订单号
	public long getOrderIdByTrackingNum(String trackNum, long tenantId )  {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrdOrdersInfo.class);
		ca.add(Restrictions.eq("trackingNum",trackNum ));//运单号
		ca.add(Restrictions.eq("tenantId", tenantId));//租户ID
		List<OrdOrdersInfo> orderInfoList = ca.list();	
	   if(orderInfoList == null || orderInfoList.size()<=0){
		   throw new BusinessException("没有此运单信息"); 
	   }
		return orderInfoList.get(0).getOrderId();
		
	}
	
	public long getOrderIdByTrackingNum(String trackNum )  {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrdOrdersInfo.class);
		ca.add(Restrictions.eq("trackingNum",trackNum ));//运单号
		List<OrdOrdersInfo> orderInfoList = ca.list();	
	   if(orderInfoList == null || orderInfoList.size()<=0){
		   throw new BusinessException("没有此运单信息"); 
	   }
		return orderInfoList.get(0).getOrderId();
		
	}
	/*
	 *获取前/后几天的时间 
	 * day:为负数，得到提前几天时间
	 * day:为正数，得到以后几天时间
	 */
	
	public String getBeforOrAfterDaytime(int day) {
		  
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
		  Calendar c = Calendar.getInstance();  
		  c.add(Calendar.DATE, day);  
		  Date monday = c.getTime();
		  String preOrafterDay = sdf.format(monday);
		  return preOrafterDay;
    } 
	/**
	 * 状态靠后的状态
	 * @param orderInfo
	 * @return
	 */
	public int maxOrderState(long orderId){
		Session session = SysContexts.getEntityManager(true);
		List<Object> list = session.createQuery("select orderState from OrderInfo where orderId = :orderId ").setParameter("orderId", orderId).list();
		if (list!=null&&list.size()>0&&list.get(0)!=null&&Integer.valueOf(list.get(0).toString()) == SysStaticDataEnumYunQi.ORDER_INFO_STATE.PARTIAL_SIGN) {
			return Integer.parseInt(list.get(0).toString());
		}
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		List<Object> childOrderStates = orderInfoChildTF.getChildOrderStates(orderId);
		List<Map<String,Integer>> listMap = new ArrayList<Map<String,Integer>>();
		if (childOrderStates != null && childOrderStates.size() > 0) {
			
			for (int i = 0; i < childOrderStates.size(); i++) {
				int state = childOrderStates.get(i) != null ? Integer.parseInt(childOrderStates.get(i).toString()) : -1;
				Map<String,Integer> map = new HashMap<String, Integer>();
				
				//先分优先级
				if (state > 0) {
					switch (state) {
					case SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN:
						map.put("level", 1);
						map.put("orderState", state);
						break;
					case SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DEPARTURE:
						map.put("level", 2);
						map.put("orderState", state);
						break;
					case SysStaticDataEnumYunQi.ORDER_INFO_STATE.IN_TRANSIT:
						map.put("level", 3);
						map.put("orderState", state);
						break;
					case SysStaticDataEnumYunQi.ORDER_INFO_STATE.FLOATION_CARGO:
						map.put("level", 4);
						map.put("orderState", state);
						break;
					case SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY:
						map.put("level", 5);
						map.put("orderState", state);
						break;
					case SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN:
						map.put("level", 6);
						map.put("orderState", state);
						break;
					default:
						map.put("level", 7);
						map.put("orderState", state);
						break;
					}
				}
				listMap.add(map);
			}
		}
		//获取优先级最大的状态
		if (listMap != null && listMap.size() > 0) {
			int level = listMap.get(0).get("level");
			int orderState = listMap.get(0).get("orderState");
	        for(int i=1;i<listMap.size();i++){
	            if(listMap.get(i).get("level")>level){
	            	orderState=listMap.get(i).get("orderState");
	            }
	        }
	        return orderState;
		}
		return -1;
	}
	/**
	 * 判断是否存在该运单号
	 * @param trackingNum
	 * @param tenantId
	 * @return
	 */
	public boolean checkTrackingNum(String trackingNum,long tenantId){
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery(" select order_number from order_info where order_number =:trackingNum and tenant_id =:tenantId ");
		query.setParameter("trackingNum", trackingNum);
		query.setParameter("tenantId", tenantId);
		List<Object> list = query.list();
		if (list!=null&&list.size()>0) {
			return true;
		}
		return false;
	}


	public Query queryChildTrackList(Map<String, Object> inParam,int orderState) {
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		String  beginArrivalTime=DataFormat.getStringKey(inParam, "beginArrivalTime");
		String  endArrivalTime=DataFormat.getStringKey(inParam, "endArrivalTime");
		String  consigneeName=DataFormat.getStringKey(inParam, "consigneeName");
		String  trackingNum=DataFormat.getStringKey(inParam, "trackingNum");
		StringBuffer sql = new StringBuffer();
		Map<String,Object> sqlParam = new HashMap<String,Object>();
//		sql.append(" SELECT OC.CHILD_TRACKING_NUM_ALI,OT.CONSIGNEE,OT.des_city_name,OC.tracking_num,OC.child_tracking_num,"
//				+ "  OC.CHILD_ORDER_ID,OC.ORDER_ID,OD.BATCH_NUM,OC.child_order_id"
//				+ " FROM order_info_child OC, order_info OT,ord_depart_detail OD,ord_depart_info OI"
//				+ " WHERE OC.ORDER_ID = OT.ORDER_ID AND OI.BATCH_NUM = OD.BATCH_NUM "
//				+ " AND OI.DESC_ORG_ID = OC.DISPATCHING_ORG_ID AND OC.child_order_id = OD.order_ID"
//				+ "  AND OC.child_order_state = 2  AND OC.CURRENT_ORG_ID = :currentOrgId ");
		sql.append(" SELECT OC.CHILD_TRACKING_NUM_ALI,OT.CONSIGNEE,OT.des_city_name,OC.tracking_num,OC.child_tracking_num,OC.CHILD_ORDER_ID,OC.ORDER_ID,OC.child_order_id,oc.child_order_state ");
		sql.append(" FROM order_info_child OC INNER JOIN order_info OT ON OC.ORDER_ID=OT.ORDER_ID   ");
		if (StringUtils.isNotEmpty(beginArrivalTime) || StringUtils.isNotEmpty(endArrivalTime)) {
			sql.append(" LEFT JOIN  (select dd.ARRIVAL_TIME,dd.ORDER_ID,od.DESC_ORG_ID from ord_depart_detail dd,ord_depart_info od  ");
			sql.append(" where dd.BATCH_NUM = od.BATCH_NUM ) as arrivalTime ON arrivalTime.order_id = oc.CHILD_ORDER_ID and  arrivalTime.DESC_ORG_ID = oc.DISPATCHING_ORG_ID ");
		}
		sql.append(" where OC.CURRENT_ORG_ID = :currentOrgId ");
		sql.append(" and OC.child_order_state = :orderState  ");
		sqlParam.put("orderState", orderState);
		sqlParam.put("currentOrgId", user.getOrgId());
		if(StringUtils.isNotEmpty(beginArrivalTime)){
			sql.append("  and arrivalTime.ARRIVAL_TIME >= :beginArrivalTime ");
			sqlParam.put("beginArrivalTime", beginArrivalTime+"00:00:00");
		}
		if(StringUtils.isNotEmpty(endArrivalTime)){
			sql.append("  and arrivalTime.ARRIVAL_TIME <= :endArrivalTime  ");
			sqlParam.put("endArrivalTime", endArrivalTime+"59:59:59");
		}
		if(StringUtils.isNotEmpty(consigneeName)){
			sql.append("  and OT.CONSIGNEE like :consigneeName ");
			sqlParam.put("consigneeName", "%"+consigneeName+"%");
		}
		if(StringUtils.isNotEmpty(trackingNum)){
			sql.append("  and  OC.tracking_num like :trackingNum ");
			sqlParam.put("trackingNum", "%" +trackingNum+"%");
		}
		 sql.append("  order by OC.child_tracking_num  desc");
		 Query query = session.createSQLQuery(sql.toString());
		 query.setProperties(sqlParam);
		 return query;
	}
	
	public Query queryOrderListByOrderState(Map<String, Object> inParam,Integer[] orderState) {
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		String  beginArrivalTime=DataFormat.getStringKey(inParam, "beginArrivalTime");
		String  endArrivalTime=DataFormat.getStringKey(inParam, "endArrivalTime");
		String  consigneeName=DataFormat.getStringKey(inParam, "consigneeName");
		String  trackingNum=DataFormat.getStringKey(inParam, "trackingNum");
		StringBuffer sql = new StringBuffer();
		Map<String,Object> sqlParam = new HashMap<String,Object>();
//		sql.append("SELECT OT.order_number, OT.CONSIGNEE, OT.des_city_name, OT.ORDER_ID,OT.order_state "
//				+ "FROM order_info_child OC ,  order_info  OT, ord_depart_detail OD  "
//				+ "WHERE OC.ORDER_ID = OT.ORDER_ID  AND OC.child_order_id = OD.ORDER_ID "
//				+ "  AND OC.CURRENT_ORG_ID = :currentOrgId  AND OT.ORDER_STATE IN (:orderState) and OC.child_order_state = :childOrderState ");
		
		sql.append(" SELECT OT.order_number, OT.CONSIGNEE, OT.des_city_name, OT.ORDER_ID,OT.order_state, OT.order_state_out  ");
		sql.append(" FROM order_info_child OC INNER JOIN order_info OT ON OC.ORDER_ID=OT.ORDER_ID   ");
		if (StringUtils.isNotEmpty(beginArrivalTime) || StringUtils.isNotEmpty(endArrivalTime)) {
			sql.append(" LEFT JOIN  (select dd.ARRIVAL_TIME,dd.ORDER_ID,od.DESC_ORG_ID from ord_depart_detail dd,ord_depart_info od  ");
			sql.append(" where dd.BATCH_NUM = od.BATCH_NUM ) as arrivalTime ON arrivalTime.order_id = oc.CHILD_ORDER_ID and  arrivalTime.DESC_ORG_ID = oc.DISPATCHING_ORG_ID ");
		}
		sql.append(" where OC.CURRENT_ORG_ID = :currentOrgId ");
		sql.append(" AND OT.ORDER_STATE_OUT IN (:orderState) and OC.child_order_state = :childOrderState  ");
		sqlParam.put("currentOrgId", user.getOrgId());
		sqlParam.put("orderState", orderState);
		sqlParam.put("childOrderState", SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN);
		if(StringUtils.isNotEmpty(beginArrivalTime)){
			sql.append("  and arrivalTime.ARRIVAL_TIME >= :beginArrivalTime  ");
			sqlParam.put("beginArrivalTime", beginArrivalTime+"00:00:00");
		}
		if(StringUtils.isNotEmpty(endArrivalTime)){
			sql.append("  and arrivalTime.ARRIVAL_TIME <= :endArrivalTime  ");
			sqlParam.put("endArrivalTime", endArrivalTime+"59:59:59");
		}
		if(StringUtils.isNotEmpty(consigneeName)){
			sql.append("  and OT.CONSIGNEE like :consigneeName ");
			sqlParam.put("consigneeName", "%"+consigneeName+"%");
		}
		if(StringUtils.isNotEmpty(trackingNum)){
			sql.append("  and  OT.order_number like :trackingNum ");
			sqlParam.put("trackingNum", "%" +trackingNum+"%");
		}
		 sql.append("  GROUP BY ot.order_id ORDER BY OT.order_number DESC ");
		 Query query = session.createSQLQuery(sql.toString());
		 query.setProperties(sqlParam);
		 return query;
	}
	
	
	public Query queryChileTrackLBybacthNum(Map<String, Object> inParam,  int vehiclelist) {
		BaseUser user = SysContexts.getCurrentOperator();
		long orgId = user.getOrgId();
		Session session = SysContexts.getEntityManager(true);
		String  batchNum=DataFormat.getStringKey(inParam, "batchNum");
		StringBuffer sql = new StringBuffer();
		Map<String,Object> sqlParam = new HashMap<String,Object>();
		sql.append("SELECT OC.CHILD_TRACKING_NUM_ALI, OT.consignee, OT.des_city_name,OC.child_order_state,"
				+ "  OC.child_tracking_num,"
				+ " OT.VOLUME, OT.WEIGHT,OC.order_id,OC.child_order_id"
				+ " FROM order_info_child OC,order_info OT, ord_depart_info OD, ord_depart_detail CV "
				+ " WHERE OC.order_id = OT.order_id  AND CV.order_id = OC.CHILD_ORDER_ID AND OD.batch_num = CV.batch_num  ");
			sql.append("AND OC.CHILD_ORDER_STATE = :list  and OC.CURRENT_ORG_ID = :currentOrgId ");
		if(StringUtils.isNotEmpty(batchNum)){
			sql.append("  and OD.BATCH_NUM = :batchNum  ");
			sqlParam.put("batchNum", batchNum);
		}
		 sqlParam.put(" list",  vehiclelist);
		 sqlParam.put("currentOrgId", user.getOrgId());
		 sql.append("  GROUP BY OC.child_tracking_num "); 
		 Query query = session.createSQLQuery(sql.toString());
		 query.setParameter("list", vehiclelist);
		 query.setParameter("batchNum", batchNum);
		 query.setParameter("currentOrgId",orgId);
		 //query.setProperties(sqlParam);
		 return query;		 
	}
	
	public Query queryChileTrackBybacthNum(Map<String, Object> inParam,  int vehiclelist) {
		BaseUser user = SysContexts.getCurrentOperator();
		long orgId = user.getOrgId();
		Session session = SysContexts.getEntityManager(true);
		String  batchNum=DataFormat.getStringKey(inParam, "batchNum");
		StringBuffer sql = new StringBuffer();
		Map<String,Object> sqlParam = new HashMap<String,Object>();
		sql.append("SELECT OC.CHILD_TRACKING_NUM_ALI, OT.consignee, OT.des_city_name,OC.child_order_state,"
				+ "  OC.child_tracking_num,"
				+ " OT.VOLUME, OT.WEIGHT, OC.order_id,OC.child_order_id"
				+ " FROM order_info_child OC,order_info OT, ord_depart_info OD, ord_depart_detail CV "
				+ " WHERE OC.order_id = OT.order_id  AND CV.order_id = OC.CHILD_ORDER_ID AND OD.batch_num = CV.batch_num  ");
			sql.append("AND OC.CHILD_ORDER_STATE != :list  and OC.CURRENT_ORG_ID = :currentOrgId ");
		if(StringUtils.isNotEmpty(batchNum)){
			sql.append("  and OD.BATCH_NUM = :batchNum  ");
			sqlParam.put("batchNum", batchNum);
		}
		 sqlParam.put(" list",  vehiclelist);
		 sqlParam.put("currentOrgId", user.getOrgId());
		 sql.append("  GROUP BY OC.child_tracking_num "); 
		 Query query = session.createSQLQuery(sql.toString());
		 query.setParameter("list", vehiclelist);
		 query.setParameter("batchNum", batchNum);
		 query.setParameter("currentOrgId",orgId);
		 //query.setProperties(sqlParam);
		 return query;		 
	}
	/**
	 * 
	 * @param orderId
	 * @param type 1:加 2：减
	 */
	public void updateDepartCount(long orderId,int type){
		Session session = SysContexts.getEntityManager();
		String sql = "";
		if (type == 1) {
			sql = "update order_info o set o.DEPART_COUNT = IF(o.DEPART_COUNT is null or o.DEPART_COUNT <= 0,1,o.DEPART_COUNT+1) where o.order_id = :orderId ";
		}else if(type == 2){
			sql = "update order_info o set o.DEPART_COUNT = IF(o.DEPART_COUNT is null or o.DEPART_COUNT <= 0,0,o.DEPART_COUNT-1) where o.order_id = :orderId ";
		}
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
	}
	
	/**
	 * 校验多次配载的单
	 * @param orderId 主运单id
	 * @return
	 */
	public int checkOrderInfoMany(long orderId){
		Session session = SysContexts.getEntityManager(true);
		String hql = " select count(o.orderId) from OrderInfoChild o where o.orderId = :orderId  ";
		Object object = session.createQuery(hql).setParameter("orderId", orderId).uniqueResult();
		if (object != null) {
			return Integer.valueOf(object.toString());
		}
		return 0;
	}
	/**
	 * 查询运单状态
	 */
	public Integer queryOrderInterchange(long orderId){
		Session session = SysContexts.getEntityManager(true);
		return (Integer) session.createQuery(" select o.interchange from OrderInfo o where o.orderId = :orderId ").setParameter("orderId", orderId).uniqueResult();
	}
	/**
	 * 运单管理查询列表
	 * @param inParam
	 * @param isSum
	 * @return
	 */
	public Query queryOrderInfoWeb(Map<String,Object> inParam,boolean isSum){
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer(" SELECT ");
		if (isSum) {
			sb.append(" SUM(o.NUMBER),SUM(o.WEIGHT),SUM(o.VOLUME),SUM(f.FREIGHT), ");
			sb.append(" SUM(f.REPUTATION),SUM(f.PREMIUM),SUM(f.DELIVERY_CHARGE),SUM(f.TRANSIT_FEE),SUM(f.TIP), ");
			sb.append(" SUM(f.COLLECT_MONEY),SUM(f.LAND_FEE),SUM(f.SERVICE_CHARGE),SUM(f.OTHER_FEE),SUM(f.TOTAL_FEE), ");
		}else{
			sb.append(" o.ORDER_ID,o.ORDER_NUMBER,o.ORDER_STATE,os.ORDER_NO,o.BILLING_ORG_ID, ");
			sb.append(" o.TENANT_ID,o.CREATE_NAME,o.CREATE_TIME,o.DES_CITY_NAME,o.PULL_NAME, ");
			sb.append(" o.PULL_PHONE,o.CONSIGNOR,o.CONSIGNOR_PHONE,o.CONSIGNOR_ADDRESS,o.CONSIGNEE, ");
			sb.append(" o.CONSIGNEE_PHONE,o.CONSIGNEE_ADDRESS,o.INTERCHANGE,o.PAYMENT,o.PRODUCT_NAME, ");
			sb.append(" o.NUMBER,o.PACK_NAME,o.WEIGHT,o.VOLUME,f.FREIGHT, ");
			sb.append(" f.REPUTATION,f.PREMIUM,f.DELIVERY_CHARGE,f.TRANSIT_FEE,f.TIP, ");
			sb.append(" f.COLLECT_MONEY,f.LAND_FEE,f.SERVICE_CHARGE,f.OTHER_FEE,f.TOTAL_FEE, ");
			sb.append(" o.REMARKS ");
		}
		sb.append(" FROM  order_info o INNER JOIN ord_orders_info os ON o.ORDS_ID = os.ORDER_ID ");
		sb.append(" INNER JOIN order_fee f ON f.ORDER_ID = o.ORDER_ID ");
		sb.append(" WHERE 1=1 ");
		sb.append(" AND o.TENANT_ID = :tenantId ");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tenantId", user.getTenantId());
		String createTimeBegin = DataFormat.getStringKey(inParam, "createTimeBegin");
		if (StringUtils.isNotEmpty(createTimeBegin)) {
			sb.append(" AND o.create_time >= :createTimeBegin ");
			map.put("createTimeBegin", createTimeBegin+" 00:00:00");
		}
		String createTimeEnd = DataFormat.getStringKey(inParam, "createTimeEnd");
		if (StringUtils.isNotEmpty(createTimeEnd)) {
			sb.append(" AND o.create_time <= :createTimeEnd ");
			map.put("createTimeEnd", createTimeEnd+" 23:59:59");
		}
		int orderState = DataFormat.getIntKey(inParam, "orderState");
		if (orderState > 0) {
			sb.append(" AND o.order_state = :orderState ");
			map.put("orderState", orderState);
		}
		String orderNo = DataFormat.getStringKey(inParam, "orderNo");
		if (StringUtils.isNotEmpty(orderNo)) {
			sb.append(" AND os.order_no like :orderNo ");
			map.put("orderNo", "%"+orderNo+"%");
		}
		String trackingNum = DataFormat.getStringKey(inParam, "trackingNum");
		if (StringUtils.isNotEmpty(trackingNum)) {
			sb.append(" AND o.order_number like :trackingNum ");
			map.put("trackingNum", "%"+trackingNum+"%");
		}
		String consignor = DataFormat.getStringKey(inParam, "consignor");
		if (StringUtils.isNotEmpty(consignor)) {
			sb.append(" AND o.consignor like :consignor ");
			map.put("consignor", "%"+consignor+"%");
		}
		String consignorPhone = DataFormat.getStringKey(inParam, "consignorPhone");
		if (StringUtils.isNotEmpty(consignorPhone)) {
			sb.append(" AND o.consignor_phone like :consignorPhone ");
			map.put("consignorPhone", "%"+consignorPhone+"%");
		}
		String consignee = DataFormat.getStringKey(inParam, "consignee");
		if (StringUtils.isNotEmpty(consignee)) {
			sb.append(" AND o.consignee like :consignee ");
			map.put("consignee", "%"+consignee+"%");
		}
		String consigneePhone = DataFormat.getStringKey(inParam, "consigneePhone");
		if (StringUtils.isNotEmpty(consigneePhone)) {
			sb.append(" AND o.consignee_Phone like :consigneePhone ");
			map.put("consigneePhone", "%"+consigneePhone+"%");
		}
		String pullName = DataFormat.getStringKey(inParam, "pullName");
		if (StringUtils.isNotEmpty(pullName)) {
			sb.append(" AND o.pull_Name like :pullName ");
			map.put("pullName", "%"+pullName+"%");
		}
		String pullPhone = DataFormat.getStringKey(inParam, "pullPhone");
		if (StringUtils.isNotEmpty(pullPhone)) {
			sb.append(" AND o.pull_Phone like :pullPhone ");
			map.put("pullPhone", "%"+pullPhone+"%");
		}
		sb.append(" order by o.order_id desc ");
		return session.createSQLQuery(sb.toString()).setProperties(map);
	}
	/**
	 * 通过orderId订单号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getOrderInfoByLong(List<String> orderId){
		Session session = SysContexts.getEntityManager(true);
		List<Object> list = session.createSQLQuery("select o.order_Id from Order_Info o where o.ords_Id in (:orderId) ").setParameterList("orderId", orderId).list();
		List<Long> orders = new ArrayList<Long>();
		for (Object obj : list) {
			if (obj!=null) {
				orders.add(Long.valueOf(obj.toString()));
			}
		}
		return orders;
	}
	
	/**
	 * 运单号获取子运单id
	 * @param inParam
	 * @param TMS
	 * @param tenantId
	 * @return
	 */
	public List<String> getChildIdsTrackingNum(Map<String, Object> inParam, String TMS, long tenantId )  {
		List<SysStaticData> list = SysStaticDataUtil.getSysStaticDataList("YQ_TENANT_TMS");
		long tenantIdNew = -1;
		if (list != null && list.size() > 0) {
			for (SysStaticData sysStaticData : list) {
				if (TMS.equals(sysStaticData.getCodeTypeAlias())) {
					tenantIdNew = Long.valueOf(sysStaticData.getCodeValue());
				}
			}
		}
		if (tenantIdNew< 0) {
			throw new BusinessException("没有对接的租户配置！");
		}
		Session session = SysContexts.getEntityManager(true);
		String orderNumber = DataFormat.getStringKey(inParam, "orderNumber");
		Criteria ca = session.createCriteria(OrderInfoChild.class);
		ca.add(Restrictions.eq("trackingNum", orderNumber));
		ca.add(Restrictions.eq("tenantId", tenantId));
		ca.add(Restrictions.eq("childOrderState", SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY));
		List<OrderInfoChild> childs = ca.list();
		List<String> childOrderIds = new ArrayList<String>();
		if (childs!=null&&childs.size() > 0) {
			for (OrderInfoChild orderInfoChild : childs) {
				childOrderIds.add(String.valueOf(orderInfoChild.getChildOrderId()));
			}
		}
		return childOrderIds;
	}
	
	
	public long getOrderInfoByTrackingNum(Map<String, Object> inParam, String TMS, long tenantId )  {
		List<SysStaticData> list = SysStaticDataUtil.getSysStaticDataList("YQ_TENANT_TMS");
		long tenantIdNew = -1;
		if (list != null && list.size() > 0) {
			for (SysStaticData sysStaticData : list) {
				if (TMS.equals(sysStaticData.getCodeTypeAlias())) {
					tenantIdNew = Long.valueOf(sysStaticData.getCodeValue());
				}
			}
		}
		if (tenantIdNew< 0) {
			throw new BusinessException("没有对接的租户配置！");
		}
		Session session = SysContexts.getEntityManager(true);
		String orderNumber = DataFormat.getStringKey(inParam, "orderNumber");
		Criteria ca = session.createCriteria(OrderInfo.class);
		ca.add(Restrictions.eq("orderNumber",orderNumber ));//运单号
		ca.add(Restrictions.eq("tenantId", tenantId));//租户ID
		List<OrderInfo> orderInfoList = ca.list();	
	   if(orderInfoList == null && orderInfoList.size()<=0  ){
		   throw new BusinessException("没有此订单信息"); 
	   }
		return orderInfoList.get(0).getOrderId();
		
	}
	/**
	 * 运单主键id查询已签收的数量
	 * @param orderId
	 * @return
	 */
	public int isPartialSign(long orderId){
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createQuery("select count(*) from OrderInfo o,OrderInfoChild c where o.orderId = c.orderId and c.childOrderState = :state and c.orderId = :orderId ");
		query.setParameter("orderId", orderId);
		query.setParameter("state", SysStaticDataEnumYunQi.ORDER_INFO_STATE.DO_SIGN);
		Object obj = query.uniqueResult();
		if (obj!=null) {
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
	/**
	 * 签收回显【601075】
	 * @param orderId
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> getOrdSign(long orderId) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createQuery("select s.orderId,s.flowId,s.signDesc from OrdSignInfo s,OrderInfo o where o.ordsId = s.orderId  and o.orderId = :orderId ").setParameter("orderId", orderId);
		List<Object[]> list = query.list();
		SysAttachSV sysAttachSV = (SysAttachSV) SysContexts.getBean("sysAttachSV");
		Map<String,Object> map = new HashMap<String, Object>();
		if (list!=null&&list.size()>0) {
			for (Object[] objects : list) {
				map.put("remark", objects[2] != null ? objects[2].toString(): "");
				List<Map<String,Object>> flowIdList = new ArrayList<Map<String,Object>>();
				if (objects[1]!=null) {
					String sFlowId = objects[1].toString();
					String[] flowIds = sFlowId.split(",");
					for (String string : flowIds) {
						if (StringUtils.isNotBlank(string)||StringUtils.isNotEmpty(string) ) {
							Map<String,Object> map2 = new HashMap<String, Object>(); 
							map2.put("signImgId", string);
							map2.put("signImgUrl", sysAttachSV.getAttachFile(string));
							flowIdList.add(map2);
						}
					}
				}
				map.put("items", flowIdList);
			}
		}
		return map;
	}


	public OrderInfo getOrderInfoByOrderNumber(String trackingNum) {
		// TODO Auto-generated method stub
		Session session = SysContexts.getEntityManager(true);
		Criteria orderInfo = session.createCriteria(OrderInfo.class);
		orderInfo.add(Restrictions.eq("orderNumber", trackingNum));
	    List<OrderInfo> orderInfos = orderInfo.list();
	    if(orderInfos==null || orderInfos.size()<=0){
	    	throw new BusinessException("无此运单信息！");
	    }
		return orderInfos.get(0);
	}


	public int getOrderStateOut(int orderState) {
		int state = 0;
		if (orderState > 0) {
			switch (orderState) {
			case SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN:
				state = SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PENDING_DEPARTURE;
				break;
			case SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DEPARTURE:
				state = SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PENDING_DEPARTURE;
				break;
			case SysStaticDataEnumYunQi.ORDER_INFO_STATE.IN_TRANSIT:
				state = SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.IN_TRANSIT;
				break;
			case SysStaticDataEnumYunQi.ORDER_INFO_STATE.FLOATION_CARGO:
				state = SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.IN_TRANSIT;
				break;
			case SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY:
				state = SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PENDING_DELIVERY;
				break;
			case SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN:
				state = SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.WAIT_SIGN;
				break;
			case SysStaticDataEnumYunQi.ORDER_INFO_STATE.PARTIAL_SIGN:
				state = SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PARTIAL_SIGN;
				break;
			case SysStaticDataEnumYunQi.ORDER_INFO_STATE.CANCERING:
				state = SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.CANCERING;
				break;
			case SysStaticDataEnumYunQi.ORDER_INFO_STATE.DO_CANCER:
				state = SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.DO_CANCER;
				break;
			case SysStaticDataEnumYunQi.ORDER_INFO_STATE.DO_SIGN:
				state = SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.DO_SIGN;
				break;
			default:
				break;
			}
			
		}
		return state;
	}


	
	
	
	
}
