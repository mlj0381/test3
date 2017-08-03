package com.wo56.business.match.chain;

import com.framework.components.match.chain.MatchCommand;
import com.framework.components.match.common.MatchDataUtils;
import com.framework.components.match.vo.BaseMatchObject;
import com.wo56.business.match.impl.BusiMatchConsts;

public class CarTypeRuleCommand extends MatchCommand {

	private static final String field = BusiMatchConsts.Fields.carType;
	
	@Override
	public boolean doMatchRule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		if (MatchDataUtils.getAObjAttr(aObj, field) != null && MatchDataUtils.getZObjAttr(zObj, field) != null 
				&& MatchDataUtils.getAObjAttr(aObj, field).equals(MatchDataUtils.getZObjAttr(zObj, field))) {
			return true;
		}
		return false;
	}

	@Override
	public long transVaule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		//加分基数
		return 1;
	}

}
