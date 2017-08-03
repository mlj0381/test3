
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.wo56.business.grabOrder.out.BusiGrabControlOut;

public class GrabTest {
	
	
	@BeforeClass
	public static void before() throws Exception{
		
	}
	@Test
	public  void addOrderInfo() throws Exception{
		String orderId="20170406123456";
		long busiUserId=12345;
		long zxTenantId=7;
		double latitude=23.1698780000;
		double longitude=113.4576670000;
		String orderNO="201704061000000";
		String createTime="2017-04-06 18:00:00";
		String handlerTime="2017-04-08 18:00:00";
		String pickAddr="广州创意大厦";
		String destCityName="杭州";
		BusiGrabControlOut.addOrderInfo(orderId, busiUserId, zxTenantId, latitude, longitude, orderNO, createTime, handlerTime, pickAddr,destCityName);
	}
	@Test
	public  void addUserInfo() throws Exception{
		long userId=123;
		long tenantId=7;
		double latitude=23.1698780000;
		double longitude=113.4576670000;
		int singularNum=1;
		int maxSingularNum=3;
		int restState=1;
		String billId="15920117242";
		BusiGrabControlOut.addUserInfo(userId, latitude, longitude, tenantId, singularNum, maxSingularNum, restState, billId);
	}
	
	@Test
	public  void getOrderInfoByDistance() throws Exception{
		
		double latitude=23.1698780000;
		double longitude=113.4576670000;		
		List<Map<String, Object>> retList=  BusiGrabControlOut.getOrderInfoByDistance(latitude, longitude, 50, 10);
		System.out.println(retList);
	}
	
	@Test
	public  void getUserByDistance() throws Exception{
//		double latitude=23.1688780000;
//		double longitude=113.4576670000;
		
		double latitude=23.1061250000;
		double longitude=113.5738580000;
		
		Set<String> retList=  BusiGrabControlOut.getUserByDistance(latitude, longitude, 500d);
		System.out.println(retList);
	}
	
	
}
