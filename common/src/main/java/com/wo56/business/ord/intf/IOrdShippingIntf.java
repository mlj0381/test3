package com.wo56.business.ord.intf;

import java.util.Map;

import com.wo56.business.ord.vo.out.ShipDepartInfoDetailOut;

/**
 * 船运发车、到车、配载业务
 * @author zjy
 *
 */
public interface IOrdShippingIntf {

	
	/**
	 * 查询船运信息
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryShipInfo(Map<String,Object> inParam) throws Exception;
	
	/**
	 * 保存船运配载信息
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> doSave(Map<String,Object> inParam) throws Exception;

	/**
	 * 删除船运配载信息
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> doDel(Map<String,Object> inParam) throws Exception;

	/**
	 * 船运到货确认
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> arriveGoods(Map<String,Object> inParam) throws Exception;
	
	/**
	 * 船运发车确认
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> confirmMatchVehi(Map<String,Object> inParam) throws Exception;
	

	/**
	 * 取消船运发车确认
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> cancerShipGO(Map<String,Object> inParam) throws Exception;
	
	/**
	 * 船运到车确认
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> ordArriveVehicle(Map<String,Object> inParam) throws Exception;
	
	/**
	 * 配载详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public ShipDepartInfoDetailOut getByBacthInfo(Map<String,Object> inParam) throws Exception;
	
}
