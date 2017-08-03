package com.wo56.business.ord.intf;

import java.util.List;
import java.util.Map;

import com.wo56.business.ord.vo.out.OrdChildOrderOpLogListOut;
import com.wo56.business.ord.vo.out.OrdOpLogListOut;

public interface IOrderInfoIntf {
	public Map<String,Object> queryOrderInfo(Map<String,Object> inParam);
	
	public Map<String,Object> queryOrderInfoWeb(Map<String,Object> inParam);
	//web 运单签收操作
	public String doSignWeb(Map<String,Object> inParam)throws Exception;
    //web 取消订单
	public String cancelOrderInfo(Map<String, Object> inParam) throws Exception;

	public Map<String, Object> queryChildOrderLogByRole(Map<String, Object> map)  throws Exception;
	
	
	
}
