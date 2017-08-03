package com.wo56.business.ac.interfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
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
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ac.impl.AcMyWalletSV;
import com.wo56.business.ac.vo.AcMyWallet;
import com.wo56.business.ac.vo.AcMyWalletHis;
import com.wo56.business.ac.vo.out.AcMyWalletListOut;
import com.wo56.business.ac.vo.out.AcMyWalletTipFeeParamOut;
import com.wo56.business.cm.impl.CmUserSV;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.ord.impl.OrderInfoSV;
import com.wo56.business.ord.intf.OrdOrdersInfoTF;
import com.wo56.business.ord.intf.OrderInfoTF;
import com.wo56.business.ord.vo.OrdGoodsInfo;
import com.wo56.business.ord.vo.OrdOrdersInfo;
import com.wo56.business.ord.vo.OrderFee;
import com.wo56.business.ord.vo.OrderInfo;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;

public class AcMyWalletTF implements IAcMyWalletIntf{
	/**
	 * 待提现接口
	 * @param tenantId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<AcMyWalletListOut> queryAcMyWallet(long tenantId){
		long userId= SysContexts.getCurrentOperator().getUserId();
		AcMyWalletSV acMyWalletSV = (AcMyWalletSV) SysContexts.getBean("acMyWalletSV");
		Session session = SysContexts.getEntityManager(true);
		Query queryGood = session.createSQLQuery(" select g.CONSIGNOR_NAME,g.CONSIGNOR_BILL,a.order_id from ord_goods_info g inner JOIN ac_my_wallet a ON g.ORDER_ID = a.ORDER_ID where a.tenant_Id = :tenantId AND a.amount_State = :amountState  AND a.STATE = :state AND a.user_id = :userId ");
		List<Object[]> listGood = queryGood.setParameter("tenantId", tenantId)
				.setParameter("amountState", SysStaticDataEnumYunQi.AMOUNT_STATE.NOT_PRESENT)
				.setParameter("state", SysStaticDataEnumYunQi.ACCOUNT_TYPE.INCOME).setParameter("userId", userId).list();
		//Query queryGood = session.createSQLQuery("select g.CONSIGNOR_NAME,g.CONSIGNOR_BILL,g.order_id from ord_goods_info where order_id in (select o.order_id from ac_my_wallet r,ord_orders_info o where o.ORDER_ID = r.ORDER_ID) ");
		Query query = acMyWalletSV.queryAcMyWallet(tenantId,userId);
		List<Object[]> list = query.list();
		List<AcMyWalletListOut> outList = new ArrayList<AcMyWalletListOut>();
		if(list != null && list.size() > 0){
			for(Object[] temp : list){
				AcMyWalletListOut out = new AcMyWalletListOut();
				out.setId(temp[0] != null ? String.valueOf(temp[0]) : "");
				out.setAmountString(temp[1] != null ? CommonUtil.parseDouble(Long.valueOf(String.valueOf(temp[1]))) : "");
				out.setOrderNumber(temp[2] != null ? String.valueOf(temp[2]) : "");
				out.setProvinceName(temp[3] != null ? SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(temp[3])).getName() : "");
				out.setCreateTime(temp[4] != null ? DateUtil.formatDate((Date)temp[4], "yyyy-MM-dd HH:mm:ss") : "");
				out.setCityName(temp[6] != null ?SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(temp[6])).getName():"");
				List<String> listStr = new ArrayList<String>();
				List<Object[]> consignorList = session.createSQLQuery("select g.CONSIGNOR_NAME,g.CONSIGNOR_BILL from ord_goods_info g where g.order_id = "+temp[5]).list();
				if(consignorList != null && consignorList.size() > 0){
					for(Object[] goods : consignorList){
						String consignor = "发货人："+ (goods[0] != null ? goods[0] : "")+"  "+(goods[1] != null ? goods[1] : "");
						listStr.add(consignor);
					}	
				}
				out.setConsignorList(listStr);
				outList.add(out);
			}
			
		}
		return outList;
	}
	
	public String presentAmount(long tenantId){
		AcMyWalletSV acMyWalletSV = (AcMyWalletSV) SysContexts.getBean("acMyWalletSV");
		return acMyWalletSV.presentAmount(tenantId);
	}
	/**
	 * 账单
	 * @param time
	 * @return
	 */
	public IPage billAcMyWallet(String time){
		List<Map<String,String>> outList = new ArrayList<Map<String,String>>();
		AcMyWalletSV acMyWalletSV = (AcMyWalletSV) SysContexts.getBean("acMyWalletSV");
		Query query = acMyWalletSV.billAcMyWallet(time);
		IPage page = PageUtil.gridPage(query);
		
		List<Object[]> list = page.getThisPageElements();
		if(list != null && list.size() > 0){
			for (Object[] objects : list) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("tenantName", objects[0] != null ? String.valueOf(objects[0]) : "");
				map.put("orderNumber", objects[1] != null ? String.valueOf(objects[1]) : "");
				map.put("amount", objects[2] != null ? CommonUtil.parseDouble(Long.parseLong(String.valueOf(objects[2]))) : "0.00");
				map.put("amountState", objects[3] != null ? SysStaticDataUtil.getSysStaticDataCodeName("AMOUNT_STATE_YUNQI", String.valueOf(objects[3])) : "");
				if(objects[3] != null){
					map.put("orderTime", DateUtil.formatDate((Date)objects[4], "yyyy-MM-dd HH:mm:ss"));
				}else{
					map.put("orderTime", "");
				}
				outList.add(map);
			}
		}
		page.setThisPageElements(outList);
		return page;
	}
	/**
	 * 生成提现明细
	 * @param orderInfo
	 * @param fee
	 * @param tenantId
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public long doSave(OrdOrdersInfo ordOrdersInfo,OrderFee fee,long tenantId,long userId,OrderInfo orderInfo) throws Exception{
		long pullId = notPullByUserId(userId, ordOrdersInfo.getCreateDate(), orderInfo.getPullPhone(), orderInfo.getPullName());
		AcMyWallet acMyWallet = new AcMyWallet();
		acMyWallet.setAmount(fee.getTip());
		acMyWallet.setAmountState(SysStaticDataEnumYunQi.AMOUNT_STATE.NOT_PRESENT);
		acMyWallet.setConsignor(orderInfo.getConsignor());
		acMyWallet.setConsignorPhone(orderInfo.getConsigneePhone());
		acMyWallet.setCreateTime(new Date());
		acMyWallet.setOrderId(ordOrdersInfo.getOrderId());
		acMyWallet.setOrderTime(ordOrdersInfo.getCreateDate());
		acMyWallet.setOrderNumber(ordOrdersInfo.getOrderNo());
		acMyWallet.setProvince(orderInfo.getDesProvince());
		acMyWallet.setProvinceName(orderInfo.getDesProvinceName());
		acMyWallet.setState(SysStaticDataEnumYunQi.ACCOUNT_TYPE.INCOME);
		acMyWallet.setTenantId(tenantId);
		//acMyWallet.setUserId(userId);
		acMyWallet.setUserId(pullId);
		AcMyWalletSV acMyWalletSV = (AcMyWalletSV) SysContexts.getBean("acMyWalletSV");
		acMyWalletSV.doSave(acMyWallet);
		
		return pullId;
	}
	/**
	 * 检验是否存在拉包工账号（没有就新建）
	 * @param userId
	 * @param createTime
	 * @param pullBill
	 * @param pullName
	 * @return
	 * @throws Exception
	 */
	private long notPullByUserId(long userId,Date createTime,String pullBill,String pullName) throws Exception{
		CmUserInfo cmUserInfo = null;
		BaseUser user = SysContexts.getCurrentOperator();
		Long pullId;
		CmUserSV cmUserSV = (CmUserSV) SysContexts.getBean("cmUserSv");
		if(userId < 0){
			cmUserInfo = cmUserSV.queryUserByAcct(pullBill);
			if(cmUserInfo == null){
				cmUserInfo = new CmUserInfo();
				cmUserInfo.setCreateTime(createTime);
				cmUserInfo.setLoginAcct(pullBill);
				cmUserInfo.setLoginPwd(K.j_s("123546"));
				cmUserInfo.setLoginType(SysStaticDataEnumYunQi.LOGIN_TYPE.WEB);
				cmUserInfo.setOpId(user.getUserId());
				cmUserInfo.setState(SysStaticDataEnumYunQi.STS.VALID);
				cmUserInfo.setUserName(pullName);
				cmUserInfo.setUserType(SysStaticDataEnumYunQi.USER_TYPE_YUNQI.VIRTUAL_USER);
				cmUserSV.doSaveCmUserInfo(cmUserInfo);
				pullId = cmUserInfo.getUserId();
			}else if(cmUserInfo.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.VIRTUAL_USER){
				pullId = cmUserInfo.getUserId();
			}else if(cmUserInfo.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULL){
				pullId = cmUserInfo.getUserId();
			}else{
				cmUserInfo = new CmUserInfo();
				cmUserInfo.setCreateTime(createTime);
				cmUserInfo.setLoginAcct(pullBill);
				cmUserInfo.setLoginPwd(K.j_s("123546"));
				cmUserInfo.setLoginType(SysStaticDataEnumYunQi.LOGIN_TYPE.WEB);
				cmUserInfo.setOpId(user.getUserId());
				cmUserInfo.setState(SysStaticDataEnumYunQi.STS.VALID);
				cmUserInfo.setUserName(pullName);
				cmUserInfo.setUserType(SysStaticDataEnumYunQi.USER_TYPE_YUNQI.VIRTUAL_USER);
				cmUserSV.doSaveCmUserInfo(cmUserInfo);
				pullId = cmUserInfo.getUserId();
			}
		}else{
			pullId = userId;
		}
		return pullId;
	}
	
	/**
	 * 生成提现明细
	 * @param orderId 单号
	 * @param createTime 开单时间
	 * @param orderNumber 运单号
	 * @param desProvince 到站id
	 * @param desProvinceName 到站名称
	 * @param consignor 发货人
	 * @param consigneePhone 发货人电话
	 * @param tip 小费
	 * @param tenantId 开单专线
	 * @param userId 拉包工
	 * @return
	 * @throws Exception 
	 */
	public Long doSaveTip(long orderId,Date createTime,String orderNumber,long desProvince,String desProvinceName,String consignor,String consigneePhone,long tip,long tenantId,long userId,String pullBill,String pullName) throws Exception{
		long pullId = notPullByUserId(userId, createTime, pullBill, pullName);
		AcMyWallet acMyWallet = new AcMyWallet();
		acMyWallet.setAmount(tip);
		acMyWallet.setAmountState(SysStaticDataEnumYunQi.AMOUNT_STATE.NOT_PRESENT);
		acMyWallet.setConsignor(consignor);
		acMyWallet.setConsignorPhone(consigneePhone);
		acMyWallet.setCreateTime(new Date());
		acMyWallet.setOrderId(orderId);
		acMyWallet.setOrderTime(createTime);
		acMyWallet.setOrderNumber(orderNumber);
		acMyWallet.setProvince(desProvince);
		acMyWallet.setProvinceName(desProvinceName);
		acMyWallet.setState(SysStaticDataEnumYunQi.ACCOUNT_TYPE.INCOME);
		acMyWallet.setTenantId(tenantId);
		acMyWallet.setUserId(pullId);
		AcMyWalletSV acMyWalletSV = (AcMyWalletSV) SysContexts.getBean("acMyWalletSV");
		acMyWalletSV.doSave(acMyWallet);
		return pullId;
	}
	
	
	/**
	 * 小费对账查询
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String,Object> billingAcMyWallet(Map<String,Object> inParam){
		AcMyWalletSV acMyWalletSV = (AcMyWalletSV) SysContexts.getBean("acMyWalletSV");
		Query query = acMyWalletSV.billingAcMyWallet(inParam,false);
		IPage page = PageUtil.gridPage(query);
		List<AcMyWalletTipFeeParamOut> tipList = new ArrayList<AcMyWalletTipFeeParamOut>();
		List<Object[]> list = page.getThisPageElements();
		if (list!= null && list.size() > 0) {
			String consignorPro = "";
			String consignorCity = "";
			String consignorCoun = "";
			String consigneePro = "";
			String consigneeCity = "";
			String consigneeCoun = "";
			for (Object[] objects : list) {
				AcMyWalletTipFeeParamOut out = new AcMyWalletTipFeeParamOut();
				out.setOrdersId(objects[0] != null ? Long.valueOf(String.valueOf(objects[0])) : -1);
				out.setId(objects[1] != null ? Long.valueOf(String.valueOf(objects[1])) : -1);
				out.setOrderNo(objects[2] != null ? String.valueOf(objects[2]) : "");
				out.setOrderNumber(objects[3] != null ? String.valueOf(objects[3]) : "");	
				out.setBillingOrgId(objects[4] != null ? Long.valueOf(String.valueOf(objects[4])) : -1);
				out.setTenantId(objects[5] != null ? Long.valueOf(String.valueOf(objects[5])) : -1);
				out.setDesCity(objects[6] != null ? Long.valueOf(String.valueOf(objects[6])) : -1);
				out.setPullName(objects[7] != null ? String.valueOf(objects[7]) : "");
				out.setPullPhone(objects[8] != null ? String.valueOf(objects[8]) : "");
				out.setTip(objects[9] != null ? Long.valueOf(String.valueOf(objects[9])) : 0);
				out.setDefaultTip(objects[10] != null ? Long.valueOf(String.valueOf(objects[10])) : 0);
				out.setNumber(objects[11] != null ? Integer.valueOf(String.valueOf(objects[11])) : 0);
				out.setWeight(objects[12] != null ? String.valueOf(objects[12]) : "");
				out.setVolume(objects[13] != null ? String.valueOf(objects[13]) : "");
				out.setFreight(objects[14] != null ? Long.valueOf(String.valueOf(objects[14])) : 0);
				out.setConsignor(objects[15] != null ? String.valueOf(objects[15]) : "");
				out.setConsignorPhone(objects[16] != null ? String.valueOf(objects[16]) : "");
				consignorPro = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(objects[31])).getName();
				consignorCity = SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(objects[32])).getName();
				consignorCoun  = SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(objects[33])).getName();
				out.setConsignorAddress(consignorPro+consignorCity+consignorCoun+objects[17] != null ? String.valueOf(objects[17]) : "");
				out.setConsignee(objects[18] != null ? String.valueOf(objects[18]) : "");
				out.setConsigneePhone(objects[19] != null ? String.valueOf(objects[19]) : "");
				consigneePro = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(objects[28])).getName();
				consigneeCity = SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(objects[29])).getName();
				consigneeCoun  = SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(objects[30])).getName();
				out.setConsigneeAddress(consigneePro+consigneeCity+consigneeCoun+objects[20] != null ? String.valueOf(objects[20]) : "");
				out.setPaymentString(objects[22] != null ? SysStaticDataUtil.getSysStaticDataCodeName("PAYMENT_TYPE_YQ", String.valueOf(objects[22])) : "");
				out.setProductName(objects[23] != null ? String.valueOf(objects[23]) : "");
				out.setPackName(objects[24] != null ? String.valueOf(objects[24]) : "");
				out.setCreateName(objects[25] != null ? String.valueOf(objects[25]) : "");
				out.setRemarks(objects[26] != null ? String.valueOf(objects[26]) : "");
				out.setOrderId(objects[27] != null ? Long.valueOf(String.valueOf(objects[27])) : -1);
				if (objects[3] != null) {
					out.setInterchangeString(objects[21] != null ? SysStaticDataUtil.getSysStaticDataCodeName("INTERCHANGE_YUNQI", String.valueOf(objects[21])) : "");
				}else{
					out.setInterchangeString(objects[21] != null ? SysStaticDataUtil.getSysStaticDataCodeName("SERVICE_TYPE", String.valueOf(objects[21])) : "");
				}
				
				tipList.add(out);
			}
		}
		Pagination p = new Pagination(page);
		p.setItems(tipList);
		Map<String,Object> map = new HashMap<String, Object>();
		map=JsonHelper.parseJSON2Map(JsonHelper.json(p));
		String _sum = DataFormat.getStringKey(inParam, "_sum");
		if ("1".equals(_sum)) {
			AcMyWalletTipFeeParamOut out =sumTipFee(inParam);
			map.put("sumData", out);
		}
		return map;
	}
	
	/**
	 * 小费对账合计
	 */
	@SuppressWarnings("unchecked")
	private AcMyWalletTipFeeParamOut sumTipFee(Map<String,Object> inParam){
		AcMyWalletSV acMyWalletSV = (AcMyWalletSV) SysContexts.getBean("acMyWalletSV");
		Query query = acMyWalletSV.billingAcMyWallet(inParam,true);
		List<Object[]> list = query.list();
		AcMyWalletTipFeeParamOut out = new AcMyWalletTipFeeParamOut();
		if(list != null&&list.size() > 0){
			for (Object[] objects : list) {
				out.setTip(objects[0] != null ? Long.valueOf(String.valueOf(objects[0])) : 0);
				out.setDefaultTip(objects[1] != null ? Long.valueOf(String.valueOf(objects[1])) : 0);
				out.setNumber(objects[2] != null ? Integer.valueOf(String.valueOf(objects[2])) : 0);
				out.setWeight(objects[3] != null ? String.valueOf(objects[3]) : "0");
				out.setVolume(objects[4] != null ? String.valueOf(objects[4]) : "0");
				out.setFreight(objects[5] != null ? Long.valueOf(String.valueOf(objects[5])) : 0);
			}
		}
		return out;
	}
	
	
	/**
	 * 调账
	 */
	public String accountTip(Map<String,Object> inParam)throws Exception{
		Session session  = SysContexts.getEntityManager();
		AcMyWalletSV acMyWalletSV = (AcMyWalletSV) SysContexts.getBean("acMyWalletSV");
		OrderInfoSV orderInfoSV = (OrderInfoSV) SysContexts.getBean("orderInfoSV");
		String tipString = DataFormat.getStringKey(inParam,"tipString");
		long tip = CommonUtil.getStringByLong(tipString);
		long id = DataFormat.getLongKey(inParam, "id");
		long ordersId = DataFormat.getLongKey(inParam,"ordersId");
		long orderId = DataFormat.getLongKey(inParam, "orderId");
		OrdOrdersInfo orders = (OrdOrdersInfo) session.get(OrdOrdersInfo.class, ordersId);
		if(orders != null){
			orders.setTipMoney(tip);
			session.save(orders);
		}
		if(orderId > 0){
			OrderFee fee = orderInfoSV.getOrderFee(orderId);
			if(fee != null){
				fee.setTip(tip);
				orderInfoSV.doSaveOrUpdateFee(fee);
			}
		}
		AcMyWallet myWallet = acMyWalletSV.getAcMyWallet(id);
		AcMyWalletHis his = new AcMyWalletHis();
		if(tip == myWallet.getAmount()){
			return "Y";
		}else{
			BeanUtils.copyProperties(his, myWallet);
			myWallet.setAmount(tip);
		}
		his.setId(null);
		his.setHisWalletId(myWallet.getId());
		acMyWalletSV.doSave(myWallet);
		acMyWalletSV.doSaveHis(his);
		return "Y";
	}
}
