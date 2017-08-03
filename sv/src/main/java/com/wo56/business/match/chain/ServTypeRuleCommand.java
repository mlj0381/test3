package com.wo56.business.match.chain;

import org.apache.commons.lang.StringUtils;

import com.framework.components.match.chain.MatchCommand;
import com.framework.components.match.common.MatchDataUtils;
import com.framework.components.match.vo.BaseMatchObject;
import com.wo56.business.match.impl.BusiMatchConsts;

public class ServTypeRuleCommand extends MatchCommand {

	private static final String field = BusiMatchConsts.Fields.servType;

	@Override
	public boolean doMatchRule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		String aVals = MatchDataUtils.getAObjAttr(aObj, field);
		String zVals = MatchDataUtils.getZObjAttr(zObj, field);
		if (StringUtils.isNotEmpty(aVals) && StringUtils.isNotEmpty(zVals)) {
			String[] aval = aVals.split(",");
			String[] zval = zVals.split(",");
			int count = 0;
			for (String av : aval) {
				for (String zv : zval) {
					if (av.equals(zv)) {
						count++;
						break;
					}
				}
			}
			//全部满足
			if (count == aval.length) {
				return true;
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
