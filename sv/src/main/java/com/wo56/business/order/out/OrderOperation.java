package com.wo56.business.order.out;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.util.DateUtil;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.route.impl.GraphRouteSV;
import com.wo56.common.consts.EnumConstsYQ;
import com.wo56.common.consts.SysStaticDataEnumYunQi;

public class OrderOperation {
	private static final Log log = LogFactory.getLog(GraphRouteSV.class);
	
	private static final String KEY_INFO = "KEY_INFO";//接口
	private static final String KEY_ORDER_NUMBER = "KEY_ORDER_NUMBER";//运单号
	private static final String KEY_USER_ID = "KEY_USER_ID";//用户
	private static final String KEY_DATE = "KEY_DATE";//时间
	private static final String KEY_CREATE_ID = "KEY_CREATE_ID";//创建人
	private static final String KEY_PASS_PORT = "KEY_PASS_PORT"; //证据类型  身份证，军官证等
	private static final String KEY_PASS_PORT_NO = "KEY_PASS_PORT_NO"; //证件编号
	private static final String KEY_SIGN_DATE = "KEY_SIGN_DATE"; //签收日期
	private static final String KEY_SIGN_MAN = "KEY_SIGN_MAN"; //签收人
	private static final int SIGN_TYPE = 3;
	private static final int ARRIVAL_OR_DISPATCHING_TYPE = 2;
	private static final int CANCEL_ORDER_TYPE = 1;
	
	
	/**
	 * 取消订单
	 * @return
	 * @throws Exception 
	 */
	public static String cancelOrder(String url,String intf,String cargoNO,String userid,long tenantId,Map<String,String> map,boolean isTask) throws Exception{
		if (!isOpen(tenantId,SysStaticDataEnumYunQi.TMS_OPEN.TMS_CANCEL_ORDER)&&!isTask) {
			log.error("关闭TMS取消订单接口调用！租户为："+tenantId);
			return "Y";
		}
		return postUrl(url, intf, cargoNO, userid, "", "", "", "", "", "", map, CANCEL_ORDER_TYPE);
	}
	/**
	 * 配送&到货
	 * @param url
	 * @param intf
	 * @param cargoNO
	 * @param userid
	 * @param date
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String arrivalOrDispatcheing(String url,String intf,String cargoNO,String userid,long tenantId,String date,Map<String,String> map,int type,boolean isTask) throws Exception{
		if (type == 1) {
			if (!isOpen(tenantId,SysStaticDataEnumYunQi.TMS_OPEN.TMS_ARRIVAL)&&!isTask) {
				log.error("关闭TMS到货接口调用！租户为："+tenantId);
				return "Y";
			}
		}else{
			if (!isOpen(tenantId,SysStaticDataEnumYunQi.TMS_OPEN.TMS_DISPATCHING)&&!isTask) {
				log.error("关闭TMS到货接口调用！租户为："+tenantId);
				return "Y";
			}
		}
		
		return postUrl(url, intf, cargoNO, userid, date, "", "", "", "", "", map, ARRIVAL_OR_DISPATCHING_TYPE);
	}
	/**
	 * 签收
	 * @param url
	 * @param intf
	 * @param cargoNO
	 * @param userid
	 * @param date
	 * @param createID
	 * @param passPort
	 * @param passPortNo
	 * @param signDate
	 * @param signMan
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String sign(String url, String intf,String cargoNO, String userid,String date,
			String createID,String passPort,String passPortNo, String signDate,String signMan,long tenantId,
			Map<String, String> param,boolean isTask) throws Exception{
		if (!isOpen(tenantId,SysStaticDataEnumYunQi.TMS_OPEN.TMS_SIGN) && !isTask) {
			log.error("关闭TMS签收接口调用！租户为："+tenantId);
			return "Y";
		}
		String json = "{\"cargoNO\":\""+cargoNO+"\","
				+ "\"sign_man\":\""+userid+"\","
				+ "\"passPort\":\""+passPort+"\","
				+ "\"passPortNO\":\""+passPortNo+"\","
				+ "\"sign_Date\":\""+signDate+"\","
				+ "\"createID\":\""+signMan+"\","
				+ "\"createDate\":\""+date+"\"}";
		log.error("签收请求柯莱："+json);
		
		return sendPost(url, "action="+EnumConstsYQ.KL_INFO.SIGN+"&json="+URLEncoder.encode(json,"utf-8"),cargoNO);
	}
	/**
	 * 开单
	 * @param items
	 * @param uri
	 * @param keyIntf
	 * @param tenantId
	 * @param isTask
	 * @return
	 * @throws Exception
	 */
	public static String billingOrder(List<Map<String,Object>> items,String uri,String keyIntf,long tenantId,boolean isTask) throws Exception{
		if (!isOpen(tenantId,SysStaticDataEnumYunQi.TMS_OPEN.TMS_BILLING) && !isTask) {
			log.error("关闭TMS开单接口调用！租户为："+tenantId);
			return "Y";
		}
		return billingOrderPostUrl(items, uri, keyIntf);
	}
	
	/**
	 * 开单信息POST
	 * @param items
	 * @param uri
	 * @param keyIntf
	 * @return
	 * @throws Exception 
	 */
	public static String billingOrderPostUrl(List<Map<String,Object>> items,String uri,String keyIntf) throws Exception{
		String json = "{\"items\":[{\"selfNumber\":\""+items.get(0).get("selfNumber")+"\","
				+ "\"collectMoneyDouble\":\""+items.get(0).get("collectMoneyDouble")+"\","
				+ "\"billingOrgName\":\""+items.get(0).get("billingOrgName")+"\","
				+ "\"orderNumber\":\""+items.get(0).get("orderNumber")+"\","
				+ "\"premiumDouble\":\""+items.get(0).get("premiumDouble")+"\","
				+ "\"landFeeDouble\":\""+items.get(0).get("landFeeDouble")+"\","
				+ "\"productName\":\""+items.get(0).get("productName")+"\","
				+ "\"desDistrictName\":\""+items.get(0).get("desDistrictName")+"\","
				+ "\"number\":\""+items.get(0).get("number")+"\","
				+ "\"carrierName\":\""+items.get(0).get("carrierName")+"\","
				+ "\"consignorAddress\":\""+items.get(0).get("consignorAddress")+"\","
				+ "\"serviceChargeDouble\":\""+items.get(0).get("serviceChargeDouble")+"\","
				+ "\"consigneePhone\":\""+items.get(0).get("consigneePhone")+"\","
				+ "\"deliveryChargeDouble\":\""+items.get(0).get("deliveryChargeDouble")+"\","
				+ "\"arrivedOrgName\":\""+items.get(0).get("arrivedOrgName")+"\","
				+ "\"paymentName\":\""+items.get(0).get("paymentName")+"\","
				+ "\"consignor\":\""+items.get(0).get("consignor")+"\","
				+ "\"consigneeAddress\":\""+items.get(0).get("consigneeAddress")+"\","
				+ "\"transitFeeDouble\":\""+items.get(0).get("transitFeeDouble")+"\","
				+ "\"desCityName\":\""+items.get(0).get("desCityName")+"\","
				+ "\"consignee\":\""+items.get(0).get("consignee")+"\","
				+ "\"totalFeeDouble\":\""+items.get(0).get("totalFeeDouble")+"\","
				+ "\"otherFeeDouble\":\""+items.get(0).get("otherFeeDouble")+"\","
				+ "\"freightDouble\":\""+items.get(0).get("freightDouble")+"\","
				+ "\"weight\":\""+items.get(0).get("weight")+"\","
				+ "\"pullName\":\""+items.get(0).get("pullName")+"\","
				+ "\"consignorPhone\":\""+items.get(0).get("consignorPhone")+"\","
				+ "\"desProvinceName\":\""+items.get(0).get("desProvinceName")+"\","
				+ "\"volume\":\""+items.get(0).get("volume")+"\","
				+ "\"reputationDouble\":\""+items.get(0).get("reputationDouble")+"\","
				+ "\"createTime\":\""+items.get(0).get("createTime")+"\","
				+ "\"interchangeName\":\""+items.get(0).get("interchangeName")+"\","
				+ "\"pullPhone\":\""+items.get(0).get("pullPhone")+"\","
				+ "\"tipDouble\":\""+items.get(0).get("tipDouble")+"\","
				+ "\"packName\":\""+items.get(0).get("packName")+"\","
				+ "\"createName\":\""+items.get(0).get("createName")+"\","
				+ "\"remarks\":\""+items.get(0).get("remarks")+"\","
				+ "\"pickingCostsDouble\":\""+items.get(0).get("pickingCostsDouble")+"\"}]}";
		String out = sendPost(uri,"action="+EnumConstsYQ.KL_INFO.BILLING+"&json="+URLEncoder.encode(json,"utf-8"), String.valueOf(items.get(0).get("orderNumber")));
		log.error("json:"+json+",out:"+out);
		return out;
	}
	
	 
	
	/**
	 * post数据
	 * @param url
	 * @param intf
	 * @param cargoNO
	 * @param userid
	 * @param date
	 * @param createID
	 * @param passPort
	 * @param passPortNo
	 * @param signDate
	 * @param signMan
	 * @param param
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private static String postUrl(String url, String intf,String cargoNO, String userid,String date,
			String createID,String passPort,String passPortNo, String signDate,String signMan,
			Map<String, String> param,int type) throws Exception{
		
		HttpUriRequest httpUriRequest = null ;
		if (type == CANCEL_ORDER_TYPE) {//取消订单
			Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE,ContentType.create("application/x-www-form-urlencoded", Consts.UTF_8).toString());
			httpUriRequest = RequestBuilder.post().addHeader(header).setUri(url).addParameter(param.get(KEY_INFO), intf)
					.addParameter(param.get(KEY_ORDER_NUMBER), URLEncoder.encode(cargoNO, "UTF-8"))
					.addParameter(param.get(KEY_USER_ID), URLEncoder.encode(userid, "UTF-8"))
					.build();
		}else if (type == ARRIVAL_OR_DISPATCHING_TYPE) {//配送&到货
			Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE,ContentType.create("application/x-www-form-urlencoded", Consts.UTF_8).toString());
			httpUriRequest = RequestBuilder.post().addHeader(header).setUri(url).addParameter(param.get(KEY_INFO), intf)
					.addParameter(param.get(KEY_ORDER_NUMBER),cargoNO)
					.addParameter(param.get(KEY_USER_ID), userid)
					.addParameter(param.get(KEY_DATE),date)
					.build();
		}else if (type == SIGN_TYPE) {//签收
			Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE,ContentType.create("text/plain", Consts.UTF_8).toString());
			Map<String,String> map = new HashMap<String, String>();
			map.put("cargoNO", cargoNO);
			map.put("sign_man", userid);
			map.put("passPort", passPort);
			map.put("passPortNO", passPortNo);
			map.put("sign_Date", signDate);
			map.put("createID", signMan);
			map.put("createDate", date);
			String json = JsonHelper.json(map);
			httpUriRequest = RequestBuilder.get().addHeader(header).setUri(url).addParameter(param.get(KEY_INFO), intf)
					.addParameter("json",json)
					.build();
		}
//		log.error("请求参数：cargoNO："+cargoNO+"，userid："+userid+"，date："+date+"，createID："+createID+"，passPort："+passPort+"，passPortNo："+passPortNo+"，sign_Date："+signDate+"，sign_man："+signMan);
		String out = "请求参数：cargoNO："+cargoNO+"，userid："+userid+"，date："+date+"，createID："+createID+"，passPort："+passPort+"，passPortNo："+passPortNo+"，sign_Date："+signDate+"，sign_man："+signMan;
		return outJson(httpUriRequest,param,cargoNO,out);
	}
	
	@SuppressWarnings({ "deprecation", "resource" })
	private static String outJson(HttpUriRequest httpUriRequest,Map<String,String> map,String orderNumber,String outJson) throws IOException {
		log.error("请求外部接口开始：运单号"+orderNumber+"入参："+outJson);
		Date date = new Date();
		HttpClient client = new DefaultHttpClient();
//		client.getParams().setIntParameter(CoreConnectionPNames.SO_LINGER, 2000);
//		client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
//		client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
		/*连接超时*/ 
		StringBuffer out = new StringBuffer(200);
		try {
			log.error("发送请求时间："+ DateUtil.formatDateByFormat(date, "yyyy-MM-dd HH:mm:ss"));
			HttpResponse res = client.execute(httpUriRequest);
			
			if (res.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = res.getEntity();
				String cs = EntityUtils.getContentCharSet(entity);
				InputStream is = entity.getContent();
				InputStream inStream = new BufferedInputStream(is);
				byte[] buffer = new byte[2024];
				int iRead;
				while ((iRead = inStream.read(buffer)) != -1) {
					out.append(new String(buffer, 0, iRead, Charset.forName(cs)));
				}
			}else{
				log.error("url:"+httpUriRequest.getURI()+"报错["+res.getStatusLine().getStatusCode()+"]");
			}
		} catch (Exception e) {
			log.error("调用tms接口报错"+httpUriRequest.getURI(), e);
			client.getConnectionManager().shutdown();
			throw new RuntimeException(e);
		}
		Date endDate = new Date();
		long runAction = endDate.getTime()-date.getTime();
		
		log.error("发送的请求:["+httpUriRequest.getURI()+"]返回的数据"+out+"发送时间结束："+ DateUtil.formatDateByFormat(endDate, "yyyy-MM-dd HH:mm:ss")+",合计共用："+runAction);
		return out.toString();
	}
	/**
	 * 是否开启调TMS
	 * false 不开启
	 * true 开启
	 * @param tenantId
	 * @param type
	 * @return
	 */
	public static boolean isOpen(long tenantId,String type){
		//long tenantId = SysContexts.getCurrentOperator().getTenantId();
		List<SysStaticData> list = SysStaticDataUtil.getSysStaticDataList(type);
		if (list != null && list.size() > 0) {
			for (SysStaticData sysStaticData : list) {
				if (sysStaticData.getCodeId() == tenantId) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	/**
	 * 柯莱用这个方法post
	 * @param args
	 */
	public static void main(String[] args) {
		
//		 sendPost("http://122.13.138.14/Mobile/Action/action.ashx","action=1005&json={\"items\":[{\"selfNumber\":\"1111\",\"collectMoneyDouble\":\"0\",\"billingOrgName\":\"广州市广州总部\",\"orderNumber\":\"10000022\",\"premiumDouble\":\"0\",\"landFeeDouble\":\"0\",\"productName\":\"普货\",\"desDistrictName\":\"\",\"number\":1,\"carrierName\":\"柯莱物流\",\"consignorAddress\":\"联和街道开泰大道162号,18620166271\",\"serviceChargeDouble\":\"0\",\"consigneePhone\":\"13800138005\",\"deliveryChargeDouble\":\"0\",\"arrivedOrgName\":\"金华市义乌\",\"paymentName\":\"到付\",\"consignor\":\"测试\",\"consigneeAddress\":\"发谁的等待的成绩坚持坚持\",\"transitFeeDouble\":\"0\",\"desCityName\":\"金华市\",\"consignee\":\"测试\",\"totalFeeDouble\":\"640.00\",\"otherFeeDouble\":\"0\",\"freightDouble\":\"640.00\",\"weight\":\"1\",\"pullName\":\"\",\"consignorPhone\":\"1111\",\"desProvinceName\":\"浙江\",\"volume\":\"4\",\"reputationDouble\":\"0\",\"createTime\":\"2017-06-07 22:28:30\",\"interchangeName\":\"配送\",\"pullPhone\":\"\",\"tipDouble\":\"128.00\",\"packName\":\"托盘\",\"createName\":\"周范纯\",\"remarks\":\"\"}]}");
	}
    
	public static String sendPost(String url, String param,String orderNumber) {
		log.error("请求外部接口开始：运单号："+orderNumber+"入参："+param);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection)realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            throw new  BusinessException ("发送POST请求出现异常:"+e.getMessage());
        } finally {
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
                if (conn!=null){
                	conn.disconnect();
                }
            }catch(IOException ex){
            	throw new  BusinessException ("发送POST请求出现异常:"+ex.getMessage());
            }
        }
        log.error("签收请求返回参数："+result);
        return result;
    }


    

   
}
