package com.wo56.business.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.sms.vo.in.SysSmsSearchList;
import com.wo56.business.sms.vo.in.SysSmsSearchVO;
import com.wo56.business.sms.vo.in.SysSmsSendIn;
import com.wo56.common.consts.InterFacesCodeConsts;
//import com.wo56.common.utils.SmsUtil;

public class SysSmsSendBO {

	public String doSend() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		SysSmsSendIn smsSendIn = new SysSmsSendIn(InterFacesCodeConsts.COMMON.SYS_SMS_SEND);
		BeanUtils.populate(smsSendIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(smsSendIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	  return JsonHelper.json(out.getContent());
	}
	
	public String doSearchMsm() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String bills = DataFormat.getStringKey(map, "bills");
		SysSmsSearchList sysSmsSearchList = new SysSmsSearchList();
		List<SysSmsSearchVO> list = new ArrayList<SysSmsSearchVO>();
		if(bills!=null && bills!="" && bills.split(",").length>0){
			for(int i=0;i<bills.split(",").length;i++){
				SysSmsSearchVO vo = new SysSmsSearchVO();
				vo.setConsigneeBill(DataFormat.getStringKey(map, "bills["+i+"].consigneeBill"));
				vo.setDeliveryType(DataFormat.getIntKey(map, "bills["+i+"].deliveryType"));
				vo.setTrackingNum(DataFormat.getStringKey(map, "bills["+i+"].trackingNum"));
				vo.setGoodsName(DataFormat.getStringKey(map, "bills["+i+"].goodsName"));
				list.add(vo);
			}
			sysSmsSearchList.setList(list);
			SimpleOutParamVO<Map> out = CallerProxy.call(sysSmsSearchList, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		}
		return "";
	}
	
	public String getTemplate() throws Exception{
		SysSmsSendIn smsSendIn = new SysSmsSendIn(InterFacesCodeConsts.COMMON.GET_SYS_TEMPLATE);
		SimpleOutParamVO<Map> out = CallerProxy.call(smsSendIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return  JsonHelper.json(out.getContent());
	}
}
