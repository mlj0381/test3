package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdQueryArrOrDelIn extends PageInParamVO implements IParamIn{

	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORD_ARRIVE_OR_DELINERY_QUERY_LYH;
	}
	/**到货/发货*/
	private String type;
	/**发货时间*/
	private String beginDate;
	/**到货时间*/
	private String endDate;
	/**收货站点*/
	private String beginOrgId;
	/**目的站点*/
	private String endOrgId;
	
	/**请求类型*/
	private String typeQuery;
	private String arrEndDate;
	private String arrBeginDate;
	
	
	public String getArrEndDate() {
		return arrEndDate;
	}
	public void setArrEndDate(String arrEndDate) {
		this.arrEndDate = arrEndDate;
	}
	public String getArrBeginDate() {
		return arrBeginDate;
	}
	public void setArrBeginDate(String arrBeginDate) {
		this.arrBeginDate = arrBeginDate;
	}
	public String getTypeQuery() {
		return typeQuery;
	}
	public void setTypeQuery(String typeQuery) {
		this.typeQuery = typeQuery;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getBeginOrgId() {
		return beginOrgId;
	}
	public void setBeginOrgId(String beginOrgId) {
		this.beginOrgId = beginOrgId;
	}
	public String getEndOrgId() {
		return endOrgId;
	}
	public void setEndOrgId(String endOrgId) {
		this.endOrgId = endOrgId;
	}
	
	
}
