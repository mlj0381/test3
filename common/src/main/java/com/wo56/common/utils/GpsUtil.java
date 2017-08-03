package com.wo56.common.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.exceptions.JedisException;

import com.framework.components.citys.City;
import com.framework.components.citys.District;
import com.framework.components.redis.RedisHelper;
import com.framework.components.redis.RemoteCacheUtil;
import com.framework.core.exception.BusinessException;
import com.framework.core.util.Resource;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.statistic.vo.Monitor;
import com.wo56.common.consts.EnumConsts;

public class GpsUtil {
	
	private static final Log log = LogFactory.getLog(GpsUtil.class);
	
	 /** 地球半径 */  
    private static final double EARTH_RADIUS = 6371000;
	private static String SERVICE_BAIDUAK = "";
	private static String WEB_BAIDUAK = "";
	private static double DEF_PI = 3.14159265359; // PI
	private static double DEF_2PI = 6.28318530712; // 2*PI
	private static double DEF_PI180 = 0.01745329252; // PI/180.0
	private static double DEF_R = 6370693.5; // radius of earth
	
	static{
		try {
			 Properties pro = Resource.loadPropertiesFromClassPath("map.properties");
			  SERVICE_BAIDUAK = pro.getProperty("key");
			  WEB_BAIDUAK = pro.getProperty("web_key");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据gps数据转成百度经纬度
	 * @param coords 
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static int getBaiduLocation(String coords,Map map,int count) throws MalformedURLException, IOException {
		String url = "http://api.map.baidu.com/geoconv/v1/?coords="+coords+"&from=1&to=5&ak="+SERVICE_BAIDUAK; 
		HttpURLConnection urlConnection = (HttpURLConnection)(new URL(url).openConnection());
		urlConnection.connect();
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String lines = reader.readLine(); 
		reader.close(); 
		urlConnection.disconnect();
		JSONObject jsonObject = JSONObject.fromObject(lines);
		int status=jsonObject.getInt("status");
		map.put("status", status);
		if(status!=0){
			switch (status) {
				case 1:
					map.put("err", "内部错误 ");
				case 21:
					map.put("err", "from非法 ");
				case 22:
					map.put("err", "to非法 ");
				case 24:
					map.put("err", "coords格式非法");
				case 25:
					map.put("err", "coords个数非法，超过限制 ");
				case 210:
					map.put("err", "IP问题");
			}
		} else {
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = JSONObject.fromObject(jsonArray.getString(i));
				String[] xy=new String[2];
				xy[0]=json.getString("x");
				xy[1]=json.getString("y");
				map.put(count, xy);
				count++;
			}
		}
		return count;
	}
	
	/**
	 * 根据gps数据转成百度经纬度
	 * @param coords
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static Map getBaiduLocation(String[] coords) throws MalformedURLException, IOException {
		Map map = new HashMap();
		int countPerPage=100;
		int totalSize=coords.length;//总记录数
		int totalPageNum=coords.length/countPerPage;//总页数
		if (totalSize%countPerPage > 0 ){
			totalPageNum = totalSize/countPerPage + 1;
		}
		int count=1;
		for(int i=0;i<totalPageNum;i++){
			count=getBaiduLocation(paging(coords,countPerPage,i+1,totalPageNum),map,count);
		}
		return map;
	}
	
	/**
	 * 数据分页;
	 * @param coords
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static String paging(String[] coords,int countPerPage,int pageNum,int totalPageNum) throws MalformedURLException, IOException {
		String str="";
		if(coords!=null && coords.length>0){
			for(int i=0;i<countPerPage;i++){
				int statNum=(pageNum - 1) * countPerPage;
				int endNum=pageNum * countPerPage;
				if(pageNum * countPerPage>coords.length){
					endNum=coords.length;
				}
				int num=statNum+i;
				if(num==endNum){
					break;
				}
				str+=coords[num]+";";
			}
		}
		str=str.substring(0,str.length()-1);
		return str;
	}
	
	public static Map getaddressLocation(long city,long district,String adderDel) throws MalformedURLException, Exception {
		City cityBean = SysStaticDataUtil.getCityDataList("SYS_CITY", city+"");
		if(cityBean==null){
			throw new BusinessException("没有找到对应的地市信息！");
		}
		String cityName=URLEncoder.encode(cityBean.getName(), "UTF-8");
		District districtBean = null;
		String districtName="";
		if(district>0){
			districtBean = SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", district+"");
			if(districtBean==null){
				throw new BusinessException("没有找到对应的县区信息！");
			}
			districtName=districtBean.getName();
		}
		String adder="";
		if(adderDel!=null && !adderDel.equals("")){
			adder=adderDel;
		}
		String cistrictBeanName=URLEncoder.encode(districtName+adder, "UTF-8");
		String url = "http://api.map.baidu.com/geocoder/v2/?address="+cistrictBeanName+"&city="+cityName+"&ak="+SERVICE_BAIDUAK+"&output=json";
		HttpURLConnection urlConnection = (HttpURLConnection)(new URL(url).openConnection());
		urlConnection.connect();
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String lines = reader.readLine(); 
		reader.close(); 
		urlConnection.disconnect();
		JSONObject jsonObject = JSONObject.fromObject(lines);
		String status=jsonObject.getString("status");
		Map map = new HashMap();
		if(!status.equals("0")){
			if(status.equals("1")){
				//throw new Exception("服务器内部错误 ！");
				//获取不到经纬度的情况
				url = "http://api.map.baidu.com/geocoder/v2/?address="+cityName+"&city="+cityName+"&ak="+SERVICE_BAIDUAK+"&output=json";
				urlConnection = (HttpURLConnection)(new URL(url).openConnection());
				urlConnection.connect();
				reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				lines = reader.readLine(); 
				reader.close(); 
				urlConnection.disconnect();
				jsonObject = JSONObject.fromObject(lines);
			}else if(status.equals("2")){
				throw new Exception("请求参数非法 ！");
			}else if(status.equals("3")){
				throw new Exception("权限校验失败 ！");
			}else if(status.equals("4")){
				throw new Exception("配额校验失败 ！");
			}else if(status.equals("5")){
				throw new Exception("ak不存在或者非法 ！");
			}else if(status.equals("101")){
				throw new Exception("服务禁用 ！");
			}else if(status.equals("102")){
				throw new Exception("不通过白名单或者安全码不对 ！");
			}else if(status.equals("2xx")){
				throw new Exception("无权限 ！");
			}else if(status.equals("3xx")){
				throw new Exception("配额错误  ！");
			}else {
				throw new Exception("转换经纬度异常！");
			}
		}
		map.put("status", status);
		String result=jsonObject.getString("result");
		JSONObject jsonObj = JSONObject.fromObject(result);
		String location=jsonObj.getString("location");
		JSONObject jsonlocation = JSONObject.fromObject(location);
		String lng=jsonlocation.getString("lng");
		String lat=jsonlocation.getString("lat");
		if(adderDel!=null && !adderDel.equals("")){
			map.put("lng", lng);
			map.put("lat", lat);
		}else{
			getOffsetdata(lng, lat, map);
		}
		String precise=jsonObj.getString("precise");
		map.put("precise", precise);
		String confidence=jsonObj.getString("confidence");
		map.put("confidence", confidence);
		String level=jsonObj.getString("level");
		map.put("level", level);
		return map;
	}
	
	/**
	 * 计算偏移位置
	 * @param lng
	 * @param lat
	 * @return
	 * @throws Exception
	 */
	public static Map getOffsetdata(String lng,String lat,Map map)throws Exception{
		String fLng=lng.substring(0, 6);
		String fLat=lat.substring(0, 6);
		if(lng.length() >9){
			String lLng=lng.substring(9, lng.length());
			lng=fLng+SysMagUtil.getRandomNumber(3)+lLng;
		}
		if(lat.length() > 9){
			String lLat=lat.substring(9, lat.length());
			lat=fLat+SysMagUtil.getRandomNumber(3)+lLat;
		}
		map.put("lng", lng);
		map.put("lat", lat);
		return map;
	}
	
	public static Map getBaiduAdder(String coords) throws MalformedURLException, Exception {
		String url = "http://api.map.baidu.com/geocoder/v2/?location="+coords+"&output=json&pois=0&ak="+SERVICE_BAIDUAK;
		HttpURLConnection urlConnection = (HttpURLConnection)(new URL(url).openConnection());
		urlConnection.connect();
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
		String lines = reader.readLine(); 
		reader.close(); 
		urlConnection.disconnect();
		JSONObject jsonObject = JSONObject.fromObject(lines);
		String status=jsonObject.getString("status");
		Map map = new HashMap();
		if(!status.equals("0")){
			if(status.equals("1")){
				log.error("服务器内部错误 ！");
			}else if(status.equals("2")){
				log.error("请求参数非法 ！");
			}else if(status.equals("3")){
				log.error("权限校验失败 ！");
			}else if(status.equals("4")){
				log.error("配额校验失败 ！");
			}else if(status.equals("5")){
				log.error("ak不存在或者非法 ！");
			}else if(status.equals("101")){
				log.error("服务禁用 ！");
			}else if(status.equals("102")){
				log.error("不通过白名单或者安全码不对 ！");
			}else if(status.equals("2xx")){
				log.error("无权限 ！");
			}else if(status.equals("3xx")){
				log.error("配额错误  ！");
			}else {
				log.error("转换异常！");
			}
		} else {
			map.put("status", status);
			String result=jsonObject.getString("result");
			JSONObject jsonObj = JSONObject.fromObject(result);
			String location=jsonObj.getString("location");
			JSONObject jsonlocation = JSONObject.fromObject(location);
			String lng=jsonlocation.getString("lng");
			map.put("lng", lng);
			String lat=jsonlocation.getString("lat");
			map.put("lat", lat);
			String formattedAddress=jsonObj.getString("formatted_address");
			map.put("formattedAddress", formattedAddress);
			String addressComponent=jsonObj.getString("addressComponent");
			JSONObject addressComponentJson = JSONObject.fromObject(addressComponent);
			String city = addressComponentJson.getString("city");
			map.put("city", city);
			String country = addressComponentJson.getString("country");
			map.put("country", country);
			String direction = addressComponentJson.getString("direction");
			map.put("direction", direction);
			String distance = addressComponentJson.getString("distance");
			map.put("distance", distance);
			String district = addressComponentJson.getString("district");
			map.put("district", district);
			String province = addressComponentJson.getString("province");
			map.put("province", province);
			String street = addressComponentJson.getString("street");
			map.put("street", street);
			String street_number = addressComponentJson.getString("street_number");
			map.put("street_number", street_number);
			String country_code = addressComponentJson.getString("country_code");
			map.put("country_code", country_code);
		}
		return map;
	}
	
	
	
	/**
	 * 根据当前的经纬度获取范围内的经纬度4个点
	 * @param lat 经度 23.126007
	 * @param lng 纬度 113.423990
	 * @param distance  距离 500
	 * @return
	 */
	public static double[][] getRectangle4Point(double lat, double lng,double distance) {
        double dlng = 2 * Math.asin(Math.sin(distance/(2*EARTH_RADIUS))/Math.cos(Math.toRadians(lat)));
        dlng = Math.toDegrees(dlng);
        double dlat = distance / EARTH_RADIUS;  
        dlat = Math.toDegrees(dlat); // # 弧度转换成角度
        double[][] locations = new double[][]{{lat + dlat, lng - dlng},//左上角
        		{lat + dlat, lng + dlng},//右上角
        		{lat - dlat, lng - dlng},//左下角
        		{lat - dlat, lng + dlng}};//右下角
        return locations;
    }
	
	public static double hav(double theta) {  
        double s = Math.sin(theta / 2);  
        return s * s;  
    }
	
	public static double getDistance(double lat0, double lng0, double lat1,double lng1) { 
        lat0 = Math.toRadians(lat0);  
        lat1 = Math.toRadians(lat1);  
        lng0 = Math.toRadians(lng0);  
        lng1 = Math.toRadians(lng1);
        double dlng = Math.abs(lng0 - lng1);  
        double dlat = Math.abs(lat0 - lat1);  
        double h = hav(dlat) + Math.cos(lat0) * Math.cos(lat1) * hav(dlng);  
        double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));
        return distance;  
    }  
	
	
	
	
	
	/**
	 * 获取距离（适合距离较近）
	 * @param lon1
	 * @param lat1
	 * @param lon2
	 * @param lat2
	 * @return
	 */
	public static double getShortDistance(double lon1, double lat1, double lon2,
			double lat2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 经度差
		dew = ew1 - ew2;
		// 若跨东经和西经180 度，进行调整
		if (dew > DEF_PI)
			dew = DEF_2PI - dew;
		else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
		// 勾股定理求斜边长
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}

	/**
	 * 获取距离（适合距离较远）
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
	
	/**
	 * 根据地址获取距离
	 * @param cityStart 起始地地市ID（北京市）
	 * @param districtStart 起始地县区ID（海淀区）
	 * @param adderDelStart 起始地详细地址（上地十街10号 ）
	 * @param cityDes   目的地地市ID（北京市）
	 * @param districtDes 目的地县区ID（海淀区)
	 * @param adderDelDes 目的地详细地址（上地十街10号 ）
	 * @return
	 * @throws Exception
	 */
	public static double getLongDistance(long cityStart,long districtStart,String adderDelStart,
			long cityDes,long districtDes,String adderDelDes) throws Exception{
		Map mapStart=getaddressLocation(cityStart,districtStart,adderDelStart);
		Map mapDes=getaddressLocation(cityDes,districtDes,adderDelDes);
		double distance=0;
		if(mapStart.get("lng")!=null && mapStart.get("lat")!=null 
				&& mapDes.get("lng")!=null && mapDes.get("lat")!=null){
			double lngStart = Double.parseDouble(mapStart.get("lng")+"");
			double latStart = Double.parseDouble(mapStart.get("lat")+"");
			double lngDes = Double.parseDouble(mapDes.get("lng")+"");
			double latDes = Double.parseDouble(mapDes.get("lat")+"");
			distance=getLongDistance(lngStart, latStart, lngDes, latDes);
		}
		return distance;
	}
	
	/**
	 * 关键地址搜索
	 * @param region
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public static List<Monitor> getSuggestion(String region,String query)throws Exception{
		List<Monitor> list = new ArrayList<Monitor>();
		if(region==null || region.equals("")){
			throw new BusinessException("请输入省份！");
		}
		if(query==null || query.equals("")){
			throw new BusinessException("请输入查询地址！");
		}
		region=URLEncoder.encode(region, "UTF-8");
		query=URLEncoder.encode(query, "UTF-8");
		String url = "http://api.map.baidu.com/place/v2/suggestion?query="+query+"&region="+region+"&output=json&ak="+SERVICE_BAIDUAK;
		HttpURLConnection urlConnection = (HttpURLConnection)(new URL(url).openConnection());
		urlConnection.connect();
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
		String res;
		StringBuilder sb = new StringBuilder("");
		while ((res = reader.readLine()) != null) {
			sb.append(res.trim());
		}
		reader.close();
		String str = sb.toString();
		JSONObject jsonObject = JSONObject.fromObject(str);
		String status=jsonObject.getString("status");
		if(!status.equals("0")){
			log.error("搜索错误。。。");
		}else{
			JSONArray jsonArray=jsonObject.getJSONArray("result");
			if(jsonArray.size()>0){
				for(int i=0;i<jsonArray.size();i++){
					JSONObject jsonResult=jsonArray.getJSONObject(i);
					String name=jsonResult.getString("name");
					String cityName=jsonResult.getString("city");
					String districtName=jsonResult.getString("district");
					try{
						String location=jsonResult.getString("location");
						JSONObject jsonlocation = JSONObject.fromObject(location);
						String lng=jsonlocation.getString("lng");
						String lat=jsonlocation.getString("lat");
						Monitor monitor= new Monitor();
						monitor.setName(name);
						monitor.setLat(lat);
						monitor.setLng(lng);
						monitor.setCityName(cityName);
						monitor.setDistrictName(districtName);
						monitor.setAddress(cityName+districtName);
						list.add(monitor);
					}catch(Exception e){
						log.error("没有经纬度信息不做处理...");
					}
				}
			}
		}
		return list;
	}
	
	
	
	public static void main(String []ager) throws Exception{
//		try {
//			int count=2;
//			String[] a=new String[count];
//			for(int i=0;i<count;i++){
//				a[i]="113.424327,23.125919";
//			}
//			Map map=GpsUtil.getBaiduLocation(a);
//			if(Integer.parseInt(map.get("status")+"")==0){
//				for(int i=0;i<count;i++){
//					String[] b = (String[])map.get(i+1);
//					System.out.println(i+1+"=="+b[0]+","+b[1]);
//				}
//			}
//			GpsUtil.getaddressLocation(1, 1,"");
//			GpsUtil.subuserId("1141111210350528");
//		GpsUtil.getBaiduAdder("23.12195,113.41248");
//     	System.out.println(GpsUtil.getBaiduAdder("23.12195,113.41248"));
//		System.out.println(GpsUtil.getGoodsLocationSql(23.126007, 113.423990, 5000));
		double mLat1 = 39.90923; // point1纬度
//		double mLon1 = 116.357428; // point1经度
//		double mLat2 = 39.90923;// point2纬度
//		double mLon2 = 116.397428;// point2经度
//		System.out.println(GpsUtil.getLongDistance(mLon1, mLat1, mLon2, mLat2));
//		System.out.println(GpsUtil.getBaiduAdder("广州市天河区科学大道162号"));
	//	System.out.println(GpsUtil.getLongDistance(1, 1, "", 1, 2, ""));
		Map<String,Double> map=GpsUtil.getLngAndLat("上海市黄浦区六合路");
		System.out.println("经度："+map.get("lng")+"---纬度："+map.get("lat"));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}			
	}
	
	public static Map<String,Double> getLngAndLat(String address){
		Map<String,Double> map=new HashMap<String, Double>();
		 String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak="+SERVICE_BAIDUAK;
	        String json = loadJSON(url);
	        JSONObject obj = JSONObject.fromObject(json);
	        if(obj.get("status").toString().equals("0")){
	        	double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
	        	double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
	        	map.put("lng", lng);
	        	map.put("lat", lat);
	        	//System.out.println("经度："+lng+"---纬度："+lat);
	        }else{
	        	//System.out.println("未找到相匹配的经纬度！");
	        }
		return map;
	}
	
	 public static String loadJSON (String url) {
	        StringBuilder json = new StringBuilder();
	        try {
	            URL oracle = new URL(url);
	            URLConnection yc = oracle.openConnection();
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                                        yc.getInputStream()));
	            String inputLine = null;
	            while ( (inputLine = in.readLine()) != null) {
	                json.append(inputLine);
	            }
	            in.close();
	        } catch (MalformedURLException e) {
	        } catch (IOException e) {
	        }
	        return json.toString();
	    }


	/**
	 * 获取两个点做为直径的类圆（矩形）的范围内的点
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public static Set<String> getBillsByGpsRang(double x1, double y1, double x2, double y2){
		double startX = x1, endX = x2, startY = y1, endY = y2;
		if (x1 > x2) {
			startX = x2;
			endX = x1;
		}
		if (y1 > y2) {
			startY = y2;
			endY = y1;
		}
		log.info("x1:"+ x1+ ", x2:" +x2+ ", y1:" +y1+ ",y2:" +y2);
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
				xSet.addAll(jedis.smembers(EnumConsts.RemoteCache.Vehicle_Gps_X_Set +i));
			}
			
			//取Y合集
			for (int i = yStartIdx; i <= yEndIdx; i++) {
				ySet.addAll(jedis.smembers(EnumConsts.RemoteCache.Vehicle_Gps_Y_Set +i));
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
	 * 根据手机号码及当前gps位置重置经纬度分片集合
	 * @param billId
	 * @param x
	 * @param y
	 */
	public static void setBillGpsPosition(String billId, double x, double y){
		//移除原先所处集合
		String[] position = getPositionByBillId(billId);
		String oldX = "0";
		String oldY = "0";
		if (position != null) {
			oldX = position[1]; //纬度
			oldY = position[0]; //经度			
		}
		RedisHelper helper = RedisHelper.getInstance();
		JedisCommands jedis = helper.getJedis();
		boolean broken = false;
		try {
			jedis.srem(EnumConsts.RemoteCache.Vehicle_Gps_X_Set + getShardIdex("X", Double.parseDouble(oldX)), billId);
			jedis.srem(EnumConsts.RemoteCache.Vehicle_Gps_Y_Set + getShardIdex("Y", Double.parseDouble(oldY)), billId);
			
			//增加当前位置集合
			jedis.sadd(EnumConsts.RemoteCache.Vehicle_Gps_X_Set + getShardIdex("X", x), billId);
			jedis.sadd(EnumConsts.RemoteCache.Vehicle_Gps_Y_Set + getShardIdex("Y", y), billId);
		} catch (JedisException e) {
	        broken = helper.handleJedisException(e);
	        throw e;
		} finally {
			helper.closeResource(jedis, broken);
		}
		
	}
	/**
	 * 根据手机号码查询上传的地理位置
	 * @param billId
	 * @return  经度|纬度|地理位置|城市|省份|区域
	 */
	public static String[] getPositionByBillId(String billId){
		String[] returnInfo = null;
		if(StringUtils.isNotEmpty(billId)){
			String position = RemoteCacheUtil.get(EnumConsts.RemoteCache.BillId_Gps_Position+billId);
			if(StringUtils.isNotEmpty(position)){
				//记录车辆当前位置信息(key:VGP_随车手机,value:经度|纬度|地理位置|城市|省份|区域)
				String allPos = String.valueOf(position);
				if(allPos!=null){
					String[] pos = allPos.split("\\|");
					if(pos!=null&&pos.length>=2){
						returnInfo = new String[6];
						returnInfo[0] = pos[0];
						returnInfo[1] = pos[1];
						if(pos.length >= 3){
							returnInfo[2] = pos[2];
							if(pos.length >= 4){
								returnInfo[3] = pos[3];
								if(pos.length >= 5){
									returnInfo[4] = pos[4];
									if(pos.length >= 6){
										returnInfo[5] = pos[5];
									}
								}
							}
						}
					}
				}
			}
	}
		return returnInfo;
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
	
}
