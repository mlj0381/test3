package com.wo56.business.ord.intf;

import java.util.List;
import java.util.Map;

import com.wo56.business.ord.vo.out.OrdSalesTrackingOut;
public interface IOrdOrderInfoNewIntf {
	/**
	 * 保存运单：目前是签收后修改运单的操作
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doSave(Map inParam) throws Exception;
	/**
	 * 运单详情的方法
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getOrderInfoDetailNew(Map<String,Object> inParam)throws Exception;
	/**
	 * 运单详情的方法
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> selectAll(Map<String,Object> inParam)throws Exception;
	/**
	 * 运单详情费用核销展示
	 */
	public Map<String,Object> queryOrdFreeAcc(Map<String,Object> inParam);
	/**
	 * 运单详情费用异动展示
	 */
	public Map<String,Object> queryTranCostShowByTrackingNum(Map<String,Object> inParam);
	/**
	 * 运单详情售后跟踪展示
	 */
	public List<OrdSalesTrackingOut> queryOrdSalesByTrackingNum(Map<String,Object> inParam) throws Exception;
	/**
	 * 运单管理查询：新的方式，不是分页的方式
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryOrderInfo(Map<String,Object> inParam)throws Exception;
}
