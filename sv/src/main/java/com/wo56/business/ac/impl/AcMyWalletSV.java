package com.wo56.business.ac.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.wo56.business.ac.vo.AcMyWallet;
import com.wo56.business.ac.vo.AcMyWalletHis;
import com.wo56.business.ac.vo.out.AcMyWalletListOut;
import com.wo56.business.ac.vo.out.AcMyWalletOut;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;

public class AcMyWalletSV {
	/**
	 * 流水Id查询详情
	 * @param acountId
	 * @return
	 */
	public List<Object[]> getAccountById(long accountId){
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery(" select o.order_no,a.amount,o.province_id,o.CREATE_DATE from ac_my_wallet a INNER JOIN ord_orders_info o ON o.ORDER_ID = a.order_id where a.ACCOUNT_ID = :accountId order by a.id desc ");
		List<Object[]> list = query.setParameter("accountId", accountId).list();
		return list;
	}
	/**
	 * 待提现列表
	 * @param tenantId
	 * @return
	 */
	public Query queryAcMyWallet(long tenantId,long userId){
		Session session = SysContexts.getEntityManager(true);
		String sql = " select a.ID,a.AMOUNT,o.ORDER_NO,o.PROVINCE_ID,a.CREATE_TIME,o.ORDER_ID ,o.city_id from ac_my_wallet a INNER  JOIN  ord_orders_info o ON a.ORDER_ID = o.ORDER_ID where a.tenant_Id = :tenantId AND a.amount_State = :amountState AND a.STATE = :state AND a.USER_ID = :userId order by a.id desc ";
		Query query = session.createSQLQuery(sql);
		query.setParameter("tenantId", tenantId);
		query.setParameter("amountState", SysStaticDataEnumYunQi.AMOUNT_STATE.NOT_PRESENT);
		query.setParameter("state", SysStaticDataEnumYunQi.ACCOUNT_TYPE.INCOME);
		query.setParameter("userId", userId);
		return query;
	}
	
	public List<AcMyWallet> getAcMyWalletList(List<String> ids){
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery("select * from ac_my_wallet where id in (:ids) ").addEntity(AcMyWallet.class);
		List<AcMyWallet> list = query.setParameterList("ids", ids).list();
		return list;
	}
	
	public String doSave(AcMyWallet acMyWallet){
		Session session = SysContexts.getEntityManager();
		session.saveOrUpdate(acMyWallet);
		return "Y";
	}
	/**
	 * 查询未提现金额合计
	 * @param tenantId
	 * @return
	 */
	public String presentAmount(long tenantId){
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery("select SUM(AMOUNT) from ac_my_wallet where tenant_id = :tenantId and AMOUNT_STATE = :amountState and user_id = :userId");
		query.setParameter("tenantId", tenantId);
		query.setParameter("amountState", SysStaticDataEnumYunQi.AMOUNT_STATE.NOT_PRESENT);
		query.setParameter("userId", SysContexts.getCurrentOperator().getUserId());
		Object obj = query.uniqueResult();
		String amount = obj != null ? CommonUtil.parseDouble(Long.parseLong(String.valueOf(obj)))  : "0.00";
		return amount;
	}
	/**
	 * 查询账单
	 * @param time
	 * @return
	 */
	public Query billAcMyWallet(String time){
		String[] arr = time.split("-");
		int month = Integer.parseInt(arr[1])-1;
		String lastDay = CommonUtil.getLastDayOfMonth(Integer.parseInt(arr[0]), month);
		String oneDay = CommonUtil.getFirstDayOfMonth(Integer.parseInt(arr[0]),month);
		Session session = SysContexts.getEntityManager(true);
		String sb = " select t.name,o.ORDER_NO,a.AMOUNT,a.AMOUNT_STATE,a.CREATE_TIME from ac_my_wallet a INNER JOIN ord_orders_info o ON o.ORDER_ID = a.ORDER_ID  left join sys_tenant_def t on a.tenant_id = t.tenant_id where a.user_id = :userId AND a.CREATE_TIME >= :oneDay and a.CREATE_TIME <= :lastDay order by a.id desc ";
		Query query =session.createSQLQuery(sb);
		query.setParameter("userId", SysContexts.getCurrentOperator().getUserId());
		query.setParameter("lastDay", lastDay);
		query.setParameter("oneDay", oneDay);
		return query;
	}
	
	/**
	 * 小费对账列表查询
	 * @author qlfeng
	 * @param inParam
	 * @return
	 */
	public Query billingAcMyWallet(Map<String,Object> inParam,boolean isSum){
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		StringBuffer sb = new StringBuffer("SELECT ");
		if(isSum){
			sb.append(" SUM(a.AMOUNT), SUM(f.DEFAULT_TIP), ");
			sb.append(" SUM(if(t.ORDER_NUMBER is  null ,o.ORDER_NUM,t.NUMBER)) as NUMBER,  ");
			sb.append(" SUM(if(t.ORDER_NUMBER is  null ,o.WEIGHT,t.WEIGHT)) as WEIGHT,  ");
			sb.append(" SUM(if(t.ORDER_NUMBER is  null ,o.VOLUME,t.VOLUME)) as VOLUME, ");
			sb.append(" SUM(if(t.ORDER_NUMBER is  null ,o.FREIGHT,f.FREIGHT)) as FREIGHT ");
		}else{
			sb.append(" o.ORDER_ID,a.ID,o.ORDER_NO,t.ORDER_NUMBER,t.billing_org_id,a.TENANT_ID, ");
			sb.append(" IF(t.ORDER_NUMBER is null,o.CITY_ID,t.DES_CITY) as city, ");
			sb.append(" IF(t.ORDER_NUMBER is null,b.WORKER_NAME,t.PULL_NAME) as pullName, ");
			sb.append(" IF(t.ORDER_NUMBER is null,b.WORKER_PHONE,t.PULL_PHONE) as pullPhone, ");
			sb.append(" a.AMOUNT,f.DEFAULT_TIP, ");
			sb.append(" IF(t.ORDER_NUMBER is null,o.ORDER_NUM,t.NUMBER) as number, ");
			sb.append(" IF(t.ORDER_NUMBER is null,o.WEIGHT,t.WEIGHT) as weight, ");
			sb.append(" IF(t.ORDER_NUMBER is null,o.VOLUME,t.VOLUME) as volume, ");
			sb.append(" IF(t.ORDER_NUMBER is null,o.FREIGHT,f.FREIGHT) as freight, ");
			sb.append(" IF(t.ORDER_NUMBER is null,g.CONSIGNOR_NAME,t.CONSIGNOR) as consignor, ");
			sb.append(" IF(t.ORDER_NUMBER is null,g.CONSIGNOR_BILL,t.CONSIGNOR_PHONE) as consignorPhone, ");
			sb.append(" IF(t.ORDER_NUMBER is null,g.ADDRESS,t.CONSIGNOR_ADDRESS) as consignorAddress, ");
			sb.append(" IF(t.ORDER_NUMBER is null,o.CONSIGNEE_NAME,t.CONSIGNEE) as consignee, ");
			sb.append(" IF(t.ORDER_NUMBER is null,o.CONSIGNEE_BILL,t.CONSIGNEE_PHONE) as consigneePhone, ");
			sb.append(" IF(t.ORDER_NUMBER is null,o.ADDRESS,t.CONSIGNEE_ADDRESS) as consigneeAddress, ");
			sb.append(" IF(t.ORDER_NUMBER is null,o.SERVICE_TYPE,t.INTERCHANGE) as interchange, ");
			sb.append(" IF(t.ORDER_NUMBER is null,o.PAYMENT_TYPE,t.PAYMENT) as payment, ");
			sb.append(" IF(t.ORDER_NUMBER is null,o.PRODUCTS,t.PRODUCT_NAME) as products, ");
			sb.append(" t.PACK_NAME, ");
			sb.append(" IF(t.ORDER_NUMBER is null,c.user_name,t.CREATE_NAME) as createName, ");
			sb.append(" IF(t.ORDER_NUMBER is null,o.REMARK,t.REMARKS) as remark,t.ORDER_ID as orderInfo, ");
			sb.append(" IF (t.ORDER_NUMBER IS NULL,o.PROVINCE_ID,'') AS consigneePro, ");
			sb.append(" IF (t.ORDER_NUMBER IS NULL,o.CITY_ID,'') AS consigneeCity, ");
			sb.append(" IF (t.ORDER_NUMBER IS NULL,o.COUNTY_ID,'') AS consigneeCoun, ");
			sb.append(" IF (t.ORDER_NUMBER IS NULL,g.PROVINCE_ID,'') AS consignorPro, ");
			sb.append(" IF (t.ORDER_NUMBER IS NULL,g.CITY_ID,'') AS consignorCity, ");
			sb.append(" IF (t.ORDER_NUMBER IS NULL,g.COUNTY_ID,'') AS consignorCoun ");
		}
		sb.append(" FROM ac_my_wallet a ");
		sb.append(" INNER JOIN ord_orders_info o ON a.ORDER_ID = o.ORDER_ID ");
		sb.append(" LEFT JOIN order_info t ON o.ORDER_ID = t.ORDS_ID ");
		sb.append(" LEFT JOIN order_fee f ON t.ORDER_ID = f.ORDER_ID ");
		sb.append(" LEFT JOIN ord_busi_person b ON b.ORDER_ID = o.ORDER_ID ");
		sb.append(" LEFT JOIN cm_user_info c ON c.user_id = o.CREATE_ID ");
		sb.append(" LEFT JOIN ord_goods_info g on g.ORDER_ID = o.ORDER_ID  and g.ID_NO = (select MAX(gg.id_no) from ord_goods_info as gg where gg.order_id=o.order_id ) ");
		sb.append(" where (a.tenant_id = :tenantId or a.user_id in (select user_id from ac_wallet_rel where pull_tenant_id = :tenantId)) and a.amount_State = :amountState ");
		sb.append(" and a.tenant_id = :tenantId ");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tenantId", user.getTenantId());
		map.put("amountState", SysStaticDataEnumYunQi.AMOUNT_STATE.NOT_PRESENT);
		String ordersBegin = DataFormat.getStringKey(inParam, "ordersBegin");
		if(StringUtils.isNotEmpty(ordersBegin)){
			sb.append(" AND o.CREATE_DATE >= :ordersBegin ");
			map.put("ordersBegin", ordersBegin+" 00:00:00");
		}
		String ordersEnd = DataFormat.getStringKey(inParam, "ordersEnd");
		if(StringUtils.isNotEmpty(ordersEnd)){
			sb.append(" AND o.CREATE_DATE <= :ordersEnd ");
			map.put("ordersEnd", ordersEnd+" 23:59:59");
		}
		String billingBegin = DataFormat.getStringKey(inParam, "billingBegin");
		if(StringUtils.isNotEmpty(billingBegin)){
			sb.append(" AND t.CREATE_TIME >= :billingBegin ");
			map.put("billingBegin", billingBegin+" 00:00:00");
		}
		String pullPhone = DataFormat.getStringKey(inParam, "pullPhone");
		if(StringUtils.isNotEmpty(pullPhone)){
			sb.append(" AND (t.pull_PHONE like :pullPhone or b.WORKER_PHONE like :pullPhone)  ");
			map.put("pullPhone", "%"+pullPhone+"%");
		}
		
		String billingEnd = DataFormat.getStringKey(inParam, "billingEnd");
		if(StringUtils.isNotEmpty(billingEnd)){
			sb.append(" AND t.CREATE_TIME <= :billingEnd ");
			map.put("billingEnd", billingEnd+" 23:59:59");
		}
		String billingOrgName = DataFormat.getStringKey(inParam,"billingOrgName");
		if(StringUtils.isNotBlank(billingOrgName)){
			sb.append(" AND t.billing_Org_Id in (select org_id from organization where org_name like :billingOrgName ) ");
			map.put("billingOrgName", "%"+billingOrgName+"%");
		}
		String orderNo = DataFormat.getStringKey(inParam, "orderNo");
		if(StringUtils.isNotEmpty(orderNo)){
			sb.append(" AND o.order_No like :orderNo ");
			map.put("orderNo", "%"+orderNo+"%");
		}
		String orderNumber = DataFormat.getStringKey(inParam, "orderNumber");
		if(StringUtils.isNotEmpty(orderNumber)){
			sb.append(" AND t.order_Number like :orderNumber ");
			map.put("orderNumber", "%"+orderNumber+"%");
		}
		String consignor = DataFormat.getStringKey(inParam, "consignor");
		if(StringUtils.isNotEmpty(consignor)){
			sb.append(" AND (t.consignor like :consignor or g.CONSIGNOR_NAME like :consignor) ");
			map.put("consignor", "%"+consignor+"%");
		}
		String consignorPhone = DataFormat.getStringKey(inParam, "consignorPhone");
		if(StringUtils.isNotEmpty(consignorPhone)){
			sb.append(" AND (t.consignor_phone like :consignorPhone or g.CONSIGNOR_BILL like :consignorPhone) ");
			map.put("consignorPhone", "%"+consignorPhone+"%");
		}
		
		String pullName = DataFormat.getStringKey(inParam, "pullName");
		if(StringUtils.isNotEmpty(pullName)){
			sb.append(" AND (t.pull_name like :pullName or b.WORKER_NAME like :pullName) ");
			map.put("pullName", "%"+pullName+"%");
		}
		
		String createName = DataFormat.getStringKey(inParam, "createName");
		if(StringUtils.isNotEmpty(createName)){
			sb.append(" AND (t.create_Name like :createName or c.user_name like :createName) ");
			map.put("createName", "%"+createName+"%");
		}
		
		String consignee = DataFormat.getStringKey(inParam, "consignee");
		if(StringUtils.isNotEmpty(consignee)){
			sb.append(" AND (t.consignee like :consignee or o.consignee_name like :consignee) ");
			map.put("consignee", "%"+consignee+"%");
		}
		
		String consigneePhone = DataFormat.getStringKey(inParam, "consigneePhone");
		if(StringUtils.isNotEmpty(consigneePhone)){
			sb.append(" AND (t.consignee_Phone like :consigneePhone or o.consignee_BILL like :consigneePhone) ");
			map.put("consigneePhone", "%"+consigneePhone+"%");
		}
		sb.append(" order by a.id desc ");
		Query query = session.createSQLQuery(sb.toString());
		query.setProperties(map);
		return query;
	}
	/**
	 * 保存历史
	 * @param his
	 * @return
	 */
	public String doSaveHis(AcMyWalletHis his){
		Session session = SysContexts.getEntityManager(false);
		session.save(his);
		return "Y";
	}
	/**
	 * id查询
	 * @param id
	 * @return
	 */
	public AcMyWallet getAcMyWallet(long id){
		Session session = SysContexts.getEntityManager(true);
		return (AcMyWallet) session.get(AcMyWallet.class, id);
	}
	
	
	public List<AcMyWallet> getAcMyWalletList(String[] ids){
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery("select * from ac_my_wallet where id in (:ids) ").addEntity(AcMyWallet.class);
		List<AcMyWallet> list = query.setParameterList("ids", ids).list();
		return list;
	}
	
	public String doSaveOrUpdate(AcMyWallet acMyWallet){
		Session session = SysContexts.getEntityManager(false);
		session.saveOrUpdate(acMyWallet);
		return "Y";
	}
	
	public List<AcMyWallet> getAcMyWalletList(long accountId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(AcMyWallet.class);
		List<AcMyWallet> list = ca.add(Restrictions.eq("accountId", accountId)).list();
		return list;
	}
}
