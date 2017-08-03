package com.wo56.business.ord.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.wo56.business.ord.intf.OrdOrdersWebTF;
import com.wo56.common.consts.SysStaticDataEnumYunQi;

public class OrdOrdersInfoSV {
	
	private static final Log log = LogFactory.getLog(OrdOrdersInfoSV.class);

	public Query queryTracksInfo(Map<String,Object> inParam, boolean isSum){
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		String consigneeBill=DataFormat.getStringKey(inParam, "consigneeBill");
		String consigneeName=DataFormat.getStringKey(inParam, "consigneeName");
		String consignorBill=DataFormat.getStringKey(inParam, "consignorBill");
		String consignorName=DataFormat.getStringKey(inParam, "consignorName");
		String createName =DataFormat.getStringKey(inParam, "createName");//下单人
		long tenantId1=0;
		long tenantId=DataFormat.getLongKey(inParam, "tenantId");//开单物流
		String orderNo=DataFormat.getStringKey(inParam, "orderNo");//
		String trackingNum=DataFormat.getStringKey(inParam, "trackingNum");//
		String workerName=DataFormat.getStringKey(inParam, "workerName");
		String workerBill=DataFormat.getStringKey(inParam, "workerBill");
		String beginInputTime=DataFormat.getStringKey(inParam, "beginInputTime");
		String endInputTime=DataFormat.getStringKey(inParam, "endInputTime");
		String beginCreateTime=DataFormat.getStringKey(inParam, "beginCreateTime");
		String endCreateTime=DataFormat.getStringKey(inParam, "endCreateTime");
		String inputUserName=DataFormat.getStringKey(inParam, "inputUserName");
		int orderState = DataFormat.getIntKey(inParam, "orderState");
		//取消时间
		String endCancelTime=DataFormat.getStringKey(inParam, "endCancelTime");
		String beginCancelTime=DataFormat.getStringKey(inParam, "beginCancelTime");
	
		if(user.getUserType()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.CHAIN){
			tenantId1=user.getTenantId();
		}
		String companyName = "";
		String orgName = "";
		String inputParamJson=StringEscapeUtils.unescapeHtml(DataFormat.getStringKey(inParam, "inputParamJson"));
		if (StringUtils.isNotEmpty(inputParamJson)) {
			
			Map<String,Object> inputParamMap=JsonHelper.parseJSON2Map(inputParamJson);
			
			if(StringUtils.isNotEmpty(consigneeBill)){
				 
			 }else{
				 consigneeBill=DataFormat.getStringKey(inputParamMap, "consigneeBill"); 
			 }
			 if(StringUtils.isNotEmpty(consigneeName)){
				 
			 }else{
				 consigneeName=DataFormat.getStringKey(inputParamMap, "consigneeName"); 
			 }
			 if(StringUtils.isNotEmpty(consignorBill)){
				 
			 }else{
				 consignorBill=DataFormat.getStringKey(inputParamMap, "consignorBill"); 
			 }
			 
			 if(StringUtils.isNotEmpty(consignorName)){
				 
			 }else {
				 consignorName=DataFormat.getStringKey(inputParamMap, "consignorName"); 
			 }
			 if(tenantId>0){
				 
			 }else {
				 tenantId=DataFormat.getLongKey(inputParamMap, "tenantId");
			 }
			 if(StringUtils.isNotEmpty(orderNo)){
				 
			 }else {
				 orderNo=DataFormat.getStringKey(inputParamMap, "orderNo"); 
			 }
			 
			 if(StringUtils.isNotEmpty(trackingNum)){
				 
			 }else {
				 trackingNum=DataFormat.getStringKey(inputParamMap, "trackingNum"); 
			 }
			
			if(StringUtils.isNotEmpty(companyName)){
				
			}else {
				companyName=DataFormat.getStringKey(inputParamMap, "companyName"); 
			}
			
			if(StringUtils.isNotEmpty(inputUserName)){
				
			}else {
				 inputUserName=DataFormat.getStringKey(inputParamMap, "inputUserName");//开单人
			}
			
			if(StringUtils.isNotEmpty(createName)){
				
			}else {
				createName=DataFormat.getStringKey(inputParamMap, "createName");//下单人
			}
			 
			if(StringUtils.isNotEmpty(orgName)){
				
			}else {
				 orgName = DataFormat.getStringKey(inputParamMap, "orgName");//开单网点 
			}
			
           if(StringUtils.isNotEmpty(workerName)){
				
			}else {
				 workerName=DataFormat.getStringKey(inputParamMap, "workerName");
			}
          
            if(StringUtils.isNotEmpty(workerBill)){
				
			}else {
				workerBill=DataFormat.getStringKey(inputParamMap, "workerBill");
			}	
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		if (isSum) {
			sql.append("  SUM(o.NUMBER),SUM(o.WEIGHT),SUM(o.VOLUME),SUM(F.FREIGHT),SUM(F.REPUTATION),SUM(F.PREMIUM),SUM(F.DELIVERY_CHARGE),  ");//7
			sql.append(" SUM(F.TRANSIT_FEE),SUM(F.OTHER_FEE),SUM(F.COLLECT_MONEY),SUM(F.LAND_FEE),SUM(F.SERVICE_CHARGE),SUM(F.TOTAL_FEE),SUM(F.TIP),SUM(F.PACKING_COSTS) ");
		}else{
			sql.append(" o.order_id,o.order_number,  o.order_state_out, od.order_no, o.BILLING_ORG_ID, o.tenant_id, o.DES_PROVINCE_NAME, o.DES_CITY_NAME,o.DES_DISTRICT_NAME, ");
			sql.append( " o.PULL_NAME, o.pull_phone, o.CONSIGNOR, o.CONSIGNOR_PHONE, o.CONSIGNOR_ADDRESS,o.CONSIGNEE, o.CONSIGNEE_PHONE, o.CONSIGNEE_ADDRESS,");//17
			sql.append( "od.service_type, o.PAYMENT, o.PRODUCT_NAME, o.NUMBER, o.PACK_NAME,o.WEIGHT, o.VOLUME,");//23
			sql.append( "f.FREIGHT, f.REPUTATION,f.PREMIUM, f.DELIVERY_CHARGE, f.TRANSIT_FEE,f.tip, f.COLLECT_MONEY, f.LAND_fee,");//31
			sql.append( "f.SERVICE_CHARGE, f.OTHER_FEE, f.total_fee , o.remarks, o.create_name, od.COMPANY_NAME, os.`SIGN_NAME`, os.`SIGN_DATE`, o.create_time ,");//40
			sql.append( " o.op_time, o.op_name , f.PACKING_COSTS");//32
		    
		}
		sql.append( "  FROM order_info o left join  ord_orders_info od on od.order_id = o.ords_id  ");
	    sql.append( " left join order_fee f on o.order_id = f.order_id  ");
	    sql.append( " left join `ord_sign_info` os on od.order_id = os.order_id where 1=1 and o.order_state_out != 0  ");
		if(orderState==-1){
			 sql.append(" and  o.order_state_out not in (8,9) ");
		}
		
		//开单时间   运单状态 订单号  运单号  发货人  发货人电话  拉包公  开单人  收货人 收货人电话
		Map<String,Object> sqlParam = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(beginInputTime)){
			sql.append(" and o.create_time >= :beginInputTime ");
			sqlParam.put("beginInputTime",beginInputTime+ " 00:00:00");
		}
		if(StringUtils.isNotEmpty(endInputTime)){
			sql.append(" and o.create_time <= :endInputTime ");
			sqlParam.put("endInputTime", endInputTime+" 23:59:59");
		}
		// 开单物流
		if(tenantId>0){
			sql.append(" and o.tenant_id=:tenantId ");
			sqlParam.put("tenantId", tenantId);
		}
		if(tenantId1>0){
			sql.append(" and o.tenant_id=:tenantId ");
			sqlParam.put("tenantId", tenantId1);
		}
		//订单号 
		if(StringUtils.isNotEmpty(orderNo)){
			sql.append(" and od.order_no like :orderNo  ");
			sqlParam.put("orderNo", "%"+orderNo+"%");
		}
		//运单号 
		if(StringUtils.isNotEmpty(trackingNum)){
			sql.append(" and o.order_number like :trackingNum  ");
			sqlParam.put("trackingNum", "%"+trackingNum+"%");
		}
		//收货人号码
		if(StringUtils.isNotEmpty(consigneeBill)){
			sql.append(" and o.CONSIGNEE_PHONE like :consigneeBill  ");
			sqlParam.put("consigneeBill", "%"+consigneeBill+"%");
		}
		//收货人名称
		if(StringUtils.isNotEmpty(consigneeName)){
			sql.append(" and o.CONSIGNEE like :consigneeName  ");
			sqlParam.put("consigneeName","%"+ consigneeName+"%");
		}
		//发货人
		if(StringUtils.isNotEmpty(consignorName)){
			sql.append(" and o.CONSIGNOR like :consignorName  ");
			sqlParam.put("consignorName","%"+consignorName+"%");
		}
		if(StringUtils.isNotEmpty(consignorBill)){
			sql.append(" and o.CONSIGNOR_PHONE like :consignorBill  ");
			sqlParam.put("consignorBill", "%"+consignorBill+"%");
		}
		//拉包工
		if(StringUtils.isNotEmpty(workerName)){
			sql.append(" and o.PULL_NAME like :workerName  ");
			sqlParam.put("workerName", "%"+workerName+"%");
		}
		if(StringUtils.isNotEmpty(workerBill)){
			sql.append(" and o.pull_phone like :workerBill  ");
			sqlParam.put("workerBill", "%"+workerBill+"%");
		}
		//开单人
		if(StringUtils.isNotEmpty(inputUserName)){
			sql.append(" and o.create_name like :inputUserName  ");
			sqlParam.put("inputUserName", "%"+inputUserName+"%");
		}
		//运单状态
		if(orderState>0){
			if(orderState==610){
				sql.append(" and o.order_state_out in (6,10) ");
				//sqlParam.put("orderState", orderState);
			}else  if(orderState==89){
				sql.append(" and o.order_state in (8,9) ");
			}else if(orderState==13){
				sql.append(" and o.order_state_out = 3 ");
			}else if(orderState==45){
				sql.append(" and o.order_state_out = 4 ");
			}else{
				sql.append("  and  o.order_state_out = :orderState" );
				sqlParam.put("orderState", orderState);
			}
		}
		
		if(StringUtils.isNotEmpty(orgName)){
				sql.append(" and o.BILLING_ORG_ID in (select org_id from organization where  org_name  like :orgName ) ");
				sqlParam.put("orgName","%"+orgName+"%");
		}		
		

		//取消时间
		if(StringUtils.isNotEmpty(beginCancelTime)){
				sql.append(" and o.OP_TIME >= :beginCancelTime ");
				sqlParam.put("beginCancelTime",beginCancelTime+ " 00:00:00");
		}
		if(StringUtils.isNotEmpty(endCancelTime)){
				sql.append(" and o.OP_TIME <= :endCancelTime ");
				sqlParam.put("endCancelTime", endCancelTime+" 23:59:59");
		}
		
		sql.append("  order by o.order_id desc ");
		Query query = session.createSQLQuery(sql.toString());
		query.setProperties(sqlParam);
		return query;
	}
	
	public Query queryTracksInfoSum(Map<String,Object> inParam){
		BaseUser user = SysContexts.getCurrentOperator();
		Integer[] orderStates = new Integer[]{
				SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.DO_SIGN,
				SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.IN_TRANSIT,
				SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PENDING_DELIVERY,
				SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PENDING_DEPARTURE,
				SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.WAIT_SIGN,
				SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PARTIAL_SIGN,
				SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.DO_CANCER,
				SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.CANCERING		
		};
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(1),o.order_state_out ");
		sql.append("  FROM order_info o LEFT JOIN  ord_orders_info od ON od.order_id = o.ords_id  ");
		sql.append (" LEFT JOIN order_fee f ON o.order_id = f.order_id ");
		sql.append (" LEFT JOIN `ord_sign_info` os ON od.order_id = os.order_id where o.order_state_out != 0");
		if(user.getUserType() ==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.CHAIN ){
			sql.append( "  and o.TENANT_ID =:tenantId  ");
		} 
		sql.append("  group by o.order_state_out ");
		Session session = SysContexts.getEntityManager(true);
		if(user.getUserType() ==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.CHAIN ){
			return session.createSQLQuery(sql.toString()).setParameter("tenantId", user.getTenantId());
		}else {
			return session.createSQLQuery(sql.toString());
		}
	}
	
	//web 子运单列表查询
	public Query querychildOrderInfo(Map<String,Object> inParam){
		
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		String trackingNum=DataFormat.getStringKey(inParam, "trackingNum");
		String inputParamJson=StringEscapeUtils.unescapeHtml(DataFormat.getStringKey(inParam, "inputParamJson"));
		if (StringUtils.isNotEmpty(inputParamJson)) {
			Map<String,Object> inputParamMap=JsonHelper.parseJSON2Map(inputParamJson);
			 trackingNum=DataFormat.getStringKey(inputParamMap, "trackingNum");
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT child_tracking_num_ali, child_order_state, current_org_id FROM order_info_child WHERE 1=1");
		Map<String,Object> sqlParam = new HashMap<String,Object>();
		//运单号 
		if(StringUtils.isNotEmpty(trackingNum)){
			 sql.append(" and tracking_num = :trackingNum ");
			 sqlParam.put("trackingNum", trackingNum);
		}
		Query query = session.createSQLQuery(sql.toString());
		query.setProperties(sqlParam);
		return query;
	}

	public Query queryDepartInfo(Map<String, Object> inParam) {
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		String trackingNum=DataFormat.getStringKey(inParam, "trackingNum");
		String inputParamJson=StringEscapeUtils.unescapeHtml(DataFormat.getStringKey(inParam, "inputParamJson"));
		if (StringUtils.isNotEmpty(inputParamJson)) {
			Map<String,Object> inputParamMap=JsonHelper.parseJSON2Map(inputParamJson);
			 trackingNum=DataFormat.getStringKey(inputParamMap, "trackingNum");
		}
		//SELECT  od.发车批次， 合同编号， 车牌号，司机名称， 司机电话 FROM  order_info_child  oc,  ord_depart_info od, ord_depart_detail odd WHERE oc.child_order_id = odd.order_id AND od.batch_num = odd.batch_num;  
		StringBuffer sql = new StringBuffer();
		sql.append("select od.BATCH_NUM_ALIAS, od.TRANSPORT_CONTRACT, od.plate_number, od.driver_name, od.driver_bill, "
				+ "od.source_org_id,  od.desc_org_id, od.order_num, od.weight, od.volume,od.DEPART_OP_NAME, "
				+ "od.load_time, od.depart_time, od.arrival_time, od.arrival_op_name, od.state, od.remarks");
		sql.append("  FROM  order_info_child  oc,  ord_depart_info od, ord_depart_detail odd ");
		sql.append("  WHERE oc.child_order_id = odd.order_id AND od.batch_num = odd.batch_num");
		sql.append("  and oc.TRACKING_NUM= :trackingNum  limit 1");
		Query query = session.createSQLQuery(sql.toString());
		query.setParameter("trackingNum",trackingNum );
		return query;
	}
	//待发车列表查询
	
	public Query queryWaitDepartsInfo(Map<String, Object> inParam, boolean isSum) {
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		String consigneeBill=DataFormat.getStringKey(inParam, "consigneeBill");
		String consigneeName=DataFormat.getStringKey(inParam, "consigneeName");
		String consignorBill=DataFormat.getStringKey(inParam, "consignorBill");
		String consignorName=DataFormat.getStringKey(inParam, "consignorName");
		String createName =DataFormat.getStringKey(inParam, "createName");//下单人		
		long tenantId=DataFormat.getLongKey(inParam, "tenantId");//开单物流
		String orderNo=DataFormat.getStringKey(inParam, "orderNo");//
		String trackingNum=DataFormat.getStringKey(inParam, "trackingNum");//
		String workerName=DataFormat.getStringKey(inParam, "workerName");
		String workerBill=DataFormat.getStringKey(inParam, "workerBill");
		String beginInputTime=DataFormat.getStringKey(inParam, "beginInputTime");
		String endInputTime=DataFormat.getStringKey(inParam, "endInputTime");
		String beginCreateTime=DataFormat.getStringKey(inParam, "beginCreateTime");
		String endCreateTime=DataFormat.getStringKey(inParam, "endCreateTime");
		String inputUserName=DataFormat.getStringKey(inParam, "inputUserName");
		int orderState = DataFormat.getIntKey(inParam, "orderState");
	
		if(user.getUserType()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.CHAIN){
			tenantId=user.getTenantId();
		}
		String companyName = "";
		String inputParamJson=StringEscapeUtils.unescapeHtml(DataFormat.getStringKey(inParam, "inputParamJson"));
		if (StringUtils.isNotEmpty(inputParamJson)) {
			Map<String,Object> inputParamMap=JsonHelper.parseJSON2Map(inputParamJson);
			 consigneeBill=DataFormat.getStringKey(inputParamMap, "consigneeBill");
			 consigneeName=DataFormat.getStringKey(inputParamMap, "consigneeName");
			 consignorBill=DataFormat.getStringKey(inputParamMap, "consignorBill");
			 consignorName=DataFormat.getStringKey(inputParamMap, "consignorName");
			 tenantId=DataFormat.getLongKey(inputParamMap, "tenantId");
			 orderNo=DataFormat.getStringKey(inputParamMap, "orderNo");
			 trackingNum=DataFormat.getStringKey(inputParamMap, "trackingNum");
			 workerName=DataFormat.getStringKey(inputParamMap, "workerName");
			 workerBill=DataFormat.getStringKey(inputParamMap, "workerBill");
			 inputUserName=DataFormat.getStringKey(inputParamMap, "inputUserName");
			 companyName=DataFormat.getStringKey(inputParamMap, "companyName"); 
			 createName=DataFormat.getStringKey(inputParamMap, "createName");//下单人
		}
		String timeOut = DataFormat.getStringKey(inParam, "timeOut");
		//
		StringBuffer sql = new StringBuffer();
		//订单号 运单号 归属省市区 提货地址 到站 cityName 开单物流 companyName 
		//拉包工 拉包工电话 发货人 发货人号码 收货人 收货人号码  收货地址 服务方式 下单时间 开单人 开单时间  备注 
		//派单人 派单时间   预约提货时间 提货时间 
		sql.append(" SELECT  o.order_no,o.order_id,o.tracking_num,o.province_id,o.city_id,o.county_id,o.address, ");
		sql.append(" o.service_type,o.payment_type,o.consignee_Name,o.consignee_Bill,o.create_date, ");//12
		sql.append(" o.DIS_OP_NAME,o.DIS_TIME,o.OP_DATE,g.consignor_Name,g.consignor_Bill,g.province_Id as goodsProvince, ");//18
		sql.append(" g.city_id as goodsCity,g.county_id as goodsCountyId,g.address as goodsAddress,g.id_no,g.PLAN_PICKUP_TIME,g.PICKUP_TIME, ");//24
		sql.append(" P.WORKER_PHONE,P.WORKER_NAME,P.DELIVERY_BILL,P.INPUT_USER_NAME,P.INPUT_TIME, ");//29
		sql.append(" P.DELIVERY_TIME,P.GX_END_TIME,o.remark,o.TENANT_ID,o.CREATE_DATE,o.COMPANY_NAME ");//35
		if(StringUtils.isNotEmpty(timeOut) && "true".equals(timeOut)){
			sql.append(" ,(UNIX_TIMESTAMP(now())*1000 - UNIX_TIMESTAMP(o.CREATE_DATE)*1000 ) as timeout   ");
		}
		sql.append(" FROM ord_orders_info AS O INNER JOIN  ord_goods_info AS G ON O.ORDER_ID=G.ORDER_ID and G.id_no=O.id_no  INNER JOIN ord_busi_person AS P ON  O.ORDER_ID=P.ORDER_ID  ");
		//sql.append(" LEFT JOIN ord_sign_info as S ON s.ORDER_ID = o.ORDER_ID ");
		sql.append(" WHERE  O.order_state = 10  ");
		
		//开单时间  下单时间 订单号    发货人  发货人电话  拉包工  拉包工电话   开单人  收货人 收货人电话
		Map<String,Object> sqlParam = new HashMap<String,Object>();
		//下单时间
		if(StringUtils.isNotEmpty(orderNo)){
			sql.append(" and o.ORDER_NO like :orderNo  ");
			sqlParam.put("orderNo", "%"+orderNo+"%");
		}
		if(StringUtils.isNotEmpty(timeOut) && "true".equals(timeOut)){
			sql.append(" and (((UNIX_TIMESTAMP(now())*1000 - UNIX_TIMESTAMP(o.CREATE_DATE)*1000 )/60000) > (SELECT cfg_value from sys_cfg where cfg_name='ORDERS_TIMEOUT'))  ");
		}
		if(StringUtils.isNotEmpty(trackingNum)){
			sql.append(" and o.tracking_num like :trackingNum  ");
			sqlParam.put("trackingNum", "%"+trackingNum+"%");
		}
		if(StringUtils.isNotEmpty(consigneeBill)){
			sql.append(" and o.consignee_Bill like :consigneeBill  ");
			sqlParam.put("consigneeBill", "%"+consigneeBill+"%");
		}
		if(StringUtils.isNotEmpty(consigneeName)){
			sql.append(" and o.consignee_Name like :consigneeName  ");
			sqlParam.put("consigneeName", "%"+consigneeName+"%");
		}
		if(StringUtils.isNotEmpty(consignorName)){
			sql.append(" and g.consignor_Name like :consignorName  ");
			sqlParam.put("consignorName","%"+consignorName+"%");
		}
		if(StringUtils.isNotEmpty(consignorBill)){
			sql.append(" and g.consignor_Bill like :consignorBill  ");
			sqlParam.put("consignorBill", "%"+consignorBill+"%");
		}
		if(StringUtils.isNotEmpty(workerName)){
			sql.append(" and p.worker_Name like :workerName  ");
			sqlParam.put("workerName", "%"+workerName+"%");
		}
		if(StringUtils.isNotEmpty(workerBill)){
			sql.append(" and p.worker_phone like :workerBill  ");
			sqlParam.put("workerBill", "%"+workerBill+"%");
		}
		if(StringUtils.isNotEmpty(inputUserName)){
			sql.append(" and p.input_User_Name like :inputUserName  ");
			sqlParam.put("inputUserName", "%"+inputUserName+"%");
		}
		if(orderState>0){
			sql.append(" and o.ORDER_STATE=:orderState ");
			sqlParam.put("orderState", orderState);
		}
		if(StringUtils.isNotEmpty(beginCreateTime)){
			sql.append(" and o.create_Date >= :beginCreateTime ");
			sqlParam.put("beginCreateTime", beginCreateTime+ " 00:00:00");
		}
		if(StringUtils.isNotEmpty(endCreateTime)){
			sql.append(" and o.create_Date <= :endCreateTime ");
			sqlParam.put("endCreateTime",  endCreateTime+" 23:59:59");
		}
		if(StringUtils.isNotEmpty(beginInputTime)){
			sql.append(" and p.input_time >= :beginInputTime ");
			sqlParam.put("beginInputTime",beginInputTime+ " 00:00:00");
		}
		if(StringUtils.isNotEmpty(endInputTime)){
			sql.append(" and p.input_time <= :endInputTime ");
			sqlParam.put("endInputTime", endInputTime+" 23:59:59");
		}
		if(StringUtils.isNotEmpty(timeOut) && "true".equals(timeOut)){
			sql.append(" order by timeout desc    ");
		}else{
			sql.append("  order by o.order_id desc ");
		}
		
		Query query = session.createSQLQuery(sql.toString());
		query.setProperties(sqlParam);
		return query;
	}
	
	
	
	
	
}
