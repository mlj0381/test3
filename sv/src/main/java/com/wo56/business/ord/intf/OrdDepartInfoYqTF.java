package com.wo56.business.ord.intf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.cm.intf.CmVehicleInfoTF;
import com.wo56.business.cm.vo.CmDriverInfo;
import com.wo56.business.cm.vo.CmVehicleInfo;
import com.wo56.business.ord.impl.OrdDepartInfoYqSV;
import com.wo56.business.ord.vo.OrdBusiPerson;
import com.wo56.business.ord.vo.OrdDepartDetail;
import com.wo56.business.ord.vo.OrdDepartDetailId;
import com.wo56.business.ord.vo.OrdDepartInfo;
import com.wo56.business.ord.vo.OrdOrdersInfo;
import com.wo56.business.ord.vo.OrderFee;
import com.wo56.business.ord.vo.OrderInfo;
import com.wo56.business.ord.vo.OrderInfoChild;
import com.wo56.business.ord.vo.OrderInfoTmsError;
import com.wo56.business.ord.vo.in.OrdDepartInfoIn;
import com.wo56.business.ord.vo.out.OrdDepartInfoOutDelYq;
import com.wo56.business.ord.vo.out.OrdDepartInfoOutWeb;
import com.wo56.business.ord.vo.out.OrdDepartInfoOutYq;
import com.wo56.business.ord.vo.out.OrdDepartQueryOutYQ;
import com.wo56.business.ord.vo.out.OrderDepartChildOutWeb;
import com.wo56.business.order.out.OrderPostUtil;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SmsYQUtil;
import com.wo56.common.utils.SysTenantDefCacheDataUtil;


public class OrdDepartInfoYqTF implements IOrdDepartInfoYqIntf{
	
	private static final Log log = LogFactory.getLog(OrdDepartInfoYqTF.class);
	
	/**
	 * 保存与修改
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String doSaveOrUpdateDepart(Map<String,Object> inParam) throws Exception{
		Date date = new Date();
		String dateStr = DateUtil.formatDate(date, "yyyyMMdd");
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		BaseUser user = SysContexts.getCurrentOperator();
		List<String> childOrderIds = (List<String>) inParam.get("childOrderIds");
		if (childOrderIds == null || childOrderIds.size() < 0) {
			throw new BusinessException("请选择需要配载的子运单!");
		}
		long batchNum = DataFormat.getLongKey(inParam, "batchNum");
		String batchNumAlias = DataFormat.getStringKey(inParam, "batchNumAlias");
		long descOrgId = DataFormat.getLongKey(inParam, "descOrgId");
		if (descOrgId < 0) {
			throw new BusinessException("请选择配送网点！");
		}
		String driverBill = DataFormat.getStringKey(inParam, "driverBill");
		if (StringUtils.isEmpty(driverBill)) {
			throw new BusinessException("请填写司机手机！");
		}else if(!CommonUtil.isCheckPhone(driverBill)){
			throw new BusinessException("请输入正确的司机手机号码！");
		}
		String driverName = DataFormat.getStringKey(inParam, "driverName");
		if (StringUtils.isEmpty(driverName)) {
			throw new BusinessException("请填写司机名称！");
		}
		String length = DataFormat.getStringKey(inParam, "length");
		if (StringUtils.isEmpty(length)) {
			throw new BusinessException("请填写车辆长度！");
		}
		int vehicleType = DataFormat.getIntKey(inParam, "vehicleType");
		if (vehicleType < 0) {
			throw new BusinessException("请选择车辆类型！");
		}
		String plateNumber = DataFormat.getStringKey(inParam, "plateNumber");
		if (StringUtils.isEmpty(plateNumber)) {
			throw new BusinessException("请填写车牌号码！");
		}else if(!CommonUtil.isPlateNumber(plateNumber)){
			throw new BusinessException("请输入正确车牌号码！");
		}
		long vehicleId = DataFormat.getLongKey(inParam, "vehicleId");
		long driverId = DataFormat.getLongKey(inParam, "driverId");	
		
		CmVehicleInfoTF cmVehicleInfoTF = (CmVehicleInfoTF) SysContexts.getBean("cmVehicleTF");
		CmVehicleInfo vehicleInfo = cmVehicleInfoTF.doSaveOrUpdateVehicle(vehicleId, vehicleType, length, plateNumber);
		CmDriverInfo cmDriverInfo = cmVehicleInfoTF.doSaveOrUpdateDriver(driverId, driverName, driverBill);
		
		OrdDepartInfo ordDepartInfo = new OrdDepartInfo();
		OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		Set<Long> orderIds = new HashSet<Long>();
		OrdStockTF ordStockTF = (OrdStockTF) SysContexts.getBean("ordStockTF");
		OrdChildOpLogTF ordChildOpLogTF = (OrdChildOpLogTF) SysContexts.getBean("ordChildOpLogTF");
		if (batchNum < 0) {
			BeanUtils.copyProperties(ordDepartInfo, inParam);
			ordDepartInfo.setLoadTime(new Date());
			ordDepartInfo.setLoadOpId(user.getUserId());
			ordDepartInfo.setLoadOpName(user.getUserName());
			ordDepartInfo.setState(SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.WAIT_DEPART);
			
		}else{
			ordDepartInfo = ordDepartInfoYqSV.getOrdDepartInfo(batchNum);
			if (ordDepartInfo.getSourceOrgId() != user.getOrgId()) {
				throw new BusinessException("不是发车网点不能修改配载信息！");
			}
			if (ordDepartInfo.getState() == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVAL_OF_GOODS || ordDepartInfo.getState() == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVAL_OF_GOODS_PART) {
				throw new BusinessException("该批次【"+ordDepartInfo.getBatchNumAlias()+"】，是【部分到货】或【全部到货】状态不能操作修改配载！");
			}
			BeanUtils.copyProperties(ordDepartInfo, inParam);
			List<OrdDepartDetail> ordDepartDetails = ordDepartInfoYqSV.getOrdDepartDetailList(batchNum);
			Set<Long> set = new HashSet<Long>();
			//修改配载时，先删除之前的数据，子运单状态回退到待配载
			for (OrdDepartDetail ordDepartDetail : ordDepartDetails) {
				OrderInfoChild orderInfoChild = orderInfoChildTF.getOrderInfoChild(ordDepartDetail.getId().getOrderId());
				if (ordDepartInfo.getState() == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ON_THE_WAY || ordDepartInfo.getState() == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVE) {
					//取消出库
					ordStockTF.cancelOutPutStorage(orderInfoChild.getChildOrderId(), user.getOrgId());
				}
				orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN);
				set.add(orderInfoChild.getOrderId());
				orderInfoChildTF.doUpdate(orderInfoChild, user);
				ordDepartInfoYqSV.delOrdDepartDetail(ordDepartDetail);
			}
			//更新运单最新状态
			if (set != null && set.size() > 0) {
				for (Long orderId : set) {
					OrderInfo orderInfo = orderInfoTF.getOrderInfo(orderId);
					int orderState = orderInfoTF.maxOrderState(orderId);
					orderInfo.setOrderState(orderState);
				}
			}
		}
		ordDepartInfo.setSourceOrgId(user.getOrgId());
		ordDepartInfo.setTenantId(user.getTenantId());
		ordDepartInfo.setVehicleId(vehicleInfo.getVehicleId());
		ordDepartInfo.setPlateNumber(vehicleInfo.getPlateNumber());
		ordDepartInfo.setDriverId(cmDriverInfo.getId());
		ordDepartInfo.setDriverName(cmDriverInfo.getName());
		ordDepartInfo.setDriverBill(cmDriverInfo.getBill() != null ? cmDriverInfo.getBill() : "");
		ordDepartInfoYqSV.doSaveOrUpdate(ordDepartInfo);
		if (StringUtils.isEmpty(batchNumAlias)) {
			ordDepartInfo.setBatchNumAlias(dateStr + ordDepartInfo.getBatchNum());
		}
		List<Map<String,Long>> list = new ArrayList<Map<String,Long>>();
		for (String string : childOrderIds) {
			OrderInfoChild orderInfoChild = orderInfoChildTF.getOrderInfoChild(Long.valueOf(string));
			if (orderInfoChild.getChildOrderState().intValue() != SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN) {
				throw new BusinessException("该子运单号【"+orderInfoChild.getChildTrackingNumAli()+"】，不是待配载状态不能进行配载操作！");
			}
			//配载详情
			OrdDepartDetail ordDepartDetail = new OrdDepartDetail();
			OrdDepartDetailId ordDepartDetailId = new OrdDepartDetailId();
			ordDepartDetail.setId(ordDepartDetailId);
			ordDepartDetailId.setBatchNum(ordDepartInfo.getBatchNum());
			ordDepartDetailId.setOrderId(Long.valueOf(string));
			ordDepartDetail.setCreateDate(date);
			ordDepartInfoYqSV.doSaveOrUpdateDetail(ordDepartDetail);
			//修改子运单信息
			
			if (ordDepartInfo.getState() == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVE) {
				orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.FLOATION_CARGO);
				ordDepartDetail.setArrivalOpId(user.getUserId());
				ordDepartDetail.setArrivalOpName(user.getUserName());
				ordDepartDetail.setArrivalTime(new Date());
				ordStockTF.outPutStaStorage(orderInfoChild.getChildOrderId(), user.getOrgId());
			}else if(ordDepartInfo.getState() == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ON_THE_WAY){
				orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.IN_TRANSIT);
				ordStockTF.outPutStaStorage(orderInfoChild.getChildOrderId(), user.getOrgId());
			}else{
				orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DEPARTURE);
			}
			orderInfoChildTF.doUpdate(orderInfoChild, user);
			//存运单主键
			orderIds.add(orderInfoChild.getOrderId());
			Map<String,Long> childMap = new HashMap<String, Long>();
			childMap.put("orderId", orderInfoChild.getOrderId());
			childMap.put("childOrderId", orderInfoChild.getChildOrderId());
			//childMap.put("", value)
			list.add(childMap);
			//记录日志
			ordChildOpLogTF.departLog(orderInfoChild, 6, user, ordDepartInfo);
		}
		//配载子运单按运单号分组
		Map<String,List<Long>> map = new HashMap<String, List<Long>>();
		for(Map<String,Long> child : list){
			if (map.get(child.get("orderId").toString()) == null) {
				map.put(child.get("orderId").toString(), null);
			}
		}
		for(String rawTypeId : map.keySet()){
			List<Long> childOrderIdList = new ArrayList<Long>();
			for(Map<String,Long> child : list){
				if (rawTypeId.equals(child.get("orderId").toString())) {
					childOrderIdList.add(child.get("childOrderId"));
					map.put(String.valueOf(child.get("orderId")), childOrderIdList);
				}
			}
		}
		long freight = 0;
		long totalFee = 0;
		long collectMoney = 0;
		OrdLogNewTF ordLogNewTF = (OrdLogNewTF) SysContexts.getBean("ordLogNewTF");		
		//更新最新状态
		for (Long long1 : orderIds) {
			OrderInfo orderInfo = orderInfoTF.getOrderInfo(long1);
			int orderState = orderInfoTF.maxOrderState(long1);
			orderInfo.setOrderState(orderState);
			int i = orderInfoTF.checkOrderInfoMany(long1);
			if (i == map.get(long1.toString()).size()) {
				orderInfo.setDepartCount(0);
			}else{
				orderInfo.setDepartCount(1);
			} 
			orderInfoTF.doUpdate(orderInfo, user);
			OrderFee orderFee = orderInfoTF.getOrderFee(orderInfo.getOrderId());
			freight = (orderFee.getFreight() != null ? orderFee.getFreight() : 0) + freight;
			if (orderInfo.getPayment() == SysStaticDataEnumYunQi.PAYMENT_TYPE_YQ.ARR_PAY) {
				totalFee = totalFee + (orderFee.getTotalFee() != null ? orderFee.getTotalFee() : 0); 
			}
			collectMoney = collectMoney + (orderFee.getCollectMoney() != null ? orderFee.getCollectMoney() : 0); 
			
			//记录开单日记
			
			ordLogNewTF.departOrderLog(user,orderInfo.getOrdsId());
		}
		ordDepartInfo.setFreight(freight);
		ordDepartInfo.setTotalFee(totalFee);
		ordDepartInfo.setCollectMoney(collectMoney);
		
		return "Y";
	}
	
	/***
	 * 查询发车配载列表【601051】
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String,Object> getordDepartInfos(Map<String,Object> inParam){
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		//d.BATCH_NUM,d.BATCH_NUM_ALIAS,d.LOAD_TIME,d.SOURCE_ORG_ID,d.DESC_ORG_ID,d.ORDER_NUM,d.VOLUME,d.WEIGHT,d.DEPART_OP_ID
		List<OrdDepartInfoOutYq> listOut = new ArrayList<OrdDepartInfoOutYq>();
		IPage p = PageUtil.gridPage(ordDepartInfoYqSV.doQueryOrdDepart(inParam,1));
		List<Object[]> list = p.getThisPageElements();
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				OrdDepartInfoOutYq out = new OrdDepartInfoOutYq();
				out.setBatchNum(objects[0] != null ? objects[0].toString() : "");
				out.setBatchNumAlias(objects[1] != null ? objects[1].toString() : "");
				out.setLoadTime(objects[2] != null ? DateUtil.formatDate(((Date)objects[2]), "yyyy-MM-dd") : "");
				out.setSourceOrgName(objects[3]!=null ? OraganizationCacheDataUtil.getOrgName(Long.valueOf(objects[3].toString())) : "");
				out.setDescOrgName(objects[4] != null ? OraganizationCacheDataUtil.getOrgName(Long.valueOf(objects[4].toString())) : "");
				out.setOrderNum(objects[5] != null ? objects[5].toString() : "");
				out.setVolume(objects[6] != null ? objects[6].toString() : "");
				out.setWeight(objects[7] != null ? objects[7].toString() : "");
				out.setIsDepart(objects[8] != null ? objects[8].toString() : "");
				out.setNumber(objects[10] != null ? objects[10].toString() : "");
				out.setIsDepartName(SysStaticDataUtil.getSysStaticDataCodeName("VEHICLE_STATE_YQ", out.getIsDepart()));
				listOut.add(out);
			}
		}
		Pagination page = new Pagination(p);
		page.setItems(listOut);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	}
	/**
	 * 批次id查询配载详情（601056）
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> getOrdDepartInfo(Map<String,Object> inParam)throws Exception{
		long batchNum = DataFormat.getLongKey(inParam,"batchNum");
		if (batchNum < 0) {
			throw new BusinessException("请传入批次号ID！");
		}
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		OrdDepartInfo ordDepartInfo = ordDepartInfoYqSV.getOrdDepartInfo(batchNum);
		List<OrdDepartDetail> ordDepartDetails = ordDepartInfoYqSV.getOrdDepartDetailList(batchNum);
		OrdDepartInfoOutDelYq ordDepartInfoOutDelYq = new OrdDepartInfoOutDelYq();
		BeanUtils.copyProperties(ordDepartInfoOutDelYq, ordDepartInfo);
		CmVehicleInfoTF cmVehicleInfoTF = (CmVehicleInfoTF) SysContexts.getBean("cmVehicleTF");
		CmVehicleInfo cmVehicleInfo = cmVehicleInfoTF.getCmVehicleInfo(ordDepartInfo.getVehicleId());
		ordDepartInfoOutDelYq.setVehicleType(cmVehicleInfo.getVehicleType()!=null?cmVehicleInfo.getVehicleType().toString():"");
		ordDepartInfoOutDelYq.setVehicleLength(cmVehicleInfo.getLength()!=null?cmVehicleInfo.getLength().toString():"");
		ordDepartInfoOutDelYq.setIsDepart(String.valueOf(ordDepartInfo.getState()));
		ordDepartInfoOutDelYq.setDescOrgName(ordDepartInfo.getDescOrgIdName());
		ordDepartInfoOutDelYq.setVehicleTypeName(cmVehicleInfo.getVehicleTypeName()!=null?cmVehicleInfo.getVehicleTypeName().toString():"");
		if (ordDepartDetails==null||ordDepartDetails.size()<=0) {
			throw new BusinessException("该批次号【"+ordDepartInfo.getBatchNumAlias()+"】配载信息有误！");
		}
		Set<Long> orderIds = new HashSet<Long>();
		for (int i = 0; i < ordDepartDetails.size(); i++) {
			orderIds.add(ordDepartDetails.get(i).getId().getOrderId());
		}
		ordDepartInfoOutDelYq.setItems(orderInfoChildTF.getOrdDepartOrder(orderIds));
		return JsonHelper.parseJSON2Map(JsonHelper.json(ordDepartInfoOutDelYq));
	}
	/**
	 * 多个到货【601063】
	 * @param inParam
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> arrivalGoods(Map<String,Object> inParam) throws Exception{
		List<String> childOrderIds = (List<String>) inParam.get("childOrderIds");
		if (childOrderIds != null && childOrderIds.size() > 0) {
			for (String string : childOrderIds) {
				inParam.put("childOrderId", string);
				sweepArrivalGoods(inParam);
			}
		}else{
			throw new BusinessException("请选择需要到货的数据！");
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
	}
	
	/**
	 * 扫描到货操作
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> sweepArrivalGoods(Map<String,Object> inParam) throws Exception{
		Date date = new Date();
		BaseUser user = SysContexts.getCurrentOperator();
		long batchNum = DataFormat.getLongKey(inParam, "batchNum");
		String childTrackingNum = DataFormat.getStringKey(inParam, "childTrackingNum");
		long childOrderId = DataFormat.getLongKey(inParam, "childOrderId");
		String tmsklinfo = DataFormat.getStringKey(inParam, "tmsklinfo");
		if (batchNum < 0) {
			throw new BusinessException("请传入批次号ID！");
		}
		if (StringUtils.isEmpty(childTrackingNum) && childOrderId < 0) {
			throw new BusinessException("请传入子运单号！");
		}
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		OrderInfoChild orderInfoChild = orderInfoChildTF.getOrderInfoChildByChildTrackingNumber(childTrackingNum,childOrderId);
		if (orderInfoChild == null) {
			throw new BusinessException("无该子运单信息！");
		}
		OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		OrderInfo orderInfo = orderInfoTF.getOrderInfo(orderInfoChild.getOrderId());
		if (orderInfo == null ) {
			throw new BusinessException("无该运单信息！");
		}
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		OrdDepartInfo ordDepartInfo = ordDepartInfoYqSV.getOrdDepartInfo(batchNum);
		if (ordDepartInfo == null) {
			throw new BusinessException("无该批次号信息！");
		}
		if (ordDepartInfo.getDescOrgId() != user.getOrgId().longValue()) {
			throw new BusinessException("当前网点【"+OraganizationCacheDataUtil.getOrgName(user.getOrgId())+"】不等于目的网点【"+ordDepartInfo.getDescOrgId()+"】，不能操作到货！");
		}
		List<Object> list = ordDepartInfoYqSV.checkDepartInfoByTrackingNum(batchNum, childTrackingNum,childOrderId).list();
		if (list != null && list.size() > 0) {
			for (Object object : list) {
				int state = object != null ? Integer.valueOf(object.toString()) : -1;
				if (state == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVE || state == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVAL_OF_GOODS_PART) {
					
				}else{
					throw new BusinessException("该【"+orderInfoChild.getChildTrackingNumAli()+"】子运单号未到车，不能操作到货！");
				}
			}
		}else{
			throw new BusinessException("该【"+orderInfoChild.getChildTrackingNumAli()+"】子运单号不在该批次号，不能操作到货！");
		}
		boolean isCurOrg = orderInfoChild.getDispatchingOrgId().longValue() == user.getOrgId().longValue();
		boolean isTakePick =  orderInfo.getInterchange() == SysStaticDataEnumYunQi.INTERCHANGE_YUNQI.TAKE_PICK ? true : false;
		if (isCurOrg) {
			if (isTakePick) {
				orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN);
			}else{
				orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY);
			}
		}else{
			orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN);
		}
		orderInfoChildTF.doUpdate(orderInfoChild, user);
		//到货入库
		OrdStockTF ordStockTF = (OrdStockTF) SysContexts.getBean("ordStockTF");
		ordStockTF.putInCarStorage(orderInfoChild.getChildOrderId(), user.getOrgId());
		boolean isArrivalTMS = orderInfo.getOrderState() == SysStaticDataEnumYunQi.ORDER_INFO_STATE.FLOATION_CARGO ? true : false ;
		if (ordDepartInfo.getState() == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVE || ordDepartInfo.getState() == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVAL_OF_GOODS_PART) {
			int arrivalNum = orderInfoChildTF.checkOrderChildState(batchNum);
			if (ordDepartInfo.getNumber() == null) {
				throw new BusinessException("件数为空，数据有误！");
			}
			if (arrivalNum > 0 && arrivalNum == ordDepartInfo.getNumber()) {//存在未到货的就是部分到货
				ordDepartInfo.setState(SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVAL_OF_GOODS);//全部到货
			}else if (arrivalNum > 0) {
				ordDepartInfo.setState(SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVAL_OF_GOODS_PART);//部分到货
			}
		}else{
			throw new BusinessException("该批次【"+ordDepartInfo.getBatchNumAlias()+"】，未做到车操作，不能到货！");
		}
		//更新配载到货操作
		ordDepartInfoYqSV.updateDepartDetali(user, date, batchNum, childOrderId);
		
		ordDepartInfo.setArrivalOpId(user.getUserId());
		ordDepartInfo.setArrivalOpName(user.getUserName());
		ordDepartInfo.setArrivalTime(date);
		ordDepartInfoYqSV.doSaveOrUpdate(ordDepartInfo);
		int orderState = orderInfoTF.maxOrderState(orderInfo.getOrderId());//对内状态
		//到货网点是否等于到达网点，不是，对外母运单状态为运输中，对内运单状态为待配载
		boolean isArrivalGoodsCurOrg = orderInfoChild.getCurrentOrgId().longValue() == orderInfo.getArrivedOrgId().longValue();
		if(!isArrivalGoodsCurOrg){
			orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.IN_TRANSIT);
		}else{
			 // 自提 
			 if (isTakePick) {
				  orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.WAIT_SIGN); 
			  }else{
				   orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PENDING_DELIVERY); 
			  }		
		}
		orderInfo.setOrderState(orderState);
		orderInfoTF.doUpdate(orderInfo,user);
		//到货日志
		OrdChildOpLogTF ordChildOpLogTF = (OrdChildOpLogTF) SysContexts.getBean("ordChildOpLogTF");
		ordChildOpLogTF.departLog(orderInfoChild, 9, user, ordDepartInfo);
		if (isArrivalTMS) {
			OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
			OrdOrdersInfo ordOrdersInfo = ordersInfoTF.getOrdOrdersInfo(orderInfo.getOrdsId());
			 if(!isArrivalGoodsCurOrg){
				//中转 
				ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT);
			}else{
				if (isTakePick) {
					ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN);
				}
				else{
					ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY);
				}	
			}
			//没有中转 
			
			
			ordOrdersInfo.setOpId(user.getUserId());
			ordOrdersInfo.setOpDate(date);
			ordersInfoTF.doUpdateOrders(ordOrdersInfo,user);
			OrdBusiPerson ordBusiPerson = ordersInfoTF.getOrdBusiPerson(orderInfo.getOrdsId());
			ordBusiPerson.setGxEndOpId(user.getUserId());
			ordBusiPerson.setGxEndOpName(user.getUserName());
			ordBusiPerson.setGxEndTime(date);
			ordersInfoTF.doUpdateBusi(ordBusiPerson);
			// 写日志
			OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
			ordLogNewTF.gxArriverGoodsOrderLog(user.getUserId(), user, ordOrdersInfo, SysTenantDefCacheDataUtil.getTenantName(user.getTenantId()), user.getUserName(), OraganizationCacheDataUtil.getOrgName(user.getOrgId()),StringUtils.defaultIfEmpty(OraganizationCacheDataUtil.getOrganization(user.getOrgId()).getSupportStaffPhone(), ""),ordDepartInfo);
			if (isCurOrg) {
				Session session = SysContexts.getEntityManager(true);
				//TODO 推送（系统自动推送消息给到收货人，发货人）
				if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
					SmsYQUtil.arriveSendConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(),ordOrdersInfo.getOrderId(),OraganizationCacheDataUtil.getOrganization(user.getOrgId()).getSupportStaffPhone(), OraganizationCacheDataUtil.getOrgName(user.getOrgId()),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, ordersInfoTF.isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE,  ordOrdersInfo.getConsigneeBill());
				}
				Query query = session.createSQLQuery("select consignor_bill from ord_goods_info where order_id=:orderId");
				query.setParameter("orderId", ordOrdersInfo.getOrderId());
				List consignorList = query.list();
				for (Object consignor_bill : consignorList) {
					if(consignor_bill!=null&&StringUtils.isNotEmpty(consignor_bill.toString())){
						SmsYQUtil.arriveSendConsignor(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderId(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, ordersInfoTF.isRegisterUser(session, consignor_bill.toString())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, consignor_bill.toString(),ordOrdersInfo.getOrderNo());
					}
				}
			}
			OrderInfoTmsErrorTF orderInfoTmsErrorTF = (OrderInfoTmsErrorTF) SysContexts.getBean("orderInfoTmsErrorTF");
			//tms柯莱到货
			if (CommonUtil.tmsTenantId("TMS_KL") == user.getTenantId().longValue() && StringUtils.isEmpty(tmsklinfo)) {
				String isArrival = OrderPostUtil.arrivalKL(orderInfo.getOrderNumber(), user.getUserName(), DateUtil.formatDate(date, "yyyy-MM-dd"), user.getTenantId(),false);
				if (!"Y".equals(isArrival)) {
					log.error(isArrival);
					OrderInfoTmsError orderInfoTmsError = new OrderInfoTmsError();
					orderInfoTmsError.setOrderNumber(orderInfo.getOrderNumber());
					orderInfoTmsError.setUserName(user.getUserName());
					orderInfoTmsError.setCreateTime(date);
					orderInfoTmsError.setTenantId(user.getTenantId());
					orderInfoTmsError.setOrderId(orderInfo.getOrderId());
					orderInfoTmsError.setOrdsId(orderInfo.getOrdsId());
					orderInfoTmsError.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.FLOATION_CARGO);
					orderInfoTmsErrorTF.doSaveOrderInfoTmsError(orderInfoTmsError);
				}
			}
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
	}
	/**
	 * 撤销到货
	 * @param inParam
	 * @return
	 * 1.更改运单状态最新
	 * 2.更新子运单状态 待到货
	 * 3.更新主订单状态
	 * 4.更新配载表状态
	 * 5.撤销库存数据
	 * 6.情况配载表时间人物
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> cancelArrival(Map<String,Object> inParam) throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		long batchNum = DataFormat.getLongKey(inParam, "batchNum");
		if (batchNum < 0) {
			throw new BusinessException("请传入批次号！");
		}
		List<String> childOrderIds = (List<String>) inParam.get("childOrderIds");
		if (childOrderIds == null || childOrderIds.size() <= 0) {
			throw new BusinessException("请选择需要撤销到货的子运单号！");
		}
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		OrdDepartInfo ordDepartInfo = ordDepartInfoYqSV.getOrdDepartInfo(batchNum);
		int state = ordDepartInfo.getState();
		if (state != SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVAL_OF_GOODS_PART) {
			throw new BusinessException("该批次号【"+ordDepartInfo.getBatchNumAlias()+"】，不是【部分到货】，不能操作撤销到货！");
		}
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		Set<Long> orderIds = new HashSet<Long>();
		OrdStockTF ordStockTF = (OrdStockTF) SysContexts.getBean("ordStockTF");
		OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		OrdChildOpLogTF ordChildOpLogTF = (OrdChildOpLogTF) SysContexts.getBean("ordChildOpLogTF");
		//回退状态为未到货，删除库存
		for (String string : childOrderIds) {
			long childOrderId = Long.parseLong(string);
			OrderInfoChild orderInfoChild = orderInfoChildTF.getOrderInfoChild(childOrderId);
			String childOrderStateName = SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE", orderInfoChild.getChildOrderState().toString());
			if (user.getOrgId().longValue() == orderInfoChild.getDispatchingOrgId().longValue()) {
				if (orderInfoTF.queryOrderInterchange(orderInfoChild.getOrderId()) != SysStaticDataEnumYunQi.INTERCHANGE_YUNQI.TAKE_PICK) {
					if (orderInfoChild.getChildOrderState() != SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY) {
						throw new BusinessException("该子运单【"+orderInfoChild.getChildTrackingNumAli()+"】，为【"+childOrderStateName+"】不能做撤销到货！");
					}
				}else{
					if (orderInfoChild.getChildOrderState() != SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN) {
						throw new BusinessException("该子运单【"+orderInfoChild.getChildTrackingNumAli()+"】，为【"+childOrderStateName+"】不能做撤销到货！");
					}
				}
			}else{
				if (orderInfoChild.getChildOrderState() != SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN) {
					throw new BusinessException("该子运单好【"+orderInfoChild.getChildTrackingNumAli()+"】，为【"+childOrderStateName+"】状态，不能撤销到货！");
				}
			}
			
			orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.FLOATION_CARGO);
			//更新配载到货操作
			ordDepartInfoYqSV.updateDepartDetali(null, null, batchNum, childOrderId);
			orderInfoChildTF.doUpdate(orderInfoChild, user);
			ordStockTF.cancelArrivalStock(childOrderId, user.getOrgId());
			orderIds.add(orderInfoChild.getOrderId());
			//到货日志
			ordChildOpLogTF.departLog(orderInfoChild, 5, user, null);
		}
		
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		OrdLogNewTF ordLogNewTF = (OrdLogNewTF) SysContexts.getBean("ordLogNewTF");
		//更改主运单和主订单状态
		for (Long orderId : orderIds) {
			int tackingState = orderInfoTF.maxOrderState(orderId);
			OrderInfo orderInfo = orderInfoTF.getOrderInfo(orderId);
			orderInfo.setOrderState(tackingState);
			//加多对外运单状态
			int orderStateOut = orderInfoTF.getOrderStateOut(tackingState);
			orderInfo.setOrderStateOut(orderStateOut);
			
			orderInfoTF.doUpdate(orderInfo,user);
			OrdOrdersInfo ordOrdersInfo = ordersInfoTF.getOrdOrdersInfo(orderInfo.getOrdsId());
			ordOrdersInfo.setOrderState(ordersInfoTF.maxOrdersState(tackingState));
			ordersInfoTF.doUpdateOrders(ordOrdersInfo,user);
			ordLogNewTF.departLog(user, 5, orderInfo.getOrdsId(),ordDepartInfo);	
		}
		int arraivalNum = orderInfoChildTF.checkOrderChildState(batchNum);
		if (arraivalNum > 0 && arraivalNum == ordDepartInfo.getOrderNum()) {
			ordDepartInfo.setState(SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVAL_OF_GOODS);
		}else if(arraivalNum > 0){
			ordDepartInfo.setState(SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVAL_OF_GOODS_PART);
		}else if(arraivalNum <= 0){
			ordDepartInfo.setState(SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVE);
			ordDepartInfo.setArrivalOpId(null);
			ordDepartInfo.setArrivalOpName(null);
			ordDepartInfo.setArrivalTime(null);
		}
		ordDepartInfoYqSV.doSaveOrUpdate(ordDepartInfo);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
	}
    //到车配载列表查询
	public Map<String, Object> getDepartAndArrivalInfos(
			Map<String, Object> inParam) {
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		//d.BATCH_NUM,d.BATCH_NUM_ALIAS,d.LOAD_TIME,d.SOURCE_ORG_ID,d.DESC_ORG_ID,d.ORDER_NUM,d.VOLUME,d.WEIGHT,d.DEPART_OP_ID
		List<OrdDepartInfoOutYq> listOut = new ArrayList<OrdDepartInfoOutYq>();
		IPage p = PageUtil.gridPage(ordDepartInfoYqSV.doQueryOrdDepart(inParam,2));
		List<Object[]> list = p.getThisPageElements();
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				OrdDepartInfoOutYq out = new OrdDepartInfoOutYq();
				out.setBatchNum(objects[0] != null ? objects[0].toString() : "");
				out.setBatchNumAlias(objects[1] != null ? objects[1].toString() : "");
				out.setLoadTime(objects[2] != null ? DateUtil.formatDate(((Date)objects[2]), "yyyy-MM-dd") : "");
				out.setSourceOrgName(objects[3]!=null ? OraganizationCacheDataUtil.getOrgName(Long.valueOf(objects[3].toString())) : "");
				out.setDescOrgName(objects[4] != null ? OraganizationCacheDataUtil.getOrgName(Long.valueOf(objects[4].toString())) : "");
				out.setOrderNum(objects[5] != null ? objects[5].toString() : "");
				out.setVolume(objects[6] != null ? objects[6].toString() : "");
				out.setWeight(objects[7] != null ? objects[7].toString() : "");
				out.setIsDepart(objects[8] != null ? objects[8].toString() : "");
				out.setNumber(objects[10] != null ? objects[10].toString() : "");
				out.setIsDepartName(SysStaticDataUtil.getSysStaticDataCodeName("VEHICLE_STATE_YQ", out.getIsDepart()));
				listOut.add(out);
			}
		}
		Pagination page = new Pagination(p);
		page.setItems(listOut);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	}

	public Map<String, Object> delDepartInfos(Map<String, Object> inParam) {
		// TODO Auto-generated method stub
		//按照批次号Id删除 , 发车的不能删除  ，子运单状态改为待配载
		BaseUser user = SysContexts.getCurrentOperator();
		long batchNum = DataFormat.getLongKey(inParam, "batchNum");
		if (batchNum < 0) {
			throw new BusinessException("请传入批次号！");
		}
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		OrdDepartInfo ordDepartInfo = ordDepartInfoYqSV.getOrdDepartInfo(batchNum);
		if(ordDepartInfo ==null){
			throw new BusinessException("无该批次信息");
		}
		int state = ordDepartInfo.getState();
		if(state == SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ON_THE_WAY){
			throw new BusinessException("该批次号【"+ordDepartInfo.getBatchNumAlias()+"】，已发车，不能操作删除配载！");
		}
		List<OrdDepartDetail> list = ordDepartInfoYqSV.getOrdDepartDetailList(batchNum);//根据批次号得到子运单号
		if(list== null&& list.size() <=0 ){
			throw new BusinessException("无该批次信息");
		}
		
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		if(orderInfoTF == null){
			throw new BusinessException("无此运单信息");
		}
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		if(ordersInfoTF == null){
			throw new BusinessException("无此订单信息");
		}
		Set<Long> childOrderIds = new HashSet<Long>();//子订单id
		Set<Long> orderIds = new HashSet<Long>();//主订单id
		for(OrdDepartDetail ordDepartDetail : list){
			long childOrderId = ordDepartDetail.getId().getOrderId();
			
			childOrderIds.add(childOrderId);//得到子订单
			List<OrderInfoChild> orderInfoChilds = orderInfoChildTF.getOrderInfoByChildOrderId(childOrderId);
			if(orderInfoChilds==null && orderInfoChilds.size()<=0){
				throw new BusinessException("无此子订单信息");
			}
			for(OrderInfoChild orderInfoChild : orderInfoChilds){
				orderIds.add(orderInfoChild.getOrderId());//得到主订单
			}
			ordDepartInfoYqSV.delOrdDepartDetail(ordDepartDetail);//删除批次详表
		}
		//修改子运单状态为待配载
		for(Long orderId: childOrderIds){
			List<OrderInfoChild> orderInfoChilds = orderInfoChildTF.getOrderInfoByChildOrderId(orderId);
			if(orderInfoChilds==null && orderInfoChilds.size()<=0){
				throw new BusinessException("无此子订单信息");
			}
			OrderInfoChild orderInfoChild =orderInfoChilds.get(0);
			if(orderInfoChild.getChildOrderState()!=SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DEPARTURE){
				continue;
			}
			orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN);
			orderInfoChildTF.doUpdate(orderInfoChild, user);
		}
		//更新主运单状态为子运单最后的状态
		for(Long orderId: orderIds){
			//根据zi运单得到母运单
			int tackingState = orderInfoTF.maxOrderState(orderId);
			OrderInfo orderInfo = orderInfoTF.getOrderInfo(orderId);
			orderInfo.setOrderState(tackingState);
			if(user.getOrgId().longValue() == orderInfo.getBillingOrgId().longValue()){
				orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PENDING_DEPARTURE);
			}else{
				//中转
				orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.IN_TRANSIT);
			}
			orderInfoTF.doUpdate(orderInfo,user);
		}
		ordDepartInfoYqSV.delOrdDepartDetail(ordDepartInfo);//删除批次表
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
	}
    /* 发车
     * 根据 批次号 更新子运单 主运单状态  修改库存表 状态为出库   
     */
	public Map<String, Object> doDepart(Map<String, Object> inParam) throws Exception {	
		// TODO Auto-generated method stub
		Date date = new Date();
		BaseUser user = SysContexts.getCurrentOperator();
		long batchNum = DataFormat.getLongKey(inParam, "batchNum");
		if (batchNum < 0) {
			throw new BusinessException("请传入批次号！");
		}
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		OrdDepartInfo ordDepartInfo = ordDepartInfoYqSV.getOrdDepartInfo(batchNum);
		if(ordDepartInfo == null){
			throw new BusinessException("无次批次配载信息！");
		}
		if(ordDepartInfo.getState() != SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.WAIT_DEPART ){
		    throw new BusinessException("该【"+ordDepartInfo.getBatchNumAlias()+"】 不是待发车状态，不能操作发车");
		}
		if(user.getOrgId() != ordDepartInfo.getSourceOrgId()){
			throw new BusinessException("不是起始网点，不能操作发车");
		}
		
		List<OrdDepartDetail> list = ordDepartInfoYqSV.getOrdDepartDetailList(batchNum);//根据批次号得到子运单号
		OrdStockTF ordStockTF = (OrdStockTF) SysContexts.getBean("ordStockTF");//存库表
		int departState =SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ON_THE_WAY;
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		OrdChildOpLogTF ordChildOpLogTF = (OrdChildOpLogTF) SysContexts.getBean("ordChildOpLogTF");
		Set<Long> childOrderIds = new HashSet<Long>();//子订单id
		Set<Long> orderIds = new HashSet<Long>();//主订单id
		//库存表 STOCK_OUT_TYPE 出库类型 0 发车 1 中转 2 送货上门 3 签收出库
		for(OrdDepartDetail ordDepartDetail : list){
			long childOrderId = ordDepartDetail.getId().getOrderId();//子运单ID
			childOrderIds.add(childOrderId);//得到子订单
		}
		//更新主运单状态为子运单最后的状态
		for(Long childOrderId :childOrderIds ){
			 OrderInfoChild orderInfoChild  = orderInfoChildTF.getOrderInfoChild(childOrderId);
			if(orderInfoChild==null ){
				throw new BusinessException("子运单信息不存在！");
			 }
			 Long orderId =  orderInfoChild.getOrderId();//主运单 
			 orderIds.add(orderId);//去重 
			 orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.IN_TRANSIT);
			 orderInfoChildTF.doUpdate(orderInfoChild, user);
			 ordStockTF.outPutStaStorage(childOrderId, user.getOrgId());//根据子运单id 发车出库
			 
			 //发车日志
			
			 ordChildOpLogTF.departLog(orderInfoChild, 1, user, ordDepartInfo);
		}
		OrdLogNewTF ordLogNewTF = (OrdLogNewTF) SysContexts.getBean("ordLogNewTF");	
		//修改主运单状态
		for(Long orderId:orderIds){
			 int tackingState = orderInfoTF.maxOrderState(orderId);//主运单id
			 OrderInfo orderInfo = orderInfoTF.getOrderInfo(orderId);//主orderId
			 orderInfo.setOrderState(tackingState);
			 //加多对外运单状态
			 int orderStateOut = orderInfoTF.getOrderStateOut(tackingState);
			 if(user.getOrgId().longValue()!= orderInfo.getArrivedOrgId().longValue()){
				orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.IN_TRANSIT); 
			 }else {
				 orderInfo.setOrderStateOut(orderStateOut); 
			 }
			 orderInfoTF.doUpdate(orderInfo,user);
			 //根据主运单id 修改 ord_orders_info 表 状态为 在途中
			 long ordOrderId = orderInfo.getOrdsId();//下单表  订单id
			 OrdOrdersInfo ordOrderInfo = ordersInfoTF.getOrdOrdersInfo(ordOrderId);
			 ordOrderInfo.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.IN_TRANSIT);
			 ordersInfoTF.doUpdateOrders(ordOrderInfo, user);	
			 
			//记录开单日记
				
			ordLogNewTF.departLog(user, 1, orderInfo.getOrdsId(), ordDepartInfo);
		}
		ordDepartInfoYqSV.updateDepartDetaliByDepart(user,date,departState,batchNum);//更新配载表		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
		
	}
    //到车
	public Map<String, Object> doArrvite(Map<String, Object> inParam) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		BaseUser user = SysContexts.getCurrentOperator();
		long batchNum = DataFormat.getLongKey(inParam, "batchNum");
		if (batchNum < 0) {
		   throw new BusinessException("请传入批次号！");
		}
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		OrdDepartInfo ordDepartInfo = ordDepartInfoYqSV.getOrdDepartInfo(batchNum);
		if(ordDepartInfo == null){
			throw new BusinessException("无次批次配载信息！");
		}
		if(ordDepartInfo.getState() != SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ON_THE_WAY ){
		    throw new BusinessException("该【"+ordDepartInfo.getBatchNumAlias()+"】 不是已发车状态，不能操作到车");
		}
		if(user.getOrgId() != ordDepartInfo.getDescOrgId()){
			throw new BusinessException("不是配送网点，不能操作到车");
		}
		List<OrdDepartDetail> list = ordDepartInfoYqSV.getOrdDepartDetailList(batchNum);//根据批次号得到子运单号
		
		
		int departState =SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVE;
		//int stockInType = SysStaticDataEnum.STOCK_IN_TYPE.STOCK_IN_CAR;
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		Set<Long> childOrderIds = new HashSet<Long>();//子订单id
		Set<Long> orderIds = new HashSet<Long>();//主订单id
		//库存表 STOCK_OUT_TYPE 出库类型 0 发车 1 中转 2 送货上门 3 签收出库
		for(OrdDepartDetail ordDepartDetail : list){
		     long childOrderId = ordDepartDetail.getId().getOrderId();//子运单ID
		     childOrderIds.add(childOrderId);//得到子订单
		}
		OrdChildOpLogTF ordChildOpLogTF = (OrdChildOpLogTF) SysContexts.getBean("ordChildOpLogTF");
		//更新主运单状态为子运单最后的状态
		for(Long childOrderId :childOrderIds ){
			OrderInfoChild orderInfoChild  = orderInfoChildTF.getOrderInfoChild(childOrderId);
			if(orderInfoChild==null ){
				throw new BusinessException("子运单信息不存在！");
			 }
			 Long orderId =  orderInfoChild.getOrderId();//主运单 
			 orderIds.add(orderId);//去重 
			 orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.FLOATION_CARGO);//未到货
			 orderInfoChildTF.doUpdate(orderInfoChild, user);			  
			 ordChildOpLogTF.departLog(orderInfoChild, 3, user, ordDepartInfo);
		 }
		OrdLogNewTF ordLogNewTF = (OrdLogNewTF) SysContexts.getBean("ordLogNewTF");	
		 //修改主运单状态
		 for(Long orderId:orderIds){
			 int tackingState = orderInfoTF.maxOrderState(orderId);//主运单id
			 OrderInfo orderInfo = orderInfoTF.getOrderInfo(orderId);//主orderId
			 if(orderInfo == null){
				 throw new BusinessException("无该开单信息！"); 
			 }
			 orderInfo.setOrderState(tackingState);
			 
			//加多对外运单状态
			int orderStateOut = orderInfoTF.getOrderStateOut(tackingState);
		    orderInfo.setOrderStateOut(orderStateOut);

			 orderInfoTF.doUpdate(orderInfo,user);
			 //根据主运单id 修改 ord_orders_info 表 状态为 在途中
			 long ordOrderId = orderInfo.getOrdsId();//下单表  订单id
			 OrdOrdersInfo ordOrderInfo = ordersInfoTF.getOrdOrdersInfo(ordOrderId);
			 if(ordOrderInfo == null){
				 throw new BusinessException("无该下单信息！"); 
			 }
			 ordOrderInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT);//运输中
			 ordersInfoTF.doUpdateOrders(ordOrderInfo, user);
			//记录开单日记
			ordLogNewTF.departLog(user, 3, orderInfo.getOrdsId(),ordDepartInfo);	 
		 }
		 ordDepartInfoYqSV.updateDepartDetaliByDepart(user,date,departState,batchNum);//更新配载表
		 Map<String,Object> map = new HashMap<String, Object>();
		 map.put("info", "Y");
		 return map;
		
	}
    //撤销发车
	public Map<String, Object> cancelDepart(Map<String, Object> inParam) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		BaseUser user = SysContexts.getCurrentOperator();
		long batchNum = DataFormat.getLongKey(inParam, "batchNum");
		if (batchNum < 0) {
			 throw new BusinessException("请传入批次号！");
		 }
		 OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		 OrdDepartInfo ordDepartInfo = ordDepartInfoYqSV.getOrdDepartInfo(batchNum);
		 if(ordDepartInfo == null){
				throw new BusinessException("无次批次配载信息！");
		}
		if(user.getOrgId() != ordDepartInfo.getSourceOrgId()){
				throw new BusinessException("不是起始网点，不能操作到车");
		}
		 if(ordDepartInfo.getState()!= SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ON_THE_WAY){
			throw new BusinessException("配载状态不是已发车，不能操作取消发车");
		 }
		 List<OrdDepartDetail> list = ordDepartInfoYqSV.getOrdDepartDetailList(batchNum);//根据批次号得到子运单号
		 if(list==null && list.size()<=0){
			 throw new BusinessException("无此批次配载信息"); 
		 }
		
		 OrdStockTF ordStockTF = (OrdStockTF) SysContexts.getBean("ordStockTF");//存库表
		 int departState =SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.WAIT_DEPART; //待发车
		 int stockInType = SysStaticDataEnum.STOCK_IN_TYPE.STOCK_IN_ORD;
		 OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		 OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		 OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		 Set<Long> childOrderIds = new HashSet<Long>();//子订单id
		 Set<Long> orderIds = new HashSet<Long>();//主订单id
		 //库存表 STOCK_OUT_TYPE 出库类型 0 发车 1 中转 2 送货上门 3 签收出库
		 for(OrdDepartDetail ordDepartDetail : list){
			  long childOrderId = ordDepartDetail.getId().getOrderId();//子运单ID
			  childOrderIds.add(childOrderId);//得到子订单
		  }
		 OrdChildOpLogTF ordChildOpLogTF = (OrdChildOpLogTF) SysContexts.getBean("ordChildOpLogTF");
		  //更新主运单状态为子运单最后的状态
		 for(Long childOrderId :childOrderIds ){
		       OrderInfoChild orderInfoChild  = orderInfoChildTF.getOrderInfoChild(childOrderId);
		       if(orderInfoChild==null ){
				    throw new BusinessException("无该子运单信息！");
		       }
		       Long orderId =  orderInfoChild.getOrderId();//主运单 
		       orderIds.add(orderId);//去重 
		       orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DEPARTURE);//待发车
		       orderInfoChildTF.doUpdate(orderInfoChild, user);
		       //检查是否在库，若在,删除
		       ordStockTF.cancelOutPutStorage(childOrderId, user.getOrgId());   
		       //取消发车日志
		       ordChildOpLogTF.departLog(orderInfoChild, 2, user, ordDepartInfo);
		 }
		 OrdLogNewTF ordLogNewTF = (OrdLogNewTF) SysContexts.getBean("ordLogNewTF");
		 //修改主运单状态
		for(Long orderId:orderIds){
			 int tackingState = orderInfoTF.maxOrderState(orderId);//主运单id
			 OrderInfo orderInfo = orderInfoTF.getOrderInfo(orderId);//主orderId
			 if(orderInfo == null){
				 throw new BusinessException("无该运单信息！");
			 }
			 orderInfo.setOrderState(tackingState);
			//加多对外运单状态
			 if(user.getOrgId().longValue() != orderInfo.getBillingOrgId().longValue()){
				 orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.IN_TRANSIT);
			 }else {
				 orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PENDING_DEPARTURE); 
			 }
//			int orderStateOut = orderInfoTF.getOrderStateOut(tackingState);
//			orderInfo.setOrderStateOut(orderStateOut);
			orderInfoTF.doUpdate(orderInfo,user);
			//根据主运单id 修改 ord_orders_info 表 状态为 在途中
			 long ordOrderId = orderInfo.getOrdsId();//下单表  订单id
			 OrdOrdersInfo ordOrderInfo = ordersInfoTF.getOrdOrdersInfo(ordOrderId);
			 if(ordOrderInfo == null){
				 throw new BusinessException("无该订单信息！");
			 }
			 if(user.getOrgId().longValue()!= orderInfo.getBillingOrgId().longValue()){
				 //中转
				 ordOrderInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT);//运输中
			 }else {
				 ordOrderInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.PENDING_DEPARTURE);//待发车 
			 }
			 
			 ordersInfoTF.doUpdateOrders(ordOrderInfo, user);
			 
			 		
			 ordLogNewTF.departLog(user, 2, orderInfo.getOrdsId(),ordDepartInfo);	
							 			 
		}
		//ordDepartInfoYqSV.updateDepartDetaliByDepart(user, date,departState, batchNum);//更新配载表	
		ordDepartInfoYqSV.delDepartDetaliByDepart(user, date,departState, batchNum);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
	}
    //取消到车
	public Map<String, Object> cancelArrvite(Map<String, Object> inParam) throws Exception {
		Date date = new Date();
		BaseUser user = SysContexts.getCurrentOperator();
		long batchNum = DataFormat.getLongKey(inParam, "batchNum");
		if (batchNum < 0) {
			throw new BusinessException("请传入批次号！");
		}
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		OrdDepartInfo ordDepartInfo = ordDepartInfoYqSV.getOrdDepartInfo(batchNum);
		if(ordDepartInfo == null){
			throw new BusinessException("无该批次配载信息！");
		}
		if(user.getOrgId() != ordDepartInfo.getDescOrgId()){
			throw new BusinessException("不是配送网点，不能操作到车");
		}
		if(ordDepartInfo.getState()!= SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ARRIVE){
			throw new BusinessException("配载状态不是已到车，不能操作取消到车");
		}
		List<OrdDepartDetail> list = ordDepartInfoYqSV.getOrdDepartDetailList(batchNum);//根据批次号得到子运单号
		int departState =SysStaticDataEnumYunQi.VEHICLE_STATE_YQ.ON_THE_WAY; //再途中
	    OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		Set<Long> childOrderIds = new HashSet<Long>();//子订单id
		Set<Long> orderIds = new HashSet<Long>();//主订单id
		//库存表 STOCK_OUT_TYPE 出库类型 0 发车 1 中转 2 送货上门 3 签收出库
		for(OrdDepartDetail ordDepartDetail : list){
			long childOrderId = ordDepartDetail.getId().getOrderId();//子运单ID
			childOrderIds.add(childOrderId);//得到子订单
		}
		OrdChildOpLogTF ordChildOpLogTF = (OrdChildOpLogTF) SysContexts.getBean("ordChildOpLogTF");
	    //更新主运单状态为子运单最后的状态
		for(Long childOrderId :childOrderIds ){
		     OrderInfoChild orderInfoChild  = orderInfoChildTF.getOrderInfoChild(childOrderId);
		     if(orderInfoChild==null ){
		         throw new BusinessException("子运单信息不存在！");
			 }
			Long orderId =  orderInfoChild.getOrderId();//主运单 
		    orderIds.add(orderId);//去重 
			orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.IN_TRANSIT);//在途中
			orderInfoChildTF.doUpdate(orderInfoChild, user);  
			ordChildOpLogTF.departLog(orderInfoChild, 4, user, ordDepartInfo);
		 }
		OrdLogNewTF ordLogNewTF = (OrdLogNewTF) SysContexts.getBean("ordLogNewTF");	
		 //修改主运单状态
		 for(Long orderId:orderIds){
			int tackingState = orderInfoTF.maxOrderState(orderId);//主运单id
			OrderInfo orderInfo = orderInfoTF.getOrderInfo(orderId);//主orderId
			orderInfo.setOrderState(tackingState);
			//加多对外运单状态
			int orderStateOut = orderInfoTF.getOrderStateOut(tackingState);
			orderInfo.setOrderStateOut(orderStateOut);

			orderInfoTF.doUpdate(orderInfo,user);
			//根据主运单id 修改 ord_orders_info 表 状态为 在途中
			long ordOrderId = orderInfo.getOrdsId();//下单表  订单id
			OrdOrdersInfo ordOrderInfo = ordersInfoTF.getOrdOrdersInfo(ordOrderId);
			ordOrderInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT);//运输中
			ordersInfoTF.doUpdateOrders(ordOrderInfo, user);
			 	
			 ordLogNewTF.departLog(user, 4, orderInfo.getOrdsId(),ordDepartInfo);					 			 
		}
		 //清空 ord_depart_info 和 ord_depart_detail 表中  到车人，到车时间，到车操作人
		ordDepartInfoYqSV.delDepartDetaliByDepart(user, date,departState, batchNum);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
	}
	/**
	 * 配载列表查询
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String,Object> doQuery(Map<String,Object> inParam){
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		IPage page = PageUtil.gridPage(ordDepartInfoYqSV.doQuery(inParam,false));
		List<Object[]> list = page.getThisPageElements();
		List<OrdDepartQueryOutYQ> outList = new ArrayList<OrdDepartQueryOutYQ>();
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				OrdDepartQueryOutYQ out = new OrdDepartQueryOutYQ();
				out.setBatchNum(Long.valueOf(objects[0].toString()));
				out.setBatchNumAlias(objects[1] != null ? objects[1].toString() : "");
				out.setState(objects[2] != null ? SysStaticDataUtil.getSysStaticDataCodeName("VEHICLE_STATE_YQ", objects[2].toString()) : "");
				out.setLoadOpName(objects[3] != null ? objects[3].toString() : "");
				out.setLoadTime(objects[4] != null ? DateUtil.formatDate((Date)objects[4], "yyyy-MM-dd HH:mm:ss") : "");
				out.setDepartTime(objects[5] != null ? DateUtil.formatDate((Date)objects[5], "yyyy-MM-dd HH:mm:ss") : "");
				out.setPlateNumber(objects[6] != null ? objects[6].toString() : "");
				out.setDriverName(objects[7] != null ? objects[7].toString() : "");
				out.setDriverBill(objects[8] != null ? objects[8].toString() : "");
				out.setTenantName(objects[9] != null ? SysTenantDefCacheDataUtil.getTenantName(Long.valueOf(objects[9].toString())) : "");
				out.setSourceOrgName(objects[10] != null ? OraganizationCacheDataUtil.getOrgName(Long.valueOf(objects[10].toString())) : "");
				out.setDescOrgName(objects[11] != null ? OraganizationCacheDataUtil.getOrgName(Long.valueOf(objects[11].toString())) : "");
				out.setOrderNum(objects[12] != null ? objects[12].toString() : "");
				out.setNumber(objects[13] != null ? objects[13].toString() : "");
				out.setVolume(objects[14] != null ? objects[14].toString() : "");
				out.setWeight(objects[15] != null ? objects[15].toString() : "");
				out.setFreight(objects[16] != null ? CommonUtil.parseDouble(Long.valueOf(objects[16].toString())) : "");
				out.setTotalFee(objects[17] != null ? CommonUtil.parseDouble(Long.valueOf(objects[17].toString())) : "");
				out.setCollectMoney(objects[18] != null ? CommonUtil.parseDouble(Long.valueOf(objects[18].toString())) : "");
				out.setRemarks(objects[19] != null ? objects[19].toString() : "");
				outList.add(out);
			}
		}
		Pagination p = new Pagination(page);
		p.setItems(outList);
		Map<String,Object> pageMap = new HashMap<String, Object>();
		pageMap = JsonHelper.parseJSON2Map(JsonHelper.json(p));
		String _sum = DataFormat.getStringKey(inParam, "_sum");
		if("1".equals(_sum)){
			OrdDepartQueryOutYQ sumDepartInfo= doQuerySum(inParam,ordDepartInfoYqSV);
			pageMap.put("sumData", sumDepartInfo);
		}
		return pageMap;
	}
	/**
	 * 汇总配载列表
	 * @param inParam
	 * @param ordDepartInfoYqSV
	 * @return
	 */
	private OrdDepartQueryOutYQ doQuerySum(Map<String,Object> inParam,OrdDepartInfoYqSV ordDepartInfoYqSV){
		List<Object[]> list = ordDepartInfoYqSV.doQuery(inParam, true).list();
		OrdDepartQueryOutYQ out = new OrdDepartQueryOutYQ();
		//SUM(d.ORDER_NUM),SUM(d.NUMBER),SUM(d.VOLUME),SUM(d.WEIGHT),SUM(d.FREIGHT),SUM(d.total_fee),SUM(d.COLLECT_MONEY)
		if (list!= null && list.size() > 0) {
			for (Object[] objects : list) {
				out.setOrderNum(objects[0] != null ? objects[0].toString() : "");
				out.setNumber(objects[1] != null ? objects[1].toString() : "");
				out.setVolume(objects[2] != null ? objects[2].toString() : "");
				out.setWeight(objects[3] != null ? objects[3].toString() : "");
				out.setFreight(objects[4] != null ? CommonUtil.parseDouble(Long.valueOf(objects[4].toString())) : "");
				out.setTotalFee(objects[5] != null ? CommonUtil.parseDouble(Long.valueOf(objects[5].toString())) : "");
				out.setCollectMoney(objects[6] != null ? CommonUtil.parseDouble(Long.valueOf(objects[6].toString())) : "");
			}
		}
		return out;
	}
	/**
	 * 查询配载详情web
	 * @param inParam
	 * @return
	 */
	public OrdDepartInfoOutWeb getOrdDepartInfoWeb(Map<String,Object> inParam){
		long batchNum = DataFormat.getLongKey(inParam, "batchNum");
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		List<Object[]> deaprtList = ordDepartInfoYqSV.getOrdDepartInfoWeb(batchNum).list();
		OrdDepartInfoOutWeb departInfoOutWeb = new OrdDepartInfoOutWeb();
		//d.BATCH_NUM,d.BATCH_NUM_ALIAS,d.STATE,d.LOAD_OP_NAME,d.LOAD_TIME,d.DEPART_TIME,
		//d.SOURCE_ORG_ID,d.DESC_ORG_ID,d.ARRIVAL_VEH_TIME,d.DRIVER_NAME,d.DRIVER_BILL,d.PLATE_NUMBER
		//,v.VEHICLE_TYPE,d.VOLUME,d.WEIGHT,d.TOTAL_FEE,d.COLLECT_MONEY
		if (deaprtList!=null&&deaprtList.size()>0) {
			for (Object[] objects : deaprtList) {
				departInfoOutWeb.setBatchNum(objects[0] != null ? objects[0].toString() : "");
				departInfoOutWeb.setBatchNumAlias(objects[1] != null ? objects[1].toString() : "");
				departInfoOutWeb.setState(objects[2] != null ? SysStaticDataUtil.getSysStaticDataCodeName("VEHICLE_STATE_YQ", objects[2].toString()) : "");
				departInfoOutWeb.setLoadOpName(objects[3] != null ? objects[3].toString() : "");;
				departInfoOutWeb.setLoadTime(objects[4] != null ? DateUtil.formatDateByFormat((Date)objects[4], "yyyy-MM-dd HH:mm:ss") : "");
				departInfoOutWeb.setDepartTime(objects[5] != null ? DateUtil.formatDateByFormat((Date)objects[5], "yyyy-MM-dd HH:mm:ss") : "");
				departInfoOutWeb.setSourceOrgName(objects[6] != null ? OraganizationCacheDataUtil.getOrgName(Long.valueOf(objects[6].toString())) : "");
				departInfoOutWeb.setDescOrgName(objects[7] != null ? OraganizationCacheDataUtil.getOrgName(Long.valueOf(objects[7].toString())) : "");
				departInfoOutWeb.setArrivalVehTime(objects[8] != null ? DateUtil.formatDateByFormat((Date)objects[8], "yyyy-MM-dd HH:mm:ss") : "");
				departInfoOutWeb.setDriverName(objects[9] != null ? objects[9].toString() : "");
				departInfoOutWeb.setDriverBill(objects[10] != null ? objects[10].toString() : "");
				departInfoOutWeb.setPlateNumber(objects[11] != null ? objects[11].toString() : "");
				departInfoOutWeb.setVehicleType(objects[12] != null ? SysStaticDataUtil.getSysStaticDataCodeName("VEHICLE_TYPE", objects[12].toString()) : "");
				departInfoOutWeb.setVolume(objects[13] != null ? objects[13].toString() : "");
				departInfoOutWeb.setWeight(objects[14] != null ? objects[14].toString() : "");
				departInfoOutWeb.setTotalFee(objects[15] != null ? CommonUtil.parseDouble(Long.valueOf(objects[15].toString())) : "");
				departInfoOutWeb.setCollectMoney(objects[16] != null ? CommonUtil.parseDouble(Long.valueOf(objects[16].toString())) : "");
			}
		}
		List<Object[]> childList = ordDepartInfoYqSV.getOrdDepartChild(batchNum).list();
		List<OrderDepartChildOutWeb> childWebList = new ArrayList<OrderDepartChildOutWeb>();
		//c.CHILD_ORDER_ID,c.TRACKING_NUM,c.CHILD_TRACKING_NUM_ALI,c.CHILD_ORDER_STATE,
//		o.CREATE_TIME,o.BILLING_ORG_ID,c.CURRENT_ORG_ID,c.DISPATCHING_ORG_ID,o.DES_CITY_NAME,
//		o.CONSIGNOR,o.CONSIGNOR_PHONE,o.CONSIGNOR_ADDRESS,o.CONSIGNEE,o.CONSIGNEE_PHONE,o.CONSIGNEE_ADDRESS,
//		o.INTERCHANGE,o.PAYMENT,o.PRODUCT_NAME,o.PACK_NAME,o.REMARKS
		if (childList!=null&&childList.size()>0) {
			for (Object[] objects : childList) {
				OrderDepartChildOutWeb out = new OrderDepartChildOutWeb();
				out.setChildOrderId(objects[0] != null ? objects[0].toString() : "");
				out.setTrackingNum(objects[1] != null ? objects[1].toString() : "");
				out.setChildTrackingNumAli(objects[2] != null ? objects[2].toString() : "");
				out.setChildOrderState(objects[3] != null ? SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE", objects[3].toString()) : "");
				out.setCreateTime(objects[4] != null ? DateUtil.formatDateByFormat((Date)objects[4], "yyyy-MM-dd HH:mm:ss") : "");
				out.setBillingOrgName(objects[5] != null ? OraganizationCacheDataUtil.getOrgName(Long.valueOf(objects[5].toString())) : "");
				out.setCurrentOrgName(objects[6] != null ? OraganizationCacheDataUtil.getOrgName(Long.valueOf(objects[6].toString())) : "");
				out.setDispatchingOrgName(objects[7] != null ? OraganizationCacheDataUtil.getOrgName(Long.valueOf(objects[7].toString())) : "");
				out.setDesCityName(objects[8] != null ? objects[8].toString() : "");
				out.setConsignor(objects[9] != null ? objects[9].toString() : "");
				out.setConsignorPhone(objects[10] != null ? objects[10].toString() : "");
				out.setConsignorAddress(objects[11] != null ? objects[11].toString() : "");
				out.setConsignee(objects[12] != null ? objects[12].toString() : "");
				out.setConsigneePhone(objects[13] != null ? objects[13].toString() : "");
				out.setConsigneeAddress(objects[14] != null ? objects[14].toString() : "");
				out.setInterchangeName(objects[15] != null ? SysStaticDataUtil.getSysStaticDataCodeName("INTERCHANGE_YUNQI", objects[15].toString()) : "");
				out.setPaymentName(objects[16] != null ? SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE_YQ", objects[16].toString()) : "");
				out.setProductName(objects[17] != null ? objects[17].toString() : "");
				out.setPackName(objects[18] != null ? objects[18].toString() : "");
				out.setRemarks(objects[19] != null ? objects[19].toString() : "");
				childWebList.add(out);
			}
		}
		departInfoOutWeb.setChildList(childWebList);
		return departInfoOutWeb;
	}
	
	/***
	 * tms到货逻辑
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> arrivalTMS(Map<String,Object> inParam){
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		BaseUser user = SysContexts.getCurrentOperator();
	
		
		OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		OrdStockTF ordStockTF = (OrdStockTF) SysContexts.getBean("ordStockTF");
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		boolean isDepart = ordDepartInfoYqSV.checkIsDepart(orderId);
		if (isDepart) {
			throw new BusinessException("到货错误！");
		}
		
		OrderInfo orderInfo = orderInfoTF.getOrderInfo(orderId);
		if (orderInfo.getOrderState() == SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN) {
			throw new BusinessException("到货失败！，运单状态为【"+SysStaticDataUtil.getSysStaticDataCodeName("INTERCHANGE_YUNQI", String.valueOf(orderInfo.getOrderState()))+"】不能到货！");
			
		}
		boolean isOwn = orderInfo.getInterchange() == SysStaticDataEnumYunQi.INTERCHANGE_YUNQI.TAKE_PICK ? true : false;
		if (isOwn) {
			orderInfo.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN);
		}else{
			orderInfo.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY);
		}
		orderInfoTF.doUpdate(orderInfo, user);
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		List<OrderInfoChild> childs = orderInfoChildTF.getOrderInfoChilds(orderId);
		if (childs!=null&&childs.size()>0) {
			for (OrderInfoChild orderInfoChild : childs) {
				if (isOwn) {
					orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN);
				}else{
					orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY);
				}
				orderInfoChild.setDispatchingId(user.getUserId());
				orderInfoChild.setDispatchingName(user.getUserName());
				ordStockTF.putInCarStorage(orderId, orderInfoChild.getDispatchingOrgId());
				orderInfoChildTF.doUpdate(orderInfoChild, user);
			}
		}
		OrdOrdersInfoTF ordOrdersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		OrdOrdersInfo ordersInfo = ordOrdersInfoTF.getOrdOrdersInfo(orderInfo.getOrdsId());
		if (isOwn) {
			ordersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN);
		}else{
			ordersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY);
		}
		ordOrdersInfoTF.doUpdateOrders(ordersInfo, user);
		OrdBusiPerson ordBusiPerson = ordOrdersInfoTF.getOrdBusiPerson(ordersInfo.getOrderId());
		ordBusiPerson.setDeliveryId(user.getUserId());
		ordBusiPerson.setDeliveryTime(new Date());
		ordBusiPerson.setDeliveryName(user.getUserName());
		ordBusiPerson.setDeliveryBill(user.getTelphone() != null ? user.getTelphone() : "");
		ordBusiPerson.setGxEndOpId(user.getUserId());
		ordBusiPerson.setGxEndOpName(user.getUserName());
		ordBusiPerson.setGxEndTime(new Date());
		ordOrdersInfoTF.doUpdateBusi(ordBusiPerson);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("info", "Y");
		return map;
	}
}
