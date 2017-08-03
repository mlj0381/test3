package com.wo56.business.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.util.SysStaticDataUtil;

public class SelectStaticSV {

	public Map<String,List<Map<String,Object>>> getSelectStatic(String codeType,String codeValue){
		List<SysStaticData> sysDatas = new ArrayList();
		if(codeType!=null && !codeType.equals("") && codeValue!=null && !"".equals(codeValue)){
			SysStaticData sysData = SysStaticDataUtil.getSysStaticData(codeType, codeValue);
			sysDatas.add(sysData);
		}else if(codeType!=null && !codeType.equals("")){
			sysDatas = SysStaticDataUtil.getSysStaticDataList(codeType);
		}else{
			throw new BusinessException("入参[codeType]为空");
		}
		List<Map<String,Object>> rtnList = new ArrayList<Map<String,Object>>();
		if(sysDatas!=null&&sysDatas.size()>0){
			for(SysStaticData data : sysDatas){
				if(data!=null){
					Map<String,Object> hs = new HashMap<String,Object>();
					hs.put("codeValue", data.getCodeValue());
					hs.put("codeName", data.getCodeName());
					hs.put("codeTypeAlias", data.getCodeTypeAlias()==null?"":data.getCodeTypeAlias());
					hs.put("codeDesc", data.getCodeDesc()==null?"":data.getCodeDesc());
					hs.put("sortId", data.getSortId());
					rtnList.add(hs);
				}
			}
		}
		Map<String,List<Map<String,Object>>> cnt = new HashMap<String,List<Map<String,Object>>>();
		cnt.put("SysStaticDatas", rtnList); 
		return cnt;
	}
}
