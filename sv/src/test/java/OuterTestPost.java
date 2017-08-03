
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.framework.core.encrypt.MD5;

public class OuterTestPost {
	final static Charset utf8 = Charset.forName("UTF-8");
	
	
	public String post(String url, String str) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		StringBuffer out = new StringBuffer(200);
		try {
			
			StringEntity s = new StringEntity(str, utf8);
			s.setContentEncoding(utf8.name());
			s.setContentType("application/json");
			post.setEntity(s);
			HttpResponse res = client.execute(post);
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
				System.out.println(out);
			}
		} catch (Exception e) {
			client.getConnectionManager().shutdown();
			throw new RuntimeException(e);
		}
		return out.toString();
	}
	

	
	public String postStr(String url,Map<String, Object> map){
		return this.postStr(url, getUrlParamsByMap(map));
	}
	public String postStr(String url,String str){
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		StringBuffer out = new StringBuffer(200);
		try {
			StringEntity s = new StringEntity(str, utf8);
			s.setContentEncoding(utf8.name());
			s.setContentType("application/x-www-form-urlencoded");
			post.setEntity(s);
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = res.getEntity();
				String cs = EntityUtils.getContentCharSet(entity);
				InputStream is = entity.getContent();
				InputStream inStream = new BufferedInputStream(is);
				byte[] buffer = new byte[1024];
				int iRead;
				while ((iRead = inStream.read(buffer)) != -1) {
					out.append(new String(buffer, 0, iRead, Charset.forName(cs)));
				}
			}
		} catch (Exception e) {
			client.getConnectionManager().shutdown();
			throw new RuntimeException(e);
		}
		return out.toString();
	}
	
	
	public String call(String content,String inCode,String tokenId){
		Random rand = new Random();
		String appId = "APP";
		String secretKey = "0E7D290524A5943KDD4224167DBF45F4";
		String rd = String.valueOf(rand.nextInt(1000));
		String time = String.valueOf(System.currentTimeMillis());
		String[] arry = { secretKey, tokenId, time, rd, content };
		Arrays.sort(arry);
		System.out.println(Arrays.toString(arry));
		String sign = MD5.eccryptSHA(Arrays.toString(arry));
		System.out.println("::::::" + sign);
		String json = "{\"content\":"+content+",\"inCode\":\""+inCode+"\",\"appId\":\""+appId+"\",\"tokenId\":\""+tokenId+"\",\"time\":\""+time+"\",\"rd\":\""+rd+"\",\"sign\":\""+sign+"\"}";
		System.out.println(json);
//		String url = "http://58.67.143.220:89/intf";
//		String url = "http://192.168.1.250:89/intf";
		String url = "http://localhost:19002/intf";
//		String url = "http://pt.yq.wo56.com/intf";
		return this.post(url, json);
	}
	
	public  String getUrlParamsByMap(Map<String, Object> map) {  
	    if (map == null) {  
	        return "";  
	    }  
	    StringBuffer sb = new StringBuffer();  
	    for (Map.Entry<String, Object> entry : map.entrySet()) {  
	        sb.append(entry.getKey() + "=" + entry.getValue());  
	        sb.append("&");  
	    }  
	    String s = sb.toString();  
	    if (s.endsWith("&")) {  
	        s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");  
	    }  
	    return s;  
	}  

	
	/**
	 * @param args
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws Exception {
		String s = "%E6%9D%8E%E5%A5%95%E9%87%8E";
	s="%25E6%259D%258E%25E5%25A5%2595%25E9%2587%258E";
		System.out.println(URLDecoder.decode(URLDecoder.decode(s,"utf8"),"utf-8"));
		String name="";
		System.out.println(URLEncoder.encode(name,"utf-8"));
//		OuterTestPost t = new OuterTestPost();
//		Random rand = new Random();
//		String tokenId = "000000000000000";
//		String appId = "YZT";
//		String secretKey = "KDD4224167DBF45F2MIe43KDD42241X5";
//		String rd = String.valueOf(rand.nextInt(1000));
//		String time = String.valueOf(System.currentTimeMillis());
//		String content = "{\"userCode\":\"15920117244\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\"}";
////		content = Base64.encodeBase64StringUnChunked(content.getBytes(utf8));
//		String[] arry = { secretKey, tokenId, time, rd, content };
//		Arrays.sort(arry);
//		System.out.println(Arrays.toString(arry));
//		String sign = MD5.eccryptSHA(Arrays.toString(arry));
//		System.out.println("::::::" + sign);
//		String json = "{\"content\":"+content+",\"inCode\":\"300000\",\"appId\":\""+appId+"\",\"tokenId\":\""+tokenId+"\",\"time\":\""+time+"\",\"rd\":\""+rd+"\",\"sign\":\""+sign+"\"}";
//		System.out.println(json);
//		String url = "http://127.0.0.1:17002/intf";
//		t.post(url, json);
		
//		OuterTestPost t = new OuterTestPost();
//		System.out.println(t.getEncode("http://if.1ziton.com:8090/smsCenter/sms/sendSMS"));
	}

}
