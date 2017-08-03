package com.wo56.business.ord.intf;

import java.util.List;
import java.util.Map;

import com.wo56.business.ord.vo.OrdSignInfo;
import com.wo56.business.ord.vo.OrderFee;
import com.wo56.business.ord.vo.out.OrdDetailOutParam;
import com.wo56.business.ord.vo.out.OrdGoodsInfoOutParam;
import com.wo56.business.ord.vo.out.OrdOpLogListOut;
import com.wo56.business.ord.vo.out.TrackListOutParam;

public interface IOrdOrdersWebIntf {
	
	/***
	 * 统计任务数量
	 * */
	public Map<String,Integer> statisticsTaskCount(Map<String,Object> inParam) throws Exception ;
	
	/***
	 *任务查询
	 * */
	public Map<String,Object> doQuery(Map<String,Object> inParam) throws Exception ;
	
	
	/***
	 *漂浮框查询多个提货信息
	 * */
	public List<Map<String,Object>> queryPickInfo(Map<String,Object> inParam) throws Exception ; 
	/****
	 * 催单列表查询
	 * @param inParam
	 * */
	public Map<String,Object>   reminderQuery(Map<String,Object> inParam) throws Exception;
	
	/****
	 * 订单管理列表查询
	 * @param inParam
	 * */
	public Map<String,Object>   queryOrders(Map<String,Object> inParam) throws Exception;
		
	/****
	 * 查询拉包工
	 * @param inParam
	 * */
	public Map<String,Object>   queryWorker(Map<String,Object> inParam) throws Exception;
	
	/****
	 * 分配拉包工
	 * @param inParam
	 * */
	public Map<String,Object>   disReceipt(Map<String,Object> inParam) throws Exception;


	/****
	 * 到货操作
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object>  gxArriveGoods(Map<String,Object> inParam) throws Exception;
	
	/****
	 * 配送  602009
	 * @param inParam
	 * */
	public Map<String,Object>  deliveryOrder(Map<String,Object> inParam) throws Exception;
	
	/**
	 * 下单详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public OrdDetailOutParam  ordDetails(Map<String,Object> inParam) throws Exception;
	
	
	/**
	 * 运单详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public OrdDetailOutParam ordInfoDetails(Map<String, Object> map)throws Exception;
	
	/**
	 * 下单发货信息详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List<OrdGoodsInfoOutParam>  queryGoodsInfo(Map<String,Object> inParam) throws Exception;
	
	/**
	 * 开单费用信息详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public OrderFee  queryFee(Map<String,Object> inParam) throws Exception;
	
	/**
	 * 签收信息详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public OrdSignInfo  querySignInfo(Map<String,Object> inParam) throws Exception;
	/**
	 * 跟踪信息详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List<OrdOpLogListOut>   queryLogByRole(Map<String,Object> inParam) throws Exception;
	
	/**
	 * 签收信息
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object>   signUp(Map<String,Object> inParam) throws Exception;
	
	/****
	 * 运单管理列表查询
	 * @param inParam
	 * */

	public Map<String, Object> queryTracks(Map<String, Object> map) throws Exception;
	
	/****
	 * 运单管理列表查询
	 * @param inParam
	 * */

	public Map<String, Object> queryTracksSum(Map<String, Object> map) throws Exception;
	
	
	/****
	 * 子运单列表查询
	 * @param inParam
	 * */
	
	public Map<String, Object> querychildOrderInfo(Map<String, Object> map) throws Exception;
	/****
	 * 子运单列表查询
	 * @param inParam
	 * */
	
	public Map<String, Object> queryDepart(Map<String, Object> map) throws Exception;

	/****
	 * 待发车管理列表查询
	 * @param inParam
	 * */
	public Map<String,Object>   queryWaitDeparts(Map<String,Object> inParam) throws Exception;

}
