package com.wo56.business.match.chain;

import org.apache.commons.lang.StringUtils;

import com.framework.components.match.chain.MatchCommand;
import com.framework.components.match.common.MatchDataUtils;
import com.framework.components.match.vo.BaseMatchObject;
import com.wo56.business.match.impl.BusiMatchConsts;

public class CreditRuleCommand extends MatchCommand {

	private static final String field = BusiMatchConsts.Fields.credit;
	
	@Override
	public boolean doMatchRule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		//匹配成功
		return true;
	}

	@Override
	public long transVaule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		//加分基数
		String val = MatchDataUtils.getZObjAttr(zObj, field);
		if (StringUtils.isNotEmpty(val) && StringUtils.isNumeric(val)) {
			return Long.parseLong(val);
		} else {
			return 0;
		}
	}

}
