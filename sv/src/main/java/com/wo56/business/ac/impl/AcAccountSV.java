package com.wo56.business.ac.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ac.vo.AcAccount;
import com.wo56.business.ord.vo.OrdDepartInfo;
import com.wo56.business.transfer.vo.OrdTransferInfo;
import com.wo56.common.consts.AccountManageConsts;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.utils.CommonUtil;

/**
 * 用户帐户信息
 * 
 * @author zx
 *
 */
public class AcAccountSV {
	
	private static final Log logger = LogFactory.getLog(AcAccountSV.class);
	/**
	 * 保存账户信息
	 * 
	 * @param cmAccount
	 */
	public void save(AcAccount cmAccount) {
		Session session = SysContexts.getEntityManager();
		cmAccount.setCreateTime(new Date());
		session.saveOrUpdate(cmAccount);
	}

	/**
	 * 查询账户信息
	 * 
	 * @param accountId
	 * @return
	 */
	public AcAccount getCmAccountById(long accountId) {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(AcAccount.class);
		ca.add(Restrictions.eq("accountId", accountId));
		List<AcAccount> accounts = ca.list();
		return accounts.get(0);
	}
	

	public List<AcAccount> getCmAccountByCondition(int objType,int accountType,long objId) {
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(AcAccount.class);
		if(objType>=0){
			ca.add(Restrictions.eq("objType", objType));
		}
		if(accountType>=0){
			ca.add(Restrictions.eq("accountType", accountType));
		}
		if(objId>=0){
			ca.add(Restrictions.eq("objId", objId));
		}
		List<AcAccount> accounts = ca.list();
		return accounts;
	}
	
	/**
	 * 账户金额变动，money可以为负数
	 * @param accountId账户ID
	 * @param money金额
	 * @return
	 */
	public long changeAccountMoney(long accountId, long money) {
		BaseUser baseUser = SysContexts.getCurrentOperator();
		logger.info("账户金额变动：账户ID" + accountId + "变动金额" + money + "操作员id" + baseUser.getUserId() + "操作员" + baseUser.getUserName());
		Session session = SysContexts.getEntityManager();
		Criteria ca = session.createCriteria(AcAccount.class);
		ca.setLockMode(LockMode.UPGRADE);
		ca.add(Restrictions.eq("accountId", accountId));
		AcAccount aa = (AcAccount) ca.uniqueResult();
		if (aa == null) {
			throw new BusinessException("账户信息为空");
		}
		aa.setBalance(aa.getBalance() + money);
		session.saveOrUpdate(aa);
		return aa.getBalance();
	}
	
	/**
	 * 账户金额变动，money可以为负数
	 * @param accountId账户ID
	 * @param money金额
	 * @return
	 */
	public void closeAccountMoney(long accountId, long money) {
		/*BaseUser baseUser = SysContexts.getCurrentOperator();
		logger.info("账户金额变动：账户ID" + accountId + "变动金额" + money + "操作员id" + baseUser.getUserId() + "操作员" + baseUser.getUserName());*/
		Session session = SysContexts.getEntityManager();
		Criteria ca = session.createCriteria(AcAccount.class);
		ca.setLockMode(LockMode.UPGRADE);
		ca.add(Restrictions.eq("accountId", accountId));
		AcAccount aa = (AcAccount) ca.uniqueResult();
		if (aa == null) {
			throw new BusinessException("账户信息为空");
		}
		aa.setBalance(aa.getBalance() +money);
		session.saveOrUpdate(aa);
	}
	
	
	/**
	 * 
	 * lyh 的账户明细核销操作
	 * @param checkedId
	 * @throws Exception 
	 * @param type   1 :中转核销 2：其他 
	 * 
	 */
	public void dealAcAcAccountDtlCheckSts(long checkedId, int type) throws Exception{
		logger.info("核销处理接口:checkedId=" + checkedId);
		//核销日志处理
//		AcAccountDtlLogSV logSv = (AcAccountDtlLogSV) SysContexts.getBean("acAccountDtlLogSV");
//		logSv.opAccountDtlLog(ac, checkAmount,1,beforeAmount);
	
	}
	
	
	/**
	 * 
	 * lyh 的账户明细核销操作 NEW　(支持部分核销)
	 * @param Map　
	 * 
	 */
//	@SuppressWarnings("rawtypes")
//	public void dealAcAcAccountDtlCheckStsNew(Map inParam) throws Exception{
//		Session session = SysContexts.getEntityManager();
//		BaseUser user = SysContexts.getCurrentOperator();
//		long checkedId = DataFormat.getLongKey(inParam, "checkedId");
//		int feeType = DataFormat.getIntKey(inParam, "feeType");
//		String withoutAmount = DataFormat.getStringKey(inParam, "withoutAmount");//实际当前要核销金额
//		long orderId = DataFormat.getLongKey(inParam, "orderId"); //订单号
//		// 处理2种情况 1：旧数据 的存在核销ID 2 新数据不存在ID（必须生成核销明细规则和以前1样）
//		if(StringUtils.isEmpty(withoutAmount)){
//			throw new BusinessException("传入的实际需核销金额不能为空，核销失败。");
//		}
//		if(!CommonUtil.isNumber(withoutAmount)){
//			throw new BusinessException("传入的实际需核销金额不是数字，核销失败。");
//		}
//		if(orderId <= 0 
//				&& feeType != AccountManageConsts.Common. FEE_TYPE_TRANSPORT_FEE
//				   && feeType != AccountManageConsts.Common. FEE_TYPE_ZX_FEE){
//			throw new BusinessException("请传入订单号");
//		}
//		long checkAmount = Math.round((Double.valueOf(withoutAmount) * 100)); //最新核销金额
//		if(checkedId > 0){
//			
//			//旧数据 的存在核销ID 
//		   AcAccountDtl acAccountDtl = dealOldCheckAccountDtl(session, user, checkAmount, checkedId);
//			
//			if(acAccountDtl.getFeeType() == AccountManageConsts.Common.FEE_TYPE_PROVE_TIP &&
//					acAccountDtl.getCheckSts() == AccountManageConsts.AcCashProve.CHECK_STS_OK){
//				//回扣处理
//				OrdFee ordFee =(OrdFee)session.get(OrdFee.class, orderId);
//				//已核销 回扣已返
//				ordFee.setIsPayDiscount(SysStaticDataEnum.IS_PAY_DISCOUNT.IS_PAY_DISCOUNT);
//				session.saveOrUpdate(ordFee);
//			}
//			 
//			
//				
//		}else{
//			//2 新数据不存在ID（必须生成核销明细规则和以前1样）(新逻辑的处理数据) （到付、代收货款需特殊数据）
//			
//			//现在 开单网点 （现付、到付、月结、回单付、转账、代收货款、代收手续费、回扣、提货费）
//			AcOrderAccountOperSV accountOperSV = (AcOrderAccountOperSV)SysContexts.getBean("orderAccountSV");
//			OrdOrderInfo orderInfo = (OrdOrderInfo) session.get(OrdOrderInfo.class,orderId);
//			if(orderInfo == null){
//				 throw new BusinessException("查找运单信息失败。");
//			}
//			
//			OrdFee ordFee = (OrdFee) session.get(OrdFee.class,orderId);
//			
//			if(ordFee == null){
//				throw new BusinessException("查找费用信息失败。");
//			}
//			
//			if((feeType == AccountManageConsts.Common.FEE_TYPE_ARRIVE_PAY||feeType == AccountManageConsts.Common.FEE_TYPE_GET_GOODS_PAY) &&
//					orderInfo.getSeeOrderState() != SysStaticDataEnum.SEE_ORDER_TYPE.SEE_ORDER_TYPE5 ){
//				 throw new BusinessException("运单["+orderInfo.getTrackingNum()+"]未签收不可做现付核销操作。");
//				
//			}
//			long fee = 0;
//			if(feeType == AccountManageConsts.Common.FEE_TYPE_CASH_PAY){
//				//现付
//				fee = ordFee.getCashPayment();
//			}else if(feeType == AccountManageConsts.Common.FEE_TYPE_ARRIVE_PAY){
//				//到付
//				fee = ordFee.getFreightCollect();
//			}else if(feeType == AccountManageConsts.Common.FEE_TYPE_BACK_PAY){
//				//回单付
//				fee = ordFee.getReceiptPayment();
//			}else if(feeType == AccountManageConsts.Common.FEE_TYPE_MONTH_PAY){
//				//月结
//				fee = ordFee.getMonthlyPayment();
//			}else if(feeType == AccountManageConsts.Common.FEE_TYPE_GET_GOODS_PAY){
//				//代收货款
//				fee = ordFee.getCollectingMoney();
//			}else if(feeType == AccountManageConsts.Common.FEE_TYPE_GET_POUN_PAY){
//				//代收货款手续费
//				fee = ordFee.getProcedureFee();
//			}else if(feeType == AccountManageConsts.Common.FEE_TYPE_PROVE_TIP){
//			   //回扣
//				fee = ordFee.getDiscount();
//			}else if(feeType == AccountManageConsts.Common.FEE_TYPE_FORWARD_ACCOUNT_FEE){
//				//转账
//				fee = ordFee.getTransPayment();
//			}else if(feeType == AccountManageConsts.Common.FEE_TYPE_DRIVERLY_GOODS_MONEY){
//				//提货费
//				fee = ordFee.getPickingCosts();
//			}else if(feeType == AccountManageConsts.Common.FEE_TYPE_ACTUAL_DRIVERLY_GOODS_MONEY){
//				//实际提货费
//				fee = ordFee.getActualBillCosts();
//			}else if(feeType == AccountManageConsts.Common.FEE_TYPE_MAT_GOODS_MONEY){
//				//垫货款
//				fee = ordFee.getAdvanceMoney();
//			}else{
//				 throw new BusinessException("传入核销类型无效");
//			}
//			if(fee < checkAmount){
//				 throw new BusinessException("实际核销金额不能大于核销金额。");
//			}
//			//判断是否已核销
//			dealCheckAccount(orderId, feeType, AccountManageConsts.Common.OBJ_TYPE_POINT, user.getOrgId());
//			accountOperSV.operOrderAccountChangeNew(orderInfo,ordFee,feeType,checkAmount,fee);
//
//		}
//	
//	}
//	
	/***
	 *  lyh 中转费用核销
	 * 1、支持部分核销
	 * 2、兼容已生成的核销
	 * 3、后续中转不在生成核销记录（核销动作才生成）
	 * @throws Exception 
	 */
//	@SuppressWarnings("rawtypes")
//	public void dealTranferFeeChecksNew(Map inParam) throws Exception {
//		Session session = SysContexts.getEntityManager();
//		BaseUser user = SysContexts.getCurrentOperator();
//		long checkedId = DataFormat.getLongKey(inParam, "checkedId");
//		String withoutAmount = DataFormat.getStringKey(inParam, "withoutAmount");//实际当前要核销金额
//		long orderId = DataFormat.getLongKey(inParam, "orderId"); //订单号
//		AcOrderAccountOperSV accountOperSV = (AcOrderAccountOperSV)SysContexts.getBean("orderAccountSV");
//		OrdOrderInfoTF orderInfoTF = (OrdOrderInfoTF) SysContexts.getBean("ordOrderInfoTF");
//		// 处理2种情况 1：旧数据 的存在核销ID 2 新数据不存在ID（必须生成核销明细规则和以前1样）
//		if(StringUtils.isEmpty(withoutAmount)){
//			throw new BusinessException("传入的实际需核销金额不能为空，核销失败。");
//		}
//		if(!CommonUtil.isNumber(withoutAmount)){
//			throw new BusinessException("传入的实际需核销金额不是数字，核销失败。");
//		}
//		if(orderId <= 0 ){
//			throw new BusinessException("请传入订单号");
//		}
//		long checkAmount = Math.round((Double.valueOf(withoutAmount) * 100)); //最新核销金额
//		if(checkedId > 0){
//			//已生成核销记录
//			//dealOldCheckAccountDtl(session, user, checkAmount, checkedId);
//		}else{
//			//需要生成中转核销 （中转网点  支出  承运商）（现在的代码逻辑与代货款、现付分开）
//			 
//			 OrdOrderInfo orders = orderInfoTF.getOrderInfoById(orderId);
//			 if(orders == null){
//				 throw new BusinessException("查找运单信息失败!");
//			 }
//			   
//			 OrdTransitOutgoing out = orderInfoTF.getTransitOutgoingByOrderId(orderId, true);
//			 //中转需要生成核销记录
//			 if(out == null){
//				 throw new BusinessException("查找运单号["+orders.getTrackingNum()+"]外发记录失败!");
//			 }
//			 if(checkAmount > (out.getOutgoingFee() == null ? 0 : Math.abs(out.getOutgoingFee()))){
//				 throw new BusinessException("运单号["+orders.getTrackingNum()+"]的核销金额不能大于外发费用金额!");
//			 }
//			 //判断是否已核销
//			 long chekcOrgId = out.getCarrierOrgId();
//			 dealCheckAccount(orders.getOrderId(),AccountManageConsts.Common.FEE_TYPE_TRANSFER,AccountManageConsts.Common.OBJ_TYPE_POINT,chekcOrgId);
//			 accountOperSV.operTranferAccountChangeNew(orders,out,checkAmount);
//		}
//	}
//	
		
	

	/***
	 *  lyh 配安费、维修费 费用核销
	 * 1、支持部分核销
	 * 2、兼容已生成的核销
	 * 3、后续中转不在生成核销记录（核销动作才生成）
	 * @throws Exception 
	 */
	
	@SuppressWarnings("rawtypes")
	public void dealTaskFeeChecksNew(Map inParam) throws Exception {
		
		
	}
	
	/***
	 *  lyh 大车费、大车装卸费 费用核销
	 * 1、支持部分核销
	 * 2、兼容已生成的核销
	 * 3、后续中转不在生成核销记录（核销动作才生成）
	 * @throws Exception 
	 */
//	@SuppressWarnings("rawtypes")
//	public void dealTransportFeeChecksNew(Map inParam) throws Exception {
//		Session session = SysContexts.getEntityManager();
//		BaseUser user = SysContexts.getCurrentOperator();
//		long checkedId = DataFormat.getLongKey(inParam, "checkedId");
//		String withoutAmount = DataFormat.getStringKey(inParam, "withoutAmount");//实际当前要核销金额
//		long batchNum = DataFormat.getLongKey(inParam, "batchNum"); //批次ID
//		int queryType = DataFormat.getIntKey(inParam, "queryType"); //1:干线短驳 2：中转
//		int feeType = DataFormat.getIntKey(inParam, "feeType"); //24大车运费24大车装卸费
//		if(feeType != 24 && feeType != 25){
//			throw new BusinessException("输入核销类型有误。");
//		}
//		AcOrderAccountOperSV accountOperSV = (AcOrderAccountOperSV)SysContexts.getBean("orderAccountSV");
//		OrdOrderInfoTF orderInfoTF = (OrdOrderInfoTF) SysContexts.getBean("ordOrderInfoTF");
//		// 处理2种情况 1：旧数据 的存在核销ID 2 新数据不存在ID（必须生成核销明细规则和以前1样）
//		if(StringUtils.isEmpty(withoutAmount)){
//			throw new BusinessException("传入的实际需核销金额不能为空，核销失败。");
//		}
//		if(!CommonUtil.isNumber(withoutAmount)){
//			throw new BusinessException("传入的实际需核销金额不是数字，核销失败。");
//		}
//		if(batchNum <= 0 ){
//			throw new BusinessException("请传入批次主键");
//		}
//		long checkAmount = Math.round((Double.valueOf(withoutAmount) * 100)); //最新核销金额
//		if(checkedId > 0){
//			//已生成核销记录
//		//	dealOldCheckAccountDtl(session, user, checkAmount, checkedId);
//		}else{
//			//需要生成大车费核销 、装卸费核销
//			long checkOrgId = 0;
//			long fee = 0;
//			Long faceObjId = 0L;
//			String faceObjName = "";
//			int paySts = 1;//是否已付、未付1未付 、2已付  
//			int nodeType = AccountManageConsts.AcAccountDtl.NODE_TYPE_DEPART;
//			if(queryType == 1){
//				//干线短驳发车
//				OrdDepartInfo order = 	orderInfoTF.getDepartInfo(batchNum);
//				if(order == null){
//					throw new BusinessException("批次信息查询失败");
//				}
//				if(feeType == AccountManageConsts.Common.FEE_TYPE_TRANSPORT_FEE){
//					checkOrgId = order.getFreightPayDot();
//					paySts = order.getPayState();
//					fee = order.getFreight();
//				}else{
//					checkOrgId = order.getStevedoringPayDot();
//					paySts = order.getStevedoringPayState();
//					fee = order.getStevedoring();
//				}
//				faceObjId =  order.getDriverId() == null ? -1 : order.getDriverId();
//				faceObjName = order.getDriverName();
//				
//			}else{
//				nodeType = AccountManageConsts.AcAccountDtl.NODE_TYPE_TRANSIT;
//				//中转发车
//				OrdTransferInfo order = orderInfoTF.getTranferDepartInfo(batchNum);
//				if(order == null){
//					throw new BusinessException("批次信息查询失败");
//				}
//				if(feeType == AccountManageConsts.Common.FEE_TYPE_TRANSPORT_FEE){
//					checkOrgId = order.getFreightPayDot();
//					paySts = order.getPayState();
//					fee = order.getFreight();
//				}else{
//					checkOrgId = order.getStevedoringPayDot();
//					paySts = order.getStevedoringPayState();
//					fee = order.getStevedoring();
//				}
//				faceObjId =  order.getDriverId() == null ? -1 : order.getDriverId();
//				faceObjName = order.getDriverName();
//			}
//			if(fee < checkAmount){
//				throw new BusinessException("传入的实际需核销金额大于核销总金额,核销失败。");
//			}
//			 //判断是否已核销
//			 dealCheckAccount(batchNum,AccountManageConsts.Common.FEE_TYPE_TRANSFER,AccountManageConsts.Common.OBJ_TYPE_POINT,checkOrgId);
//			 //处理费用信息
//			 accountOperSV.dealTranspotFee(batchNum,checkOrgId,fee,checkAmount,faceObjId,faceObjName,paySts,feeType,nodeType);
//			
//		}
//		
//	}
	
	
	/**
	 * 判断是否已核销(已存在核销记录信息，不能走最新的核销逻辑)
	 * @param orderId 订单号/批次号
	 * @param feeType 科目类型
	 * @param objType 对象类型
	 * @param chekcOrgId 核销网点
	 * @param descOrgId 到站网点
	 */
	@SuppressWarnings("rawtypes")
	public void dealCheckAccount(long orderId, int feeType,int objType, long chekcOrgId) {
	}
//	/**
//	 * 
//	 * 处理已生成核销记录的 核销处理
//	 * 
//	 * 1:旧运单数据（核销记录已在每个节点生成）
//	 * 2:核销后反核销（核销记录已生成）
//	 *  TODO 
//	 * @throws Exception 
//	 */
//	public AcAccountDtl dealOldCheckAccountDtl(Session session, BaseUser user, long checkAmount,long checkedId) throws Exception {
//		//旧数据 的存在核销ID 
//		AcAccountDtl acAccountDtl = (AcAccountDtl) session.get(AcAccountDtl.class,checkedId);
//		if (acAccountDtl == null) {
//			throw new BusinessException("未找到["+checkedId+"]核销信息，核销失败。");
//		}
//		if(acAccountDtl.getCheckSts() == AccountManageConsts.AcCashProve.CHECK_STS_OK){
//			 throw new BusinessException("运单["+acAccountDtl.getTrackingNum()+"]已核销不可再进行核销操作。");
//		}
//		// 处理支付状态
//		acAccountDtl.setPaySts(dealAccountDtlPaySts(acAccountDtl.getFeeType(),1));
//		int feeType = acAccountDtl.getFeeType();
//		
//		
//		// 已核销
//		Date createDate = new Date();
//		acAccountDtl.setCheckDate(createDate);
//		acAccountDtl.setCheckOpId(user.getUserId());
//		acAccountDtl.setModifyOpId(user.getUserId());
//		acAccountDtl.setPayStsModifyDate(createDate);
//		long checkAmountShu = acAccountDtl.getCheckAmount() == null ? 0 : acAccountDtl.getCheckAmount(); //已核销金额
//		long fee = Math.abs(acAccountDtl.getFee()); //防止负数计算出错
//		if(checkAmount < 0){
//			 throw new BusinessException("运单["+acAccountDtl.getTrackingNum()+"]核销金额不能小于0。");
//		}
//		
//		if(fee < (checkAmountShu + checkAmount)){
//			throw new BusinessException("运单["+acAccountDtl.getTrackingNum()+"]实际需核销金额加上已核销金额不能核销金额。");
//		}
//		long beforeAmount = 0;
//		
//		if(acAccountDtl.getCheckSts() == AccountManageConsts.AcCashProve.CHECK_STS_NON){
//			beforeAmount = acAccountDtl.getFee();
//		}else{
//			beforeAmount = acAccountDtl.getWithoutAmount();
//		}
//		acAccountDtl.setCheckAmount(checkAmountShu + checkAmount); //已核销金额
//		acAccountDtl.setWithoutAmount(fee - (checkAmountShu + checkAmount)); //剩余核销金额
//		
//		if(fee - (checkAmount + checkAmountShu) > 0){
//			acAccountDtl.setCheckSts(AccountManageConsts.AcCashProve.CHECK_STS_OK_PART);
//		}else{
//			acAccountDtl.setCheckSts(AccountManageConsts.AcCashProve.CHECK_STS_OK);
//		}
//		session.saveOrUpdate(acAccountDtl);
//		//if(acAccountDtl.getFeeType()==AccountManageConsts.Common.FEE_TYPE_ARRIVE_PAY||acAccountDtl.getFeeType()==AccountManageConsts.Common.FEE_TYPE_GET_GOODS_PAY){
//			Query query = session.createSQLQuery("UPDATE AC_ACCOUNT_DTL SET Check_Date=:checkDate,check_Op_Id=:checkOpId,modify_Op_Id=:modifyOpId,pay_Sts_Modify_Date=:payStsModifyDate,check_Amount=:checkAmount,without_Amount=:withoutAmount,check_Sts=:checkSts where fee_type=:feeType and order_id=:orderId and tracking_num=:trackingNum and  (obj_type=:objType or (face_obj_type=:objType and obj_Type not in (:faceObjType) ))   ");
//			query.setParameter("checkDate", acAccountDtl.getCheckDate());
//			query.setParameter("checkOpId", acAccountDtl.getCheckOpId());
//			query.setParameter("modifyOpId", acAccountDtl.getModifyOpId());
//			query.setParameter("payStsModifyDate", acAccountDtl.getPayStsModifyDate());
//			query.setParameter("checkAmount", acAccountDtl.getCheckAmount());
//			query.setParameter("withoutAmount", acAccountDtl.getWithoutAmount());
//			query.setParameter("checkSts", acAccountDtl.getCheckSts());
//			query.setParameter("objType", AccountManageConsts.Common.OBJ_TYPE_POINT);
//			query.setParameter("feeType",acAccountDtl.getFeeType());
//			query.setParameter("orderId", acAccountDtl.getOrderId());
//			query.setParameter("trackingNum", acAccountDtl.getTrackingNum());
//			query.setParameterList("faceObjType", new Integer[]{AccountManageConsts.Common.OBJ_TYPE_SHIPPER,AccountManageConsts.Common.OBJ_TYPE_SF_PARTANERS});
//			query.executeUpdate();
//	//	}
////		//核销日志处理
////		AcAccountDtlLogSV logSv = (AcAccountDtlLogSV) SysContexts.getBean("acAccountDtlLogSV");
////		logSv.opAccountDtlLog(acAccountDtl, checkAmount,1,Math.abs(beforeAmount));
//		
//		//对于代收货款处理 支出那条数据(旧数据需处理)
//		if(feeType == AccountManageConsts.Common.FEE_TYPE_GET_GOODS_PAY){
//			AcAccountDtl a = getPayGoodsInfo(acAccountDtl, feeType);
//			if(a != null){
//				a.setCheckDate(createDate);
//				a.setCheckOpId(user.getUserId());
//				a.setModifyOpId(user.getUserId());
//				a.setPayStsModifyDate(createDate);
//				a.setCheckAmount(checkAmountShu + checkAmount); //已核销金额
//				a.setWithoutAmount(fee - (checkAmountShu + checkAmount)); //剩余核销金额
//				
//				if(fee - (checkAmount + checkAmountShu) > 0){
//					a.setCheckSts(AccountManageConsts.AcCashProve.CHECK_STS_OK_PART);
//				}else{
//					a.setCheckSts(AccountManageConsts.AcCashProve.CHECK_STS_OK);
//				}
//				logSv.opAccountDtlLog(a, checkAmount,1,beforeAmount);
//			}
//			
//			
//		}
//		return acAccountDtl;
//	}
//	
	
	
	/**
	 * 
	 * lyh 的账户明细反核销操作
	 * @param checkedId
	 * @throws Exception 
	 * @param type   1 :中转反核销 2：其他 
	 * 
	 */
	public void dealUnAcAcAccountDtlCheckSts(long checkedId,int type) throws Exception{
		logger.info("反核销操作处理接口:checkedId=" + checkedId);	
	}
	
    /***
     * 
     * 处理支付状态
     * @param ac  核销明细
     * @param type 1：核销 2 反核销
     */
	public int dealAccountDtlPaySts(int payType,int type) {
		if(type == 1){
			if(payType == AccountManageConsts.Common.PAY_TYPE_INCOME){
				//改为已收
				 return AccountManageConsts.Common.PAY_STS_HAS_RECEIVE;
				
			}else{
				//改为已支
				return AccountManageConsts.Common.PAY_STS_HAS_PAY;
			}
		}else{
			if(payType == AccountManageConsts.Common.PAY_TYPE_INCOME){
				//改为未收
				return AccountManageConsts.Common.PAY_STS_NO_RECEIVE;
				
			}else{
				//改为未支
				return AccountManageConsts.Common.PAY_STS_NO_PAY;
			}
		}
		
	}
    /**
     * 获取代收货款支出的数据
     * @param ac
     * @param feeType
     **/
	 /**
     * 获取 回扣核销，提货费核销，代收货款核销（收入）的核销数
     * 
     **/
	
	/**
	 * 查询外发费是否已核销
	 */
	public Integer isCheckOutFeeState(long orderId, long trackingNum,long orgId) {
		Session  session = SysContexts.getEntityManager();
		Query query = session.createSQLQuery("SELECT COUNT(1) FROM ac_account_dtl a WHERE a.ORDER_ID=:orderId "
				               + " AND a.TRACKING_NUM=:trackingNum AND a.FEE_TYPE=:feeType AND a.OBJ_ID=:orgId ");
		
		query.setParameter("orderId", orderId);
		query.setParameter("trackingNum", trackingNum);
		query.setParameter("orgId", orgId);
		query.setParameter("feeType", AccountManageConsts.Common.FEE_TYPE_TRANSFER);
		
		Object obj = query.uniqueResult();
		if(obj != null){
			return Integer.valueOf(query.uniqueResult()+"");
		}
		return null;
	}


}
