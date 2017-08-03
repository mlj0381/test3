package com.wo56.business.route.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class OrgFeeConfigParamDtlIn implements IParamIn {

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.BASE.QUERY_ORG_FEE_DTL;
	}
	
		private long orgId;//网点ID
		private int countType;//计费类型
		private long tenantId;
		private long feeId;
		private int collectType;
	
		public long getOrgId() {
			return orgId;
		}
		public void setOrgId(long orgId) {
			this.orgId = orgId;
		}
		public int getCountType() {
			return countType;
		}
		public void setCountType(int countType) {
			this.countType = countType;
		}
	
		public long getTenantId() {
			return tenantId;
		}
		public void setTenantId(long tenantId) {
			this.tenantId = tenantId;
		}
		public long getFeeId() {
			return feeId;
		}
		public void setFeeId(long feeId) {
			this.feeId = feeId;
		}
		public int getCollectType() {
			return collectType;
		}
		public void setCollectType(int collectType) {
			this.collectType = collectType;
		}
		

	}
