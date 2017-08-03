package com.wo56.business.match.chain;

import org.apache.commons.lang.StringUtils;

import com.framework.components.match.chain.MatchCommand;
import com.framework.components.match.common.MatchDataUtils;
import com.framework.components.match.vo.BaseMatchObject;
import com.wo56.business.match.impl.BusiMatchConsts;

public class MaxLoadRuleCommand extends MatchCommand {

	private static final String field = BusiMatchConsts.Fields.maxLoad;
	
	@Override
	public boolean doMatchRule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		String val = MatchDataUtils.getZObjAttr(zObj, field);
		if (StringUtils.isNotEmpty(val) && StringUtils.isNumeric(val) && Integer.parseInt(val) > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public long transVaule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		//加分基数, 剩余接单能力
		String val = MatchDataUtils.getZObjAttr(zObj, field);
		if (StringUtils.isNotEmpty(val) && StringUtils.isNumeric(val)) {
			return Integer.parseInt(val);
		} else {
			return 0;
		}
	}
}
