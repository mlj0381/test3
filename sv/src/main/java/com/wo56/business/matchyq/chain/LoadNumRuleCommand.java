package com.wo56.business.matchyq.chain;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.components.match.chain.MatchCommand;
import com.framework.components.match.common.MatchDataUtils;
import com.framework.components.match.vo.BaseMatchObject;
import com.wo56.business.matchyq.impl.BusiMatchConstsYq;
/**
 * 
 * 接单数量的属性
 * 
 * 该命令的配置比例为2%
 * 规则的公式:50-${V}
 * 
 * @author liyiye
 *
 */
public class LoadNumRuleCommand extends MatchCommand {
	private static final Log log = LogFactory.getLog(LoadNumRuleCommand.class);

	private static final String field = BusiMatchConstsYq.Fields.maxLoad;
	
	@Override
	public boolean doMatchRule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		if(log.isDebugEnabled()){
			log.debug("经纬度匹配：A:["+aObj.getId()+
					"],B:["+zObj.getId()+"]");
		}
		return true;
	}

	@Override
	public long transVaule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		String val = MatchDataUtils.getZObjAttr(zObj, field);
		if (StringUtils.isNotEmpty(val) && StringUtils.isNumeric(val)) {
			return Integer.parseInt(val);
		} else {
			return 0L;
		}
	}
}
