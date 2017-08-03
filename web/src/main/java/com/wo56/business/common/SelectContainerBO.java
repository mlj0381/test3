package com.wo56.business.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.common.vo.out.ContainerParamOut;

public class SelectContainerBO {
	
	public String doQueryContainer() throws Exception {	
		Map<String, String[]> mapPara = SysContexts.getRequestParameterMap();
		String addVCode=DataFormat.getStringKey(mapPara,"addV");
	//	String vehicleLength = SysContexts.getRequestParam("vehicleLength");
		Map map = new HashMap();
		List<SysStaticData> lists = SysStaticDataUtil.getSysStaticDataList("VEHICLE_TYPE");
		List list3 = new ArrayList();
		if(lists.size()>0){
			for(int i=0;i<lists.size();i++){
				SysStaticData sysStaticData = lists.get(i);
				ContainerParamOut containerBean = new  ContainerParamOut();
				containerBean.setContainerId(sysStaticData.getCodeValue());
				//注册车辆时过滤掉“不限”
				if("不限".equals(sysStaticData.getCodeName())){
					containerBean.setContainerName("");
				}else{
					containerBean.setContainerName(sysStaticData.getCodeName());
				}
				containerBean.setContainerType(sysStaticData.getCodeType());
				if(map.containsKey(sysStaticData.getCodeValue())){
				}else{
				   list3.add(containerBean);
				}
			}
		}
		return JsonHelper.json(list3);
	}
}
