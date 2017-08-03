package com.wo56.business.ord.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.wo56.business.ord.vo.OrdDepartDetail;
import com.wo56.business.ord.vo.OrdDepartInfo;
import com.wo56.common.consts.SysStaticDataEnumYunQi;


public class OrdDepartInfoYqSV {
	/**
	 * 保存与修改
	 * @param departInfo
	 */
	public void doSaveOrUpdate(OrdDepartInfo departInfo){
		Session session = SysContexts.getEntityManager();
		session.saveOrUpdate(departInfo);
	}
	/**
	 * 通过主键查询配载批次
	 * @param id
	 * @return
	 */
	public OrdDepartInfo getOrdDepartInfo(long id){
		Session session = SysContexts.getEntityManager(true);
		return (OrdDepartInfo) session.get(OrdDepartInfo.class, id);
	}
	/**
	 * 批次号获取详情信息
	 * @param batchNum
	 * @return
	 */
	public List<OrdDepartDetail> getOrdDepartDetailList(long batchNum){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrdDepartDetail.class);
		ca.add(Restrictions.eq("id.batchNum", batchNum));
		return ca.list();
	}
	
	
	/**
	 * 删除批次详情信息
	 * @param ordDepartDetail
	 */
	public void delOrdDepartDetail(OrdDepartDetail ordDepartDetail){
		Session session = SysContexts.getEntityManager();
		session.delete(ordDepartDetail);
	}
	
	/**
	 * 删除批次信息
	 * @param ordDepartDetail
	 */
	public void delOrdDepartDetail(OrdDepartInfo ordDepartDetail){
		Session session = SysContexts.getEntityManager();
		session.delete(ordDepartDetail);
	}
	
	/**
	 * 保存批次详情信息
	 * @param ordDepartDetail
	 */
	public void doSaveOrUpdateDetail(OrdDepartDetail ordDepartDetail){
		Session session = SysContexts.getEntityManager();
		session.saveOrUpdate(ordDepartDetail);
	}
	/**
	 * 查询发车配载信息
	 * @type 1为发车 2：到车
	 * @param inParam
	 * @return
	 */
	public Query doQueryOrdDepart(Map<String,Object> inParam,int type){
		BaseUser user = SysContexts.getCurrentOperator();
		StringBuffer sb = new StringBuffer(" select ");
		sb.append(" d.BATCH_NUM,d.BATCH_NUM_ALIAS,d.LOAD_TIME,d.SOURCE_ORG_ID,d.DESC_ORG_ID,d.ORDER_NUM,d.VOLUME,d.WEIGHT,d.STATE,d.ARRIVAL_VEH_TIME,d.number  from ord_depart_info d where 1=1 ");
		Map<String,Object> map = new HashMap<String, Object>();
		if (type == 1) {
			sb.append(" AND d.SOURCE_ORG_ID = :orgId ");
			map.put("orgId", user.getOrgId());
		}else if(type == 2){
			sb.append(" AND d.DESC_ORG_ID = :orgId ");
			map.put("orgId", user.getOrgId());
			sb.append(" AND d.state != :state ");
			map.put("state", SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.WAIT_DEPART);
		}
		String batchNumAlias = DataFormat.getStringKey(inParam, "batchNum");
		if (StringUtils.isNotEmpty(batchNumAlias)) {
			sb.append(" AND d.BATCH_NUM_ALIAS like :batchNumAlias ");
			map.put("batchNumAlias", "%"+batchNumAlias+"%");
		}
		long descOrgId = DataFormat.getLongKey(inParam, "descOrgId");
		if (descOrgId > 0) {
			sb.append(" AND d.DESC_ORG_ID = :descOrgId ");
			map.put("descOrgId", descOrgId);
		}
		String loadTimeBegin = DataFormat.getStringKey(inParam, "timeBegin");
		if (StringUtils.isNotEmpty(loadTimeBegin)) {
			sb.append(" AND d.LOAD_TIME >= :loadTimeBegin ");
			map.put("loadTimeBegin", loadTimeBegin + " 00:00:00");
		}
		String loadTimeEnd = DataFormat.getStringKey(inParam, "timeEnd");
		if (StringUtils.isNotEmpty(loadTimeEnd)) {
			sb.append(" and d.LOAD_TIME <= :loadTimeEnd ");
			map.put("loadTimeEnd", loadTimeEnd+" 23:59:59");
		}
		sb.append(" order by d.BATCH_NUM desc ");
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery(sb.toString());
		query.setProperties(map);
		return query;
	}
	/**
	 * 校验该批次号和子运单
	 * @param batchNum
	 * @param childTrackingNum
	 * @return
	 */
	public Query checkDepartInfoByTrackingNum(long batchNum,String childTrackingNum,long childOrderId){
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sql = new StringBuffer(" select ");
		sql.append(" d.state ");
		sql.append(" from ord_depart_info d inner join ord_depart_detail t on d.batch_num = t.batch_num ");
		sql.append(" inner join order_info_child c ON c.child_order_id = t.order_id where d.BATCH_NUM = :batchNum ");
		if (childOrderId > 0) {
			sql.append(" and c.child_order_id = :childOrderId ");
		}
		if (StringUtils.isNotEmpty(childTrackingNum)) {
			sql.append(" and c.child_traking_num = :childTrackingNum ");
		}
		Query query = session.createSQLQuery(sql.toString());
		query.setParameter("batchNum", batchNum);
		if (childOrderId > 0) {
			query.setParameter("childOrderId", childOrderId);
		}
		if (StringUtils.isNotEmpty(childTrackingNum)) {
			query.setParameter("childTrackingNum", childTrackingNum);
		}
		return query;
	}
	/**
	 * 到货更新配载信息
	 * @param user
	 * @param date
	 * @param batchNum
	 * @param childOrderIds
	 */
	public void  updateDepartDetali(BaseUser user,Date date,long batchNum,long childOrderId){
		Session session = SysContexts.getEntityManager();
		String hql = "update OrdDepartDetail o set o.arrivalTime = :arrivalTime,o.arrivalOpId = :arrivalOpId,o.arrivalOpName = :arrivalOpName where o.id.batchNum = :batchNum and o.id.orderId = :childOrderId ";
		Query query = session.createQuery(hql.toString());
		if (user == null) {
			query.setParameter("arrivalOpId", null);
			query.setParameter("arrivalOpName", null);
		}else{
			query.setParameter("arrivalOpId", user.getUserId());
			query.setParameter("arrivalOpName", user.getUserName());
		}
		query.setParameter("arrivalTime", date);
		query.setParameter("batchNum", batchNum);
		query.setParameter("childOrderId", childOrderId);
		query.executeUpdate();
	}

	
	/**
	 * 发车更新配载信息
	 * @param user
	 * @param date
	 * @param batchNum
	 * @param childOrderIds
	 */
	public void  updateDepartDetaliByDepart(BaseUser user,Date date, int state,long batchNum){
		Session session = SysContexts.getEntityManager();
		String hql = null;
		if(state == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ON_THE_WAY || state == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.WAIT_DEPART ){
			 hql = "update ord_depart_info o set o.state = :state,o.depart_op_id = :departOpId,o.depart_op_name = :departOpName, o.DEPART_TIME = :date  where o.batch_num = :batchNum ";
		} 
		else if (state == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVE   ) {
			hql = "update ord_depart_info o set o.state = :state,o.ARRIVAL_VEH_OP_ID = :departOpId,o.ARRIVAL_VEH_OP_NAME = :departOpName,  o.ARRIVAL_VEH_TIME = :date where o.batch_num = :batchNum ";
		}
//		String hql = "update ord_depart_info o set o.state = :state,o.depart_op_id = :departOpId,o.depart_op_name = :departOpName where o.batch_num = :batchNum ";
		Query query = session.createSQLQuery(hql.toString());
		if (user == null) {
			query.setParameter("departOpId", null);
			query.setParameter("departOpName", null);
		}else{
			query.setParameter("departOpId", user.getUserId());
			query.setParameter("departOpName", user.getUserName());
			query.setParameter("date", date);
		}
		query.setParameter("state", state);
		query.setParameter("batchNum", batchNum);
		
		query.executeUpdate();
	}
	
	//清空发车/到车配载信息
	public void  delDepartDetaliByDepart(BaseUser user,Date date, int state,long batchNum){
		Session session = SysContexts.getEntityManager();
		String hql = null;
		//撤销发车
		if( state == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.WAIT_DEPART ){
			 hql = "update  ord_depart_info o  set  o.depart_op_id=null,  o.depart_op_name=null,depart_time =null,o.state = :state"
			 		+ " where o.batch_num = :batchNum ";
		} 
		else if (state == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ON_THE_WAY ) {
			//撤销到车
			hql = "update ord_depart_info o set o.ARRIVAL_VEH_OP_ID=null, o.ARRIVAL_VEH_OP_NAME=null,o.ARRIVAL_VEH_TIME = null,o.state = :state "
					+ "  where  o.batch_num = :batchNum ";
		}
//		String hql = "update ord_depart_info o set o.state = :state,o.depart_op_id = :departOpId,o.depart_op_name = :departOpName where o.batch_num = :batchNum ";
		Query query = session.createSQLQuery(hql.toString());
		query.setParameter("state", state);
		query.setParameter("batchNum", batchNum);
		
		query.executeUpdate();
	}
	
	
	
	/**
	 * 查询配载列表
	 * @param inParam
	 * @return
	 */
	public Query doQuery(Map<String,Object> inParam,boolean isSum){
		BaseUser user = SysContexts.getCurrentOperator();
		String loadTimeBegin = DataFormat.getStringKey(inParam, "loadTimeBegin");
		String loadTimeEnd = DataFormat.getStringKey(inParam, "loadTimeEnd");
		long sourceOrgId = DataFormat.getLongKey(inParam, "sourceOrgId");
		long descOrgId = DataFormat.getLongKey(inParam, "descOrgId");
		String batchNumAlias = DataFormat.getStringKey(inParam, "batchNumAlias");
		String driverName = DataFormat.getStringKey(inParam, "driverName");
		String driverBill = DataFormat.getStringKey(inParam, "driverBill");
		String loadOpName = DataFormat.getStringKey(inParam, "loadOpName");
		int state = DataFormat.getIntKey(inParam, "state");
		String plateNumber = "";
		String inputParamJson=StringEscapeUtils.unescapeHtml(DataFormat.getStringKey(inParam, "inputParamJson"));
		if (StringUtils.isNotEmpty(inputParamJson)) {
			Map<String,Object> objectMap=JsonHelper.parseJSON2Map(inputParamJson);
			plateNumber = DataFormat.getStringKey(objectMap, "plateNumber");
			batchNumAlias = DataFormat.getStringKey(objectMap, "batchNumAlias");
			state = DataFormat.getIntKey(objectMap, "state");
		}
		StringBuffer sb = new StringBuffer(" SELECT ");
		if (isSum) {
			sb.append(" SUM(d.ORDER_NUM),SUM(d.NUMBER),SUM(d.VOLUME),SUM(d.WEIGHT),SUM(d.FREIGHT),SUM(d.total_fee),SUM(d.COLLECT_MONEY) ");
		}else{
			sb.append(" d.BATCH_NUM,d.batch_Num_Alias,d.STATE,d.LOAD_OP_NAME,d.LOAD_TIME,d.DEPART_TIME,d.PLATE_NUMBER, ");
			sb.append(" d.DRIVER_NAME,d.DRIVER_BILL,d.TENANT_ID,d.SOURCE_ORG_ID,d.DESC_ORG_ID,d.ORDER_NUM, ");
			sb.append(" d.NUMBER,d.VOLUME,d.WEIGHT,d.FREIGHT,d.total_fee,d.COLLECT_MONEY,d.REMARKS ");
		}
		
		sb.append("  FROM ord_depart_info d where 1=1 ");
		sb.append(" and (d.SOURCE_ORG_ID = :orgId or d.DESC_ORG_ID = :orgId or d.tenant_id =:tenantId ) ");
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orgId", user.getOrgId());
		map.put("tenantId", user.getTenantId());
		if (StringUtils.isNotEmpty(plateNumber)) {
			sb.append(" AND d.PLATE_NUMBER like :plateNumber ");
			map.put("plateNumber", "%"+plateNumber+"%");
		}
		if (StringUtils.isNotEmpty(loadTimeBegin)) {
			sb.append(" and d.LOAD_TIME >= :loadTimeBegin ");
			map.put("loadTimeBegin", loadTimeBegin);
		}
		if (StringUtils.isNotEmpty(loadTimeEnd)) {
			sb.append(" and d.LOAD_TIME <= :loadTimeEnd ");
			map.put("loadTimeEnd", loadTimeEnd);
		}
		if (sourceOrgId > 0) {
			sb.append(" and d.SOURCE_ORG_ID = :sourceOrgId ");
			map.put("sourceOrgId", sourceOrgId);
		}
		if (descOrgId > 0) {
			sb.append(" and d.DESC_ORG_ID = :descOrgId ");
			map.put("descOrgId", descOrgId);
		}
		if (StringUtils.isNotEmpty(batchNumAlias)) {
			sb.append(" and d.batch_Num_Alias like :batchNumAlias ");
			map.put("batchNumAlias", "%"+batchNumAlias+"%");
		}
		if (StringUtils.isNotEmpty(driverName)) {
			sb.append(" and d.DRIVER_NAME like :driverName ");
			map.put("driverName", "%"+driverName+"%");
		}
		if (StringUtils.isNotEmpty(driverBill)) {
			sb.append(" and d.driver_Bill like :driverBill ");
			map.put("driverBill", "%"+driverBill+"%");
		}
		if (StringUtils.isNotEmpty(loadOpName)) {
			sb.append(" and d.load_Op_Name like :loadOpName ");
			map.put("loadOpName", "%"+loadOpName+"%");
		}
		if (state > 0) {
			sb.append(" and d.state = :state ");
			map.put("state", state);
		}
		sb.append(" order by d.batch_Num desc ");
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery(sb.toString());
		query.setProperties(map);
		return query;
	}
	/**
	 * 配载详情信息
	 */
	public Query getOrdDepartInfoWeb(long batchNum){
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer(" SELECT ");
		sb.append(" d.BATCH_NUM,d.BATCH_NUM_ALIAS,d.STATE,d.LOAD_OP_NAME,d.LOAD_TIME,d.DEPART_TIME, ");
		sb.append(" d.SOURCE_ORG_ID,d.DESC_ORG_ID,d.ARRIVAL_VEH_TIME,d.DRIVER_NAME,d.DRIVER_BILL,d.PLATE_NUMBER ");
		sb.append(" ,v.VEHICLE_TYPE,d.VOLUME,d.WEIGHT,d.TOTAL_FEE,d.COLLECT_MONEY ");
		sb.append(" from ord_depart_info d LEFT JOIN cm_vehicle_info v ON d.VEHICLE_ID = v.VEHICLE_ID where d.BATCH_NUM = :batchNum ");
		Query query = session.createSQLQuery(sb.toString());
		query.setParameter("batchNum", batchNum);
		return query;
	} 
	/**
	 * 查询批次的子运单信息
	 * @param batchNum
	 * @return
	 */
	public Query getOrdDepartChild(long batchNum){
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer(" SELECT ");
		sb.append(" c.CHILD_ORDER_ID,c.TRACKING_NUM,c.CHILD_TRACKING_NUM_ALI,c.CHILD_ORDER_STATE, ");
		sb.append(" o.CREATE_TIME,o.BILLING_ORG_ID,c.CURRENT_ORG_ID,c.DISPATCHING_ORG_ID,o.DES_CITY_NAME, ");
		sb.append(" o.CONSIGNOR,o.CONSIGNOR_PHONE,o.CONSIGNOR_ADDRESS,o.CONSIGNEE,o.CONSIGNEE_PHONE,o.CONSIGNEE_ADDRESS, ");
		sb.append(" o.INTERCHANGE,o.PAYMENT,o.PRODUCT_NAME,o.PACK_NAME,o.REMARKS ");
		sb.append("  from ord_depart_detail d INNER JOIN order_info_child c ON d.ORDER_ID = c.CHILD_ORDER_ID ");
		sb.append(" INNER JOIN order_info o ON o.ORDER_ID = c.ORDER_ID  ");
		sb.append("  where d.BATCH_NUM = :batchNum ");
		sb.append(" order by o.order_id desc ");
		Query query = session.createSQLQuery(sb.toString());
		query.setParameter("batchNum", batchNum);
		return query;
	}
	public void delDepartDetailByChildOrderId(BaseUser user, Date date,
			long batchNum, Set<Long> childOrderIds) {
		Session session = SysContexts.getEntityManager();
		String hql = null;
		 hql = "update  ord_depart_detail o  set  o.arrival_time=null,  o.arrival_op_name=null,o.arrival_op_id =null  "
			 		+ " where   o.batch_num = :batchNum and o.order_id in :childOrderIds";
		 
		 Query query = session.createSQLQuery(hql.toString());
		 query.setParameter("batchNum", batchNum);
		 query.setParameterList("childOrderIds", childOrderIds);
			query.executeUpdate();
		
	}
	/**
	 * 子运单号获取配载信息
	 * @param  detail
	 * @param departInfos
	 * @param childOrderId
	 * @return
	 */
	public List<OrdDepartDetail> getDepartByChild(long childOrderId){
		Session session = SysContexts.getEntityManager(true);
		List<OrdDepartDetail> departs = session.createCriteria(OrdDepartDetail.class).add(Restrictions.eq("orderId", childOrderId)).list();
		return departs;
	}
	
	/**
	 * 判断改子运单是否进行了配载
	 * true 存在配载信息
	 * false 不存在配载信息
	 */
	public boolean checkIsDepart(long orderId){
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery("select count(1) from order_info_child c,ord_depart_detail d where c.child_order_id = d.order_id and c.order_id = :orderId");
		query.setParameter("orderId", orderId);
		Object obj = query.uniqueResult();
		if (obj != null&&Integer.parseInt(obj.toString()) > 0) {
			return true;
		}else{
			return false;
		}
	}
}
