package com.wo56.business.grabOrder.out;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.exceptions.JedisException;

import com.framework.components.redis.RedisHelper;
import com.framework.core.util.JsonHelper;
import com.wo56.business.grabOrder.util.GpsUtil;
import com.wo56.common.consts.EnumConsts;
/**
 * 业务操作redis的方法
 * 
 * 待推送的订单
 * 待接收的拉包工
 * 待多次推送的数据
 * 
 * @author liyiye
 *
 */
public class BusiGrabControlOut {
	private static final Log log = LogFactory.getLog(BusiGrabControlOut.class);
	/**
	 * 客户下单，保存订单的信息
	 * 
	 * @param orderId 主键
	 * @param busiUserId 下单的人的用户id
	 * @param zxTenantId 指定的专线id 
	 * @param latitude   提货地址的纬度
	 * @param longitude  提货地址的经度
	 * @param orderNO  订单号
	 * @param createTime 下单时间
	 * @param handlerTime 预约时间
	 * @param pickAddr  提货地址
	 */
	public static void addOrderInfo(String orderId,long busiUserId,long zxTenantId,
			double latitude,double longitude,String orderNO,
			String createTime,String handlerTime,String pickAddr,String destCityName){
		//设置订单的经纬度
		GpsUtil.setOrderIdGpsPosition(orderId, latitude, longitude,null,null);
		
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			Map<String,String> mapVl=new HashMap<String,String>();
			if(busiUserId>0){
				mapVl.put(BusiGrabConsts.Order.BUSI_USER_ID, String.valueOf(busiUserId));
			}
			if(zxTenantId>0){
				mapVl.put(BusiGrabConsts.Order.ZX_TENANT_ID, String.valueOf(zxTenantId));
			}
			mapVl.put(BusiGrabConsts.Order.SEND_COUNT, "0");
			mapVl.put(BusiGrabConsts.Order.LATITUDE, String.valueOf(latitude));
			mapVl.put(BusiGrabConsts.Order.LONGITUDE, String.valueOf(longitude));
			
			mapVl.put(BusiGrabConsts.Order.ORDER_NO, orderNO);
			mapVl.put(BusiGrabConsts.Order.ORDER_ID, orderId);
			mapVl.put(BusiGrabConsts.Order.CREATE_TIME, createTime);
			mapVl.put(BusiGrabConsts.Order.HANDLER_TIME, handlerTime);
			mapVl.put(BusiGrabConsts.Order.PICK_ADDR, pickAddr);
			mapVl.put(BusiGrabConsts.Order.DEST_CITY_NAME, destCityName);
			
			
			jedis.sadd(EnumConsts.RemoteCache.ORDER_ALL_SET_KEY, orderId);
			jedis.hset(EnumConsts.RemoteCache.ORDER_ALL_HASH_KEY, orderId, JsonHelper.json(mapVl));
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	
	/**
	 * 检查多次发送的订单，满足条件的，加入到待发送的订单 保存订单的信息Set
	 * 
	 * 需要把多次推送的订单次数的属性更新到待发送的订单的属性
	 * 
	 * @param orderId
	 */
	public static void addOrderInfoSet(String orderId){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			jedis.sadd(EnumConsts.RemoteCache.ORDER_ALL_SET_KEY, orderId);
			//更新发送的次数
			String multJsonStr= jedis.hget(EnumConsts.RemoteCache.MULT_ORDER_ALL_HASH_KEY, orderId);
			Map<String,Object> multMap= JsonHelper.parseJSON2Map(multJsonStr);
			Object count= multMap.get(BusiGrabConsts.MultOrder.SEND_COUNT);
			String orderJsonStr= jedis.hget(EnumConsts.RemoteCache.ORDER_ALL_HASH_KEY, orderId);
			Map<String,Object> orderMap= JsonHelper.parseJSON2Map(orderJsonStr);
			orderMap.put(BusiGrabConsts.Order.SEND_COUNT, count);
			jedis.hset(EnumConsts.RemoteCache.ORDER_ALL_HASH_KEY, orderId,JsonHelper.json(orderMap));
			
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	
	/**
	 * 删除抢单的所有缓存
	 * @param orderId
	 */
	public static void delAllOrderInfo(String orderId){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			jedis.srem(EnumConsts.RemoteCache.ORDER_ALL_SET_KEY, orderId);
			jedis.hdel(EnumConsts.RemoteCache.ORDER_ALL_HASH_KEY, orderId);
			
			jedis.srem(EnumConsts.RemoteCache.MULT_ORDER_ALL_SET_KEY, orderId);
			jedis.hdel(EnumConsts.RemoteCache.MULT_ORDER_ALL_HASH_KEY, orderId);
			
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	/**
	 * 删除待推送的订单信息
	 * @param orderId
	 */
	public static void delOrderInfo(String orderId){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			jedis.srem(EnumConsts.RemoteCache.ORDER_ALL_SET_KEY, orderId);
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	/**
	 * 批量删除 待推送的订单信息 
	 * 
	 * @param orderIds
	 */
	public void delOrderInfo(String[] orderIds){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			jedis.srem(EnumConsts.RemoteCache.ORDER_ALL_SET_KEY, orderIds);
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	
	/**
	 * 更新用户的经纬度
	 * @param userId
	 * @param latitude
	 * @param longitude
	 */
	public static void updateUserInfoPoint(long userId,double latitude,double longitude){
		Double oldLatitude=null;
		Double oldLongitude=null;
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		Map<String,Object> jsonMap=new HashMap<String, Object>();
		try {
			String jsonVl=jedis.hget(EnumConsts.RemoteCache.USER_ALL_HASH_KEY, String.valueOf(userId));
			if(StringUtils.isNotBlank(jsonVl)){
				jsonMap= JsonHelper.parseJSON2Map(jsonVl);
				if(jsonMap!=null){
					Object x= jsonMap.get(BusiGrabConsts.User.LATITUDE);
					Object y= jsonMap.get(BusiGrabConsts.User.LONGITUDE);
					if(x!=null && y!=null){
						oldLatitude=Double.valueOf(x.toString());
						oldLongitude=Double.valueOf(y.toString());
					}
					jsonMap.put(BusiGrabConsts.User.LATITUDE, latitude);
					jsonMap.put(BusiGrabConsts.User.LONGITUDE, longitude);
				}
			}else{
				jsonMap.put(BusiGrabConsts.User.LATITUDE, latitude);
				jsonMap.put(BusiGrabConsts.User.LONGITUDE, longitude);
			}
			jedis.hset(EnumConsts.RemoteCache.USER_ALL_HASH_KEY, String.valueOf(userId),JsonHelper.json(jsonMap));
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
		//设置用户的经纬度的分片
		GpsUtil.setUserIdGpsPosition(String.valueOf(userId), latitude, longitude,oldLatitude,oldLongitude);
	}
	
	/**
	 * 新增用户信息
	 * @param userId
	 * @param latitude
	 * @param longitude
	 * @param tenantId
	 * @param singularNum
	 * @param maxSingularNum
	 * @param restState
	 * @param billId
	 */
	public static void addUserInfo(long userId,double latitude,double longitude,long tenantId,long singularNum,long maxSingularNum,int restState,String billId){
		Double oldLatitude=null;
		Double oldLongitude=null;
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		Map<String,Object> jsonMap=new HashMap<String, Object>();
		try {
			String jsonVl=jedis.hget(EnumConsts.RemoteCache.USER_ALL_HASH_KEY, String.valueOf(userId));
			if(StringUtils.isNotBlank(jsonVl)){
				jsonMap= JsonHelper.parseJSON2Map(jsonVl);
				if(jsonMap!=null){
					Object x= jsonMap.get(BusiGrabConsts.User.LATITUDE);
					Object y= jsonMap.get(BusiGrabConsts.User.LONGITUDE);
					if(x!=null && y!=null){
						oldLatitude=Double.valueOf(x.toString());
						oldLongitude=Double.valueOf(y.toString());
					}
				}
			}
			jsonMap.put(BusiGrabConsts.User.LATITUDE, latitude);
			jsonMap.put(BusiGrabConsts.User.LONGITUDE, longitude);
			jsonMap.put(BusiGrabConsts.User.TENANT_ID , tenantId);
			jsonMap.put(BusiGrabConsts.User.SINGULAR_NUM  , singularNum);
			
			jsonMap.put(BusiGrabConsts.User.MAX_SINGULAR_NUM , maxSingularNum);
			jsonMap.put(BusiGrabConsts.User.REST  , restState);
			jsonMap.put(BusiGrabConsts.User.BILL  , billId);
			
			jedis.sadd(EnumConsts.RemoteCache.USER_ALL_SET_KEY, String.valueOf(userId));
			
			jedis.hset(EnumConsts.RemoteCache.USER_ALL_HASH_KEY, String.valueOf(userId),JsonHelper.json(jsonMap));
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
		//设置用户的经纬度的分片
		GpsUtil.setUserIdGpsPosition(String.valueOf(userId), latitude, longitude,oldLatitude,oldLongitude);
	}
	
	
	
	
	/**
	 * 更新用户的接单数
	 * @param userId
	 */
	public static void updateUserInfoSingularNum(long userId,int singularNum){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		Map<String,Object> jsonMap=new HashMap<String, Object>();
		try {
			String jsonVl=jedis.hget(EnumConsts.RemoteCache.USER_ALL_HASH_KEY, String.valueOf(userId));
			if(StringUtils.isNotBlank(jsonVl)){
				jsonMap= JsonHelper.parseJSON2Map(jsonVl);
				if(jsonMap!=null){
					jsonMap.put(BusiGrabConsts.User.SINGULAR_NUM, singularNum);
				}
			}else{
				jsonMap.put(BusiGrabConsts.User.SINGULAR_NUM, singularNum);
			}
			jedis.hset(EnumConsts.RemoteCache.USER_ALL_HASH_KEY, String.valueOf(userId),JsonHelper.json(jsonMap));
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	
	/**
	 * 更新用户的最大接单数
	 * @param userId
	 */
	public void updateUserInfoMaxSingularNum(long userId,int maxSingularNum){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		Map<String,Object> jsonMap=new HashMap<String, Object>();
		try {
			String jsonVl=jedis.hget(EnumConsts.RemoteCache.USER_ALL_HASH_KEY, String.valueOf(userId));
			if(StringUtils.isNotBlank(jsonVl)){
				jsonMap= JsonHelper.parseJSON2Map(jsonVl);
				if(jsonMap!=null){
					jsonMap.put(BusiGrabConsts.User.MAX_SINGULAR_NUM, maxSingularNum);
				}
			}else{
				jsonMap.put(BusiGrabConsts.User.MAX_SINGULAR_NUM, maxSingularNum);
			}
			jedis.hset(EnumConsts.RemoteCache.USER_ALL_HASH_KEY, String.valueOf(userId),JsonHelper.json(jsonMap));
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	
	/**
	 * 更新拉包工的上下班的状态
	 * @param userId
	 * @param state 0：休息，1：上班
	 */
	public static void updateUserInfoRest(long userId,int state){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		Map<String,Object> jsonMap=new HashMap<String, Object>();
		try {
			String jsonVl=jedis.hget(EnumConsts.RemoteCache.USER_ALL_HASH_KEY, String.valueOf(userId));
			if(StringUtils.isNotBlank(jsonVl)){
				jsonMap= JsonHelper.parseJSON2Map(jsonVl);
				if(jsonMap!=null){
					jsonMap.put(BusiGrabConsts.User.REST, state);
				}
			}else{
				jsonMap.put(BusiGrabConsts.User.REST, state);
			}
			jedis.hset(EnumConsts.RemoteCache.USER_ALL_HASH_KEY, String.valueOf(userId),JsonHelper.json(jsonMap));
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	
	/**
	 * 更新拉包工的租户
	 * 
	 * @param userId
	 * @param tenantId 
	 */
	public void updateUserInfoTenantId(long userId,long tenantId){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		Map<String,Object> jsonMap=new HashMap<String, Object>();
		try {
			String jsonVl=jedis.hget(EnumConsts.RemoteCache.USER_ALL_HASH_KEY, String.valueOf(userId));
			if(StringUtils.isNotBlank(jsonVl)){
				jsonMap= JsonHelper.parseJSON2Map(jsonVl);
				if(jsonMap!=null){
					jsonMap.put(BusiGrabConsts.User.TENANT_ID, tenantId);
				}
			}else{
				jsonMap.put(BusiGrabConsts.User.TENANT_ID, tenantId);
			}
			jedis.hset(EnumConsts.RemoteCache.USER_ALL_HASH_KEY, String.valueOf(userId),JsonHelper.json(jsonMap));
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	
	/**
	 * 获取待发送的订单的数据
	 * 
	 * @return
	 */
	public static Set<String> getAllToSendOrder(){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			Set<String> orderIdSet=jedis.smembers(EnumConsts.RemoteCache.ORDER_ALL_SET_KEY);
			return orderIdSet;
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	
	
	/**
	 * 获取订单的全部信息
	 * 
	 * @param orderId
	 * @return
	 */
	public static Map<String,Object> getOrderInfoMap(String orderId){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			String jsonStr=jedis.hget(EnumConsts.RemoteCache.ORDER_ALL_HASH_KEY, orderId);
			if(StringUtils.isEmpty(jsonStr)){
				return null;
			}
			return JsonHelper.parseJSON2Map(jsonStr);
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	/**
	 * 获取经纬度 一定范围内的用户数据
	 * 
	 * @param latitude
	 * @param longitude
	 * @param distance
	 * @return 用户的id
	 */
	public static Set<String> getUserByDistance(double latitude,double longitude,double distance){
		double[][] rec4Point= GpsUtil.getRectangle4Point(latitude, longitude, distance);
		Set<String> userSet= GpsUtil.getUserIdsByGpsRang(rec4Point[0][0], rec4Point[0][1], rec4Point[3][0], rec4Point[3][1]);
		Set<String> retSet=new HashSet<String>();
		Iterator<String> iterator= userSet.iterator();
		while(iterator.hasNext()){
			String userId=iterator.next();
			Map<String, Object> userMap= getUserInfo(userId);
			if(userMap!=null){
				Object userLatitude= userMap.get(BusiGrabConsts.User.LATITUDE);
				Object useLongitude= userMap.get(BusiGrabConsts.User.LONGITUDE);
				if(userLatitude!=null && useLongitude!=null){
					double distanceTemp=GpsUtil.getLongDistance(latitude, longitude, Double.valueOf(userLatitude.toString()), Double.valueOf(useLongitude.toString()));
					if(distanceTemp<distance){
						retSet.add(userId);
					}
				}
			}
			
		}
		return retSet;
	}
	
	/**
	 * 获取用户的信息
	 * 
	 * @param userId
	 * @return
	 */
	public static Map<String,Object> getUserInfo(String userId){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		Map<String,Object> jsonMap=new HashMap<String, Object>();
		try {
			String jsonVl=jedis.hget(EnumConsts.RemoteCache.USER_ALL_HASH_KEY, userId);
			if(StringUtils.isNotBlank(jsonVl)){
				jsonMap= JsonHelper.parseJSON2Map(jsonVl);
			}
			return jsonMap;
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	
	/**
	 *添加待多次推送的订单的数据
	 * 
	 * @param userId
	 * @return
	 */
	public static void  addMultOrderInfo(String orderId,long dateTime,int count){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			Map<String,Object> mapVl=new HashMap<String, Object>();
			mapVl.put(BusiGrabConsts.MultOrder.SEND_TIME, new Date().getTime());
			mapVl.put(BusiGrabConsts.MultOrder.SEND_COUNT, count);
			jedis.sadd(EnumConsts.RemoteCache.MULT_ORDER_ALL_SET_KEY, orderId);
			jedis.hset(EnumConsts.RemoteCache.MULT_ORDER_ALL_HASH_KEY, orderId, JsonHelper.json(mapVl));
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	
	/**
	 *获取多次推送的订单属性
	 * 
	 * @param userId
	 * @return
	 */
	public static Map<String, Object>  getMultOrderInfoMap(String orderId){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			String jsonVl=jedis.hget(EnumConsts.RemoteCache.MULT_ORDER_ALL_HASH_KEY, orderId);
			if(StringUtils.isNotEmpty(jsonVl)){
				return JsonHelper.parseJSON2Map(jsonVl);
			}else{
				return null;
			}
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	/**
	 * 获取待多次发送的订单的数据
	 * 
	 * @return
	 */
	public static Set<String> getAllMultToSendOrder(){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			Set<String> orderIdSet=jedis.smembers(EnumConsts.RemoteCache.MULT_ORDER_ALL_SET_KEY);
			return orderIdSet;
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	
	/**
	 *删除待多次推送的订单的数据
	 * 
	 * @param userId
	 * @return
	 */
	public static void  delMultOrderInfo(String orderId){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			jedis.srem(EnumConsts.RemoteCache.MULT_ORDER_ALL_SET_KEY, orderId);
			jedis.hdel(EnumConsts.RemoteCache.MULT_ORDER_ALL_HASH_KEY, orderId);
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	
	/**
	 * 获取经纬度 一定范围内的订单数量
	 * 
	 * @param latitude 纬度
	 * @param longitude 经度
	 * @param distance
	 * @param num
	 * @return 用户的id
	 */
	public static List<Map<String, Object>> getOrderInfoByDistance(double latitude,double longitude,double distance,int num){
		double[][] rec4Point= GpsUtil.getRectangle4Point(latitude, longitude, distance);
		Set<String> orderIdSet= GpsUtil.getOrderIdsByGpsRang(rec4Point[0][0], rec4Point[0][1], rec4Point[3][0], rec4Point[3][1]);
		List<Map<String, Object>> orderInfoList=new ArrayList<Map<String,Object>>();
		if(orderIdSet.size()<num){
			num=orderIdSet.size();
		}
		Iterator<String> iterator=  orderIdSet.iterator();
		for(int i=0;iterator.hasNext();i++){
			String orderId=iterator.next();
			Map<String, Object>  orderInfoMap=BusiGrabControlOut.getOrderInfoMap(orderId);
			
			if(orderInfoMap==null){
				continue;
			}
			
			Object orderLatitude=orderInfoMap.get(BusiGrabConsts.Order.LATITUDE);
			Object orderLongitude=orderInfoMap.get(BusiGrabConsts.Order.LONGITUDE);
			
			if(orderLatitude==null || orderLongitude==null){
				continue;
			}
			
			double distanceTemp=GpsUtil.getLongDistance(latitude, longitude, 
					Double.valueOf(orderLatitude.toString()), Double.valueOf(orderLongitude.toString()));
			log.error("订单号："+orderId+"距离："+distanceTemp+"入参经纬度："+latitude+","+longitude+"订单的经纬度："+orderLatitude.toString()+","+orderLongitude.toString());
			if(distanceTemp<distance){
				orderInfoMap.put(BusiGrabConsts.Order.ORDER_ID, orderId);
				orderInfoList.add(orderInfoMap);
			}
			
			if(i==(num-1)){
				break;
			}
		}
		return orderInfoList;
	}
	
	/**
	 * 抢单成功后，删除该订单的缓存数据
	 * @param orderId
	 */
	public static void delAllOrderInfoAndGps(String orderId){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			String orderJson= jedis.hget(EnumConsts.RemoteCache.ORDER_ALL_HASH_KEY, orderId);
			Map<String,Object> orderMap=JsonHelper.parseJSON2Map(orderJson);
			if (orderMap != null) {
				Object xObj=orderMap.get(BusiGrabConsts.Order.LATITUDE);
				Object yObj=orderMap.get(BusiGrabConsts.Order.LONGITUDE);
				GpsUtil.delOrderIdGpsPosition(orderId, Double.valueOf(xObj.toString()), Double.valueOf(yObj.toString()));
				
				jedis.srem(EnumConsts.RemoteCache.ORDER_ALL_SET_KEY, orderId);
				jedis.hdel(EnumConsts.RemoteCache.ORDER_ALL_HASH_KEY, orderId);
				
				jedis.srem(EnumConsts.RemoteCache.MULT_ORDER_ALL_SET_KEY, orderId);
				jedis.hdel(EnumConsts.RemoteCache.MULT_ORDER_ALL_HASH_KEY, orderId);
			}else{
				log.error("片区没有该【"+orderId+"】订单数据");
			}
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
	}
	
}
