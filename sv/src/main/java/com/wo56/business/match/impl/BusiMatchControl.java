package com.wo56.business.match.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.components.match.common.MatchDataUtils;
import com.framework.components.match.vo.BaseMatchObject;

public class BusiMatchControl {
	private static final Log log = LogFactory.getLog(BusiMatchControl.class);
	/**
	 * 修改工人最大服务能力
	 * @throws Exception 
	 */
	public static int chgServLoad(long zId, int value) throws Exception {
//		String load = MatchDataUtils.getZObjAttr(zId, BusiMatchConsts.Fields.maxLoad);
		String load="";
//		try {
//			throw new BusinessException("师傅id"+zId);
//		} catch (Exception ex) {
//			log.error("1111111111111111111111", ex);
//		}
		if(StringUtils.isBlank(load) || !StringUtils.isNumeric(load)){
			load="0";
		}
		int destLoad = Integer.parseInt(load) + value;
		MatchDataUtils.setZObjAttr(zId, BusiMatchConsts.Fields.maxLoad, String.valueOf(destLoad));
		
		return destLoad;
	}
	
	/**
	 * 修改工人仓库容积
	 * @throws Exception 
	 */
	public static int chgStoreCubel(long zId, int value) throws Exception {
//		String val = MatchDataUtils.getZObjAttr(zId, BusiMatchConsts.Fields.storeCub);
		String val="";
		if(StringUtils.isBlank(val) || !StringUtils.isNumeric(val)){
			val="0";
		}
		int destVal = Integer.parseInt(val) + value;
		MatchDataUtils.setZObjAttr(zId, BusiMatchConsts.Fields.storeCub, String.valueOf(destVal));
		return destVal;
	}
	
	/**
	 * 修改信用值
	 * @throws Exception 
	 */
	public static void chgCredit(long zId, int value) throws Exception {
		MatchDataUtils.setZObjAttr(zId, BusiMatchConsts.Fields.credit, String.valueOf(value));
	}
	
	/**
	 * 增加工单到缓存
	 * @param aObj
	 * @throws Exception
	 */
	public static void addTask2Cache(BaseMatchObject aObj) throws Exception {
		MatchDataUtils.addAQueueObj(aObj);
	}
	
	/**
	 * 增加工人到缓存
	 * @param aObj
	 * @throws Exception
	 */
	public static void addWoker2Cache(BaseMatchObject zObj) throws Exception {
		MatchDataUtils.addZObj(zObj);
	}
}
