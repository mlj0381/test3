package com.wo56.business.ord;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.components.fdfs.impl.SysAttachFileBO;
import com.framework.components.fdfs.vo.SysAttach;
import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.cache.vo.SysCfg;
import com.framework.core.exception.BusinessException;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.SysCfgUtil;
import com.framework.core.util.impl.SysConfig;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.ord.intf.IOrdOrdersWebIntf;
import com.wo56.business.ord.intf.IOrderInfoIntf;
import com.wo56.business.ord.vo.OrdSignInfo;
import com.wo56.business.ord.vo.OrderFee;
import com.wo56.business.ord.vo.in.OrderShareIn;
import com.wo56.business.ord.vo.in.OrdersTipWeCahtIn;
import com.wo56.business.ord.vo.out.OrdChildOrderOpLogListOut;
import com.wo56.business.ord.vo.out.OrdDetailOutParam;
import com.wo56.business.ord.vo.out.OrdGoodsInfoOutParam;
import com.wo56.business.ord.vo.out.OrdOpLogListOut;
import com.wo56.business.ord.vo.out.TrackListOutParam;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.InterFacesCodeConsts;
import com.wo56.common.utils.QrCodeUtil;

public class OrdOrdersWebBO extends BaseBO{
	private static final Log log = LogFactory.getLog(OrdOrdersWebBO.class);

	private static final int IMA_WIDTH = 350;  
    private static final int IMA_HEIGHT = 350;  
	/**
	 * 任务统计
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String statisticsTaskCount() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken<Map<String,Integer>>(){}.getType());
		Map<String,Integer> rtnMap = sv.statisticsTaskCount(map);
		return JsonHelper.json(rtnMap);
	}
	
	/**
	 * 列表查询
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doQuery() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken< Map<String, Object>>(){}.getType());
		 Map<String, Object> rtnMap = sv.doQuery(map);
		return JsonHelper.json(rtnMap);
	}
	
	/**
	 * 列表查询
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String reminderQuery() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken< Map<String, Object>>(){}.getType());
		 Map<String, Object> rtnMap = sv.reminderQuery(map);
		return JsonHelper.json(rtnMap);
	}
	
	/**
	 * 订单管理列表查询
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String queryOrders() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken< Map<String, Object>>(){}.getType());
		 Map<String, Object> rtnMap = sv.queryOrders(map);
		return JsonHelper.json(rtnMap);
	}
	/**
	 * 运单管理列表查询
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String queryTracks() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken< Map<String, Object>>(){}.getType());
		Map<String, Object> rtnMap = sv.queryTracks(map);
		return JsonHelper.json(rtnMap);
	}
	

	/**
	 * 待发车列表查询
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String queryWaitDeparts() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken< Map<String, Object>>(){}.getType());
		Map<String, Object> rtnMap = sv.queryWaitDeparts(map);
		return JsonHelper.json(rtnMap);
	}
	/**
	 * 运单管理子运单列表查询
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String querychildOrderInfo() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken< Map<String, Object>>(){}.getType());
		Map<String, Object> rtnMap = sv.querychildOrderInfo(map);
		return JsonHelper.json(rtnMap);
	}
	
	
	/**
	 *漂浮框查询多个提货信息
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String queryPickInfo() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken<List<Map<String,Object>>>(){}.getType());
		List<Map<String,Object>> queryPickInfo = sv.queryPickInfo(map);
		return JsonHelper.json(queryPickInfo);
	}
	
	/**
	 * 查找拉包工
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String queryWorker() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken< Map<String, Object>>(){}.getType());
		Map<String, Object> rtnMap = sv.queryWorker(map);
		return JsonHelper.json(rtnMap);
	}
	
	/**
	 * 分配拉包工
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String disReceipt() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken< Map<String, Object>>(){}.getType());
		 Map<String, Object> rtnMap = sv.disReceipt(map);
		return JsonHelper.json(rtnMap);
	}
	
	/**
	 * 分享订单
	 * @param inParam
	 * @return
	 */
	public String getOrdersInfo() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		OrdersTipWeCahtIn in = new OrdersTipWeCahtIn();
		BeanUtils.populate(in, map);
		SimpleOutParamVO<Map<String,Object>> out = new SimpleOutParamVO<Map<String,Object>>();
		
		in.setInCode(InterFacesCodeConsts.SBITIP.QUERY_ORDER_DETAIL);
		out = CallerProxy.call(in, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		
		Map orderInfoMap= out.getContent();
		
		if(orderInfoMap.get("orderNo")==null){
			throw new BusinessException("查询不到订单数据！");
		}
		
		String orderNo=orderInfoMap.get("orderNo").toString();
		
		
		SysAttachFileBO sysAttachFile = (SysAttachFileBO) SysContexts.getBean("attach");
		//生成二维码图片
		ByteArrayOutputStream outStrem=null;
		ByteArrayInputStream inputStream=null;
		try {
			outStrem= new ByteArrayOutputStream();
			SysCfg sysCfg = SysCfgUtil.getSysCfg(EnumConsts.SysCfg.QR_CODE_PRE,3);
			
			//测试生成二维码
//			InputStream io = Resource.loadInputStreamFromClassPath("/image/weChat/icon_wx.png");
			String logoUrl=SysConfig.getConfigValueFromKey("logo.url");
			//http://localhost:10002/image/weChat/icon_wx.png
			URL url = new URL(logoUrl); 
			
			 HttpURLConnection conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.  
	         conn.setDoInput(true);  
	         conn.connect();  
	         InputStream io = conn.getInputStream();
			
			//本地测试生成二维码
			/*File f=new File("G:/eclipseWorkspace/vpyqDev/woyqDev/web/html/image/weChat/icon_wx.png"); 
			InputStream io =new FileInputStream(f);*/
			QrCodeUtil.encode(orderNo,IMA_WIDTH, IMA_HEIGHT, io,outStrem);
			
			byte[] byteArray = outStrem.toByteArray();
			inputStream= new ByteArrayInputStream(byteArray);
			int size = byteArray.length;
			String fileName=in.getOrderId()+".jpg";
			SysAttach sysAttach = sysAttachFile.doUpload(inputStream, fileName, size);
			String qrCodeUrl=sysAttach.getFullPathUrl();
			String qrCodeId = String.valueOf(sysAttach.getFlowId());
			
			in.setQrCodeId(qrCodeId);
			in.setQrCodeUrl(qrCodeUrl);
			
			in.setInCode(InterFacesCodeConsts.SBITIP.sbiTip);
			out = CallerProxy.call(in, new TypeToken<SimpleOutParamVO<Map<String,Object>>>(){}.getType());

			
		} catch (Exception e) {
			log.error("生成二维码名片出错,堆栈信息如下:",e);
			throw new BusinessException("生产二维码错误,订单号["+in.getOrderId()+"]");
		}finally {
              if (inputStream != null) {
            	  inputStream.close();
              }
              if (outStrem != null) {
            	  outStrem.flush();
            	  outStrem.close();
              }
          }
		
		return JsonHelper.json(out.getContent());
	}
	/**
	 * 到货操作
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String gxArriveGoods() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken< Map<String, Object>>(){}.getType());
		 Map<String, Object> rtnMap = sv.gxArriveGoods(map);
		return JsonHelper.json(rtnMap);
	}


	/**
	 * 配送操作
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String deliveryOrder() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken< Map<String, Object>>(){}.getType());
		 Map<String, Object> rtnMap = sv.deliveryOrder(map);
		return JsonHelper.json(rtnMap);
	}
	
	/**
	 * 运单详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String ordDetails() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken<OrdDetailOutParam>(){}.getType());
		OrdDetailOutParam ordDetails = sv.ordDetails(map);
		return JsonHelper.json(ordDetails);
	}
	
	
	//运单基本信息
		public String ordInfoDetails() throws Exception{
			Map<String, Object> map = SysContexts.getRequestParamFlatMap();
			IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken<OrdDetailOutParam>(){}.getType());
			OrdDetailOutParam rtnMap = sv.ordInfoDetails(map);
			return JsonHelper.json(rtnMap);
		}
		
	
	/**
	 * 下单发货信息详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String  queryGoodsInfo() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken<List<OrdGoodsInfoOutParam>>(){}.getType());
	    List<OrdGoodsInfoOutParam> list = sv.queryGoodsInfo(map);
		return JsonHelper.json(list);
	}
	
	/**
	 * 开单费用信息详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String  queryFee() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken<OrderFee>(){}.getType());
	     OrderFee queryFee = sv.queryFee(map);
	     if(queryFee!=null){
		return JsonHelper.json(queryFee);
	     }else{
	    	 return "";
	     }
	}
	
	/**
	 * 签收信息详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String  querySignInfo() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken<OrdSignInfo>(){}.getType());
	     OrdSignInfo signInfo = sv.querySignInfo(map);
	     if(signInfo!=null){
	    	 return JsonHelper.json(signInfo);
	     }else{
	    	 return "";
	     }
	}
	/**
	 * 跟踪信息详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String  queryLogByRole() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken<List<OrdOpLogListOut>>(){}.getType());
	     List<OrdOpLogListOut> list = sv.queryLogByRole(map);
		return JsonHelper.json(list);
	}
	
	/**
	 * 跟踪子运单信息详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String  queryChildOrderLogByRole() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrderInfoIntf sv = CallerProxy.getSVBean(IOrderInfoIntf.class, "orderInfoTF", new TypeToken<Map<String,Object>>(){}.getType());
		Map<String, Object> list = sv.queryChildOrderLogByRole(map);
		return JsonHelper.json(list);
	}
	
	/**
	 * web 订单签收
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String  signUp() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken<Map<String,Object>>(){}.getType());
	    Map<String, Object> rtnMap = sv.signUp(map);
		return JsonHelper.json(rtnMap);
	}
	
	/**
	 * web 运单签收
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String  signUpOrderInfo() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrderInfoIntf sv = CallerProxy.getSVBean(IOrderInfoIntf.class, "orderInfoTF", new TypeToken<Map<String,Object>>(){}.getType());
	    String rtnMap = sv.doSignWeb(map);
		return JsonHelper.json(rtnMap);
	}
	
	/**
	 * 分享订单
	 * @return
	 * @throws Exception
	 */
	public String getOrdersByOrderNo() throws Exception{
		Map<String,Object> inParam = SysContexts.getRequestParamFlatMap();
		OrderShareIn in = new OrderShareIn();
		BeanUtils.populate(in, inParam);
		in.setInCode(InterFacesCodeConsts.SBITIP.SHARE_ORDER);
		SimpleOutParamVO<Map<String,Object>> out = CallerProxy.call(in, new TypeToken<SimpleOutParamVO<Map<String,Object>>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 运单管理列表查询统计
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String queryTracksSum() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken< Map<String, Object>>(){}.getType());
		Map<String, Object> rtnMap = sv.queryTracksSum(map);
		return JsonHelper.json(rtnMap);
	}
	
	
	
	public String queryDepart() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrdOrdersWebIntf sv = CallerProxy.getSVBean(IOrdOrdersWebIntf.class, "ordOrdersWebTF", new TypeToken< Map<String, Object>>(){}.getType());
		Map<String, Object> rtnMap = sv.queryDepart(map);
		return JsonHelper.json(rtnMap);
	}
	
	//cancelOrderInfo
	
	public String cancelOrderInfo() throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrderInfoIntf sv = CallerProxy.getSVBean(IOrderInfoIntf.class, "orderInfoTF", new TypeToken<Map<String,Object>>(){}.getType());
	    String rtnMap = sv.cancelOrderInfo(map);
		return JsonHelper.json(rtnMap);
	
	}
	
	
	
	
	
	
}
