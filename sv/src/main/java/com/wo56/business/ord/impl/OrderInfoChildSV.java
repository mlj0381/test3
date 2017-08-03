package com.wo56.business.ord.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.wo56.business.ord.vo.OrderInfo;
import com.wo56.business.ord.vo.OrderInfoChild;
import com.wo56.business.ord.vo.OrderInfoChildSign;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;

public class OrderInfoChildSV {
	/**
	 * 保存与修改
	 * @param orderInfoChild
	 */
	public void doSaveOrUpdate(OrderInfoChild orderInfoChild,BaseUser user){
		Session session = SysContexts.getEntityManager();
		orderInfoChild.setOpId(user.getUserId());
		orderInfoChild.setOpName(user.getUserName());
		orderInfoChild.setOpTime(new Date());
		if (user.getOrgId() != null && user.getOrgId() > 0) {
			orderInfoChild.setCurrentOrgId(user.getOrgId());
		}
		session.saveOrUpdate(orderInfoChild);
	}
	
	public void doSaveOrUpdateByWeb(OrderInfoChild orderInfoChild,BaseUser user){
		Session session = SysContexts.getEntityManager();
		orderInfoChild.setOpId(user.getUserId());
		orderInfoChild.setOpName(user.getUserName());
		orderInfoChild.setOpTime(new Date());
		orderInfoChild.setCurrentOrgId(orderInfoChild.getDispatchingOrgId());
		session.saveOrUpdate(orderInfoChild);
	}
	
	
	/**
	 * 查询存在子运单待配载的运单数据
	 * @return
	 */
	public Query orderInfoByChildOrderState(Map<String,Object> inParam){
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		StringBuffer stringBuffer = new StringBuffer(" select ");
		stringBuffer.append(" o.order_id,o.VOLUME,o.WEIGHT from order_info_child c,order_info o where c.ORDER_ID = o.ORDER_ID and c.CHILD_ORDER_STATE = :childOrderState and c.CURRENT_ORG_ID = :currentOrgId ");
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("childOrderState", SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN);
		map.put("currentOrgId", user.getOrgId());
		long dispatchingOrgId = DataFormat.getLongKey(inParam, "descOrgId");
		if (dispatchingOrgId > 0) {
			stringBuffer.append(" AND c.DISPATCHING_ORG_ID = :dispatchingOrgId ");
			map.put("dispatchingOrgId", dispatchingOrgId);
		}
		String orderTimeBegin = DataFormat.getStringKey(inParam, "timeBegin");
		if (StringUtils.isNotEmpty(orderTimeBegin)) {
			stringBuffer.append(" AND o.CREATE_TIME = :orderTimeBegin ");
			map.put("orderTimeBegin", orderTimeBegin +" 00:00:00");
		}
		String orderTimeEnd = DataFormat.getStringKey(inParam, "timeEnd");
		if (StringUtils.isNotEmpty(orderTimeEnd)) {
			stringBuffer.append(" AND o.CREATE_TIME = :orderTimeEnd ");
			map.put("orderTimeEnd", orderTimeEnd+" 23:59:59");
		}
		String consigneeName = DataFormat.getStringKey(inParam, "consigneeName");
		if (StringUtils.isNotEmpty(consigneeName)) {
			stringBuffer.append(" AND o.CONSIGNEE like :consigneeName ");
			map.put("consigneeName", "%"+consigneeName+"%");
		}
		String orderNumber  = DataFormat.getStringKey(inParam, "trackingNum");
		if (StringUtils.isNotEmpty(orderNumber)) {
			stringBuffer.append(" AND o.ORDER_NUMBER like :orderNumber ");
			map.put("orderNumber", "%"+orderNumber+"%");
		}
		stringBuffer.append(" ORDER BY o.order_id desc ");
		Query query = session.createSQLQuery(stringBuffer.toString());
		query.setProperties(map);
		return query;
	}
	
	/**
	 * 查询存在子运单待配载的运单数据
	 * @return
	 */
	public Query orderInfoByChildOrderState(Map<String,Object> inParam, int childOrderState ){
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		StringBuffer stringBuffer = new StringBuffer(" select ");
		stringBuffer.append(" o.order_id,o.VOLUME,o.WEIGHT from order_info_child c,order_info o where c.ORDER_ID = o.ORDER_ID and c.CHILD_ORDER_STATE = :childOrderState and c.CURRENT_ORG_ID = :currentOrgId ");
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("childOrderState", childOrderState);
		map.put("currentOrgId", user.getOrgId());
		long dispatchingOrgId = DataFormat.getLongKey(inParam, "descOrgId");
		if (dispatchingOrgId > 0) {
			stringBuffer.append(" AND c.DISPATCHING_ORG_ID = :dispatchingOrgId ");
			map.put("dispatchingOrgId", dispatchingOrgId);
		}
		String orderTimeBegin = DataFormat.getStringKey(inParam, "timeBegin");
		if (StringUtils.isNotEmpty(orderTimeBegin)) {
			stringBuffer.append(" AND o.CREATE_TIME = :orderTimeBegin ");
			map.put("orderTimeBegin", orderTimeBegin +" 00:00:00");
		}
		String orderTimeEnd = DataFormat.getStringKey(inParam, "timeEnd");
		if (StringUtils.isNotEmpty(orderTimeEnd)) {
			stringBuffer.append(" AND o.CREATE_TIME = :orderTimeEnd ");
			map.put("orderTimeEnd", orderTimeEnd+" 23:59:59");
		}
		String consigneeName = DataFormat.getStringKey(inParam, "consigneeName");
		if (StringUtils.isNotEmpty(consigneeName)) {
			stringBuffer.append(" AND o.CONSIGNEE like :consigneeName ");
			map.put("consigneeName", "%"+consigneeName+"%");
		}
		String orderNumber  = DataFormat.getStringKey(inParam, "trackingNum");
		if (StringUtils.isNotEmpty(orderNumber)) {
			stringBuffer.append(" AND o.ORDER_NUMBER like :orderNumber ");
			map.put("orderNumber", "%"+orderNumber+"%");
		}
		stringBuffer.append(" ORDER BY o.order_id desc ");
		Query query = session.createSQLQuery(stringBuffer.toString());
		query.setProperties(map);
		return query;
	}
	
	
	/**
	 * 查询待配载信息
	 * @param inParam
	 * @return
	 */
	public Query childStowagePlan(List<Long> ordersId){
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		StringBuffer stringBuffer = new StringBuffer(" select ");
		stringBuffer.append(" c.CHILD_ORDER_ID,c.CHILD_TRACKING_NUM_ALI,o.CONSIGNEE,o.DES_CITY_NAME,c.CHILD_ORDER_STATE,c.order_id from order_info o LEFT JOIN order_info_child c ON o.ORDER_ID = c.ORDER_ID where c.CHILD_ORDER_STATE = :childOrderState and c.CURRENT_ORG_ID = :orgId ");
		stringBuffer.append(" AND o.order_id in (:ordersId) ");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("childOrderState", SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN);
		map.put("orgId", user.getOrgId());
		map.put("ordersId", ordersId);
		Query query = session.createSQLQuery(stringBuffer.toString());
		query.setProperties(map);
		return query;
	}
	
	/**
<<<<<<< .mine
	 * 按照子运单状态查询子运单信息
	 * @param inParam
	 * @return
	 */
	public Query childStowagePlan(List<Long> ordersId,int childOrderState){
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		StringBuffer stringBuffer = new StringBuffer(" select ");
		stringBuffer.append(" c.CHILD_ORDER_ID,c.CHILD_TRACKING_NUM_ALI,o.CONSIGNEE,o.DES_CITY_NAME,c.CHILD_ORDER_STATE,c.order_id from order_info o LEFT JOIN order_info_child c ON o.ORDER_ID = c.ORDER_ID where c.CHILD_ORDER_STATE = :childOrderState and c.CURRENT_ORG_ID = :orgId ");
		stringBuffer.append(" AND o.order_id in (:ordersId) ");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("childOrderState", SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN);
		map.put("orgId", user.getOrgId());
		map.put("ordersId", ordersId);
		Query query = session.createSQLQuery(stringBuffer.toString());
		query.setProperties(map);
		return query;
	}
	/**
	 * 查询存在子运单待配载的运单数据
	 * @return
	 */
	public Query orderInfoByChild(Map<String,Object> inParam){
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		StringBuffer stringBuffer = new StringBuffer(" select ");
		stringBuffer.append(" c.CHILD_ORDER_ID,c.CHILD_TRACKING_NUM_ALI,o.CONSIGNEE,o.DES_CITY_NAME,c.CHILD_ORDER_STATE,c.order_id,o.VOLUME,o.WEIGHT,o.number ");
		stringBuffer.append(" from order_info o LEFT JOIN order_info_child c ON o.ORDER_ID = c.ORDER_ID  ");
		stringBuffer.append(" where c.CHILD_ORDER_STATE = :childOrderState and c.CURRENT_ORG_ID = :currentOrgId ");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("childOrderState", SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN);
		map.put("currentOrgId", user.getOrgId());
		long dispatchingOrgId = DataFormat.getLongKey(inParam, "descOrgId");
		if (dispatchingOrgId > 0) {
			stringBuffer.append(" AND c.DISPATCHING_ORG_ID = :dispatchingOrgId ");
			map.put("dispatchingOrgId", dispatchingOrgId);
		}
		String orderTimeBegin = DataFormat.getStringKey(inParam, "timeBegin");
		if (StringUtils.isNotEmpty(orderTimeBegin)) {
			stringBuffer.append(" AND o.CREATE_TIME >= :orderTimeBegin ");
			map.put("orderTimeBegin", orderTimeBegin +" 00:00:00");
		}
		String orderTimeEnd = DataFormat.getStringKey(inParam, "timeEnd");
		if (StringUtils.isNotEmpty(orderTimeEnd)) {
			stringBuffer.append(" AND o.CREATE_TIME <= :orderTimeEnd ");
			map.put("orderTimeEnd", orderTimeEnd+" 23:59:59");
		}
		String consigneeName = DataFormat.getStringKey(inParam, "consigneeName");
		if (StringUtils.isNotEmpty(consigneeName)) {
			stringBuffer.append(" AND o.CONSIGNEE like :consigneeName ");
			map.put("consigneeName", "%"+consigneeName+"%");
		}
		String orderNumber  = DataFormat.getStringKey(inParam, "trackingNum");
		if (StringUtils.isNotEmpty(orderNumber)) {
			stringBuffer.append(" AND o.ORDER_NUMBER like :orderNumber ");
			map.put("orderNumber", "%"+orderNumber+"%");
		}
		stringBuffer.append(" ORDER BY o.order_id desc ");
		Query query = session.createSQLQuery(stringBuffer.toString());
		query.setProperties(map);
		return query;
	}
	
	
	/**
>>>>>>> .r70708
	 * 通过id获取子运单信息
	 * @param id
	 * @return
	 */
	public OrderInfoChild getOrderInfoChild(long id){
		Session session = SysContexts.getEntityManager(true);
		OrderInfoChild orderInfoChild = (OrderInfoChild) session.get(OrderInfoChild.class, id);
		return orderInfoChild;
	}
	
	/**
	 * 通过运单id获取子运单信息
	 * @param childorderId
	 * @return
	 */
	public List<OrderInfoChild> getOrderInfoChilds(long orderId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrderInfoChild.class);
		ca.add(Restrictions.eq("orderId", orderId));
		return ca.list();
	}
	
	public List<OrderInfoChild> getOrderInfoByChildOrderId(long childOrderId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrderInfoChild.class);
		ca.add(Restrictions.eq("childOrderId", childOrderId));
		return ca.list();
	}
	
	/**
	 * 查询运单状态
	 * @param orderId
	 * @return
	 */
	public List<Object> getOrderState(long orderId){
		Session session = SysContexts.getEntityManager(true);
		String hql = "select childOrderState from OrderInfoChild where orderId = :orderId";
		//Query query = session.createSQLQuery(" select CHILD_ORDER_STATE from order_info_child where order_id = :orderId ");
		Query query = session.createQuery(hql);
		query.setParameter("orderId", orderId);
		List<Object> list = query.list();
		return list;
	}
	/**
	 * 获取多个子运单
	 * @param orderIds
	 * @return
	 */
	public Query getOrderInfoChildByOrderIds(Set<Long> orderIds){
		Session session = SysContexts.getEntityManager(true);
		return session.createSQLQuery(" select c.CHILD_ORDER_ID,c.CHILD_TRACKING_NUM_ALI,o.CONSIGNEE,o.DES_CITY_NAME,c.CHILD_ORDER_STATE,c.order_id,o.VOLUME,o.WEIGHT,o.DEPART_COUNT,o.number from order_info o LEFT JOIN order_info_child c ON o.ORDER_ID = c.ORDER_ID where c.child_order_id in (:orderIds) order by c.order_id desc ").setParameterList("orderIds", orderIds);
	}
	/**
	 * 子运单号查询子运单信息
	 * @param childTrackingNum
	 * @return
	 */
	public OrderInfoChild getOrderInfoChildByChildTrackingNum(String childTrackingNum){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrderInfoChild.class);
		ca.add(Restrictions.eq("childTrackingNum", childTrackingNum));
		List<OrderInfoChild> list = ca.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	/***
	 * 存在未到货的数据
	 * true 部分到货或者全部到货
	 * false 不存在到货
	 * @param batchNum
	 * @return
	 */
	public int checkOrderChildState(long batchNum){
		int orderNum = -1;
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		StringBuffer hql = new StringBuffer(" select ");
		hql.append(" count(*) ");
		hql.append(" from OrdDepartDetail p,OrderInfoChild c,OrdStock t where p.id.orderId = c.childOrderId ");
		hql.append(" and t.orderId = c.childOrderId ");
		hql.append(" and p.id.batchNum = :batchNum and t.stockInType = :stockInType and t.orgId = :orgId ");
		Query query = session.createQuery(hql.toString());
		query.setParameter("batchNum", batchNum);
		query.setParameter("stockInType", SysStaticDataEnum.STOCK_IN_TYPE.STOCK_IN_CAR);
		query.setParameter("orgId", user.getOrgId());
		Object obj = query.uniqueResult();
		if (obj!=null) {
			return Integer.parseInt(obj.toString());
		}
		return orderNum;
	}
	
	/**
	 * 获取多个子运单
	 * @param orderIds
	 * @return
	 */
	public Query sweepChildTrackingNum(String childTrackingNum){
		Session session = SysContexts.getEntityManager(true);
		return session.createSQLQuery(" select c.CHILD_ORDER_ID,c.CHILD_TRACKING_NUM_ALI,o.CONSIGNEE,o.DES_CITY_NAME,c.CHILD_ORDER_STATE,o.weight,o.volume,o.order_Id,o.number from order_info o LEFT JOIN order_info_child c ON o.ORDER_ID = c.ORDER_ID where c.child_tracking_num = :childTrackingNum ").setParameter("childTrackingNum", childTrackingNum);
	}
	/**
	 * 查询待签收的子运单数据
	 * @param orderId
	 * @return
	 */
	public List<OrderInfoChild> sign(long orderId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrderInfoChild.class);
		ca.add(Restrictions.eq("orderId", orderId));
		ca.add(Restrictions.eq("childOrderState", SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN));
		List<OrderInfoChild> infoChilds = ca.list();
		return infoChilds;
	}
	public void doSaveSign(OrderInfoChildSign orderInfoChildSign){
		SysContexts.getEntityManager().save(orderInfoChildSign);
	}
	/**
	 * 获取运单下子运单状态的子运单id
	 * @param orderId
	 * @return
	 */
	public List<Long> getOrderInfoChildId(long orderId,int state){
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createQuery("SELECT c.childOrderId FROM OrderInfoChild c where  c.childOrderState = :childOrderState and c.orderId = :orderId ");
		query.setParameter("childOrderState", state);
		query.setParameter("orderId", orderId);
		List<Long> list = query.list();
		return list;
	}
	/**
	 * 通过运单号查询子运单信息
	 * @param trackingNum
	 * @param tenantId
	 * @return
	 */
	public List<OrderInfoChild> getChildTrackingNum (String trackingNum,long tenantId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrderInfoChild.class);
		ca.add(Restrictions.eq("trackingNum", trackingNum));
		ca.add(Restrictions.eq("tenantId", tenantId));
		List<OrderInfoChild> list = ca.list();
		return list;
	}
	
	/**
	 * 通过订单主键查询运单下的待配送的单
	 */
	public List<String> getchildIdByOrdsId(long ordsId){
		Session session = SysContexts.getEntityManager(true);
		String hql = "select c.childOrderId from OrderInfo o,OrderInfoChild c where o.orderId = c.orderId and o.ordsId = :ordsId ";
		Query query = session.createQuery(hql);
		List<Object[]> list = query.list();
		List<String> childOrderIds = new ArrayList<String>();
		if (list!= null&&list.size()>0) {
			for (Object[] objects : list) {
				if (objects[0] != null) {
					childOrderIds.add(String.valueOf(objects[0]));
				}
			}
		}
		return childOrderIds;
	}
}
