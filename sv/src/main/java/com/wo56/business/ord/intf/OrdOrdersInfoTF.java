package com.wo56.business.ord.intf;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysCfg;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.encrypt.K;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysCfgUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ac.impl.AcWalletRelSV;
import com.wo56.business.ac.interfaces.AcMyWalletTF;
import com.wo56.business.ac.vo.AcWalletRel;
import com.wo56.business.cm.intf.CmAreaTF;
import com.wo56.business.cm.intf.CmUserInfoPullTF;
import com.wo56.business.cm.intf.CmUserOrgRelTF;
import com.wo56.business.cm.vo.CmPullBlack;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.business.grabOrder.out.BusiGrabConsts;
import com.wo56.business.grabOrder.out.BusiGrabControlOut;
import com.wo56.business.ord.impl.OrdDepartInfoYqSV;
import com.wo56.business.ord.vo.OrdBusiPerson;
import com.wo56.business.ord.vo.OrdGoodsInfo;
import com.wo56.business.ord.vo.OrdOrdersInfo;
import com.wo56.business.ord.vo.OrdSignInfo;
import com.wo56.business.ord.vo.OrderFee;
import com.wo56.business.ord.vo.OrderInfo;
import com.wo56.business.ord.vo.OrderInfoChild;
import com.wo56.business.ord.vo.OrderInfoTmsError;
import com.wo56.business.ord.vo.out.OrdOrdersDetailOutParam;
import com.wo56.business.ord.vo.out.OrdOrdersInfoOutParam;
import com.wo56.business.ord.vo.out.OrdPickDetailOutParam;
import com.wo56.business.ord.vo.out.OrdPickInfoOutParam;
import com.wo56.business.ord.vo.out.OrdQueryByWeChatOut;
import com.wo56.business.ord.vo.out.OrdQueryDetailByWeChatOut;
import com.wo56.business.ord.vo.out.OrderNoOut;
import com.wo56.business.ord.vo.out.ZxOrdListOutParam;
import com.wo56.business.ord.vo.out.ZxOrdViewOutParam;
import com.wo56.business.order.out.OrderPostUtil;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.sys.impl.SysAttachSV;
import com.wo56.business.sys.vo.SysTenantDef;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SmsYQUtil;
import com.wo56.common.utils.SysTenantDefCacheDataUtil;
/***
 * 下单模块的类
 * @author zjy
 *
 */
public class OrdOrdersInfoTF {
	private static final Log log = LogFactory.getLog(OrdOrdersInfoTF.class);
	
//	private static Long[] TMS_TENANT_ID = new Long[]{tmsTenantId("TMS_KL")};
	
//	private static long tmsTenantId(String tms){
//		List<SysStaticData> list =SysStaticDataUtil.getSysStaticDataList("TMS_TENANT_YQ");
//		if (list!=null&&list.size()>0) {
//			for (SysStaticData sysStaticData : list) {
//				if (tms.equals(sysStaticData.getCodeTypeAlias())) {
//					return Long.valueOf(sysStaticData.getCodeValue());
//				}
//			}
//		}
//		return -1;
//	}
	
	/***
	 * 判断是否是注册用户
	 * @param loginAcct
	 * @return
	 */
	public boolean isRegisterUser(Session session,String loginAcct){

		Query query = session.createSQLQuery("SELECT COUNT(1) FROM CM_USER_INFO WHERE LOGIN_ACCT=:loginAcct and user_type != 11 ");
		query.setParameter("loginAcct", loginAcct);
		 BigInteger count = (BigInteger) query.uniqueResult();
		if(count.intValue()>0){
			return true;
		}
		return false;
	}
	
	private void doSaveOrderInfoTmsError(OrderInfoTmsError orderInfoTmsError,Session session){
		Criteria ca = session.createCriteria(OrderInfoTmsError.class);
		ca.add(Restrictions.eq("orderNumber", orderInfoTmsError.getOrderNumber()));
		ca.add(Restrictions.eq("orderState", orderInfoTmsError.getOrderState()));
		List<OrderInfoTmsError> orderInfoTmsErrors =  ca.list();
		if (orderInfoTmsErrors == null || orderInfoTmsErrors.size() <= 0) {
			orderInfoTmsError.setSendFlag(0);
			session.save(orderInfoTmsError);
		}
	}
	/**
	 * 判断是否是微信用户
	 * false 存在openid
	 * true 不存在
	 */
	public boolean isWeChatUser(Session session,String loginAcct){
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
	 * 下单保存
	 * @param inParam
	 * 
	 * */
	public String  saveOrders(Map<String,Object> inParam) throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager();
		int orderType = DataFormat.getIntKey(inParam, "orderType");
		long consigneeId = DataFormat.getLongKey(inParam, "consigneeId");
		long provinceId = DataFormat.getLongKey(inParam, "provinceId");
		long cityId = DataFormat.getLongKey(inParam, "cityId");
		long companyId = DataFormat.getLongKey(inParam, "companyId");
		String companyName = DataFormat.getStringKey(inParam, "companyName");
		String workerPhone = DataFormat.getStringKey(inParam, "workerPhone");
		if (StringUtils.isNotEmpty(workerPhone)) {
			if (!CommonUtil.isCheckPhone(workerPhone)&&!CommonUtil.isCheckMobiPhone(workerPhone)) {
				throw new BusinessException("请输入正确的拉包工电话！");
			}
		}
//		if(consigneeId<0){
//			throw new BusinessException("收货人不能为空");
//		}
		if(orderType<0){
			throw new BusinessException("下单类型不能为空");
		}
		if(provinceId<0){
			throw new BusinessException("到站省份不能为空");
		}
		if(cityId<0){
			throw new BusinessException("到站城市不能为空");
		}
		if(StringUtils.isEmpty(SysStaticDataUtil.getCityDataList ("SYS_CITY", cityId+"").getName())){
			throw new BusinessException("到站城市值存在异常，请检查!");
		}
		/*if(StringUtils.isEmpty(workerPhone)&&companyId<0){
			throw new BusinessException("抱歉，拉包工跟承运专线必须二选一!");
		}*/
		JSONArray fromObject = JSONArray.fromObject(inParam.get("consignorInfo"));
		if(fromObject==null|| fromObject.size()==0){
			throw new BusinessException("发货信息不能为空!");
		}
		int count=0;
		String products="";
		Double weight=0.0;
		Double volume=0.0;
		String lat="";
		String lon="";
		String orderNo=CommonUtil.getOrderNO();
		List<OrdGoodsInfo> arrayList = new ArrayList<OrdGoodsInfo>();
		Date d = new Date();
		
		String planPickupTimeRedis="";
		String pickAddr="";
		int i=0;
		CmAreaTF cmAreaTF = (CmAreaTF) SysContexts.getBean("cmAreaTF");
		Iterator iterator=  fromObject.iterator();
		long idNo = -1;
		while (iterator.hasNext()) {
			JSONObject  myJson = (JSONObject)iterator.next();
			Map m = myJson;
			String planPickupTime = DataFormat.getStringKey(m, "planPickupTime");
			OrdGoodsInfo goodsInfo = new OrdGoodsInfo();
			BeanUtils.populate(goodsInfo,m);
			long consignorId = DataFormat.getLongKey(m, "consignorId");
			
			if(StringUtils.isEmpty(goodsInfo.getConsignorName())){
				throw new BusinessException("发货人名称不能为空");
			}
			if(StringUtils.isEmpty(goodsInfo.getConsignorBill())){
				throw new BusinessException("发货人电话不能为空");
			}
			if(!CommonUtil.isCheckMobiPhone(goodsInfo.getConsignorBill())&&!CommonUtil.isCheckPhone(goodsInfo.getConsignorBill())){
				throw new BusinessException("发货人电话格式不正确!");
			}
			if(goodsInfo.getProvinceId()==null||goodsInfo.getProvinceId()<0){
				throw new BusinessException("提货省份不能为空!");
			}
			if(goodsInfo.getCityId()==null||goodsInfo.getCityId()<0){
				throw new BusinessException("提货城市不能为空!");
			}
			if(StringUtils.isEmpty(goodsInfo.getAddress())){
				throw new BusinessException("发货人详细地址不能为空");
			}
			
			if(StringUtils.isNotEmpty(planPickupTime)){
				//多个提货点的话，只是取第一个的提货时间
				if(i==0){
					planPickupTimeRedis=planPickupTime;
				}
				
				goodsInfo.setPlanPickupTime(DateUtil.parseDate(planPickupTime, DateUtil.DATETIME_FORMAT1));
			}
			if(i==0){
				pickAddr=goodsInfo.getAddress();
			}
			
			i=i+1;
			
			goodsInfo.setConsignorId(consignorId);
			goodsInfo.setCreateDate(d);
			goodsInfo.setOpDate(d);
			goodsInfo.setOpId(user.getUserId());
			goodsInfo.setCreateId(user.getUserId());
			goodsInfo.setIsPickup(0);
			goodsInfo.setOrderNo(orderNo);
			arrayList.add(goodsInfo);
			count=count+(goodsInfo.getCount()==null?0:goodsInfo.getCount());
			if(StringUtils.isNotEmpty(goodsInfo.getProduct())){
				if(StringUtils.isNotEmpty(products)){
					products=products+","+goodsInfo.getProduct();
				}else{
					products=goodsInfo.getProduct();
				}
			}
			weight=weight+(StringUtils.isEmpty(goodsInfo.getWeight())?0.0D:Double.parseDouble(goodsInfo.getWeight()));
			volume=volume+(StringUtils.isEmpty(goodsInfo.getVolume())?0.0D:Double.parseDouble(goodsInfo.getVolume()));
			if(StringUtils.isEmpty(lat)){
				lat=StringUtils.isEmpty(goodsInfo.getLat())?"":goodsInfo.getLat();
			}
			if(StringUtils.isEmpty(lon)){
				lon=StringUtils.isEmpty(goodsInfo.getLon())?"":goodsInfo.getLon();
			}
			if (StringUtils.isEmpty(goodsInfo.getLon()) || StringUtils.isEmpty(goodsInfo.getLat())) {
				Map<String,String> map =cmAreaTF.getCmAreaByCityOrAddress(
						goodsInfo.getProvinceId() !=null ? goodsInfo.getProvinceId() : -1
						, goodsInfo.getCityId() != null ? goodsInfo.getCityId() : -1, 
						goodsInfo.getCountyId() != null ? goodsInfo.getCountyId() : -1,
						goodsInfo.getAddress());
				goodsInfo.setLon(map.get("longitude"));
				goodsInfo.setLat(map.get("latitude"));
			}
			
		}
		//consignorInfo.
		OrdOrdersInfo ordOrdersInfo = new OrdOrdersInfo();
		OrdBusiPerson ordBusiPerson = new OrdBusiPerson();
		BeanUtils.populate(ordOrdersInfo, inParam);
		BeanUtils.populate(ordBusiPerson, inParam);

		if(StringUtils.isEmpty(ordOrdersInfo.getConsigneeName())){
			throw new BusinessException("收货人名称不能为空");
		}
		if(StringUtils.isEmpty(ordOrdersInfo.getConsigneeBill())){
			throw new BusinessException("收货人电话不能为空");
		}
		if(!CommonUtil.isCheckMobiPhone(ordOrdersInfo.getConsigneeBill())&&!CommonUtil.isCheckPhone(ordOrdersInfo.getConsigneeBill())){
			throw new BusinessException("收货人电话格式不正确!");
		}
		if(ordOrdersInfo.getPaymentType()==null||ordOrdersInfo.getPaymentType()<0){
			throw new BusinessException("付款方式不能为空");
		}
		if(ordOrdersInfo.getServiceType()==null||ordOrdersInfo.getServiceType()<0){
			throw new BusinessException("配送方式不能为空");
		}
		if(volume!=null&&!"null".equals(volume)){
			ordOrdersInfo.setVolume(volume.toString());
		}
		if(weight!=null&&!"null".equals(volume)){
		ordOrdersInfo.setWeight(weight.toString());
		}
		ordOrdersInfo.setOrderNo(orderNo);
		ordOrdersInfo.setOrderNum(count);
		ordOrdersInfo.setProducts(products);
		ordOrdersInfo.setOpDate(d);
		ordOrdersInfo.setOpId(user.getUserId());
		ordOrdersInfo.setCreateDate(d);
		if(user.getUserType().intValue()!=SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
			if(orderType==1){
				ordOrdersInfo.setCreateType(1);
			}else{
				ordOrdersInfo.setCreateType(2);
			}
		}else{
			ordOrdersInfo.setCreateType(3);
		}
		String tips="发货成功，是否继续发货";
		if(orderType==2){
			tips="订货成功，是否继续订货";
		}
		ordOrdersInfo.setCreateId(user.getUserId());
		if(StringUtils.isEmpty(ordBusiPerson.getWorkerPhone()) &&user.getUserType().intValue()!=SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
			ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING);
		}else{
			ordOrdersInfo.setDisId(user.getUserId());
			ordOrdersInfo.setDisTime(d);
			ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP);
		}
		ordOrdersInfo.setTenantId(companyId);
		ordOrdersInfo.setConTenantId(companyId);
		ordOrdersInfo.setCompanyName(companyName);
		session.save(ordOrdersInfo);
		for (OrdGoodsInfo ordGoodsInfo : arrayList) {
			ordGoodsInfo.setOrderId(ordOrdersInfo.getOrderId());
			session.save(ordGoodsInfo);
			idNo = ordGoodsInfo.getIdNo();
		}
		ordOrdersInfo.setIdNo(idNo);
		ordBusiPerson.setOrderId(ordOrdersInfo.getOrderId());
		ordBusiPerson.setCreateDate(d);
		ordBusiPerson.setCreateId(user.getUserId());
		CmUserInfoPullTF cmUserInfoPullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
		//当前用户不是拉包工，且下单选择了拉包工
		if(user.getUserType().intValue()!=SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL&&StringUtils.isNotEmpty(ordBusiPerson.getWorkerPhone())){
			ordOrdersInfo.setDisTime(d);
			ordOrdersInfo.setDisId(user.getUserId());
			ordOrdersInfo.setDisOpName(user.getUserName());
			Criteria ca = session.createCriteria(CmUserInfo.class);
			ca.add(Restrictions.eq("loginAcct", ordBusiPerson.getWorkerPhone()));
			ca.add(Restrictions.eq("userType", SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL));
			CmUserInfo cmUserInfo = (CmUserInfo) ca.uniqueResult();
			if(cmUserInfo!=null){
				ordBusiPerson.setWorkerName(cmUserInfo.getUserName());
				ordBusiPerson.setWorkerId(cmUserInfo.getUserId());
				CmUserInfoPull byUserPull = cmUserInfoPullTF.getByUserPull(cmUserInfo.getUserId());
				if(byUserPull!=null){
					//数据库加上 接单数
					CmUserInfoPull cmUserInfoPull= cmUserInfoPullTF.addSingularNum(byUserPull.getUserId());
					if (cmUserInfoPull != null) {
						//匹配更新接单数
						int singularNum=0;
						if(byUserPull.getSingularNum()!=null){
							singularNum=byUserPull.getSingularNum().intValue();
						}
						BusiGrabControlOut.updateUserInfoSingularNum(byUserPull.getUserId(), singularNum);
//						BusiMatchControlYq.updatePullLoadNum(byUserPull.getUserId(), byUserPull.getTenantId(), cmUserInfoPull.getSingularNum(),cmUserInfoPull.getDefaultSingularNum());
					}
				}
			}
		}else if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
			ordOrdersInfo.setDisTime(d);
			ordOrdersInfo.setDisId(user.getUserId());
			ordOrdersInfo.setDisOpName(user.getUserName());
			ordBusiPerson.setWorkerName(user.getUserName());
			ordBusiPerson.setWorkerId(user.getUserId());
			ordBusiPerson.setWorkerPhone(user.getTelphone());
			CmUserInfoPull byUserPull = cmUserInfoPullTF.getByUserPull(user.getUserId());
			if(byUserPull!=null){
				//数据库加上 接单数
				CmUserInfoPull cmUserInfoPull= cmUserInfoPullTF.addSingularNum(byUserPull.getUserId());
				//匹配更新接单数
				BusiGrabControlOut.updateUserInfoSingularNum(cmUserInfoPull.getUserId(), cmUserInfoPull.getSingularNum().intValue());
//				BusiMatchControlYq.updatePullLoadNum(byUserPull.getUserId(), byUserPull.getTenantId(),cmUserInfoPull.getSingularNum(),cmUserInfoPull.getDefaultSingularNum());
			}
		}
		session.save(ordBusiPerson);
		//推送或者发送短信
		saveOrderPush(session, user, ordOrdersInfo, arrayList, ordBusiPerson);
		//写操作日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.saveOrderLog(user.getUserId(), orderType, StringUtils.defaultString(user.getTelphone(),""), user, ordOrdersInfo);
		if(user.getUserType().intValue()!=SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL&&
				StringUtils.isEmpty(ordBusiPerson.getWorkerPhone())&&ordOrdersInfo.getConTenantId()>0){
			//自动派单 添加到redis
//			BusiMatchControlYq.addOrderInfo(ordOrdersInfo.getOrderId(), ordOrdersInfo.getConTenantId(),lat, lon, ordOrdersInfo.getCreateId());
			
		}
		if(SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING==ordOrdersInfo.getOrderState()){
			//抢单
			String createDate=DateUtil.formatDate(ordOrdersInfo.getCreateDate(), DateUtil.DATETIME_FORMAT);
			BusiGrabControlOut.addOrderInfo(String.valueOf(ordOrdersInfo.getOrderId()), ordOrdersInfo.getCreateId(), ordOrdersInfo.getConTenantId(),  
					 Double.valueOf(StringUtils.isNotBlank(lat) ? lat :"0"),Double.valueOf(StringUtils.isNotBlank(lon) ? lon : "0"), ordOrdersInfo.getOrderNo(), createDate, planPickupTimeRedis, pickAddr,ordOrdersInfo.getCityName());
			
		}
		return tips;
	}
	/***
	 * 下单推送逻辑
	 * @param session
	 * @param user
	 * @param ordOrdersInfo
	 * @param arrayList
	 * @param ordBusiPerson
	 * @throws Exception 
	 */
	private void saveOrderPush(Session session, BaseUser user,OrdOrdersInfo ordOrdersInfo,List<OrdGoodsInfo> arrayList,OrdBusiPerson ordBusiPerson) throws Exception{
		OrdGoodsInfo goodsInfo = arrayList.get(0);
		//去掉重复的电话号码
		Set<String> consignorBillSet = new HashSet<String>();
		for (OrdGoodsInfo ordGoodsInfo : arrayList) {
			if (StringUtils.isNotEmpty(ordGoodsInfo.getConsignorBill())) {
				consignorBillSet.add(ordGoodsInfo.getConsignorBill());
			}
		}
		if (user.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL) {//拉包工下单
			for (String consignorBill : consignorBillSet) {
				if(StringUtils.isNotEmpty(consignorBill)){
					if(isRegisterUser(session, consignorBill)){
						SmsYQUtil.sendOrderBusi(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), user.getUserName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, ordOrdersInfo.getOrderId(), consignorBill);
					}else{
						SmsYQUtil.sendSmsOrderBusi(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), user.getUserName(),  ordOrdersInfo.getOrderId(), consignorBill);
					}
					if (!isWeChatUser(session, consignorBill)) {
						SmsYQUtil.sendOrderBusi(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderNo(), user.getUserName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), consignorBill);
					}
				}
				if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
					if (!isWeChatUser(session, ordOrdersInfo.getConsigneeBill())) {
						SmsYQUtil.sendSmsOrderReceiver(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), user.getUserName(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getConsigneeBill());
					}
					SmsYQUtil.sendSmsOrderReceiver(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), user.getUserName(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getConsigneeBill());
				}
				
			}
		}else{
			if (ordOrdersInfo.getOrderType().intValue() == 1) {//发货下单 
				if (!user.getTelphone().equals(goodsInfo.getConsignorBill())) {//发货人不等于当前登录人时
					if (!isWeChatUser(session, goodsInfo.getConsignorBill())) {
						SmsYQUtil.sendOrderBusi(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderNo(),user.getUserName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), goodsInfo.getConsignorBill());
					}
					if(isRegisterUser(session, goodsInfo.getConsignorBill())){
						SmsYQUtil.sendOrderBusi(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), user.getUserName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, ordOrdersInfo.getOrderId(), goodsInfo.getConsignorBill());
					}else{
						SmsYQUtil.sendSmsOrderBusi(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), user.getUserName(),  ordOrdersInfo.getOrderId(), goodsInfo.getConsignorBill());
					}
				}
				if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
					if (!isWeChatUser(session, ordOrdersInfo.getConsigneeBill())) {
						SmsYQUtil.sendSmsOrderReceiver(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), user.getUserName(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getConsigneeBill());
					}
					SmsYQUtil.sendSmsOrderReceiver(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), user.getUserName(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getConsigneeBill());
				}
			}else if(ordOrdersInfo.getOrderType().intValue() == 2){//收货下单
				if (!user.getTelphone().equals(ordOrdersInfo.getConsigneeBill())) {//收货人不等于当前登录时，下单人为当前用户名
					if (!isWeChatUser(session, ordOrdersInfo.getConsigneeBill())) {
						SmsYQUtil.sendSmsOrderReceiver(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), user.getUserName(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getConsigneeBill());
					}
					SmsYQUtil.sendSmsOrderReceiver(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), user.getUserName(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getConsigneeBill());
				}
				
				if (consignorBillSet != null  && consignorBillSet.size() > 0) {
					for (String consignorBill : consignorBillSet) {
						if(StringUtils.isNotEmpty(consignorBill)){
							if (!isWeChatUser(session, consignorBill)) {
								SmsYQUtil.sendOrderBusi(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderNo(),user.getUserName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), consignorBill);
							}
							if(isRegisterUser(session, consignorBill)){
								SmsYQUtil.sendOrderBusi(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), user.getUserName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, ordOrdersInfo.getOrderId(), consignorBill);
							}else{
								SmsYQUtil.sendSmsOrderBusi(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), user.getUserName(),  ordOrdersInfo.getOrderId(), consignorBill);
							}
						}
					}
				}
			}
			//拉包工只发一条
			if(arrayList.size()>1){
				SmsYQUtil.sendPullWorkerMul(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), goodsInfo.getProvineName()+goodsInfo.getCityName()+goodsInfo.getAddress(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordBusiPerson.getWorkerPhone())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getOrderId(), ordBusiPerson.getWorkerPhone());
			}else {
				SmsYQUtil.sendPullWorker(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), goodsInfo.getProvineName()+goodsInfo.getCityName()+goodsInfo.getAddress(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordBusiPerson.getWorkerPhone())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getOrderId(), ordBusiPerson.getWorkerPhone());
			}
		}
	}
	
	/****
	 * 下单详情查询
	 * @param inParam
	 * */
	@SuppressWarnings("unchecked")
	public OrdOrdersDetailOutParam  orderDetail(Map<String,Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if(orderId<0){
			throw new BusinessException("订单编号不能为空！");
		}
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在");
		}
		OrdOrdersDetailOutParam outParam = new OrdOrdersDetailOutParam();
		BeanUtils.copyProperties( outParam,ordOrdersInfo);
		if(user.getUserId().longValue()==ordOrdersInfo.getCreateId().longValue()){
			outParam.setIsOrders(1);
		}else{
			outParam.setIsOrders(0);
		}
		if (ordOrdersInfo.getDisId()!=null && ordOrdersInfo.getDisId().longValue() == 0) {
			outParam.setIsGradOrders("1");
		}else{
			outParam.setIsGradOrders("0");
		}
//		outParam.setCityName(ordOrdersInfo.getCityName());
//		outParam.setCountyName(ordOrdersInfo.getCountyName());
//		outParam.setProvinceName(ordOrdersInfo.getProvinceName());
		outParam.setCompanyId(ordOrdersInfo.getTenantId()!=null?ordOrdersInfo.getTenantId():null);
		outParam.setAddress(ordOrdersInfo.getAddress());
		outParam.setOrderTimeString(DateUtil.formatDateByFormat(ordOrdersInfo.getCreateDate(), "yyyy-MM-dd HH:mm"));
		outParam.setRemark(ordOrdersInfo.getRemark() != null ? ordOrdersInfo.getRemark() : "");
		if(ordOrdersInfo.getTenantId() != null && ordOrdersInfo.getTenantId() > 0){
			SysTenantDef tenant = (SysTenantDef) session.get(SysTenantDef.class, ordOrdersInfo.getTenantId());
			outParam.setTenantPhone(tenant.getCsPhones());
		}
		outParam.setTipString(ordOrdersInfo.getTipMoney() != null ? CommonUtil.parseDouble(ordOrdersInfo.getTipMoney()) : "");
		outParam.setFreightString(ordOrdersInfo.getFreight() != null ? CommonUtil.parseDouble(ordOrdersInfo.getFreight()) : "");
		String supportStaffPhone = "";
		List<Object> billingOrgIds = session.createSQLQuery(" select o.billing_Org_Id from Order_Info o where o.ords_Id = :orderId ").setParameter("orderId", orderId).list();
		if (billingOrgIds!=null&&billingOrgIds.size()>0) {
			long billingOrgId = Long.valueOf(billingOrgIds.get(0).toString());
			if (billingOrgId>0) {
				Organization organization = OraganizationCacheDataUtil.getOrganization(billingOrgId);
				if (organization!=null) {
					supportStaffPhone = organization.getSupportStaffPhone();
				}
			}
		}
		outParam.setSupportStaffPhone(supportStaffPhone);
		Criteria ca = session.createCriteria(OrdGoodsInfo.class);
		ca.add(Restrictions.eq("orderId", ordOrdersInfo.getOrderId()));
		List<OrdGoodsInfo> list = ca.list();
		for (OrdGoodsInfo ordGoodsInfo : list) {
			if(user.getTelphone().equals(ordOrdersInfo.getConsigneeBill())||
					SysStaticDataEnumYunQi.USER_TYPE_YUNQI.DISTRIBUTION==user.getUserType().intValue()
					||SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER==user.getUserType().intValue()
					||SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL==user.getUserType().intValue()
					||user.getUserId().longValue()==ordOrdersInfo.getCreateId().longValue()){
				OrdPickDetailOutParam pickInfoOutParam = new OrdPickDetailOutParam();
				BeanUtils.copyProperties(pickInfoOutParam, ordGoodsInfo);
				pickInfoOutParam.setProvinceName(ordGoodsInfo.getProvineName());
				pickInfoOutParam.setCityName(ordGoodsInfo.getCityName());
				pickInfoOutParam.setCountyName(ordGoodsInfo.getCountyName());
				pickInfoOutParam.setAddress(ordGoodsInfo.getAddress());
				pickInfoOutParam.setConsignorBill(ordGoodsInfo.getConsignorBill());
				pickInfoOutParam.setConsignorName(ordGoodsInfo.getConsignorName());
				pickInfoOutParam.setIdNo(ordGoodsInfo.getIdNo());
				pickInfoOutParam.setIsPickup(ordGoodsInfo.getIsPickup());
				pickInfoOutParam.setPlanPickupTime(ordGoodsInfo.getPlanPickupTime()==null?"":DateUtil.formatDate(ordGoodsInfo.getPlanPickupTime(),DateUtil.DATETIME_FORMAT1));
				pickInfoOutParam.setLatitude(ordGoodsInfo.getLat());
				pickInfoOutParam.setLongitude(ordGoodsInfo.getLon());
				outParam.getPickList().add(pickInfoOutParam);
			}else{
				if(user.getTelphone().equals(ordGoodsInfo.getConsignorBill())){
					OrdPickDetailOutParam pickInfoOutParam = new OrdPickDetailOutParam();
					BeanUtils.copyProperties(pickInfoOutParam, ordGoodsInfo);
					pickInfoOutParam.setProvinceName(ordGoodsInfo.getProvineName());
					pickInfoOutParam.setCityName(ordGoodsInfo.getCityName());
					pickInfoOutParam.setCountyName(ordGoodsInfo.getCountyName());
					pickInfoOutParam.setAddress(ordGoodsInfo.getAddress());
					pickInfoOutParam.setConsignorBill(ordGoodsInfo.getConsignorBill());
					pickInfoOutParam.setConsignorName(ordGoodsInfo.getConsignorName());
					pickInfoOutParam.setIdNo(ordGoodsInfo.getIdNo());
					pickInfoOutParam.setIsPickup(ordGoodsInfo.getIsPickup());
					pickInfoOutParam.setPlanPickupTime(ordGoodsInfo.getPlanPickupTime()==null?"":DateUtil.formatDate(ordGoodsInfo.getPlanPickupTime(),DateUtil.DATETIME_FORMAT1));
					pickInfoOutParam.setLatitude(ordGoodsInfo.getLat());
					pickInfoOutParam.setLongitude(ordGoodsInfo.getLon());
					outParam.getPickList().add(pickInfoOutParam);
				}
			}
		}
		OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
		if(ordBusiPerson!=null){
			outParam.setWorkerBill(ordBusiPerson.getWorkerPhone());
			outParam.setWorkerName(ordBusiPerson.getWorkerName());
		}
		return outParam;
	}
	
	/****
	 * 任务列表查询(专线角色)
	 * @param inParam
	 * */
	public Map<String,Object>  zxQuery(Map<String,Object> inParam) throws Exception{
		String consigneeBill=DataFormat.getStringKey(inParam, "consigneeBill");
		String trackingNum=DataFormat.getStringKey(inParam, "trackingNum");
		String orderNo=DataFormat.getStringKey(inParam, "orderNo");
		int dealState = DataFormat.getIntKey(inParam, "dealState");
		int type = DataFormat.getIntKey(inParam, "type");
		int merchanOrDistri = DataFormat.getIntKey(inParam, "merchanOrDistri");
		if (type > 0 && merchanOrDistri == 1) {
			throw new BusinessException("不是配送员，请切换至配送！");
		}
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		StringBuffer sql = new StringBuffer();
		List<Integer> orderList = new ArrayList<Integer>();
		if(user.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION){
			if(1 == merchanOrDistri){
				sql.append("select o.ORDS_ID,o.DES_CITY_NAME,o.CONSIGNEE,o.CONSIGNEE_PHONE,o.consignor,o.CONSIGNOR_PHONE,o.consignor_Address,o.CARRIER_NAME,o.ORDER_NUMBER,o.order_state_out,a.order_no,o.PULL_NAME,o.PULL_PHONE,f.TIP,a.CREATE_DATE,f.TOTAL_FEE,o.billing_Org_Id,o.order_state_out from order_info as o,ord_orders_info as a,order_fee f  where o.ORDS_ID=a.order_id and f.order_id = o.order_id and o.CREATE_ID=:userId  ");
			}
			if(2 == merchanOrDistri){
				sql.append("select o.ORDS_ID,o.DES_CITY_NAME,o.CONSIGNEE,o.CONSIGNEE_PHONE,o.consignor,o.CONSIGNOR_PHONE,o.consignor_Address,o.CARRIER_NAME,o.ORDER_NUMBER,o.order_state_out,a.order_no,o.PULL_NAME,o.PULL_PHONE,f.TIP,a.CREATE_DATE,f.TOTAL_FEE,o.billing_Org_Id,o.order_state_out from order_info as o,ord_orders_info as a,ord_busi_person as p,order_fee f where o.ORDS_ID=a.order_id and o.order_id = f.order_id and a.order_id=p.order_id    ");
			}
		}
		if(user.getUserType()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER){
			sql.append("select o.ORDS_ID,o.DES_CITY_NAME,o.CONSIGNEE,o.CONSIGNEE_PHONE,o.consignor,o.CONSIGNOR_PHONE,o.consignor_Address,o.CARRIER_NAME,o.ORDER_NUMBER,o.order_state_out,a.order_no,o.PULL_NAME,o.PULL_PHONE,f.TIP,a.CREATE_DATE,f.TOTAL_FEE,o.billing_Org_Id, o.order_state_out  from order_info as o,ord_orders_info as a,order_fee f  where o.ORDS_ID=a.order_id and f.order_id = o.order_id  and o.order_state_out != 0 and o.CREATE_ID=:userId  ");
		}
		if(user.getUserType()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.DISTRIBUTION){
			sql.append("select o.ORDS_ID,o.DES_CITY_NAME,o.CONSIGNEE,o.CONSIGNEE_PHONE,o.consignor,o.CONSIGNOR_PHONE,o.consignor_Address,o.CARRIER_NAME,o.ORDER_NUMBER,o.order_state_out,a.order_no,o.PULL_NAME,o.PULL_PHONE,f.TIP,a.CREATE_DATE,f.TOTAL_FEE,o.billing_Org_Id,  o.order_state_out from order_info as o,ord_orders_info as a,ord_busi_person as p,order_fee f where o.ORDS_ID=a.order_id and o.order_id = f.order_id and a.order_id=p.order_id and o.order_state_out != 0   ");
		}
		
		sql.append(" and a.tenant_id=:tenantId ");
		if(StringUtils.isEmpty(sql.toString())){
			throw new BusinessException("接口调用错误");
		}
		if(StringUtils.isNotEmpty(trackingNum)){
			sql.append(" and o.ORDER_NUMBER=:orderNumber ");
		}
		if(StringUtils.isNotEmpty(orderNo)){
			sql.append(" and a.order_No=:orderNo ");
		}
		if(StringUtils.isNotEmpty(consigneeBill)){
			sql.append(" and o.CONSIGNEE_PHONE=:consigneePhone ");
		}
		if((type==1||type==2)&&StringUtils.isEmpty(consigneeBill) &&StringUtils.isEmpty(trackingNum)){
			throw new BusinessException("请输入运单号或者收货人手机号查询");
		}
		if(type<0){
			if(user.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION){
				if(2 == merchanOrDistri){
					sql.append(" and ( p.delivery_id=:userId or (p.GX_END_OP_ID=:userId and a.SERVICE_TYPE=1) ) ");
				}
			}
			
			if(user.getUserType()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.DISTRIBUTION){
				sql.append(" and ( p.delivery_id=:userId or (p.GX_END_OP_ID=:userId and a.SERVICE_TYPE=1) ) ");
			}
			if(dealState==SysStaticDataEnum.DEAL_STATE.WAIT_DEAL){
				orderList.add(SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN);
				orderList.add(SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DEPARTURE);
				orderList.add(SysStaticDataEnumYunQi.ORDER_INFO_STATE.IN_TRANSIT);
				orderList.add(SysStaticDataEnumYunQi.ORDER_INFO_STATE.FLOATION_CARGO);
				orderList.add(SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY);
				orderList.add(SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN);
			}
			if(dealState==SysStaticDataEnum.DEAL_STATE.DEALING){
				orderList.add(SysStaticDataEnumYunQi.ORDER_INFO_STATE.DO_SIGN);		
				orderList.add(SysStaticDataEnumYunQi.ORDER_INFO_STATE.DO_CANCER);	
				orderList.add(SysStaticDataEnumYunQi.ORDER_INFO_STATE.CANCERING);		
			}
		}else{
			if(type==1){
				orderList.add(SysStaticDataEnumYunQi.ORDER_INFO_STATE.FLOATION_CARGO);
			}
			if(type==2){
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY);
			}
			if(type==3){
				sql.append(" and ((p.delivery_id=:userId) or (p.GX_END_OP_ID=:userId and a.SERVICE_TYPE=1)) ");
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN);
			}
		}
		if(orderList.size()>0)
		sql.append(" and o.order_state_out  in (:orderStateList) ");
		sql.append(" order by o.order_id desc ");
		Query query = session.createSQLQuery(sql.toString());
		query.setParameter("tenantId", user.getTenantId());
		if(type<0||type==3){
			query.setParameter("userId", user.getUserId());
		}
		if(orderList.size()>0)
		query.setParameterList("orderStateList", orderList);
		if(StringUtils.isNotEmpty(trackingNum)){
			query.setParameter("orderNumber", trackingNum);
		}
		if(StringUtils.isNotEmpty(orderNo)){
			query.setParameter("orderNo", orderNo);
		}
		if(StringUtils.isNotEmpty(consigneeBill)){
			query.setParameter("consigneePhone", consigneeBill);
		}
		IPage p = PageUtil.gridPage(query);
		List<ZxOrdListOutParam> rtnList = new ArrayList<ZxOrdListOutParam>();
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		if(list==null || list.size()<=0){
			
		}else {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = list.get(i);
				ZxOrdListOutParam zxOrdListOutParam = new ZxOrdListOutParam();
				//o.ORDS_ID,o.DES_CITY_NAME,o.CONSIGNEE,o.CONSIGNEE_PHONE,o.consignor,o.CONSIGNOR_PHONE,
				//o.consignor_Address,o.CARRIER_NAME,o.ORDER_NUMBER,a.order_state,a.order_no
				long orderId=((BigInteger)obj[0]).longValue();
				zxOrdListOutParam.setOrderId(orderId);
				zxOrdListOutParam.setCityName(obj[1]+"");
				zxOrdListOutParam.setConsigneeBill(obj[3].toString()!=null?obj[3].toString():"");
				zxOrdListOutParam.setConsigneeName(obj[2].toString()!=null?obj[2].toString():"");
				zxOrdListOutParam.setConsignorName(obj[4].toString()!=null?obj[4].toString():"");
				zxOrdListOutParam.setConsignorBill(obj[5].toString()!=null?obj[5].toString():"");
				zxOrdListOutParam.setPickAddress(obj[6].toString()!=null?obj[6].toString():"");
				String companyName = (String)obj[7];
				if("null".equals(companyName)|| StringUtils.isEmpty(companyName)){
						zxOrdListOutParam.setCompanyName("");
				}else{
						zxOrdListOutParam.setCompanyName(obj[7].toString()!=null?obj[7].toString():"");
					}
				zxOrdListOutParam.setTrackingNum(obj[8].toString()!=null?obj[8].toString():"");
				//zxOrdListOutParam.setOrderState(((Integer)obj[9]).intValue());
				Integer orderStateOut = ((Integer)obj[17]).intValue();
				zxOrdListOutParam.setOrderState(orderStateOut.intValue());
				if(orderStateOut == 0){
					zxOrdListOutParam.setOrderStateName(SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE_OUT", 3+""));
				}else {
					zxOrdListOutParam.setOrderStateName(SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE_OUT", ((Integer)obj[17]).intValue()+""));
				}
				
				zxOrdListOutParam.setOrderNo(obj[10].toString()!=null?obj[10].toString():"");
				zxOrdListOutParam.setPullName(obj[11] != null ? String.valueOf(obj[11]) : "");
				zxOrdListOutParam.setPullPhone(obj[12] != null ? String.valueOf(obj[12]) : "");
				zxOrdListOutParam.setTipString(obj[13] != null ? CommonUtil.parseDouble(Long.valueOf(obj[13].toString())) : "");
				zxOrdListOutParam.setOrdersTime(obj[14] != null ? DateUtil.formatDate((Date)obj[14], "yyyy-MM-dd HH:mm") : "");
				zxOrdListOutParam.setTotalFeeString(obj[15] != null ? CommonUtil.parseDouble(Long.valueOf(obj[15].toString())) : "");
				zxOrdListOutParam.setSupportStaffPhone(obj[16]!=null ? OraganizationCacheDataUtil.getOrganization(Long.valueOf(obj[16].toString())).getSupportStaffPhone() : "");
				rtnList.add(zxOrdListOutParam);
			}
		}
		p.setThisPageElements(rtnList);
		Pagination<Object[]> page = new Pagination<Object[]>(p);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		return rtnMap;
	}
	
	
	/****
	 * 任务列表查询
	 * @param inParam
	 * */
	public Map<String,Object>  doQuery(Map<String,Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		String consigneeBill=DataFormat.getStringKey(inParam, "consigneeBill");
		String orderNo=DataFormat.getStringKey(inParam, "orderNo");
		int isIndexQuery = DataFormat.getIntKey(inParam, "isIndexQuery");
		int dealState = DataFormat.getIntKey(inParam, "dealState");
		int type = DataFormat.getIntKey(inParam, "type");
//		if(type<0){
//			throw new BusinessException("type 类型不能为空!");
//		}
		List<Integer> orderList = new ArrayList<Integer>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT distinct O.ORDER_ID FROM ord_orders_info AS O ,ord_goods_info AS G ,ord_busi_person AS P WHERE O.ORDER_ID=G.ORDER_ID AND O.ORDER_ID=P.ORDER_ID and O.order_type!=3 and  O.create_type!=4   ");
		if(isIndexQuery==1){
			if(StringUtils.isNotEmpty(orderNo)){
				sql.append(" and o.ORDER_NO=:orderNo  ");
			}
			if(StringUtils.isNotEmpty(consigneeBill)){
				sql.append(" and o.consignee_Bill=:consigneeBill  ");
			}
		}
		if(type>0&&user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
			sql.append(" and o.ORDER_TYPE=:orderType  ");
		}
		if(dealState>0){
			sql.append(" and o.ORDER_STATE IN (:orderIdList)  ");
		}
		if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS){
			if(type == 1){
				sql.append(" AND ( (O.CREATE_ID = :userId AND O.ORDER_TYPE = :orderType ) OR (O.create_id != :userId and O.order_type in (1,2) and G.CONSIGNOR_BILL = :bill)) ");
			}else if(type == 2){
				sql.append(" AND ( (O.CREATE_ID = :userId AND O.ORDER_TYPE = :orderType ) OR (O.create_id != :userId AND O.CONSIGNEE_BILL = :bill and O.order_type in (1,2)) ) ");
			}else{
				sql.append(" and (O.CREATE_ID=:userId OR g.CONSIGNOR_BILL=:bill OR O.CONSIGNEE_BILL=:bill ) ");
			}
			if(dealState==SysStaticDataEnum.DEAL_STATE.WAIT_DEAL){
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING);
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP);
			}
			if(dealState==SysStaticDataEnum.DEAL_STATE.DEALING){
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING);		
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY);
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT);
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN);
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.PENDING_DEPARTURE);
			}
			if(dealState==SysStaticDataEnum.DEAL_STATE.FINISH_DEAL){
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN);		
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.DO_CANCER);
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.CANCERING);
			}
		}
		if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
			sql.append(" and (O.CREATE_ID=:userId OR P.WORKER_PHONE=:bill  )");
			if(dealState==SysStaticDataEnum.DEAL_STATE.WAIT_DEAL){
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP);
			}
			if(dealState==SysStaticDataEnum.DEAL_STATE.DEALING){
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING);		
			}
			if(dealState==SysStaticDataEnum.DEAL_STATE.FINISH_DEAL){
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY);
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT);
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN);
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN);		
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.DO_CANCER);
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.CANCERING);
				orderList.add(SysStaticDataEnumYunQi.ORDERS_STATE.PENDING_DEPARTURE);
			}
		}
		sql.append(" order by o.order_id desc ");
		Query query = session.createSQLQuery(sql.toString());
		if(isIndexQuery==1){
			if(StringUtils.isNotEmpty(orderNo)){
				query.setParameter("orderNo", orderNo);
			}
			if(StringUtils.isNotEmpty(consigneeBill)){
				query.setParameter("consigneeBill", consigneeBill);
			}
		}
		if(type>0){
			query.setParameter("orderType", type);
		}
		if(orderList.size()>0){
			query.setParameterList("orderIdList", orderList);
		}
		if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS){
			query.setParameter("userId", user.getUserId());
			query.setParameter("bill", user.getTelphone());
		}
		if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
			query.setParameter("userId", user.getUserId());
			query.setParameter("bill", user.getTelphone());
		}
		IPage p = PageUtil.gridPage(query);
		List<Long> orderIdList = new ArrayList<Long>();
		List<Object> list = (List<Object>)p.getThisPageElements();
		for (Object orderId : list) {
			orderIdList.add(((BigInteger)orderId).longValue());
		}
		Map<Long,OrdOrdersInfoOutParam> map = new HashMap<Long,OrdOrdersInfoOutParam>();
		List<OrdOrdersInfoOutParam> outList = new ArrayList<OrdOrdersInfoOutParam>();
		if(orderIdList.size()>0){
			Query sqlQuery = session.createSQLQuery("SELECT  O.*,P.*,ifnull(G.PROVINCE_ID,'') as pickprovince,ifnull(G.CITY_ID,'') as  pickcity,ifnull(G.COUNTY_ID,'') as pickcountyId,"
					+ "ifnull(G.ADDRESS,'') as pickaddress,G.ID_NO AS idNo,G.consignor_Bill AS consignorBill,G.consignor_Name AS consignorName,"
					+ "G.is_Pickup AS isPickup,G.plan_Pickup_Time AS planPickupTime,"
					+ "G.CODE AS code,G.COLOR as color,G.COUNT AS count,G.standard AS standard,G.product AS product,(select CS_PHONES from sys_tenant_def where TENANT_ID = o.TENANT_ID) as tenantPhone,(select billing_Org_Id from order_info where ords_id = O.order_id) as billingOrgId FROM ord_orders_info AS O ,ord_goods_info AS G ,ord_busi_person AS P WHERE O.ORDER_ID=G.ORDER_ID AND O.ORDER_ID=P.ORDER_ID AND O.ORDER_ID IN (:orderIdList) ")
					.addEntity("O",OrdOrdersInfo.class).addEntity("P",OrdBusiPerson.class).
					addScalar("pickprovince").addScalar("pickcity").addScalar("pickcountyId").addScalar("pickaddress").
			addScalar("idNo").addScalar("consignorBill").addScalar("consignorName").addScalar("isPickup").addScalar("planPickupTime").
			addScalar("code").addScalar("color").addScalar("count").addScalar("standard").addScalar("product").addScalar("tenantPhone").addScalar("billingOrgId");
			sqlQuery.setParameterList("orderIdList", orderIdList);
			
			List<Object[]> dataList = sqlQuery.list();
			for (Object[] obj : dataList) {
				OrdOrdersInfo ordOrdersInfo=(OrdOrdersInfo)obj[0];
				
				OrdBusiPerson ordBusiPerson=(OrdBusiPerson)obj[1];
				
				String provinceId=(String)obj[2];
				String cityId=(String)obj[3];
				String countyId=(String)obj[4];
				String address=(String)obj[5];
				BigInteger idNo=(BigInteger)obj[6];
				String consignorBill=(String)obj[7];
				String consignorName=(String)obj[8];
				Integer isPickup=(Integer)obj[9];
				Date planPickupTime=(Date)obj[10];
				String code=(String)obj[11];
				String color=(String)obj[12];
				Integer count=(Integer)obj[13];
				String standard=(String)obj[14];
				String product=(String)obj[15];
				OrdOrdersInfoOutParam outParam = map.get(ordOrdersInfo.getOrderId());
				if(outParam==null){
					outParam = new OrdOrdersInfoOutParam();
				}
				BeanUtils.copyProperties(outParam,ordOrdersInfo);
				if(user.getUserId().longValue()==ordOrdersInfo.getCreateId().longValue()){
					outParam.setIsOrders(1);
				}else{
					outParam.setIsOrders(0);
				}
				//是否抢单
				if (ordOrdersInfo.getDisId() != null && ordOrdersInfo.getDisId().longValue() == 0L) {
					outParam.setIsGradOrders(1);
				}else{
					outParam.setIsGradOrders(0);
				}
				outParam.setWorkerBill(ordBusiPerson.getWorkerPhone());
				outParam.setWorkerName(ordBusiPerson.getWorkerName());
				outParam.setTenantPhone(obj[16] != null ? obj[16].toString() : "");
				outParam.setSupportStaffPhone(obj[17] !=null ? OraganizationCacheDataUtil.getOrganization(Long.valueOf(obj[17].toString())).getSupportStaffPhone() : "");
				outParam.setTipString(ordOrdersInfo.getTipMoney() != null ? CommonUtil.parseDouble(ordOrdersInfo.getTipMoney()) : "");
				outParam.setOrdersTime(ordOrdersInfo.getCreateDate()!=null  ? DateUtil.formatDate(ordOrdersInfo.getCreateDate(), "yyyy-MM-dd HH:mm") : "");
				outParam.setFreightString(ordOrdersInfo.getFreight() != null ? CommonUtil.parseDouble(ordOrdersInfo.getFreight()) : "");
				if(user.getTelphone().equals(ordOrdersInfo.getConsigneeBill())||
						SysStaticDataEnumYunQi.USER_TYPE_YUNQI.DISTRIBUTION==user.getUserType().intValue()
						||SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER==user.getUserType().intValue()
						||SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL==user.getUserType().intValue()
						||user.getUserId().longValue()==ordOrdersInfo.getCreateId().longValue()){
					OrdPickInfoOutParam pickInfoOutParam = new OrdPickInfoOutParam();
					pickInfoOutParam.setAddress(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", provinceId.toString()).getName() +SysStaticDataUtil.getCityDataList("SYS_CITY", cityId.toString()).getName() +address);
					pickInfoOutParam.setConsignorBill(consignorBill);
					pickInfoOutParam.setConsignorName(consignorName);
					pickInfoOutParam.setIdNo(idNo.longValue());
					pickInfoOutParam.setIsPickup(isPickup);
					pickInfoOutParam.setCode(code);
					pickInfoOutParam.setColor(color);
					//pickInfoOutParam.setTipString(ordOrdersInfo.getTipMoney() != null ? CommonUtil.parseDouble(ordOrdersInfo.getTipMoney()) : "");
					//pickInfoOutParam.setOrdersTime(ordOrdersInfo.getCreateDate()!=null  ? DateUtil.formatDate(ordOrdersInfo.getCreateDate(), "yyyy-MM-dd HH:mm") : "");
					if(count!=null){
					pickInfoOutParam.setCount(count);
					}
					pickInfoOutParam.setProduct(product);
					pickInfoOutParam.setStandard(standard);
					pickInfoOutParam.setPlanPickupTime(planPickupTime==null?"":DateUtil.formatDate(planPickupTime,DateUtil.DATETIME_FORMAT1));
					outParam.getPickList().add(pickInfoOutParam);
				}else{
					if(user.getTelphone().equals(consignorBill)){
						OrdPickInfoOutParam pickInfoOutParam = new OrdPickInfoOutParam();
						pickInfoOutParam.setAddress(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", provinceId.toString()).getName() +SysStaticDataUtil.getCityDataList("SYS_CITY", cityId.toString()).getName() +address);
						pickInfoOutParam.setConsignorBill(consignorBill);
						pickInfoOutParam.setConsignorName(consignorName);
						pickInfoOutParam.setIdNo(idNo.longValue());
						pickInfoOutParam.setIsPickup(isPickup);
						pickInfoOutParam.setCode(code);
						pickInfoOutParam.setColor(color);
						if(count!=null){
							pickInfoOutParam.setCount(count);
							}
						pickInfoOutParam.setProduct(product);
						pickInfoOutParam.setStandard(standard);
						//pickInfoOutParam.setOrdersTime(ordOrdersInfo.getCreateDate()!=null  ? DateUtil.formatDate(ordOrdersInfo.getCreateDate(), "yyyy-MM-dd HH:mm") : "");
						//pickInfoOutParam.setTipString(ordOrdersInfo.getTipMoney() != null ? CommonUtil.parseDouble(ordOrdersInfo.getTipMoney()) : "");
						pickInfoOutParam.setPlanPickupTime(planPickupTime==null?"":DateUtil.formatDate(planPickupTime,DateUtil.DATETIME_FORMAT1));
						outParam.getPickList().add(pickInfoOutParam);
					}
				}
				map.put(ordOrdersInfo.getOrderId(), outParam);
			}
		}
		for (long orderId : orderIdList) {
			if(map.get(orderId)!=null){
				outList.add(map.get(orderId));
			}
		}
		p.setThisPageElements(outList);
		Pagination<Object[]> page = new Pagination<Object[]>(p);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		return rtnMap;
	}
	

	/****
	 * 下单详情查询
	 * @param inParam
	 * */
	public Map<String,Object>  getByIdOrderInfo(Map<String,Object> inParam) throws Exception{ 
		String orderNo = DataFormat.getStringKey(inParam, "orderNo");
		if(StringUtils.isEmpty(orderNo)){
			throw new BusinessException("订单交易编号为空!");
		}
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		Criteria ca = session.createCriteria(OrdOrdersInfo.class);
		ca.add(Restrictions.eq("orderNo", orderNo));
		List<OrdOrdersInfo> list = ca.list();
		if(list==null||list.size()==0){
			throw new BusinessException("订单信息不存在！");
		}
		OrdOrdersInfo ordOrdersInfo = list.get(0);
		Criteria ordCa = session.createCriteria(OrdGoodsInfo.class);
		ordCa.add(Restrictions.eq("orderId", ordOrdersInfo.getOrderId()));
		List<OrdGoodsInfo> list2 = ordCa.list();
		Map<String,Object> map = new HashMap<String,Object>();
		if (list2.size()!=0) {
			OrdGoodsInfo ordGoodsInfo = list2.get(0);
			map.put("consignorBill", StringUtils.defaultString(ordGoodsInfo.getConsignorBill(), ""));
			map.put("consignorName", StringUtils.defaultString(ordGoodsInfo.getConsignorName(), ""));
			// StringUtils.defaultString(ordGoodsInfo.getCountyName(), "")+StringUtils.defaultString(ordGoodsInfo.getStreetName(), "")
//			String consignorAddress = StringUtils.defaultString(ordGoodsInfo.getProvineName(), "")
//					+StringUtils.defaultString(ordGoodsInfo.getCityName(), "")
//					+StringUtils.defaultString(ordGoodsInfo.getCountyName(), "")
//					+StringUtils.defaultString(ordGoodsInfo.getAddress(), "");
			String consignorAddress = StringUtils.defaultString(ordGoodsInfo.getAddress(), "");
			map.put("shipAddress",  StringUtils.defaultString(consignorAddress, ""));
		}
//		String consigneeAddress = StringUtils.defaultString(ordOrdersInfo.getProvinceName(), "")
//				+StringUtils.defaultString(ordOrdersInfo.getCityName(), "")
//				+StringUtils.defaultString(ordOrdersInfo.getCountyName(), "")
//				+StringUtils.defaultString(ordOrdersInfo.getAddress(), "");
		String consigneeAddress =StringUtils.defaultString(ordOrdersInfo.getAddress(), "");
		map.put("address", consigneeAddress);
		map.put("cityId", ordOrdersInfo.getCityId());
		map.put("provinceId", ordOrdersInfo.getProvinceId());
		map.put("provinceName", ordOrdersInfo.getProvinceName());
		map.put("cityName", ordOrdersInfo.getCityName());
		map.put("companyName", ordOrdersInfo.getCompanyName());
		map.put("companyId", ordOrdersInfo.getTenantId()==null?null:ordOrdersInfo.getTenantId());
		map.put("consigneeBill", ordOrdersInfo.getConsigneeBill());
		map.put("consigneeId", ordOrdersInfo.getConsigneeId());
		map.put("consigneeName", ordOrdersInfo.getConsigneeName());
		map.put("orderNo", ordOrdersInfo.getOrderNo());
		map.put("orderId", ordOrdersInfo.getOrderId());
		map.put("remark", ordOrdersInfo.getRemark());
		map.put("districtId", ordOrdersInfo.getCountyId());
		map.put("districtName", ordOrdersInfo.getCountyName());
		int interchange = -1;
		if (ordOrdersInfo.getServiceType() == SysStaticDataEnumYunQi.SERVICE_TYPE.DEVLIVERY) {
			interchange = SysStaticDataEnumYunQi.INTERCHANGE_YUNQI.DEVLIVERY_FLOOR_UP;
		}else{
			interchange = SysStaticDataEnumYunQi.INTERCHANGE_YUNQI.TAKE_PICK;
		}
		String  interchangeName = SysStaticDataUtil.getSysStaticDataCodeName("INTERCHANGE_YUNQI", String.valueOf(interchange));
		map.put("interchange", interchange);
		map.put("paymentType", ordOrdersInfo.getPaymentType());
		map.put("interchangeName", interchangeName);
		map.put("paymentTypeName", SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE_YQ", String.valueOf(ordOrdersInfo.getPaymentType())));
		OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
		if(ordBusiPerson!=null){
			map.put("workerBill", ordBusiPerson.getWorkerPhone());
			map.put("workerId", ordBusiPerson.getWorkerId());
			map.put("workerName", ordBusiPerson.getWorkerName());
		}
		return map;
	}
	
	/****
	 * 催单
	 * @param inParam
	 * */
	public Map<String,Object>  reminderOrder(Map<String,Object> inParam) throws Exception{ 
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if(orderId<0){
			throw new BusinessException("订单编号不能为空!");
		}
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在！");
		}
		Criteria ca = session.createCriteria(OrdGoodsInfo.class);
		ca.add(Restrictions.or(Restrictions.eq("createId", user.getUserId()), Restrictions.eq("consignorBill", user.getTelphone()))  );
		ca.add(Restrictions.eq("orderId", ordOrdersInfo.getOrderId()));
		List<OrdGoodsInfo> list = ca.list();
		if(list==null|| list.size()==0){
			throw new BusinessException("抱歉，你不是该单的发货方，无法点击催单！");
		}
		for (OrdGoodsInfo ordGoodsInfo : list) {
			if(ordGoodsInfo.getReminderCount()!=null&&ordGoodsInfo.getReminderCount()==3){
				throw new BusinessException("您已催单，若拉包工没有按时提货，系统将重新分配拉包工提货!");
			}
			ordGoodsInfo.setReminderCount(ordGoodsInfo.getReminderCount()==null?1:ordGoodsInfo.getReminderCount()+1);
			ordGoodsInfo.setReminderTime(new Date());
			ordGoodsInfo.setOpDate(new Date());
			ordGoodsInfo.setOpId(user.getUserId());
			session.update(ordGoodsInfo);
		}
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.reminderOrderLog(user.getUserId(), user, ordOrdersInfo);
		//TODO 推送信息拉包工或者专线
		OrdBusiPerson ordBusiPerson= (OrdBusiPerson)session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
		if(ordBusiPerson!=null){
			if(StringUtils.isNotEmpty(ordBusiPerson.getWorkerPhone())){
				SmsYQUtil.reminderBusinessSendCarrier(user.getTenantId()==null?-1:user.getTenantId(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordBusiPerson.getWorkerPhone())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordBusiPerson.getWorkerPhone());
			}
		}
		Map map = new HashMap();
		map.put("tips", "催单成功!已通知拉包工!");
		return map;
	}
	
	/****
	 * 发货
	 * @param inParam
	 * */
	public Map<String,Object>  sendGoods(Map<String,Object> inParam) throws Exception{ 
		String planPickupTime = DataFormat.getStringKey(inParam, "planPickupTime");
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if(orderId<0){
			throw new BusinessException("订单编号不能为空!");
		}
		if(StringUtils.isEmpty(planPickupTime)){
			throw new BusinessException("预计提货时间不能为空!");
		}
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在！");
		}
		if(ordOrdersInfo.getOrderState().intValue()!=SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP&&ordOrdersInfo.getOrderState().intValue()!=SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING){
			throw new BusinessException("下单状态为【"+ordOrdersInfo.getOrderStateName()+"】,不允许操作发货");
		}
		Criteria ca = session.createCriteria(OrdGoodsInfo.class);
		ca.add(Restrictions.or(Restrictions.eq("consignorBill", user.getTelphone()),Restrictions.eq("createId", user.getUserId())));
		ca.add(Restrictions.eq("orderId", orderId));
		List<OrdGoodsInfo> list = ca.list();
		if(list==null||list.size()==0){
			throw new BusinessException("抱歉，你不是该单的发货方，无法点击发货！");
		}
		OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
		for (OrdGoodsInfo ordGoodsInfo : list) {
			ordGoodsInfo.setPlanPickupTime(DateUtil.formatStringToDate(planPickupTime, DateUtil.DATETIME_FORMAT1));
			ordGoodsInfo.setOpDate(new Date());
			ordGoodsInfo.setOpId(user.getUserId());
			session.update(ordGoodsInfo);
			//系统自动推送消息给到拉包工和收货人。
			if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
				SmsYQUtil.sendGoodsToConSignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getConsigneeBill());
			}
			if(StringUtils.isNotEmpty(ordBusiPerson.getWorkerPhone())){
				SmsYQUtil.sendGoodsToWorker(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(),ordGoodsInfo.getProvineName()+ordGoodsInfo.getCityName()+ordGoodsInfo.getAddress(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordBusiPerson.getWorkerPhone())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getOrderId(), ordBusiPerson.getWorkerPhone());
			}
		}
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.deliveryGoodsOrderLog(user.getUserId(), user, ordOrdersInfo,planPickupTime);
		Map map = new HashMap();
		map.put("tips", "发货成功，等待拉包工提货");
		return map;
	}
	
	/****
	 * 关联运单号 602013
	 * @param inParam
	 * */
	public Map<String,Object>  linkageBill(Map<String,Object> inParam) throws Exception{ 
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String trackingNum = DataFormat.getStringKey(inParam, "trackingNum");
		String companyName = DataFormat.getStringKey(inParam, "companyName");
		if(orderId<0){
			throw new BusinessException("订单编号不能为空!");
		}
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在！");
		}
		if(ordOrdersInfo.getOrderState()!=SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING
				&&ordOrdersInfo.getOrderState()!=SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP){
			throw new BusinessException("订单状态为【"+ordOrdersInfo.getOrderStateName()+"】,关联运单号！");
		}
		ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT);
		ordOrdersInfo.setOpDate(new Date());
		ordOrdersInfo.setOpId(user.getUserId());
		ordOrdersInfo.setTrackingNum(trackingNum);
		ordOrdersInfo.setCompanyName(companyName);
		session.update(ordOrdersInfo);
		OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
		if(ordBusiPerson!=null){
			ordBusiPerson.setInputTime(new Date());
			session.update(ordBusiPerson);
			//拉包工更新接单数
			if(ordBusiPerson.getWorkerId()!=null&&ordBusiPerson.getWorkerId() > 0){
				CmUserInfoPullTF cmUserInfoPullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
				CmUserInfoPull pull = cmUserInfoPullTF.reduceSingularNum(ordBusiPerson.getWorkerId());
				BusiGrabControlOut.updateUserInfoSingularNum(pull.getUserId(), pull.getSingularNum().intValue());
//				BusiMatchControlYq.updatePullLoadNum(pull.getUserId(), pull.getTenantId(), pull.getSingularNum(),pull.getDefaultSingularNum());
			}
		}
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.linkageOrderLog(user.getUserId(), user, ordOrdersInfo, companyName, ordBusiPerson.getWorkerName(), trackingNum);
		//TODO 推送信息 收货人/发货人
		if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
			if (!isWeChatUser(session, ordOrdersInfo.getConsigneeBill())) {
				SmsYQUtil.sendBillingDelivery(user.getTenantId()==null?-1L:user.getTenantId(), trackingNum, EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getCompanyName(), ordOrdersInfo.getConsigneeBill());
			}
			SmsYQUtil.sendBillingDelivery(user.getTenantId()==null?-1L:user.getTenantId(), trackingNum, EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getCompanyName(), ordOrdersInfo.getConsigneeBill());
		}
		Query query = session.createSQLQuery("select consignor_bill from ord_goods_info where order_id=:orderId");
		query.setParameter("orderId", ordOrdersInfo.getOrderId());
		List consignorList = query.list();
		for (Object consignor_bill : consignorList) {
			if(consignor_bill!=null&&StringUtils.isNotEmpty(consignor_bill.toString())){
				if (!isWeChatUser(session, consignor_bill.toString())) {
					SmsYQUtil.sendBillingDelivery(user.getTenantId()==null?-1L:user.getTenantId(), trackingNum, EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getCompanyName(), consignor_bill.toString());
				}
				SmsYQUtil.sendBillingDelivery(user.getTenantId()==null?-1L:user.getTenantId(), trackingNum, EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, consignor_bill.toString())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getCompanyName(), consignor_bill.toString());
			}
		}
		Map rtnMap = new HashMap();
		rtnMap.put("tips", "填写运单成功");
		return rtnMap;
	}
	
	
	/****
	 * 取消下单 602015
	 * @param inParam
	 * */
	public Map<String,Object>  cancerOrders(Map<String,Object> inParam) throws Exception{ 
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String remark=DataFormat.getStringKey(inParam, "remark");
		if(orderId<0){
			throw new BusinessException("订单编号不能为空!");
		}
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在！");
		}
		if(ordOrdersInfo.getOrderState().intValue()==SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN
				||ordOrdersInfo.getOrderState().intValue()==SysStaticDataEnumYunQi.ORDERS_STATE.DO_CANCER||
				ordOrdersInfo.getOrderState().intValue()==SysStaticDataEnumYunQi.ORDERS_STATE.CANCERING){
					throw new BusinessException("下单状态为【"+ordOrdersInfo.getOrderStateName()+"】,不允许取消！");
		}
		if(user.getUserId().longValue()!=ordOrdersInfo.getCreateId()){
			throw new BusinessException("抱歉，你不是该单的下单人，不允许取消订单！");
		}
		int type=2;
		String tips="取消成功";
		ordOrdersInfo.setOpDate(new Date());
		ordOrdersInfo.setOpId(user.getUserId());
		int oldOrderState=ordOrdersInfo.getOrderState();
		if(ordOrdersInfo.getOrderState().intValue()==SysStaticDataEnumYunQi.ORDERS_STATE.PENDING_DEPARTURE){
			tips="已成功发起取消申请,待专线审核";
			type=1;
			ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.CANCERING);
		}else{
			//TODO 取消时，判断该订单是否已经超时，否则继续进入抢单逻辑
//			Query query = session.createSQLQuery(" select o.order_id,o.ORDER_NO,(UNIX_TIMESTAMP(now())*1000 - UNIX_TIMESTAMP(o.CREATE_DATE)*1000 ) from ord_orders_info o where (((UNIX_TIMESTAMP(now())*1000 - UNIX_TIMESTAMP(o.CREATE_DATE)*1000 )/60000) < (SELECT cfg_value from sys_cfg where cfg_name='ORDERS_TIMEOUT'))  and o.ORDER_ID = :orderId ");
//			query.setParameter("orderId", orderId);
			ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.DO_CANCER);
		}
		ordOrdersInfo.setCancerReason(remark);
		session.update(ordOrdersInfo);
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.cancerOrderLog(user.getUserId(),type,user, ordOrdersInfo);
		OrdBusiPerson ordBusiPerson=(OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
		//TODO 推送信息 发货人，下单人，收货人
		if(user.getUserType()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
			Query query = session.createSQLQuery("select consignor_bill from ord_goods_info where order_id=:orderId");
			query.setParameter("orderId", ordOrdersInfo.getOrderId());
			List consignorList = query.list();
			for (Object consignor_bill : consignorList) {
				if(consignor_bill!=null&&StringUtils.isNotEmpty(consignor_bill.toString())){
					SmsYQUtil.cancelOrderCarrierSendConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(),ordOrdersInfo.getOrderNo(),ordBusiPerson.getWorkerPhone(),  EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, consignor_bill.toString())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, consignor_bill.toString());
				}
			}
			if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
				SmsYQUtil.cancelOrderCarrierSendConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(),ordOrdersInfo.getOrderNo(),ordBusiPerson.getWorkerPhone(),  EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getConsigneeBill());
			}
		}else if(StringUtils.isNotEmpty(user.getTelphone())&&user.getTelphone().equals(ordOrdersInfo.getConsigneeBill())){
				Query query = session.createSQLQuery("select consignor_bill from ord_goods_info where order_id=:orderId");
				query.setParameter("orderId", ordOrdersInfo.getOrderId());
				List consignorList = query.list();
				for (Object consignor_bill : consignorList) {
					if(consignor_bill!=null&&StringUtils.isNotEmpty(consignor_bill.toString())){
						SmsYQUtil.cancelOrderSendConsignor(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(),ordOrdersInfo.getConsigneeName(), ordOrdersInfo.getConsigneeBill(), ordOrdersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, consignor_bill.toString())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, consignor_bill.toString());
					}
				}
				if(StringUtils.isNotEmpty(ordBusiPerson.getWorkerPhone())){
					SmsYQUtil.cancelOrderSendCarrier(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordBusiPerson.getWorkerPhone())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordBusiPerson.getWorkerPhone());
				}
		}else{
			if(StringUtils.isNotEmpty(ordBusiPerson.getWorkerPhone())){
				SmsYQUtil.cancelOrderSendCarrier(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordBusiPerson.getWorkerPhone())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordBusiPerson.getWorkerPhone());
			}
			if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
				SmsYQUtil.cancelOrderSendConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(),user.getTelphone(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session,ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getConsigneeBill());
			}

		}
		if(ordBusiPerson.getInputUserId()!=null){
			CmUserInfo cmUserInfo = (CmUserInfo) session.get(CmUserInfo.class, ordBusiPerson.getInputUserId());
			if(cmUserInfo!=null){
				if(CommonUtil.isCheckPhone(cmUserInfo.getLoginAcct())){
					SmsYQUtil.cancelOrderSendMerchandiser(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(),remark, EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE,cmUserInfo.getLoginAcct());
				}
			}
		}
		
		
		if(oldOrderState==SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING ){
			//该订单还没有被拉包工抢单，需要删除缓存的数据
			BusiGrabControlOut.delAllOrderInfoAndGps(String.valueOf(orderId));
		}
		if (ordBusiPerson.getWorkerId() != null) {
			if(oldOrderState==SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP  || 
					oldOrderState==SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING){
				CmUserInfoPullTF cmUserInfoPullTF=(CmUserInfoPullTF)SysContexts.getBean("cmUserInfoPullTF");
				
					CmUserInfoPull cmUserInfoPull= cmUserInfoPullTF.reduceSingularNum(ordBusiPerson.getWorkerId());
					BusiGrabControlOut.updateUserInfoSingularNum(ordBusiPerson.getWorkerId(), cmUserInfoPull.getSingularNum().intValue());
				
			}
		}
		Map map = new HashMap();
		map.put("tips",tips);
		return map;
	}
	
	/****
	 * 打小费 602011
	 * @param inParam
	 * */
	@SuppressWarnings("unchecked")
	public Map<String,Object>  payTips(Map<String,Object> inParam) throws Exception{ 
		String orderNo = DataFormat.getStringKey(inParam, "orderNo");
		String freight = DataFormat.getStringKey(inParam, "freight");
		String tipsMoney = DataFormat.getStringKey(inParam, "tipsMoney");
		String trackingNum = DataFormat.getStringKey(inParam, "trackingNum");
		String weight = DataFormat.getStringKey(inParam, "weight");
		if(StringUtils.isEmpty(orderNo)){
			throw new BusinessException("订单交易编号不能为空!");
		}
		if(StringUtils.isEmpty(tipsMoney)){
			throw new BusinessException("小费不能为空!");
		}
		if(!CommonUtil.isDouble(tipsMoney)&&!CommonUtil.isNumber(tipsMoney)){
			throw new BusinessException("小费格式不对!");
		}
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		if(user.getUserType().intValue()!=SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION){
			throw new BusinessException("抱歉，你没有权限操作打小费!");
		}
		Criteria ca = session.createCriteria(OrdOrdersInfo.class);
		ca.add(Restrictions.eq("orderNo", orderNo));
		List<OrdOrdersInfo> list = ca.list();
		if(list.size()==0){
			throw new BusinessException("订单信息不存在！");
		}
		OrdOrdersInfo ordOrdersInfo = list.get(0);
		if(ordOrdersInfo.getOrderState()!=SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING
				&&ordOrdersInfo.getOrderState()!=SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP){
			throw new BusinessException("订单状态为【"+ordOrdersInfo.getOrderStateName()+"】,不允许打点小费！");
		}
		ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT);
		if(StringUtils.isNotEmpty(weight)){
			ordOrdersInfo.setWeight(weight);
		}
		ordOrdersInfo.setTrackingNum(trackingNum);
		Double tips=Double.valueOf(tipsMoney)*100;
		ordOrdersInfo.setTipMoney(tips.longValue());
		if(CommonUtil.isDouble(freight)||CommonUtil.isNumber(freight)){
			Double freightD=Double.valueOf(freight)*100;
			ordOrdersInfo.setFreight(freightD.longValue());
		}
		ordOrdersInfo.setOpDate(new Date());
		ordOrdersInfo.setOpId(user.getUserId());
		ordOrdersInfo.setCompanyName(SysTenantDefCacheDataUtil.getTenantName(user.getTenantId()==null?0:user.getTenantId()));
		ordOrdersInfo.setTenantId(user.getTenantId());
		ordOrdersInfo.setOrgId(user.getOrgId());
		session.update(ordOrdersInfo);
		long workerId=-1L;
		String name="";
		String phone="";
		List<OrdGoodsInfo> ordGoodsInfoList = session.createCriteria(OrdGoodsInfo.class).add(Restrictions.eq("orderId", ordOrdersInfo.getOrderId())).list();
		if (ordGoodsInfoList!=null&&ordGoodsInfoList.size() >0) {
			for (OrdGoodsInfo ordGoodsInfo : ordGoodsInfoList) {
				ordGoodsInfo.setIsPickup(1);
			}
		}
		OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
		if(ordBusiPerson!=null){
			ordBusiPerson.setInputTime(new Date());
			ordBusiPerson.setInputUserId(user.getUserId());
			ordBusiPerson.setInputUserName(user.getUserName());
			workerId=ordBusiPerson.getWorkerId()==null?-1:ordBusiPerson.getWorkerId();
			name=ordBusiPerson.getWorkerName();
			phone=ordBusiPerson.getWorkerPhone();
			session.update(ordBusiPerson);
			//拉包工更新接单数
			if(ordBusiPerson.getWorkerId()!=null&&ordBusiPerson.getWorkerId() > 0){
				CmUserInfoPullTF cmUserInfoPullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
				CmUserInfoPull pull = cmUserInfoPullTF.reduceSingularNum(ordBusiPerson.getWorkerId());
				BusiGrabControlOut.updateUserInfoSingularNum(pull.getUserId(), pull.getSingularNum().intValue());
//				BusiMatchControlYq.updatePullLoadNum(pull.getUserId(), pull.getTenantId(),pull.getSingularNum(),pull.getDefaultSingularNum());
			}
			if(StringUtils.isNotEmpty(ordBusiPerson.getWorkerPhone())){
				SmsYQUtil.tipMoneySendSmsCarrier(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(), ordOrdersInfo.getCompanyName(), CommonUtil.parseDouble(ordOrdersInfo.getTipMoney()), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordBusiPerson.getWorkerPhone())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordBusiPerson.getWorkerPhone(),ordOrdersInfo.getOrderNo());
			}
		}
		// 调用林峰提供接口
		AcMyWalletTF acMyWalletTF = (AcMyWalletTF) SysContexts.getBean("acMyWalletTF");
		 workerId = acMyWalletTF.doSaveTip(ordOrdersInfo.getOrderId(), new Date(), trackingNum, ordOrdersInfo.getProvinceId(), ordOrdersInfo.getProvinceName(), ordOrdersInfo.getConsigneeName(), ordOrdersInfo.getConsigneeBill(), ordOrdersInfo.getTipMoney(), ordOrdersInfo.getTenantId(), workerId,phone,name);
		if(ordBusiPerson.getWorkerId()==null){
			ordBusiPerson.setWorkerId(workerId);
		}
		
		if(workerId>0){
			AcWalletRelSV acWalletRelSV = (AcWalletRelSV) SysContexts.getBean("acWalletRelSV");
			List<AcWalletRel> acWalletRelList = acWalletRelSV.checkAcWalletRel(workerId, user.getTenantId());
			AcWalletRel acWalletRel = null;
			if(acWalletRelList == null || acWalletRelList.size() <= 0){
				acWalletRel = new AcWalletRel();
				acWalletRel.setCreateTime(new Date());
				acWalletRel.setTenantId(user.getTenantId());
				acWalletRel.setTenantName(CommonUtil.getTennatNameById(user.getTenantId()));
				acWalletRel.setUserId(workerId);
				
				CmUserOrgRelTF cmUserOrgRelTF = (CmUserOrgRelTF) SysContexts.getBean("cmUserOrgRelTF");
				List<Organization> organizations= cmUserOrgRelTF.queryOrgByUserId(workerId);
				
				if(organizations!=null && organizations.size()==1){
					acWalletRel.setPullTenantId(organizations.get(0).getTenantId());
				}else{
					throw new BusinessException("拉包工归属的组织不正确！");
				}
				
				
				acWalletRelSV.doSave(acWalletRel);
			}
		}
		
		
		
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.payTipsOrderLog(user.getUserId(), user, ordOrdersInfo, ordOrdersInfo.getCompanyName(), user.getUserName(), OraganizationCacheDataUtil.getOrgName(user.getOrgId()),StringUtils.defaultIfEmpty(OraganizationCacheDataUtil.getOrganization(user.getOrgId()).getSupportStaffPhone(), ""));
		//TODO 推送信息
		if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
			if (!isWeChatUser(session, ordOrdersInfo.getConsigneeBill())) {
				SmsYQUtil.sendBillingDelivery(user.getTenantId()==null?-1L:user.getTenantId(), trackingNum, EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getCompanyName(), ordOrdersInfo.getConsigneeBill());
			}
			SmsYQUtil.sendBillingDelivery(user.getTenantId()==null?-1L:user.getTenantId(), trackingNum, EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getCompanyName(), ordOrdersInfo.getConsigneeBill());
		}
		Query query = session.createSQLQuery("select consignor_bill from ord_goods_info where order_id=:orderId");
		query.setParameter("orderId", ordOrdersInfo.getOrderId());
		List consignorList = query.list();
		for (Object consignor_bill : consignorList) {
			
			if(consignor_bill!=null&&StringUtils.isNotEmpty(consignor_bill.toString())){
				SmsYQUtil.sendBillingDelivery(user.getTenantId()==null?-1L:user.getTenantId(), trackingNum, EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, consignor_bill.toString())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getCompanyName(), consignor_bill.toString());
				if (!isWeChatUser(session, consignor_bill.toString())) {
					SmsYQUtil.sendBillingDelivery(user.getTenantId()==null?-1L:user.getTenantId(), trackingNum, EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), ordOrdersInfo.getCompanyName(), consignor_bill.toString());
				}
			}
		}
		Map map = new HashMap();
		map.put("tips", "打小费成功");
		return map;
	}
	
	/****
	 * 提货  602010
	 * @param inParam
	 * */
	public Map<String,Object>  pickUp(Map<String,Object> inParam) throws Exception{ 
		long idNo = DataFormat.getLongKey(inParam, "idNo");
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if(orderId<0){
			throw new BusinessException("订单编号不能为空!");
		}
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在！");
		}
		if(ordOrdersInfo.getOrderState().intValue()!=SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP){
			throw new BusinessException("下单状态为【"+ordOrdersInfo.getOrderStateName()+"】,不允许操作提货");
		}
		
		OrdBusiPerson busi = (OrdBusiPerson) session.get(OrdBusiPerson.class, orderId);
		//调单后
		if (busi != null && busi.getWorkerId() != null && !busi.getWorkerId().equals(user.getUserId())) {
			throw new BusinessException("订单发生了改变，请刷新！");
		}
		//已提货
		Query query = session.createSQLQuery("SELECT COUNT(1) FROM Ord_Goods_Info WHERE ORDER_ID=:orderId and IS_PICKUP=1 ");
		query.setParameter("orderId", orderId);
		Object uniqueResult = query.uniqueResult();
		int pickCount=((BigInteger)uniqueResult).intValue();
		//所有的货统计
		Query sqlQuery = session.createSQLQuery("SELECT COUNT(1) FROM Ord_Goods_Info WHERE ORDER_ID=:orderId ");
		sqlQuery.setParameter("orderId", orderId);
		int totalCount =( (BigInteger)sqlQuery.uniqueResult()).intValue();
		
		Criteria ca = session.createCriteria(OrdGoodsInfo.class);
		ca.add(Restrictions.eq("idNo", idNo));
		ca.add(Restrictions.eq("orderId", orderId));
		List<OrdGoodsInfo> list = ca.list();
		if(list==null||list.size()==0){
			throw new BusinessException("提货信息不存在!");
		}
		OrdGoodsInfo ordGoodsInfo = list.get(0);
		Date d = new Date();
		ordGoodsInfo.setPickupTime(d);
		ordGoodsInfo.setIsPickup(1);
		ordGoodsInfo.setOpDate(d);
		ordGoodsInfo.setOpId(user.getUserId());
		session.update(ordGoodsInfo);
		if(totalCount==(pickCount+1)){//已提货的等于所有货+1
			ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING);
			ordOrdersInfo.setOpDate(d);
			ordOrdersInfo.setOpId(user.getUserId());
			session.update(ordOrdersInfo);
		}
		
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.pickupOrderLog(user.getUserId(), user, ordOrdersInfo, user.getUserName());
				//TODO 推送信息
		if (!isWeChatUser(session, ordOrdersInfo.getConsigneeBill())) {
			SmsYQUtil.sendPickConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4,ordOrdersInfo.getOrderId(), EnumConstsYQ.SmsType.WECHAT_TYPE,  ordOrdersInfo.getConsigneeBill());
		}
		SmsYQUtil.sendPickConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2,ordOrdersInfo.getOrderId(), isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE,  ordOrdersInfo.getConsigneeBill());
		if (ordOrdersInfo.getCreateId() != null) {
			CmUserInfo cmUserInfo = (CmUserInfo) session.get(CmUserInfo.class, ordOrdersInfo.getCreateId());
			if (!cmUserInfo.getLoginAcct().equals( ordOrdersInfo.getConsigneeBill())) {
				if (StringUtils.isNotEmpty(cmUserInfo.getOpenId())) {
					SmsYQUtil.sendPickConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4,ordOrdersInfo.getOrderId(), EnumConstsYQ.SmsType.WECHAT_TYPE,  cmUserInfo.getLoginAcct());
				}
				SmsYQUtil.sendPickConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2,ordOrdersInfo.getOrderId(), EnumConstsYQ.SmsType.MOPBILE_TYPE,  cmUserInfo.getLoginAcct());
			}
		}
		Map map = new HashMap();
		map.put("tips", "提货成功，尽快拉往物流公司");
		return map;
	}
	
	/****
	 * 所有提货  602022
	 * @param inParam
	 * */
	public Map<String,Object>  pickUpAll(Map<String,Object> inParam) throws Exception{ 
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if(orderId<0){
			throw new BusinessException("订单编号不能为空!");
		}
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在！");
		}
		if(ordOrdersInfo.getOrderState().intValue()!=SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP){
			throw new BusinessException("下单状态为【"+ordOrdersInfo.getOrderStateName()+"】,不允许操作提货");
		}
		
		Criteria ca = session.createCriteria(OrdGoodsInfo.class);
		ca.add(Restrictions.eq("orderId", orderId));
		List<OrdGoodsInfo> list = ca.list();
		Date d = new Date();
		if(list==null||list.size()==0){
			throw new BusinessException("提货信息不存在!");
		}else{
			for (OrdGoodsInfo ordGoodsInfo : list) {
				ordGoodsInfo.setPickupTime(d);
				ordGoodsInfo.setIsPickup(1);
				ordGoodsInfo.setOpDate(d);
				ordGoodsInfo.setOpId(user.getUserId());
				session.update(ordGoodsInfo);
			}
		}
		ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING);
		ordOrdersInfo.setOpDate(d);
		ordOrdersInfo.setOpId(user.getUserId());
		session.update(ordOrdersInfo);
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.pickupOrderLog(user.getUserId(), user, ordOrdersInfo, user.getUserName());
				//TODO 推送信息
		SmsYQUtil.sendPickConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2,ordOrdersInfo.getOrderId(), isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE,  ordOrdersInfo.getConsigneeBill());
		Map map = new HashMap();
		map.put("tips", "提货成功，尽快拉往物流公司");
		return map;
	}
	
	
	
	/****
	 * 签收  602012
	 * @param inParam
	 * */
	public Map<String,Object>  signUp(Map<String,Object> inParam) throws Exception{ 
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		//String userTypeByIntf =  DataFormat.getStringKey(inParam, "userType");
		String userTypeByIntf =  DataFormat.getStringKey(inParam, "tmsklinfo");
		if(orderId<0){
			throw new BusinessException("订单编号不能为空!");
		}
		String signDesc = DataFormat.getStringKey(inParam, "signDecs");
		String flowId = DataFormat.getStringKey(inParam, "flowId");
		String certificateNo =  DataFormat.getStringKey(inParam, "certificateNo");
		String certificateType = DataFormat.getStringKey(inParam, "certificateType");
		
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在！");
		}
		//不管是接口还是app 只有待签收状态的订单才可以操作签收
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
		signInfo.setExt1(StringUtils.defaultString(user.getTelphone(), ""));
		signInfo.setOrderId(orderId);
		signInfo.setSignDate(d);
		signInfo.setSignName(user.getUserName());
		signInfo.setFlowId(flowId);
		signInfo.setSignDesc(signDesc);
		signInfo.setSource(1);
		if(StringUtils.isNotEmpty(certificateNo)){
			signInfo.setCertificateNo(certificateNo);
		}				
		if(StringUtils.isNotEmpty(certificateType)){
			signInfo.setCertificateType(Integer.valueOf(certificateType));
		}	
		session.save(signInfo);
		List<Long> list = new ArrayList<Long>();
		list.add(ordOrdersInfo.getOrderId());
		updateOrderInfo(session,list,ordOrdersInfo.getOrderState());
		//String tmsklinfo = DataFormat.getStringKey(inParam, "tmsklinfo");
		
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.signUpOrderLog(user.getUserId(), user, ordOrdersInfo, ordOrdersInfo.getCompanyName(), OraganizationCacheDataUtil.getOrgName(ordOrdersInfo.getOrgId()==null?0:ordOrdersInfo.getOrgId()), user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS?1:0);
		//TODO 推送
		/****
		 *  1﹜	配送员登陆app，点击签收，跳转到签收页面，通过扫描运单条形码，或输入收货人号码，运单号进行查找到签收的订单，然后点击签收，上传签收图片及备注，提交完成签收操作（系统自动推送消息给到收货人，发货人）
			2﹜	收货人进行签收，收货人登陆app，找到订单后点击签收，提示签收成功（系统会自动推送消息给到发货人和专线物流公司）。
			3﹜	发货人进行签收，发货人登陆app，找到订单后点击签收，提示签收成功（系统自动推送消息给到收货人和专线公司）。
		 */
		
		if(StringUtils.isNotEmpty(userTypeByIntf)){
		
		}else {
			if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION||ordOrdersInfo.getConsigneeBill().equals(user.getTelphone())){
				Query query = session.createSQLQuery("select consignor_bill from ord_goods_info where order_id=:orderId");
				query.setParameter("orderId", ordOrdersInfo.getOrderId());
				List consignorList = query.list();
				for (Object consignor_bill : consignorList) {
					if(consignor_bill!=null&&StringUtils.isNotEmpty(consignor_bill.toString())){
						if (!isWeChatUser(session, consignor_bill.toString())) {
							SmsYQUtil.signSendConsignor(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderId(),signInfo.getSignName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, consignor_bill.toString(),ordOrdersInfo.getOrderNo());
						}
						SmsYQUtil.signSendConsignor(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderId(),signInfo.getSignName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, consignor_bill.toString())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, consignor_bill.toString(),ordOrdersInfo.getOrderNo());
					}
				}
			}else{
				if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
					if (!isWeChatUser(session, ordOrdersInfo.getConsigneeBill())) {
						SmsYQUtil.signSendConsignor(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderId(),signInfo.getSignName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getConsigneeBill(),ordOrdersInfo.getOrderNo());
					}
					SmsYQUtil.signSendConsignor(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderId(),signInfo.getSignName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getConsigneeBill(),ordOrdersInfo.getOrderNo());
				}
			}
			if (ordOrdersInfo.getCreateId() != null) {
				CmUserInfo cmUserInfo = (CmUserInfo) session.get(CmUserInfo.class, ordOrdersInfo.getCreateId());
				if (!cmUserInfo.getLoginAcct().equals( ordOrdersInfo.getConsigneeBill())) {
					if (StringUtils.isNotEmpty(cmUserInfo.getOpenId())) {
						SmsYQUtil.signSendConsignor(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderId(),signInfo.getSignName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, cmUserInfo.getLoginAcct(),ordOrdersInfo.getOrderNo());
					}
					SmsYQUtil.signSendConsignor(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderId(),signInfo.getSignName(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, cmUserInfo.getLoginAcct(),ordOrdersInfo.getOrderNo());
				}
			}
		}
		//调TMS签收 来自接口的不掉用此接口
		String isFlag = "";
		if (StringUtils.isEmpty(userTypeByIntf)&& CommonUtil.tmsTenantId("TMS_KL") == user.getTenantId().longValue()) {
			Criteria ca = session.createCriteria(OrderInfo.class);
			List<OrderInfo> listOrder = ca.add(Restrictions.eq("ordsId", orderId)).list();
			if (listOrder != null && listOrder.size() > 0) {
				String isSign = OrderPostUtil.signKL(listOrder.get(0).getOrderNumber(), user.getUserName(), DateUtil.formatDateByFormat(d, "yyyy-MM-dd HH:mm:ss"), user.getUserName(), "", "", DateUtil.formatDateByFormat(d, "yyyy-MM-dd HH:mm:ss"), user.getUserName(), user.getTenantId(),false);
				if (!"Y".equals(isSign)) {
					log.error(isSign);
					OrderInfoTmsError orderInfoTmsError = new OrderInfoTmsError();
					orderInfoTmsError.setOrderNumber(listOrder.get(0).getOrderNumber());
					orderInfoTmsError.setUserName(user.getUserName());
					orderInfoTmsError.setCreateTime(d);
					orderInfoTmsError.setSignName(user.getUserName());
					orderInfoTmsError.setSignTime(d);
					orderInfoTmsError.setTenantId(user.getTenantId());
					orderInfoTmsError.setOrderId(listOrder.get(0).getOrderId());
					orderInfoTmsError.setOrdsId(orderId);
					orderInfoTmsError.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN);
					doSaveOrderInfoTmsError(orderInfoTmsError, session);
					isFlag = "，同步TMS签收错误！";
					//throw new BusinessException("同步TMS签收错误！");
				}
			}
		}
		Map map = new HashMap();
		map.put("tips", "货物已签收"+isFlag);
		return map;
	}
	
	/****
	 * 编辑下单信息  602014
	 * @param inParam
	 * */
	@SuppressWarnings("rawtypes")
	public Map<String,Object>  updateOrderInfo(Map<String,Object> inParam) throws Exception{ 
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String address = DataFormat.getStringKey(inParam, "address");
		String consigneeName = DataFormat.getStringKey(inParam, "consigneeName");
		String consigneeBill = DataFormat.getStringKey(inParam, "consigneeBill");
		long cityId = DataFormat.getLongKey(inParam, "cityId");
		int serviceType = DataFormat.getIntKey(inParam, "serviceType");
		long provinceId = DataFormat.getLongKey(inParam, "provinceId");
		long consigneeId = DataFormat.getLongKey(inParam, "consigneeId");
		String latitude = DataFormat.getStringKey(inParam, "latitude");
		String longitude = DataFormat.getStringKey(inParam, "longitude");
		
		
		JSONArray fromObject = JSONArray.fromObject(inParam.get("consignorInfo"));
		if(fromObject==null|| fromObject.size()==0){
			throw new BusinessException("发货信息不能为空!");
		}
		
		if(orderId<0){
			throw new BusinessException("订单编号不能为空!");
		}
		if(cityId<0){
			throw new BusinessException("请选择到站城市!");
		}
		if(consigneeId<0){
			if(StringUtils.isEmpty(consigneeBill)){
				throw new BusinessException("收货人手机不能为空!");
			}
			if(StringUtils.isEmpty(consigneeName)){
				throw new BusinessException("收货人名称不能为空!");
			}
		}
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在！");
		}
		if(user.getUserId().longValue()!=ordOrdersInfo.getCreateId().longValue()){
			throw new BusinessException("抱歉，你没有权限编辑下单信息!");
		}
		String phone="";
		if(ordOrdersInfo.getOrgId()!=null){
			Organization organization = OraganizationCacheDataUtil.getOrganization(ordOrdersInfo.getOrgId());
			phone=organization.getSupportStaffPhone();
		}
		if(ordOrdersInfo.getOrderState().intValue()!=SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP&&
				ordOrdersInfo.getOrderState().intValue()!=SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING&&
				ordOrdersInfo.getOrderState().intValue()!=SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING){
			throw new BusinessException("货物已经在【"+ordOrdersInfo.getOrderStateName()+"】,请拔打电话联系物流公司。"+phone);
		}
		ordOrdersInfo.setServiceType(serviceType);
		ordOrdersInfo.setAddress(address);
		ordOrdersInfo.setProvinceId(provinceId);
		ordOrdersInfo.setCityId(cityId);
		
		ordOrdersInfo.setConsigneeBill(consigneeBill);
		ordOrdersInfo.setConsigneeName(consigneeName);
		ordOrdersInfo.setOpDate(new Date());
		ordOrdersInfo.setOpId(user.getUserId());
		ordOrdersInfo.setLatitude(latitude);
		ordOrdersInfo.setLongitude(longitude);
		
		//删除所有该单抢单信息
		if (ordOrdersInfo.getOrderState() == SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING) {
			BusiGrabControlOut.delAllOrderInfo(String.valueOf(orderId));
		}
		
		int count=0;
		String products="";
		Double weight=0.0;
		Double volume=0.0;
		String lat="";
		String lon="";
		List<OrdGoodsInfo> arrayList = new ArrayList<OrdGoodsInfo>();
		Date d = new Date();
		String planPickupTimeRedis="";
		String pickAddr="";
		int i=0;
		CmAreaTF cmAreaTF = (CmAreaTF) SysContexts.getBean("cmAreaTF");
		Iterator iterator=  fromObject.iterator();
		long idNo = -1;
		List<Long> idNos = new ArrayList<Long>();
		boolean isNewGoods = false;
		List<OrdGoodsInfo> goods = new ArrayList<OrdGoodsInfo>();
		while (iterator.hasNext()) {
			int isPick = 0;
			JSONObject  myJson = (JSONObject)iterator.next();
			Map m = myJson;
			String planPickupTime = DataFormat.getStringKey(m, "planPickupTime");
			OrdGoodsInfo goodsInfo = null;
			long idNoe = DataFormat.getLongKey(m, "idNo");
			if (idNoe > 0) {
				goodsInfo = (OrdGoodsInfo) session.get(OrdGoodsInfo.class, idNoe);
				isPick = goodsInfo.getIsPickup();
			}else{
				goodsInfo = new OrdGoodsInfo();
				isNewGoods = true;
			}
			BeanUtils.populate(goodsInfo,m);
			long consignorId = DataFormat.getLongKey(m, "consignorId");
			
			if(StringUtils.isEmpty(goodsInfo.getConsignorName())){
				throw new BusinessException("发货人名称不能为空");
			}
			if(StringUtils.isEmpty(goodsInfo.getConsignorBill())){
				throw new BusinessException("发货人电话不能为空");
			}
			if(!CommonUtil.isCheckMobiPhone(goodsInfo.getConsignorBill())&&!CommonUtil.isCheckPhone(goodsInfo.getConsignorBill())){
				throw new BusinessException("发货人电话格式不正确!");
			}
			if(goodsInfo.getProvinceId()==null||goodsInfo.getProvinceId()<0){
				throw new BusinessException("提货省份不能为空!");
			}
			if(goodsInfo.getCityId()==null||goodsInfo.getCityId()<0){
				throw new BusinessException("提货城市不能为空!");
			}
			if(StringUtils.isEmpty(goodsInfo.getAddress())){
				throw new BusinessException("发货人详细地址不能为空");
			}
			
			if(StringUtils.isNotEmpty(planPickupTime)){
				//多个提货点的话，只是取第一个的提货时间
				if(i==0){
					planPickupTimeRedis=planPickupTime;
				}
				
				goodsInfo.setPlanPickupTime(DateUtil.parseDate(planPickupTime, DateUtil.DATETIME_FORMAT1));
			}
			if(i==0){
				pickAddr=goodsInfo.getAddress();
			}
			
			i=i+1;
			
			goodsInfo.setConsignorId(consignorId);
			goodsInfo.setCreateDate(d);
			goodsInfo.setOpDate(d);
			goodsInfo.setOpId(user.getUserId());
			goodsInfo.setCreateId(user.getUserId());
			goodsInfo.setIsPickup(0);
			goodsInfo.setOrderNo(ordOrdersInfo.getOrderNo());
			goodsInfo.setOrderId(ordOrdersInfo.getOrderId());
			arrayList.add(goodsInfo);
			count=count+(goodsInfo.getCount()==null?0:goodsInfo.getCount());
			if(StringUtils.isNotEmpty(goodsInfo.getProduct())){
				if(StringUtils.isNotEmpty(products)){
					products=products+","+goodsInfo.getProduct();
				}else{
					products=goodsInfo.getProduct();
				}
			}
			weight=weight+(StringUtils.isEmpty(goodsInfo.getWeight())?0.0D:Double.parseDouble(goodsInfo.getWeight()));
			volume=volume+(StringUtils.isEmpty(goodsInfo.getVolume())?0.0D:Double.parseDouble(goodsInfo.getVolume()));
			if(StringUtils.isEmpty(lat)){
				lat=StringUtils.isEmpty(goodsInfo.getLat())?"":goodsInfo.getLat();
			}
			if(StringUtils.isEmpty(lon)){
				lon=StringUtils.isEmpty(goodsInfo.getLon())?"":goodsInfo.getLon();
			}
			if (StringUtils.isEmpty(goodsInfo.getLon()) || StringUtils.isEmpty(goodsInfo.getLat())) {
				Map<String,String> map =cmAreaTF.getCmAreaByCityOrAddress(
						goodsInfo.getProvinceId() !=null ? goodsInfo.getProvinceId() : -1
						, goodsInfo.getCityId() != null ? goodsInfo.getCityId() : -1, 
						goodsInfo.getCountyId() != null ? goodsInfo.getCountyId() : -1,
						goodsInfo.getAddress());
				goodsInfo.setLon(map.get("longitude"));
				goodsInfo.setLat(map.get("latitude"));
			}
			goodsInfo.setIsPickup(isPick);
			session.saveOrUpdate(goodsInfo);
			idNo = goodsInfo.getIdNo();
			idNos.add(idNo);
			goods.add(goodsInfo);
		}
		//删除多余的提货地址
		session.createQuery("delete from OrdGoodsInfo g where g.idNo not in (:idNos) and g.orderId = :orderId").setParameterList("idNos", idNos).setParameter("orderId", orderId).executeUpdate();
		int isPickCount = 0;
		for (OrdGoodsInfo ordGoodsInfo : goods) {
			isPickCount = ordGoodsInfo.getIsPickup() + isPickCount;
		}
		
		ordOrdersInfo.setIdNo(idNo);
		ordOrdersInfo.setWeight(String.valueOf(weight));//总重量
		ordOrdersInfo.setVolume(String.valueOf(volume));//总体积
		ordOrdersInfo.setOrderNum(count);//总件数
		ordOrdersInfo.setProducts(products);//品名
		
		OrdBusiPerson busi = (OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
		if (busi!=null) {
			if (isNewGoods && StringUtils.isNotEmpty(busi.getWorkerName())) {
				ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP);
			}
		}
		if (isPickCount == goods.size()) {
			ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING);
		}
		session.update(ordOrdersInfo);
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.modifyOrderLog(user.getUserId(), user, ordOrdersInfo);
		Object oPullBill = session.createQuery("select b.workerPhone from OrdBusiPerson b where b.orderId = :orderId ").setParameter("orderId", orderId).uniqueResult();
		if (oPullBill != null) {
			if (isRegisterUser(session, String.valueOf(oPullBill))) {
				SmsYQUtil.updateOrderSendCarrier(user.getTenantId() != null ? user.getTenantId() : -1, ordOrdersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, String.valueOf(oPullBill));
			}else{
				SmsYQUtil.updateOrderSendCarrier(user.getTenantId() != null ? user.getTenantId() : -1, ordOrdersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.NOTICE_TYPE, String.valueOf(oPullBill));
			}
			if (isWeChatUser(session, String.valueOf(oPullBill))) {
				SmsYQUtil.updateOrderSendCarrier(user.getTenantId() != null ? user.getTenantId() : -1, ordOrdersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, String.valueOf(oPullBill));
			}
		}
		if(SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING==ordOrdersInfo.getOrderState()){
			//抢单
			String createDate=DateUtil.formatDate(ordOrdersInfo.getCreateDate(), DateUtil.DATETIME_FORMAT);
			BusiGrabControlOut.addOrderInfo(String.valueOf(ordOrdersInfo.getOrderId()), ordOrdersInfo.getCreateId(), ordOrdersInfo.getConTenantId(),  
					 Double.valueOf(StringUtils.isNotBlank(lat) ? lat : "0"),Double.valueOf(StringUtils.isNotBlank(lon) ? lon : "0"), ordOrdersInfo.getOrderNo(), createDate, planPickupTimeRedis, pickAddr,ordOrdersInfo.getCityName());
			
		}
		Map map = new HashMap();
		map.put("tips", "修改信息成功");
		return map;
	}
	public static void main(String[] args) throws Exception {
		System.out.println(K.k_s("{RC2}R41jAUgXckcix8c7"));
	}
	/****
	 * 配送  602009
	 * @param inParam
	 * */
	@SuppressWarnings("unchecked")
	public Map<String,Object>  deliveryOrder(Map<String,Object> inParam) throws Exception{ 
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String userTypeByIntf =  DataFormat.getStringKey(inParam, "tmsklinfo");
		
		if(orderId<0){
			throw new BusinessException("订单编号不能为空！");
		}
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在！");
		}
		if(StringUtils.isNotEmpty(userTypeByIntf)){
			
		}else {
			if(user.getUserType().intValue()!=SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION){
				throw new BusinessException("抱歉，你不是配送员，不能操作配送!");
			}
			
		}
		if(ordOrdersInfo.getOrderState().intValue()!=SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY){
			throw new BusinessException("订单状态为【"+ordOrdersInfo.getOrderStateName()+"】,不允许操作!");
		}
		ordOrdersInfo.setOpDate(new Date());
		ordOrdersInfo.setOpId(user.getUserId());
		ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN);
		session.update(ordOrdersInfo);
		OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
		if(ordBusiPerson!=null){
			ordBusiPerson.setDeliveryId(user.getUserId());
			ordBusiPerson.setDeliveryName(user.getUserName());
			ordBusiPerson.setDeliveryBill(user.getTelphone());
			ordBusiPerson.setDeliveryTime(new Date());
			ordBusiPerson.setOpId(user.getUserId());
			session.update(ordBusiPerson);
		}
		List<Long> list = new ArrayList<Long>();
		list.add(ordOrdersInfo.getOrderId());
		updateOrderInfo(session,list,ordOrdersInfo.getOrderState());
		
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.deliveryOrderLog(user.getUserId(), user, ordOrdersInfo, SysTenantDefCacheDataUtil.getTenantName(user.getTenantId()), user.getUserName(), OraganizationCacheDataUtil.getOrgName(user.getOrgId()));
		//TODO 推送信息 （系统自动推送消息给到收货人，发货人）
		if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
			if (!isWeChatUser(session, ordOrdersInfo.getConsigneeBill())) {
				SmsYQUtil.distributionSendConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(),ordOrdersInfo.getOrderNo(),user.getUserName(), user.getTelphone(),  ordOrdersInfo.getConsigneeBill(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE);
			}
			SmsYQUtil.distributionSendConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderId(),ordOrdersInfo.getOrderNo(),user.getUserName(), user.getTelphone(),  ordOrdersInfo.getConsigneeBill(),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE);
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
		String isFlag = "";
		if (StringUtils.isEmpty(userTypeByIntf)&& CommonUtil.tmsTenantId("TMS_KL") == user.getTenantId().longValue()) {
			Criteria ca = session.createCriteria(OrderInfo.class);
			List<OrderInfo> listOrder = ca.add(Restrictions.eq("ordsId", orderId)).list();
			//调柯莱TML配送
			if (listOrder != null && listOrder.size() > 0) {
				String isDispatcheing = OrderPostUtil.dispatcheingKL(listOrder.get(0).getOrderNumber(), user.getUserName(), DateUtil.formatDate(ordOrdersInfo.getOpDate(), "yyyy-MM-dd HH:mm:ss"), user.getTenantId(),false);
				if (!"Y".equals(isDispatcheing)) {
					log.error(isDispatcheing);
					OrderInfoTmsError orderInfoTmsError = new OrderInfoTmsError();
					orderInfoTmsError.setOrderNumber(listOrder.get(0).getOrderNumber());
					orderInfoTmsError.setUserName(user.getUserName());
					orderInfoTmsError.setCreateTime(ordOrdersInfo.getOpDate());
					orderInfoTmsError.setTenantId(user.getTenantId());
					orderInfoTmsError.setOrderId(listOrder.get(0).getOrderId());
					orderInfoTmsError.setOrdsId(orderId);
					orderInfoTmsError.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY);
					doSaveOrderInfoTmsError(orderInfoTmsError, session);
					isFlag = "，同步TMS配送错误！";
				}
			}
		}
		Map map = new HashMap();
		map.put("tips", "开始配送"+isFlag);
		return map;
	}
	
	/****
	 * 下单物流公司查询（602017）
	 * @param inParam
	 * */
	public Map<String,Object>  queryOrgList(Map<String,Object> inParam) throws Exception{ 
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		long destCityId = DataFormat.getLongKey(inParam, "destCityId");
		long destProvinceId = DataFormat.getLongKey(inParam, "destProvinceId");
		long pickCityId = DataFormat.getLongKey(inParam, "pickCityId");
		long pickProvinceId = DataFormat.getLongKey(inParam, "pickProvinceId");
		if(destCityId<0){
			throw new BusinessException("到站城市不能为空!");
		}
		if(destProvinceId<0){
			throw new BusinessException("到站省份不能为空!");
			}
		if(pickCityId<0){
			throw new BusinessException("提货城市不能为空!");
		}
		if(pickProvinceId<0){
			throw new BusinessException("提货省份不能为空!");
		}
		List<Map<String,Object>> rtnList = new ArrayList<Map<String,Object>>();
		Query query = session.createSQLQuery(" select DISTINCT BEGIN_TENANT_ID from route_towards where BEGIN_OWNER_REGION = :beginOwnerRegion and END_OWNER_REGION = :endOwnerRegion ");
		query.setParameter("beginOwnerRegion", pickCityId);
		query.setParameter("endOwnerRegion", destCityId);
		List<Object> list = query.list();
		if (list != null && list.size() > 0) {
			for (Object routeTowards : list) {
				if (routeTowards !=null) {
					Map<String, Object> map = new HashMap<String,Object>();
					map.put("orgId",String.valueOf(routeTowards));
					map.put("orgName", SysTenantDefCacheDataUtil.getTenantName(Long.valueOf(String.valueOf(routeTowards))));
					rtnList.add(map);
				}
			}
		}else{
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("orgId","");
			map.put("orgName", "");
			rtnList.add(map);
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(rtnList));
	}
	/****
	 * 干线到货（602018）
	 * @param inParam
	 * */
	public Map<String,Object>  gxArriveGoods(Map<String,Object> inParam) throws Exception{ 
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String userTypeByIntf =  DataFormat.getStringKey(inParam, "tmsklinfo");
		if(orderId<0){
			throw new BusinessException("订单编号不能为空！");
		}
		
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在！");
		}		
		Criteria orderInfo = session.createCriteria(OrderInfo.class);
		orderInfo.add(Restrictions.eq("ordsId", orderId));
		Integer interchange =0;
		List<OrderInfo> orderInfoList = orderInfo.list();
		if(orderInfoList==null || orderInfoList.size()<=0){
			throw new BusinessException("开单信息不存在！");
		}
		OrderInfo ordsInfo = orderInfoList.get(0);
		OrdDepartInfoYqSV ordDepartInfoYqSV = (OrdDepartInfoYqSV) SysContexts.getBean("ordDepartInfoYqSV");
		boolean isDepart = ordDepartInfoYqSV.checkIsDepart(ordsInfo.getOrderId());
		if (isDepart && StringUtils.isNotEmpty(userTypeByIntf)) {
			throw new BusinessException("到货错误！");
		}
		interchange = ordsInfo.getInterchange();
		if(StringUtils.isNotEmpty(userTypeByIntf)){
			
		}else
		{
			if(user.getUserType().intValue()!=SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION){
				throw new BusinessException("抱歉，你不是配送员，不能操作干线到货!");
			}
		}
		if(ordOrdersInfo.getOrderState().intValue()!=SysStaticDataEnumYunQi.ORDERS_STATE.PENDING_DEPARTURE){
			throw new BusinessException("运单状态为【"+ordOrdersInfo.getOrderStateName()+"】，不能操作干线到货!");
		}
		if(interchange==SysStaticDataEnumYunQi.INTERCHANGE_YUNQI.TAKE_PICK){
		    ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN);
		    ordsInfo.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN);
		    ordsInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.WAIT_SIGN);
		    
	    }else{
		    ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY);
		    ordsInfo.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY);
		    ordsInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PENDING_DELIVERY);
	    }
		Date d = new Date();
		ordOrdersInfo.setOpDate(d);
		ordOrdersInfo.setOpId(user.getUserId());
		session.update(ordOrdersInfo);
		ordsInfo.setOpId(user.getUserId());
		ordsInfo.setOpName(user.getUserName());
		ordsInfo.setOpTime(new Date());
		session.update(ordsInfo);
		OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
		if(ordBusiPerson!=null){
			ordBusiPerson.setGxEndOpId(user.getUserId());
			ordBusiPerson.setGxEndOpName(user.getUserName());
			ordBusiPerson.setGxEndTime(d);
			session.update(ordBusiPerson);
		}
		List<Long> list = new ArrayList<Long>();
		list.add(ordOrdersInfo.getOrderId());
		updateOrderInfo(session,list,ordOrdersInfo.getOrderState());
		OrdStockTF ordStockTF = (OrdStockTF) SysContexts.getBean("ordStockTF");
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		List<OrderInfoChild> childs = orderInfoChildTF.getOrderInfoChilds(ordsInfo.getOrderId());
		if (childs!=null&&childs.size()>0) {
			for (OrderInfoChild orderInfoChild : childs) {
				if (interchange==SysStaticDataEnumYunQi.INTERCHANGE_YUNQI.TAKE_PICK) {
					orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN);
				}else{
					orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY);
				}
				orderInfoChild.setDispatchingId(user.getUserId());
				orderInfoChild.setDispatchingName(user.getUserName());
				ordStockTF.putInCarStorage(orderInfoChild.getChildOrderId(), orderInfoChild.getDispatchingOrgId());
				orderInfoChildTF.doUpdate(orderInfoChild, user);
				orderInfoChild.setCurrentOrgId(orderInfoChild.getDispatchingOrgId());
			}
		}
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.gxArriverGoodsOrderLog(user.getUserId(), user, ordOrdersInfo, SysTenantDefCacheDataUtil.getTenantName(user.getTenantId()), user.getUserName(), OraganizationCacheDataUtil.getOrgName(user.getOrgId()),StringUtils.defaultIfEmpty(OraganizationCacheDataUtil.getOrganization(user.getOrgId()).getSupportStaffPhone(), ""));
		//TODO 推送（系统自动推送消息给到收货人，发货人）
		if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
			SmsYQUtil.arriveSendConsignee(user.getTenantId()==null?-1L:user.getTenantId(), ordOrdersInfo.getOrderNo(),ordOrdersInfo.getOrderId(),OraganizationCacheDataUtil.getOrganization(user.getOrgId()).getSupportStaffPhone(), OraganizationCacheDataUtil.getOrgName(user.getOrgId()),EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE,  ordOrdersInfo.getConsigneeBill());
		}
		Query query = session.createSQLQuery("select consignor_bill from ord_goods_info where order_id=:orderId");
		query.setParameter("orderId", ordOrdersInfo.getOrderId());
		List consignorList = query.list();
		for (Object consignor_bill : consignorList) {
			if(consignor_bill!=null&&StringUtils.isNotEmpty(consignor_bill.toString())){
				SmsYQUtil.arriveSendConsignor(user.getTenantId()==null?-1L:user.getTenantId(),ordOrdersInfo.getOrderId(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, consignor_bill.toString())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, consignor_bill.toString(),ordOrdersInfo.getOrderNo());
			}
		}
		String isFlag= "";
		//tms柯莱到货
		if (StringUtils.isEmpty(userTypeByIntf)&& CommonUtil.tmsTenantId("TMS_KL") == user.getTenantId().longValue()) {
			Criteria ca = session.createCriteria(OrderInfo.class);
			List<OrderInfo> listOrder = ca.add(Restrictions.eq("ordsId", orderId)).list();
			if (listOrder != null && listOrder.size() > 0) {
				String isArrival = OrderPostUtil.arrivalKL(listOrder.get(0).getOrderNumber(), user.getUserName(), DateUtil.formatDate(ordOrdersInfo.getOpDate(), "yyyy-MM-dd HH:mm:ss"), user.getTenantId(),false);
				if (!"Y".equals(isArrival)) {
					log.error(isArrival);
					OrderInfoTmsError orderInfoTmsError = new OrderInfoTmsError();
					orderInfoTmsError.setOrderNumber(listOrder.get(0).getOrderNumber());
					orderInfoTmsError.setUserName(user.getUserName());
					orderInfoTmsError.setCreateTime(ordOrdersInfo.getOpDate());
					orderInfoTmsError.setTenantId(user.getTenantId());
					orderInfoTmsError.setOrderId(listOrder.get(0).getOrderId());
					orderInfoTmsError.setOrdsId(orderId);
					orderInfoTmsError.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT);
					doSaveOrderInfoTmsError(orderInfoTmsError, session);
					isFlag = "，同步TMS到货错误！";
				}
			}
		}
		Map map = new HashMap();
		map.put("tips", "已成功到货"+isFlag);
		return map;
	}
	
	public void updateOrderInfo(Session session ,List<Long> orderId,int orderState){
		Query query = session.createSQLQuery(" update Order_Info set order_state=:orderState where ords_id in (:orderId) ");
		query.setParameterList("orderId", orderId);
		query.setParameter("orderState", orderState);
	}
	/****
	 * 匹配拉包工成功
	 * @param ORDERID 下单主键
	 * @param worKerId 拉包工userId
	 * ***/
	public void  matchSuceeful(long orderId,long worKerId) throws Exception{
		Session session = SysContexts.getEntityManager();
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在！");
		}
		OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, orderId);
		Criteria ca = session.createCriteria(CmUserInfo.class);
		ca.add(Restrictions.eq("userId", worKerId));
		ca.add(Restrictions.eq("userType", SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL));
		CmUserInfo cmUserInfo = (CmUserInfo) ca.uniqueResult();
		if(cmUserInfo!=null){
			CmUserInfoPullTF cmUserInfoPullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
			CmUserInfoPull byUserPull = cmUserInfoPullTF.addSingularNum(worKerId);
			ordBusiPerson.setWorkerId(cmUserInfo.getUserId());
			ordBusiPerson.setWorkerPhone(cmUserInfo.getLoginAcct());
			ordBusiPerson.setWorkerName(cmUserInfo.getUserName());
			session.update(ordBusiPerson);
			//自动派单
			BusiGrabControlOut.updateUserInfoSingularNum(byUserPull.getUserId(), byUserPull.getSingularNum().intValue());
//			BusiMatchControlYq.updatePullLoadNum(byUserPull.getUserId(), byUserPull.getTenantId(), byUserPull.getSingularNum(),byUserPull.getDefaultSingularNum());
		}
		ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP);
		ordOrdersInfo.setDisTime(new Date());
		ordOrdersInfo.setDisId(0L);
		ordOrdersInfo.setDisOpName(" 系统智能匹配");
		session.update(ordOrdersInfo);
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.disWorkerLog(0, ordBusiPerson.getWorkerPhone(), null, ordOrdersInfo, ordBusiPerson.getWorkerName());
		
		Query query = session.createSQLQuery("select PROVINCE_ID,city_id,address,consignor_bill from ord_goods_info where order_id=:orderId");
		query.setParameter("orderId", ordOrdersInfo.getOrderId());
		List<Object[]> list = query.list();
		//自动匹配不用改 		
		for (Object[] objects : list) {
			SmsYQUtil.sendPullWorker(-1L, ordOrdersInfo.getOrderNo(), 
					CommonUtil.getCityName(Long.parseLong(objects[0]+""),null, null)+CommonUtil.getCityName(null,Long.parseLong(objects[1]+""), null)+objects[2],EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, 
					ordOrdersInfo.getOrderId(), cmUserInfo.getLoginAcct());
			if(objects[3]!=null&&StringUtils.isNotEmpty(objects[3]+"")){
				SmsYQUtil.disWorkerToConsigor(-1L, ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(), cmUserInfo.getUserName(), cmUserInfo.getLoginAcct(), objects[3].toString());
			}
		}
	}
	
	

	/****
	 * 开单时调用
	 * @param orderId 下单主键
	 * @param tenantId 开单员租户
	 * @param tipMoney 小费 分
	 * @param inputName 开单人名称
	 * @param inputUserId 开单人id
	 * @param orgId 网点id
	 * @param companyName 物流名称
	 * 
	 * ***/
	public OrdOrdersInfo  saveInputReceipt(long orderId,long tenantId,long tipMoney,String inputName,long orgId,long inputUserId,String trackingNum,String companyName,String weight,String volume,Long totalFee) throws Exception{
		Session session = SysContexts.getEntityManager();
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在！");
		}
		ordOrdersInfo.setTrackingNum(trackingNum);
		ordOrdersInfo.setCompanyName(companyName);
		ordOrdersInfo.setTenantId(tenantId);
		ordOrdersInfo.setTipMoney(tipMoney);
		ordOrdersInfo.setOrgId(orgId);
		if(StringUtils.isNotEmpty(weight)&&!"null".equals(weight)){
			ordOrdersInfo.setWeight(weight);
		}
		if(StringUtils.isNotEmpty(volume)&&!"null".equals(volume)){
		ordOrdersInfo.setVolume(volume);
		}
		Date d = new Date();
		ordOrdersInfo.setOpDate(d);
		ordOrdersInfo.setOpId(inputUserId);
		ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.PENDING_DEPARTURE);
		ordOrdersInfo.setFreight(totalFee);
		session.update(ordOrdersInfo);
		OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, orderId);
		if(ordBusiPerson!=null){
			ordBusiPerson.setInputTime(d);
			ordBusiPerson.setInputUserId(inputUserId);
			ordBusiPerson.setInputUserName(inputName);
			session.update(ordBusiPerson);
		}
//		if(StringUtils.isNotEmpty(ordOrdersInfo.getConsigneeBill())){
//			if (isWeChatUser(session, ordOrdersInfo.getConsigneeBill())) {
//				SmsYQUtil.sendBillingDelivery(tenantId, trackingNum, EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), companyName, ordOrdersInfo.getConsigneeBill());
//			}
//			SmsYQUtil.sendBillingDelivery(tenantId, trackingNum, EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, ordOrdersInfo.getConsigneeBill())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getOrderId(), companyName, ordOrdersInfo.getConsigneeBill());
//		}
//		Query query = session.createSQLQuery("select consignor_bill from ord_goods_info where order_id=:orderId");
//		query.setParameter("orderId", ordOrdersInfo.getOrderId());
//		List list = query.list();
//		for (Object consignor_bill : list) {
//			if(consignor_bill!=null&&StringUtils.isNotEmpty(consignor_bill.toString())){
//				if (isWeChatUser(session, consignor_bill.toString())) {
//					SmsYQUtil.sendBillingDelivery(tenantId, trackingNum, EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), companyName, consignor_bill.toString());
//				}
//				SmsYQUtil.sendBillingDelivery(tenantId, trackingNum, EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, consignor_bill.toString())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, ordOrdersInfo.getOrderId(), companyName, consignor_bill.toString());
//			}
//		}
		return ordOrdersInfo;
	}
	
	/**
	 * 下单详情（专线角色）
	 * @param inParam 
	 * 		  orderId 下单主键id
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object>  zxOrderView(Map<String,Object> inParam) throws Exception{ 
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if(orderId<0){
			throw new BusinessException("业务编号id不能为空!");
		}
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		ZxOrdViewOutParam outParam = new ZxOrdViewOutParam();
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在!");
		}
		outParam.setOrderId(ordOrdersInfo.getOrderId());
		outParam.setOrderNo(ordOrdersInfo.getOrderNo());
		
		
//		outParam.setOrderState(ordOrdersInfo.getOrderState());
//		outParam.setOrderStateName(ordOrdersInfo.getOrderStateName());
		
		outParam.setOrderTimeString(DateUtil.formatDateByFormat(ordOrdersInfo.getCreateDate(), "yyyy-MM-dd HH:mm"));//下单时间
		Criteria ca = session.createCriteria(OrderInfo.class);
		ca.add(Restrictions.eq("ordsId", orderId));
		List<OrderInfo> list = ca.list();
		if(list.size()==0){
			throw new BusinessException("运单信息不存在!");
		}
		OrderInfo orderInfo = list.get(0);
		
		//outParam.setOrderState(orderInfo.getOrderState());
		outParam.setOrderState(orderInfo.getOrderStateOut());
		//outParam.setOrderStateName(SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE", String.valueOf(orderInfo.getOrderState())));
		outParam.setOrderStateName(SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE_OUT", String.valueOf(orderInfo.getOrderStateOut())));
		outParam.setInputTimeString(DateUtil.formatDateByFormat(orderInfo.getCreateTime(), "yyyy-MM-dd HH:mm"));//开单时间
		outParam.setConsigneeBill(orderInfo.getConsigneePhone());
		outParam.setConsigneeName(orderInfo.getConsignee());
		outParam.setCityName(orderInfo.getDesCityName());
		outParam.setDistrictName(orderInfo.getDesDistrictName());
		outParam.setProvinceName(orderInfo.getDesProvinceName());
		
//		outParam.setAddress(orderInfo.getDesProvinceName()+orderInfo.getDesCityName()+StringUtils.defaultString(orderInfo.getDesDistrictName(), "")+StringUtils.defaultString(orderInfo.getConsigneeAddress(), ""));
		outParam.setAddress(orderInfo.getConsigneeAddress());
//		outParam.setCityName(orderInfo.getDesCityName());
		outParam.setCompanyName(orderInfo.getCarrierName());
		outParam.setCount(orderInfo.getNumber()==null?"0":orderInfo.getNumber()+"");
		outParam.setProduct(orderInfo.getProductName());
		outParam.setPackageName(orderInfo.getPackName());
		outParam.setPaymentTypeName(orderInfo.getPaymentName());
		outParam.setTraCityName(OraganizationCacheDataUtil.getOrgName(orderInfo.getArrivedOrgId()!=null?orderInfo.getArrivedOrgId():0L));
		outParam.setTrackingNum(orderInfo.getOrderNumber());
		outParam.setVolume(orderInfo.getVolume());
		outParam.setWeight(orderInfo.getWeight());
		outParam.setWorkerName(orderInfo.getPullName());
		outParam.setServiceTypeName(orderInfo.getInterchangeName());
		outParam.setWorkerBill(orderInfo.getPullPhone());
		outParam.setConsignorBill(orderInfo.getConsignorPhone());
		outParam.setConsignorName(orderInfo.getConsignor());
		outParam.setPickAddress(orderInfo.getConsignorAddress());
		outParam.setRemark(orderInfo.getRemarks());
		outParam.setTrackingId(String.valueOf(orderInfo.getOrderId()));
		String supportStaffPhone = "";
		if (orderInfo.getBillingOrgId() != null && orderInfo.getBillingOrgId().longValue() > 0) {
			Organization organization = OraganizationCacheDataUtil.getOrganization(orderInfo.getBillingOrgId());
			if (organization!=null) {
				supportStaffPhone = organization.getSupportStaffPhone();
			}
		}
		outParam.setSupportStaffPhone(supportStaffPhone);
		outParam.setPikeCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(orderInfo.getStaCity())).getName());
		if(orderInfo.getTenantId() != null && orderInfo.getTenantId() > 0){
			SysTenantDef tenant = (SysTenantDef) session.get(SysTenantDef.class, orderInfo.getTenantId());
			outParam.setTenantPhone(tenant.getCsPhones());
		}
		Criteria ordFeeCa = session.createCriteria(OrderFee.class);
		ordFeeCa.add(Restrictions.eq("orderId", orderInfo.getOrderId()));
		List<OrderFee> list2 = ordFeeCa.list();
		if(list2.size()>0){
			OrderFee orderFee = list2.get(0);
			if(orderFee.getCollectMoney()!=null){
				outParam.setCollectMoney(CommonUtil.parseDouble(orderFee.getCollectMoney()));
			}
			if(orderFee.getDeliveryCharge()!=null){
				outParam.setDeliveryCharge(CommonUtil.parseDouble(orderFee.getDeliveryCharge()));
			}
			if(orderFee.getFreight()!=null){
				outParam.setFreight(CommonUtil.parseDouble(orderFee.getFreight()));
			}
			if(orderFee.getLandFee()!=null){
				outParam.setLandFee(CommonUtil.parseDouble(orderFee.getLandFee()));
			}
			if(orderFee.getOtherFee()!=null){
				outParam.setOtherFee(CommonUtil.parseDouble(orderFee.getOtherFee()));
			}
			if(orderFee.getPremium()!=null){
				outParam.setPremium(CommonUtil.parseDouble(orderFee.getPremium()));
			}
			if(orderFee.getReputation()!=null){
				outParam.setReputation(CommonUtil.parseDouble(orderFee.getReputation()));
			}
			if(orderFee.getServiceCharge()!=null){
				outParam.setServiceCharge(CommonUtil.parseDouble(orderFee.getServiceCharge()));
			}
			if(orderFee.getTransitFee()!=null){
				outParam.setTransitFee(CommonUtil.parseDouble(orderFee.getTransitFee()));
			}
			if(orderFee.getTotalFee()!=null){
				outParam.setTotalFee(CommonUtil.parseDouble(orderFee.getTotalFee()));
			}
			if(orderFee.getTip()!=null){
				outParam.setTipsFee(CommonUtil.parseDouble(orderFee.getTip()));
			}
			//包装费
			if(orderFee.getPickingCosts()!=null){
				outParam.setPickingFee(CommonUtil.parseDouble(orderFee.getPickingCosts()));
			}
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(outParam));
		
	}
	
	/****
	 * 确认取消（602023）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,String>  confirmCancer(Map<String,Object> inParam) throws Exception{
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if(orderId<0){
			throw new BusinessException("业务编号id不能为空!");
		}
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在!");
		}
		if(user.getUserType().intValue()!=SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION){
			throw new BusinessException("抱歉，你不是开单员，没有该权限!");
		}
		if(ordOrdersInfo.getOrderState().intValue()!=SysStaticDataEnumYunQi.ORDERS_STATE.CANCERING){
			throw new BusinessException("下单状态为【"+ordOrdersInfo.getOrderStateName()+"】，不能操作!");
		}
		ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.DO_CANCER);
		ordOrdersInfo.setOpDate(new Date());
		ordOrdersInfo.setOpId(user.getUserId());
		session.update(ordOrdersInfo);
		// 写日志
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
		ordLogNewTF.cancerAuthOrderLog(user.getUserId(), user, ordOrdersInfo);
		//TODO 推送信息
		Map<String,String> map = new HashMap<String,String>();
		map.put("tips", "订单取消通过");
		return map;
	}
	
	/****
	 * 判断是否可以编辑（602023）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,String>  isUpdateOrderInfo(Map<String,Object> inParam) throws Exception{
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if(orderId<0){
			throw new BusinessException("业务编号id不能为空!");
		}
		Session session = SysContexts.getEntityManager(true);
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在!");
		}
		if(ordOrdersInfo.getOrderState().intValue()==SysStaticDataEnumYunQi.ORDERS_STATE.DO_CANCER||
				ordOrdersInfo.getOrderState().intValue()==SysStaticDataEnumYunQi.ORDERS_STATE.CANCERING){
					throw new BusinessException("下单状态为【"+ordOrdersInfo.getOrderStateName()+"】,不允许编辑！");
		}
		String tips="";
		String yOrN="Y";
		String phone="";
		if(ordOrdersInfo.getOrderState().intValue()==SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN
				||ordOrdersInfo.getOrderState().intValue()==SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY||
				ordOrdersInfo.getOrderState().intValue()==SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT||
				ordOrdersInfo.getOrderState().intValue()==SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN){
			yOrN="N";
			tips="货物已经在【"+ordOrdersInfo.getOrderStateName()+"】了，请拨打电话联系物流公司。";
			if(ordOrdersInfo.getOrgId()!=null&&ordOrdersInfo.getOrgId()>0){
				Organization organization = OraganizationCacheDataUtil.getOrganization(ordOrdersInfo.getOrgId());
				if(organization!=null){
					phone=StringUtils.defaultString(organization.getSupportStaffPhone(),"");
				}
			}
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("tips", tips);
		map.put("isOk", yOrN);
		map.put("phone", phone);
		return map;
	}
	
	/**
	 * 
	 * 开单保存生成下单信息，对外不展示下单信息
	 * @param inParam
	 * orderInfo 开单信息
	 * startProvinceId 始发省份id
	 * startCityId 始发城市id
	 * user 当前用户信息
	 * tipsMoney 小费
	 * freight 运费
	 * */
	public OrdOrdersInfo  saveOrders(BaseUser user ,OrderInfo orderInfo,long startProvinceId,long startCityId,long tipsMoney,long freight) throws Exception{
		Session session = SysContexts.getEntityManager();
		int count=0;
		Double weight=0.0;
		Double volume=0.0;
		String lat="";
		String lon="";
		String orderNo=CommonUtil.getOrderNO();
		Date d = new Date();
		weight=weight+(StringUtils.isEmpty(orderInfo.getWeight())?0.0D:Double.parseDouble(orderInfo.getWeight()));
		volume=volume+(StringUtils.isEmpty(orderInfo.getVolume())?0.0D:Double.parseDouble(orderInfo.getVolume()));
		//consignorInfo.
		OrdOrdersInfo ordOrdersInfo = new OrdOrdersInfo();
		ordOrdersInfo.setConsigneeBill(StringUtils.defaultIfEmpty(orderInfo.getConsigneePhone(), ""));
		ordOrdersInfo.setConsigneeName(StringUtils.defaultIfEmpty(orderInfo.getConsignee(), ""));
		ordOrdersInfo.setVolume(volume.toString());
		ordOrdersInfo.setWeight(weight.toString());
		ordOrdersInfo.setOrderNo(orderNo);
		ordOrdersInfo.setOrderNum(count);
		ordOrdersInfo.setProducts(orderInfo.getProductName());
		ordOrdersInfo.setOpDate(d);
		ordOrdersInfo.setOpId(user.getUserId());
		ordOrdersInfo.setCreateDate(d);
		ordOrdersInfo.setCreateId(user.getUserId());
		ordOrdersInfo.setProvinceId(orderInfo.getDesProvince());
		ordOrdersInfo.setCityId(orderInfo.getDesCity());
		ordOrdersInfo.setAddress(orderInfo.getConsigneeAddress());
		ordOrdersInfo.setTipMoney(tipsMoney);
		ordOrdersInfo.setTrackingNum(orderInfo.getOrderNumber());
		
		ordOrdersInfo.setTenantId(orderInfo.getTenantId());
		
		ordOrdersInfo.setCompanyName(orderInfo.getCarrierName());
		if(orderInfo.getInterchange()!=null){
			if (orderInfo.getInterchange() == SysStaticDataEnumYunQi.INTERCHANGE_YUNQI.DEVLIVERY_FLOOR_UP) {
				ordOrdersInfo.setServiceType(SysStaticDataEnumYunQi.SERVICE_TYPE.DEVLIVERY);
			}else{
				ordOrdersInfo.setServiceType(SysStaticDataEnumYunQi.SERVICE_TYPE.TAKE_PICK);
			}
		}
		if(orderInfo.getPayment()!=null)
			ordOrdersInfo.setPaymentType(orderInfo.getPayment());
		ordOrdersInfo.setOrgId(user.getOrgId());
		ordOrdersInfo.setCreateType(4);
		ordOrdersInfo.setOrderType(3);
		ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.PENDING_DEPARTURE);
		if(StringUtils.isNotEmpty(orderInfo.getPullPhone())){
			ordOrdersInfo.setDisId(user.getUserId());
			ordOrdersInfo.setDisTime(d);
		}
		ordOrdersInfo.setFreight(freight);
		if(orderInfo.getDesDistrict()!=null){
			ordOrdersInfo.setCountyId(orderInfo.getDesDistrict());
		}
		ordOrdersInfo.setRemark(orderInfo.getRemarks());
		session.save(ordOrdersInfo);
		OrdBusiPerson ordBusiPerson = new OrdBusiPerson();
		ordBusiPerson.setOrderId(ordOrdersInfo.getOrderId());
		ordBusiPerson.setCreateDate(d);
		ordBusiPerson.setInputTime(d);
		ordBusiPerson.setInputUserId(user.getUserId());
		ordBusiPerson.setInputUserName(user.getUserName());
		ordBusiPerson.setOpId(user.getUserId());
		ordBusiPerson.setCreateId(user.getUserId());
		if(orderInfo.getPullId()!=null){
			ordBusiPerson.setWorkerId(orderInfo.getPullId());
		}
		if(StringUtils.isNotEmpty(orderInfo.getPullName())){
			ordBusiPerson.setWorkerName(orderInfo.getPullName());
		}
		if(StringUtils.isNotEmpty(orderInfo.getPullPhone())){
			ordBusiPerson.setWorkerPhone(orderInfo.getPullPhone());
		}
		session.save(ordBusiPerson);
		OrdGoodsInfo goodsInfo = new OrdGoodsInfo();
		goodsInfo.setConsignorName(StringUtils.defaultIfEmpty(orderInfo.getConsignor(), ""));
		goodsInfo.setConsignorBill(StringUtils.defaultIfEmpty(orderInfo.getConsignorPhone(), ""));
		goodsInfo.setCreateDate(d);
		goodsInfo.setOpDate(d);
		goodsInfo.setOpId(user.getUserId());
		goodsInfo.setProduct(orderInfo.getProductName());
		goodsInfo.setCount(orderInfo.getNumber());
		goodsInfo.setCreateId(user.getUserId());
		goodsInfo.setIsPickup(1);
		goodsInfo.setOrderNo(orderNo);
		goodsInfo.setOrderId(ordOrdersInfo.getOrderId());
		goodsInfo.setProvinceId(startProvinceId);
		goodsInfo.setCityId(startCityId);
		goodsInfo.setVolume(ordOrdersInfo.getVolume());
		goodsInfo.setWeight(orderInfo.getWeight());
		goodsInfo.setTrackingNum(ordOrdersInfo.getTrackingNum());
		session.save(goodsInfo);
		//TODO 写操作日志
		ordOrdersInfo.setIdNo(goodsInfo.getIdNo());
		
		return ordOrdersInfo;
	}
	
	
	/***
	 * 报表统计
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>>  statisticsData(Map<String,Object> inParam) throws Exception{ 
		int type = DataFormat.getIntKey(inParam, "type");
		if(type!=1&&type!=2){
			throw new BusinessException("统计类型不对!");
		}
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sql = new StringBuffer();
		String startTime="";
		String endTime="";
		Date d = new Date();
		List<Integer> ls = new ArrayList<Integer>();
		if(type==1){
			endTime=DateUtil.getCurrDateTimeEnd();
			startTime= DateUtil.formatDate(DateUtil.addMonth(d, -5),DateUtil.YEAR_MONTH_FIRSTDAY+" 00:00:00");
			for(int i=6;i>0;i--){
				ls.add(DateUtil.getMonth(DateUtil.addMonth(d, -(i-1))));
			}
		}else{
			endTime=DateUtil.getCurrDateTimeEnd();
			startTime=DateUtil.formatDate(DateUtil.addDate(new Date(), -6),DateUtil.DATE_ZEROTIME_FORMAT);
			ls.add(Integer.parseInt( getWeekOfDate(DateUtil.addDate(d, -6), 0)));
			ls.add(Integer.parseInt( getWeekOfDate(DateUtil.addDate(d, -5), 0)));
			ls.add(Integer.parseInt( getWeekOfDate(DateUtil.addDate(d, -4), 0)));
			ls.add(Integer.parseInt( getWeekOfDate(DateUtil.addDate(d, -3), 0)));
			ls.add(Integer.parseInt( getWeekOfDate(DateUtil.addDate(d, -2), 0)));
			ls.add(Integer.parseInt( getWeekOfDate(DateUtil.addDate(d, -1), 0)));
			ls.add(Integer.parseInt( getWeekOfDate(d, 0)));
		}
		//配送员
		if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.DISTRIBUTION){
			if(type==1){
				sql.append("select MONTH(p.DELIVERY_TIME) AS months,count(a.order_Id) count from order_info as o,ord_orders_info as a,ord_busi_person as p where o.ORDS_ID=a.order_id and a.order_id=p.order_id  and p.delivery_id=:userId and p.DELIVERY_TIME>=:startTime and  p.DELIVERY_TIME<=:endTime  group by months order by a.order_id desc");
			}else{
				sql.append("select DATE_FORMAT(p.DELIVERY_TIME,'%w') days,count(a.order_Id) count from order_info as o,ord_orders_info as a,ord_busi_person as p where o.ORDS_ID=a.order_id and a.order_id=p.order_id  and p.delivery_id=:userId and  p.DELIVERY_TIME>=:startTime and  p.DELIVERY_TIME<=:endTime group by days  order by a.order_id desc ");
			}
			//开单员
		}else if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER){
			if(type==1){
				sql.append("select MONTH(o.CREATE_TIME) AS months,count(o.order_Id) count from order_info as o where o.CREATE_ID=:userId and o.CREATE_TIME>=:startTime and  o.CREATE_TIME<=:endTime  group by months order by o.order_id desc");
			}else{
				sql.append("select DATE_FORMAT(o.CREATE_TIME,'%w') days,count(o.order_Id) count from order_info as o  where   o.CREATE_ID=:userId and  o.CREATE_TIME>=:startTime and  o.CREATE_TIME<=:endTime group by days order by o.order_id  desc ");
			}
		}else if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){//拉包工
			if(type==1){//FROM ord_orders_info AS O ,ord_goods_info AS G ,ord_busi_person AS P WHERE O.ORDER_ID=G.ORDER_ID AND O.ORDER_ID=P.ORDER_ID AND O.CREATE_TYPE!=4
				sql.append("select MONTH(P.INPUT_TIME) AS months,count(o.order_Id) count from ord_orders_info as o,ord_busi_person AS P where  O.ORDER_ID=P.ORDER_ID and p.worker_id=:userId and P.INPUT_TIME>=:startTime and  P.INPUT_TIME<=:endTime  and o.order_state in (4,5,6,7) group by months order by o.order_id desc");
			}else{
				sql.append("select DATE_FORMAT(P.INPUT_TIME,'%w') days,count(o.order_Id) count from ord_orders_info as o,ord_busi_person AS P  where   O.ORDER_ID=P.ORDER_ID and  p.worker_id=:userId and  P.INPUT_TIME>=:startTime and  P.INPUT_TIME<=:endTime and o.order_state in (4,5,6,7) group by days order by o.order_id desc ");
			}
		}else if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS){//客户
			if(type==1){
				sql.append("select MONTH(o.CREATE_DATE) AS months,count(o.order_Id) count from ord_orders_info as o where o.CREATE_ID=:userId and o.CREATE_DATE>=:startTime and  o.CREATE_DATE<=:endTime  group by months order by o.order_id desc ");
			}else{
				sql.append("select DATE_FORMAT(o.CREATE_DATE,'%w') days,count(o.order_Id) count from ord_orders_info as o  where   o.CREATE_ID=:userId and  o.CREATE_DATE>=:startTime and  o.CREATE_DATE<=:endTime group by days order by o.order_id desc ");
			}
		}
		Query query = session.createSQLQuery(sql.toString());
		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);
		query.setParameter("userId", user.getUserId());
		String[] weekDaysName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" }; 
		List<Object[]> list = query.list();
		List<Map<String,Object>>  rtnList = new ArrayList<Map<String,Object>> ();
		if(type==1){
			//(处理查询数据)
			for(int i : ls){
				boolean isExist=false;
				for(Object[] js : list){
					if((i+"").equals(js[0]+"")){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("key", i+"月");
						map.put("count", js[1]+"");
						rtnList.add(map);
						isExist=true;
						break;
					}
				}
				if(!isExist){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("key", i+"月");
					map.put("count", 0+"");
					rtnList.add(map);
				}
			}
		}else{
			//(处理查询数据)
			for(int i : ls){
				boolean isExist=false;
				for(Object[] js : list){
					String day=js[0]!=null?Integer.parseInt(js[0]+"")+"":"";
					if((i+"").equals(day+"")){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("key", weekDaysName[i]);
						map.put("count", js[1]+"");
						rtnList.add(map);
						isExist=true;
						break;
					}
				}
				if(!isExist){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("key", weekDaysName[i]+"");
					map.put("count", 0);
					rtnList.add(map);
				}
			}
		}
		return rtnList;
	}


	 /** 
    * 根据日期获得星期 
    * @param date 
    * @return 
    */ 
	public  String getWeekOfDate(Date date,int type) { 
	  String[] weekDaysName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" }; 
	  String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" }; 
	  Calendar calendar = Calendar.getInstance(); 
	  calendar.setTime(date); 
	  int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; 
	  if(type==1){
		  return weekDaysName[intWeek]; 
	  }else{
		  return weekDaysCode[intWeek]; 
	  }
	} 

	/**
	 * 单号查信息
	 * @param orderNo
	 * @return
	 */
	public OrdOrdersInfo getOrdOrdersInfoByOrderNo(String orderNo){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrdOrdersInfo.class);
		List<OrdOrdersInfo> list = ca.add(Restrictions.eq("orderNo", orderNo)).list();
		if(list != null && list.size() == 1){
			return list.get(0);
		}
		return null;
	}

	
	
	
	
	/**
	 * 微信订单记录
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map queryOrders(Map<String, String> inParam) throws Exception {
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT distinct O.ORDER_ID FROM ord_orders_info AS O ,ord_goods_info AS G ,ord_busi_person AS P WHERE O.ORDER_ID=G.ORDER_ID AND O.ORDER_ID=P.ORDER_ID AND O.create_type!=4   ");
		if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS){
			sql.append(" and (O.CREATE_ID=:userId OR g.CONSIGNOR_BILL=:bill OR O.CONSIGNEE_BILL=:bill )");
		}
		if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
			sql.append(" and (O.CREATE_ID=:userId OR P.WORKER_PHONE=:bill  )");
		}
		sql.append(" order by o.order_id desc ");
		Query query = session.createSQLQuery(sql.toString());
		if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS){
			query.setParameter("userId", user.getUserId());
			query.setParameter("bill", user.getTelphone());
		}
		if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
			query.setParameter("userId", user.getUserId());
			query.setParameter("bill", user.getTelphone());
		}
		IPage p = PageUtil.gridPage(query);
		List<Long> orderIdList = new ArrayList<Long>();
		List<Object> list = (List<Object>) p.getThisPageElements();
		for (Object orderId : list) {
			orderIdList.add(((BigInteger) orderId).longValue());
		}
		Map<Long, OrdQueryByWeChatOut> map = new HashMap<Long, OrdQueryByWeChatOut>();
		List<OrdQueryByWeChatOut> outList = new ArrayList<OrdQueryByWeChatOut>();
		if (orderIdList.size() > 0) {
			Criteria ca=session.createCriteria(OrdOrdersInfo.class);
			ca.add(Restrictions.in("orderId", orderIdList));
			ca.addOrder(Order.desc("orderId"));
			List<OrdOrdersInfo>orderList=ca.list();
			if(list!=null && list.size()>0){
				for(OrdOrdersInfo orders:orderList){
					OrdQueryByWeChatOut out=new OrdQueryByWeChatOut();
					  long cityId=orders.getCityId();
					  String cityName = SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(cityId)).getName();
                      out.setCityName(cityName);
                      out.setCreateDate(orders.getCreateDate());
                      Integer orderSate=orders.getOrderState();
                      String stateName = SysStaticDataUtil.getSysStaticDataCodeName("ORDERS_STATE", String.valueOf(orderSate));
                      out.setOrderStateName(stateName);
                      out.setConsigneeName(orders.getConsigneeName());
                      out.setConsigneeBill(orders.getConsigneeBill());
                      out.setDeliveryAddress(orders.getAddress());
                      out.setOrderId(orders.getOrderId());
                      outList.add(out);
				}
			}
		}
		p.setThisPageElements(outList);
		Pagination<Object[]> page = new Pagination<Object[]>(p);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		return rtnMap;
	}
	/**
	 * 微信订单详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public OrdQueryDetailByWeChatOut  queryOrdersInfo(Map<String,Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if(orderId<0){
			throw new BusinessException("订单编号不能为空！");
		}
		OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if(ordOrdersInfo==null){
			throw new BusinessException("订单信息不存在");
		}
		OrdQueryDetailByWeChatOut outParam = new OrdQueryDetailByWeChatOut();
		outParam.setOrderId(ordOrdersInfo.getOrderId());
		outParam.setDeliveryAddress(ordOrdersInfo.getAddress());
		long cityId=ordOrdersInfo.getCityId();
		String cityName = SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(cityId)).getName();
		outParam.setCityName(cityName);
		Integer orderSate=ordOrdersInfo.getOrderState();
		String stateName = SysStaticDataUtil.getSysStaticDataCodeName("ORDERS_STATE", String.valueOf(orderSate));
		outParam.setOrderState(stateName);
		outParam.setConsigneeName(ordOrdersInfo.getConsigneeName());
		outParam.setConsigneeBill(ordOrdersInfo.getConsigneeBill());
		outParam.setTrackingNum(ordOrdersInfo.getTrackingNum());
		outParam.setOrderNo(ordOrdersInfo.getOrderNo());
		outParam.setCompanyName(ordOrdersInfo.getCompanyName());
		outParam.setOrderId(ordOrdersInfo.getOrderId());
		String serviceTypeName=SysStaticDataUtil.getSysStaticDataCodeName("SERVICE_TYPE", String.valueOf(ordOrdersInfo.getServiceType()));
		outParam.setServiceTypeName(serviceTypeName);
		outParam.setTrackingNum(ordOrdersInfo.getTrackingNum());
		String paymentTypeName=SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE_YQ",String.valueOf(ordOrdersInfo.getPaymentType()));
		outParam.setPaymentTypeName(paymentTypeName);
		outParam.setCreateDate(ordOrdersInfo.getCreateDate());
		Criteria ca = session.createCriteria(OrdGoodsInfo.class);
		ca.add(Restrictions.eq("orderId", ordOrdersInfo.getOrderId()));
		List<OrdGoodsInfo> list = ca.list();
		for (OrdGoodsInfo ordGoodsInfo : list) {
			if(user.getTelphone().equals(ordOrdersInfo.getConsigneeBill())||
					SysStaticDataEnumYunQi.USER_TYPE_YUNQI.DISTRIBUTION==user.getUserType().intValue()
					||SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER==user.getUserType().intValue()
					||SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL==user.getUserType().intValue()
					||user.getUserId().longValue()==ordOrdersInfo.getCreateId().longValue()){
				Map rtnMap = new HashMap();
				rtnMap.put("consignorName", ordGoodsInfo.getConsignorName());
				rtnMap.put("consignorBill", ordGoodsInfo.getConsignorBill());
				rtnMap.put("address", ordGoodsInfo.getProvineName()+ordGoodsInfo.getCityName()+ordGoodsInfo.getAddress());
				outParam.getPickList().add(rtnMap);
				outParam.setPlanPickupTime(ordGoodsInfo.getPlanPickupTime());
			}else{
				if(user.getTelphone().equals(ordGoodsInfo.getConsignorBill())){
					Map rtnMap = new HashMap();
					rtnMap.put("consignorName", ordGoodsInfo.getConsignorName());
					rtnMap.put("consignorBill", ordGoodsInfo.getConsignorBill());
					rtnMap.put("address", ordGoodsInfo.getProvineName()+ordGoodsInfo.getCityName()+ordGoodsInfo.getAddress());
					outParam.getPickList().add(rtnMap);
					outParam.setPlanPickupTime(ordGoodsInfo.getPlanPickupTime());
				}
			}
		}
		OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, ordOrdersInfo.getOrderId());
		if(ordBusiPerson!=null){
			outParam.setCarrierName(ordBusiPerson.getWorkerName());
			outParam.setCarrierBill(ordBusiPerson.getWorkerPhone());
		}
		return outParam;
	}
	/**
	 * 工号获取拉包工订单
	 * @param jobNumber
	 * @return
	 */
	public Map<String,Object> orderNoByJobNumber(String jobNumber){
		if(StringUtils.isEmpty(jobNumber)){
			throw new BusinessException("请输入工号！");
		}
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		Organization org = (Organization) session.get(Organization.class, user.getOrgId());
		if(org == null){
			throw new BusinessException("无该开单员的信息！");
		}else if(org.getRegionId() == null){
			throw new BusinessException("无该开单页的城市！");
		}
	 	Query query =session.createSQLQuery(" SELECT o.order_no, o.CITY_ID, o.CONSIGNEE_NAME, o.CONSIGNEE_BILL FROM cm_user_info c INNER JOIN cm_user_info_pull p ON c.USER_ID = p.USER_ID INNER JOIN ord_busi_person b ON b.WORKER_ID = c.USER_ID INNER JOIN ord_orders_info o ON o.ORDER_ID = b.ORDER_ID where o.ORDER_STATE in :orderState AND p.JOB_NUMBER = :jobNumber and c.state = :state ");
		query.setParameterList("orderState", new Integer[]{SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING,SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP});
		query.setParameter("state", SysStaticDataEnumYunQi.STS.VALID);
		query.setParameter("jobNumber", org.getRegionId()+jobNumber);
		List<Object[]> list = query.list();
		List<OrderNoOut> listOut = new ArrayList<OrderNoOut>();
		if(list != null && list.size() > 0){
			for (Object[] objects : list) {
				OrderNoOut out = new OrderNoOut();
				out.setOrderNo(objects[0] != null ? String.valueOf(objects[0]) : "");
				out.setCityName(objects[1] != null ? SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(objects[1])).getName() : "");
				out.setPullName(objects[2] != null ? String.valueOf(objects[2]) : "");
				out.setPullPhone(objects[3] != null ? String.valueOf(objects[3]) : "");
				listOut.add(out);
			}
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(listOut));
	}
	/**
	 * 签收下详情（601046）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> signOrdersInfo(Map<String,Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if (orderId < 0) {
			throw new BusinessException("请传订单id！");
		}
		OrdSignInfo sign = (OrdSignInfo) session.get(OrdSignInfo.class, orderId);
		if (sign == null) {
			throw new BusinessException("无该订单签收信息！");
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("signName", sign != null ? sign.getSignName() != null ? sign.getSignName() : "" :"");
		map.put("signPhone", sign != null ? sign.getExt1() != null ? sign.getExt1() : "" :"");
		map.put("signTime", sign != null ? sign.getSignDate() != null ? DateUtil.formatDateByFormat(sign.getSignDate(), "yyyy-MM-dd HH:mm") : "" :"");
		map.put("remark", sign != null ? sign.getSignDesc() != null ? sign.getSignDesc() : "" :"");
		String url = sign != null ? sign.getFlowId() != null ? sign.getFlowId() : "" :"";
		String[] arr = url.split(",");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		SysAttachSV sysAttachSV = (SysAttachSV) SysContexts.getBean("sysAttachSV");
		if (arr != null && arr.length > 0) {
			for(String temp : arr){
				Map<String,Object> img = new HashMap<String,Object>();
				img.put("signImgId", temp);
				img.put("signImgUrl", sysAttachSV.getAttachFile(temp));
				list.add(img);
			}
		}else{
			Map<String,Object> img = new HashMap<String,Object>();
			img.put("signImgId", "");
			img.put("signImgUrl", "");
			list.add(img);
		}
		map.put("items", list);
		return map;
	}
	
	
	
	/**
	 * 抢单列表
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> grabOrdersList(Map<String,Object> inParam){
		BaseUser user = SysContexts.getCurrentOperator();
		long userType = user.getUserType();
		if (userType != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL) {
			throw new BusinessException("只有拉包工才能查看抢单列表！");
		}
		String latitude = DataFormat.getStringKey(inParam, "latitude");//纬度
		String longitude = DataFormat.getStringKey(inParam, "longitude");//经度
		String orderId=DataFormat.getStringKey(inParam, "orderId");
		long userId = user.getUserId();//拉包工id
		String loginAcct = user.getTelphone();//拉包工账号
		
		
		List<Map<String,Object>> listOut = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> orderInfoMap=null;
		if(StringUtils.isNotEmpty(orderId)){
			 	orderInfoMap=BusiGrabControlOut.getOrderInfoMap(orderId.toString());
			 	Map<String, Object> out = new HashMap<String, Object>();
			 	if(orderInfoMap!=null){
			 		out.put("orderId", orderInfoMap.get(BusiGrabConsts.Order.ORDER_ID));
					out.put("pickAddress", orderInfoMap.get(BusiGrabConsts.Order.PICK_ADDR));
					out.put("desCityName", orderInfoMap.get(BusiGrabConsts.Order.DEST_CITY_NAME));
					out.put("orderPickTime", orderInfoMap.get(BusiGrabConsts.Order.HANDLER_TIME));
					out.put("ordersTime", orderInfoMap.get(BusiGrabConsts.Order.CREATE_TIME));
					orderInfoMap=out;
			 	}else{
			 		orderId=null;
			 	}
				
		}
		listOut=getOrderInfoFromRedis(user.getTelphone(), latitude, longitude, orderId, orderInfoMap);
		
		if(listOut.size()==0){
			return new HashMap<String, Object>();
		}
		
		Map<String,Object> map = JsonHelper.parseJSON2Map(JsonHelper.json(listOut));
		return map;
	}
	
	/**
	 * 如果传入的orderId表示该订单没有被抢单，需要把该数据放在第一列
	 * 
	 * @param billId
	 * @param latitude
	 * @param longitude
	 * @param orderId
	 * @return
	 */
	private List<Map<String,Object>> getOrderInfoFromRedis(String billId,String latitude,String longitude,String orderId,Map<String, Object> orderMap){
		List<Map<String,Object>> listOut = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> pushList= getOrderInfoByPush(billId,10);
		SysCfg sysCfg = SysCfgUtil.getSysCfg(EnumConstsYQ.GRAB_ORDERS.GRAB_ORDER_DISTANCE);
		Set<String> orderSet=new HashSet<String>();
		
		
		if(StringUtils.isNotEmpty(orderId)){
			listOut.add(orderMap);
			orderSet.add(orderId);
		}
		log.error("抢单列表：手机号码["+billId+"],获取推送过的数据:["+pushList.size()+"]");
		if(pushList.size()>0){
				for (Map<String, Object> map : pushList) {
						String orderIdTemp=map.get(BusiGrabConsts.Order.ORDER_ID).toString();
						if(orderSet.contains(orderIdTemp)){
							continue;
						}
						Map<String, Object> out = new HashMap<String, Object>();
						out.put("orderId", map.get(BusiGrabConsts.Order.ORDER_ID));
						out.put("pickAddress", map.get(BusiGrabConsts.Order.PICK_ADDR));
						out.put("desCityName", map.get(BusiGrabConsts.Order.DEST_CITY_NAME));
						out.put("orderPickTime", map.get(BusiGrabConsts.Order.HANDLER_TIME));
						out.put("ordersTime", map.get(BusiGrabConsts.Order.CREATE_TIME));
						orderSet.add(orderIdTemp);
						listOut.add(out);
					if(listOut.size()==5){
						//已经够5条数据
						break;
					}
				}
		}
		
		int count=5;
		if(listOut.size()<5){
			count=count-listOut.size();
			List<Map<String,Object>> listMap = BusiGrabControlOut.getOrderInfoByDistance(Double.valueOf(StringUtils.isNotBlank(latitude) ? latitude : "0"), Double.valueOf(StringUtils.isNotBlank(longitude) ? longitude : "0"), Double.valueOf(sysCfg.getCfgValue()), count);
			log.error("抢单列表：手机号码["+billId+"],获取附近的数据:["+listMap.size()+"]");
			if (listMap!=null&&listMap.size()>0) {
				for (Map<String, Object> map : listMap) {
					Map<String, Object> out = new HashMap<String, Object>();
					String orderIdTemp=map.get(BusiGrabConsts.Order.ORDER_ID).toString();
					if(orderSet.contains(orderIdTemp)){
						continue;
					}
					
					out.put("orderId", map.get(BusiGrabConsts.Order.ORDER_ID));
					out.put("pickAddress", map.get(BusiGrabConsts.Order.PICK_ADDR));
					out.put("desCityName", map.get(BusiGrabConsts.Order.DEST_CITY_NAME));
					out.put("orderPickTime", map.get(BusiGrabConsts.Order.HANDLER_TIME));
					out.put("ordersTime", map.get(BusiGrabConsts.Order.CREATE_TIME));
					orderSet.add(orderIdTemp);
					listOut.add(out);
					if(listOut.size()==5){
						//已经够5条数据
						break;
					}
				}
			}
		}
		
		
		
		return listOut;
	}
	
	/**
	 * 通过手机号码，查询之前推送给拉包工 的还没有被抢单的数据
	 * @param billId
	 * @return
	 */
	public List<Map<String,Object>> getOrderInfoByPush(String billId,int count){
		List<Map<String, Object>> retList=new ArrayList<Map<String,Object>>();
		String yyyyMM=DateUtil.formatDate(new Date(), DateUtil.YEAR_MONTH_FORMAT2);
		Session session = SysContexts.getEntityManager(true);
		String dateBegin=DateUtil.formatDate(new Date(), DateUtil.DATE_FORMAT)+" 00:00:00";
		String dateEnd=DateUtil.formatDate(new Date(), DateUtil.DATE_FORMAT)+" 23:59:59";
		
		StringBuffer sqlBuf=new StringBuffer("select i.order_id from ord_orders_info i,sys_sms_send_his_").append(yyyyMM).append(" s");
		sqlBuf.append(" where s.obj_type=3 and s.bill_id=:billId and s.obj_id=i.order_id");
		sqlBuf.append(" and s.send_date >:dateBegin and s.send_date<:dateEnd ");
		sqlBuf.append(" and i.ORDER_STATE=1 limit ").append(count);
	 	Query query =session.createSQLQuery(sqlBuf.toString());
		query.setParameter("dateBegin", dateBegin);
		query.setParameter("billId", billId);
		query.setParameter("dateEnd", dateEnd);
		List<Object> list = query.list();
		if(list!=null){
			for(Object objects:list){
				Object orderId=objects;
				Map<String, Object> orderInfoMap=BusiGrabControlOut.getOrderInfoMap(orderId.toString());
				if(orderInfoMap!=null){
					retList.add(orderInfoMap);
				}
			}
		}
		return retList;
	}
	
	/**
	 * 抢单
	 * （1）校验考勤：是否上班状态；
	 * （2）校验接单数：是否接单已经超过最大接单数；
	 * （3）校验拉黑：是否被客户拉黑、是否被专线拉黑、是否被平台拉黑、是否有效；
	 * （4）校验单号：是否已经被抢的单，是否存在该单；
	 * （5）抢单成功：修改订单信息、删除抢单的号码；
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> grabOrders(Map<String,Object> inParam) throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager();
		Date date = new Date();
		long userId = user.getUserId();
		if (user.getUserType() != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL) {
			throw new BusinessException("抢单失败！");
		}
		CmUserInfoPullTF cmUserInfoPullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
		CmUserInfoPull pull = cmUserInfoPullTF.getByUserPull(userId);
		if (pull == null) {
			throw new BusinessException("抢单失败！");
		}
		//（1）校验考勤：是否上班状态；
		if (pull.getPullWork() != SysStaticDataEnumYunQi.PULL_WORK_YUNQI.PULL_WORK1) {
			throw new BusinessException("抢单失败，请打卡上班！");
		}
		//（2）校验接单数：是否接单已经超过最大接单数；
		if (pull.getSingularNum() != null && pull.getSingularNum().longValue() >=  pull.getDefaultSingularNum().longValue()) {
			throw new BusinessException("抢单失败，你有未完成订单，请先完成订单后继续抢单！");
		}
		Criteria ca = session.createCriteria(CmPullBlack.class);
		List<CmPullBlack> blackList = ca.add(Restrictions.eq("bill", user.getTelphone())).add(Restrictions.eq("status", SysStaticDataEnumYunQi.STS.VALID)).list();
		CmPullBlack black = null;
		boolean isBlack = false;
		if(blackList != null && blackList.size()>0){
			black = blackList.get(0);
			isBlack = true;
		}else{
			isBlack = false;
		}
		long orderId = DataFormat.getLongKey(inParam,"orderId");
		OrdOrdersInfo order = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		OrdBusiPerson busi = (OrdBusiPerson) session.get(OrdBusiPerson.class, orderId);
		//（3）校验拉黑：是否被客户拉黑、是否被专线拉黑、是否被平台拉黑、是否有效；
		if(isBlack && black.getType().intValue() == SysStaticDataEnumYunQi.BLACK_TYPE.BLACK_PLAT){//平台
			throw new BusinessException("抢单失败，该单已经被抢了！");
		}
		if((isBlack && order.getCreateId() != null) && 
				((black.getType().intValue() == SysStaticDataEnumYunQi.BLACK_TYPE.BLACK_CUSTOMER 
					&& black.getBusinessId().longValue() == order.getCreateId().longValue()))){//客户
			throw new BusinessException("抢单失败，该单已经被抢了！");
		}
		if ((isBlack && order.getTenantId() != null) && 
				((black.getType().intValue() == SysStaticDataEnumYunQi.BLACK_TYPE.BLACK_CHAIN 
						&& black.getBusinessId().longValue() == order.getTenantId().longValue()))) {//专线
			throw new BusinessException("抢单失败，该单已经被抢了！");
		}
		
		//（4）校验单号：是否已经被抢的单，是否存在该单；
		if(order == null || order.getOrderState() != SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING 
				|| (busi.getWorkerId() != null && busi.getWorkerId() > 0)
				|| StringUtils.isNotEmpty(busi.getWorkerName())){
			throw new BusinessException("抢单失败，该单已经被抢了！");
		}
		//（5）抢单成功：修改订单信息、删除抢单的号码；
		StringBuffer upSql = new StringBuffer(" UPDATE ord_busi_person p,ord_orders_info o SET ");
		upSql.append(" p.WORKER_ID = :pullId, ");
		upSql.append(" p.WORKER_PHONE = :pullPhone, ");
		upSql.append(" p.WORKER_NAME = :pullName, ");
		upSql.append(" o.ORDER_STATE = :orderState, ");
		upSql.append(" o.DIS_ID = :disId, ");
		upSql.append(" o.DIS_TIME = :disTime, ");
		upSql.append(" o.DIS_OP_NAME = :disOpName ");
		upSql.append(" WHERE p.ORDER_ID = o.ORDER_ID AND o.ORDER_ID = :orderId AND o.ORDER_STATE = :queryState AND p.WORKER_ID IS NULL ");
		
		Query query = session.createSQLQuery(upSql.toString());
		query.setParameter("pullId", userId);
		query.setParameter("pullPhone", user.getTelphone());
		query.setParameter("pullName", user.getUserName());
		query.setParameter("orderState", SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP);
		query.setParameter("disId", 0L);
		query.setParameter("disTime", date);
		query.setParameter("disOpName", "抢单");
		query.setParameter("orderId", orderId);
		query.setParameter("queryState", SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING);
		boolean isSuccess = false;
		int i = 0;
		try {
			i = query.executeUpdate();
		} catch (Exception e) {
			throw new BusinessException("抢单失败，该订单已被抢走了！");
		}
		if (i != 2) {
			throw new BusinessException("抢单失败，该订单已被抢走了！");
		}else{
			isSuccess = true;
		}
		if (isSuccess) {	
			OrdOrdersInfo ordOrdersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
			//OrdBusiPerson ordBusiPerson = (OrdBusiPerson) session.get(OrdBusiPerson.class, orderId);
			// 写日志
			OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
			ordLogNewTF.disWorkerLog(0, user.getTelphone(), null, ordOrdersInfo, user.getUserName());
			
			Query queryLog = session.createSQLQuery("select PROVINCE_ID,city_id,address,consignor_bill from ord_goods_info where order_id=:orderId");
			queryLog.setParameter("orderId", ordOrdersInfo.getOrderId());
			List<Object[]> list = queryLog.list();
			if(list.size()==1){
				for (Object[] objects : list) {
					if(objects[3]!=null&&StringUtils.isNotEmpty(objects[3]+"")){
						SmsYQUtil.disWorkerToConsigor(-1L, ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(), user.getUserName(), user.getTelphone(), objects[3].toString());
					}
					break;
			     }
			}
			else {
				for (Object[] objects : list) {
					if(objects[3]!=null&&StringUtils.isNotEmpty(objects[3]+"")){
						SmsYQUtil.disWorkerToConsigor(-1L, ordOrdersInfo.getOrderId(), ordOrdersInfo.getOrderNo(), user.getUserName(), user.getTelphone(), objects[3].toString());
					}
					break;
				}
			}
		//TODO 更新接单数
		CmUserInfoPull cmUserInfoPull= cmUserInfoPullTF.addSingularNum(user.getUserId());
		BusiGrabControlOut.updateUserInfoSingularNum(cmUserInfoPull.getUserId(), cmUserInfoPull.getSingularNum().intValue());
		//TODO 调用删除redis里的单号方法
		BusiGrabControlOut.delAllOrderInfoAndGps(String.valueOf(orderId));
			
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
		  
	}
	/**
	 * 取消抢单订单信息
	 * @param inParam
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> cancelGrabOrders(Map<String,Object> inParam) throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager();
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String remark = DataFormat.getStringKey(inParam, "remark");
		CmUserInfoPullTF cmUserInfoPullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
		
		if (orderId < 0) {
			throw new BusinessException("请传入订单编号！");
		}
//		if (StringUtils.isEmpty(remark)) {
//			throw new BusinessException("请输入取消原因！");
//		}
		OrdOrdersInfo ordersInfo = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, orderId);
		if (ordersInfo==null) {
			throw new BusinessException("无该订单信息，不能取消！");
		}
		if (ordersInfo.getOrderState().intValue() != SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP) {
			throw new BusinessException("该订单【"+ordersInfo.getOrderNo()+"】，不是待提货状态不能取消！");
		}
		ordersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.DIS_ING);
		OrdBusiPerson busi = (OrdBusiPerson) session.get(OrdBusiPerson.class, orderId);
		CmUserInfoPull pull = cmUserInfoPullTF.getByUserPull(busi.getWorkerId());
		List<OrdGoodsInfo> goods = session.createCriteria(OrdGoodsInfo.class).add(Restrictions.eq("orderId", orderId)).list();
		if (goods == null || goods.size()<0) {
			throw new BusinessException("提货信息不存在！");
		}
		ordersInfo.setDisId(null);
		ordersInfo.setDisOpName(null);
		ordersInfo.setDisTime(null);
		if (busi==null) {
			throw new BusinessException("配送信息不存在！");
		}
		busi.setWorkerId(null);
		busi.setWorkerName(null);
		busi.setWorkerPhone(null);
		for (OrdGoodsInfo ordGoodsInfo : goods) {
			ordGoodsInfo.setIsPickup(0);
			session.update(ordGoodsInfo);
		}
		session.update(busi);
		session.update(ordersInfo);
		
		
		CmUserInfo cmUserInfo = (CmUserInfo) session.get(CmUserInfo.class, user.getUserId());
		// 写日志
		if (cmUserInfo.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL) {
			//发给发货人短信通知订单取消了
			for (OrdGoodsInfo ordGoodsInfo : goods) {
				if (StringUtils.isNotBlank(ordGoodsInfo.getConsignorBill())) {
					if (isRegisterUser(session, ordGoodsInfo.getConsignorBill())) {
						SmsYQUtil.cancelOrderSendConsignorPull(user.getTenantId() != null ? user.getTenantId() : -1, ordersInfo.getOrderId(), ordersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.NOTICE_TYPE, ordGoodsInfo.getConsignorBill(),user.getUserName());
					}else{
						SmsYQUtil.cancelOrderSendConsignorPull(user.getTenantId() != null ? user.getTenantId() : -1, ordersInfo.getOrderId(),  ordersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, ordGoodsInfo.getConsignorBill(),user.getUserName());
					}
					if (isWeChatUser(session, ordGoodsInfo.getConsignorBill())) {
						SmsYQUtil.cancelOrderSendConsignorPull(user.getTenantId() != null ? user.getTenantId() : -1, ordersInfo.getOrderId(),  ordersInfo.getOrderNo(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordGoodsInfo.getConsignorBill(),user.getUserName());
					}
				}
			}
			OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
			ordLogNewTF.cancerOrderLogPull(user.getUserId(),2,user, ordersInfo);
		}
		//TODO 取消接单数
		CmUserInfoPull cmUserInfoPull= cmUserInfoPullTF.reduceSingularNum(pull.getUserId());
		BusiGrabControlOut.updateUserInfoSingularNum(cmUserInfoPull.getUserId(), cmUserInfoPull.getSingularNum().intValue());
		SysCfg sysCfg = SysCfgUtil.getSysCfg("ORDERS_TIMEOUT");
		long timeOut = (new Date().getTime() - ordersInfo.getCreateDate().getTime()) / 1000L / 60L;
		if (timeOut <= Long.parseLong(sysCfg.getCfgValue())) {
			//TODO 调用redis里加进抢单逻辑里
			BusiGrabControlOut.addOrderInfo(String.valueOf(orderId), busi.getInputUserId() != null ? busi.getInputUserId() : -1
					, ordersInfo.getConTenantId() != null ? ordersInfo.getConTenantId() : -1, 
					Double.valueOf(goods.get(0).getLat()), Double.valueOf(goods.get(0).getLon()), ordersInfo.getOrderNo(), 
					DateUtil.formatDate(ordersInfo.getCreateDate(), "yyyy-MM-dd HH:mm:ss"), 
					DateUtil.formatDate(goods.get(0).getPlanPickupTime(), "yyyy-MM-dd HH:mm:ss"), 
					goods.get(0).getProvineName()+goods.get(0).getCityName()+goods.get(0).getCountyName()+goods.get(0).getAddress(), 
					ordersInfo.getCityName());
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tips", "取消成功");
		return map;
	}
	/**
	 * 是否查看物流查询详情
	 * @param inParam
	 * @return
	 */
	public Map<String,String> isShowOrdersInfo(Map<String,Object> inParam){
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		int merchanOrDistri = DataFormat.getIntKey(inParam, "merchanOrDistri");
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		long userId = user.getUserId();
		Map<String,Object> sqlParam = new HashMap<String, Object>();
		int userType = user.getUserType();
		StringBuffer sb = new StringBuffer("SELECT * ");
		if (userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION) {
			if (merchanOrDistri == 1) {
				sb.append(" from order_info t where CREATE_ID = :userId AND t.ords_id = :orderId ");
				sqlParam.put("userId", userId);
				sqlParam.put("orderId", orderId);
			}
			if (merchanOrDistri == 2) {
				sb.append("  FROM order_info t LEFT JOIN ord_busi_person p ON t.ORDS_ID = p.order_id INNER JOIN ord_orders_info o ON o.order_id = p.order_id ");
				sb.append(" where ( p.delivery_id=:userId or (p.GX_END_OP_ID=:userId and o.SERVICE_TYPE=1) ) and o.order_id =:orderId");
				sqlParam.put("orderId", orderId);
				sqlParam.put("userId", userId);
			}
		}else if (userType == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS) {
			sb.append(" from ord_orders_info o  ");
			sb.append(" where (o.order_id in (select distinct order_id from ord_goods_info where CONSIGNOR_BILL = :bill) or (o.consignee_bill = :bill or o.CREATE_ID = :userId )) and o.ORDER_TYPE IN (1,2) ");
			sb.append(" and o.order_id = :orderId ");
			sqlParam.put("orderId", orderId);
			sqlParam.put("bill", user.getTelphone());
			sqlParam.put("userId", userId);
		}
		Query query = session.createSQLQuery(sb.toString());
		List<Object[]> list = query.setProperties(sqlParam).list();
		Map<String,String> map = new HashMap<String, String>();
		if (list!=null&&list.size()>0) {
			map.put("isShow", "1");
		}else{
			map.put("isShow", "2");
		}
		return map;
	}
	/**
	 * 获取订单数据
	 * @param id
	 * @return
	 */
	public OrdOrdersInfo getOrdOrdersInfo(long id){
		Session session = SysContexts.getEntityManager(true);
		return (OrdOrdersInfo) session.get(OrdOrdersInfo.class, id);
	}
	
	/**
	 * 获取订单与人物关系数据
	 */
	public OrdBusiPerson getOrdBusiPerson(long id){
		Session session = SysContexts.getEntityManager(true);
		return (OrdBusiPerson) session.get(OrdBusiPerson.class, id);
	}
	/**
	 * 保存orders
	 * @param ordOrdersInfo
	 */
	public void doUpdateOrders(OrdOrdersInfo ordOrdersInfo,BaseUser user){
		ordOrdersInfo.setOpDate(new Date());
		ordOrdersInfo.setOpId(user.getUserId());
		SysContexts.getEntityManager().update(ordOrdersInfo);
	}
	/**
	 * 保存busi
	 * @param ordBusiPerson
	 */
	public void doUpdateBusi(OrdBusiPerson ordBusiPerson){
		SysContexts.getEntityManager().update(ordBusiPerson);
	}
	/**
	 * 运单状态对应订单状态
	 * @param orderState
	 * @return
	 */
	public int maxOrdersState(int orderState){
		int ordersState = -1;
		switch (orderState) {
		case SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN:
			ordersState = SysStaticDataEnumYunQi.ORDERS_STATE.PENDING_DEPARTURE;
			break;
		case SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DEPARTURE:
			ordersState = SysStaticDataEnumYunQi.ORDERS_STATE.PENDING_DEPARTURE;
			break;
		case SysStaticDataEnumYunQi.ORDER_INFO_STATE.IN_TRANSIT:
			ordersState = SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT;
			break;
		case SysStaticDataEnumYunQi.ORDER_INFO_STATE.FLOATION_CARGO:
			ordersState = SysStaticDataEnumYunQi.ORDERS_STATE.IN_TRANSIT;
			break;
		case SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY:
			ordersState = SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_DELIVERY;
			break;
		case SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN:
			ordersState = SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_SIGN;
			break;
		default:
			ordersState = SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN;
			break;
		}
		return ordersState;
	}
	/**
	 * 保存签收
	 * @param ordSignInfo
	 */
	public void doSaveSign(OrdSignInfo ordSignInfo){
		SysContexts.getEntityManager().save(ordSignInfo);
	}
	/**
	 *查询签收
	 */
	public OrdSignInfo getOrdSignInfo(long orderId){
		return (OrdSignInfo) SysContexts.getEntityManager(true).get(OrdSignInfo.class, orderId);
	}
}

