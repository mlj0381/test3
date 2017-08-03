package com.wo56.business.match.chain;

import org.apache.commons.lang.StringUtils;

import com.framework.components.match.chain.MatchCommand;
import com.framework.components.match.common.MatchDataUtils;
import com.framework.components.match.vo.BaseMatchObject;
import com.wo56.business.match.impl.BusiMatchConsts;

public class AreaRuleCommand extends MatchCommand {

	private static final String field = BusiMatchConsts.Fields.area;

	@Override
	public boolean doMatchRule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		String zVals = MatchDataUtils.getZObjAttr(zObj, field);
		if (StringUtils.isNotEmpty(zVals)) {
			String[] vals = zVals.split(",");
			for (String val : vals) {
				if (MatchDataUtils.getAObjAttr(aObj, field).equals(val)) {
					return true;
				}
			}
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
