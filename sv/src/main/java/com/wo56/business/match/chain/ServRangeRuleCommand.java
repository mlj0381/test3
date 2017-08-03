package com.wo56.business.match.chain;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.framework.components.match.chain.MatchCommand;
import com.framework.components.match.common.MatchDataUtils;
import com.framework.components.match.vo.BaseMatchObject;
import com.wo56.business.match.impl.BusiMatchConsts;

public class ServRangeRuleCommand extends MatchCommand {

	private static double DEF_PI180 = 0.01745329252; // PI/180.0
	private static double DEF_R = 6370693.5; // radius of earth
	
	private static final String fieldx = BusiMatchConsts.Fields.pointX;
	private static final String fieldy = BusiMatchConsts.Fields.pointY;
	
	@Override
	public boolean doMatchRule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		return true;
	}

	@Override
	public long transVaule(BaseMatchObject aObj, BaseMatchObject zObj)
			throws Exception {
		if (StringUtils.isNotEmpty(MatchDataUtils.getAObjAttr(aObj, fieldy)) && StringUtils.isNotEmpty(MatchDataUtils.getAObjAttr(aObj, fieldx))
				&& StringUtils.isNotEmpty(MatchDataUtils.getZObjAttr(zObj, fieldy)) && StringUtils.isNotEmpty(MatchDataUtils.getZObjAttr(zObj, fieldx))
				&& StringUtils.isNumeric(MatchDataUtils.getAObjAttr(aObj, fieldy)) && StringUtils.isNumeric(MatchDataUtils.getAObjAttr(aObj, fieldx))
				&& StringUtils.isNumeric(MatchDataUtils.getZObjAttr(zObj, fieldy)) && StringUtils.isNumeric(MatchDataUtils.getZObjAttr(zObj, fieldx))) {
			// 安装师傅仓库地址 到 收货人地址 的距离
			return Math.round(getLongDistance(
					Double.parseDouble(MatchDataUtils.getAObjAttr(aObj, fieldy)),
					Double.parseDouble(MatchDataUtils.getAObjAttr(aObj, fieldx)),
					Double.parseDouble(MatchDataUtils.getZObjAttr(zObj, fieldy)),
					Double.parseDouble(MatchDataUtils.getZObjAttr(zObj, fieldx))));
		} 
		return 0;
	}
	
	/**
	 * 获取两点距离
	 * @param lon1
	 * @param lat1
	 * @param lon2
	 * @param lat2
	 * @return
	 */
	public static double getLongDistance(double lon1, double lat1, double lon2,
			double lat2) {
		double ew1, ns1, ew2, ns2;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 求大圆劣弧与球心所夹的角(弧度)
		distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1)
				* Math.cos(ns2) * Math.cos(ew1 - ew2);
		// 调整到[-1..1]范围内，避免溢出
		if (distance > 1.0)
			distance = 1.0;
		else if (distance < -1.0)
			distance = -1.0;
		// 求大圆劣弧长度
		distance = DEF_R * Math.acos(distance);
		BigDecimal bigDecimal = new BigDecimal(distance);
		return bigDecimal.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
