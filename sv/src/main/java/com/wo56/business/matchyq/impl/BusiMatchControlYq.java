package com.wo56.business.matchyq.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.components.match.common.MatchDataUtils;
import com.framework.components.match.vo.BaseMatchObject;
/**
 * 用于更新下面两个数据的对外的方法
 * A端数据（客户下单，待匹配的数据），Z端（拉包工数据，匹配的数据）
 * 
 * @author liyiye
 *
 */
public class BusiMatchControlYq {
	private static final Log log = LogFactory.getLog(BusiMatchControlYq.class);
	
	/**
	 * 下单的时候，把订单的信息放到待处理的列表
	 * @param orderId
	 * @param tenantId
	 * @param pointX 经度
	 * @param pointY 纬度
	 * @param createId 创建人id
	 */
	public static void addOrderInfo(Long orderId,Long tenantId,String pointX,String pointY,Long createId) throws Exception {
		BaseMatchObject aObj=new BaseMatchObject();
		aObj.setId(orderId);
		aObj.setTenantId(BusiMatchConstsYq.z_tenantId);
		Map<String, String> attrs=new HashMap<String, String>();
		attrs.put(BusiMatchConstsYq.Fields.tenantId, tenantId.toString());
		attrs.put(BusiMatchConstsYq.Fields.pointX, pointX.toString());
		attrs.put(BusiMatchConstsYq.Fields.pointY, pointY.toString());
		attrs.put(BusiMatchConstsYq.Fields.createId, createId.toString());
		aObj.setAttrs(attrs);
		MatchDataUtils.addAQueueObj(aObj);
	}
	/**
	 * 删除订单，需要把订单号
	 * @param orderId
	 */
	public static void removeOrderInfo(Long orderId)throws Exception {
		BaseMatchObject aObj=new BaseMatchObject();
		aObj.setId(orderId);
		aObj.setTenantId(BusiMatchConstsYq.z_tenantId);
		MatchDataUtils.remAQueueObj(aObj);
	}
	
	/**
	 * 拉包工登录/上班的时候，上传拉包工的属性
	 * @param userId
	 * @param tenantId 租户
	 * @param loadNum  接单数
	 * @param pointX   经度
	 * @param pointY   纬度
	 */
	public static void loginAndWork(Long userId,Long tenantId,Integer loadNum,Double pointX,Double pointY)throws Exception {
		BaseMatchObject zObj=new BaseMatchObject();
		zObj.setId(userId);
		zObj.setTenantId(BusiMatchConstsYq.z_tenantId);
		Map<String, String> attrs=new HashMap<String, String>();
		attrs.put(BusiMatchConstsYq.Fields.tenantId, tenantId.toString());
		attrs.put(BusiMatchConstsYq.Fields.pointX, pointX.toString());
		attrs.put(BusiMatchConstsYq.Fields.pointY, pointY.toString());
		attrs.put(BusiMatchConstsYq.Fields.maxLoad, loadNum.toString());
		zObj.setAttrs(attrs);
		MatchDataUtils.addZObj(zObj);
	}
	/**
	 * 拉包工休息
	 * @param userId 
	 */
	public static void rest(Long userId)throws Exception {
		MatchDataUtils.remTenantZObj(BusiMatchConstsYq.z_tenantId, userId);
	}
	/**
	 * 如果拉包的接单数等于默认接单数，表示该拉包工不能再进行分配订单
	 * 拉包工 更新 接单数信息
	 * @param userId
	 * @param tenantId
	 * @param loadNum
	 * @param defaultSingularNum 默认接单数
	 */
	public static void updatePullLoadNum(Long userId,Long tenantId,Long loadNum,Long defaultSingularNum)throws Exception{
		if(loadNum!=null && defaultSingularNum!=null && defaultSingularNum.longValue()==loadNum.longValue()){
			rest(userId);
			return;
		}
		if(loadNum != null){
			MatchDataUtils.setZObjAttr(userId, BusiMatchConstsYq.Fields.maxLoad, loadNum.toString());
		}
	}
	/**
	 * 拉包工，更新 经纬度
	 * @param userId
	 * @param pointX
	 * @param pointY
	 */
	public static void updatePullPointXY(Long userId,Double pointX,Double pointY)throws Exception{
		MatchDataUtils.setZObjAttr(userId, BusiMatchConstsYq.Fields.pointX, pointX.toString());
		MatchDataUtils.setZObjAttr(userId, BusiMatchConstsYq.Fields.pointY, pointY.toString());
	}
}
