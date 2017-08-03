package com.wo56.business.ord.intf;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.wo56.business.cm.intf.CmUserInfoTF;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;

public class OrdIndexStatisticsTF {

	/**
	 * 首页统计
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map indexStatistics(Map<String, Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager();
		BaseUser base = SysContexts.getCurrentOperator();
		long userId = base.getUserId();
		// 根据userId查询用户类型
		CmUserInfoTF userTF = (CmUserInfoTF) SysContexts.getBean("cmUserInfoTF");
		CmUserInfo user = userTF.getUserInfo(userId, -1);
		Integer userType = user.getUserType();
		int merchanOrDistri = DataFormat.getIntKey(inParam, "merchanOrDistri");
		Map map = new HashMap();
		// 物流公司开单统计：1.今日开单 2.今日支出
		if (userType != null && userType > 0) {
			
			if(userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION){
				// 开单员
				if (1 == merchanOrDistri) {
					StringBuffer sb = new StringBuffer();
					sb.append(" SELECT COUNT(d.ORDER_ID),SUM(f.TIP)FROM order_fee AS f ,order_info d  ");
					sb.append(" WHERE d.ORDER_ID = f.ORDER_ID AND d.create_time > :beginTime ");
					sb.append(" AND d.create_time  < :endTime AND d.create_ID = :userId ");
					Query query = session.createSQLQuery(sb.toString());
					Date date = new Date();
					query.setParameter("beginTime", DateUtil.formatDate((Date) date, "yyyy-MM-dd") + " 00:00:00");
					query.setParameter("endTime", DateUtil.formatDate((Date) date, "yyyy-MM-dd") + " 23:59:59");
					query.setParameter("userId", userId);
					List<Object[]> list = query.list();
					if (list != null && list.size() > 0) {
						for (Object[] temp : list) {
							map.put("billingCount", temp[0] != null ? String.valueOf(temp[0]) : 0);
							String payCount = CommonUtil.parseDouble(temp[1]!=null?((BigDecimal) temp[1]).longValue():0);
							map.put("payCount", payCount);
						}
					}
				}
				// 配送统计： 1：今日配送，2：今日签收
				if (2 == merchanOrDistri) {
					StringBuffer sb = new StringBuffer();
					sb.append("SELECT COUNT(b.ORDER_ID)FROM ord_busi_person AS b");
					sb.append(" where b.DELIVERY_TIME>=:beginTime and b.DELIVERY_TIME<=:endTime ");
					sb.append(" and b.DELIVERY_ID=:userId ");
					Query query = session.createSQLQuery(sb.toString());
					Date date = new Date();
					query.setParameter("beginTime", DateUtil.formatDate(date, "yyyy-MM-dd") + " 00:00:00");
					query.setParameter("endTime", DateUtil.formatDate(date, "yyyy-MM-dd") + " 23:59:59");
					query.setParameter("userId", userId);
					Object obj = query.uniqueResult();
					map.put("goodsCount", obj != null ? String.valueOf(obj) : 0);
					// 今日签收
					StringBuffer signSql = new StringBuffer();
					signSql.append(
							"SELECT COUNT(o.ORDER_ID) FROM ord_orders_info AS o ,ord_sign_info AS s,ord_busi_person AS b ");
					signSql.append(" WHERE s.SIGN_DATE>=:beginTime AND s.SIGN_DATE<=:endTime ");
					signSql.append(" AND b.DELIVERY_ID=:userId AND o.ORDER_STATE=7 and o.ORDER_ID=s.ORDER_ID and o.ORDER_ID=b.ORDER_ID ");
					Query signQuery = session.createSQLQuery(signSql.toString());
					signQuery.setParameter("beginTime", DateUtil.formatDate(date, "yyyy-MM-dd") + " 00:00:00");
					signQuery.setParameter("endTime", DateUtil.formatDate(date, "yyyy-MM-dd") + " 23:59:59");
					signQuery.setParameter("userId", userId);
					Object signObj = signQuery.uniqueResult();
					map.put("signCount", signObj != null ? String.valueOf(signObj) : 0);

				}
			}
			
			// 开单员
			if (userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER) {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT COUNT(d.ORDER_ID),SUM(f.TIP)FROM order_fee AS f ,order_info d  ");
				sb.append(" WHERE d.ORDER_ID = f.ORDER_ID AND d.create_time > :beginTime ");
				sb.append(" AND d.create_time  < :endTime AND d.create_ID = :userId ");
				Query query = session.createSQLQuery(sb.toString());
				Date date = new Date();
				query.setParameter("beginTime", DateUtil.formatDate((Date) date, "yyyy-MM-dd") + " 00:00:00");
				query.setParameter("endTime", DateUtil.formatDate((Date) date, "yyyy-MM-dd") + " 23:59:59");
				query.setParameter("userId", userId);
				List<Object[]> list = query.list();
				if (list != null && list.size() > 0) {
					for (Object[] temp : list) {
						map.put("billingCount", temp[0] != null ? String.valueOf(temp[0]) : 0);
						String payCount = CommonUtil.parseDouble(temp[1]!=null?((BigDecimal) temp[1]).longValue():0);
						map.put("payCount", payCount);
					}
				}
			}
			// 配送统计： 1：今日配送，2：今日签收
			if (userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.DISTRIBUTION) {
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT COUNT(b.ORDER_ID)FROM ord_busi_person AS b");
				sb.append(" where b.DELIVERY_TIME>=:beginTime and b.DELIVERY_TIME<=:endTime ");
				sb.append(" and b.DELIVERY_ID=:userId ");
				Query query = session.createSQLQuery(sb.toString());
				Date date = new Date();
				query.setParameter("beginTime", DateUtil.formatDate(date, "yyyy-MM-dd") + " 00:00:00");
				query.setParameter("endTime", DateUtil.formatDate(date, "yyyy-MM-dd") + " 23:59:59");
				query.setParameter("userId", userId);
				Object obj = query.uniqueResult();
				map.put("goodsCount", obj != null ? String.valueOf(obj) : 0);
				// 今日签收
				StringBuffer signSql = new StringBuffer();
				signSql.append(
						"SELECT COUNT(o.ORDER_ID) FROM ord_orders_info AS o ,ord_sign_info AS s,ord_busi_person AS b ");
				signSql.append(" WHERE s.SIGN_DATE>=:beginTime AND s.SIGN_DATE<=:endTime ");
				signSql.append(" AND b.DELIVERY_ID=:userId AND o.ORDER_STATE=7 and o.ORDER_ID=s.ORDER_ID and o.ORDER_ID=b.ORDER_ID ");
				Query signQuery = session.createSQLQuery(signSql.toString());
				signQuery.setParameter("beginTime", DateUtil.formatDate(date, "yyyy-MM-dd") + " 00:00:00");
				signQuery.setParameter("endTime", DateUtil.formatDate(date, "yyyy-MM-dd") + " 23:59:59");
				signQuery.setParameter("userId", userId);
				Object signObj = signQuery.uniqueResult();
				map.put("signCount", signObj != null ? String.valueOf(signObj) : 0);

			}
			// 拉包工
			if (userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL) {
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT COUNT(b.ORDER_ID),SUM(o.TIP_MONEY)FROM ord_busi_person AS b,ord_goods_info AS g,ord_orders_info AS o");
				sb.append(" WHERE b.ORDER_ID = g.ORDER_ID AND b.ORDER_ID = o.ORDER_ID AND g.IS_PICKUP =1 AND o.ORDER_STATE  IN (4,5,6,7) ");
				sb.append(" AND b.WORKER_ID =:userId AND b.INPUT_TIME >=:beginTime AND b.INPUT_TIME <=:endTime  ");
				Query query = session.createSQLQuery(sb.toString());
				query.setParameter("userId", userId);
				Date date = new Date();
				query.setParameter("beginTime", DateUtil.formatDate(date, "yyyy-MM-dd") + " 00:00:00");
				query.setParameter("endTime", DateUtil.formatDate(date, "yyyy-MM-dd") + " 23:59:59");
				List<Object[]> list = query.list();
				if (list.size() > 0 && list != null) {
					for (Object[] temp : list) {
						map.put("taskCount", temp[0] != null ? String.valueOf(temp[0]) : 0);
						String tipCount = CommonUtil.parseDouble(temp[1]!=null?((BigDecimal) temp[1]).longValue():0);
						map.put("tipCount", tipCount);
					}
				}
				StringBuffer taskSql = new StringBuffer();
				taskSql.append("SELECT COUNT(o.ORDER_ID)FROM ord_orders_info AS o ,ord_busi_person AS b");
				taskSql.append(" WHERE b.ORDER_ID=o.ORDER_ID AND o.ORDER_STATE=2");
				taskSql.append(" AND b.WORKER_ID =:userId  ");
				Query taskQuery = session.createSQLQuery(taskSql.toString());
				taskQuery.setParameter("userId", userId);
				Object taskObj = taskQuery.uniqueResult();
				if (taskObj != null && Integer.valueOf(String.valueOf(taskObj)) > 0) {
					map.put("taskState", "1");
				} else {
					map.put("taskState", "0");
				}
				//拉包工上班状态
				Criteria ca=session.createCriteria(CmUserInfoPull.class);
				ca.add(Restrictions.eq("userId", userId));
				CmUserInfoPull userInfo=(CmUserInfoPull)ca.uniqueResult();
				String pullWork =String.valueOf(userInfo.getPullWork());
				map.put("pullWork", pullWork);
			}

		}
		// 商户
		if (userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS) {
			//今日发货
			StringBuffer sb=new StringBuffer();
			sb.append("SELECT o.ORDER_ID,o.ORDER_TYPE FROM ord_orders_info AS o ");
			sb.append(" WHERE  o.ORDER_STATE NOT IN (8,9) AND o.CREATE_DATE >=:beginTime AND o.CREATE_DATE <=:endTime ");
			sb.append(" and (o.order_id in (select distinct order_id from ord_goods_info where CONSIGNOR_BILL = :bill) or (o.consignee_bill = :bill or o.CREATE_ID = :userId )) and o.ORDER_TYPE IN (1,2) ");
			Query query=session.createSQLQuery(sb.toString());
			Date date = new Date();
			query.setParameter("userId", userId);
			query.setParameter("bill", user.getLoginAcct());
			query.setParameter("beginTime", DateUtil.formatDate(date, "yyyy-MM-dd") + " 00:00:00");
			query.setParameter("endTime", DateUtil.formatDate(date, "yyyy-MM-dd") + " 23:59:59");
			Integer goodsCount=0;
			Integer orderCount=0;
			List<Object[]> orderObj=query.list();
			if(orderObj!=null&&orderObj.size()>0){
				for(Object[] obj :orderObj){
					if(obj[1]!=null && (Integer)obj[1]==1){
						 goodsCount++;
					}		 
					if(obj[1]!=null && (Integer)obj[1]==2){
						 orderCount++; 
					}
				}
			
			}
			map.put("goodsCount", goodsCount);
			map.put("orderCount", orderCount);
		}
		return map;
	}

}
