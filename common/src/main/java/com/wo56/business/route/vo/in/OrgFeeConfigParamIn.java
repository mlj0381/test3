package com.wo56.business.route.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrgFeeConfigParamIn extends PageInParamVO implements IParamIn {

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.BASE.ORG_FEE_CONFIG_QUERY;
	}
	
	private long orgId;//网点ID
	private long feeId;//科目ID
	
		public long getOrgId() {
			return orgId;
		}
		public void setOrgId(long orgId) {
			this.orgId = orgId;
		}
		public long getFeeId() {
			return feeId;
		}
		public void setFeeId(long feeId) {
			this.feeId = feeId;
		}
		

	}
