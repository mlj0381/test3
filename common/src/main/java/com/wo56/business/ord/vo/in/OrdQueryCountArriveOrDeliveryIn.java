package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrdQueryCountArriveOrDeliveryIn extends PageInParamVO implements IParamIn{

	private static final long serialVersionUID = -3107996056975599605L;
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.ORD.ORD_ARRIVE_OR_DELINERY_COUNT_QUERY;
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
