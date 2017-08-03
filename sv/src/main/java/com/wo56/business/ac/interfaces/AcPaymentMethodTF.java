package com.wo56.business.ac.interfaces;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.ac.impl.AcPaymentMethodSV;
import com.wo56.business.ac.vo.AcPaymentMethod;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;

public class AcPaymentMethodTF {

	/**
	 * 新增/修改收款方式
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doSaveAcPaymentMethod(Map<String, String> inParam) throws Exception {
		Session session = SysContexts.getEntityManager();
		BaseUser base = SysContexts.getCurrentOperator();
		long userId = base.getUserId();
		AcPaymentMethodSV acPaymentMethodSV = (AcPaymentMethodSV) SysContexts.getBean("acPaymentMethodSV");
		AcPaymentMethod acPaymentMethod = new AcPaymentMethod();
		if (userId < 0) {
			throw new BusinessException("用户Id不能为空");
		}
		int paymentType = DataFormat.getIntKey(inParam, "paymentType");
		if (paymentType < 0) {
			throw new BusinessException("收款类型不能为空");
		}
		String accountNum = DataFormat.getStringKey(inParam, "accountNum");
		String accountName = DataFormat.getStringKey(inParam, "accountName");
		Date date = new Date();
		// 微信
		if (paymentType == SysStaticDataEnumYunQi.RECEI_TYPE_YQ.WX_RECEI_TYPE_YQ) {
			if (StringUtils.isEmpty(accountNum)) {
				throw new BusinessException("微信账号不能为空");
			}
			if (StringUtils.isEmpty(accountName)) {
				throw new BusinessException("账号真实姓名不能为空");
			}
			Criteria criteria = session.createCriteria(AcPaymentMethod.class);
			criteria.add(Restrictions.eq("userId", userId));
			AcPaymentMethod acPaymentMethodRes = (AcPaymentMethod) criteria.uniqueResult();
			if (acPaymentMethodRes != null) {
				if (acPaymentMethodRes.getAccountNum()!=null&&acPaymentMethodRes.getAccountNum().equals(accountNum)&&acPaymentMethodRes.getPaymentType()==paymentType) {
					throw new BusinessException("该微信账户已存在!");
				}
				//修改账户认证状态改为审核中
				Criteria puCriteria = session.createCriteria(CmUserInfoPull.class);
				puCriteria.add(Restrictions.eq("userId", userId));
				CmUserInfoPull cmUserInfoPull=(CmUserInfoPull)puCriteria.uniqueResult();
				cmUserInfoPull.setPullState(SysStaticDataEnumYunQi.PULL_STATE.AUDIT);
				session.update(cmUserInfoPull);
				//更新数据
				acPaymentMethodRes.setAccountNum(accountNum);
				acPaymentMethodRes.setAccountName(accountName);
				acPaymentMethodRes.setBankCard(null);
				acPaymentMethodRes.setBankHolder(null);
				acPaymentMethodRes.setBankName(null);
				acPaymentMethodRes.setBill(null);
				acPaymentMethodRes.setCity(null);
				acPaymentMethodRes.setCityName(null);
				acPaymentMethodRes.setIdCard(null);
				acPaymentMethodRes.setPaymentType(paymentType);
				acPaymentMethodRes.setProvince(null);
				acPaymentMethodRes.setProvinceName(null);
				acPaymentMethodRes.setOpDate(date);
				acPaymentMethodRes.setBankCode(null);
				session.update(acPaymentMethodRes);
			} else {
				//修改账户认证状态改为审核中
				Criteria puCriteria = session.createCriteria(CmUserInfoPull.class);
				puCriteria.add(Restrictions.eq("userId", userId));
				CmUserInfoPull cmUserInfoPull=(CmUserInfoPull)puCriteria.uniqueResult();
				cmUserInfoPull.setPullState(SysStaticDataEnumYunQi.PULL_STATE.AUDIT);
				session.update(cmUserInfoPull);
				acPaymentMethod.setAccountNum(accountNum);
				acPaymentMethod.setAccountName(accountName);
				acPaymentMethod.setPaymentType(paymentType);
				acPaymentMethod.setCreateTime(date);
				acPaymentMethod.setUserId(userId);
				acPaymentMethod.setState(SysStaticDataEnum.STS.VALID);
				acPaymentMethodSV.doSaveAcPaymentMethod(acPaymentMethod);
			}
		}
		// 支付宝
		if (paymentType == SysStaticDataEnumYunQi.RECEI_TYPE_YQ.ZFB_RECEI_TYPE_YQ) {
			Criteria criteria = session.createCriteria(AcPaymentMethod.class);
			criteria.add(Restrictions.eq("userId", userId));
			if (StringUtils.isEmpty(accountNum)) {
				throw new BusinessException("支付宝账号不能为空");
			}
			if (StringUtils.isEmpty(accountName)) {
				throw new BusinessException("账号真实姓名不能为空");
			}
			AcPaymentMethod acPaymentMethodRes = (AcPaymentMethod) criteria.uniqueResult();
			if (acPaymentMethodRes != null) {
				if (acPaymentMethodRes.getAccountNum()!=null&&acPaymentMethodRes.getAccountNum().equals(accountNum)&&acPaymentMethodRes.getPaymentType()==paymentType) {
					throw new BusinessException("该支付宝账户已存在!");
				}
				//修改账户认证状态改为审核中
				Criteria puCriteria = session.createCriteria(CmUserInfoPull.class);
				puCriteria.add(Restrictions.eq("userId", userId));
				CmUserInfoPull cmUserInfoPull=(CmUserInfoPull)puCriteria.uniqueResult();
				cmUserInfoPull.setPullState(SysStaticDataEnumYunQi.PULL_STATE.AUDIT);
				session.update(cmUserInfoPull);
				acPaymentMethodRes.setAccountNum(accountNum);
				acPaymentMethodRes.setAccountName(accountName);
				acPaymentMethodRes.setBankCard(null);
				acPaymentMethodRes.setBankHolder(null);
				acPaymentMethodRes.setBankName(null);
				acPaymentMethodRes.setBill(null);
				acPaymentMethodRes.setCity(null);
				acPaymentMethodRes.setCityName(null);
				acPaymentMethodRes.setIdCard(null);
				acPaymentMethodRes.setPaymentType(paymentType);
				acPaymentMethodRes.setProvince(null);
				acPaymentMethodRes.setProvinceName(null);
				acPaymentMethodRes.setBankCode(null);
				acPaymentMethodRes.setOpDate(date);
				session.update(acPaymentMethodRes);
			} else {
				//修改账户认证状态改为审核中
				Criteria puCriteria = session.createCriteria(CmUserInfoPull.class);
				puCriteria.add(Restrictions.eq("userId", userId));
				CmUserInfoPull cmUserInfoPull=(CmUserInfoPull)puCriteria.uniqueResult();
				cmUserInfoPull.setPullState(SysStaticDataEnumYunQi.PULL_STATE.AUDIT);
				session.update(cmUserInfoPull);
				acPaymentMethod.setAccountNum(accountNum);
				acPaymentMethod.setAccountName(accountName);
				acPaymentMethod.setPaymentType(paymentType);
				acPaymentMethod.setCreateTime(date);
				acPaymentMethod.setUserId(userId);
				acPaymentMethod.setState(SysStaticDataEnum.STS.VALID);
				acPaymentMethodSV.doSaveAcPaymentMethod(acPaymentMethod);
			}
		}
		// 银行卡
		if (paymentType == SysStaticDataEnumYunQi.RECEI_TYPE_YQ.BANK_RECEI_TYPE_YQ) {
			String bankCard = DataFormat.getStringKey(inParam, "bankCard");
			if (StringUtils.isEmpty(bankCard)) {
				throw new BusinessException("银行卡号不能为空");
			}
			String bankHolder = DataFormat.getStringKey(inParam, "bankHolder");
			if (StringUtils.isEmpty(bankHolder)) {
				throw new BusinessException("持卡人不能为空");
			}
			String bill = DataFormat.getStringKey(inParam, "bill");
			if (StringUtils.isEmpty(bill)) {
				throw new BusinessException("手机号不能为空");
			}
			long city = DataFormat.getLongKey(inParam, "cityId");
			if (city < 0) {
				throw new BusinessException("开户城市Id不能为空");
			}
			String idCard = DataFormat.getStringKey(inParam, "idCard");
			if (StringUtils.isEmpty(idCard)) {
				throw new BusinessException("身份证号码不能为空");
			}
			long province = DataFormat.getLongKey(inParam, "provinceId");
			if (province < 0) {
				throw new BusinessException("开户省份Id不能为空");
			}
			String bankCode=DataFormat.getStringKey(inParam, "bankCode");
			if(StringUtils.isEmpty(bankCode)){
				throw new BusinessException("银行编码不能为空");
			}
			Criteria criteria = session.createCriteria(AcPaymentMethod.class);
			criteria.add(Restrictions.eq("userId", userId));
			AcPaymentMethod acPaymentMethodRes = (AcPaymentMethod) criteria.uniqueResult();
			if (acPaymentMethodRes != null) {
				if (acPaymentMethodRes.getBankCard()!=null&&acPaymentMethodRes.getBankCard().equals(bankCard)) {
					throw new BusinessException("该银行卡账户已存在!");
				} else {
					//修改账户认证状态改为审核中
					Criteria puCriteria = session.createCriteria(CmUserInfoPull.class);
					puCriteria.add(Restrictions.eq("userId", userId));
					CmUserInfoPull cmUserInfoPull=(CmUserInfoPull)puCriteria.uniqueResult();
					cmUserInfoPull.setPullState(SysStaticDataEnumYunQi.PULL_STATE.AUDIT);
					session.update(cmUserInfoPull);
					acPaymentMethodRes.setBankCard(bankCard);
					acPaymentMethodRes.setBankHolder(bankHolder);
					acPaymentMethodRes.setBankCode(bankCode);
					String bankName=SysStaticDataUtil.getSysStaticDataCodeName("BANK_TYPE_YUNQI", bankCode);
					if(StringUtils.isEmpty(bankName)){
						throw new BusinessException("该银行不存在!");
					}
					acPaymentMethodRes.setBankName(bankName);
					acPaymentMethodRes.setBill(bill);
					acPaymentMethodRes.setCity(city);
					String cityName = SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(city)).getName();
					acPaymentMethodRes.setCityName(cityName);
					acPaymentMethodRes.setIdCard(idCard);
					acPaymentMethodRes.setProvince(province);
					String provinceName = SysStaticDataUtil
							.getProvinceDataList("SYS_PROVINCE", String.valueOf(province)).getName();
					acPaymentMethodRes.setProvinceName(provinceName);
					acPaymentMethodRes.setPaymentType(paymentType);
					acPaymentMethodRes.setAccountName(null);
					acPaymentMethodRes.setAccountNum(null);
					acPaymentMethodRes.setOpDate(date);
					session.update(acPaymentMethodRes);
				}
			} else {
				//修改账户认证状态改为审核中
				Criteria puCriteria = session.createCriteria(CmUserInfoPull.class);
				puCriteria.add(Restrictions.eq("userId", userId));
				CmUserInfoPull cmUserInfoPull=(CmUserInfoPull)puCriteria.uniqueResult();
				cmUserInfoPull.setPullState(SysStaticDataEnumYunQi.PULL_STATE.AUDIT);
				session.update(cmUserInfoPull);
				acPaymentMethod.setBankCard(bankCard);
				acPaymentMethod.setBankHolder(bankHolder);
				acPaymentMethod.setBankCode(bankCode);
				String bankName=SysStaticDataUtil.getSysStaticDataCodeName("BANK_TYPE_YUNQI", bankCode);
				if(StringUtils.isEmpty(bankName)){
					throw new BusinessException("该银行不存在!");
				}
				acPaymentMethod.setBankName(bankName);
				acPaymentMethod.setBill(bill);
				acPaymentMethod.setCity(city);
				String cityName = SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(city)).getName();
				acPaymentMethod.setCityName(cityName);
				acPaymentMethod.setIdCard(idCard);
				acPaymentMethod.setProvince(province);
				String provinceName = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(province))
						.getName();
				acPaymentMethod.setProvinceName(provinceName);
				acPaymentMethod.setPaymentType(paymentType);
				acPaymentMethod.setCreateTime(date);
				acPaymentMethod.setUserId(userId);
				acPaymentMethod.setState(SysStaticDataEnum.STS.VALID);
				acPaymentMethodSV.doSaveAcPaymentMethod(acPaymentMethod);
			}

		}
		Map map = new HashMap();
		map.put("info", "Y");
		return map;
	}
	/**
	 * 通过userId查询付款方式
	 * @param userId
	 * @return
	 */
	public AcPaymentMethod getAcPaymentMethodByUserId(long userId){
		AcPaymentMethodSV acPaymentMethodSV = (AcPaymentMethodSV) SysContexts.getBean("acPaymentMethodSV");
		Criteria ca = acPaymentMethodSV.getAcPaymentMethodByUserId(userId);
		List<AcPaymentMethod> list = ca.list();
		if(list != null && list.size() == 1){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * @param oldPayment 存在该账户
	 * @param user
	 * @param paymentType
	 * @param bankName
	 * @param bankCode
	 * @param bankICard
	 * @param bankHolder
	 * @return
	 */
	public String doSavePaymentByPull(AcPaymentMethod oldPayment,CmUserInfo user,int paymentType,String bankName,String bankCode,String bankICard,String bankHolder){
		if(paymentType < 0){
			throw new BusinessException("请选择支付类型！");
		}
		AcPaymentMethod payment = new AcPaymentMethod();
		if(oldPayment != null){
			payment = oldPayment;
		}else{
			payment.setCreateTime(new Date());
		}
		
		//银行卡类型
		if(paymentType == SysStaticDataEnumYunQi.RECEI_TYPE_YQ.BANK_RECEI_TYPE_YQ){
			if(StringUtils.isEmpty(bankHolder)){
				throw new BusinessException("持卡人/帐号名，不能为空！");
			}
			if(StringUtils.isEmpty(bankICard)){
				throw new BusinessException("卡号/帐号，不能为空！");
			}
			if(StringUtils.isEmpty(bankCode)){
				throw new BusinessException("归属银行，不能为空！");
			}
			payment.setBankCard(bankICard);
			payment.setBankCode(bankCode);
			payment.setBankHolder(bankHolder);
			payment.setBankName(bankName);
			payment.setBill(user.getLoginAcct());
			payment.setPaymentType(paymentType);
			payment.setState(SysStaticDataEnumYunQi.STS.VALID);
			payment.setUserId(user.getUserId());
			payment.setAccountName(null);
			payment.setAccountNum(null);
			payment.setOpDate(new Date());
		}else{//微信，支付宝
			if(StringUtils.isEmpty(bankHolder)){
				throw new BusinessException("持卡人/帐号名，不能为空！");
			}
			if(StringUtils.isEmpty(bankICard)){
				throw new BusinessException("卡号/帐号，不能为空！");
			}
			payment.setAccountName(bankHolder);
			payment.setAccountNum(bankICard);
			payment.setBankCard(null);
			payment.setBankCode(null);
			payment.setBankHolder(null);
			payment.setBill(user.getLoginAcct());
			payment.setCity(null);
			payment.setCityName(null);
			payment.setIdCard(null);
			payment.setOpDate(new Date());
			payment.setPaymentType(paymentType);
			payment.setProvince(null);
			payment.setProvinceName(null);
			payment.setState(SysStaticDataEnumYunQi.STS.VALID);
			payment.setUserId(user.getUserId());
		}
		
		AcPaymentMethodSV acPaymentMethodSV = (AcPaymentMethodSV) SysContexts.getBean("acPaymentMethodSV");
		acPaymentMethodSV.doSaveAcPaymentMethod(payment);
		return "Y";
	}
}
