package com.wo56.business.ac.interfaces;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.wo56.business.ac.impl.AcAccountWalletSV;
import com.wo56.business.ac.impl.AcMyWalletSV;
import com.wo56.business.ac.vo.AcAccountWallet;
import com.wo56.business.ac.vo.AcMyWallet;
import com.wo56.business.ac.vo.AcMyWalletHis;
import com.wo56.business.ac.vo.AcPaymentMethod;
import com.wo56.business.ac.vo.out.AcAccountWalletListOut;
import com.wo56.business.ac.vo.out.AcAccountWalletOut;
import com.wo56.business.ac.vo.out.AcAccountWalletPageOut;
import com.wo56.business.ac.vo.out.AcAccountWalletParamOut;
import com.wo56.business.ac.vo.out.AcAccountWalletTipOut;
import com.wo56.business.ac.vo.out.AcMyWalletOut;
import com.wo56.business.cm.intf.CmUserInfoPullTF;
import com.wo56.business.cm.intf.CmUserInfoYunQiTF;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.business.sys.vo.SysTenantDef;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.BeanUtil;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.SmsYQUtil;

public class AcAccountWalletTF implements IAcAccountWalletIntf{
	/**
	 * 已提现
	 * @param tenantId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Pagination queryAcAccoutnWallet(long tenantId,int amountState){
		if(tenantId < 0){
			throw new BusinessException("请选择公司！");
		}
		AcAccountWalletSV acAccountWalletSV = (AcAccountWalletSV) SysContexts.getBean("acAccountWalletSV");
		
		Query query = acAccountWalletSV.queryAcAccoutnWallet(tenantId,amountState);
		List<AcAccountWalletParamOut> listOut = new ArrayList<AcAccountWalletParamOut>();
		IPage p = PageUtil.gridPage(query);
		Pagination page = new Pagination(p);
		List<Object[]> list = page.getItems();
		if(list != null && list.size() > 0){
			for (Object[] objects : list) {
				AcAccountWalletParamOut out = new AcAccountWalletParamOut();
				out.setId(objects[0] != null ? String.valueOf(objects[0]) : "");
				out.setAccountNum(objects[1] != null ? String.valueOf(objects[1]) : "");
				out.setAmount(objects[2] != null ? String.valueOf(CommonUtil.parseDouble(Long.valueOf(String.valueOf(objects[2])))) : "");
				if (objects[3] != null) {
					out.setShowTime(DateUtil.formatDate((Date)objects[3], "yyyy-MM-dd HH:mm"));
				}else{
					out.setShowTime("");
				}
				if (objects[4] != null) {
					out.setApplyTime(DateUtil.formatDate((Date)objects[4], "yyyy-MM-dd HH:mm"));
				}else{
					out.setApplyTime("");
				}
				out.setPaymentType(objects[5] != null ? String.valueOf(objects[5]) : "");
				listOut.add(out);
			}
		}
		page.setItems(listOut);
		return page;
	}
	/**
	 * 提现详情
	 * @param accountId
	 * @return
	 */
	public AcAccountWalletOut getAcAccountDet(long accountId){
		if(accountId < 0){
			throw new BusinessException("请传入流水id");
		}
		AcAccountWalletSV acAccountWalletSV = (AcAccountWalletSV) SysContexts.getBean("acAccountWalletSV");
		//获取流水信息
		AcAccountWallet ac = acAccountWalletSV.getAcMyWalletByAccountNum(String.valueOf(accountId));
		AcMyWalletSV acMyWalletSV = (AcMyWalletSV) SysContexts.getBean("acMyWalletSV");
		if(ac == null){
			throw new BusinessException("无该提现编号的信息！");
		}
		AcAccountWalletOut out = new AcAccountWalletOut();
		out.setAmount(ac.getAmount() != null && ac.getAmount() > 0 ? String.valueOf(CommonUtil.parseDouble(ac.getAmount() )) : "0.00");
		out.setApplyTime(DateUtil.formatDateByFormat(ac.getApplyTime(), "yyyy-MM-dd HH:mm"));
		out.setShowTime(DateUtil.formatDateByFormat(ac.getShowTime(), "yyyy-MM-dd HH:mm"));
		Session session = SysContexts.getEntityManager(true);
		SysTenantDef tenant = (SysTenantDef) session.get(SysTenantDef.class, ac.getTenantId());
		out.setTenantName(tenant.getName());
		//获取流水详情
		List<AcMyWalletOut> listMy = new ArrayList<AcMyWalletOut>();
		List<Object[]> list = acMyWalletSV.getAccountById(ac.getId());
		for(Object[] temp : list){
			AcMyWalletOut outMy = new AcMyWalletOut();
			outMy.setAmount(temp[1] != null  ? CommonUtil.parseDouble(Long.parseLong(String.valueOf(temp[1]))) : "0.00");
			outMy.setDesProvince(temp[2] != null ? SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(temp[2])).getName() : "");
			outMy.setOrderNumber(temp[0] != null ? String.valueOf(temp[0]) : "");
			outMy.setOrderTime(temp[3] != null ? DateUtil.formatDateByFormat((Date)temp[3], "yyyy-MM-dd HH:mm") : "");
			listMy.add(outMy);
		}
		out.setItems(listMy);
		return out;
	}
	
	/**
	 * 提现申请
	 * @throws Exception 
	 */
	public Map<String,Object> applyAcAccountWallet(List<String> ids,long tenantId) throws Exception{
		AcMyWalletSV acMyWalletSV = (AcMyWalletSV) SysContexts.getBean("acMyWalletSV");
		List<AcMyWallet> list = acMyWalletSV.getAcMyWalletList(ids);
		BaseUser user  = SysContexts.getCurrentOperator();
		Date date = new Date();
		long amount = 0;
		long userId = SysContexts.getCurrentOperator().getUserId();
		CmUserInfoPullTF cmUserInfoPullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
		CmUserInfoPull pull = cmUserInfoPullTF.getCmUserInfoPull(userId);
		if(pull.getPullState() != SysStaticDataEnumYunQi.PULL_STATE.CERTIFIED){
			throw new BusinessException("你未认证通过，请认证通过后才能提现！");
		}
		
		AcPaymentMethodTF acPaymentMethodTF = (AcPaymentMethodTF) SysContexts.getBean("acPaymentMethodTF");
		AcPaymentMethod payment = acPaymentMethodTF.getAcPaymentMethodByUserId(userId);
		if(payment == null){
			throw new BusinessException("请绑定支付方式后，再做提现申请！");
		}
		
		AcAccountWalletSV acAccountWalletSV = (AcAccountWalletSV) SysContexts.getBean("acAccountWalletSV");
		AcAccountWallet wallet = new AcAccountWallet();
		String accountNum = DateUtil.formatDate(date, "yyyyMMdd");
		wallet.setAmountState(SysStaticDataEnumYunQi.AMOUNT_STATE.CASH_WITHDRAWAL);//提现状态
		wallet.setAmountType(SysStaticDataEnumYunQi.ACCOUNT_TYPE.INCOME);//提现类型
		wallet.setApplyTime(date);//申请提现时间
		wallet.setTenantId(tenantId);//租户
		wallet.setUserId(userId);
		
		wallet.setCreateTime(date);
		wallet.setApplyId(user.getUserId());
		wallet.setState(SysStaticDataEnumYunQi.STS.VALID);
		wallet.setAuditStatus(SysStaticDataEnumYunQi.AUDIT_STATUS_YQ.UNAUDITED);
		wallet.setWriteState(SysStaticDataEnumYunQi.WRITE_STATE_YQ.WRITE_NOT);
		wallet.setPaymentType(payment.getPaymentType());
		if(payment.getPaymentType() == SysStaticDataEnumYunQi.RECEI_TYPE_YQ.BANK_RECEI_TYPE_YQ){
			wallet.setPaymentCard(payment.getBankCard());
		}else{
			wallet.setPaymentCard(payment.getAccountNum());
		}
		acAccountWalletSV.applyAcAccoutnWallet(wallet);
		long pullId = 0L;
		if(list != null && list.size() > 0){
			for (AcMyWallet acMyWallet : list) {
				acMyWallet.setAmountState(SysStaticDataEnumYunQi.AMOUNT_STATE.CASH_WITHDRAWAL);
				acMyWallet.setState(SysStaticDataEnumYunQi.ACCOUNT_TYPE.INCOME);
				acMyWallet.setApplyTime(date);
				acMyWallet.setCreateTime(new Date());
				acMyWallet.setAccountId(wallet.getId());
				amount = acMyWallet.getAmount()+amount;
				pullId = acMyWallet.getUserId();
				acMyWalletSV.doSave(acMyWallet);
				
			}
		}
		wallet.setAmount(amount);//金额
		wallet.setAccountNum(accountNum+wallet.getId());//流水号
		wallet.setUserId(pullId);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("companyName", CommonUtil.getTennatNameById(tenantId));
		map.put("amount", wallet.getAmount() != null ? CommonUtil.getDoubleFixedNew2(wallet.getAmount().toString()) : "");
		map.put("accountNum", wallet.getAccountNum() != null ? wallet.getAccountNum() : "");
		map.put("accountState", wallet.getAmountState() != null ? SysStaticDataUtil.getSysStaticDataCodeName("AMOUNT_STATE_YUNQI", String.valueOf(wallet.getAmountState())) : "");
		
		return map;
	}
	/**
	 * 查询提现总金额
	 * @param tenantId
	 * @param amountState
	 * @return
	 */
	public String applyAmount(long tenantId,int amountState){
		AcAccountWalletSV acAccountWalletSV = (AcAccountWalletSV) SysContexts.getBean("acAccountWalletSV");
		return acAccountWalletSV.applyAmountList(tenantId, amountState);
	}
	/**
	 * 提现管理
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String,Object> queryCashTipFee(Map<String,Object> inParam){
		AcAccountWalletSV acAccountWalletSV = (AcAccountWalletSV) SysContexts.getBean("acAccountWalletSV");
		String _sum = DataFormat.getStringKey(inParam, "_sum");
		Query query = acAccountWalletSV.queryCashTipFee(inParam,false);
		IPage page = PageUtil.gridPage(query);
		List<Object[]> list = page.getThisPageElements();
		List<AcAccountWalletPageOut> listOut = new ArrayList<AcAccountWalletPageOut>();
		if(list != null && list.size() > 0){
			for (Object[] objects : list) {
				AcAccountWalletPageOut out = new AcAccountWalletPageOut();
				out.setId(objects[0] != null ? Long.valueOf(String.valueOf(objects[0])) : -1);
				out.setAccountNum(objects[1] != null ? String.valueOf(objects[1]) : "");
				out.setUserName(objects[2] != null ? String.valueOf(objects[2]) : "");
				out.setLoginAcct(objects[3] != null ? String.valueOf(objects[3]) : "");
				out.setAmount(objects[4] != null ? Long.valueOf(String.valueOf(objects[4])) : 0);
				out.setApplyTime(objects[5] != null ? (Date)objects[5] : null);
				out.setApplyString(objects[6] != null ? String.valueOf(objects[6]) : "");
				out.setAuditStatus(objects[7] != null ? Integer.valueOf(String.valueOf(objects[7])) : -1);
				out.setAuditString(objects[8] != null ? String.valueOf(objects[8]) : "");
				out.setAuditTime(objects[9] != null ? (Date)objects[9] : null);
				out.setWriteState(objects[10] != null ? Integer.valueOf(String.valueOf(objects[10])) : -1);
				out.setWriteTime(objects[11] != null ? (Date)objects[11] : null);
				out.setWriteString(objects[12] != null ? String.valueOf(objects[12]) : "");
				out.setWriteRemark(objects[13] != null ? String.valueOf(objects[13]) : "");
				out.setUserId(objects[14] != null ? Long.valueOf(String.valueOf(objects[14])) : 0);
				listOut.add(out);
			}
		}
		page.setThisPageElements(listOut);
		Pagination p = new Pagination(page);
		Map<String,Object> map = JsonHelper.parseJSON2Map(JsonHelper.json(p));
		if("1".equals(_sum)){
			AcAccountWalletPageOut sum = sumCashTip(inParam);
			map.put("sumData", sum);
		}
		return map;
	}
	/**
	 * 合计费用
	 * @param inParam
	 * @return
	 */
	private AcAccountWalletPageOut sumCashTip(Map<String,Object> inParam){
		AcAccountWalletSV acAccountWalletSV = (AcAccountWalletSV) SysContexts.getBean("acAccountWalletSV");
		Query query = acAccountWalletSV.queryCashTipFee(inParam, true);
		Object object = query.uniqueResult();
		AcAccountWalletPageOut out = new AcAccountWalletPageOut();
		out.setAmount(object != null ? Long.valueOf(String.valueOf(object)) : 0);
		return out;
	}
	
	/**
	 * 待提现（公司下的拉包工）
	 * @param inParam
	 * @return
	 */
	public List<AcAccountWalletListOut> getAcMyWalletByUserId(Map<String,Object> inParam) throws Exception{
		AcAccountWalletSV acAccountWalletSV = (AcAccountWalletSV) SysContexts.getBean("acAccountWalletSV");
		BaseUser user  = SysContexts.getCurrentOperator();
		long userId = DataFormat.getLongKey(inParam,"userId");
		int audit = DataFormat.getIntKey(inParam,"audit");
		
		int amountState = -1;
		long accountId = DataFormat.getLongKey(inParam,"accountId");
		
		if(audit == 1){
			amountState = -1;
		}else if(audit == 2){
			amountState = -1;
		}else{
			amountState = SysStaticDataEnumYunQi.AMOUNT_STATE.NOT_PRESENT;
		}
		Query query =  null;
		AcAccountWallet ac = acAccountWalletSV.getAcAccountWallet(accountId);
		if(ac!=null && ac.getState() == SysStaticDataEnumYunQi.STS.NULLITY){
			query = acAccountWalletSV.getAcMyWalletHisByUserId(userId, user.getTenantId(),amountState,accountId);
		}else{
			query = acAccountWalletSV.getAcMyWalletByUserId(userId, user.getTenantId(),amountState,accountId);
		}
		List<AcAccountWalletListOut> listMap = new ArrayList<AcAccountWalletListOut>();
		
		List<Object[]> list = query.list();
		if(list != null && list.size() > 0){
			for (Object[] objects : list) {
				AcAccountWalletListOut out  = new AcAccountWalletListOut();
				out.setId(objects[0] != null ? Long.valueOf(objects[0].toString()) : null);
				out.setState(SysStaticDataUtil.getSysStaticDataCodeName("AMOUNT_STATE_YUNQI", String.valueOf(objects[1])));
				out.setTrackingNum(objects[2] != null ? objects[2].toString() : "");
				out.setOrderNum(objects[3] != null ? objects[3].toString() : "");
				out.setAmount(objects[4] != null ? String.valueOf(CommonUtil.getDoubleFormatLongMoney(Long.valueOf(String.valueOf(objects[4])), 2)) : "");
				listMap.add(out);
			}
		}
		return listMap;
	}
	/**
	 * web提现申请
	 * @param inParam
	 * @return
	 * @throws Exception 
	 */
	public String applyTipFee(Map<String,Object> inParam) throws Exception{
		BaseUser user = SysContexts.getCurrentOperator();
		String ids = DataFormat.getStringKey(inParam, "tipId");
		long userId = DataFormat.getLongKey(inParam, "userId");
		int audit = DataFormat.getIntKey(inParam, "audit");
		
		CmUserInfoPullTF cmUserInfoPullTF = (CmUserInfoPullTF) SysContexts.getBean("cmUserInfoPullTF");
		CmUserInfoYunQiTF cmUserInfoYunQiTF = (CmUserInfoYunQiTF) SysContexts.getBean("cmUserInfoYunQiTF");
		CmUserInfo userInfo = cmUserInfoYunQiTF.getCmUserInfo(userId);
		CmUserInfoPull pull = cmUserInfoPullTF.getCmUserInfoPull(userId);
		
		if(userInfo.getUserType() != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.VIRTUAL_USER && pull!=null&& pull.getPullState() != SysStaticDataEnumYunQi.PULL_STATE.CERTIFIED){
			throw new BusinessException("该拉包工未认证通过，请认证通过后才能提现！");
		}
			
		
		
		AcPaymentMethodTF acPaymentMethodTF = (AcPaymentMethodTF) SysContexts.getBean("acPaymentMethodTF");
		AcPaymentMethod payment = acPaymentMethodTF.getAcPaymentMethodByUserId(userId);
		if(payment == null&&userInfo.getUserType() != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.VIRTUAL_USER){
			throw new BusinessException("请绑定支付方式后，再做提现申请！");
		}
		String[] arr = ids.split(",");
		Date createDate = new Date();
		AcMyWalletSV acMyWalletSV = (AcMyWalletSV) SysContexts.getBean("acMyWalletSV");
		List<AcMyWallet> listAc = acMyWalletSV.getAcMyWalletList(arr);
		AcAccountWallet acAccountWallet = new AcAccountWallet();
		if(audit == 2){
			String remark = DataFormat.getStringKey(inParam, "remark");
			acAccountWallet.setAmountState(SysStaticDataEnumYunQi.AMOUNT_STATE.ALREADY_PRESENT);//已提现
			acAccountWallet.setAuditStatus(SysStaticDataEnumYunQi.AUDIT_STATUS_YQ.AUDIT_THROUGH);//审核通过
			acAccountWallet.setAuditId(user.getUserId());
			acAccountWallet.setAuditTime(createDate);
			acAccountWallet.setAuditRemark(remark);
			
		}else{
			acAccountWallet.setAmountState(SysStaticDataEnumYunQi.AMOUNT_STATE.CASH_WITHDRAWAL);//提现中
			acAccountWallet.setAuditStatus(SysStaticDataEnumYunQi.AUDIT_STATUS_YQ.UNAUDITED);
		}
		acAccountWallet.setAmountType(SysStaticDataEnumYunQi.ACCOUNT_TYPE.INCOME);
		acAccountWallet.setApplyId(user.getUserId());
		acAccountWallet.setApplyTime(createDate);
		acAccountWallet.setCreateTime(createDate);
		acAccountWallet.setState(SysStaticDataEnumYunQi.STS.VALID);
		acAccountWallet.setTenantId(user.getTenantId());
		acAccountWallet.setUserId(userId);
		acAccountWallet.setWriteState(SysStaticDataEnumYunQi.WRITE_STATE_YQ.WRITE_NOT);
		acAccountWallet.setPaymentType(payment == null|| payment.getPaymentType() == null ? -1 : payment.getPaymentType());
		if(payment == null){
			acAccountWallet.setPaymentCard(userInfo.getLoginAcct());
		}else if(payment.getPaymentType() == SysStaticDataEnumYunQi.RECEI_TYPE_YQ.BANK_RECEI_TYPE_YQ){
			acAccountWallet.setPaymentCard(payment.getBankCard());
		}else{
			acAccountWallet.setPaymentCard(payment.getAccountNum());
		}
		
		AcAccountWalletSV acAccountWalletSV = (AcAccountWalletSV) SysContexts.getBean("acAccountWalletSV");
		acAccountWalletSV.applyAcAccoutnWallet(acAccountWallet);
		long amount = 0;
		if (listAc != null && listAc.size() > 0) {
			for (AcMyWallet acMyWallet : listAc) {
				acMyWallet.setApplyTime(createDate);
				if(audit == 2){
					acMyWallet.setAmountState(SysStaticDataEnumYunQi.AMOUNT_STATE.ALREADY_PRESENT);
					acMyWallet.setShowTime(createDate);
				}else{
					acMyWallet.setAmountState(SysStaticDataEnumYunQi.AMOUNT_STATE.CASH_WITHDRAWAL);
				}
				acMyWallet.setApplyTime(createDate);
				acMyWallet.setState(SysStaticDataEnumYunQi.ACCOUNT_TYPE.INCOME);
				amount = acMyWallet.getAmount()+amount;
				acMyWallet.setAccountId(acAccountWallet.getId());
				acMyWalletSV.doSaveOrUpdate(acMyWallet);
			}
		}
		String num = DateUtil.formatDateByFormat(createDate, "yyyyMMdd");
		acAccountWallet.setAccountNum(num+acAccountWallet.getId());
		acAccountWallet.setAmount(amount);
		if(audit == 2){
			SmsYQUtil.extractMoneySendSms(acAccountWallet.getTenantId(), DateUtil.formatDate(acAccountWallet.getCreateTime(), "yyyy-MM-dd HH:mm:ss"), acAccountWallet.getPaymentCard(), CommonUtil.parseDouble(acAccountWallet.getAmount()), SysStaticDataEnumYunQi.OBJ_TYPE.PUBLIC_MESSAGR, EnumConstsYQ.SmsType.NOTICE_TYPE, userInfo.getLoginAcct());
		}
		
		
		return "Y";
	}
	/**
	 * 审核
	 * @param inParam
	 * @return
	 * @throws Exception 
	 */
	public String auditTip(Map<String,Object> inParam) throws Exception{
		Date create = new Date();
		String remark = DataFormat.getStringKey(inParam, "remark");
		long id = DataFormat.getLongKey(inParam,"id");
		if(id < 0){
			throw new BusinessException("请传流水编号！");
		}
		AcMyWalletSV acMyWalletSV = (AcMyWalletSV) SysContexts.getBean("acMyWalletSV");
		AcAccountWalletSV acAccountWalletSV = (AcAccountWalletSV) SysContexts.getBean("acAccountWalletSV");
		AcAccountWallet acAccountWallet = acAccountWalletSV.getAcAccountWallet(id);
		
		if(acAccountWallet == null){
			throw new BusinessException("无该流水信息！");
		}else if(acAccountWallet.getState() == SysStaticDataEnumYunQi.STS.NULLITY){
			throw new BusinessException("该流水信息已经失效！");
		}else if(acAccountWallet.getAuditStatus() == SysStaticDataEnumYunQi.AUDIT_STATUS_YQ.AUDIT_NOT){
			throw new BusinessException("审核不通过后，不能再审批了！");
		}else if(acAccountWallet.getAuditStatus() == SysStaticDataEnumYunQi.AUDIT_STATUS_YQ.AUDIT_THROUGH){
			throw new BusinessException("已经审核通过了，不能再审核了！");
		}
		//发送短信！
		CmUserInfoYunQiTF userInfoSV = (CmUserInfoYunQiTF) SysContexts.getBean("cmUserInfoYunQiTF");
		CmUserInfo userInfo = userInfoSV.getCmUserInfo(acAccountWallet.getUserId());
		
		int isPass = DataFormat.getIntKey(inParam,"isPass");
		AcPaymentMethodTF acPaymentMethodTF = (AcPaymentMethodTF) SysContexts.getBean("acPaymentMethodTF");
		AcPaymentMethod payment = acPaymentMethodTF.getAcPaymentMethodByUserId(acAccountWallet.getUserId());
		if(userInfo.getUserType() != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.VIRTUAL_USER && payment == null && isPass == 1){
			throw new BusinessException("请绑定支付方式后，再做审批！");
		}
		long userId = SysContexts.getCurrentOperator().getUserId();
		
		if(isPass < 0){
			throw new BusinessException("请确认是审批是否通过！");
		}
		List<AcMyWallet> myList = acMyWalletSV.getAcMyWalletList(id);
		if(isPass == 1){
			acAccountWallet.setAmountState(SysStaticDataEnumYunQi.AMOUNT_STATE.ALREADY_PRESENT);
			acAccountWallet.setAuditId(userId);
			acAccountWallet.setAuditStatus(SysStaticDataEnumYunQi.AUDIT_STATUS_YQ.AUDIT_THROUGH);
			acAccountWallet.setAuditTime(create);
			acAccountWallet.setAuditRemark(remark);
			acAccountWallet.setShowTime(create);
			acAccountWalletSV.applyAcAccoutnWallet(acAccountWallet);
			if (myList!= null && myList.size() > 0) {
				for (AcMyWallet acMyWallet : myList) {
					acMyWallet.setAmountState(SysStaticDataEnumYunQi.AMOUNT_STATE.ALREADY_PRESENT);
					acMyWallet.setShowTime(create);
					acMyWalletSV.doSaveOrUpdate(acMyWallet);
				}
			}
		}else{
			if(StringUtils.isEmpty(remark)){
				throw new BusinessException("请输入审批备注！");
			}
			acAccountWallet.setState(SysStaticDataEnumYunQi.STS.NULLITY);
			acAccountWallet.setAuditId(userId);
			acAccountWallet.setAuditStatus(SysStaticDataEnumYunQi.AUDIT_STATUS_YQ.AUDIT_NOT);
			acAccountWallet.setAuditTime(create);
			acAccountWallet.setAuditRemark(remark);
			if (myList!= null && myList.size() > 0) {
				for (AcMyWallet acMyWallet : myList) {
					AcMyWalletHis his = new AcMyWalletHis();
					BeanUtil.copyProperties(acMyWallet, his);
					his.setHisWalletId(acMyWallet.getId());
					his.setId(null);
					acMyWalletSV.doSaveHis(his);
					acMyWallet.setAmountState(SysStaticDataEnumYunQi.AMOUNT_STATE.NOT_PRESENT);
					acMyWallet.setShowTime(null);
					acMyWallet.setAccountId(null);
					acMyWallet.setApplyTime(null);
					acMyWalletSV.doSaveOrUpdate(acMyWallet);
				}
			}
		}
		Session session = SysContexts.getEntityManager();
		if(isPass == 1){
			if(isRegisterUser(session, userInfo.getLoginAcct())){
				SmsYQUtil.extractMoneySendSms(acAccountWallet.getTenantId(), DateUtil.formatDate(acAccountWallet.getCreateTime(), "yyyy-MM-dd HH:mm:ss"), acAccountWallet.getPaymentCard(), CommonUtil.parseDouble(acAccountWallet.getAmount()), SysStaticDataEnumYunQi.OBJ_TYPE.PUBLIC_MESSAGR, EnumConstsYQ.SmsType.MOPBILE_TYPE, userInfo.getLoginAcct());
			}else {
				SmsYQUtil.extractMoneySendSms(acAccountWallet.getTenantId(), DateUtil.formatDate(acAccountWallet.getCreateTime(), "yyyy-MM-dd HH:mm:ss"), acAccountWallet.getPaymentCard(), CommonUtil.parseDouble(acAccountWallet.getAmount()), SysStaticDataEnumYunQi.OBJ_TYPE.PUBLIC_MESSAGR, EnumConstsYQ.SmsType.NOTICE_TYPE, userInfo.getLoginAcct());
			}

		}else{
           if(isRegisterUser(session, userInfo.getLoginAcct())){	
        	   SmsYQUtil.notExtractMoneySendSms(acAccountWallet.getTenantId(), DateUtil.formatDate(acAccountWallet.getCreateTime(), "yyyy-MM-dd HH:mm:ss"), acAccountWallet.getPaymentCard(), CommonUtil.parseDouble(acAccountWallet.getAmount()), SysStaticDataEnumYunQi.OBJ_TYPE.PUBLIC_MESSAGR, EnumConstsYQ.SmsType.MOPBILE_TYPE, userInfo.getLoginAcct(), acAccountWallet.getAuditRemark());
			}else {
				SmsYQUtil.notExtractMoneySendSms(acAccountWallet.getTenantId(), DateUtil.formatDate(acAccountWallet.getCreateTime(), "yyyy-MM-dd HH:mm:ss"), acAccountWallet.getPaymentCard(), CommonUtil.parseDouble(acAccountWallet.getAmount()), SysStaticDataEnumYunQi.OBJ_TYPE.PUBLIC_MESSAGR, EnumConstsYQ.SmsType.NOTICE_TYPE, userInfo.getLoginAcct(), acAccountWallet.getAuditRemark());
			}
			
		}
		
		return "Y";
	}
	
	private boolean isRegisterUser(Session session, String loginAcct) {
		// TODO Auto-generated method stub
		Query query = session.createSQLQuery("SELECT COUNT(1) FROM CM_USER_INFO WHERE LOGIN_ACCT=:loginAcct and user_type != 11 ");
		query.setParameter("loginAcct", loginAcct);
		 BigInteger count = (BigInteger) query.uniqueResult();
		if(count.intValue()>0){
			return true;
		}
		return false;
	}
	/**
	 * 核销
	 */
	public String writeTip(Map<String,Object> inParam){
		String accountId = DataFormat.getStringKey(inParam, "accountId");
		AcAccountWalletSV acAccountWalletSV = (AcAccountWalletSV) SysContexts.getBean("acAccountWalletSV");
		String[] ids = accountId.split(",");
		Long[] lIds = CommonUtil.stringToLong(ids);
		List<AcAccountWallet> acList = acAccountWalletSV.getAcAccountWalletList(lIds);
		if(acList!=null && acList.size() > 0){
			for (AcAccountWallet ac : acList) {
				if(ac.getAuditStatus()!=SysStaticDataEnumYunQi.AUDIT_STATUS_YQ.AUDIT_THROUGH){
					throw new BusinessException("该流水号【"+ac.getAccountNum()+"】未审核或审核不通过的费用，不能核销！");
				}
				long userId = SysContexts.getCurrentOperator().getUserId();
				int write = DataFormat.getIntKey(inParam, "write");
				Date create = new Date();
				if(write == 1){
					if(ac.getWriteState() == SysStaticDataEnumYunQi.WRITE_STATE_YQ.WRITE_YES){
						throw new BusinessException("该流水号【"+ac.getAccountNum()+"】已经核销了，无需再核销！");
					}
					ac.setWriteId(userId);
					ac.setWriteState(SysStaticDataEnumYunQi.WRITE_STATE_YQ.WRITE_YES);
					ac.setWriteTime(create);
				}else{
					if(ac.getWriteState() == SysStaticDataEnumYunQi.WRITE_STATE_YQ.WRITE_NOT){
						throw new BusinessException("该流水号【"+ac.getAccountNum()+"】未核销，无需反核销！");
					}
					ac.setWriteId(null);
					ac.setWriteState(SysStaticDataEnumYunQi.WRITE_STATE_YQ.WRITE_NOT);
					ac.setWriteTime(null);
				}
				acAccountWalletSV.applyAcAccoutnWallet(ac);
			}
		}
		
		return "Y";
	}
}
