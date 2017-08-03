package com.wo56.business.ord.intf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.wo56.business.ord.impl.OrderFeeRuleSV;
import com.wo56.business.ord.vo.OrderFeeRule;
import com.wo56.business.ord.vo.OrderTipRuleExt;
import com.wo56.business.ord.vo.in.OrderFeeRuleSaveIn;
import com.wo56.business.ord.vo.out.OrderFeeTipParamOut;
import com.wo56.business.route.impl.RouteTowardsSV;
import com.wo56.business.route.vo.RouteTowards;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class OrderFeeRuleTF implements IOrderFeeRuleIntf{
	/**
	 * 列表查询
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> queryOrderFeeTip(Map<String,Object> inParam) throws Exception{
		String[]  noFileds = new String[]{
				"tenantId","tipType","isCurrency","createTime","beginCity","endCity",
				"minWeightFee","minWeightFeeString","maxWeightFee","maxWeightFeeString",
				"minVolumeFee","minVolumeFeeString","maxVolumeFee","maxVolumeFeeString",
				"minFee","minFeeString","numberFee","numberFeeString"
		};
		return JsonHelper.parseJSON2Map(JsonHelper.json(dealOrderFeeTipParamOut(inParam,SysStaticDataEnumYunQi.RULE_TYPE.tip),noFileds));
	}
	
	/**
	 * 运费列表查询
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> queryOrderFeeFreight(Map<String,Object> inParam) throws Exception{
		String[]  noFileds = new String[]{
				"tenantId","link","tipType","isFirst","isWeight","isFreight","isCurrencyString",
				"createTime","createTimeString","isCurrency","beginCity","endCity","numberFee",
				"numberFeeString","minWeightFee","maxWeightFee","minVolumeFee","maxVolumeFee"
		};
		return JsonHelper.parseJSON2Map(JsonHelper.json(dealOrderFeeTipParamOut(inParam,SysStaticDataEnumYunQi.RULE_TYPE.freight), noFileds));
	}
	
	/**
	 * 保费列表查询
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> queryOrderFeePremium(Map<String,Object> inParam) throws Exception{
		String[]  noFileds = new String[]
				{"tenantId","link","tipType","isFirst","isWeight","isFreight","isCurrencyString",
				"createTime","createTimeString","isCurrency","beginCity","endCity","minWeightFee",
				"minWeightFeeString","maxWeightFee","maxWeightFeeString","minVolumeFee","minVolumeFeeString",
				"maxVolumeFee","maxVolumeFeeString","minFee","minFeeString","numberFee"
				};
		return JsonHelper.parseJSON2Map(JsonHelper.json(dealOrderFeeTipParamOut(inParam,SysStaticDataEnumYunQi.RULE_TYPE.premium),noFileds));
	}
	
	/**
	 * 处理费用数据
	 * @param tip
	 * @param tenantId
	 * @param ruleName
	 * @param staProvince
	 * @param staCity
	 * @param desProvince
	 * @param desCity
	 * @return
	 * @throws Exception
	 */
	public Pagination dealOrderFeeTipParamOut(Map<String,Object> inParam,int tip) throws Exception{
		long tenantId = DataFormat.getLongKey(inParam,"tenantId");
		String ruleName = DataFormat.getStringKey(inParam, "ruleName");
		long beginOrgId = DataFormat.getLongKey(inParam,"beginOrgId");
		long endOrgId = DataFormat.getLongKey(inParam,"endOrgId");
		OrderFeeRuleSV orderFeeRuleSV = (OrderFeeRuleSV) SysContexts.getBean("orderFeeRuleSV");
		
		//专线公司只能查询自己的公司
		BaseUser baseUser=  SysContexts.getCurrentOperator();
		if(SysStaticDataEnumYunQi.TENANT_TYPE.CHAIN ==baseUser.getUserType()){
			tenantId=baseUser.getTenantId();
		}
		
		IPage page = PageUtil.gridPage(orderFeeRuleSV.getOrderFeeRule(tip,tenantId,ruleName,beginOrgId,endOrgId));
		Pagination p = new Pagination(page);
		List<Object[]> list = p.getItems();
		List<OrderFeeTipParamOut> outList = new ArrayList<OrderFeeTipParamOut>();
		if(list != null && list.size() > 0){
			for(Object[] temp : list){
				OrderFeeTipParamOut out = new OrderFeeTipParamOut();
				out.setId(temp[0] != null ? Long.valueOf(String.valueOf(temp[0])) : -1);
				out.setRuleName(temp[1] != null ? String.valueOf(temp[1]) : "");
				out.setTenantId(temp[2] != null ? Long.valueOf(String.valueOf(temp[2])) : -1);
				out.setBeginCity(temp[3] != null ? Long.valueOf(String.valueOf(temp[3])) : -1);
				out.setEndCity(temp[4] != null ? Long.valueOf(String.valueOf(temp[4])) : -1);
				out.setTipType(temp[5] != null ? Integer.valueOf(String.valueOf(temp[5])) : -1);
				out.setIsCurrency(temp[6] != null ? Integer.valueOf(String.valueOf(temp[6])) : -1);
				out.setCreateTime(temp[7] != null ? (Date)temp[7] : null);
				
				out.setMaxWeightFee(temp[9] != null ? Long.valueOf(String.valueOf(temp[9])) : -1);
				out.setMinWeightFee(temp[10] != null ? Long.valueOf(String.valueOf(temp[10])) : -1);
				out.setMaxVolumeFee(temp[11] != null ? Long.valueOf(String.valueOf(temp[11])) : -1);
				out.setMinVolumeFee(temp[12] != null ? Long.valueOf(String.valueOf(temp[12])) : -1);
				out.setMinFee(temp[13] != null ? Long.valueOf(String.valueOf(temp[13])) : -1);
				out.setNumberFee(temp[14] != null ? Long.valueOf(String.valueOf(temp[14])) : -1);
				out.setBeginOrgName(OraganizationCacheDataUtil.getOrgName(Long.valueOf(String.valueOf(temp[15]))));
				out.setEndOrgName(OraganizationCacheDataUtil.getOrgName(Long.valueOf(String.valueOf(temp[16]))));
				out.setLink(out.getBeginOrgName()+"-"+out.getEndOrgName());
				outList.add(out);
			}
		}
		p.setItems(outList);
		return p;
	}
	
	/**
	 * 小费规则保存
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doSaveTipFee(Map<String,Object> inParam)throws Exception{
		OrderFeeRuleSV orderFeeRuleSV = (OrderFeeRuleSV) SysContexts.getBean("orderFeeRuleSV");
		Session session = SysContexts.getEntityManager();
		OrderFeeRule rule = null;
		long id = DataFormat.getLongKey(inParam,"id");
		int tipType = DataFormat.getIntKey(inParam,"tipType");
		if(tipType < 0){
			throw new BusinessException("请选择计费类型！");
		}
		String ruleName = DataFormat.getStringKey(inParam, "ruleName");
		if (StringUtils.isEmpty(ruleName)) {
			throw new BusinessException("请输入计费规则名称");
		}
		long tenantId = DataFormat.getLongKey(inParam,"tenantId");
		if(tenantId < 0){
			throw new BusinessException("请选择归属专线公司！");
		}
		long beginOrgId  = DataFormat.getLongKey(inParam,"beginOrgId");
		if(beginOrgId < 0){
			throw new BusinessException("请选择起始网点！");
		}
		long endOrgId = DataFormat.getLongKey(inParam, "endOrgId");
		if (endOrgId < 0) {
			throw new BusinessException("请选择目的网点！");
		}
		//查询路由是否存在
		RouteTowardsSV routeTowardsSV = (RouteTowardsSV) SysContexts.getBean("routeTowardsSV");
		RouteTowards route = routeTowardsSV.getRouteTowardsByOrg(beginOrgId, endOrgId);
		if(route == null){
			throw new BusinessException("不存在该开始网点和目的网点的线路，请配置线路！");
		}
		
		String staFeeString = DataFormat.getStringKey(inParam, "staFeeString"); 
		if (StringUtils.isEmpty(staFeeString)) {
			throw new BusinessException("请输入起价！");
		}else if(!CommonUtil.isNumber(staFeeString)){
			throw new BusinessException("请输入正确的起价！");
		}
		String topFeeString = DataFormat.getStringKey(inParam, "topFeeString");
		if (StringUtils.isEmpty(topFeeString)) {
			throw new BusinessException("请输入封顶价！");
		}else if (!CommonUtil.isNumber(topFeeString)){
			throw new BusinessException("请输入正确的封顶价！");
		}
		int isCurrency = DataFormat.getIntKey(inParam,"isCurrency");
		OrderFeeRule isCurrencyRule = null;
		if(isCurrency == SysStaticDataEnumYunQi.IS_CURRENCY.IS_CURRENCY){
			isCurrencyRule = orderFeeRuleSV.isCurrencyRule(tenantId);
		}
		OrderFeeRule checkRule = orderFeeRuleSV.getOrderFeeRuleLine(beginOrgId, endOrgId, tenantId, SysStaticDataEnumYunQi.RULE_TYPE.tip);
		if(id > 0){
			rule = orderFeeRuleSV.getOrderFeeRule(id);
			if(isCurrencyRule != null && rule.getId() != isCurrencyRule.getId()){
				isCurrencyRule.setIsCurrency(SysStaticDataEnumYunQi.IS_CURRENCY.NOT_CURRENCY);//把原有的设置为不是通用的
				orderFeeRuleSV.doSaveOrUpdate(isCurrencyRule);
			}
			if(checkRule != null && rule != null && checkRule.getId() != rule.getId()){
				throw new BusinessException("已经存在该发站和到站的计费规则了！");
			}
			OrderFeeRuleSaveIn feeIn = new OrderFeeRuleSaveIn();
			BeanUtils.populate(feeIn, inParam);
			BeanUtils.copyProperties(rule, feeIn);
			rule.setTowardsId(route.getTowardsId());
		}else{
			if(checkRule != null){
				throw new BusinessException("已经存在该发站和到站的计费规则了！");
			}
			rule = new OrderFeeRule();
			if(isCurrencyRule != null){
				isCurrencyRule.setIsCurrency(SysStaticDataEnumYunQi.IS_CURRENCY.NOT_CURRENCY);//把原有的设置为不是通用的
				orderFeeRuleSV.doSaveOrUpdate(isCurrencyRule);
			}
			OrderFeeRuleSaveIn feeIn = new OrderFeeRuleSaveIn();
			BeanUtils.populate(feeIn, inParam);
			BeanUtils.copyProperties(rule, feeIn);
			rule.setCreateTime(new Date());
			rule.setTowardsId(route.getTowardsId());
		}
		rule.setRuleType(SysStaticDataEnumYunQi.RULE_TYPE.tip);
		if(tipType == SysStaticDataEnumYunQi.TIP_TYPE.WEIGHT_INCREMENT){
			String first = DataFormat.getStringKey(inParam, "first");
			if(rule.getAddWeight() == null || rule.getAddWeight() < 0){
				throw new BusinessException("请输入续重！");
			}
			rule.setFirst(first);
			rule.setMaxVolumeFee(null);
			rule.setMaxWeightFee(null);
			rule.setPercentage(null);
			rule.setMinFee(null);
			rule.setMinVolumeFee(null);
			rule.setMinWeightFee(null);
			rule.setNumberFee(null);
		}else if(tipType == SysStaticDataEnumYunQi.TIP_TYPE.FREIGHT_RATE){
			String percentage = DataFormat.getStringKey(inParam, "percentage");
			if(StringUtils.isEmpty(percentage)){
				throw new BusinessException("请输入占比率！");
			}else if(!CommonUtil.isNumber(percentage)){
				throw new BusinessException("请输入正确的占比率！");
			}
			rule.setPercentage(percentage);
			rule.setAddWeight(null);
			rule.setFirst(null);
			rule.setMaxVolumeFee(null);
			rule.setMaxWeightFee(null);
			rule.setMinFee(null);
			rule.setMinVolumeFee(null);
			rule.setMinWeightFee(null);
			rule.setNumberFee(null);
		}else if(tipType == SysStaticDataEnumYunQi.TIP_TYPE.WEIGHT_RANGE){
			orderFeeRuleSV.doSaveOrUpdate(rule);
			
			List<OrderTipRuleExt> isCheck = orderFeeRuleSV.getOrderTipRuleExtList(rule.getId());
			if(isCheck != null){
				for (OrderTipRuleExt orderTipRuleExt : isCheck) {
					session.delete(orderTipRuleExt);
				}
			}
			int num = DataFormat.getIntKey(inParam,"num");
			List<OrderTipRuleExt> list =  new ArrayList<OrderTipRuleExt>();
			for(int i=0;i<=num;i++){
				OrderTipRuleExt tip = new OrderTipRuleExt();
				tip.setFeeString(DataFormat.getStringKey(inParam, "orderTipRule_"+i+"_feeString"));
				tip.setMaxWieght(DataFormat.getStringKey(inParam, "orderTipRule_"+i+"_maxWieght"));
				tip.setMinWieght(DataFormat.getStringKey(inParam, "orderTipRule_"+i+"_minWieght"));
				list.add(tip);
			}
			if(list == null || list.size() < 0){
				throw new BusinessException("请输入区间费用！");
			}else{
				for(int i=0;i<list.size();i++){
					if(list.get(i).getFee() == null || list.get(i).getFee() < 0){
						throw new BusinessException("请输入第"+(i+1)+"的区间费用！");
					}
					if(StringUtils.isEmpty(list.get(i).getMinWieght())){
						throw new BusinessException("请输入第"+(i+1)+"的区间最小重量！");
					}
					if(StringUtils.isEmpty(list.get(i).getMaxWieght())){
						throw new BusinessException("请输入第"+(i+1)+"的区间最大重量！");
					}
					list.get(i).setTipId(rule.getId());
					orderFeeRuleSV.doSaveOrUpdateTip(list.get(i));
				}
			}
			rule.setPercentage(null);
			rule.setAddWeight(null);
			rule.setFirst(null);
			rule.setMaxVolumeFee(null);
			rule.setMaxWeightFee(null);
			rule.setMinFee(null);
			rule.setMinVolumeFee(null);
			rule.setMinWeightFee(null);
			rule.setNumberFee(null);
			return "Y";
		}else{
			throw new BusinessException("选择的计费规则有误！");
		}
		if(rule.getId() != null){
			List<OrderTipRuleExt> isCheck = orderFeeRuleSV.getOrderTipRuleExtList(rule.getId());
			if(isCheck != null){
				for (OrderTipRuleExt orderTipRuleExt : isCheck) {
					session.delete(orderTipRuleExt);
				}
			}
		}
		orderFeeRuleSV.doSaveOrUpdate(rule);
		return "Y";
	}
	/**
	 * 根据id获取规则
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> getOrderFeeTipOut(Map<String,Object> inParam){
		OrderFeeRuleSV orderFeeRuleSV = (OrderFeeRuleSV) SysContexts.getBean("orderFeeRuleSV");
		long id = DataFormat.getLongKey(inParam,"id");
		if(id < 0){
			throw new BusinessException("请传入计费规则编号！");
		}
		OrderFeeRule orderFeeRule = orderFeeRuleSV.getOrderFeeRule(id);
		List<OrderTipRuleExt> list = orderFeeRuleSV.getOrderTipRuleExtList(id);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("feeRule", orderFeeRule);
		map.put("tipFee", list);
		return map;
	}
	/**
	 * 获取通用模板
	 */
	public Map<String,Object> isCurrencyRule(Map<String,Object> inParam){
		OrderFeeRuleSV orderFeeRuleSV = (OrderFeeRuleSV) SysContexts.getBean("orderFeeRuleSV");
		long tenantId = DataFormat.getLongKey(inParam,"tenantId");
		OrderFeeRule rule = orderFeeRuleSV.isCurrencyRule(tenantId);
		if(rule == null){
			throw new BusinessException("无通用模板可用！");
		}
		List<OrderTipRuleExt> list = orderFeeRuleSV.getOrderTipRuleExtList(rule.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("feeRule", rule);
		map.put("tipFee", list);
		return map;
	}
	/**
	 * 删除小费计费规则
	 */
	public String delOrderFeeRule(Map<String,Object> inParam){
		OrderFeeRuleSV orderFeeRuleSV = (OrderFeeRuleSV) SysContexts.getBean("orderFeeRuleSV");
		long id = DataFormat.getLongKey(inParam,"ruleId");
		if (id < 0) {
			throw new BusinessException("请传入规则编号！");
		}
		orderFeeRuleSV.delOrderFeeRule(id);
		return "Y";
	}
	/**
	 * 保存运费
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doSaveFreight(Map<String,Object> inParam)throws Exception{
		OrderFeeRuleSV orderFeeRuleSV = (OrderFeeRuleSV) SysContexts.getBean("orderFeeRuleSV");
		OrderFeeRule rule = null;
		OrderFeeRuleSaveIn in = new OrderFeeRuleSaveIn();
		long id = DataFormat.getLongKey(inParam,"id");
		long tenantId = DataFormat.getLongKey(inParam,"tenantId");
		if(tenantId < 0){
			throw new BusinessException("请选择归属专线公司！");
		}
		long beginOrgId  = DataFormat.getLongKey(inParam,"beginOrgId");
		if(beginOrgId < 0){
			throw new BusinessException("请选择起始网点！");
		}
		long endOrgId = DataFormat.getLongKey(inParam, "endOrgId");
		if (endOrgId < 0) {
			throw new BusinessException("请选择目的网点！");
		}
		//查询路由是否存在
		RouteTowardsSV routeTowardsSV = (RouteTowardsSV) SysContexts.getBean("routeTowardsSV");
		RouteTowards route = routeTowardsSV.getRouteTowardsByOrg(beginOrgId, endOrgId);
		if(route == null){
			throw new BusinessException("不存在该开始网点和目的网点的线路，请配置线路！");
		}
		//校验是否存在该运费的规则
		OrderFeeRule checkRule = orderFeeRuleSV.getOrderFeeRuleLine(beginOrgId, endOrgId, tenantId, SysStaticDataEnumYunQi.RULE_TYPE.freight);
		if(id > 0){
			rule = orderFeeRuleSV.getOrderFeeRule(id);
			if(checkRule != null && rule != null && checkRule.getId() != rule.getId()){
				throw new BusinessException("已经存在该发站和到站的计费规则了！");
			}
		}else{
			if(checkRule != null){
				throw new BusinessException("已经存在该发站和到站的计费规则了！");
			}
			rule = new OrderFeeRule();
			rule.setCreateTime(new Date());
		}
		BeanUtils.populate(in, inParam);
		BeanUtils.copyProperties(rule, in);
		rule.setTowardsId(route.getTowardsId());
		rule.setRuleType(SysStaticDataEnumYunQi.RULE_TYPE.freight);
		orderFeeRuleSV.doSaveOrUpdate(rule);
		return "Y";
	}
	/**
	 * 保存运费
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doSavePremium(Map<String,Object> inParam)throws Exception{
		OrderFeeRuleSV orderFeeRuleSV = (OrderFeeRuleSV) SysContexts.getBean("orderFeeRuleSV");
		OrderFeeRule rule = null;
		OrderFeeRuleSaveIn in = new OrderFeeRuleSaveIn();
		long id = DataFormat.getLongKey(inParam,"id");
		long tenantId = DataFormat.getLongKey(inParam,"tenantId");
		if(tenantId < 0){
			throw new BusinessException("请选择归属专线公司！");
		}
		long beginOrgId  = DataFormat.getLongKey(inParam,"beginOrgId");
		if(beginOrgId < 0){
			throw new BusinessException("请选择起始网点！");
		}
		long endOrgId = DataFormat.getLongKey(inParam, "endOrgId");
		if (endOrgId < 0) {
			throw new BusinessException("请选择目的网点！");
		}
		//查询路由是否存在
		RouteTowardsSV routeTowardsSV = (RouteTowardsSV) SysContexts.getBean("routeTowardsSV");
		RouteTowards route = routeTowardsSV.getRouteTowardsByOrg(beginOrgId, endOrgId);
		if(route == null){
			throw new BusinessException("不存在该开始网点和目的网点的线路，请配置线路！");
		}
		//校验是否存在该运费的规则
		OrderFeeRule checkRule = orderFeeRuleSV.getOrderFeeRuleLine(beginOrgId, endOrgId, tenantId, SysStaticDataEnumYunQi.RULE_TYPE.premium);
		if(id > 0){
			rule = orderFeeRuleSV.getOrderFeeRule(id);
			if(checkRule != null && rule != null && checkRule.getId() != rule.getId()){
				throw new BusinessException("已经存在该发站和到站的计费规则了！");
			}
		}else{
			if(checkRule != null){
				throw new BusinessException("已经存在该发站和到站的计费规则了！");
			}
			rule = new OrderFeeRule();
			rule.setCreateTime(new Date());
		}
		BeanUtils.populate(in, inParam);
		BeanUtils.copyProperties(rule, in);
		rule.setTowardsId(route.getTowardsId());
		rule.setRuleType(SysStaticDataEnumYunQi.RULE_TYPE.premium);
		orderFeeRuleSV.doSaveOrUpdate(rule);
		return "Y";
	}
}
