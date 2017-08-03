package com.wo56.business.order.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.core.exception.BusinessException;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.wo56.business.route.impl.GraphRouteSV;
import com.wo56.common.consts.EnumConstsYQ;

public class OrderPostUtil {
	
	private static final Log log = LogFactory.getLog(OrderPostUtil.class);
	
	private static final String KEY_INFO = "KEY_INFO";//接口
	private static final String KEY_ORDER_NUMBER = "KEY_ORDER_NUMBER";//运单号
	private static final String KEY_USER_ID = "KEY_USER_ID";//用户
	private static final String KEY_DATE = "KEY_DATE";//时间
	private static final String KEY_CREATE_ID = "KEY_CREATE_ID";//创建人
	private static final String KEY_PASS_PORT = "KEY_PASS_PORT"; //证据类型  身份证，军官证等
	private static final String KEY_PASS_PORT_NO = "KEY_PASS_PORT_NO"; //证件编号
	private static final String KEY_SIGN_DATE = "KEY_SIGN_DATE"; //签收日期
	private static final String KEY_SIGN_MAN = "KEY_SIGN_MAN"; //签收人
	
	
	
	/**
	 * 柯莱取消订单TMS
	 * @param cargoNO
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public static String cancelOrderKL(String cargoNO,String userid,long tenantId,boolean isTask) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		map.put(KEY_INFO, EnumConstsYQ.KL_PARAM.ACTION);
		map.put(KEY_ORDER_NUMBER, EnumConstsYQ.KL_PARAM.CARGO_NO);
		map.put(KEY_USER_ID, EnumConstsYQ.KL_PARAM.USERID);
		String json = OrderOperation.cancelOrder(EnumConstsYQ.KL_URL.KL_URL, EnumConstsYQ.KL_INFO.CANCEL_ORDER, cargoNO, userid,tenantId, map,isTask);
		return status(json,"取消订单错误！");
	}
	/**
	 * 到货TMS
	 * @param cargoNO
	 * @param userid
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String arrivalKL(String cargoNO,String userid,String date,long tenantId,boolean isTask) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		map.put(KEY_INFO, EnumConstsYQ.KL_PARAM.ACTION);
		map.put(KEY_ORDER_NUMBER, EnumConstsYQ.KL_PARAM.CARGO_NO);
		map.put(KEY_USER_ID, EnumConstsYQ.KL_PARAM.USERID);
		map.put(KEY_DATE, EnumConstsYQ.KL_PARAM.CREATE_DATE);
		String json = OrderOperation.arrivalOrDispatcheing(EnumConstsYQ.KL_URL.KL_URL, EnumConstsYQ.KL_INFO.ARRIVAL_GOODS, cargoNO, userid,tenantId, date, map,1,isTask);
		return status(json,"到货错误！");
	}
	
	/**
	 * 配送TMS
	 * @param cargoNO
	 * @param userid
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String dispatcheingKL(String cargoNO,String userid,String date,long tenantId,boolean isTask) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		map.put(KEY_INFO, EnumConstsYQ.KL_PARAM.ACTION);
		map.put(KEY_ORDER_NUMBER, EnumConstsYQ.KL_PARAM.CARGO_NO);
		map.put(KEY_USER_ID, EnumConstsYQ.KL_PARAM.USERID);
		map.put(KEY_DATE, EnumConstsYQ.KL_PARAM.CREATE_DATE);
		String json = OrderOperation.arrivalOrDispatcheing(EnumConstsYQ.KL_URL.KL_URL, EnumConstsYQ.KL_INFO.DISPATCHING, cargoNO, userid,tenantId, date, map,2,isTask);
		return status(json,"配送错误");
	}
	
	/**
	 * 签收TMS
	 * @param cargoNO
	 * @param userid
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String signKL(String cargoNO,String userid,String date,String createID,String passPort,String passPortNo,String signDate,String signMan,long tenantId,boolean isTask) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		map.put(KEY_INFO, EnumConstsYQ.KL_PARAM.ACTION);
		String json = OrderOperation.sign(EnumConstsYQ.KL_URL.KL_URL, EnumConstsYQ.KL_INFO.SIGN, cargoNO, userid, date, createID, passPort, passPortNo, signDate, signMan, tenantId,map,isTask);
		return status(json,"签收错误！");
	}
	/**
	 * KL
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public static String billingOrderKL(Map<String,Object> inParam,long tenantId,boolean isTask) throws Exception{
		List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
		items.add(inParam);
		String json = OrderOperation.billingOrder(items, EnumConstsYQ.KL_URL.KL_URL, EnumConstsYQ.KL_PARAM.ACTION, tenantId, isTask);
		return status(json,"开单错误！");
	}
	
	private static String status(String json,String error){
		if (!"Y".equals(json)) {
			
			if (StringUtils.isNotEmpty(json)) {
				try{
					Map<String,Object> inParam = JsonHelper.parseJSON2Map(json);
					int errorCode = DataFormat.getIntKey(inParam, "errorCode");
					String errorMessage = DataFormat.getStringKey(inParam, "errorMessage");
					if (errorCode == 0) {
						return "Y";
					}else{
						return errorMessage;
					}
				}catch(Exception e){
					log.equals("返回json格式错误！");
					return "N";
				}
			}else{
				log.error(error);
				return "N";
			}
		}
		return "Y";
	}
	
}
