package com.wo56.business.ord.intf;

import java.util.Map;

import com.wo56.business.ord.vo.out.OrdDepartInfoOutWeb;

public interface IOrdDepartInfoYqIntf {
	public Map<String,Object> doQuery(Map<String,Object> inParam);
	public OrdDepartInfoOutWeb getOrdDepartInfoWeb(Map<String,Object> inParam);
}
