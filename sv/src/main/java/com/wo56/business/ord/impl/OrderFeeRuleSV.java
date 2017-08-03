package com.wo56.business.ord.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.wo56.business.cm.impl.CmUserOrgRelSV;
import com.wo56.business.cm.vo.CmUserOrgRel;
import com.wo56.business.ord.vo.OrderFee;
import com.wo56.business.ord.vo.OrderFeeRule;
import com.wo56.business.ord.vo.OrderTipRuleExt;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.route.vo.RouteIntroduce;
import com.wo56.business.route.vo.RouteTowards;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class OrderFeeRuleSV {
	
	public Criteria getOrderFeeRule(long tenantId,long arrivedOrgId,int ruleType){
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		if(user.getOrgId() == null){
			throw new BusinessException("无该开单员公司网点信息！");
		}
		
		
		
		Criteria ca = session.createCriteria(OrderFeeRule.class);
		ca.add(Restrictions.eq("tenantId", tenantId));
		ca.add(Restrictions.eq("ruleType", ruleType));
		ca.add(Restrictions.eq("beginOrgId", user.getOrgId()));
		ca.add(Restrictions.eq("endOrgId", arrivedOrgId));
		return ca;
	}
	
	/**
	 * 运费算费
	 * @param tenantId
	 * @param weight
	 * @param province
	 * @param city
	 * @param volume
	 * @return
	 */
	public long freightCount(String weight,long arrivedOrgId,String volume){
		if(StringUtils.isEmpty(weight) && StringUtils.isEmpty(volume)){
			throw new BusinessException("请输入重量或体积！");
		}
		List<OrderFeeRule> list = getOrderFeeRule(getUserTenant(SysContexts.getCurrentOperator().getUserId()), arrivedOrgId, SysStaticDataEnumYunQi.RULE_TYPE.freight).list();
		OrderFeeRule rule = new OrderFeeRule();
		if(list.size() == 1){
			rule = list.get(0);
		}else{
			return 0;
		}
		double dWeight = CommonUtil.getDoubleByString(weight);
		double dVolume = CommonUtil.getDoubleByString(volume);
		double dMinWeightFee = CommonUtil.getDoubleFormatLongMoney(rule.getMinWeightFee(), 2);
		double dMinVolumeFee = CommonUtil.getDoubleFormatLongMoney(rule.getMinVolumeFee(), 2);
		long minFee = rule.getMinFee();
		double dMinFee = CommonUtil.getDoubleFormatLongMoney(minFee, 2);
		
		double ferightWeight = dWeight * dMinWeightFee; //最低重量计算费用
		double ferightVolume = dVolume * dMinVolumeFee;//最低体积费用
		
		long ferightFee = 0;
		if(ferightWeight <= 0){
			ferightFee =  CommonUtil.getStringByLong(String.valueOf(ferightVolume));
		}
		if(ferightVolume <= 0){
			ferightFee =  CommonUtil.getStringByLong(String.valueOf(ferightWeight));
		}
		if(ferightWeight < ferightVolume && ferightWeight > 0 && ferightWeight >= dMinFee){
			ferightFee = CommonUtil.getStringByLong(String.valueOf(ferightWeight));
		}else if(ferightWeight > ferightVolume && ferightVolume > 0 && ferightVolume >= dMinFee){
			ferightFee =  CommonUtil.getStringByLong(String.valueOf(ferightVolume));
		}else if (ferightWeight > dMinFee) {
			ferightFee = CommonUtil.getStringByLong(String.valueOf(ferightWeight));
		}else if (ferightVolume > dMinFee) {
			ferightFee = CommonUtil.getStringByLong(String.valueOf(ferightVolume));
		}else{
			ferightFee = CommonUtil.getStringByLong(String.valueOf(ferightWeight));
		}
		if (ferightFee < minFee) {
			return minFee;
		}
		return ferightFee;
	}
	/**
	 * 小费算法规则
	 * @param tenantId
	 * @param weight
	 * @param province
	 * @param city
	 * @param volume
	 * @param freight
	 * @return
	 */
	public long tipCount(String weight,long arrivedOrgId,long freight){
		List<OrderFeeRule> list = getOrderFeeRule(getUserTenant(SysContexts.getCurrentOperator().getUserId()), arrivedOrgId, SysStaticDataEnumYunQi.RULE_TYPE.tip).list();
		OrderFeeRule rule = new OrderFeeRule();
		long tip = 0;
		if(list.size() == 1){
			rule = list.get(0);
		}else{
			return 0;
		}
		double dWeight = 0;
		if(StringUtils.isNotEmpty(weight)){
			dWeight = CommonUtil.getDoubleByString(weight);//重量
		}
		
		//1.按首重的情况(t=s+2*(w-5))
		int tipType = rule.getTipType();
		if(tipType == SysStaticDataEnumYunQi.TIP_TYPE.WEIGHT_INCREMENT){
            double dFirst = CommonUtil.getDoubleByString(rule.getFirst());//首重
            double addWeight = CommonUtil.getDoubleFormatLongMoney(rule.getAddWeight(), 2);//续重
            double staFee = CommonUtil.getDoubleFormatLongMoney(rule.getStaFee(), 2);//起价
            double dTip = 0D;
            double dTopFee = CommonUtil.getDoubleFormatLongMoney(rule.getTopFee(), 2);//封顶价
			//当总重量大于首重时
			if(dWeight > dFirst){
				dTip = staFee + addWeight * (dWeight - dFirst);
			}else if(dWeight <= dFirst){
				dTip = staFee;
			}
			//当费用大于封顶价时，取封顶价
			if(dTip > dTopFee){
				dTip = dTopFee;
			}
			tip = CommonUtil.getStringByLong(String.valueOf(dTip));
			
		}else if(tipType == SysStaticDataEnumYunQi.TIP_TYPE.WEIGHT_RANGE){//按重量区间取值
			List<OrderTipRuleExt> tipList = getOrderTipRuleExt(rule.getId());
			for(OrderTipRuleExt temp : tipList){
		        double dMinWieght = CommonUtil.getDoubleByString(temp.getMinWieght());
		        double dMaxWieghit = CommonUtil.getDoubleByString(temp.getMaxWieght());
				if(dMinWieght > dWeight || dMaxWieghit < dWeight){
					tip = temp.getFee();
				}
			}
			
		}else if(tipType == SysStaticDataEnumYunQi.TIP_TYPE.FREIGHT_RATE){//按运费占比
			if (freight < 0) {
				throw new BusinessException("请填写运费！");
			}
			String str = rule.getPercentage();
			BigDecimal bg = new BigDecimal(str);  
            double type = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
			double dFreight = CommonUtil.getDoubleFormatLongMoney(freight, 2);
			double dTip = dFreight * type / 100;
			tip = CommonUtil.getStringByLong(String.valueOf(dTip));
			if(tip < rule.getStaFee()){
				tip =  rule.getStaFee();
			}else if(tip > rule.getTopFee()){
				tip =  rule.getTopFee();
			}
		}
		return tip;
	}
	/**
	 * 保费
	 * @param tenantId
	 * @param province
	 * @param city
	 * @param number
	 * @return
	 */
	public long premiumCount(long arrivedOrgId,int number){
		List<OrderFeeRule> list = getOrderFeeRule(getUserTenant(SysContexts.getCurrentOperator().getUserId()), arrivedOrgId, SysStaticDataEnumYunQi.RULE_TYPE.premium).list();
		OrderFeeRule rule = new OrderFeeRule();
		if(list.size() == 1){
			rule = list.get(0);
		}else{
			return 0;
		}
		double dPremium = 0;
		double dNumberFee = CommonUtil.getDoubleFormatLongMoney(rule.getNumberFee(), 2);
		double dNumber = number;
		dPremium = dNumber * dNumberFee;
		return CommonUtil.getStringByLong(String.valueOf(dPremium));
	}
	/**
	 * 获取小费规则
	 * @param id
	 * @return
	 */
	public List<OrderTipRuleExt> getOrderTipRuleExt(long id){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrderTipRuleExt.class);
		ca.add(Restrictions.eq("tipId", id));
		List<OrderTipRuleExt> list = ca.list();
		return list;
	}
	/**
	 * 获取用户的租户
	 * @param userId
	 * @return
	 */
	public long getUserTenant(long userId){
		CmUserOrgRelSV cmUserOrgRelSV = (CmUserOrgRelSV) SysContexts.getBean("cmUserOrgRelSV");
		long tenantId = 0;
		List<CmUserOrgRel> relList = cmUserOrgRelSV.getUserRel(userId);
		if(relList != null && relList.size() > 0){
			tenantId = relList.get(0).getTenantId();
		}else{
			throw new BusinessException("无该用户的专线信息！");
		}
		return tenantId;
	}
	/**
	 * 费用规则
	 * @param ruleType
	 * @return
	 */
	public Query getOrderFeeRule(int ruleType,long tenantId,String ruleName,long beginOrgId,long endOrgId){
		Session session = SysContexts.getEntityManager(true);
		

		StringBuffer sb = new StringBuffer(" select f.ID,f.RULE_NAME,f.TENANT_ID,r.BEGIN_OWNER_REGION,r.END_OWNER_REGION,f.TIP_TYPE,f.IS_CURRENCY,f.CREATE_TIME,r.TOWARDS_ID,f.MAX_WEIGHT_FEE,f.MIN_WEIGHT_FEE,f.MAX_VOLUME_FEE,f.MIN_VOLUME_FEE,f.MIN_FEE,f.NUMBER_FEE,f.BEGIN_ORG_ID,f.END_ORG_ID ");
		sb.append(" from order_fee_rule f INNER JOIN route_towards r ON f.BEGIN_ORG_ID = r.BEGIN_ORG_ID AND f.END_ORG_ID = r.END_ORG_ID AND rule_Type = :ruleType ");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ruleType", ruleType);
		if(tenantId > 0){
			sb.append(" AND f.TENANT_ID = :tenantId ");
			map.put("tenantId", tenantId);
		}
		if(StringUtils.isNotEmpty(ruleName)){
			sb.append(" AND f.rule_Name like :ruleName ");
			map.put("ruleName", "%"+ruleName+"%");
		}
		if(beginOrgId > 0){
			sb.append(" AND f.begin_Org_Id = :beginOrgId ");
			map.put("beginOrgId", beginOrgId);
		}
		if(endOrgId > 0){
			sb.append(" AND f.end_Org_Id = :endOrgId ");
			map.put("endOrgId", endOrgId);
		}
		sb.append(" order by f.id desc ");
		Query query = session.createSQLQuery(sb.toString());
		return query.setProperties(map);
	}
	/**
	 * 保存与修改
	 * @param rule
	 * @return
	 */
	public String doSaveOrUpdate(OrderFeeRule rule){
		Session session = SysContexts.getEntityManager(false);
		session.saveOrUpdate(rule);
		return "Y";
	}
	
	public OrderFeeRule getOrderFeeRule(long id){
		Session session = SysContexts.getEntityManager(true);
		return (OrderFeeRule) session.get(OrderFeeRule.class, id);
	}
	
	/**
	 * 小费扩展表
	 */
	public String doSaveOrUpdateTip(OrderTipRuleExt tip){
		Session session = SysContexts.getEntityManager();
		session.saveOrUpdate(tip);
		return "Y";
	}
	
	/**
	 * 始发地与目的地查询规则
	 */
	public OrderFeeRule getOrderFeeRuleLine(long beginOrgId,long endOrgId,long tenantId,int ruleType){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrderFeeRule.class);
		ca.add(Restrictions.eq("beginOrgId", beginOrgId));
		ca.add(Restrictions.eq("endOrgId", endOrgId));
		ca.add(Restrictions.eq("tenantId", tenantId));
		ca.add(Restrictions.eq("ruleType", ruleType));
		List<OrderFeeRule> list = ca.list();
		if(list != null && list.size() == 1){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 查询区间费用
	 * @param ruleId
	 * @return
	 */
	public List<OrderTipRuleExt> getOrderTipRuleExtList(long ruleId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrderTipRuleExt.class);
		return ca.add(Restrictions.eq("tipId", ruleId)).list();
	}
	
	/**
	 * 查询通用模板
	 */
	public OrderFeeRule isCurrencyRule(long tenantId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(OrderFeeRule.class);
		List<OrderFeeRule> list = ca.add(Restrictions.eq("isCurrency", SysStaticDataEnumYunQi.IS_CURRENCY.IS_CURRENCY)).list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 删除
	 */
	@SuppressWarnings("unchecked")
	public String delOrderFeeRule(long id){
		Session session = SysContexts.getEntityManager();
		OrderFeeRule rule = (OrderFeeRule) session.get(OrderFeeRule.class, id);
		if(rule.getRuleType() == SysStaticDataEnumYunQi.RULE_TYPE.tip && rule.getTipType() == SysStaticDataEnumYunQi.TIP_TYPE.WEIGHT_RANGE){
			List<OrderTipRuleExt> tipList = session.createCriteria(OrderTipRuleExt.class).add(Restrictions.eq("tipId", rule.getId())).list();
			if(tipList != null && tipList.size() > 0){
				for (OrderTipRuleExt orderTipRuleExt : tipList) {
					session.delete(orderTipRuleExt);
				}
			}
		}
		session.delete(rule);
		return "Y";
	}
}
