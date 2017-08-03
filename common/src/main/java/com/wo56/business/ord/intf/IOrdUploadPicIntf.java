package com.wo56.business.ord.intf;

import java.util.Map;

public interface IOrdUploadPicIntf {
	
	public Map queryOrderPic(Map<String,Object>inParam)throws Exception;
	
	public Map doSaveOrderPic(Long orderId,String flowId,Long trackingNum)throws Exception;
	
	public Map doSaveOrderPic(Map<String,Object>inParam)throws Exception;
}
