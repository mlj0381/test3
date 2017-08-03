
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.framework.core.util.JsonHelper;
import com.wo56.common.utils.EncryPwdUtil;

public class AppTest {
	
	private static  String tokenId="";
	
	@BeforeClass
	public static void before() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		//配送员
		//contentMap.put("billId", "13800138080");  
		//开单员 
		//拉包工
		//商家
		contentMap.put("billId", ""); 
		contentMap.put("pwd", EncryPwdUtil.pwdEncryption("123456"));
		
		String JsonStr= t.call(JsonHelper.json(contentMap),"601000","");
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
		tokenId=content.get("tokenId");
	}
	@Test
	public  void intf_601000() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("billId", "13800138005");
		contentMap.put("pwd", EncryPwdUtil.pwdEncryption("123456"));
		contentMap.put("appOsVer", "iOS10.2");
		contentMap.put("appVerNo", "1.0");
		contentMap.put("pushAppId", "");
		contentMap.put("pushChannelId", "");
		contentMap.put("mobileBrand", "IPhone");
		contentMap.put("mobileType", "iPhone");
		
		String JsonStr= t.call(JsonHelper.json(contentMap),"601000","");
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
//		tokenId=content.get("tokenId");
		Assert.assertNotNull(content.get("tokenId"));
	}
	@Test
	public  void intf_601001() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("billId", "13800138009");
		contentMap.put("password", EncryPwdUtil.pwdEncryption("123456"));
		contentMap.put("userName", "qiulinfeng");
		contentMap.put("userType", "7");
		contentMap.put("captcha", "803335");
		contentMap.put("appOsVer", "iOS10.2");
		contentMap.put("appVerNo", "1.0");
		contentMap.put("pushAppId", "");
		contentMap.put("pushChannelId", "");
		contentMap.put("mobileBrand", "IPhone");
		contentMap.put("mobileType", "iPhone");
		contentMap.put("province", "28");
		contentMap.put("city", "28297");
		contentMap.put("district", "282972811");
		contentMap.put("tenantId", "1");
		String JsonStr= t.call(JsonHelper.json(contentMap),"601001","");
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
//		tokenId=content.get("tokenId");
		Assert.assertNotNull(content.get("tokenId"));
	}
	@Test
	public  void intf_601003() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("billId", "13800138005");
		contentMap.put("busiType", "3");
		
		String JsonStr= t.call(JsonHelper.json(contentMap),"601003","");
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
//		tokenId=content.get("tokenId");
		Assert.assertNotNull(content.get("tokenId"));
	}
	@Test
	public  void intf_601016() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("billId", "13800138008");
		contentMap.put("captcha", "953275");
		
		String JsonStr= t.call(JsonHelper.json(contentMap),"601016","");
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
//		tokenId=content.get("tokenId");
		Assert.assertNotNull(content.get("tokenId"));
	}
	@Test
	public  void intf_600000() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("adderssDefault", "0");
		contentMap.put("address", "科学城创意大厦");
		contentMap.put("bill", "15098652422");
		contentMap.put("cityId", "10100");
		contentMap.put("districtId", "101001000");
		contentMap.put("eand", "0");
		contentMap.put("linkman", "thuiyu");
		contentMap.put("nand", "0");
		contentMap.put("provinceId", "10");
		contentMap.put("type", "1");
		String JsonStr= t.call(JsonHelper.json(contentMap),"600000",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
		
	}
	@Test
	public  void intf_600006() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("id", "46");
		contentMap.put("bill", "15892459777");
		contentMap.put("name", "ttt");
		contentMap.put("type", "2");
		String JsonStr= t.call(JsonHelper.json(contentMap),"600006",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public  void intf_601004() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		/*contentMap.put("tenantType", "1");*/
		String JsonStr= t.call(JsonHelper.json(contentMap),"601004","");
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public void intf_600008()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("id", "10");
		String JsonStr= t.call(JsonHelper.json(contentMap),"600008",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public void intf_600009()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		String JsonStr= t.call(JsonHelper.json(contentMap),"600009",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public void intf_600010()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("id", "8");
		contentMap.put("type", "0");
		String JsonStr= t.call(JsonHelper.json(contentMap),"600010",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public void intf_601008()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,Object> contentMap=new HashMap<String,Object>();
		contentMap.put("userId", "8");
		contentMap.put("equipmentNumber", "13800138005");
		List<Map<String,String>> list = new ArrayList<>();
		Map<String,String> map = new HashMap<>();
		map.put("nand", "90.00");
		map.put("eand", "80.00");
		map.put("sort", "1");
		list.add(map);
		//String[] str = new String[]{}
		contentMap.put("locations", list);
		String JsonStr= t.call(JsonHelper.json(contentMap),"601008",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public void intf_600011()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("varName", "15021651295");
		String JsonStr= t.call(JsonHelper.json(contentMap),"600011",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public void intf_600002()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("type", "1");
		String JsonStr= t.call(JsonHelper.json(contentMap),"600002",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public void intf_600001()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("id", "0");
		String JsonStr= t.call(JsonHelper.json(contentMap),"600001",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public void intf_600005()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("type", "1");
		String JsonStr= t.call(JsonHelper.json(contentMap),"600005",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public void intf_600003()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("id", "2");
		String JsonStr= t.call(JsonHelper.json(contentMap),"600003",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public void intf_600004()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("state", "1");
		contentMap.put("pointX", "22");
		contentMap.put("pointY", "22");
		String JsonStr= t.call(JsonHelper.json(contentMap),"600004",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
		org.junit.Assert.assertEquals("Y", content.get("info"));
	}
	@Test
	public void intf_600007()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("frontIdCard", "222");
		contentMap.put("backIdCard", "222");
		String JsonStr= t.call(JsonHelper.json(contentMap),"600007",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public void intf_600013()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		String JsonStr= t.call(JsonHelper.json(contentMap),"600013",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public void intf_600012()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		//支付宝测试
		/*contentMap.put("paymentType", "2");
		contentMap.put("accountNum","15021651295");
		contentMap.put("accountName", "唐辉玉");*/
		//微信测试
		/*contentMap.put("paymentType", "0");
		contentMap.put("accountNum","15021651295");
		contentMap.put("accountName", "唐辉玉");*/
		//银行卡测试
		contentMap.put("bankCard", "6217907000011673223");
		contentMap.put("paymentType", "1");
		contentMap.put("bankCode", "104");
		contentMap.put("bankHolder", "Gugenggeng");
		contentMap.put("bill", "13545253625");
		contentMap.put("cityId", "28297");
		contentMap.put("idCard", "445281199218121212");
		contentMap.put("provinceId", "28");
		String JsonStr= t.call(JsonHelper.json(contentMap),"600012",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public void intf_601015()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("userPicId", "11");
		String JsonStr= t.call(JsonHelper.json(contentMap),"601015",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public void intf_601012()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		//contentMap.put("type", "1");
		contentMap.put("isAll", "1");
		String JsonStr= t.call(JsonHelper.json(contentMap),"601012",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public void intf_602002()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("wayBill", "18820791677");
		String JsonStr= t.call(JsonHelper.json(contentMap),"602002",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public void intf_601022()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		//contentMap.put("wayBill", "17740886953");
		String JsonStr= t.call(JsonHelper.json(contentMap),"601022",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public  void intf_601006() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		String JsonStr= t.call(JsonHelper.json(contentMap),"601006",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public  void intf_601007() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		String JsonStr= t.call(JsonHelper.json(contentMap),"601007",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public  void intf_601024() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("carrierId", "11");//承运商公司ID
		contentMap.put("carrierName", "12");//承运商公司名称
		contentMap.put("collectMoney", "13");//代收货款
		contentMap.put("consignee", "14");//收货人
		contentMap.put("consigneeAddress", "15");//收货人地址
		contentMap.put("consigneePhone", "16");//收货人电话
		contentMap.put("consignor", "17");//发货人
		contentMap.put("consignorAddress", "18");//发货人地址
		contentMap.put("consignorPhone", "19");//发货人电话
		contentMap.put("deliveryCharge", "20");//送货费
		contentMap.put("desCity", "28297");//到站城市ID
		contentMap.put("desProvince", "28");//到站省份ID
		contentMap.put("freight", "23");//运费
		contentMap.put("interchange", "2");//交接方法
		contentMap.put("landFee", "25");//落地费
		contentMap.put("number", "26");//件数
		contentMap.put("orderNum", "27");//订单号
		contentMap.put("ordsId", "28");//下单id
		contentMap.put("otherFee", "29");//其他费用
		contentMap.put("packName", "30");//包装
		contentMap.put("payment", "1");//付款方式
		contentMap.put("premium", "32");//保费
		contentMap.put("productName", "33");//品名
		contentMap.put("pullId", "34");//拉包工ID
		contentMap.put("pullName", "35");//拉包工名称
		contentMap.put("remarks", "36");//备注
		contentMap.put("reputation", "37");//声价
		contentMap.put("serviceCharge", "38");//服务费
		contentMap.put("tip", "39");//小费
		contentMap.put("totalFee", "40");//总费用
		contentMap.put("traCity", "41");//中转城市ID
		contentMap.put("traProvince", "42");//中转省份ID
		contentMap.put("transitFee", "43");//中转费
		contentMap.put("volume", "44");//体积
		contentMap.put("weight", "45");//重量
		String JsonStr= t.call(JsonHelper.json(contentMap),"601024",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public  void intf_601025() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("weight", "100");
		contentMap.put("desProvince", "28");
		contentMap.put("desCity", "28297");
		contentMap.put("staProvince", "28");
		contentMap.put("staCity", "28297");
		contentMap.put("volume", "120");
		String JsonStr= t.call(JsonHelper.json(contentMap),"601025",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public  void intf_601011() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		
		String JsonStr= t.call(JsonHelper.json(contentMap),"601011",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public  void intf_601019() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("tenantId", "1");
		String JsonStr= t.call(JsonHelper.json(contentMap),"601019",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public  void intf_601029() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("tenantId", "1");
		String JsonStr= t.call(JsonHelper.json(contentMap),"601029",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public  void intf_601030() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("accountTime", "2017-02");
		String JsonStr= t.call(JsonHelper.json(contentMap),"601030",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public  void intf_601031() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,Object> contentMap=new HashMap<String,Object>();
		List<String> str = new ArrayList<String>();
		str.add("3");
		contentMap.put("tenantId", "1");
		contentMap.put("ids", str);
		String JsonStr= t.call(JsonHelper.json(contentMap),"601031",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public  void intf_601017() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,Object> contentMap=new HashMap<String,Object>();
		List<String> str = new ArrayList<String>();
		str.add("3");
		contentMap.put("tenantId", "1");
		contentMap.put("amountState", "2");
		//contentMap.put("ids", str);
		String JsonStr= t.call(JsonHelper.json(contentMap),"601017",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public  void intf_601020() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,Object> contentMap=new HashMap<String,Object>();
		List<String> str = new ArrayList<String>();
		str.add("3");
		contentMap.put("id", "2");
		//contentMap.put("ids", str);
		String JsonStr= t.call(JsonHelper.json(contentMap),"601020",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public  void intf_601028() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,Object> contentMap=new HashMap<String,Object>();
		contentMap.put("tenantId", "1");
		String JsonStr= t.call(JsonHelper.json(contentMap),"601028",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	
	@Test
	public void intf_601009()throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("billId", "13800138005");
		contentMap.put("captcha", "730211");
		contentMap.put("newLoginPwd", "1234567");
		contentMap.put("oldLoginPwd", "123456");
		String JsonStr= t.call(JsonHelper.json(contentMap),"601009",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
	}
	@Test
	public  void intf_602005() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		String JsonStr= t.call(JsonHelper.json(contentMap),"602005",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
		
	}
	@Test
	public  void intf_700006() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		String JsonStr= t.call(JsonHelper.json(contentMap),"700006",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
		
	}
	
	@Test
	public  void intf_700008() throws Exception{
		OuterTestPost t = new OuterTestPost();
		Map<String,String> contentMap=new HashMap<String,String>();
		contentMap.put("orderId", "80004825");
		String JsonStr= t.call(JsonHelper.json(contentMap),"700008",tokenId);
		Map<String, String> content=(Map<String, String>) JsonHelper.parseJSON2Map(JsonStr).get("content");
		
	}
	
}
