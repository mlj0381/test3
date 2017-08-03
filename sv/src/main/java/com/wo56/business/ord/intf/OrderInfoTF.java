package com.wo56.business.ord.intf;


import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ac.impl.AcWalletRelSV;
import com.wo56.business.ac.interfaces.AcMyWalletTF;
import com.wo56.business.ac.vo.AcWalletRel;
import com.wo56.business.cm.intf.CmCustomerYQTF;
import com.wo56.business.cm.intf.CmUserInfoPullTF;
import com.wo56.business.cm.intf.CmUserInfoYunQiTF;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.business.grabOrder.out.BusiGrabControlOut;
import com.wo56.business.ord.impl.OrdOrdersInfoSV;
import com.wo56.business.ord.impl.OrderFeeRuleSV;
import com.wo56.business.ord.impl.OrderInfoSV;
import com.wo56.business.ord.vo.OrdChildOpLog;
import com.wo56.business.ord.vo.OrdOpLog;
import com.wo56.business.ord.vo.OrdOrdersInfo;
import com.wo56.business.ord.vo.OrdSignInfo;
import com.wo56.business.ord.vo.OrderFee;
import com.wo56.business.ord.vo.OrderInfo;
import com.wo56.business.ord.vo.OrderInfoChild;
import com.wo56.business.ord.vo.OrderInfoChildSign;
import com.wo56.business.ord.vo.OrderInfoTmsError;
import com.wo56.business.ord.vo.in.OrderInfoSaveIn;
import com.wo56.business.ord.vo.out.OrdChildOrderOpLogListOut;
import com.wo56.business.ord.vo.out.OrdOpLogListOut;
import com.wo56.business.ord.vo.out.OrderInfoAbutmentOut;
import com.wo56.business.ord.vo.out.OrderInfoCountOut;
import com.wo56.business.ord.vo.out.OrderInfoMessagePage;
import com.wo56.business.ord.vo.out.OrderInfoPageOut;
import com.wo56.business.order.out.OrderPostUtil;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.route.vo.RouteTowards;
import com.wo56.business.sys.vo.SysTenantDef;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.sms.utils.SysTenantExtendCacheUtil;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SmsYQUtil;
import com.wo56.common.utils.SysTenantDefCacheDataUtil;

public class OrderInfoTF implements IOrderInfoIntf{
	private static final Log log = LogFactory.getLog(OrderInfoTF.class);
	
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
	 * 开单
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> doSaveOrderInfo(Map<String,String> inParam)throws Exception{
		OrderInfo orderInfo = new OrderInfo();
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		BaseUser user = SysContexts.getCurrentOperator();
		long ordsId = DataFormat.getLongKey(inParam,"ordsId");//下单id
		long desCity = DataFormat.getLongKey(inParam,"desCity");//到站城市ID
		long arrivedOrgId = DataFormat.getLongKey(inParam,"arrivedOrgId");
		String trackingNum = DataFormat.getStringKey(inParam, "trackingNum");
		if (StringUtils.isNotEmpty(trackingNum)) {
			if (orderInfoSV.checkTrackingNum(trackingNum, user.getTenantId())) {
				throw new BusinessException("已经存在该运单号，请重新填写！");
			}
			if (!CommonUtil.isLong(trackingNum)) {
				throw new BusinessException("运单号只能是纯数字！");
			}
			if (trackingNum.length() > 16) {
				throw new BusinessException("运单号不能超过16位数！");
			}
		}else{
			throw new BusinessException("运单号不能为空！");
		}
		if(arrivedOrgId < 0){
			throw new BusinessException("请选择中转站！");
		}
		if(desCity < 0){
			throw new BusinessException("请输入到站城市！");
		}
		/*long desProvince = DataFormat.getLongKey(inParam,"desProvince");//到站省份ID
		if(desProvince < 0){
			throw new BusinessException("请输入到站省份！");
		}*/
		String consignee = DataFormat.getStringKey(inParam, "consignee");//收货人
		if(StringUtils.isEmpty(consignee)){
			throw new BusinessException("请输入收货人名称！");
		}
		String consigneePhone = DataFormat.getStringKey(inParam, "consigneePhone");//收货人电话
		if(StringUtils.isEmpty(consigneePhone)){
			throw new BusinessException("请输入收货人电话！");
		}else{
			if (!CommonUtil.isCheckPhone(consigneePhone)&&!CommonUtil.isCheckMobiPhone(consigneePhone)) {
				throw new BusinessException("请输入正确的收货人电话！");
			}
		}
		String consignor = DataFormat.getStringKey(inParam, "consignor");//发货人
//		if(StringUtils.isEmpty(consignor)){
//			throw new BusinessException("请输入发货人！");
//		}
		
		String consignorPhone = DataFormat.getStringKey(inParam, "consignorPhone");
		if (StringUtils.isNotEmpty(consignorPhone)) {
			if (!CommonUtil.isCheckPhone(consignorPhone)&&!CommonUtil.isCheckMobiPhone(consignorPhone)) {
				throw new BusinessException("请输入正确的发货人电话！");
			}
		}
		String consigneeAddress = DataFormat.getStringKey(inParam, "consigneeAddress");
		String productName = DataFormat.getStringKey(inParam, "productName");//品名
		if(StringUtils.isEmpty(productName)){
			throw new BusinessException("请输入品名！");
		}
		long pullId = DataFormat.getLongKey(inParam,"pullId");//拉包工ID
		int number = DataFormat.getIntKey(inParam, "number");//件数
		if(number < 0){
			throw new BusinessException("请输入件数！");
		}
		String packName = DataFormat.getStringKey(inParam, "packName");//包装
		if(StringUtils.isEmpty(packName)){
			throw new BusinessException("请输入包装！");
		}
		int payment = DataFormat.getIntKey(inParam,"payment");//付款方式
		if(payment < 0){
			throw new BusinessException("请选择付款方式！");
		}
		int interchange = DataFormat.getIntKey(inParam, "interchange");//交接方法
		if(interchange < 0){
			throw new BusinessException("请选择交接方式！");
		}else if(interchange != SysStaticDataEnumYunQi.INTERCHANGE_YUNQI.TAKE_PICK){
			if (StringUtils.isEmpty(consigneeAddress)) {
				throw new BusinessException("请输入收货地址！");
			}
		}
		String freightString = DataFormat.getStringKey(inParam,"freightString");//运费
		if(StringUtils.isEmpty(freightString)){
			throw new BusinessException("请输入运费！");
		}
		String totalFeeString = DataFormat.getStringKey(inParam,"totalFeeString");//总费用
		if(StringUtils.isEmpty(totalFeeString)){
			throw new BusinessException("请输入总运费！");
		}
		//new code pickage cost
		String pickingCostsString = DataFormat.getStringKey(inParam,"pickingCostsString");
		String weight = DataFormat.getStringKey(inParam, "weight");
		String volume = DataFormat.getStringKey(inParam, "volume");
		if(StringUtils.isEmpty(weight) && StringUtils.isEmpty(volume)){
			throw new BusinessException("请输入重量或体积！");
		}
		Date date = new Date();
		OrderFee fee = new OrderFee();
		BeanUtils.populate(orderInfo, inParam);
		
		
		long userId = user.getUserId();
		long tenantId = user.getTenantId();
		long orgId = user.getOrgId();
		orderInfo.setOrderNumber(trackingNum);
		orderInfo.setCreateTime(date);
		long desProvince = SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(desCity)).getProvId();
		orderInfo.setDesProvince(desProvince);
		orderInfo.setCreateId(user.getUserId());
		orderInfo.setCreateName(user.getUserName());
		orderInfo.setDesProvinceName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(orderInfo.getDesProvince())).getName());
		orderInfo.setDesCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(orderInfo.getDesCity())).getName());
		orderInfo.setTraProvinceName(SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(orderInfo.getTraProvince())).getName());
		orderInfo.setTraCityName(SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(orderInfo.getTraCity())).getName());
		orderInfo.setPaymentName(SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE_YQ", String.valueOf(orderInfo.getPayment())));
		orderInfo.setInterchangeName(SysStaticDataUtil.getSysStaticDataCodeName("INTERCHANGE_YUNQI", String.valueOf(orderInfo.getInterchange())));
		orderInfo.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN);
		orderInfo.setBillingOrgId(user.getOrgId());
		orderInfo.setTenantId(tenantId);
		//提货地址
		Organization org = OraganizationCacheDataUtil.getOrganization(orgId);
		if (org.getProvinceId() != null) {
			orderInfo.setStaProvince(Long.valueOf(org.getProvinceId()));
		}
		if (org.getRegionId() != null) {
			orderInfo.setStaCity(Long.valueOf(org.getRegionId()));
		}
		if (org.getCountyId() != null) {
			orderInfo.setStaDistrict(Long.valueOf(org.getCountyId()));
		}
		//加多对外运单状态 
		orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PENDING_DEPARTURE);
		orderInfoSV.doSaveOrUpdate(orderInfo);
		//拆分子运单，开单入库
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		orderInfoChildTF.resolutionOrderInfo(orderInfo, user);
		
		//费用
		OrderInfoSaveIn feeIn = new OrderInfoSaveIn();
		BeanUtils.populate(feeIn, inParam);//金额处理
		BeanUtils.copyProperties(fee, feeIn);
		fee.setOrderId(orderInfo.getOrderId());
		OrderFeeRuleSV orderFeeRuleSV = (OrderFeeRuleSV) SysContexts.getBean("orderFeeRuleSV");
		//记录计算小费的金额
		long defaultTip = 0;
		try {
			defaultTip = orderFeeRuleSV.tipCount(weight,arrivedOrgId, fee.getFreight() != null ? fee.getFreight() : 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fee.setDefaultTip(defaultTip);
		orderInfoSV.doSaveOrUpdateFee(fee);
		
		//开单后，调用下单接口
		OrdOrdersInfoTF orderOrdersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		Session session = SysContexts.getEntityManager(true);
		SysTenantDef sysTenantDef = (SysTenantDef) session.get(SysTenantDef.class, tenantId);
		
		OrdOrdersInfo ordOrdersInfo = null;
		if(ordsId > 0){
			OrdOrdersInfo checkOrders= (OrdOrdersInfo) session.get(OrdOrdersInfo.class, ordsId);
			if(checkOrders == null){
				throw new BusinessException("无该订单号信息！");
			}else if(!(checkOrders.getOrderState() == SysStaticDataEnumYunQi.ORDERS_STATE.CARRY_PACKAGE_ING
					|| checkOrders.getOrderState() == SysStaticDataEnumYunQi.ORDERS_STATE.WAIT_PCIKUP)){
				throw new BusinessException("该订单【"+checkOrders.getOrderNo()+"】不在拉包中或者待提货，不能开单！");
			}
			
			//开单时要调用的接口
			ordOrdersInfo = orderOrdersInfoTF.saveInputReceipt(orderInfo.getOrdsId(), tenantId, fee.getTip() != null ? fee.getTip() : 0 , user.getUserName(), 
					orgId, userId, orderInfo.getOrderNumber(), sysTenantDef.getName()
					,orderInfo.getWeight() != null ? orderInfo.getWeight() : "",orderInfo.getVolume() != null ? orderInfo.getVolume() : "",fee.getTotalFee());
		}else{
			ordOrdersInfo =  orderOrdersInfoTF.saveOrders(user, orderInfo,StringUtils.isNotBlank(org.getProvinceId()) ? Long.valueOf(org.getProvinceId()) : -1,StringUtils.isNotBlank(org.getRegionId()) ? Long.valueOf(org.getRegionId()) : -1, fee.getTip() == null ? 0 : fee.getTip(), fee.getTotalFee());
		}
		orderInfo.setOrdsId(ordOrdersInfo.getOrderId());
		
		//生成小费提现
		if(StringUtils.isNotEmpty(orderInfo.getPullPhone()) && fee.getTip() != null && fee.getTip() > 0){
			AcMyWalletTF acMyWalletTF = (AcMyWalletTF) SysContexts.getBean("acMyWalletTF");
			pullId = acMyWalletTF.doSave(ordOrdersInfo, fee, tenantId, pullId,orderInfo);
			orderInfo.setPullId(pullId);
		}else if(pullId < 0 && StringUtils.isNotEmpty(orderInfo.getPullPhone())){
			CmUserInfoYunQiTF cmUserInfoTF = (CmUserInfoYunQiTF) SysContexts.getBean("cmUserInfoYunQiTF");
			CmUserInfo cmUserInfo = cmUserInfoTF.getCmUserByLoginAcct(orderInfo.getPullPhone(), SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL);
			if(cmUserInfo != null){
				pullId = cmUserInfo.getUserId();
			}
		}
		//记录开单日记
		OrdLogNewTF ordLogNewTF = (OrdLogNewTF) SysContexts.getBean("ordLogNewTF");		
		ordLogNewTF.billingInOrderLog(userId, user, ordOrdersInfo);
		if(pullId > 0){
			CmUserInfoPullTF cmUserInfoPullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
			CmUserInfoPull pull = cmUserInfoPullTF.getByUserPull(pullId);
			AcWalletRelSV acWalletRelSV = (AcWalletRelSV) SysContexts.getBean("acWalletRelSV");
			List<AcWalletRel> acWalletRelList = acWalletRelSV.checkAcWalletRel(pullId, tenantId);
			AcWalletRel acWalletRel = null;
			if(acWalletRelList == null || acWalletRelList.size() <= 0){
				acWalletRel = new AcWalletRel();
				acWalletRel.setCreateTime(date);
				acWalletRel.setTenantId(tenantId);
				acWalletRel.setTenantName(sysTenantDef.getName());
				acWalletRel.setUserId(pullId);
				if(pull != null){
					acWalletRel.setPullTenantId(pull.getTenantId());
				}else{
					acWalletRel.setPullTenantId(tenantId);
				}
				acWalletRelSV.doSave(acWalletRel);
			}
			
			if(pull != null){
				
				CmUserInfoPull cmUserInfoPull=cmUserInfoPullTF.reduceSingularNum(pull.getUserId());
				BusiGrabControlOut.updateUserInfoSingularNum(cmUserInfoPull.getUserId(), cmUserInfoPull.getSingularNum().intValue());
				//拉包工更新接单数
//				BusiMatchControlYq.updatePullLoadNum(cmUserInfoPull.getUserId(), cmUserInfoPull.getTenantId(), cmUserInfoPull.getSingularNum(),cmUserInfoPull.getDefaultSingularNum());
			}
		}
		//更新发货人或者收货人
		CmCustomerYQTF cmCustomerYQTF = (CmCustomerYQTF) SysContexts.getBean("cmCustomerYQTF");
		//发货人
		if (StringUtils.isNotEmpty(consignor)) {
			cmCustomerYQTF.doSaveOrUpdateByOrder(DataFormat.getLongKey(inParam,"consignorId"), SysStaticDataEnumYunQi.CUSTOMER_TYPE.CONSIGNOR,consignor, DataFormat.getStringKey(inParam, "consignorPhone"), DataFormat.getStringKey(inParam, "consignorAddress"));
		}
		//收货人
		cmCustomerYQTF.doSaveOrUpdateByOrder(DataFormat.getLongKey(inParam,"consigneeId"), SysStaticDataEnumYunQi.CUSTOMER_TYPE.CONSIGNEE,consignee, consigneePhone, DataFormat.getStringKey(inParam, "consigneeAddress"));
		
		if(StringUtils.isNotEmpty(orderInfo.getConsigneePhone())){
			if (!isWeChatUser(session, orderInfo.getConsigneePhone())) {
				SmsYQUtil.sendBillingDelivery(tenantId, orderInfo.getOrderNumber(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, orderInfo.getOrdsId(), orderInfo.getCarrierName(), orderInfo.getConsigneePhone());
			}
			SmsYQUtil.sendBillingDelivery(tenantId, orderInfo.getOrderNumber(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, orderInfo.getConsigneePhone())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, orderInfo.getOrdsId(), orderInfo.getCarrierName(), orderInfo.getConsigneePhone());
		}
		if(orderInfo.getConsignorPhone()!=null&&StringUtils.isNotEmpty(orderInfo.getConsignorPhone())){
			if (!isWeChatUser(session, orderInfo.getConsignorPhone())) {
				SmsYQUtil.sendBillingDelivery(tenantId, orderInfo.getOrderNumber(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), orderInfo.getCarrierName(), orderInfo.getConsignorPhone());
			}
			SmsYQUtil.sendBillingDelivery(tenantId, orderInfo.getOrderNumber(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, isRegisterUser(session, orderInfo.getConsignorPhone())==true?EnumConstsYQ.SmsType.MOPBILE_TYPE:EnumConstsYQ.SmsType.NOTICE_TYPE, orderInfo.getOrdsId(), orderInfo.getCarrierName(), orderInfo.getConsignorPhone());
		}
		if (ordOrdersInfo.getCreateId() != null&& ordOrdersInfo.getOrderType() != 3 &&ordOrdersInfo.getCreateType() != 4) {
			CmUserInfo cmUserInfo = (CmUserInfo) session.get(CmUserInfo.class, ordOrdersInfo.getCreateId());
			if ((!cmUserInfo.getLoginAcct().equals(orderInfo.getConsignorPhone())) && (!cmUserInfo.getLoginAcct().equals(orderInfo.getConsigneePhone()))) {
				if (!isWeChatUser(session, cmUserInfo.getLoginAcct())) {
					SmsYQUtil.sendBillingDelivery(tenantId, orderInfo.getOrderNumber(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE4, EnumConstsYQ.SmsType.WECHAT_TYPE, ordOrdersInfo.getOrderId(), orderInfo.getCarrierName(), cmUserInfo.getLoginAcct());
				}
				SmsYQUtil.sendBillingDelivery(tenantId, orderInfo.getOrderNumber(), EnumConstsYQ.OBJ_TYPE.OBJ_TYPE2, EnumConstsYQ.SmsType.MOPBILE_TYPE, orderInfo.getOrdsId(), orderInfo.getCarrierName(), cmUserInfo.getLoginAcct());
			}
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderNumber", orderInfo.getOrderNumber());
		map.put("info", "Y");
		List<RouteTowards> list = session.createCriteria(RouteTowards.class).add(Restrictions.eq("beginOrgId", user.getOrgId())).add(Restrictions.eq("endOrgId", arrivedOrgId)).list();
		String staCityName = "";
		if(list != null && list.size() > 0){
			staCityName = SysStaticDataUtil.getCityDataList("SYS_CITY", list.get(0).getBeginOwnerRegion()).getName();
		}
		map.put("staCityName", staCityName);
		if (CommonUtil.tmsTenantId("TMS_KL") == user.getTenantId().longValue()) {
			OrderInfoTmsErrorTF orderInfoTmsErrorTF = (OrderInfoTmsErrorTF) SysContexts.getBean("orderInfoTmsErrorTF");
			Organization arrivedOrg = null;
			if (orderInfo.getArrivedOrgId()!=null) {
				arrivedOrg = OraganizationCacheDataUtil.getOrganization(orderInfo.getArrivedOrgId());
			}
			Organization billingOrg =  OraganizationCacheDataUtil.getOrganization(orderInfo.getBillingOrgId());
			Map<String,Object> tmsInParam = new HashMap<String, Object>();
			if (arrivedOrg!=null) {
				tmsInParam.put("arrivedOrgName",SysStaticDataUtil.getCityDataList("SYS_CITY", arrivedOrg.getRegionId()).getName()+arrivedOrg.getOrgName());
			}else{
				tmsInParam.put("arrivedOrgName","");
			}
			tmsInParam.put("billingOrgName", SysStaticDataUtil.getCityDataList("SYS_CITY", billingOrg.getRegionId()).getName()+billingOrg.getOrgName());
			tmsInParam.put("carrierName",orderInfo.getCarrierName() != null ?  orderInfo.getCarrierName() : "");
			tmsInParam.put("collectMoneyDouble",fee.getCollectMoney() != null ? CommonUtil.parseDouble(fee.getCollectMoney()) : "0");
			tmsInParam.put("consignee",orderInfo.getConsignee() != null ?  orderInfo.getConsignee() : "");
			tmsInParam.put("consigneeAddress",orderInfo.getConsigneeAddress() != null ? orderInfo.getConsigneeAddress() : "");
			tmsInParam.put("consigneePhone",orderInfo.getConsigneePhone() != null ? orderInfo.getConsigneePhone() : "");
			tmsInParam.put("consignor",orderInfo.getConsignor() != null ? orderInfo.getConsignor() : "");
			tmsInParam.put("consignorAddress",(orderInfo.getConsignorAddress() != null ? orderInfo.getConsignorAddress()+"，" : "")+(orderInfo.getConsignorPhone() != null ? orderInfo.getConsignorPhone() : ""));
			tmsInParam.put("consignorPhone",StringUtils.defaultIfBlank(orderInfo.getSelfNumber(), ""));
			tmsInParam.put("createName",orderInfo.getCreateName() != null ?  orderInfo.getCreateName() : "");
			tmsInParam.put("createTime", DateUtil.formatDate(orderInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			tmsInParam.put("deliveryChargeDouble",fee.getDeliveryCharge() != null ? CommonUtil.parseDouble(fee.getDeliveryCharge()) : "0");
			tmsInParam.put("desCityName", StringUtils.defaultIfEmpty(orderInfo.getDesCityName(), ""));
			tmsInParam.put("desDistrictName", StringUtils.defaultIfEmpty(orderInfo.getDesDistrictName(), ""));
			tmsInParam.put("desProvinceName", StringUtils.defaultIfEmpty(orderInfo.getDesProvinceName(), ""));
			tmsInParam.put("freightDouble",fee.getFreight() != null ? CommonUtil.parseDouble(fee.getFreight()) : "0");
			tmsInParam.put("interchangeName", SysStaticDataUtil.getSysStaticDataCodeName("INTERCHANGE_YUNQI", String.valueOf(orderInfo.getInterchange())));
			tmsInParam.put("landFeeDouble",fee.getLandFee() != null ? CommonUtil.parseDouble(fee.getLandFee()) : "0");
			tmsInParam.put("number", orderInfo.getNumber());
			tmsInParam.put("orderNumber", orderInfo.getOrderNumber());
			tmsInParam.put("otherFeeDouble",fee.getOtherFee() !=null ? CommonUtil.parseDouble(fee.getOtherFee()) : "0");
			tmsInParam.put("packName", orderInfo.getPackName());
			tmsInParam.put("paymentName", SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE_YQ", String.valueOf(orderInfo.getPayment())));
			tmsInParam.put("premiumDouble",fee.getPremium() != null ? CommonUtil.parseDouble(fee.getPremium()) : "0");
			tmsInParam.put("productName", orderInfo.getProductName());
			tmsInParam.put("pullName",orderInfo.getPullName() != null ? orderInfo.getPullName() : "");
			tmsInParam.put("pullPhone",orderInfo.getPullPhone() != null ? orderInfo.getPullPhone() : "");
			tmsInParam.put("remarks",orderInfo.getRemarks() != null ? orderInfo.getRemarks() : "");
			tmsInParam.put("reputationDouble",fee.getReputation() !=null ?  CommonUtil.parseDouble(fee.getReputation()) : "0");
			tmsInParam.put("serviceChargeDouble", fee.getServiceCharge() !=null ? CommonUtil.parseDouble(fee.getServiceCharge()) : "0");
			tmsInParam.put("tipDouble",fee.getTip() != null ? CommonUtil.parseDouble(fee.getTip()) : "0");
			tmsInParam.put("totalFeeDouble",fee.getTotalFee() != null ? CommonUtil.parseDouble(fee.getTotalFee()) : "0");
			tmsInParam.put("transitFeeDouble",fee.getTransitFee() != null ? CommonUtil.parseDouble(fee.getTransitFee()) : "0");
			tmsInParam.put("volume", StringUtils.isNotBlank(orderInfo.getVolume()) ? orderInfo.getVolume() : "0");
			tmsInParam.put("weight",StringUtils.isNotBlank(orderInfo.getWeight()) ? orderInfo.getWeight() : "0");
			tmsInParam.put("selfNumber", orderInfo.getSelfNumber() != null ? orderInfo.getSelfNumber() : "");
			tmsInParam.put("pickingCostsDouble", fee.getPickingCosts() !=null ? CommonUtil.parseDouble(fee.getPickingCosts()) : "0");
			//String billing =OrderPostUtil.billingOrderKL(tmsInParam, tenantId, false);
            //if (!"Y".equals(billing)) {
				//log.error(billing);
				OrderInfoTmsError orderInfoTmsError = new OrderInfoTmsError();
				BeanUtils.populate(orderInfoTmsError, tmsInParam);
				orderInfoTmsError.setOrderNumber(orderInfo.getOrderNumber());
				orderInfoTmsError.setUserName(user.getUserName());
				orderInfoTmsError.setCreateTime(date);
				orderInfoTmsError.setSignName(user.getUserName());
				orderInfoTmsError.setSignTime(date);
				orderInfoTmsError.setTenantId(user.getTenantId());
				orderInfoTmsError.setOrderId(orderInfo.getOrderId());
				orderInfoTmsError.setOrdsId(orderInfo.getOrdsId());
				orderInfoTmsError.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN);
				orderInfoTmsErrorTF.doSaveOrderInfoTmsError(orderInfoTmsError);
//			}
		}
		return map;
		
	}
	/**
	 * 变更专线订单
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String,Object> queryOrderInfo(Map<String,Object> inParam){
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		Query query = orderInfoSV.queryOrderInfo(inParam,false);
		IPage page  = PageUtil.gridPage(query);
		List<OrderInfoPageOut> outList = new ArrayList<OrderInfoPageOut>();
		List<Object[]> list = page.getThisPageElements();
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				OrderInfoPageOut out = new OrderInfoPageOut();
				out.setOrderNo(objects[0] != null ? String.valueOf(objects[0]) : "");
				out.setOrderNumber(objects[1] != null ? String.valueOf(objects[1]) : "");
				out.setBillingOrgName(objects[2] != null ? OraganizationCacheDataUtil.getOrgName(Long.valueOf(String.valueOf(objects[2]))) : "");
				out.setOrdTenantName(objects[3] != null ? SysTenantDefCacheDataUtil.getTenantName(Long.valueOf(String.valueOf(objects[3]))) : "");
				out.setOrdsTenantName(objects[4] != null ? SysTenantDefCacheDataUtil.getTenantName(Long.valueOf(String.valueOf(objects[4]))) : "");
				out.setCityName(objects[5] != null ? String.valueOf(objects[5]) : "");
				out.setPullName(objects[6] != null ? String.valueOf(objects[6]) : "");
				out.setPullPhone(objects[7] != null ? String.valueOf(objects[7]) : "");
				out.setConsignor(objects[8] != null ? String.valueOf(objects[8]) : "");
				out.setConsignorPhone(objects[9] != null ? String.valueOf(objects[9]) : "");
				out.setConsignorAddress(objects[10] != null ? String.valueOf(objects[10]) : "");
				out.setTipString(objects[11] != null ? String.valueOf(CommonUtil.getDoubleFormatLongMoney(Long.valueOf(String.valueOf(objects[11])), 2)) : "");
				out.setCreateDataString(objects[12] != null ? DateUtil.formatDateByFormat((Date)objects[12], "yyyy-MM-dd HH:mm:ss") : "");
				out.setOrderId(objects[13] != null  ? Long.valueOf(objects[13].toString()) : -1);
				outList.add(out);
			}
		}
		Pagination p = new Pagination(page);
		p.setItems(outList);
		Map<String,Object> map = new HashMap<String, Object>();
		map = JsonHelper.parseJSON2Map(JsonHelper.json(p));
		String _sum = DataFormat.getStringKey(inParam, "_sum");
		if ("1".equals(_sum)) {
			OrderInfoPageOut sum = sumOrderInfo(inParam, orderInfoSV);
			map.put("sumData", sum);
		}
		return map;
	}
	/**
	 * 合计
	 * @param inParam
	 * @param orderInfoSV
	 * @return
	 */
	private OrderInfoPageOut sumOrderInfo(Map<String,Object> inParam,OrderInfoSV orderInfoSV){
		Query query = orderInfoSV.queryOrderInfo(inParam, true);
		Object object = query.uniqueResult();
		OrderInfoPageOut out  = new OrderInfoPageOut();
		out.setTipString(object != null ? String.valueOf(CommonUtil.getDoubleFormatLongMoney(Long.valueOf(String.valueOf(object)), 2)) : "0.00");
		return out;
	}
	/***
	 * 统计查询（app）
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String,Object> getConuntOrderInfo(Map<String,Object> inParam){
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		long userType = SysContexts.getCurrentOperator().getUserType();
		Query query = orderInfoSV.getConuntOrderInfo(inParam,false);//统计数据
		IPage p = PageUtil.gridPage(query);
		List<Object[]> list = p.getThisPageElements(); 
		List<OrderInfoCountOut> outList = new ArrayList<OrderInfoCountOut>();
		if (list!=null&&list.size()>0) {
			for (Object[] objects : list) {
				OrderInfoCountOut out = new OrderInfoCountOut();
				//t.ORDER_NUMBER,o.TIP_MONEY,t.DES_CITY_NAME,o.FREIGHT,o.WEIGHT,o.ORDER_NUM,t.CREATE_TIME,t.CONSIGNEE,o.CREATE_DATE,o.CONSIGNEE_NAME
				out.setOrderNumber(objects[0] != null ? objects[0].toString() : "");
				out.setTipString(objects[1] != null ? CommonUtil.parseDouble(Long.valueOf(objects[1].toString())) : "0.00");
				out.setDesCityName(objects[2] != null ? SysStaticDataUtil.getCityDataList("SYS_CITY", objects[2].toString()).getName() : "");
				out.setTotalFeeString(objects[3] != null ? CommonUtil.parseDouble(Long.valueOf(objects[3].toString())) : "0.00");
				out.setWeight(objects[4] != null ? objects[4].toString() : "0.00");
				out.setNumber(objects[5] != null ? objects[5].toString() : "0");
				out.setCreateTimeString(objects[6] != null ? DateUtil.formatDate((Date)objects[6], "MM-dd HH:mm") : "");
				if (userType==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL) {
					out.setConsignee(objects[9] != null ? objects[9].toString() : "");
				}else{
					out.setConsignee(objects[7] != null ? objects[7].toString() : "");
				}
				out.setOrdersTime(objects[8] != null ? DateUtil.formatDate((Date)objects[8], "MM-dd HH:mm") : "");
				out.setTenantName(objects[10]!=null ? objects[10].toString() : "");
				out.setOrderId(objects[11] != null ? objects[11].toString() : "");
				outList.add(out);
			}
		}
		p.setThisPageElements(outList);
		Pagination page = new Pagination(p);
		//page.setItems(outList);
		Map<String,Object> map = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		Query queryCount = orderInfoSV.getConuntOrderInfo(inParam,true);
		List<Object[]> listCount = queryCount.list();
		if (listCount!=null&&listCount.size()>0) {
			for (Object[] objects : listCount) {
				//SUM(o.TIP_MONEY),SUM(o.FREIGHT),SUM(1)
				map.put("countOrderNumberString", objects[2] != null ? objects[2].toString() : "0");
				map.put("countTotalFeeString", objects[1] != null ? CommonUtil.parseDouble(Long.valueOf(objects[1].toString())) : "0.00");
				map.put("countTipString", objects[0] != null ? CommonUtil.parseDouble(Long.valueOf(objects[0].toString())) : "0.00");
				map.put("countNumberString", objects[3] != null ? objects[3].toString() : "0");
			}
		}else{
			map.put("countOrderNumberString", "");
			map.put("countTotalFeeString","");
			map.put("countTipString","");
			map.put("countNumberString", "0");
		}
		return map;
	}
	
	
	
	/**
	 * 对接获取开单信息
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String,Object> abutmentGetOrderInfo(Map<String,Object> inParam,String TMS)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		Query query = orderInfoSV.abutmentGetOrderInfo(inParam,TMS);
		IPage p = PageUtil.gridPage(query);
		List<Object[]> list = p.getThisPageElements();
		List<OrderInfoAbutmentOut> listOut = new ArrayList<OrderInfoAbutmentOut>();
		if (list != null && list.size() > 0) {
			for (Object[] objects : list) {
				OrderInfoAbutmentOut out = new OrderInfoAbutmentOut();
				OrderInfo orderInfo = (OrderInfo) objects[0];
				OrderFee orderFee = (OrderFee) objects[1];
				session.evict(orderInfo);
				session.evict(orderFee);
				BeanUtils.copyProperties(out, orderInfo);
				if(orderFee != null){
					BeanUtils.copyProperties(out, orderFee);
				}
				if (orderInfo.getCreateTime() != null) {
					out.setCreateTime(DateUtil.formatDate(orderInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				}
				listOut.add(out);
			}
		}
		p.setThisPageElements(listOut);
		Pagination page = new Pagination(p);
		String[] files = new String[]{"arrivedOrgId","billingOrgId","freight","reputation","premium","deliveryCharge","transitFee",
				"otherFee","collectMoney","landFee","serviceCharge","totalFee","tip"};
		//page.setItems(listOut);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page,files));
	}
	

	/**
	 * 对接获取配送信息
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> abutmentGetdeliveryOrderInfoKL(Map<String, Object> inParam, String TMS) throws Exception{
		// TODO Auto-generated method stub
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		BaseUser user = SysContexts.getCurrentOperator();
		Map<String, Object> param =  new HashMap<String, Object>();
		Map<String, Object>  deliverOrder =  new HashMap<String, Object>();
		Map<String, Object> mapOut = new HashMap<String, Object>();
		String orderNumber = DataFormat.getStringKey(inParam, "orderNumber");
		try {
			List<String> childOrderIds =  orderInfoSV.getChildIdsTrackingNum(inParam,TMS,user.getTenantId());		
			if(childOrderIds!=null&&childOrderIds.size()>0){
				param.put("childOrderIds",childOrderIds);
			}else {
				mapOut.put("massage", "此订单信息不存在");
				mapOut.put("result","N" );
				return mapOut;
			}
			param.put("tmsklinfo",SysStaticDataEnumYunQi.TMS_KL_INFO);
			OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
			deliverOrder = orderInfoChildTF.dispatching(param);
//			deliverOrder = ordersInfoTF.deliveryOrder(param);//调配送接口
			if(deliverOrder!=null && deliverOrder.size()>0){
				mapOut.put("massage", "此订单已配送");
				mapOut.put("result","Y" );
			}else {
				mapOut.put("massage", "此订单未配送");
				mapOut.put("result","N" );
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			mapOut.put("massage", e.getMessage());
			mapOut.put("result","N" );
			log.error("KL对接配送报错，运单号："+orderNumber, e);
			throw new Exception(e.getMessage());
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			mapOut.put("massage", "系统异常报错!");
			mapOut.put("result","N" );
			log.error("KL对接配送报错，运单号："+orderNumber, e);
			throw new Exception(e.getMessage());
		}
		return mapOut;
	}
	/**
	 * 对接获取到货信息
	 * @param inParam
	 * @return
	 */
	public Map<String, Object> abutmentGetgxArriveGoodsKL(Map<String, Object> inParam, String TMS) throws Exception{
		// TODO Auto-generated method stub
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		BaseUser user = SysContexts.getCurrentOperator();
		Map<String, Object> param =  new HashMap<String, Object>();
		Map<String, Object> mapOut =  new HashMap<String, Object>();
		String orderNumber = DataFormat.getStringKey(inParam, "orderNumber");
		try {
			OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");	
			long orderId = orderInfoSV.getOrderIdByTrackingNum(inParam,TMS,user.getTenantId());
			
			if(orderId>0){
			     param.put("orderId",orderId) ;
			}else {
				mapOut.put("massage", "此订单信息不存在");
				mapOut.put("result","N" );
				return mapOut;
			}
			param.put("tmsklinfo",SysStaticDataEnumYunQi.TMS_KL_INFO);
//			OrdDepartInfoYqTF ordDepartInfoYqTF = (OrdDepartInfoYqTF) SysContexts.getBean("ordDepartInfoYqTF");
			
			
			Map<String, Object> gxArriveGoods = ordersInfoTF.gxArriveGoods(param);
			if(gxArriveGoods!=null && gxArriveGoods.size()>0){
				mapOut.put("massage", "此订单成功到货");
				mapOut.put("result","Y" );
			}else {
				mapOut.put("massage", "此订单未到货");
				mapOut.put("result","N" );
			}
		}catch (BusinessException e) {
			// TODO Auto-generated catch block
			//mapOut.put("massage", "系统异常报错!");
			mapOut.put("massage", e.getMessage());
			mapOut.put("result","N" );
			log.error("KL对接到货报错，运单号："+orderNumber, e);
			throw new Exception(e.getMessage());
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			mapOut.put("massage", "系统异常报错!");
			mapOut.put("result","N" );
			log.error("KL对接到货报错，运单号："+orderNumber, e);
			throw new Exception(e.getMessage());
		}
		
		return mapOut;
	}
	
	/**
	 * 对接获取签收信息
	 * @param inParam
	 * @return
	 */
	public Map<String, Object> abutmentGetsignUpKL(Map<String, Object> inParam,String TMS) throws Exception{
		
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		String signDesc = DataFormat.getStringKey(inParam, "signDecs");//签约描述
		String pictureUrl =  DataFormat.getStringKey(inParam, "pictureUrl");//签约描述
		Map<String, Object> param =  new HashMap<String, Object>();
		Map<String, Object> mapOut = new HashMap<String, Object>();
		BaseUser user = SysContexts.getCurrentOperator();
		String orderNumber = DataFormat.getStringKey(inParam, "orderNumber");
		try {
			String flowId = orderInfoSV.getFlowId(pictureUrl);
			long orderId = orderInfoSV.getOrderIdByTrackingNum(inParam,TMS,user.getTenantId());//得到订单Id
			//param.put("userType","");
			if(orderId >0){
				List<String> list = new ArrayList<String>();
				list.add(String.valueOf(orderId));
			     param.put("orderIds",list) ;
			}else {
				mapOut.put("massage", "此订单信息不存在");
				mapOut.put("result","N" );
				return mapOut; 
			}
			if (StringUtils.isNotEmpty(signDesc)) {
				param.put("signDecs", signDesc);
			}
			if (StringUtils.isNotEmpty(flowId)) {
				param.put("flowId", flowId);
			}
			param.put("tmsklinfo",SysStaticDataEnumYunQi.TMS_KL_INFO);
			param.put("type", 1);
			Map<String, Object> signUp = doSign(param);
			if(signUp!=null && signUp.size()>0){
				mapOut.put("massage", "此订单已经签收");
				mapOut.put("result","Y" );
			}else {
				mapOut.put("massage", "此订单未签收");
				mapOut.put("result","N" );
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			mapOut.put("massage", e.getMessage());
			mapOut.put("result","N" );
			log.error("KL对接签收报错，运单号："+orderNumber, e);	
			throw new Exception(e.getMessage());
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			mapOut.put("massage", "系统异常报错!");
			mapOut.put("result","N" );
			log.error("KL对接签收报错，运单号："+orderNumber, e);
			throw new Exception(e.getMessage());
		}	
		return mapOut;
	}
	
	/***
	 * 判断是否是注册用户
	 * @param loginAcct
	 * @return
	 */
     private boolean isRegisterUser(Session session,String loginAcct){
		Query query = session.createSQLQuery("SELECT COUNT(1) FROM CM_USER_INFO WHERE LOGIN_ACCT=:loginAcct and user_type != 11 ");
		query.setParameter("loginAcct", loginAcct);
		 BigInteger count = (BigInteger) query.uniqueResult();
		if(count.intValue()>0){
			return true;
		}
		return false;
	}
	/**
	 * 通过主键查询运单信息
	 * @param id
	 * @return
	 */
	public OrderInfo getOrderInfo(long id){
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		return orderInfoSV.getOrderInfo(id);
	}
	/**
	 * 判断靠后的状态
	 * @param orderId
	 * @return
	 */
	public int maxOrderState(long orderId){
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		return orderInfoSV.maxOrderState(orderId);
	}
	/**
	 * 获取运单号
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> trackingNum() throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		String allTrackingNum = CommonUtil.getTrackingNumAll(user.getTenantId(), user.getUserId());
		String trackingBeginNum = SysTenantExtendCacheUtil.getValue(user.getTenantId(), EnumConsts.TRACKING_NUM_REDIS.TANANT_TRACKING_NUM_BEGIN);
		Map<String,String> map = new HashMap<String, String>();
		map.put("trackingBeginNum", trackingBeginNum);
		map.put("trackingNum", allTrackingNum.substring(trackingBeginNum.length(), allTrackingNum.length()));
		return map;
	}
	
	public void doUpdate(OrderInfo orderInfo,BaseUser user){
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		orderInfo.setOpId(user.getUserId());
		orderInfo.setOpName(user.getUserName());
		orderInfo.setOpTime(new Date());
		orderInfoSV.doSaveOrUpdate(orderInfo);
	}

	public Map<String, Object> queryDispatchingList(Map<String, Object> inParam) {	
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		int orderState = SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY ;
		Query query = orderInfoSV.queryChildTrackList(inParam,orderState);
		//List<Object[]> list = query.list();
		//OC.CHILD_TRACKING_NUM_ALI,OT.CONSIGNEE,OT.des_city_name,OC.tracking_num,OC.child_tracking_num,OC.CHILD_ORDER_ID,OC.ORDER_ID,OC.child_order_id
		IPage p = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		if(list ==null && list.size()<=0){
			throw new BusinessException("无此子运单信息");
		}
		List<Map<String, String>> listOut = new ArrayList<Map<String, String>>();
		if(list!=null&& list.size()>0){
			 for (Object[] objects : list) {
				 Map<String, String> map = new HashMap<String, String>();
				 map.put("childOrderState",objects[8] != null ? SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE", objects[8].toString()) : "");
				 map.put("childTrackingNumAli",objects[0] != null ? String.valueOf(objects[0]) : "" );
				 map.put("consigneeName",objects[1] != null ? String.valueOf(objects[1]) : "" );
				 map.put("destCityName",objects[2] != null ? String.valueOf(objects[2]) : "");
				 map.put("childOrderId",objects[5] != null ? String.valueOf(objects[5]) : "");
				 map.put("orderId",objects[6] != null ? String.valueOf(objects[6]) : "");
				 listOut.add(map);
			 }
		 }	
		p.setThisPageElements(listOut);
		Pagination<Object[]> page = new Pagination<Object[]>(p);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		return rtnMap;
	}

	public Map<String, Object> querySignInfoList(Map<String, Object> inParam) {
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
//		int orderState = SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN ;
		Integer[] orderStates = new Integer[]{SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN,SysStaticDataEnumYunQi.ORDER_INFO_STATE.PARTIAL_SIGN};
		Query query = orderInfoSV.queryOrderListByOrderState(inParam,orderStates);
		IPage p = PageUtil.gridPage(query);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		List<Map<String, String>> listOut = new ArrayList<Map<String, String>>();
		if(list ==null || list.size()<=0){
			
		}
		
		if(list!=null&& list.size()>0){
			 for (Object[] objects : list) {
				 Map<String, String> map = new HashMap<String, String>();
				 map.put("orderStateValue", objects[4]!=null ? objects[4].toString() : "");
				 map.put("orderState",objects[5] != null ? SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE_OUT", objects[5].toString()) : "");
				 map.put("trackingNum",objects[0] != null ? String.valueOf(objects[0]) : "" );
				 map.put("consigneeName",objects[1] != null ? String.valueOf(objects[1]) : "" );
				 map.put("destCityName",objects[2] != null ? String.valueOf(objects[2]) : "");
				 map.put("orderId",objects[3] != null ? String.valueOf(objects[3]) : ""); 
				 listOut.add(map);
			 }
		 }		
		p.setThisPageElements(listOut);
		Pagination<Object[]> page = new Pagination<Object[]>(p);		
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		return rtnMap;
	}
    //未到货列表查询
	public Map<String, Object> queryFloationCargoList(Map<String, Object> inParam) {
		DecimalFormat df=new DecimalFormat("0.####");
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		int vehiclelist = SysStaticDataEnumYunQi.ORDER_INFO_STATE.FLOATION_CARGO;
		Query query = orderInfoSV.queryChileTrackLBybacthNum(inParam,vehiclelist);
		IPage p = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		if(list==null && list.size()<=0){
			throw new BusinessException("无该批次配载信息！");
		}
		List<Map<String, String>> listOut = new ArrayList<Map<String, String>>();
		//OC.CHILD_TRACKING_NUM_ALI, OT.consignee, OT.des_city_name,OC.child_order_state，
		//OC.child_tracking_num， OD.VOLUME, OD.WEIGHT, OD.ORDER_NUM, OD.NUMBER
		if(list!=null&& list.size()>0){
			 for (Object[] objects : list) {
				 Map<String, String> map = new HashMap<String, String>();
				 map.put("childTrackingNumAli",objects[0] != null ? String.valueOf(objects[0]) : "" );//子运单别名
				 map.put("consigneeName",objects[1] != null ? String.valueOf(objects[1]) : "" );
				 map.put("destCityName",objects[2] != null ? String.valueOf(objects[2]) : "");
				 Integer childOrderState = (Integer)objects[3];
				 map.put("childOrderState",  SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE", String.valueOf(childOrderState)));
				 String weightStr = (String)objects[6];
				 double weight = 0;
				 if (StringUtils.isNotBlank(weightStr)) {
					 weight = Double.valueOf(weightStr)/1000;
				 }
				 map.put("weight",weight+ "");
				 map.put("volume",objects[5] != null ? String.valueOf(objects[5]) : "" );
				 map.put("orderId",objects[7] != null ? String.valueOf(objects[7]) : "" );
				 map.put("childOrderId",objects[8] != null ? String.valueOf(objects[8]) : "" ); 
				 listOut.add(map);
			 }
		 }	
		p.setThisPageElements(listOut);
		Pagination<Object[]> page = new Pagination<Object[]>(p);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		return rtnMap;
	}

	public Map<String, Object> queryArrivalList(Map<String, Object> inParam) {
		DecimalFormat df=new DecimalFormat("0.####");
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		int vehiclelist = SysStaticDataEnumYunQi.ORDER_INFO_STATE.FLOATION_CARGO;
		Query query = orderInfoSV.queryChileTrackBybacthNum(inParam,vehiclelist);
		IPage p = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		//List<Object[]> list = query.list();
		List<Map<String, String>> listOut = new ArrayList<Map<String, String>>();
		if(list==null || list.size()<=0){
			throw new BusinessException("无该批次配载信息！");
		}
		if(list!=null&& list.size()>0){
			 for (Object[] objects : list) {
				 Map<String, String> map = new HashMap<String, String>();
				 map.put("childTrackingNumAli",objects[0] != null ? String.valueOf(objects[0]) : "" );//子运单别名
				 map.put("consigneeName",objects[1] != null ? String.valueOf(objects[1]) : "" );
				 map.put("destCityName",objects[2] != null ? String.valueOf(objects[2]) : "");
				 Integer childOrderState = (Integer)objects[3];
				 map.put("childOrderState", SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE", String.valueOf(childOrderState)));
				 String weightStr = (String)objects[6];
				 double weight = 0;
				 if (StringUtils.isNotBlank(weightStr)) {
					 weight = Double.valueOf(weightStr)/1000;
				 }
				 map.put("weight",weight+ "");
				 map.put("volume",objects[5] != null ? String.valueOf(objects[5]) : "" );				 
				 map.put("orderId",objects[7] != null ? String.valueOf(objects[7]) : "" );
				 map.put("childOrderId",objects[8] != null ? String.valueOf(objects[8]) : "" );
				 listOut.add(map);
			 }
		 }
		p.setThisPageElements(listOut);
		Pagination<Object[]> page = new Pagination<Object[]>(p);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		return rtnMap;
	}
	
	/**
	 * 签收【602012】
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> doSign(Map<String,Object> inParam) throws Exception{
		List<String> orderIds = (List<String>) inParam.get("orderIds");
		String tmsklinfo = DataFormat.getStringKey(inParam, "tmsklinfo");
		if (orderIds == null || orderIds.size() <= 0) {
			throw new BusinessException("请选择需要签收的运单！");
		}
		List<Long> list = new ArrayList<Long>();
		
		int type = DataFormat.getIntKey(inParam, "type");
		int userType = DataFormat.getIntKey(inParam, "userType");
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		if (type == 1) {//客户签收 或 web平台
			
			list = orderInfoSV.getOrderInfoByLong(orderIds);
			if (list==null||list.size()<=0) {
				throw new BusinessException("没有该订单号的运单信息！");
			}
		}else if(type == 2  ){//专线签收
			for (String string : orderIds) {
				list.add(Long.valueOf(string)) ;
			}
		}else{
			throw new BusinessException("请传入正确的入参类型！");
		}
		
		Date date = new Date();
		BaseUser user = SysContexts.getCurrentOperator();
		String signDesc = DataFormat.getStringKey(inParam, "signDecs");
		String flowId = DataFormat.getStringKey(inParam, "flowId");
		String certificateNo =  DataFormat.getStringKey(inParam, "certificateNo");
		String certificateType = DataFormat.getStringKey(inParam, "certificateType");
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		OrderInfoTmsErrorTF orderInfoTmsErrorTF = (OrderInfoTmsErrorTF) SysContexts.getBean("orderInfoTmsErrorTF");
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		OrdStockTF ordStockTF = (OrdStockTF) SysContexts.getBean("ordStockTF");
		OrdChildOpLogTF ordChildOpLogTF = (OrdChildOpLogTF) SysContexts.getBean("ordChildOpLogTF");
		for (Long string : list) {
			long orderId = string;
			boolean isWaitSign = false;//是否存在待签收数据
			List<OrderInfoChild> orderInfoChilds = orderInfoChildTF.getOrderInfoChildsByState(orderId);
			if (orderInfoChilds != null && orderInfoChilds.size() > 0) {
				for (OrderInfoChild orderInfoChild : orderInfoChilds) {
					orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.DO_SIGN);
					orderInfoChildTF.doUpdate(orderInfoChild, user);
					OrderInfoChildSign orderInfoChildSign = new OrderInfoChildSign();
					orderInfoChildSign.setChildOrderId(orderInfoChild.getChildOrderId());
					orderInfoChildSign.setChildTrackingNum(orderInfoChild.getChildTrackingNum());
					orderInfoChildSign.setCreateId(user.getUserId());
					orderInfoChildSign.setCreateName(user.getUserName());
					orderInfoChildSign.setCreateTime(date);
					orderInfoChildSign.setImageId(flowId);
					orderInfoChildSign.setRemark(signDesc);
					orderInfoChildSign.setSignName(user.getUserName());
					orderInfoChildSign.setSignTime(date);
					orderInfoChildSign.setSignType(2);
					orderInfoChildTF.doSaveSign(orderInfoChildSign);
					//签收出库
					if (type == 1) {
						ordStockTF.signOutStock(orderInfoChild.getChildOrderId(), orderInfoChild.getDispatchingOrgId());
					}else{
						ordStockTF.signOutStock(orderInfoChild.getChildOrderId(), user.getOrgId());
					}
					ordChildOpLogTF.departLog(orderInfoChild, 11, user, null);
				}
				isWaitSign = true;
			}else{
				isWaitSign = false;
			}
			if (!isWaitSign) {
				throw new BusinessException("该运单不是【待签收】不能签收！");
			}
			OrderInfo orderInfo = getOrderInfo(orderId);
			
			boolean isPartialSign = orderInfoSV.isPartialSign(orderId) == orderInfo.getNumber();
//			int tackingState = orderInfo.getOrderState();
			OrdOrdersInfo ordOrdersInfo = ordersInfoTF.getOrdOrdersInfo(orderInfo.getOrdsId());
			if (isWaitSign) {
				//保存运单
				if (isPartialSign) {
					orderInfo.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.DO_SIGN);
					orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.DO_SIGN);
				}else{
					orderInfo.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.PARTIAL_SIGN);
					orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PARTIAL_SIGN);
				}
				doUpdate(orderInfo,user);
				
				//保存订单
				if (isPartialSign) {
					ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN);
				}else{
					ordOrdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.PARTIAL_SIGN);
				}
				ordersInfoTF.doUpdateOrders(ordOrdersInfo, user);
				//保存签收表
				OrdSignInfo signInfo = ordersInfoTF.getOrdSignInfo(ordOrdersInfo.getOrderId());
				if (signInfo==null) {
					signInfo = new OrdSignInfo();
				}
				
				signInfo.setCreateId(user.getUserId());
				signInfo.setTrackingNum(ordOrdersInfo.getTrackingNum());
				signInfo.setOrderNo(ordOrdersInfo.getOrderNo());
				signInfo.setExt1(StringUtils.defaultString(user.getTelphone(), ""));
				signInfo.setOrderId(ordOrdersInfo.getOrderId());
				signInfo.setSignDate(date);
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
				ordersInfoTF.doSaveSign(signInfo);
				if (isPartialSign) {
					// 写日志
					OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
					ordLogNewTF.signUpOrderLog(user.getUserId(), user, ordOrdersInfo, ordOrdersInfo.getCompanyName(), OraganizationCacheDataUtil.getOrgName(ordOrdersInfo.getOrgId()==null?0:ordOrdersInfo.getOrgId()), user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS?1:0);
					if(StringUtils.isNotEmpty(tmsklinfo)){
						
					}else {
						Session session = SysContexts.getEntityManager(true);
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
				}
				long tenantId = user.getTenantId() != null ? user.getTenantId() : orderInfo.getTenantId();
				if (CommonUtil.tmsTenantId("TMS_KL") == tenantId && StringUtils.isEmpty(tmsklinfo)) {
					String isSign = OrderPostUtil.signKL(orderInfo.getOrderNumber(), user.getUserName(), DateUtil.formatDateByFormat(date, "yyyy-MM-dd"), user.getUserName(), "", "", DateUtil.formatDateByFormat(date, "yyyy-MM-dd"), user.getUserName(), user.getTenantId(),false);
					if (!"Y".equals(isSign)) {
						log.error(isSign);
						OrderInfoTmsError orderInfoTmsError = new OrderInfoTmsError();
						orderInfoTmsError.setOrderNumber(orderInfo.getOrderNumber());
						orderInfoTmsError.setUserName(user.getUserName());
						orderInfoTmsError.setCreateTime(date);
						orderInfoTmsError.setSignName(user.getUserName());
						orderInfoTmsError.setSignTime(date);
						orderInfoTmsError.setTenantId(user.getTenantId());
						orderInfoTmsError.setOrderId(orderInfo.getOrderId());
						orderInfoTmsError.setOrdsId(orderInfo.getOrdsId());
						orderInfoTmsError.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN);
						orderInfoTmsErrorTF.doSaveOrderInfoTmsError(orderInfoTmsError);
					}
				}
			}
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tips", "货物已签收");
		return map;
	}
	
	
	/**
	 * 
	 * @param orderId
	 * @param type 1:加 2：减
	 */
	public void updateDepartCount(long orderId,int type){
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		orderInfoSV.updateDepartCount(orderId, type);
	}
	/**
	 * 校验多次配载的单
	 * @param orderId 主运单id
	 * @return
	 */
	public int checkOrderInfoMany(long orderId){
		return ((OrderInfoSV) SysContexts.getBean("orderInfoSV")).checkOrderInfoMany(orderId);
	}
	
	public OrderFee getOrderFee(long orderId){
		return ((OrderInfoSV) SysContexts.getBean("orderInfoSV")).getOrderFee(orderId);
	}
	/**
	 * 查询运单状态
	 */
	public Integer queryOrderInterchange(long orderId){
		return ((OrderInfoSV) SysContexts.getBean("orderInfoSV")).queryOrderInterchange(orderId);
	}
	/**
	 * 运单管理列表
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> queryOrderInfoWeb(Map<String,Object> inParam){
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		IPage p = PageUtil.gridPage(orderInfoSV.queryOrderInfoWeb(inParam, false));
		List<Object[]> list = p.getThisPageElements();
		List<OrderInfoMessagePage> outList = new ArrayList<OrderInfoMessagePage>();
//		o.ORDER_ID,o.ORDER_NUMBER,o.ORDER_STATE,os.ORDER_NO,o.BILLING_ORG_ID, 0
//		o.TENANT_ID,o.CREATE_NAME,o.CREATE_TIME,o.DES_CITY_NAME,o.PULL_NAME, 5
//		o.PULL_PHONE,o.CONSIGNOR,o.CONSIGNOR_PHONE,o.CONSIGNOR_ADDRESS,o.CONSIGNEE, 10
//		o.CONSIGNEE_PHONE,o.CONSIGNEE_ADDRESS,o.INTERCHANGE,o.PAYMENT,o.PRODUCT_NAME, 15
//		o.NUMBER,o.PACK_NAME,o.WEIGHT,o.VOLUME,f.FREIGHT, 20
//		f.REPUTATION,f.PREMIUM,f.DELIVERY_CHARGE,f.TRANSIT_FEE,f.TIP, 25
//		f.COLLECT_MONEY,f.LAND_FEE,f.SERVICE_CHARGE,f.OTHER_FEE,f.TOTAL_FEE, 30
//		o.REMARKS  35
		if (list!=null&&list.size()>0) {
			for (Object[] objects : list) {
				OrderInfoMessagePage out = new OrderInfoMessagePage(
						objects[0] != null ? objects[0].toString() : "", 
						objects[1] != null ? objects[1].toString() : "", 
						objects[2] != null ? SysStaticDataUtil.getSysStaticDataCodeName("ORDER_INFO_STATE", objects[2].toString()) : "", 
						objects[3] != null ? objects[3].toString() : "", 
						objects[4] != null ? OraganizationCacheDataUtil.getOrgName(Long.valueOf(objects[4].toString())) : "", 
						objects[5] != null ? SysTenantDefCacheDataUtil.getTenantName(Long.valueOf(objects[5].toString())) : "", 
						objects[6] != null ? objects[6].toString() : "", 
						objects[7] != null ? DateUtil.formatDate((Date)objects[7], "yyyy-MM-dd HH:mm:ss") : "", 
						objects[8] != null ? objects[8].toString() : "", 
						objects[9] != null ? objects[9].toString() : "",
						objects[10] != null ? objects[10].toString() : "", 
						objects[11] != null ? objects[11].toString() : "", 
						objects[12] != null ? objects[12].toString() : "", 
						objects[13] != null ? objects[13].toString() : "", 
						objects[14] != null ? objects[14].toString() : "", 
						objects[15] != null ? objects[15].toString() : "",
						objects[16] != null ? objects[16].toString() : "",
						objects[17] != null ? SysStaticDataUtil.getSysStaticDataCodeName("INTERCHANGE_YUNQI", objects[17].toString()) : "", 
						objects[18] != null ? SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE_YQ", objects[18].toString()) : "", 
						objects[19] != null ? objects[19].toString() : "", 
						objects[20] != null ? objects[20].toString() : "", 
						objects[21] != null ? objects[21].toString() : "",
						objects[22] != null ? objects[22].toString() : "", 
						objects[23] != null ? objects[23].toString() : "", 
						objects[24] != null ? CommonUtil.parseDouble(Long.valueOf(objects[24].toString())) : "", 
						objects[25] != null ? CommonUtil.parseDouble(Long.valueOf(objects[25].toString())) : "",
						objects[26] != null ? CommonUtil.parseDouble(Long.valueOf(objects[26].toString())) : "", 
						objects[27] != null ? CommonUtil.parseDouble(Long.valueOf(objects[27].toString())) : "", 
						objects[28] != null ? CommonUtil.parseDouble(Long.valueOf(objects[28].toString())) : "", 
						objects[29] != null ? CommonUtil.parseDouble(Long.valueOf(objects[29].toString())) : "", 
						objects[30] != null ? CommonUtil.parseDouble(Long.valueOf(objects[30].toString())) : "", 
						objects[31] != null ? CommonUtil.parseDouble(Long.valueOf(objects[31].toString())) : "", 
						objects[32] != null ? CommonUtil.parseDouble(Long.valueOf(objects[32].toString())) : "", 
						objects[33] != null ? CommonUtil.parseDouble(Long.valueOf(objects[33].toString())) : "", 
						objects[34] != null ? CommonUtil.parseDouble(Long.valueOf(objects[34].toString())) : "", 
						objects[35] != null ? objects[35].toString() : "");
				outList.add(out);
			}
		}
		Pagination page = new Pagination(p);
		page.setItems(outList);
		Map<String,Object> map = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		String _sum = DataFormat.getStringKey(inParam, "_sum");
		if ("1".equals(_sum)) {
			OrderInfoMessagePage infoMessagePage = queryOrderInfoWebSum(inParam, orderInfoSV);
			map.put("sumData", infoMessagePage);
		}
		return map;
	}
	/**
	 * 运单管理统计
	 * @param inParam
	 * @param orderInfoSV
	 * @return
	 */
	private OrderInfoMessagePage queryOrderInfoWebSum(Map<String,Object> inParam,OrderInfoSV orderInfoSV){
		List<Object[]> list = orderInfoSV.queryOrderInfoWeb(inParam, true).list();
//		SUM(o.NUMBER),SUM(o.WEIGHT),SUM(o.VOLUME),SUM(f.FREIGHT)
//		SUM(f.REPUTATION),SUM(f.PREMIUM),SUM(f.DELIVERY_CHARGE),SUM(f.TRANSIT_FEE),SUM(f.TIP)
//		SUM(f.COLLECT_MONEY),SUM(f.LAND_FEE),SUM(f.SERVICE_CHARGE),SUM(f.OTHER_FEE),SUM(f.TOTAL_FEE)
		OrderInfoMessagePage infoMessagePage = new OrderInfoMessagePage();
		if (list!=null&&list.size()>0) {
			for (Object[] objects : list) {
				infoMessagePage.setNumber(objects[0] != null ? objects[0].toString() : "");
				infoMessagePage.setWeight(objects[1] != null ? objects[1].toString() : "");
				infoMessagePage.setVolume(objects[2] != null ? objects[2].toString() : "");
				infoMessagePage.setFreight(objects[3] != null ? CommonUtil.parseDouble(Long.valueOf(objects[3].toString())) : "");
				infoMessagePage.setReputation(objects[4] != null ? CommonUtil.parseDouble(Long.valueOf(objects[4].toString())) : "");
				infoMessagePage.setPremium(objects[5] != null ? CommonUtil.parseDouble(Long.valueOf(objects[5].toString())) : "");
				infoMessagePage.setDeliveryCharge(objects[6] != null ? CommonUtil.parseDouble(Long.valueOf(objects[6].toString())) : "");
				infoMessagePage.setTransitFee(objects[7] != null ? CommonUtil.parseDouble(Long.valueOf(objects[7].toString())) : "");
				infoMessagePage.setTip(objects[8] != null ? CommonUtil.parseDouble(Long.valueOf(objects[5].toString())) : "");
				infoMessagePage.setCollectMoney(objects[5] != null ? CommonUtil.parseDouble(Long.valueOf(objects[5].toString())) : "");
				infoMessagePage.setLandFee(objects[5] != null ? CommonUtil.parseDouble(Long.valueOf(objects[5].toString())) : "");
				infoMessagePage.setServiceCharge(objects[5] != null ? CommonUtil.parseDouble(Long.valueOf(objects[5].toString())) : "");
				infoMessagePage.setOtherFee(objects[5] != null ? CommonUtil.parseDouble(Long.valueOf(objects[5].toString())) : "");
				infoMessagePage.setTotalFee(objects[5] != null ? CommonUtil.parseDouble(Long.valueOf(objects[5].toString())) : "");
			}
		}
		return infoMessagePage;
	}
	
	/**
	 * web操作配送
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String dispatchingWeb(Map<String,Object> inParam) throws Exception{
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		if (orderId < 0) {
			throw new BusinessException("请选择需要配送的数据！");
		}
		List<Long> list = orderInfoChildTF.getOrderInfoChildId(orderId, SysStaticDataEnumYunQi.ORDER_INFO_STATE.PENDING_DELIVERY);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("childOrderIds", list);
		orderInfoChildTF.dispatching(map);
		return "Y";
	}
	
	/**
	 * web签收
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doSignWeb(Map<String,Object> inParam) throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String signDesc = DataFormat.getStringKey(inParam, "signDesc");
		String signName = DataFormat.getStringKey(inParam, "signName");
		String trackingNum = DataFormat.getStringKey(inParam, "trackingNum");
		String flowId = DataFormat.getStringKey(inParam, "flowId");
		String certificateNo =  DataFormat.getStringKey(inParam, "certificateNo");
		String certificateType = DataFormat.getStringKey(inParam, "certificateType");
		String phone = DataFormat.getStringKey(inParam, "ext1");
		int type= DataFormat.getIntKey(inParam, "type");
		
		if (orderId < 0) {
			throw new BusinessException("请选择需要配送的数据！");
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("trackingNum", trackingNum);
		map.put("userType", user.getUserType());
		map.put("signDecs",signDesc!=null?signDesc:"");
		map.put("flowId", flowId!=null?flowId:"");
		map.put("certificateNo", certificateNo!=null?certificateNo:"");
		map.put("phone", phone!=null?phone:"");
		map.put("certificateType", certificateType!=null?certificateType:"");
		map.put("type", type);
		map.put("signName",signName);
		doSignToWeb(map);
		return "Y";
	}

	private Map<String,Object>  doSignToWeb(Map<String, Object> inParam) throws Exception {
		BaseUser user = SysContexts.getCurrentOperator();
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String phone = DataFormat.getStringKey(inParam, "phone");
		String signName = DataFormat.getStringKey(inParam, "signName");
		OrdOrdersInfo ordOdersInfo  = new OrdOrdersInfo();
		OrderInfo orderInfo = new OrderInfo();
		String tmsklinfo = DataFormat.getStringKey(inParam, "tmsklinfo");	
		int userType = DataFormat.getIntKey(inParam, "userType");
		int type = DataFormat.getIntKey(inParam, "type");
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		OrdOrdersInfoTF ordersInfoTF = (OrdOrdersInfoTF) SysContexts.getBean("ordersInfoTF");
		OrderInfoTmsErrorTF orderInfoTmsErrorTF = (OrderInfoTmsErrorTF) SysContexts.getBean("orderInfoTmsErrorTF");
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
		OrdStockTF ordStockTF = (OrdStockTF) SysContexts.getBean("ordStockTF");
		OrdChildOpLogTF ordChildOpLogTF = (OrdChildOpLogTF) SysContexts.getBean("ordChildOpLogTF");
		Date date = new Date();
		String signDesc = DataFormat.getStringKey(inParam, "signDecs");
		String flowId = DataFormat.getStringKey(inParam, "flowId");
		String certificateNo =  DataFormat.getStringKey(inParam, "certificateNo");
		int  certificateType = DataFormat.getIntKey(inParam, "certificateType");
		boolean isWaitSign = false;//是否已签收标识
		//平台
		if(userType ==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PLATFORM || userType ==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.CHAIN){
			//type=1， 则是派单管理 签收， 2 则是运单管理 签收
			if(type == 1){
				ordOdersInfo = ordersInfoTF.getOrdOrdersInfo(orderId); //下单
				orderInfo = orderInfoSV.getOrderInfoByOrderNumber(ordOdersInfo.getTrackingNum());//开单 
			}else if(type == 2){
				orderInfo = orderInfoSV.getOrderInfo(orderId);//运单管理  开单
				ordOdersInfo = ordersInfoTF.getOrdOrdersInfo(orderInfo.getOrdsId()); //下单
			}else{
				throw new BusinessException("无效操作！");
			}
		}
		    //找出 没有签收的子运单 做签收操作 
			List<OrderInfoChild> orderInfoChilds = orderInfoChildTF.getOrderInfoChildsByState(orderInfo.getOrderId());
			if (orderInfoChilds != null && orderInfoChilds.size() > 0) {
			    for (OrderInfoChild orderInfoChild : orderInfoChilds) {
				    orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.DO_SIGN);
				    orderInfoChildTF.doUpdateByWeb(orderInfoChild, user);
				    OrderInfoChildSign orderInfoChildSign = new OrderInfoChildSign();
				    orderInfoChildSign.setChildOrderId(orderInfoChild.getChildOrderId());
				    orderInfoChildSign.setChildTrackingNum(orderInfoChild.getChildTrackingNum());
				    orderInfoChildSign.setCreateId(user.getUserId());
				    orderInfoChildSign.setCreateName(user.getUserName());
				    orderInfoChildSign.setCreateTime(date);
				    orderInfoChildSign.setImageId(flowId);
				    orderInfoChildSign.setRemark(signDesc);
				    orderInfoChildSign.setSignName(user.getUserName());
				    orderInfoChildSign.setSignTime(date);
				    orderInfoChildSign.setSignType(2);
				    orderInfoChildTF.doSaveSign(orderInfoChildSign);
				    //签收出库
				    ordStockTF.signOutStock(orderInfoChild.getChildOrderId(), orderInfoChild.getDispatchingOrgId());
				    ordChildOpLogTF.departLog(orderInfoChild, 11, user, null);
				    isWaitSign = true;	
			   }
			}	
		if (!isWaitSign) {
			throw new BusinessException("已没有【待签收】的子运单，不能再操作签收！");
		}
		
		boolean isPartialSign = orderInfoSV.isPartialSign(orderInfo.getOrderId()) == orderInfo.getNumber();
		if (isWaitSign) {
			if(isPartialSign){
				orderInfo.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.DO_SIGN);
				orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.DO_SIGN);
			}else{
				//保存运单
				orderInfo.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.PARTIAL_SIGN);
				orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PARTIAL_SIGN);
			}
			
			doUpdate(orderInfo,user);	
			//保存订单
			if (isPartialSign) {
				ordOdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.DO_SIGN);
			}else{
				ordOdersInfo.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.PARTIAL_SIGN);
			}
			ordersInfoTF.doUpdateOrders(ordOdersInfo, user);
		    //保存签收表
			OrdSignInfo signInfo = ordersInfoTF.getOrdSignInfo(ordOdersInfo.getOrderId());
			if (signInfo==null) {
				signInfo = new OrdSignInfo();
			}
				
			signInfo.setCreateId(user.getUserId());
			signInfo.setTrackingNum(ordOdersInfo.getTrackingNum());
			signInfo.setOrderNo(ordOdersInfo.getOrderNo());
			signInfo.setExt1(StringUtils.defaultString(phone, ""));
			signInfo.setOrderId(ordOdersInfo.getOrderId());
			signInfo.setSignDate(date);
			signInfo.setSignName(signName!=null?signName:"");
			signInfo.setFlowId(flowId);
			signInfo.setSignDesc(signDesc);
			signInfo.setSource(2);
			signInfo.setCertificateNo(certificateNo.toString()!=null?certificateNo.toString():"");			
			if(certificateType > 0){
				signInfo.setCertificateType(certificateType);
			}	
			ordersInfoTF.doSaveSign(signInfo);
			// 写日志
			OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
			ordLogNewTF.signUpOrderLog(user.getUserId(), user, ordOdersInfo, ordOdersInfo.getCompanyName(), OraganizationCacheDataUtil.getOrgName(ordOdersInfo.getOrgId()==null?0:ordOdersInfo.getOrgId()), user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.BUSINESS?1:0);
			
			long tenantId = user.getTenantId() != null ? user.getTenantId() : orderInfo.getTenantId();
			if (CommonUtil.tmsTenantId("TMS_KL") == tenantId && StringUtils.isEmpty(tmsklinfo)) {
				String isSign = OrderPostUtil.signKL(orderInfo.getOrderNumber(), user.getUserName(), DateUtil.formatDateByFormat(date, "yyyy-MM-dd"), user.getUserName(), "", "", DateUtil.formatDateByFormat(date, "yyyy-MM-dd"), user.getUserName(), user.getTenantId(),false);
				if (!"Y".equals(isSign)) {
					log.error(isSign);
					OrderInfoTmsError orderInfoTmsError = new OrderInfoTmsError();
					orderInfoTmsError.setOrderNumber(orderInfo.getOrderNumber());
					orderInfoTmsError.setUserName(user.getUserName());
					orderInfoTmsError.setCreateTime(date);
					orderInfoTmsError.setSignName(user.getUserName());
					orderInfoTmsError.setSignTime(date);
					orderInfoTmsError.setTenantId(user.getTenantId());
					orderInfoTmsError.setOrderId(orderInfo.getOrderId());
					orderInfoTmsError.setOrdsId(orderInfo.getOrdsId());
					orderInfoTmsError.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.WAIT_SIGN);
					orderInfoTmsErrorTF.doSaveOrderInfoTmsError(orderInfoTmsError);
				}
			}
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tips", "货物已签收");
		return map;
		
	}

	/**
	 * 签收信息回显【601075】
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getOrdSign(long orderId) throws Exception{
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		return orderInfoSV.getOrdSign(orderId);
	} 
	
	
	//取消订单  开单员
    //	运单管理--在待发车状态加多一个取消功能，点击取消判断运单状态为“待配载”，并库存在开单网点才能进行取消
    //	取消成功后，将运单状态变更为已取消，订单状态变更为已取消；同时推送给科莱tms进行取消运单
	//update 
	public String  cancelOrderInfo(Map<String, Object> inParam) throws Exception 
	{
		//cancelTrack(inParam);
		String trackingNum = DataFormat.getStringKey(inParam, "trackingNum");
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		Session session = SysContexts.getEntityManager(true);
		
		OrdOrdersInfoSV ordOrdersInfoSV=  (OrdOrdersInfoSV) SysContexts.getBean("ordOrdersInfoSV"); 
		OrdStockTF ordStockTF = (OrdStockTF) SysContexts.getBean("ordStockTF");
		
		OrderInfoChildTF orderInfoChildTF = (OrderInfoChildTF) SysContexts.getBean("orderInfoChildTF");
	 
		BaseUser user = SysContexts.getCurrentOperator();
		
		OrderInfo orderInfo = orderInfoSV.getOrderInfoByOrderNumber(trackingNum);
		if(orderInfo==null ){
			 throw new BusinessException("无此运单信息");	
		}
		long orderId = orderInfo.getOrderId();
		long ordsId = orderInfo.getOrdsId();
	    List<OrderInfoChild> orderInfoChilds = orderInfoChildTF.getOrderInfoChilds(orderId);//得到子运单
	    if(orderInfo.getOrderStateOut() != SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.PENDING_DEPARTURE ){
			 throw new BusinessException("运单已配载，请先删除配载，再操作取消运单");	
	   }
		
	   if(orderInfo.getOrderState() != SysStaticDataEnumYunQi.ORDER_INFO_STATE.STOWAGE_PLAN ){
			 throw new BusinessException("运单不是待配载状态，不能取消运单");	
	   }
	   // app和 专线，只能开单员取消自己网点的运单
	   if(user.getUserType()!=SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PLATFORM){
		   if(user.getUserType()!=SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION){
		      throw new BusinessException("此用户不是开单员，不能操作取消运单"); 
	       }
		   if(orderInfoChilds.get(0).getCurrentOrgId().longValue() != orderInfo.getBillingOrgId().longValue())
		   {
			   throw new BusinessException("开单网点不是当前网点，不能操作取消运单"); 
		   }
		   
	   }
	
	   if (orderInfoChilds != null && orderInfoChilds.size() > 0) {
			for (OrderInfoChild orderInfoChild : orderInfoChilds) {	
				if(orderInfoChild.getCurrentOrgId().longValue()!=orderInfo.getBillingOrgId().longValue() ){
					throw new BusinessException("库存网点不是开单网点，不能取消运单");
				}
				if(ordStockTF.checkPutIn(orderInfoChild.getChildOrderId(),orderInfoChild.getCurrentOrgId())){
					ordStockTF.deletePutInStorage(orderInfoChild.getChildOrderId(),orderInfo.getBillingOrgId());//删除库存表记录
				}
				orderInfoChild.setChildOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.DO_CANCER);
				orderInfoChild.setOpName(user.getUserName());
				orderInfoChild.setOpTime(new Date());
				orderInfoChild.setOpId(user.getUserId());
				session.update(orderInfoChild);//更新子运单表	
				//子运单写日志
			}	
		}
		//更新运单表
		orderInfo.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.DO_CANCER);
		orderInfo.setOpName(user.getUserName());
		orderInfo.setOpTime(new Date());
		orderInfo.setOpId(user.getUserId());
		orderInfo.setOrderStateOut(SysStaticDataEnumYunQi.ORDER_INFO_STATE_OUT.DO_CANCER);
		orderInfoSV.doSaveOrUpdate(orderInfo);
		
		OrdOrdersInfo ordOrdersInfos = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, ordsId);
		
		if(ordOrdersInfos ==null ){
			throw new BusinessException("无此订单信息！");
		}
		//更新订单表
		ordOrdersInfos.setOrderState(SysStaticDataEnumYunQi.ORDERS_STATE.DO_CANCER);
		ordOrdersInfos.setOpDate(new Date());
		ordOrdersInfos.setOpId(user.getUserId());
		session.update(ordOrdersInfos);
		//写主单日志 
		OrdLogNewTF ordLogNewTF=(OrdLogNewTF)SysContexts.getBean("ordLogNewTF");
	    ordLogNewTF.cancerOrderLog(user.getUserId(),1, user,  ordOrdersInfos);
	    
	    String 	json = "N";  
		//调TMS 取消运单
		String tmsklinfo = DataFormat.getStringKey(inParam, "tmsklinfo");
		long tenantId = user.getTenantId() != null ? user.getTenantId() : orderInfo.getTenantId();
		if (CommonUtil.tmsTenantId("TMS_KL") == tenantId && StringUtils.isEmpty(tmsklinfo)) {
			 json = OrderPostUtil.cancelOrderKL(trackingNum,user.getUserName().toString(),user.getTenantId(), false );
			if (!"Y".equals(json)) {
				OrderInfoTmsErrorTF orderInfoTmsErrorTF = (OrderInfoTmsErrorTF) SysContexts.getBean("orderInfoTmsErrorTF");
				log.error(json);
				OrderInfoTmsError orderInfoTmsError = new OrderInfoTmsError();
				orderInfoTmsError.setOrderNumber(orderInfo.getOrderNumber());
				orderInfoTmsError.setUserName(user.getUserName());
				orderInfoTmsError.setCreateTime(new Date());
				orderInfoTmsError.setTenantId(user.getTenantId());
				orderInfoTmsError.setOrderId(orderInfo.getOrderId());
				orderInfoTmsError.setOrdsId(orderInfo.getOrdsId());
				orderInfoTmsError.setOrderState(SysStaticDataEnumYunQi.ORDER_INFO_STATE.DO_CANCER);
				orderInfoTmsErrorTF.doSaveOrderInfoTmsError(orderInfoTmsError);
				throw new BusinessException("云货宝取消开单成功，同步TMS失败");	
		     }
	     }	
		return "Y";			
	  }

	public int getOrderStateOut(int orderState) {
		// TODO Auto-generated method stub
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		return orderInfoSV.getOrderStateOut(orderState);
	}
	
	
	@Override
	public   Map<String, Object> queryChildOrderLogByRole(Map<String, Object> inParam)throws Exception {
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		List<OrdChildOrderOpLogListOut> listOut = new ArrayList<OrdChildOrderOpLogListOut>();
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		String childOrderNo = DataFormat.getStringKey(inParam, "childTrackingNum");//子运单号别名
		//根据子运单号得到子运单跟踪信息  ord_child_op_log
		Criteria ca = session.createCriteria(OrdChildOpLog.class);
		ca.add(Restrictions.eq("inType", SysStaticDataEnum.OP_TYPE.IN_TYPE_YES));
		ca.add(Restrictions.eq("childTrackingNum",childOrderNo));
		ca.addOrder(Order.desc("id"));
		//内部查询
		List<OrdChildOpLog> lists = ca.list();
		if(lists==null|| lists.size()<= 0){
			
		}
		for(OrdChildOpLog log : lists){
			OrdChildOrderOpLogListOut out = new OrdChildOrderOpLogListOut();
			BeanUtils.copyProperties(out, log);
			//内部查询
			out.setOpContent(log.getInContent());
			listOut.add(out);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("o", listOut);
		return map;
	}


	
}
