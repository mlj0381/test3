package com.wo56.business.grabOrder.util;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.exceptions.JedisException;

import com.framework.components.redis.RedisHelper;
import com.framework.components.redis.RemoteCacheUtil;
import com.wo56.common.consts.EnumConsts;

public class GpsUtil {
	
	private static final Log log = LogFactory.getLog(GpsUtil.class);
	
	 /** 地球半径 */  
    private static final double EARTH_RADIUS = 6371000;
	private static double DEF_PI = 3.14159265359; // PI
	private static double DEF_2PI = 6.28318530712; // 2*PI
	private static double DEF_PI180 = 0.01745329252; // PI/180.0
	private static double DEF_R = 6370693.5; // radius of earth
	
	/**
	 * 数据分页;
	 * @param coords
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
//	private static String paging(String[] coords,int countPerPage,int pageNum,int totalPageNum) throws MalformedURLException, IOException {
//		String str="";
//		if(coords!=null && coords.length>0){
//			for(int i=0;i<countPerPage;i++){
//				int statNum=(pageNum - 1) * countPerPage;
//				int endNum=pageNum * countPerPage;
//				if(pageNum * countPerPage>coords.length){
//					endNum=coords.length;
//				}
//				int num=statNum+i;
//				if(num==endNum){
//					break;
//				}
//				str+=coords[num]+";";
//			}
//		}
//		str=str.substring(0,str.length()-1);
//		return str;
//	}
	
	
	
	/***
	 * 根据当前的经纬度获取范围内的经纬度4个点
	 * 
	 * @param latitude  纬度  30.500
	 * @param longitude 经度  120.500
	 * @param distance  距离 单位米
	 * @return
	 *       纬度，经度
	 */
	public static double[][] getRectangle4Point(double latitude, double longitude,double distance) {
        double dlng = 2 * Math.asin(Math.sin(distance/(2*EARTH_RADIUS))/Math.cos(Math.toRadians(latitude)));
        dlng = Math.toDegrees(dlng);
        double dlat = distance / EARTH_RADIUS;  
        dlat = Math.toDegrees(dlat); // # 弧度转换成角度
        double[][] locations = new double[][]{{latitude + dlat, longitude - dlng},//左上角
        		{latitude + dlat, longitude + dlng},//右上角
        		{latitude - dlat, longitude - dlng},//左下角
        		{latitude - dlat, longitude + dlng}};//右下角
        System.out.println("左上角:"+(latitude + dlat)+","+ (longitude - dlng));
        System.out.println("右上角:"+(latitude + dlat)+","+ (longitude + dlng));
        System.out.println("左下角:"+(latitude - dlat)+","+ (longitude - dlng));
        System.out.println("右下角:"+(latitude - dlat)+","+ (longitude + dlng));
        return locations;
    }
	
//	public static double hav(double theta) {  
//        double s = Math.sin(theta / 2);  
//        return s * s;  
//    }
	
//	public static double getDistance(double lat0, double lng0, double lat1,double lng1) { 
//        lat0 = Math.toRadians(lat0);  
//        lat1 = Math.toRadians(lat1);  
//        lng0 = Math.toRadians(lng0);  
//        lng1 = Math.toRadians(lng1);
//        double dlng = Math.abs(lng0 - lng1);  
//        double dlat = Math.abs(lat0 - lat1);  
//        double h = hav(dlat) + Math.cos(lat0) * Math.cos(lat1) * hav(dlng);  
//        double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));
//        return distance;  
//    }  
	
	
	
	
	
	/**
	 * 获取距离（适合距离较近）
	 * @param lon1
	 * @param lat1
	 * @param lon2
	 * @param lat2
	 * @return
	 */
//	public static double getShortDistance(double lon1, double lat1, double lon2,
//			double lat2) {
//		double ew1, ns1, ew2, ns2;
//		double dx, dy, dew;
//		double distance;
//		// 角度转换为弧度
//		ew1 = lon1 * DEF_PI180;
//		ns1 = lat1 * DEF_PI180;
//		ew2 = lon2 * DEF_PI180;
//		ns2 = lat2 * DEF_PI180;
//		// 经度差
//		dew = ew1 - ew2;
//		// 若跨东经和西经180 度，进行调整
//		if (dew > DEF_PI)
//			dew = DEF_2PI - dew;
//		else if (dew < -DEF_PI)
//			dew = DEF_2PI + dew;
//		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
//		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
//		// 勾股定理求斜边长
//		distance = Math.sqrt(dx * dx + dy * dy);
//		return distance;
//	}

	/**
	 * 获取距离（适合距离较远）
	 * @param lon1
	 * @param lat1
	 * @param lon2
	 * @param lat2
	 * @return
	 */
//	public static double getLongDistance(double lon1, double lat1, double lon2,
//			double lat2) {
//		double ew1, ns1, ew2, ns2;
//		double distance;
//		// 角度转换为弧度
//		ew1 = lon1 * DEF_PI180;
//		ns1 = lat1 * DEF_PI180;
//		ew2 = lon2 * DEF_PI180;
//		ns2 = lat2 * DEF_PI180;
//		// 求大圆劣弧与球心所夹的角(弧度)
//		distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1)
//				* Math.cos(ns2) * Math.cos(ew1 - ew2);
//		// 调整到[-1..1]范围内，避免溢出
//		if (distance > 1.0)
//			distance = 1.0;
//		else if (distance < -1.0)
//			distance = -1.0;
//		// 求大圆劣弧长度
//		distance = DEF_R * Math.acos(distance);
//		BigDecimal bigDecimal = new BigDecimal(distance);
//		return bigDecimal.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
//	}

	/**
	 * 获取两个点做为直径的类圆（矩形）的范围内的订单数据
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public static Set<String> getOrderIdsByGpsRang(double latitude1, double longitude1, double latitude2, double longitude2){
		double startX = latitude1, endX = latitude2, startY = longitude1, endY = longitude2;
		if (latitude1 > latitude2) {
			startX = latitude2;
			endX = latitude1;
		}
		if (longitude1 > longitude2) {
			startY = longitude2;
			endY = longitude1;
		}
		log.info("x1:"+ latitude1+ ", x2:" +latitude2+ ", y1:" +longitude1+ ",y2:" +longitude2);
		//获取占用的分片开始位置及截止位置
		int xStartIdx = -1, xEndIdx = -1, yStartIdx = -1, yEndIdx = -1;
		for (int i=0; i<EnumConsts.GPS_SHARDING.shardingNum; i++) {
			double currX = EnumConsts.GPS_SHARDING.latitude[0] + (double) (EnumConsts.GPS_SHARDING.xRang * i);
			if (currX <= startX && (currX + EnumConsts.GPS_SHARDING.xRang) > startX) {
				xStartIdx = i;
			}
			if (currX <= endX && (currX + EnumConsts.GPS_SHARDING.xRang) > endX) {
				xEndIdx = i;
			}
		}
		
		for (int i=0; i<EnumConsts.GPS_SHARDING.shardingNum; i++) {
			double currY = EnumConsts.GPS_SHARDING.longitude[0] + (double) (EnumConsts.GPS_SHARDING.yRang * i);
			if (currY <= startY && (currY + EnumConsts.GPS_SHARDING.yRang) > startY) {
				yStartIdx = i;
			}
			if (currY <= endY && (currY + EnumConsts.GPS_SHARDING.yRang) > endY) {
				yEndIdx = i;
			}
		}
		//取X合集
		Set<String> xSet = new HashSet<String>();
		Set<String> ySet = new HashSet<String>();
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			for (int i = xStartIdx; i <= xEndIdx; i++) {
				xSet.addAll(jedis.smembers(EnumConsts.RemoteCache.ORDERID_Gps_LAT_Set +i));
			}
			
			//取Y合集
			for (int i = yStartIdx; i <= yEndIdx; i++) {
				ySet.addAll(jedis.smembers(EnumConsts.RemoteCache.ORDERID_Gps_LONG_Set +i));
			}
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
		
		//取XY交集
		xSet.retainAll(ySet);
		log.info("xrang:"+ xStartIdx+ "--" +xEndIdx);
		log.info("yrang:"+ yStartIdx+ "--" +yEndIdx);
		log.info("集合大小" + ySet.size());
		return xSet;
	}
	
	/**
	 * 获取两个点做为直径的类圆（矩形）的范围内的用户数据
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public static Set<String> getUserIdsByGpsRang(double latitude1, double longitude1, double latitude2, double longitude2){
		double startX = latitude1, endX = latitude2, startY = longitude1, endY = longitude2;
		if (latitude1 > latitude2) {
			startX = latitude2;
			endX = latitude1;
		}
		if (longitude1 > longitude2) {
			startY = longitude2;
			endY = longitude1;
		}
		log.info("x1:"+ latitude1+ ", x2:" +latitude2+ ", y1:" +longitude1+ ",y2:" +longitude2);
		//获取占用的分片开始位置及截止位置
		int xStartIdx = -1, xEndIdx = -1, yStartIdx = -1, yEndIdx = -1;
		for (int i=0; i<EnumConsts.GPS_SHARDING.shardingNum; i++) {
			double currX = EnumConsts.GPS_SHARDING.latitude[0] + (double) (EnumConsts.GPS_SHARDING.xRang * i);
			if (currX <= startX && (currX + EnumConsts.GPS_SHARDING.xRang) > startX) {
				xStartIdx = i;
			}
			if (currX <= endX && (currX + EnumConsts.GPS_SHARDING.xRang) > endX) {
				xEndIdx = i;
			}
		}
		
		for (int i=0; i<EnumConsts.GPS_SHARDING.shardingNum; i++) {
			double currY = EnumConsts.GPS_SHARDING.longitude[0] + (double) (EnumConsts.GPS_SHARDING.yRang * i);
			if (currY <= startY && (currY + EnumConsts.GPS_SHARDING.yRang) > startY) {
				yStartIdx = i;
			}
			if (currY <= endY && (currY + EnumConsts.GPS_SHARDING.yRang) > endY) {
				yEndIdx = i;
			}
		}
		//取X合集
		Set<String> xSet = new HashSet<String>();
		Set<String> ySet = new HashSet<String>();
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			for (int i = xStartIdx; i <= xEndIdx; i++) {
				xSet.addAll(jedis.smembers(EnumConsts.RemoteCache.USERID_Gps_LAT_Set +i));
			}
			
			//取Y合集
			for (int i = yStartIdx; i <= yEndIdx; i++) {
				ySet.addAll(jedis.smembers(EnumConsts.RemoteCache.USERID_Gps_LONG_Set +i));
			}
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
		
		//取XY交集
		xSet.retainAll(ySet);
		log.info("xrang:"+ xStartIdx+ "--" +xEndIdx);
		log.info("yrang:"+ yStartIdx+ "--" +yEndIdx);
		log.info("集合大小" + ySet.size());
		return xSet;
	}
	
	
	/**
	 * 设置订单的经纬度分片
	 * 
	 * @param userId
	 * @param latitude
	 * @param longitude
	 */
	public static void setUserIdGpsPosition(String userId, double latitude, double longitude,Double oldLatitude,Double oldLongitude){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			if(oldLatitude!=null && oldLongitude!=null){
				jedis.srem(EnumConsts.RemoteCache.USERID_Gps_LAT_Set + getShardIdex("X", oldLatitude), userId);
				jedis.srem(EnumConsts.RemoteCache.USERID_Gps_LONG_Set + getShardIdex("Y", oldLongitude), userId);
			}
			//增加当前位置集合
			jedis.sadd(EnumConsts.RemoteCache.USERID_Gps_LAT_Set + getShardIdex("X", latitude), userId);
			jedis.sadd(EnumConsts.RemoteCache.USERID_Gps_LONG_Set + getShardIdex("Y", longitude), userId);
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
		
	}
	
	

	/**
	 * 设置订单的经纬度分片
	 * 
	 * @param orderId
	 * @param latitude
	 * @param longitude
	 * @param oldLatitude
	 * @param oldLongitude
	 */
	public static void setOrderIdGpsPosition(String orderId, double latitude, double longitude,Double oldLatitude,Double oldLongitude){
		
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			if(oldLatitude!=null && oldLongitude!=null){
				jedis.srem(EnumConsts.RemoteCache.ORDERID_Gps_LAT_Set + getShardIdex("X", oldLatitude), orderId);
				jedis.srem(EnumConsts.RemoteCache.ORDERID_Gps_LONG_Set + getShardIdex("Y", oldLongitude), orderId);
			}
			//增加当前位置集合
			jedis.sadd(EnumConsts.RemoteCache.ORDERID_Gps_LAT_Set + getShardIdex("X", latitude), orderId);
			jedis.sadd(EnumConsts.RemoteCache.ORDERID_Gps_LONG_Set + getShardIdex("Y", longitude), orderId);
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
		
	}
	/**
	 * 获取当前点所在的分片
	 * @param type
	 * @param pos
	 * @return
	 */
	private static int getShardIdex(String type, double pos) {
		if ("X".equals(type)) {
			for (int i=0; i<EnumConsts.GPS_SHARDING.shardingNum; i++) {
				double currX = EnumConsts.GPS_SHARDING.latitude[0] + (double) (EnumConsts.GPS_SHARDING.xRang * i);
				if (currX <= pos && (currX + EnumConsts.GPS_SHARDING.xRang) > pos) {
					return i;
				}
			}
		} else if ("Y".equals(type)){
			for (int i=0; i<EnumConsts.GPS_SHARDING.shardingNum; i++) {
				double currY = EnumConsts.GPS_SHARDING.longitude[0] + (double) (EnumConsts.GPS_SHARDING.yRang * i);
				if (currY <= pos && (currY + EnumConsts.GPS_SHARDING.yRang) > pos) {
					return i;
				}
			}
		}
		return 0;
	}
	
	/**
	 * 设置订单的经纬度分片
	 * 
	 * @param orderId
	 * @param latitude
	 * @param longitude
	 * @param oldX
	 * @param oldY
	 */
	public static void delOrderIdGpsPosition(String orderId, double latitude, double longitude){
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			jedis.srem(EnumConsts.RemoteCache.ORDERID_Gps_LAT_Set + getShardIdex("X", latitude), orderId);
			jedis.srem(EnumConsts.RemoteCache.ORDERID_Gps_LONG_Set + getShardIdex("Y", longitude), orderId);
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
		
	}
	
	/**
	 * 获取距离（适合距离较远）
	 * 
	 * @param latitude1 纬度
	 * @param longitude1 经度
	 * @param latitude2 纬度
	 * @param longitude2 经度
	 * @return
	 */
	public static double getLongDistance(double latitude1, double longitude1,
			double latitude2,double longitude2) {
		double ew1, ns1, ew2, ns2;
		double distance;
		// 角度转换为弧度
		ew1 = longitude1 * DEF_PI180;
		ns1 = latitude1 * DEF_PI180;
		ew2 = longitude2 * DEF_PI180;
		ns2 = latitude2 * DEF_PI180;
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
