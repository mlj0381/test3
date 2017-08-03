package com.wo56.business.route.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class RouteRutingIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ROUTE.QUERY_ROUTE_RUTING;
	}
	/**网点编号**/
	//public String orgId;
	public Integer collectType;//1、查询起始网点        3、查询是结束网点
	public Integer addType;//1、不添加“全部”  2、要添加“全部”
	public Integer getCollectType() {
		return collectType;
	}
	public void setCollectType(Integer collectType) {
		this.collectType = collectType;
	}
	public Integer getAddType() {
		return addType;
	}
	public void setAddType(Integer addType) {
		this.addType = addType;
	}
	
}
