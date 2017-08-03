package com.wo56.business.ord.intf;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.components.citys.City;
import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.intf.CmUserInfoPullTF;
import com.wo56.business.cm.intf.OrganizationYunQiTF;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.business.grabOrder.out.BusiGrabConsts;
import com.wo56.business.grabOrder.out.BusiGrabControlOut;
import com.wo56.business.ord.impl.OrdOrdersInfoSV;
import com.wo56.business.ord.impl.OrderInfoSV;
import com.wo56.business.ord.vo.OrdBusiPerson;
import com.wo56.business.ord.vo.OrdGoodsInfo;
import com.wo56.business.ord.vo.OrdOpLog;
import com.wo56.business.ord.vo.OrdOrdersInfo;
import com.wo56.business.ord.vo.OrdSignInfo;
import com.wo56.business.ord.vo.OrderFee;
import com.wo56.business.ord.vo.OrderInfo;
import com.wo56.business.ord.vo.out.ChildOrderInfoOut;
import com.wo56.business.ord.vo.out.DepartOrderInfoOut;
import com.wo56.business.ord.vo.out.OrdDetailOutParam;
import com.wo56.business.ord.vo.out.OrdGoodsInfoOutParam;
import com.wo56.business.ord.vo.out.OrdListOutParam;
import com.wo56.business.ord.vo.out.OrdListSumOutParam;
import com.wo56.business.ord.vo.out.OrdOpLogListOut;
import com.wo56.business.ord.vo.out.OrdOrdersInfoQRCodeOut;
import com.wo56.business.ord.vo.out.OrdOrdersListOutParam;
import com.wo56.business.ord.vo.out.TrackListOutParam;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.BeanUtil;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.GpsUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SmsYQUtil;
import com.wo56.common.utils.SysTenantDefCacheDataUtil;
/***
 * 下单模块的类
 * @author zjy
 *
 */
public class OrdOrdersWebTF implements IOrdOrdersWebIntf {
	private static final Log log = LogFactory.getLog(OrdOrdersWebTF.class);
	
	private Query queryOrdersInfo(Map<String,Object> inParam,boolean isSum){
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		String consigneeBill=DataFormat.getStringKey(inParam, "consigneeBill");
		String consigneeName=DataFormat.getStringKey(inParam, "consigneeName");
		String consignorBill=DataFormat.getStringKey(inParam, "consignorBill");
		String consignorName=DataFormat.getStringKey(inParam, "consignorName");
		String createName =DataFormat.getStringKey(inParam, "createName");//下单人
		long tenantId=DataFormat.getLongKey(inParam, "tenantId");
		long tenantId1=0;
		String orderNo=DataFormat.getStringKey(inParam, "orderNo");
		String trackingNum=DataFormat.getStringKey(inParam, "trackingNum");
		String workerName=DataFormat.getStringKey(inParam, "workerName");
		String workerBill=DataFormat.getStringKey(inParam, "workerBill");
		String beginInputTime=DataFormat.getStringKey(inParam, "beginInputTime");
		String endInputTime=DataFormat.getStringKey(inParam, "endInputTime");
		String beginCreateTime=DataFormat.getStringKey(inParam, "beginCreateTime");
		String endCreateTime=DataFormat.getStringKey(inParam, "endCreateTime");
		String inputUserName=DataFormat.getStringKey(inParam, "inputUserName");
		String beginCancelTime=DataFormat.getStringKey(inParam, "beginCancelTime");
		String endCancelTime=DataFormat.getStringKey(inParam, "endCancelTime");
		int orderState = DataFormat.getIntKey(inParam, "orderState");
		String orderStateName=DataFormat.getStringKey(inParam, "orderStateName");//订单状态
		long orgId = DataFormat.getLongKey(inParam, "orgId");
		if(user.getUserType()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.CHAIN){
			tenantId1=user.getTenantId();
		}		
		String companyName = "";
		String orgName = "";
		String cityName = "";
		 String pickAddress = "";
		 String receiverAddress = "";
		 String serviceTypeName = "";
		 String paymentTypeName = "";
		 String serviceType = "";
		 String paymentType = "";
		 String productName = "";
		 String serviceCharge = "";
		 String packingName = "";
		 String deliveryCharge = "";
		 String remark = "";
		 String count = "";
		 String weight = "";
		 String volume = "";
		 String freight = "";
		 String reputation = "";
		 String premium = "";
		 String transitFee = "";
		 String tipsMonery = "";
		 String collectMoney = "";
		 String landFee = "";
		 String pickFee = "";
		 String otherFee = "";
		 String totalFee = "";
		 String createDate="";
		 String inputTime="";
		 long freightLong = 0;
		 long reputationLong =0;
		 long transitFeeLong = 0;
		 long tipsMoneryLong =0;
		 long collectMoneyLong = 0;
		 long landFeeLong= 0;
		 long pickFeeLong =0;
		 long otherFeeLong =0;
		 long totalFeeLong =0;
		 long premiumLong = 0;
		 long serviceChargeLong = 0;
		 long deliveryChargeLong=0;
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
			 companyName=DataFormat.getStringKey(inputParamMap, "companyName"); //开单物流
			 createName=DataFormat.getStringKey(inputParamMap, "createName");//下单人
			 orderStateName=DataFormat.getStringKey(inputParamMap, "orderStateName");//订单状态	 
			 List<SysStaticData> sysStatic = SysStaticDataUtil.getSysStaticDataList("ORDERS_STATE");
			 if(sysStatic == null && sysStatic.size()<=0){
				 throw new BusinessException("在静态表sys_static_data未配置订单状态数据！");  
			 }
			 for(SysStaticData stata:sysStatic ){
				if(stata.getCodeName().equals(orderStateName)){
					orderState= Integer.parseInt(stata.getCodeValue());
					break;
				} 
				
			 }
			  orgName = DataFormat.getStringKey(inputParamMap, "orgName");//开单网点
			  cityName = DataFormat.getStringKey(inputParamMap, "cityName");//到站
			  pickAddress = DataFormat.getStringKey(inputParamMap, "pickAddress");
			  receiverAddress = DataFormat.getStringKey(inputParamMap, "receiverAddress");
			  serviceTypeName =DataFormat.getStringKey(inputParamMap, "serviceTypeName");
			  paymentTypeName =DataFormat.getStringKey(inputParamMap, "paymentTypeName");
			  productName = DataFormat.getStringKey(inputParamMap, "productName");
			  serviceCharge = DataFormat.getStringKey(inputParamMap, "serviceCharge");
			  packingName = DataFormat.getStringKey(inputParamMap, "packingName");
			  deliveryCharge = DataFormat.getStringKey(inputParamMap, "deliveryCharge");
			  remark = DataFormat.getStringKey(inputParamMap, "remark");
			  count = DataFormat.getStringKey(inputParamMap, "count");
			  weight =DataFormat.getStringKey(inputParamMap,"weight");
			  volume =DataFormat.getStringKey(inputParamMap,"volume");
			  freight = DataFormat.getStringKey(inputParamMap,"freight");
			  reputation = DataFormat.getStringKey(inputParamMap,"reputation");
			  premium = DataFormat.getStringKey(inputParamMap,"premium");
			  transitFee = DataFormat.getStringKey(inputParamMap,"transitFee");
			  tipsMonery =DataFormat.getStringKey(inputParamMap,"tipsMonery");
			  collectMoney = DataFormat.getStringKey(inputParamMap,"collectMoney");
			  landFee = DataFormat.getStringKey(inputParamMap,"landFee");
			  pickFee = DataFormat.getStringKey(inputParamMap,"pickFee");
			  otherFee =DataFormat.getStringKey(inputParamMap,"otherFee");
			  totalFee =DataFormat.getStringKey(inputParamMap,"totalFee");	
			  createDate=DataFormat.getStringKey(inputParamMap,"createDate");
			  inputTime=DataFormat.getStringKey(inputParamMap,"createTime");
			  if(StringUtils.isNotEmpty(freight)){
				  freightLong = Long.valueOf(freight)*100; 
			  }
			  if(StringUtils.isNotEmpty(pickFee)){
				  pickFeeLong =Long.valueOf(pickFee)*100;
			  }
			  if(StringUtils.isNotEmpty(landFee)){
				  landFeeLong=  Long.valueOf(landFee)*100;
			  }
			  if(StringUtils.isNotEmpty(collectMoney)){
				  collectMoneyLong =  Long.valueOf(collectMoney)*100;
			  }
			  if(StringUtils.isNotEmpty(tipsMonery)){
				  tipsMoneryLong = Long.valueOf(tipsMonery)*100;
			  }
			  if(StringUtils.isNotEmpty(transitFee)){
				  transitFeeLong = Long.valueOf(transitFee)*100;
			  }
			  if(StringUtils.isNotEmpty(reputation)){
				  reputationLong = Long.valueOf(reputation)*100;
			  }
			 
			  if(StringUtils.isNotEmpty(otherFee)){
				  otherFeeLong =Long.valueOf(otherFee)*100;
			  }
			  if(StringUtils.isNotEmpty(totalFee)){
				  totalFeeLong =Long.valueOf(totalFee)*100;
			  }
			  if(StringUtils.isNotEmpty(premium)){
				  premiumLong =Long.valueOf(premium)*100; 
			  }
			  if(StringUtils.isNotEmpty(serviceCharge)){
				  serviceChargeLong = Long.valueOf(serviceCharge)*100; 
			  }
			  if(StringUtils.isNotEmpty(deliveryCharge)){
				  deliveryChargeLong =Long.valueOf(deliveryCharge)*100; 
			  }
		    
		}
//		if(StringUtils.isNotEmpty(orderNo)){
//			Criteria orderInfo = session.createCriteria(OrdOrdersInfo.class);
//			orderInfo.add(Restrictions.eq("orderNo", orderNo));
//			List<OrdOrdersInfo> ordOrdersInfo = orderInfo.list();
//			ordInfo = ordOrdersInfo.get(0);
//			
//			Criteria goodsCa = session.createCriteria(OrdGoodsInfo.class);
//		    goodsCa.add(Restrictions.eq("orderId", ordInfo.getOrderId()));
//		    List<OrdGoodsInfo> ordGoodsInfos = goodsCa.list();
//		    ordGoodInfo = ordGoodsInfos.get(0);	
//		}
	
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		if (isSum) {
			sql.append(" sum(oo.NUMBER),sum(oo.WEIGHT),sum(oo.VOLUME),sum(F.FREIGHT),sum(F.REPUTATION),sum(F.PREMIUM),sum(F.DELIVERY_CHARGE),");//7
			sql.append("sum(F.TRANSIT_FEE),sum(F.OTHER_FEE),sum(F.COLLECT_MONEY),sum(F.LAND_FEE),sum(F.SERVICE_CHARGE),sum(F.TOTAL_FEE),sum(o.TIP_MONEY),sum(F.PACKING_COSTS)");
		}else{
			sql.append(" o.order_no,o.order_id,o.tracking_num,o.province_id as ordersProvinceId,o.city_id as ordersCityId,o.county_id as ordersCountyId,o.address as ordersAddress,");
			sql.append( "o.service_type,o.payment_type,o.consignee_Name,o.consignee_Bill,o.create_date,");//12
			sql.append( "o.DIS_OP_NAME,o.DIS_TIME,o.OP_DATE,g.consignor_Name,g.consignor_Bill,g.province_Id,");//18
			sql.append( "g.city_id,g.county_id,g.address,g.id_no,g.PLAN_PICKUP_TIME,g.PICKUP_TIME,");//24
			sql.append( "P.WORKER_PHONE,P.WORKER_NAME,P.DELIVERY_BILL,P.INPUT_USER_NAME,P.INPUT_TIME,");//29
			sql.append( "P.DELIVERY_TIME,P.GX_END_TIME,oo.REMARKS,");//32
			sql.append( "oo.PRODUCT_NAME,oo.PACK_NAME,oo.CREATE_NAME,oo.NUMBER,");//36
			sql.append( "oo.WEIGHT,oo.VOLUME,");//38
			sql.append( "F.FREIGHT,F.REPUTATION,F.PREMIUM,F.DELIVERY_CHARGE,F.TRANSIT_FEE,F.OTHER_FEE,F.COLLECT_MONEY");//45
			sql.append( ",F.LAND_FEE,F.SERVICE_CHARGE,F.TOTAL_FEE,o.order_state,o.company_name,o.org_id,o.TIP_MONEY,o.tenant_id,o.CREATE_DATE,oo.CREATE_TIME,cm.user_name as ordersCreateName ,F.PACKING_COSTS   ");//57
		}
		sql.append("  FROM ord_orders_info AS O left JOIN ");

		sql.append( " ord_goods_info AS G ON O.ORDER_ID=G.ORDER_ID and G.id_no=o.id_no ");
		//sql.append( "from ord_goods_info as gg where gg.order_id=o.order_id) ");
		sql.append( " left JOIN ord_busi_person AS P ON  O.ORDER_ID=P.ORDER_ID ");
		sql.append( "left join order_info as oo on oo.ords_id=o.order_id  left JOIN ORDER_FEE AS F ON F.ORDER_ID=OO.ORDER_ID ");
		sql.append( " left  join cm_user_info cm ON cm.user_id = o.CREATE_ID ");
		sql.append( "WHERE 1=1 " );
		if(orderState==-1){
			sql.append( "and o.order_state not in (8,9)  " );
		}else if(orderState==-2){
			sql.append( " and o.order_state  in (8,9)  " );
		}
			
		Map<String,Object> sqlParam = new HashMap<String,Object>();
		if(tenantId>0 ){
			sql.append(" and o.tenant_id=:tenantId ");
			sqlParam.put("tenantId", tenantId);
		}
		if(tenantId1>0){
			sql.append(" and o.tenant_id=:tenantId ");
			sqlParam.put("tenantId", tenantId1);
			
		}
		if(StringUtils.isNotEmpty(orderNo)){
			sql.append(" and o.ORDER_NO like :orderNo  ");
			sqlParam.put("orderNo", "%"+orderNo+"%");
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
			sqlParam.put("consigneeName","%"+ consigneeName+"%");
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
			sql.append(" and o.ORDER_STATE =:orderState ");
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
		//取消时间
		if(StringUtils.isNotEmpty(beginCancelTime)){
			sql.append(" and o.OP_DATE>= :beginCancelTime ");
			sqlParam.put("beginCancelTime",beginCancelTime+ " 00:00:00");
		}
		if(StringUtils.isNotEmpty(endCancelTime)){
			sql.append(" and o.OP_DATE <= :endCancelTime ");
			sqlParam.put("endCancelTime", endCancelTime+" 23:59:59");
		}
		if (StringUtils.isNotEmpty(companyName)) {
			sql.append(" and o.COMPANY_NAME like :companyName ");
			sqlParam.put("companyName", "%"+companyName+"%");
		}
		//下单人
		if(StringUtils.isNotEmpty(createName)){
			sql.append("  and cm.user_name like :createName ");
			sqlParam.put("createName", "%"+createName+"%");
		}
		
		if(StringUtils.isNotEmpty(weight)){
			sql.append("  and oo.weight >= :weight ");
			sqlParam.put("weight", weight);
		}
		
		if(StringUtils.isNotEmpty(volume)){
			sql.append("  and oo.volume >= :volume ");
			sqlParam.put("volume", volume);
		}
		//费用相关字段
		
		if(StringUtils.isNotEmpty(freight)){
			sql.append("  and F.freight >= :freight ");
			sqlParam.put("freight", freightLong);
		}
		
		if(StringUtils.isNotEmpty(reputation)){
			sql.append("  and F.reputation >= :reputation ");
			sqlParam.put("reputation", reputationLong);
		}
		if(StringUtils.isNotEmpty(premium)){
			sql.append("  and F.premium >= :premium ");
			sqlParam.put("premium", premiumLong);
		}
		if(StringUtils.isNotEmpty(transitFee)){
			sql.append("  and F.transit_Fee >= :transitFee ");
			sqlParam.put("transitFee", transitFeeLong);
		}
		if(StringUtils.isNotEmpty(tipsMonery)){
			sql.append("  and F.tip >= :tipsMonery ");
			sqlParam.put("tipsMonery", tipsMoneryLong);
		}
		
		if(StringUtils.isNotEmpty(collectMoney)){
			sql.append("  and F.collect_money >= :collectMoney ");
			sqlParam.put("collectMoney", collectMoneyLong);
		}
		
		if(StringUtils.isNotEmpty(landFee)){
			sql.append("  and F.land_Fee >= :landFee ");
			sqlParam.put("landFee", landFeeLong);
		}
		if(StringUtils.isNotEmpty(pickFee)){
			sql.append("  and F.PACKING_COSTS >= :pickFee ");
			sqlParam.put("pickFee", pickFeeLong);
		}
		if(StringUtils.isNotEmpty(otherFee)){
			sql.append("  and F.other_Fee >= :otherFee ");
			sqlParam.put("otherFee", otherFeeLong);
		}
		if(StringUtils.isNotEmpty(totalFee)){
			sql.append("  and F.total_Fee >= :totalFee ");
			sqlParam.put("totalFee", totalFeeLong);
		}
		if(StringUtils.isNotEmpty(serviceCharge)){
			sql.append("  and F.service_charge >= :serviceCharge ");
			sqlParam.put("serviceCharge", serviceChargeLong);
		}
		//deliveryCharge
		if(StringUtils.isNotEmpty(deliveryCharge)){
			sql.append("  and F.delivery_charge >= :deliveryCharge ");
			sqlParam.put("deliveryCharge", deliveryChargeLong);
		}
		if(StringUtils.isNotEmpty(remark)){
			sql.append("  and oo.remarks like :remark ");
			sqlParam.put("remark", "%"+remark+"%");
		}
		
		if(StringUtils.isNotEmpty(count)){
			sql.append("  and oo.NUMBER >= :count ");
			sqlParam.put("count", count);
		}
		if(StringUtils.isNotEmpty(productName)){
			sql.append("  and oo.PRODUCT_NAME like :productName ");
			sqlParam.put("productName", "%"+productName+"%");
		}
		
		if(StringUtils.isNotEmpty(packingName)){
			sql.append("  and oo.PACK_NAME like :packingName ");
			sqlParam.put("packingName", "%"+packingName+"%");
		}
		//收货地址
		if(StringUtils.isNotEmpty(receiverAddress )){
//			long provinceId = ordInfo.getProvinceId();
//			long cityId = ordInfo.getCityId();
//			long countyId = ordInfo.getCountyId();
//			String address = ordInfo.getAddress();
//			if(provinceId>0){
//				sql.append("  and o.PROVINCE_ID =  :provinceId ");
//				sqlParam.put("provinceId", provinceId);
//			}
//			if(cityId>0){
//				sql.append("  and o.CITY_ID =  :cityId ");
//				sqlParam.put("cityId", cityId);
//			}
//			if(countyId>0){
//				sql.append("  and o.COUNTY_ID =  :countyId ");
//				sqlParam.put("countyId", countyId);
//			}
//			if(StringUtils.isNotEmpty(address)){
//				sql.append("  and o.address like :address ");
//				sqlParam.put("address", "%"+address+"%");
//			}
			
		}
		//发货地址
		if(StringUtils.isNotEmpty(pickAddress)){
//			long pickProvinceId = ordGoodInfo.getProvinceId();
//			long pickCityId = ordGoodInfo.getCityId();
//			long pickCountyId = ordGoodInfo.getCountyId();
//			String pickAdress = ordGoodInfo.getAddress();
//			if(pickProvinceId>0){
//				sql.append("  and g.PROVINCE_ID =  :pickProvinceId ");
//				sqlParam.put("pickProvinceId", pickProvinceId);
//			}
//			if(pickCityId>0){
//				sql.append("  and g.CITY_ID =  :pickCityId ");
//				sqlParam.put("pickCityId", pickCityId);
//			}
//			if(pickCountyId>0){
//				sql.append("  and g.county_id =  :pickCountyId ");
//				sqlParam.put("pickCountyId", pickCountyId);
//			}
//			if(StringUtils.isNotEmpty(pickAdress)){
//				sql.append("  and g.address like :pickAdress ");
//				sqlParam.put("pickAdress", "%"+pickAdress+"%");
//			}
		}
		//服务方式
		if(StringUtils.isNotEmpty(serviceTypeName)){
			serviceType = getSysStaticCodeValue(serviceTypeName, "SERVICE_TYPE");
			sql.append("  and o.service_type = :serviceType ");
			sqlParam.put("serviceType", serviceType);
		}
		
		//付款方式
       if(StringUtils.isNotEmpty(paymentTypeName)){
    	     paymentTypeName = getSysStaticCodeValue(paymentTypeName, "PAYMENT_TYPE_YQ");
			sql.append("  and o.payment_type = :paymentTypeName ");
			sqlParam.put("paymentTypeName", paymentTypeName);
		}
		//到站cityName
        if(StringUtils.isNotEmpty(cityName)){
			sql.append("  and oo.des_city_name like :cityName ");
			sqlParam.put("cityName", "%"+cityName+"%");
		}
		//开单网点orgName
        if(StringUtils.isNotEmpty(orgName)){
        	sql.append(" and o.org_id in (select org_id from organization where  org_name  like :orgName  ) ");
        	sqlParam.put("orgName", "%"+orgName+"%");	
		}
        //查询条件  开单网点 
        if(orgId>0){
        	sql.append("  and o.org_id = :id ");
			sqlParam.put("id", orgId);
        }
        //开单时间  下单时间
        if(StringUtils.isNotEmpty(createDate)){
			sql.append("  and o.CREATE_DATE like :createDate ");
			sqlParam.put("createDate", "%"+createDate+"%");
		}
        if(StringUtils.isNotEmpty(inputTime)){
			sql.append("  and P.INPUT_TIME like :inputTime");
			sqlParam.put("inputTime", "%"+inputTime+"%");
		}
		
		sql.append("  order by o.order_id desc ");
		Query query = session.createSQLQuery(sql.toString());
		query.setProperties(sqlParam);
		return query;
	}


	private String getSysStaticCodeValue(String codeName,String str) {
		// TODO Auto-generated method stub
		 String codeValue = "";
		 List<SysStaticData> sysStatic = SysStaticDataUtil.getSysStaticDataList(str);
		  if(sysStatic == null && sysStatic.size()<=0){
			 throw new BusinessException("在静态表sys_static_data未配置订单状态数据！");  
		 }
		 for(SysStaticData stata:sysStatic ){
			if(stata.getCodeName().equals(codeName)){
				codeValue= Integer.parseInt(stata.getCodeValue())+"";
				break;
			} 
			
		 }
		return codeValue;
	}


	/****
	 * 订单管理列表查询
	 * @param inParam
	 * */
	@SuppressWarnings("unchecked")
	public Map<String,Object>   queryOrders(Map<String,Object> inParam) throws Exception{
		
		IPage p = PageUtil.gridPage(queryOrdersInfo(inParam, false));
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		List<OrdListOutParam> outList = new ArrayList<OrdListOutParam>();
		for (Object[] obj : list) {
			OrdListOutParam outParam = new OrdListOutParam();
			String orderNoOut=(String) obj[0];
			outParam.setOrderNo(orderNoOut);
			BigInteger orderId=(BigInteger) obj[1];
			outParam.setOrderId(orderId.longValue());
			String trackingNumOut=(String) obj[2];
			outParam.setTrackingNum(trackingNumOut);
			BigInteger provinceId=(BigInteger) obj[3];
			BigInteger cityId=(BigInteger) obj[4];
			outParam.setCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(cityId)).getName());
			BigInteger countyId=(BigInteger) obj[5];
			String receiveAdress=(String) obj[6];
			String address = "";
			address=SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(provinceId)).getName();
			if(cityId!=null)
			address=address+SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(cityId)).getName();
			if(countyId==BigInteger.valueOf(-1)||countyId==null){
				
			}else{
				address=address+SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(countyId))!=null?SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(countyId)).getName():"";
			}
			
			if(receiveAdress!=null){
				outParam.setReceiverAddress(address+receiveAdress);
			}else {
				outParam.setReceiverAddress(address);
			}
			
			Integer serviceType=(Integer) obj[7];
			if(serviceType!=null){
				outParam.setServiceTypeName(SysStaticDataUtil.getSysStaticDataCodeName("SERVICE_TYPE", String.valueOf(serviceType)));
			}
			Integer paymentType=(Integer) obj[8];
			if(paymentType!=null){
				outParam.setPaymentTypeName(SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE_YQ", String.valueOf(paymentType)));
			}
			String consigneeNameOut=(String) obj[9];
			outParam.setConsigneeName(consigneeNameOut);
			String consigneeBillOut=(String) obj[10];
			outParam.setConsigneeBill(consigneeBillOut);
			Date createDate=(Date) obj[11];
			outParam.setCreateTime(DateUtil.formatDate(createDate,DateUtil.DATETIME_FORMAT1));
			String disOpName=(String) obj[12];
			outParam.setDisOpName(disOpName);
			Date disTime=(Date) obj[13];
			if(disTime!=null){
				outParam.setDisTime(DateUtil.formatDate(disTime,DateUtil.DATETIME_FORMAT1));
			}
			Date signDate=(Date) obj[14];
			if(signDate!=null){
				outParam.setSignTime(DateUtil.formatDate(signDate,DateUtil.DATETIME_FORMAT1));
				outParam.setOpTime(DateUtil.formatDate(signDate,DateUtil.DATETIME_FORMAT1));
			}
			String consignorNameOut=(String) obj[15];
			outParam.setConsignorName(consignorNameOut);
			String consignorBillOut=(String) obj[16];
			outParam.setConsignorBill(consignorBillOut);
			BigInteger pickProvinceId=(BigInteger) obj[17];
			BigInteger pickCityId=(BigInteger) obj[18];
			BigInteger pickCountyId=(BigInteger) obj[19];
			String pickAdress=(String) obj[20];
			String pickStation="";
			pickStation=pickStation+SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(pickProvinceId)).getName();
			if(pickCityId!=null)
			pickStation=pickStation+SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(pickCityId)).getName();
			if(pickCountyId!=null)
			//pickStation=pickStation+SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT",String.valueOf(pickCountyId))!=null?SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(pickCountyId)).getName():"";
			pickStation=pickStation+SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(pickCountyId)).getName();
			outParam.setPickStation(pickStation);
			if(pickAdress!=null){
				outParam.setPickAddress(pickStation+pickAdress);	
			}else{
				outParam.setPickAddress(pickStation);
			}
			
			BigInteger idNo=(BigInteger) obj[21];
			if (idNo != null) {
				outParam.setIdNo(idNo.longValue());
			}
			Date planPickupTime=(Date) obj[22];
			if(planPickupTime!=null){
				outParam.setPlanPickupTime(DateUtil.formatDate(planPickupTime,DateUtil.DATETIME_FORMAT1));
			}
			Date pickupTime=(Date) obj[23];
			if(pickupTime!=null){
				outParam.setPickupTime(DateUtil.formatDate(pickupTime,DateUtil.DATETIME_FORMAT1));
			}
			String workerBillOut=(String) obj[24];
			outParam.setWorkerBill(workerBillOut);
			String workerNameOut=(String) obj[25];
			outParam.setWorkerName(workerNameOut);
			String deliveryBill=(String) obj[26];
			String inputUserNameOut=(String) obj[27];
			outParam.setInputUserName(inputUserNameOut);
			Date inputTime=(Date) obj[28];
			if(inputTime!=null){
				outParam.setInputTime(DateUtil.formatDate(inputTime,DateUtil.DATETIME_FORMAT1));
				outParam.setCreateTime(DateUtil.formatDate(inputTime,DateUtil.DATETIME_FORMAT1));
			}
			Date deliveryTime=(Date) obj[29];
			if(deliveryTime!=null){
				outParam.setDeliveryTime(DateUtil.formatDate(deliveryTime,DateUtil.DATETIME_FORMAT1));
			}
			Date gxEndTime=(Date) obj[30];
			if(gxEndTime!=null){
				outParam.setGxEndTime(DateUtil.formatDate(gxEndTime,DateUtil.DATETIME_FORMAT1));
			}
			outParam.setRemark(obj[31] != null ? obj[31].toString() : "");
			String productName=(String) obj[32];
			outParam.setProductName(productName);
			String packingName=(String) obj[33];
			outParam.setPackingName(packingName);
			//String createName=(String) obj[34];
			//outParam.setInputUserName(createName);
			Integer count=(Integer) obj[35];
			if(count!=null){
			outParam.setCount(count+"");
			}
			String weight=(String) obj[36];
			if(StringUtils.isNotEmpty(weight)){
				outParam.setWeight(weight);
			}
			String volume=(String) obj[37];
			if(StringUtils.isNotEmpty(volume)){
				outParam.setVolume(volume);
			}
			BigInteger freight=(BigInteger) obj[38];
			if(freight!=null){
				outParam.setFreight(com.wo56.common.utils.CommonUtil.parseDouble(freight.longValue()));
			}
			BigInteger reputation=(BigInteger) obj[39];
			if(reputation!=null){
				outParam.setReputation(com.wo56.common.utils.CommonUtil.parseDouble(reputation.longValue()));
			}
			BigInteger premium=(BigInteger) obj[40];
			if(premium!=null){
				outParam.setPremium(com.wo56.common.utils.CommonUtil.parseDouble(premium.longValue()));
			}
			BigInteger delivery_charge=(BigInteger) obj[41];
			if(delivery_charge!=null){
				outParam.setDeliveryCharge(com.wo56.common.utils.CommonUtil.parseDouble(delivery_charge.longValue()));
			}
			BigInteger transit_fee=(BigInteger) obj[42];
			if(transit_fee!=null){
				outParam.setTransitFee(com.wo56.common.utils.CommonUtil.parseDouble(transit_fee.longValue()));
			}
			BigInteger other_fee=(BigInteger) obj[43];
			if(other_fee!=null){
				outParam.setOtherFee(com.wo56.common.utils.CommonUtil.parseDouble(other_fee.longValue()));
			}
			BigInteger collect_money=(BigInteger) obj[44];
			if(collect_money!=null){
				outParam.setCollectMoney(com.wo56.common.utils.CommonUtil.parseDouble(collect_money.longValue()));
			}
			BigInteger land_fee=(BigInteger) obj[45];
			if(land_fee!=null){
				outParam.setLandFee(com.wo56.common.utils.CommonUtil.parseDouble(land_fee.longValue()));
			}
			BigInteger service_charge=(BigInteger) obj[46];
			if(service_charge!=null){
				outParam.setServiceCharge(com.wo56.common.utils.CommonUtil.parseDouble(service_charge.longValue()));
			}
			BigInteger total_fee=(BigInteger) obj[47];
			if(total_fee!=null){
				outParam.setTotalFee(com.wo56.common.utils.CommonUtil.parseDouble(total_fee.longValue()));
			}
			Integer orderStateOut=(Integer) obj[48];
			if(orderStateOut!=null){
				outParam.setOrderStateName(SysStaticDataUtil.getSysStaticDataCodeName("ORDERS_STATE", String.valueOf(orderStateOut)));
			}
			String companyNames=(String) obj[49];
			BigInteger tenantIdO= (BigInteger) obj[52];
			if(tenantIdO!=null){
			outParam.setCompanyName(SysTenantDefCacheDataUtil.getTenantName(tenantIdO.longValue()));
			}else{
				outParam.setCompanyName(companyNames);
			}
			BigInteger orgId=(BigInteger) obj[50];
			if(orgId!=null){
				outParam.setOrgName(OraganizationCacheDataUtil.getOrgName(orgId.longValue()));
			}
			BigInteger tipsMoney=(BigInteger) obj[51];
			if(tipsMoney!=null){
				outParam.setTipsMonery(com.wo56.common.utils.CommonUtil.parseDouble(tipsMoney.longValue()));
			}
			Date createDateStr=(Date) obj[53];
			outParam.setCreateDate(DateUtil.formatDate(createDateStr,DateUtil.DATETIME_FORMAT1));
			Date createTime=(Date) obj[54];
			//outParam.setCreateTime(DateUtil.formatDate(createTime,DateUtil.DATETIME_FORMAT1));
			outParam.setCreateName(obj[55] != null ?  obj[55].toString() : "");		
			//outParam.setPickFee(obj[56]!=null? obj[56].toString() : "");//包装费
			BigInteger pickFee=(BigInteger) obj[56];
			if(pickFee!=null){
				outParam.setPickFee(com.wo56.common.utils.CommonUtil.parseDouble(pickFee.longValue()));
			}
			outList.add(outParam);
		}
		p.setThisPageElements(outList);
		Pagination<Object[]> page = new Pagination<Object[]>(p);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		String _sum = DataFormat.getStringKey(inParam, "_sum");
		if("1".equals(_sum)){
			OrdListSumOutParam sumOrderInfo= queryOrdersSum(inParam);
			rtnMap.put("sumData", sumOrderInfo);
		}
		return rtnMap;
	}
	//运单
		
	/****
	 * 订单管理列表查询
	 * @param inParam
	 * */
	public OrdListSumOutParam  queryOrdersSum(Map<String,Object> inParam) throws Exception{
		IPage p = PageUtil.gridPage(queryOrdersInfo(inParam, true));
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		OrdListSumOutParam out =new  OrdListSumOutParam();
		for (Object[] obj : list) {
			BigDecimal number= (BigDecimal)obj[0];
			if(number!=null){
			out.setCount(number.toString());
			}
			Double weight= (Double)obj[1];
			if(weight!=null){
			out.setWeight(weight.toString());
			}
			Double volume= (Double)obj[2];
			if(volume!=null)
			out.setVolume(volume.toString());
			BigDecimal freight= (BigDecimal)obj[3];
			if(freight!=null)
			out.setFreight(CommonUtil.parseDouble(freight.longValue()));
			BigDecimal reputation= (BigDecimal)obj[4];
			if(reputation!=null)
			out.setReputation(CommonUtil.parseDouble(reputation.longValue()));
			BigDecimal premium= (BigDecimal)obj[5];
			if(premium!=null)
			out.setPremium(CommonUtil.parseDouble(premium.longValue()));
			BigDecimal deliveryCharge= (BigDecimal)obj[6];
			if(deliveryCharge!=null)
			out.setDeliveryCharge(CommonUtil.parseDouble(deliveryCharge.longValue()));
			BigDecimal transitFee= (BigDecimal)obj[7];
			if(transitFee!=null)
			out.setTransitFee(CommonUtil.parseDouble(transitFee.longValue()));
			BigDecimal otherFee= (BigDecimal)obj[8];
			if(otherFee!=null)
			out.setOtherFee(CommonUtil.parseDouble(otherFee.longValue()));
			BigDecimal collectMoney= (BigDecimal)obj[9];
			if(collectMoney!=null)
			out.setCollectMoney(CommonUtil.parseDouble(collectMoney.longValue()));
			BigDecimal landFee= (BigDecimal)obj[10];
			if(landFee!=null)
			out.setLandFee(CommonUtil.parseDouble(landFee.longValue()));
			BigDecimal serviceCharge= (BigDecimal)obj[11];
			if(serviceCharge!=null)
			out.setServiceCharge(CommonUtil.parseDouble(serviceCharge.longValue()));
			BigDecimal totalFee= (BigDecimal)obj[12];
			if(totalFee!=null)
			out.setTotalFee(CommonUtil.parseDouble(totalFee.longValue()));
			BigDecimal tipsMoney= (BigDecimal)obj[13];
			if(tipsMoney!=null)
			out.setTipsMonery(CommonUtil.parseDouble(tipsMoney.longValue()));
			BigDecimal pickFee= (BigDecimal)obj[14];
			if(pickFee != null){
				out.setPickFee(CommonUtil.parseDouble(pickFee.longValue()));	
			}	    
		}
		return out;
	}
	/****
	 * 催单列表查询
	 * @param inParam
	 * */
	public Map<String,Object>   reminderQuery(Map<String,Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		String consigneeBill=DataFormat.getStringKey(inParam, "consigneeBill");
		String consigneeName=DataFormat.getStringKey(inParam, "consigneeName");
		String consignorBill=DataFormat.getStringKey(inParam, "consignorBill");
		String consignorName=DataFormat.getStringKey(inParam, "consignorName");
		String orderNo=DataFormat.getStringKey(inParam, "orderNo");
		String workerName=DataFormat.getStringKey(inParam, "workerName");
		String workerBill=DataFormat.getStringKey(inParam, "workerBill");
		String beginCreateTime=DataFormat.getStringKey(inParam, "beginCreateTime");
		String endCreateTime=DataFormat.getStringKey(inParam, "endCreateTime");
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  o.order_no,o.order_id,o.tracking_num,o.province_id,o.city_id,o.county_id,o.address,");//12
		sql.append("o.service_type,o.payment_type,o.consignee_Name,o.consignee_Bill,o.create_date,");//18
		sql.append("o.DIS_OP_NAME,o.DIS_TIME,o.OP_DATE,g.consignor_Name,g.consignor_Bill,g.province_Id as goodsProvince,");//24
		sql.append("g.city_id as goodsCity,g.county_id as goodsCountyId,g.address as goodsAddress,g.id_no,g.PLAN_PICKUP_TIME,g.PICKUP_TIME,");//29
		sql.append("P.WORKER_PHONE,P.WORKER_NAME,P.DELIVERY_BILL,P.INPUT_USER_NAME,P.INPUT_TIME,");
		sql.append("P.DELIVERY_TIME,P.GX_END_TIME,o.remark,g.REMINDER_TIME,g.REMINDER_count,o.company_name,o.tenant_id    FROM ord_orders_info AS O INNER JOIN  ord_goods_info AS G ON O.ORDER_ID=G.ORDER_ID   INNER JOIN ord_busi_person AS P ON  O.ORDER_ID=P.ORDER_ID  WHERE  O.order_type!=3 and  O.create_type!=4  and o.order_state in (1,2) and g.reminder_count>0");
		Map<String,Object> sqlParam = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(orderNo)){
			sql.append(" and o.ORDER_NO like :orderNo  ");
			sqlParam.put("orderNo", "%"+orderNo+"%");
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
		
		if(StringUtils.isNotEmpty(beginCreateTime)){
			sql.append(" and o.create_Date >= :beginCreateTime ");
			sqlParam.put("beginCreateTime", beginCreateTime+ " 00:00:00");
		}
		if(StringUtils.isNotEmpty(endCreateTime)){
			sql.append(" and o.create_Date <= :endCreateTime ");
			sqlParam.put("endCreateTime",  endCreateTime+" 23:59:59");
		}
		sql.append("  order by o.order_id desc ");
		Query query = session.createSQLQuery(sql.toString());
		query.setProperties(sqlParam);
		IPage p = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		List<OrdOrdersListOutParam> outList = new ArrayList<OrdOrdersListOutParam>();
		for (Object[] obj : list) {
			OrdOrdersListOutParam outParam = new OrdOrdersListOutParam();
			String orderNoOut=(String) obj[0];
			outParam.setOrderNo(orderNoOut);
			BigInteger orderId=(BigInteger) obj[1];
			outParam.setOrderId(orderId.longValue());
			String trackingNumOut=(String) obj[2];
			outParam.setTrackingNum(trackingNumOut);
			BigInteger provinceId=(BigInteger) obj[3];
			BigInteger cityId=(BigInteger) obj[4];
			outParam.setCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", cityId+"").getName());
			BigInteger countyId=(BigInteger) obj[5];
			String receiveAdress=(String) obj[6];
			String address=SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", provinceId+"").getName();
			if(cityId!=null)
			address=address+SysStaticDataUtil.getCityDataList("SYS_CITY", cityId+"").getName();
			if(countyId!=null)
			address=address+(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", countyId+"")!=null?SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", countyId+"").getName():"");
			outParam.setReceiverAddress(address+receiveAdress);
			Integer serviceType=(Integer) obj[7];
			if(serviceType!=null){
				outParam.setServiceTypeName(SysStaticDataUtil.getSysStaticDataCodeName("SERVICE_TYPE", serviceType+""));
			}
			Integer paymentType=(Integer) obj[8];
			if(paymentType!=null){
				outParam.setPaymentTypeName(SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE_YQ", paymentType+""));
			}
			String consigneeNameOut=(String) obj[9];
			outParam.setConsigneeName(consigneeNameOut);
			String consigneeBillOut=(String) obj[10];
			outParam.setConsigneeBill(consigneeBillOut);
			Date createDate=(Date) obj[11];
			outParam.setCreateTime(DateUtil.formatDate(createDate,DateUtil.DATETIME_FORMAT1));
			String disOpName=(String) obj[12];
			outParam.setDisOpName(disOpName);
			Date disTime=(Date) obj[13];
			if(disTime!=null){
				outParam.setDisTime(DateUtil.formatDate(disTime,DateUtil.DATETIME_FORMAT1));
			}
			Date signDate=(Date) obj[14];
			if(signDate!=null){
				outParam.setSignTime(DateUtil.formatDate(signDate,DateUtil.DATETIME_FORMAT1));
			}
			String consignorNameOut=(String) obj[15];
			outParam.setConsignorName(consignorNameOut);
			String consignorBillOut=(String) obj[16];
			outParam.setConsignorBill(consignorBillOut);
			BigInteger pickProvinceId=(BigInteger) obj[17];
			BigInteger pickCityId=(BigInteger) obj[18];
			BigInteger pickCountyId=(BigInteger) obj[19];
			String pickAdress=(String) obj[20];
			String pickStation="";
			pickStation=pickStation+SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", pickProvinceId+"").getName();
			if(pickCityId!=null)
			pickStation=pickStation+SysStaticDataUtil.getCityDataList("SYS_CITY", pickCityId+"").getName();
			if(pickCountyId!=null)
			pickStation=pickStation+(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", pickCountyId+"")!=null?SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", pickCountyId+"").getName():"");
			outParam.setPickStation(pickStation);
			outParam.setPickAddress(pickAdress);
			BigInteger idNo=(BigInteger) obj[21];
			outParam.setIdNo(idNo.longValue());
			Date planPickupTime=(Date) obj[22];
			if(planPickupTime!=null){
				outParam.setPlanPickupTime(DateUtil.formatDate(planPickupTime,DateUtil.DATETIME_FORMAT1));
			}
			Date pickupTime=(Date) obj[23];
			if(pickupTime!=null){
				outParam.setPickupTime(DateUtil.formatDate(pickupTime,DateUtil.DATETIME_FORMAT1));
			}
			String workerBillOut=(String) obj[24];
			outParam.setWorkerBill(workerBillOut);
			String workerNameOut=(String) obj[25];
			outParam.setWorkerName(workerNameOut);
			String deliveryBill=(String) obj[26];
			String inputUserNameOut=(String) obj[27];
			outParam.setInputUserName(inputUserNameOut);
			Date inputTime=(Date) obj[28];
			if(inputTime!=null){
				outParam.setInputTime(DateUtil.formatDate(inputTime,DateUtil.DATETIME_FORMAT1));
			}
			Date deliveryTime=(Date) obj[29];
			if(deliveryTime!=null){
				outParam.setDeliveryTime(DateUtil.formatDate(deliveryTime,DateUtil.DATETIME_FORMAT1));
			}
			Date gxEndTime=(Date) obj[30];
			if(gxEndTime!=null){
				outParam.setGxEndTime(DateUtil.formatDate(gxEndTime,DateUtil.DATETIME_FORMAT1));
			}
			
			outParam.setRemark(obj[31]+"");
			Date reminderTime=(Date) obj[32];
			if(reminderTime!=null){
				outParam.setReminderTime(DateUtil.formatDate(reminderTime,DateUtil.DATETIME_FORMAT1));
			}
			Integer reminderCOunt=(Integer) obj[33];
			if(reminderCOunt!=null){
				outParam.setReminderCount(reminderCOunt+"");
			}else{
				outParam.setReminderCount("0");
			}
			String companyName=(String) obj[34];
			BigInteger tenantId=(BigInteger) obj[35];
			if(tenantId!=null){
				outParam.setCompanyName(SysTenantDefCacheDataUtil.getTenantName(tenantId.longValue()));
			}else{
				outParam.setCompanyName(companyName);
			}
			outList.add(outParam);
		}
		p.setThisPageElements(outList);
		Pagination<Object[]> page = new Pagination<Object[]>(p);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		return rtnMap;
	}
	
	/****
	 * 任务列表查询
	 * @param inParam
	 * */
	public Map<String,Object>   doQuery(Map<String,Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		String consigneeBill=DataFormat.getStringKey(inParam, "consigneeBill");
		String consigneeName=DataFormat.getStringKey(inParam, "consigneeName");
		String consignorBill=DataFormat.getStringKey(inParam, "consignorBill");
		String consignorName=DataFormat.getStringKey(inParam, "consignorName");
		String orderNo=DataFormat.getStringKey(inParam, "orderNo");
		String trackingNum=DataFormat.getStringKey(inParam, "trackingNum");
		String workerName=DataFormat.getStringKey(inParam, "workerName");
		String workerBill=DataFormat.getStringKey(inParam, "workerBill");
		String beginInputTime=DataFormat.getStringKey(inParam, "beginInputTime");
		String endInputTime=DataFormat.getStringKey(inParam, "endInputTime");
		String beginCreateTime=DataFormat.getStringKey(inParam, "beginCreateTime");
		String endCreateTime=DataFormat.getStringKey(inParam, "endCreateTime");
		String inputUserName=DataFormat.getStringKey(inParam, "inputUserName");
		int orderState = DataFormat.getIntKey(inParam, "orderState");
		String timeOut = DataFormat.getStringKey(inParam, "timeOut");
		
		String inputParamJson=StringEscapeUtils.unescapeHtml(DataFormat.getStringKey(inParam, "inputParamJson"));
		if (StringUtils.isNotEmpty(inputParamJson)) {
			Map<String,Object> inputParamMap=JsonHelper.parseJSON2Map(inputParamJson);
			 orderNo=DataFormat.getStringKey(inputParamMap, "orderNo");
			 trackingNum=DataFormat.getStringKey(inputParamMap, "trackingNum");
			 workerName=DataFormat.getStringKey(inputParamMap, "workerName");
			 workerBill=DataFormat.getStringKey(inputParamMap, "workerBill");
		}
		
		
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  o.order_no,o.order_id,o.tracking_num,o.province_id,o.city_id,o.county_id,o.address, ");
		sql.append(" o.service_type,o.payment_type,o.consignee_Name,o.consignee_Bill,o.create_date, ");//12
		sql.append(" o.DIS_OP_NAME,o.DIS_TIME,o.OP_DATE,g.consignor_Name,g.consignor_Bill,g.province_Id as goodsProvince, ");//18
		sql.append(" g.city_id as goodsCity,g.county_id as goodsCountyId,g.address as goodsAddress,g.id_no,g.PLAN_PICKUP_TIME,g.PICKUP_TIME, ");//24
		sql.append(" P.WORKER_PHONE,P.WORKER_NAME,P.DELIVERY_BILL,P.INPUT_USER_NAME,P.INPUT_TIME, ");//29
		sql.append(" P.DELIVERY_TIME,P.GX_END_TIME,o.remark,o.TENANT_ID,o.CREATE_DATE,s.SIGN_DATE,o.COMPANY_NAME  ");//34
		
		if(StringUtils.isNotEmpty(timeOut) && "true".equals(timeOut)){
			sql.append(" ,(UNIX_TIMESTAMP(now())*1000 - UNIX_TIMESTAMP(o.CREATE_DATE)*1000 ) as timeout   ");
		}
		sql.append(" FROM ord_orders_info AS O INNER JOIN  ord_goods_info AS G ON O.ORDER_ID=G.ORDER_ID and G.id_no=O.id_no  INNER JOIN ord_busi_person AS P ON  O.ORDER_ID=P.ORDER_ID  ");
		sql.append(" LEFT JOIN ord_sign_info as S ON s.ORDER_ID = o.ORDER_ID ");
//		sql.append(" WHERE  O.order_type!=3 and  O.create_type!=4   ");
		sql.append(" WHERE  1=1  ");
		Map<String,Object> sqlParam = new HashMap<String,Object>();
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
			if(orderState==6){
				sql.append(" and o.ORDER_STATE in (6,11) ");
			}else{
				sql.append(" and o.ORDER_STATE=:orderState ");
				sqlParam.put("orderState", orderState);
			}
			
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
		IPage p = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		List<OrdOrdersListOutParam> outList = new ArrayList<OrdOrdersListOutParam>();
		for (Object[] obj  : list) {
			OrdOrdersListOutParam outParam = new OrdOrdersListOutParam();
			String orderNoOut=(String) obj[0];
			outParam.setOrderNo(orderNoOut);
			BigInteger orderId=(BigInteger) obj[1];
			outParam.setOrderId(orderId.longValue());
			String trackingNumOut=(String) obj[2];
			outParam.setTrackingNum(trackingNumOut);
			BigInteger provinceId=(BigInteger) obj[3];
			BigInteger cityId=(BigInteger) obj[4];
			outParam.setCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", cityId+"").getName());
			BigInteger countyId=(BigInteger) obj[5];
			String receiveAdress=(String) obj[6];
			String address="";
			address=SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", provinceId+"").getName();
			if(cityId!=null)
			address=address+SysStaticDataUtil.getCityDataList("SYS_CITY", cityId+"").getName();
			//if(countyId!=null)
			if(countyId==BigInteger.valueOf(-1)||countyId==null){
					
			}else{
				address=address+SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(countyId))!=null?SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(countyId)).getName():"";
			}
			//address=address+SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", countyId+"")!=null?SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", countyId+"").getName():"";
			if(receiveAdress!=null){
				outParam.setReceiverAddress(address+receiveAdress);
			}else{
				outParam.setReceiverAddress(address);
			}
			
			Integer serviceType=(Integer) obj[7];
			if(serviceType!=null){
				outParam.setServiceTypeName(SysStaticDataUtil.getSysStaticDataCodeName("SERVICE_TYPE", serviceType+""));
			}
			Integer paymentType=(Integer) obj[8];
			if(paymentType!=null){
				outParam.setPaymentTypeName(SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE_YQ", paymentType+""));
			}
			String consigneeNameOut=(String) obj[9];
			outParam.setConsigneeName(consigneeNameOut);
			String consigneeBillOut=(String) obj[10];
			outParam.setConsigneeBill(consigneeBillOut);
			Date createDate=(Date) obj[11];
			outParam.setCreateTime(DateUtil.formatDate(createDate,DateUtil.DATETIME_FORMAT1));
			String disOpName=(String) obj[12];
			outParam.setDisOpName(disOpName);
			Date disTime=(Date) obj[13];
			if(disTime!=null){
				outParam.setDisTime(DateUtil.formatDate(disTime,DateUtil.DATETIME_FORMAT1));
			}
			Date signDate=(Date) obj[14];
			if(signDate!=null){
				outParam.setSignTime(DateUtil.formatDate(signDate,DateUtil.DATETIME_FORMAT1));
			}
			String consignorNameOut=(String) obj[15];
			outParam.setConsignorName(consignorNameOut);
			String consignorBillOut=(String) obj[16];
			outParam.setConsignorBill(consignorBillOut);
			BigInteger pickProvinceId=(BigInteger) obj[17];
			BigInteger pickCityId=(BigInteger) obj[18];
			BigInteger pickCountyId=(BigInteger) obj[19];
			String pickAdress=(String) obj[20];
			String pickStation="";
			pickStation=pickStation+SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", pickProvinceId+"").getName();
			if(pickCityId!=null){
				pickStation=pickStation+SysStaticDataUtil.getCityDataList("SYS_CITY", pickCityId+"").getName();
			}
			if(pickCountyId!=null){
				pickStation=pickStation+(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", pickCountyId+"")!=null?SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", pickCountyId+"").getName():"");
			}
			//outParam.setPickProCityCounty(pickStation);
			outParam.setPickStation(pickStation);
			outParam.setPickAddress(pickAdress);
			BigInteger idNo=(BigInteger) obj[21];
			outParam.setIdNo(idNo.longValue());
			Date planPickupTime=(Date) obj[22];
			if(planPickupTime!=null){
				outParam.setPlanPickupTime(DateUtil.formatDate(planPickupTime,DateUtil.DATETIME_FORMAT1));
			}
			Date pickupTime=(Date) obj[23];
			if(pickupTime!=null){
				outParam.setPickupTime(DateUtil.formatDate(pickupTime,DateUtil.DATETIME_FORMAT1));
			}
			String workerBillOut=(String) obj[24];
			outParam.setWorkerBill(workerBillOut);
			String workerNameOut=(String) obj[25];
			outParam.setWorkerName(workerNameOut);
			String deliveryBill=(String) obj[26];
			String inputUserNameOut=(String) obj[27];
			outParam.setInputUserName(inputUserNameOut);
			Date inputTime=(Date) obj[28];
			if(inputTime!=null){
				outParam.setInputTime(DateUtil.formatDate(inputTime,DateUtil.DATETIME_FORMAT1));
			}
			Date deliveryTime=(Date) obj[29];
			if(deliveryTime!=null){
				outParam.setDeliveryTime(DateUtil.formatDate(deliveryTime,DateUtil.DATETIME_FORMAT1));
			}
			Date gxEndTime=(Date) obj[30];
			if(gxEndTime!=null){
				outParam.setGxEndTime(DateUtil.formatDate(gxEndTime,DateUtil.DATETIME_FORMAT1));
			}
			outParam.setRemark(obj[31] != null ? obj[31].toString() : "");
			
			//增加专线名称  通过id转化名称
//			long tenantId =-1;
//			tenantId=obj[32]==null? -1:Long.valueOf(obj[32].toString());
//			String tenantName = CommonUtil.getTennatNameById(tenantId);
//			if(tenantName!=null){
//				outParam.setTenantName(tenantName);
//			}else {
//				outParam.setTenantName("");
//			}
			
			//增加下单时间
			Date doOrderTime=(Date)obj[33];
			if(doOrderTime!=null){
				outParam.setDoOrderTime(DateUtil.formatDate(doOrderTime,DateUtil.DATETIME_FORMAT1));
			}
			outParam.setSignTime(obj[34]!=null? DateUtil.formatDate((Date)obj[34],DateUtil.DATETIME_FORMAT1) : null);
			
			outParam.setTenantName(obj[35] != null ? obj[35].toString() : "");
			if(StringUtils.isNotEmpty(timeOut) && "true".equals(timeOut)){
				outParam.setTimeOutString(CommonUtil.formatTime(obj[36]!=null ? Long.valueOf(obj[36].toString()) : 0));
				
			}	
			outList.add(outParam);
		}
		p.setThisPageElements(outList);
		Pagination<Object[]> page = new Pagination<Object[]>(p);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		return rtnMap;
	}
	


	/***
	 * 任务列表派单管理统计
	 * @param inParam 
	 * 
	 */
	@Override
	public Map<String,Integer> statisticsTaskCount(Map<String,Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		Integer[] orderStateArr= new Integer[]{SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING,SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP,
				SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING,SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT,
				SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN,SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY,SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN, 
				SysStaticDataEnumYunQi.ORDERS_STATE.PENDING_DEPARTURE,SysStaticDataEnumYunQi.ORDERS_STATE.PARTIAL_SIGN};
		int orderstate = DataFormat.getIntKey(inParam, "orderstate");
		//String sql = " and ((now() - o.CREATE_DATE) / 60000) > (SELECT cfg_value from sys_cfg where cfg_name='ORDERS_TIMEOUT') ";
		Query query = session.createSQLQuery("select count(1),order_state   FROM ord_orders_info AS O INNER JOIN  ord_goods_info AS G ON O.ORDER_ID=G.ORDER_ID and G.id_no=O.id_no  WHERE o.order_state in (:orderStateList)  group by o.order_state  ");
		query.setParameterList("orderStateList", orderStateArr);
		
		Query query1 = session.createSQLQuery("select count(1)  FROM ord_orders_info AS O INNER JOIN  ord_goods_info AS G ON O.ORDER_ID=G.ORDER_ID and G.id_no=O.id_no  WHERE  o.order_state = :orderState AND (((UNIX_TIMESTAMP(now())*1000 - UNIX_TIMESTAMP(o.CREATE_DATE)*1000 )/60000) > (SELECT cfg_value from sys_cfg where cfg_name='ORDERS_TIMEOUT'))   ");
		query1.setParameter("orderState", SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING);
		Object object = query1.uniqueResult();
		
		List<Object[]> list = query.list();
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("timeOutDis", object != null ? Integer.valueOf(object.toString()) : 0);
		map.put("waitDis", 0);
		map.put("waitPickup", 0);
		map.put("carryPackaging", 0);
		map.put("inTransit", 0);
		map.put("waitDelivery", 0);
		map.put("waitSign", 0);
		map.put("partialSign", 0);
		map.put("doSign", 0);
		map.put("waitSignNew", 0);
		map.put("waitDepart", 0);
		for (Object[] obj : list) {
			BigInteger count= (BigInteger)obj[0];
			Integer orderState= (Integer)obj[1];
			if(orderState==SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING){
				map.put("waitDis", count.intValue());
			}else if(orderState==SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP){
				map.put("waitPickup", count.intValue());
			}else if(orderState==SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING){
				map.put("carryPackaging", count.intValue());
			}else if(orderState==SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT){
				map.put("inTransit", count.intValue());
			}else if(orderState==SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY){
				map.put("waitDelivery", count.intValue());
			}else if(orderState==SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN){
				//map.put("waitSign", count.intValue());
				map.put("waitSignNew", count.intValue());
			}else if(orderState==SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN){
				map.put("doSign", count.intValue());
			}else if(orderState==SysStaticDataEnumYunQi.ORDERS_STATE.PENDING_DEPARTURE){
				map.put("waitDepart", count.intValue());
			}else if(orderState == SysStaticDataEnumYunQi.ORDERS_STATE.PARTIAL_SIGN){
				map.put("partialSign", count.intValue());
			}
			
			
		}
		map.put("waitSign", map.get("waitSignNew")+map.get("partialSign"));
		return map;
	}

	@Override
	public List<Map<String, Object>> queryPickInfo(Map<String, Object> inParam)
			throws Exception {
		Session session = SysContexts.getEntityManager(true);
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if(orderId<0){
			throw new BusinessException("订单号不能为空!");
		}
		Criteria ca = session.createCriteria(OrdGoodsInfo.class);
		ca.add(Restrictions.eq("orderId", orderId));
		List<OrdGoodsInfo> list = ca.list();
		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		for (OrdGoodsInfo ordGoodsInfo : list) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("consignorName", StringUtils.defaultString(ordGoodsInfo.getConsignorName(),""));
			map.put("consignorBill",  StringUtils.defaultString(ordGoodsInfo.getConsignorBill(),""));
			map.put("planPickTime", ordGoodsInfo.getPlanPickupTime()==null?"":DateUtil.formatDate( ordGoodsInfo.getPlanPickupTime(), DateUtil.DATETIME_FORMAT));
			map.put("product", ordGoodsInfo.getProduct() != null ? ordGoodsInfo.getProduct() : "");
			map.put("pickAddress",StringUtils.defaultString(ordGoodsInfo.getProvineName(),"")+StringUtils.defaultString(ordGoodsInfo.getCityName(),"")+StringUtils.defaultString(ordGoodsInfo.getCountyName(),"") +StringUtils.defaultString(ordGoodsInfo.getAddress(),""));
			map.put("count", ordGoodsInfo.getCount()==null?0: ordGoodsInfo.getCount());
			rtnList.add(map);
		}
		return rtnList;
	}

	/****
	 * 查询拉包工
	 * 1、分配拉包工：剔除平台拉黑，改订单的客户拉黑，拉包公司拉黑，
	 * 2、若选择了专线公司，则自动优先加载到专线拉包工，然后专线相关的拉包公司拉包工，先返回10条
	 * 
	 * 拉包公列表展示
	 * 1、优先展示距离提货人方圆500/1000米以内接单数最少的20名拉包公
	 * 2、业务规则： 
	 *    a、 不展示拉黑和休息中的拉包工
	 *    b、
	 *    c、按照距离和接单数排序
	 *           
	 * 
	 */
	@Override
	public Map<String, Object> queryWorker(Map<String, Object> inParamt)
	throws Exception {
		String orderId =DataFormat.getStringKey(inParamt, "orderId");
		String workerBill = DataFormat.getStringKey(inParamt, "workerBill");
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try{
			
			if(StringUtils.isEmpty(orderId)){
				throw new BusinessException("请至少选择一条下单信息!");
			}
			if(StringUtils.isEmpty(workerBill)){
				//手动派单列表展示
				List<Map<String, Object>> queryWorkList = queryWorkerList(Long.valueOf(orderId));	
				if (queryWorkList == null || queryWorkList.size() <= 0 ) {
					return rtnMap;
				}
				rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(queryWorkList));
			}else {
				//查询拉包工
				 rtnMap = queryWorkerByBill(Long.valueOf(orderId),workerBill);
			}
			return rtnMap;
			
		}catch (BusinessException e){
			rtnMap.put("massage", e.getMessage());
		}
		 return rtnMap;
	}
	/*
	 * 手动派单 拉包工展示列表
	 */
	
	/*
	 * 手动派单 拉包工展示列表
	 */
	
	private List<Map<String, Object>> queryWorkerList(long orderId){
		Session session = SysContexts.getEntityManager(true);
		//获取订单的经纬度
		String latitude = null; //提货人经度
		String longitude = null;//提货人纬度
		List<Map<String, Object>> retListOut = new ArrayList<Map<String,Object>>();
		Set<String> userIdByDistance500List = null;
		Set<String>userIdByDistance1000List = null;
		Criteria goodsCa = session.createCriteria(OrdGoodsInfo.class);
		goodsCa.add(Restrictions.eq("orderId", orderId));
		List<OrdGoodsInfo> ordGoodsInfos = goodsCa.list();
		for (OrdGoodsInfo ordGoodsInfo : ordGoodsInfos) {
			if(ordGoodsInfo.getLat()==null || ordGoodsInfo.getLon()==null){
				continue;
			}else{
				latitude = ordGoodsInfo.getLat() ;//提货人的经度
				longitude =ordGoodsInfo.getLon();//提货人的纬度
				break;
			}
		}
		
		if (StringUtils.isEmpty(latitude) && StringUtils.isEmpty(longitude)) {
			
			throw new BusinessException("提貨人地址經緯度找不到！");
		}
		OrdOrdersInfo ordOrdersInfos = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfos!=null){
			//OrdOrdersInfo info=ordOrdersInfos.get(0);
			Long createUserId=ordOrdersInfos.getCreateId();
			Long zxTenantId=ordOrdersInfos.getConTenantId();
			if (StringUtils.isNotEmpty(latitude) && StringUtils.isNotEmpty(longitude)) {
				userIdByDistance500List = BusiGrabControlOut.getUserByDistance(Double.valueOf(latitude.toString()),Double.valueOf(longitude.toString()),500.00);
				if(userIdByDistance500List==null || userIdByDistance500List.size()<0){
					return retListOut;
				}
			}
			
			
			Set<String> fileterPullSet = new HashSet<String>();
			//根据用户ID列表得到提货人方圆1000米最少接单数的拉包公信息 并排序
			if(userIdByDistance500List.size() < 20){
				if (StringUtils.isNotEmpty(latitude) && StringUtils.isNotEmpty(longitude)) {
					userIdByDistance1000List = BusiGrabControlOut.getUserByDistance(Double.valueOf(latitude.toString()),Double.valueOf(longitude.toString()),1000.00);				
					if(userIdByDistance1000List==null && userIdByDistance1000List.size()<=0){
						return retListOut;
					}
					else if(userIdByDistance1000List.size()>0){
						//过滤拉黑和在休息的拉包工
						fileterPullSet = fileterPullBlack(zxTenantId, createUserId, userIdByDistance1000List);
						//得到前台展示结果
						List<Map<String,Object>> retListNoSort=  listPullInfo(fileterPullSet, latitude, longitude);
						//排序
						if(fileterPullSet.size()>0){
							return  descBynumAndDisctant(retListNoSort);
						}
				   }
		    
				}
			}
			else  if(userIdByDistance500List.size()>=20){
				fileterPullSet = fileterPullBlack(zxTenantId, createUserId, userIdByDistance500List);					
				if(fileterPullSet.size()>0){
				//排序
				List<Map<String, Object>> retListNoSort=  listPullInfo(fileterPullSet, latitude, longitude);
				if(fileterPullSet.size()>0){
					return  descBynumAndDisctant(retListNoSort);
				}				
            }	
		}
	}else{
		throw new BusinessException("订单信息找不到！");
	}	
		return null;
	}
	
	/*
	 * 手动派单 拉包工展示列表
	 */
	
	private List<Map<String, Object>> queryWorkerListNew(long orderId){
		Session session = SysContexts.getEntityManager(true);
		String latitude = null; //提货人经度
		String longitude = null;//提货人纬度
		List<Map<String, Object>> retListOut = new ArrayList<Map<String,Object>>();
		Set<String> userIdByDistance500List = null;
		Set<String>userIdByDistance1000List = null;
		Criteria goodsCa = session.createCriteria(OrdGoodsInfo.class);
		goodsCa.add(Restrictions.eq("orderId", orderId));
		List<OrdGoodsInfo> ordGoodsInfos = goodsCa.list();
		for (OrdGoodsInfo ordGoodsInfo : ordGoodsInfos) {
			if(ordGoodsInfo.getLat()==null || ordGoodsInfo.getLon()==null){
				continue;
			}else{
				latitude = ordGoodsInfo.getLat() ;//提货人的经度
				longitude =ordGoodsInfo.getLon();//提货人的纬度
				break;
			}
		}
		if (StringUtils.isEmpty(latitude) && StringUtils.isEmpty(longitude)) {	
				return retListOut;
		}	
		OrdOrdersInfo ordOrdersInfos = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfos!=null){
			Long createUserId=ordOrdersInfos.getCreateId();
			Long zxTenantId=ordOrdersInfos.getConTenantId();
			if (StringUtils.isNotEmpty(latitude) && StringUtils.isNotEmpty(longitude)) {
				userIdByDistance500List = BusiGrabControlOut.getUserByDistance(Double.valueOf(latitude.toString()),Double.valueOf(longitude.toString()),500.00);
				if(userIdByDistance500List==null || userIdByDistance500List.size()<0){
					return retListOut;
				}
				
			}			
			Set<String> fileterPullSet = new HashSet<String>();
			//根据用户ID列表得到提货人方圆1000米最少接单数的拉包公信息 并排序
			if(userIdByDistance500List.size() < 20){
				if (StringUtils.isNotEmpty(latitude) && StringUtils.isNotEmpty(longitude)) {
					userIdByDistance1000List = BusiGrabControlOut.getUserByDistance(Double.parseDouble(latitude.toString()),Double.parseDouble(longitude.toString()),1000.00);				
					if(userIdByDistance1000List==null && userIdByDistance1000List.size()<=0){
						return retListOut;
					}
					else if(userIdByDistance1000List.size()>0){
						//过滤拉黑和在休息的拉包工
						fileterPullSet = fileterPullBlack(zxTenantId, createUserId, userIdByDistance1000List);
						//得到前台展示结果
						List<Map<String,Object>> retListNoSort=  listPullInfo(fileterPullSet, latitude, longitude);
						//排序
						if(fileterPullSet.size()>0){
							return  descBynumAndDisctant(retListNoSort);
						}
				      }
				    }
			    }
			    else  if(userIdByDistance500List.size()>=20){
				     fileterPullSet = fileterPullBlack(zxTenantId, createUserId, userIdByDistance500List);					
				     if(fileterPullSet.size()>0){
				          //排序
				          List<Map<String, Object>> retListNoSort=  listPullInfo(fileterPullSet, latitude, longitude);
				          if(fileterPullSet.size()>0){
					       return  descBynumAndDisctant(retListNoSort);
				           }				
                       }	
		           }
		      }else{
		     throw new BusinessException("订单信息找不到！");
	        }	
		return null;
   }
	
	
	//排序
	
  private List<Map<String, Object>> descBynumAndDisctant(List<Map<String,Object>> retListNoSor){
	  
	    List<Map<String, Object>> retListOut = new ArrayList<Map<String,Object>>();
		//排序
		Collections.sort(retListNoSor, new Comparator<Map<String, Object>>() {
				
		     @Override
		     public int compare(Map<String, Object> o1,Map<String, Object> o2) {
			     // TODO Auto-generated method stub
			     int countO1 = Integer.valueOf(o1.get("count").toString());
			     int countO2 = Integer.valueOf(o2.get("count").toString());
			     double distanceO1 = Double.valueOf(o1.get("distance").toString());
			     double distanceO2 = Double.valueOf(o2.get("distance").toString());
					
			     //先排距离,再排接单数
			     if(distanceO1 < distanceO2){
				     return -1;
			     }else if(countO1 < countO2){
				      return -1;
			     }else return 0;
		     }
		});
		//取前20个map 值 
		for(int i=0;i<20;i++){
			retListOut.add(retListNoSor.get(i));
			if(i== retListNoSor.size()-1) break;
		}
		return retListOut;
	}
  
	
	/*
	 * 根据拉包工账号查询
	 */
	private Map<String, Object> queryWorkerByBill(long orderId,String workerBill){
		Session session = SysContexts.getEntityManager(true);
		String workBillNum = null;
		String latitude = null; //提货人经度
		String longitude = null;//提货人纬度
		Long cityId = null;////得到提货区域编码
		Criteria goodsCa = session.createCriteria(OrdGoodsInfo.class);
		goodsCa.add(Restrictions.eq("orderId", orderId));
		List<OrdGoodsInfo> ordGoodsInfos = goodsCa.list();//提货地址区域码
		//根据拉包工得到拉包工账号
		if(workerBill.length()==5){
			//得到提货区域编码
			for (OrdGoodsInfo ordGoodsInfo : ordGoodsInfos) {
				  if(ordGoodsInfo.getCityId()==null || ordGoodsInfo.getCityId()==null){
					continue;
				  }else{
					 latitude = ordGoodsInfo.getLat() ;//提货人的经度
					 longitude =ordGoodsInfo.getLon();//提货人的纬度
					 cityId = ordGoodsInfo.getCityId();
					 workBillNum = cityId.toString()+workerBill;
					 break;
				 }
			 }
		}
       else {
			for (OrdGoodsInfo ordGoodsInfo : ordGoodsInfos) {
				  if(ordGoodsInfo.getLat()==null || ordGoodsInfo.getLon()==null){
					continue;
				  }else{
					 latitude = ordGoodsInfo.getLat() ;//提货人的经度
					 longitude =ordGoodsInfo.getLon();//提货人的纬度
					 break;
				 }
			 }
		}
		new HashMap<String, Object>();
		Map<String,Object> PullInfoByBill = new HashMap<String, Object>();
	    PullInfoByBill  = PullInfoByBill(orderId,latitude,longitude,workBillNum,workerBill);
	    //组装map
		return PullInfoByBill;
	}
	/**
	 * 获取拉包工离订单的距离信息
	 * @param pullUserSet
	 * @param latitude //提货人经度
	 * @param longitude //提货人纬度
	 * @return
	 */
	private List<Map<String,Object>> listPullInfo(Set<String> pullUserSet,String latitude,String longitude){
		Session session = SysContexts.getEntityManager(true);
		String userLatitude=null;
		String useLongitude=null;
		String pullBill    =null;
//		String singularNum =null;
//	    String tenant_id   =null;
	    double lat = 0;//拉包公经度
	    double lon = 0;//拉包公纬度
	    double distance = 0;//距离
		   
	    StringBuffer sb = new StringBuffer();
		sb.append("SELECT p.USER_ID,p.PROVINCE_NAME,p.CITY_NAME,p.DISTRICT_NAME,p.TENANT_ID,cm.login_acct,cm.user_name,p.SINGULAR_NUM FROM cm_user_info_pull p,cm_user_info cm where cm.user_id = p.user_id and cm.state != 0  and p.pull_work = 1"
				+ " and p.user_id in (:pullUserSet) ");
		List<Map<String,Object>> outList = new ArrayList<Map<String,Object>>();	
		Query sqlQuery = session.createSQLQuery(sb.toString());
		if(pullUserSet.size()>0){
			sqlQuery.setParameterList("pullUserSet", pullUserSet);
		}else{
			return outList;
		}
		
		List<Object[]> cmUserInfoPulls = sqlQuery.list();
		List<Map<String,Object>> pullWorkList = new ArrayList();//用于存放展示拉包公的list	
			
		for(String str: pullUserSet){
			//得到拉包工的接单数，经纬度
			Map<String,Object> userMap = BusiGrabControlOut.getUserInfo(str);  
			Map<String,Object> map = new HashMap<String,Object>(); 
			if(userMap!=null){
				userLatitude= userMap.get(BusiGrabConsts.User.LATITUDE).toString();//拉包工经度
				useLongitude= userMap.get(BusiGrabConsts.User.LONGITUDE).toString();//拉包工纬度
			    pullBill    = userMap.get(BusiGrabConsts.User.BILL).toString();//拉包公账号
				lat =  Double.parseDouble(userLatitude.toString());
				lon =  Double.parseDouble(useLongitude.toString());
				BusiGrabControlOut.updateUserInfoPoint(Long.valueOf(str),lat,lon);	
//				map.put("userLatitude",userLatitude);
//				map.put("useLongitude", useLongitude);
				map.put("pullBill", pullBill);
				pullWorkList.add(map);
				
			}
		}	
		if(cmUserInfoPulls!=null && cmUserInfoPulls.size()>0){
			//TODO
			for (Object[] obj : cmUserInfoPulls) {
				Map<String,Object> map1 = new HashMap<String,Object>();
				BigInteger userId=(BigInteger) obj[0];
				map1.put("userId", userId.longValue());
				String provinceName=(String) obj[1];
				String cityName=(String) obj[2];
				String countyName=(String) obj[3];
				map1.put("address", provinceName+cityName+countyName);
				BigInteger tenantIdOut=(BigInteger) obj[4];
				map1.put("tenantName", SysTenantDefCacheDataUtil.getTenantName(tenantIdOut.longValue()));
				String phone=(String) obj[5];
				map1.put("phone", phone);
				String name=(String) obj[6];
				map1.put("name", name);
				BigInteger count =(BigInteger) obj[7];
				map1.put("count", count);
				for(Map<String, Object> mapData : pullWorkList){
					String s = mapData.get("pullBill").toString();
					if((String)obj[5]!=null && s.equals(obj[5].toString())){
						distance = GpsUtil.getLongDistance(Double.parseDouble(latitude),Double.parseDouble(longitude), lat, lon);	
						if(distance == 0){
							map1.put("distance","");
						}
						map1.put("distance",distance );
						break;
					}
					
				}
				outList.add(map1);
			}			
		}
		return outList;		
	}


	private Map<String, Object> PullInfoByBill(Long orderId,String latitude,String longitude, String workBillNum, String workerBill){	
		 Session session = SysContexts.getEntityManager(true);
		 double lat = 0;//拉包公经度
		 double lon = 0;//拉包公纬度
		 double distance = 0;//距离
		 Map<String, Object> rtnMap = new HashMap<String, Object>();			
		 StringBuffer sqlNew = new StringBuffer();
		 String addSql="";
		 if(StringUtils.isNotEmpty(workerBill)){
			  addSql=" and cm.login_acct="+workerBill;
		  }  
		   //按工号查询
		   if(StringUtils.isNotEmpty(workBillNum)){
		  	   addSql=	" and p.PULL_WORK = " + workBillNum;
		   }		 
		   sqlNew.append("SELECT p.USER_ID,p.PROVINCE_NAME,p.CITY_NAME,p.DISTRICT_NAME,p.TENANT_ID,cm.login_acct,cm.user_name,"
		   		+ "p.SINGULAR_NUM,b.type,p.pull_work  "
		   		+ " FROM cm_user_info_pull AS p "
		   		+ "LEFT JOIN cm_user_info AS cm ON p.user_id = cm.user_id "
		   		+ "LEFT  JOIN cm_pull_black AS b  ON cm.login_acct = b.bill "
		   		+ "LEFT JOIN cm_user_info_pull AS pl ON pl.user_id = cm.user_id "
		   		+ "WHERE cm.user_type = 7 " + addSql);
		   
		   Query sqlQuery = session.createSQLQuery(sqlNew.toString());
		  // Map<String, Object> pullUserSet = BusiGrabControlOut.getUserInfo(orderId.toString()) ;
		   IPage p = PageUtil.gridPage(sqlQuery);
		   List<Object[]> pullWorkerList = (List<Object[]>)p.getThisPageElements();
		   List<Map<String,String>> retList=new ArrayList<Map<String,String>>();
		   List<Map<String,Object>> outList = new ArrayList<Map<String,Object>>();	
		   
		   //拉黑和休息 
		   if(pullWorkerList!=null && pullWorkerList.size()>0){
			   for (Object[] obj : pullWorkerList) {
//				    String pullLat = null;
//					String pullLon = null;
					Map<String,Object> map1 = new HashMap<String,Object>();
					BigInteger userId=(BigInteger) obj[0];
					map1.put("userId", userId.longValue());
					String provinceName=(String) obj[1];
					String cityName=(String) obj[2];
					String countyName=(String) obj[3];
					map1.put("address", provinceName+cityName+countyName);
					BigInteger tenantIdOut=(BigInteger) obj[4];
					map1.put("tenantName", SysTenantDefCacheDataUtil.getTenantName(tenantIdOut.longValue()));
					String phone=(String) obj[5];
					map1.put("phone", phone);
					String name=(String) obj[6];
					map1.put("name", name);
					BigInteger count =(BigInteger) obj[7];
					map1.put("count", count);
					Map<String,Object> mapData = getPullInfo(userId);
					String pullLat = mapData.get("lat").toString();
					String pullLon = mapData.get("lon").toString();
					String s = mapData.get("pullBill").toString();
					lat = Double.parseDouble(mapData.get("lat").toString());
					lon = Double.parseDouble(mapData.get("lon").toString());
					if((String)obj[5]!=null && s.equals(obj[5].toString())){
						if (StringUtils.isEmpty(latitude) && StringUtils.isEmpty(longitude)){
							map1.put("distance","");
						}else if(StringUtils.isEmpty(pullLat) && StringUtils.isEmpty(pullLon)){
			            	map1.put("distance","");
			            }else {
			            	distance = GpsUtil.getLongDistance(Double.parseDouble(latitude),Double.parseDouble(longitude), lat, lon);
							map1.put("distance",distance );
			            }
					}
					//拉黑状态
					BigInteger blackType =(BigInteger) obj[8];
					map1.put("blackType", blackType);
					
					//休息状态
					Integer pullWork = Integer.valueOf(obj[9].toString());
					map1.put("pullWork",pullWork);
					outList.add(map1);
				}	
				p.setThisPageElements(outList);
				Pagination<Object[]> page = new Pagination<Object[]>(p);
				rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
				return rtnMap;  
		   }
			
			return rtnMap;
	}


  private Map<String, Object> getPullInfo(BigInteger userId ){
	    double lat = 0.0;
	    double lon = 0.0;
		//得到拉包工的接单数，经纬度
	    Map<String,Object> userMap = new HashMap<String, Object>();
	    userMap = BusiGrabControlOut.getUserInfo(userId.toString());  
	    if(userMap==null && userMap.size()<0){
	    	return userMap;
	    }
	    Map<String,Object> map = new HashMap<String,Object>();
	    String userLatitude = null;
	    String useLongitude = null;
		if(userMap!=null){
			userLatitude= String.valueOf(userMap.get(BusiGrabConsts.User.LATITUDE));//拉包工经度
			useLongitude= String.valueOf(userMap.get(BusiGrabConsts.User.LONGITUDE));//拉包工纬度	
			String singularNum = String.valueOf(userMap.get(BusiGrabConsts.User.SINGULAR_NUM));
			String pullBill    = String.valueOf(userMap.get(BusiGrabConsts.User.BILL));//拉包公账号
		    String tenant_id   =  String.valueOf(userMap.get(BusiGrabConsts.User.TENANT_ID));//拉包公账号     
		    if(userLatitude.equals("null")){
		    }else {
		    	lat = Double.valueOf(userLatitude);
		    }
		    if(useLongitude.equals("null")){
			 }else {
				 lon =  Double.valueOf(useLongitude);	
			 }
			BusiGrabControlOut.updateUserInfoPoint(Long.valueOf(userId.toString()),lat,lon);	
			map.put("userLatitude",userLatitude);
		    map.put("useLongitude", useLongitude);
			map.put("singularNum", singularNum);
			map.put("pullBill", pullBill);
			map.put("tenant_id", tenant_id);
			map.put("lat",lat);
			map.put("lon",lon);
		}			  
	    return map;
	  
  }
	
	/**
	 * 过滤拉黑的数据
	 * @param zxTenantId
	 * @param createUserId
	 * @param pullUserSet
	 * @return
	 */
	private Set<String> fileterPullBlack(Long zxTenantId,Long createUserId,Set<String> pullUserSet){
			Session session = SysContexts.getEntityManager(true);
		   
		    StringBuffer sb = new StringBuffer();
			sb.append("SELECT p.user_id FROM cm_pull_black b,cm_user_info_pull p,cm_user_info u");
			sb.append(" WHERE b.BILL=u.LOGIN_ACCT AND p.USER_ID=u.USER_ID");
			sb.append(" AND p.USER_ID IN (:pullUserSet)");
			
			if(zxTenantId==null){
				sb.append(" AND (TYPE=3 OR TYPE=2 OR (TYPE=0 AND TARGET_ID=:createUserId) ) ");
			}else{
				sb.append(" AND (TYPE=3 OR TYPE=2 OR (TYPE=0 AND TARGET_ID=:createUserId) OR (TYPE=1 AND BUSINESS_ID=:zxTenantId)) ");
			}
			
			
			Query sqlQuery = session.createSQLQuery(sb.toString());
			sqlQuery.setParameterList("pullUserSet", pullUserSet);
			sqlQuery.setParameter("createUserId",createUserId);
			if(zxTenantId!=null){
				sqlQuery.setParameter("zxTenantId",zxTenantId);
			}
			
			List<BigInteger> workerList = sqlQuery.list(); //拉黑列表
			Set<String> retSet =new HashSet<String>();
			for(String userId:pullUserSet){
				if(!workerList.contains(BigInteger.valueOf(Long.valueOf(userId)))){
					retSet.add(userId);
				}
			}
			
			
		  StringBuffer sb1 = new StringBuffer();
		  sb1.append("SELECT user_id FROM cm_user_info_pull p");
		  sb1.append(" WHERE p.PULL_WORK = 1 ");
		  sb1.append("AND p.USER_ID IN (:pullUserSet)");
			
			Query query = session.createSQLQuery(sb1.toString());
			query.setParameterList("pullUserSet", retSet);
			List<BigInteger> list = query.list(); //休息中的拉包工
			Set<String> set =new HashSet<String>();
			for(BigInteger userId:list){
				//if(workerList.contains(BigInteger.valueOf(Long.valueOf(userId)))){
				set.add(String.valueOf(userId));
				//}
			}
			return set;
	}
	
	/***
	 * 判断是否是注册用户
	 * @param loginAcct
	 * @return
	 */
	private boolean isRegisterUser(Session session,String loginAcct){
		Query query = session.createSQLQuery("SELECT COUNT(1) FROM CM_USER_INFO WHERE LOGIN_ACCT=:loginAcct ");
		query.setParameter("loginAcct", loginAcct);
		 BigInteger count = (BigInteger) query.uniqueResult();
		if(count.intValue()>0){
			return true;
		}
		return false;
	}
	
	/****
	 * @param inParam
	 * 分配任务
	 */
	@Override
	public Map<String, Object> disReceipt(Map<String, Object> inParam)
			throws Exception {
		// TODO Auto-generated method stub
		String orderId =DataFormat.getStringKey(inParam, "orderId");
		String notes =DataFormat.getStringKey(inParam, "notes");
		long userId = DataFormat.getLongKey(inParam, "userId");
		if(StringUtils.isEmpty(orderId)){
			throw new BusinessException("请至少选择一条下单信息!");
		}
		if(userId<0){
			throw new BusinessException("请选择拉包工!");
		}
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		List<Long> orderIdList = new ArrayList<Long>();
		String[] orderIdArr = orderId.split(",");
		for (String orderStr : orderIdArr) {
			orderIdList.add(Long.parseLong(orderStr));
		}
		List<String> noAllow=new ArrayList<String>();
		for (String orderStr : orderIdArr) {
			OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, Long.parseLong(orderStr));
			OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class,  Long.parseLong(orderStr));
			//平台派单管理，将当前的拉包工提货的订单调配给其他人，拉包工代客下单的不能调单
			if (ordOrdersInfo.getOrderState() != SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING) {
				if(ordOrdersInfo.getCreateId().longValue()==ordBusiPerson.getWorkerId().longValue()){
					noAllow.add(ordOrdersInfo.getOrderNo());
				}
			}
		}
		
		if(noAllow.size()>0){
			throw new BusinessException("订单号:"+noAllow.toString()+"是拉包工代客下单，不能进行调单");
		}
		
		for (String orderStr : orderIdArr) {
			
			OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, Long.parseLong(orderStr));
			if(ordOrdersInfo==null){
				throw new BusinessException("下单信息不存在！");
			}
			if(ordOrdersInfo.getOrderState()!=SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP&&ordOrdersInfo.getOrderState()!=SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING){
				throw new BusinessException("订单号["+ordOrdersInfo.getOrderNo()+"]已提货不能调单，请刷新页面或勾掉该单号！");
			}
			OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class,  Long.parseLong(orderStr));
			Long oldWorkerId=ordBusiPerson.getWorkerId();//原来旧的拉包工的id
			Criteria ca = session.createCriteria(CmUserInfo.class);
			ca.add(Restrictions.eq("userId", userId));
			ca.add(Restrictions.eq("userType", SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL));
			CmUserInfo cmUserInfo = (CmUserInfo) ca.uniqueResult();
			
			if(StringUtils.isNotEmpty(ordBusiPerson.getWorkerPhone())){
				Query query = session.createSQLQuery("select consignor_bill from ord_goods_info where order_id=:orderId");
				query.setParameter("orderId", ordOrdersInfo.getOrderId());
				List consignorList = query.list();
				for (Object consignor_bill : consignorList) {
					if(consignor_bill!=null&&StringUtils.isNotEmpty(consignor_bill.toString())){
						SmsYQUtil.backstageSendConsignor(user.getTenantId()==null?-1L:user.getTenantId(),cmUserInfo.getUserName(),cmUserInfo.getLoginAcct(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, consignor_bill.toString(),ordOrdersInfo.getOrderId(),ordOrdersInfo.getOrderNo());
					}
				}
				SmsYQUtil.backstageSendCarrier(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordBusiPerson.getWorkerPhone())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordBusiPerson.getWorkerPhone());
			}
			Query query = session.createSQLQuery("select PROVINCE_ID,city_id,address,consignor_bill from ord_goods_info where order_id=:orderId");
			query.setParameter("orderId", ordOrdersInfo.getOrderId());
			List<Object[]> list = query.list();
			ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP);
			ordOrdersInfo.setDisTime(new Date());
			ordOrdersInfo.setDisId(user.getUserId());
			ordOrdersInfo.setDisOpName(user.getUserName());
			ordOrdersInfo.setRemark(notes);
			session.update(ordOrdersInfo);
			//zhangy
			if(list.size()==1){
				Object[] objects = list.get(0);
				SmsYQUtil.sendPullWorker(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), 
						CommonUtil.getCityName(Long.parseLong(objects[0]+""),null, null)+CommonUtil.getCityName(null,Long.parseLong(objects[1]+""), null)+objects[2],EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, 
						ordOrdersInfo.getOrderId(), cmUserInfo.getLoginAcct());
				if(objects[3]!=null&&StringUtils.isNotEmpty(objects[3]+"")){
						if (isWeChatUser(session, objects[3].toString())) {
							 SmsYQUtil.disWorkerToConsigorWeCaht(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(), cmUserInfo.getUserName(), cmUserInfo.getLoginAcct(), objects[3].toString());
						}
					   SmsYQUtil.disWorkerToConsigor(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(), cmUserInfo.getUserName(), cmUserInfo.getLoginAcct(), objects[3].toString());
				}
			   
			}
			else{
				Object[] objects = list.get(0);
				SmsYQUtil.sendPullWorkerMul(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), 
				CommonUtil.getCityName(Long.parseLong(objects[0]+""),null, null)+CommonUtil.getCityName(null,Long.parseLong(objects[1]+""), null)+objects[2],EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, 
				ordOrdersInfo.getOrderId(), cmUserInfo.getLoginAcct());
				if(objects[3]!=null&&StringUtils.isNotEmpty(objects[3]+"")){
					 SmsYQUtil.disWorkerToConsigor(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(), cmUserInfo.getUserName(), cmUserInfo.getLoginAcct(), objects[3].toString());
				}
			}			
			if(cmUserInfo!=null){
				ordBusiPerson.setWorkerId(cmUserInfo.getUserId());
				ordBusiPerson.setWorkerPhone(cmUserInfo.getLoginAcct());
				ordBusiPerson.setWorkerName(cmUserInfo.getUserName());
				session.update(ordBusiPerson);
				
				CmUserInfoPullTF cmUserInfoPullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
				//新的老包工增加订单数
				CmUserInfoPull byUserPull = cmUserInfoPullTF.addSingularNum(userId);
				BusiGrabControlOut.updateUserInfoSingularNum(byUserPull.getUserId(), byUserPull.getSingularNum().intValue());
//				BusiMatchControlYq.updatePullLoadNum(byUserPull.getUserId(), byUserPull.getTenantId(), byUserPull.getSingularNum(),byUserPull.getDefaultSingularNum());
				if(oldWorkerId!=null){
					CmUserInfoPull oldUserPull = cmUserInfoPullTF.reduceSingularNum(oldWorkerId);
					if (oldUserPull!=null&&oldUserPull.getUserId()!=null) {
						
						BusiGrabControlOut.updateUserInfoSingularNum(oldUserPull.getUserId(), oldUserPull.getSingularNum().intValue());
					}
	//				BusiMatchControlYq.updatePullLoadNum(oldUserPull.getUserId(), oldUserPull.getTenantId(), oldUserPull.getSingularNum(),oldUserPull.getDefaultSingularNum());
				}
			}
			
			// 写日志
			OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
			ordLogNewTF.disWorkerLog(user.getUserId(), ordBusiPerson.getWorkerPhone(), user, ordOrdersInfo, ordBusiPerson.getWorkerName());
		}
		//TODO 调用删除redis里的单号方法
		BusiGrabControlOut.delAllOrderInfoAndGps(String.valueOf(orderId));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isOk", "Y");
		return map;
	}
	
	/**
	 * 判断是否是微信用户
	 * false 存在openid
	 * true 不存在
	 */
	private boolean isWeChatUser(Session session,String loginAcct){
		Criteria ca = session.createCriteria(CmUserInfo.class);
		ca.add(Restrictions.eq("loginAcct", loginAcct));
		ca.add(Restrictions.eq("state", SysStaticDataEnumYunQi.STS.VALID));
		ca.add(Restrictions.eq("userType", SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS));
		List<CmUserInfo> list  = ca.list();
		if (list!=null&&list.size()>0) {
			for (CmUserInfo cmUserInfo : list) {
				if (StringUtils.isNotEmpty(cmUserInfo.getOpenId())) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 分享订单
	 * @param orderId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getOrdersInfo(Map<String,Object> inParam) throws Exception{
		long orderId = DataFormat.getLongKey(inParam,"orderId");
		long qrCodeId = DataFormat.getLongKey(inParam,"qrCodeId");
		String qrCodeUrl = DataFormat.getStringKey(inParam,"qrCodeUrl");
		if(orderId < 0){
			throw new BusinessException("请传入订单编码！");
		}
		Session session = SysContexts.getEntityManager(true);
		OrdOrdersInfo orders = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(orders==null){
			throw new BusinessException("分享的订单不存在！");
		}
		OrdOrdersInfoQRCodeOut out = new OrdOrdersInfoQRCodeOut();
		out.setOrderId(orderId);
		out.setCityName(orders.getCityName());
		out.setConsigneeAddress(orders.getProvinceName() + orders.getCityName()+orders.getAddress());
		out.setConsigneeName(orders.getConsigneeName());
		out.setConsigneePhone(orders.getConsigneeBill());
		out.setTenantName(SysTenantDefCacheDataUtil.getTenantName(orders.getTenantId()));
		//保存二维码图片地址
		orders.setQrCodeId(qrCodeId);
		orders.setQrCodeUrl(qrCodeUrl);
		session.save(orders);
		
		out.setOrdersQrCodeUrl(qrCodeUrl);
		OrdBusiPerson busi = (OrdBusiPerson) session.get(OrdBusiPerson.class, orderId);
		out.setPullName(busi.getWorkerName());
		out.setPullPhone(busi.getWorkerPhone());
		List<OrdGoodsInfo> list = session.createCriteria(OrdGoodsInfo.class).add(Restrictions.eq("orderId", orderId)).list();
		out.setGoodList(list);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orders", out);
		map.put("goods", list);
		return map;
	}
	
	/**
	 * 分享订单
	 * @param orderId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getOrdersInfoOfNo(Map<String,Object> inParam) throws Exception{
		long orderId = DataFormat.getLongKey(inParam,"orderId");
		
		if(orderId < 0){
			throw new BusinessException("请传入订单编码！");
		}
		Session session = SysContexts.getEntityManager(true);
		OrdOrdersInfo orders = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(orders==null){
			throw new BusinessException("分享的订单不存在！");
		}
		Map<String,Object > retMap=new HashMap<String, Object>();
		
		retMap.put("orderNo", orders.getOrderNo());
		return retMap;
	}
	
	
	
	/****
	 * 干线到货（602018）
	 * @param inParam
	 * */
	public Map<String,Object>  gxArriveGoods(Map<String,Object> inParam) throws Exception{ 
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		String orderId = DataFormat.getStringKey(inParam, "orderId");
		if(StringUtils.isEmpty(orderId)){
			throw new BusinessException("请至少选择一条下单信息!");
		}
		List<Long> orderIdList = new ArrayList<Long>();
		String[] orderIdArr = orderId.split(",");
		for (String orderStr : orderIdArr) {
			orderIdList.add(Long.parseLong(orderStr));
		}
		for (Long orderIdL : orderIdList) {
			OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderIdL);
			if(ordOrdersInfo==null){
				throw new BusinessException("下单信息不存在！");
			}
			if(ordOrdersInfo.getOrderState().intValue()!=SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT){
				throw new BusinessException("运单状态为【"+ordOrdersInfo.getOrderStateName()+"】，不能操作干线到货!");
			}
			if(ordOrdersInfo.getServiceType()==SysStaticDataEnumYunQi.SERVICE_TYPE.TAKE_PICK){
				ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN);
			}else{
				ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY);
			}
			Date d = new Date();
			ordOrdersInfo.setOpDate(d);
			ordOrdersInfo.setOpId(user.getUserId());
			session.update(ordOrdersInfo);
			OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
			if(ordBusiPerson!=null){
				ordBusiPerson.setGxEndOpId(user.getUserId());
				ordBusiPerson.setGxEndOpName(user.getUserName());
				ordBusiPerson.setGxEndTime(d);
				session.update(ordBusiPerson);
			}
			// 写日志
			OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
			ordLogNewTF.gxArriverGoodsOrderLog(user.getUserId(), user, ordOrdersInfo, SysTenantDefCacheDataUtil.getTenantName(user.getTenantId()), user.getUserName(), OraganizationCacheDataUtil.getOrgName(user.getOrgId()),StringUtils.defaultIfEmpty(OraganizationCacheDataUtil.getOrganization(user.getOrgId()).getSupportStaff(), ""));
			//TODO 推送（系统自动推送消息给到收货人，发货人）
			if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
				SmsYQUtil.arriveSendConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(),ordOrdersInfo.getOrderId(),OraganizationCacheDataUtil.getOrganization(user.getOrgId()).getSupportStaff(), OraganizationCacheDataUtil.getOrgName(user.getOrgId()),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE,  ordOrdersInfo.getConsigneeBill());
			}
			Query query = session.createSQLQuery("select consignor_bill from ord_goods_info where order_id=:orderId");
			query.setParameter("orderId", ordOrdersInfo.getOrderId());
			List consignorList = query.list();
			for (Object consignor_bill : consignorList) {
				if(consignor_bill!=null&&StringUtils.isNotEmpty(consignor_bill.toString())){
					SmsYQUtil.arriveSendConsignor(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderId(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, consignor_bill.toString())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, consignor_bill.toString(),ordOrdersInfo.getOrderNo());
				}
			}
		}
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		ordersInfoTF.updateOrderInfo(session,orderIdList,SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN);
		Map map = new HashMap();
		map.put("tips", "已成功到货");
		return map;
	}

	/***
	 * 配送操作
	 * orderId 多个以逗号分隔
	 */
	@Override
	public Map<String, Object> deliveryOrder(Map<String, Object> inParam)
			throws Exception {
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		String orderId = DataFormat.getStringKey(inParam, "orderId");
		if(StringUtils.isEmpty(orderId)){
			throw new BusinessException("请至少选择一条下单信息!");
		}
		List<Long> orderIdList = new ArrayList<Long>();
		String[] orderIdArr = orderId.split(",");
		for (String orderStr : orderIdArr) {
			orderIdList.add(Long.parseLong(orderStr));
		}
		for (Long orderIdL : orderIdList) {
			OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderIdL);
			if(ordOrdersInfo==null){
				throw new BusinessException("下单信息不存在！");
			}
			ordOrdersInfo.setOpDate(new Date());
			ordOrdersInfo.setOpId(user.getUserId());
			ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN);
			session.update(ordOrdersInfo);
			OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
			if(ordBusiPerson!=null){
				ordBusiPerson.setDeliveryId(user.getUserId());
				ordBusiPerson.setDeliveryName(user.getUserName());
				ordBusiPerson.setDeliveryBill(StringUtils.defaultString( user.getTelphone(),""));
				ordBusiPerson.setDeliveryTime(new Date());
				ordBusiPerson.setOpId(user.getUserId());
				session.update(ordBusiPerson);
			}
			// 写日志
			OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
			ordLogNewTF.deliveryOrderLog(user.getUserId(), user, ordOrdersInfo, SysTenantDefCacheDataUtil.getTenantName(user.getTenantId()), user.getUserName(), OraganizationCacheDataUtil.getOrgName(user.getOrgId()));
			//TODO 推送信息 （系统自动推送消息给到收货人，发货人）
			//TODO 推送信息 （系统自动推送消息给到收货人，发货人）
			if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
				SmsYQUtil.distributionSendConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(),ordOrdersInfo.getOrderNo(),user.getUserName(), user.getTelphone(),  ordOrdersInfo.getConsigneeBill(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE);
			}
		}
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		ordersInfoTF.updateOrderInfo(session,orderIdList,SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN);
		Map map = new HashMap();
		map.put("isOk", "Y");
		return map;
	}

	/****
	 * 订单详情
	 */
	@Override
	public OrdDetailOutParam ordDetails(Map<String, Object> inParam)
			throws Exception {
		Session session = SysContexts.getEntityManager(true);
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String orderNo = DataFormat.getStringKey(inParam, "orderNo");
		OrdDetailOutParam outParam = new OrdDetailOutParam();
		OrdOrdersInfo ordOrdersInfo = getByOrderIdOrOrderNo(orderId, orderNo, session);
		outParam.setOrderNo(ordOrdersInfo.getOrderNo());
		outParam.setOrderStateName(ordOrdersInfo.getOrderStateName());
		outParam.setCreateTime(DateUtil.formatDate(ordOrdersInfo.getCreateDate(), DateUtil.DATETIME_FORMAT));
		OrderInfo orderInfo = getByOrderInfoId(ordOrdersInfo.getOrderId(), session);
		if(orderInfo!=null){
			outParam.setCityName(orderInfo.getDesCityName());
			outParam.setTrackingNum(orderInfo.getOrderNumber());
			outParam.setInputTime(DateUtil.formatDate(orderInfo.getCreateTime(), DateUtil.DATETIME_FORMAT));
			outParam.setInputUserName(orderInfo.getCreateName());
			outParam.setTenantName(orderInfo.getCarrierName());
			outParam.setOrgIdName(OraganizationCacheDataUtil.getOrgName(orderInfo.getBillingOrgId()));
			outParam.setWorkerBill(orderInfo.getPullPhone());
			outParam.setWorkerName(orderInfo.getPullName());
			outParam.setServiceTypeName(orderInfo.getInterchangeName());
			outParam.setPaymentTypeName(orderInfo.getPaymentName());
			outParam.setConsigneeName(orderInfo.getConsignee());
			outParam.setConsigneeBill(orderInfo.getConsigneePhone());
			outParam.setAddress(orderInfo.getDesProvinceName()+orderInfo.getDesCityName()+StringUtils.defaultString(orderInfo.getDesDistrictName(), "")+StringUtils.defaultString(orderInfo.getConsigneeAddress(), ""));
			outParam.setProduct(orderInfo.getProductName());
			outParam.setCount(orderInfo.getNumber()==null?"0":orderInfo.getNumber().toString());
			outParam.setPackingName(orderInfo.getPackName());
			outParam.setWeight(orderInfo.getWeight());
			outParam.setVolume(orderInfo.getVolume());
			outParam.setRemark(orderInfo.getRemarks());
		}else{
			outParam.setCityName(ordOrdersInfo.getCityName());
			outParam.setTrackingNum(ordOrdersInfo.getTrackingNum());
			if(ordOrdersInfo.getTenantId()!=null){
				outParam.setTenantName(SysTenantDefCacheDataUtil.getTenantName(ordOrdersInfo.getTenantId()));
			}else{
				outParam.setTenantName(ordOrdersInfo.getCompanyName());
			}
			outParam.setServiceTypeName(ordOrdersInfo.getServiceTypeName());
			outParam.setPaymentTypeName(ordOrdersInfo.getPaymentTypeName());
			outParam.setConsigneeName(ordOrdersInfo.getConsigneeName());
			outParam.setConsigneeBill(ordOrdersInfo.getConsigneeBill());
			outParam.setAddress(ordOrdersInfo.getProvinceName()+ordOrdersInfo.getCityName()+StringUtils.defaultString(ordOrdersInfo.getCountyName(), "")+StringUtils.defaultString(ordOrdersInfo.getAddress(), ""));
			outParam.setProduct(ordOrdersInfo.getProducts());
			outParam.setCount(ordOrdersInfo.getOrderNum()==null?"0":ordOrdersInfo.getOrderNum().toString());
			outParam.setWeight(ordOrdersInfo.getWeight());
			outParam.setVolume(ordOrdersInfo.getVolume());
			outParam.setRemark(ordOrdersInfo.getRemark());
			if(ordOrdersInfo.getOrgId()!=null)
			outParam.setOrgIdName(OraganizationCacheDataUtil.getOrgName(ordOrdersInfo.getOrgId()));
			OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
			if(ordBusiPerson!=null){
				outParam.setInputTime(DateUtil.formatDate(ordBusiPerson.getInputTime(), DateUtil.DATETIME_FORMAT));
				outParam.setInputUserName(ordBusiPerson.getInputUserName());
				outParam.setWorkerBill(ordBusiPerson.getWorkerPhone());
				outParam.setWorkerName(ordBusiPerson.getWorkerName());
			}
		}
		return outParam;
	}
	
	
	
	/****
	 * 运单详情
	 */
	@Override
	public OrdDetailOutParam ordInfoDetails(Map<String, Object> inParam)throws Exception {
		Session session = SysContexts.getEntityManager(true);
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		//long trackingId =  DataFormat.getLongKey(inParam, "paramOrderId");
		String orderNo = DataFormat.getStringKey(inParam, "orderNo");
		String trackingNum = DataFormat.getStringKey(inParam, "trackingNum");
		OrdDetailOutParam outParam = new OrdDetailOutParam();
		OrdOrdersInfo ordOrdersInfo = getByOrderIdOrOrderNo(orderId, orderNo, session);//得到订单信息
		outParam.setOrderNo(ordOrdersInfo.getOrderNo());
		outParam.setOrderStateName(ordOrdersInfo.getOrderStateName());
		outParam.setCreateTime(DateUtil.formatDate(ordOrdersInfo.getCreateDate(), DateUtil.DATETIME_FORMAT));
		OrderInfo orderInfo = getOrderInfoByTrackNo(trackingNum, session);//得到运单信息
		if(orderInfo!=null){
			outParam.setTrackingStateName(SysStaticDataUtil.getSysStaticData("ORDER_INFO_STATE_OUT", orderInfo.getOrderStateOut()+"").getCodeName());
			outParam.setCityName(orderInfo.getDesCityName());
			outParam.setTrackingNum(orderInfo.getOrderNumber());
			outParam.setInputTime(DateUtil.formatDate(orderInfo.getCreateTime(), DateUtil.DATETIME_FORMAT));
			outParam.setInputUserName(orderInfo.getCreateName());
			outParam.setTenantName(orderInfo.getCarrierName());
			outParam.setOrgIdName(OraganizationCacheDataUtil.getOrgName(orderInfo.getBillingOrgId()));
			outParam.setWorkerBill(orderInfo.getPullPhone());
			outParam.setWorkerName(orderInfo.getPullName());
			outParam.setServiceTypeName(orderInfo.getInterchangeName());
			outParam.setPaymentTypeName(orderInfo.getPaymentName());
			outParam.setConsigneeName(orderInfo.getConsignee());
			outParam.setConsigneeBill(orderInfo.getConsigneePhone());
			outParam.setAddress(orderInfo.getDesProvinceName()+orderInfo.getDesCityName()+StringUtils.defaultString(orderInfo.getDesDistrictName(), "")+StringUtils.defaultString(orderInfo.getConsigneeAddress(), ""));
			outParam.setProduct(orderInfo.getProductName());
			outParam.setCount(orderInfo.getNumber()==null?"0":orderInfo.getNumber().toString());
			outParam.setPackingName(orderInfo.getPackName());
			outParam.setWeight(orderInfo.getWeight());
			outParam.setVolume(orderInfo.getVolume());
			outParam.setRemark(orderInfo.getRemarks());
		}else{
			outParam.setCityName(ordOrdersInfo.getCityName());
			outParam.setTrackingNum(ordOrdersInfo.getTrackingNum());
			if(ordOrdersInfo.getTenantId()!=null){
				outParam.setTenantName(SysTenantDefCacheDataUtil.getTenantName(ordOrdersInfo.getTenantId()));
			}else{
				outParam.setTenantName(ordOrdersInfo.getCompanyName());
			}
			outParam.setServiceTypeName(ordOrdersInfo.getServiceTypeName());
			outParam.setPaymentTypeName(ordOrdersInfo.getPaymentTypeName());
			outParam.setConsigneeName(ordOrdersInfo.getConsigneeName());
			outParam.setConsigneeBill(ordOrdersInfo.getConsigneeBill());
			outParam.setAddress(ordOrdersInfo.getProvinceName()+ordOrdersInfo.getCityName()+StringUtils.defaultString(ordOrdersInfo.getCountyName(), "")+StringUtils.defaultString(ordOrdersInfo.getAddress(), ""));
			outParam.setProduct(ordOrdersInfo.getProducts());
			outParam.setCount(ordOrdersInfo.getOrderNum()==null?"0":ordOrdersInfo.getOrderNum().toString());
			outParam.setWeight(ordOrdersInfo.getWeight());
			outParam.setVolume(ordOrdersInfo.getVolume());
			outParam.setRemark(ordOrdersInfo.getRemark());
			if(ordOrdersInfo.getOrgId()!=null)
			outParam.setOrgIdName(OraganizationCacheDataUtil.getOrgName(ordOrdersInfo.getOrgId()));
			OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
			if(ordBusiPerson!=null){
				outParam.setInputTime(DateUtil.formatDate(ordBusiPerson.getInputTime(), DateUtil.DATETIME_FORMAT));
				outParam.setInputUserName(ordBusiPerson.getInputUserName());
				outParam.setWorkerBill(ordBusiPerson.getWorkerPhone());
				outParam.setWorkerName(ordBusiPerson.getWorkerName());
			}
		}
		return outParam;
	}
	
	
	
	/****
	 * 根据主键或者订单交易编号查询开单信息
	 * @param orderId
	 * @param session
	 * @return
	 */
	private OrderInfo getByOrderInfoId(long orderId,Session session){
		Criteria ca = session.createCriteria(OrderInfo.class);
		ca.add(Restrictions.eq("ordsId", orderId));
		List<OrderInfo> list = ca.list();
		if(list.size()>0){
			return  list.get(0);
		}else{
			return null;
		}
		
	}
	/****
	 * 根据主键或者订单交易编号查询下单信息
	 * @param orderId
	 * @param orderNo
	 * @param session
	 * @return
	 */
	private OrdOrdersInfo getByOrderIdOrOrderNo(long orderId,String orderNo,Session session){
		if(orderId>0){
			OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
			if(ordOrdersInfo==null){
				throw new BusinessException("订单信息不存在!");
			}
			return ordOrdersInfo;
		}else{
			Criteria ca = session.createCriteria(OrdOrdersInfo.class);
			ca.add(Restrictions.eq("orderNo", orderNo));
			List<OrdOrdersInfo> list = ca.list();
			if(list.size()==0){
				throw new BusinessException("订单信息不存在!");
			}
			return list.get(0);
		}
	}
	
	/****
	 * 根据主键或者订单交易编号查询开单信息
	 * @param orderId
	 * @param orderNo
	 * @param session
	 * @return
	 */
	private OrderInfo getOrderInfoByTrackNo(String trackingNum,Session session){
	     
	        	Criteria ca = session.createCriteria(OrderInfo.class);
				ca.add(Restrictions.eq("orderNumber", trackingNum));
				List<OrderInfo> list = ca.list();
				if(list.size()==0){
					throw new BusinessException("运单信息不存在!");
				}
				return list.get(0);
	}

	/***
	 * 发货信息
	 */
	@Override
	public List<OrdGoodsInfoOutParam> queryGoodsInfo(Map<String, Object> inParam)
			throws Exception {
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String orderNo = DataFormat.getStringKey(inParam, "orderNo");
		List<OrdGoodsInfoOutParam> outList = new ArrayList<OrdGoodsInfoOutParam>();
		OrdOrdersInfo ordOrdersInfo=null;
		try {
			ordOrdersInfo = getByOrderIdOrOrderNo(orderId, orderNo, session);
			Criteria ca = session.createCriteria(OrdGoodsInfo.class);
			ca.add(Restrictions.eq("orderId", ordOrdersInfo.getOrderId()));
			List<OrdGoodsInfo> list = ca.list();
			for (OrdGoodsInfo ordGoodsInfo : list) {
				session.evict(ordGoodsInfo);
				OrdGoodsInfoOutParam outParam = new OrdGoodsInfoOutParam();
				BeanUtil.copyProperties(ordGoodsInfo,outParam );
				outParam.setAddress(ordGoodsInfo.getProvineName()+ordGoodsInfo.getCityName()+StringUtils.defaultString(ordGoodsInfo.getCountyName(), "")+StringUtils.defaultString(ordGoodsInfo.getAddress(), ""));
				outList.add(outParam);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outList;
	}

	/****
	 * 开单费用信息
	 */
	@Override
	public OrderFee queryFee(Map<String, Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager(true);
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String orderNo = DataFormat.getStringKey(inParam, "orderNo");
		try {
			OrdOrdersInfo ordOrdersInfo = getByOrderIdOrOrderNo(orderId, orderNo, session);
			OrderInfo orderInfo = getByOrderInfoId(ordOrdersInfo.getOrderId(), session);
			if(orderInfo!=null){
				OrderFee orderFee = null;
				List<OrderFee> orderFeeList = session.createCriteria(OrderFee.class).add(Restrictions.eq("orderId", orderInfo.getOrderId())).list();
				if (orderFeeList!=null&&orderFeeList.size()>0) {
					orderFee = orderFeeList.get(0);
				}
				return orderFee;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/****
	 * 签收信息
	 */
	@Override
	public OrdSignInfo querySignInfo(Map<String, Object> inParam)
			throws Exception {
		Session session = SysContexts.getEntityManager(true);
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String orderNo = DataFormat.getStringKey(inParam, "orderNo");
		OrdSignInfo ordSignInfo=null;
		try {
			OrdOrdersInfo ordOrdersInfo = getByOrderIdOrOrderNo(orderId, orderNo, session);
			ordSignInfo = (OrdSignInfo) session.get(OrdSignInfo.class, ordOrdersInfo.getOrderId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if(ordSignInfo!=null){
			if(StringUtils.isNotEmpty(ordSignInfo.getCertificateNo()))
			{
				ordSignInfo.setCertificateTypeString(SysStaticDataUtil.getSysStaticDataCodeName("CERTIFICATE_TYPE", ordSignInfo.getCertificateType()+""));
			}
			return ordSignInfo;
		}else{
			return null;
		}
	}

	/****
	 * 跟踪信息
	 */
	@Override
	public List<OrdOpLogListOut> queryLogByRole(Map<String, Object> inParam)
			throws Exception {
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String orderNo = DataFormat.getStringKey(inParam, "orderNo");
		OrdOrdersInfo ordOrdersInfo = getByOrderIdOrOrderNo(orderId, orderNo, session);
		Criteria ca = session.createCriteria(OrdOpLog.class);
		ca.add(Restrictions.eq("inType", SysStaticDataEnum.OP_TYPE.IN_TYPE_YES));
		ca.add(Restrictions.eq("orderId", ordOrdersInfo.getOrderId()));
		ca.addOrder(Order.desc("id"));
		//内部查询
		List<OrdOpLog> lists = ca.list();
		List<OrdOpLogListOut> listOut = new ArrayList<OrdOpLogListOut>();
		for(OrdOpLog log : lists){
			OrdOpLogListOut out = new OrdOpLogListOut();
			BeanUtils.copyProperties(out, log);
			//内部查询
			out.setOpContent(log.getInContent());
			listOut.add(out);
		}
		return listOut;
	}

	@Override
	public Map<String, Object> signUp(Map<String, Object> inParam)
			throws Exception {
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if(orderId<0){
			throw new BusinessException("订单编号不能为空!");
		}
		String signDesc = DataFormat.getStringKey(inParam, "signDesc");
		String signName = DataFormat.getStringKey(inParam, "signName");
		String phone = DataFormat.getStringKey(inParam, "phone");
		String flowId = DataFormat.getStringKey(inParam, "flowId");
		String certificateNo= DataFormat.getStringKey(inParam, "certificateNo");
		String certificateType =DataFormat.getStringKey(inParam, "certificateType"); 
		if(StringUtils.isEmpty(signName)){
			throw new BusinessException("签收人姓名不能为空！");
		}
		if(StringUtils.isEmpty(phone)){
			throw new BusinessException("签收人手机不能为空！");
		}
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("下单信息不存在！");
		}
		if(ordOrdersInfo.getOrderState().intValue()!=SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN){
			throw new BusinessException("订单状态为【"+ordOrdersInfo.getOrderStateName()+"】,不允许签收!");
		}
		if(StringUtils.isNotEmpty(flowId)){
			String[] flowIdArr = flowId.split(",");
			for(int i=0;i<flowIdArr.length;i++){
				if(flowIdArr[0].length()>20){
					throw new BusinessException("上传图片数据错误!"+flowId);
				}
			}
		}
		Date d = new Date();
		ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN);
		ordOrdersInfo.setOpDate(d);
		ordOrdersInfo.setOpId(user.getUserId());
		session.update(ordOrdersInfo);
		OrdSignInfo signInfo = new OrdSignInfo();
		signInfo.setCreateId(user.getUserId());
		signInfo.setTrackingNum(ordOrdersInfo.getTrackingNum());
		signInfo.setOrderNo(ordOrdersInfo.getOrderNo());
		signInfo.setExt1(phone);
		signInfo.setOrderId(orderId);
		signInfo.setSignDate(d);
		signInfo.setSignName(signName);
		signInfo.setFlowId(flowId);
		signInfo.setSignDesc(signDesc);
		signInfo.setSource(2);
		if(StringUtils.isNotEmpty(certificateNo)){
			signInfo.setCertificateNo(certificateNo);
		}
		if(StringUtils.isNotEmpty(certificateType)){
			signInfo.setCertificateType(Integer.valueOf(certificateType));
		}
		session.save(signInfo);
		List<Long> list = new ArrayList<Long>();
		list.add(ordOrdersInfo.getOrderId());
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		ordersInfoTF.updateOrderInfo(session,list,SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN);
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.signUpOrderLog(user.getUserId(), user, ordOrdersInfo, ordOrdersInfo.getCompanyName(), OraganizationCacheDataUtil.getOrgName(ordOrdersInfo.getOrgId()==null?0:ordOrdersInfo.getOrgId()), user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS?1:0);
		//TODO 推送
		/****
		 * 1﹜	配送员登陆app，点击签收，跳转到签收页面，通过扫描运单条形码，或输入收货人号码，运单号进行查找到签收的订单，然后点击签收，上传签收图片及备注，提交完成签收操作（系统自动推送消息给到收货人，发货人）
			2﹜	收货人进行签收，收货人登陆app，找到订单后点击签收，提示签收成功（系统会自动推送消息给到发货人和专线物流公司）。
			3﹜	发货人进行签收，发货人登陆app，找到订单后点击签收，提示签收成功（系统自动推送消息给到收货人和专线公司）。
		 */
		Query query = session.createSQLQuery("select consignor_bill from ord_goods_info where order_id=:orderId");
		query.setParameter("orderId", ordOrdersInfo.getOrderId());
		List consignorList = query.list();
		for (Object consignor_bill : consignorList) {
			if(consignor_bill!=null&&StringUtils.isNotEmpty(consignor_bill.toString())){
				SmsYQUtil.signSendConsignor(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderId(),signInfo.getSignName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, consignor_bill.toString())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, consignor_bill.toString(),ordOrdersInfo.getOrderNo());
			}
		}
		if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
			SmsYQUtil.signSendConsignor(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderId(),signInfo.getSignName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getConsigneeBill(),ordOrdersInfo.getOrderNo());
		}
		Map map = new HashMap();
		map.put("tips", "货物已签收");
		return map;
	}
	/**
	 * 分享订单
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getOrdersByOrderNo(Map<String,Object> inParam) throws Exception{
		String orderNo = DataFormat.getStringKey(inParam, "orderNo");
		if(StringUtils.isEmpty(orderNo)){
			throw new BusinessException("分享失败，没有订单号！");
		}
		Session session = SysContexts.getEntityManager(true);  
		Query query =  session.createSQLQuery("SELECT o.order_no,o.`TRACKING_NUM`,f.total_fee,o.city_id,o.`COMPANY_NAME`,o.consignee_name,o.consignee_bill,o.PAYMENT_type,o.order_State "
				+ "FROM ord_orders_info o LEFT JOIN order_info t ON o.order_id = t.ords_id "
				+ " LEFT JOIN  order_fee   f  ON  t.order_id = f.order_id"
				+ "  WHERE  o.order_no = :orderNo ");
		query.setParameter("orderNo", orderNo);
		List<Object[]> list = query.list();
		Map<String,Object> mapOut = new HashMap<String, Object>();
		if (list != null && list.size()>0) {
			for (Object[] objects : list) {
				mapOut.put("orderNo", objects[0].toString());
				mapOut.put("trackingNum", objects[1].toString());
				mapOut.put("totalFee", objects[2] != null ? CommonUtil.parseDouble(Long.valueOf(objects[2].toString())) : "");
				mapOut.put("desCityName",objects[3] != null ?SysStaticDataUtil.getCityDataList("SYS_CITY", objects[3].toString()).getName():"");
				mapOut.put("carrierName", objects[4] != null ? objects[4].toString() : "");
				mapOut.put("consignee", objects[5] != null ? objects[5].toString() : "");
				mapOut.put("consigneePhone", objects[6] != null ? objects[6].toString() : "");
				mapOut.put("paymentType", objects[7] != null ? SysStaticDataUtil.getSysStaticData("PAYMENT_TYPE_YQ", objects[7].toString()).getCodeName() : "");
				mapOut.put("orderState", objects[8] != null ? SysStaticDataUtil.getSysStaticDataCodeName("ORDERS_STATE", objects[8].toString()) : "");
			}
		}
		return mapOut;
	}
	
	/****
	 * 运单管理列表查询
	 * @param inParam
	 * */
	@SuppressWarnings("unchecked")
	public Map<String,Object>   queryTracks(Map<String,Object> inParam) throws Exception{
		OrdOrdersInfoSV orderInfoSV = (OrdOrdersInfoSV) SysContexts.getBean("ordOrdersInfoSV");
		IPage p = PageUtil.gridPage(orderInfoSV.queryTracksInfo(inParam,false));
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		if(list==null || list.size()<=0){
			return rtnMap;
		}
		List<TrackListOutParam> outList = new ArrayList<TrackListOutParam>();
		for (Object[] obj : list) {
			TrackListOutParam outParam = new TrackListOutParam();
			BigInteger orderId=(BigInteger) obj[0];
			outParam.setOrderId(orderId.longValue());
			String trackingNumOut=(String) obj[1];
			outParam.setTrackingNum(trackingNumOut);
			Integer orderState=(Integer) obj[2];
			outParam.setTrackingStateName(SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE_OUT", String.valueOf(orderState)));
			String  orderNo = (String)obj[3];
			outParam.setOrderNo(orderNo);
			//orgName
			BigInteger orgName =(BigInteger)obj[4];
			outParam.setOrgName(OraganizationCacheDataUtil.getOrgName(orgName.longValue()));
			BigInteger tenantId = (BigInteger)obj[5];
			String companyName = (String)obj[37];
			if(tenantId!=null){
				outParam.setCompanyName(SysTenantDefCacheDataUtil.getTenantName(tenantId.longValue()));
				outParam.setTenantId(SysTenantDefCacheDataUtil.getTenantName(tenantId.longValue()));
			}else{
				outParam.setCompanyName(companyName);
				outParam.setTenantId(companyName);
			}
			String province = (String)obj[6];
			String city =(String)obj[7];
			String County =(String)obj[8];
			if(County!=null){
				outParam.setCityName(province+city+County);
			}else{
				outParam.setCityName(province+city);
			}
			//拉包工
			String workerNameOut=(String) obj[9];
			outParam.setWorkerName(workerNameOut);
			String workerBillOut=(String) obj[10];
			outParam.setWorkerBill(workerBillOut);
			//发货人
			String consignorNameOut=(String) obj[11];
			outParam.setConsignorName(consignorNameOut);
			String consignorBillOut=(String) obj[12];
			outParam.setConsignorBill(consignorBillOut);
			String consignorAddressOut=(String) obj[13];
			outParam.setPickAddress(consignorAddressOut);
			//收货人
			String consigneeNameOut=(String) obj[14];
			outParam.setConsigneeName(consigneeNameOut);
			String consigneeBillOut=(String) obj[15];
			outParam.setConsigneeBill(consigneeBillOut);
			String receiverAddress=(String) obj[16];
			outParam.setReceiverAddress(receiverAddress);
			Integer serviceType=(Integer) obj[17];
			if(serviceType!=null){
				outParam.setServiceTypeName(SysStaticDataUtil.getSysStaticDataCodeName("SERVICE_TYPE", String.valueOf(serviceType)));
			    outParam.setDeliverTypeName(SysStaticDataUtil.getSysStaticDataCodeName("SERVICE_TYPE", String.valueOf(serviceType)));
			}
			//付费方式
			Integer paymentType=(Integer) obj[18];
			if(paymentType!=null){
				outParam.setPaymentTypeName(SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE_YQ", String.valueOf(paymentType)));
			}
			//品名 件数 包装  
			String productName=(String) obj[19];
			outParam.setProductName(productName);
			Integer count=(Integer) obj[20];
			if(count!=null){
			outParam.setCount(count+"");
			}
			String packingName=(String) obj[21];
			outParam.setPackingName(packingName);
			//重量 体积
			String weight=(String) obj[22];
			if(StringUtils.isNotEmpty(weight)){
				outParam.setWeight(weight);
			}
			String volume=(String) obj[23];
//			if(StringUtils.isNotEmpty(volume)){
//				outParam.setVolume(volume);
//			}
			outParam.setVolume(volume != null ? volume : "");//重量
			
			//运费 声价 保费 送货费 中转费  小费  货款  落地费 服务费  其他费用 总运费 
			//String createName=(String) obj[34];
			//outParam.setInputUserName(createName);
			
			
			BigInteger freight=(BigInteger) obj[24];
			if(freight!=null){
				outParam.setFreight(com.wo56.common.utils.CommonUtil.parseDouble(freight.longValue()));
			}
			BigInteger reputation=(BigInteger) obj[25];
			if(reputation!=null){
				outParam.setReputation(com.wo56.common.utils.CommonUtil.parseDouble(reputation.longValue()));
			}
			BigInteger premium=(BigInteger) obj[26];
			if(premium!=null){
				outParam.setPremium(com.wo56.common.utils.CommonUtil.parseDouble(premium.longValue()));
			}
			BigInteger delivery_charge=(BigInteger) obj[27];
			if(delivery_charge!=null){
				outParam.setDeliveryCharge(com.wo56.common.utils.CommonUtil.parseDouble(delivery_charge.longValue()));
			}
			BigInteger transit_fee=(BigInteger) obj[28];
			if(transit_fee!=null){
				outParam.setTransitFee(com.wo56.common.utils.CommonUtil.parseDouble(transit_fee.longValue()));
			}
			
			//小费
			BigInteger tipsMoney=(BigInteger) obj[29];
			if(tipsMoney!=null){
				outParam.setTipsMonery(com.wo56.common.utils.CommonUtil.parseDouble(tipsMoney.longValue()));
			}
			BigInteger collect_money=(BigInteger) obj[30];
			if(collect_money!=null){
				outParam.setCollectMoney(com.wo56.common.utils.CommonUtil.parseDouble(collect_money.longValue()));
			}
			BigInteger land_fee=(BigInteger) obj[31];
			if(land_fee!=null){
				outParam.setLandFee(com.wo56.common.utils.CommonUtil.parseDouble(land_fee.longValue()));
			}
			
			BigInteger service_charge=(BigInteger) obj[32];
			if(service_charge!=null){
				outParam.setServiceCharge(com.wo56.common.utils.CommonUtil.parseDouble(service_charge.longValue()));
			}
			BigInteger other_fee=(BigInteger) obj[33];
			if(other_fee!=null){
				outParam.setOtherFee(com.wo56.common.utils.CommonUtil.parseDouble(other_fee.longValue()));
			}
			BigInteger total_fee=(BigInteger) obj[34];
			if(total_fee!=null){
				outParam.setTotalFee(com.wo56.common.utils.CommonUtil.parseDouble(total_fee.longValue()));
			}
			outParam.setRemark(obj[35] != null ? obj[35].toString() : "");//备注
			//开单人
			String inputUserNameOut=(String) obj[36];
			outParam.setInputUserName(inputUserNameOut);
			outParam.setSignName(obj[38] != null ? obj[38].toString() : "");
			Date signTime=(Date) obj[39];
			outParam.setSignTime(DateUtil.formatDate(signTime,DateUtil.DATETIME_FORMAT1));
			Date inputTime=(Date) obj[40];
			outParam.setInputTime(DateUtil.formatDate(inputTime,DateUtil.DATETIME_FORMAT1));
			//取消时间
			Date opTime=(Date) obj[41];
			outParam.setOpTime(DateUtil.formatDate(inputTime,DateUtil.DATETIME_FORMAT1));
			outParam.setOpName(obj[42] != null ? obj[42].toString() : "");
			BigInteger pick_fee=(BigInteger) obj[43];
			if(pick_fee!=null){
				outParam.setPickFee(com.wo56.common.utils.CommonUtil.parseDouble(pick_fee.longValue()));

			}
			outList.add(outParam);
		}
		p.setThisPageElements(outList);
		Pagination page = new Pagination(p);
		
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		String _sum = DataFormat.getStringKey(inParam, "_sum");
		if("1".equals(_sum)){
			OrdListSumOutParam sumOrderInfo= queryOrderInfoSum(inParam);
			rtnMap.put("sumData", sumOrderInfo);
		}
		return rtnMap;
	}
	// 运单统计列表
	private OrdListSumOutParam queryOrderInfoSum(Map<String, Object> inParam) {
		
		OrdOrdersInfoSV orderInfoSV = (OrdOrdersInfoSV) SysContexts.getBean("ordOrdersInfoSV");
		IPage p = PageUtil.gridPage(orderInfoSV.queryTracksInfo(inParam,true));
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		
		OrdListSumOutParam out =new  OrdListSumOutParam();
		for (Object[] obj : list) {
			BigDecimal number= (BigDecimal)obj[0];
			if(number!=null){
			out.setCount(number.toString());
			}
			double  weight= (double )obj[1];
			if(weight!=0){	
			out.setWeight(String.valueOf(weight)!=null?weight+"":"");
			}
			double   volume= (double  )obj[2];
			if(volume!=0)
			out.setVolume(String.valueOf(volume)!=null?volume+"":"");
			BigDecimal freight= (BigDecimal)obj[3];
			if(freight!=null)
			out.setFreight(CommonUtil.parseDouble(freight.longValue()));
			BigDecimal reputation= (BigDecimal)obj[4];
			if(reputation!=null)
			out.setReputation(CommonUtil.parseDouble(reputation.longValue()));
			BigDecimal premium= (BigDecimal)obj[5];
			if(premium!=null)
			out.setPremium(CommonUtil.parseDouble(premium.longValue()));
			BigDecimal deliveryCharge= (BigDecimal)obj[6];
			if(deliveryCharge!=null)
			out.setDeliveryCharge(CommonUtil.parseDouble(deliveryCharge.longValue()));
			BigDecimal transitFee= (BigDecimal)obj[7];
			if(transitFee!=null)
			out.setTransitFee(CommonUtil.parseDouble(transitFee.longValue()));
			BigDecimal otherFee= (BigDecimal)obj[8];
			if(otherFee!=null)
			out.setOtherFee(CommonUtil.parseDouble(otherFee.longValue()));
			BigDecimal collectMoney= (BigDecimal)obj[9];
			if(collectMoney!=null)
			out.setCollectMoney(CommonUtil.parseDouble(collectMoney.longValue()));
			BigDecimal landFee= (BigDecimal)obj[10];
			if(landFee!=null)
			out.setLandFee(CommonUtil.parseDouble(landFee.longValue()));
			BigDecimal serviceCharge= (BigDecimal)obj[11];
			if(serviceCharge!=null)
			out.setServiceCharge(CommonUtil.parseDouble(serviceCharge.longValue()));
			BigDecimal totalFee= (BigDecimal)obj[12];
			if(totalFee!=null)
			out.setTotalFee(CommonUtil.parseDouble(totalFee.longValue()));
			BigDecimal tipsMoney= (BigDecimal)obj[13];
			if(tipsMoney!=null)
			out.setTipsMonery(CommonUtil.parseDouble(tipsMoney.longValue()));
			BigDecimal pickFee= (BigDecimal)obj[14];
			if(pickFee != null){
				out.setPickFee(CommonUtil.parseDouble(pickFee.longValue()));	
			}	    
		}
		return out;
	}


	/**
	 * 统计
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> queryTracksSum(Map<String,Object> inParam){	
		OrdOrdersInfoSV orderInfoSV = (OrdOrdersInfoSV) SysContexts.getBean("ordOrdersInfoSV");
		List<Object[]> list = orderInfoSV.queryTracksInfoSum(inParam).list();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("doSignTracks", 0);
		map.put("inTransitTracks", 0);
		map.put("waitDeliveryTracks", 0);
		map.put("waitDepartTracks", 0);
		map.put("waitSignTracks", 0);
		map.put("cancelTracks", 0);
		Map<String,Integer> mapAll = new HashMap<String, Integer>();
		mapAll.put("doSignTracks",  0);
		mapAll.put("inTransitTracks",  0);
		mapAll.put("floationCargoTracks",  0);
		mapAll.put("waitDeliveryTracks",  0);
		mapAll.put("stowagePlanTracks",  0);
		mapAll.put("waitDepartTracks",  0);
		mapAll.put("cancelTracks", 0);
		mapAll.put("doCancelTracks", 0);
		mapAll.put("partialSignTracks",  0);
		mapAll.put("waitSignTracks", 0);
		int allTotal=0;
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				Integer total = objects[0] != null ? Integer.valueOf(objects[0].toString()) : 0;
				Integer state = objects[1] != null ? Integer.valueOf(objects[1].toString()) : 0;
				//allTotal = total + allTotal;
				switch (state) {
				case SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.DO_SIGN:
					mapAll.put("doSignTracks", total);
					break;
				case SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.IN_TRANSIT:
					mapAll.put("inTransitTracks", total);
					break;
				case SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PENDING_DELIVERY:
					mapAll.put("waitDeliveryTracks", total);
					break;
				case SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PENDING_DEPARTURE:
					mapAll.put("waitDepartTracks", total);
					break;
				case SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.WAIT_SIGN:
					mapAll.put("waitSignTracks", total);
					break;
				case SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.CANCERING:	
					mapAll.put("cancelTracks", total);
					break;
				case SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.DO_CANCER:	
					mapAll.put("doCancelTracks", total);
					break;
				case SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PARTIAL_SIGN:
					mapAll.put("partialSignTracks", total);
					break;	
				default:
					break;
				}
			}
		}
		map.put("doSignTracks",mapAll.get("doSignTracks") );//签收
		map.put("inTransitTracks", mapAll.get("inTransitTracks")+ mapAll.get("floationCargoTracks"));//运输中
		map.put("waitDeliveryTracks", mapAll.get("waitDeliveryTracks"));//待配送
		map.put("waitDepartTracks", mapAll.get("waitDepartTracks")+ mapAll.get("stowagePlanTracks"));
		map.put("waitSignTracks", mapAll.get("partialSignTracks")+mapAll.get("waitSignTracks"));//待签收 
		map.put("cancelTracks", mapAll.get("doCancelTracks")+ mapAll.get("cancelTracks"));
		allTotal = mapAll.get("doSignTracks")+mapAll.get("inTransitTracks")+mapAll.get("floationCargoTracks")
				+mapAll.get("waitDeliveryTracks")+mapAll.get("waitDepartTracks")+mapAll.get("stowagePlanTracks")
				+mapAll.get("partialSignTracks")+mapAll.get("waitSignTracks");
		map.put("trackManage", allTotal);
		return map;
	}
	
	
	
	/****
	 * 运单管理子运单列表查询
	 * @param inParam
	 * */
	@SuppressWarnings("unchecked")
	public Map<String,Object>   querychildOrderInfo(Map<String,Object> inParam) throws Exception{
		OrdOrdersInfoSV orderInfoSV = (OrdOrdersInfoSV) SysContexts.getBean("ordOrdersInfoSV");
		Query query = orderInfoSV.querychildOrderInfo(inParam);
		List<Object[]> list = query.list();
		List<ChildOrderInfoOut>listOut = new ArrayList<ChildOrderInfoOut>();
		Map<String,Object> map = new HashMap<String, Object>();
		for (Object[] obj : list) {
			ChildOrderInfoOut outParam = new ChildOrderInfoOut();
			String childOrderName = (String)obj[0];
			outParam.setChildOrderName(childOrderName);
			Integer orderState=(Integer) obj[1];
			outParam.setChildOrderState(SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE", String.valueOf(orderState)));
			BigInteger orgId=(BigInteger) obj[2];
			outParam.setStockOrgName(OraganizationCacheDataUtil.getOrgName(Long.valueOf(orgId+"")));
			outParam.setTrackInfo("");
			listOut.add(outParam);
		}	
		map.put("childTrack", listOut);
		return map;
	}
	
	/****
	 * 运单管理配载信息列表查询
	 * @param inParam
	 * */
	@SuppressWarnings("unchecked")
	public Map<String,Object>   queryDepart(Map<String,Object> inParam) throws Exception{
		OrdOrdersInfoSV orderInfoSV = (OrdOrdersInfoSV) SysContexts.getBean("ordOrdersInfoSV");
		Query query = orderInfoSV.queryDepartInfo(inParam);
		List<Object[]> list = query.list();
		List<DepartOrderInfoOut>listOut = new ArrayList<DepartOrderInfoOut>();
		Map<String,Object> map = new HashMap<String, Object>();
		//发车批次， 合同编号， 车牌号，司机名称， 司机电话 ，发车网点， 到车网点， 运单数量， 配载重量， 发车确认人，配载时间，发车时间， 到车时间， 到货确认人，运输状态， 备注 
		for (Object[] obj : list) {
			DepartOrderInfoOut outParam = new DepartOrderInfoOut();
			String batchNum = (String)obj[0];
			outParam.setBatchNum(String.valueOf(batchNum)!=null? String.valueOf(batchNum):"");
			String transportContract = (String)obj[1];
			outParam.setTransportContract(transportContract!=null?transportContract:"");
			String plateNumber = (String)obj[2];
			outParam.setPlateNumber(plateNumber!=null?plateNumber:"");
			String driverName = (String)obj[3];
			outParam.setDriverName(driverName!=null?driverName:"");
			String driverBill = (String)obj[4];
			outParam.setDriverBill(driverBill!=null?driverBill:"");
			BigInteger sourceOrgId = (BigInteger)obj[5];
			outParam.setSourceOrgId(OraganizationCacheDataUtil.getOrgName(Long.valueOf(sourceOrgId+"")));//需要翻译
			BigInteger descOrgId = (BigInteger)obj[6];
			outParam.setDescOrgId(OraganizationCacheDataUtil.getOrgName(Long.valueOf(descOrgId+"")));
			Integer number=(Integer) obj[7];
			outParam.setNumber(String.valueOf(number)!=null?String.valueOf(number):"");
			double departVolume = (double)obj[9];
			outParam.setDepartVolume(String.valueOf(departVolume)!=null?departVolume+"":"");
			double departWeigth = (double)obj[8];
			outParam.setDepartWeigth(String.valueOf(departWeigth)!=null?departWeigth+"":"");
			String departOpName = (String)obj[10];
			outParam.setDepartOpName(departOpName!=null?departOpName:"");
			Date loadTime=(Date) obj[11];
			outParam.setLoadTime(DateUtil.formatDate(loadTime,DateUtil.DATETIME_FORMAT1));
			Date departTime=(Date) obj[12];
			outParam.setDepartTime(DateUtil.formatDate(departTime,DateUtil.DATETIME_FORMAT1));
			Date arrivalTime=(Date) obj[13];
			outParam.setArrivalTime(DateUtil.formatDate(arrivalTime,DateUtil.DATETIME_FORMAT1));
			String arrivalOpName = (String)obj[14];
			outParam.setArrivalOpName(arrivalOpName!=null?arrivalOpName:"");
			Integer state=(Integer) obj[15];
			outParam.setState(SysStaticDataUtil.getSysStaticDataCodeName("VEHICLE_STATE_YQ", String.valueOf(state)));
			String remark = (String)obj[16];
			outParam.setRemark(remark!=null?remark:"");
			listOut.add(outParam);
		}	
		map.put("departInfo", listOut);
		return map;
	}
	
	/****
	 * 待发车列表查询
	 * @param inParam
	 * */
	@SuppressWarnings("unchecked")
	public Map<String,Object>   queryWaitDeparts(Map<String,Object> inParam) throws Exception{
		String timeOut = DataFormat.getStringKey(inParam, "timeOut");
		OrdOrdersInfoSV orderInfoSV = (OrdOrdersInfoSV) SysContexts.getBean("ordOrdersInfoSV");
		IPage p = PageUtil.gridPage(orderInfoSV.queryWaitDepartsInfo(inParam,false));
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		List<OrdOrdersListOutParam> outList = new ArrayList<OrdOrdersListOutParam>();
		for (Object[] obj : list) {
			OrdOrdersListOutParam outParam = new OrdOrdersListOutParam();
			String orderNoOut=(String) obj[0];
			outParam.setOrderNo(orderNoOut);
			BigInteger orderId=(BigInteger) obj[1];
			outParam.setOrderId(orderId.longValue());
			String trackingNumOut=(String) obj[2];
			outParam.setTrackingNum(trackingNumOut);
			BigInteger provinceId=(BigInteger) obj[3];
			BigInteger cityId=(BigInteger) obj[4];
			outParam.setCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", cityId+"").getName());
			BigInteger countyId=(BigInteger) obj[5];
			String receiveAdress=(String) obj[6];
			String address=SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", provinceId+"").getName();
			if(cityId!=null)
			address=address+SysStaticDataUtil.getCityDataList("SYS_CITY", cityId+"").getName();
			if(countyId!=null)
			address=address+SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", countyId+"")!=null?SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", countyId+"").getName():"";
			if(receiveAdress!=null){
				outParam.setReceiverAddress(address+receiveAdress);
			}else{
				outParam.setReceiverAddress(address);
			}
			//outParam.setReceiverAddress(address+receiveAdress);
			Integer serviceType=(Integer) obj[7];
			if(serviceType!=null){
				outParam.setServiceTypeName(SysStaticDataUtil.getSysStaticDataCodeName("SERVICE_TYPE", serviceType+""));
			}
			Integer paymentType=(Integer) obj[8];
			if(paymentType!=null){
				outParam.setPaymentTypeName(SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE_YQ", paymentType+""));
			}
			String consigneeNameOut=(String) obj[9];
			outParam.setConsigneeName(consigneeNameOut);
			String consigneeBillOut=(String) obj[10];
			outParam.setConsigneeBill(consigneeBillOut);
			Date createDate=(Date) obj[11];
			outParam.setCreateTime(DateUtil.formatDate(createDate,DateUtil.DATETIME_FORMAT1));
			String disOpName=(String) obj[12];
			outParam.setDisOpName(disOpName);
			Date disTime=(Date) obj[13];
			if(disTime!=null){
				outParam.setDisTime(DateUtil.formatDate(disTime,DateUtil.DATETIME_FORMAT1));
			}
			Date signDate=(Date) obj[14];
			if(signDate!=null){
				outParam.setSignTime(DateUtil.formatDate(signDate,DateUtil.DATETIME_FORMAT1));
			}
			String consignorNameOut=(String) obj[15];
			outParam.setConsignorName(consignorNameOut);
			String consignorBillOut=(String) obj[16];
			outParam.setConsignorBill(consignorBillOut);
			BigInteger pickProvinceId=(BigInteger) obj[17];
			BigInteger pickCityId=(BigInteger) obj[18];
			BigInteger pickCountyId=(BigInteger) obj[19];
			String pickAdress=(String) obj[20];
			String pickStation="";
			pickStation=pickStation+SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", pickProvinceId+"").getName();
			if(pickCityId!=null){
				pickStation=pickStation+SysStaticDataUtil.getCityDataList("SYS_CITY", pickCityId+"").getName();
			}
			if(pickCountyId!=null){
				pickStation=pickStation+(SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", pickCountyId+"")!=null?SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", pickCountyId+"").getName():"");
			}
			//outParam.setPickProCityCounty(pickStation);
			outParam.setPickStation(pickStation);
			outParam.setPickAddress(pickAdress);
			BigInteger idNo=(BigInteger) obj[21];
			outParam.setIdNo(idNo.longValue());
			Date planPickupTime=(Date) obj[22];
			if(planPickupTime!=null){
				outParam.setPlanPickupTime(DateUtil.formatDate(planPickupTime,DateUtil.DATETIME_FORMAT1));
			}
			Date pickupTime=(Date) obj[23];
			if(pickupTime!=null){
				outParam.setPickupTime(DateUtil.formatDate(pickupTime,DateUtil.DATETIME_FORMAT1));
			}
			String workerBillOut=(String) obj[24];
			outParam.setWorkerBill(workerBillOut);
			String workerNameOut=(String) obj[25];
			outParam.setWorkerName(workerNameOut);
			String deliveryBill=(String) obj[26];
			String inputUserNameOut=(String) obj[27];
			outParam.setInputUserName(inputUserNameOut);
			Date inputTime=(Date) obj[28];
			if(inputTime!=null){
				outParam.setInputTime(DateUtil.formatDate(inputTime,DateUtil.DATETIME_FORMAT1));
			}
			Date deliveryTime=(Date) obj[29];
			if(deliveryTime!=null){
				outParam.setDeliveryTime(DateUtil.formatDate(deliveryTime,DateUtil.DATETIME_FORMAT1));
			}
			Date gxEndTime=(Date) obj[30];
			if(gxEndTime!=null){
				outParam.setGxEndTime(DateUtil.formatDate(gxEndTime,DateUtil.DATETIME_FORMAT1));
			}
			outParam.setRemark(obj[31] != null ? obj[31].toString() : "");
			
			//增加专线名称  通过id转化名称
//			long tenantId =-1;
//			tenantId=obj[32]==null? -1:Long.valueOf(obj[32].toString());
//			String tenantName = CommonUtil.getTennatNameById(tenantId);
//			if(tenantName!=null){
//				outParam.setTenantName(tenantName);
//			}else {
//				outParam.setTenantName("");
//			}
			
			//增加下单时间
			Date doOrderTime=(Date)obj[33];
			if(doOrderTime!=null){
				outParam.setDoOrderTime(DateUtil.formatDate(doOrderTime,DateUtil.DATETIME_FORMAT1));
			}
			//outParam.setSignTime(obj[34]!=null? DateUtil.formatDate((Date)obj[34],DateUtil.DATETIME_FORMAT1) : null);
			
			outParam.setTenantName(obj[34] != null ? obj[34].toString() : "");
			if(StringUtils.isNotEmpty(timeOut) && "true".equals(timeOut)){
				outParam.setTimeOutString(CommonUtil.formatTime(obj[35]!=null ? Long.valueOf(obj[35].toString()) : 0));
				
			}	
			outList.add(outParam);
		}
		p.setThisPageElements(outList);
		Pagination<Object[]> page = new Pagination<Object[]>(p);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		return rtnMap;	
	 }
		
	
}

