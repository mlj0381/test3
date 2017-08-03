package com.wo56.business.ord.intf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.ord.impl.OrderInfoChildSV;
import com.wo56.business.ord.vo.OrdBusiPerson;
import com.wo56.business.ord.vo.OrdOrdersInfo;
import com.wo56.business.ord.vo.OrderInfo;
import com.wo56.business.ord.vo.OrderInfoChild;
import com.wo56.business.ord.vo.OrderInfoChildSign;
import com.wo56.business.ord.vo.OrderInfoTmsError;
import com.wo56.business.ord.vo.out.OrderChildStowagePlanOut;
import com.wo56.business.ord.vo.out.OrderInfoChildOutList;
import com.wo56.business.order.out.OrderPostUtil;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SmsYQUtil;
import com.wo56.common.utils.SysTenantDefCacheDataUtil;

public class OrderInfoChildTF {
	
	private static final Log log = LogFactory.getLog(OrderInfoChildTF.class);
	
	/**
	 * 拆分子运单
	 * @param orderInfo
	 */
	public List<OrderInfoChild> resolutionOrderInfo(OrderInfo orderInfo,BaseUser user){
		if (orderInfo == null || orderInfo.getNumber() == null || orderInfo.getNumber().intValue() <= 0) {
			throw new BusinessException("件数必须大于0！");
		}
		int number = orderInfo.getNumber();
		long orderId = orderInfo.getOrderId();
		String orderNumber = orderInfo.getOrderNumber();
		List<OrderInfoChild> orderInfoChilds = new ArrayList<OrderInfoChild>();
		OrderInfoChildSV orderInfoChildSV = (OrderInfoChildSV) SysContexts.getBean("orderInfoChildSV");
		OrdChildOpLogTF ordChildOpLogTF = (OrdChildOpLogTF) SysContexts.getBean("ordChildOpLogTF");
		for(int i = 1;i <= number ; i++){
			String orderNumberChild = "";
			String childTrackingNumAli = "";
			if (i<=9) {
				orderNumberChild = orderNumber +"0"+ i;
				childTrackingNumAli = orderNumber +"-0"+ i;
			}else{
				orderNumberChild = orderNumber + i;
				childTrackingNumAli = orderNumber +"-"+ i;
			}
			OrderInfoChild orderInfoChild = new OrderInfoChild();
			orderInfoChild.setOrderId(orderId);
			orderInfoChild.setTrackingNum(orderNumber);//运单号
			orderInfoChild.setChildTrackingNum(orderNumberChild);//子运单号
			orderInfoChild.setChildTrackingNumAli(childTrackingNumAli);
			orderInfoChild.setCreateId(user.getUserId());//创建人
			orderInfoChild.setCreateName(user.getUserName());//创建人名称
			orderInfoChild.setCreateTime(new Date());//创建时间
			orderInfoChild.setCurrentOrgId(user.getOrgId());//当前网点
			orderInfoChild.setTenantId(user.getTenantId());//当前租户
			orderInfoChild.setDispatchingOrgId(orderInfo.getArrivedOrgId() != null ? orderInfo.getArrivedOrgId() : -1);//到达
			orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN);//子运单状态
			orderInfoChildSV.doSaveOrUpdate(orderInfoChild, user);
			orderInfoChilds.add(orderInfoChild);
			//开单入库
			OrdStockTF stockTF = (OrdStockTF) SysContexts.getBean("ordStockTF");
			stockTF.putInOrgStorage(orderInfoChild.getChildOrderId(), user.getOrgId());
			
			//记录日志
			
			ordChildOpLogTF.departLog(orderInfoChild, 7, user, null);
		}
		return orderInfoChilds;
	}
	/***
	 * 选择子订单待配载列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> childStowagePlan(Map<String,Object> inParam){
		OrderInfoChildSV orderInfoChildSV = (OrderInfoChildSV) SysContexts.getBean("orderInfoChildSV");
		//List<Object[]> objects = orderInfoChildSV.orderInfoByChildOrderState(inParam).list();
		//List<OrderInfoChildOutList> orderInfoChildOutLists = new ArrayList<OrderInfoChildOutList>();
		//o.order_id,o.VOLUME,o.WEIGHT
		IPage page = PageUtil.gridPage(orderInfoChildSV.orderInfoByChild(inParam));
		
		//c.CHILD_ORDER_ID,c.CHILD_TRACKING_NUM_ALI,o.CONSIGNEE,o.DES_CITY_NAME,c.CHILD_ORDER_STATE,c.order_id
		List<OrderChildStowagePlanOut> child = new ArrayList<OrderChildStowagePlanOut>();
		//List<Long> ordersId = new ArrayList<Long>();
		List<Object[]> objects = page.getThisPageElements();
		if (objects != null && objects.size() > 0) {
			for (Object[] objects2 : objects) {
				OrderChildStowagePlanOut out = new OrderChildStowagePlanOut();
				out.setChildOrderId(objects2[0]!=null ? objects2[0].toString() : "");
				out.setChildTrackingNumAli(objects2[1] != null ? objects2[1].toString() : "");
				out.setConsigneeName(objects2[2] != null ? objects2[2].toString() : "");
				out.setDestCityName(objects2[3] != null ? objects2[3].toString() : "");
				out.setChildOrderState(objects2[4] != null ? SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE", objects2[4].toString()) : "");
				out.setOrderId(objects2[5] != null ? objects2[5].toString() : "");
				out.setVolume(objects2[6] != null ? objects2[6].toString() : "0");
				if (objects2[7]!=null&&StringUtils.isNotBlank(objects2[7].toString())) {
					out.setWeight(String.valueOf(Double.valueOf(objects2[7].toString()) / 1000));
				}else{
					out.setWeight("0");
				}
				out.setChildNumber(objects2[8] != null ? objects2[8].toString() : "0");
				child.add(out);
			}
		}
//		if (ordersId != null && ordersId.size() > 0) {
//			List<Object[]> list = orderInfoChildSV.childStowagePlan(ordersId).list();
//			if (orderInfoChildOutLists != null && orderInfoChildOutLists.size() > 0) {
//				for (OrderInfoChildOutList infoChildOutList : orderInfoChildOutLists) {
//					List<OrderChildStowagePlanOut> child = new ArrayList<OrderChildStowagePlanOut>();
//					if (list != null && list.size() > 0) {
//						for (Object[] objects2 : list) {
//							if (objects2[5] != null) {
//								if (infoChildOutList.getOrderId().equals(objects2[5].toString())) {
//									OrderChildStowagePlanOut out = new OrderChildStowagePlanOut();
//									out.setChildOrderId(objects2[0]!=null ? objects2[0].toString() : "");
//									out.setChildTrackingNumAli(objects2[1] != null ? objects2[1].toString() : "");
//									out.setConsigneeName(objects2[2] != null ? objects2[2].toString() : "");
//									out.setDestCityName(objects2[3] != null ? objects2[3].toString() : "");
//									out.setChildOrderState(objects2[4] != null ? SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE", objects2[4].toString()) : "");
//									child.add(out);
//								}
//							}
//						}
//					}
//					infoChildOutList.setChild(child);
//				}
//			}
//		}
		Pagination p = new Pagination(page);
		page.setThisPageElements(child);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	}
	/**
	 * 通过id查询子运单信息
	 * @param id
	 * @return
	 */
	public OrderInfoChild getOrderInfoChild(long id){
		OrderInfoChildSV orderInfoChildSV = (OrderInfoChildSV) SysContexts.getBean("orderInfoChildSV");
		return orderInfoChildSV.getOrderInfoChild(id);
	}
	/**
	 * 修改子运单
	 * @param orderInfoChild
	 * @param user
	 */
	public void doUpdate(OrderInfoChild orderInfoChild,BaseUser user){
		OrderInfoChildSV orderInfoChildSV = (OrderInfoChildSV) SysContexts.getBean("orderInfoChildSV");
		orderInfoChildSV.doSaveOrUpdate(orderInfoChild, user);
	}
	
	public void doUpdateByWeb(OrderInfoChild orderInfoChild,BaseUser user){
		OrderInfoChildSV orderInfoChildSV = (OrderInfoChildSV) SysContexts.getBean("orderInfoChildSV");
		orderInfoChildSV.doSaveOrUpdateByWeb(orderInfoChild, user);
	}
	/**
	 * 通过运单id获取子运单信息
	 * @return
	 */
	public List<OrderInfoChild> getOrderInfoChilds(long orderId){
		OrderInfoChildSV orderInfoChildSV = (OrderInfoChildSV) SysContexts.getBean("orderInfoChildSV");
		return orderInfoChildSV.getOrderInfoChilds(orderId);
	}
	
	
	/**
	 * 获取子运单状态
	 * @param orderId
	 * @return
	 */
	public List<Object> getChildOrderStates(long orderId){
		OrderInfoChildSV orderInfoChildSV = (OrderInfoChildSV) SysContexts.getBean("orderInfoChildSV");
		return orderInfoChildSV.getOrderState(orderId);
	}
	/**
	 * 批次orderId获取多个运单
	 * @param orderIds
	 * @return
	 */
	public List<OrderChildStowagePlanOut> getOrdDepartOrder(Set<Long> orderIds){
		OrderInfoChildSV orderInfoChildSV = (OrderInfoChildSV) SysContexts.getBean("orderInfoChildSV");
		List<Object[]> objects = orderInfoChildSV.getOrderInfoChildByOrderIds(orderIds).list();
		List<OrderChildStowagePlanOut> outList = new ArrayList<OrderChildStowagePlanOut>();
		if (objects != null && objects.size() > 0) {
			for (Object[] objects2 : objects) {
				OrderChildStowagePlanOut out  = new OrderChildStowagePlanOut();
				out.setChildOrderId(objects2[0]!=null ? objects2[0].toString() : "");
				out.setChildTrackingNumAli(objects2[1] != null ? objects2[1].toString() : "");
				out.setConsigneeName(objects2[2] != null ? objects2[2].toString() : "");
				out.setDestCityName(objects2[3] != null ? objects2[3].toString() : "");
				out.setChildOrderState(objects2[4] != null ? SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE", objects2[4].toString()) : "");
				out.setOrderId(objects2[5] != null ? objects2[5].toString() : "");
				out.setVolume(objects2[6] != null ? objects2[6].toString() : "");
				if (objects2[7]!=null&&StringUtils.isNotBlank(objects2[7].toString())) {
					out.setWeight(String.valueOf(Double.valueOf(objects2[7].toString()) / 1000));
				}else{
					out.setWeight("0");
				}
				out.setIsSplit(objects2[8] != null ? objects2[8].toString() : "0");
				out.setChildNumber(objects2[9] != null ? objects2[9].toString() : "0");
				outList.add(out);
			}
		}
		return outList;
	}
	/**
	 * 查询子运单
	 * @param childTrackingNum
	 * @return
	 */
	public OrderInfoChild getOrderInfoChildByChildTrackingNumber(String childTrackingNum, long childOrderId){
		OrderInfoChildSV orderInfoChildSV = (OrderInfoChildSV) SysContexts.getBean("orderInfoChildSV");
		OrderInfoChild orderInfoChild = null;
		if (childOrderId > 0) {
			orderInfoChild = orderInfoChildSV.getOrderInfoChild(childOrderId);
		}else{
			orderInfoChild = orderInfoChildSV.getOrderInfoChildByChildTrackingNum(childTrackingNum);
			if (orderInfoChild == null) {
				throw new BusinessException("无该子运单信息！！");
			}
		}
		
		
		return orderInfoChild;
	}
	
	/***
	 * 存在未到货的数据
	 * >0 存在未到货
	 * -1 不存在未到货
	 * @param batchNum
	 * @return
	 */
	public int checkOrderChildState(long batchNum){
		return ((OrderInfoChildSV)SysContexts.getBean("orderInfoChildSV")).checkOrderChildState(batchNum);
	}
	/**
	 * 扫描获取待配载数据【601071】
	 * @param childTrackingNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> sweepChildTrackingNum(String childTrackingNum){
		OrderInfoChildSV orderInfoChildSV = (OrderInfoChildSV) SysContexts.getBean("orderInfoChildSV");
		List<Object[]> objects = orderInfoChildSV.sweepChildTrackingNum(childTrackingNum).list();
		OrderChildStowagePlanOut out  = new OrderChildStowagePlanOut();
		//c.CHILD_ORDER_ID,c.CHILD_TRACKING_NUM_ALI,o.CONSIGNEE,o.DES_CITY_NAME,c.CHILD_ORDER_STATE,o.weight,o.volume,o.orderId,o.number
		if (objects != null && objects.size() > 0) {
			for (Object[] objects2 : objects) {
				out.setChildOrderId(objects2[0]!=null ? objects2[0].toString() : "");
				out.setChildTrackingNumAli(objects2[1] != null ? objects2[1].toString() : "");
				out.setConsigneeName(objects2[2] != null ? objects2[2].toString() : "");
				out.setDestCityName(objects2[3] != null ? objects2[3].toString() : "");
				int state = objects2[4] != null ? Integer.valueOf(objects2[4].toString()) : -1;
				out.setChildOrderState(state > 0 ? SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE", String.valueOf(state)) : "");
				if (state != SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN) {
					throw new BusinessException("该子运单号【"+out.getChildTrackingNumAli()+"】，状态为【"+out.getChildOrderState()+"】不能配载！");
				}
				out.setWeight(objects2[5] != null ? String.valueOf(Double.valueOf(StringUtils.isNotBlank(objects2[5].toString()) ? objects2[5].toString() : "0") / 1000) : "");
				out.setVolume(objects2[6] != null ? objects2[6].toString() : "");
				out.setOrderId(objects2[7] != null ? objects2[7].toString() : "");
				out.setChildNumber(objects2[8] != null ? objects2[8].toString() : "");
			}
		}else{
			throw new BusinessException("无该子运单号信息！");
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(out));
	}
	/**
	 * 配送【601050】
	 * @param inParam
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> dispatching(Map<String,Object> inParam) throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		String tmsklinfo = DataFormat.getStringKey(inParam, "tmsklinfo");
		List<String> childOrderIds = (List<String>) inParam.get("childOrderIds");
		if (childOrderIds == null || childOrderIds.size() < 0) {
			throw new BusinessException("请选择需要配送的子运单！");
		}
		//OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		Set<Long> orderIds = new HashSet<Long>();
		OrdChildOpLogTF ordChildOpLogTF = (OrdChildOpLogTF) SysContexts.getBean("ordChildOpLogTF");
		for (String childOrderId : childOrderIds) {
			OrderInfoChild orderInfoChild = getOrderInfoChild(Long.valueOf(childOrderId));
			if (orderInfoChild.getChildOrderState() != SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY) {
				throw new BusinessException("该子运单【"+orderInfoChild.getChildTrackingNumAli()+"】，不在待配送状态不能操作配送！");
			}
			orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN);
			orderInfoChild.setDispatchingId(user.getUserId());
			orderInfoChild.setDispatchingName(user.getUserName());
			orderInfoChild.setDispatchingTime(new Date());
			orderIds.add(orderInfoChild.getOrderId());
			doUpdate(orderInfoChild, user);
			if (StringUtils.isNotEmpty(tmsklinfo)) {
				orderInfoChild.setCurrentOrgId(orderInfoChild.getDispatchingOrgId());
			}
			//配送日志
			ordChildOpLogTF.departLog(orderInfoChild, 10, user, null);
		}
		OrderInfoTF orderInfoTF = (OrderInfoTF) SysContexts.getBean("orderInfoTF");
		OrdOrdersInfoTF ordOrdersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		OrderInfoTmsErrorTF orderInfoTmsErrorTF = (OrderInfoTmsErrorTF) SysContexts.getBean("orderInfoTmsErrorTF");
		Session session = SysContexts.getEntityManager(true);
		for (Long orderId : orderIds) {
			//运单表
			OrderInfo orderInfo = orderInfoTF.getOrderInfo(orderId);
			//订单表
			OrdOrdersInfo ordOrdersInfo = ordOrdersInfoTF.getOrdOrdersInfo(orderInfo.getOrdsId());
			//订单关系人物表
			OrdBusiPerson ordBusiPerson = ordOrdersInfoTF.getOrdBusiPerson(ordOrdersInfo.getOrderId());
			boolean isDispatch = orderInfo.getOrderState() == SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY ? true : false;
			if (isDispatch) {
				//保存运单
				int trackingState = orderInfoTF.maxOrderState(orderId);
				orderInfo.setOrderState(trackingState);
				orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.WAIT_SIGN);
				orderInfoTF.doUpdate(orderInfo,user);
				
				//保存订单
				ordOrdersInfo.setOrderState(ordOrdersInfoTF.maxOrdersState(trackingState));
				ordOrdersInfoTF.doUpdateOrders(ordOrdersInfo, user);
				//保存关系人物表
				ordBusiPerson.setDeliveryId(user.getUserId());
				ordBusiPerson.setDeliveryBill(user.getTelphone());
				ordBusiPerson.setDeliveryName(user.getUserName());
				ordBusiPerson.setDeliveryTime(new Date());
				ordOrdersInfoTF.doUpdateBusi(ordBusiPerson);
				// 写日志
				OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
				ordLogNewTF.deliveryOrderLog(user.getUserId(), user, ordOrdersInfo, SysTenantDefCacheDataUtil.getTenantName(user.getTenantId()), user.getUserName(), OraganizationCacheDataUtil.getOrgName(user.getOrgId()));
				
				//TODO 推送信息 （系统自动推送消息给到收货人，发货人）
				if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
					if (!ordOrdersInfoTF.isWeChatUser(session, ordOrdersInfo.getConsigneeBill())) {
						SmsYQUtil.distributionSendConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(),ordOrdersInfo.getOrderNo(),user.getUserName(), user.getTelphone(),  ordOrdersInfo.getConsigneeBill(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE);
					}
					SmsYQUtil.distributionSendConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(),ordOrdersInfo.getOrderNo(),user.getUserName(), user.getTelphone(),  ordOrdersInfo.getConsigneeBill(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, ordOrdersInfoTF.isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE);
				}
				if (ordOrdersInfo.getCreateId() != null) {
					CmUserInfo cmUserInfo = (CmUserInfo) session.get(CmUserInfo.class, ordOrdersInfo.getCreateId());
					if (!cmUserInfo.getLoginAcct().equals( ordOrdersInfo.getConsigneeBill())) {
						if (StringUtils.isNotEmpty(cmUserInfo.getOpenId())) {
							SmsYQUtil.distributionSendConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(),ordOrdersInfo.getOrderNo(),user.getUserName(), user.getTelphone(),  cmUserInfo.getLoginAcct(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE);
						}
						SmsYQUtil.distributionSendConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(),ordOrdersInfo.getOrderNo(),user.getUserName(), user.getTelphone(),  cmUserInfo.getLoginAcct(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.WECHAT_TYPE);
					}
				}
				if (CommonUtil.tmsTenantId("TMS_KL") == user.getTenantId().longValue() && StringUtils.isEmpty(tmsklinfo)) {
					//调柯莱TML配送
					String isDispatcheing = OrderPostUtil.dispatcheingKL(orderInfo.getOrderNumber(), user.getUserName(), DateUtil.formatDate(ordOrdersInfo.getOpDate(), "yyyy-MM-dd"), user.getTenantId(),false);
					if (!"Y".equals(isDispatcheing)) {
						log.error(isDispatcheing);
						OrderInfoTmsError orderInfoTmsError = new OrderInfoTmsError();
						orderInfoTmsError.setOrderNumber(orderInfo.getOrderNumber());
						orderInfoTmsError.setUserName(user.getUserName());
						orderInfoTmsError.setCreateTime(ordOrdersInfo.getOpDate());
						orderInfoTmsError.setTenantId(user.getTenantId());
						orderInfoTmsError.setOrderId(orderInfo.getOrderId());
						orderInfoTmsError.setOrdsId(orderId);
						orderInfoTmsError.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY);
						orderInfoTmsErrorTF.doSaveOrderInfoTmsError(orderInfoTmsError);
					}
				}
			}
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
	}
	/**
	 * 查询待签收的数据子运单
	 * @param orderId
	 * @return
	 */
	public List<OrderInfoChild> getOrderInfoChildsByState(long orderId){
		return ((OrderInfoChildSV)SysContexts.getBean("orderInfoChildSV")).sign(orderId);
	}
	
	public void doSaveSign(OrderInfoChildSign orderInfoChildSign){
		((OrderInfoChildSV)SysContexts.getBean("orderInfoChildSV")).doSaveSign(orderInfoChildSign);
	}
	/**
	 * 获取运单下子运单状态的子运单id
	 * @param orderId
	 * @return
	 */
	public List<Long> getOrderInfoChildId(long orderId,int state){
		return ((OrderInfoChildSV)SysContexts.getBean("orderInfoChildSV")).getOrderInfoChildId(orderId,state);
	}
	public List<OrderInfoChild> getOrderInfoByChildOrderId(long childOrderId) {
		// TODO Auto-generated method stub
		return ((OrderInfoChildSV)SysContexts.getBean("orderInfoChildSV")).getOrderInfoByChildOrderId(childOrderId);
	}
	
	public List<OrderInfoChild> getChildTrackingNum(String trackingNum,long tenantId){
		return ((OrderInfoChildSV)SysContexts.getBean("orderInfoChildSV")).getChildTrackingNum(trackingNum, tenantId);
	}
	/**
	 * 通过订单主键查询运单下的待配送的单
	 */
	public List<String> getchildIdByOrdsId(long ordsId){
		return ((OrderInfoChildSV)SysContexts.getBean("orderInfoChildSV")).getchildIdByOrdsId(ordsId);
	}
}
