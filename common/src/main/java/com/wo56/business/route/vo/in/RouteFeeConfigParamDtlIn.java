package com.wo56.business.route.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class RouteFeeConfigParamDtlIn implements IParamIn {

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.BASE.QUERY_ROUTE_FEE_DTL;
	}
	private long startOrgid;
	private long endOrgid;
	private long receiOrgid;
	private long tenantId;
	private long feeId;
	private int carriageMode;
	private int countType;//计费方式
	
		public int getCountType() {
			return countType;
		}
		public void setCountType(int countType) {
			this.countType = countType;
		}
		public long getStartOrgid() {
			return startOrgid;
		}
		public void setStartOrgid(long startOrgid) {
			this.startOrgid = startOrgid;
		}
		public long getEndOrgid() {
			return endOrgid;
		}
		public void setEndOrgid(long endOrgid) {
			this.endOrgid = endOrgid;
		}
		public long getReceiOrgid() {
			return receiOrgid;
		}
		public void setReceiOrgid(long receiOrgid) {
			this.receiOrgid = receiOrgid;
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
		public int getCarriageMode() {
			return carriageMode;
		}
		public void setCarriageMode(int carriageMode) {
			this.carriageMode = carriageMode;
		}
	
		

	}
