package com.wo56.business.matchyq.chain;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.components.match.chain.MatchCommand;
import com.framework.components.match.common.MatchDataUtils;
import com.framework.components.match.vo.BaseMatchObject;
import com.wo56.business.matchyq.impl.BusiMatchConstsYq;
import com.wo56.common.utils.GpsUtil;
/**
 * gsp距离的计算命令，计算2个点之间的距离
 * 该命令的配置比例为8%
 * 规则的公式:1000-${V}
 * 
 * @author liyiye
 *
 */
public class PointXYRuleCommand extends MatchCommand {
	private static final Log log = LogFactory.getLog(PointXYRuleCommand.class);

	private static final String pointX = BusiMatchConstsYq.Fields.pointX;
	private static final String pointY = BusiMatchConstsYq.Fields.pointY;
	
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
		String zPointX = MatchDataUtils.getZObjAttr(zObj, pointX);
		String zPointY =MatchDataUtils.getZObjAttr(zObj, pointY);
		
		String aPointX=MatchDataUtils.getAObjAttr(aObj, pointX);
		String aPointY=MatchDataUtils.getAObjAttr(aObj, pointY);
		if(StringUtils.isEmpty(zPointX) || StringUtils.isEmpty(zPointY) ||
				StringUtils.isEmpty(aPointX) || StringUtils.isEmpty(aPointY)){
			return 0;
		}
		//计算距离
		double distance=GpsUtil.getLongDistance(Double.valueOf(zPointX), Double.valueOf(zPointY), Double.valueOf(aPointX), Double.valueOf(aPointY));
		
		return (long)distance;
	}
}
